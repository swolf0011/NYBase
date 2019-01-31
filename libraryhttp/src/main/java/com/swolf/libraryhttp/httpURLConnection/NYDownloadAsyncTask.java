package com.swolf.libraryhttp.httpURLConnection;

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
    private String urlStr;
    private String paramJson;
    private HashMap<String, Object> paramMap;
    private HashMap<String, String> headMap;
    private EParamType paramType;
    private EMethod method;
    private String filePath;
    private long fileLength;
    private long beginIndex;
    private boolean isNew;

    private IProgressCallback callback;

    public NYDownloadAsyncTask(String urlStr,
                               String paramJson,
                               HashMap<String, Object> paramMap,
                               HashMap<String, String> headMap,
                               EParamType paramType,
                               EMethod method,
                               String filePath,
                               IProgressCallback callback) {
        this(urlStr, paramJson, paramMap, headMap, paramType, method, filePath, 0, 0, true, callback);
    }


    public NYDownloadAsyncTask(String urlStr,
                               String paramJson,
                               HashMap<String, Object> paramMap,
                               HashMap<String, String> headMap,
                               EParamType paramType,
                               EMethod method,
                               String filePath,
                               long fileLength,
                               long beginIndex,
                               boolean isNew,
                               IProgressCallback callback) {
        super();
        this.urlStr = urlStr;
        this.paramJson = paramJson;
        this.paramMap = paramMap;
        this.headMap = headMap;
        this.paramType = paramType;
        this.method = method;
        this.filePath = filePath;
        this.fileLength = fileLength;
        this.beginIndex = beginIndex;
        this.isNew = isNew;
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        if (fileLength > 0) {
            return download(urlStr, paramJson, paramMap, headMap,
                    paramType, method, filePath, fileLength, beginIndex, isNew);
        } else {
            return download(urlStr, paramJson, paramMap, headMap,
                    paramType, method, filePath, 0, 0, true);
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
     * @return
     */
    @SuppressWarnings("resource")
    private boolean download(
            String urlStr,
            String paramJson,
            HashMap<String, Object> paramMap,
            HashMap<String, String> headMap,
            EParamType paramType,
            EMethod method,
            String filePath,
            long fileLength,
            long beginIndex,
            boolean append) {
        InputStream is = null;
        FileOutputStream fos = null;
        HttpURLConnection conn = null;
        File file = new File(filePath);
        long size = beginIndex;
        if (fileLength > 0) {
            this.publishProgress((int) (size * 100 / fileLength));
        }
        try {
            fos = new FileOutputStream(file,append);
            conn = NYURLConnectionUtil.getInstance().httpURLConnection(urlStr, paramJson, paramMap, headMap, paramType, method);
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
