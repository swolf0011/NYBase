package com.swolf.librarybase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView通用适配器
 * Created by LiuYi-15973602714
 */
public abstract class NYRecyclerViewAdapter<T, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {
    private Context context;
    private List<T> list;
    private int item_layout;

    public NYRecyclerViewAdapter(Context context, List<T> list, int item_layout) {
        this.context = context;
        this.list = list;
        this.item_layout = item_layout;
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(item_layout, parent, false);
        return createViewHolder(view);
    }


    @Override
    public void onBindViewHolder(K holder, final int position) {
        bindViewHolder(holder, position, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * 移除
     *
     * @param position
     */
    public void removeNotifyItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size() - position);
    }

    /**
     * 添加
     *
     * @param t
     */
    public void insertedNotifyItem(T t) {
        list.add(t);
        int position = list.size();
        notifyItemInserted(position);
        notifyItemRangeChanged(position, list.size() - position);
    }

    /**
     * 添加
     *
     * @param t
     * @param position
     */
    public void insertedNotifyItem(T t, int position) {
        list.add(position, t);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, list.size() - position);
    }

    public abstract void bindViewHolder(K holder, int position, final T t);

    public abstract K createViewHolder(View view);
}
