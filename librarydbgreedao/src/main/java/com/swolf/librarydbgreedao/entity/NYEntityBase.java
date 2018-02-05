package com.swolf.librarydbgreedao.entity;


import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * 实体基类
 * Created by LiuYi-15973602714
 */
@SuppressWarnings("serial")
public class NYEntityBase implements Serializable {
    @Id(autoincrement = true)
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
