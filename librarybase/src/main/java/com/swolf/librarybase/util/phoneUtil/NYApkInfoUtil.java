package com.swolf.librarybase.util.phoneUtil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * apk信息工具
 * Created by LiuYi-15973602714
 */
public class NYApkInfoUtil {
    /**
     * 获取版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取软件版本名
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * app标签
     */
    public static String APPLICATION_LABEL = "application_label";
    /**
     * app图标
     */
    public static String APPLICATION_ICON = "application_icon";
    /**
     * 包名
     */
    public static String PACKAGE_NAME = "package_name";
    /**
     * 版本名
     */
    public static String VERSION_NAME = "version_name";
    /**
     * 版本号
     */
    public static String VERSION_CODE = "version_code";

    /**
     * 获取已安装apk信息，versionName版本名，versionCode版本号等。
     */
    public static Map<String, Object> getInstallAPKInfo(Context context, String packageName) {
        Map<String, Object> map = new HashMap<String, Object>();
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                ApplicationInfo appInfo = packageInfo.applicationInfo;
                map.put(APPLICATION_LABEL, pm.getApplicationLabel(appInfo).toString());// app标签
                map.put(APPLICATION_ICON, pm.getApplicationIcon(appInfo));// app图标
                map.put(PACKAGE_NAME, packageInfo.packageName);// 包名
                map.put(VERSION_NAME, packageInfo.versionName);// 版本名
                map.put(VERSION_CODE, packageInfo.versionCode);// 版本号
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取未安装apk信息，versionName版本名，versionCode版本号等。
     *
     * @param context
     * @param archiveFilePath Environment.getExternalStorageDirectory()+"/"+"TestB.apk"
     * @return
     */
    public static Map<String, Object> getUninstallAPKInfo(Context context, String archiveFilePath) {
        Map<String, Object> map = new HashMap<String, Object>();
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            map.put(APPLICATION_LABEL, pm.getApplicationLabel(appInfo).toString());// app标签
            map.put(APPLICATION_ICON, pm.getApplicationIcon(appInfo));// app图标
            map.put(PACKAGE_NAME, packageInfo.packageName);// 包名
            map.put(VERSION_NAME, packageInfo.versionName);// 版本名
            map.put(VERSION_CODE, packageInfo.versionCode);// 版本号
        }
        return map;
    }
}
