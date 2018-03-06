package com.bocop.zyt.bocop.zyt.gui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.BMJFActivity;
import com.bocop.zyt.bocop.jxplatform.activity.JIEHUIActivity;
import com.bocop.zyt.bocop.jxplatform.activity.KhtFirstActivity;
import com.bocop.zyt.bocop.jxplatform.activity.ZqtFirstActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.activity.riders.RiderFristActivity;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.MyUtil;
import com.bocop.zyt.bocop.xms.activity.XmsMainActivity;
import com.bocop.zyt.bocop.xms.activity.YbtActivity;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.bocop.zyt.model.NUser;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.constants.Constants;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class BiFinanceActivity extends BaseAct implements OnClickListener {

	private ImageView iv_top_ads;
	private TextView tv_actionbar_title;
	private ImageView iv_bg_logo;
	private Timer _timer;
	private Toast toast;
	
	public static void startAct(Context context) {
		Intent intent = new Intent(context, BiFinanceActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void init_widget() {
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText(getResources().getString(R.string.ybt_main_item_03));
		iv_top_ads = (ImageView) findViewById(R.id.iv_top_ads);
		FImageloader.load_by_resid_fit_src(this, R.drawable.bg_pic_act_main_yin_ci_tong_fgt_tao_ci_jing_rong,
				iv_top_ads);

		iv_bg_logo = (ImageView) findViewById(R.id.iv_bg_logo);
		int[] sc = ScreenUtil.get_screen_size(this);
		int w1 = sc[0] - DisplayUtil.dip2px(this, 70);
		int h1 = w1;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		iv_bg_logo.setLayoutParams(p);

		findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_04).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_05).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_06).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_07).setOnClickListener(this);
		findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_03_01_03).setOnClickListener(this);
		findViewById(R.id.ll_item_03_01_04).setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_bi_finance);
		_timer = new Timer();
		init_widget();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_item_01_01_01:
			XWebAct.startAct(this, IURL.Bank_ZHong_Xiao_Tong, getResources().getString(R.string.ybt_finance_item_01_01_01),"");
			break;
		case R.id.ll_item_01_01_02:
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_ZDT);
					Intent intent = new Intent(BiFinanceActivity.this, TrainsActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.ll_item_01_01_03:
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_GDT);
					Intent intent = new Intent(BiFinanceActivity.this, TrainsActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.ll_item_02_01_01:
			Intent intent = new Intent(this, KhtFirstActivity.class);
			intent.putExtra("title", getResources().getString(R.string.ygt_item_03_01_01));
			intent.putExtra("isShowTitle", true);
			startActivity(intent);
			break;
		case R.id.ll_item_02_01_02:
			// 理财管家
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					final Context context = BiFinanceActivity.this;
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
									intent.putExtra("title", getResources().getString(R.string.ygt_item_03_01_02));
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
		case R.id.ll_item_02_01_03:
			XWebAct.startAct(this, IURL.getBankCaiFuTong(this), "财富通","");
			break;
		case R.id.ll_item_02_01_04:
			try {
				if (MyUtil.isAppInstalled(this, "com.boc.bocop.container")) {
					_timer.schedule(new TimerTask() {

						@Override
						public void run() {

							PackageManager packageManager = BiFinanceActivity.this.getPackageManager();
							Intent intent = new Intent();
							intent = packageManager.getLaunchIntentForPackage("com.boc.bocop.container");
							startActivity(intent);
						}
					}, 2000);
					if (toast == null) {
						toast = Toast.makeText(BiFinanceActivity.this, "正在为您跳转中银易商", 2000);
						toast.setGravity(Gravity.CENTER, 0, 0);
					}
					toast.show();
				} else {
					Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(Constants.UrlForZyys));
					startActivity(viewIntent);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.ll_item_02_01_05:
			// 售汇通
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putString("flag", "0");
					Intent intent = new Intent(BiFinanceActivity.this, JIEHUIActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.ll_item_02_01_06:
			// 购汇通
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					Intent intent = new Intent(BiFinanceActivity.this, JIEHUIActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.ll_item_02_01_07:
			Intent intentBX = new Intent(this, YbtActivity.class);
			startActivity(intentBX);
			break;
		case R.id.ll_item_02_01_08:
			Intent intentZQ = new Intent(this, ZqtFirstActivity.class);
			intentZQ.putExtra("title", getResources().getString(R.string.ygt_item_03_03_02));
			startActivity(intentZQ);
			break;
		case R.id.ll_item_03_01_01:
			// 在线缴费
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(BiFinanceActivity.this, BMJFActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});			
			break;
		case R.id.ll_item_03_01_02:
			// 易车行
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(BiFinanceActivity.this, RiderFristActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.ll_item_03_01_03:
			// 秘书通
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(BiFinanceActivity.this, XmsMainActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.ll_item_03_01_04:
			NUser.LoginInfo u = LoginHelper.get_instance(this).get_login_info();
			String url = IURL.Bank_Bian_Jie_Lv_You;
			if(LoginUtil.isLog(this)){
				url = url +  "?userid=" + u.userid + "&accessToken=" + u.access_token;
			}
			XWebAct.startAct(this, url, "旅游通","");
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		_timer.cancel();
		_timer = null;
	}

}
