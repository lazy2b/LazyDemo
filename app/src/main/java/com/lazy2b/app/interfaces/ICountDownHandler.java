/**
 * 项目名:CommSrc
 * 包  名:com.royaleu.comm.interfaces
 * 文件名:ICountDownHandler.java
 * 创  建:2015年12月21日下午2:51:05
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.app.interfaces;

import com.lazy2b.libs.interfaces.ILazyBase;

/**
 * 类名: ICountDownHandler <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:Administrator
 * @version $Id: ICountDownHandler.java 15 2016-05-25 07:01:09Z lazy2b $
 */
public interface ICountDownHandler extends ILazyBase {
	/**
	 * 倒计时完成
	 */
	void onFinish();

	/**
	 * 倒计时进行中
	 * 
	 * @param currTime
	 */
	void onTick(long currTime);
}
