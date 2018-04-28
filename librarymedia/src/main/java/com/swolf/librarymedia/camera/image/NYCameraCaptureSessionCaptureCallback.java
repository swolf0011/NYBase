package com.swolf.librarymedia.camera.image;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.widget.Toast;

/**
 * 拍照保存图片捕获回调
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NYCameraCaptureSessionCaptureCallback extends CameraCaptureSession.CaptureCallback {
  NYCamera2Image camera2Utils;

  public NYCameraCaptureSessionCaptureCallback(NYCamera2Image camera2Utils) {
    this.camera2Utils = camera2Utils;
  }
  @Override
  //拍照完成后提示图片的保存位置
  public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
    super.onCaptureCompleted(session, request, result);
    Toast.makeText(camera2Utils.activity, "Saved:" + camera2Utils.mFile, Toast.LENGTH_SHORT).show();
    camera2Utils.createCameraPreview();
  }
}
