/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.constants
 * 文件名:AppData.java
 * 创  建:2016年1月26日上午11:59:58
 * Copyright © 2016, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.constants;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import com.lazy2b.libs.interfaces.ILazyBase;
import com.lidroid.xutils.util.LogUtils;

/**
 * 类名: AppData <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public class AppData implements ILazyBase {

	public static void init(Context cxt) {
		PACKAGE_NAME = cxt.getPackageName();
		getPixels(cxt);
		getAppVersion(cxt);
		getTelInfo(cxt);
	}

	public static String PACKAGE_NAME = "";
	/**
	 * sim卡编号
	 */
	public static String SIM_SN = "";
	/**
	 * 手机唯一标识
	 */
	public static String IMEI = "";
	/**
	 * 手机型号
	 */
	public static String PHONE_MODEL = "";
	/**
	 * 手机系统版本
	 */
	public static String SYS_VER = "";
	/**
	 * APP版本名 ，AndroidManifest.xml 中的android:versionName
	 */
	public static String VER_NAME = "";
	/**
	 * APP版本名 ，AndroidManifest.xml 中的android:versionName 子版本号。 如果有的话 如 ，v1.0.0_1
	 */
	public static int SUB_VER_CODE = 0;
	/**
	 * APP版本名 ，AndroidManifest.xml 中的android:versionCode
	 */
	public static int VER_CODE = 1;
	public static int widthPixels;
	public static int heightPixels;

	// public static String JSESSIONID = "";

	private static void getPixels(Context cxt) {
		widthPixels = cxt.getResources().getDisplayMetrics().widthPixels;
		heightPixels = cxt.getResources().getDisplayMetrics().heightPixels;
		LogUtils.i("[ AppData ] widthPixels*heightPixels==>" + widthPixels + "*" + heightPixels);
	}

	private static void getTelInfo(Context cxt) {
		TelephonyManager tm = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
		SIM_SN = tm.getSimSerialNumber();
		IMEI = tm.getDeviceId();
		PHONE_MODEL = android.os.Build.MODEL;
		SYS_VER = android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取APP版本信息
	 * 
	 * @param cxt
	 */
	private static void getAppVersion(Context cxt) {
		int subVerCode = 0;
		int verCode = 4;
		String verName = "";
		try {
			verCode = cxt.getPackageManager().getPackageInfo(cxt.getPackageName(), 0).versionCode;
			verName = cxt.getPackageManager().getPackageInfo(cxt.getPackageName(), 0).versionName;
			String[] vns = verName.split("_");
			if (vns.length > 1) {
				// VER_NAME
				subVerCode = Integer.parseInt(vns[1]);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		VER_NAME = verName;
		VER_CODE = verCode;
		SUB_VER_CODE = subVerCode;
	}
}
