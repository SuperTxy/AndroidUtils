package com.txy.androidutils;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.MEDIA_MOUNTED;
import static android.os.Environment.getExternalStorageState;

/**
 * Created by Apple on 17/9/10.
 */

public class FileUtils {

    public static File createTmpFile(Context context, String prefix, String suffix) throws IOException {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
        prefix = prefix + date + "_";
        File dir;
        if (TextUtils.equals(getExternalStorageState(), MEDIA_MOUNTED)) {
            dir = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM + "/Camera");
            if (!dir.exists()) {
                dir = getCacheDirectory(context, true);
            }
        } else {
            dir = getCacheDirectory(context, true);
        }
        return File.createTempFile(prefix, suffix, dir);
    }

    public static File createIMGFile(Context context) throws IOException {
        return createTmpFile(context, "IMG", ".jpg");
    }

    public static File createVIDFile(Context context) throws IOException {
        return createTmpFile(context, "VID", ".mp4");
    }

    private static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = getExternalStorageState();
        } catch (NullPointerException e) {
            externalStorageState = "";
        } catch (IncompatibleClassChangeError e) {
            externalStorageState = "";
        }
        if (preferExternal && TextUtils.equals(externalStorageState, MEDIA_MOUNTED) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = context.getFilesDir().getPath() + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return appCacheDir;

    }

    private static boolean hasExternalStoragePermission(Context context) {
        return PERMISSION_GRANTED == context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}

