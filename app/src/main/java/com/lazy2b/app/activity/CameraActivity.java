/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app.activity
 * 文件名:CameraActivity.java
 * 创  建:2016年6月21日下午3:29:41
 * Copyright © 2016, GDQL All Rights Reserved.
 */
package com.lazy2b.app.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.lazy2b.app.R;
import com.lazy2b.app.activity.CameraActivity.CameraViewHolder;
import com.lazy2b.app.interfaces.ICameraOptHandler;
import com.lazy2b.libs.app.BaseActivity;
import com.lazy2b.libs.view.BaseViewHolder;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类名: CameraActivity <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:Administrator
 * @version $Id: CameraActivity.java 16 2016-06-28 06:45:05Z lazy2b $
 */
public class CameraActivity extends BaseActivity<CameraViewHolder>
		implements SurfaceHolder.Callback, ICameraOptHandler {

	Camera mCamera = null;

	public static class CameraViewHolder extends BaseViewHolder {

		ICameraOptHandler cOptHandler;

		@BindView(R.id.sfv_camera)
		SurfaceView sfv;

		@BindView(R.id.btn_takepic)
		Button btn_takepic;

		@OnClick({ R.id.btn_takepic })
		void takePic(View view) {
			cOptHandler.takePic();
		}

		public SurfaceHolder getHolder() {
			return sfv.getHolder();
		}

		@Override
		protected void onInit() {
			super.onInit();
		}

		public void init(ICameraOptHandler handler, SurfaceHolder.Callback call) {
			cOptHandler = handler;
			sfv.getHolder().addCallback(call);
			sfv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

	}

	@Override
	public void initView() {
		super.initView();
		mHolder.init(this, this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		/* 相机初始化 */
		initCamera();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			/* 打开相机， */
			mCamera = Camera.open();
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
		}
	}

	private AutoFocusCallback mAutoFocusCallback = new AutoFocusCallback();
	private Bitmap bmp;
	private int flag = 5;

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopCamera();
		mCamera.release();
		mCamera = null;
	}

	@Override
	public void takePic() {
		mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
	} /* 告定义class AutoFocusCallback */

	private ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};

	private PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] _data, Camera _camera) {
		}
	};
	private PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] _data, Camera _camera) {
			/* 取得相仞 */
			try {
				/* 取得相仞Bitmap对象 */
				bmp = BitmapFactory.decodeByteArray(_data, 0, _data.length);
				// 发送给isbn分析
				// String isbn = getISBN(bmp);获得图片解析后的信息
				// Log.i(TAG, "isbn:" + flag + isbn);
				flag--;
				if (flag > 0) {
					mCamera.autoFocus(mAutoFocusCallback);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public final class AutoFocusCallback implements android.hardware.Camera.AutoFocusCallback {
		public void onAutoFocus(boolean focused, Camera camera) {
			/* 对到焦点拍照 */
			if (focused) {
				takePic();
			}
		}
	};

	/* 相机初始化的method */
	private void initCamera() {
		if (mCamera != null) {
			try {
				Camera.Parameters parameters = mCamera.getParameters();
				/*
				 * 设定相片大小为1024*768， 格式为JPG
				 */
				parameters.setPictureFormat(PixelFormat.JPEG);
				parameters.setPictureSize(1024, 768);
				mCamera.setDisplayOrientation(90);
				mCamera.setParameters(parameters);
				/* 开启预览画面 */
				mCamera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* 停止相机的method */
	private void stopCamera() {
		if (mCamera != null) {
			try {
				/* 停止预览 */
				mCamera.stopPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Class getHolderCls() {
		return CameraViewHolder.class;
	}

	@Override
	protected int getContentResId() {
		/* 隐藏状态栏 */
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/* 隐藏标题栏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/* 设定屏幕显示为横向 */
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		return R.layout.act_camera;
	}

}
