package com.swolf.libraryviewlistview.swipemenulistview;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 滑动菜单View
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYSwipeMenuView extends LinearLayout implements OnClickListener {

    private NYSwipeMenuListView mListView;
    private NYSwipeMenuLayout mLayout;
    private NYSwipeMenu mMenu;
    private OnSwipeItemClickListener onItemClickListener;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public NYSwipeMenuView(NYSwipeMenu menu, NYSwipeMenuListView listView) {
        super(menu.getContext());
        mListView = listView;
        mMenu = menu;
        List<NYSwipeMenuItem> items = menu.getMenuItems();
        int id = 0;
        for (NYSwipeMenuItem item : items) {
            addItem(item, id++);
        }
    }

    private void addItem(NYSwipeMenuItem item, int id) {
        LayoutParams params = new LayoutParams(item.getWidth(),
                LayoutParams.MATCH_PARENT);
        LinearLayout parent = new LinearLayout(getContext());
        parent.setId(id);
        parent.setGravity(Gravity.CENTER);
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setLayoutParams(params);
        parent.setBackgroundDrawable(item.getBackground());
        parent.setOnClickListener(this);
        addView(parent);

        if (item.getIcon() != null) {
            parent.addView(createIcon(item));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            parent.addView(createTitle(item));
        }

    }

    private ImageView createIcon(NYSwipeMenuItem item) {
        ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(item.getIcon());
        return iv;
    }

    private TextView createTitle(NYSwipeMenuItem item) {
        TextView tv = new TextView(getContext());
        tv.setText(item.getTitle());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(item.getTitleSize());
        tv.setTextColor(item.getTitleColor());
        return tv;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null && mLayout.isOpen()) {
            onItemClickListener.onItemClick(this, mMenu, v.getId());
        }
    }

    public OnSwipeItemClickListener getOnSwipeItemClickListener() {
        return onItemClickListener;
    }

    public void setOnSwipeItemClickListener(OnSwipeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setLayout(NYSwipeMenuLayout mLayout) {
        this.mLayout = mLayout;
    }

    public static interface OnSwipeItemClickListener {
        void onItemClick(NYSwipeMenuView view, NYSwipeMenu menu, int index);
    }
}
