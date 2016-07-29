/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.utils
 * 文件名:ActStack.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.utils;

import java.util.ArrayList;
import java.util.List;

import com.lazy2b.libs.interfaces.ILazyBase;

import android.app.Activity;

/**
 * 类名: ActStack <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: ActStack.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public class ActStack implements ILazyBase {

	public interface IActStackHandler extends ILazyBase {
		void addToActStack();

		void delByActStack();
	}

	static List<Activity> mStack = null;

	public static void init() {
		mStack = new ArrayList<Activity>();
	}

	public static void add(Activity item) {
		if (mStack == null)
			return;
		try {
			mStack.add(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void remove(Activity item) {
		if (mStack == null)
			return;
		try {
			mStack.remove(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Activity top() {
		if (mStack == null)
			return null;
		try {
			if (mStack.size() > 0) {
				return mStack.get(mStack.size() - 1);
			}
		} catch (Exception e) {
		}
		return null;
	}

	public static void finishAll() {
		if (mStack == null)
			return;
		for (Activity act : mStack) {
			if (act != null && !act.isFinishing()) {
				act.finish();
			}
		}
		mStack.clear();
	}

}
