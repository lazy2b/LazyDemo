/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app.activity
 * 文件名:TestActivity1.java
 * 创  建:2015年10月31日下午2:09:26
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.app.activity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.piasy.rxandroidaudio.AudioRecorder;
import com.github.piasy.rxandroidaudio.AudioRecorder.OnErrorListener;
import com.lazy2b.app.App;
import com.lazy2b.app.R;
import com.lazy2b.app.utils.SoundPoolUtils;
import com.lazy2b.libs.adapter.BaseLvAdapter;
import com.lazy2b.libs.app.BaseHttpActivity;
import com.lazy2b.libs.model.RespBaseModel;
import com.lazy2b.libs.utils.ComUtils;
import com.lazy2b.libs.utils.StrUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 类名: TestActivity1 <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: AudioTestActivity.java 16 2016-06-28 06:45:05Z lazy2b $
 */
public class AudioTestActivity extends BaseHttpActivity implements OnErrorListener, OnGestureListener, OnTouchListener {

	long onDownTime = -1L;

	Map<String, Long> mRecordTimeMap = new HashMap<String, Long>();

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return mGestureDetector.onTouchEvent(event);
		case MotionEvent.ACTION_UP:
			stop();
			SoundPoolUtils.play(mCxt, R.raw.audio_record_end);
			LogUtils.i(event.getAction() + "-" + (System.currentTimeMillis() - onDownTime) + "!!!!!!!!!!!!!!");
			return mGestureDetector.onTouchEvent(event);
		case MotionEvent.ACTION_MOVE:
			return mGestureDetector.onTouchEvent(event);
		}
		return false;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		LogUtils.i(e.getAction() + "-" + (System.currentTimeMillis() - onDownTime));
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		start();
		LogUtils.i(e.getAction() + "-" + (System.currentTimeMillis() - onDownTime));
	}

	@Override
	public void onShowPress(MotionEvent e) {
		LogUtils.i(e.getAction() + "-" + (System.currentTimeMillis() - onDownTime));
		prepare();
	}

	long recordTime = -1L;

	void stop() {
		LogUtils.i("stop()");
		button7.setBackgroundResource(R.drawable.btn_record_selector);
		if (mCurrRedState != AudioState.IDLE) {
			recordTime = -1L;
			mCurrRedState = AudioState.IDLE;
			recordTime = mAudioRecord.stopRecord();
			if (recordTime > 300 && audioFile.length() > 10) {
				mRecordTimeMap.put(audioFile.getName(), recordTime);
				play(audioFile.getAbsolutePath());
				refLv();
				print("录音成功:马上上传");
				// upload();
			} else {
				try {
					if (audioFile.exists()) {
						audioFile.delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				print("录音失败:时间过短");
			}
		}
	}

	void start() {
		LogUtils.i("start()");
		SoundPoolUtils.play(mCxt, R.raw.audio_record_ready);
		if (mCurrRedState == AudioState.PREPARED && mAudioRecord.startRecord()) {
			mCurrRedState = AudioState.RECORDING;
			button7.setBackgroundResource(R.drawable.btn_record_ok);
			print("可以讲话了。。。");
		} else {
			print("录音失败:start");
		}
	}

	void prepare() {
		LogUtils.i("prepare()");
		try {
			if (mCurrRedState == AudioState.IDLE
					&& mAudioRecord
							.prepareRecord(MediaRecorder.AudioSource.MIC, MediaRecorder.OutputFormat.DEFAULT,
									MediaRecorder.AudioEncoder.DEFAULT,
									audioFile = File.createTempFile("record_", ".amr",
											tmpRecDir = (tmpRecDir == null || !tmpRecDir.canWrite())
													? App.getTmpRecordPath(System.currentTimeMillis() + "")
													: tmpRecDir))) {
				mCurrRedState = AudioState.PREPARED;
			}
		} catch (IOException e1) {
			mAudioRecord.stopRecord();
			mCurrRedState = AudioState.IDLE;
			print("录音失败:prepare");
			e1.printStackTrace();
		}
	}

	int scrollState = -1;

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		long difTime = System.currentTimeMillis() - onDownTime;

		if (difTime > 200 && difTime < 350 && scrollState < 1) {// equels
																// showPress
			LogUtils.i("showPress...." + e1.getAction() + "-" + (System.currentTimeMillis() - onDownTime));
			prepare();
			scrollState = 1;
		}
		if (difTime > 600 && scrollState == 1) { // start record
			scrollState = 2;
			start();
			LogUtils.i("start record...." + e1.getAction() + "-" + (System.currentTimeMillis() - onDownTime));
		}

		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		LogUtils.i(e1.getAction() + "-" + (System.currentTimeMillis() - onDownTime));
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		scrollState = -1;
		onDownTime = System.currentTimeMillis();
		stop();
		SoundPoolUtils.play(mCxt, R.raw.audio_record_start);
		LogUtils.i(e.getAction() + "-@@@@@@@@@@@@@@@@@@@");
		return true;
	}

	private GestureDetector mGestureDetector;

	AudioRecorder mAudioRecord = null;

	AudioState mCurrRedState = AudioState.IDLE;

	enum AudioState {
		IDLE, RECORDING, PREPARED
	}

	public AudioTestActivity() {
		mAudioRecord = AudioRecorder.getInstance();
		mAudioRecord.setOnErrorListener(this);
		mGestureDetector = new GestureDetector(mCxt, this);
	}

	MediaRecorder mediaRecorder = null;
	File audioFile = null;

	@BindView(R.id.textView)
	TextView testTextView;
	@BindView(R.id.button7)
	Button button7;

	@BindView(R.id.lv_act_recorder)
	ListView lvRecorder;

	File tmpRecDir = null;

	@Override
	public void initData() {
		super.initData();
		mUIHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 01:// download(recorderList.get(position));
					play(recorderList.get((int) msg.obj).getAbsolutePath());
					break;
				case 02:
					App.t("ddd");
					boolean del = recorderList.get((int) msg.obj).delete();
					if (del) {
						recorderList.remove((int) msg.obj);
						mAdapter.setList(recorderList);
						mAdapter.notifyDataSetChanged();
					}
					break;
				}

			}
		};
		SoundPoolUtils.init();
		SoundPoolUtils.load(mCxt, R.raw.audio_record_ready);
		SoundPoolUtils.load(mCxt, R.raw.audio_record_start);
		SoundPoolUtils.load(mCxt, R.raw.audio_record_end);
	}

	long downTime = 0L;

	@Override
	public void initView() {
		super.initView();
		loadRecorderListByNetwork();

		button7.setOnTouchListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stop();
		stopPlay(null);
		try {
			if (tmpRecDir != null) {
				delFile(tmpRecDir);
				// tmpRecDir.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void delFile(File pFile) {
		if (!pFile.delete()) {
			for (File file : pFile.listFiles()) {
				delFile(file);
			}
			try {
				pFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	void print(final String txt) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				testTextView.setText(txt);
			}
		});
	}

	@SuppressWarnings("unchecked")
	void refLv() {
		if (tmpRecDir != null) {
			final File[] files = tmpRecDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.length() > 10 && file.getAbsolutePath().endsWith(".amr");
				}
			});
			recorderList = new ArrayList<File>() {
				{
					for (File file : files) {
						add(file);
					}
				}
			};
			Arrays.asList(tmpRecDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.length() > 10 && file.getAbsolutePath().endsWith(".amr");
				}
			}));

			if (mAdapter == null) {
				mAdapter = new BaseLvAdapter<File>(mCxt, recorderList, mUIHandler, R.layout.lv_item_main) {
					@Override
					protected BaseLvHolder getHolder(View view) {
						return new BaseLvHolder(mCxt, mUIHandler, view) {
							@BindView(R.id.tv_main_lv_item)
							TextView tv_rec;

							@Override
							protected void onInit() {
								tv_rec = (TextView) mRoot;
							}

							@Override
							public void fill(Object... args) {
								super.fill(args);
								File rec = (File) args[0];
								tv_rec.setText("");
								String fileInfo = new StringBuffer(rec.getName()).append("\n")
										.append(StrUtils.formatTime("yyyy-MM-dd HH:mm:ss|录音时长", rec.lastModified()))
										.append(mRecordTimeMap.get(rec.getName())).append("毫秒\t\t")
										.append(StrUtils.formatSize(rec.length() / 1024.f) + " KB").toString();

								SpannableString nSb = new SpannableString(fileInfo);
								nSb.setSpan(new ForegroundColorSpan(Color.BLACK), 0, fileInfo.indexOf("\n"),
										Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
								nSb.setSpan(new ForegroundColorSpan(Color.GRAY), fileInfo.indexOf("\n"),
										fileInfo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
								nSb.setSpan(new RelativeSizeSpan(0.8f), fileInfo.indexOf("\n"), fileInfo.length(),
										Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
								tv_rec.append(nSb);
								tv_rec.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

								// setText(+"\n" + +"\t" + );
							}
						};
					}
				};
				// };
				//
				// new ArrayAdapter<File>(mCxt, R.layout.lv_item_main,
				// R.id.tv_main_lv_item, recorderList) {
				//
				// };
				lvRecorder.setAdapter(mAdapter);
				lvRecorder.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// mUIHandler.obtainMessage(02,
						// position).sendToTarget();
						play(recorderList.get(position).getAbsolutePath());
					}

				});
				lvRecorder.setOnItemLongClickListener(new OnItemLongClickListener() {

					@SuppressWarnings("unchecked")
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
						// mUIHandler.obtainMessage(02,
						// position).sendToTarget();
						boolean del = recorderList.get(position).delete();
						if (del) {
							mRecordTimeMap.remove(recorderList.get(position));
							recorderList.remove(position);
							App.t("删除成功");
							mAdapter.setList(recorderList);
							mAdapter.notifyDataSetChanged();
						}
						return true;
					}
				});
			} else {
				mAdapter.setList(recorderList);
				mAdapter.notifyDataSetChanged();
			}
		}
	}

	public void loadRecorderListByNetwork() {
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, "http://www.lazy2b.com/scaner.php?path=./uploads", new RequestCallBack<String>() {

			@Override
			public void onStart() {
				testTextView.setText("conn...");
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				if (isUploading) {
					testTextView.setText("upload: " + current + "/" + total);
				} else {
					testTextView.setText("reply: " + current + "/" + total);
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// recorderList = JSON.parseObject(responseInfo.result,
				// RespReplyModel.class).data;
				//
				// if (mAdapter == null) {
				// mAdapter = new ArrayAdapter<String>(mCxt,
				// R.layout.lv_item_main, R.id.tv_main_lv_item,
				// recorderList);
				// lvRecorder.setAdapter(mAdapter);
				// lvRecorder.setOnItemClickListener(new OnItemClickListener() {
				// @Override
				// public void onItemClick(AdapterView<?> parent, View view, int
				// position, long id) {
				// download(recorderList.get(position));
				// }
				// });
				// } else {
				// ((ArrayAdapter) mAdapter).clear();
				// ((ArrayAdapter) mAdapter).addAll(recorderList);
				// }
				// testTextView.setText("reply: reList.size->" +
				// recorderList.size());

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				testTextView.setText(error.getExceptionCode() + ":" + msg);
			}
		});

	}

	List<File> recorderList = null;

	BaseLvAdapter mAdapter = null;

	void upload() {
		if (audioFile == null || !audioFile.exists() || !audioFile.canRead()) {
			App.t("暂无录音");
			return;
		}
		RequestParams params = new RequestParams();
		// params.addHeader("name", "value");
		// params.addQueryStringParameter("name", "value");

		// 只包含字符串参数时默认使用BodyParamsEntity，
		// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
		// params.addBodyParameter("name", "value");

		// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
		// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
		// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
		// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
		// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
		params.addBodyParameter("uploadfile", audioFile);
		App.t("上传刚才录音");
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, "http://www.lazy2b.com/upload.php", params, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				testTextView.setText("conn...");
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				if (isUploading) {
					testTextView.setText("upload: " + current + "/" + total);
				} else {
					testTextView.setText("reply: " + current + "/" + total);
				}
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				mDownloadUrl = JSON.parseObject(responseInfo.result, RespReplyModel.class).message;
				testTextView.setText("reply: " + responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				testTextView.setText(error.getExceptionCode() + ":" + msg);
			}
		});
	}

	public void upload(View view) {
		upload();
	}

	public static class RespReplyModel extends RespBaseModel {

		public String code;
		public String message;
		public List<String> data;

	}

	String getFilePath(String url) {
		return App.getRecordCachePath() + "" + ComUtils.md5(url) + ".amr";
	}

	void download(final String url) {
		HttpUtils http = new HttpUtils();
		http.download(url, getFilePath(url), true, true, new RequestCallBack<File>() {
			@Override
			public void onStart() {
				testTextView.setText("downloading...");
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				if (isUploading) {
					testTextView.setText("download: " + current + "/" + total);
				} else {
					testTextView.setText("reply: " + current + "/" + total);
				}
			}

			@Override
			public void onSuccess(ResponseInfo<File> resp) {
				testTextView.setText("download success!" + resp.result.getAbsolutePath());
				play(resp.result.getAbsolutePath());
			}

			@Override
			public void onFailure(HttpException paramHttpException, String paramString) {
				play(getFilePath(url));
				testTextView.setText("download failure!" + paramString);
			}
		});
	}

	String mDownloadUrl = "http://www.lazy2b.com/uploads/record_719416143.amr";

	public void download(View view) {
		App.t("下载刚上传得录音");
		download(mDownloadUrl);
	}

	public void start(View view) {
		start();
	}

	public void stop(View view) {
		stop();
	}

	MediaPlayer mediaPlayer = null;

	public void play(View view) {
		try {
			play(audioFile.getAbsolutePath());
		} catch (Exception e) {
			App.t("暂无可供播放录音");
		}

	}

	public synchronized void play(Object url) {
		try {
			if (mediaPlayer != null) {
				if (mediaPlayer.isLooping() || mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
					mediaPlayer.release();
				}
				mediaPlayer = null;
			}

			if (url instanceof String) {
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setVolume(1.0f, 1.0f);
				mediaPlayer.reset();
				mediaPlayer.setDataSource(mCxt, Uri.parse((String) url));
			} else if (url instanceof Integer) {
				mediaPlayer = MediaPlayer.create(this, (Integer) url);
				mediaPlayer.setVolume(1.0f, 1.0f);
			}
			mediaPlayer.prepare();
			// 设置需要播放的数据源
			// 准备播放，如果是流媒体需要调用prepareAsync进行异步准备
			// if (Common.PLAY_MODE_SINGLE_LOOP == mPlayMode) {
			// mediaPlayer.setLooping(true); // 单曲循环
			// } else {
			mediaPlayer.setLooping(false); // 不循环播放
			// }
			mediaPlayer.start(); // 开始播放，如果是播放流媒体，需要等到流媒体准备完成才能播放（在prepare的callback函数中调用start进行播放）
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					mediaPlayer.stop();
					mediaPlayer.release();
					mediaPlayer = null;
				}

			});
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void stopPlay(View view) {
		try {
			if (mediaPlayer == null) {
				return;
			}
			mediaPlayer.stop();
			mediaPlayer.release();
		} catch (Exception e) {
			App.t("暂无播放中的录音");
		}
	}

	public void rePlay(View view) {
		try {
			if (mediaPlayer == null) {
				return;
			}
			// stopPlay(view);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onError(int error) {
	}

	@Override
	public void onSuccess(RespBaseModel resp) {
	}

	@Override
	public void onFailure(String action, HttpException error, String msg) {
	}

	@Override
	protected int getContentResId() {
		return R.layout.act_audio_test;
	}
}
