package com.swolf.librarymedia.media.voice;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.view.Surface;

/**
 * 录视频
 * <!—摄像头-->
 * <uses-permission android:name="android.permission.CAMERA" />
 * <!—硬件支持-->
 * <uses-feature android:name="android.hardware.camera"/>
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 * <!—sd卡写入权限-->
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.WRITE_SETTINGS" />
 * <!—请求访问使用照相设备-->
 * <uses-permission android:name="android.permission.CAMERA" />
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYVoiceRecorder {

	private static MediaRecorder mediaRecorder = new MediaRecorder();

	/**
	 * 开始录音
	 */
	public static void record(String filePath) {
		record(filePath, AudioSource.MIC, OutputFormat.DEFAULT, AudioEncoder.AMR_NB);
	}

	/**
	 * 开始录音
	 */
	public static void record(String filePath, int audio_source, int output_format, int audio_encoder) {
		createFile(filePath);
		mediaRecorder = new MediaRecorder();
		mediaRecorder.reset();
		mediaRecorder.setAudioSource(audio_source); // 设置录音源为MIC
		mediaRecorder.setOutputFormat(output_format); // 定义输出格式
		mediaRecorder.setAudioEncoder(audio_encoder); // 定义音频编码
		mediaRecorder.setOutputFile(filePath); // 定义输出文件
		try {
			mediaRecorder.prepare();// 准备录音
			mediaRecorder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createFile(String filePath) {
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 停止
	 */
	public static void stop() {
		if (mediaRecorder != null) {
			try {
				mediaRecorder.stop();
				mediaRecorder.release();
				mediaRecorder = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 振动幅度
	 * 
	 * @return
	 */
	public static double getAmplitude() {
		if (mediaRecorder != null)
			return (mediaRecorder.getMaxAmplitude() / 2700.0);
		else
			return 0;
	}

	/**
	 * 默认值录像
	 */
	public static void cameraShooting(String filePath, Surface sv) {
		cameraShooting(filePath, AudioSource.MIC, OutputFormat.THREE_GPP, AudioEncoder.AMR_NB, VideoSource.CAMERA,
				VideoEncoder.MPEG_4_SP, 320, 240, 20, sv);
	}

	/**
	 * 默认值录像
	 * 
	 * @param filePath
	 *          定义输出文件
	 * @param audio_source
	 *          设置录音源为MIC
	 * @param output_format
	 *          定义输出格式
	 * @param audio_encoder
	 *          定义音频编码
	 * @param video_source
	 *          摄像头为视频源
	 * @param video_encoder
	 *          定义视频编码
	 * @param width
	 *          定义视频宽
	 * @param height
	 *          定义视频高
	 * @param rate
	 *          每秒多少帧
	 * @param sv
	 *          定义视频显示
	 */
	public static void cameraShooting(String filePath, int audio_source, int output_format, int audio_encoder,
			int video_source, int video_encoder, int width, int height, int rate, Surface sv) {
		mediaRecorder = new MediaRecorder();
		mediaRecorder.reset();
		mediaRecorder.setAudioSource(audio_source);
		mediaRecorder.setOutputFormat(output_format);
		mediaRecorder.setAudioEncoder(audio_encoder);
		mediaRecorder.setVideoSource(video_source);
		mediaRecorder.setVideoEncoder(video_encoder);
		mediaRecorder.setOutputFile(filePath);
		// 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
		mediaRecorder.setVideoSize(width, height);
		// 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
		mediaRecorder.setVideoFrameRate(rate);
		mediaRecorder.setPreviewDisplay(sv);
		try {
			mediaRecorder.prepare();
			mediaRecorder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

// /:~