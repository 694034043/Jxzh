package com.bocop.zyt.bocop.zyt.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.widget.SlidCircleView;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Yan_Tobacco_Main_Act extends BaseAct {

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

	public static void startAct(Context context) {
		Intent intent = new Intent(context, Yan_Tobacco_Main_Act.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_yan_tobacco_main);
		_timer = new Timer();
		init_widget();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		_timer.cancel();
		_timer = null;
	}

	@Override
	public void init_widget() {
		// TODO Auto-generated method stub
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv_actionbar_title.setText("银烟通");

		vp_top_banner = (ViewPager) findViewById(R.id.vp_top_banner);
		rl_vp_container = (RelativeLayout) findViewById(R.id.rl_vp_container);
		vp_indicator = (CirclePageIndicator) findViewById(R.id.vp_indicator);

		iv_cicle = (SlidCircleView) findViewById(R.id.iv_cicle);

		show_top_banner();
		iv_cicle_bg = (ImageView) findViewById(R.id.iv_cicle_bg);
		// tv_shimmer = (ShimmerTextView) findViewById(R.id.tv_shimmer);

		// FImageloader.load_by_resid_fit_src(this,
		// R.drawable.banner_pic_yin_yan_tong_ze_ren, iv_banner);
		int[] sc = ScreenUtil.get_screen_size(this);
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
		int padding = DisplayUtil.dip2px(this, ciccle_pading);
		iv_cicle_bg.setPadding(padding, padding, padding, padding);

		//Glide.with(this).load(R.drawable.yin_yan_tong_red_cicle).into(iv_cicle_bg);
		anim();
		rl_vp_container.setVisibility(View.INVISIBLE);
	}

	/*int[] resGif = { R.drawable.bg_pic_yin_yan_tong_gif_01, R.drawable.bg_pic_yin_yan_tong_gif_02,
			R.drawable.bg_pic_yin_yan_tong_gif_03, R.drawable.bg_pic_yin_yan_tong_gif_04,
			R.drawable.bg_pic_yin_yan_tong_gif_05, R.drawable.bg_pic_yin_yan_tong_gif_06,
			R.drawable.bg_pic_yin_yan_tong_gif_07, R.drawable.bg_pic_yin_yan_tong_gif_08,
			R.drawable.bg_pic_yin_yan_tong_gif_09, R.drawable.bg_pic_yin_yan_tong_gif_10,
			R.drawable.bg_pic_yin_yan_tong_gif_11,

	};*/

	int position = 0;
	int last = 0;

	class GifRunnable implements Runnable {
		@Override
		public void run() {
			// Glide.with(MainActivity.this).load(resGif[position]).into(iv_cicle);
			/*iv_cicle.setImageResource(resGif[position]);

			if (position == 10) {
				last++;
				if (last == 7) {
					last = 0;
					position = 0;
				}
			} else {
				position++;
			}*/
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

				_handler.post(gifRunnable);

			}
		}, 0, 100);
	}

	public static final int[] top_banner_pics = { 
//			R.drawable.banner_pic_yin_yan_tong_ze_ren,
			R.drawable.banner_pic_yin_yan_tong_xing_huo,
//			R.drawable.banner_pic_yin_yan_tong_tobacco,
			};

	private void show_top_banner() {
		LayoutInflater inflater = getLayoutInflater();

		int[] sc = ScreenUtil.get_screen_size(this);
		List<View> vs = new ArrayList<>();
		int w = sc[0];
		int h = (int) (30.0 / 72 * sc[0]);
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
		vp_indicator.setVisibility(View.INVISIBLE);
		loop_banner();

	}

	int banner_position = 0;

	public void loop_banner() {

		_timer.schedule(new TimerTask() {
			@Override
			public void run() {

				_handler.post(new Runnable() {
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

}
