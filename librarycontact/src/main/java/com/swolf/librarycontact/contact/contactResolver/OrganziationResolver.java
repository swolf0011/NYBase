package com.swolf.librarycontact.contact.contactResolver;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContactsEntity;

import com.swolf.librarycontact.contact.bean.OrganizationBean;


/**
 * 组织提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class OrganziationResolver {
    private ContentResolver contentResolver;

    public OrganziationResolver(Context context) {
        super();
        this.contentResolver = context.getContentResolver();
    }

    public ArrayList<OrganizationBean> getOrganziationByContactId(String contactId) {
        String selection = RawContacts.CONTACT_ID + "=" + contactId;
        Cursor cursor = contentResolver.query(RawContactsEntity.CONTENT_URI, null, selection, null, null);
        ArrayList<OrganizationBean> list = getOrganziationByCursor(cursor);
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public ArrayList<OrganizationBean> getOrganziationByCursor(Cursor cursor) {
        ArrayList<OrganizationBean> list = new ArrayList<OrganizationBean>();
        if (cursor != null) {
            String itemMimeType;
            if (cursor.moveToFirst()) {
                do {
                    itemMimeType = cursor.getString(cursor.getColumnIndex(RawContactsEntity.MIMETYPE));
                    if (itemMimeType.equals(Organization.CONTENT_ITEM_TYPE)) {
                        OrganizationBean organziation = new OrganizationBean();
                        organziation.id = cursor.getString(cursor.getColumnIndex(RawContactsEntity.DATA_ID));
                        organziation.typeId = cursor.getString(cursor.getColumnIndex(Organization.TYPE));
                        organziation.type = cursor.getString(cursor.getColumnIndex(Organization.LABEL));
                        organziation.company = cursor.getString(cursor.getColumnIndex(Organization.COMPANY));
                        organziation.title = cursor.getString(cursor.getColumnIndex(Organization.TITLE));

                        organziation.idBackup = organziation.id;
                        organziation.typeIdBackup = organziation.typeId;
                        organziation.typeBackup = organziation.type;
                        organziation.companyBackup = organziation.company;
                        organziation.titleBackup = organziation.title;

                        list.add(organziation);
                    }
                } while (cursor.moveToNext());
            }
        }
        return list;
    }
}

// /:~