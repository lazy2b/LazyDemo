/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.utils
 * 文件名:ComUtils.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.lazy2b.libs.app.BaseAppMgr;
import com.lazy2b.libs.interfaces.ILazyBase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;

/***
 * 通用工具类
 * 
 * @author Jack.lin
 * @version $Id: ComUtils.java 5 2015-11-06 05:20:17Z lazy2b $
 */
/**
 * 类名: ComUtils <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public class ComUtils implements ILazyBase {

	public static RunningServiceInfo serIsRunning(Context context,
			String serName) {

		// List<RunningAppProcessInfo> proceses = am.getRunningAppProcesses();
		//
		// for (RunningAppProcessInfo process : proceses) {
		// LogUtils.i("[ App ] processName->" + process.processName
		// + " pkgList->" + Arrays.toString(process.pkgList));
		// }

		List<RunningServiceInfo> services = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE))
				.getRunningServices(Integer.MAX_VALUE);

		RunningServiceInfo runService = null;

		String packageName = context.getPackageName();

		for (RunningServiceInfo service : services) {

//			LogUtils.e(service.service.getClassName());

			if (service.service.getClassName().equals(packageName)) {
				runService = service;
				break;
			}
		}

		return runService;

	}

	public static RunningTaskInfo appIsRunning(Context context) {

		// List<RunningAppProcessInfo> proceses = am.getRunningAppProcesses();
		//
		// for (RunningAppProcessInfo process : proceses) {
		// LogUtils.i("[ App ] processName->" + process.processName
		// + " pkgList->" + Arrays.toString(process.pkgList));
		// }

		List<RunningTaskInfo> tasks = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE))
				.getRunningTasks(Integer.MAX_VALUE);

		RunningTaskInfo runTask = null;

		String packageName = context.getPackageName();

		for (RunningTaskInfo task : tasks) {
			if (task.baseActivity.getPackageName().equals(packageName)) {
				runTask = task;
				break;
			}
		}

		return runTask;

	}

	/**
	 * * 从apk中获取版本信息
	 * 
	 * @param context
	 * @param channelPrefix
	 *            渠道前缀
	 * @return
	 */
	public static String getChannelFromApk(Context context, String channelPrefix) {
		// 从apk包中获取
		ApplicationInfo appinfo = context.getApplicationInfo();
		String sourceDir = appinfo.sourceDir;
		// 默认放在meta-inf/里， 所以需要再拼接一下
		String key = "META-INF/" + channelPrefix;
		String ret = "";
		ZipFile zipfile = null;
		try {
			zipfile = new ZipFile(sourceDir);
			Enumeration<?> entries = zipfile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				String entryName = entry.getName();
				if (entryName.startsWith(key)) {
					ret = entryName;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (zipfile != null) {
				try {
					zipfile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String[] split = ret.split(channelPrefix);
		String channel = "";
		if (split != null && split.length >= 2) {
			channel = ret.substring(channelPrefix.length());
		}
		return channel;
	}

	/**
	 * MD5 加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		if (str == null) {
			return null;
		}
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	/**
	 * 获取客户端MAC地址(MD5加密)
	 */
	public static String getMac() {
		WifiManager wifi = (WifiManager) BaseAppMgr.inst
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		if (info != null) {
			String mac = info.getMacAddress();
			return getMD5Str(mac);
		}
		return "";
	}

	/**
	 * 获取客户地理位置（经纬度）
	 * 
	 * @return“Latitude纬度，Longitude经度”
	 */
	public static String getGeo() {
		LocationManager locationManager = (LocationManager) BaseAppMgr.inst
				.getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		if (location != null) {
			return location.getLatitude() + "," + location.getLongitude();
		}
		return "";
	}

	/**
	 * 将字符串转成MD5值
	 * 
	 * @param string
	 * @return
	 */
	public static String md5(String string) {
		byte[] hash;

		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString();
	}

	// public static String getSignCode(HashMap<String, String> map) {
	// ArrayList<String> keys = new ArrayList<String>(map.keySet());
	// Collections.sort(keys);
	// String pramsStr = "";
	// for (String key : keys) {
	// pramsStr += "&" + key + "=" + map.get(key);
	// }
	// return md5(pramsStr.substring(1) + Cfg.sign_key);
	// }

	// 控件抖动动画
	// public static void startShake(Context context, View view) {
	// Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
	// // Toast.makeText(this, "输入关键字不能为空.", 0).show();
	// view.startAnimation(shake);
	// }

	/**
	 * 返回包的签名，如果包名为空则返回当前包的签名
	 * 
	 * @param cxt
	 * @param packageName
	 * @return
	 */
	public static String getPackageSign(Context cxt, String packageName) {
		PackageManager pm = cxt.getPackageManager();
		List<PackageInfo> apps = pm
				.getInstalledPackages(PackageManager.GET_SIGNATURES);
		Iterator<PackageInfo> iter = apps.iterator();
		while (iter.hasNext()) {
			PackageInfo packageinfo = iter.next();
			packageName = (!StrUtils.isEmpty(packageName)) ? packageName : cxt
					.getPackageName();
			if (packageinfo.packageName.equals(packageName)) {
				return packageinfo.signatures[0].toString();
			}
		}
		return null;
	}

	// public static void showEmptyView(Context contex, LinearLayout view,
	// String msg, int visibility, final Handler handler, final int message) {
	// // view.setVisibility(View.VISIBLE);
	// if (View.GONE == visibility) {
	// view.getChildAt(0).setVisibility(View.VISIBLE);
	//
	// view.getChildAt(1).setVisibility(View.GONE);
	// view.getChildAt(3).setVisibility(View.GONE);
	//
	// } else {
	// view.getChildAt(0).setVisibility(View.GONE);
	//
	// view.getChildAt(1).setVisibility(View.VISIBLE);
	// if (handler == null) {
	// view.getChildAt(3).setVisibility(View.GONE);
	// } else {
	// view.getChildAt(3).setVisibility(View.VISIBLE);
	//
	// }
	//
	// }
	// TextView tv = (TextView) view.getChildAt(2);
	// msg = msg.replaceAll("<p>", "").replaceAll("</p>", "");
	// tv.setText(Html.fromHtml(msg.trim()));
	// // tv.setText(msg);
	//
	// if (handler != null) {
	// // 添加点击事件
	// view.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	//
	// handler.obtainMessage(message).sendToTarget();
	// }
	// });
	// } else {
	// view.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// }
	// });
	// }
	//
	// }

	/**
	 * 发送广播
	 * 
	 * @param cxt
	 * @param act
	 * @param b
	 */
	public static void sendBroadcast(Context cxt, String act, Bundle b) {
		Intent i = new Intent();
		if (StrUtils.isEmpty(act)) {
			return;
		}
		if (b != null) {
			i.putExtras(b);
		}
		i.setAction(act);
		cxt.sendBroadcast(i);
	}

	/**
	 * 
	 * 从某个Activity跳转到另外一个Activity的通用函数
	 * 
	 * @param context
	 *            当前所在activity的上下文
	 * @param cls
	 *            需要跳转的activity的class
	 * @param finishSelf
	 *            是否结束当前activity自身 true：结束 false：不结束
	 * @param bundle
	 *            传递到下一个activity的值，没有时传null
	 */
	public static void goAct(Context context, Class<?> cls, boolean finishSelf,
			Bundle bundle) {
		try {
			Intent it = new Intent();
			it.setClass(context, cls);
			if (bundle != null) {
				it.putExtras(bundle);
			}

			context.startActivity(it);

			if (finishSelf) {
				Activity activity = (Activity) context;
				activity.finish();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 从某个context跳转到另外一个Activity的通用函数
	 * 
	 * @param context
	 *            当前上下文
	 * @param cls
	 *            需要跳转的activity的class
	 * @param flag
	 *            Intent.FLAG_XXXXXXXX
	 * @param finishSelf
	 *            是否结束当前activity自身 true：结束 false：不结束
	 * @param bundle
	 *            传递到下一个activity的值，没有时传null
	 */
	public static void goAct(Context context, Class<?> cls, int flag,
			boolean finishSelf, Bundle bundle) {
		try {

			Intent it = new Intent();
			it.setFlags(flag);
			it.setClass(context, cls);
			if (bundle != null) {
				it.putExtras(bundle);
			}

			context.startActivity(it);

			if (finishSelf) {
				Activity activity = (Activity) context;
				activity.finish();
			}
		} catch (Exception e) {
		}

	}

	public static void goActForRes(Context context, Class<?> cls,
			int requestCode, Bundle bundle) {
		Intent it = new Intent();
		it.setClass(context, cls);
		if (bundle != null) {
			it.putExtras(bundle);
		}

		((Activity) context).startActivityForResult(it, requestCode);
	}

	public static String getNetWorkName(Context mContext) {
		if (mContext == null) {
			// System.out.println(mContext + "context");
			return "";
		}
		WifiManager wifi = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		if (wifi != null && wifi.isWifiEnabled()) {
			return "WIFI";
		} else {
			return CurrentAPN(mContext);
		}
	}

	static String APN_str = "";

	public static String CurrentAPN(Context mContext) {
		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return APN_str;
		}
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null) {
			return APN_str;
		}

		String extraInfo = info.getExtraInfo();
		if (extraInfo == null || TextUtils.isEmpty(extraInfo)) {
			{
				if (info.isAvailable() && info.isConnected()) {
					return "UnKnowed";
				} else
					return APN_str;
			}
		} else {
			return extraInfo.toUpperCase();
		}
	}

	public static boolean isConditionName(String cName) {
		Pattern p = Pattern.compile("^[\u4E00-\u9FA5a-zA-Z0-9]{1,10}$");
		Matcher m = p.matcher(cName);
		return m.matches();
	}

	/**
	 * 用户名验证
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isUsrName(String usrName) {
		Pattern p = Pattern
				.compile("^[\u4E00-\u9FA5a-zA-Z0-9][\u4E00-\u9FA5a-zA-Z0-9_]{2,15}$");
		Matcher m = p.matcher(usrName);
		return m.matches();
	}

	/**
	 * 用户名验证
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isPassword(String pass) {
		Pattern p = Pattern.compile("^\\w{4,}$");
		Matcher m = p.matcher(pass);
		return m.matches();
	}

	/**
	 * 手机号码验证
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isCellphone(String mobiles) {
		// Pattern p = Pattern
		// .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Pattern p = Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 邮箱验证
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isNetworkConnected(Context context) {
		try {
			if (context != null) {
				return ((ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE))
						.getActiveNetworkInfo().isConnectedOrConnecting();
			}
		} catch (Exception e) {
		}
		return false;

	}

	/**
	 * 判断是否有快捷方式
	 * 
	 * @return
	 */
	// public static boolean hasShortcut(Activity activity) {
	// SharedPreferences pf = activity.getSharedPreferences("info", 0);
	// return pf.getBoolean("shortcut", false);
	// /*
	// * boolean isInstallShortcut = false; final ContentResolver cr =
	// * activity.getContentResolver(); final String AUTHORITY
	// * ="com.android.launcher.settings"; final Uri CONTENT_URI =
	// * Uri.parse("content://" +AUTHORITY + "/favorites?notify=true"); Cursor
	// * c = cr.query(CONTENT_URI,new String[] {"title","iconResource"
	// * },"title=?", new String[]
	// * {activity.getString(R.string.app_name).trim()}, null); if(c!=null &&
	// * c.getCount()>0){ isInstallShortcut = true ; } return
	// * isInstallShortcut ;
	// */
	// }

	/**
	 * 为程序创建桌面快捷方式
	 */
	// public static void addShortcut(Context context, String className) {
	// // Intent shortcut = new
	// // Intent("com.android.launcher.action.INSTALL_SHORTCUT");
	// //
	// // // 快捷方式的名称
	// // shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
	// // context.getString(R.string.app_name));
	// // shortcut.putExtra("duplicate", false); // 不允许重复创建
	// // Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
	// // shortcutIntent.setClassName(context, className);
	// // shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	// //
	// // // 快捷方式的图标
	// // ShortcutIconResource iconRes =
	// // Intent.ShortcutIconResource.fromContext(context,
	// // R.drawable.app_logo);
	// // shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
	// //
	// // context.sendBroadcast(shortcut);
	// }

	// /**
	// * 实现文本复制
	// *
	// * @param content
	// * @param context
	// */
	// @SuppressLint("NewApi")
	// public static void copy(String content, Context context) {
	// if (Build.VERSION.SDK_INT > 11) {
	// ClipboardManager cmb = (ClipboardManager) context
	// .getSystemService(Context.CLIPBOARD_SERVICE);
	// cmb.setPrimaryClip(ClipData.newPlainText("", content.trim()));
	// } else {
	// android.text.ClipboardManager c = (android.text.ClipboardManager) context
	// .getSystemService(Context.CLIPBOARD_SERVICE);
	// }
	// }

	/**
	 * 实现粘贴功能
	 * 
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String paste(Context context) {
		// 得到剪贴板管理器
		ClipboardManager cm = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return cm.getText().toString().trim();
	}

	private static long lastClickTime;

	/**
	 * 判断控件是否快速点击
	 * 
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 传入QQ号码，通过意图跳转到指定QQ聊天窗口
	 * 
	 * @param number
	 * @return
	 */
	public static void toQQNumWindow(Context context, String number) {
		Intent intent = new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + number.trim()));
		// Intent intent = new Intent(Intent.ACTION_VIEW,
		// Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=800047987"));
		context.startActivity(intent);
	}

}
