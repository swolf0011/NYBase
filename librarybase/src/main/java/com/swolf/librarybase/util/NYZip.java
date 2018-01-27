package com.swolf.librarybase.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Zip工具
 * Created by LiuYi-15973602714
 */
public class NYZip {
    private static class NYSubHolder{
        private static NYZip util = new NYZip();
    }

    private NYZip(){
    }

    public static NYZip getInstance(){
        return NYZip.NYSubHolder.util;
    }
    /**
     * 取得压缩包中的 文件列表(文件夹,文件自选)
     *
     * @param zipFilePath   压缩包名字
     * @param containFolder 是否包括 文件夹
     * @param containFile   是否包括 文件
     * @return
     * @throws Exception
     */
    public ArrayList<File> getFileList(String zipFilePath, boolean containFolder, boolean containFile) throws Exception {
        ArrayList<File> fileList = new ArrayList<File>();
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(szName);
                if (containFolder) {
                    fileList.add(folder);
                }
            } else {
                File file = new File(szName);
                if (containFile) {
                    fileList.add(file);
                }
            }
        }
        if (inZip != null) {
            inZip.close();
        }
        return fileList;
    }

    /**
     * 返回压缩包中的文件InputStream
     *
     * @param zipFilePath 压缩文件的名字
     * @param fileString  解压文件的名字
     * @return InputStream
     * @throws Exception
     */
    public InputStream upZip(String zipFilePath, String fileString) throws Exception {
        ZipFile zipFile = new ZipFile(zipFilePath);
        ZipEntry zipEntry = zipFile.getEntry(fileString);
        return zipFile.getInputStream(zipEntry);
    }


    /**
     * 解压一个压缩文档 到指定位置
     *
     * @param zipFilePath   压缩包的名字
     * @param outPathString 指定的路径
     * @throws Exception
     */
    public void unZip(String zipFilePath, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry;
        String szName = "";
        FileOutputStream out = null;
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {
                copyFile(inZip, outPathString + File.separator + szName);
            }
        }
        if (out != null) {
            out.flush();
            out.close();
        }
        if (inZip != null) {
            inZip.close();
        }
    }

    private void copyFile(ZipInputStream inZip, String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream out = null;
        try {
            file.createNewFile();
            out = new FileOutputStream(file);
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = inZip.read(buffer)) != -1) {
                out.write(buffer, 0, len);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩文件,文件夹
     *
     * @param srcFileString 要压缩的文件/文件夹名字
     * @param zipFileString 指定压缩的目的和名字
     * @throws Exception
     */
    public void zip(String srcFileString, String zipFileString) throws Exception {
        // 创建Zip包
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
        // 打开要输出的文件
        File file = new File(srcFileString);
        // 压缩
        zip(file.getParent() + File.separator, file.getName(), outZip);
        // 完成,关闭
        if (outZip != null) {
            outZip.flush();
            outZip.close();
        }
    }

    /**
     * 压缩文件
     *
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     * @throws Exception
     */
    private void zip(String folderString, String fileString, ZipOutputStream zipOutputSteam)
            throws Exception {
        if (zipOutputSteam == null)
            return;
        File file = new File(folderString + fileString);
        FileInputStream inputStream = null;
        // 判断是不是文件
        if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(fileString);
            inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOutputSteam.write(buffer, 0, len);
            }
            zipOutputSteam.closeEntry();
        } else {
            // 文件夹的方式,获取文件夹下的子文件
            String fileList[] = file.list();
            // 如果没有子文件, 则添加进去即可
            if (fileList.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }
            // 如果有子文件, 遍历子文件
            for (int i = 0; i < fileList.length; i++) {
                zip(folderString, fileString + File.separator + fileList[i], zipOutputSteam);
            }
        }
        if (inputStream != null) {
            inputStream.close();
        }
        if (zipOutputSteam != null) {
            zipOutputSteam.flush();
            zipOutputSteam.close();
        }
    }


    ///////////////
    private final int BUFF_SIZE = 1024 * 1024; // 1M Byte

    /**
     * 批量压缩文件（夹）
     *
     * @param resFileList 要压缩的文件（夹）列表
     * @param zipFile     生成的压缩文件
     * @throws IOException 当压缩过程出错时抛出
     */
//    public static void zipFiles(Collection<File> resFileList, File zipFile) throws IOException {
//        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(
//                zipFile), BUFF_SIZE));
//        for (File resFile : resFileList) {
//            zipFile(resFile, zipout, "");
//        }
//        zipout.close();
//    }

    /**
     * 批量压缩文件（夹）
     *
     * @param resFileList 要压缩的文件（夹）列表
     * @param zipFile     生成的压缩文件
     * @param comment     压缩文件的注释
     * @throws IOException 当压缩过程出错时抛出
     */
//    public static void zipFiles(Collection<File> resFileList, File zipFile, String comment)
//            throws IOException {
//        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(
//                zipFile), BUFF_SIZE));
//        for (File resFile : resFileList) {
//            zipFile(resFile, zipout, "");
//        }
//        zipout.setComment(comment);
//        zipout.close();
//    }

    /**
     * 解压缩一个文件
     *
     * @param zipFile    压缩文件
     * @param folderPath 解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */
    public void upZipFile(File zipFile, String folderPath) throws ZipException, IOException {
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            InputStream in = zf.getInputStream(entry);
            String str = folderPath + File.separator + entry.getName();
            str = new String(str.getBytes("8859_1"), "GB2312");
            File desFile = new File(str);
            if (!desFile.exists()) {
                File fileParentDir = desFile.getParentFile();
                if (!fileParentDir.exists()) {
                    fileParentDir.mkdirs();
                }
                desFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(desFile);
            byte buffer[] = new byte[BUFF_SIZE];
            int realLength;
            while ((realLength = in.read(buffer)) > 0) {
                out.write(buffer, 0, realLength);
            }
            in.close();
            out.close();
        }
    }

    /**
     * 解压文件名包含传入文字的文件
     *
     * @param zipFile      压缩文件
     * @param folderPath   目标文件夹
     * @param nameContains 传入的文件匹配名
     * @throws ZipException 压缩格式有误时抛出
     * @throws IOException  IO错误时抛出
     */
    public ArrayList<File> upZipSelectedFile(File zipFile, String folderPath,
                                                    String nameContains) throws ZipException, IOException {
        ArrayList<File> fileList = new ArrayList<File>();

        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdir();
        }

        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            if (entry.getName().contains(nameContains)) {
                InputStream in = zf.getInputStream(entry);
                String str = folderPath + File.separator + entry.getName();
                str = new String(str.getBytes("8859_1"), "GB2312");
                // str.getBytes("GB2312"),"8859_1" 输出
                // str.getBytes("8859_1"),"GB2312" 输入
                File desFile = new File(str);
                if (!desFile.exists()) {
                    File fileParentDir = desFile.getParentFile();
                    if (!fileParentDir.exists()) {
                        fileParentDir.mkdirs();
                    }
                    desFile.createNewFile();
                }
                OutputStream out = new FileOutputStream(desFile);
                byte buffer[] = new byte[BUFF_SIZE];
                int realLength;
                while ((realLength = in.read(buffer)) > 0) {
                    out.write(buffer, 0, realLength);
                }
                in.close();
                out.close();
                fileList.add(desFile);
            }
        }
        return fileList;
    }

    /**
     * 获得压缩文件内文件列表
     *
     * @param zipFile 压缩文件
     * @return 压缩文件内文件名称
     * @throws ZipException 压缩文件格式有误时抛出
     * @throws IOException  当解压缩过程出错时抛出
     */
    public ArrayList<String> getEntriesNames(File zipFile) throws ZipException, IOException {
        ArrayList<String> entryNames = new ArrayList<String>();
        Enumeration<?> entries = getEntriesEnumeration(zipFile);
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            entryNames.add(new String(getEntryName(entry).getBytes("GB2312"), "8859_1"));
        }
        return entryNames;
    }

    /**
     * 获得压缩文件内压缩文件对象以取得其属性
     *
     * @param zipFile 压缩文件
     * @return 返回一个压缩文件列表
     * @throws ZipException 压缩文件格式有误时抛出
     * @throws IOException  IO操作有误时抛出
     */
    public Enumeration<?> getEntriesEnumeration(File zipFile) throws ZipException,
            IOException {
        ZipFile zf = new ZipFile(zipFile);
        return zf.entries();

    }

    /**
     * 取得压缩文件对象的注释
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的注释
     * @throws UnsupportedEncodingException
     */
    public String getEntryComment(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getComment().getBytes("GB2312"), "8859_1");
    }

    /**
     * 取得压缩文件对象的名称
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的名称
     * @throws UnsupportedEncodingException
     */
    public String getEntryName(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getName().getBytes("GB2312"), "8859_1");
    }

    /**
     * 压缩文件
     *
     * @param resFile  需要压缩的文件（夹）
     * @param zipout   压缩的目的文件
     * @param rootpath 压缩的文件路径
     * @throws FileNotFoundException 找不到文件时抛出
     * @throws IOException           当压缩过程出错时抛出
     */
    private void zipFile(File resFile, ZipOutputStream zipout, String rootpath)
            throws FileNotFoundException, IOException {
        rootpath = rootpath + (rootpath.trim().length() == 0 ? "" : File.separator)
                + resFile.getName();
        rootpath = new String(rootpath.getBytes("8859_1"), "GB2312");
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            for (File file : fileList) {
                zipFile(file, zipout, rootpath);
            }
        } else {
            byte buffer[] = new byte[BUFF_SIZE];
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile),
                    BUFF_SIZE);
            zipout.putNextEntry(new ZipEntry(rootpath));
            int realLength;
            while ((realLength = in.read(buffer)) != -1) {
                zipout.write(buffer, 0, realLength);
            }
            in.close();
            zipout.flush();
            zipout.closeEntry();
        }
    }
}

// /:~