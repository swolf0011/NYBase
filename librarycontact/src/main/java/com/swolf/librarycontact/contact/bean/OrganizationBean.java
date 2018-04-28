package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 组织
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class OrganizationBean extends ContactIdBean implements Serializable {
	public String id;// 组织ID
	public String typeId;// 组织类型ID
	public String type;// 组织类型
	public String company;// 公司
	public String title;// 职务
	public String idBackup;// 组织ID备份
	public String typeIdBackup;// 组织类型ID备份
	public String typeBackup;// 组织类型备份
	public String companyBackup;// 公司备份
	public String titleBackup;// 职务备份

	public boolean isUpdate() {
		if (!TextUtils.equals(typeId, typeIdBackup) || !TextUtils.equals(company, companyBackup)
				|| !TextUtils.equals(title, titleBackup)) {
			return true;
		} else {
			return false;
		}
	}
}

// /:~