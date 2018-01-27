package com.swolf.librarybase.photo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.swolf.librarybase.util.NYFileUtil;
import com.swolf.librarybase.util.encryptDecrypt.NYBase64Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片工具
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressLint("InflateParams")
public class NYPhotoUtil {



    /**
     * 获取SDCard所有图片
     */
    public static List<NYPhoto> getSDCardPhotoList(Context context) {
        List<NYPhoto> photoList = new ArrayList<NYPhoto>();
        ContentResolver cr = context.getContentResolver();
        if (cr != null) {
            String[] projection = {MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.DATA,
                    MediaStore.Images.Thumbnails.HEIGHT, MediaStore.Images.Thumbnails.WIDTH};
            Cursor cursor = cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null,
                    MediaStore.Images.Thumbnails.DEFAULT_SORT_ORDER);

            if (null != cursor && cursor.moveToFirst()) {
                do {
                    NYPhoto p = new NYPhoto();
                    p.id = cursor.getInt(0);
                    p.path = cursor.getString(1);
                    p.height = cursor.getInt(2);
                    p.width = cursor.getInt(3);
                    p.bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, p.id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
                    photoList.add(p);
                } while (cursor.moveToNext());
            }
        }
        return photoList;
    }









    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 图片实体
     * Created by LiuYi-15973602714 on 2017-01-01
     */
    public static class NYPhoto {
        public int id;
        public String url;
        public String path;
        public int height;
        public int width;
        public Bitmap bitmap;
    }
}
