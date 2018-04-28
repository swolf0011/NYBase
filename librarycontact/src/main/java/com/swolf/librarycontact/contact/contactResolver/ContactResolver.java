package com.swolf.librarycontact.contact.contactResolver;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContactsEntity;
import android.text.TextUtils;

import com.swolf.librarycontact.contact.bean.ContactBean;
import com.swolf.librarycontact.contact.bean.GroupMembershipBean;
import com.swolf.librarycontact.contact.bean.NameBean;

/**
 * 联系人提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class ContactResolver {
    private Context context;
    private ContentResolver cr;

    public ContactResolver(Context context) {
        super();
        this.context = context;
        this.cr = context.getContentResolver();
    }

    public ArrayList<ContactBean> getAllContact() {
        ArrayList<ContactBean> list = new ArrayList<ContactBean>();
        Cursor cursor = cr.query(Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                ContactBean contact = new ContactBean();
                contact.contactId = cursor.getString(cursor.getColumnIndex(Contacts._ID));
                contact.name.displayName = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
                contact.rawContactId = getRawContactIdByContactId(contact.contactId);
                list.add(contact);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public ArrayList<ContactBean> getContactOfUnGroup() {
        ArrayList<ContactBean> list = new ArrayList<ContactBean>();
        ContactResolver ccr = new ContactResolver(context);
        ArrayList<ContactBean> allList = ccr.getAllContact();
        GroupMembershipResolver gmr = new GroupMembershipResolver(context);
        String rawContactIds = gmr.getAllGroupExistsRawContactIds();
        int size = allList.size();
        if (TextUtils.isEmpty(rawContactIds)) {
            for (int i = 0; i < size; i++) {
                ContactBean cb = allList.get(i);
                list.add(cb);
            }
        } else {
            String[] rawContactIdArray = rawContactIds.split(",");
            for (int i = 0; i < size; i++) {
                boolean bool = true;
                ContactBean cb = allList.get(i);
                for (int j = 0; j < rawContactIdArray.length; j++) {
                    if (TextUtils.equals(cb.rawContactId, rawContactIdArray[j])) {
                        bool = false;
                        break;
                    }
                }
                if (bool) {
                    list.add(cb);
                }
            }
        }
        allList.clear();
        allList = null;
        return list;
    }

    public ArrayList<ContactBean> getContactByGroupId(String groupId) {
        ArrayList<ContactBean> list = new ArrayList<ContactBean>();
        GroupMembershipResolver gmr = new GroupMembershipResolver(context);
        ArrayList<GroupMembershipBean> groupMembershipList = gmr.getGroupMembershipByGroupId(groupId);
        if (!groupMembershipList.isEmpty()) {
            ContactResolver ccr = new ContactResolver(context);
            int size = groupMembershipList.size();
            for (int i = 0; i < size; i++) {
                GroupMembershipBean groupMembership = groupMembershipList.get(i);
                ContactBean cb = ccr.getContactByContactId(groupMembership.rawContactId);
                if (cb != null) {
                    list.add(cb);
                }
            }
        }
        return list;
    }

    public ArrayList<ContactBean> getContactByNumber(String number) {
        ArrayList<ContactBean> list = new ArrayList<ContactBean>();
        String[] projection = {Phone.CONTACT_ID};
        String selection = Phone.NUMBER + "=?";
        String[] selectionArgs = {number};
        Cursor cursor = cr.query(Phone.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            do {
                String contactId = cursor.getString(cursor.getColumnIndex(Phone.CONTACT_ID));
                ContactBean contact = getContactByContactId(contactId);
                list.add(contact);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public ArrayList<ContactBean> getContactIdRawContactIdName2ContactByNumber(String number) {
        ArrayList<ContactBean> list = new ArrayList<ContactBean>();
        String[] projection = {Phone.CONTACT_ID, Phone.RAW_CONTACT_ID, PhoneLookup.DISPLAY_NAME};
        String selection = Phone.NUMBER + "=?";
        String[] selectionArgs = {number};
        Cursor cursor = cr.query(Phone.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            do {
                ContactBean contact = new ContactBean();
                contact.name = new NameBean();
                contact.name.displayName = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
                contact.contactId = cursor.getString(cursor.getColumnIndex(Phone.CONTACT_ID));
                contact.rawContactId = cursor.getString(cursor.getColumnIndex(Phone.RAW_CONTACT_ID));
                contact.photo = new PhotoResolver(context).getPhotoByContactId(contact.contactId);
                list.add(contact);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public ArrayList<String> getRawContactIdsByNumber(String number) {
        ArrayList<String> list = new ArrayList<String>();
        String[] projection = {Phone.RAW_CONTACT_ID};
        String selection = Phone.NUMBER + "=?";
        String[] selectionArgs = {number};
        Cursor cursor = cr.query(Phone.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex(Phone.RAW_CONTACT_ID)));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public String getContactIdByRawContactId(String rawContactId) {
        String contactId = null;
        String[] projection = {RawContacts.CONTACT_ID};
        String selection = RawContacts._ID + "=?";
        String[] selectionArgs = {rawContactId};
        Cursor cursor = cr.query(RawContacts.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            contactId = cursor.getString(0);
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return contactId;
    }

    public String getRawContactIdByContactId(String contactId) {
        String rawContactId = null;
        String[] projection = {RawContacts._ID};
        String selection = RawContacts.CONTACT_ID + "=?";
        String[] selectionArgs = {contactId};
        Cursor cursor = cr.query(RawContacts.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            rawContactId = cursor.getString(0);
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return rawContactId;
    }

    public ArrayList<String> getRawContactIdsByName(String name) {
        ArrayList<String> list = new ArrayList<String>();
        String[] projection = {Data.RAW_CONTACT_ID};
        String selection = Contacts.DISPLAY_NAME + "=?";
        String[] selectionArgs = {name};
        Cursor cursor = cr.query(Data.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex(Data.RAW_CONTACT_ID)));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public ContactBean getContactByContactId(String contactId) {
        String selection = RawContacts._ID + " =?";
        String[] selectionArgs = {contactId};
        Cursor cursor = cr.query(RawContactsEntity.CONTENT_URI, null, selection, selectionArgs, null);
        ContactBean cb = new ContactBean();
        cb.contactId = contactId;
        cb.rawContactId = getRawContactIdByContactId(contactId);
        cb.ring = new RingtoneResolver(context).getRingtoneByContactId(contactId);
        cb.photo = new PhotoResolver(context).getPhotoByContactId(contactId);
        cb.name = new StructuredNameResolver(context).getStructuredNameByCursor(cursor);
        cb.birthday = new BirthdayResolver(context).getBirthdayByCursor(cursor);
        cb.nickname = new NicknameResolver(context).getNicknameByCursor(cursor);
        GroupMembershipResolver gmr = new GroupMembershipResolver(context);
        cb.groupMembershipList = gmr.getGroupMembershipByRawContactId(cb.rawContactId);
        cb.phoneList = new PhoneResolver(context).getPhoneByCursor(cursor);
        cb.emailList = new EmailResolver(context).getEmailByCursor(cursor);
        cb.postalList = new PostalResolver(context).getPostalByCursor(cursor);
        cb.organizationList = new OrganziationResolver(context).getOrganziationByCursor(cursor);
        cb.imList = new ImResolver(context).getImByCursor(cursor);
        cb.websiteList = new WebsiteResolver(context).getWebsiteByCursor(cursor);
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return cb;
    }

    public boolean delContactByRawContactId(String rawContactId) {
        boolean bool = false;
        if (!TextUtils.isEmpty(rawContactId)) {
            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
            long Id = Long.parseLong(rawContactId);
            ops.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(RawContacts.CONTENT_URI, Id)).build());
            try {
                cr.applyBatch(ContactsContract.AUTHORITY, ops);
                bool = true;
            } catch (Exception e) {
                e.printStackTrace();
                bool = false;
            }
        }
        return bool;
    }

    public boolean delContactByRawContactIds(ArrayList<String> list) {
        boolean bool = false;
        if (list != null && !list.isEmpty()) {
            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
            for (String string : list) {
                long Id = Long.parseLong(string);
                ops.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(RawContacts.CONTENT_URI, Id)).build());
            }
            try {
                cr.applyBatch(ContactsContract.AUTHORITY, ops);
                bool = true;
            } catch (Exception e) {
                e.printStackTrace();
                bool = false;
            }
        }
        return bool;
    }

    public boolean delContactByName(String name) {
        return delContactByRawContactIds(getRawContactIdsByName(name));
    }

    public boolean delContactByNumber(String number) {
        return delContactByRawContactIds(getRawContactIdsByNumber(number));
    }

    public boolean delContactByContactId(String contactId) {
        return delContactByRawContactId(getRawContactIdByContactId(contactId));
    }
}

// /:~