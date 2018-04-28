package com.swolf.librarycontact.contact.contactResolver;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContactsEntity;

import com.swolf.librarycontact.contact.bean.PhoneBean;


/**
 * 电话提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class PhoneResolver {
    private ContentResolver contentResolver;

    public PhoneResolver(Context context) {
        super();
        this.contentResolver = context.getContentResolver();
    }

    public ArrayList<PhoneBean> getPhoneByContactId(String contactId) {
        String selection = RawContacts.CONTACT_ID + "=" + contactId;
        Cursor cursor = contentResolver.query(RawContactsEntity.CONTENT_URI, null, selection, null, null);
        ArrayList<PhoneBean> list = getPhoneByCursor(cursor);
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public ArrayList<PhoneBean> getPhoneByCursor(Cursor cursor) {
        ArrayList<PhoneBean> list = new ArrayList<PhoneBean>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String itemMimeType = cursor.getString(cursor.getColumnIndex(RawContactsEntity.MIMETYPE));
                    if (itemMimeType.equals(Phone.CONTENT_ITEM_TYPE)) {
                        PhoneBean phone = new PhoneBean();
                        phone.id = cursor.getString(cursor.getColumnIndex(RawContactsEntity.DATA_ID));
                        phone.typeId = cursor.getString(cursor.getColumnIndex(Phone.TYPE));
                        phone.type = cursor.getString(cursor.getColumnIndex(Phone.LABEL));
                        phone.number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                        phone.idBackup = phone.id;
                        phone.typeIdBackup = phone.typeId;
                        phone.typeBackup = phone.type;
                        phone.numberBackup = phone.number;
                        list.add(phone);
                    }
                } while (cursor.moveToNext());
            }
        }
        return list;
    }
}

// /:~