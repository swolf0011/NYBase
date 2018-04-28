package com.swolf.librarymedia.camera.image;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.support.annotation.NonNull;


/**
 * 拍照驱动状态回调
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NYCameraDeviceStateCallback extends CameraDevice.StateCallback {
    NYCamera2Image camera2Utils;

    public NYCameraDeviceStateCallback(NYCamera2Image camera2Utils) {
        this.camera2Utils = camera2Utils;
    }

    @Override
    public void onOpened(@NonNull CameraDevice cd) {
        camera2Utils.cameraDevice = cd;
        camera2Utils.createCameraPreview();
    }

    @Override
    public void onDisconnected(@NonNull CameraDevice cd) {
        camera2Utils.cameraDevice.close();
        camera2Utils.cameraDevice = null;
    }

    @Override
    public void onError(@NonNull CameraDevice cd, int error) {
        camera2Utils.cameraDevice.close();
        camera2Utils.cameraDevice = null;
    }
}
