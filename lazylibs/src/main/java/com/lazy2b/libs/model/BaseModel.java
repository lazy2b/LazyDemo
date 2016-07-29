/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.model
 * 文件名:BaseModel.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashMap;

import com.lazy2b.libs.interfaces.ILazyBase;

/**
 * 类名: BaseModel <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseModel.java 3 2015-10-28 03:27:15Z lazy2b $
 */
@SuppressWarnings("serial")
public abstract class BaseModel implements Serializable, ILazyBase {

	public String toString() {

		Class<? extends BaseModel> cls = this.getClass();

		StringBuffer sb = new StringBuffer();

		sb.append(cls.getSimpleName() + "[");

		Field[] fs = cls.getDeclaredFields();

		String tmpFieldValStr = "";
		Object fo = null;
		for (Field f : fs) {
			try {
				if ("this$0".equals(f.getName()))
					continue;
				fo = f.get(this);
			} catch (Exception ex) {
				try {
					String mn = "get"
							+ f.getName().substring(0, 1).toUpperCase()
							+ f.getName().substring(1);
					Method m = cls.getMethod(mn, new Class[] {});
					fo = m.invoke(this, new Object[] {});

				} catch (Exception ex1) {
					ex1.printStackTrace();
				}
			}
			if (fo == null) {
				tmpFieldValStr = "";
			} else {
				try {
					if (fo instanceof Integer) {
						tmpFieldValStr = f.getName() + "=" + f.getInt(this);
					} else if (fo instanceof String) {
						tmpFieldValStr = f.getName() + "="
								+ (String) f.get(this);
					} else if (fo instanceof Double) {
						tmpFieldValStr = f.getName()
								+ "="
								+ new DecimalFormat(".##").format(f
										.getDouble(this));
					} else if (fo instanceof Long) {
						tmpFieldValStr = f.getName() + "=" + f.getLong(this);
					} else if (fo instanceof Boolean) {
						tmpFieldValStr = f.getName() + "=" + f.get(this);
					} else if (fo instanceof HashMap<?, ?>) {
						tmpFieldValStr = f.getName() + "="
								+ ((HashMap<?, ?>) f.get(this)).toString();
					} else if (fo instanceof Object) {
						tmpFieldValStr = f.getName() + "="
								+ f.get(this).toString();
					}
				} catch (Exception e) {
					tmpFieldValStr = f.getName() + "=";
				}
			}
			sb.append(tmpFieldValStr + ",");
			fo = null;
			tmpFieldValStr = null;
		}

		cls = null;
		fs = null;

		String printStr = sb.toString();

		sb = null;

		return printStr.substring(0, printStr.length() - 2) + "] ";
	}
}
