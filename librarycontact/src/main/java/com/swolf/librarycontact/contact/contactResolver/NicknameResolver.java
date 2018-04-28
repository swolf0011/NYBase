package com.swolf.librarycontact.contact.contactResolver;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContactsEntity;
import android.text.TextUtils;

import com.swolf.librarycontact.contact.bean.NicknameBean;


/**
 * 昵称提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NicknameResolver {
	private ContentResolver contentResolver;

	public NicknameResolver(Context context) {
		super();
		this.contentResolver = context.getContentResolver();
	}

	public NicknameBean getNicknameByContactId(String contactId) {
		String selection = RawContacts.CONTACT_ID + "=" + contactId;
		Cursor cursor = contentResolver.query(RawContactsEntity.CONTENT_URI, null, selection, null, null);
		NicknameBean nickname = getNicknameByCursor(cursor);
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return nickname;
	}

	public NicknameBean getNicknameByCursor(Cursor cursor) {
		NicknameBean nickname = null;
		if (cursor != null) {
			String itemMimeType;
			if (cursor.moveToFirst()) {
				do {
					itemMimeType = cursor.getString(cursor.getColumnIndex(RawContactsEntity.MIMETYPE));
					if (itemMimeType.equals(Nickname.CONTENT_ITEM_TYPE)) {
						String id = cursor.getString(cursor.getColumnIndex(RawContactsEntity.DATA_ID));
						String nicknameStr = cursor.getString(cursor.getColumnIndex(Nickname.NAME));
						if (!TextUtils.isEmpty(nicknameStr)) {
							nickname = new NicknameBean();
							nickname.id = id;
							nickname.nickname = nicknameStr;
							nickname.idBackup = id;
							nickname.nicknameBackup = nicknameStr;
							break;
						}
					}
				} while (cursor.moveToNext());
			}

		}
		return nickname;
	}

}

// /:~