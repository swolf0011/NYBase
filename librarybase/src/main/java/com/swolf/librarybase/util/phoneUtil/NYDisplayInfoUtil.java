package com.swolf.librarybase.util.phoneUtil;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 * 屏幕信息工具
 * Created by LiuYi-15973602714
 */
public class NYDisplayInfoUtil {

    /**
     * 获得手机分辨率
     */
    public static String getPhoneResolution(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 获得手机的宽带和高度像素单位为px
        return dm.widthPixels + "X" + dm.heightPixels;
    }

    /**
     * 获取屏幕度量
     */
    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获取屏幕密度
     */
    public static float getDisplayDensity(Activity activity) {
        DisplayMetrics dm = getDisplayMetrics(activity);
        return dm.density;
    }

    /**
     * 获取屏幕宽DP
     */
    public static float getDisplayWidth(Activity activity) {
        DisplayMetrics dm = getDisplayMetrics(activity);
        float density = dm.density;
        return dm.widthPixels * density;
    }

    /**
     * 获取屏幕高DP
     */
    public static float getDisplayHeigth(Activity activity) {
        DisplayMetrics dm = getDisplayMetrics(activity);
        float density = dm.density;
        return dm.heightPixels * density;
    }

    /**
     * 获取屏幕宽像素PX
     */
    public static float getDisplayPXWidth(Activity activity) {
        DisplayMetrics dm = getDisplayMetrics(activity);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高像素PX
     */
    public static float getDisplayPXHeigth(Activity activity) {
        DisplayMetrics dm = getDisplayMetrics(activity);
        return dm.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp的单位转成为px
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px的单位转成为dp
     */
    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽
     */
    public static int getScreenWidth(Activity activity) {
        return getDisplayMetrics(activity).widthPixels;
    }

    /**
     * 获取屏幕高
     */
    public static int getScreenHeight(Activity activity) {
        return getDisplayMetrics(activity).heightPixels;
    }

    private static final String STATUSBAR_CLASS_NAME = "com.android.internal.R$dimen";
    private static final String STATUSBAR_FIELD_HEIGHT = "status_bar_height";

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int sbar = 0;
        try {
            c = Class.forName(STATUSBAR_CLASS_NAME);
            obj = c.newInstance();
            field = c.getField(STATUSBAR_FIELD_HEIGHT);
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

}
