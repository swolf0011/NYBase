package com.swolf.librarymedia.camera.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.media.ImageReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * 比较大小关于面积
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYImageReaderOnImageAvailableListener implements ImageReader.OnImageAvailableListener {
    NYCamera2Image camera2Utils;

    public NYImageReaderOnImageAvailableListener(NYCamera2Image camera2Utils) {
        this.camera2Utils = camera2Utils;
    }


    @Override
    public void onImageAvailable(ImageReader reader) {
        Image image = null;
        //读取图像并保存
        try {
            image = reader.acquireLatestImage();
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            save(bytes);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (image != null) {
                image.close();
            }
        }
    }

    private void save(byte[] bytes) throws IOException {
        OutputStream output = null;
        try {
            output = new FileOutputStream(camera2Utils.mFile);
            output.write(bytes);
            output.flush();
        } finally {
            if (null != output) {
                output.close();
            }
        }

        if ("1".equals(camera2Utils.cameraId)) {
            Bitmap mBitmap = BitmapFactory.decodeFile(camera2Utils.mFile.getAbsolutePath());
            if (mBitmap != null) {
                int w = mBitmap.getWidth();
                int h = mBitmap.getHeight();
                Matrix matrix = new Matrix();
                //matrix.postScale(-1, 1); // 镜像水平翻转
                matrix.postScale(1, -1); // 镜像上下翻转
                Bitmap convertBmp = Bitmap.createBitmap(mBitmap, 0, 0, w, h, matrix, true);
                if (convertBmp != null) {
                    mBitmap.recycle();
                    mBitmap = convertBmp;
                }
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(camera2Utils.mFile);
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    mBitmap.recycle();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null != out) {
                        out.close();
                    }
                }
            }
        }


    }

}
