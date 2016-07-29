/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:BaseHttpFragment.java
 * 创  建:2015年10月22日上午10:38:37
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import com.lazy2b.libs.interfaces.IContainerInit;
import com.lazy2b.libs.interfaces.IFrgMutualHandler;
import com.lazy2b.libs.interfaces.IHolderHandler;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.view.BaseViewHolder;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/**
 * 类名: BaseHttpFragment <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseFragment.java 73 2016-05-25 06:36:25Z lazy2b $
 */
public abstract class BaseFragment<THolder extends BaseViewHolder> extends Fragment
		implements IContainerInit, ILazyBase, IHolderHandler<THolder> {

	protected Context mCxt = null;

	/**
	 * 界面普通处理句柄
	 */
	protected Handler mUIHandler = null;
	/**
	 * 界面ViewHolder，为了界面控件和Activity分离，可使Activity更简洁，更专注于界面流程控制和数据处理。
	 */
	protected THolder mHolder = null;

	protected IFrgMutualHandler mMutualHandler = null;

	protected View mRoot;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		try {
			mRoot = inflater.inflate(getLayoutResId(), container, false);
			// getView();
		} catch (Exception e) {
			e.printStackTrace();
		}

		findView();

		return mRoot;

	}

	protected abstract int getLayoutResId();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCxt = getActivity();
		if (getActivity() instanceof IFrgMutualHandler) {
			mMutualHandler = (IFrgMutualHandler) getActivity();
		}
		mUIHandler = new Handler() {
		};
		initData();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		onCreate();
	}

	@Override
	public void findView() {

		try {
			ButterKnife.bind(this, mRoot);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public View findViewById(int rId) {
		try {
			return mRoot.findViewById(rId);
		} catch (Exception e) {
			return null;
		}
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
	@SuppressWarnings("unchecked")
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
	public boolean preOnCreate() {
		return true;
	}

	@Override
	public void init() {
		initView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	protected void onCreate() {
		if (preOnCreate()) {

			init();

		} else {

		}
	}

}
