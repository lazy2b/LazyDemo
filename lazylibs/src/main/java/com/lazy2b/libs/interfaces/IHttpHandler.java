/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:IHttpHandler.java
 * 创  建:2015年10月22日上午10:58:02
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

import com.lazy2b.libs.constants.RespDataType;
import com.lazy2b.libs.model.RespBaseModel;
import com.lidroid.xutils.exception.HttpException;

/**
 * 类名: IHttpHandler <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IHttpHandler.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public interface IHttpHandler extends ILazyBase {

	/**
	 * 
	 * @param action
	 * @param url
	 * @param retDataModelCls
	 * @param bodyUnfixParms
	 * @param rdt
	 *            <br/>
	 *            返回数据类型标识【可不传，默认为{@link RespDataType#Object}】:<br/>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;枚举 {@link RespDataType#Object} |
	 *            {@link RespDataType#List} | {@link RespDataType#Map}
	 */
	void post(String action, String url, Class<?> retDataModelCls, Object bodyUnfixParms, RespDataType... rdt);

	/**
	 * 
	 * @param action
	 * @param url
	 * @param retDataModelCls
	 * @param rdt
	 *            <br/>
	 *            返回数据类型标识【可不传，默认为{@link RespDataType#Object}】:<br/>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;枚举 {@link RespDataType#Object} |
	 *            {@link RespDataType#List} | {@link RespDataType#Map}
	 */
	void get(String action, String url, Class<?> retDataModelCls, RespDataType... rdt);

	/**
	 * 
	 * @param action
	 * @param total
	 * @param current
	 * @param isUploading
	 */
	void onLoading(String action, long total, long current, boolean isUploading);

	/**
	 * 
	 * @param action
	 */
	void onReqStart(String action);

	/**
	 * 
	 * @param resp
	 */
	void onSuccess(RespBaseModel resp);

	/**
	 * 
	 * @param action
	 * @param error
	 * @param msg
	 */
	void onFailure(String action, HttpException error, String msg);

}