package com.bocop.zyt.bocop.yfx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.yfx.activity.stageprodetail.BindCardActivity;
import com.bocop.zyt.jx.base.BaseFragment;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;


/**
 * 
 * 产品详情
 * 
 * @author lh
 * 
 */
public class StageProDetailFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = initView(R.layout.yfx_fragment_stage_pro_detail);
		return view;
	}

	@OnClick({ R.id.btnApply })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnApply:
			baseActivity.callMe(BindCardActivity.class);
			break;

		default:
			break;
		}
	}
}
