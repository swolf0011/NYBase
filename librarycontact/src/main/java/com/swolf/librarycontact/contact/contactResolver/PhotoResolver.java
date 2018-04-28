package com.swolf.librarycontact.contact.contactResolver;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.text.TextUtils;

import com.swolf.librarycontact.contact.bean.PhotoBean;


/**
 * 图像提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class PhotoResolver {
    private ContentResolver contentResolver;

    public PhotoResolver(Context context) {
        super();
        this.contentResolver = context.getContentResolver();
    }

    public PhotoBean getPhotoByContactId(String contactId) {
        PhotoBean photo = null;
        String[] projection = {Contacts.PHOTO_ID};
        String selection = Contacts._ID + " = ? ";
        String[] selectionArgs = {contactId};
        Cursor photoCursor = null;
        Cursor cursor = contentResolver.query(Contacts.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            String id = cursor.getString(0);
            if (!TextUtils.isEmpty(id)) {
                String[] photoProjection = new String[]{Photo.PHOTO};
                String photoSelection = Data._ID + " = ? ";
                String[] photoSelectionArgs = new String[]{id};
                photoCursor = contentResolver
                        .query(Data.CONTENT_URI, photoProjection, photoSelection, photoSelectionArgs, null);
                if (photoCursor.moveToFirst()) {
                    byte[] photoByte = photoCursor.getBlob(0);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
                    photo = new PhotoBean();
                    photo.id = id;
                    photo.photo = bitmap;
                    photo.idBackup = id;
                    photo.photoBackup = bitmap;
                } else {
                    // bitmap = BitmapFactory.decodeResource(context.getResources(),
                    // R.drawable.contract_edit_avatar_normal);
                }
            } else {
                // bitmap = BitmapFactory.decodeResource(context.getResources(),
                // R.drawable.contract_edit_avatar_normal);
            }
        }
        if (photoCursor != null) {
            photoCursor.close();
            photoCursor = null;
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return photo;
    }

}

// /:~