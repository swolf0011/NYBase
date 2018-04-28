package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.net.Uri;
import android.text.TextUtils;

/**
 * 铃声
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class RingBean extends ContactIdBean implements Serializable {
	public Uri uri;// 铃声uri
	public String uriStr;// 铃声uriStr
	public String ringName;// 铃声名
	public Uri uriBackup;// 铃声uri备份
	public String uriStrBackup;// 铃声uriStr备份
	public String ringNameBackup;// 铃声名备份

	public boolean isUpdate() {
		if (uri != uriBackup || !TextUtils.equals(uriStr, uriStrBackup) || !TextUtils.equals(ringName, ringNameBackup)) {
			return true;
		} else {
			return false;
		}
	}
}

// /:~