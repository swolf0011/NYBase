package com.swolf.librarybase.baseView.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;


/**
 * FragmentActivity基类
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYBaseFragmentActivity extends FragmentActivity {
    private static final String tag = "NYBaseFragmentActivity";
    /**
     * 本Activity
     */
    public NYBaseFragmentActivity activity;

//    @ViewInject(R.id.textView_title)
//    TextView textView_title;

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