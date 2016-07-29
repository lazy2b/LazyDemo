/**
 * 项目名:BJBox
 * 包  名:com.royaleu.bjbox.observer
 * 文件名:SmsObserver.java
 * 创  建:2015年11月11日上午11:14:28
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.app.observer;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

/**
 * 类名: SmsObserver <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:linjp@ql.com
 * @version $Id: SmsObserver.java 6 2015-11-13 01:45:45Z lazy2b $
 */
public class SmsObserver extends ContentObserver {

	protected Context mCxt;

	protected Handler mHandler;

	// getContentResolver().registerContentObserver(Uri.parse
	// ("content://sms"), true, new SmsObserver(new Handler()));
	public SmsObserver(Handler handler) {
		super(handler);
	}

	public SmsObserver(Context cxt, Handler handler) {
		this(handler);
		mCxt = cxt;
		mHandler = handler;
	}

	// 短信相关权限：
	// <!-- 发送消息-->
	// <uses-permission android:name="android.permission.SEND_SMS"/>
	// <!-- 阅读消息-->
	// <uses-permission android:name="android.permission.READ_SMS"/>
	// <!-- 写入消息-->
	// <uses-permission android:name="android.permission.WRITE_SMS" />
	// <!-- 接收消息 -->
	// <uses-permission android:name="android.permission.RECEIVE_SMS"/>
	// 相关的协议：
	// content://sms/inbox 收件箱
	// content://sms/sent 已发送
	// content://sms/draft 草稿
	// content://sms/outbox 发件箱
	// content://sms/failed 发送失败
	// content://sms/queued 待发送列表
	// _id
	// 短信序号，如100
	// 　　thread_id
	// 对话的序号，如100，与同一个手机号互发的短信，其序号是相同的　　
	// 　　address
	// 发件人地址，即手机号，如+86138138000　　
	// 　　person
	// 发件人，如果发件人在通讯录中则为具体姓名，陌生人为null　　
	// 　　date
	// 日期，long型，如1346988516，可以对日期显示格式进行设置　　
	// 　　protocol
	// 协议0SMS_RPOTO短信，1MMS_PROTO彩信　　
	// 　　read
	// 是否阅读0未读，1已读　　
	// 　　status
	// 短信状态-1接收，0complete,64pending,128failed　　
	// 　　type
	// 短信类型1是接收到的，2是已发出　　
	// 　　body
	// 短信具体内容
	// 　　service_center
	// 短信服务中心号码编号，如+8613800755500

	String getStr(Cursor cursor, String cName) {
		if (!cName.contains("date"))
			return cursor.getString(cursor.getColumnIndex(cName));
		return new SimpleDateFormat("MM/dd hh:mm:ss").format(new Date(cursor
				.getLong(cursor.getColumnIndex(cName))));
	}

	protected void printSms(String uri) {

		LogUtils.i("@@@@" + uri + "-----------------------");

		Cursor cursor = mCxt.getContentResolver().query(Uri.parse(uri), null,
				null, null, null);
		// 遍历查询结果获取用户正在发送的短信
		while (cursor.moveToNext()) {
			String[] ns = { "_id", "address", "date", "read", "status", "type",
					"body", "seen", "sync_ver" };
			StringBuffer sb = new StringBuffer();
			for (String cName : ns) {
				try {
					sb.append(cName).append("=>").append(getStr(cursor, cName))
							.append(" | ");
				} catch (Exception e) {
				}
			}
			LogUtils.d(sb.toString());

			// StringBuffer sb = new StringBuffer();
			// // 获取短信的发送地址
			// sb.append("发送地址："
			// + cursor.getString(cursor.getColumnIndex("address")));
			// // 获取短信的标题
			// sb.append("\n标题："
			// + cursor.getString(cursor.getColumnIndex("subject")));
			// // 获取短信的内容
			// sb.append("\n内容：" +
			// cursor.getString(cursor.getColumnIndex("body")));
			// // 获取短信的发送时间
			// Date date = new
			// Date(cursor.getLong(cursor.getColumnIndex("date")));
			// // 格式化以秒为单位的日期
			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
			// sb.append("\n时间：" + sdf.format(date));
			// System.out.println("查询到的正在发送的短信：" + sb.toString());
			// Toast.makeText(MonitorSms.this, sb.toString(), Toast.LENGTH_LONG)
			// .show();
			// txtView.setText(sb.toString());
		}
		cursor.close();
		cursor = null;
		LogUtils.i("------------------------------------------------");
	}

	@Override
	public void onChange(boolean selfChange) {
		LogUtils.e("onChange------------------------------------------------");
		// 查询发送向箱中的短信
		String[] smsUris = {
				// "content://sms/inbox", // 收件箱
				"content://sms/sent", // 已发送
				// "content://sms/draft", // 草稿
//				"content://sms/outbox", // 发件箱
//				"content://sms/failed", // 发送失败
//				"content://sms/queued" // 待发送列表
		};

		for (String uri : smsUris) {
			printSms(uri);
		}

		super.onChange(selfChange);
	}
}
