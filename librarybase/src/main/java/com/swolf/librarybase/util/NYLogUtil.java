package com.swolf.librarybase.util;

import android.util.Log;

import com.swolf.librarybase.BuildConfig;

public class NYLogUtil {

    public static void i(String msg){
        if(BuildConfig.DEBUG){
            Log.i(NYLogUtil.class.getSimpleName(),msg);
        }
    }
    public static void d(String msg){
        if(BuildConfig.DEBUG){
            Log.d(NYLogUtil.class.getSimpleName(),msg);
        }
    }
    public static void e(String msg){
        if(BuildConfig.DEBUG){
            Log.e(NYLogUtil.class.getSimpleName(),msg);
        }
    }
    public static void v(String msg){
        if(BuildConfig.DEBUG){
            Log.v(NYLogUtil.class.getSimpleName(),msg);
        }
    }
}
