package com.lazy2b.libs.model;

import java.util.List;
import java.util.Map;

/**
 * 类名: RespBaseModel <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
@SuppressWarnings("serial")
public class RespBaseModel extends BaseModel {

	/**
	 * 请求响应状态码
	 */
	public int reqStatus = 0;
	/**
	 * 请求Tag[标记]可用于取消当前请求
	 */
	public String reqAction = "";
	/**
	 * 响应字符串
	 */
	public String respStr = "";
	/**
	 * 响应为空
	 */
	public boolean respEmpty = false;

	public List<?> list = null;

	public Map<?, ?> map = null;

	public Object obj = null;

}