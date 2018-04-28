package com.swolf.libraryviewwheelview.wheelview;

/**
 * WheelView适配器
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public interface WheelAdapter {
    /**
     * Gets items count
     *
     * @return the count of wheel items
     */
    public int getItemsCount();

    /**
     * Gets a wheel item by index.
     *
     * @param index the item index
     * @return the wheel item text or null
     */
    public String getItem(int index);

    /**
     * Gets maximum item length. It is used to determine the wheel width.
     * If -1 is returned there will be used the default wheel width.
     *
     * @return the maximum item length or -1
     */
    public int getMaximumLength();
}
