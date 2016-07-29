package com.lazy2b.app.receiver;
/**
 * 项目名:BetNotice
 * 包  名:com.royaleu.BetNotice.receiver
 * 文件名:HomeKeyReceiver.java
 * 创  建:2015年10月31日下午3:06:51
 * Copyright © 2015, GDQL All Rights Reserved.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 类名: HomeKeyReceiver <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: HomeKeyReceiver.java 3 2015-11-06 05:19:58Z lazy2b $
 */
public class HomeKeyReceiver extends BroadcastReceiver {
	String SYSTEM_REASON = "reason";
	String SYSTEM_HOME_KEY = "homekey";
	String SYSTEM_HOME_KEY_LONG = "recentapps";

	@Override
	public void onReceive(Context cxt, Intent intent) {
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
			String reason = intent.getStringExtra(SYSTEM_REASON);
			if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
				// 表示按了home键,程序到了后台
				Toast.makeText(cxt, "home", 1).show();
			} else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
				// 表示长按home键,显示最近使用的程序列表
			}
		}
	}

}
