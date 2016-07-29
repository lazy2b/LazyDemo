/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.utils
 * 文件名:MarqueeTextView.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 类名: MarqueeTextView <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: MarqueeTextView.java 58 2016-03-17 11:26:13Z lazy2b $
 */
public class MarqueeTextView extends TextView {
	/** 是否停止滚动 */
	private boolean mStopMarquee;
	private String mText;
	private float mCoordinateX;
	private float mTextWidth;

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setText(String text) {
		this.mText = text;
		mTextWidth = getPaint().measureText(mText);
		if (mHandler.hasMessages(0))
			mHandler.removeMessages(0);
		mHandler.sendEmptyMessageDelayed(0, 2000);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onAttachedToWindow() {
		mStopMarquee = false;
		if (!(mText == null || mText.isEmpty()))
			mHandler.sendEmptyMessageDelayed(0, 2000);
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		mStopMarquee = true;
		if (mHandler.hasMessages(0))
			mHandler.removeMessages(0);
		super.onDetachedFromWindow();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!(mText == null || mText.isEmpty()))
			canvas.drawText(mText, mCoordinateX, 30, getPaint());
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (Math.abs(mCoordinateX) > (mTextWidth + 5)) {
					mCoordinateX = 0;
					invalidate();
					if (!mStopMarquee) {
						sendEmptyMessageDelayed(0, 500);
					}
				} else {
					mCoordinateX -= 1;
					invalidate();
					if (!mStopMarquee) {
						sendEmptyMessageDelayed(0, 30);
					}
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

}
