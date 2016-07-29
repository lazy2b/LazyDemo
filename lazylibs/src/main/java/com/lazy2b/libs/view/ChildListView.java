/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.utils
 * 文件名:ChildListView.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 类名: ChildListView <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: ChildListView.java 58 2016-03-17 11:26:13Z lazy2b $
 */
public class ChildListView extends ListView {
	public ChildListView(Context context) {
		super(context);
	}

	public ChildListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChildListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// 不出现滚动条
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
