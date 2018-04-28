package com.swolf.librarymedia.music;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

/**
 * 音乐工具
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYMusicUtil {
    public static List<NYMusic> getMusicData(Context context) {
        List<NYMusic> musicList = new ArrayList<NYMusic>();
        ContentResolver cr = context.getContentResolver();
        if (cr != null) {
            Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if (null == cursor) {
                return null;
            }
            if (cursor.moveToFirst()) {
                do {
                    NYMusic m = new NYMusic();
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    if ("<unknown>".equals(singer)) {
                        singer = "unknown artist";
                    }
                    String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                    long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String sbr = name.substring(name.length() - 3, name.length());
                    if (sbr.equals("mp3")) {
                        m.title = title;
                        m.singer = singer;
                        m.album = album;
                        m.size = size;
                        m.time = time;
                        m.url = url;
                        m.name = name;
                        musicList.add(m);
                    }

                } while (cursor.moveToNext());
            }
        }
        return musicList;
    }

    public static void seletAudio(Activity activity, int requestCode) {
        Intent intent = openIntentByType("audio/*.mp3", null);
        activity.startActivityForResult(intent, requestCode);
    }

    public static Intent openIntentByType(String type, String title) {

        Intent intent = new Intent();
        if (null != type && type.length() > 0) {
            intent.setType(type);
        } else {
            intent.setType("audio/*.mp3");
        }
        intent.setAction("android.intent.action.GET_CONTENT");
        if (null != title && title.length() > 0) {
            return Intent.createChooser(intent, title);
        }
        return intent;
    }
}
