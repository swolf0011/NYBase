package com.swolf.librarycontact.contact.contactResolver;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContactsEntity;

import com.swolf.librarycontact.contact.bean.PostalBean;


/**
 * 邮箱提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class PostalResolver {
	private ContentResolver contentResolver;

	public PostalResolver(Context context) {
		super();
		this.contentResolver = context.getContentResolver();
	}

	public ArrayList<PostalBean> getPostalByContactId(String contactId) {
		String selection = RawContacts.CONTACT_ID + "=" + contactId;
		Cursor cursor = contentResolver.query(RawContactsEntity.CONTENT_URI, null, selection, null, null);
		ArrayList<PostalBean> list = getPostalByCursor(cursor);
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return list;
	}

	public ArrayList<PostalBean> getPostalByCursor(Cursor cursor) {
		ArrayList<PostalBean> list = new ArrayList<PostalBean>();
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				String itemMimeType;
				do {
					itemMimeType = cursor.getString(cursor.getColumnIndex(RawContactsEntity.MIMETYPE));
					if (itemMimeType.equals(StructuredPostal.CONTENT_ITEM_TYPE)) {
						PostalBean position = new PostalBean();
						position.id = cursor.getString(cursor.getColumnIndex(RawContactsEntity.DATA_ID));
						position.typeId = cursor.getString(cursor.getColumnIndex(StructuredPostal.TYPE));
						position.type = cursor.getString(cursor.getColumnIndex(StructuredPostal.LABEL));
						position.street = cursor.getString(cursor.getColumnIndex(StructuredPostal.STREET));
						position.pobox = cursor.getString(cursor.getColumnIndex(StructuredPostal.POBOX));
						position.neighborhood = cursor.getString(cursor.getColumnIndex(StructuredPostal.NEIGHBORHOOD));
						position.city = cursor.getString(cursor.getColumnIndex(StructuredPostal.CITY));
						position.state = cursor.getString(cursor.getColumnIndex(StructuredPostal.REGION));
						position.zipCode = cursor.getString(cursor.getColumnIndex(StructuredPostal.POSTCODE));
						position.country = cursor.getString(cursor.getColumnIndex(StructuredPostal.COUNTRY));

						position.idBackup = position.id;
						position.typeIdBackup = position.typeId;
						position.typeBackup = position.type;
						position.streetBackup = position.street;
						position.poboxBackup = position.pobox;
						position.neighborhoodBackup = position.neighborhood;
						position.cityBackup = position.city;
						position.stateBackup = position.state;
						position.zipCodeBackup = position.zipCode;
						position.countryBackup = position.country;

						list.add(position);
					}
				} while (cursor.moveToNext());
			}
		}
		return list;
	}
}

// /:~