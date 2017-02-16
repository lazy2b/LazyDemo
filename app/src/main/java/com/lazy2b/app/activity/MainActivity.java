/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app.activity
 * 文件名:MenuActivity.java
 * 创  建:2015年10月28日上午11:50:56
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.app.activity;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.lazy2b.app.R;
import com.lazy2b.app.adapter.MainLvAdapter;
import com.lazy2b.app.observer.SmsObserver;
import com.lazy2b.app.receiver.HomeKeyReceiver;
import com.lazy2b.libs.adapter.BaseLvAdapter;
import com.lazy2b.libs.app.BaseActivity;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.utils.ComUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 类名: MenuActivity <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: MainActivity.java 3 2015-11-06 05:19:58Z lazy2b $
 */
public class MainActivity extends BaseActivity implements ILazyBase {

	HomeKeyReceiver homeKeyRec = new HomeKeyReceiver();

	SmsObserver smsObserver = null;

	public void t(String msg) {
		Toast.makeText(mCxt, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		HttpURLConnection urlConn = null;
//		try {
//			URL url = new URL("http://www.android.com/");
//			urlConn = (HttpURLConnection) url.openConnection();
//			InputStream in = new BufferedInputStream(urlConn.getInputStream());
//
//			// readStream(in);
//		} catch (Exception ex) {
//
//		} finally {
//			urlConn.disconnect();
//		}
//
//		registerReceiver(homeKeyRec, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
//
//		getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, smsObserver);
//
//		ComUtils.serIsRunning(this, "");
//
//		// Http.pingIpAddr();
//
//		t("6啊6啊6啊");

		// String afterEncodeStr = "afterEncodeStr";
		// String newStr = ecTxt(afterEncodeStr);
		// t(ecTxt(afterEncodeStr) + "|" + Jni.decodeFromC(newStr,
		// newStr.length()));
		// transTxt(afterEncodeStr);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(homeKeyRec);
		getContentResolver().unregisterContentObserver(smsObserver);
	}

	protected void onCreate() {
		if (preOnCreate()) {

			init();


//			asdfsadfasdfsadfsadfsdf

		} else {

		}
	}

	@BindView(R.id.lv_act_main)
	ListView lv_main;

	@Override
	public void initView() {

		mAdapter = new MainLvAdapter(mCxt, mList, new Handler());
		lv_main.setAdapter(mAdapter);
		lv_main.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> root, View view, int position, long id) {
				try {
					ComUtils.goAct(mCxt, Class.forName(mList.get(position).name), false, null);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

	};

	BaseLvAdapter mAdapter;

	List<ActivityInfo> mList;

	@Override
	public void initData() {
		super.initData();
		try {
			mList = new ArrayList<ActivityInfo>(Arrays.asList(mCxt.getPackageManager().getPackageInfo(getPackageName(),
					PackageManager.GET_ACTIVITIES).activities));
			mList.remove(0);

			mUIHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
				}
			};

			smsObserver = new SmsObserver(mCxt, mUIHandler);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			// TipUtils.showLoadTips("");

			finish();

			return true;
		}

		return false;
	}

	@Override
	public void addToActStack() {// 首页不加入界面堆栈计划
		// ActStack.add(this);
	}

	@Override
	public void delByActStack() {// 首页不加入界面堆栈计划
		// ActStack.remove(this);
	}

	@Override
	public int getContentResId() {
		return R.layout.act_main;
	}

}
