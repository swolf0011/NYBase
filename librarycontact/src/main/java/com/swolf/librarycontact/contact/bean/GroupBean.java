package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

/**
 * 分组
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class GroupBean extends ContactIdBean implements Serializable {
	public String id;// 分组ID
	public String title;// 分组标题
	public String idBackup;// 分组ID备份
	public String titleBackup;// 分组标题备份

	public int count;// 人数
}

// /:~