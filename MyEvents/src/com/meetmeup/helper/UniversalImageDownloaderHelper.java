package com.meetmeup.helper;

import android.graphics.Bitmap;

import com.meetmeup.activity.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
public class UniversalImageDownloaderHelper {
	
	static DisplayImageOptions options;
	
	public static DisplayImageOptions initRoundedThumbConfig(){
	    options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_launcher)
			.showImageForEmptyUri(R.drawable.ic_launcher)
			.showImageOnFail(R.drawable.ic_launcher)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.considerExifParams(true)
			.displayer(new RoundedBitmapDisplayer(100))
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
	    return options;
	}
	
	public static DisplayImageOptions initLargeImageConfig(){
		options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.ic_launcher)
			.showImageOnFail(R.drawable.ic_launcher)
			.resetViewBeforeLoading(true)
			.cacheOnDisc(true)
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true)
			.displayer(new FadeInBitmapDisplayer(300))
			.build();
		return options;
	}
}