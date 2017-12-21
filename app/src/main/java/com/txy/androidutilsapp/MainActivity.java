package com.txy.androidutilsapp;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.txy.androidutils.R;

public class MainActivity extends AppCompatActivity {
    private String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new TxyPermissionUtils(this).checkPermission(permissions, "没有权限",
//                new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
    }
}
