package com.swolf.librarymedia.media.video;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.SurfaceHolder;

/**
 * 播放视频
 * <!—sd卡写入权限-->
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.WRITE_SETTINGS" />
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYVideoPlayer {

    private MediaPlayer mediaPlayer;
    private int currentPosition = 0;

    public interface IMediaPlayerListener {
        void prepared(MediaPlayer mp);

        void completion(MediaPlayer mp);

        boolean error(MediaPlayer mp, int what, int extra);

        void bufferingUpdate(MediaPlayer mp, int percent);
    }

    public NYVideoPlayer(IMediaPlayerListener listener, SurfaceHolder sh) {
        this(false, listener, sh);
    }

    public NYVideoPlayer(boolean looping, final IMediaPlayerListener listener, SurfaceHolder sh) {
        super();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.setLooping(looping);
            mediaPlayer.setDisplay(sh);
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (listener != null) {
                        listener.completion(mp);
                    }
                }
            });
            mediaPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    if (listener != null) {
                        listener.bufferingUpdate(mp, percent);
                    }
                }
            });
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (listener != null) {
                        listener.prepared(mp);
                    }
                }
            });
            mediaPlayer.setOnErrorListener(new OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (listener != null) {
                        listener.error(mp, what, extra);
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置音量0.0--1.0之间
     */
    public void setVolume(float leftVolume, float rightVolume) {
        mediaPlayer.setVolume(leftVolume, rightVolume);
    }

    /**
     * 是否循环
     */
    public void setLooping(boolean looping) {
        mediaPlayer.setLooping(looping);
    }

    /**
     * 播放
     */
    public void play(String filepath) {
        try {
            mediaPlayer.setDataSource(filepath);
            mediaPlayer.prepare();
            mediaPlayer.seekTo(currentPosition);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        currentPosition = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();
    }

    /**
     * 停止
     */
    public void stop() {
        try {
            mediaPlayer.stop();
            currentPosition = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放
     */
    public void release() {
        try {
            mediaPlayer.release();
            mediaPlayer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拖动
     */
    public void seekTo(int position) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(position);
        } else {
            currentPosition = position;
        }
    }
}

// /:~