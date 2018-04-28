package com.swolf.libraryencryptdecrypt.encryptDecrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.annotation.SuppressLint;

/**
 * 加密解密工具类
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYEncryptDecryptUtil {
    private NYEncryptDecryptUtil() {
    }

    /**
     * 信息摘要算法 MD5 (非对称): 让大容量信息在用数字签名软件签署私人密匙前被"压缩"成一种保密的格式。
     */

    /**
     * 散列算法 SHA (非对称):散列算法，散列是信息的提炼，通常其长度要比信息小得多，且为一个固定长度。
     */

    /**
     * 数据编码BASE64:采用Base64编码不仅比较简短，同时也具有不可读性，即所编码的数据不会被人用肉眼所直接看到。
     */

    /**
     * 数据加密算法 DES (对称):DES是21世纪的加密标准，现新一代加密标准是AES。
     */

    /************* Base64 *****************/
    // TODO Base64

    /**
     * @param data 源加密数据
     * @return BASE64加密数据
     */
    public static byte[] BASE64_encrypt2Bytes(byte[] data) {
        if (data == null) {
            return null;
        }
        return NYBase64Util.encode(data).getBytes();
    }

    /**
     * @param data 源加密数据
     * @return BASE64加密数据
     */
    public static byte[] BASE64_encrypt2Bytes(String data) {
        if (data == null) {
            return null;
        }
        return NYBase64Util.encode(data.getBytes()).getBytes();
    }

    /**
     * @param data 源加密数据
     * @return BASE64加密串
     */
    public static String BASE64_encrypt2String(byte[] data) {
        if (data == null) {
            return null;
        }
        return NYBase64Util.encode(data);
    }

    /**
     * @param data 源加密数据
     * @return BASE64加密串
     */
    public static String BASE64_encrypt2String(String data) {
        if (data == null) {
            return null;
        }
        return NYBase64Util.encode(data.getBytes());
    }

    /**
     * @param data 需要解密的字符串
     * @return BASE64解密后的数据
     */
    public static byte[] BASE64_decrypt2Bytes(String data) {
        if (data == null) {
            return null;
        }
        return NYBase64Util.decode(data);
    }

    /**
     * @param data 需要解密的字符串
     * @return BASE64解密后的字符串
     */
    public static String BASE64_decrypt2String(String data) {
        if (data == null) {
            return null;
        }
        return new String(NYBase64Util.decode(data));
    }

    /************* MD5 *****************/
    // TODO MD5

    /**
     * @param data    源加密数据
     * @param md5_key
     * @return MD5加密后的字符串 (32位)
     */
    public static byte[] MD5_encrypt2Bytes(byte[] data, String md5_key) {
        if (data == null) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance(md5_key);
            md5.update(data);
            return md5.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data    源加密数据
     * @param md5_key
     * @return MD5加密后的字符串 (32位)
     */
    public static byte[] MD5_encrypt2Bytes(String data, String md5_key) {
        if (data == null) {
            return null;
        }
        return MD5_encrypt2Bytes(data.getBytes(), md5_key);
    }

    /**
     * @param data    源加密数据
     * @param md5_key
     * @return MD5加密后的字符串 (32位)
     */
    public static String MD5_encrypt2String(byte[] data, String md5_key) {
        if (data == null) {
            return null;
        }
        return byte2hex(MD5_encrypt2Bytes(data, md5_key));
    }

    /**
     * @param data    源加密数据
     * @param md5_key
     * @return MD5加密后的字符串 (32位)
     */
    public static String MD5_encrypt2String(String data, String md5_key) {
        if (data == null) {
            return null;
        }
        return byte2hex(MD5_encrypt2Bytes(data, md5_key));
    }

    /**
     * @param data    源加密字符串
     * @param md5_key
     * @param n       加密次数
     * @return
     */
    public static String MD5_encrypt2String(byte[] data, String md5_key, int n) {
        if (data == null) {
            return null;
        }
        try {
            for (int i = 0; i < n; i++) {
                data = MD5_encrypt2Bytes(data, md5_key);
            }
            return byte2hex(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /************* SHA *****************/
    // TODO SHA

    /**
     * @param data    源加密数据
     * @param sha_key
     * @return SHA加密后的数据
     */
    public static byte[] SHA_encrypt2Bytes(byte[] data, String sha_key) {
        if (data == null) {
            return null;
        }
        try {
            MessageDigest sha = MessageDigest.getInstance(sha_key);
            sha.update(data);
            return sha.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data    源加密数据
     * @param sha_key
     * @return SHA加密后的数据
     */
    public static byte[] SHA_encrypt2Bytes(String data, String sha_key) {
        if (data == null) {
            return null;
        }
        return SHA_encrypt2Bytes(data.getBytes(), sha_key);
    }

    /**
     * SHA加密
     *
     * @param data    源加密数据
     * @param sha_key
     * @return SHA加密后的字符串 (40位)
     */
    public static String SHA_encrypt2String(byte[] data, String sha_key) {
        if (data == null) {
            return null;
        }
        return byte2hex(SHA_encrypt2Bytes(data, sha_key));
    }

    /**
     * SHA加密
     *
     * @param data    源加密数据
     * @param sha_key
     * @return SHA加密后的字符串 (40位)
     */
    public static String SHA_encrypt2String(String data, String sha_key) {
        if (data == null) {
            return null;
        }
        return SHA_encrypt2String(data.getBytes(), sha_key);
    }

    /************* DES *****************/
    // TODO DES

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES加密后的数据
     */
    @SuppressLint("TrulyRandom")
    public static byte[] DES_encrypt2Bytes(byte[] desKeySpec_key, byte[] data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        try {
            DESKeySpec deskey = new DESKeySpec(desKeySpec_key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(secretKeyFactory_key);
            SecretKey securekey = keyfactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance(secretKeyFactory_key);
            SecureRandom random = new SecureRandom();
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES加密后的数据
     */
    public static byte[] DES_encrypt2Bytes(String desKeySpec_key, byte[] data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return DES_encrypt2Bytes(desKeySpec_key.getBytes(), data, secretKeyFactory_key);
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES加密后的数据
     */
    public static byte[] DES_encrypt2Bytes(byte[] desKeySpec_key, String data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return DES_encrypt2Bytes(desKeySpec_key, data.getBytes(), secretKeyFactory_key);
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES加密后的数据
     */
    public static byte[] DES_encrypt2Bytes(String desKeySpec_key, String data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return DES_encrypt2Bytes(desKeySpec_key.getBytes(), data.getBytes(), secretKeyFactory_key);
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES加密后的数据
     */
    public static String DES_encrypt2String(byte[] desKeySpec_key, byte[] data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return byte2hex(DES_encrypt2Bytes(desKeySpec_key, data, secretKeyFactory_key));
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES加密后的数据
     */
    public static String DES_encrypt2String(String desKeySpec_key, byte[] data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return byte2hex(DES_encrypt2Bytes(desKeySpec_key, data, secretKeyFactory_key));
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES加密后的数据
     */
    public static String DES_encrypt2String(byte[] desKeySpec_key, String data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return byte2hex(DES_encrypt2Bytes(desKeySpec_key, data, secretKeyFactory_key));
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES加密后的数据
     */
    public static String DES_encrypt2String(String desKeySpec_key, String data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return byte2hex(DES_encrypt2Bytes(desKeySpec_key, data, secretKeyFactory_key));
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES解密后的数据
     */
    public static byte[] DES_decrypt2Bytes(byte[] desKeySpec_key, byte[] data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        try {
            DESKeySpec deskey = new DESKeySpec(desKeySpec_key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(secretKeyFactory_key);
            SecretKey securekey = keyfactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance(secretKeyFactory_key);
            SecureRandom random = new SecureRandom();
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES解密后的数据
     */
    public static byte[] DES_decrypt2Bytes(String desKeySpec_key, byte[] data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return DES_decrypt2Bytes(desKeySpec_key.getBytes(), data, secretKeyFactory_key);
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES解密后的数据
     */
    public static byte[] DES_decrypt2Bytes(byte[] desKeySpec_key, String data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return DES_decrypt2Bytes(desKeySpec_key, hex2byte(data), secretKeyFactory_key);
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES解密后的数据
     */
    public static byte[] DES_decrypt2Bytes(String desKeySpec_key, String data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return DES_decrypt2Bytes(desKeySpec_key.getBytes(), hex2byte(data), secretKeyFactory_key);
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES解密后的数据
     */
    public static String DES_decrypt2String(byte[] desKeySpec_key, byte[] data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return byte2hex(DES_decrypt2Bytes(desKeySpec_key, data, secretKeyFactory_key));
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES解密后的数据
     */
    public static String DES_decrypt2String(String desKeySpec_key, byte[] data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return byte2hex(DES_decrypt2Bytes(desKeySpec_key, data, secretKeyFactory_key));
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES解密后的数据
     */
    public static String DES_decrypt2String(byte[] desKeySpec_key, String data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return byte2hex(DES_decrypt2Bytes(desKeySpec_key, data, secretKeyFactory_key));
    }

    /**
     * @param desKeySpec_key       DES密钥
     * @param data                 源加密数据
     * @param secretKeyFactory_key
     * @return DES解密后的数据
     */
    public static String DES_decrypt2String(String desKeySpec_key, String data, String secretKeyFactory_key) {
        if (desKeySpec_key == null || data == null || secretKeyFactory_key == null) {
            return null;
        }
        return byte2hex(DES_decrypt2Bytes(desKeySpec_key, data, secretKeyFactory_key));
    }

    /**
     * 二进制转哈希码
     *
     * @param data 源二进制数据
     * @return 哈希码
     */
    @SuppressLint("DefaultLocale")
    private static String byte2hex(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; n < data.length; n++) {
            stmp = Integer.toHexString(data[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 哈希码转二进制
     *
     * @param hex 源哈希码字符串
     * @return 二进制数据
     */
    private static byte[] hex2byte(String hex) {
        if (hex == null) {
            return null;
        }
        byte[] bs = hex.getBytes();
        if ((bs.length % 2) != 0) {
            return null;
        }
        byte[] bh = new byte[bs.length / 2];
        for (int n = 0; n < bs.length; n += 2) {
            bh[n / 2] = (byte) Integer.parseInt(new String(bs, n, 2), 16);
        }
        return bh;
    }
}