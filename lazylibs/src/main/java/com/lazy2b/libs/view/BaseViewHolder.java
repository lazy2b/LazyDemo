/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.utils
 * 文件名:BaseViewHolder.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.view;

import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.model.BaseModel;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;

/**
 * 类名: BaseViewHolder <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseViewHolder.java 5 2015-11-06 05:20:17Z lazy2b $
 */
public class BaseViewHolder extends BaseModel implements ILazyBase {

	public Context mCxt;
	public View mRoot;
	public Handler mHandler;

	public View mEmpty;

	public ViewGroup mContainer;
	

	public void reloadByNoNet() {
		try {
			if (mContainer != null && mEmpty != null) {
				if (mContainer.getVisibility() == View.GONE || mContainer.getVisibility() == View.INVISIBLE) {
					((ViewGroup) mContainer.getParent()).removeView(mEmpty);
					mContainer.setVisibility(View.VISIBLE);
				} else {
					mContainer.removeView(mEmpty);
				}
				mEmpty = null;
			}
		} catch (Exception e) {
		}
	}

	public View findView(int id) {
		if (mRoot != null) {
			return mRoot.findViewById(id);
		}
		return null;
	}

	public ViewGroup getEmptyView(boolean isNoNet) {
		return null;
	}

	public int getEmptyViewInsertIdx() {
		return 1;
	}

	/**
	 * 获取特殊显示的空界面LayoutParams【Root为RelativeLayout时有效】
	 * 
	 * @return
	 */
	public RelativeLayout.LayoutParams getHasRuleRlLp() {
		return null;
	}

	public void showEmptyView(boolean isNoNet) {
		try {
			if (mRoot instanceof ViewGroup) {
				ViewGroup vgRoot = (ViewGroup) mRoot;
				if (mContainer == null) {
					mContainer = vgRoot.getId() == R.id.content ? (ViewGroup) vgRoot.getChildAt(0) : vgRoot;
				}
				if (mEmpty == null) {
					mEmpty = getEmptyView(isNoNet);
				}
				if (mEmpty == null) {
					mContainer = null;
					return;
				}
				if (mContainer instanceof RelativeLayout) {
					RelativeLayout.LayoutParams rlp = getHasRuleRlLp();
					if (rlp == null) {
						rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
								RelativeLayout.LayoutParams.MATCH_PARENT);
					}
					mContainer.addView(mEmpty, mContainer.getChildCount(), rlp);
				} else {
					int idx = getEmptyViewInsertIdx();
					if (idx == -1) {
						try {
							mContainer.setVisibility(View.GONE);
							((ViewGroup) mContainer.getParent()).addView(mEmpty);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						mContainer.addView(mEmpty, idx,
								new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fill(Object... args) {
	}

	public BaseViewHolder() {
	}

	public BaseViewHolder(Context cxt, View root) {

		init(cxt, root);

	}

	public BaseViewHolder(Context cxt, Handler handler, View root) {

		init(cxt, handler, root);

	}

	protected BaseViewHolder init(Context cxt, View root) {
		ButterKnife.bind(this, root);
		mCxt = cxt;
		mRoot = root;
		try {
			onInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	protected BaseViewHolder init(Context cxt, Handler handler, View root) {
		ButterKnife.bind(this, root);
		mCxt = cxt;
		mHandler = handler;
		mRoot = root;
		try {
			onInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	protected void onInit() {
		// Toast.makeText(mCxt, mRoot.getClass().toString(),
		// Toast.LENGTH_SHORT).show();
	}

	/**
	 * 把输入值转换为Color值
	 * 
	 * @param cxt
	 *            上下文对象。便于获取values中配置的颜色值
	 * @param cArgs
	 *            可为资源ID或颜色字符串
	 * @return Color值【int】
	 */
	public int c(Object cArgs) {
		return cArgs instanceof String ? Color.parseColor((String) cArgs)
				: mCxt.getResources().getColor((Integer) cArgs);
	}

	public String s(Object obj) {
		return obj instanceof String ? (String) obj : mCxt.getString((Integer) obj);
	}

	public static BaseViewHolder create(Context cxt, View root, Class<? extends BaseViewHolder> cls) {
		try {
			return cls.newInstance().init(cxt, root);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BaseViewHolder create(Context cxt, Handler handler, View root, Class<? extends BaseViewHolder> cls) {
		try {
			return cls.newInstance().init(cxt, handler, root);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
