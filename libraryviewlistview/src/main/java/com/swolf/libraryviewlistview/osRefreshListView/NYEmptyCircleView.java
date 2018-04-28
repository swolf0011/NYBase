package com.swolf.libraryviewlistview.osRefreshListView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


/**
 * 空圆View
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYEmptyCircleView extends View {
    private Paint mTopPaint, mBottomPaint;
    private RectF mOval;
    private float mTopStartAngle = 188;
    private float mBottomStartAngle = 8;
    private float mSweepAngle = 0;

    private float SWEEP_INC = 2;
    private static final float MAX_SWEEP_ANGLE = 164;

    public NYEmptyCircleView(Context context) {
        this(context, null);
    }

    public NYEmptyCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NYEmptyCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void init() {
        mTopPaint = new Paint();
        mTopPaint.setAntiAlias(true);
        mTopPaint.setStyle(Paint.Style.STROKE);
        mTopPaint.setStrokeWidth(2);
        mTopPaint.setColor(Color.RED);

        mBottomPaint = new Paint(mTopPaint);

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int width = (int) (25 * scale + 0.5f);
        mOval = new RectF(2, 2, width - 2, width - 2);
    }

    public void setActualAngle(float actualAngle) {
        if (0 == actualAngle) {
            return;
        }

        SWEEP_INC = MAX_SWEEP_ANGLE / actualAngle;
    }

    public void initSweepAngle() {
        mSweepAngle = 0;
    }

    private void drawArcs(Canvas canvas) {
        canvas.drawArc(mOval, mTopStartAngle, mSweepAngle, false, mTopPaint);
        canvas.drawArc(mOval, mBottomStartAngle, mSweepAngle, false, mBottomPaint);
    }

    public void updateDrawAngle(float angle) {
        mSweepAngle = angle * SWEEP_INC;

        if (mSweepAngle < 0) {
            mSweepAngle = 0;
        }

        if (mSweepAngle > MAX_SWEEP_ANGLE) {
            mSweepAngle = MAX_SWEEP_ANGLE;
        }

        invalidate();
    }

    public void increaseDrawAngle() {
        mSweepAngle += SWEEP_INC;
        if (mSweepAngle > MAX_SWEEP_ANGLE) {
            mSweepAngle = MAX_SWEEP_ANGLE;
            return;
        }

        invalidate();
    }

    public void decreaseDrawAngle() {
        mSweepAngle -= SWEEP_INC;
        if (mSweepAngle < 0) {
            mSweepAngle = 0;
            return;
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);

        drawArcs(canvas);

        super.onDraw(canvas);
    }
}
