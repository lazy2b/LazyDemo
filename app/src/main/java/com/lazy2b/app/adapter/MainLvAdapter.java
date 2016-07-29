/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app.adapter
 * 文件名:MainLvAdapter.java
 * 创  建:2015年10月31日下午12:23:08
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.app.adapter;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.widget.TextView;

import com.lazy2b.app.R;
import com.lazy2b.libs.adapter.BaseLvAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * 类名: MainLvAdapter <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: MainLvAdapter.java 3 2015-11-06 05:19:58Z lazy2b $
 */
public class MainLvAdapter extends BaseLvAdapter<ActivityInfo> {

	/**
	 * @param mCxt
	 * @param mList
	 * @param handler
	 */
	public MainLvAdapter(Context _cxt, List<ActivityInfo> _list, Handler _handler) {
		super(_cxt, _list, _handler, R.layout.lv_item_main);
	}

	@SuppressWarnings("serial")
	public static class Holder extends BaseLvHolder {
		@BindView(R.id.tv_main_lv_item)
		TextView tv_item;
	}

	@Override
	protected Class<? extends BaseLvHolder> getHolderClass() {
		return Holder.class;
	}

	@Override
	protected void handleView(BaseLvHolder _holder) {

		
		
		ActivityInfo item = getItem(_holder.position);

		Holder holder = (Holder) _holder;

		holder.tv_item.setText(item.name);

	}

}
