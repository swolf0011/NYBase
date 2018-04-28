package com.swolf.libraryviewwheelview.wheelview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


import com.swolf.libraryviewwheelview.R;

import java.util.Calendar;

/**
 * 日期WheelView
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressLint("WrongConstant")
public class NYWheelViewDate {

    /**
     * 最终显示View，需要加要使用者的视图中。
     */
    public View view;

    public interface INYWheelViewDateHander {
        public void onChanging(int year, int month, int day);
    }

    private WheelView wheelViewYear = null;
    private WheelView wheelViewMonth = null;
    private WheelView wheelViewDay = null;

    private Integer[] years = {};
    private Integer[] months = {};
    private Integer[] days = {};

    private int indexYear = 1;
    private int indexMonth = 2;
    private int indexDay = 3;

    INYWheelViewDateHander hander;
    Context context;

    public NYWheelViewDate(Context context, int beginYear, int endYear, int year, int month, int day, INYWheelViewDateHander hander) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.ny_view_wheel_view_date, null);

        this.wheelViewYear = (WheelView) view.findViewById(R.id.wheelViewYear);
        this.wheelViewMonth = (WheelView) view.findViewById(R.id.wheelViewMonth);
        this.wheelViewDay = (WheelView) view.findViewById(R.id.wheelViewDay);

        this.hander = hander;

        initYear(year,beginYear,endYear - beginYear>=0?endYear - beginYear:0);
        initMonth(year, month);
        initDay(year, month, day);

        resume();
    }

    /**
     * 如果UI显示有出问题，可以在Activity的onResume()中调用。
     */
    public void resume() {
        setWheelViewYearChangingListener();
        setWheelViewMonthChangingListener();
        setWheelViewDayChangingListener();
    }
    private void initYear(int year,int beginYear,int size) {
        years = new Integer[size];
        for (int i = 0; i < size; i++) {
            int value = beginYear + i;
            if (value==year) {
                indexYear = i;
            }
            years[i] = value;
        }

        initWheelViewYear();
    }

    private void initMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int monthSize = 12;
//        if (year==currentYear) {
//            monthSize = calendar.get(Calendar.MONTH) + 1;
//        }
        months = new Integer[monthSize];
        for (int i = 0; i < monthSize; i++) {
            int value = i + 1;
            if (value==month) {
                indexMonth = i;
            }
            months[i] = value;
        }
        if(indexMonth>=monthSize){
            indexMonth =monthSize-1;
        }

        initWheelViewMonth();
    }

    private void initDay(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int daySize = 1;
//        if (year ==currentYear&&month==currentMonth) {
//            daySize = calendar.get(Calendar.DAY_OF_MONTH);
//        }else{
            calendar.set(year, month - 1, 1);
            daySize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        }
        days = new Integer[daySize];
        for (int i = 0; i < daySize; i++) {
            int value = i + 1;
            if (value==day) {
                indexDay = i;
            }
            days[i] = value;
        }

        if(indexDay>=daySize){
            indexDay =daySize-1;
        }

        initWheelViewDay();
    }

    private void initWheelViewYear() {
        wheelViewYear.setAdapter(new ArrayWheelAdapter<Integer>(years));
        wheelViewYear.setVisibleItems(5);
        wheelViewYear.setCyclic(false);
        wheelViewYear.setCurrentItem(indexYear);
    }

    private void initWheelViewMonth() {
        wheelViewMonth.setAdapter(new ArrayWheelAdapter<Integer>(months));
        wheelViewMonth.setVisibleItems(5);
        wheelViewMonth.setCyclic(true);
        wheelViewMonth.setCurrentItem(indexMonth);
    }

    private void initWheelViewDay() {
        wheelViewDay.setAdapter(new ArrayWheelAdapter<Integer>(days));
        wheelViewDay.setVisibleItems(5);
        wheelViewDay.setCyclic(true);
        wheelViewDay.setCurrentItem(indexDay);
    }

    private void setWheelViewYearChangingListener() {
        wheelViewYear.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                indexYear = newValue;
                changMonth();
                changDay();
            }
        });
    }
    private void changMonth(){
        int yy =years[indexYear];
        int mm =months[indexMonth];

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int monthSize = 12;
//        if (yy==currentYear) {
//            monthSize = calendar.get(Calendar.MONTH) + 1;
//        }
        months = new Integer[monthSize];
        for (int i = 0; i < monthSize; i++) {
            int value = i + 1;
            if (value==mm) {
                indexMonth = i;
            }
            months[i] = value;
        }
        if(indexMonth>=monthSize){
            indexMonth =monthSize-1;
        }

        initWheelViewMonth();
    }

    private void setWheelViewMonthChangingListener() {
        wheelViewMonth.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                indexMonth = newValue;
                changDay();
            }
        });
    }

    private void changDay(){
        int yy =years[indexYear];
        int mm =months[indexMonth];
        int dd =days[indexDay];

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int daySize = 1;
//        if (yy==currentYear&&mm==currentMonth) {
//            daySize = calendar.get(Calendar.DAY_OF_MONTH);
//            int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//            if(maximum<daySize){
//                daySize = maximum;
//            }
//        }else{
            calendar.set(yy, mm - 1, 1);
            daySize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        }
        days = new Integer[daySize];
        for (int i = 0; i < daySize; i++) {
            int value = i + 1;
            if (value==dd) {
                indexDay = i;
            }
            days[i] = value;
        }

        if(indexDay>=daySize){
            indexDay =daySize-1;
        }

        initWheelViewDay();
        if (hander != null) {
            hander.onChanging(yy,
                    mm,
                    days[indexDay]);
        }
    }

    private void setWheelViewDayChangingListener() {
        wheelViewDay.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                indexDay = newValue;
                if (hander != null) {
                    hander.onChanging(years[wheelViewYear.getCurrentItem()], months[wheelViewMonth.getCurrentItem()], days[wheelViewDay.getCurrentItem()]);
                }
            }
        });
    }


}
