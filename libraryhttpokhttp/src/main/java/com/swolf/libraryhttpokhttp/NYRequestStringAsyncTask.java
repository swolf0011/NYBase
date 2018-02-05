package com.swolf.libraryhttpokhttp;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * 请求
 * Created by LiuYi-15973602714
 */
public class NYRequestStringAsyncTask extends AsyncTask<Void, Void, String> {
    // 后台服务接口地址
    private String urlStr;
    // 请求参数
    private HashMap<String, Object> paramMap;
    private String paramJson = null;
    // head参数
    private HashMap<String, String> headMap;
    private NYRequest.EParamType paramType;
    private NYRequest.EMethod method;
    private NYIAsyncTaskCallback callback;
    private NYRequest request = null;
    private NYOkHttpClient nyOkHttpClient = null;


    public NYRequestStringAsyncTask(NYOkHttpClient nyOkHttpClient,
                                    String urlStr,
                                    String paramJson,
                                    HashMap<String, Object> paramMap,
                                    HashMap<String, String> headMap,
                                    NYRequest.EParamType paramType,
                                    NYRequest.EMethod method,
                                    NYIAsyncTaskCallback callback) {
        super();
        request = new NYRequest(nyOkHttpClient);
        init(urlStr, paramJson, paramMap, headMap, paramType, method, callback);
    }
    private void init(String urlStr,
                      String paramJson,
                      HashMap<String, Object> paramMap,
                      HashMap<String, String> headMap,
                      NYRequest.EParamType paramType,
                      NYRequest.EMethod method,
                      NYIAsyncTaskCallback callback) {
        this.urlStr = urlStr;
        this.paramJson = paramJson;
        this.paramMap = paramMap;
        this.headMap = headMap;
        this.paramType = paramType;
        this.method = method;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return request.request(urlStr, paramJson, paramMap, headMap, paramType, method);
    }

    @Override
    protected void onPostExecute(String result) {
        if (TextUtils.isEmpty(result)) {
            callback.exception("网络异常，请检查网络连接！");
        } else {
            callback.success(result);
        }
    }

    public interface NYIAsyncTaskCallback {

        /**
         * 结果成功的处理
         */
        public void success(String data);

        /**
         * 结果失败的处理
         */
        public void exception(String errerMsg);
    }
}
