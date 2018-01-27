package com.swolf.librarybase.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Intent软件跳转工具
 * Created by LiuYi-15973602714
 */
public class NYIntentUtil {
    private static class NYSubHolder{
        private static NYIntentUtil util = new NYIntentUtil();
    }

    private NYIntentUtil(){

    }

    public static NYIntentUtil getInstance(){
        return NYIntentUtil.NYSubHolder.util;
    }
    /**
     * 调用浏览器
     *
     * @param context
     * @param urlStr
     */
    public void browser(Context context, String urlStr) {
        Uri uri = Uri.parse(urlStr);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    /**
     * 百度地图上显示该经纬度的当前位置
     *
     * @param ctontext
     * @param latitude  纬度
     * @param longitude 经度
     * @param title
     * @param content
     * @param appName
     */
    public void openBaiduMap(Context ctontext, double latitude, double longitude, String title, String content, String appName) {
        StringBuffer sb = new StringBuffer("intent://map/marker?location=");
        sb.append(latitude);
        sb.append(",");
        sb.append(longitude);
        sb.append("&title=");
        sb.append(title);
        sb.append("&content=");
        sb.append(content);
        sb.append("&src=");
        sb.append(appName);
        sb.append("#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
        try {
            Intent intent = Intent.parseUri(sb.toString(), Intent.URI_INTENT_SCHEME);
            if (new File("/data/data/" + "com.baidu.BaiduMap").exists()) {
                ctontext.startActivity(intent);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 高德地图上显示该经纬度的当前位置
     *
     * @param ctontext
     * @param latitude  纬度
     * @param longitude 经度
     * @param appName
     * @param poiname
     */
    public void openGaoDeMap(Context ctontext, double latitude, double longitude, String appName, String poiname) {
        StringBuffer sb = new StringBuffer("androidamap://viewMap?sourceApplication=");
        sb.append(appName);
        sb.append("&poiname=");
        sb.append(poiname);
        sb.append("&lat=");
        sb.append(latitude);
        sb.append("&lon=");
        sb.append(longitude);
        sb.append("&dev=0");
        try {
            Intent intent = Intent.parseUri(sb.toString(), Intent.URI_INTENT_SCHEME);
            ctontext.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 高德地图上显示该经纬度的当前位置
     *
     * @param ctontext
     * @param latitude  纬度
     * @param longitude 经度
     * @param appName
     */
    public void openGaoDeMap2(Context ctontext, double latitude, double longitude, String appName) {
        Uri uri = Uri.parse("androidamap://navi?sourceApplication=" + appName + "&lat=" + latitude + "&lon=" + longitude + "&dev=0");
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        intent.setPackage("com.autonavi.minimap");
        ctontext.startActivity(intent);
    }

    /**
     * 显示某个坐标在地图上
     *
     * @param context
     * @param latitude  纬度
     * @param longitude 经度
     */
    public void openMap(Context context, float latitude, float longitude) {
        Uri uri = Uri.parse("geo:" + latitude + "," + longitude);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phoneNumber
     */
    public void dial(Context context, String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(it);
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phoneNumber
     */
    public void call(Context context, String phoneNumber) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Uri uri = Uri.parse("tel:" + phoneNumber);
            Intent it = new Intent(Intent.ACTION_CALL, uri);
            context.startActivity(it);
        } else {
            Toast.makeText(context, "Manifest.permission.CALL_PHONE 权限不可用！", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 跳转发送短信或彩信页面
     *
     * @param ctontext
     * @param sms_body 发送的消息
     */
    public void toSendPage(Context ctontext, String sms_body) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("sms_body", sms_body);
        intent.setType("vnd.android-dir/mms-sms");
        ctontext.startActivity(intent);
    }

    /**
     * 发送短信
     *
     * @param context
     * @param phoneNumber
     * @param content
     */
    public void sms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", content);
        context.startActivity(it);
    }

    /**
     * 发送彩信
     *
     * @param context
     * @param phoneNumber
     * @param content
     * @param contentStr  "content://media/external/images/media/23"
     * @param type        "image/png"
     */
    public void mms(Context context, String phoneNumber, String content, String contentStr, String type) {
        Uri uri = Uri.parse(contentStr);
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra("sms_body", content);
        it.putExtra(Intent.EXTRA_STREAM, uri);
        it.setType(type);
        context.startActivity(it);
    }


    /**
     * 播放媒体文件
     *
     * @param ctontext
     * @param mp3Path  mp3路径:"/sdcard/cwj.mp3"
     */
    public void playMp3(Context ctontext, String mp3Path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + mp3Path);
        intent.setDataAndType(uri, "audio/mp3");
        ctontext.startActivity(intent);
    }


    /**
     * 播放媒体文件
     *
     * @param context
     * @param pathSegment "1"
     */
    public void play(Context context, String pathSegment) {
        Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, pathSegment);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

}
