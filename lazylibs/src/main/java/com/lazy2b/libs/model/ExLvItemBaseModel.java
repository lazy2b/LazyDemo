/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.model
 * 文件名:ExLvItemBaseModel.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.model;

import java.util.List;

/**
 * 类名: ExLvItemBaseModel <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
@SuppressWarnings("serial")
public class ExLvItemBaseModel<T extends List<?>> extends BaseModel {
	public T mChildList;
}
