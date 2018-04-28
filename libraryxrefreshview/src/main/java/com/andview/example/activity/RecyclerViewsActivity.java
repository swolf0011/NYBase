package com.andview.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.andview.example.activity.recyclerview.BannerRecyclerViewActivity;
import com.andview.example.activity.recyclerview.GridRecyclerViewActivity;
import com.andview.example.activity.recyclerview.MultiItemRecyclerViewActivity;
import com.swolf.libraryxrefreshview.NYLinearRecyclerViewActivity;
import com.andview.example.activity.recyclerview.ShowFooterWhenNoMoreDataRecyclerViewActivity;
import com.andview.example.activity.recyclerview.SilenceRecyclerViewActivity;
import com.andview.example.activity.recyclerview.StaggeredRecyclerViewActivity;
import com.andview.example.activity.recyclerview.WithoutBaseAdapterRecyclerViewActivity;
import com.swolf.libraryxrefreshview.R;

public class RecyclerViewsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerviews);
    }

    public void onClick(View v) {
        Intent intent = null;
        int i = v.getId();
        if (i == R.id.bt_linear) {
            intent = new Intent(this, NYLinearRecyclerViewActivity.class);

        } else if (i == R.id.bt_grid) {
            intent = new Intent(this, GridRecyclerViewActivity.class);

        } else if (i == R.id.bt_staggered) {
            intent = new Intent(this, StaggeredRecyclerViewActivity.class);

        } else if (i == R.id.bt_banner) {
            intent = new Intent(this, BannerRecyclerViewActivity.class);

        } else if (i == R.id.bt_slience) {
            intent = new Intent(this, SilenceRecyclerViewActivity.class);

        } else if (i == R.id.bt_multi_item) {
            intent = new Intent(this, MultiItemRecyclerViewActivity.class);

        } else if (i == R.id.bt_without_baseRecyclerAdapter) {
            intent = new Intent(this, WithoutBaseAdapterRecyclerViewActivity.class);

        } else if (i == R.id.bt_showFooter_noMoreData) {
            intent = new Intent(this, ShowFooterWhenNoMoreDataRecyclerViewActivity.class);

        } else {
        }
        if (intent != null) {
            startActivity(intent);
        }

    }
}
