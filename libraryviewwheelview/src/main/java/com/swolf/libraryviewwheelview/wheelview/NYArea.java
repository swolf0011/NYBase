package com.swolf.libraryviewwheelview.wheelview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NYArea implements Serializable{
    public long code;//编号
    public String name;//名字
    public List<NYArea> subs = new ArrayList<>();//下级地区

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NYArea> getSubs() {
        return subs;
    }

    public void setSubs(List<NYArea> subs) {
        this.subs = subs;
    }
}
