package com.bocop.zyt.bocop.jxplatform.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtilAnother;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.jx.base.BaseApplication;

public class HomeFragmentHelper {

	private HomeFragment fragment;
	private FragmentActivity act;

	public HomeFragmentHelper(HomeFragment fragment){
		this.fragment=fragment;
		this.act=fragment.getActivity();
	}
	
	public static final int TAG_ZHI_GONG_GONG_JI_DAI=10043;//职工公积贷
	
	//--------------------------银铁通---------------------------------
	
	public static final int Tag_zhigongxiaofeidai=10044;//职工消费贷
	
	//--------------------------银供通---------------------------------
	public static final int TAG_XIAO_FEI_DAI = 10046;//消费贷
	public static final int TAG_FU_NONG_DAI = 10047;//扶农贷
	public static final int TAG_GONG_JI_DAI=10048;//公积贷
	
	//--------------------------银军通---------------------------------
	public static final int TAG_JUN_REN_ZHUAN_XIANG_DAI = 10049;//军人尊享贷
	//--------------------------银笔通---------------------------------
	public static final int TAG_BI_BI_CHUANG_TONG = 10050;//银笔通 笔创通
	//--------------------------银文通---------------------------------
	public static final int TAG_WEN_XIAO_DAI = 10501;//文消贷
	public static final int TAG_WEN_CHUANG_DAI = 10601;//文创贷
	//--------------------------银瓷通------------------------------
	public static final int TAG_CI_XIAO_DAI_BAO = 10502;//消贷宝
	public static final int TAG_CI_CHUANG_DAI_BAO = 10602;//创贷宝
	//--------------------------银烟通------------------------------
	public static final int TAG_YAN_XIAO_FEI_YI_DAI = 10503;//消费易贷
	public static final int TAG_YAN_CHUANG_YE_YI_DAI = 10603;//创业易贷
	
	public void login_call(int tag){
		
		
		switch (tag) {
		case Tag_zhigongxiaofeidai:{
			start_zhigongxiaofeidai_act();
			return;
		}
			

		default:
			break;
		}
	}
	
	
	/**
	 * 职工消费贷
	 */
	public void start_zhigongxiaofeidai_act(){
		
		if (BaseApplication.getInstance().isNetStat()) {
			if (LoginUtil.isLog(act)) {
				Bundle bundle = new Bundle();
				bundle.putInt("PRO_FLAG", Tag_zhigongxiaofeidai);
				Intent intent = new Intent(fragment.getActivity(), TrainsActivity.class);
				intent.putExtras(bundle);
				fragment.startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(act,fragment, Tag_zhigongxiaofeidai);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(act);
		}
		
	}
	
	
	//--------------------------银铁通---------------------------------
	
}
