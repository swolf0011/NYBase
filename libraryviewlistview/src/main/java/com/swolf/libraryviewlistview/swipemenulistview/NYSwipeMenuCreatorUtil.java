package com.swolf.libraryviewlistview.swipemenulistview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;

/**
 * 滑动菜单创建工具
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYSwipeMenuCreatorUtil {

    public static NYSwipeMenuCreator creator(final Context context, final NYSwipeMenuItem... items) {
        NYSwipeMenuCreator creator = new NYSwipeMenuCreator() {
            @Override
            public void create(NYSwipeMenu menu) {
                for (NYSwipeMenuItem item : items) {
                    menu.addMenuItem(item);
                }
            }
        };
        return creator;
    }

    public static NYSwipeMenuItem newSwipeMenuItem(Context context, int backgroundColor, String title, int titleSize, int titleColor, int icon) {
        NYSwipeMenuItem item = new NYSwipeMenuItem(context);
        item.setBackground(new ColorDrawable(backgroundColor));
        item.setWidth(dp2px(context, 90));
        item.setTitle(title);
        item.setTitleSize(titleSize);
        item.setTitleColor(titleColor);
        if (icon > 0) {
            item.setIcon(icon);
        }
        return item;
    }

    private static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
