package com.bocop.zyt.bocop.jxplatform.trafficassistant;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.Update;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.util.MyUtils;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;

@ContentView(R.layout.activity_about)
public class AbountActivity extends BaseActivity {
	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;

	// 关于页面
	@ViewInject(R.id.llt_about)
	private LinearLayout llAbout;
	@ViewInject(R.id.tv_version)
	private TextView tvVersion;

	@ViewInject(R.id.btnUpdate)
	private Button btnUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		Log.i("tag", "onResume于");
		super.onResume();
		Log.i("tag", "initData");
		initData();
	}

	private void initData() {
		tv_titleName.setText("关于");
		Log.i("tag", "关于");
		tvVersion.setText("版本号 " + 	 MyUtils.getVersionName(this));
		if(BocSdkConfig.NEED_UPDATE){
			btnUpdate.setVisibility(View.VISIBLE);
			btnUpdate.setText("有新版本（" + BocSdkConfig.NEW_APP_VERSION + "），点击下载");
		}else{
			btnUpdate.setVisibility(View.GONE);
		}
//		boolean needUpdate = getIntent().getBooleanExtra("needUpdate", false);
//		if (needUpdate) {
//			btnUpdate.setVisibility(View.VISIBLE);
//		} else {
//			btnUpdate.setVisibility(View.GONE);
//		}
	}

//	@OnClick(R.id.lltBack)
//	public void back(View v) {
//		finish();
//	}

	@OnClick(R.id.btnUpdate)
	public void btnUpdate(View v) {
//		Intent updateIntent =new Intent(AbountActivity.this, DownloadService.class);  
//		updateIntent.putExtra("url", BocSdkConfig.APP_URL);
//		startService(updateIntent);  
//		Log.i("tag", "startService");
		Update update = new Update(this);
		update.showUpdate();;
	}
}
