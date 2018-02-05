package com.swolf.libraryimgglide;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.io.File;


/**
 * Created by LiuYi-15973602714
 */
public class NYGlide {
    /**
     * 最简单加载网络图片的用法
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadDemo1(Context context, String imgUrl, ImageView imageView) {
        RequestBuilder rb =  Glide.with(context).load(imgUrl);
                rb.into(imageView);
    }
    /**
     * 最简单加载网络图片的用法
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadDemo(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context).load(imgUrl)
                .thumbnail(0.1f)
                .placeholder(NYGlideBuilder.placeholderImgRes)//设置占位图
                .error(NYGlideBuilder.errorImgRes)//设置错误图片
                .fitCenter()//适配居中
                .dontAnimate()//直接显示图片而没有任何淡入淡出效果
                .dontTransform()//淡入淡出效果
                .into(imageView);
    }

    /**
     * 最简单加载网络图片的用法
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void load(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context).load(imgUrl)
                .thumbnail(0.1f)
                .placeholder(NYGlideBuilder.placeholderImgRes)//设置占位图
                .error(NYGlideBuilder.errorImgRes)//设置错误图片
                .fitCenter()//适配居中
                .dontTransform()//淡入淡出效果
                .into(imageView);
    }
    /**
     * 资源文件中加载
     *
     * @param context
     * @param resourceId
     * @param imageView
     */
    public static void load(Context context, int resourceId, ImageView imageView) {
        GlideApp.with(context).load(resourceId)
                .thumbnail(0.1f)
                .placeholder(NYGlideBuilder.placeholderImgRes)//设置占位图
                .error(NYGlideBuilder.errorImgRes)//设置错误图片
                .fitCenter()//适配居中
                .dontTransform()//淡入淡出效果
                .into(imageView);
    }

    /**
     * 文件中加载
     *
     * @param context
     * @param file
     * @param imageView
     */
    public static void load(Context context, File file, ImageView imageView) {
        GlideApp.with(context).load(file)
                .thumbnail(0.1f)
                .placeholder(NYGlideBuilder.placeholderImgRes)//设置占位图
                .error(NYGlideBuilder.errorImgRes)//设置错误图片
                .fitCenter()//适配居中
                .dontTransform()//淡入淡出效果
                .into(imageView);
    }

    /**
     * URI中加载图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static void load(Context context, Uri uri, ImageView imageView) {
        GlideApp.with(context).load(uri)
                .thumbnail(0.1f)
                .placeholder(NYGlideBuilder.placeholderImgRes)//设置占位图
                .error(NYGlideBuilder.errorImgRes)//设置错误图片
                .fitCenter()//适配居中
                .dontTransform()//淡入淡出效果
                .into(imageView);
    }

}
