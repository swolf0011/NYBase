package com.swolf.librarybase.util;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具
 * Created by LiuYi-15973602714
 */
public class NYDateUtil {
    private static class NYSubHolder{
        private static NYDateUtil util = new NYDateUtil();
    }
    private NYDateUtil(){
    }
    public static NYDateUtil getInstance(){
        return NYDateUtil.NYSubHolder.util;
    }


    /**
     *
     * @param date
     * @param dateFormat    日期格式
     * @return
     */
    public String date2Str(Date date, String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(date);
    }


    /**
     *
     * @param str
     * @param dateFormat    日期格式
     * @return
     */
    public Date str2Date(String str, String dateFormat) {
        try {
            return new SimpleDateFormat(dateFormat).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 差毫秒数
     *
     * @param begin
     * @param end
     * @return
     */
    public long compare(Date begin, Date end) {
        return end.getTime() - begin.getTime();
    }

    /**
     * 相差天数
     *
     * @param begin
     * @param end
     * @return
     */
    public long compareDay(Date begin, Date end) {
        long lon = end.getTime() - begin.getTime();
        long day = lon%(1000*60*60*24)==0?lon/(1000*60*60*24):lon%(1000*60*60*24)+1;
        return day;
    }
}
