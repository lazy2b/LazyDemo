/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:BaseHttpFragment.java
 * 创  建:2015年10月22日上午10:38:37
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import com.lazy2b.libs.constants.RespDataType;
import com.lazy2b.libs.interfaces.IContainerInit;
import com.lazy2b.libs.interfaces.IHttpHandler;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.model.BaseReqCallBack;

import android.support.v4.app.Fragment;

/**
 * 类名: BaseHttpFragment <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public abstract class BaseHttpFragment<T> extends Fragment implements IHttpHandler, IContainerInit, ILazyBase {
	@Override
	public void initData() {
	}

	@Override
	public void initView() {
	}

	/**
	 * 
	 * @param action
	 * @param url
	 * @param retDataModelCls
	 *            返回数据类
	 * @param bodyUnfixParms
	 *            可变参数
	 * @param rdt
	 *            <br/>
	 *            返回数据类型标识【可不传，默认为{@link RespDataType#Object}】:<br/>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;枚举 {@link RespDataType#Object} |
	 *            {@link RespDataType#List} | {@link RespDataType#Map}
	 */
	@Override
	public void post(String action, String url, Class<?> retDataModelCls, Object bodyUnfixParms, RespDataType... rdt) {
		Http.post(url, bodyUnfixParms, new BaseReqCallBack(action, retDataModelCls, this, rdt));
	}

	/**
	 * 
	 * @param action
	 * @param url
	 * @param retDataModelCls
	 *            返回数据类
	 * @param rdt
	 *            <br/>
	 *            返回数据类型标识【可不传，默认为{@link RespDataType#Object}】:<br/>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;枚举 {@link RespDataType#Object} |
	 *            {@link RespDataType#List} | {@link RespDataType#Map}
	 */
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

}
