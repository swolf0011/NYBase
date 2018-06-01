package com.swolf.libraryhttp.httpURLConnection;

import android.content.Context;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * https请求的ssl工具类
 * Created by LiuYi-15973602714
 */
public class NYSSLUtil {
    public static SSLContext getNotCerSSLContext() {
        // 生成SSLContext对象
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SSLContext getHavCerSSLContext(Context context, String assetsCerFileName) {
        // 生成SSLContext对象
        SSLContext sslContext = null;
        InputStream is = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            // 从assets中加载证书
            is = context.getAssets().open(assetsCerFileName);
            // 证书工厂
            CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
            Certificate cer = cerFactory.generateCertificate(is);
            // 密钥库
            KeyStore kStore = KeyStore.getInstance("PKCS12");
            kStore.load(null, null);
            kStore.setCertificateEntry("trust", cer);// 加载证书到密钥库中
            // 密钥管理器
            KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyFactory.init(kStore, null);// 加载密钥库到管理器
            // 信任管理器
            TrustManagerFactory tFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tFactory.init(kStore);// 加载密钥库到信任管理器
            // 初始化
            sslContext.init(keyFactory.getKeyManagers(), tFactory.getTrustManagers(), new SecureRandom());

            return sslContext;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
