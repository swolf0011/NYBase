package com.swolf.libraryviewwheelview.wheelview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.swolf.librarygson.NYGsonParser;
import com.swolf.libraryviewwheelview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 三个WheelView
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYWheelViewAreaMap {
    /**
     * 最终显示View，需要加要使用者的视图中。
     */
    public View view;

    public interface INYWheelView3Hander {
        public void onChanging(String value1, String value2, String value3);
    }

    List<String[]> strsList2 = new ArrayList<>();
    List<List<String[]>> strsList3 = new ArrayList<>();


    private String[] values1 = {};
    private String[] values2 = {};
    private String[] values3 = {};

    int index1 = 0;
    int index2 = 0;
    int index3 = 0;

    WheelView wheelView1;
    WheelView wheelView2;
    WheelView wheelView3;

    String value1;
    String value2;
    String value3;

    INYWheelView3Hander hander;

    Map<String, Map<String, String[]>> areaMap;

    public NYWheelViewAreaMap(Context context, String areaJsonObject,
                              String v1, String v2, String v3, INYWheelView3Hander hander) {
        parserArea(areaJsonObject);
        this.value1 = v1;
        this.value2 = v2;
        this.value3 = v3;

        this.values1 = v1s();
        this.values2 = v2s(v1);
        this.values3 = v3s(v1, v2);

        view = LayoutInflater.from(context).inflate(R.layout.ny_view_wheel_view_3, null);
        this.wheelView1 = (WheelView) view.findViewById(R.id.wheelView1);
        this.wheelView2 = (WheelView) view.findViewById(R.id.wheelView2);
        this.wheelView3 = (WheelView) view.findViewById(R.id.wheelView3);
        this.hander = hander;
        initData(value1, value2, value3);
        resume();
    }

    private String[] v1s() {
        if (areaMap != null && areaMap.size() > 0) {
            List<String> list = new ArrayList<>();
            for (Map.Entry<String, Map<String, String[]>> item : areaMap.entrySet()) {
                list.add(item.getKey());
            }
            String[] strs = (String[]) list.toArray();
            return strs;
        }
        return null;
    }

    private String[] v2s(String v1) {
        if (areaMap != null && areaMap.size() > 0) {
            Map<String, String[]> map = areaMap.get(v1);
            if (map != null && map.size() > 0) {
                List<String> list = new ArrayList<>();
                for (Map.Entry<String, String[]> item : map.entrySet()) {
                    list.add(item.getKey());
                }
                String[] strs = (String[]) list.toArray();
                return strs;
            }
        }
        return null;
    }

    private String[] v3s(String v1, String v2) {
        if (areaMap != null && areaMap.size() > 0) {
            Map<String, String[]> map = areaMap.get(v1);
            if (map != null && map.size() > 0) {
                String[] strs = map.get(v2);
                if (strs != null) {
                    return strs;
                }
            }
        }
        return null;
    }

    private void parserArea(String areaJson) {
        try {
            areaMap = NYGsonParser.jsonStr2Object(areaJson, new TypeToken<Map<String, Map<String, String[]>>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                value1 = values1[index1];
                values2 = v2s(value1);

                index2 = 0;
                value2 = values2[index2];
                initWheelView2();

                values3 = v3s(value1,value2);
                index3 = 0;
                value3 = values3[index3];
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
                value2 = values2[index2];

                values3 = v3s(value1,value2);
                index3 = 0;
                value3 = values3[index3];
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
                value3 = values3[index3];

                if (hander != null) {
                    hander.onChanging(values1[wheelView1.getCurrentItem()], values2[wheelView2.getCurrentItem()], values3[wheelView3.getCurrentItem()]);
                }
            }
        });
    }
}