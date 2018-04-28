package com.swolf.libraryviewgridview.draggridview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.swolf.libraryviewgridview.R;

import java.util.Collections;
import java.util.List;

/**
 * 可移动GridView适配器
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressLint({ "ViewHolder", "InflateParams" }) 
public abstract class NYAbsDragGridViewAdapter<T> extends BaseAdapter{
	private List<T> list;
	private LayoutInflater mInflater;
	private int mHidePosition = -1;
	private Context context;
	private int itemLayout;

	public NYAbsDragGridViewAdapter(Context context, List<T> list, int itemLayout) {
		this.context = context;
		this.list = list;
		this.itemLayout = itemLayout;
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

	/**
	 * 由于复用convertView导致某些item消失了，所以这里不复用item，
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.ny_item_draggridview, null);
		initView( convertView,  list.get(position));
		if (position == mHidePosition) {
			viewINVISIBLE(convertView);
		}
		return convertView;
	}

	public abstract  void initView(View convertView,T t);

	public abstract  void viewINVISIBLE(View convertView);

	public void reorderItems(int oldPosition, int newPosition) {
		T temp = list.get(oldPosition);
		if (oldPosition < newPosition) {
			for (int i = oldPosition; i < newPosition; i++) {
				Collections.swap(list, i, i + 1);
			}
		} else if (oldPosition > newPosition) {
			for (int i = oldPosition; i > newPosition; i--) {
				Collections.swap(list, i, i - 1);
			}
		}

		list.set(newPosition, temp);
	}


	public void setHideItem(int hidePosition) {
		this.mHidePosition = hidePosition;
		notifyDataSetChanged();
	}

}
