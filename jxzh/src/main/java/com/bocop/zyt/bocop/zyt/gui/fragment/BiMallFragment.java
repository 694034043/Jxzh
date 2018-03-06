package com.bocop.zyt.bocop.zyt.gui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.gui.BaseFragment;
import com.bocop.zyt.bocop.zyt.gui.XWebAct;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;


public class BiMallFragment extends BaseFragment implements OnClickListener{

	private View v;
	private ImageView iv_top_ads;
	private TextView tv_actionbar_title;
	private ImageView iv_bg_logo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.layout_bi_mall, null);
		init_widget();
		return v;
	}
	
	public void init_widget() {
		tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText(getResources().getString(R.string.ybt_main_item_02));
		iv_top_ads = (ImageView) v.findViewById(R.id.iv_top_ads);
		int[] ret = FImageloader.load_by_resid_fit_src(getActivity(),
				R.drawable.pic_bi_top_banner_01, iv_top_ads);
		iv_bg_logo = (ImageView) v.findViewById(R.id.iv_bg_logo);
		int[] sc = ScreenUtil.get_screen_size(getActivity());
		int w1 = sc[0] - DisplayUtil.dip2px(getActivity(), 70);
		int h1 = w1;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		p.setMargins(0, ret[1] + DisplayUtil.dip2px(getActivity(), 35), 0, 0);
		p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		p.addRule(RelativeLayout.CENTER_HORIZONTAL);
		iv_bg_logo.setLayoutParams(p);

		v.findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_04).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_05).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_06).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_item_01_01_01:
			XWebAct.startAct(getActivity(), IURL.Bi_jin_yuan_tang, getResources().getString(R.string.ybt_mall_item_01_01_01),true);
			break;
		case R.id.ll_item_01_01_02:
			XWebAct.startAct(getActivity(), IURL.Bi_zhou_peng_cheng_bi_zhuang, getResources().getString(R.string.ybt_mall_item_01_01_02),true);
			break;
		case R.id.ll_item_01_01_03:
			XWebAct.startAct(getActivity(), IURL.Bi_an_bang_bi_zhuang, getResources().getString(R.string.ybt_mall_item_01_01_03),true);
			break;
		case R.id.ll_item_01_01_04:
			XWebAct.startAct(getActivity(), IURL.Bi_shuang_wei, getResources().getString(R.string.ybt_mall_item_01_01_04),true);
			break;
		case R.id.ll_item_01_01_05:
			XWebAct.startAct(getActivity(), IURL.Bi_zhou_zhi_guang_ge, getResources().getString(R.string.ybt_mall_item_01_01_05),true);
			break;
		case R.id.ll_item_01_01_06:
			XWebAct.startAct(getActivity(), IURL.Bi_si_he_bi_zhuang, getResources().getString(R.string.ybt_mall_item_01_01_06),true);
			break;
		}
	}
}
