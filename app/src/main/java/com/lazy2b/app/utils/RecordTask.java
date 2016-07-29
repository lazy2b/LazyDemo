/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app.utils
 * 文件名:RecordTask.java
 * 创  建:2016年5月19日上午10:01:24
 * Copyright © 2016, GDQL All Rights Reserved.
 */
package com.lazy2b.app.utils;

import java.io.File;
import java.io.IOException;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Message;

/**
 * 类名: RecordTask <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * Params, Progress, Result
 * 
 * @author E-mail:Administrator
 * @version $Id: RecordTask.java 15 2016-05-25 07:01:09Z lazy2b $
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RecordTask extends AsyncTask<Object, Integer, String> {
	long startTime = -1L;
	MediaRecorder mediaRecorder = null;
	File audioFile = null;
	Context mCxt;

	void createAudioFile() {
		try {
			audioFile = File.createTempFile("record_", ".amr");
		} catch (IOException e) {
			try {
				audioFile = File.createTempFile("record_", ".amr", Environment.getDataDirectory());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public RecordTask(Context cxt, long time) {
		mCxt = cxt;
		startTime = time;
		createAudioFile();
	}

	public RecordTask(Context cxt) {
		mCxt = cxt;
		startTime = System.currentTimeMillis();
		createAudioFile();
	}

	public String getAudioFilePath() {
		if (audioFile != null) {
			return audioFile.getAbsolutePath();
		}
		return null;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	protected void onCancelled(String result) {
		super.onCancelled(result);
		if (getStatus() == Status.RUNNING || getStatus() == Status.PENDING) {
			if (System.currentTimeMillis() - startTime > 500) {
				stop();
			} else {

			}
		}
	}

	public void stop() {
		mediaRecorder.reset();
		mediaRecorder.release(); // 刻录完成一定要释放资源
		mediaRecorder = null;
	}

	@Override
	protected void onPreExecute() {
		mediaRecorder = new MediaRecorder();
		// 第1步：设置音频来源（MIC表示麦克风）
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// 第2步：设置音频输出格式（默认的输出格式）
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		// 第3步：设置音频编码方式（默认的编码方式）
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		// 第4步：指定音频输出文件
		mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected String doInBackground(Object... params) {
		try {// 第5步：调用prepare方法
			mediaRecorder.prepare();
			// 第6步：调用start方法开始录音
			mediaRecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return audioFile.getAbsolutePath();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

}
