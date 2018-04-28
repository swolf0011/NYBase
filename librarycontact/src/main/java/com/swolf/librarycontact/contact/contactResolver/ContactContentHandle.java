package com.swolf.librarycontact.contact.contactResolver;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;

import com.swolf.librarycontact.contact.bean.ContactBean;
import com.swolf.librarycontact.contact.bean.EmailBean;
import com.swolf.librarycontact.contact.bean.GroupMembershipBean;
import com.swolf.librarycontact.contact.bean.ImBean;
import com.swolf.librarycontact.contact.bean.OrganizationBean;
import com.swolf.librarycontact.contact.bean.PhoneBean;
import com.swolf.librarycontact.contact.bean.PostalBean;
import com.swolf.librarycontact.contact.bean.WebsiteBean;


/**
 * 联系人处理类
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class ContactContentHandle {

	private ContactBean contact;
	private ArrayList<ContentProviderOperation> ops;

	public boolean updateDelInsertByContactId(Context context, ContactBean contact) {
		boolean bool = false;
		if (contact != null && !TextUtils.isEmpty(contact.contactId)) {
			this.contact = contact;
			ops = new ArrayList<ContentProviderOperation>();
			ringUpdateDelInsertByContactId();
			nameUpdateDelInsertByContactId();
			photoUpdateDelInsertByContactId();
			nicknameUpdateDelInsertByContactId();
			birthdayUpdateDelInsertByContactId();
			phoneUpdateDelInsertByContactId();
			emailUpdateDelInsertByContactId();
			imUpdateDelInsertByContactId();
			organizationUpdateDelInsertByContactId();
			postalUpdateDelInsertByContactId();
			postalUpdateDelInsertByContactId();
			websiteUpdateDelInsertByContactId();
			try {
				if (ops.size() > 0) {
					ContentResolver cr = context.getContentResolver();
					ContentProviderResult[] result = cr.applyBatch(ContactsContract.AUTHORITY, ops);
					if (result != null && result.length > 0 && TextUtils.isEmpty(this.contact.contactId)) {
						this.contact.rawContactId = ContentUris.parseId(result[0].uri) + "";
						this.contact.contactId = new ContactResolver(context).getContactIdByRawContactId(this.contact.rawContactId);
					}
				}
				groupUpdateDelInsertByContactId(context);
				bool = true;
			} catch (Exception e) {
				e.printStackTrace();
				bool = false;
			}
		}
		return bool;
	}

	public boolean save(Context context, ContactBean contact) {
		boolean bool = false;
		if (contact != null && TextUtils.isEmpty(contact.contactId)) {
			this.contact = contact;
			ops = new ArrayList<ContentProviderOperation>();
			ringSave();
			nameSave();
			photoSave();
			nicknameSave();
			birthdaySave();
			phoneSave();
			emailSave();
			imSave();
			organizationSave();
			postalSave();
			websiteSave();
			try {
				if (ops.size() > 0) {
					ContentResolver cr = context.getContentResolver();
					ContentProviderResult[] result = cr.applyBatch(ContactsContract.AUTHORITY, ops);
					if (result != null && result.length > 0 && TextUtils.isEmpty(this.contact.contactId)) {
						this.contact.rawContactId = ContentUris.parseId(result[0].uri) + "";
						this.contact.contactId = new ContactResolver(context).getContactIdByRawContactId(this.contact.rawContactId);
						groupSave(context);
						bool = true;
					}
				} else {
					groupSave(context);
					bool = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				bool = false;
			}
		}
		return bool;
	}

	private void ringUpdateDelInsertByContactId() {
		if (contact.ring != null && null != contact.ring.uri && contact.ring.uri.toString().length() > 0) {
			Builder builder = ContentProviderOperation.newUpdate(Contacts.CONTENT_URI);
			builder.withSelection(Contacts._ID + "=?", new String[] { this.contact.contactId });
			builder.withValue(Contacts.CUSTOM_RINGTONE, contact.ring.uri.toString());
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void nameUpdateDelInsertByContactId() {
		if (contact.delName != null) {// 删除
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { contact.name.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		if (contact.name != null) {
			ContentValues cv = new ContentValues();
			cv.put(StructuredName.GIVEN_NAME, contact.name.firstName);
			cv.put(StructuredName.FAMILY_NAME, contact.name.lastName);
			cv.put(StructuredName.MIDDLE_NAME, contact.name.middleName);
			Builder builder = null;
			if (TextUtils.isEmpty(contact.name.id)) {// 修改添加新的数据
				builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
				builder.withValue(Data.RAW_CONTACT_ID, contact.rawContactId);
				builder.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
			} else {
				if (contact.name.isUpdate()) {// 修改
					builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
					builder.withSelection(Data._ID + "=?", new String[] { contact.name.id });
				}
			}
			if (builder != null) {
				builder.withValues(cv);
				builder.withYieldAllowed(true);
				ops.add(builder.build());
			}
		}
	}

	private void photoUpdateDelInsertByContactId() {
		if (contact.delPhoto != null) {// 删除
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { contact.photo.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		if (contact.photo != null) {
			ContentValues cv = new ContentValues();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			contact.photo.photo.compress(Bitmap.CompressFormat.PNG, 100, out);
			cv.put(Photo.PHOTO, out.toByteArray());
			Builder builder = null;
			if (TextUtils.isEmpty(contact.photo.id)) {// 修改添加新的数据
				builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
				builder.withValue(Data.RAW_CONTACT_ID, contact.rawContactId);
				builder.withValue(Data.MIMETYPE, Photo.CONTENT_ITEM_TYPE);
				builder.withValue(Photo.IS_SUPER_PRIMARY, 1);
			} else {
				if (contact.photo.isUpdate()) {// 修改
					builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
					builder.withSelection(Data._ID + "=?", new String[] { contact.photo.id });
				}
			}
			if (builder != null) {
				builder.withValues(cv);
				builder.withYieldAllowed(true);
				ops.add(builder.build());
			}
		}
	}

	private void nicknameUpdateDelInsertByContactId() {
		if (contact.delNickname != null) {// 删除
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { contact.nickname.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		if (contact.nickname != null) {
			ContentValues cv = new ContentValues();
			cv.put(Nickname.NAME, contact.nickname.nickname);
			Builder builder = null;
			if (TextUtils.isEmpty(contact.nickname.id)) {// 修改添加新的数据
				builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
				builder.withValue(Data.MIMETYPE, Nickname.CONTENT_ITEM_TYPE);
				builder.withValue(Nickname.TYPE, Nickname.TYPE_DEFAULT);
				builder.withValue(Data.RAW_CONTACT_ID, contact.rawContactId);
			} else {
				if (contact.nickname.isUpdate()) {// 修改
					builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
					builder.withSelection(Data._ID + "=?", new String[] { contact.nickname.id });
				}
			}
			if (builder != null) {
				builder.withValues(cv);
				builder.withYieldAllowed(true);
				ops.add(builder.build());
			}
		}
	}

	private void birthdayUpdateDelInsertByContactId() {
		if (contact.delBirthday != null) {// 删除
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { contact.birthday.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		if (contact.birthday != null) {
			ContentValues cv = new ContentValues();
			cv.put(Event.DATA, contact.birthday.birthday);
			cv.put(Event.TYPE, Event.TYPE_BIRTHDAY);
			Builder builder = null;
			if (TextUtils.isEmpty(contact.birthday.id)) {// 修改添加新的数据
				builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
				builder.withValue(Data.MIMETYPE, Event.CONTENT_ITEM_TYPE);
				builder.withValue(Data.RAW_CONTACT_ID, contact.rawContactId);
			} else {
				if (contact.birthday.isUpdate()) {// 修改
					builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
					builder.withSelection(Data._ID + "=?", new String[] { contact.birthday.id });
				}
			}
			if (builder != null) {
				builder.withValues(cv);
				builder.withYieldAllowed(true);
				ops.add(builder.build());
			}
		}
	}

	private void phoneUpdateDelInsertByContactId() {
		int size = contact.delPhoneList.size();
		for (int i = 0; i < size; i++) {
			PhoneBean phone = contact.delPhoneList.get(i);
			if (TextUtils.isEmpty(phone.id)) {
				continue;
			}
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { phone.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		int count = contact.phoneList.size();
		for (int i = 0; i < count; i++) {
			PhoneBean phone = contact.phoneList.get(i);
			if (TextUtils.isEmpty(phone.number)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(Phone.TYPE, phone.typeId);
			cv.put(Phone.NUMBER, phone.number);
			cv.put(Phone.LABEL, phone.type);
			Builder builder = null;
			if (TextUtils.isEmpty(phone.id)) {// 修改添加新的数据
				builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
				builder.withValue(Data.RAW_CONTACT_ID, contact.rawContactId);
				builder.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
			} else {
				if (phone.isUpdate()) {// 修改
					builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
					builder.withSelection(Data._ID + "=?", new String[] { phone.id });
				}
			}
			if (builder != null) {
				builder.withValues(cv);
				builder.withYieldAllowed(true);
				ops.add(builder.build());
			}
		}
	}

	private void emailUpdateDelInsertByContactId() {
		int size = contact.delEmailList.size();
		for (int i = 0; i < size; i++) {
			EmailBean email = contact.delEmailList.get(i);
			if (TextUtils.isEmpty(email.id)) {
				continue;
			}
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { email.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		int count = contact.emailList.size();
		for (int i = 0; i < count; i++) {
			EmailBean email = contact.emailList.get(i);
			if (TextUtils.isEmpty(email.email)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(Email.TYPE, email.typeId);
			cv.put(Email.DATA, email.email);
			cv.put(Email.LABEL, email.type);
			Builder builder = null;
			if (TextUtils.isEmpty(email.id)) {// 修改添加新的数据
				builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
				builder.withValue(Data.RAW_CONTACT_ID, contact.rawContactId);
				builder.withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
			} else {
				if (email.isUpdate()) {// 修改
					builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
					builder.withSelection(Data._ID + "=?", new String[] { email.id });
				}
			}
			if (builder != null) {
				builder.withValues(cv);
				builder.withYieldAllowed(true);
				ops.add(builder.build());
			}
		}
	}

	private void imUpdateDelInsertByContactId() {
		int size = contact.delImList.size();
		for (int i = 0; i < size; i++) {
			ImBean im = contact.delImList.get(i);
			if (TextUtils.isEmpty(im.id)) {
				continue;
			}
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { im.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		int count = contact.imList.size();
		for (int i = 0; i < count; i++) {
			ImBean im = contact.imList.get(i);
			// 空的不处理
			if (TextUtils.isEmpty(im.im)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(Im.PROTOCOL, im.typeId);
			cv.put(Im.CUSTOM_PROTOCOL, im.type);
			cv.put(Im.DATA, im.im);
			Builder builder = null;
			if (TextUtils.isEmpty(im.id)) {// 修改添加新的数据
				builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
				builder.withValue(Data.RAW_CONTACT_ID, contact.rawContactId);
				builder.withValue(Data.MIMETYPE, Im.CONTENT_ITEM_TYPE);
			} else {
				if (im.isUpdate()) {// 修改
					builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
					builder.withSelection(Data._ID + "=?", new String[] { im.id });
				}
			}
			if (builder != null) {
				builder.withValues(cv);
				builder.withYieldAllowed(true);
				ops.add(builder.build());
			}
		}
	}

	private void organizationUpdateDelInsertByContactId() {
		int size = contact.delOrganizationList.size();
		for (int i = 0; i < size; i++) {
			OrganizationBean organization = contact.delOrganizationList.get(i);
			if (TextUtils.isEmpty(organization.id)) {
				continue;
			}
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { organization.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		int count = contact.organizationList.size();
		for (int i = 0; i < count; i++) {
			OrganizationBean organization = contact.organizationList.get(i);
			if (TextUtils.isEmpty(organization.company) && TextUtils.isEmpty(organization.title)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(Organization.TYPE, organization.typeId);
			cv.put(Organization.COMPANY, organization.company);
			cv.put(Organization.TITLE, organization.title);
			cv.put(Organization.LABEL, organization.type);
			Builder builder = null;
			if (TextUtils.isEmpty(organization.id)) {// 修改添加新的数据
				builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
				builder.withValue(Data.RAW_CONTACT_ID, contact.rawContactId);
				builder.withValue(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
			} else {
				if (organization.isUpdate()) {// 修改
					builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
					builder.withSelection(Data._ID + "=?", new String[] { organization.id });
				}
			}
			if (builder != null) {
				builder.withValues(cv);
				builder.withYieldAllowed(true);
				ops.add(builder.build());
			}
		}
	}

	private void postalUpdateDelInsertByContactId() {
		int size = contact.delPostalList.size();
		for (int i = 0; i < size; i++) {
			PostalBean postal = contact.delPostalList.get(i);
			if (TextUtils.isEmpty(postal.id)) {
				continue;
			}
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { postal.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		int count = contact.postalList.size();
		for (int i = 0; i < count; i++) {
			PostalBean postal = contact.postalList.get(i);
			if (TextUtils.isEmpty(postal.street) && TextUtils.isEmpty(postal.pobox) && TextUtils.isEmpty(postal.neighborhood)
					&& TextUtils.isEmpty(postal.city) && TextUtils.isEmpty(postal.state) && TextUtils.isEmpty(postal.zipCode)
					&& TextUtils.isEmpty(postal.country)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(StructuredPostal.TYPE, postal.typeId);
			cv.put(StructuredPostal.LABEL, postal.type);
			cv.put(StructuredPostal.STREET, postal.street);
			cv.put(StructuredPostal.POBOX, postal.pobox);
			cv.put(StructuredPostal.NEIGHBORHOOD, postal.neighborhood);
			cv.put(StructuredPostal.CITY, postal.city);
			cv.put(StructuredPostal.REGION, postal.state);
			cv.put(StructuredPostal.POSTCODE, postal.zipCode);
			cv.put(StructuredPostal.COUNTRY, postal.country);
			Builder builder = null;
			if (TextUtils.isEmpty(postal.id)) {// 修改添加新的数据
				builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
				builder.withValue(Data.RAW_CONTACT_ID, contact.rawContactId);
				builder.withValue(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
			} else {
				if (postal.isUpdate()) {// 修改
					builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
					builder.withSelection(Data._ID + "=?", new String[] { postal.id });
				}
			}
			if (builder != null) {
				builder.withValues(cv);
				builder.withYieldAllowed(true);
				ops.add(builder.build());
			}
		}
	}

	private void websiteUpdateDelInsertByContactId() {
		int size = contact.delWebsiteList.size();
		for (int i = 0; i < size; i++) {
			WebsiteBean website = contact.delWebsiteList.get(i);
			if (TextUtils.isEmpty(website.id)) {
				continue;
			}
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { website.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		int count = contact.websiteList.size();
		for (int i = 0; i < count; i++) {
			WebsiteBean website = contact.websiteList.get(i);
			if (TextUtils.isEmpty(website.website)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(Website.URL, website.website);
			Builder builder = null;
			if (TextUtils.isEmpty(website.id)) {// 修改添加新的数据
				builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
				builder.withValue(Data.RAW_CONTACT_ID, contact.rawContactId);
				builder.withValue(Data.MIMETYPE, Website.CONTENT_ITEM_TYPE);
			} else {
				if (website.isUpdate()) {// 修改
					builder = ContentProviderOperation.newUpdate(Data.CONTENT_URI);
					builder.withSelection(Data._ID + "=?", new String[] { website.id });
				}
			}
			if (builder != null) {
				builder.withValues(cv);
				builder.withYieldAllowed(true);
				ops.add(builder.build());
			}
		}
	}

	private void groupUpdateDelInsertByContactId(Context context) {

		GroupMembershipResolver gmr = new GroupMembershipResolver(context);

		int size = contact.delGroupMembershipList.size();
		for (int i = 0; i < size; i++) {
			GroupMembershipBean groupMembership = contact.delGroupMembershipList.get(i);
			if (TextUtils.isEmpty(groupMembership.id)) {
				continue;
			}
			Builder builder = ContentProviderOperation.newDelete(Data.CONTENT_URI);
			builder.withSelection(Data._ID + "=?", new String[] { groupMembership.id });
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
		int count = contact.groupMembershipList.size();
		for (int i = 0; i < count; i++) {
			GroupMembershipBean groupMembership = contact.groupMembershipList.get(i);
			if (TextUtils.isEmpty(groupMembership.id)) {
				continue;
			}
			if (TextUtils.isEmpty(groupMembership.id)) {// 修改添加新的数据
				gmr.addGroupMembership(groupMembership);
			} else {
				if (groupMembership.isUpdate()) {// 修改
					gmr.updateGroupMembershipByContactIds(groupMembership);
				}
			}
		}
	}

	private void ringSave() {
		if (contact.ring != null) {
			Builder builder = ContentProviderOperation.newInsert(RawContacts.CONTENT_URI);
			builder.withValue(RawContacts.ACCOUNT_TYPE, null);
			builder.withValue(RawContacts.ACCOUNT_NAME, null);
			// 处理铃声
			if (null != contact.ring.uri && contact.ring.uri.toString().length() > 0) {
				builder.withValue(Contacts.CUSTOM_RINGTONE, contact.ring.uri.toString());
			}
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void nameSave() {
		if (contact.name != null) {
			ContentValues cv = new ContentValues();
			cv.put(StructuredName.GIVEN_NAME, contact.name.firstName);
			cv.put(StructuredName.FAMILY_NAME, contact.name.lastName);
			cv.put(StructuredName.MIDDLE_NAME, contact.name.middleName);
			Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
			builder.withValueBackReference(Data.RAW_CONTACT_ID, 0);
			builder.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
			builder.withValues(cv);
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void photoSave() {
		if (contact.photo != null) {
			ContentValues cv = new ContentValues();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			contact.photo.photo.compress(Bitmap.CompressFormat.PNG, 100, out);
			cv.put(Photo.PHOTO, out.toByteArray());
			Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
			builder.withValueBackReference(Data.RAW_CONTACT_ID, 0);
			builder.withValue(Data.MIMETYPE, Photo.CONTENT_ITEM_TYPE);
			builder.withValue(Photo.IS_SUPER_PRIMARY, 1);
			builder.withValues(cv);
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void nicknameSave() {
		if (contact.nickname != null) {
			ContentValues cv = new ContentValues();
			cv.put(Nickname.NAME, contact.nickname.nickname);
			Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
			builder.withValue(Data.RAW_CONTACT_ID, 0);
			builder.withValue(Data.MIMETYPE, Nickname.CONTENT_ITEM_TYPE);
			builder.withValue(Nickname.TYPE, Nickname.TYPE_DEFAULT);
			builder.withValues(cv);
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void birthdaySave() {
		if (contact.birthday != null) {
			ContentValues cv = new ContentValues();
			cv.put(Event.DATA, contact.birthday.birthday);
			cv.put(Event.TYPE, Event.TYPE_BIRTHDAY);
			Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
			builder.withValueBackReference(Data.RAW_CONTACT_ID, 0);
			builder.withValue(Data.MIMETYPE, Event.CONTENT_ITEM_TYPE);
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void phoneSave() {
		int count = contact.phoneList.size();
		for (int i = 0; i < count; i++) {
			PhoneBean phone = contact.phoneList.get(i);
			if (TextUtils.isEmpty(phone.number)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(Phone.TYPE, phone.typeId);
			cv.put(Phone.NUMBER, phone.number);
			cv.put(Phone.LABEL, phone.type);
			Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
			builder.withValueBackReference(Data.RAW_CONTACT_ID, 0);
			builder.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
			builder.withValues(cv);
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void emailSave() {
		int count = contact.emailList.size();
		for (int i = 0; i < count; i++) {
			EmailBean email = contact.emailList.get(i);
			if (TextUtils.isEmpty(email.email)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(Email.TYPE, email.typeId);
			cv.put(Email.DATA, email.email);
			cv.put(Email.LABEL, email.type);
			Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
			builder.withValueBackReference(Data.RAW_CONTACT_ID, 0);
			builder.withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
			builder.withValues(cv);
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void imSave() {
		int count = contact.imList.size();
		for (int i = 0; i < count; i++) {
			ImBean im = contact.imList.get(i);
			if (TextUtils.isEmpty(im.im)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(Im.PROTOCOL, im.typeId);
			cv.put(Im.CUSTOM_PROTOCOL, im.type);
			cv.put(Im.DATA, im.im);
			Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
			builder.withValueBackReference(Data.RAW_CONTACT_ID, 0);
			builder.withValue(Data.MIMETYPE, Im.CONTENT_ITEM_TYPE);
			builder.withValues(cv);
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void organizationSave() {
		int count = contact.organizationList.size();
		for (int i = 0; i < count; i++) {
			OrganizationBean organization = contact.organizationList.get(i);
			if (TextUtils.isEmpty(organization.company) && TextUtils.isEmpty(organization.title)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(Organization.TYPE, organization.typeId);
			cv.put(Organization.COMPANY, organization.company);
			cv.put(Organization.TITLE, organization.title);
			cv.put(Organization.LABEL, organization.type);
			Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
			builder.withValueBackReference(Data.RAW_CONTACT_ID, 0);
			builder.withValue(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
			builder.withValues(cv);
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void postalSave() {
		int count = contact.postalList.size();
		for (int i = 0; i < count; i++) {
			PostalBean postal = contact.postalList.get(i);
			if (TextUtils.isEmpty(postal.street) && TextUtils.isEmpty(postal.pobox) && TextUtils.isEmpty(postal.neighborhood)
					&& TextUtils.isEmpty(postal.city) && TextUtils.isEmpty(postal.state) && TextUtils.isEmpty(postal.zipCode)
					&& TextUtils.isEmpty(postal.country)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(StructuredPostal.TYPE, postal.typeId);
			cv.put(StructuredPostal.LABEL, postal.type);
			cv.put(StructuredPostal.STREET, postal.street);
			cv.put(StructuredPostal.POBOX, postal.pobox);
			cv.put(StructuredPostal.NEIGHBORHOOD, postal.neighborhood);
			cv.put(StructuredPostal.CITY, postal.city);
			cv.put(StructuredPostal.REGION, postal.state);
			cv.put(StructuredPostal.POSTCODE, postal.zipCode);
			cv.put(StructuredPostal.COUNTRY, postal.country);

			Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
			builder.withValueBackReference(Data.RAW_CONTACT_ID, 0);
			builder.withValue(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
			builder.withValues(cv);
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void websiteSave() {
		int count = contact.websiteList.size();
		for (int i = 0; i < count; i++) {
			WebsiteBean website = contact.websiteList.get(i);
			if (TextUtils.isEmpty(website.website)) {
				continue;
			}
			ContentValues cv = new ContentValues();
			cv.put(Website.URL, website.website);
			Builder builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
			builder.withValueBackReference(Data.RAW_CONTACT_ID, 0);
			builder.withValue(Data.MIMETYPE, Website.CONTENT_ITEM_TYPE);
			builder.withValues(cv);
			builder.withYieldAllowed(true);
			ops.add(builder.build());
		}
	}

	private void groupSave(Context context) {
		GroupMembershipResolver gmr = new GroupMembershipResolver(context);
		int count = contact.groupMembershipList.size();
		for (int i = 0; i < count; i++) {
			GroupMembershipBean groupMembership = contact.groupMembershipList.get(i);
			if (TextUtils.isEmpty(groupMembership.id)) {
				continue;
			}
			gmr.addGroupMembership(groupMembership);
		}
	}
}

// /:~