package com.swolf.libraryhttpokhttp;



import com.swolf.libraryhttpokhttp.util.NYRequestParamsUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求
 * Created by LiuYi-15973602714
 */
public class NYRequest {
    public enum EParamType {
        JSONStr, MAP
    }
    public enum EMethod {
        GET, POST, PATCH, DELETE, PUT, HEAD
    }

    public MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private NYOkHttpClient nyOkHttpClient;

    public NYRequest(NYOkHttpClient nyOkHttpClient) {
        this.nyOkHttpClient = nyOkHttpClient;
    }


    public String request(String urlStr,
                          String paramJson,
                          HashMap<String, Object> paramMap,
                          HashMap<String, String> headMap,
                          EParamType paramType,
                          EMethod method) {
        String result = "";

        Request request = null;
        RequestBody requestBody = null;

        Request.Builder rBuilder = new Request.Builder().url(urlStr);
        NYRequestParamsUtil rpu = new NYRequestParamsUtil();
        rpu.setHead(rBuilder, headMap);
        FormBody.Builder fbBuilder = new FormBody.Builder();
        rpu.setParam(fbBuilder, paramMap);
        requestBody = fbBuilder.build();
        if (EParamType.JSONStr.equals(paramType)) {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, paramJson);
        }
        switch (method) {
            case GET:
                request = rBuilder.get().build();
                break;
            case HEAD:
                request = rBuilder.head().build();
                break;
            case POST:
                request = rBuilder.post(requestBody).build();
                break;
            case PATCH:
                request = rBuilder.patch(requestBody).build();
                break;
            case DELETE:
                request = rBuilder.delete(requestBody).build();
                break;
            case PUT:
                request = rBuilder.put(requestBody).build();
                break;
            default:
                request = rBuilder.build();
                break;
        }
        Call call = nyOkHttpClient.getOkHttpClient().newCall(request);
        try {
            Response response = call.execute();
            if (response != null && response.isSuccessful()) {
                try {
                    result = response.body().string();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Call request(String urlStr,
                        String paramJson,
                        HashMap<String, Object> paramMap,
                        HashMap<String, String> headMap,
                        EParamType paramType,
                        EMethod method,
                        Callback callback) {
        Request request = null;
        RequestBody requestBody = null;
        Request.Builder rBuilder = new Request.Builder().url(urlStr);
        NYRequestParamsUtil rpu = new NYRequestParamsUtil();
        rpu.setHead(rBuilder, headMap);
        FormBody.Builder fbBuilder = new FormBody.Builder();
        rpu.setParam(fbBuilder, paramMap);
        requestBody = fbBuilder.build();
        if (EParamType.JSONStr.equals(paramType)) {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, paramJson);
        }
        switch (method) {
            case GET:
                request = rBuilder.get().build();
                break;
            case HEAD:
                request = rBuilder.head().build();
                break;
            case POST:
                request = rBuilder.post(requestBody).build();
                break;
            case PATCH:
                request = rBuilder.patch(requestBody).build();
                break;
            case DELETE:
                request = rBuilder.delete(requestBody).build();
                break;
            case PUT:
                request = rBuilder.put(requestBody).build();
                break;
            default:
                request = rBuilder.build();
                break;
        }
        Call call = nyOkHttpClient.getOkHttpClient().newCall(request);
        call.enqueue(callback);
        return call;
    }

}