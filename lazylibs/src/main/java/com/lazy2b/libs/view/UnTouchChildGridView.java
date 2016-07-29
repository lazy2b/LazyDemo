/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.view
 * 文件名:UnTouchChildGridView.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 类名: UnTouchChildGridView <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: UnTouchChildGridView.java 58 2016-03-17 11:26:13Z lazy2b $
 */
public class UnTouchChildGridView extends ChildGridView {

	public UnTouchChildGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public UnTouchChildGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UnTouchChildGridView(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if (ev.getAction() == MotionEvent.ACTION_MOVE) {

			return true; // 禁止GridView滑动

		}

		return super.dispatchTouchEvent(ev);

	}

}
