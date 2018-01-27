package com.swolf.librarybase.photo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.swolf.librarybase.util.NYFileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 保存图片工具
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYSavePhotoUtil {

    /**
     * 保存图片2文件
     *
     * @param bitmap
     * @param CompressFormat 枚举：CompressFormat.JPEG;CompressFormat.PNG;Bitmap
     *                       .CompressFormat.WEBP;
     * @param saveFile
     * @return
     */
    public static boolean saveImage2File(Bitmap bitmap, CompressFormat CompressFormat, File saveFile) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat, 100, baos);
        boolean b = saveImage2File(baos.toByteArray(), saveFile);
        if (baos != null) {
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    /**
     * 保存图片2文件
     *
     * @param bs
     * @param saveFile
     * @return
     */
    public static boolean saveImage2File(byte[] bs, File saveFile) {
        FileOutputStream fos = null;
        if (saveFile == null) {
            return false;
        }
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            fos = new FileOutputStream(saveFile);
            fos.write(bs);
            fos.flush();
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 保存当前屏幕的截图,返回图片路径
     */
    public String saveCurrentWindowIsBitmap(Activity activity) {
        String path = "";
        Bitmap bitmap = NYPhotoTransformationUtil.currentWindowToBitmap(activity);

        File cameraDir = NYFileUtil.getInstance().getFile("cameraDir" + File.separator);
        if (!cameraDir.exists()) {
            cameraDir.mkdirs();
        }
        String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(cameraDir, fileName);

        FileOutputStream fos = null;
        //3.保存Bitmap
        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(CompressFormat.JPEG, 90, fos);
            fos.flush();
            path = imageFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        bitmap.recycle();
        return path;
    }
}
