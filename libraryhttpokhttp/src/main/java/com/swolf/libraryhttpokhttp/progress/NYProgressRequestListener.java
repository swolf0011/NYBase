package com.swolf.libraryhttpokhttp.progress;

/**
 * 进度请求监听
 * Created by LiuYi-15973602714
 */
public interface NYProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
