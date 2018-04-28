package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 名字
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class NameBean extends ContactIdBean implements Serializable {
	public String id;// 名字ID
	public String firstName;// 第1的名字
	public String lastName;// 最后的名字
	public String middleName;// 中间的名字
	public String displayName;// 显示的名字只是用来获取值
	public String pinyinAll;// 全部拼音
	public String pinyinInitial;// 首字母拼音
	public String key;// 首字拼音首字母大写

	public String idBackup;// 名字ID备份
	public String firstNameBackup;// 第1的名字备份
	public String lastNameBackup;// 最后的名字备份
	public String middleNameBackup;// 中间的名字备份
	public String displayNameBackup;// 显示的名字备份

	public boolean isUpdate() {
		if (!TextUtils.equals(firstName, firstNameBackup) || !TextUtils.equals(lastName, lastNameBackup)
				|| !TextUtils.equals(middleName, middleNameBackup)) {
			return true;
		} else {
			return false;
		}
	}

}

// /:~