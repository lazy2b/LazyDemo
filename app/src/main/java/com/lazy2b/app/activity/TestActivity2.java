/**
 * 项目名:Lazy2b
 * 包  名:com.lazy2b.app.activity
 * 文件名:TestActivity1.java
 * 创  建:2015年10月31日下午2:09:26
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.lazy2b.app.R;
import com.lazy2b.app.widget.ClickCountView;
import com.lazy2b.libs.app.BaseActivity;
import com.lazy2b.libs.app.BaseAppMgr;

import butterknife.BindView;

/**
 * 类名: TestActivity1 <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: TestActivity2.java 3 2015-11-06 05:19:58Z lazy2b $
 */
public class TestActivity2 extends BaseActivity {

	@Override
	protected int getContentResId() {
		return R.layout.frg_0;
	}

	@BindView(R.id.ccv_001)
	ClickCountView ccv_001;

	public void ddddd(View view) {

		BaseAppMgr.t(ccv_001.getWidth() +":" + ccv_001.getHeight());

		Animation animation = new TranslateAnimation(Animation.ABSOLUTE,-ccv_001.getWidth(), Animation.ABSOLUTE, 0, Animation.ABSOLUTE,0, Animation.ABSOLUTE,0  );
				//AnimationUtils.loadAnimation(this, R.anim.anim_gift_in);
		animation.setDuration(300);
		ccv_001.setAnimation(animation);
		ccv_001.setVisibility(View.VISIBLE);
//		mCxt.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-654-9200")));
//		国内客服电话：<a href='tel:400-654-9200'>400-654-9200</a><br/>

//		goAct(mCxt, TestActivity1.class, false, null);
	}

	public static void goAct(Context context, Class<?> cls, boolean finishSelf, Bundle bundle) {
		try {
			Intent it = new Intent();
			it.setClass(context, cls);
//			it.setAction(cls.getSimpleName());
			if (bundle != null) {
				it.putExtras(bundle);
			}

			context.startActivity(it);

			if (finishSelf) {
				Activity activity = (Activity) context;
				activity.finish();
			}
		} catch (Exception e) {
		}
	}
}
