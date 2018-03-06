package com.bocop.zyt.bocop.jxplatform.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;


@ContentView(R.layout.gjt_zyed_activity)
public class GjtZyedActivity extends BaseActivity {
	
	@ViewInject(R.id.tv_titleName)
	TextView tvTitle;
	@ViewInject(R.id.tvSubTitle)
	TextView tvSubTitle;
	
	private int FLAG = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tvTitle.setText("中银江西公积贷");
	}
	
	@OnClick({R.id.btnGoApply})
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.btnGoApply:
			Intent intent = new Intent(GjtZyedActivity.this,ZYEDActivity.class);
			startActivity(intent);
			break;

		}
	}
}
