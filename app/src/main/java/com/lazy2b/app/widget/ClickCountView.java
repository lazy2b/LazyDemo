/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app.widget
 * 文件名:ClickCountView.java
 * 创  建:2016年6月20日下午5:07:41
 * Copyright © 2016, GDQL All Rights Reserved.
 */
package com.lazy2b.app.widget;

import com.lazy2b.libs.utils.DensityUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 类名: ClickCountView <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:Administrator
 * @version $Id: ClickCountView.java 16 2016-06-28 06:45:05Z lazy2b $
 */
public class ClickCountView extends View implements OnClickListener {

	protected int mCount = 0;

	protected Paint mPaint = null;

	protected Rect mBounds = null;

	public ClickCountView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public ClickCountView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ClickCountView(Context context) {
		super(context);
		init();
	}

	protected void init() {
		mPaint = new Paint();
		mBounds = new Rect();
		setOnClickListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setColor(Color.RED);
		canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
		mPaint.setColor(Color.BLUE);
		mPaint.setTextSize(DensityUtils.dp2px(getContext(), 14));
		String text = String.valueOf(mCount);
		mPaint.getTextBounds(text, 0, text.length(), mBounds);
		canvas.drawText(text, getWidth() / 2 - mBounds.width() / 2, getHeight() / 2 + mBounds.height() / 2, mPaint);
	}

	@Override
	public void onClick(View v) {
		mCount++;
		invalidate();
	}

}
