package com.swolf.librarybase.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用适配器
 * Created by LiuYi-15973602714
 */
public class NYAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> list;
    private int itemLayout;
    private IHandlerView handlerView;

    /**
     * @param context
     * @param list        数据集合
     * @param itemLayout  布局文件
     * @param handlerView 处理View
     */
    public NYAdapter(Context context,
                     List<T> list,
                     int itemLayout,
                     IHandlerView handlerView) {
        super();
        this.context = context;
        this.list = list;
        this.itemLayout = itemLayout;
        this.handlerView = handlerView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(context);
            convertView = li.inflate(itemLayout, null);
        }
        return handlerView.getView(position, convertView);
    }

    public interface IHandlerView {
        /**
         * TextView textView_name = ViewHolder.get(convertView, textView_nameId);
         */
        View getView(final int position, View convertView);
    }

    public static class ViewHolder {
        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> sparseArray = (SparseArray<View>) view.getTag();
            if (sparseArray == null) {
                sparseArray = new SparseArray<View>();
                view.setTag(sparseArray);
            }
            View childView = sparseArray.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                sparseArray.put(id, childView);
            }
            return (T) childView;
        }
    }
}
