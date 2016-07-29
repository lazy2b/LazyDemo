/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.utils
 * 文件名:DialogBuilder.java
 * 创  建:2015年10月20日下午5:35:19
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.utils;

import com.lazy2b.libs.interfaces.ILazyBase;

import android.app.Dialog;
import android.content.Context;

/**
 * 类名: DialogBuilder <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: DialogBuilder.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public interface DialogBuilder extends ILazyBase {

	Dialog create(Context mCxt);

	void upd(Dialog dialog);

}
