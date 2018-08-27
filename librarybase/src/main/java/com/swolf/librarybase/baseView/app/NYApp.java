package com.swolf.librarybase.baseView.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 自定义app
 * Created by LiuYi-15973602714
 */
public class NYApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initDefaultUnCachExceptionHandler();
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    /**
     * 应用开始就设置全局捕获异常器没有设置就会用系统默认的
     */
    private void initDefaultUnCachExceptionHandler() {
        NYUncaughtExceptionHandler handler = NYUncaughtExceptionHandler.getInstance(this.getApplicationContext());
        handler.setDefaultUnCachExceptionHandler();
    }


    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            NYActivityManager.getInstance().pushActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            NYActivityManager.getInstance().popActivity(activity);
        }
    };
}
