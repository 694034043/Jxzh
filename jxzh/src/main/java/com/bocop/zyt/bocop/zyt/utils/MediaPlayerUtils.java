package com.bocop.zyt.bocop.zyt.utils;

import android.media.MediaPlayer;

public class MediaPlayerUtils {
	
	private static MediaPlayer mPlayer;
	
	public static MediaPlayer create(){
		if(mPlayer==null){
			mPlayer = new MediaPlayer();
		}
		return mPlayer;
	}
	
	

}
