package com.swolf.librarybase.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.io.File;

public class NYInstallUtil {

    //<uses-permissionandroid:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
    private static class NYSubHolder {
        private static NYInstallUtil util = new NYInstallUtil();
    }

    private NYInstallUtil() {

    }

    public static NYInstallUtil getInstance() {
        return NYInstallUtil.NYSubHolder.util;
    }

    /**
     * 安装应用的流程  大于8.0需要用户手动打开未知来源安装权限
     * 需要在清单文件中加入权限  REQUEST_INSTALL_PACKAGES
     */
    public void installProcess(final Activity activity, File apkFile, final int selfRequestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean haveInstallPermission = activity.getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {
                //没有未知来源安装权限权限
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("提示");
                builder.setMessage("安装应用需要打开未知来源权限，请去设置中开启应用权限，以允许安装来自此来源的应用");
                builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startInstallPermissionSettingActivity(activity, selfRequestCode);
                        }
                    }
                });
                builder.show();
                return;//防止系统执行默认的方法会跳转页面后弹窗提示，所以会重复 return 掉
            }
        }
        installApk(activity, apkFile);
    }

    /**
     * 安装应用
     *
     * @param context
     * @param apkFile
     */
    private void installApk(Context context, File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        } else {//Android7.0之后获取uri要用contentProvider
            Uri uri = Uri.fromFile(apkFile);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * @param activity
     * @param selfRequestCode
     */
    private void startInstallPermissionSettingActivity(Activity activity, int selfRequestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri packageURI = Uri.parse("package:" + activity.getPackageName());
            //注意这个是8.0新API
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
            activity.startActivityForResult(intent, selfRequestCode);
        }
    }

    /**
     * 申请未知应用安装权限的回调结果
     *
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     * @param selfRequestCode
     * @param apkFile
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, int selfRequestCode, File apkFile) {
        if (requestCode == selfRequestCode) {
            if (apkFile != null && apkFile.exists()) {
                installProcess(activity, apkFile, selfRequestCode);
            }
        }
    }
}

