package com.swolf.libraryhttp.httpURLConnection;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * 请求
 * Created by LiuYi-15973602714
 */
public class NYRequestStringAsyncTask extends AsyncTask<Void, Void, String> {
    // 后台服务接口地址
    private String serviceUrl;
    // 请求参数
    private HashMap<String, Object> paramMap;
    // head参数
    private HashMap<String, String> heads;
    private String requestMethod;
    private NYIAsyncTaskCallback callback;

    /**
     * @param serviceUrl
     * @param paramMap
     * @param heads
     * @param requestMethod GET, POST, PATCH, DELETE, PUT, HEAD
     * @param callback
     */
    public NYRequestStringAsyncTask(String serviceUrl,
                                    HashMap<String, Object> paramMap,
                                    HashMap<String, String> heads,
                                    String requestMethod,
                                    NYIAsyncTaskCallback callback) {
        super();
        this.serviceUrl = serviceUrl;
        this.paramMap = paramMap;
        this.heads = heads;
        this.requestMethod = requestMethod;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return NYURLConnectionUtil.getInstance().requestStr(serviceUrl, paramMap, heads, requestMethod);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null && result.length() > 0) {
            callback.success(result);
        } else {
            callback.exception("网络异常，请检查网络连接！");
        }
    }

    public interface NYIAsyncTaskCallback {
        public void success(String result);

        public void exception(String errerMsg);
    }
}
