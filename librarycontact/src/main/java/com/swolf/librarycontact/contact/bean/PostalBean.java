package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 邮政
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class PostalBean extends ContactIdBean implements Serializable {
	public String id;// 邮政ID
	public String typeId;// 邮政类型ID
	public String type;// 邮政类型
	public String street;// 街道
	public String pobox;// 邮政信箱
	public String neighborhood;// 街区
	public String city;// 城市
	public String state;// 省/直辖市/自治区
	public String zipCode;// 邮政编码
	public String country;// 国家/地区
	public String idBackup;// 邮政IDBackup
	public String typeIdBackup;// 邮政类型IDBackup
	public String typeBackup;// 邮政类型Backup
	public String streetBackup;// 街道Backup
	public String poboxBackup;// 邮政信箱Backup
	public String neighborhoodBackup;// 街区Backup
	public String cityBackup;// 城市Backup
	public String stateBackup;// 省/直辖市/自治区Backup
	public String zipCodeBackup;// 邮政编码Backup
	public String countryBackup;// 国家/地区Backup

	public boolean isUpdate() {
		if (!TextUtils.equals(typeId, typeIdBackup) || !TextUtils.equals(street, streetBackup)
				|| !TextUtils.equals(pobox, poboxBackup) || !TextUtils.equals(neighborhood, neighborhoodBackup)
				|| !TextUtils.equals(city, cityBackup) || !TextUtils.equals(state, stateBackup)
				|| !TextUtils.equals(zipCode, zipCodeBackup) || !TextUtils.equals(country, countryBackup)) {
			return true;
		} else {
			return false;
		}
	}

}

// /:~