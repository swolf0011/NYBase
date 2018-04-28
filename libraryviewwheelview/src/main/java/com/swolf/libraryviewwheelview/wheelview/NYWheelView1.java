package com.swolf.libraryviewwheelview.wheelview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.swolf.libraryviewwheelview.R;


/**
 * 一个WheelView
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYWheelView1 {
    /**
     * 最终显示View，需要加要使用者的视图中。
     */
    public View view;

    public interface INYWheelView1Hander {
        public void onChanging(String value1);
    }

    private String[] values1 = {};

    int index1 = 0;

    WheelView wheelView1;

    INYWheelView1Hander hander;

    public NYWheelView1(Context context, String[] strs1, String value1, INYWheelView1Hander hander) {

        view = LayoutInflater.from(context).inflate(R.layout.ny_view_wheel_view_1, null);

        this.wheelView1 = (WheelView) view.findViewById(R.id.wheelView1);

        this.values1 = strs1;

        this.hander = hander;

        initData(value1);
        resume();
    }

    /**
     * 如果UI显示有出问题，可以在Activity的onResume()中调用。
     */
    public void resume() {

        initWheelView1();

        setWheelViewChangingListener();
    }

    private void initData(String value1) {
        for (int i = 0; i < values1.length; i++) {
            if (values1[i].equals(value1)) {
                index1 = i;
                break;
            }
        }
    }

    private void initWheelView1() {
        wheelView1.setAdapter(new ArrayWheelAdapter<String>(values1));
        wheelView1.setVisibleItems(5);
        wheelView1.setItemTextLength(6);
        wheelView1.setCyclic(false);
        wheelView1.setCurrentItem(index1);
    }

    private void setWheelViewChangingListener() {

        wheelView1.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                index1 = newValue;

                if (hander != null) {
                    hander.onChanging(values1[wheelView1.getCurrentItem()]);
                }
            }
        });
    }
}
