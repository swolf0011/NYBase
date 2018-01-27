package com.swolf.librarybase.util.phoneUtil;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by LiuYi-15973602714
 */
@SuppressLint("DefaultLocale")
public class NYApnUtil {

    /**
     * CMWAP到CMNET的转化，不能在UI线程中调用
     */
    public static void cmwap2cmnet(Context c) {
        NYApnUtil apnUtil = new NYApnUtil();
        try {
            String apnStr = apnUtil.getCurrentAPNFromSetting(c);
            if (TextUtils.equals(apnStr, "cmwap")) {
                apnUtil.updateAPN2Net(c);
                Thread.sleep(1000 * 15);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测网络是否通
     */
    public static synchronized boolean checkNetStatus(Context context) {
        boolean netStatus = false;
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            connManager.getActiveNetworkInfo();
            if (connManager.getActiveNetworkInfo() != null) {
                netStatus = connManager.getActiveNetworkInfo().isConnected();
            }
        } catch (Exception e) {
            e.printStackTrace();
            netStatus = false;
        }
        return netStatus;
    }

    public static final Uri CURRENT_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
    public static final Uri APN_LIST_URI = Uri.parse("content://telephony/carriers");

    /**
     * 获取来自设置的当前APN
     */
    public String getCurrentAPNFromSetting(Context c) {
        String apnName1 = "";
        ContentResolver cr = c.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = cr.query(CURRENT_APN_URI, null, null, null, null);
            String curApnId = null;
            if (cursor != null && cursor.moveToFirst()) {
                curApnId = cursor.getString(cursor.getColumnIndex("_id"));
                apnName1 = cursor.getString(cursor.getColumnIndex("apn"));
                cursor.close();
            }
            if (curApnId != null) {
                cursor = cr.query(APN_LIST_URI, null, " _id = ?", new String[]{curApnId}, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String apnName = cursor.getString(cursor.getColumnIndex("apn"));
                    return apnName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return apnName1;
    }

    /**
     * 检查APN
     */
    public void checkAPN(Context c) {
        String newAPN = "cmnet";
        String currentAPN = getCurrentAPNFromSetting(c);
        if (currentAPN == null || !currentAPN.equals(newAPN)) {
            updateAPN2Net(c);
        }
    }

    /**
     * 将网络改成net的
     */
    public int updateAPN2Net(Context c) {
        return updateAPN(c, "cmnet");
    }

    /**
     * 网络改成wap的
     */
    public int updateAPN2Wap(Context c) {
        return updateAPN(c, "cmwap");
    }

    /**
     * 修改网络
     */
    private int updateAPN(Context c, String newAPN) {
        ContentResolver cr = c.getContentResolver();
        Cursor cursor = null;
        try {
            String[] selectionArge = new String[]{newAPN.toLowerCase()};
            cursor = cr.query(APN_LIST_URI, null, " apn = ? and current = 1", selectionArge, null);
            String apnId = null;
            if (cursor != null && cursor.moveToFirst()) {
                apnId = cursor.getString(cursor.getColumnIndex("_id"));
                cursor.close();
            }
            if (apnId != null) {
                ContentValues values = new ContentValues();
                values.put("apn_id", apnId);
                cr.update(CURRENT_APN_URI, values, null, null);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 1;
    }
}

// /:~