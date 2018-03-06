package com.bocop.zyt.bocop.zyt.gui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.tools.DisplayUtil;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.WebForZytActivity;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.broadcast.PlayerMusicBroadCastReciver;
import com.bocop.zyt.bocop.zyt.utils.MediaPlayerUtils;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * 银政通(鹰潭专属) 专属服务 activity
 * 
 * @author chenyc 20170914
 */
@ContentView(R.layout.activity_yzt_yintan_dedicated_service)
public class YZTYinTanDedicatedServiceActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.tv_actionbar_title)
	private TextView tv_actionbar_title;
	@ViewInject(R.id.vp_top_banner)
	private ViewPager vp_top_banner;
	@ViewInject(R.id.rl_vp_container)
	private RelativeLayout rl_vp_container;
	@ViewInject(R.id.vp_indicator)
	private CirclePageIndicator vp_indicator;
	@ViewInject(R.id.iv_cicle)
	private ImageView iv_cicle;
	protected float iv_cicle_X;
	protected float iv_cicle_Y;
	private int ciccle_pading = 6;
	int banner_position = 0;
	private Timer _timer;
	private ImageView iv_music;
	private MyPlayerMusicBroadCastReciver reciver;
	private MediaPlayer mPlayer;

	public static Handler handler = new Handler();
	public static final int[] top_banner_pics = { 
			R.drawable.yzt_yingtan_banner_01,
			R.drawable.yzt_yingtan_banner_02,
			R.drawable.yzt_yingtan_banner_03,
			R.drawable.yzt_yingtan_banner_04};
	
	public static void startAct(Context context){
		Intent intent = new Intent(context, YZTYinTanDedicatedServiceActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}
	
	public void fun_back_press(){
		finish();
	}

	private void initViews() {
		_timer = new Timer();
		IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);
        registerReceiver(reciver = new MyPlayerMusicBroadCastReciver(), myIntentFilter);
        mPlayer = MediaPlayerUtils.create();
		int[] sc = ScreenUtil.get_screen_size(this);
		int w1 = sc[0];
		int h1 = w1;
		iv_music = (ImageView) findViewById(R.id.iv_music);
		iv_music.setVisibility(View.GONE);
		iv_music.setOnClickListener(this);
		if(!mPlayer.isPlaying()){
			iv_music.setImageResource(R.drawable.icon_music_off);
		}
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		iv_cicle.setLayoutParams(p);
		int padding = DisplayUtil.dip2px(this, ciccle_pading);
		iv_cicle.setPadding(padding, padding, padding, padding);
		//FImageloader.load_by_resid(this, R.drawable.cicle_pic_yzt_yingtan, iv_cicle);
		//iv_cicle.setBac
		iv_cicle.setOnClickListener(this);
		iv_cicle.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				iv_cicle_X = event.getX();
				iv_cicle_Y = event.getY();
				return false;
			}
		});
        tv_actionbar_title.setText(getResources().getString(R.string.yzt_ytzq_dedicated_service_title));
		show_top_banner();
	}
	
	public class MyPlayerMusicBroadCastReciver extends PlayerMusicBroadCastReciver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			super.onReceive(context, intent);
			int state = intent.getIntExtra("playState", -1);
			if(state == 1){
				iv_music.setImageResource(R.drawable.icon_music_on);
			}else if(state == 2){
				iv_music.setImageResource(R.drawable.icon_music_off);
			}
		}

	}

	private void show_top_banner() {
		LayoutInflater inflater = getLayoutInflater();
		int[] sc = ScreenUtil.get_screen_size(this);
		List<View> vs = new ArrayList<>();
		int w = sc[0];
		//int h = (int) (4 / 10 * sc[0]);
		int h = DisplayUtil.dip2px(this, 180);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
		rl_vp_container.setLayoutParams(p);
		for (int res : top_banner_pics) {
			ImageView iv = (ImageView) inflater.inflate(R.layout.act_main_gan_jiang_xin_qu_fragment_top_banner_iv,
					null);
			iv.setLayoutParams(p);
			FImageloader.load_by_resid(this, res, iv);
			vs.add(iv);
		}
		vp_top_banner.setAdapter(new ViewPagerLoopAdapter(vs));
		vp_indicator.setViewPager(vp_top_banner);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_cicle: {
			int[] sc = ScreenUtil.get_screen_size(this);
			int r = (int) (1.0 * sc[0] / 2 - DisplayUtil.dip2px(this, ciccle_pading) - 130);
			ILOG.log_4_7("x " + iv_cicle.getX() + "  y " + iv_cicle.getY() + " w " + iv_cicle.getWidth());
			ILOG.log_4_7("中心坐标 " + iv_cicle.getWidth() / 2 + "  " + (iv_cicle.getWidth() / 2 + iv_cicle.getY()));
			int c_x = (int) (1.0 * iv_cicle.getWidth() / 2);
			int c_y = c_x;
			ILOG.log_4_7("点击坐标 " + iv_cicle_X + "  " + iv_cicle_Y);
			int rl_x = (int) (iv_cicle_X - c_x);
			int rl_y = (int) (-iv_cicle_Y + c_y);
			ILOG.log_4_7("相对坐标 " + rl_x + "  " + rl_y);
			if (Math.abs(rl_x) * Math.abs(rl_x) + Math.abs(rl_y) * Math.abs(rl_y) > r * r) {
				ILOG.log_4_7(" 区域  外");
				return;
			}
			int area = 0;
			if (rl_y <= 0) {
				if (rl_x >= 0) {
					area = 2;
				} else {
					area = 3;
				}
			} else {
				double angle = 1.0 * Math.abs(rl_y) / Math.abs(rl_x);
				ILOG.log_4_7("angle " + angle);
				if (angle >= 0.57) {
					area = 1;
				} else {
					if (rl_x >= 0) {
						area = 2;
					} else {
						area = 3;
					}
				}
			}
			ILOG.log_4_7("点击 区域 " + area);
			switch (area) {
			case 1:
				WebForZytActivity.startAct(this, IURL.zxt_wltdpt_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_dedicated_service_item00), true);
				break;
			case 2:
				WebForZytActivity.startAct(this, IURL.yingtan_smart_city, getResources().getString(R.string.yzt_ytzq_dedicated_service_item02), true);
				break;
			case 3:
				YZTYinTanIndustrialPlatformActivity.startAct(this);
				break;
			}
			break;
		}
		case R.id.iv_music:
			if(mPlayer.isPlaying()){
				mPlayer.pause();

				sendPlayerMusicBroadCast(2);
				iv_music.setImageResource(R.drawable.icon_music_off);
			}else{
				mPlayer.start();
				sendPlayerMusicBroadCast(1);
				iv_music.setImageResource(R.drawable.icon_music_on);
			}
			break;
		}
	}
	
	public void sendPlayerMusicBroadCast(int state){
		Intent intent = new Intent();  //Itent就是我们要发送的内容
        intent.putExtra("playState", state);  
        intent.setAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
        sendBroadcast(intent);   //发送广播
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(reciver);
	}
}
