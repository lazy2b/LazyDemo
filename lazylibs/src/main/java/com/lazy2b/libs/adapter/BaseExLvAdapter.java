/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:BaseExLvAdapter.java
 * 创  建:2015年10月20日下午4:04:30
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.model.ExLvItemBaseModel;
import com.lazy2b.libs.view.BaseViewHolder;
import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import butterknife.ButterKnife;

/**
 * 类名: BaseExLvAdapter <br/>
 * 描述: TODO. <br/>
 * 功能: 如果子项需要显示不同的View时请自定义{@link #getChildViewTag()},否则按默认即可. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public abstract class BaseExLvAdapter<T extends ExLvItemBaseModel, CT extends Object> extends BaseExpandableListAdapter
		implements ILazyBase {

	protected List<T> mList = null;

	protected List<List<CT>> mChildList = null;

	protected Handler mUIHandler = null;

	protected Context mCxt = null;

	protected LayoutInflater mInflater = null;

	protected int mGroupLayoutResId = android.R.layout.activity_list_item;
	protected int mChildLayoutResId = android.R.layout.activity_list_item;

	private BaseExLvAdapter(Context cxt, List<T> groupList, Handler handler) {

		mCxt = cxt;
		mUIHandler = handler;
		setGroupList(groupList);

	}

	protected View inflate(int resId) {
		if (mInflater != null)
			return mInflater.inflate(resId, null);
		return null;
	}

	/*
	 * **********************************************************************
	 * start !!!!!!!!!!Child about!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * *********************************************************************
	 */
	public BaseExLvAdapter(Context cxt, List<T> groupList, List<List<CT>> childList, Handler handler, int groupResId,
			int childResId) {
		this(cxt, groupList, handler);
		setChildList(childList);
		mChildLayoutResId = childResId;
		mGroupLayoutResId = groupResId;
		mInflater = LayoutInflater.from(cxt);
	}

	public void setChild(int index, List<CT> childList) {

		try {
			if (mChildList == null) {

				mChildList = new ArrayList<List<CT>>();

			}

			if (childList != null) {

				mChildList.set(index, childList);

			}
		} catch (Exception e) {
		}

	}

	public void setChildList(List<List<CT>> _childList) {

		try {
			if (mChildList == null) {

				mChildList = new ArrayList<List<CT>>();

			}

			if (_childList != null) {

				mChildList.clear();
				mChildList.addAll(_childList);

			}
		} catch (Exception e) {
		}

	}

	public void addChild(int index, List<CT> childList) {

		if (mChildList == null) {

			mChildList = new ArrayList<List<CT>>();

		}

		if (childList != null) {

			mChildList.add(index, childList);

		}

	}

	public void addChildList(List<List<CT>> childList) {

		if (mChildList == null) {

			mChildList = new ArrayList<List<CT>>();

		}

		if (childList != null) {

			mChildList.addAll(childList);

		}

	}

	protected static class BaseChildHolder extends BaseViewHolder {
		public int groupPosition;
		public int childPosition;

		@Override
		public void fill(Object... args) {
			super.fill(args);
		}
	}

	/**
	 * 如果子项需要显示不同的View时请子定义
	 * 
	 * @return
	 */
	protected int getChildViewTag() {
		// try {
		// return mSnTplRes.get(BetUtils.PT);
		// } catch (Exception e) {
		// e.printStackTrace();
		return mChildLayoutResId;
		// }
	}

	/**
	 * @param view
	 * @return
	 */
	private BaseChildHolder getChildHolder(View view, Class<? extends BaseChildHolder> cls) {
		BaseChildHolder holder = getChildHolder();
		if (holder != null) {
			ButterKnife.bind(holder, view);
		} else {
			holder = (BaseChildHolder) BaseViewHolder.create(mCxt, view, cls);
		}
		return holder;
	}

	protected BaseChildHolder getChildHolder() {
		return null;
	}

	protected Class<? extends BaseChildHolder> getChildHolderClass() {
		return BaseChildHolder.class;
	}

	protected abstract void handleChildView(BaseChildHolder _holder);

	protected void initChildView(BaseChildHolder _holder) {

	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
		BaseChildHolder holder;

		int childViewTag = getChildViewTag();

		if (view == null || (view != null && view.getTag(childViewTag) == null)) {

			try {
				view = inflate(mChildLayoutResId);
			} catch (Exception e) {
				LogUtils.i(e.getMessage());
			}

			holder = getChildHolder(view, getChildHolderClass());

			holder.groupPosition = groupPosition;
			holder.childPosition = childPosition;

			initChildView(holder);

			view.setTag(childViewTag, holder);

		} else {

			holder = (BaseChildHolder) view.getTag(childViewTag);

			holder.groupPosition = groupPosition;
			holder.childPosition = childPosition;

		}
		handleChildView(holder);

		return view;
	}

	/*
	 * **********************************************************************
	 * end !!!!!!!!!!Child about!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * *********************************************************************
	 */

	/*
	 * **********************************************************************
	 * **********************************************************************
	 * **********************************************************************
	 * **********************************************************************
	 * **********************************************************************
	 * **********************************************************************
	 */

	/*
	 * **********************************************************************
	 * start !!!!!!!!!!Group about!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * *********************************************************************
	 */
	public BaseExLvAdapter(Context cxt, List<T> groupList, Handler handler, int groupResId, int childResId) {

		this(cxt, groupList, handler);
		mChildLayoutResId = childResId;
		mGroupLayoutResId = groupResId;
		mInflater = LayoutInflater.from(cxt);
	}

	public void setGroup(int index, T item) {

		try {
			if (mList == null) {

				mList = new ArrayList<T>();

			}

			if (item != null) {

				mList.set(index, item);

			}
		} catch (Exception e) {
		}

	}

	public void setGroupList(List<T> _list) {

		if (mList == null) {
			mList = new ArrayList<T>();
		}

		if (_list != null) {
			mList.clear();
			mList.addAll(_list);
		}

	}

	public void addGroup(int index, T item) {

		if (mList == null) {

			mList = new ArrayList<T>();

		}

		if (item != null) {

			mList.add(index, item);

		}

	}

	public void addGroupList(List<T> _list) {

		if (mList == null) {

			mList = new ArrayList<T>();

		}

		if (_list != null) {

			mList.addAll(_list);

		}

	}

	protected static class BaseGroupHolder extends BaseViewHolder {
		public int groupPosition;
		public boolean isExpanded;

		@Override
		public void fill(Object... args) {
		}
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
		BaseGroupHolder holder;

		if (view == null || (view != null && view.getTag(mGroupLayoutResId) == null)) {

			try {
				view = inflate(mGroupLayoutResId);
			} catch (Exception e) {
				LogUtils.i(e.getMessage());
			}

			holder = getGroupHolder(view, getGroupHolderClass());

			holder.groupPosition = groupPosition;
			holder.isExpanded = isExpanded;

			initGroupView(holder);

			view.setTag(mGroupLayoutResId, holder);

		} else {

			holder = (BaseGroupHolder) view.getTag(mGroupLayoutResId);

			holder.groupPosition = groupPosition;
			holder.isExpanded = isExpanded;

		}

		handleGroupView(holder);

		return view;
	}

	protected Class<? extends BaseGroupHolder> getGroupHolderClass() {
		return BaseGroupHolder.class;
	}

	protected BaseGroupHolder getGroupHolder() {
		return null;
	}

	protected BaseGroupHolder getGroupHolder(View view, Class<? extends BaseGroupHolder> cls) {
		BaseGroupHolder holder = getGroupHolder();
		if (holder != null) {
			ButterKnife.bind(holder, view);
		} else {
			holder = (BaseGroupHolder) BaseViewHolder.create(mCxt, view, cls);
		}
		return holder;
	}

	protected void handleGroupView(BaseGroupHolder _holder) {

	}

	protected void initGroupView(BaseGroupHolder _holder) {
	}

	/*
	 * **********************************************************************
	 * end !!!!!!!!!!Group about!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * *********************************************************************
	 */

	@Override
	public int getChildrenCount(int groupPosition) {

		try {
			if (mChildList == null) {
				return mList.get(groupPosition).mChildList.size();
			}
			return mChildList.get(groupPosition).size();
		} catch (Exception e) {
			return 0;
		}

	}

	@Override
	public CT getChild(int groupPosition, int childPosition) {

		try {
			if (mChildList == null) {
				return (CT) mList.get(groupPosition).mChildList.get(childPosition);
			}
			return mChildList.get(groupPosition).get(childPosition);

		} catch (Exception e) {

			return null;

		}

	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;

	}

	@Override
	public T getGroup(int groupPosition) {

		try {
			return mList.get(groupPosition);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public int getGroupCount() {

		try {
			return mList.size();
		} catch (Exception e) {
			return 0;
		}

	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
