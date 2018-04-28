package com.swolf.librarymedia.media.voice;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;

/**
 * 播放资源视频
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYVoicePlayerRawResource {

	private static MediaPlayer mediaPlayer;
	private static int currentPosition = 0;// 当前进度

	public interface IMediaPlayerListener {
		void prepared(MediaPlayer mp);

		void completion(MediaPlayer mp);

		boolean error(MediaPlayer mp, int what, int extra);

		void bufferingUpdate(MediaPlayer mp, int percent);
	}

	public static void play(Context context, int resid, boolean looping, final IMediaPlayerListener listener) {
		mediaPlayer = MediaPlayer.create(context, resid);
		mediaPlayer.reset();
		mediaPlayer.setLooping(looping);
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
		try {
			mediaPlayer.prepare();
			mediaPlayer.seekTo(currentPosition);
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 暂停 */
	public static void pause() {
		currentPosition = mediaPlayer.getCurrentPosition();
		mediaPlayer.pause();
	}

	/** 停止 */
	public void stop() {
		try {
			mediaPlayer.stop();
			currentPosition = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 释放 */
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
	 * 
	 * @param position
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