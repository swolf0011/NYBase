package com.swolf.librarygson;

import com.google.gson.reflect.TypeToken;
import com.swolf.librarygson.entity.NYArea;

import java.util.List;
import java.util.Map;

public class NYAddrParser {
    private static Map<String, Map<String, String[]>> areaMap;
    private static List<NYArea> areaList;
    public static Map<String, Map<String, String[]>> parserMapArea(String areaJson) {
        if (areaMap == null) {
            areaMap = NYGsonParser.jsonStr2Object(areaJson, new TypeToken<Map<String, Map<String, String[]>>>() {
            });
        }
        return areaMap;
    }
    public static List<NYArea> parserListArea(String areaJson) {
        if (areaList == null) {
            areaList = NYGsonParser.jsonStr2Object(areaJson, new TypeToken<List<NYArea>>() {
            });
        }
        return areaList;
    }
}
