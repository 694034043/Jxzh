package com.bocop.zyt.bocop.zyt.gui;

import com.bocop.zyt.R;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * yct过度动画
 * @author ftl
 *
 */
@ContentView(R.layout.xms_activity_yct_guide)
public class YCTGuideActivity extends BaseActivity {

	@ViewInject(R.id.gvGuide)
	private GifImageView gvGuide;
	
	private GifDrawable gifFromResource;
	private MediaPlayer mPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPlayer = MediaPlayer.create(this,R.raw.yct_guide_voice);
		mPlayer.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				if(mPlayer!=null){
					mp.start();
				}
			}
		});
		try {
			gifFromResource = new GifDrawable(getResources(), R.drawable.yct_enter_bg);
			gifFromResource.setLoopCount(1);
			//Glide.with(this).load(R.raw.xms_img_guide).into(new GlideDrawableImageViewTarget(gvGuide, 1));
			gvGuide.setBackgroundDrawable(gifFromResource);
			gifFromResource.addAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationCompleted() {
					_handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							finish();
							overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
						}
					}, 2000);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
	}

}
