package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 分组与联系人关系
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class GroupMembershipBean extends ContactIdBean implements Serializable {
	public String id;// 组成员关系ID
	public String rawContactId;// 联系人rawId
	public String groupRawId;// 分组ID
	public String groupTitle;// 分组名

	public String idBackup;// 组成员关系ID备份
	public String rawContactIdBackup;// 联系人rawId备份
	public String groupRawIdBackup;// 分组ID备份
	public String groupTitleBackup;// 分组名备份

	public boolean isUpdate() {
		if (!TextUtils.equals(rawContactId, rawContactIdBackup) || !TextUtils.equals(groupRawId, groupRawIdBackup)) {
			return true;
		} else {
			return false;
		}
	}
}

// /:~