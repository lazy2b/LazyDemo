package com.lazy2b.libs.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;


/**
 * 类名: BaseTabFrgAdapter <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseTabFrgAdapter.java 58 2016-03-17 11:26:13Z lazy2b $
 */
public class BaseTabFrgAdapter<T extends Fragment> implements RadioGroup.OnCheckedChangeListener {
	public List<T> mFrgs; // 一个tab页面对应一个Fragment
	protected RadioGroup mRgs; // 用于切换tab
	protected FragmentActivity mFrgAct; // Fragment所属的Activity
	protected int mFrgId; // Activity中所要被替换的区域的id
	protected int mCurrIndex; // 当前Tab页面索引

	protected OnExtraListener mExtraListener; // 用于让调用者在切换tab时候增加新的功能

	protected boolean[] mIsAdds;

	protected int[] mRbIds;

	public BaseTabFrgAdapter() {
	}

	public BaseTabFrgAdapter(FragmentActivity frgAct, int frgId, List<T> frgs, RadioGroup rgs, int[] rgIds) {
		this.mRgs = rgs;
		this.mFrgAct = frgAct;
		this.mFrgId = frgId;
		this.mRbIds = rgIds;
		this.mFrgs = frgs;
		onInit();

	}

	public void onInit() {
		mIsAdds = new boolean[mFrgs.size()];
		for (int i = 0; i < mFrgs.size(); i++) {
			mIsAdds[i] = false;
		}
		mRgs.setOnCheckedChangeListener(this);
	}

	public int afterLoginIndex = 0;

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

		for (int i = 0; i < mRbIds.length - 1; i++) {
			if (mRbIds[i] == checkedId) {
				Fragment fragment = mFrgs.get(i);
				FragmentTransaction ft = obtainFragmentTransaction(i);

				// getCurrentFragment().onPause(); // 暂停当前tab
				// getCurrentFragment().onStop(); // 暂停当前tab

				// ft.remove(fragment).commit();
				// ft.add(fragmentContentId, fragment);

				if (fragment.isAdded()) {
					// fragment.onStart(); // 启动目标tab的onStart()
					// fragment.onResume(); // 启动目标tab的onResume()
				} else {

					if (!mIsAdds[i]) {
						ft.add(mFrgId, fragment);// .commitAllowingStateLoss();
						mIsAdds[i] = true;
					}
				}
				showTab(i); // 显示目标tab
				// ft.commitAllowingStateLoss();
				ft.commitAllowingStateLoss();

				// 如果设置了切换tab额外功能功能接口
				if (null != mExtraListener) {
					mExtraListener.OnCheckedChanged(radioGroup, checkedId, i);
				}
			}
		}

	}

	/**
	 * 切换tab
	 * 
	 * @param idx
	 */
	public void showTab(int idx) {
		for (int i = 0; i < mFrgs.size(); i++) {
			Fragment fragment = mFrgs.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);
			if (idx == i) {
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			ft.commitAllowingStateLoss();
		}
		mCurrIndex = idx; // 更新目标tab为当前tab
	}

	/**
	 * 获取一个带动画的FragmentTransaction
	 * 
	 * @param index
	 * @return
	 */
	protected FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = mFrgAct.getSupportFragmentManager().beginTransaction();
		// 设置切换动画
		/*
		 * if(index > currentTab){ ft.setCustomAnimations(R.anim.slide_left_in,
		 * R.anim.slide_left_out); }else{
		 * ft.setCustomAnimations(R.anim.slide_right_in,
		 * R.anim.slide_right_out); }
		 */

		// ft.setCustomAnimations(R.anim.slide_left_out, R.anim.slide_left_in);

		return ft;
	}

	public int getCurrIndex() {
		return mCurrIndex;
	}

	public Fragment getCurrentFragment() {
		return mFrgs.get(mCurrIndex);
	}

	public OnExtraListener getExtraListener() {
		return mExtraListener;
	}

	public void setExtraListener(OnExtraListener listener) {
		this.mExtraListener = listener;
	}

	/**
	 * 切换tab额外功能功能接口
	 */
	public interface OnExtraListener {
		public void OnCheckedChanged(RadioGroup radioGroup, int checkedId, int index);
	}

}
