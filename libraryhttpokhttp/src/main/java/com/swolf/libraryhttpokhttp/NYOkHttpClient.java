package com.swolf.libraryhttpokhttp;


import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import static okhttp3.internal.Util.UTF_8;
/**
 * Created by LiuYi-15973602714
 */
public class NYOkHttpClient {
    private OkHttpClient okHttpClient;
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
    private final String TAG = "NetWorkLogger";

    public NYOkHttpClient() {
        this(30 * 1000L, 60 * 3 * 1000L);
    }

    public NYOkHttpClient(long connection_timeout_milliseconds, long read_timeout_milliseconds) {
        this(connection_timeout_milliseconds, read_timeout_milliseconds,null);
    }
    public NYOkHttpClient(Interceptor[] interceptors) {
        this(30 * 1000L, 60 * 3 * 1000L,interceptors);
    }
    public NYOkHttpClient(long connection_timeout_milliseconds, long read_timeout_milliseconds, Interceptor[] interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connection_timeout_milliseconds, TimeUnit.MILLISECONDS)
                .readTimeout(read_timeout_milliseconds, TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .addInterceptor(cacheInterceptor);
        if (interceptors != null && interceptors.length > 0) {
            for (Interceptor item : interceptors) {
                builder.addInterceptor(item);
            }
        }
        okHttpClient = builder.build();
    }

    private Interceptor logInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            printRequestMessage(request);
            Response response = chain.proceed(request);
            printResponseMessage(response);
            return response;
        }
        /**
         * 打印请求消息
         */
        private void printRequestMessage(Request request) {
            if (request == null) {
                return;
            }
            Log.i(TAG, "Url   : " + request.url().url().toString());
            Log.i(TAG, "Method: " + request.method());
            Log.i(TAG, "Heads : " + request.headers());
            RequestBody requestBody = request.body();
            if (requestBody == null) {
                return;
            }
            try {
                Buffer bufferedSink = new Buffer();
                requestBody.writeTo(bufferedSink);
                Charset charset = requestBody.contentType().charset();
                charset = charset == null ? Charset.forName("utf-8") : charset;
                Log.i(TAG, "Params: " + bufferedSink.readString(charset));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 打印返回消息
         */
        private void printResponseMessage(Response response) {
            if (response == null || !response.isSuccessful()) {
                return;
            }
            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            try {
                source.request(Long.MAX_VALUE); // Buffer the entire body.
            } catch (IOException e) {
                e.printStackTrace();
            }
            Buffer buffer = source.buffer();
            Charset charset = UTF_8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset();
            }
            if (contentLength != 0) {
                String result = buffer.clone().readString(charset);
                Log.i(TAG, "Response: " + result);
            }
        }
    };
    private Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=" + 3600 * 6 + " ,max-stale=2419200";
            }
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }
    };
}

