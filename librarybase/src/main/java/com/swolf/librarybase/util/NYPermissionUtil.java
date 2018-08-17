package com.swolf.librarybase.util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Android6.0及以上申请权限
 * Created by LiuYi-15973602714
 */
public class NYPermissionUtil {

//    public final int PERMISSION_requestCode = 1000;

    private static class NYSubHolder{
        private static NYPermissionUtil util = new NYPermissionUtil();
    }

    private NYPermissionUtil(){
    }

    public static NYPermissionUtil getInstance(){
        return NYPermissionUtil.NYSubHolder.util;
    }

    /**
     * 判断系统api版本大于等于23才走权限检查
     */
    public boolean isMNC() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 检测权限并申请
     * @param activity
     * @param permissions
     * @return
     */
    public boolean checkSelfPermission2requestPermissions(Activity activity,String[] permissions,int PERMISSION_requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        boolean bool = true;
        List<String> list = new ArrayList<>();
        for (String str:permissions) {
            if (ContextCompat.checkSelfPermission(activity,str) != PackageManager.PERMISSION_GRANTED) {
                list.add(str);
            }
        }
        if (list.size() > 0) {
            String[] strs = new String[list.size()];
            for (int i = 0; i < strs.length; i++) {
                strs[i] = list.get(i);
            }
            //没有权限，请求权限
            ActivityCompat.requestPermissions(activity, strs, PERMISSION_requestCode);
            bool = false;
        }
        return bool;
    }

    /**
     * 检测权限
     * @param activity
     * @param permissions
     * @return
     */
    public String[] checkSelfPermission(Activity activity,String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return null;
        }
        boolean bool = true;
        List<String> list = new ArrayList<>();
        for (String str:permissions) {
            if (ContextCompat.checkSelfPermission(activity,str) != PackageManager.PERMISSION_GRANTED) {
                list.add(str);
            }
        }
        if (list.size() > 0) {
            String[] strs = new String[list.size()];
            for (int i = 0; i < strs.length; i++) {
                strs[i] = list.get(i);
            }
            return strs;
        }
        return null;
    }


    public void onRequestPermissionsResult(Activity activity,int requestCode,
                                              String[] permissions,
                                              int[] grantResults,int PERMISSION_requestCode,IRequestPermissionsResultCallback callback) {
        if (requestCode == PERMISSION_requestCode&&callback!=null) {
            callback.hander();
//            String[] temp_permissions = checkSelfPermission( activity, permissions);
//            if(temp_permissions==null||temp_permissions.length==0){
//
//            }else{
//                checkSelfPermission2requestPermissions(activity,permissions,int PERMISSION_requestCode)
//            }
        }
    }

    public interface IRequestPermissionsResultCallback{
        void hander();
    }
}
