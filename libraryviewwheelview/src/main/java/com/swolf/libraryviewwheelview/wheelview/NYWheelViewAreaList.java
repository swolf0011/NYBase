package com.swolf.libraryviewwheelview.wheelview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.swolf.libraryviewwheelview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 三个WheelView
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYWheelViewAreaList {
    /**
     * 最终显示View，需要加要使用者的视图中。
     */
    public View view;

    public interface INYWheelView3Hander {
        public void onChanging(String value1, String value2, String value3);
    }
    private List<NYArea> areaList;
    private String value1;
    private String value2;
    private String value3;
    private String[] values1 = {};
    private String[] values2 = {};
    private String[] values3 = {};
    private WheelView wheelView1;
    private WheelView wheelView2;
    private WheelView wheelView3;
    private INYWheelView3Hander hander;
    private int index1 = 0;
    private int index2 = 0;
    private int index3 = 0;

    public NYWheelViewAreaList(Context context, List<NYArea> areaList,
                               String v1, String v2, String v3, INYWheelView3Hander hander) {
        this.areaList = areaList;
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
        if (areaList != null && areaList.size() > 0) {
            String[] strs = new String[areaList.size()];
            for(int i = 0; i<strs.length;i++){
                strs[i] = areaList.get(i).name;
            }
            return strs;
        }
        return null;
    }

    private String[] v2s(String v1) {
        if (areaList != null && areaList.size() > 0) {
            int ix = 0;
            for(int i = 0; i<areaList.size() ;i++){
                if(v1.equals(areaList.get(i).name)){
                    ix = i;
                    break;
                }
            }
            NYArea a = areaList.get(ix);
            if(a!=null){
                List<NYArea> lx = a.getSubs();
                if(lx!=null&&lx.size()>0){
                    String[] strs = new String[lx.size()];
                    for(int i = 0; i<strs.length;i++){
                        strs[i] = lx.get(i).name;
                    }
                    return strs;
                }
            }

        }
        return null;
    }

    private String[] v3s(String v1, String v2) {
        if (areaList != null && areaList.size() > 0) {
            int ix = 0;
            for(int i = 0; i<areaList.size() ;i++){
                if(v1.equals(areaList.get(i).name)){
                    ix = i;
                    break;
                }
            }
            NYArea a = areaList.get(ix);
            if(a!=null){
                List<NYArea> lx = a.getSubs();
                if(lx!=null&&lx.size()>0){
                    ix = 0;
                    for(int i = 0; i<lx.size() ;i++){
                        if(v1.equals(lx.get(i).name)){
                            ix = i;
                            break;
                        }
                    }
                    a = areaList.get(ix);
                    if(a!=null) {
                        lx = a.getSubs();
                        if(lx!=null&&lx.size()>0){
                            String[] strs = new String[lx.size()];
                            for(int i = 0; i<strs.length;i++){
                                strs[i] = lx.get(i).name;
                            }
                            return strs;
                        }
                    }
                }
            }
        }
        return null;
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
