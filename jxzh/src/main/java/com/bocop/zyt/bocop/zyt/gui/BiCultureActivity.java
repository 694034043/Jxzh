package com.bocop.zyt.bocop.zyt.gui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.fmodule.utils.FImageloader;


public class BiCultureActivity extends BaseAct {

	private ImageView iv_top_ads;
	private TextView tv_actionbar_title;
	
	public static void startAct(Context context) {
		Intent intent = new Intent(context, BiCultureActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	public void init_widget() {
		tv_actionbar_title=(TextView)findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText(getResources().getString(R.string.ybt_main_item_01));
		iv_top_ads=(ImageView)findViewById(R.id.iv_top_ads);
		FImageloader.load_by_resid_fit_src(this, R.drawable.bg_pic_act_main_yin_ci_tong_fgt_qian_nian_ci_du, iv_top_ads);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_bi_culture);
		init_widget();
	}
	
}
