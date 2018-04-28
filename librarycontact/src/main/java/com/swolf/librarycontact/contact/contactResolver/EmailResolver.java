package com.swolf.librarycontact.contact.contactResolver;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContactsEntity;

import com.swolf.librarycontact.contact.bean.EmailBean;


/**
 * 邮箱提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class EmailResolver {
    private ContentResolver contentResolver;

    public EmailResolver(Context context) {
        super();
        this.contentResolver = context.getContentResolver();
    }

    public ArrayList<EmailBean> getEmailByContactId(String contactId) {
        String selection = RawContacts.CONTACT_ID + "=" + contactId;
        Cursor cursor = contentResolver.query(RawContactsEntity.CONTENT_URI, null, selection, null, null);
        ArrayList<EmailBean> list = getEmailByCursor(cursor);
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public ArrayList<EmailBean> getEmailByCursor(Cursor cursor) {
        ArrayList<EmailBean> list = new ArrayList<EmailBean>(0);// 邮件
        if (cursor != null) {
            String itemMimeType;
            if (cursor.moveToFirst()) {
                do {
                    itemMimeType = cursor.getString(cursor.getColumnIndex(RawContactsEntity.MIMETYPE));
                    if (itemMimeType.equals(Email.CONTENT_ITEM_TYPE)) {
                        EmailBean email = new EmailBean();
                        email.id = cursor.getString(cursor.getColumnIndex(RawContactsEntity.DATA_ID));
                        email.typeId = cursor.getString(cursor.getColumnIndex(Email.TYPE));
                        email.type = cursor.getString(cursor.getColumnIndex(Email.LABEL));
                        email.email = cursor.getString(cursor.getColumnIndex(Email.DATA));

                        email.idBackup = email.id;
                        email.typeIdBackup = email.typeId;
                        email.typeBackup = email.type;
                        email.emailBackup = email.email;
                        list.add(email);
                    }
                } while (cursor.moveToNext());
            }
        }
        return list;
    }

}

// /:~