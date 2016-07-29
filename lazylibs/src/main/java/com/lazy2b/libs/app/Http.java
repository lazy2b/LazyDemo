/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.app
 * 文件名:Http.java
 * 创  建:2016年1月26日上午11:59:58
 * Copyright © 2016, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.lazy2b.libs.constants.AppData;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.model.BaseModel;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.util.PreferencesCookieStore;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * 类名: Http <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public class Http implements ILazyBase {
	private static HttpUtils http;

	public static class HttpConfig extends BaseModel {
		public int timeOut = HTTP_TIMEOUT;
		public int soTimeOut = HTTP_SO_TIMEOUT;
		public int cacheTime = HTTP_CACHE_TIME;
		public String charset = "UTF-8";
		public boolean needCookie = false;
		public String userAgent = "";
	}

	public static final String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
	public static final String AcceptEncoding = "gzip, deflate, sdch";

	/**
	 * 连接超时：connectionTimeout指的是连接一个url的连接等待时间。
	 */
	public static final int HTTP_TIMEOUT = 10 * 1000;
	/**
	 * 读取数据超时：soTimeout指的是连接上一个url，获取response的返回等待时间
	 */
	public static final int HTTP_SO_TIMEOUT = 10 * 1000;
	public static final int HTTP_CACHE_TIME = 0 * 1000;

	public synchronized static void init(Context cxt, HttpConfig cfg) {
		synchronized (String.class) {
			try {
				if (http == null) {
					if (cfg == null) {
						init(cxt);
						return;
					}
					// 设置请求超时时间为10秒
					http = new HttpUtils(cfg.timeOut);
					http.configSoTimeout(cfg.soTimeOut);
					http.configResponseTextCharset(cfg.charset);
					http.configUserAgent(cfg.userAgent);
					// 设置当前请求的缓存时间
					http.configCurrentHttpCacheExpiry(cfg.cacheTime);
					// 设置默认请求的缓存时间
					http.configDefaultHttpCacheExpiry(cfg.cacheTime);
					http.configRequestRetryCount(0);
					// 保存服务器端(Session)的Cookie
					if (cfg.needCookie) {
						PreferencesCookieStore cookieStore = new PreferencesCookieStore(cxt);
						cookieStore.clear(); // 清除原来的cookie
						http.configCookieStore(cookieStore);
					}
					LogUtils.i("初始化[Http]成功！");
				}
			} catch (Exception e) {
				LogUtils.i("初始化[Http]失败！");
				e.printStackTrace();
			}
		}
	}

	public synchronized static void init(Context cxt) {
		synchronized (String.class) {
			try {
				if (http == null) {
					// 设置请求超时时间为10秒
					http = new HttpUtils(HTTP_TIMEOUT);
					http.configSoTimeout(HTTP_SO_TIMEOUT);
					http.configResponseTextCharset("UTF-8");
					http.configUserAgent(usrAgent(cxt));
					// 设置当前请求的缓存时间
					http.configCurrentHttpCacheExpiry(HTTP_CACHE_TIME);
					// 设置默认请求的缓存时间
					http.configDefaultHttpCacheExpiry(HTTP_CACHE_TIME);
					http.configRequestRetryCount(0);
					// 保存服务器端(Session)的Cookie
					PreferencesCookieStore cookieStore = new PreferencesCookieStore(cxt);
					cookieStore.clear(); // 清除原来的cookie
					http.configCookieStore(cookieStore);
					LogUtils.i("初始化[Http]成功！");
				}
			} catch (Exception e) {
				LogUtils.i("初始化[Http]失败！");
				e.printStackTrace();
			}
		}
	}

	public synchronized static void get(String url, RequestCallBack<?> callBack) {
		LogUtils.d("url==>" + url);
		RequestParams params = new RequestParams();
		// params.addHeader("Accept", Accept);
		params.addHeader("Accept-Encoding", AcceptEncoding);
		params.addHeader("Vary", "Accept-Encoding");
		http.send(HttpRequest.HttpMethod.GET, url, params, callBack);
	}

	public synchronized static void post(String url, Object bodyUnfixParms, RequestCallBack<?> callBack) {
		LogUtils.d("url==>" + url);
		http.send(HttpRequest.HttpMethod.POST, url, param(bodyUnfixParms), callBack);
	}

	public static String usrAgent(Context cxt) {
		return "Mozilla/5.0 (" + cxt.getPackageName() + "/" + AppData.VER_NAME + ") (Android; Android OS "
				+ android.os.Build.VERSION.RELEASE + ";" + Locale.getDefault().getLanguage() + ")";
	}

	@Deprecated
	public final static boolean pingIpAddr() {
		String mPingIpAddrResult;
		LogUtils.e("start ping:" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
		try {
			String ipAddress = "www.baidu.com";
			Process p = Runtime.getRuntime().exec("ping -c 1 -i 10 -w 1" + ipAddress);
			int status = p.waitFor();
			if (status == 0) {
				LogUtils.e("result ping:" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
				return true;
			} else {
				mPingIpAddrResult = "Fail: IP addr not reachable";
			}
		} catch (IOException e) {
			mPingIpAddrResult = "Fail: IOException";
		} catch (InterruptedException e) {
			mPingIpAddrResult = "Fail: InterruptedException";
		}
		LogUtils.e("fail ping:" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
		return false;
	}

	public static boolean canUsableUrl(String url) {
		boolean result = false;
		BufferedReader in = null;
		try {
			java.net.URL oUrl = new java.net.URL(url);
			in = new BufferedReader(new InputStreamReader(oUrl.openStream()));
			result = in.ready();
		} catch (Exception e) { // Report any errors that arise
			result = false;
			LogUtils.e(e.getMessage());
			LogUtils.e("Usage:   java   HttpClient   <URL>   [<filename>]");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static boolean isAvailable(Context cxt) {
		try {
			if (cxt != null) {
				return ((ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()
						.isConnectedOrConnecting();
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static RequestParams param(Object unfixParms) {
		RequestParams params = new RequestParams();
		try {
			if (unfixParms != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> up = (Map<String, Object>) unfixParms;
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				LogUtils.d("本次请求参数如下:");
				for (String key : up.keySet()) {
					list.add(new BasicNameValuePair(key, up.get(key).toString()));
					LogUtils.d("key==>" + key + " | value==>" + up.get(key).toString());
				}
				params.addBodyParameter(list);
			}
			// params.addHeader("Accept", Accept);
			params.addHeader("Vary", "Accept-Encoding");
			params.addHeader("Accept-Encoding", AcceptEncoding);
		} catch (Exception e) {
		}
		return params;
	}

}