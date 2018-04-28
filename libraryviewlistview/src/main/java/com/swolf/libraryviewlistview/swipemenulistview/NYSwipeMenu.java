package com.swolf.libraryviewlistview.swipemenulistview;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动菜单
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYSwipeMenu {

    private Context mContext;
    private List<NYSwipeMenuItem> mItems;
    private int mViewType;

    public NYSwipeMenu(Context context) {
        mContext = context;
        mItems = new ArrayList<NYSwipeMenuItem>();
    }

    public Context getContext() {
        return mContext;
    }

    public void addMenuItem(NYSwipeMenuItem item) {
        mItems.add(item);
    }

    public void removeMenuItem(NYSwipeMenuItem item) {
        mItems.remove(item);
    }

    public List<NYSwipeMenuItem> getMenuItems() {
        return mItems;
    }

    public NYSwipeMenuItem getMenuItem(int index) {
        return mItems.get(index);
    }

    public int getViewType() {
        return mViewType;
    }

    public void setViewType(int viewType) {
        this.mViewType = viewType;
    }

}
