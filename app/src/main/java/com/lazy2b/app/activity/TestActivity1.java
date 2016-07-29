/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app.activity
 * 文件名:TestActivity1.java
 * 创  建:2015年10月31日下午2:09:26
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.app.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.lazy2b.app.R;
import com.lazy2b.libs.app.BaseHttpActivity;
import com.lazy2b.libs.model.RespBaseModel;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

/**
 * 类名: TestActivity1 <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: TestActivity1.java 3 2015-11-06 05:19:58Z lazy2b $
 */
public class TestActivity1 extends BaseHttpActivity {

	@BindView(R.id.tv_title_center)
	TextView test;

	@Override
	public int getContentResId() {
		return R.layout.tpl_title_bar;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// init();

		// 外部矩形弧度
		float[] outerR = new float[] { 10, 10, 10, 10, 50, 50, 10, 10 };
		// 内部矩形与外部矩形的距离
		RectF inset = new RectF(100, 100, 50, 50);
		// 内部矩形弧度
		float[] innerRadii = new float[] { 20, 20, 20, 20, 20, 20, 20, 20 };

		RoundRectShape rr = new RoundRectShape(outerR, inset, innerRadii);
		ShapeDrawable drawable = new ShapeDrawable(rr);
		// 指定填充颜色
		drawable.getPaint().setColor(Color.BLUE);
		// 指定填充模式
		drawable.getPaint().setStyle(Paint.Style.FILL);
	}

	@Override
	public void initData() {
		LogUtils.i("[TestActivity1.onCreate]------------------------------------------------");
	}

	@Override
	public void findView() {
	}

	String url = "http://www.lazy2b.com/api/getHeader.php";

	long ost = 0l, oet = 0L, ott = 0L;
	long ast = 0l, aet = 0L, att = 0L;
	long xst = 0l, xet = 0L, xtt = 0L;

	public static String getDateStr(long time) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
	}

	@Override
	public void initView() {

		// HttpRequest.get(url, new BaseHttpRequestCallback<String>() {
		// @Override
		// public void onStart() {
		// ost = Calendar.getInstance().getTimeInMillis();
		// LogUtils.i("[OkHttpFinal][onStart]" + getDateStr(ost));
		// super.onStart();
		// }
		//
		// @Override
		// protected void onSuccess(String respStr) {
		// oet = Calendar.getInstance().getTimeInMillis();
		// LogUtils.i("[OkHttpFinal][onSuccess]" + getDateStr(oet) + "|use(ms):"
		// + (oet - ost));
		// Toast.makeText(mCxt, "[OkHttpFinal][onSuccess]" + respStr,
		// Toast.LENGTH_SHORT).show();
		// super.onSuccess(respStr);
		// }
		// });

		new AsyncHttpClient().get(url, new TextHttpResponseHandler() {

			@Override
			public void onStart() {
				ast = Calendar.getInstance().getTimeInMillis();
				LogUtils.i("[AsyncHttpClient][onStart]" + getDateStr(ast));
				super.onStart();
			}

			public void onSuccess(int paramInt, cz.msebera.android.httpclient.Header[] paramArrayOfHeader,
					String result) {
				aet = Calendar.getInstance().getTimeInMillis();
				LogUtils.i("[AsyncHttpClient][onSuccess]" + getDateStr(aet) + "|use(ms):" + (aet - ast));
				Toast.makeText(mCxt, "[AsyncHttpClient][onSuccess]" + result, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
			};
		});

		// new HttpTask() {
		//
		// protected void onPreExecute() {
		//
		// LogUtils.i("[HttpTask][onPreExecute]" + getDateStr());
		//
		// };
		//
		// @SuppressLint("NewApi")
		// public boolean handleMessage(android.os.Message msg) {
		// LogUtils.i("[HttpTask][handleMessage]" + getDateStr());
		// Toast.makeText(mCxt, "[HttpTask][handleMessage]" +
		// msg.getData().getString("json", "dddd"),
		// Toast.LENGTH_SHORT).show();
		//
		// return true;
		//
		// };
		// }.execute(url);

		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onStart() {
				xst = Calendar.getInstance().getTimeInMillis();
				LogUtils.i("[HttpUtils][onStart]" + getDateStr(xst));
				super.onStart();
			}

			@Override
			public void onFailure(com.lidroid.xutils.exception.HttpException arg0, String arg1) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> resp) {
				xet = Calendar.getInstance().getTimeInMillis();
				LogUtils.i("[HttpUtils][onSuccess]" + getDateStr(xet) + "|use(ms):" + (xet - xst));
//				Toast.makeText(mCxt, "[HttpUtils][onSuccess]" + resp.result, Toast.LENGTH_SHORT).show();
			}
		});

		// test.setBackgroundDrawable(drawable);
		// get("是的", "http://www.lazy2b.com/api/getHeader.php",
		// RespBaseModel.class);

	}

	@Override
	public void onReqStart(String action) {
//		LogUtils.i("[OkHttp][onReqStart]" + getDateStr());
		super.onReqStart(action);
	}

	@Override
	public void onSuccess(RespBaseModel resp) {
//		LogUtils.i("[OkHttp][onSuccess]" + getDateStr());
		Toast.makeText(mCxt, "[onSuccess]" + resp.respStr, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFailure(String action, HttpException error, String msg) {
	}

	// @Override
	// public void onFailure(String action, Exception ex) {
	// }

}
