package com.swolf.librarygson;

import com.google.gson.reflect.TypeToken;

/**
 * gson解析类Demo
 * Created by LiuYi-15973602714
 */
class NYGsonParserDemo {

    public static void main(String[] args) {
        String str = "{}";

        NYParent p0 = NYGsonParser.jsonStr2Object(str, new TypeToken<NYParent>(){});
        String pStr0 = NYGsonParser.object2JsonStr(p0);

        NYSub<NYParent> p1 = NYGsonParser.jsonStr2Object(str, new TypeToken<NYSub<NYParent>>(){});
        String pStr1 = NYGsonParser.object2JsonStr(p1);
    }

    private class NYParent {

    }
    private class NYSub<T>{

        T object;
    }
}