package com.swolf.librarybase.util;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数工具
 * Created by LiuYi-15973602714
 */
public class NYRandomNumberUtil {

    private static class NYSubHolder{
        private static NYRandomNumberUtil util = new NYRandomNumberUtil();
    }

    private NYRandomNumberUtil(){
    }

    public static NYRandomNumberUtil getInstance(){
        return NYRandomNumberUtil.NYSubHolder.util;
    }
    /**
     * i~j random int
     */
    public int randomInt(int i, int j) {
        return (int) (Math.random() * (1 + j - i)) + i;
    }

    /**
     * 0~i random int
     */
    public int randomInt(int i) {
        return (int) (Math.random() * (i + 1));
    }

    /**
     * 0~1 random double
     */
    public double randomDouble() {
        return Math.random();
    }

    /**
     * UUID random String :5d981b3c-82df-4d81-b799-e3fcee051424
     */
    public String randomUUID2String() {
        return UUID.randomUUID().toString();
    }

    /**
     * UUID random long
     */
    public long randomUUID2Long() {
        return UUID.randomUUID().timestamp();
    }

    private final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String intactChar = "0123456789abcdefghijklmnopqrstuvwxyz";
    private final String letterChar = "abcdefghijklmnopqrstuvwxyz";
    private final String numberChar = "0123456789";
    private final String hexChar = "0123456789ABCDEF";

    /**
     * 随机生成字符串, 大小写数字混合
     *
     * @param length 生成的长度
     */
    public String randomAllChar(int length) {
        StringBuffer strbuf = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            strbuf.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return strbuf.toString();
    }

    /**
     * 随机生成字符串, 小写数字混合
     *
     * @param length 生成的长度
     */
    public String randomIntactChar(int length) {
        StringBuffer strbuf = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            strbuf.append(intactChar.charAt(random.nextInt(intactChar.length())));
        }
        return strbuf.toString();
    }

    /**
     * 随机生成字符串, 小写
     *
     * @param length 生成的长度
     */
    public String randomLetterChar(int length) {
        StringBuffer strbuf = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            strbuf.append(letterChar.charAt(random.nextInt(letterChar.length())));
        }
        return strbuf.toString();
    }

    /**
     * 随机生成字符串, 数字
     *
     * @param length 生成的长度
     */
    public String randomNumberChar(int length) {
        StringBuffer strbuf = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            strbuf.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return strbuf.toString();
    }

    /**
     * 随机生成字符串, HEX, 十六进制数字和字母
     *
     * @param length 生成的长度
     */
    public String randomHexChar(int length) {
        StringBuffer strbuf = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            strbuf.append(hexChar.charAt(random.nextInt(hexChar.length())));
        }
        return strbuf.toString();
    }
}
