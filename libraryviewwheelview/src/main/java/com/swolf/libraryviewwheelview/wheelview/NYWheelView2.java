package com.swolf.libraryviewwheelview.wheelview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


import com.swolf.libraryviewwheelview.R;

import java.util.List;

/**
 * 两个WheelView
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYWheelView2 {
    /**
     * 最终显示View，需要加要使用者的视图中。
     */
    public View view;

    public interface INYWheelView2Hander {
        public void onChanging(String value1, String value2);
    }

    List<String[]> strs2;

    private String[] values1 = {};
    private String[] values2 = {};

    int index1 = 0;
    int index2 = 0;

    WheelView wheelView1;
    WheelView wheelView2;

    INYWheelView2Hander hander;

    public NYWheelView2(Context context, String[] strs1, List<String[]> strs2, String value1, String value2, INYWheelView2Hander hander) {

        view = LayoutInflater.from(context).inflate(R.layout.ny_view_wheel_view_2, null);

        this.wheelView1 = (WheelView) view.findViewById(R.id.wheelView1);
        this.wheelView2 = (WheelView) view.findViewById(R.id.wheelView2);

        this.values1 = strs1;
        this.strs2 = strs2;

        this.hander = hander;

        initData(value1, value2);

        resume();
    }

    /**
     * 如果UI显示有出问题，可以在Activity的onResume()中调用。
     */
    public void resume() {

        initWheelView1();
        initWheelView2();

        setWheelViewChangingListener();
    }

    private void initData(String value1, String value2) {
        for (int i = 0; i < values1.length; i++) {
            if (values1[i].equals(value1)) {
                index1 = i;
                break;
            }
        }
        values2 = strs2.get(index1);
        for (int i = 0; i < values2.length; i++) {
            if (values2[i].equals(value2)) {
                index2 = i;
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

    private void initWheelView2() {
        wheelView2.setAdapter(new ArrayWheelAdapter<String>(values2));
        wheelView2.setVisibleItems(5);
        wheelView2.setItemTextLength(6);
        wheelView2.setCyclic(false);
        wheelView2.setCurrentItem(index2);
    }

    private void setWheelViewChangingListener() {

        wheelView1.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                index1 = newValue;

                values2 = strs2.get(index1);
                index2 = 0;
                initWheelView2();

                if (hander != null) {
                    hander.onChanging(values1[wheelView1.getCurrentItem()], values2[wheelView2.getCurrentItem()]);
                }
            }
        });
        wheelView2.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                index2 = newValue;

                if (hander != null) {
                    hander.onChanging(values1[wheelView1.getCurrentItem()], values2[wheelView2.getCurrentItem()]);
                }
            }
        });

    }
}
