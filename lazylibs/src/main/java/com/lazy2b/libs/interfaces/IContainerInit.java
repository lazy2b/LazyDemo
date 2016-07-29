/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.interfaces
 * 文件名:IContainerInit.java
 * 创  建:2015年10月26日下午2:16:05
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

/**
 * 类名: IContainerInit <br/>
 * 描述: 通用Activity初始化方法定义. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IContainerInit.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public interface IContainerInit extends ILazyBase {

	/**
	 * onCreate 调用之前
	 * 
	 * @return 可返回是否继续执行onCreate
	 */
	boolean preOnCreate();

	void init();

	/**
	 * 获取初始化数据，如 Intent 中传过来的参数，onCreate中调用，先于 {@link #findView()}
	 * 
	 * @return
	 */
	void initData();

	/**
	 * 获取界面，onCreate中调用，后于 {@link #initData()}
	 * 
	 * @return
	 */
	void findView();

	/**
	 * 初始化界面，onCreate中调用，后于 {@link #findView()}
	 * 
	 * @return
	 */
	void initView();

}
