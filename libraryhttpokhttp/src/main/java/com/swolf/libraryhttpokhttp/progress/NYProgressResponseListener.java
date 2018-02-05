package com.swolf.libraryhttpokhttp.progress;

/**
 * UI进度请求监听
 * Created by LiuYi-15973602714
 */
public interface NYProgressResponseListener {
  void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
