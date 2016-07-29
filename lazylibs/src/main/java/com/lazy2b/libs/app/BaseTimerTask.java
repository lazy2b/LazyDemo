/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.app
 * 文件名:BaseTimerTask.java
 * 创  建:2016年1月26日上午11:59:58
 * Copyright © 2016, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import java.lang.reflect.Field;
import java.util.TimerTask;

/**
 * 类名: BaseTimerTask <br/>
 * 描述: TODO. <br/>
 * 功能: 动态修改执行间距 <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseTimerTask.java 55 2016-02-01 10:21:14Z lazy2b $
 */
public abstract class BaseTimerTask extends TimerTask {

	public void setPeriod(long period) {
		setDeclaredField(TimerTask.class, this, "period", period);
	}

	// 通过反射修改字段的值
	private static boolean setDeclaredField(Class<?> clazz, Object obj, String name, Object value) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.set(obj, value);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
