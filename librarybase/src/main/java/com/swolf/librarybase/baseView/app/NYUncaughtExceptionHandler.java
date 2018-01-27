package com.swolf.librarybase.baseView.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 异常处理
 * Created by LiuYi-15973602714
 */
public class NYUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static NYUncaughtExceptionHandler cauchExceptionHandler = null;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
    private String logErrorDirPath = null;
    private Thread.UncaughtExceptionHandler handler;

    private NYUncaughtExceptionHandler(Context context) {
        handler = Thread.getDefaultUncaughtExceptionHandler();
        logErrorDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + context.getPackageName() +
                File.separator + "errorlog";
        File file = new File(logErrorDirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static NYUncaughtExceptionHandler getInstance(Context context) {
        if (cauchExceptionHandler == null) {
            synchronized (NYUncaughtExceptionHandler.class) {
                if (cauchExceptionHandler == null) {
                    cauchExceptionHandler = new NYUncaughtExceptionHandler(context);
                }
            }
        }
        return cauchExceptionHandler;
    }

    public void setDefaultUnCachExceptionHandler() { //在Application开始时调用
        Thread.setDefaultUncaughtExceptionHandler(this); //设置应用默认的全局捕获异常器
    }

    @SuppressLint("LongLogTag")
    @Override //应用没有捕抓的异常会到这里来,如果我们设置了应用的默认全局捕抓异常为CauchExceptionHandler的话
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.d("NYUncaughtExceptionHandler", throwable.getMessage()); //异常信息
        //可以上传错误日志
        if (!handleException(throwable) && handler != null) {
            handler.uncaughtException(thread, throwable);
        } else {
            //系统退出，kill进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 处理异常信息
     *
     * @param throwable
     */
    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        // 保存日志文件
        saveErrorInfoToFile(throwable);
        return true;
    }

    /**
     * 保存错误信息到sd中
     *
     * @param throwable
     */
    private void saveErrorInfoToFile(Throwable throwable) {
        FileOutputStream fos = null;
        StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String result = writer.toString();
        sb.append(result);
        try {
            String time = formatter.format(new Date());
            String fileName = logErrorDirPath + File.separator + "error-" + time + ".txt";
            File f = new File(fileName);
            if (!f.getParentFile().exists() || !f.getParentFile().isDirectory()) {
                f.getParentFile().mkdirs();
            }
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            fos = new FileOutputStream(f);
            fos.write(sb.toString().getBytes());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
