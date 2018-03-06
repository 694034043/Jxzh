package com.bocop.zyt.bocop.zyyr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.xms.bean.ConstHead;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyyr.bean.AuthenInfoResponse;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.thoughtworks.xstream.io.StreamException;

/**
 * 
 * 个人中心（我）
 * 
 * @author lh
 * 
 */
@ContentView(R.layout.zyyr_activity_user_center)
public class UserCenterActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tvTitle.setText("我");
	}

	@OnClick({ R.id.rlMyLoan, R.id.rlUD })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rlMyLoan:
			callMe(MyLoanActivity.class);
			break;
		case R.id.rlUD:
			requestAuthenInfo();// 请求认证信息
			break;

		default:
			break;
		}
	}

	/**
	 * 请求认证资料
	 */
	private void requestAuthenInfo() {

		RequestBody formBody = new FormEncodingBuilder().add("userId", LoginUtil.getUserId(this)).build();
		CspUtil cspUtil = new CspUtil(this);
		cspUtil.postCspNoLogin(BocSdkConfig.ZYYR_QUERY_USERINFO, formBody, true, new CspUtil.CallBack() {

			@Override
			public void onSuccess(String responStr) {
				dealSucessMessage(responStr);// 处理成功返回信息

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(String responStr) {
				CspUtil.onFailure(UserCenterActivity.this, responStr);
			}
		});
	}

	/**
	 * 处理成功返回信息
	 * 
	 * @param responStr
	 */
	protected void dealSucessMessage(String responStr) {
		try {
			AuthenInfoResponse authenInfoResponse = XStreamUtils.getFromXML(responStr, AuthenInfoResponse.class);
			ConstHead constHead = authenInfoResponse.getConstHead();
			if (null != constHead) {
				if ("00".equals(constHead.getErrCode())) {
					Intent intent = new Intent(UserCenterActivity.this, AuthentInfoCheckActivity.class);
					intent.putExtra("AuthentInfo", authenInfoResponse.getAuthenInfoBean().getAuthenInfo());
					startActivity(intent);
				} else if ("01".equals(constHead.getErrCode())) {
					callMe(AuthentInfoActivity.class);
				}
			} else {
				ToastUtils.show(this, "系统异常", 0);
			}

		} catch (StreamException e) {
			ToastUtils.showError(this, "后台数据异常", Toast.LENGTH_SHORT);
		}

	}
}
