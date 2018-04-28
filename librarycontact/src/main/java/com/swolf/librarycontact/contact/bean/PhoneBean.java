package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 电话
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class PhoneBean extends ContactIdBean implements Serializable {
	public String id;// 电话ID
	public String typeId;// 电话类型ID
	public String type;// 类型
	public String number;// 号码
	// public String affiliationId;// 归属地ID
	// public String affiliation;// 归属地
	// public String callSimId;// 呼叫sim卡ID
	// public String callSimName;// 呼叫sim卡名
	// public String callSimNumber;// 呼叫sim卡号码
	public String idBackup;// 电话ID备份
	public String typeIdBackup;// 电话类型ID备份
	public String typeBackup;// 类型备份
	public String numberBackup;// 号码备份

	public boolean isUpdate() {
		if (!TextUtils.equals(typeId, typeIdBackup) || !TextUtils.equals(number, numberBackup)) {
			return true;
		} else {
			return false;
		}
	}
}

// /:~