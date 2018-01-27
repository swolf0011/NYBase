package com.swolf.librarybase.nyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.swolf.librarybase.R;

/**
 * 标题
 * xmlns:nyapp="http://schemas.android.com/apk/res-auto
 * Created by LiuYi-15973602714
 */
public class NYTitleView extends LinearLayout {

    int imageView_left_resource;
    String textView_left_text;
    float textView_left_textsize;
    int textView_left_textcolor;
    String textView_title_text;
    float textView_title_textsize;
    int textView_title_textcolor;
    String textView_right_text;
    float textView_right_textsize;
    int textView_right_textcolor;
    int imageView_right_resource;

    public ImageView imageViewLeft;
    public TextView textViewLeft;
    public TextView textViewTitle;
    public TextView textViewRight;
    public ImageView imageViewRight;

    Paint paint;

    public NYTitleView(Context context) {
        this(context, null);
    }

    public NYTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NYTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }


    public NYTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.ny_view_title, this);
        imageViewLeft = (ImageView) findViewById(R.id.imageViewLeft);
        textViewLeft = (TextView) findViewById(R.id.textViewLeft);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewRight = (TextView) findViewById(R.id.textViewRight);
        imageViewRight = (ImageView) findViewById(R.id.imageViewRight);
    }
    public NYTitleView setImageViewLeft(int resource){
        imageViewLeft.setImageResource(resource);
        return this;
    }
    public NYTitleView setImageViewLeft(int padding,int resource){
        imageViewLeft.setPadding(padding, padding, padding, padding);
        imageViewLeft.setImageResource(resource);
        return this;
    }
    public NYTitleView setTextViewLeft(String text){
        textViewLeft.setText(text);
        return this;
    }
    public NYTitleView setTextViewLeft(int textColor,String text){
        textViewLeft.setTextColor(textColor);
        textViewLeft.setText(text);
        return this;
    }
    public NYTitleView setTextViewLeft(int textColor,String text,float textSize){
        textViewLeft.setTextColor(textColor);
        textViewLeft.setText(text);
        textViewLeft.setTextSize(textSize);
        return this;
    }
    public NYTitleView setTextViewTitle(String text){
        textViewTitle.setText(text);
        return this;
    }
    public NYTitleView setTextViewTitle(int textColor,String text){
        textViewTitle.setTextColor(textColor);
        textViewTitle.setText(text);
        return this;
    }
    public NYTitleView setTextViewTitle(int textColor,String text,float textSize){
        textViewTitle.setTextColor(textColor);
        textViewTitle.setText(text);
        textViewTitle.setTextSize(textSize);
        return this;
    }
    public NYTitleView setTextViewRight(String text){
        textViewRight.setText(text);
        return this;
    }
    public NYTitleView setTextViewRight(int textColor,String text){
        textViewRight.setTextColor(textColor);
        textViewRight.setText(text);
        return this;
    }
    public NYTitleView setTextViewRight(int textColor,String text,float textSize){
        textViewRight.setTextColor(textColor);
        textViewRight.setText(text);
        textViewRight.setTextSize(textSize);
        return this;
    }
    public NYTitleView setImageViewRight(int resource){
        imageViewRight.setImageResource(resource);
        return this;
    }
    public NYTitleView setImageViewRight(int padding,int resource){
        imageViewRight.setPadding(padding, padding, padding, padding);
        imageViewRight.setImageResource(resource);
        return this;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }







    public NYTitleView setSubViewsVisibility(
            int imageViewLeftVisibility,
            int textViewLeftVisibility,
            int textViewRightVisibility,
            int imageViewRightVisibility
    ) {
        imageViewLeft.setVisibility(imageViewLeftVisibility);
        textViewLeft.setVisibility(textViewLeftVisibility);
        textViewRight.setVisibility(textViewRightVisibility);
        imageViewRight.setVisibility(imageViewRightVisibility);
        return this;
    }



    public NYTitleView setImageViewLeftClickListener(View.OnClickListener clickListener) {
        imageViewLeft.setOnClickListener(clickListener);
        return this;
    }
    public NYTitleView setTextViewLeftClickListener(View.OnClickListener clickListener) {
        textViewLeft.setOnClickListener(clickListener);
        return this;
    }
    public NYTitleView setTextViewTitleClickListener(View.OnClickListener clickListener) {
        textViewTitle.setOnClickListener(clickListener);
        return this;
    }
    public NYTitleView setTextViewRightClickListener(View.OnClickListener clickListener) {
        textViewRight.setOnClickListener(clickListener);
        return this;
    }

    public NYTitleView setImageViewRightClickListener(View.OnClickListener clickListener) {
        imageViewRight.setOnClickListener(clickListener);
        return this;
    }
}
