/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app
 * 文件名:App.java
 * 创  建:2015年10月28日上午11:55:32
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.app;

import java.io.File;
import java.io.IOException;

import com.lazy2b.libs.app.BaseAppMgr;

import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * 类名: App <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: App.java 3 2015-11-06 05:19:58Z lazy2b $
 */
public class App extends BaseAppMgr {

	@Override
	public void onCreate() {
		super.onCreate();

	}

	public static String getRootPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + cxt.getPackageName()
				+ File.separator;
	}

	public static File getTmpRecordPath(String flag) {

		String dir = getRootPath() + "tmpRec" + File.separator + flag + File.separator;

		File tmp = new File(dir);

		if (!tmp.exists()) {
			tmp.mkdirs();
		}

		return tmp;
	}

	public static String getRecordCachePath() {
		return getRootPath() + "cache" + File.separator;
	}

	/**
	 * 获取字符资源
	 * 
	 * @param args
	 * @return
	 */
	public static String s(Object sArgs) {
		return sArgs instanceof String ? (String) sArgs : cxt.getString((Integer) sArgs);
	}

	/**
	 * 弹出一个小黑提示框
	 * 
	 * @param msg
	 */
	public static void t(Object msg) {
		Toast toast = Toast.makeText(cxt, s(msg), Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.getView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(cxt, "toast.getView().setOnClickListener()", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.TOP, 0, 0);
				toast.show();
			}
		});
		toast.show();
	}

}
