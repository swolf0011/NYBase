package com.swolf.librarymedia.camera.image;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraCaptureSession;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * 拍照保存图片状态回调
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NYCameraCaptureSessionStateCallback extends CameraCaptureSession.StateCallback {

  NYCamera2Image camera2Utils;

  public NYCameraCaptureSessionStateCallback(NYCamera2Image camera2Utils) {
    this.camera2Utils = camera2Utils;
  }
  @Override
  public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
    //The camera is already closed
    if (null == camera2Utils.cameraDevice) {
      return;
    }
    // When the session is ready, we start displaying the preview.
    camera2Utils.cameraCaptureSessions = cameraCaptureSession;
    camera2Utils.updatePreview();
  }

  @Override
  public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
    Toast.makeText(camera2Utils.activity, "Configuration change", Toast.LENGTH_SHORT).show();
  }
}
