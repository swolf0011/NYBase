package com.swolf.librarybase.photo.popupWindow;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuYi-15973602714
 */
public class NYPickPhotoUtil {

    /**
     * 权限标示
     */
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;

    static String uriToPath6(Activity activity, Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(activity, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath6(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                path = getImagePath6(activity, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            path = getImagePath6(activity, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            path = uri.getPath();
        }
        return path;
    }



    //获取照片路径
    static String getImagePath6(Activity activity, Uri uri, String selection) {
        String path = null;
        //通过Uri和 selection获取真实图片路径
        Cursor cursor = activity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 通过URI，获取文件路径
     */
    static String getFilePathFromUri(Context context, Uri uri) {
        String path = "";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor != null) {
            cursor.moveToFirst();
            path = cursor.getString(index);
            cursor.close();
            cursor = null;
        }
        return path;
    }


    //////////////
    static boolean checkSelfPermissionGallery6(Activity activity) {
        boolean bool = true;
        //权限检查
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有权限，请求权限
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE);
            bool = false;
        }
        return bool;
    }


    static boolean checkSelfPermissionTakePhoto6(Activity activity) {
        boolean bool = true;
        List<String> list = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.CAMERA);
        }
        if (list.size() > 0) {
            String[] strs = new String[list.size()];
            for (int i = 0; i < strs.length; i++) {
                strs[i] = list.get(i);
            }
            //没有权限，请求权限
            ActivityCompat.requestPermissions(activity, strs, PERMISSION_WRITE_EXTERNAL_STORAGE);
            bool = false;
        }
        return bool;
    }

    static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
