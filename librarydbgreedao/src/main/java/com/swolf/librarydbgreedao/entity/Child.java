package com.swolf.librarydbgreedao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Child
 * Created by LiuYi-15973602714
 */
@Entity
public class Child extends NYEntityBase {

//    @Property：设置一个非默认关系映射所对应的列名，默认是使用字段名，例如：
//@Property(nameInDb = "USERNAME")
//private String name;
//    @NotNull：设置数据库表当前列不能为空
//    @Transient：添加此标记后不会生成数据库表的列
//    @Unique：向数据库添加了一个唯一的约束
//    @Index：使用@Index作为一个属性来创建一个索引，通过name设置索引别名，也可以通过unique给索引添加约束
    @Property @NotNull
    public String name;

    @Transient
    public String email;

    @Generated(hash = 700376886)
    public Child(@NotNull String name) {
        this.name = name;
    }

    @Generated(hash = 891984724)
    public Child() {
    }

//    @ToOne(joinProperty = "parentId")
//    public Parent parent; // 外键表id

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Parent getParent() {
//        return parent;
//    }
//
//    public void setParent(Parent parent) {
//        this.parent = parent;
//    }
}
