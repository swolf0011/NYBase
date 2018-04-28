package com.swolf.librarybase.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;

/**
 * 手机文件工具类
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYPhoneFileUtil {
    private String name;
    private Context context;

    public NYPhoneFileUtil(String name, Context context) {
        super();
        this.name = name;
        this.context = context;
    }

    public boolean save(String content) {
        FileOutputStream fileOutputStream = null;
        PrintStream printStream = null;
        try {
            fileOutputStream = context.openFileOutput(name, Activity.MODE_PRIVATE);
            printStream = new PrintStream(fileOutputStream);
            printStream.print(content);
            printStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (printStream != null) {
                    printStream.close();
                    printStream = null;
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    fileOutputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean clear() {
        FileOutputStream fileOutputStream = null;
        PrintStream printStream = null;
        try {
            fileOutputStream = context.openFileOutput(name, Activity.MODE_PRIVATE);
            printStream = new PrintStream(fileOutputStream);
            printStream.print("");
            printStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (printStream != null) {
                    printStream.close();
                    printStream = null;
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    fileOutputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean del() {
        return context.deleteFile(name);
    }

    public String getData() {
        StringBuffer result = new StringBuffer();
        FileInputStream fileInputStream = null;
        Scanner scanner = null;
        try {
            fileInputStream = context.openFileInput(name);
            scanner = new Scanner(fileInputStream);
            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (scanner != null) {
                    scanner.close();
                    scanner = null;
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                    fileInputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

}
