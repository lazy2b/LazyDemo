/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app.jni
 * 文件名:Jni.java
 * 创  建:2016年3月21日下午2:50:54
 * Copyright © 2016, GDQL All Rights Reserved.
 */
package com.lazy2b.app.jni;

/**
 * 类名: Jni <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:Administrator
 * @version $Id: Jni.java 15 2016-05-25 07:01:09Z lazy2b $
 */
public class Jni {

	static {
		System.loadLibrary("lazylibs");
	}
	
	public static native String encodeFromC(String text, int length);
	public static native String decodeFromC(String text, int length);
	
}
