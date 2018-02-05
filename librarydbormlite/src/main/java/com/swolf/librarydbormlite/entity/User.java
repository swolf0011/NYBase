package com.swolf.librarydbormlite.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by LiuYi-15973602714
 */
@DatabaseTable(tableName = "User")
public class User extends NYEntityBase{
    @DatabaseField(columnName = "userName")
    public String userName ="";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +"id=" + id +", userName='" + userName + '\'' +'}';
    }
}
