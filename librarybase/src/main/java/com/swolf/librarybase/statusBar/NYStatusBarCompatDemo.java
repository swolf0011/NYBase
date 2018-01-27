package com.swolf.librarybase.statusBar;

import android.app.Activity;


/**
 * android状态栏一体化、沉浸式状态栏(兼容低版本)Demo
 * Created by LiuYi-15973602714
 */
public class NYStatusBarCompatDemo {

    private void test(Activity activity) {
        boolean bool = false;
        if (bool) {
            bool = false;
            NYStatusBarCompat.translucentStatusBar(activity);
        } else {
            bool = true;
            NYStatusBarCompat.setStatusBarColor(activity, "#FF0000");
        }
    }
}
