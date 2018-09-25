/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.swolf.librarymedia.photo;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;


/**
 * Desction:获取图片
 */
public class PhotoTools {

    private Context mContext;

    private OnGetPhotoListener listener;

    public interface OnGetPhotoListener{
        void onFinish(List<PhotoFolderInfo> photoFolderInfos);
    }

    private PhotoTools(Context mContext, OnGetPhotoListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        PhotoTask task = new PhotoTask();
        task.executeOnExecutor(Executors.newCachedThreadPool(),listener);
    }

    public static PhotoTools newInstance(Context mContext, OnGetPhotoListener listener) {
        return new PhotoTools(mContext,listener);
    }

    /**
     * 重新获取一遍数据
     */
    public void refresh(){

        PhotoTask task = new PhotoTask();
        task.executeOnExecutor(Executors.newCachedThreadPool(),listener);
    }

    /**
     * 获取所有图片的子线程
     */
    private class PhotoTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            List<PhotoFolderInfo> photoFolderInfos = getAllPhotoFolder(mContext);
            return photoFolderInfos;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(listener != null){
                listener.onFinish((List<PhotoFolderInfo>) o);
            }
        }
    }
    /**
     * 获取所有图片
     * @param context
     * @return
     */
    public List<PhotoFolderInfo> getAllPhotoFolder(Context context) {
        List<PhotoFolderInfo> allFolderList = new ArrayList<>();
        //所有图片
        PhotoFolderInfo allPhotoFolderInfo = new PhotoFolderInfo();
        allPhotoFolderInfo.setFolderId(0);
        allPhotoFolderInfo.setFolderName("全部图片");
        allPhotoFolderInfo.setPhotoList(new ArrayList<PhotoInfo>());
        allFolderList.add(0, allPhotoFolderInfo);

        final String[] projectionPhotos = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Thumbnails.DATA
        };
        final ArrayList<PhotoFolderInfo> allPhotoFolderList = new ArrayList<>();
        HashMap<Integer, PhotoFolderInfo> bucketMap = new HashMap<>();
        Cursor cursor = null;
        try {
            cursor = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    , projectionPhotos, "", null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
            if (cursor != null) {
                final int bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                final int bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
                final int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                final int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                while (cursor.moveToNext()) {
                    String bucketName = cursor.getString(bucketNameColumn);
                    int bucketId = cursor.getInt(bucketIdColumn);
                    final String path = cursor.getString(dataColumn);
                    final int imageId = cursor.getInt(imageIdColumn);

                    File file = new File(path);
                    if ( file.exists() && file.length() > 0 ) {
                        final PhotoInfo photoInfo = new PhotoInfo();
                        photoInfo.setPhotoId(imageId);
                        photoInfo.setPhotoPath(path);
                        //photoInfo.setThumbPath(thumb);
                        if (allPhotoFolderInfo.getCoverPhoto() == null) {
                            allPhotoFolderInfo.setCoverPhoto(photoInfo);
                        }
                        //添加到所有图片
                        allPhotoFolderInfo.getPhotoList().add(photoInfo);
                        //通过bucketId获取文件夹
                        PhotoFolderInfo photoFolderInfo = bucketMap.get(bucketId);
                        if (photoFolderInfo == null) {
                            photoFolderInfo = new PhotoFolderInfo();
                            photoFolderInfo.setPhotoList(new ArrayList<PhotoInfo>());
                            photoFolderInfo.setFolderId(bucketId);
                            photoFolderInfo.setFolderName(bucketName);
                            photoFolderInfo.setCoverPhoto(photoInfo);
                            bucketMap.put(bucketId, photoFolderInfo);
                            allPhotoFolderList.add(photoFolderInfo);
                        }
                        photoFolderInfo.getPhotoList().add(photoInfo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        allFolderList.addAll(allPhotoFolderList);
        return allFolderList;
    }
}
