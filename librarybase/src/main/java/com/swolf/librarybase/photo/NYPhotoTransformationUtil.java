package com.swolf.librarybase.photo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.swolf.librarybase.util.NYFileUtil;
import com.swolf.librarybase.util.encryptDecrypt.NYBase64Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuYi-15973602714
 */
@SuppressLint("InflateParams")
public class NYPhotoTransformationUtil {
    /**
     * 创建缩略图 40X40
     */
    public static Bitmap createThumbnail(Bitmap bitmap) {
        return ThumbnailUtils.extractThumbnail(bitmap, 40, 40);
    }

    /**
     * 创建缩略图
     */
    public static Bitmap createThumbnail(Bitmap bitmap, int width, int height) {
        return ThumbnailUtils.extractThumbnail(bitmap, width, height);
    }
    /**
     * drawable转Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Config config = null;
        if (drawable.getOpacity() != PixelFormat.OPAQUE) {
            config = Config.ARGB_8888;
        } else {
            config = Config.RGB_565;
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 文件2Bitmap
     *
     * @param filePath
     * @return
     */
    public Bitmap fileToBitmap(String filePath) {
        if (NYFileUtil.getInstance().isFileExist(filePath)) {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;// begin set is true;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, newOpts);
            newOpts.inJustDecodeBounds = false;// set is true;
            return bitmap;
        }
        return null;
    }



    /**
     * bitmap转 byte[]
     */
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        byte[] bytes = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        bytes = outStream.toByteArray();
        try {
            outStream.close();
            outStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    /**
     * Bitmap转base64,图片的base64编码是不包含图片头的，如（data:image/jpg;base64,）
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        byte[] bs = bitmapToBytes(bitmap);
        return NYBase64Util.encode(bs);
    }
    /**
     * byte[]转bitmap
     */
    public static Bitmap bytesToBitmap(byte[] bs) {
        return BitmapFactory.decodeByteArray(bs, 0, bs.length);
    }

    /**
     * InputStream转bitmap
     */
    public static Bitmap inputStreamToBitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }


    /**
     * 获取ImageView中的Bitmap
     */
    public static Bitmap getBitmapByImageView(ImageView v) {
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return bitmap;
    }
    /**
     * 将View转成Bitmap
     */
    public static Bitmap viewToBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        byte[] bs = NYPhotoCompressUtil.bitmapToBytes(bitmap, Bitmap.CompressFormat.WEBP, 200);
        if (bs != null && bs.length > 0) {
            bitmap = bytesToBitmap(bs);
        }
        return bitmap;
    }
    /**
     * 当前屏幕的截图
     */
    public static Bitmap currentWindowToBitmap(Activity activity) {
        String path = "";
        //1.构建Bitmap
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int w = point.x;
        int h = point.y;
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_4444);
        //2.获取屏幕
        View decorview = activity.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        bitmap = decorview.getDrawingCache();
        return bitmap;
    }

}
