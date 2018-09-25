package com.swolf.librarybase.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class NYViewHolderHelper extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews = new SparseArray<View>();

    public NYViewHolderHelper(View itemView) {
        super(itemView);
    }
    /**
     * 根据id获取控件
     *
     * @param viewId 控件id
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

}
