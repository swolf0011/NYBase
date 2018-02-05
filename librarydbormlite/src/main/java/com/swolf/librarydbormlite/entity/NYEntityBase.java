package com.swolf.librarydbormlite.entity;


import com.j256.ormlite.field.DatabaseField;
import java.io.Serializable;

/**
 * 实体基类
 * Created by LiuYi-15973602714
 */
@SuppressWarnings("serial")
public class NYEntityBase implements Serializable {
    @DatabaseField(generatedId = true)
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
