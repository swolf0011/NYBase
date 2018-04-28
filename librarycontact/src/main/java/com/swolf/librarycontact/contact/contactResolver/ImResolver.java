package com.swolf.librarycontact.contact.contactResolver;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContactsEntity;

import com.swolf.librarycontact.contact.bean.ImBean;


/**
 * Im提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class ImResolver {
    private ContentResolver contentResolver;

    public ImResolver(Context context) {
        super();
        this.contentResolver = context.getContentResolver();
    }

    public ArrayList<ImBean> getImByContactId(String contactId) {
        String selection = RawContacts.CONTACT_ID + "=" + contactId;
        Cursor cursor = contentResolver.query(RawContactsEntity.CONTENT_URI, null, selection, null, null);
        ArrayList<ImBean> list = getImByCursor(cursor);
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public ArrayList<ImBean> getImByCursor(Cursor cursor) {
        ArrayList<ImBean> list = new ArrayList<ImBean>();
        if (cursor != null) {
            String itemMimeType;
            if (cursor.moveToFirst()) {
                do {
                    itemMimeType = cursor.getString(cursor.getColumnIndex(RawContactsEntity.MIMETYPE));
                    if (itemMimeType.equals(Im.CONTENT_ITEM_TYPE)) {
                        ImBean im = new ImBean();
                        im.id = cursor.getString(cursor.getColumnIndex(RawContactsEntity.DATA_ID));
                        im.typeId = cursor.getString(cursor.getColumnIndex(Im.PROTOCOL));
                        im.type = cursor.getString(cursor.getColumnIndex(Im.CUSTOM_PROTOCOL));
                        im.im = cursor.getString(cursor.getColumnIndex(Im.DATA));

                        im.idBackup = im.id;
                        im.typeIdBackup = im.typeId;
                        im.typeBackup = im.type;
                        im.imBackup = im.im;

                        list.add(im);
                    }
                } while (cursor.moveToNext());
            }
        }
        return list;
    }

}

// /:~