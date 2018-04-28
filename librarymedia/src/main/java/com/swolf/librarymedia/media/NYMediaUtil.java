package com.swolf.librarymedia.media;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;

/**
 * 打开本地多媒体工具
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYMediaUtil {

    /**
     * 打开本地图片
     */
    public void openLoacalImage(Context context, int requestCode, String title) {
        openIntentByType(context, requestCode, title, "image/*");
    }

    /**
     * 打开本地音乐
     */
    public void openLoacalAudio(Context context, int requestCode, String title) {
        openIntentByType(context, requestCode, title, "audio/*");
    }

    /**
     * 根据mimeType 打开mimeType
     */
    private void openIntentByType(Context context, int requestCode, String title, String type) {
        if (!TextUtils.isEmpty(type)) {
            Intent intent = new Intent();
            intent.setType(type);
            intent.setAction("android.intent.action.GET_CONTENT");
            if (!TextUtils.isEmpty(type)) {
                intent = Intent.createChooser(intent, title);
            }
            Activity activity = (Activity) context;
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /*** 系统铃声库 */
    public void openChooseRingtone(Context context, int requestCode) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent, requestCode);
    }

    /*** 选择系统铃声的结果处理 */
    public Uri openChooseRingtoneResultHandle(Context context, int resultCode, Intent data, String contactId) {
        if (resultCode != -1 || data == null)
            return null;
        // 系统铃声
        Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        if (uri == null) {
            // 音频
            uri = data.getData();
        }
        if (uri != null) {
            @SuppressWarnings("unused")
            String ringName = RingtoneManager.getRingtone(context, uri).getTitle(context);
            // 保存铃声
            ContentValues values = new ContentValues();
            values.put(Contacts.CUSTOM_RINGTONE, uri.toString());
            String selection = Contacts._ID + "=?";
            String[] selectionArgs = {contactId};
            context.getContentResolver().update(Contacts.CONTENT_URI, values, selection, selectionArgs);
        }
        return uri;
    }

}

// /:~