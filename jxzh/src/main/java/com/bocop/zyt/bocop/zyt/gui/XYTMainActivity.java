package com.bocop.zyt.bocop.zyt.gui;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.WebForZytActivity;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

@ContentView(R.layout.act_xyt_main)
public class XYTMainActivity extends BaseActivity {
	
	@ViewInject(R.id.tv_actionbar_title)
    private TextView tv_actionbar_title;
	public static final String XYT_TITLE = "xyt_title";
	private String title;
	
	public static void startAct(Context context,String title){
		Intent intent = new Intent(context,XYTMainActivity.class);
		intent.putExtra(XYT_TITLE, title);
		context.startActivity(intent);
	}
	
	public void onClickEvent(View v){
		switch (v.getId()) {
		case R.id.v_btn_01:
			WebForZytActivity.startAct(this, IURL.XYT_SELECT_CITY, title, true);
			break;
		case R.id.v_btn_02:
			ImageAdsAct.startAct(this, R.drawable.xyt_tztyt_prompt,title);
			//WebForZytActivity.startAct(this, IURL.XYT_PROMPT, title, true);
			break;
		}
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		title = getIntent().getStringExtra(XYT_TITLE);
		title = TextUtils.isEmpty(title)?"校园通":title;
		tv_actionbar_title.setText(title);
	}
	
	public void fun_back_press(){
		finish();
	}
}
