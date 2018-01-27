package com.swolf.librarybase.util;

import android.content.Context;

import java.io.UnsupportedEncodingException;

/**
 * 字符集工具
 * Created by LiuYi-15973602714
 */
public class NYCharsetUtil {
    public enum ECharset {
        US_ASCII("US-ASCII"),
        ISO_8859_1("ISO-8859-1"),
        UTF_8("UTF-8"),
        GBK("GBK");
        public String value;
        private ECharset(String value) {
            this.value = value;
        }
    }
    private static class NYSubHolder{
        private static NYCharsetUtil util = new NYCharsetUtil();
    }
    private NYCharsetUtil(){
    }
    public static NYCharsetUtil getInstance(){
        return NYCharsetUtil.NYSubHolder.util;
    }

    public String changeCharset(String str, ECharset charset) {
        try {
            if (str != null) {
                byte[] bs = str.getBytes();
                return new String(bs, charset.value);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String changeCharset(String str, ECharset oldCharset, ECharset newCharset) {
        try {
            if (str != null) {
                byte[] bs = str.getBytes(oldCharset.value);
                return new String(bs, newCharset.value);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
