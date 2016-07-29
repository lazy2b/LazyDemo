/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app.model
 * 文件名:SmsInfoModel.java
 * 创  建:2015年11月11日下午12:30:31
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.app.model;

import android.database.Cursor;

import com.lazy2b.libs.model.BaseModel;

/**
 * 类名: SmsInfoModel <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:linjp@ql.com
 * @version $Id: SmsInfoModel.java 7 2015-11-13 10:32:26Z lazy2b $
 */
public class SmsInfoModel extends BaseModel {

	public SmsInfoModel(Cursor cursor) {
		String[] ns = cursor.getColumnNames();
		StringBuffer sb = new StringBuffer();
		for (String cName : ns) {

		}

	}

	public int _id;
	// 短信序号，如100
	public int thread_id;
	// 对话的序号，如100，与同一个手机号互发的短信，其序号是相同的　　
	public int address;
	// 发件人地址，即手机号，如+86138138000　　
	public int person;
	// 发件人，如果发件人在通讯录中则为具体姓名，陌生人为null　　
	public long date;
	// 日期，long型，如1346988516，可以对日期显示格式进行设置　　
	public int protocol;
	// 协议0SMS_RPOTO短信，1MMS_PROTO彩信　　
	public int read;
	// 是否阅读0未读，1已读　　
	public int status;
	// 短信状态-1接收，0complete,64pending,128failed　　
	public int type;
	// 短信类型1是接收到的，2是已发出　　
	public String body;
	// 短信具体内容
	public int service_center;
	// 短信服务中心号码编号，如+8613800755500
}
