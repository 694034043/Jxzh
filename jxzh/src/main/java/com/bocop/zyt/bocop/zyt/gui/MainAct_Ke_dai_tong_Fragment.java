package com.bocop.zyt.bocop.zyt.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.KhtFirstActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebForZytActivity;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bocop.zyt.jx.base.BaseFragment;

@ContentView(R.layout.act_ke_dai_tong_fragment)
public class MainAct_Ke_dai_tong_Fragment extends BaseFragment implements OnClickListener{
	
	@ViewInject(R.id.tv_actionbar_title)
	private TextView tv_actionbar_title;
	@ViewInject(R.id.vp_top_banner)
	private ViewPager vp_top_banner;
	@ViewInject(R.id.vp_indicator)
	private CirclePageIndicator vp_indicator;
	@ViewInject(R.id.rl_vp_container)
	private RelativeLayout rl_vp_container;
	@ViewInject(R.id.LL_menu_main)
	private LinearLayout llMenuMain;
	@ViewInject(R.id.btn_top_01)
	private Button topBtn01;
	@ViewInject(R.id.btn_top_02)
	private Button topBtn02;
	@ViewInject(R.id.btn_top_03)
	private Button topBtn03;
	@ViewInject(R.id.btn_top_04)
	private Button topBtn04;
	@ViewInject(R.id.v_qk_01)
	private View vQk01;
	@ViewInject(R.id.v_qk_02)
	private View vQk02;
	@ViewInject(R.id.v_qk_03)
	private View vQk03;
	int banner_position = 0;
	private Timer _timer;
	public static final int[] top_banner_pics = { 
			R.drawable.ic_kdt_top_banner_04,
			R.drawable.ic_kdt_top_banner_01,
			R.drawable.ic_kdt_top_banner_02,
			R.drawable.ic_kdt_top_banner_03,
			R.drawable.ic_kdt_top_banner_06,
			R.drawable.ic_kdt_top_banner_07};
	private int[] scs;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.act_ke_dai_tong_fragment, null);
		return view;
	}
	
	@Override
	protected void initData() {
		super.initData();
		topBtn01.setOnClickListener(this);
		topBtn02.setOnClickListener(this);
		topBtn03.setOnClickListener(this);
		topBtn04.setOnClickListener(this);
		vQk01.setOnClickListener(this);
		vQk02.setOnClickListener(this);
		vQk03.setOnClickListener(this);

	}
	
	
	@Override
	protected void initView() {
		super.initView();
		_timer = new Timer();
		tv_actionbar_title.setText(getResources().getString(R.string.kdt_module_title));
		scs = ScreenUtil.get_screen_size(getActivity());
		initViewSet();
		show_top_banner();
		
	}
	
	private void initViewSet() {
		int w = scs[0];
		int h = (int) (511.0/692 * scs[0]);
		LayoutParams p = (LayoutParams) llMenuMain.getLayoutParams();
		p.height = h;
		llMenuMain.setLayoutParams(p);
	}

	private void show_top_banner() {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		List<View> vs = new ArrayList<>();
		int w = scs[0];
		int h = (int) (30.0 / 72 * scs[0]);
		LayoutParams p = new LayoutParams(w, h);
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
	

	public void loop_banner() {

		_timer.schedule(new TimerTask() {
			@Override
			public void run() {

				_handler.post(new Runnable() {
					@Override
					public void run() {
						banner_position++;
						if (banner_position % top_banner_pics.length == 0) {
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
		switch (v.getId()) {
		case R.id.btn_top_01:
			ImageAdsAct.startAct(getActivity(), R.drawable.pic_kdt_cpjs, getResources().getString(R.string.kdt_cpjs));
			break;
		case R.id.btn_top_02:
			KhtFirstActivity.startAct(getActivity(),getResources().getString(R.string.kdt_module_key),"开户通",true);
			break;
		case R.id.btn_top_03:
			WebForZytActivity.startAct(getActivity(), IURL.ke_ke_dai_tong, getResources().getString(R.string.kdt_module_title), false);
			break;
		case R.id.btn_top_04:
			ImageAdsAct.startAct(getActivity(), R.drawable.pic_kdt_cjwt, getResources().getString(R.string.kdt_cjwt));
			break;
		case R.id.v_qk_01:
			WebForZytActivity.startAct(getActivity(), IURL.ke_jxskjcxggfwpt, getResources().getString(R.string.kdt_quick_line_03), true);
			break;
		case R.id.v_qk_02:
			WebForZytActivity.startAct(getActivity(), IURL.ke_gjxq, getResources().getString(R.string.kdt_quick_line_02), true);	
			break;
		case R.id.v_qk_03:
			WebForZytActivity.startAct(getActivity(), IURL.ke_jxskxjst, getResources().getString(R.string.kdt_quick_line_01), true);
			break;
		}
	}
}
