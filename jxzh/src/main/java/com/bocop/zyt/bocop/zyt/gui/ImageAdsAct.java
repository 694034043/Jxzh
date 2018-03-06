package com.bocop.zyt.bocop.zyt.gui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.fmodule.utils.FImageloader;

public class ImageAdsAct extends BaseAct {

	private TextView tv_actionbar_title;
	private ImageView iv_main;
	private int image_res;
	private String title;
	private int layout;

	public static int Layout_Qing_hua_ci_bar=R.layout.act_image_ads_qing_hua_ci_bar;
	private int head_image_res;
	private ImageView iv_head;
	private LinearLayout rootView;
	private String bgColor;

	public static void startAct(Context context, int image_res, String title) {
		Intent intent = new Intent(context, ImageAdsAct.class);
		intent.putExtra("image_res", image_res);
		intent.putExtra("title", title);
		intent.putExtra("layout", 0);
		context.startActivity(intent);

	}

	public static void startAct(Context context, int image_res, String title, int layout_res) {
		Intent intent = new Intent(context, ImageAdsAct.class);
		intent.putExtra("image_res", image_res);
		intent.putExtra("title", title);
		intent.putExtra("layout", layout_res);
		context.startActivity(intent);
	}

	public static void startAct(Context context, int head_images_res,int image_res, String title, int layout_res) {
		Intent intent = new Intent(context, ImageAdsAct.class);
		intent.putExtra("head_images_res", head_images_res);
		intent.putExtra("image_res", image_res);
		intent.putExtra("title", title);
		intent.putExtra("layout", layout_res);
		context.startActivity(intent);
	}

	public static void startAct(Context context,int image_res, String title,String bgColor) {
		Intent intent = new Intent(context, ImageAdsAct.class);
		intent.putExtra("image_res", image_res);
		intent.putExtra("title", title);
		intent.putExtra("bgColor", bgColor);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		image_res = getIntent().getExtras().getInt("image_res");
		head_image_res = getIntent().getExtras().getInt("head_image_res");
		title = getIntent().getExtras().getString("title");
		layout = getIntent().getExtras().getInt("layout");
		bgColor = getIntent().getExtras().getString("bgColor");
		if (layout != 0) {
			setContentView(layout);
		} else {
			setContentView(R.layout.act_image_ads);
		}
		init_widget();
	}

	@Override
	public void init_widget() {
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText(title);
		iv_main = (ImageView) findViewById(R.id.iv_main);
		rootView = (LinearLayout) findViewById(R.id.act_qi_fu_tong);
		FImageloader.load_by_resid_fit_src(this, image_res, iv_main);
		iv_head = (ImageView) findViewById(R.id.iv_head);
		if(!TextUtils.isEmpty(bgColor)){
			rootView.setBackgroundColor(Color.parseColor(bgColor));
		}
		if(head_image_res!=0){
			iv_head.setBackgroundResource(head_image_res);
			iv_head.setVisibility(View.VISIBLE);
		}
	}
}
