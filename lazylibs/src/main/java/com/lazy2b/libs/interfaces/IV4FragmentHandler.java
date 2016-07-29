/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.interfaces
 * 文件名:IV4FragmentHandler.java
 * 创  建:2015年10月22日上午10:12:06
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 类名: IV4FragmentHandler <br/>
 * 描述: TODO.extends IV4FragmentHandler <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IV4FragmentHandler.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public interface IV4FragmentHandler extends ILazyBase {

	public FragmentManager frgManager();

	public FragmentTransaction frgTrans();

	public int addFrg(Fragment frg);

	public int addFrg(Fragment frg, String tag);

	public int addFrgBackStack(Fragment frg);

	public int addFrgBackStack(Fragment frg, String tag);

	public Fragment findFrg(int id);

	public Fragment findFrg(String tag);

}
