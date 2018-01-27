package com.swolf.librarybase.util.phoneUtil;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * sms工具
 * Created by LiuYi-15973602714
 */
public class NYSmsUtil {
    public static final String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    public static final String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";

//      <!-- 允许程序发送SMS短信 -->
//    <uses-permission android:name="android.permission.SEND_SMS" />

    /**
     * 发送短信
     */
    private static void sendSMS(Context context, String phoneNumber, String message, Intent sentIntent,
                                Intent deliverIntent) {
        SmsManager sms = SmsManager.getDefault();

        if (sentIntent != null) {
            sentIntent.setAction(SENT_SMS_ACTION);
        }
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (deliverIntent != null) {
            deliverIntent.setAction(DELIVERED_SMS_ACTION);
        }
        PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, deliverIntent, 0);
        ArrayList<String> msgs = sms.divideMessage(message);// 拆分短信
        for (String msg : msgs) {
            sms.sendTextMessage(phoneNumber, null, msg, sentPI, deliverPI);
        }
    }

    /**
     * 发送短信
     */
    public static void sendSMS(Context context, String phoneNumber, String message) {
        sendSMS(context, phoneNumber, message, null, null);
    }

    /**
     * 群发送短信
     */
    public static void sends(Context context, String[] phoneNumbers, String message, Intent sentIntent,
                             Intent deliverIntent) {
        for (String phoneNumber : phoneNumbers) {
            sendSMS(context, phoneNumber, message, sentIntent, deliverIntent);
        }
    }

    /**
     * 群发送短信
     */
    public static void sends(Context context, String[] phoneNumbers, String message) {
        for (String phoneNumber : phoneNumbers) {
            sendSMS(context, phoneNumber, message, null, null);
        }
    }

    /**
     * 得到最后一条短信的会话id
     */
    public static int getLastSmsThreadID(Context context) {
        int threadId = 0;
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/"),
                new String[]{"* ,max(date) from sms--"}, null, null, "");
        if (null != cursor && cursor.moveToFirst()) {
            threadId = (int) cursor.getLong(cursor.getColumnIndex("thread_id"));
        }
        if (cursor != null) {
            cursor.close();
        }
        return threadId;
    }

    /**
     * 查询全部短信
     */
    public static Cursor queryAllSMS(Context context) {
        Uri uri = Uri.parse("content://sms/");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, "date desc");
        return cursor;
    }

    /**
     * 查询短信
     */
    public static Cursor querySMS(Context context, String selection, String[] selectionArgs, String order) {
        Uri uri = Uri.parse("content://sms/");
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, order);
        return cursor;
    }

    /**
     * 插入发送中的短信,返回ID
     */
    public static long insertSMS(Context context, long threadId, String body, String address) {
        ContentValues cv = new ContentValues();
        cv.put("thread_id", threadId);
        cv.put("date", System.currentTimeMillis());
        cv.put("body", body);
        cv.put("type", 2);
        cv.put("address", address);
        cv.put("seen", 1);
        Uri uri = Uri.parse("content://sms/");
        Uri result = context.getContentResolver().insert(uri, cv);
        long smsId = Long.valueOf(result.getLastPathSegment());
        return smsId;
    }

    /**
     * 更新短信状态
     */
    public static int updateSMSType(Context context, long smsId, int type) {
        Uri uri = Uri.parse("content://sms/");
        ContentValues cv = new ContentValues();
        cv.put("type", type);
        String selectSql = "_id = " + smsId;
        return context.getContentResolver().update(uri, cv, selectSql, null);
    }

    /**
     * 直接删除短信
     */
    public static void deleteSMS(Context context, long smsId) {
        Uri uri = Uri.parse("content://sms/");
        String selectSql = "_id = " + smsId;
        context.getContentResolver().delete(uri, selectSql, null);
    }

}

// /:~