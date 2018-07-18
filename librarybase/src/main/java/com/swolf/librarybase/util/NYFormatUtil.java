package com.swolf.librarybase.util;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;

/**
 * 格式化工具
 * Created by LiuYi-15973602714
 */
@SuppressLint("DefaultLocale")
public class NYFormatUtil {
    private static class NYSubHolder {
        private static NYFormatUtil util = new NYFormatUtil();
    }

    private NYFormatUtil() {
    }

    public static NYFormatUtil getInstance() {
        return NYFormatUtil.NYSubHolder.util;
    }

    public static String money_prefix = "￥";

    /**
     * m和km格式距离
     *
     * @param distance
     * @return
     */
    public String formatDistance(float distance) {
        if (distance >= 1000) {
            if (distance % 1000 == 0) {
                distance = distance / 1000;
                return String.format("%.0f", distance) + "km";
            } else {
                distance = distance / 1000;
                return String.format("%.1f", distance) + "km";
            }
        } else {
            return String.format("%.0f", distance) + "m";
        }
    }

    /**
     * 格式钱币 money_prefix+"#,##0.00"
     *
     * @param distance
     * @param money_prefix
     * @return
     */
    public String formatMoney(double distance, String money_prefix) {
        return money_prefix +  new DecimalFormat("#,##0.00").format(distance);
    }

    /**
     * 格式化Double为Money "###,###,###,###.##"
     */
    public String formatMoney(double distance) {
        return new DecimalFormat("###,###,###,###.##").format(distance);
    }

    /**
     * 转换成模糊查询的字符串 %xx%
     */
    public String convertToSQLLikeStr(String val) {
        return "%" + val.trim() + "%";
    }

    /**
     * 二进制byte数组用String串来表示
     */
    @SuppressLint("DefaultLocale")
    public String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        String str = hs.toUpperCase();
        return str;
    }

    /**
     * 将String的二进制字符串转成数组
     */
    public final byte[] hex2Byte(String b) {
        char data[] = b.toCharArray();
        int l = data.length;
        byte out[] = new byte[l >> 1];
        int i = 0;
        for (int j = 0; j < l; ) {
            int f = Character.digit(data[j++], 16) << 4;
            f |= Character.digit(data[j++], 16);
            out[i] = (byte) (f & 0xff);
            i++;
        }
        return out;
    }

    /**
     * 将int型的数据转换成byte数组，四个字节
     */
    public byte[] int2Byte(int intValue) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);
        }
        return b;
    }

    /**
     * 将byte数组转换成int型，4个字节的数组
     */
    public int byte2Int(byte[] b) {
        int intValue = 0, tempValue = 0xFF;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & tempValue) << (8 * (3 - i));
        }
        return intValue;
    }

    /**
     * 将int型数字转换成两个字节的数组
     */
    public byte[] short2byte(int n) {
        byte b[] = new byte[2];
        b[0] = (byte) (n >> 8);
        b[1] = (byte) n;
        return b;
    }

    /**
     * 将两个字节的数组转成int型
     */
    public int byte2short(byte[] b) {
        return (b[1] & 0xFF) + ((((short) b[0]) << 8) & 0xFF00);
    }

}
