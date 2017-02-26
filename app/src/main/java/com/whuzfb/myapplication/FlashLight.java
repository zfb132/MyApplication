package com.whuzfb.myapplication;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by zfb15 on 2017/2/18.
 */

public class FlashLight extends Activity {

    protected ImageView imgFlashlight;
    protected ImageView imgFlashlight_control;
    protected Camera mCamera;
    protected Camera.Parameters mParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashlight);

        imgFlashlight = (ImageView) findViewById(R.id.imageView_flashlight);
        imgFlashlight_control = (ImageView) findViewById(R.id.imageview_flashlight_controller);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Toast.makeText(this, "当前设备没有闪光灯", Toast.LENGTH_LONG).show();
            return;
        } else {
            Toast.makeText(this, "当前设备有闪光灯", Toast.LENGTH_LONG).show();
        }
        imgFlashlight.setTag(false);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        ViewGroup.LayoutParams laParams = (ViewGroup.LayoutParams) imgFlashlight_control.getLayoutParams();
        laParams.height = point.y * 3 / 32;
        laParams.width = point.x / 6;
        imgFlashlight_control.setLayoutParams(laParams);

    }

    public void onClick_Flashlight(View view) {
        if (((Boolean) imgFlashlight.getTag()) == false) {
            openFlashlight();
        } else {
            closeFlashlight();
        }
    }

    protected void openFlashlight(){
        TransitionDrawable drawable =(TransitionDrawable)imgFlashlight                 .getDrawable();
        drawable.startTransition(200);
        imgFlashlight.setTag(true);
        try {
            mCamera = Camera.open();
            int textureId = 0;
            mCamera.setPreviewTexture(new SurfaceTexture(textureId));
            mCamera.startPreview();
            mParameters = mCamera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // 关闭闪光灯
    protected void closeFlashlight() {
        TransitionDrawable drawable = (TransitionDrawable) imgFlashlight                 .getDrawable();
        if (((Boolean) imgFlashlight.getTag())) {
            drawable.reverseTransition(200);
            //图像切换的时间间隔Flashlight.xml
            imgFlashlight.setTag(false);
            if (mCamera != null) {
                mParameters = mCamera.getParameters();
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(mParameters);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }
    }
    //如果手电筒失去焦点，自动关闭Flashlight
    @Override
    public void onPause(){
        super.onPause();
        closeFlashlight();
    }
}
