package com.lazy2b.ex.image;

import android.graphics.Bitmap;

import com.lazy2b.libs.TrimPhotoUtil;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
/**
 ** 矩形圆角
 * @author Administrator
 *
 */
public class CircleOrthogonDisplayListener implements BitmapDisplayer{

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware,
			LoadedFrom loadedFrom) {
		Bitmap newBitmap=TrimPhotoUtil.toRoundCorner(bitmap,10);
		imageAware.setImageBitmap(newBitmap);		
	}

}
