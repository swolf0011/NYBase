package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 网站
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class WebsiteBean extends ContactIdBean implements Serializable {
	public String id;// 网站ID
	public String typeId;// 网站类型ID
	public String type;// 网站类型
	public String website;// 网站
	public String idBackup;// 网站ID备份
	public String typeIdBackup;// 网站类型ID备份
	public String typeBackup;// 网站类型备份
	public String websiteBackup;// 网站备份

	public boolean isUpdate() {
		if (!TextUtils.equals(typeId, typeIdBackup) || !TextUtils.equals(website, websiteBackup)) {
			return true;
		} else {
			return false;
		}
	}
}

// /:~