package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 邮箱
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class EmailBean extends ContactIdBean implements Serializable {
	public String id;// 邮箱ID
	public String typeId;// 邮箱类型ID
	public String type;// 邮箱类型
	public String email;// 邮箱
	public String idBackup;// 邮箱ID备份
	public String typeIdBackup;// 邮箱类型ID备份
	public String typeBackup;// 邮箱类型备份
	public String emailBackup;// 邮箱备份
	public boolean isUpdate() {
		if (!TextUtils.equals(typeId, typeIdBackup) || !TextUtils.equals(email, emailBackup)) {
			return true;
		} else {
			return false;
		}
	}
}

// /:~