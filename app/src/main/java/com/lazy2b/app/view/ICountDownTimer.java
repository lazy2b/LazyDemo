/**
 * 项目名:CommSrc
 * 包  名:com.royaleu.comm.view
 * 文件名:ICountDownTimer.java
 * 创  建:2015年10月29日下午5:44:03
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.app.view;

import com.lazy2b.app.interfaces.ICountDownHandler;

import android.os.CountDownTimer;

/**
 * 类名: ICountDownTimer <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:linjp@ql.com
 * @version $Id: ICountDownTimer.java 15 2016-05-25 07:01:09Z lazy2b $
 */
public class ICountDownTimer<IHander extends ICountDownHandler> extends CountDownTimer {

	protected IHander mHandler;

	public ICountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	public ICountDownTimer(IHander handler, long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		this.mHandler = handler;
	}

	@Override
	public void onFinish() {
		if (mHandler != null) {
			mHandler.onFinish();
		}
	}

	@Override
	public void onTick(long millisUntilFinished) {
		if (mHandler != null) {
			mHandler.onTick(millisUntilFinished);
		}
	}
}
