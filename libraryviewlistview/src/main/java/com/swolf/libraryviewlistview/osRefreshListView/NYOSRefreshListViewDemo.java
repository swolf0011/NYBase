package com.swolf.libraryviewlistview.osRefreshListView;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * 下拉刷新上拉更多ListView Demo
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYOSRefreshListViewDemo extends Activity {

    NYOSRefreshListView listview1;

    int selectIndex = 0;

    ArrayList<String> list = new ArrayList<String>();

    NYOSRefreshListViewUtil refreshListViewUtil;

    /**
     * 初始化Value
     */
    public void initValue() {
        refreshListViewUtil = new NYOSRefreshListViewUtil(listview1, new NYOSRefreshListViewUtil.IRefreshListenerHandler() {
            @Override
            public void refreshListenerHandler(int page_currentPage) {
                // getMyCusEmp(activity, page_currentPage, pagesize, "", "");
            }
        });
    }


    /**
     * 获取网络数据
     */
    public void getNetDate() {
        refreshListViewUtil.setPage_currentPage(1);
        refreshListViewUtil.setPage_uploadState(1);

//		getMyCusEmp(this, refreshListViewUtil.getPage_currentPage(), pagesize, "", "");
    }

    /**
     * item点击事件
     */
    public void itemClickImpl(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (arg0 == listview1) {
            String item = list.get(arg2 - 1);//索引减1是因为listview添加了头部的下拉刷新。
        }
    }


    private void getMyCusEmp(final Activity activity, int curpage, int pagesize, String signs, String searchstr) {
//		NYLoadingDialog.showLoadingDialog(activity);
//		HttpManager.getMyCusEmp(curpage, pagesize, Constant.tcususeremp.tcur_userid, signs, searchstr,
//				new IAsyncTaskCallback<RespDataGetMyCusEmp>() {
//					@Override
//					public void success(Resp<RespDataGetMyCusEmp> resp) {
//						NYLoadingDialog.dismissLoadingDialog();
//						
//						
//						// 1为下拉刷新,清空之前的数据
//						if (refreshListViewUtil.getPage_uploadState() == 1) {
//							list.clear();
//						}
//						if (resp.data.recs != null && resp.data.recs.size() > 0) {
//							list.addAll(resp.data.recs);
//							refreshListViewUtil.setOnLoadMoreListener(resp.data.sumpage);
//						}

//						adapter.notifyDataSetChanged();

//						refreshListViewUtil.setOnRefreshListener();
//						refreshListViewUtil.refreshListViewOnComplete();
//					}
//
//					@Override
//					public void exception(String errerMsg) {
//						NYLoadingDialog.dismissLoadingDialog();
//						NYToastUtil.show(activity, errerMsg);
//						
//						
//						refreshListViewUtil.refreshListViewOnComplete();
//					}
//				});
    }


}
