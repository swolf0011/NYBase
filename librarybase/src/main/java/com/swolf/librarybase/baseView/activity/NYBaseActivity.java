package com.swolf.librarybase.baseView.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swolf.librarybase.baseView.NYIBaseViewFun;

/**
 * Activity基类
 * Created by LiuYi-15973602714
 */
public abstract class NYBaseActivity extends AppCompatActivity implements NYIBaseViewFun {
    public AppCompatActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }
}
