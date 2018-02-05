package com.swolf.libraryimgglide;

import android.content.Context;


/**
 * Created by LiuYi-15973602714
 */
public class NYGlideClear {

    /**
     * 清除内存缓存:必须在UI线程中调用
     * @param context
     */
    public static void clearMemory(Context context) {
        GlideApp.get(context).clearMemory();
    }
    /**
     * 清除磁盘缓存:必须在后台线程中调用，建议同时clearMemory()
     * @param context
     */
    public static void clearDiskCache(Context context) {
        GlideApp.get(context).clearDiskCache();
    }

}
