package com.swolf.libraryhttpokhttp.progress;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 进度帮助
 * Created by LiuYi-15973602714
 */
public class NYProgressHelper {
    /**
     * 包装OkHttpClient，用于下载文件的回调
     *
     * @param client           待包装的OkHttpClient
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient，使用clone方法返回
     */
    public static OkHttpClient addProgressResponseListener(OkHttpClient client, final NYProgressResponseListener progressListener) {
        //克隆
        OkHttpClient clone = client;
        //增加拦截器
        clone.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new NYProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        });
        return clone;
    }

    /**
     * 包装请求体用于上传文件的回调
     *
     * @param requestBody             请求体RequestBody
     * @param progressRequestListener 进度回调接口
     * @return 包装后的进度回调请求体
     */
    public static NYProgressRequestBody addProgressRequestListener(RequestBody requestBody, NYProgressRequestListener progressRequestListener) {
        //包装请求体
        return new NYProgressRequestBody(requestBody, progressRequestListener);
    }
}
