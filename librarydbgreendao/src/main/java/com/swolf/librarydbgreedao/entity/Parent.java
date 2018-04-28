package com.swolf.librarydbgreedao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * Parent
 * Created by LiuYi-15973602714
 */
@Entity
public class Parent extends NYEntityBase {

    @Property
    @NotNull
    public String name;
    @Property
    public String email;
    @Property
    public boolean isAdmin;
    @Property
    public String time;

    @Property
    private String date;

    @Generated(hash = 1683225693)
    public Parent(@NotNull String name, String email, boolean isAdmin, String time,
                  String date) {
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
        this.time = time;
        this.date = date;
    }

    @Generated(hash = 981245553)
    public Parent() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
