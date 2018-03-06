package com.bocop.zyt.bocop.zyt.gui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.JIEHUIActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.xms.activity.XmsMainActivity;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class Ci_jing_rong_Act extends BaseAct implements OnClickListener {

	public static void startAct(Context context) {
		Intent intent = new Intent(context, Ci_jing_rong_Act.class);
		context.startActivity(intent);
	}

	private ImageView iv_top_ads;
	private TextView tv_actionbar_title;
	private ImageView iv_bg_logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_ci_jing_rong);
		init_widget();
	}

	@Override
	public void init_widget() {
		// TODO Auto-generated method stub
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText("银瓷通");
		iv_top_ads = (ImageView) findViewById(R.id.iv_top_ads);
		FImageloader.load_by_resid_fit_src(this, R.drawable.bg_pic_act_main_yin_ci_tong_fgt_tao_ci_jing_rong, iv_top_ads);

		iv_bg_logo = (ImageView) findViewById(R.id.iv_bg_logo);
		int[] sc = ScreenUtil.get_screen_size(this);
		int w1 = sc[0]- DisplayUtil.dip2px(this, 70);
		int h1 = w1;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		iv_bg_logo.setLayoutParams(p);

		findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_04).setOnClickListener(this);

		findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_04).setOnClickListener(this);

		findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_03_01_03).setOnClickListener(this);
		findViewById(R.id.ll_item_03_01_04).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.ll_item_01_01_01: {
			WebActivity.startAct(this,"http://119.29.107.253:8080/zyhtbanking/h5/page/openAccount/index.html?platform=tct", "开户宝",R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_02: {
			WebActivity.startAct(this, "http://119.29.107.253:8080/zyhtbanking/h5/page/tczxt/index.html?platform=tct", "快贷宝",R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_03: {
			WebActivity.startAct(this, "http://119.29.107.253:8080/zyhtbanking/h5/page/letterGuarantee/index.html?platform=tct", "出口宝",R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_04: {
			WebActivity.startAct(this, "http://119.29.107.253:8080/zyhtbanking/h5/page/creditCard/index.html?platform=tct", "保函宝",R.drawable.shape_base_yct_action_bar);
			break;
		}

		case R.id.ll_item_02_01_01: {
			
//			个贷通
			
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					
					
					ContentDisplayAct.startActForGeDaiBao(_act);
					
//					Bundle bundle = new Bundle();
//					bundle.putInt("PRO_FLAG", 5);
//					// Intent intent = new Intent(baseActivity,
//					// LoanMainActivity.class);
//					Intent intent = new Intent(_act, TrainsActivity.class);
//					intent.putExtras(bundle);
//					startActivity(intent);
					
					
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_02_01_02: {
			// 创业通
			
			
			ContentDisplayAct.startActForChuangYeBao(this);
//			Bundle bundle = new Bundle();
//			bundle.putInt("PRO_FLAG", 6);
//			 Intent intent = new Intent(baseActivity, LoanMainActivity.class);
//			Intent intent = new Intent(this, TrainsActivity.class);
//			intent.putExtras(bundle);
//			startActivity(intent);
			break;
		}
		case R.id.ll_item_02_01_03: {

			// 购汇通
			LoginHelper.get_instance(this).login(_act, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putString("flag", "1");
					Intent intent = new Intent(_act, JIEHUIActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_02_01_04: {

			// 售汇通
			LoginHelper.get_instance(this).login(_act, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putString("flag", "0");
					Intent intent = new Intent(_act, JIEHUIActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}

		case R.id.ll_item_03_01_01: {
			XWebAct.startAct(_act, IURL.Bank_Ban_Ka_Tong, "线上办卡","");
			break;
		}
		case R.id.ll_item_03_01_02: {
			LoginHelper.get_instance(this).login(_act, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(_act, XmsMainActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_03_01_03: {

			XWebAct.startAct(_act, IURL.getBankCaiFuTong(this), "财富管家","");
			break;
		}
		case R.id.ll_item_03_01_04: {
			// 理财通
			LoginHelper.get_instance(this).login(_act, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					final Context context = _act;
					RestTemplateJxBank restTemplate = new RestTemplateJxBank(context);
					JsonRequestParams params = new JsonRequestParams();
					String action = LoginUtil.getToken(context);
					String userid = LoginUtil.getUserId(context);
					Log.i("action", action);
					Log.i("userid", userid);
					params.put("enctyp", "");
					params.put("password", "");
					params.put("grant_type", "implicit");
					params.put("user_id", userid);
					// params.put("client_secret",
					// MainActivity.CONSUMER_SECRET);
					params.put("client_id", "357"); // 易惠通
					//params.put("client_id", BocSdkConfig.CONSUMER_KEY);
					params.put("acton", action);
					// https://openapi.boc.cn/bocop/oauth/token
					restTemplate.postGetType("https://openapi.boc.cn/oauth/token", params,
							new JsonHttpResponseHandler("UTF-8") {
								private ProgressDialog progressDialog;

								@Override
								public void onStart() {
									super.onStart();
									progressDialog = new ProgressDialog(context);
									progressDialog.setMessage("正在努力加载中...");
									progressDialog.setCanceledOnTouchOutside(false);
									progressDialog.show();
								}

								@Override
								public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
									System.out.println("nihao======" + response.toString());
									progressDialog.dismiss();
									Intent intent = new Intent(context, MoneySelectWebView.class);
									intent.putExtra("url",
											"https:/openapi.boc.cn/ezdb/mobileHtml/html/userCenter/index.html?channel=android");
									try {
										intent.putExtra("access_token", response.getString("access_token"));
										intent.putExtra("refresh_token", response.getString("refresh_token"));
										intent.putExtra("user_id", response.getString("user_id"));
										context.startActivity(intent);
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}

								@Override
								public void onFailure(int statusCode, Header[] headers, Throwable throwable,
										JSONObject errorResponse) {
									if (progressDialog != null) {
										progressDialog.dismiss();
									}
								}
							});
				}

				@Override
				public void fali() {

				}
			});
			break;
		}

		default:
			break;
		}
	}

}
