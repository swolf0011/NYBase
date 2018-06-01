package com.swolf.libraryhttp.httpURLConnection;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 上传
 * Created by LiuYi-15973602714
 */
public class NYUploadAsyncTask extends AsyncTask<Void, Integer, String> {
    public int connection_timeout = 1000 * 30;// 连接超时
    public int read_timeout = 1000 * 60;// 读取超时
    public String charsetName = "UTF-8";// 字符集

    // 后台服务接口地址
    private String urlStr;
    // 请求参数
    private HashMap<String, Object> paramMap;
    // head参数
    private HashMap<String, String> headMap;

    private String fileKey;
    private String filePath;
    private long fileLength;
    private long begin;
    private IProgressCallback callback;

    /**
     * 上传文件，只能会提交，不能断点提交。
     */
    /**
     * 上传文件
     *
     * @param urlStr
     * @param paramMap
     * @param headMap
     * @param fileKey    文件Key（服务器接受的key）
     * @param filePath   上传的文件路径
     * @param fileLength 上传的文件总长度
     * @param begin      开始上传的位置
     * @param callback
     */
    public NYUploadAsyncTask(String urlStr,
                             HashMap<String, Object> paramMap,
                             HashMap<String, String> headMap,
                             String fileKey,
                             String filePath,
                             long fileLength,
                             long begin,
                             IProgressCallback callback) {
        super();
        this.urlStr = urlStr;
        this.paramMap = paramMap;
        this.headMap = headMap;
        this.fileKey = fileKey;
        this.filePath = filePath;
        this.fileLength = fileLength;
        this.begin = begin;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return upload(urlStr, paramMap, headMap, fileKey, filePath, fileLength, begin);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        callback.progress(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null && result.length() > 0) {
            callback.success(result);
        } else {
            callback.exception("网络异常，请检查网络连接，重新上传。");
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


    private String upload(String urlStr,
                          HashMap<String, Object> paramMap,
                          HashMap<String, String> headMap,
                          String fileKey,
                          String filePath,
                          long fileLength,
                          long begin) {
        StringBuffer result = new StringBuffer();
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--";
        String LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型


        byte[] end_data_line = LINE_END.getBytes();
        fileLength += end_data_line.length;
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
        fileLength += end_data.length;
        int progress = 0;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        InputStream is = null;
        RandomAccessFile raf = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST"); // 请求方式
            conn.setReadTimeout(read_timeout);
            conn.setConnectTimeout(connection_timeout);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestProperty("Charset", charsetName); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            setHead(conn, headMap);

            StringBuffer textSb = new StringBuffer();
            if (paramMap != null) {
                for (Map.Entry<String, Object> en : paramMap.entrySet()) {
                    textSb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    textSb.append("Content-Disposition: form-data; name=\"" + en.getKey() + "\"" + LINE_END);
                    textSb.append("Content-Type: text/plain; charset=" + charsetName + LINE_END);
                    textSb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
                    textSb.append(LINE_END);
                    textSb.append(en.getValue());
                    textSb.append(LINE_END);
                }
            }

            dos = new DataOutputStream(conn.getOutputStream());
            if (dos != null) {
                fileLength += textSb.toString().getBytes().length;
                dos.write(textSb.toString().getBytes());

                File file = new File(filePath);
                if (file != null && file.exists()) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINE_END);
                    /**
                     * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                     * filename是文件的名字，包含后缀名的 比如:abc.png
                     */
                    sb.append("Content-Disposition: form-data; name=\"" + fileKey + "\"; filename=\"" + file.getName() + "\"" + LINE_END);
                    sb.append("Content-Type: application/octet-stream; charset=" + charsetName + LINE_END);
                    sb.append(LINE_END);

                    fileLength += sb.toString().getBytes().length;
                    dos.write(sb.toString().getBytes());

                    raf = new RandomAccessFile(file, "r");
                    raf.seek(begin);
                    progress = (int) ((begin * 100) / fileLength);
                    this.publishProgress(progress);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = raf.read(bytes)) != -1) {
                        dos.write(bytes, 0, len);
                        begin += len;
                        progress = (int) ((begin * 100) / fileLength);
                        this.publishProgress(progress);
                    }
                    dos.write(end_data_line);
                    begin += end_data_line.length;
                    progress = (int) ((begin * 100) / fileLength);
                    this.publishProgress(progress);
                }
                dos.write(end_data);
                dos.flush();
                begin += end_data_line.length;
                progress = (int) ((begin * 100) / fileLength);
                this.publishProgress(progress);
            }
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            if (res != 200) {
                disconnect(conn);
            } else {
                byte[] bytes2 = new byte[1024 * 4];
                is = conn.getInputStream();
                int length = -1;
                while ((length = is.read(bytes2)) != -1) {
                    result.append(new String(bytes2, 0, length));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new StringBuffer();
        } finally {
            randomAccessFileClose(raf);
            inputStreamClose(is);
            outputStreamClose(dos);
            disconnect(conn);
        }
        return result.toString();
    }


    public void disconnect(HttpURLConnection conn) {
        if (conn != null) {
            conn.disconnect();
            conn = null;
        }
    }

    public void randomAccessFileClose(RandomAccessFile raf) {
        if (raf != null) {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            raf = null;
        }
    }

    public void inputStreamClose(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            is = null;
        }
    }

    public void outputStreamClose(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            os = null;
        }
    }


    /**
     * 参数设置
     *
     * @param conn
     * @param paramMap
     * @return
     */
    private String setParam(HttpURLConnection conn, HashMap<String, Object> paramMap) {

        StringBuffer sb = new StringBuffer();
        if (paramMap != null) {

            for (Map.Entry<String, Object> en : paramMap.entrySet()) {
                sb.append(en.getKey());
                sb.append("=");
                sb.append(en.getValue());
                sb.append("&");
            }
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

    /**
     * 参数设置
     *
     * @param conn
     * @param headMap
     * @return
     */
    private void setHead(HttpURLConnection conn, HashMap<String, String> headMap) {
        if (headMap != null) {
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> en : headMap.entrySet()) {
                conn.setRequestProperty(en.getKey(), en.getValue());
                sb.append(en.getKey());
                sb.append(":");
                sb.append(en.getValue());
                sb.append(",");
            }
        }
    }
}
