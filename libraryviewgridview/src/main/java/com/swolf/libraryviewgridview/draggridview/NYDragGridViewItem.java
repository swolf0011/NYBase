package com.swolf.libraryviewgridview.draggridview;

import java.io.Serializable;

/**
 * 可移动GridViewItem
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class NYDragGridViewItem implements Serializable {

    public int _id;
    public String name;
    public String url;
    public String action;
    public String icon;
    public EDragItemType type = EDragItemType.NATIVE;

    public enum EDragItemType {
        HTML, NATIVE;
    }

}