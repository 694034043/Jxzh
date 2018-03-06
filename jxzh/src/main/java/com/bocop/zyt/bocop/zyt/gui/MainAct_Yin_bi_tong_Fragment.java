package com.bocop.zyt.bocop.zyt.gui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.gui.fragment.BiCultureFragment;
import com.bocop.zyt.bocop.zyt.gui.fragment.BiFinanceFragment;
import com.bocop.zyt.bocop.zyt.gui.fragment.BiMallFragment;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainAct_Yin_bi_tong_Fragment extends BaseFragment implements OnClickListener{

	private View v;
	private ViewPager vp_top_banner;
	private RelativeLayout rl_vp_container;
	private CirclePageIndicator vp_indicator;
	public static ArrayList<BaseFragment> fragmentList = new ArrayList<>();
	private Timer _timer;
	public static final int[] top_banner_pics = { R.drawable.pic_bi_top_banner_01,
			R.drawable.pic_bi_top_banner_02 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_timer = new Timer();
		initData();
	}
	
	private void initData() {
		fragmentList.clear();
		fragmentList.add(new BiCultureFragment());
		fragmentList.add(new BiMallFragment());
		fragmentList.add(new BiFinanceFragment());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.act_main_yin_bi_tong_fragment, null);
		TextView tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText("银笔通");
		vp_top_banner = (ViewPager) v.findViewById(R.id.vp_top_banner);
		rl_vp_container = (RelativeLayout) v.findViewById(R.id.rl_vp_container);
		vp_indicator = (CirclePageIndicator) v.findViewById(R.id.vp_indicator);
		v.findViewById(R.id.tv_item_01).setOnClickListener(this);
		v.findViewById(R.id.tv_item_02).setOnClickListener(this);
		v.findViewById(R.id.tv_item_03).setOnClickListener(this);
		show_top_banner();
		return v;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_item_01:
			CiViewPagerActivity.startAct(getActivity(),0,fragmentList);
			break;
		case R.id.tv_item_02:
			CiViewPagerActivity.startAct(getActivity(),1,fragmentList);
			break;
		case R.id.tv_item_03:
			CiViewPagerActivity.startAct(getActivity(),2,fragmentList);
			break;
		}
	}
	
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
	public void onDestroy() {
		super.onDestroy();
		ILOG.log_4_7(this.getClass().getName() + "  onDestroy ");
		_timer.cancel();
		_timer = null;
	}
}
