package com.swolf.librarybase.util.phoneUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * apk信息工具
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 * Created by LiuYi-15973602714
 */
public class NYApkInfoUtil {

    private static class NYSubHolder {
        private static NYApkInfoUtil util = new NYApkInfoUtil();
    }

    private NYApkInfoUtil() {
    }

    public static NYApkInfoUtil getInstance() {
        return NYSubHolder.util;
    }

    public class ApkInfo {
        public String application_label;//app标签
        public Drawable application_icon;//app图标
        public String package_name;//包名
        public String version_name;//版本名
        public int version_code;//版本号
    }

    public class SystemInfo {
        public String serial;//序列号
        public String totalMemory;//系统总内存
        public String imei;
        public String imsi;
        public String model;
        public String brand;
        public String number;

        public String type;//cpu型号
        public String frequency;//cpu频率
        public String mac;//手机MAC地址
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    public SystemInfo getSystemInfo(Context context) {
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.serial = getSerialNumber();
        systemInfo.totalMemory = getTotalMemory(context);

        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        systemInfo.imei = mTm.getDeviceId();
        systemInfo.imsi = mTm.getSubscriberId();
        systemInfo.model = Build.MODEL;
        systemInfo.brand = Build.BRAND;
        systemInfo.number = mTm.getLine1Number();
        if (TextUtils.isEmpty(systemInfo.imei)) {
            //android.provider.Settings;
            systemInfo.imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        getCpuInfo(systemInfo);
        systemInfo.mac = getMacAddress(context);
        return systemInfo;
    }

    public ApkInfo getSelfAPKInfo(Context context) {
        ApkInfo apkInfo = new ApkInfo();
        try {
            apkInfo.application_label = "";
            apkInfo.application_icon = null;
            apkInfo.package_name = context.getPackageName();
            apkInfo.version_name = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            apkInfo.version_code = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return apkInfo;
    }

    /**
     * 获取已安装apk信息，versionName版本名，versionCode版本号等。
     */
    public ApkInfo getInstallAPKInfo(Context context, String packageName) {
        ApkInfo apkInfo = new ApkInfo();
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                ApplicationInfo appInfo = packageInfo.applicationInfo;

                apkInfo.application_label = pm.getApplicationLabel(appInfo).toString();
                apkInfo.application_icon = pm.getApplicationIcon(appInfo);
                apkInfo.package_name = packageInfo.packageName;
                apkInfo.version_name = packageInfo.versionName;
                apkInfo.version_code = packageInfo.versionCode;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return apkInfo;
    }

    /**
     * 获取未安装apk信息，versionName版本名，versionCode版本号等。
     *
     * @param context
     * @param archiveFilePath Environment.getExternalStorageDirectory()+"/"+"TestB.apk"
     * @return
     */
    public ApkInfo getUninstallAPKInfo(Context context, String archiveFilePath) {
        ApkInfo apkInfo = new ApkInfo();
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            apkInfo.application_label = pm.getApplicationLabel(appInfo).toString();
            apkInfo.application_icon = pm.getApplicationIcon(appInfo);
            apkInfo.package_name = packageInfo.packageName;
            apkInfo.version_name = packageInfo.versionName;
            apkInfo.version_code = packageInfo.versionCode;
        }
        return apkInfo;
    }

    /**
     * 获取分辨率Width*Height
     */
    public DisplayMetrics getResolution(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        String s = "" + dm.widthPixels + "*" + dm.heightPixels;
        return dm;
    }

    //////////

    /**
     * 获取序列号
     *
     * @return
     */
    private String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;

    }

    /**
     * 获得系统总内存
     */
    private String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 手机CPU信息
     */
    private void getCpuInfo(SystemInfo systemInfo) {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                systemInfo.type = systemInfo.type + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            systemInfo.frequency += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取手机MAC地址
     * 只有手机开启wifi才能获取到mac地址
     */
    private String getMacAddress(Context context) {
        String result = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        return result;
    }
}
