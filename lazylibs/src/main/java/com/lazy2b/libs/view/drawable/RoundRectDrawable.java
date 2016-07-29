/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.view
 * 文件名:RoundRectDradable.java
 * 创  建:2015-11-17上午12:42:12
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.view.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * 类名: RoundRectDradable <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: RoundRectDrawable.java 14 2015-11-17 08:39:39Z lazy2b $
 */
public class RoundRectDrawable extends Drawable {
	private static final float DEFAULT_RADIUS = -1.01f;
	private Paint mPaint = new Paint();
	private RoundRectShape mShape;
	private float[] mOuter;
	private int mColor;
	private int mPressColor;
	private float mTopLeftRadius = DEFAULT_RADIUS;
	private float mTopRightRadius = DEFAULT_RADIUS;
	private float mBottomLeftRadius = DEFAULT_RADIUS;
	private float mBottomRightRadius = DEFAULT_RADIUS;

	public RoundRectDrawable() {
		mColor = Color.WHITE;
		mPressColor = Color.WHITE;
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
	}

	public RoundRectDrawable setRadius(float topLeft, float topRight, float bottomLeft, float bottomRight) {
		setTopLeftRadius(topLeft);
		setTopRightRadius(topRight);
		setBottomLeftRadius(bottomLeft);
		setBottomRightRadius(bottomRight);
		return this;
	}

	public float getTopLeftRadius() {
		return mTopLeftRadius;
	}

	public void setTopLeftRadius(float topLeftRadius) {
		this.mTopLeftRadius = topLeftRadius;
	}

	public float getTopRightRadius() {
		return mTopRightRadius;
	}

	public void setTopRightRadius(float topRightRadius) {
		this.mTopRightRadius = topRightRadius;
	}

	public float getBottomLeftRadius() {
		return mBottomLeftRadius;
	}

	public void setBottomLeftRadius(float bottomLeftRadius) {
		this.mBottomLeftRadius = bottomLeftRadius;
	}

	public float getBottomRightRadius() {
		return mBottomRightRadius;
	}

	public void setBottomRightRadius(float bottomRightRadius) {
		this.mBottomRightRadius = bottomRightRadius;
	}

	public int getPressColor() {
		return mPressColor;
	}

	public void setPressColor(int pressColor) {
		this.mPressColor = pressColor;
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
		refreshShape();
		mShape.resize(bounds.right - bounds.left, bounds.bottom - bounds.top);
	}

	private void refreshShape() {
		mOuter = new float[] { mTopLeftRadius, mTopLeftRadius, mTopRightRadius, mTopRightRadius, mBottomRightRadius,
				mBottomRightRadius, mBottomLeftRadius, mBottomLeftRadius };
		mShape = new RoundRectShape(mOuter, null, null);
	}

	public void setColor(int color) {
		mColor = color;
		mPaint.setColor(color);
	}

	@Override
	public void draw(Canvas canvas) {
		mShape.draw(canvas, mPaint);
	}

	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return mPaint.getAlpha();
	}
}