package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 昵称
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class NicknameBean extends ContactIdBean implements Serializable {
	public String id;// 昵称ID
	public String nickname;// 昵称
	public String idBackup;// 昵称ID备份
	public String nicknameBackup;// 昵称备份

	public boolean isUpdate() {
		if (TextUtils.equals(nickname, nicknameBackup)) {
			return false;
		} else {
			return true;
		}
	}
}

// /:~