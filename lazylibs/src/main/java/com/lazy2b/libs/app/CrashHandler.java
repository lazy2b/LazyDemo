/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.app
 * 文件名:CrashHandler.java
 * 创  建:2016年1月26日上午11:59:58
 * Copyright © 2016, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.lazy2b.libs.constants.AppData;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.utils.ActStack;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * 类名: CrashHandler <br/>
 * 描述: 异常崩溃处理类 <br/>
 * 功能: 当程序发生未捕获异常时，由该类来接管程序并记录发送错误报告. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public class CrashHandler implements UncaughtExceptionHandler, ILazyBase {

	/** 错误日志文件名称 */
	static final String LOG_NAME = "/crash.txt";

	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	BaseAppMgr application;

	/**
	 * @brief 构造函数
	 * @details 获取系统默认的UncaughtException处理器，设置该CrashHandler为程序的默认处理器 。
	 * @param context
	 *            上下文
	 */
	public CrashHandler(BaseAppMgr application) {
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
		this.application = application;
	}

	/**
	 * @brief 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// 如果用户没有处理则让系统默认的异常处理器来处理
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			// 等待会后结束程序
			try {
				Log.i(LOG_NAME, "exit start");
				Thread.sleep(3000);
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(10);
				// Intent intent = new
				// Intent(application.getApplicationContext(),
				// MainActivity.class);
				// PendingIntent restartIntent = PendingIntent.getActivity(
				// application.getApplicationContext(), 0, intent,
				// Intent.FLAG_ACTIVITY_NEW_TASK);
				// //退出程序
				// AlarmManager mgr =
				// (AlarmManager)application.getSystemService(Context.ALARM_SERVICE);
				// mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
				// restartIntent); // 1秒钟后重启应用
				ActStack.finishAll();
				Log.i(LOG_NAME, "exit end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @brief 自定义错误处理，收集错误信息
	 * @details 发送错误报告等操作均在此完成
	 * @param ex
	 *            异常
	 * @return true：如果处理了该异常信息；否则返回false。
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return true;
		}
		ex.printStackTrace();
		// 提示错误消息
		new Thread() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					Toast.makeText(application.getApplicationContext(), "应用发生异常，即将退出！", Toast.LENGTH_LONG).show();
					Looper.loop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		// 保存错误报告文件
		saveCrashInfoToFile(ex);
		return true;
	}

	/**
	 * @brief 保存错误信息到文件中
	 * @param ex
	 *            异常
	 */
	private void saveCrashInfoToFile(Throwable ex) {
		final StackTraceElement[] stack = ex.getStackTrace();
		final String message = ex.getMessage();
		/* 准备错误日志文件 */
		File logFile = new File("/sdcard/" + AppData.PACKAGE_NAME + "_" + AppData.VER_NAME + "_"
				+ Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ".txt");
		// File logFile = new File("\\" + application.getPackageName() +
		// LOG_NAME);
		if (!logFile.getParentFile().exists()) {
			logFile.getParentFile().mkdirs();
		}
		/* 写入错误日志 */
		FileWriter fw = null;
		final String lineFeed = "\r\n";
		try {
			fw = new FileWriter(logFile, true);
			fw.write(new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + lineFeed
					+ lineFeed);
			fw.write(message + lineFeed);
			for (int i = 0; i < stack.length; i++) {
				fw.write(stack[i].toString() + lineFeed);
			}
			fw.write(lineFeed);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fw)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
