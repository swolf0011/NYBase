package com.swolf.librarycontact.contact;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 联系人工具
 * <uses-permission android:name="android.permission.READ_CONTACTS"></uses-permission>
 * <uses-permission android:name="android.permission.WRITE_CONTACTS"></uses-permission>
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class ContactUtil {

    // <uses-permission android:name="android.permission.READ_CONTACTS" />
    // <uses-permission android:name="android.permission.WRITE_CONTACTS" />

    private static Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;

    @SuppressWarnings("unused")
    private static Uri dataUri = ContactsContract.Data.CONTENT_URI;
    @SuppressWarnings("unused")
    private static Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;

    private static String[] projection = {ContactsContract.Contacts.CONTENT_ITEM_TYPE,
            ContactsContract.Contacts.CONTENT_TYPE, ContactsContract.Contacts.CONTENT_VCARD_TYPE,

            ContactsContract.Contacts._ID, ContactsContract.Contacts._COUNT,

            ContactsContract.Contacts.CUSTOM_RINGTONE,// URI与联系人相关联的自定义铃声。
            ContactsContract.Contacts.LAST_TIME_CONTACTED,// 最后一次接触了接触。
            ContactsContract.Contacts.SEND_TO_VOICEMAIL,// 接触是否应该总是被发送到语音邮件。
            ContactsContract.Contacts.STARRED,// 是接触出演
            ContactsContract.Contacts.TIMES_CONTACTED,// 的次数已被接触的接触

            ContactsContract.Contacts.CONTACT_PRESENCE,// 联系存在状态。
            ContactsContract.Contacts.CONTACT_STATUS,// 联系人的最新状态更新。
            ContactsContract.Contacts.CONTACT_STATUS_ICON,// 源的接触状态的图标资源ID。
            ContactsContract.Contacts.CONTACT_STATUS_LABEL,// 资源ID的标签描述的源的接触状态。
            ContactsContract.Contacts.CONTACT_STATUS_RES_PACKAGE,// 状态：标签和图标包中包含的资源。
            ContactsContract.Contacts.CONTACT_STATUS_TIMESTAMP,// 插入/更新的最新状态时的绝对时间（以毫秒为单位）。
            ContactsContract.Contacts.CUSTOM_RINGTONE,// URI与联系人相关联的自定义铃声。
            ContactsContract.Contacts.CONTACT_STATUS_LABEL,// 参照保持的照片的数据表中的行。

            ContactsContract.Contacts.DISPLAY_NAME,// 显示该联系人的姓名

            ContactsContract.Contacts.HAS_PHONE_NUMBER,// 接触的一个指标，这是否至少有一个电话号码。
            ContactsContract.Contacts.IN_VISIBLE_GROUP, ContactsContract.Contacts.LOOKUP_KEY,// 查找电话号码KEY
            ContactsContract.Contacts.PHOTO_ID,// 参照保持的照片的数据表中的行。
    };

    /**
     * 查询全部联系人
     *
     * @return
     */
    public static Cursor queryAll(ContentResolver contentResolver) {
        // 查询数据，返回Cursor
        return contentResolver.query(contactsUri, projection, null, null, null);
    }

    /**
     * 条件查询联系人
     *
     * @param projections
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    public static Cursor queryBy(ContentResolver contentResolver, String[] projections, String selection,
                                 String[] selectionArgs, String sortOrder) {
        // 查询数据，返回Cursor String selection, String[] selectionArgs, String
        // sortOrder
        return contentResolver.query(contactsUri, projections, selection, selectionArgs, sortOrder);
    }

    /**
     * 根据联系人名查询联系人电话
     *
     * @param display_name 联系人名
     * @return "CONTENT_TYPE:13312341234,CONTENT_TYPE:13312341235";
     */
    public static List<String> queryPhoneNumberByDisplay_name(ContentResolver contentResolver, String display_name) {
        // String result = "CONTENT_TYPE:13312341234,CONTENT_TYPE:13312341235";
        List<String> list = new ArrayList<String>();
        Cursor cursor = null;
        try {
            // 获取ContentResolver
            String[] projections = {ContactsContract.Contacts.DISPLAY_NAME,// 显示该联系人的姓名
                    ContactsContract.Contacts.HAS_PHONE_NUMBER,// 接触的一个指标，这是否至少有一个电话号码。
                    ContactsContract.Contacts.LOOKUP_KEY // 查找电话号码KEY
            };
            String selection = ContactsContract.Contacts.DISPLAY_NAME + "=?";
            String[] selectionArgs = {display_name}; // 查询参数
            cursor = contentResolver.query(contactsUri, projections, selection, selectionArgs, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String phoneNumbers = "";
                    if (cursor.getInt(1) > 0) {
                        phoneNumbers = getAllPhoneNumbersByLookUpKey(contentResolver, cursor.getString(2));
                    }
                    list.add(phoneNumbers);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * 删除联系人
     *
     * @param contentResolver
     * @param where
     * @param selectionArgs
     * @return
     */
    public static int delete(ContentResolver contentResolver, String where, String[] selectionArgs) {
        return contentResolver.delete(contactsUri, where, selectionArgs);
    }

    /**
     * 根据联系人名删除联系人
     *
     * @param contentResolver
     * @param display_name    联系人名
     * @return
     */
    public static int deleteByDisplayName(ContentResolver contentResolver, String display_name) {
        String where = ContactsContract.Contacts.DISPLAY_NAME + "=?";
        String[] selectionArgs = {display_name};
        return contentResolver.delete(contactsUri, where, selectionArgs);
    }

    /**
     * 根据电话号码删除联系人
     *
     * @param contentResolver
     * @param phoneNumber     电话号码
     * @return
     */
    public static int deleteByPhoneNumber(ContentResolver contentResolver, String phoneNumber) {
        int contactId = getContactIdByPhoneNumber(contentResolver, phoneNumber);
        String where = ContactsContract.Contacts._ID + "=?";
        String[] selectionArgs = {contactId + ""};
        return contentResolver.delete(contactsUri, where, selectionArgs);
    }

    /**
     * 删除全部联系人
     *
     * @param contentResolver
     */
    public static int deleteAll(ContentResolver contentResolver) {
        return contentResolver.delete(contactsUri, null, null);
    }

    /**
     * @param resolver
     * @return
     */
    @SuppressWarnings("unused")
    private static Uri insert(ContentResolver resolver) {

        // 插入raw_contacts表，并获取_id属性
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentValues values = new ContentValues();
        long contact_id = ContentUris.parseId(resolver.insert(uri, values));
        // 插入data表
        uri = Uri.parse("content://com.android.contacts/data");
        // add Name
        values.put("raw_contact_id", contact_id);
        values.put(Data.MIMETYPE, "vnd.android.cursor.item/name");
        values.put("data2", "zdong");
        values.put("data1", "xzdong");
        resolver.insert(uri, values);
        values.clear();
        // add Phone
        values.put("raw_contact_id", contact_id);
        values.put(Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
        values.put("data2", "2"); // 手机
        values.put("data1", "87654321");
        resolver.insert(uri, values);
        values.clear();
        // add email
        values.put("raw_contact_id", contact_id);
        values.put(Data.MIMETYPE, "vnd.android.cursor.item/email_v2");
        values.put("data2", "2"); // 单位
        values.put("data1", "xzdong@xzdong.com");
        resolver.insert(uri, values);

        return null;

    }

    private static Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    /**
     * 根据电话号码查找联系人名
     *
     * @param contentResolver
     * @param phoneNumber     要查找的电话号码
     * @return 联系人的姓名，如果没有则返回“未知”
     */
    public static String getContactNameByPhoneNumber(ContentResolver contentResolver, String phoneNumber) {
        String name = null;
        Cursor cursor = null;
        try {
            String phoneSelection = ContactsContract.CommonDataKinds.Phone.NUMBER + "=?";
            String[] phoneSelectionArgs = {phoneNumber}; // 查询参数
            cursor = contentResolver.query(phoneUri, null, phoneSelection, phoneSelectionArgs, null); // 查询全部手机号码
            String display_name = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;// 查询联系人姓名
            cursor.moveToFirst(); // 移动到第一条
            name = cursor.getString(cursor.getColumnIndex(display_name)); // 查询联系人姓名
            if (name == null) { // 如果没有找到联系人姓名
                name = "未知"; // 设置为未知
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return name;
    }

    /**
     * 根据电话号码查找联系人的id
     *
     * @param contentResolver
     * @param phoneNumber     要查找的电话号码
     * @return 联系人的id
     */
    public static int getContactIdByPhoneNumber(ContentResolver contentResolver, String phoneNumber) {
        int contact_id = -1;
        Cursor cursor = null;
        try {
            String phoneSelection = ContactsContract.CommonDataKinds.Phone.NUMBER + "=?";
            String[] phoneSelectionArgs = {phoneNumber}; // 查询参数
            cursor = contentResolver.query(phoneUri, null, phoneSelection, phoneSelectionArgs, null); // 查询全部手机号码
            String raw_contact_id = ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID;// 查询联系人姓名
            cursor.moveToFirst(); // 移动到第一条
            contact_id = cursor.getInt(cursor.getColumnIndex(raw_contact_id));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return contact_id;
    }

    /**
     * 根据lookUp_Key获得电话号码与内容类型
     *
     * @param contentResolver
     * @param lookUp_Key
     * @return "CONTENT_TYPE:13312341234,CONTENT_TYPE:13312341235";
     */
    public static String getAllPhoneNumbersByLookUpKey(ContentResolver contentResolver, String lookUp_Key) {
        // String result = "CONTENT_TYPE:13312341234,CONTENT_TYPE:13312341235";
        String allPhoneNo = "";
        Cursor cursor = null;
        try {
            String[] proj2 = {ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE};
            String selection = ContactsContract.Data.LOOKUP_KEY + "=?";
            String[] selectionArgs = {lookUp_Key};
            cursor = contentResolver.query(phoneUri, proj2, selection, selectionArgs, null);
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndex(proj2[0]));
                String content_type = cursor.getString(cursor.getColumnIndex(proj2[1]));
                allPhoneNo += content_type + ":" + number + ",";
            }
            if (allPhoneNo.length() > 0) {
                allPhoneNo = allPhoneNo.substring(1);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return allPhoneNo;
    }

}

// /~