package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * im
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class ImBean extends ContactIdBean implements Serializable {
	public String id;// imID
	public String typeId;// im类型ID
	public String type;// im类型
	public String im;// im
	public String idBackup;// imID备份
	public String typeIdBackup;// im类型ID备份
	public String typeBackup;// im类型备份
	public String imBackup;// im备份

	public boolean isUpdate() {
		if (!TextUtils.equals(typeId, typeIdBackup) || !TextUtils.equals(im, imBackup)) {
			return true;
		} else {
			return false;
		}
	}
}

// /:~