/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.model
 * 文件名:BaseReqCallBack.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.model;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.lazy2b.libs.constants.RespDataType;
import com.lazy2b.libs.interfaces.IHttpHandler;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;

import android.text.TextUtils;

/**
 * 类名: BaseReqCallBack <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseReqCallBack.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public class BaseReqCallBack extends RequestCallBack<String> implements ILazyBase {

	public final static int ERROR = 3;

	protected String action = null;

	protected Class<?> retCls = null;

	protected RespBaseModel resp = null;

	protected IHttpHandler handler = null;

	protected RespDataType retType = RespDataType.Object;

	public BaseReqCallBack(String _act, Class<?> _cls, IHttpHandler _handler) {
		this.action = _act;
		this.retCls = _cls;
		this.handler = _handler;
	}

	public BaseReqCallBack(String _act, Class<?> _cls, IHttpHandler _handler, RespDataType... rdt) {
		this(_act, _cls, _handler);
		if (rdt != null && rdt.length > 0) {
			this.retType = rdt[0];
		}
	}

	public BaseReqCallBack(String _act, Class<?> _cls, IHttpHandler _handler, RespDataType _type) {
		this(_act, _cls, _handler);
		this.retType = _type;
	}

	@Override
	public void onLoading(long total, long current, boolean isUploading) {
		if (handler != null) {
			handler.onLoading(action, total, current, isUploading);
		}
	}

	@Override
	public void onStart() {
		if (handler != null) {
			handler.onReqStart(action);
		}
	}

	@Override
	public void onSuccess(ResponseInfo<String> respInfo) {

		try {
			LogUtils.d("[ onSuccess.respInfo ] action->" + action + " | respStr->" + respInfo.result + " | statusCode->"
					+ respInfo.statusCode);

			if (retCls == null)
				return;

			if (TextUtils.isEmpty(respInfo.result)) {
				LogUtils.d("返回数据为空！");
				resp = new RespBaseModel();
				resp.respEmpty = true;
				resp.reqAction = action;
				// resp.xRi = respInfo;
			} else if (retCls != null) {
				try {
					switch (retType) {
					case Object:
						if (ExtraProcessor.class.isAssignableFrom(retCls)) {
							try {
								resp = (RespBaseModel) JSON.parseObject(respInfo.result, retCls,
										(ExtraProcessor) retCls.newInstance());
							} catch (Exception e) {
								resp = (RespBaseModel) JSON.parseObject(respInfo.result, retCls);
							}
						} else {
							resp = (RespBaseModel) JSON.parseObject(respInfo.result, retCls);
						}
						break;
					case List:
						resp = new RespBaseModel();
						resp.list = JSON.parseArray(respInfo.result, retCls);
						break;
					case Map:
						resp = new RespBaseModel();
						resp.map = (Map<?, ?>) JSON.parseObject(respInfo.result, Map.class,
								new MapExtraProcessor(retCls));
						break;
					default:
						break;
					}

					resp.respStr = respInfo.result;
					resp.reqAction = action;
					// resp.xRi = respInfo;

				} catch (Exception e) {

					LogUtils.i("解析返回数据异常！");

					e.printStackTrace();

					onFailure(new HttpException(HttpException.PARSE_DATA_EXCEPTION, "解析数据异常！"), respInfo.result.trim());

					return;

				}
			}

			if (handler != null) {
				handler.onSuccess(resp);
			}

			return;
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onFailure(HttpException error, String msg) {
		try {
			LogUtils.d("[ reqFailure ] action->" + action + " | msg->" + msg + " | error->" + error.getMessage());
			if (handler != null) {
				handler.onFailure(action, error, msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
