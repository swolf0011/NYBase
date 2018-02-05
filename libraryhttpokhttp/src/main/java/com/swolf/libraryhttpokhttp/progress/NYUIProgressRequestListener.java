package com.swolf.libraryhttpokhttp.progress;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * UI进度请求监听
 * Created by LiuYi-15973602714
 */
public abstract class NYUIProgressRequestListener implements NYProgressRequestListener {
    private static final int REQUEST_UPDATE = 0x01;

    //处理UI层的Handler子类
    private static class UIHandler extends Handler {
        //弱引用
        private final WeakReference<NYUIProgressRequestListener> mUIProgressRequestListenerWeakReference;

        public UIHandler(Looper looper, NYUIProgressRequestListener uiProgressRequestListener) {
            super(looper);
            mUIProgressRequestListenerWeakReference = new WeakReference(uiProgressRequestListener);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE:
                    NYUIProgressRequestListener uiProgressRequestListener = mUIProgressRequestListenerWeakReference.get();
                    if (uiProgressRequestListener != null) {
                        //获得进度实体类
                        NYProgressModel progressModel = (NYProgressModel) msg.obj;
                        //回调抽象方法
                        uiProgressRequestListener.onUIRequestProgress(progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone());
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    //主线程Handler
    private final Handler mHandler = new UIHandler(Looper.getMainLooper(), this);

    @Override
    public void onRequestProgress(long bytesRead, long contentLength, boolean done) {
        //通过Handler发送进度消息
        Message message = Message.obtain();
        message.obj = new NYProgressModel(bytesRead, contentLength, done);
        message.what = REQUEST_UPDATE;
        mHandler.sendMessage(message);
    }

    /**
     * UI层回调抽象方法
     *
     * @param bytesWrite    当前写入的字节长度
     * @param contentLength 总字节长度
     * @param done          是否写入完成
     */
    public abstract void onUIRequestProgress(long bytesWrite, long contentLength, boolean done);
}
