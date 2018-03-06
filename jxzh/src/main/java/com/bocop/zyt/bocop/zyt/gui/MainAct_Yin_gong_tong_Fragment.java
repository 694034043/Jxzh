package com.bocop.zyt.bocop.zyt.gui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.BMJFActivity;
import com.bocop.zyt.bocop.jxplatform.activity.HuiDuiTongActivity;
import com.bocop.zyt.bocop.jxplatform.activity.ZqtFirstActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.activity.riders.RiderFristActivity;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragmentHelper;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.MyUtil;
import com.bocop.zyt.bocop.kht.activity.KhtActivity;
import com.bocop.zyt.bocop.xms.activity.XmsMainActivity;
import com.bocop.zyt.bocop.xms.activity.YbtActivity;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.bocop.zyt.model.NUser;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.constants.Constants;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainAct_Yin_gong_tong_Fragment extends BaseFragment implements View.OnClickListener {
	private View v;
	private TextView tv_actionbar_title;
	private ViewPager vp_top_banner;
	private CirclePageIndicator vp_indicator;
	private RelativeLayout rl_vp_container;
	private Timer _timer;
	Toast toast;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_timer = new Timer();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ILOG.log_4_7(this.getClass().getName() + "  onDestroy ");
		_timer.cancel();
		_timer = null;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.act_main_yin_gong_tong_fragment, null);
		tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText("银供通");
		vp_top_banner = (ViewPager) v.findViewById(R.id.vp_top_banner);
		rl_vp_container = (RelativeLayout) v.findViewById(R.id.rl_vp_container);
		vp_indicator = (CirclePageIndicator) v.findViewById(R.id.vp_indicator);

		v.findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_04).setOnClickListener(this);

		v.findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_04).setOnClickListener(this);

		v.findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_04).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_02_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_02_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_03_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_03_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_04_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_04_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_04_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_04_01_04).setOnClickListener(this);

		show_top_banner();

		return v;
	}

	public static final int[] top_banner_pics = { R.drawable.pic_gong_top_banner_01
			};

	private void show_top_banner() {
		LayoutInflater inflater = getActivity().getLayoutInflater();

		int[] sc = ScreenUtil.get_screen_size(getActivity());
		List<View> vs = new ArrayList<>();
		int w = sc[0];
		int h = (int) (30.0 / 72 * sc[0]);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
		rl_vp_container.setLayoutParams(p);
		for (int res : top_banner_pics) {
			ImageView iv = (ImageView) inflater.inflate(R.layout.act_main_gan_jiang_xin_qu_fragment_top_banner_iv,
					null);

			iv.setLayoutParams(p);
			FImageloader.load_by_resid(getActivity(), res, iv);
			vs.add(iv);
		}
		vp_top_banner.setAdapter(new ViewPagerLoopAdapter(vs));
		vp_indicator.setViewPager(vp_top_banner);

		loop_banner();

	}

	int banner_position = 0;

	public void loop_banner() {

		_timer.schedule(new TimerTask() {
			@Override
			public void run() {

				get_handler().post(new Runnable() {
					@Override
					public void run() {
						banner_position++;
						if (banner_position % 4 == 0) {
							banner_position = 0;
						}
						vp_top_banner.setCurrentItem(banner_position, true);
					}
				});

			}
		}, 3000, 3000);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_item_01_01_01: {
			XWebAct.startAct(getActivity(), IURL.Bank_ZHong_Xiao_e_Dai,
					getResources().getString(R.string.ygt_item_01_01_01),true);
			break;
		}
		case R.id.ll_item_01_01_02: {
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_FU_NONG_DAI);
					Intent intent = new Intent(getActivity(), TrainsActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_01_01_03: {
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_XIAO_FEI_DAI);
					Intent intent = new Intent(getActivity(), TrainsActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_01_01_04: {
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_GONG_JI_DAI);
					Intent intent = new Intent(getActivity(), TrainsActivity.class);
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
			// 在线开户
			KhtActivity.startAct(getActivity(), IURL.Bank_kht_ygt_url, getResources().getString(R.string.ygt_item_03_01_01),true);
			break;
		}
		case R.id.ll_item_03_01_02: {
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
					// params.put("client_id", "481");
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
		}
		case R.id.ll_item_03_01_03: {
			XWebAct.startAct(getActivity(), IURL.getBankCaiFuTong(getActivity()),
					getResources().getString(R.string.ygt_item_03_01_03),true);
			break;
		}
		case R.id.ll_item_03_01_04: {
			try {
				if(MyUtil.isAppInstalled(getActivity(), "com.boc.bocop.container")){
				    _timer.schedule(new TimerTask() {
				    	
						@Override
						public void run() {
							
							PackageManager packageManager = getActivity().getPackageManager();   
						    Intent intent=new Intent();
						    intent = packageManager.getLaunchIntentForPackage("com.boc.bocop.container");
						    startActivity(intent);
						}
					}, 2000);
				}else{
				    Intent viewIntent = new Intent("android.intent.action.VIEW",Uri.parse(Constants.UrlForZyys));
				    startActivity(viewIntent);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case R.id.ll_item_03_02_01: {
			/*//售汇通
            LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                @Override
                public void suc() {
                	Bundle bundle = new Bundle();
    				bundle.putString("flag", "2");
    				Intent intent = new Intent(getActivity(), JIEHUIActivity.class);
    				intent.putExtras(bundle);
    				startActivity(intent);
                }

                @Override
                public void fali() {

                }
            });*/
			break;
		}
		case R.id.ll_item_03_02_02: {
			//汇兑通
            LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                @Override
                public void suc() {
                	Bundle bundle = new Bundle();
                	bundle.putString("title", getResources().getString(R.string.ygt_item_03_02_02));
    				Intent intent = new Intent(getActivity(), HuiDuiTongActivity.class);
    				intent.putExtras(bundle);
    				startActivity(intent);
                }

                @Override
                public void fali() {

                }
            });
			break;
		}
		case R.id.ll_item_03_03_01: {
			Intent intent = new Intent(getActivity(), YbtActivity.class);
			intent.putExtra("title", getResources().getString(R.string.ygt_item_03_03_01));
			startActivity(intent);
			break;
		}
		case R.id.ll_item_03_03_02: {
			Intent intent = new Intent(getActivity(), ZqtFirstActivity.class);
			intent.putExtra("title", getResources().getString(R.string.ygt_item_03_03_02));
			startActivity(intent);
			break;
		}
		case R.id.ll_item_02_01_01: {
			XWebAct.startAct(getActivity(), IURL.Bank_gong_xiao_e_jia,
					getResources().getString(R.string.ygt_item_02_01_01),"");
			break;
		}
		case R.id.ll_item_02_01_02: {
			XWebAct.startAct(getActivity(), IURL.Bank_Jing_Pin_Gou,
					getResources().getString(R.string.ygt_item_02_01_02),true);
			break;
		}
		case R.id.ll_item_02_01_03: {
			XWebAct.startAct(getActivity(), BocSdkConfig.WEIHUI + LoginUtil.getUserId(getActivity()),
					getResources().getString(R.string.ygt_item_02_01_03),true);
			break;
		}
		case R.id.ll_item_02_01_04: {
			CustomProgressDialog.showBocFengxianDialog(getActivity(), "免责声明",
					"\t\t中国银行信用卡仅作为客户用于境外网上商户的支付结算工具，对于商品质量或商户服务无法负责；本网站引用的网址、商户及商品信息，意在为持卡人境外购物行为提供便利，并解决境外购物所遇常见问题，对境外商户网站合法性、安全性、准确性无法负责；注意网购有风险，如您在境外购物过程中发生商品质量、转运服务、货物丢失、延迟收货等购物纠纷，请您直接与购物网站或转运服务商沟通解决；境外购物需遵守国家相关法律、法规。 ",
					"com.bocop.jxplatform.activity.HTZQActivity",getResources().getString(R.string.ygt_item_02_01_04),true);
			break;
		}
		case R.id.ll_item_04_01_01: {
			// 秘书通
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(getActivity(), XmsMainActivity.class);
					intent.putExtra("title", getResources().getString(R.string.ygt_item_04_01_01));
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_04_01_02: {
			// 在线缴费
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(getActivity(), BMJFActivity.class);
					intent.putExtra("title", getResources().getString(R.string.ygt_item_04_01_02));
					intent.putExtra("isShowParamsTitle", true);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_04_01_03: {
			// 易车行
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(getActivity(), RiderFristActivity.class);
					intent.putExtra("title", getResources().getString(R.string.ygt_item_04_01_03));
					intent.putExtra("isShowParamsTitle", true);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_04_01_04: {
			NUser.LoginInfo u = LoginHelper.get_instance(getActivity()).get_login_info();
			String url = IURL.Bank_Bian_Jie_Lv_You;
			if(LoginUtil.isLog(getActivity())){
				url = url +  "?userid=" + u.userid + "&accessToken=" + u.access_token;
			}
			XWebAct.startAct(getActivity(), url, getResources().getString(R.string.ygt_item_04_01_04),true);
			break;
		}
		}
	}
}
