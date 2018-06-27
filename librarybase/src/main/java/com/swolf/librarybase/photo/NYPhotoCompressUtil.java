package com.swolf.librarybase.photo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import com.swolf.librarybase.util.NYFileUtil;
import java.io.ByteArrayOutputStream;

/**
 * 压缩图片工具
 * Created by LiuYi-15973602714
 */
@SuppressLint("InflateParams")
public class NYPhotoCompressUtil {

    /**
     * 压缩图片
     *
     * @param bitmap
     * @param new_size       新大小，单位是KB。不能<=0
     * @param compressFormat 枚举：
     *                       CompressFormat.JPEG;
     *                       CompressFormat.PNG;
     *                       CompressFormat.WEBP;
     * @return
     */
    public static byte[] compressImage(Bitmap bitmap, int new_size, Bitmap.CompressFormat compressFormat) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > new_size) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            bitmap.compress(compressFormat, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        return baos.toByteArray();
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @param new_size       新大小，单位是KB。不能<=0
     * @param compressFormat 枚举：
     *                       CompressFormat.JPEG;
     *                       CompressFormat.PNG;
     *                       CompressFormat.WEBP;
     * @param filePath
     * @return
     */
    public static boolean compressImage(Bitmap bitmap, int new_size, Bitmap.CompressFormat compressFormat, String filePath) {
        byte[] bs = compressImage(bitmap, new_size, compressFormat);
        boolean b = NYFileUtil.getInstance().write(filePath, bs, true);
        return b;
    }

    public static Bitmap imageFilePathToBitmap(String filePath, Bitmap.Config rgb_565, int n_w, int n_h) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// begin set is true;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        options.inJustDecodeBounds = false;// set is true;
        int w = options.outWidth;
        int h = options.outHeight;
        int inSampleSize = 1;// 1:not compress
        if (w > h && w > n_w) {
            inSampleSize = (int) (options.outWidth / n_w);
        } else if (w < h && h > n_h) {
            inSampleSize = (int) (options.outHeight / n_h);
        }
        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        options.inSampleSize = inSampleSize;
        options.inPreferredConfig = rgb_565;// 该模式是默认的,可不设
        bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }


    /**
     * 调整大小图片，放大或缩小图片
     */
    public static Bitmap resizeImage(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap b = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return b;
    }

    public static Bitmap createScaledBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap bm = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return bm;
    }

}
