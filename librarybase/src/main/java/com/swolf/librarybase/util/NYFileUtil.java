package com.swolf.librarybase.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * 文件工具类
 * Created by LiuYi-15973602714
 */
public class NYFileUtil {

    /**
     * SDCard /mnt/sdcard/1
     */
    public String SDPATH = "";

    private String charset = "UTF-8";

    private static class NYSubHolder{
        private static NYFileUtil util = new NYFileUtil();
    }

    private NYFileUtil(){
        if (SDCardExist()) {
            SDPATH = Environment.getExternalStorageDirectory() + File.separator;
        } else {
            SDPATH = "";
        }
    }

    public static NYFileUtil getInstance(){
        return NYFileUtil.NYSubHolder.util;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }


    /**
     * SDCard exist.true|false
     */
    public boolean SDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public File getFile(String path){
        return new File(SDPATH + path);
    }
    /**
     * creatFile. 0：failure；1：succeed
     */
    public int createFile(String filePath) {
        int state = 0;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        if (!(file.getParentFile().exists())) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
            state = 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return state;
    }

    /**
     * creatDir.0：failure；1：succeed；2：exist
     */
    public int creatDir(String dirPath) {
        File dir = new File(dirPath);
        if (dir.exists()) {
            return 2;
        }
        return dir.mkdirs()?1:0;
    }

    /**
     * isFileExist
     */
    public boolean isFileExist(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * readString
     */
    public String readString(String filePath) {
        StringBuffer result = new StringBuffer("");
        if (isFileExist(filePath)) {
            FileInputStream fileInputStream = (FileInputStream) readInputStream(filePath);
            try {
                if (fileInputStream != null) {
                    byte buffer[] = new byte[4 * 1024];
                    int length = -1;
                    while ((length = fileInputStream.read(buffer)) != -1) {
                        result.append(new String(buffer, 0, length, charset));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeInputStream(fileInputStream);
            }
        }
        return result.toString();
    }

    /**
     * readString
     */
    public static String readString(InputStream is) {
        StringBuffer result = new StringBuffer("");
        try {
            if (is != null) {
                byte buffer[] = new byte[4 * 1024];
                int length = -1;
                while ((length = is.read(buffer)) != -1) {
                    result.append(new String(buffer, 0, length, "UTF-8"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    private void closeInputStream(InputStream inputStream){
        try {
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void closeOutputStream(OutputStream outputStream){
        try {
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * readBitmap
     */
    public Bitmap readBitmap(String filePath) {
        return readBitmap(filePath, 1);
    }

    /**
     * readBitmap
     */
    public Bitmap readBitmap(String filePath, int inSampleSize) {
        Bitmap bitmap = null;
        if (isFileExist(filePath)) {
            Options options = new Options();
            // options.inSampleSize = 4;// Bitmap is 1/4 size
            if (inSampleSize > 0) {
                options.inSampleSize = inSampleSize;
            }
            bitmap = BitmapFactory.decodeFile(filePath, options);
        }
        return bitmap;
    }

    /**
     * readInputStream
     */
    public InputStream readInputStream(String filePath) {
        FileInputStream fileInputStream = null;
        try {
            if (isFileExist(filePath)) {
                File file = new File(filePath);
                fileInputStream = new FileInputStream(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                fileInputStream = null;
            }
        }
        return fileInputStream;
    }

    /**
     * readByteArray
     */
    public byte[] readByteArray(String filePath) {
        byte buffer[] = null;
        FileInputStream fileInputStream = (FileInputStream) readInputStream(filePath);
        try {
            if (fileInputStream != null) {
                File file = new File(filePath);
                buffer = new byte[(int) file.length()];
                fileInputStream.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeInputStream(fileInputStream);
        }
        return buffer;
    }

    /**
     * readByteArray
     */
    public byte[] readByteArray(String filePath, long start, int length) {
        RandomAccessFile raf = null;
        byte[] buffer = null;
        try {
            if (start >= 0 && length > 0) {
                buffer = new byte[length];
                raf = new RandomAccessFile(filePath, "rw");
                raf.seek(start);
                raf.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (raf != null) {
                    raf.close();
                    raf = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return buffer;
    }

    /*****************************************************************/

    /**
     * write
     */
    public boolean write(String filePath, InputStream inputStream, boolean isSuperaddition) {
        boolean b = false;
        FileOutputStream fileOutputStream = null;
        try {
            if (!isFileExist(filePath)) {
                createFile(filePath);
            }
            if (inputStream != null) {
                File file = new File(filePath);
                fileOutputStream = new FileOutputStream(file, isSuperaddition);
                byte[] buffer = new byte[4 * 1024];
                int length = -1;
                while ((length = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.flush();
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeInputStream(inputStream);
            closeOutputStream(fileOutputStream);
        }
        return b;
    }

    /**
     * write
     */
    public boolean write(String filePath, String str, boolean isSuperaddition) {
        byte[] buffer = str.getBytes();
        return write(filePath, buffer, isSuperaddition);
    }

    /**
     * write
     */
    public boolean write(String filePath, byte[] buffer, boolean isSuperaddition) {
        boolean b = false;
        FileOutputStream fileOutputStream = null;
        try {
            if (!isFileExist(filePath)) {
                createFile(filePath);
            }
            if (buffer != null) {
                File file = new File(filePath);
                fileOutputStream = new FileOutputStream(file, isSuperaddition);
                fileOutputStream.write(buffer);
                fileOutputStream.flush();
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeOutputStream(fileOutputStream);
        }
        return b;
    }

    /**
     * write
     */
    public boolean write(String filePath, long start, byte[] buffer) {
        boolean b = false;
        RandomAccessFile raf = null;
        try {
            if (!isFileExist(filePath)) {
                createFile(filePath);
            }
            if (buffer != null) {
                raf = new RandomAccessFile(filePath, "rw");
                raf.seek(start);
                raf.write(buffer);
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                    raf = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    /*****************************************************************/

    /**
     * copyContent2File
     */
    public boolean copyContent2File(String source_filePath, String target_filePath) {
        boolean b = false;
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(source_filePath));
            outBuff = new BufferedOutputStream(new FileOutputStream(target_filePath));
            byte[] bs = new byte[1024 * 4];
            int length = -1;
            while ((length = inBuff.read(bs)) != -1) {
                outBuff.write(bs, 0, length);
            }
            outBuff.flush();
            b = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeInputStream(inBuff);
            closeOutputStream(outBuff);
        }
        return b;
    }

    /**
     * copyFile
     */
    public boolean copyFile(String source_filePath, String target_filePath) {
        createFile(target_filePath);
        return copyContent2File(source_filePath, target_filePath);
    }

    /**
     * copyDir
     */
    public boolean copyDir(String source_dirPath, String target_dirPath) {
        boolean b = true;
        File target_dir = new File(target_dirPath);
        if (!target_dir.exists()) {
            target_dir.mkdirs();
        }
        File src_dir = new File(source_dirPath);
        File[] file = src_dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                String sourceFileStr = file[i].getAbsolutePath();
                String targetFileStr = target_dir.getAbsolutePath() + File.separator + file[i].getName();
                b = copyFile(sourceFileStr, targetFileStr);
                if (b == false) {
                    break;
                }
            }
            if (file[i].isDirectory()) {
                String sourceDirStr = src_dir.getAbsolutePath() + File.separator + file[i].getName();
                String targetDirStr = target_dir.getAbsolutePath() + File.separator + file[i].getName();
                copyDir(sourceDirStr, targetDirStr);
            }
        }
        return b;
    }

}
