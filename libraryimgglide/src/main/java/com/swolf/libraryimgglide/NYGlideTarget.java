package com.swolf.libraryimgglide;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;


/**
 * Created by LiuYi-15973602714
 */
public class NYGlideTarget {

    /**
     * 最简单加载网络图片的用法
     *
     * @param context
     * @param imgUrl
     * @param target
     */
    public static void load(Context context, String imgUrl, SimpleTarget<Bitmap> target) {
        GlideApp.with(context).asBitmap().load(imgUrl).into(target);
    }
    /**
     * 资源文件中加载
     *
     * @param context
     * @param resourceId
     * @param target
     */
    public static void load(Context context, int resourceId, SimpleTarget<Bitmap> target) {
        GlideApp.with(context).asBitmap().load(resourceId).into(target);
    }

    /**
     * 文件中加载
     *
     * @param context
     * @param file
     * @param target
     */
    public static void load(Context context, File file, SimpleTarget<Bitmap> target) {
        GlideApp.with(context).asBitmap().load(file).into(target);
    }

    /**
     * URI中加载图片
     *
     * @param context
     * @param uri
     * @param target
     */
    public static void load(Context context, Uri uri, SimpleTarget<Bitmap> target) {
        GlideApp.with(context).asBitmap().load(uri).into(target);
    }

}
