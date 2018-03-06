package com.bocop.zyt.bocop.zyt.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.WebForZytActivity;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragmentHelper;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainAct_Yin_wen_tong_Fragment extends BaseFragment implements View.OnClickListener {
	private View v;
	private TextView tv_actionbar_title;
	private ViewPager vp_top_banner;
	private CirclePageIndicator vp_indicator;
	private RelativeLayout rl_vp_container;
	private Timer _timer;
	protected float iv_cicle_X;
	protected float iv_cicle_Y;
	private int ciccle_pading = 36;
	ViewPagerLoopAdapter adapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_timer = new Timer();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ILOG.log_4_7(this.getClass().getName() + "  onDestroy ");
		if(_timer!=null){
			_timer.cancel();
			_timer = null;
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.act_main_yin_wen_tong_fragment, null);
		tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText("银文通");
		vp_top_banner = (ViewPager) v.findViewById(R.id.vp_top_banner);
		rl_vp_container = (RelativeLayout) v.findViewById(R.id.rl_vp_container);
		vp_indicator = (CirclePageIndicator) v.findViewById(R.id.vp_indicator);
		f1Content = (LinearLayout) v.findViewById(R.id.ll_f1_content);
		item0302f2Content = (LinearLayout) v.findViewById(R.id.ll_item_03_02_f2);
		int[] sc = ScreenUtil.get_screen_size(getActivity());
		int w1 = sc[0];
		int h1 = w1;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		p.addRule(RelativeLayout.CENTER_IN_PARENT);

		v.findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);

		v.findViewById(R.id.ll_item_01_02_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_02_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_02_03).setOnClickListener(this);

		v.findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);

		v.findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_03).setOnClickListener(this);

		v.findViewById(R.id.ll_item_03_02_f2_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_02_f2_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_02_f2_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_02_f2_04).setOnClickListener(this);


		v.findViewById(R.id.ll_item_04_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_04_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_04_01_03).setOnClickListener(this);

		v.findViewById(R.id.iv_ywt_bottom_01).setOnClickListener(this);

		show_top_banner();

		return v;
	}

	public static final int[] top_banner_pics = {
			R.drawable.pic_wen_top_banner_01,
			R.drawable.pic_wen_top_banner_02,
			R.drawable.pic_wen_top_banner_03,
			R.drawable.pic_wen_top_banner_04};

	public static final int[] top_banner_pics_mall = {
			R.drawable.pic_wen_top_banner_05
	};

	private void show_top_banner() {
		LayoutInflater inflater = getActivity().getLayoutInflater();

		int[] sc = ScreenUtil.get_screen_size(getActivity());
		vs = new ArrayList<>();
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
		vp_top_banner.setAdapter(adapter = new ViewPagerLoopAdapter(vs));
		vp_indicator.setViewPager(vp_top_banner);
		loop_banner();

	}

	public void updateBanners(int[] banners){
		vs.clear();
		LayoutInflater inflater = getActivity().getLayoutInflater();
		for (int res : banners) {
			ImageView iv = (ImageView) inflater.inflate(R.layout.act_main_gan_jiang_xin_qu_fragment_top_banner_iv,
					null);

			FImageloader.load_by_resid(getActivity(), res, iv);
			vs.add(iv);
		}
		if(banners.length<=1){
			try {
				_timer.cancel();
				_timer = null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			loop_banner();
		}
		vp_top_banner.setAdapter(adapter = new ViewPagerLoopAdapter(vs));
		vp_indicator.setViewPager(vp_top_banner);
	}

	int banner_position = 0;
	private LinearLayout f1Content;
	private LinearLayout item0302f2Content;
	private List<View> vs;

	public void loop_banner() {
		if(_timer==null)
			_timer = new Timer();
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
	public void goBack() {
		if(floor>1){
			floor--;
			f1Content.setVisibility(View.VISIBLE);
			item0302f2Content.setVisibility(View.GONE);
			updateBanners(top_banner_pics);
			tv_actionbar_title.setText("银文通");
		}else{
			super.goBack();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_item_01_01_01: {
				ImageAdsAct.startAct(getActivity(), R.drawable.ic_wen_ads_item_01_01_01, getResources().getString(R.string.ywt_item_01_01_01));
				break;
			}
			case R.id.ll_item_01_01_02: {
				ImageAdsAct.startAct(getActivity(), R.drawable.ic_wen_ads_item_01_01_02, getResources().getString(R.string.ywt_item_01_01_02));
				break;
			}
			case R.id.ll_item_01_01_03: {
				ImageAdsAct.startAct(getActivity(), R.drawable.ic_wen_ads_item_01_01_03, getResources().getString(R.string.ywt_item_01_01_03));
				break;
			}
			case R.id.ll_item_01_02_01: {
				ImageAdsAct.startAct(getActivity(), R.drawable.ic_wen_ads_item_01_02_01, getResources().getString(R.string.ywt_item_01_02_01));
				break;
			}
			case R.id.ll_item_01_02_02: {
				ImageAdsAct.startAct(getActivity(), R.drawable.ic_wen_ads_item_01_02_02, getResources().getString(R.string.ywt_item_01_02_02));
				break;
			}
			case R.id.ll_item_01_02_03: {
				ImageAdsAct.startAct(getActivity(), R.drawable.ic_wen_ads_item_01_02_03, getResources().getString(R.string.ywt_item_01_02_03));
				break;
			}
			case R.id.ll_item_02_01_01: {
				ImageAdsAct.startAct(getActivity(), R.drawable.ic_wen_ads_item_02_01_02, getResources().getString(R.string.ywt_item_02_01_01));
				break;
			}
			case R.id.ll_item_02_01_02: {
				ImageAdsAct.startAct(getActivity(), R.drawable.ic_wen_ads_item_02_01_03, getResources().getString(R.string.ywt_item_02_01_02));
				break;
			}
			case R.id.ll_item_02_01_03: {
				ImageAdsAct.startAct(getActivity(), R.drawable.ic_wen_ads_item_02_01_01, getResources().getString(R.string.ywt_item_02_01_03));
				break;
			}
			case R.id.ll_item_03_01_01: {
				//Ci_Mall_Act.startAct(getActivity());
				//BaseFragmentAct.startAct(getActivity(), new CiMallFragment());
				CiMallActivity.startAct(getActivity());
				break;
			}
			case R.id.ll_item_03_01_02: {
				f1Content.setVisibility(View.GONE);
				item0302f2Content.setVisibility(View.VISIBLE);
				updateBanners(top_banner_pics_mall);
				tv_actionbar_title.setText("文化商城");
				floor++;
				//ToastUtils.show(getActivity(),"敬请期待", Toast.LENGTH_LONG);
				break;
			}
			case R.id.ll_item_03_01_03: {
				ImageAdsAct.startAct(getActivity(), R.drawable.ic_wen_ads_item_03_01_03, getResources().getString(R.string.ywt_item_01_f2_01_01_04));
				break;
			}
			case R.id.ll_item_03_02_f2_01:
				WebForZytActivity.startAct(getActivity(), IURL.Bank_DTXZ, getResources().getString(R.string.ywt_item_03_02_f2_01), false);
				break;
			case R.id.ll_item_03_02_f2_02:
				WebForZytActivity.startAct(getActivity(), IURL.Bank_XMLHS, getResources().getString(R.string.ywt_item_03_02_f2_02), false);
				break;
			case R.id.ll_item_03_02_f2_03:
				WebForZytActivity.startAct(getActivity(), IURL.Bank_TXC, getResources().getString(R.string.ywt_item_03_02_f2_03), false);
				break;
			case R.id.ll_item_03_02_f2_04:
				WebForZytActivity.startAct(getActivity(), IURL.Bank_BDWL, getResources().getString(R.string.ywt_item_03_02_f2_04), false);
				break;
			case R.id.ll_item_04_01_01: {
				XWebAct.startAct(getActivity(), IURL.Bank_Yin_Wen_tong, getResources().getString(R.string.ywt_item_01_f2_01_01_01),true);
				break;
			}
			case R.id.ll_item_04_01_02: {
				LoginHelper.get_instance(getActivity()).login(getActivity(), new LoginHelper.LoginCallback() {
					@Override
					public void suc() {
						Bundle bundle = new Bundle();
						bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_WEN_XIAO_DAI);
						// Intent intent = new Intent(baseActivity, LoanMainActivity.class);
						bundle.putString("title", getResources().getString(R.string.ywt_item_01_f2_01_01_02));
						bundle.putBoolean("isShowParamsTitle", true);
						Intent intent = new Intent(getActivity(), TrainsActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}

					@Override
					public void fali() {

					}
				});
				break;
			}
			case R.id.ll_item_04_01_03: {
				LoginHelper.get_instance(getActivity()).login(getActivity(), new LoginHelper.LoginCallback() {
					@Override
					public void suc() {
						Bundle bundle = new Bundle();
						bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_WEN_CHUANG_DAI);
						Intent intent = new Intent(getActivity(), TrainsActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}

					@Override
					public void fali() {

					}
				});
				break;
			}
			case R.id.iv_ywt_bottom_01:
				XWebAct.startAct(getActivity(), IURL.Bank_ywt_jxwhcydt,getResources().getString(R.string.ywt_item_bottom_banner_01), true);
				break;
		}
	}
}
