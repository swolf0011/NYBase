package com.swolf.librarybase.photo.popupWindow;

import android.Manifest;
import android.app.Activity;

import com.swolf.librarybase.util.NYPermissionUtil;

public class NYPickPhotoPermissionUtil {


    private static String[] my_permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_CONTACTS
    };

    /**
     * 检测权限并申请
     *
     * @param activity
     * @return
     */
    public static boolean checkSelfPermission2requestPermissions(Activity activity, int PERMISSION_requestCode) {
        return NYPermissionUtil.getInstance().checkSelfPermission2requestPermissions(activity, my_permissions, PERMISSION_requestCode);
    }


    public static boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, int PERMISSION_requestCode) {
        return NYPermissionUtil.getInstance().onRequestPermissionsResult(requestCode,
                permissions,
                grantResults, PERMISSION_requestCode);
    }
}
