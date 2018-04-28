package com.swolf.libraryviewlistview.osRefreshListView;

/**
 * 下拉刷新上拉更多ListView工具
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYOSRefreshListViewUtil {
    /**
     * 当前页面数
     */
    private int page_currentPage = 1;
    /**
     * 1为下拉刷新，2为上拉加载更多
     */
    private int page_uploadState = 1;
    /**
     * 每页20条数据
     */
    private int page_size = 20;

    private NYOSRefreshListView refreshListView;
    private IRefreshListenerHandler handler;

    public NYOSRefreshListViewUtil(NYOSRefreshListView refreshListView, NYOSRefreshListViewUtil.IRefreshListenerHandler handler) {
        super();
        this.refreshListView = refreshListView;
        this.handler = handler;
    }

    public NYOSRefreshListViewUtil(NYOSRefreshListView refreshListView, int page_size, NYOSRefreshListViewUtil.IRefreshListenerHandler handler) {
        super();
        this.refreshListView = refreshListView;
        this.page_size = page_size;
        this.handler = handler;
    }

    public interface IRefreshListenerHandler {
        void refreshListenerHandler(int page_currentPage);
    }

    /**
     * 下拉刷新的监听
     */
    NYOSRefreshListView.OnRefreshListener onRefreshListener = new NYOSRefreshListView.OnRefreshListener() {
        public void onRefresh() {
            page_currentPage = 1;
            page_uploadState = 1;
            handler.refreshListenerHandler(page_currentPage);
        }
    };

    /**
     * 上拉加载更多的监听
     */
    NYOSRefreshListView.OnLoadMoreListener onLoadMoreListener = new NYOSRefreshListView.OnLoadMoreListener() {
        public void onLoadMore() {
            page_currentPage++;
            page_uploadState = 2;
            handler.refreshListenerHandler(page_currentPage);
        }
    };

    /**
     * 完成
     */
    public void refreshListViewOnComplete() {
        refreshListView.onRefreshComplete();
        refreshListView.onLoadMoreComplete();
        page_uploadState = 1;
    }

    /**
     * 设置下拉刷新监听
     */
    public void setOnRefreshListener() {
        refreshListView.setOnRefreshListener(onRefreshListener);
    }

    /**
     * 设置上拉加载更多监听
     */
    public void setOnLoadMoreListener(int page_countPage) {
        if (page_currentPage < page_countPage) {
            refreshListView.setOnLoadMoreListener(onLoadMoreListener);
        }
    }

    /**
     * 移除下拉刷新监听
     */
    public void removeOnLoadMoreListener() {
        refreshListView.removeOnLoadMoreListener();
    }

    /**
     * 移除上拉加载更多监听
     */
    public void removeOnRefreshListener() {
        refreshListView.removeOnRefreshListener();
    }

    /**
     * 获取加载的状态 ，1为下拉刷新，2为上拉加载更多
     */
    public int getPage_uploadState() {
        return page_uploadState;
    }

    public int getPage_currentPage() {
        return page_currentPage;
    }

    public void setPage_currentPage(int page_currentPage) {
        this.page_currentPage = page_currentPage;
    }

    public void setPage_uploadState(int page_uploadState) {
        this.page_uploadState = page_uploadState;
    }

    public int getPage_size() {
        return page_size;
    }

    /**
     * refreshListView 实例
     * <p>
     * private void refreshListViewDemo(NYOSRefreshListViewUtil
     * refreshListViewUtil, int curpage) {
     * NYLoadingDialog.showLoadingDialog(activity);
     * refreshListViewUtil.removeOnLoadMoreListener();
     * refreshListViewUtil.removeOnRefreshListener();
     * HttpManager.netGetVideoMsgList(DataUtil.user.tcur_userid, curpage, new
     * IAsyncTaskCallback<Resp08GetVideoMsgList>() {
     *
     * @param refreshListViewUtil
     * @param curpage
     * @Override public void success(Resp<Resp08GetVideoMsgList> resp) {
     * NYLoadingDialog.dismissLoadingDialog(); // 1为下拉刷新,清空之前的数据 if
     * (refreshListViewUtil.getPage_uploadState() == 1) { list.clear();
     * } if (resp.data.recs != null && resp.data.recs.size() > 0) {
     * list.addAll(resp.data.recs);
     * refreshListViewUtil.setOnLoadMoreListener(resp.data.sumpage); }
     * adapter.notifyDataSetChanged();
     * refreshListViewUtil.setOnRefreshListener();
     * refreshListViewUtil.refreshListViewOnComplete(); }
     * @Override public void exception(String errerMsg) {
     * NYLoadingDialog.dismissLoadingDialog();
     * refreshListViewUtil.refreshListViewOnComplete();
     * NYToastUtil.show(activity,errerMsg); } }); }
     */
    public void refreshListViewDemo(NYOSRefreshListViewUtil refreshListViewUtil, int curpage) {

    }

}
