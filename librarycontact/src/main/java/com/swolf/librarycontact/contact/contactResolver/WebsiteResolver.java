package com.swolf.librarycontact.contact.contactResolver;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContactsEntity;

import com.swolf.librarycontact.contact.bean.WebsiteBean;


/**
 * 网站提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class WebsiteResolver {
    private ContentResolver contentResolver;

    public WebsiteResolver(Context context) {
        super();
        this.contentResolver = context.getContentResolver();
    }

    public ArrayList<WebsiteBean> getWebsiteByContactId(String contactId) {
        String selection = RawContacts.CONTACT_ID + "=" + contactId;
        Cursor cursor = contentResolver.query(RawContactsEntity.CONTENT_URI, null, selection, null, null);
        ArrayList<WebsiteBean> list = getWebsiteByCursor(cursor);
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public ArrayList<WebsiteBean> getWebsiteByCursor(Cursor cursor) {
        ArrayList<WebsiteBean> list = new ArrayList<WebsiteBean>();
        if (cursor != null) {
            String itemMimeType;
            if (cursor.moveToFirst()) {
                do {
                    itemMimeType = cursor.getString(cursor.getColumnIndex(RawContactsEntity.MIMETYPE));
                    if (itemMimeType.equals(Website.CONTENT_ITEM_TYPE)) {
                        WebsiteBean website = new WebsiteBean();
                        website.id = cursor.getString(cursor.getColumnIndex(RawContactsEntity.DATA_ID));
                        website.typeId = cursor.getString(cursor.getColumnIndex(Website.TYPE));
                        website.type = cursor.getString(cursor.getColumnIndex(Website.LABEL));
                        website.website = cursor.getString(cursor.getColumnIndex(Website.URL));

                        website.idBackup = website.id;
                        website.typeIdBackup = website.typeId;
                        website.typeBackup = website.type;
                        website.websiteBackup = website.website;

                        list.add(website);
                    }
                } while (cursor.moveToNext());
            }
        }
        return list;
    }
}

// /:~