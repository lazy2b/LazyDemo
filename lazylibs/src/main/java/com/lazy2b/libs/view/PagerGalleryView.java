/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.view
 * 文件名:PagerGalleryView.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.lazy2b.libs.app.Image;
import com.lazy2b.libs.model.BaseModel;
import com.lazy2b.libs.view.PagerGalleryView.BasePGVItemModel;
import com.lidroid.xutils.util.LogUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 类名: PagerGalleryView <br/>
 * 描述: TODO. <br/>
 * 功能: 无限滚动广告栏组件. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: PagerGalleryView.java 58 2016-03-17 11:26:13Z lazy2b $
 */
@SuppressWarnings("deprecation")
public class PagerGalleryView<Item extends BasePGVItemModel> extends Gallery
		implements android.widget.AdapterView.OnItemClickListener, android.widget.AdapterView.OnItemSelectedListener,
		OnTouchListener {
	/** 显示的Activity */
	private Context mContext;
	/** 条目单击事件接口 */
	private OnGalleryItemClickListener mMyOnItemClickListener;
	/** 图片切换时间 */
	private int mSwitchTime;
	/** 自动滚动的定时器 */
	private Timer mTimer;
	/** 圆点容器 */
	private LinearLayout mOvalLayout;
	/** 当前选中的数组索引 */
	public int curIndex = 0;
	/** 上次选中的数组索引 */
	private int oldIndex = 0;
	/** 圆点选中时的背景ID */
	private int mFocusedId;
	/** 圆点正常时的背景ID */
	private int mNormalId;
	/** 图片资源ID组 */
	private int[] mAdsId;
	/** 图片网络路径数组 */
	// private String[] mUris;
	/** ImageView组 */
	private List<ImageView> listImgs;
	/** 广告条上面textView控件 */
	private TextView adgallerytxt;

	public PagerGalleryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PagerGalleryView(Context context) {
		super(context);
	}

	public PagerGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	List<Item> news = new ArrayList<Item>();

	public void start(Context context, List<Item> news, int[] adsId, int switchTime, LinearLayout ovalLayout,
			int focusedId, int normalId, TextView adgallerytxt, Button buttonBuy) {

		if (news != null)
			LogUtils.i(news.toString());

		this.news = news;
		this.mContext = context;
		this.mAdsId = adsId;
		this.mSwitchTime = switchTime;
		this.mOvalLayout = ovalLayout;
		this.mFocusedId = focusedId;
		this.mNormalId = normalId;
		this.adgallerytxt = adgallerytxt;
		ininImages();// 初始化图片组
		setAdapter(new AdAdapter());
		this.setOnItemClickListener(this);
		this.setOnTouchListener(this);
		this.setOnItemSelectedListener(this);
		this.setSoundEffectsEnabled(false);
		this.setAnimationDuration(700); // 动画时间
		this.setUnselectedAlpha(1); // 未选中项目的透明度
		// 不包含spacing会导致onKeyDown()失效!!! 失效onKeyDown()前先调用onScroll(null,1,0)可处理
		setSpacing(0);
		// 取靠近中间 图片数组的整倍数
		setSelection(0); // 默认选中中间位置为起始位置
		setFocusableInTouchMode(true);
		startTimer();// 开始自动滚动任务

	}

	/** 初始化图片组 */
	/*
	 * private void ininImages() { listImgs = new ArrayList<ImageView>(); // 图片组
	 * int len = mUris != null ? mUris.length : mAdsId.length;
	 * 
	 * for (int i = 0; i < len; i++) {
	 * 
	 * ImageView imageview = new ImageView(mContext); // 实例化ImageView的对象
	 * imageview.setScaleType(ImageView.ScaleType.FIT_XY); // 设置缩放方式
	 * imageview.setLayoutParams(new Gallery.LayoutParams(
	 * Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT));
	 * if (mUris == null) {// 本地加载图片 imageview.setImageResource(mAdsId[i]); //
	 * 为ImageView设置要显示的图片 } else { // 网络加载图片
	 * 
	 * App.imageLoader.DisplayImage(mUris[i], imageview,0);
	 * FinalBitmap.create(mContext) .display(imageview, mUris[i],
	 * imageview.getWidth(), imageview.getHeight(), null, null); }
	 * listImgs.add(imageview); }
	 * 
	 * 
	 * 
	 * initOvalLayout();// 初始化圆点
	 * 
	 * }
	 */

	public static class BasePGVItemModel extends BaseModel {

		public String imgUrl = "";
		public String tipTxt = "";

	}

	private void ininImages() {
		listImgs = new ArrayList<ImageView>(); // 图片组
		int len = news != null ? news.size() : mAdsId.length;

		for (int i = 0; i < len; i++) {

			ImageView imageview = new ImageView(mContext); // 实例化ImageView的对象
			imageview.setScaleType(ImageView.ScaleType.FIT_XY); // 设置缩放方式
			imageview.setLayoutParams(
					new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT));
			if (news == null || news.size() == 0) {// 本地加载图片
				LogUtils.i("本地加载图片");
				imageview.setImageResource(mAdsId[i]); // 为ImageView设置要显示的图片
			} else { // 网络加载图片

				Image.display(imageview, news.get(i).imgUrl);
				/*
				 * FinalBitmap.create(mContext) .display(imageview, mUris[i],
				 * imageview.getWidth(), imageview.getHeight(), null, null);
				 */
			}
			listImgs.add(imageview);
		}

		initOvalLayout();// 初始化圆点

	}

	/** 初始化圆点 */
	private void initOvalLayout() {

		if (mOvalLayout != null && listImgs.size() < 2) {// 如果只有一第图时不显示圆点容器
			mOvalLayout.removeAllViews();
			// mOvalLayout.getLayoutParams().height = 0;

		} else if (mOvalLayout != null) {
			mOvalLayout.removeAllViews();
			// 圆点的大小是 圆点窗口的 70%;
			int Ovalheight = (int) (mOvalLayout.getLayoutParams().height * 1);
			// 圆点的左右外边距是 圆点窗口的 20%;
			int Ovalmargin = (int) (mOvalLayout.getLayoutParams().height * 0.5);
			android.widget.LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Ovalheight,
					Ovalheight);
			layoutParams.setMargins(Ovalmargin, 0, Ovalmargin, 0);
			for (int i = 0; i < listImgs.size(); i++) {
				View v = new View(mContext); // 员点
				v.setLayoutParams(layoutParams);
				v.setBackgroundResource(mNormalId);
				mOvalLayout.addView(v);
			}
			// 选中第一个
			mOvalLayout.getChildAt(0).setBackgroundResource(mFocusedId);

		}
	}

	/** 无限循环适配器 */
	class AdAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			if (listImgs.size() < 2)// 如果只有一张图时不滚动
				return listImgs.size();
			return Integer.MAX_VALUE;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return listImgs.get(position % listImgs.size()); // 返回ImageView
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		int kEvent;
		if (isScrollingLeft(e1, e2)) { // 检查是否往左滑动
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else { // 检查是否往右滑动
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);
		return true;

	}

	/** 检查是否往左滑动 */
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > (e1.getX() + 50);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (MotionEvent.ACTION_UP == event.getAction() || MotionEvent.ACTION_CANCEL == event.getAction()) {
			startTimer();// 开始自动滚动任务
		} else {
			stopTimer();// 停止自动滚动任务
		}
		return false;
	}

	/** 图片切换事件 */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
		curIndex = position % listImgs.size();
		if (mOvalLayout != null && listImgs.size() > 0) { // 切换圆点
			if (listImgs.size() > 1) {
				try {
					mOvalLayout.getChildAt(oldIndex).setBackgroundResource(mNormalId); // 圆点取消
					mOvalLayout.getChildAt(curIndex).setBackgroundResource(mFocusedId);// 圆点选中

					// adgallerytxt.startAnimation(AnimationUtils.getBottom2TopShowAction(500));

					adgallerytxt.setText(news.get(curIndex).tipTxt);

					oldIndex = curIndex;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

				try {
					adgallerytxt.setText(news.get(0).tipTxt);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		// adgallerytxt.setText("" + curIndex);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	/** 项目点击事件 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (mMyOnItemClickListener != null) {
			mMyOnItemClickListener.onItemClick(curIndex);
		}
	}

	/** 设置项目点击事件监听器 */
	public void setMyOnItemClickListener(OnGalleryItemClickListener listener) {
		mMyOnItemClickListener = listener;
	}

	/** 项目点击事件监听器接口 */
	public interface OnGalleryItemClickListener {
		/**
		 * @param curIndex
		 *            //当前条目在数组中的下标
		 */
		void onItemClick(int curIndex);
	}

	/** 停止自动滚动任务 */
	public void stopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	/** 开始自动滚动任务 图片大于1张才滚动 */
	public void startTimer() {
		if (mTimer == null && listImgs.size() > 0 && mSwitchTime > 0) {
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
				public void run() {
					handler.sendMessage(handler.obtainMessage(1));
				}
			}, mSwitchTime, mSwitchTime);
		}
	}

	/** 处理定时滚动任务 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 不包含spacing会导致onKeyDown()失效!!!
			// 失效onKeyDown()前先调用onScroll(null,1,0)可处理
			onScroll(null, null, 1, 0);
			onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
		}
	};
}
