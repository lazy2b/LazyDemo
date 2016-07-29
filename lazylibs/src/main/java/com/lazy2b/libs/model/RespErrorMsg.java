/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.model
 * 文件名:RespErrorMsg.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名: RespErrorMsg <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public class RespErrorMsg {

	@SuppressWarnings("serial")
	private final static Map<Integer, String> CodeMap = new HashMap<Integer, String>() {
		{
			put(0, "成功");
			put(1, "无效的参数");
			put(3, "访问远程服务器网络异常");
			put(4, "服务器出错");
			put(5, "不在线");
			put(103, "不在线");
			put(104, "存在相同的用户名");
			put(105, "登陆错误超过3次");
			put(106, "无效的条件类型号");
			put(107, "超出彩票条件个数");
			put(108, "无效的彩种");
			put(110, "超出条件号码个数限制限制");
			put(111, "授权失败");
			put(112, "不允许出现特殊字符");
			put(113, "在别的地方登陆了");
		}
	};

	public static String getMsg(Integer code) {
		if (CodeMap.containsKey(code)) {
			return CodeMap.get(code);
		}
		return "UnKnown Code!";
	}
}
