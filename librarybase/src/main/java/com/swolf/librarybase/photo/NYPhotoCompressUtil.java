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
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap bitmapOrg = bitmap;
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap b = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
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


    /**
     * 压缩指定宽高图片到指定大小到文件
     *
     * @param filePath
     * @param cf       枚举：
     *                 CompressFormat.JPEG;
     *                 CompressFormat.PNG;
     *                 CompressFormat.WEBP;
     * @param n_w
     * @param n_h
     * @return
     */
    public static byte[] imageFilePathToBytes(String filePath, CompressFormat cf, int n_w, int n_h) {
        byte[] bs = null;
        Bitmap bitmap = imageFilePathToBitmap(filePath, cf, n_w, n_h);
        int quality = 100; // 100:not compress
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(cf, quality, baos);
        bs = baos.toByteArray();
        if (baos != null) {
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bitmap.recycle();
        return bs;

    }

    public static Bitmap imageFilePathToBitmap(String filePath, CompressFormat cf, int n_w, int n_h) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// begin set is true;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, newOpts);
        newOpts.inJustDecodeBounds = false;// set is true;

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        int inSampleSize = 1;// 1:not compress
        if (w > h && w > n_w) {
            inSampleSize = (int) (newOpts.outWidth / n_w);
        } else if (w < h && h > n_h) {
            inSampleSize = (int) (newOpts.outHeight / n_h);
        }
        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        newOpts.inSampleSize = inSampleSize;
        newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
        // newOpts.inPurgeable = true;// 同时设置才会有效
        // newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(filePath, newOpts);
        return bitmap;
    }
}
