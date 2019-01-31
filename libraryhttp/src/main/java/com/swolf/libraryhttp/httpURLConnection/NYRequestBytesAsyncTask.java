package com.swolf.libraryhttp.httpURLConnection;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * 请求
 * Created by LiuYi-15973602714
 */
public class NYRequestBytesAsyncTask extends AsyncTask<Void, Void, byte[]> {
    private String urlStr;
    private String paramJson;
    private HashMap<String, Object> paramMap;
    private HashMap<String, String> headMap;
    private EParamType paramType;
    private EMethod method;
    private NYIAsyncTaskCallback callback;

    public NYRequestBytesAsyncTask(String urlStr,
                                    String paramJson,
                                    HashMap<String, Object> paramMap,
                                    HashMap<String, String> headMap,
                                    EParamType paramType,
                                    EMethod method,
                                    NYIAsyncTaskCallback callback) {
        super();
        this.urlStr = urlStr;
        this.paramJson = paramJson;
        this.paramMap = paramMap;
        this.headMap = headMap;
        this.paramType = paramType;
        this.method = method;
        this.callback = callback;
    }

    @Override
    protected byte[] doInBackground(Void... voids) {
        return NYURLConnectionUtil.getInstance().requestBytes(urlStr,paramJson, paramMap, headMap, paramType,method);
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
