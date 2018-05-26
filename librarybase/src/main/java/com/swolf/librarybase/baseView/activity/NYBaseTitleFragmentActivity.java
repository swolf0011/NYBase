package com.swolf.librarybase.baseView.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swolf.librarybase.R;

/**
 * FragmentActivity基类
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYBaseTitleFragmentActivity extends FragmentActivity {
    private static final String tag = "NYBaseTitleFragmentActivity";
    /**
     * 本Activity
     */
    public NYBaseTitleFragmentActivity activity;


    public LinearLayout linearLayout_title;
    public ImageView imageView_left;
    public TextView textView_left;
    public TextView textView_title;
    public TextView textView_right;
    public ImageView imageView_right;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        super.requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题
//        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//        WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏显示
//        super.getWindow().addFlags(
//        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 高亮显示

        activity = this;
        Log.i(tag, "onCreate----" + activity.getClass().getSimpleName());
    }


    /**
     * @param title_bgColor              TitleBgColor资源文件
     * @param title_bgResource           TitleBgResource资源文件
     * @param imageView_left_resource    左边图片资源文件
     * @param textView_left_text         左边文本
     * @param title_text                 标题
     * @param textView_right_text        右边文本
     * @param imageView_right_resource   右边图片资源文件
     * @param imageView_left_Visibility  左边图片是否可见， View.VISIBLE
     * @param textView_left_Visibility   左边文本是否可见， View.VISIBLE
     * @param textView_right_Visibility  右边文本是否可见， View.VISIBLE
     * @param imageView_right_Visibility 右边图片是否可见， View.VISIBLE
     * @param imageView_left_click       左边图片点击事件
     * @param textView_left_click        左边文本点击事件
     * @param textView_right_click       右边文本点击事件
     * @param imageView_right_click      右边图片点击事件
     */
    public void initTitleView(
            int title_bgColor,
            int title_bgResource,
            int imageView_left_resource,
            String textView_left_text,
            String title_text,
            String textView_right_text,
            int imageView_right_resource,
            int imageView_left_Visibility,
            int textView_left_Visibility,
            int textView_right_Visibility,
            int imageView_right_Visibility,
            View.OnClickListener imageView_left_click,
            View.OnClickListener textView_left_click,
            View.OnClickListener textView_right_click,
            View.OnClickListener imageView_right_click) {

        linearLayout_title = (LinearLayout) findViewById(R.id.linearLayoutTitle);

        imageView_left = (ImageView) findViewById(R.id.imageViewLeft);
        textView_left = (TextView) findViewById(R.id.textViewLeft);
        textView_title = (TextView) findViewById(R.id.textViewTitle);
        textView_right = (TextView) findViewById(R.id.textViewRight);
        imageView_right = (ImageView) findViewById(R.id.imageViewRight);

        if (title_bgColor > 0) {
            linearLayout_title.setBackgroundColor(title_bgColor);
        } else if (title_bgResource > 0) {
            linearLayout_title.setBackgroundResource(title_bgResource);
        }

        if (imageView_left_resource > 0) {
            imageView_left.setImageResource(imageView_left_resource);
        }
        textView_left.setText(textView_left_text);
        textView_title.setText(title_text);
        textView_right.setText(textView_right_text);
        if (imageView_right_resource > 0) {
            imageView_right.setImageResource(imageView_right_resource);
        }

        imageView_left.setVisibility(imageView_left_Visibility);
        textView_left.setVisibility(textView_left_Visibility);
        textView_right.setVisibility(textView_right_Visibility);
        imageView_right.setVisibility(imageView_right_Visibility);

        imageView_left.setOnClickListener(imageView_left_click);
        textView_left.setOnClickListener(textView_left_click);
        textView_right.setOnClickListener(textView_right_click);
        imageView_right.setOnClickListener(imageView_right_click);
    }




    @Override
    protected void onResume() {
        super.onResume();
        Log.i(tag, "onResume----" + activity.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(tag, "onPause----" + activity.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(tag, "onDestroy----" + activity.getClass().getSimpleName());
    }
}