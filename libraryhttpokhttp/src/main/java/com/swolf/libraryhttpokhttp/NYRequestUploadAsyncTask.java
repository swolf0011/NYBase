package com.swolf.libraryhttpokhttp;


import android.os.AsyncTask;


import com.swolf.libraryhttpokhttp.progress.NYProgressHelper;
import com.swolf.libraryhttpokhttp.progress.NYProgressRequestBody;
import com.swolf.libraryhttpokhttp.progress.NYUIProgressRequestListener;
import com.swolf.libraryhttpokhttp.util.NYRequestParamsUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 请求
 * Created by LiuYi-15973602714
 */
public class NYRequestUploadAsyncTask extends AsyncTask<Void, Void, Call> {
    // 后台服务接口地址
    private String serviceUrl;
    // 请求参数
    private HashMap<String, Object> paramMap;
    // head参数
    private HashMap<String, String> headMap;
    private String fileParamKey;
    private String filePath;
    private Callback callback;
    private NYUIProgressRequestListener listener;
    private NYOkHttpSet nyOkHttpSet = null;


    public NYRequestUploadAsyncTask(NYOkHttpSet nyOkHttpSet,String serviceUrl, HashMap<String, Object> paramMap, HashMap<String, String> headMap,
                                    String fileParamKey, String filePath, Callback callback, NYUIProgressRequestListener listener) {
        super();
        this.nyOkHttpSet = nyOkHttpSet;
        this.serviceUrl = serviceUrl;
        this.paramMap = paramMap;
        this.headMap = headMap;
        this.fileParamKey = fileParamKey;
        this.filePath = filePath;
        this.callback = callback;
        this.listener = listener;
    }

    @Override
    protected Call doInBackground(Void... voids) {
        Call call = uploadAsync(serviceUrl, paramMap, headMap, fileParamKey, filePath, callback, listener);
        return call;
    }


    /**
     * @param urlStr
     * @param paramMap
     * @param headMap
     * @param fileParamKey 与服务器保持一致
     * @param filePath
     * @param callback
     * @param listener
     * @return
     */
    private Call uploadAsync(String urlStr, HashMap<String, Object> paramMap, HashMap<String, String> headMap, String fileParamKey, String filePath, Callback callback, NYUIProgressRequestListener listener) {
        File file = new File(filePath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Builder fbBuilder = new MultipartBody.Builder();
        fbBuilder.setType(MultipartBody.FORM);
        if (paramMap != null) {
            for (Map.Entry<String, Object> en : paramMap.entrySet()) {
                fbBuilder.addFormDataPart(en.getKey(), en.getValue() + "");
            }
        }
        fbBuilder.addFormDataPart(fileParamKey, file.getName(), fileBody);
        RequestBody requestBody = fbBuilder.build();

        Request.Builder rBuilder = new Request.Builder().url(urlStr);
        new NYRequestParamsUtil().setHead(rBuilder, headMap);
        NYProgressRequestBody progressRequestBody = NYProgressHelper.addProgressRequestListener(requestBody, listener);

        Request request = new Request.Builder()
                .url(urlStr)
                .post(progressRequestBody)
                .build();

        Call call = nyOkHttpSet.getOkHttpClient().newCall(request);
        call.enqueue(callback);
        return call;
    }

}