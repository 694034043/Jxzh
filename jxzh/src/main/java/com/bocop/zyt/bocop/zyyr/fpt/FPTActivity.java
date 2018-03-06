package com.bocop.zyt.bocop.zyyr.fpt;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.yfx.activity.LoanMainActivity;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.Date;


@ContentView(R.layout.fpt_activity)
public class FPTActivity extends BaseActivity {
	
	@ViewInject(R.id.tv_titleName)
	TextView tvTitle;
	@ViewInject(R.id.tvSubTitle)
	TextView tvSubTitle;
	
	private int FLAG = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initData();
		initView();
	}
	
	private void initData(){
		FLAG = getIntent().getIntExtra("FLAG", 1);
	}
	
	private void initView(){
		switch (FLAG) {
		case 1:
			tvTitle.setText("扶贫通");
			tvSubTitle.setText("承担社会责任，着力精准扶贫");
			break;
		case 3:
			tvTitle.setText("扶农通");
			tvSubTitle.setText("承担社会责任，助力强农富农");
			break;
		case 2:
			tvTitle.setText("扶微通");
			tvSubTitle.setText("承担社会责任，助推惠农金融");
			break;

		}
	}
	
	@OnClick({R.id.btnGoApply})
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.btnGoApply:
			if (!BocSdkConfig.isTest) {
				if (null != CacheBean.getInstance().get(CacheBean.CUST_ID)
						&& !TextUtils.isEmpty(CacheBean.getInstance().get(CacheBean.CUST_ID).toString())) {
					if (checkTime()) {
						callMe(LoanMainActivity.class);
					} else {
						Toast.makeText(this, "温馨提示：每日 07:00  --  21:00 提供服务。", Toast.LENGTH_SHORT).show();
					}
				} else {
					LoginUtil.requestBocopForCustid(this, true, new LoginUtil.OnRequestCustCallBack() {
						
						@Override
						public void onSuccess() {
							callMe(LoanMainActivity.class);
						}
					});
				}
			} else {
				CacheBean.getInstance().put(CacheBean.CUST_ID, "");
				callMe(LoanMainActivity.class);
			}
			break;

		}
	}
	
	/**
	 * 检验时间是否在规定区间内
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private boolean checkTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");

		int nowTime = Integer.parseInt(sdf.format(new Date()));
		if (nowTime >= 70000 && nowTime <= 210000) {
			return true;
		}

		return false;
	}

}
