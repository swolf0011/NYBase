package com.swolf.libraryhttpokhttp;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * 网络API
 * Created by 刘一 on 20170324
 */
public class NYOkHttpApi {
    private static final String TAG = "NYOkHttpApi";

    //回调Handler
    public interface IHandlerCallback {

        /**
         * 失败
         */
        void onFailure(IOException e, String msg);

        /**
         * 成功
         */
        void onResponse(String value);
    }

    /**
     * 将请求的消息返回主线程的handler
     */
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static NYOkHttpApi okHttpApi;

    private static class NYSubHolder {
        private static NYOkHttpApi util = new NYOkHttpApi();
    }

    public static NYOkHttpApi getInstance() {
        return NYOkHttpApi.NYSubHolder.util;
    }

    private NYOkHttpApi() {
    }

    /**
     * 请求
     */
    public String requestSync(String urlStr,
                              String paramJson,
                              HashMap<String, Object> paramMap,
                              HashMap<String, String> headMap,
                              NYRequest.EParamType paramType,
                              NYRequest.EMethod method) {

        log(urlStr, paramJson, paramMap, headMap);

        return new NYRequest(new NYOkHttpSet()).request(urlStr, paramJson, paramMap, headMap, paramType, method);
    }

    /**
     * 请求
     */
    public void requestAsync(String urlStr,
                             String paramJson,
                             HashMap<String, Object> paramMap,
                             HashMap<String, String> headMap,
                             NYRequest.EParamType paramType,
                             NYRequest.EMethod method,
                             final IHandlerCallback handlerCallback) {

        log(urlStr, paramJson, paramMap, headMap);

        new NYRequest(new NYOkHttpSet()).request(urlStr, paramJson, paramMap, headMap, paramType, method, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (handlerCallback != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            handlerCallback.onFailure(e, "请求超时!");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d(TAG, "response:" + result);
                if (handlerCallback != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            handlerCallback.onResponse(result);
                        }
                    });
                }
            }
        });
    }


    private void log(String urlStr, String paramJson, HashMap<String, Object> paramMap, HashMap<String, String> headMap) {
        Log.d(TAG, "urlStr:" + urlStr);
        Log.d(TAG, "paramJson:" + paramJson);
        StringBuffer sb1 = new StringBuffer("{");
        if (paramMap != null) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()
                    ) {

                sb1.append(entry.getKey());
                sb1.append(":");
                sb1.append(entry.getValue());
                sb1.append(",");
            }
        }
        sb1.append("}");

        Log.d(TAG, "paramMap:" + sb1.toString());

        StringBuffer sb2 = new StringBuffer("{");
        if (headMap != null) {
            for (Map.Entry<String, String> entry : headMap.entrySet()
                    ) {

                sb2.append(entry.getKey());
                sb2.append(":");
                sb2.append(entry.getValue());
                sb2.append(",");
            }
        }
        sb2.append("}");
        Log.d(TAG, "headMap:" + sb2.toString());
    }
}
