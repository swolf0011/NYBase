package com.swolf.librarycontact.contact.contactResolver;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.Data;
import android.text.TextUtils;

import com.swolf.librarycontact.contact.bean.GroupMembershipBean;

import java.util.ArrayList;


/**
 * 分组与联系人关系提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class GroupMembershipResolver {
    private Context context;
    private ContentResolver cr;

    public GroupMembershipResolver(Context context) {
        this.context = context;
        this.cr = context.getContentResolver();
    }

    public void deleteGroupMembershipByGroupId(String id) {
        String selection = GroupMembership.GROUP_ROW_ID + " = ?";
        String[] selectionArgs = {id};
        cr.delete(Data.CONTENT_URI, selection, selectionArgs);
    }

    public void deleteGroupMembershipByGroupIds(String ids) {
        String selection = GroupMembership.GROUP_ROW_ID + " in ?";
        String[] selectionArgs = {ids};
        cr.delete(Data.CONTENT_URI, selection, selectionArgs);
    }

    public void deleteGroupMembershipByContactIds(String rawContactIds) {
        String selection = GroupMembership.RAW_CONTACT_ID + " in (?)";
        String[] selectionArgs = {rawContactIds};
        cr.delete(Data.CONTENT_URI, selection, selectionArgs);
    }

    public void addGroupMembership(GroupMembershipBean groupMembership) {
        String rawContactId_return = getRawContactIdByRawContactIdGroupId(groupMembership.rawContactId,
                groupMembership.groupRawId);
        if (TextUtils.isEmpty(rawContactId_return)) {
            ContentValues cv = new ContentValues();
            cv.put(GroupMembership.RAW_CONTACT_ID, groupMembership.rawContactId);
            cv.put(GroupMembership.GROUP_ROW_ID, groupMembership.groupRawId);
            cv.put(GroupMembership.MIMETYPE, GroupMembership.CONTENT_ITEM_TYPE);
            cr.insert(Data.CONTENT_URI, cv);
        }
    }

    public void updateGroupMembershipByContactIds(GroupMembershipBean groupMembership) {
        String selection = GroupMembership._ID + " = (?)";
        String[] selectionArgs = {groupMembership.id};
        ContentValues cv = new ContentValues();
        cv.put(GroupMembership.RAW_CONTACT_ID, groupMembership.rawContactId);
        cv.put(GroupMembership.GROUP_ROW_ID, groupMembership.groupRawId);
        cv.put(GroupMembership.MIMETYPE, GroupMembership.CONTENT_ITEM_TYPE);
        cr.update(Data.CONTENT_URI, cv, selection, selectionArgs);
    }

    public ArrayList<GroupMembershipBean> getGroupMembershipByRawContactId(String rawContactId) {
        ArrayList<GroupMembershipBean> list = new ArrayList<GroupMembershipBean>();
        String[] projection = {GroupMembership._ID, GroupMembership.RAW_CONTACT_ID, GroupMembership.GROUP_ROW_ID};
        String selection = GroupMembership.RAW_CONTACT_ID + " = ?";
        String[] selectionArgs = {rawContactId};
        Cursor cursor = cr.query(Data.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            GroupResolver gr = new GroupResolver(context);
            do {
                GroupMembershipBean groupMembership = new GroupMembershipBean();
                groupMembership.id = cursor.getString(cursor.getColumnIndex(GroupMembership._ID));
                groupMembership.rawContactId = cursor.getString(cursor.getColumnIndex(GroupMembership.RAW_CONTACT_ID));
                groupMembership.groupRawId = rawContactId;
                groupMembership.groupTitle = gr.getGroupByGroupId(groupMembership.groupRawId).title;

                groupMembership.idBackup = groupMembership.id;
                groupMembership.rawContactIdBackup = groupMembership.rawContactId;
                groupMembership.groupRawIdBackup = groupMembership.groupRawId;
                groupMembership.groupTitleBackup = groupMembership.groupTitle;

                list.add(groupMembership);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    public ArrayList<GroupMembershipBean> getGroupMembershipByGroupId(String groupId) {
        ArrayList<GroupMembershipBean> list = new ArrayList<GroupMembershipBean>();
        String[] projection = {GroupMembership._ID, GroupMembership.RAW_CONTACT_ID, GroupMembership.GROUP_ROW_ID};
        String selection = GroupMembership.GROUP_ROW_ID + " = ?";
        String[] selectionArgs = {groupId};
        Cursor cursor = cr.query(Data.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            GroupResolver gr = new GroupResolver(context);
            do {
                GroupMembershipBean groupMembership = new GroupMembershipBean();
                groupMembership.id = cursor.getString(cursor.getColumnIndex(GroupMembership._ID));
                groupMembership.rawContactId = cursor.getString(cursor.getColumnIndex(GroupMembership.RAW_CONTACT_ID));
                groupMembership.groupRawId = groupId;
                groupMembership.groupTitle = gr.getGroupByGroupId(groupMembership.groupRawId).title;

                groupMembership.idBackup = groupMembership.id;
                groupMembership.rawContactIdBackup = groupMembership.rawContactId;
                groupMembership.groupRawIdBackup = groupMembership.groupRawId;
                groupMembership.groupTitleBackup = groupMembership.groupTitle;

                list.add(groupMembership);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return list;
    }

    private String getRawContactIdByRawContactIdGroupId(String rawContactId, String groupId) {
        String rawContactId_return = null;
        String[] projection = {GroupMembership.RAW_CONTACT_ID};
        String selection = GroupMembership.RAW_CONTACT_ID + " = ? and " + GroupMembership.GROUP_ROW_ID + " = ? ";
        String[] selectionArgs = {rawContactId, groupId};
        Cursor cursor = cr.query(Data.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            rawContactId_return = cursor.getString(cursor.getColumnIndex(GroupMembership.RAW_CONTACT_ID));
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return rawContactId_return;
    }

    public String getAllGroupExistsRawContactIds() {
        String rawContactIds = null;
        String[] projection = {GroupMembership.RAW_CONTACT_ID};
        Cursor cursor = cr.query(Data.CONTENT_URI, projection, null, null, null);
        if (cursor.moveToFirst()) {
            StringBuffer sb = new StringBuffer();
            do {
                String rawContactId = cursor.getString(cursor.getColumnIndex(GroupMembership.RAW_CONTACT_ID));
                sb.append(rawContactId);
                sb.append(",");
            } while (cursor.moveToNext());
            if (sb.length() > 1) {
                rawContactIds = sb.substring(0, sb.length() - 1);
            }
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return rawContactIds;
    }

}

// /:~