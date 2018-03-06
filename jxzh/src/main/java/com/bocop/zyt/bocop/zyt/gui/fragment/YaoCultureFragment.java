package com.bocop.zyt.bocop.zyt.gui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyt.gui.BaseFragment;
import com.bocop.zyt.bocop.zyt.gui.ImageAdsAct;
import com.bocop.zyt.fmodule.utils.FImageloader;


public class YaoCultureFragment extends BaseFragment implements OnClickListener{
	private View v;
	private ImageView iv_top_ads;
	private TextView tv_actionbar_title;
	private ImageView iv_bg_logo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.act_yao_culture, null);
		init_widget();
		return v;
	}
	
	public void init_widget() {
		tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText(getResources().getString(R.string.yyt_main_item_01));
		iv_top_ads = (ImageView) v.findViewById(R.id.iv_top_ads);
		int[] ret = FImageloader.load_by_resid_fit_src(getActivity(),
				R.drawable.pic_yao_top_banner_04, iv_top_ads);

		RelativeLayout.LayoutParams iv_bg_bottom_p = new RelativeLayout.LayoutParams(0, 0);
		iv_bg_bottom_p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		iv_bg_logo = (ImageView) v.findViewById(R.id.iv_bg_logo);

		v.findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_item_01_01_01:
//			ImageAdsAct.startAct(getActivity(), R.drawable.yyt_culture_bg_item_01_01_01, getResources().getString(R.string.yyt_culture_item_01));
			break;
		case R.id.ll_item_01_01_02:
//			ImageAdsAct.startAct(getActivity(), R.drawable.yyt_culture_bg_item_01_01_02_a, getResources().getString(R.string.yyt_culture_item_01));
			break;
		case R.id.ll_item_02_01_01:
//			ImageAdsAct.startAct(getActivity(), R.drawable.yyt_culture_bg_item_02_01_01, getResources().getString(R.string.yyt_culture_item_01));
			break;
		case R.id.ll_item_02_01_02:
			ToastUtils.show(getActivity(), "敬请期待", Toast.LENGTH_SHORT);
			break;
		case R.id.ll_item_03_01_01:
//			ImageAdsAct.startAct(getActivity(), R.drawable.yyt_culture_bg_item_03_01_01, getResources().getString(R.string.yyt_culture_item_01));
			break;
		case R.id.ll_item_03_01_02:
//			ImageAdsAct.startAct(getActivity(), R.drawable.yyt_culture_bg_item_03_01_02, getResources().getString(R.string.yyt_culture_item_01));
			
			break;
		}
	}
}
