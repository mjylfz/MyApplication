package com.example.john.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;

import customview.CameraInterface;
import customview.CameraView;

/**
 * Created by LFZ on 2017/12/21.
 */

public class CameraActivity extends AppCompatActivity implements CameraInterface.CamOpenOverCallback{

    CameraView cameraView;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        cameraView = (CameraView)findViewById(R.id.camera);
        button = (Button)findViewById(R.id.take_picture);

        Thread openThread = new Thread(){
            @Override
            public void run() {
                CameraInterface.getInstance().doOpenCamera(CameraActivity.this);
            }
        };
        openThread.start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraInterface.getInstance().doTakePicture();
            }
        });
    }

    @Override
    public void cameraHasOpened() {
        SurfaceHolder holder = cameraView.getmSurfaceHolder();
        CameraInterface.getInstance().doStartPreview(holder, -1f);
    }
}
