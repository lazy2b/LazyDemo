package com.lazy2b.libs.utils.http;

import java.util.HashMap;

import com.lazy2b.libs.BuildConfig;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

/**
 * 
 * 所有的HTTP请求共用TASK类
 * 
 * @author jack.lin@qq.com
 *
 *         $Id: HttpTask.java 65 2016-05-25 04:19:01Z lazy2b $
 *
 */
public class HttpTask extends AsyncTask<Object, Object, byte[]> implements Callback {

	private boolean DEBUG = BuildConfig.DEBUG;

	private static final String TAG = "HttpTask";

	public enum Method {
		GET, POST
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	public boolean handleMessage(Message msg) {
		return false;
	}

	/**
	 * 菜谱HTTP Header获取
	 * 
	 * @param method
	 * @return
	 */
	public static HashMap<String, String> getHeaderList(Method method) {
		HashMap<String, String> headers = new HashMap<String, String>();
		if (method == Method.GET) { // GET Request
			headers.put("Referer", "Android GET");
		} else if (method == Method.POST) { // POST Request
			headers.put("Referer", "Android POST");
		}

		return headers;
	}

	@Override
	protected byte[] doInBackground(Object... params) {
		byte[] result = null;

		if (params.length != 0) {
			String urlStr = (String) params[0];

			boolean isGet = true;

			HashMap<String, String> headers = new HashMap<String, String>();

			Uri uri = Uri.parse(urlStr);
			if (params.length == 1) {
				headers = getHeaderList(Method.GET);
			} else if (params.length == 2) {
				headers = getHeaderList(Method.POST);
				isGet = false;
			}

			if (DEBUG) {
				Log.d(TAG, "url = " + urlStr);
			}

			if (isGet) {
				result = HttpUtil.Instance.get(urlStr, headers);
			} else {
				HashMap<String, Object> map = (HashMap<String, Object>) params[1];
				result = HttpUtil.Instance.post(urlStr, map, headers);
			}
		} else {
			// 参数错误
		}
		return result;
	}

	@Override
	protected void onPostExecute(byte[] result) {

		String response = "";
		if (result != null) {
			response = new String(result);
		}

		if (DEBUG) {
			Log.d(TAG, "response=" + response);
			;
		}

		Message msg = new Message();
		msg.what = 1;
		Bundle bundle = new Bundle();
		bundle.putString("json", response);
		msg.setData(bundle);
		handleMessage(msg);

	}

}