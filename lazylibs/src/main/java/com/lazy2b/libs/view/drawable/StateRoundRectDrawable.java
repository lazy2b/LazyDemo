/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.view
 * 文件名:StateRoundRectDrawable.java
 * 创  建:2015-11-17上午12:43:21
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.view.drawable;

import android.graphics.Rect;
import android.graphics.drawable.StateListDrawable;

/**
 * 类名: StateRoundRectDrawable <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: StateRoundRectDrawable.java 14 2015-11-17 08:39:39Z lazy2b $
 */
public class StateRoundRectDrawable extends StateListDrawable {
	private static final float DEFAULT_RADIUS = 6.f;
	private float mTopLeftRadius = DEFAULT_RADIUS;
	private float mTopRightRadius = DEFAULT_RADIUS;
	private float mBottomLeftRadius = DEFAULT_RADIUS;
	private float mBottomRightRadius = DEFAULT_RADIUS;
	private int mNormalColor;
	private int mPressedColor;
	private RoundRectDrawable mNormalDradable;
	private RoundRectDrawable mPressedDradable;

	public StateRoundRectDrawable(int normalCorlor, int pressColor) {
		this.mNormalColor = normalCorlor;
		this.mPressedColor = pressColor;
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		if (mNormalDradable == null) {
			mNormalDradable = new RoundRectDrawable();
			mNormalDradable.setTopLeftRadius(mTopLeftRadius);
			mNormalDradable.setTopRightRadius(mTopRightRadius);
			mNormalDradable.setBottomLeftRadius(mBottomLeftRadius);
			mNormalDradable.setBottomRightRadius(mBottomRightRadius);
			mNormalDradable.setColor(mNormalColor);
			mNormalDradable.onBoundsChange(bounds);
		}
		if (mPressedDradable == null) {
			mPressedDradable = new RoundRectDrawable();
			mPressedDradable.setTopLeftRadius(mTopLeftRadius);
			mPressedDradable.setTopRightRadius(mTopRightRadius);
			mPressedDradable.setBottomLeftRadius(mBottomLeftRadius);
			mPressedDradable.setBottomRightRadius(mBottomRightRadius);
			mPressedDradable.setColor(mPressedColor);
			mPressedDradable.onBoundsChange(bounds);
		}
		this.addState(new int[] { -android.R.attr.state_pressed }, mNormalDradable);
		this.addState(new int[] { android.R.attr.state_pressed }, mPressedDradable);
	}

	public StateRoundRectDrawable setRadius(float topLeft, float topRight, float bottomLeft, float bottomRight) {
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

	public int getNormalColor() {
		return mNormalColor;
	}

	public void setNormalColor(int normalColor) {
		this.mNormalColor = normalColor;
	}

	public int getPressedColor() {
		return mPressedColor;
	}

	public void setPressedColor(int pressedColor) {
		this.mPressedColor = pressedColor;
	}
}
