package com.swolf.libraryhttp.httpURLConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * HttpURLConnection请求
 * Created by LiuYi-15973602714
 */
public class NYURLConnectionUtil {
    public int connection_timeout = 1000 * 30;// 连接超时
    public int read_timeout = 1000 * 60;// 读取超时
    public String charsetName = "UTF-8";// 字符集

    private static class NYSubHolder {
        private static NYURLConnectionUtil util = new NYURLConnectionUtil();
    }

    public static NYURLConnectionUtil getInstance() {
        return NYURLConnectionUtil.NYSubHolder.util;
    }

    private NYURLConnectionUtil() {
    }


    /**
     * postHttpURLConnection
     *
     * @param urlStr
     * @param paramMap
     * @param headMap
     * @param requestMethod GET, POST, PATCH, DELETE, PUT, HEAD
     */
    public HttpURLConnection httpURLConnection(String urlStr, HashMap<String, Object> paramMap, HashMap<String, String> headMap, String requestMethod) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setRequestMethod(requestMethod);
                conn.setConnectTimeout(connection_timeout);
                conn.setReadTimeout(read_timeout);
                setHead(conn, headMap);
                String paramStr = setParam(conn, paramMap);
                if (paramStr.length() > 0) {
                    byte[] bypes = paramStr.getBytes(charsetName);
                    conn.getOutputStream().write(bypes);
                }
                conn.connect();
                if (conn.getResponseCode() != 200) {
                    disconnect(conn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            disconnect(conn);
        }
        return conn;
    }

    /**
     * request result String for json or xml
     *
     * @param urlStr
     * @param paramMap
     * @param headMap
     * @param requestMethod GET, POST, PATCH, DELETE, PUT, HEAD
     * @return
     */
    public String requestStr(String urlStr, HashMap<String, Object> paramMap, HashMap<String, String> headMap, String requestMethod) {
        StringBuffer sb = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            conn = httpURLConnection(urlStr, paramMap, headMap, requestMethod);
            if (conn != null) {
                sb = new StringBuffer("");
                byte[] bytes = new byte[1024 * 4];
                is = conn.getInputStream();
                int length = -1;
                while ((length = is.read(bytes)) != -1) {
                    sb.append(new String(bytes, 0, length));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStreamClose(is);
            disconnect(conn);
        }
        return sb == null ? "" : sb.toString();
    }

    /**
     * request result byte[]
     *
     * @param urlStr
     * @param paramMap
     * @param headMap
     * @param requestMethod GET, POST, PATCH, DELETE, PUT, HEAD
     * @return
     */
    public byte[] requestBytes(String urlStr, HashMap<String, Object> paramMap, HashMap<String, String> headMap, String requestMethod) {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpURLConnection conn = null;
        InputStream is = null;
        int contentLength = -1;
        try {
            conn = httpURLConnection(urlStr, paramMap, headMap, requestMethod);
            if (conn != null) {
                contentLength = conn.getContentLength();
                is = conn.getInputStream();
                byte buffer[] = new byte[contentLength];
                int length = -1;
                while ((length = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
                bytes = baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            outputStreamClose(baos);
            inputStreamClose(is);
            disconnect(conn);
        }
        return bytes;
    }


    public void disconnect(HttpURLConnection conn) {
        if (conn != null) {
            conn.disconnect();
            conn = null;
        }
    }

    public void inputStreamClose(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            is = null;
        }
    }

    public void outputStreamClose(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            os = null;
        }
    }


    /**
     * 参数设置
     *
     * @param conn
     * @param paramMap
     * @return
     */
    private String setParam(HttpURLConnection conn, HashMap<String, Object> paramMap) {

        StringBuffer sb = new StringBuffer();
        if (paramMap != null) {

            for (Entry<String, Object> en : paramMap.entrySet()) {
                sb.append(en.getKey());
                sb.append("=");
                sb.append(en.getValue());
                sb.append("&");
            }
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

    /**
     * 参数设置
     *
     * @param conn
     * @param headMap
     * @return
     */
    private void setHead(HttpURLConnection conn, HashMap<String, String> headMap) {
        if (headMap != null) {
            StringBuffer sb = new StringBuffer();
            for (Entry<String, String> en : headMap.entrySet()) {
                conn.setRequestProperty(en.getKey(), en.getValue());
                sb.append(en.getKey());
                sb.append(":");
                sb.append(en.getValue());
                sb.append(",");
            }
        }
    }
}
