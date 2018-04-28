package com.swolf.libraryxrefreshview.xrefreshview;

import android.content.Context;

import com.andview.example.ui.CustomGifHeader;
import com.andview.example.ui.NoMoreDataFooterView;
import com.andview.refreshview.XRefreshView;

/**
 * Created by ly on 2017/10/18.
 */

public class NYUtil {

    public static void set(Context context, XRefreshView xRefreshView){

        xRefreshView.setSilenceLoadMore(true);// 设置静默加载模式
        xRefreshView.setPreLoadCount(4); //设置静默加载时提前加载的item个数
        xRefreshView.setPinnedTime(1000); //设置刷新完成以后，headerview固定的时间
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);//设置是否可以上拉刷新
        xRefreshView.setAutoLoadMore(false);//设置是否可以自动刷新
        xRefreshView.enableReleaseToLoadMore(true);//是否开启Recyclerview的松开加载更多功能，默认开启
        xRefreshView.enableRecyclerViewPullUp(true);//设置在被刷新的view滑倒最底部的时候，是否允许被刷新的view继续往上滑动，默认是true
        xRefreshView.enablePullUpWhenLoadCompleted(false);//设置在数据加载完成以后,是否可以向上继续拉被刷新的view,默认为true
        xRefreshView.setCustomHeaderView( new CustomGifHeader(context)); //设置自定义headerView

    }
}
