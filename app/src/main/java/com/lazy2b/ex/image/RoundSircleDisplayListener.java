package com.lazy2b.ex.image;

import android.graphics.Bitmap;

import com.lazy2b.libs.TrimPhotoUtil;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

/** 圆形头像 */
public class RoundSircleDisplayListener implements BitmapDisplayer {
	
	@Override
	public void display(Bitmap bitmap, ImageAware imageAware,
			LoadedFrom loadedFrom) {
		Bitmap newBitmap=TrimPhotoUtil.bitmapIntoCercle(bitmap);
		imageAware.setImageBitmap(newBitmap);
	}
}