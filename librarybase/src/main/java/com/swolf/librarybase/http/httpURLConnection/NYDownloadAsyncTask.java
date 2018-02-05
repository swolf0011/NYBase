package com.swolf.librarybase.http.httpURLConnection;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * 下载
 * Created by LiuYi-15973602714
 */
public class NYDownloadAsyncTask extends AsyncTask<Void, Integer, Boolean> {
    // 后台服务接口地址
    private String urlStr;
    // 请求参数
    private HashMap<String, Object> paramMap;
    // head参数
    private HashMap<String, String> headMap;

    private long beginIndex;
    private IProgressCallback callback;
    private boolean isNew;
    private String filePath;
    private long fileLength;
    private String requestMethod;


    public NYDownloadAsyncTask(String urlStr,
                               HashMap<String, Object> paramMap,
                               HashMap<String, String> headMap,
                               String requestMethod,
                               String filePath,
                               IProgressCallback callback) {
        this(urlStr, paramMap, headMap, requestMethod, filePath, 0, 0, true, callback);
    }


    public NYDownloadAsyncTask(String urlStr,
                               HashMap<String, Object> paramMap,
                               HashMap<String, String> headMap,
                               String requestMethod,
                               String filePath,
                               long fileLength,
                               long beginIndex,
                               boolean isNew,
                               IProgressCallback callback) {
        super();
        this.urlStr = urlStr;
        this.paramMap = paramMap;
        this.headMap = headMap;
        this.beginIndex = beginIndex;
        this.callback = callback;
        this.isNew = isNew;
        this.filePath = filePath;
        this.fileLength = fileLength;
        this.requestMethod = requestMethod;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        if (fileLength > 0) {
            return download(urlStr, paramMap, headMap, filePath, fileLength, beginIndex, isNew, requestMethod);
        } else {
            return download(urlStr, paramMap, headMap, filePath, 0, 0, true, requestMethod);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        callback.progress(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if (b) {
            callback.success("下载成功");
        } else {
            callback.exception("网络异常，请检查网络连接，重新下载。");
        }
    }

    public interface IProgressCallback {
        /**
         * 进度
         */
        void progress(int progress);

        /**
         * 结果成功的处理
         */
        void success(String result);

        /**
         * 结果失败的处理
         */
        void exception(String errerMsg);
    }

    /**
     * @param urlStr
     * @param paramMap
     * @param headMap
     * @param filePath
     * @param fileLength
     * @param beginIndex
     * @param isNew
     * @param requestMethod POST|GET
     * @return
     */
    @SuppressWarnings("resource")
    private boolean download(String urlStr, HashMap<String, Object> paramMap, HashMap<String, String> headMap, String filePath, long fileLength, long beginIndex, boolean isNew, String requestMethod) {
        InputStream is = null;
        FileOutputStream fos = null;
        HttpURLConnection conn = null;
        File file = new File(filePath);
        if (isNew && file.exists()) {
            file.delete();
            beginIndex = 0;
        }
        long size = beginIndex;
        if (fileLength > 0) {
            this.publishProgress((int) (size * 100 / fileLength));
        }
        try {
            fos = new FileOutputStream(file);
            conn = NYURLConnectionUtil.getInstance().httpURLConnection(urlStr, paramMap, headMap, requestMethod);
            if (conn != null) {
                byte[] bytes = new byte[1024 * 4];
                is = conn.getInputStream();
                int length = -1;
                while ((length = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, length);
                    size += length;
                    if (fileLength > 0) {
                        this.publishProgress((int) (size * 100 / fileLength));
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    is.close();
                    is = null;
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            NYURLConnectionUtil.getInstance().disconnect(conn);
        }
        return false;
    }
}
