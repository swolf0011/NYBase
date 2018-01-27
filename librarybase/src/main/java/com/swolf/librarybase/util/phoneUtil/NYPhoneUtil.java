package com.swolf.librarybase.util.phoneUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * phone工具
 * Created by LiuYi-15973602714
 */
public class NYPhoneUtil {
    /**
     * 安装APK文件
     */
    public static void installApk(Context context, String filePath1) {
        File apkfile = new File(filePath1);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 拨打电话
     */
    public static void call(Context context, String number) {
        String uri = "tel:" + number;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取本机IP地址
     */
    public String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
