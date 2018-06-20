package com.swolf.librarybase.photo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 压缩图片工具
 * Created by LiuYi-15973602714
 */
@SuppressLint("InflateParams")
public class NYPhotoCompressUtil {

    /**
     * 调整大小图片，放大或缩小图片
     */
    public static Bitmap resizeImage(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap b = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return b;
    }


    /**
     * 压缩图片2文件
     *
     * @param bitmap
     * @param cf       枚举：
     *                 CompressFormat.JPEG;
     *                 CompressFormat.PNG;
     *                 CompressFormat.WEBP;
     * @param new_size 新大小，单位是KB。不能<=0
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bitmap, CompressFormat cf, int new_size) {
        if (bitmap == null) {
            return null;
        }
        if (new_size <= 0) {
            new_size = 100;
        }
        int quality = 80; // 100:not compress
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(cf, quality, baos);
        while (baos.toByteArray().length / 1024 > new_size && quality > 10) { // >100KB
            baos.reset();
            quality -= 10;
            if (quality > 0) {
                bitmap.compress(cf, quality, baos);
            }
        }
        byte[] bs = baos.toByteArray();
        if (baos != null) {
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bs;
    }

    public static Bitmap bitmapToBitmap(Bitmap bitmap, CompressFormat cf, int new_size) {
        byte[] bs = bitmapToBytes(bitmap, cf, new_size);
        return BitmapFactory.decodeByteArray(bs, 0, bs.length);
    }

    public static Bitmap imageFilePathToBitmap(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        return bitmap;
    }
}
