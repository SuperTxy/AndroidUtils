package com.txy.androidutils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.orhanobut.logger.Logger;
import com.txy.androidutils.dialog.TxyDialogUtils;

/**
 * Created by Apple on 17/9/10.
 */

public class TxyPermissionUtils {

    private Runnable mHasPermissionRunnable;
    private Runnable mNoPermissionRunnable;
    private int REQUEST_CODE_PERMISSION = 1000;
    private Activity context;
    private TxyDialogUtils dialogUtils;
    private String[] recordPermissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] cameraPermissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] storagePermissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private String[] audioPermissions = {Manifest.permission.RECORD_AUDIO};

    public TxyPermissionUtils(Activity context) {
        this.context = context;
    }

    public void checkPermission(String[] permissions, final String message, Runnable hasPermissionDo) {
        dialogUtils = new TxyDialogUtils(context);
        checkPermission(permissions, hasPermissionDo, new Runnable() {
            @Override
            public void run() {
                dialogUtils.showPermissionDialog(message);
            }
        });
    }

    public void checkAudioPermission(final Runnable hasPermissionRunnable, final String message){
        checkPermission(audioPermissions, message, new Runnable() {
            @Override
            public void run() {
                if(TxyCheckPermission.getRecordState() == TxyCheckPermission.STATE_SUCCESS) {
                    hasPermissionRunnable.run();
                }else  dialogUtils.showPermissionDialog(message);
            }
        });
    }
    public void checkAudioPermission( Runnable hasPermissionRunnable){
        checkAudioPermission(hasPermissionRunnable,context.getString(R.string.no_audio_video_permission));
    }

    public void checkRecordVideoPermission(final Runnable hasPermissionRunnable) {
        checkPermission(recordPermissions, context.getString(R.string.no_record_video_permission), new Runnable() {
            @Override
            public void run() {
                if(TxyCheckPermission.getRecordState() == TxyCheckPermission.STATE_SUCCESS) {
                    if(TxyCheckPermission.isCameraUseable()) {
                        hasPermissionRunnable.run();
                    }else{
                        dialogUtils.showPermissionDialog("不开启相机权限，无法录制视频哦~");
                    }
                }else dialogUtils.showPermissionDialog(context.getString(R.string.no_record_video_permission));
            }
        });
    }

    public void checkCameraPermission(final Runnable hasPermissionRunnable) {
        checkPermission(cameraPermissions, context.getString(R.string.no_camera_permission), new Runnable() {
            @Override
            public void run() {
                if(TxyCheckPermission.isCameraUseable()) {
                    hasPermissionRunnable.run();
                }else dialogUtils.showPermissionDialog(context.getString(R.string.no_camera_permission));
            }
        });
    }

    public void checkStoragePermission(Runnable hasPermissionRunnable) {
        checkPermission(storagePermissions, context.getString(R.string.no_read_permission), hasPermissionRunnable);
    }

    public boolean isStorageGranted(){
        return isPermissionGranted(storagePermissions);
    }

    public void checkPermission(String[] permissions, Runnable hasPermissionDo, Runnable noPermissionDo) {
        mHasPermissionRunnable = null;
        mNoPermissionRunnable = null;
        if (isPermissionGranted(permissions))
            hasPermissionDo.run();
        else if (shouldShowRequestPermissionRationale(permissions))
            noPermissionDo.run();
        else {
            ActivityCompat.requestPermissions(context, noGrantedPermissions(permissions), REQUEST_CODE_PERMISSION);
            mNoPermissionRunnable = noPermissionDo;
            mHasPermissionRunnable = hasPermissionDo;
        }
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission))
                return true;
        }
        return false;
    }

    public boolean isPermissionGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private String[] noGrantedPermissions(String[] permissions) {
        StringBuilder noPermissions = new StringBuilder();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                if (i != 0) noPermissions.append(",");
                noPermissions.append(permissions[i]);
            }
        }
        Logger.e(noPermissions.toString().split(",").toString());
        return noPermissions.toString().split(",");
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
