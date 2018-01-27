package com.swolf.librarybase.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.util.Date;

/**
 * gson解析类
 * Created by LiuYi-15973602714
 */
public class NYGsonParser<T>{

    /**
     * @param json              json时间格式默认为:yyyy-MM-dd HH:mm:ss
     * @param typeToken              new TypeToken<VersionResponse>()
     * @return
     */
    public static <T> T jsonStr2Object(String json, TypeToken<T> typeToken) {
        return jsonStr2Object(json, typeToken, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     *
     * @param json              String时间格式默认为:yyyy-MM-dd HH:mm:ss
     * @param typeToken              new TypeToken<VersionResponse>()
     * @param dateFormatString  时间格式
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonStr2Object(String json, TypeToken<T> typeToken, String dateFormatString) {
        try {
            if (dateFormatString == null||dateFormatString.length()==0) {
                dateFormatString = "yyyy-MM-dd HH:mm:ss";
            }
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.registerTypeAdapter(Date.class, new NYGsonDateHandler(dateFormatString)).setDateFormat(DateFormat.LONG).create();
            return (T) gson.fromJson(json, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param obj obj实体的字段最好使用包装类型，这样如果字段为空就不会转换出来,时间格式默认为:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String object2JsonStr(Object obj) {
        return object2JsonStr(obj, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     *
     * @param obj obj实体的字段最好使用包装类型，这样如果字段为空就不会转换出来
     * @param dateFormatString 时间格式
     * @return
     */
    public static String object2JsonStr(Object obj, String dateFormatString) {
        try {
            if (dateFormatString == null||dateFormatString.length()==0) {
                dateFormatString = "yyyy-MM-dd HH:mm:ss";
            }
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NYGsonDateHandler(dateFormatString)).setDateFormat(DateFormat.LONG).create();
            return gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

