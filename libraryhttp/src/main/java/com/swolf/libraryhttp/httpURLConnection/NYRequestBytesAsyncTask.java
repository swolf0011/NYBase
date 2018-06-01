package com.swolf.libraryhttp.httpURLConnection;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * 请求
 * Created by LiuYi-15973602714
 */
public class NYRequestBytesAsyncTask extends AsyncTask<Void, Void, byte[]> {
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
    public NYRequestBytesAsyncTask(String serviceUrl,
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
    protected byte[] doInBackground(Void... voids) {
        return NYURLConnectionUtil.getInstance().requestBytes(serviceUrl, paramMap, heads, requestMethod);
    }

    @Override
    protected void onPostExecute(byte[] result) {
        if (result != null && result.length > 0) {
            callback.success(result);
        } else {
            callback.exception("网络异常，请检查网络连接！");
        }
    }

    public interface NYIAsyncTaskCallback {
        public void success(byte[] result);

        public void exception(String errerMsg);
    }
}
