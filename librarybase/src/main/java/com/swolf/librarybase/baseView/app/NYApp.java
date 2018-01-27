package com.swolf.librarybase.baseView.app;

import android.app.Application;

/**
 * 自定义app
 * Created by LiuYi-15973602714
 */
public class NYApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        initDefaultUnCachExceptionHandler();
    }

    /**
     *  应用开始就设置全局捕获异常器没有设置就会用系统默认的
     */
    private void initDefaultUnCachExceptionHandler(){
        NYUncaughtExceptionHandler handler = NYUncaughtExceptionHandler.getInstance(this.getApplicationContext());
        handler.setDefaultUnCachExceptionHandler();
    }
}
