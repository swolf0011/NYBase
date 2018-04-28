package com.swolf.librarycontact.contact.contactResolver;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Groups;
import android.text.TextUtils;

import com.swolf.librarycontact.contact.bean.ContactBean;
import com.swolf.librarycontact.contact.bean.GroupBean;


/**
 * 分组提供者
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class GroupResolver {
	private Context context;
	private ContentResolver cr;

	public GroupResolver(Context context) {
		this.context = context;
		this.cr = context.getContentResolver();
	}

	public GroupBean getUnGroup() {
		int count = 0;
		ContactResolver cr = new ContactResolver(context);
		ArrayList<ContactBean> allList = cr.getAllContact();
		GroupMembershipResolver gmr = new GroupMembershipResolver(context);
		String rawContactIds = gmr.getAllGroupExistsRawContactIds();
		int size = allList.size();
		if (TextUtils.isEmpty(rawContactIds)) {
			count = size;
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
					count++;
				}
			}
		}
		allList.clear();
		allList = null;

		GroupBean group = new GroupBean();
		group.id = "-1";
		group.title = "not set";
		group.count = count;
		return group;
	}

	public ArrayList<GroupBean> getAllGroup() {
		ArrayList<GroupBean> list = new ArrayList<GroupBean>();
		list.add(getUnGroup());
		String[] projection = new String[] { Groups._ID, Groups.TITLE, Groups._COUNT };
		String selection = Groups.DELETED + "=?";
		String[] selectionArgs = { "0" };
		Cursor cursor = cr.query(Groups.CONTENT_URI, projection, selection, selectionArgs, null);
		if (cursor.moveToFirst()) {
			do {
				GroupBean group = new GroupBean();
				group.id = cursor.getString(cursor.getColumnIndex(Groups._ID));
				group.title = cursor.getString(cursor.getColumnIndex(Groups.TITLE));
				group.count = cursor.getInt(cursor.getColumnIndex(Groups._COUNT));
				list.add(group);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return list;
	}

	public GroupBean getGroupByGroupId(String id) {
		GroupBean group = null;
		Uri uri = ContentUris.withAppendedId(Groups.CONTENT_URI, Long.parseLong(id));
		String[] projection = { Groups._ID, Groups.TITLE, Groups._COUNT };
		Cursor cursor = cr.query(uri, projection, null, null, null);
		if (cursor.moveToFirst()) {
			group = new GroupBean();
			group.id = cursor.getString(cursor.getColumnIndex(Groups._ID));
			group.title = cursor.getString(cursor.getColumnIndex(Groups.TITLE));
			group.count = cursor.getInt(cursor.getColumnIndex(Groups._COUNT));
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return group;
	}

	public GroupBean getGroupByTitle(String title) {
		GroupBean group = null;
		String[] projection = new String[] { Groups._ID, Groups._COUNT };
		String selection = Groups.DELETED + "=? and " + Groups.TITLE + "=?";
		String[] selectionArgs = { "0", title };
		Cursor cursor = cr.query(Groups.CONTENT_URI, projection, selection, selectionArgs, null);
		if (cursor.moveToFirst()) {
			group = new GroupBean();
			group.id = cursor.getString(cursor.getColumnIndex(Groups._ID));
			group.title = title;
			group.count = cursor.getInt(cursor.getColumnIndex(Groups._COUNT));
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return group;
	}

	public GroupBean addGroup(GroupBean group) {
		GroupBean groupResult = getGroupByTitle(group.title);
		if (groupResult == null) {
			ContentValues cv = new ContentValues();
			cv.put(Groups.TITLE, group.title);
			Uri uri = cr.insert(Groups.CONTENT_URI, cv);
			if (uri != null) {
				groupResult = new GroupBean();
				long id = ContentUris.parseId(uri);
				groupResult.id = id + "";
				groupResult.title = group.title;
				groupResult.count = 0;
			}
		}
		return groupResult;
	}

	public GroupBean updateGroupByGroupId(GroupBean group) {
		Uri uri = ContentUris.withAppendedId(Groups.CONTENT_URI, Long.parseLong(group.id));
		ContentValues cv = new ContentValues();
		cv.put(Groups.TITLE, group.title);
		int i = cr.update(uri, cv, null, null);
		if (i == 1) {
			group = getGroupByGroupId(group.id);
		}
		return group;
	}

	public void deleteGroupByGroupId(String id) {
		GroupMembershipResolver gmr = new GroupMembershipResolver(context);
		gmr.deleteGroupMembershipByGroupId(id);
		Uri uri = ContentUris.withAppendedId(Groups.CONTENT_URI, Long.parseLong(id));
		cr.delete(uri, null, null);
	}

	public void deleteGroupByGroupIds(String ids) {
		GroupMembershipResolver gmr = new GroupMembershipResolver(context);
		gmr.deleteGroupMembershipByGroupIds(ids);
		String selection = Groups._ID + " in (?)";
		String[] selectionArgs = { ids };
		cr.delete(Groups.CONTENT_URI, selection, selectionArgs);
	}
}

// /:~