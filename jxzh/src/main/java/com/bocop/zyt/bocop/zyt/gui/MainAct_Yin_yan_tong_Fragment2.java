package com.bocop.zyt.bocop.zyt.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.widget.SlidCircleView;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by ltao on 2017/2/9.
 */

public class MainAct_Yin_yan_tong_Fragment2 extends BaseFragment implements View.OnClickListener {

	private View v;
	private TextView tv_actionbar_title;
	// private ImageView iv_fun_01;
	// private ImageView iv_fun_02;
	// private ImageView iv_fun_03;
	private ViewPager vp_top_banner;
	private RelativeLayout rl_vp_container;
	private CirclePageIndicator vp_indicator;
	private Timer _timer;
	private SlidCircleView iv_cicle;
	protected float iv_cicle_X;
	protected float iv_cicle_Y;

	private int ciccle_pading = 18;
	private ImageView iv_cicle_bg;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_timer = new Timer();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ILOG.log_4_7("MainAct_YintietongFragment  onDestroy ");
		_timer.cancel();
		_timer = null;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.act_main_yin_yan_tong_fragment, null);
		tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv_actionbar_title.setText("银烟通");

		vp_top_banner = (ViewPager) v.findViewById(R.id.vp_top_banner);
		rl_vp_container = (RelativeLayout) v.findViewById(R.id.rl_vp_container);
		vp_indicator = (CirclePageIndicator) v.findViewById(R.id.vp_indicator);

		iv_cicle = (SlidCircleView) v.findViewById(R.id.iv_cicle);

//

//		iv_cicle.setOnClickListener(this);
//
//		iv_cicle.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//
//				iv_cicle_X = event.getX();
//
//				iv_cicle_Y = event.getY();
//				return false;
//			}
//		});

		show_top_banner();
		iv_cicle_bg = (ImageView) v.findViewById(R.id.iv_cicle_bg);
		// tv_shimmer = (ShimmerTextView) findViewById(R.id.tv_shimmer);

		// FImageloader.load_by_resid_fit_src(this,
		// R.drawable.banner_pic_yin_yan_tong_ze_ren, iv_banner);
		int[] sc = ScreenUtil.get_screen_size(getActivity());
		int w1 = sc[0];
		int h1 = w1;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		// p.topMargin=DisplayUtil.dip2px(getActivity(), 16);
		// p.gravity=Gravity.CENTER_HORIZONTAL;
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		iv_cicle.setLayoutParams(p);
		iv_cicle_bg.setLayoutParams(p);
		// iv_cicle.setImageResource( R.drawable.cicle_pic_yin_ci_tong);
		// FImageloader.load_by_resid_fit_src_rl(this,
		// R.drawable.yin_yan_tong_red_cicle, iv_cicle_bg, p);
		int padding = DisplayUtil.dip2px(getActivity(), ciccle_pading);
		iv_cicle_bg.setPadding(padding, padding, padding, padding);
		Glide.with(this).load(R.drawable.yin_yan_tong_green_cicle).into(iv_cicle_bg);
		anim();
		
		iv_cicle.setCallback(new SlidCircleView.Callback() {
			
			@Override
			public void up() {
				// TODO Auto-generated method stub
				Yan_Tobacco_Main_Act.startAct(getActivity());
			}
			
			@Override
			public void down() {
				// TODO Auto-generated method stub
				
				
				Yan_Jing_Rong_Main_Act.startAct(getActivity());
				
			}
		});
		return v;
	}
	
	
	
	
	int[] resGif = { 
			R.drawable.bg_pic_yin_yan_tong_green_gif_01,
			R.drawable.bg_pic_yin_yan_tong_green_gif_02,
			R.drawable.bg_pic_yin_yan_tong_green_gif_03,
			R.drawable.bg_pic_yin_yan_tong_green_gif_04,
			R.drawable.bg_pic_yin_yan_tong_green_gif_05,
			R.drawable.bg_pic_yin_yan_tong_green_gif_06,
			R.drawable.bg_pic_yin_yan_tong_green_gif_07,
			R.drawable.bg_pic_yin_yan_tong_green_gif_08,
			R.drawable.bg_pic_yin_yan_tong_green_gif_09,
			R.drawable.bg_pic_yin_yan_tong_green_gif_10,
			R.drawable.bg_pic_yin_yan_tong_green_gif_11,
			

	};

	int position = 0;
	int last = 0;

	class GifRunnable implements Runnable {
		@Override
		public void run() {
			// Glide.with(MainActivity.this).load(resGif[position]).into(iv_cicle);
			iv_cicle.setImageResource(resGif[position]);

			if (position == 10) {
				last++;
				if (last == 7) {
					last = 0;
					position = 0;
				}
			} else {
				position++;
			}
		}
	}

	GifRunnable gifRunnable = new GifRunnable();

	private void anim() {

		// ObjectAnimator animator = ObjectAnimator.ofFloat(iv_cicle,
		// "translationX", 0, 720, 720, 0);
		//
		// animator.setDuration(2000);
		// animator.setRepeatCount(Integer.MAX_VALUE);
		// animator.start();

		_timer.schedule(new TimerTask() {
			@Override
			public void run() {

				BaseAct._handler.post(gifRunnable);

			}
		}, 0, 100);
	}

	public static final int[] top_banner_pics = { 
			R.drawable.banner_pic_yin_yan_tong_ze_ren,
//			R.drawable.banner_pic_yin_yan_tong_xing_huo,
//			R.drawable.banner_pic_yin_yan_tong_tobacco,
			};

	private void show_top_banner() {
		LayoutInflater inflater = getActivity().getLayoutInflater();

		int[] sc = ScreenUtil.get_screen_size(getActivity());
		List<View> vs = new ArrayList<>();
		int w = sc[0];
		int h = (int) (30.0 / 72 * sc[0]);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
		rl_vp_container.setLayoutParams(p);
		for (int res : top_banner_pics) {
			ImageView iv = (ImageView) inflater.inflate(R.layout.act_main_gan_jiang_xin_qu_fragment_top_banner_iv,
					null);

			iv.setLayoutParams(p);
			FImageloader.load_by_resid(getActivity(), res, iv);
			vs.add(iv);
		}
		vp_top_banner.setAdapter(new ViewPagerLoopAdapter(vs));
		vp_indicator.setViewPager(vp_top_banner);
		vp_indicator.setVisibility(View.INVISIBLE);
		loop_banner();

	}

	int banner_position = 0;

	public void loop_banner() {

		_timer.schedule(new TimerTask() {
			@Override
			public void run() {

				get_handler().post(new Runnable() {
					@Override
					public void run() {
						banner_position++;
						if (banner_position % 4 == 0) {
							banner_position = 0;
						}
						vp_top_banner.setCurrentItem(banner_position, true);
					}
				});

			}
		}, 3000, 3000);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.iv_cicle: {
			int[] sc = ScreenUtil.get_screen_size(getActivity());
			int r = (int) (1.0 * sc[0] / 2 - DisplayUtil.dip2px(getActivity(), ciccle_pading));
			int c_x = (int) (1.0 * iv_cicle.getWidth() / 2);
			int c_y = c_x;
			int rl_x = (int) (iv_cicle_X - c_x);
			int rl_y = (int) (-iv_cicle_Y + c_y);
			if (Math.abs(rl_x) * Math.abs(rl_x) + Math.abs(rl_y) * Math.abs(rl_y) > r * r) {
				return;
			}
			int area=0;
			if(rl_x>0&&rl_y<0){
				area=2;
			}else if(rl_x<0&&rl_y>0){
				area=1;
			}else if(rl_x>0&&rl_y>0){
				
				if(Math.abs(rl_y)/Math.abs(rl_x)>1.0){
					area=1;
				}else{
					area=2;
				}
				
			}else{
				
				if(Math.abs(rl_y)/Math.abs(rl_x)>1.0){
					area=2;
				}else{
					area=1;
				}
				
			}
			
			if(area==1){
				Yan_Tobacco_Main_Act.startAct(getActivity());
			}else if(area==2){
				Yan_Jing_Rong_Main_Act.startAct(getActivity());
			}
			break;
		}

		default:
			break;
		}
	}

}
