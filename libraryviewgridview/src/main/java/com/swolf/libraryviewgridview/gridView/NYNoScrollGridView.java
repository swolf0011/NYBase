package com.swolf.libraryviewgridview.gridView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 无滚动条GridView
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYNoScrollGridView extends GridView {
	public NYNoScrollGridView(Context context) {
		super(context);

	}

	public NYNoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
