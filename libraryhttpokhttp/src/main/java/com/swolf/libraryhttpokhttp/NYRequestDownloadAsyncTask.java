package com.swolf.libraryhttpokhttp;


import android.os.AsyncTask;


import com.swolf.libraryhttpokhttp.util.NYProgressListener;
import com.swolf.libraryhttpokhttp.util.NYRequestParamsUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求
 * Created by LiuYi-15973602714
 */
public class NYRequestDownloadAsyncTask extends AsyncTask<Void, Integer, Void> {
    // 后台服务接口地址
    private String serviceUrl;
    // 请求参数
    private HashMap<String, Object> paramMap;
    private String paramJson = null;
    // head参数
    private HashMap<String, String> headMap;
    private String filePath;
    private NYRequest.EMethod method;
    private NYProgressListener listener;
    private NYOkHttpSet nyOkHttpSet = null;

    public NYRequestDownloadAsyncTask(NYOkHttpSet nyOkHttpSet,String serviceUrl,
                                      HashMap<String, Object> paramMap,
                                      HashMap<String, String> headMap,
                                      String filePath,
                                      NYRequest.EMethod method,
                                      NYProgressListener listener) {

        super();
        this.nyOkHttpSet = nyOkHttpSet;
        this.serviceUrl = serviceUrl;
        this.paramMap = paramMap;
        this.headMap = headMap;
        this.filePath = filePath;
        this.method = method;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        downloadSync(serviceUrl, paramMap, headMap, filePath, method, listener);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.progress(values[0]);
        super.onProgressUpdate(values);
    }


    private void downloadSync(String urlStr, HashMap<String, Object> paramMap, HashMap<String, String> headMap,
                              String filePath, NYRequest.EMethod method, NYProgressListener listener) {
        Call call = null;
        Request request = null;
        FormBody.Builder fbBuilder = new FormBody.Builder();
        NYRequestParamsUtil rpu =new NYRequestParamsUtil();
        rpu.setParam(fbBuilder, paramMap);
        RequestBody requestBody = fbBuilder.build();
        Request.Builder rBuilder = new Request.Builder().url(urlStr);
        rpu.setHead(rBuilder, headMap);
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

        InputStream is = null;
        FileOutputStream fos = null;

        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            call = nyOkHttpSet.getOkHttpClient().newCall(request);
            final Response response = call.execute();

            is = response.body().byteStream();
            long contentLength = response.body().contentLength();
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int lenght = 0;
            long count = 0l;
            while ((lenght = is.read(buffer)) > 0) {
                fos.write(buffer, 0, lenght);
                count += lenght;
                int p = (int) (count * 100 / contentLength);
                if (p > 100) {
                    p = 100;
                }
                listener.progress(p);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }
        }
    }
}