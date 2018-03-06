package com.bocop.zyt.bocop.zyt.gui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.utils.MediaPlayerUtils;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.tools.ConvertUtils;

public class MainAct_Yin_zhen_tong_ytzs_Fragment extends BaseFragment implements View.OnClickListener {

	View v;
	private ImageView ivMainBtn01;
	private ImageView ivMainBtn02;
	private LinearLayout llMainBtn;
	private ImageView ivMusic;
	MediaPlayer mPlayer;
	public static final String YZTYT_SHARE_TAG = "yztyt_share_tag";
	public static final String YZTYT_ISPLAY_MUSIC = "yztyt_isplay_music";
	private boolean isPlayMusic = true;
	private SharedPreferences sp;
	
	
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.act_main_yzt_ytzq_fragment, null);
        initViews();
        return v;
    }

	private void initViews() {
		TextView tv_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
		tv_title.setText(getResources().getString(R.string.yzt_ytzq_module_title));
		mPlayer = MediaPlayerUtils.create();
		playMp3();
		sp = getActivity().getSharedPreferences(YZTYT_SHARE_TAG, Context.MODE_PRIVATE);
		editPlayState();
		int[] sc = ScreenUtil.get_screen_size(getActivity());
		int w = sc[0];
		int h = sc[1] - ConvertUtils.dp2px(48) - ConvertUtils.dp2px(51);
		llMainBtn = (LinearLayout) v.findViewById(R.id.ll_main_btn);
		RelativeLayout.LayoutParams lpMainBtn = (RelativeLayout.LayoutParams) llMainBtn.getLayoutParams();
		lpMainBtn.height = (int) (95.0 / 666 * h);
		//lpMainBtn.width = (int) (250.0 / 531 * sc[0]);
		lpMainBtn.setMargins(0,0,0,(int) (35.0 / 666 * h));
		llMainBtn.setLayoutParams(lpMainBtn);
		ivMainBtn01 = (ImageView) v.findViewById(R.id.iv_main_btn01);
		LayoutParams lpMainBtn01 = ivMainBtn01.getLayoutParams();
		lpMainBtn01.width = (int) (250.0 / 531 * sc[0]);
		ivMainBtn01.setLayoutParams(lpMainBtn01);
		float curTranslationX = ivMainBtn01.getTranslationX();  
		ObjectAnimator animatorLeft = ObjectAnimator.ofFloat(ivMainBtn01, "translationX", -1000f, curTranslationX);  
		animatorLeft.setRepeatMode(ValueAnimator.REVERSE);
		animatorLeft.setDuration(2000);  
		animatorLeft.start();
		ivMainBtn01.setOnClickListener(this);
		ivMainBtn02 = (ImageView) v.findViewById(R.id.iv_main_btn02);
		LayoutParams lpMainBtn02 = ivMainBtn02.getLayoutParams();
		lpMainBtn02.width = (int) (250.0 / 531 * sc[0]);
		ivMainBtn02.setLayoutParams(lpMainBtn02);
		ObjectAnimator animatorRight = ObjectAnimator.ofFloat(ivMainBtn02, "translationX", 1000f, curTranslationX);  
		animatorRight.setDuration(2000);
		animatorRight.start();
		ivMainBtn02.setOnClickListener(this);
		ImageView circle = (ImageView) v.findViewById(R.id.iv_main_circle);
		RelativeLayout.LayoutParams lpCircle = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ConvertUtils.px2dp(w));  
		lpCircle.addRule(RelativeLayout.CENTER_IN_PARENT);
		//lpCircle.setMargins(0, (int)((((double)456/1118*h)-((double)w/2)/*-((double)w*12/750)*/)), 0, 0);
		//lpCircle.setMargins(0, (int)((((double)528)-((double)w*50/750)/1118*h)/*+((double)w*50/750)*/-((double)w/2)), 0, 0);
		circle.setLayoutParams(lpCircle);
		ivMusic = (ImageView) v.findViewById(R.id.iv_music);
		ivMusic.setOnClickListener(this);
		ImageView ivTitle = (ImageView) v.findViewById(R.id.iv_yztyt_main_title);
		
		Animation rotateAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anim);
		rotateAnim.setInterpolator(new LinearInterpolator());
		circle.setAnimation(rotateAnim);
		int translatH = (int)(1.0/8 * h);
		AnimatorSet animSet = new AnimatorSet();
		ObjectAnimator translat1 = ObjectAnimator.ofFloat(ivTitle, "translationY", 0,translatH);
		translat1.setDuration(1000);
		LayoutParams param = ivTitle.getLayoutParams();
		param.height = (int) (((double)w)/6);
		param.width = (int) (((double)w)/2);
		ObjectAnimator alpha = ObjectAnimator.ofFloat(ivTitle, "alpha", 0f, 1f);
		alpha.setDuration(1000);
		ObjectAnimator scalex = ObjectAnimator.ofFloat(ivTitle, "scaleX", 0f, 1.3f);
		scalex.setDuration(1000);
		ObjectAnimator scaley = ObjectAnimator.ofFloat(ivTitle, "scaleY", 0f, 1.3f);
		scaley.setDuration(1000);
		ObjectAnimator scalex2 = ObjectAnimator.ofFloat(ivTitle, "scaleX", 1.3f, 0.7f);
		scalex2.setDuration(1000);
		ObjectAnimator scaley2 = ObjectAnimator.ofFloat(ivTitle, "scaleY", 1.3f, 0.7f);
		scaley2.setDuration(1000);
		ObjectAnimator scalex3 = ObjectAnimator.ofFloat(ivTitle, "scaleX", 0.7f, 1.0f);
		scalex2.setDuration(1300);
		ObjectAnimator scaley3 = ObjectAnimator.ofFloat(ivTitle, "scaleY", 0.7f, 1.0f);
		scaley2.setDuration(1300);
		animSet.play(scalex2).with(scaley2).after(alpha).after(translat1).after(scalex).after(scaley).before(scalex3).before(scaley3);
		animSet.start();
	}

	private void editPlayState() {
		Editor edit = sp.edit();
		edit.putBoolean(YZTYT_ISPLAY_MUSIC, isPlayMusic);
		edit.commit();
	}
	
	private boolean getPlayState(){
		return sp.getBoolean(YZTYT_ISPLAY_MUSIC, true);
	}
	
	private void playMp3() {
		try {
			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.video_yztyt_main_bg);
			mPlayer.reset();
			mPlayer.setLooping(true);
			mPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                    file.getLength());
			mPlayer.prepareAsync();
			mPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					if(mPlayer!=null){
						mp.start();
						if(mPlayer.isPlaying()){
							ivMusic.setImageResource(R.drawable.icon_music_on);
						}else{
							ivMusic.setImageResource(R.drawable.icon_music_off);
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_main_btn01:
			YZTYinTanDedicatedServiceActivity.startAct(getActivity());
			break;
		case R.id.iv_main_btn02:
			YZTYinTanPublicServiceActivity.startAct(getActivity());
			break;
		case R.id.iv_music:
			if(mPlayer.isPlaying()){
				mPlayer.pause();
				ivMusic.setImageResource(R.drawable.icon_music_off);
				isPlayMusic = false;
				editPlayState();
			}else{
				mPlayer.start();
				ivMusic.setImageResource(R.drawable.icon_music_on);
				isPlayMusic = true;
				editPlayState();
			}
			break;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if(getPlayState()&&!mPlayer.isPlaying()){
			mPlayer.start();
		}
		if(mPlayer.isPlaying()){
			ivMusic.setImageResource(R.drawable.icon_music_on);
		}else{
			ivMusic.setImageResource(R.drawable.icon_music_off);
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(mPlayer.isPlaying()){
			mPlayer.pause();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mPlayer!= null) {
			mPlayer.stop();
			mPlayer= null;
		}
	}
}
