package com.bocop.zyt.bocop.yfx.activity.loanprodetail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.yfx.activity.EShareActivity;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;


/**
 * 资料提交结果
 * 
 * @author rd
 * 
 */
@ContentView(R.layout.yfx_activity_info_submit_result)
public class InfoSubmitResultActivity extends BaseActivity {
	@ViewInject(R.id.tv_titleName)
	private TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tvTitle.setText(getString(R.string.submitResult));

	}

	@OnClick({ R.id.btnBackSalaryLoan, R.id.iv_imageLeft })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_imageLeft:
			finish();
			break;
		case R.id.btnBackSalaryLoan:
			getActivityManager().finishAllWithoutActivity(EShareActivity.class);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		getActivityManager().finishAllWithoutActivity(EShareActivity.class);
		super.onBackPressed();
	}
}
