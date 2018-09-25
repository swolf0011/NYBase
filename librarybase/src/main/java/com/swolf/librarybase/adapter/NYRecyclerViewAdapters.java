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
public abstract class NYRecyclerViewAdapters<T extends NYRecyclerViewAdapterEntity> extends RecyclerView.Adapter<NYViewHolderHelper> {
    private Context context;
    private List<T> list;
    private int item_layout0;
    private int[] item_layouts;

    public NYRecyclerViewAdapters(Context context, List<T> list, int item_layout0, int ...item_layouts) {
        this.context = context;
        this.list = list;
        this.item_layout0 = item_layout0;
        this.item_layouts = item_layouts;
    }

    @Override
    public NYViewHolderHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = item_layout0;
        if(viewType>0){
            layout = item_layouts[viewType-1];
        }
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layout, parent, false);
        return new NYViewHolderHelper(view);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).typeIndex;
    }

    @Override
    public void onBindViewHolder(NYViewHolderHelper holder, final int position) {
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

    public abstract void bindViewHolder(NYViewHolderHelper holder, int position, final T t);

}
