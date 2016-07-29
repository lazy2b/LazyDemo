/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.interfaces
 * 文件名:IViewHolder.java
 * 创  建:2015-11-7上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

import android.content.Context;
import android.view.View;

/**
 * 类名: IViewHolder <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IViewHolder.java 8 2015-11-07 02:27:21Z lazy2b $
 */
public interface IViewHolder extends ILazyBase {

	void fill(Object... args);

	IViewHolder init(Context cxt, View root);
}
