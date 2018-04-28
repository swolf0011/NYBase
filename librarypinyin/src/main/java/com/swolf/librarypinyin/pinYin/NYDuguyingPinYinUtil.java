package com.swolf.librarypinyin.pinYin;

import net.duguying.pinyin.Pinyin;
import net.duguying.pinyin.PinyinException;

/**
 * Duguying拼音工具
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYDuguyingPinYinUtil {
    /**
     * 全拼,不带音调拼音
     *
     * @param content
     * @return
     */
    public static String translateNoMark(String content) throws PinyinException {
        return new Pinyin().translateNoMark(content).toUpperCase();
    }

    ;

    /**
     * 翻译为拼音首字母
     *
     * @param content
     * @return
     */
    public static String translateFirstChar(String content) throws PinyinException {
        return new Pinyin().translateFirstChar(content).toUpperCase();
    }

    ;

    /**
     * 全拼,带音调拼音
     *
     * @param content
     * @return
     */
    public static String translate(String content) throws PinyinException {
        return new Pinyin().translate(content).toUpperCase();
    }

    ;

    /**
     * 首拼音,如果空:"",如果不是字母:"#"
     *
     * @param pinyin
     * @return
     */
    public static String getFirstPinyin(String pinyin) {
        if (pinyin == null || pinyin.length() == 0) {
            return "";
        }
        //不是26的字母 就为#号
        String str0 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String str1 = pinyin.substring(0, 1);
        if (!str0.contains(str1)) {
            str1 = "#";
        }
        return str1;
    }

    /**
     * 首拼音,如果空:""
     *
     * @param pinyin
     * @return
     */
    public static String getFirstPinyin2(String pinyin) {
        if (pinyin == null || pinyin.length() == 0) {
            return "";
        }
        //不是26的字母 就为#号
        return pinyin.substring(0, 1);
    }
}
