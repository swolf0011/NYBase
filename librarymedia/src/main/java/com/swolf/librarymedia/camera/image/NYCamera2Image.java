package com.swolf.librarymedia.camera.image;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.view.Surface;


import com.swolf.librarymedia.camera.NYAutoFitTextureView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 拍照保存图片
 * <!—请求访问使用照相设备-->
 * <uses-permission android:name="android.permission.CAMERA" />
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NYCamera2Image {

    private void demo() {
        final File file = new File(Environment.getExternalStorageDirectory() + "/pic0.jpg");
        NYCamera2Image camera2Utils = new NYCamera2Image(activity, textureView, file.getAbsolutePath(), "0", 2);

        camera2Utils.startCamera2();

        camera2Utils.stopCamera2();
    }

    protected Activity activity;
    private CameraManager manager;
    private NYAutoFitTextureView textureView;
    protected File mFile;

    //    摄像头ID，"0"后，"1"前，"2"外置
    protected String cameraId;
    /**
     * 闪光。
     */
    private int control_ae_mode = CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH;

    //定义代表摄像头的成员变量，代表系统摄像头，该类的功能类似早期的Camera类。
    protected CameraDevice cameraDevice;
    //定义CameraCaptureSession成员变量，是一个拍摄绘话的类，用来从摄像头拍摄图像或是重新拍摄图像,这是一个重要的API.
    protected CameraCaptureSession cameraCaptureSessions;
    /**
     * 当程序调用setRepeatingRequest()方法进行预览时，或调用capture()进行拍照时，都需要传入CaptureRequest参数时
     * captureRequest代表一次捕获请求，用于描述捕获图片的各种参数设置。比如对焦模式，曝光模式...等，程序对照片所做的各种控制，
     * 都通过CaptureRequest参数来进行设置CaptureRequest.Builder 负责生成captureRequest对象
     */
    protected CaptureRequest.Builder captureRequestBuilder;
    //预览尺寸
    private Size imageDimension;
    private ImageReader imageReader;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private Size largest;
    private int mSensorOrientation;
    private boolean mFlashSupported;


    /**
     * @param activity
     * @param textureView
     * @param imgFile
     */
    public NYCamera2Image(Activity activity, NYAutoFitTextureView textureView, String imgFile) {
        this(activity, textureView, imgFile, "0", 2);
    }

    /**
     * @param activity
     * @param textureView
     * @param imgFile
     * @param mCameraId       "0"后，"1"前，"2"外置
     * @param control_ae_mode 闪光灯的模式
     *                        CONTROL_AE_MODE_OFF = 0;
     *                        CONTROL_AE_MODE_ON = 1;
     *                        CONTROL_AE_MODE_ON_AUTO_FLASH = 2;
     *                        CONTROL_AE_MODE_ON_ALWAYS_FLASH = 3;
     *                        CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE = 4;
     */
    public NYCamera2Image(Activity activity, NYAutoFitTextureView textureView, String imgFile, String mCameraId, int control_ae_mode) {
        this.activity = activity;
        this.textureView = textureView;
        this.manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        this.mFile = new File(imgFile);
        setParam(mCameraId, control_ae_mode);
    }


    private void setParam(String cameraId, int control_ae_mode) {
        this.cameraId = cameraId;
        if ((!"0".equals(this.cameraId)) && (!"1".equals(this.cameraId)) && (!"2".equals(this.cameraId))) {
            this.cameraId = "0";
        }
        this.control_ae_mode = control_ae_mode;
        if (this.control_ae_mode != CameraMetadata.CONTROL_AE_MODE_OFF
                && this.control_ae_mode != CameraMetadata.CONTROL_AE_MODE_ON
                && this.control_ae_mode != CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH
                && this.control_ae_mode != CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH
                && this.control_ae_mode != CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE
                ) {
            this.control_ae_mode = CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH;
        }
    }

    public void startCamera2() {
        startBackgroundThread();
        if (textureView.isAvailable()) {
            takePicture();
        } else {
            textureView.setSurfaceTextureListener(new NYTextureViewSurfaceTextureListener(this));
        }
    }

    public void stopCamera2() {
        closeCamera();
        stopBackgroundThread();
    }

    protected void openCamera(int width, int height) {
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            //权限检查
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, NYCamera2ImageConstant.REQUEST_CAMERA_PERMISSION);
                return;
            }

//      imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            largest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new NYCompareSizesByArea());
            int displayRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            boolean swappedDimensions = false;
            switch (displayRotation) {
                case Surface.ROTATION_0:
                case Surface.ROTATION_180:
                    if (mSensorOrientation == 90 || mSensorOrientation == 270) {
                        swappedDimensions = true;
                    }
                    break;
                case Surface.ROTATION_90:
                case Surface.ROTATION_270:
                    if (mSensorOrientation == 0 || mSensorOrientation == 180) {
                        swappedDimensions = true;
                    }
                    break;
                default:
            }
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            int rotatedPreviewWidth = width;
            int rotatedPreviewHeight = height;
            int maxPreviewWidth = displaySize.x;
            int maxPreviewHeight = displaySize.y;

            if (swappedDimensions) {
                rotatedPreviewWidth = height;
                rotatedPreviewHeight = width;
                maxPreviewWidth = displaySize.y;
                maxPreviewHeight = displaySize.x;
            }

            if (maxPreviewWidth > NYCamera2ImageConstant.MAX_PREVIEW_WIDTH) {
                maxPreviewWidth = NYCamera2ImageConstant.MAX_PREVIEW_WIDTH;
            }

            if (maxPreviewHeight > NYCamera2ImageConstant.MAX_PREVIEW_HEIGHT) {
                maxPreviewHeight = NYCamera2ImageConstant.MAX_PREVIEW_HEIGHT;
            }

            imageDimension = NYCamera2ImageConstant.chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth, maxPreviewHeight, largest);
            int orientation = activity.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                textureView.setAspectRatio(imageDimension.getWidth(), imageDimension.getHeight());
            } else {
                textureView.setAspectRatio(imageDimension.getHeight(), imageDimension.getWidth());
            }
            // Check if the flash is supported.
            Boolean available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            mFlashSupported = available == null ? false : available;

            configureTransform(width, height);

            manager.openCamera(cameraId, new NYCameraDeviceStateCallback(this), null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Configures the necessary {@link Matrix} transformation to `mTextureView`.
     * This method should be called after the camera preview size is determined in
     *
     * @param viewWidth  The width of `mTextureView`
     * @param viewHeight The height of `mTextureView`
     */
    private void configureTransform(int viewWidth, int viewHeight) {
        if (null == textureView || null == imageDimension || null == activity) {
            return;
        }
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, imageDimension.getHeight(), imageDimension.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max((float) viewHeight / imageDimension.getHeight(), (float) viewWidth / imageDimension.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180, centerX, centerY);
        }
        textureView.setTransform(matrix);
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (null != imageReader) {
            imageReader.close();
            imageReader = null;
        }
    }


    protected void updatePreview() {
        //设置模式为自动
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void takePicture() {
        if (null == cameraDevice) {
            return;
        }
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());

            //定义图像尺寸
            Size[] jpegSizes = null;
            if (characteristics != null) {
                //获取摄像头支持的最大尺寸
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
            }
            int width = 640;
            int height = 480;
            if (jpegSizes != null && 0 < jpegSizes.length) {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }
            //创建一个ImageReader对象，用于获得摄像头的图像数据
            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 2);
            //动态数组
            List<Surface> outputSurfaces = new ArrayList<Surface>(2);

            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(textureView.getSurfaceTexture()));
            //生成请求对象（TEMPLATE_STILL_CAPTURE此处请求是拍照）
            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            //将ImageReader的surface作为captureBuilder的输出目标
            captureBuilder.addTarget(reader.getSurface());
            ////设置自动对焦模式
            //captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            ////设置自动曝光模式
            //captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);//推荐采用这种最简单的设置请求模式
            // 获取设备方向
            int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            //根据设置方向设置照片显示的方向
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, NYCamera2ImageConstant.ORIENTATIONS.get(rotation));
            //设置图片的存储位置
//      final File file = new File(Environment.getExternalStorageDirectory() + "/pic.jpg");
            //ImageReader监听函数
            ImageReader.OnImageAvailableListener readerListener = new NYImageReaderOnImageAvailableListener(this);

            reader.setOnImageAvailableListener(readerListener, mBackgroundHandler);
            //拍照开始或是完成时调用，用来监听CameraCaptureSession的创建过程
            final CameraCaptureSession.CaptureCallback captureListener = new NYCameraCaptureSessionCaptureCallback(this);

            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    protected void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            //设置默认的预览大小
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            //请求预览
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            //创建cameraCaptureSession,第一个参数是图片集合，封装了所有图片surface,第二个参数用来监听这处创建过程
            cameraDevice.createCaptureSession(Arrays.asList(surface), new NYCameraCaptureSessionStateCallback(this), null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


}
