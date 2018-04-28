package com.swolf.libraryviewlistview.listview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ListView;

import com.swolf.libraryviewlistview.R;

/**
 * 圆角ListView
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYRoundListView extends ListView {
    private float mCornerRadius;
    private Path mClipPath;

    public NYRoundListView(Context context) {
        this(context, null);
    }

    public NYRoundListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NYRoundListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 圆角
        mCornerRadius = context.getResources().getDimensionPixelSize(R.dimen.dp20);
    }

    private void method(Canvas canvas) {
        final int width = canvas.getWidth();
        final int height = canvas.getHeight();
        if (mClipPath == null) {
            mClipPath = new Path();
            // Path.Direction.CW顺时针方向
            mClipPath.addRoundRect(new RectF(0, 0, width, height), mCornerRadius, mCornerRadius, Path.Direction.CW);
        }
        canvas.save();
        canvas.clipPath(mClipPath);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        method(canvas);
    }
}
