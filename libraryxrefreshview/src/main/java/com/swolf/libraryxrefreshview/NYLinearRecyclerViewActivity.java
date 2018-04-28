package com.swolf.libraryxrefreshview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.example.recylerview.Person;
import com.andview.example.ui.CustomGifHeader;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshView.SimpleXRefreshListener;
import com.andview.refreshview.XRefreshViewFooter;
import com.swolf.libraryxrefreshview.xrefreshview.NYSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class NYLinearRecyclerViewActivity extends Activity {
    RecyclerView recyclerView;
    NYSimpleAdapter<Person, NYSimpleAdapterViewHolder> adapter;
    List<Person> personList = new ArrayList<Person>();
    XRefreshView xRefreshView;
    int lastVisibleItem = 0;
    //    GridLayoutManager layoutManager;
    LinearLayoutManager layoutManager;
    private boolean isBottom = false;
//    private int mLoadCount = 0;

    NYPage page = new NYPage(5);
    List<Integer> ls = new ArrayList<Integer>();


    private boolean isList = true;//false 为grid布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recylerview2);
        xRefreshView = (XRefreshView) findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_test_rv);



        ls.add(page.pageSize);
        ls.add(0);

        refreshData();

        adapter = new NYSimpleAdapter<Person, NYSimpleAdapterViewHolder>(this,personList, R.layout.item_recylerview) {

            @Override
            public void getViewHolder(NYSimpleAdapterViewHolder holder, int position, Person person) {
                holder.nameTv.setText(person.getName());
                holder.ageTv.setText(person.getAge());
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                    holder.rootView.getLayoutParams().height = position % 2 != 0 ? largeCardHeight : smallCardHeight;
                }
            }

            @Override
            public NYSimpleAdapterViewHolder createViewHolder(View view) {
                return new NYSimpleAdapterViewHolder(view);
            }
        };

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 静默加载模式不能设置footerview
        recyclerView.setAdapter(adapter);

        xRefreshView.setCustomHeaderView(new CustomGifHeader(this)); //设置自定义headerView
        xRefreshView.setPinnedTime(1000); //设置刷新完成以后，headerview固定的时间
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);//设置是否可以上拉刷新
        xRefreshView.setAutoLoadMore(false);//设置是否可以自动刷新
        xRefreshView.enableReleaseToLoadMore(true);//是否开启Recyclerview的松开加载更多功能，默认开启
        xRefreshView.enableRecyclerViewPullUp(true);//设置在被刷新的view滑倒最底部的时候，是否允许被刷新的view继续往上滑动，默认是true
        xRefreshView.enablePullUpWhenLoadCompleted(false);//设置在数据加载完成以后,是否可以向上继续拉被刷新的view,默认为true


        if (page.havNext) {
            adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        } else {
            xRefreshView.setLoadComplete(true);
        }

        //设置Recyclerview的滑动监听
        xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        xRefreshView.setXRefreshViewListener(new SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                        adapter.notifyDataSetChanged();
                        xRefreshView.stopRefresh();
                        if (!page.havNext) {
                            //模拟没有更多数据的情况
                            xRefreshView.setLoadComplete(true);
                        }

                    }
                }, 500);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        loadMoreData();
                        adapter.notifyDataSetChanged();
                        xRefreshView.stopLoadMore(false);
                        //当数据加载失败 不需要隐藏footerview时，可以调用以下方法，传入false，不传默认为true
                        // 同时在Footerview的onStateFinish(boolean hideFooter)，可以在hideFooter为false时，显示数据加载失败的ui
//                            xRefreshView1.stopLoadMore(false);
                        if (!page.havNext) {
                            //模拟没有更多数据的情况
                            xRefreshView.setLoadComplete(true);
                        }

                    }
                }, 1000);
            }
        });
    }


    private void refreshData() {
        page.pageNumber = 1;
        int size = ls.get(page.pageNumber - 1);
        List<Person> list = new ArrayList<Person>();
        for (int i = 0; i < size; i++) {
            Person person = new Person(page.pageNumber + "页，name" + i, "" + i);
            list.add(person);
        }
        personList.clear();
        personList.addAll(list);
        page.havNext = list.size() == page.pageSize;
    }

    private void loadMoreData() {
        page.pageNumber++;
        int size = ls.get(page.pageNumber - 1);
        List<Person> list = new ArrayList<Person>();
        for (int i = 0; i < size; i++) {
            Person person = new Person(page.pageNumber + "页，name" + i, "" + i);
            list.add(person);
        }
        personList.addAll(list);

        page.havNext = list.size() == page.pageSize;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.menu_clear) {
            xRefreshView.setLoadComplete(false);
            //切换布局
            isList = !isList;

            if (isList) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            }
            //当切换layoutManager时，需调用此方法
            xRefreshView.notifyLayoutManagerChanged();

        } else if (menuId == R.id.menu_refresh) {
            xRefreshView.startRefresh();

        }
        return super.onOptionsItemSelected(item);
    }


    public class NYSimpleAdapterViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView nameTv;
        public TextView ageTv;
        public int position;

        public NYSimpleAdapterViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView
                    .findViewById(R.id.recycler_view_test_item_person_name_tv);
            ageTv = (TextView) itemView
                    .findViewById(R.id.recycler_view_test_item_person_age_tv);
            rootView = itemView
                    .findViewById(R.id.card_view);

        }
    }
}