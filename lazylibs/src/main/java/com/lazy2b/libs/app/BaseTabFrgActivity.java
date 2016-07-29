/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.libs.app
 * 文件名:BaseTabFrgActivity.java
 * 创  建:2015年12月7日下午3:00:02
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import com.lazy2b.libs.adapter.BaseTabFrgAdapter;
import com.lazy2b.libs.constants.RespDataType;
import com.lazy2b.libs.interfaces.IHolderHandler;
import com.lazy2b.libs.interfaces.IHttpHandler;
import com.lazy2b.libs.model.BaseReqCallBack;
import com.lazy2b.libs.model.RespBaseModel;
import com.lazy2b.libs.view.BaseViewHolder;
import com.lidroid.xutils.exception.HttpException;

/**
 * 类名: BaseTabFrgActivity <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseTabFrgActivity.java 73 2016-05-25 06:36:25Z lazy2b $
 */
public abstract class BaseTabFrgActivity<T extends BaseViewHolder, Adap extends BaseTabFrgAdapter>
		extends BaseFrgActivity<T> implements IHolderHandler<T>, IHttpHandler {

	/**
	 * 界面ViewHolder，为了界面控件和Activity分离，可使Activity更简洁，更专注于界面流程控制和数据处理。
	 */
	protected T mHolder = null;
	@SuppressWarnings("rawtypes")
	protected Adap mTabAdapter;

	@Override
	public void initData() {
	}

	protected int[] getRbIds() {
		return null;
	}

	protected abstract Adap getAdapter();

	@Override
	public void initView() {
		mHolder = getHolder(getHolderCls());// 初始化界面ViewHolder
		mTabAdapter = getAdapter();
	}

	/**
	 * 返回每个界面自己的ViewHolder类，需各子界面自己实现
	 * 
	 * @return
	 * 
	 * @return
	 */

	@Override
	public Class<T> getHolderCls() {
		return (Class<T>) BaseViewHolder.class;
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
	public T getHolder(Class<T>... cls) {
		try {
			return (T) BaseViewHolder.create(mCxt, mUIHandler, findViewById(android.R.id.content),
					cls.length > 0 ? cls[0] : getHolderCls());
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void post(String action, String url, Class<?> retDataModelCls, Object bodyUnfixParms, RespDataType... rdt) {
		Http.post(url, bodyUnfixParms, new BaseReqCallBack(action, retDataModelCls, this, rdt));

	}

	@Override
	public void get(String action, String url, Class<?> retDataModelCls, RespDataType... rdt) {
		Http.get(url, new BaseReqCallBack(action, retDataModelCls, this, rdt));
	}

	@Override
	public void onLoading(String action, long total, long current, boolean isUploading) {
	}

	@Override
	public void onReqStart(String action) {
	}

	@Override
	public void onSuccess(RespBaseModel resp) {
	}

	@Override
	public void onFailure(String action, HttpException error, String msg) {
	}

}
