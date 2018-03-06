package com.bocop.zyt.bocop.zyyr.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.xms.bean.ConstHead;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.yfx.utils.DataFormatUtil;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyyr.bean.Period;
import com.bocop.zyt.bocop.zyyr.bean.ProductDetails;
import com.bocop.zyt.bocop.zyyr.bean.ProductDetailsResponse;
import com.bocop.zyt.bocop.zyyr.bean.Status;
import com.bocop.zyt.bocop.zyyr.bean.StatusResponse;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.tools.DialogUtil;
import com.bocop.zyt.jx.view.LoadingView;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.thoughtworks.xstream.io.StreamException;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 产品详情
 * 
 * @author lh
 * 
 */
@ContentView(R.layout.zyyr_activity_pro_details)
public class ProductDetailsActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tvTitle;
	@ViewInject(R.id.tvProName)
	private TextView tvProName;
	@ViewInject(R.id.tvRate)
	private TextView tvRate;
	@ViewInject(R.id.tvProIntro)
	private TextView tvProIntro;
	/** 收益率 */
	@ViewInject(R.id.tvProceed)
	private TextView tvProceed;
	@ViewInject(R.id.llProceed)
	private LinearLayout llProceed;
	@ViewInject(R.id.tvProceedLine)
	private TextView tvProceedLine;
	@ViewInject(R.id.tvLeftTextTitle)
	private TextView tvLeftTextTitle;
	@ViewInject(R.id.tvCenterTextTitle)
	private TextView tvCenterTextTitle;
	@ViewInject(R.id.tvRightTextTitle)
	private TextView tvRightTextTitle;
	@ViewInject(R.id.tvLeftTextSubTitle)
	private TextView tvLeftTextSubTitle;
	@ViewInject(R.id.tvRightTextSubTitle)
	private TextView tvRightTextSubTitle;
	@ViewInject(R.id.loadingView)
	private LoadingView loadingView;
	@ViewInject(R.id.scrollView)
	private ScrollView scrollView;
	@ViewInject(R.id.btnApply)
	private Button btnApply;

	private String proId;
	private ProductDetails details;
	private List<String> periodList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tvTitle.setText("产品详情");
		initDate();
		requestProDetails();
	}

	private void initDate() {
		if (null != getIntent().getStringExtra("PRO_ID")) {
			proId = getIntent().getStringExtra("PRO_ID");
		}

	}

	private void initView() {
		tvLeftTextTitle.setText("最快" + details.getProLoanTime().replaceAll(" ", "") + "天");
		tvCenterTextTitle.setText(getFormatedStr(details.getInterest().replaceAll(" ", "")) + "元");
		tvRightTextTitle.setText(getFormatedStr(details.getPayPM().replaceAll(" ", "")) + "元");
		tvLeftTextSubTitle.setText(periodList.get(0) + "期");
		tvRightTextSubTitle.setText(getFormatedStr(Double.parseDouble(details.getMinAmt()) / 10000 + "") + "万");
		tvProName.setText(details.getProName().replaceAll(" ", ""));
		tvRate.setText(Double.parseDouble(details.getRate().replaceAll(" ", "")) * 100 + "%");
		tvProIntro.setText(details.getDetails().replaceAll(" ", ""));

		if (!BocSdkConfig.isTest) {
			if (!LoginUtil.isLog(this)) {
				btnApply.setBackgroundDrawable(getResources().getDrawable(R.drawable.yfx_shape_circle_stroke_greymid));
				btnApply.setClickable(false);
			}
		}
	}

	private String getFormatedStr(String str) {
		return DataFormatUtil.moneyStringFormat(str);
	}

	@OnClick({ R.id.btnApply })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnApply:
			requestAuthenStatus();
			break;

		}
	}

	/**
	 * 请求产品详情
	 */
	private void requestProDetails() {
		RequestBody formBody = new FormEncodingBuilder().add("pdtId", proId).build();
		CspUtil cspUtil = new CspUtil(this);
		cspUtil.postCspNoLogin(BocSdkConfig.ZYYR_PDT_DETAILS, formBody, true, new CspUtil.CallBack() {
			@Override
			public void onSuccess(String responStr) {
				onGetDataSuccess(responStr);
			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onFailure(String responStr) {
				loadingView.setVisibility(View.VISIBLE);
				btnApply.setVisibility(View.GONE);
				scrollView.setVisibility(View.GONE);

				loadingView.setmOnRetryListener(new LoadingView.OnRetryListener() {

					@Override
					public void retry() {
						requestProDetails();
					}
				});
			}
		});
	}

	/**
	 * 是否认证了资料
	 */
	private void requestAuthenStatus() {
		try {
			RequestBody formBody = new FormEncodingBuilder().add("userId", LoginUtil.getUserId(this)).build();
			CspUtil cspUtil = new CspUtil(this);

			cspUtil.postCspNoLogin(BocSdkConfig.ZYYR_CHCEK_USERINFO, formBody, true, new CspUtil.CallBack() {

				@Override
				public void onSuccess(String responStr) {
					try {
						StatusResponse statusResponse = XStreamUtils.getFromXML(responStr, StatusResponse.class);
						ConstHead constHead = statusResponse.getConstHead();
						if (null != constHead && "00".equals(constHead.getErrCode())) {
							Status status = statusResponse.getStatusExtern().getStatus();
							if ("0".equals(status.getStatus())) {
								DialogUtil.showWithTwoBtn(ProductDetailsActivity.this, "资料认证未通过，请先认证资料", "确定", "取消",
										new OnClickListener() {

											@Override
											public void onClick(DialogInterface dialog, int which) {
												callMe(AuthentInfoActivity.class);
											}
										}, new OnClickListener() {

											@Override
											public void onClick(DialogInterface dialog, int which) {
												dialog.dismiss();
											}
										});
							} else {
								String phone = status.getPhone();
								Intent intent = new Intent(ProductDetailsActivity.this, ApplyLoanActivity.class);
								Bundle bundle = new Bundle();
								bundle.putSerializable("DETAILS", details);
								bundle.putStringArrayList("PERIOD", (ArrayList<String>) periodList);
								bundle.putString("PHONE", phone);
								intent.putExtras(bundle);
								startActivity(intent);
							}
						}
					} catch (StreamException e) {
						ToastUtils.showError(ProductDetailsActivity.this, "后台数据异常", Toast.LENGTH_SHORT);
					}
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFailure(String responStr) {
					CspUtil.onFailure(ProductDetailsActivity.this, responStr);
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 请求产品详情成功后
	 * 
	 * @param responStr
	 */
	private void onGetDataSuccess(String responStr) {
		loadingView.setVisibility(View.GONE);
		btnApply.setVisibility(View.VISIBLE);
		scrollView.setVisibility(View.VISIBLE);
		try {
			ProductDetailsResponse detailsResponse = XStreamUtils.getFromXML(responStr, ProductDetailsResponse.class);
			ConstHead constHead = detailsResponse.getConstHead();
			if (null != constHead && "00".equals(constHead.getErrCode())) {
				details = detailsResponse.getDetailsExtern().getDetails();
				List<Period> pList = new ArrayList<>();
				pList.addAll(detailsResponse.getDetailsExtern().getPeriodList());
				periodList.clear();
				for (int i = 0; i < pList.size(); i++) {
					periodList.add(pList.get(i).getPeriod());
				}
				initView();
			} else {
				loadingView.setVisibility(View.VISIBLE);
				btnApply.setVisibility(View.GONE);
				scrollView.setVisibility(View.GONE);

				loadingView.setmOnRetryListener(new LoadingView.OnRetryListener() {

					@Override
					public void retry() {
						requestProDetails();
					}
				});
			}
		} catch (StreamException e) {
			loadingView.setVisibility(View.VISIBLE);
			btnApply.setVisibility(View.GONE);
			scrollView.setVisibility(View.GONE);

			loadingView.setmOnRetryListener(new LoadingView.OnRetryListener() {

				@Override
				public void retry() {
					requestProDetails();
				}
			});
			ToastUtils.showError(this, "后台数据异常", Toast.LENGTH_SHORT);
		}
	}
}
