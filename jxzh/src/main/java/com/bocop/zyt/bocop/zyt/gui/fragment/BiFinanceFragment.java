package com.bocop.zyt.bocop.zyt.gui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.BMJFActivity;
import com.bocop.zyt.bocop.jxplatform.activity.HuiDuiTongActivity;
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
import com.bocop.zyt.bocop.zyt.gui.BaseFragment;
import com.bocop.zyt.bocop.zyt.gui.XWebAct;
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


public class BiFinanceFragment extends BaseFragment implements OnClickListener{
	private View v;
	private ImageView iv_top_ads;
	private TextView tv_actionbar_title;
	private ImageView iv_bg_logo;
	private Timer _timer;
	private Toast toast;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_timer = new Timer();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.act_bi_finance, null);
		init_widget();
		return v;
	}
	
	public void init_widget() {
		tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText(getResources().getString(R.string.ybt_main_item_03));
		iv_top_ads = (ImageView) v.findViewById(R.id.iv_top_ads);
		int[] ret = FImageloader.load_by_resid_fit_src(getActivity(),
				R.drawable.pic_bi_top_banner_01, iv_top_ads);

		RelativeLayout.LayoutParams iv_bg_bottom_p = new RelativeLayout.LayoutParams(0, 0);
		iv_bg_bottom_p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		iv_bg_logo = (ImageView) v.findViewById(R.id.iv_bg_logo);
		int[] sc = ScreenUtil.get_screen_size(getActivity());
		int w1 = sc[0] - DisplayUtil.dip2px(getActivity(), 70);
		int h1 = w1;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		p.setMargins(0, ret[1] + DisplayUtil.dip2px(getActivity(), 35), 0, 0);
		p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		p.addRule(RelativeLayout.CENTER_HORIZONTAL);
		iv_bg_logo.setLayoutParams(p);

		v.findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_04).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_05).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_06).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_07).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_08).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_04).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_item_01_01_01:
			XWebAct.startAct(getActivity(), IURL.Bank_Bi_DAI_Tong, getResources().getString(R.string.ybt_finance_item_01_01_01),true);
			break;
		case R.id.ll_item_01_01_02:
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_ZDT);
					Intent intent = new Intent(getActivity(), TrainsActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.ll_item_01_01_03:
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_GDT);
					Intent intent = new Intent(getActivity(), TrainsActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.ll_item_02_01_01:
			Intent intent = new Intent(getActivity(), KhtFirstActivity.class);
			intent.putExtra("state", KhtFirstActivity.BI_KAI_HU_TONG);
			startActivity(intent);
			break;
		case R.id.ll_item_02_01_02:
			// 理财管家
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					final Context context = getActivity();
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
		case R.id.ll_item_02_01_03:
			XWebAct.startAct(getActivity(), IURL.getBankCaiFuTong(getActivity()), "财富通","");
			break;
		case R.id.ll_item_02_01_04:
			try {
				if (MyUtil.isAppInstalled(getActivity(), "com.boc.bocop.container")) {
					_timer.schedule(new TimerTask() {

						@Override
						public void run() {

							PackageManager packageManager = getActivity().getPackageManager();
							Intent intent = new Intent();
							intent = packageManager.getLaunchIntentForPackage("com.boc.bocop.container");
							startActivity(intent);
						}
					}, 2000);
					if (toast == null) {
						toast = Toast.makeText(getActivity(), "正在为您跳转中银易商", 2000);
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
			/*// 售汇通
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putString("flag", "0");
					Intent intent = new Intent(getActivity(), JIEHUIActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});*/
			//汇兑通
            LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                @Override
                public void suc() {
                	Bundle bundle = new Bundle();
                	bundle.putString("title", getResources().getString(R.string.ybt_finance_item_02_01_05));
    				Intent intent = new Intent(getActivity(), HuiDuiTongActivity.class);
    				intent.putExtras(bundle);
    				startActivity(intent);
                }

                @Override
                public void fali() {

                }
            });
			break;
		case R.id.ll_item_02_01_06:
			/*// 购汇通
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					//bundle.putString("flag", "0");
					Intent intent = new Intent(getActivity(), JIEHUIActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});*/
			break;
		case R.id.ll_item_02_01_07:
			Intent intentBX = new Intent(getActivity(), YbtActivity.class);
			intentBX.putExtra("title", getResources().getString(R.string.ybt_finance_item_02_01_07));
			startActivity(intentBX);
			break;
		case R.id.ll_item_02_01_08:
			Intent intentZQ = new Intent(getActivity(), ZqtFirstActivity.class);
			startActivity(intentZQ);
			break;
		case R.id.ll_item_03_01_01:
			// 在线缴费
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(getActivity(), BMJFActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});			
			break;
		case R.id.ll_item_03_01_02:
			// 易车行
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(getActivity(), RiderFristActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.ll_item_03_01_03:
			// 秘书通
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(getActivity(), XmsMainActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.ll_item_03_01_04:
			NUser.LoginInfo u = LoginHelper.get_instance(getActivity()).get_login_info();
			String url = IURL.Bank_Bian_Jie_Lv_You;
			if(LoginUtil.isLog(getActivity())){
				url = url +  "?userid=" + u.userid + "&accessToken=" + u.access_token;
			}
			XWebAct.startAct(getActivity(), url, "旅游通","");
			break;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		_timer.cancel();
		_timer = null;
	}
}
