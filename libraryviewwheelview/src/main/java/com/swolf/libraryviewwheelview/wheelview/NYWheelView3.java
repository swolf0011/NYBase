package com.swolf.libraryviewwheelview.wheelview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


import com.swolf.libraryviewwheelview.R;

import java.util.List;

/**
 * 三个WheelView
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYWheelView3 {
    /**
     * 最终显示View，需要加要使用者的视图中。
     */
    public View view;

    public interface INYWheelView3Hander {
        public void onChanging(String value1, String value2, String value3);
    }

    List<String[]> strsList2;
    List<List<String[]>> strsList3;


    private String[] values1 = {};
    private String[] values2 = {};
    private String[] values3 = {};

    int index1 = 0;
    int index2 = 0;
    int index3 = 0;

    WheelView wheelView1;
    WheelView wheelView2;
    WheelView wheelView3;

    INYWheelView3Hander hander;

    public NYWheelView3(Context context, String[] values1, String[] values2, String[] values3, List<String[]> strsList2, List<List<String[]>> strsList3, String value1, String value2, String value3, INYWheelView3Hander hander) {

        view = LayoutInflater.from(context).inflate(R.layout.ny_view_wheel_view_3, null);

        this.wheelView1 = (WheelView) view.findViewById(R.id.wheelView1);
        this.wheelView2 = (WheelView) view.findViewById(R.id.wheelView2);
        this.wheelView3 = (WheelView) view.findViewById(R.id.wheelView3);

        this.values1 = values1;
        this.strsList2 = strsList2;
        this.strsList3 = strsList3;
        this.values2 = values2;
        this.values3 = values3;

        this.hander = hander;

        initData(value1, value2, value3);

        resume();


    }

    /**
     * 如果UI显示有出问题，可以在Activity的onResume()中调用。
     */
    public void resume() {

        initWheelView1();
        initWheelView2();
        initWheelView3();

        setWheelViewChangingListener();
    }

    private void initData(String value1, String value2, String value3) {
        for (int i = 0; i < values1.length; i++) {
            if (values1[i].equals(value1)) {
                index1 = i;
                break;
            }
        }
        for (int i = 0; i < values2.length; i++) {
            if (values2[i].equals(value2)) {
                index2 = i;
                break;
            }
        }
        for (int i = 0; i < values3.length; i++) {
            if (values3[i].equals(value3)) {
                index3 = i;
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

    private void initWheelView3() {
        wheelView3.setAdapter(new ArrayWheelAdapter<String>(values3));
        wheelView3.setVisibleItems(5);
        wheelView3.setItemTextLength(6);
        wheelView3.setCyclic(false);
        wheelView3.setCurrentItem(index3);
    }

    private void setWheelViewChangingListener() {

        wheelView1.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                index1 = newValue;

                values2 = strsList2.get(index1);
                index2 = 0;
                initWheelView2();

                values3 = strsList3.get(index1).get(index2);
                index3 = 0;
                initWheelView3();

                if (hander != null) {
                    hander.onChanging(values1[wheelView1.getCurrentItem()], values2[wheelView2.getCurrentItem()], values3[wheelView3.getCurrentItem()]);
                }
            }
        });
        wheelView2.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                index2 = newValue;

                values3 = strsList3.get(index1).get(index2);
                index3 = 0;
                initWheelView3();

                if (hander != null) {
                    hander.onChanging(values1[wheelView1.getCurrentItem()], values2[wheelView2.getCurrentItem()], values3[wheelView3.getCurrentItem()]);
                }
            }
        });
        wheelView3.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                index3 = newValue;

                if (hander != null) {
                    hander.onChanging(values1[wheelView1.getCurrentItem()], values2[wheelView2.getCurrentItem()], values3[wheelView3.getCurrentItem()]);
                }
            }
        });
    }
}
