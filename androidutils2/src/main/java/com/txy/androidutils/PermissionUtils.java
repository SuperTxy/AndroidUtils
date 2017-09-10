package com.txy.androidutils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.txy.androidutils.dialog.DialogUtils;

/**
 * Created by Apple on 17/9/10.
 */

public class PermissionUtils {
    private Runnable mHasPermissionRunnable;
    private Runnable mNoPermissionRunnable;
    private int REQUEST_CODE_PERMISSION = 1000;
    private Activity context;
    private DialogUtils dialogUtils;

    public PermissionUtils(Activity context) {
        this.context = context;
    }

    public void checkPermission(String[] permissions, final String message, Runnable hasPermissionDo) {
        dialogUtils = new DialogUtils(context);
        checkPermission(permissions, hasPermissionDo, new Runnable() {
            @Override
            public void run() {
                dialogUtils.showPermissionDialog(message);
            }
        });
    }

    public void checkPermission(String[] permissions, Runnable hasPermissionDo, Runnable noPermissionDo) {
        mHasPermissionRunnable = null;
        mNoPermissionRunnable = null;
        if (isPermissionGranted(permissions))
            mHasPermissionRunnable.run();
        else if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[0]))
            mNoPermissionRunnable.run();
        else {
            ActivityCompat.requestPermissions(context, permissions, REQUEST_CODE_PERMISSION);
            mNoPermissionRunnable = noPermissionDo;
            mHasPermissionRunnable = hasPermissionDo;
        }
    }

    public boolean isPermissionGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private boolean isAllGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (isAllGranted(grantResults))
                mHasPermissionRunnable.run();
            else mNoPermissionRunnable.run();
        }
    }

    public void destroy() {
        if (dialogUtils != null)
            dialogUtils.destroy();
    }
}
