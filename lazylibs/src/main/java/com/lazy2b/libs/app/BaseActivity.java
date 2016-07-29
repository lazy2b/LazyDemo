/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.app
 * 文件名:BaseActivity.java
 * 创  建:2016年5月25日下午2:11:31
 * Copyright © 2016, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import com.lazy2b.libs.interfaces.IContainerInit;
import com.lazy2b.libs.interfaces.IHolderHandler;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.utils.ActStack;
import com.lazy2b.libs.utils.ActStack.IActStackHandler;
import com.lazy2b.libs.view.BaseViewHolder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import butterknife.ButterKnife;

/**
 * 类名: BaseActivity <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseActivity.java 73 2016-05-25 06:36:25Z lazy2b $
 */
public abstract class BaseActivity<THolder extends BaseViewHolder> extends Activity
		implements IContainerInit, IActStackHandler, ILazyBase, IHolderHandler<THolder> {

	protected Context mCxt = null;
	/**
	 * 界面普通处理句柄
	 */
	protected Handler mUIHandler = null;
	/**
	 * 界面ViewHolder，为了界面控件和Activity分离，可使Activity更简洁，更专注于界面流程控制和数据处理。
	 */
	protected THolder mHolder = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentResId());
		mCxt = this;
		onCreate();
	}

	/**
	 * @return
	 */
	protected abstract int getContentResId();

	protected void onCreate() {
		if (preOnCreate()) {

			addToActStack();

			init();

		} else {

		}
	}

	@Override
	public void addToActStack() {
		ActStack.add(this);
	}

	@Override
	public void delByActStack() {
		ActStack.remove(this);
	}

	@Override
	public boolean preOnCreate() {
		return true;
	}

	@Override
	public void init() {

		initData();

		findView();

		initView();

	}

	@Override
	public void initData() {
	}

	/**
	 * 继承时必须调用super.initView()。否则需自己初始化mHolder对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initView() {
		mHolder = getHolder(getHolderCls());// 初始化界面ViewHolder
	}

	/**
	 * 返回每个界面自己的ViewHolder类，需各子界面自己实现
	 * 
	 * @return
	 */
	@Override
	public Class<THolder> getHolderCls() {
		return (Class<THolder>) BaseViewHolder.class;
	}

	/**
	 * 返回每个界面的ViewHolder对象。
	 * 
	 * @param cls
	 *            每个界面自己的ViewHolder类，否则拿{@link #getHolderCls()} 里面定义好的。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public THolder getHolder(Class<THolder>... cls) {
		try {
			return (THolder) BaseViewHolder.create(mCxt, mUIHandler, findViewById(android.R.id.content),
					cls.length > 0 ? cls[0] : getHolderCls());
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void findView() {

		try {
			ButterKnife.bind(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

		delByActStack();

	}

	protected String str(int resId) {
		try {
			return getString(resId);
		} catch (Exception e) {
			return "";
		}
	}
}
