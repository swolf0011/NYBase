package com.swolf.librarybase.util.gson;

import com.google.gson.reflect.TypeToken;

/**
 * gson解析类Demo
 * Created by LiuYi-15973602714
 */
class NYGsonParserDemo {

    public static void main(String[] args) {
        String str = "{}";
//        Type type = new TypeToken<NYParent>() {
//        }.getType();
        NYParent p = NYGsonParser.jsonStr2Object(str, new TypeToken<NYParent>(){});

        @SuppressWarnings("unused")
        String pStr = NYGsonParser.object2JsonStr(p);
    }

    private class NYParent {

    }
}