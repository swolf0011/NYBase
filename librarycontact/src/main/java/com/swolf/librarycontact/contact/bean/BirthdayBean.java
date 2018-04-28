package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 生日
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class BirthdayBean extends ContactIdBean implements Serializable {
    public String id;// 生日ID
    public String typeId;// 类型ID
    public String birthday;// 生日
    public String idBackup;// 生日ID备份
    public String typeIdBackup;// 类型ID备份
    public String birthdayBackup;// 生日备份

    public boolean isUpdate() {
        if (TextUtils.equals(birthday, birthdayBackup)) {
            return false;
        } else {
            return true;
        }
    }

}

// /:~