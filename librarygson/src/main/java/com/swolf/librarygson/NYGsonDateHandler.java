package com.swolf.librarygson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * gson时间处理器
 * Created by LiuYi-15973602714
 */
public class NYGsonDateHandler implements JsonDeserializer<Date>, JsonSerializer<Date> {
    private String dateFormatString = "yyyy-MM-dd HH:mm:ss";

    protected NYGsonDateHandler(String dateFormatString) {
        this.dateFormatString = dateFormatString;
    }

    /**
     * 时间反序列化
     */
    public Date deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) {
        try {
            String str = arg0.toString();
            // 去除两边的引号
            str = str.substring(1, str.length() - 1);
            return new SimpleDateFormat(dateFormatString).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间序列化
     */
    public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
        return new JsonPrimitive(new SimpleDateFormat(dateFormatString).format(arg0));
    }
}
