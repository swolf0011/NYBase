package com.swolf.librarybase.baseView.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;


/**
 * Fragment工具类
 * Created by LiuYi-15973602714
 */
@SuppressLint("NewApi")
public class NYFragmentUtil {
    /**
     * 替换Fragment
     */
    public static void replaceFragment(Activity activity,
                                       Fragment fragment,
                                       int layout) {
        FragmentManager fm = activity.getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.replace(layout, fragment);
        }
        transaction.commit();
    }

    /**
     * 替换Fragment
     */
    public static void replaceFragment(FragmentActivity activity,
                                       android.support.v4.app.Fragment fragment,
                                       int layout) {
        android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
        // 开启Fragment事务
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.replace(layout, fragment);
        }
        transaction.commit();
    }

}
