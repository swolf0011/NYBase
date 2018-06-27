package com.swolf.libraryhttpokhttp;


import com.swolf.libraryhttpokhttp.progress.NYProgressHelper;
import com.swolf.libraryhttpokhttp.progress.NYProgressRequestBody;
import com.swolf.libraryhttpokhttp.progress.NYUIProgressRequestListener;
import com.swolf.libraryhttpokhttp.util.NYRequestParamsUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
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
    private NYOkHttpSet nyOkHttpSet;

    public NYRequest(NYOkHttpSet nyOkHttpSet) {
        this.nyOkHttpSet = nyOkHttpSet;
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
        Call call = nyOkHttpSet.getOkHttpClient().newCall(request);
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
        Call call = nyOkHttpSet.getOkHttpClient().newCall(request);
        call.enqueue(callback);
        return call;
    }


    /**
     * @param urlStr
     * @param headMap
     * @param filePath
     * @return
     */
    public Call uploadAsync(String urlStr, HashMap<String, String> headMap,
                            String filePath,  Callback callback) {
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        Request.Builder rBuilder = new Request.Builder().url(urlStr);
        new NYRequestParamsUtil().setHead(rBuilder, headMap);
        Request request = new Request.Builder().url(urlStr)
                .post(requestBody).build();
        Call call = nyOkHttpSet.getOkHttpClient().newCall(request);
        call.enqueue(callback);
        return call;
    }


    /**
     * @param urlStr
     * @param paramMap
     * @param headMap
     * @param fileParamKey 与服务器保持一致
     * @param filePath
     * @return
     */

    public Call uploadAsync(String urlStr, HashMap<String, Object> paramMap, HashMap<String, String> headMap,
                            String fileParamKey, String filePath, Callback callback) {

        File file = new File(filePath);
        String filename = file.getName();


        /**
         * 上传文件格式
         */
        RequestBody fileBody = MultipartBody.create(MediaType.parse("application/octet-stream"), file);//将file转换成RequestBody文件
        MultipartBody.Builder fbBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (paramMap != null) {
            for (Map.Entry<String, Object> en : paramMap.entrySet()) {
                fbBuilder.addFormDataPart(en.getKey(), en.getValue() + "");
            }
        }
        RequestBody requestBody = fbBuilder.addFormDataPart(fileParamKey, filename, fileBody).build();

        Request.Builder rBuilder = new Request.Builder();
        new NYRequestParamsUtil().setHead(rBuilder, headMap);

        Request request = rBuilder.url(urlStr).post(requestBody).build();
        Call call = nyOkHttpSet.getOkHttpClient().newCall(request);
        call.enqueue(callback);
        return call;


    }


}