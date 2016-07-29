/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.interfaces
 * 文件名:IHolderHandler.java
 * 创  建:2015年12月1日下午9:36:59
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

import com.lazy2b.libs.view.BaseViewHolder;

/**
 * 类名: IHolderHandler <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IHolderHandler.java 25 2015-12-01 13:50:03Z lazy2b $
 */
public interface IHolderHandler<T extends BaseViewHolder> extends ILazyBase {
	/**
	 * 返回每个界面自己的ViewHolder类，需各子界面自己实现
	 * 
	 * @return
	 */
	public Class<T> getHolderCls();

	/**
	 * 返回每个界面的ViewHolder对象。
	 * 
	 * @param cls
	 *            每个界面自己的ViewHolder类，否则拿{@link #getHolderCls()} 里面定义好的。
	 * @return
	 */
	public T getHolder(Class<T>... cls);
}
