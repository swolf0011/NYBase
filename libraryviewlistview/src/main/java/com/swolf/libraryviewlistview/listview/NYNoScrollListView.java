package com.swolf.libraryviewlistview.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 无滚动条ListView
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYNoScrollListView extends ListView {

    public NYNoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
