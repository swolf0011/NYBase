package com.swolf.libraryviewlistview.draglistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * 可移动ListView适配器
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public abstract class NYAbsDragListViewAdapter<T> extends BaseAdapter {
    private List<T> list;
    private Context context;
    public boolean isHidden;
    private int itemLayout;

    public NYAbsDragListViewAdapter(Context context, List<T> list, int itemLayout) {
        this.context = context;
        this.list = list;
        this.itemLayout = itemLayout;
    }

    public void showDropItem(boolean showItem) {
        this.ShowItem = showItem;
    }

    public void setInvisiblePosition(int position) {
        invisilePosition = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        /***
         * 在这里尽可能每次都进行实例化新的，这样在拖拽ListView的时候不会出现错乱.
         * 具体原因不明，不过这样经过测试，目前没有发现错乱。虽说效率不高，但是做拖拽LisView足够了。
         */
//    convertView = LayoutInflater.from(context).inflate(R.layout.ny_item_draglistview,null);
        convertView = LayoutInflater.from(context).inflate(itemLayout, null);

        initView(convertView, list.get(position));

//    TextView textView = (TextView) convertView.findViewById(R.id.item_text);
//    ImageView imageView = (ImageView) convertView.findViewById(R.id.item_image);
//    textView.setText(list.get(position));
        if (isChanged) {
            if (position == invisilePosition) {
                if (!ShowItem) {
//          convertView.findViewById(R.id.item_text).setVisibility(View.INVISIBLE);
//          convertView.findViewById(R.id.item_image).setVisibility(View.INVISIBLE);
                    viewINVISIBLE(convertView);
                }
            }
            if (lastFlag != -1) {
                if (lastFlag == 1) {
                    if (position > invisilePosition) {
                        Animation animation = getFromSelfAnimation(0, -height);
                        convertView.startAnimation(animation);
                    }
                } else if (lastFlag == 0) {
                    if (position < invisilePosition) {
                        Animation animation = getFromSelfAnimation(0, height);
                        convertView.startAnimation(animation);
                    }
                }
            }
        }
        return convertView;
    }

    public abstract void initView(View convertView, T t);

    public abstract void viewINVISIBLE(View convertView);


    /***
     * 动态修改ListVIiw的方位.
     *
     * @param start
     *            点击移动的position
     * @param down
     *            松开时候的position
     */
    private int invisilePosition = -1;
    private boolean isChanged = true;
    private boolean ShowItem = false;

    public void exchange(int startPosition, int endPosition) {
        T startObject = getItem(startPosition);
        if (startPosition < endPosition) {
            list.add(endPosition + 1, startObject);
            list.remove(startPosition);
        } else {
            list.add(endPosition, startObject);
            list.remove(startPosition + 1);
        }
        isChanged = true;
    }

    public void exchangeCopy(int startPosition, int endPosition) {
        T startObject = getCopyItem(startPosition);
        if (startPosition < endPosition) {
            mCopyList.add(endPosition + 1, startObject);
            mCopyList.remove(startPosition);
        } else {
            mCopyList.add(endPosition, startObject);
            mCopyList.remove(startPosition + 1);
        }
        isChanged = true;
    }


    public T getCopyItem(int position) {
        return mCopyList.get(position);
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

    public void addDragItem(int start, Object obj) {
        list.remove(start);// 删除该项
        list.add(start, (T) obj);// 添加删除项
    }

    private List<T> mCopyList = new ArrayList<>();

    public void copyList() {
        mCopyList.clear();
        for (T t : list) {
            mCopyList.add(t);
        }
    }

    public void pastList() {
        list.clear();
        for (T t : mCopyList) {
            list.add(t);
        }
    }

    private boolean isSameDragDirection = true;
    private int lastFlag = -1;
    private int height;
    private int dragPosition = -1;

    public void setIsSameDragDirection(boolean value) {
        isSameDragDirection = value;
    }

    public void setLastFlag(int flag) {
        lastFlag = flag;
    }

    public void setHeight(int value) {
        height = value;
    }

    public void setCurrentDragPosition(int position) {
        dragPosition = position;
    }

    public Animation getFromSelfAnimation(int x, int y) {
        TranslateAnimation go = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, x,
                Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, y);
        go.setInterpolator(new AccelerateDecelerateInterpolator());
        go.setFillAfter(true);
        go.setDuration(100);
        go.setInterpolator(new AccelerateInterpolator());
        return go;
    }

    public Animation getToSelfAnimation(int x, int y) {
        TranslateAnimation go = new TranslateAnimation(
                Animation.ABSOLUTE, x, Animation.RELATIVE_TO_SELF, 0,
                Animation.ABSOLUTE, y, Animation.RELATIVE_TO_SELF, 0);
        go.setInterpolator(new AccelerateDecelerateInterpolator());
        go.setFillAfter(true);
        go.setDuration(100);
        go.setInterpolator(new AccelerateInterpolator());
        return go;
    }
}
