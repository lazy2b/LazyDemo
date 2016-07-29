/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.app
 * 文件名:Image.java
 * 创  建:2016年1月26日上午11:59:58
 * Copyright © 2016, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import com.lazy2b.libs.interfaces.ILazyBase;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 类名: Image <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public class Image implements ILazyBase {
	// private static BitmapUtils image;

	private static ImageLoader imageLoader;

	private static Context cxt;

	public synchronized static void init(Context _cxt, ImageLoaderConfiguration configuration) {
		synchronized (String.class) {
			try {
				if (imageLoader == null) {
					cxt = _cxt;
					imageLoader = ImageLoader.getInstance();
					if (configuration != null) {
						imageLoader.init(configuration);
					} else {
						imageLoader.init(new ImageLoaderConfiguration.Builder(cxt)
								.defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(true) // 设置下载的图片是否缓存在内存中
										.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中.build())
										.build())
								.build());
					}
					LogUtils.i("初始化[Image]成功！");
				}
			} catch (Exception e) {
				LogUtils.i("初始化[Image]失败！");
				e.printStackTrace();
			}
		}
	}

	public static <T extends ImageView> void display(T container, String uri) {
		imageLoader.displayImage(uri, container);
	}

	/**
	 * 加载本地图片
	 * 
	 * @param container
	 * @param loadFailedResId
	 */
	public static <T extends ImageView> void display(T container, int resId) {
		display(container, "drawable://" + resId);
	}

	/**
	 * 加载本地图片
	 * 
	 * @param container
	 * @param loadFailedResId
	 */
	public static <T extends ImageView> void display(T container, Drawable draw) {
		display(container, "", draw);
	}

	public static <T extends ImageView> void display(T container, String uri, int loadFailedResId) {
		imageLoader.displayImage(uri, container, new DisplayImageOptions.Builder().showImageOnFail(loadFailedResId)
				.cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(loadFailedResId).build());
	}

	public static <T extends ImageView> void display(T container, String uri, int loadingResId, int loadFailedResId) {
		imageLoader.displayImage(uri, container,
				new DisplayImageOptions.Builder().showImageOnLoading(loadingResId).cacheOnDisk(true).cacheInMemory(true)
						.showImageOnFail(loadFailedResId == -1 ? loadingResId : loadFailedResId)
						.showImageOnLoading(loadingResId).build());
	}

	public static <T extends ImageView> void display(T container, String uri, Drawable loadFailedDrawable) {
		imageLoader.displayImage(uri, container, new DisplayImageOptions.Builder().showImageOnFail(loadFailedDrawable)
				.showImageOnLoading(loadFailedDrawable).build());
	}

	public static <T extends ImageView> void display(T container, String uri, DisplayImageOptions options) {
		imageLoader.displayImage(uri, container, options);
	}

	public static <T extends ImageView> void display(T container, String uri, ImageLoadingListener listener) {
		imageLoader.displayImage(uri, container, listener);
	}

	public static <T extends ImageView> void display(T container, String uri, DisplayImageOptions options,
			ImageLoadingListener listener) {
		imageLoader.displayImage(uri, container, options, listener);
	}

}