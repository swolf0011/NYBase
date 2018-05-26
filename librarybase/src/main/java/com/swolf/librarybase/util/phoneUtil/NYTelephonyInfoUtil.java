package com.swolf.librarybase.util.phoneUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

/**
 * 手机信息工具
 *  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 *  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 * Created by LiuYi-15973602714
 */
public class NYTelephonyInfoUtil {

    public class NYTelephonyInfo{
        public String number;//手机号码
        public String phoneModel;//手机型号
        public String phoneManufacturer;//手机制造商
        public String versionSDK;//SDK版本号
        public String versionRelease;//系统版本号
        public String imei;//获取设备的MIEI
        public String android_id;//设备的android_id
        public String imsi;//手机卡IMSI
        public String providersName;//IMSI的中国提供商名
        public String telephonySystem;//android系统/ophone
    }

    /**
     * 获取手机号码
     */
    public static String getPhoneNumber(Context cxt) {
        TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        tm = null;
        return number;
    }

    /**
     * 手机型号
     */
    public static String phoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 手机制造商
     */
    public static String phoneManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * SDK版本号
     */
    public static int phoneVersionSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 系统版本号
     */
    public static String phoneVersionRelease() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取设备的MIEI
     */
    public static String getIMEI(Context cxt) {
        TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (TextUtils.isEmpty(imei)) {
            //android.provider.Settings;
            imei = Settings.Secure.getString(cxt.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        tm = null;
        return imei;
    }

    /**
     * 获取设备的android_id
     */
    public static String android_id(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    /**
     * 获取手机卡IMSI
     */
    public static String getIMSI(Context cxt) {
        TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        tm = null;
        return imsi;
    }

    /**
     * 获取IMSI的中国提供商名
     */
    public static String getProvidersName(String IMSI) {
        String ProvidersName = null;
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        }
        return ProvidersName;
    }

    /**
     * 判断有无seen字段，有的话是android系统，无的话是ophone
     */
    public static String getTelephonySystem(Context context) {
        String systemName = "";
        Cursor c = context.getContentResolver().query(Uri.parse("content://sms/"), null, null, null, null);
        if (c != null && c.moveToFirst()) {
            if (c.getColumnIndex("seen") < 0) {
                systemName = "android";
            } else {
                systemName = "ophone";
            }
        }
        if (c != null) {
            c.close();
            c = null;
        }
        return systemName;
    }

    /**
     * 进入系统网络设置界面
     */
    public static void goToNetSetting(Context context) {
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        Intent intent = null;
        if (sdkVersion > 10) {
            intent = new Intent(Settings.ACTION_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings");
            intent.setComponent(comp);
            intent.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intent);
    }


    /**
     * 判断是否有网络连接,没有返回false
     */
    public static boolean hasInternetConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo network = manager.getActiveNetworkInfo();
            if (network != null && network.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否有网络连接，若没有，则弹出网络设置对话框，返回false
     */
    public static boolean validateInternet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            openWirelessSet(context);
            return false;
        } else {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        openWirelessSet(context);
        return false;
    }

    /**
     * 判断GPS定位服务是否开启
     */
    public static boolean hasLocationGPS(Context context) {
        LocationManager manager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (manager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断基站定位是否开启
     */
    public static boolean hasLocationNetWork(Context context) {
        LocationManager manager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (manager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查内存卡可读
     */
    public static void checkMemoryCard(final Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            new AlertDialog.Builder(context)
                    .setTitle("检测内存卡")
                    .setMessage("请检查内存卡")
                    .setPositiveButton("设置",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                    Intent intent = new Intent(
                                            Settings.ACTION_SETTINGS);
                                    context.startActivity(intent);
                                }
                            })
                    .setNegativeButton("退出",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();

                                }
                            }).create().show();
        }
    }

    /**
     * 打开网络设置对话框
     */
    public static void openWirelessSet(final Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder
                .setTitle("网络设置")
                .setMessage("检查网络")
                .setPositiveButton("网络设置",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                                Intent intent = new Intent(
                                        Settings.ACTION_WIRELESS_SETTINGS);
                                context.startActivity(intent);
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
        dialogBuilder.show();
    }

    /**
     * 关闭键盘
     */
    public void closeInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
