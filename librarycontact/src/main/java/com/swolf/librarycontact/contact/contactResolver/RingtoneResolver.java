package com.swolf.librarycontact.contact.contactResolver;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;

import com.swolf.librarycontact.contact.bean.RingBean;


/**
 * 铃声提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class RingtoneResolver {
    private Context context;
    private ContentResolver contentResolver;

    public RingtoneResolver(Context context) {
        super();
        this.context = context;
        this.contentResolver = context.getContentResolver();
    }

    public RingBean getRingtoneByContactId(String contactId) {
        RingBean ring = null;
        String[] projection = {Contacts.CUSTOM_RINGTONE};
        String selection = Contacts._ID + " = ? ";
        String[] selectionArgs = {contactId};
        Cursor cursor = contentResolver.query(Contacts.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            String uriStr = cursor.getString(0);
            if (!TextUtils.isEmpty(uriStr)) {
                ring = new RingBean();
                ring.uriStr = uriStr;
                ring.ringName = getMediaName(uriStr);
                ring.uriStrBackup = uriStr;
                ring.ringNameBackup = ring.ringName;
            }
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return ring;
    }

    private String getMediaName(String Url) {
        Uri uri = Uri.parse(Url);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        if (null != ringtone) {
            return ringtone.getTitle(context);
        } else {
            return "";
        }
    }


}

// /:~