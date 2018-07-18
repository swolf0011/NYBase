package com.swolf.librarybase.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具
 * Created by LiuYi-15973602714
 */
public class NYVerifyUtil {

    private static class NYSubHolder{
        private static NYVerifyUtil util = new NYVerifyUtil();
    }

    private NYVerifyUtil(){
    }

    public static NYVerifyUtil getInstance(){
        return NYVerifyUtil.NYSubHolder.util;
    }
    /**
     * 验证是否为空
     */
    public boolean isEmpty(String val) {
        return null == val || val.length() == 0;
    }

    /**
     * 校验手机号长度
     */
    public boolean isPhoneNumberLength(String phoneNumber) {
        Pattern p = Pattern.compile("^1\\d{12}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 校验手机号
     */
    public boolean isPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile("^((13[0-9])|(147)|(177)|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 校验手机号
     */
    public boolean isMobile(String mobile) {
//        String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        String REGEX_MOBILE = "^1\\d{10}$";
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 验证是否为数字
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    /**
     * 简单验证身份证:18位或15位，尾号为数字或X、x
     */
    public boolean verificationId(String value) {
        String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)|(^\\d{17}X$)|(^\\d{15}X$)|(^\\d{17}x$)|(^\\d{15}x$)";
        Pattern pattern = Pattern.compile(REGEX_ID_CARD);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 校验用户名
     */
    public boolean isUsername(String username) {
        String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     */
    public boolean isPassword(String password) {
        String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 验证是否为邮箱
     */
    public boolean isEmail(String email) {
        String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 验证是否为邮箱
     */
    public boolean isEmail2(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 校验汉字
     */
    public boolean isChinese(String chinese) {
        String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验URL
     */
    public boolean isUrl(String url) {
        String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     */
    public boolean isIPAddr(String ipAddr) {
        String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }


}
