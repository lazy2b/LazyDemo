/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.interfaces
 * 文件名:IFrgMutualHandler.java
 * 创  建:2015年10月22日下午2:08:50
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

/**
 * 类名: IFrgMutualHandler <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IFrgMutualHandler.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public interface IFrgMutualHandler extends ILazyBase {

	/**
	 * 处理来自Fragment的交互，参数自定义
	 * 
	 * @param args
	 * @return
	 */
	boolean handleFrgCall(Object... args);

}
