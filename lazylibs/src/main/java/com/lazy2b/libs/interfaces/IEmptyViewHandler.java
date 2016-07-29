/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.interfaces
 * 文件名:IEmptyViewHandler.java
 * 创  建:2015年12月30日下午3:00:19
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

import android.view.ViewGroup;

/**
 * 类名: IEmptyViewHandler <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IEmptyViewHandler.java 47 2015-12-30 10:18:09Z lazy2b $
 */
public interface IEmptyViewHandler extends ILazyBase {

	void reloadByNoNet();

	ViewGroup getEmptyView(boolean isNoNet);

	int getEmptyViewInsertIdx();

	void showEmptyView(boolean isNoNet);

}
