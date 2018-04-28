package com.swolf.librarycontact.contact.contactResolver;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContactsEntity;

import com.swolf.librarycontact.contact.bean.NameBean;


/**
 * 名字提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class StructuredNameResolver {
    private ContentResolver contentResolver;

    public StructuredNameResolver(Context context) {
        super();
        this.contentResolver = context.getContentResolver();
    }

    public NameBean getStructuredNameByContactId(String contactId) {
        String selection = RawContacts.CONTACT_ID + "=" + contactId;
        Cursor cursor = contentResolver.query(RawContactsEntity.CONTENT_URI, null, selection, null, null);
        NameBean name = getStructuredNameByCursor(cursor);
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return name;
    }

    public NameBean getStructuredNameByCursor(Cursor cursor) {
        NameBean name = null;
        if (cursor != null) {
            String itemMimeType;
            if (cursor.moveToFirst()) {
                do {
                    itemMimeType = cursor.getString(cursor.getColumnIndex(RawContactsEntity.MIMETYPE));
                    if (itemMimeType.equals(StructuredName.CONTENT_ITEM_TYPE)) {
                        name = new NameBean();
                        name.id = cursor.getString(cursor.getColumnIndex(RawContactsEntity.DATA_ID));
                        name.firstName = cursor.getString(cursor.getColumnIndex(StructuredName.GIVEN_NAME));
                        name.lastName = cursor.getString(cursor.getColumnIndex(StructuredName.FAMILY_NAME));
                        name.middleName = cursor.getString(cursor.getColumnIndex(StructuredName.MIDDLE_NAME));
                        name.displayName = cursor.getString(cursor.getColumnIndex(StructuredName.DISPLAY_NAME));
                        name.idBackup = name.id;
                        name.firstNameBackup = name.firstName;
                        name.lastNameBackup = name.lastName;
                        name.middleNameBackup = name.middleName;
                        name.displayNameBackup = name.displayName;
                        break;
                    }
                } while (cursor.moveToNext());
            }
        }
        return name;
    }
}

// /:~