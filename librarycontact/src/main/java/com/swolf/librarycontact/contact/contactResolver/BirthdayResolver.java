package com.swolf.librarycontact.contact.contactResolver;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContactsEntity;
import android.text.TextUtils;

import com.swolf.librarycontact.contact.bean.BirthdayBean;


/**
 * 生日提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class BirthdayResolver {
    private ContentResolver contentResolver;

    public BirthdayResolver(Context context) {
        super();
        this.contentResolver = context.getContentResolver();
    }

    public BirthdayBean getBirthdayByContactId(String contactId) {
        String selection = RawContacts.CONTACT_ID + "=" + contactId;
        Cursor cursor = contentResolver.query(RawContactsEntity.CONTENT_URI, null, selection, null, null);
        BirthdayBean birthday = getBirthdayByCursor(cursor);
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return birthday;
    }

    public BirthdayBean getBirthdayByCursor(Cursor cursor) {
        BirthdayBean birthday = null;
        if (cursor != null) {
            String itemMimeType;
            if (cursor.moveToFirst()) {
                do {
                    itemMimeType = cursor.getString(cursor.getColumnIndex(RawContactsEntity.MIMETYPE));
                    if (itemMimeType.equals(Event.CONTENT_ITEM_TYPE)) {
                        String id = cursor.getString(cursor.getColumnIndex(RawContactsEntity.DATA_ID));
                        int typeId = cursor.getInt(cursor.getColumnIndex(Event.TYPE));
                        String birthdayStr = cursor.getString(cursor.getColumnIndex(Event.DATA));
                        if (!TextUtils.isEmpty(birthdayStr) && typeId == Event.TYPE_BIRTHDAY) { // 生日
                            birthday = new BirthdayBean();
                            birthday.id = id;
                            birthday.typeId = typeId + "";
                            birthday.birthday = birthdayStr;
                            birthday.idBackup = id;
                            birthday.typeIdBackup = typeId + "";
                            birthday.birthdayBackup = birthdayStr;
                            break;
                        }
                    }
                } while (cursor.moveToNext());
            }
        }
        return birthday;
    }

}

// /:~