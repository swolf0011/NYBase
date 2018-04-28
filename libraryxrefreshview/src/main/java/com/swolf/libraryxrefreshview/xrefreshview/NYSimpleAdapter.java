package com.swolf.libraryxrefreshview.xrefreshview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.example.DensityUtil;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;

import java.util.List;

public abstract class NYSimpleAdapter<T,K extends RecyclerView.ViewHolder> extends BaseRecyclerAdapter<K> {
    private Context context;
    private List<T> list;
    private int item_layout;
    public int largeCardHeight, smallCardHeight;

    public abstract void getViewHolder(K holder, int position, final T t);

    public abstract K createViewHolder(View view);

    public NYSimpleAdapter( Context context,List<T> list, int item_layout) {
        this.context = context;
        this.list = list;
        this.item_layout = item_layout;
        largeCardHeight = DensityUtil.dip2px(context, 150);
        smallCardHeight = DensityUtil.dip2px(context, 100);
    }

    @Override
    public void onBindViewHolder(K holder, int position, boolean isItem) {
        getViewHolder(holder, position, list.get(position));
    }

    @Override
    public int getAdapterItemViewType(int position) {
        return 0;
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    @Override
    public K getViewHolder(View view) {
        return createViewHolder(view);
    }

    public void setData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(item_layout, parent, false);
        return createViewHolder(view);
    }

    public void insert(T t, int position) {
        insert(list, t, position);
    }

    public void remove(int position) {
        remove(list, position);
    }

    public void clear() {
        clear(list);
    }



    public T getItem(int position) {
        if (position < list.size())
            return list.get(position);
        else
            return null;
    }

}