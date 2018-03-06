package com.bocop.zyt.bocop.zyt.gui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.HuiDuiTongActivity;
import com.bocop.zyt.bocop.jxplatform.activity.JKETActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragmentHelper;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.kht.activity.KhtActivity;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.bocop.zyt.model.NUser;
import com.bocop.zyt.bocop.zyt.view.YinJunTongCustomDialog;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainAct_Yin_jun_tong_Fragment extends BaseFragment implements OnClickListener{
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
		v = inflater.inflate(R.layout.act_main_yin_jing_tong_fragment, null);
		tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText("银军通");
		vp_top_banner = (ViewPager) v.findViewById(R.id.vp_top_banner);
		rl_vp_container = (RelativeLayout) v.findViewById(R.id.rl_vp_container);
		vp_indicator = (CirclePageIndicator) v.findViewById(R.id.vp_indicator);
		
		v.findViewById(R.id.ll_item_00_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_00_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_00_01_03).setOnClickListener(this);

		v.findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);

		v.findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);
		
		v.findViewById(R.id.iv_bottom_banner_01).setOnClickListener(this);
		v.findViewById(R.id.iv_bottom_banner_02).setOnClickListener(this);
		show_top_banner();
		showDialog();
		return v;
	}

	private void showDialog() {
		if(!getSoldierInfo()){
			dialog = new YinJunTongCustomDialog(getActivity(),R.style.dialog_custom);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setCancelable(false);
			dialog.setOnUpListener(new YinJunTongCustomDialog.OnUpListener() {
				
				@Override
				public void onUp(YinJunTongCustomDialog dialog) {
					dialog.cancel();
					updateSoldierInfo();
					ToastUtils.show(getActivity(), "您的信息已提交", Toast.LENGTH_LONG);
				}
			});
			dialog.setOnCancelListener(new YinJunTongCustomDialog.OnCancelListener() {
				
				@Override
				public void onCancel(YinJunTongCustomDialog dailog) {
					MainAct_Yin_jun_tong_Fragment.this.goBack();
					dialog.cancel();
				}
			});
			dialog.show();
		}
	}

	protected void updateSoldierInfo() {
		Editor editor;
		SharedPreferences sp = getActivity().getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();
		editor.putBoolean("hasSoldierInfo", true);
		editor.commit();
	}
	
	protected boolean getSoldierInfo() {
		SharedPreferences sp = getActivity().getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		return sp.getBoolean("hasSoldierInfo", false);
	}

	public static final int[] top_banner_pics = { R.drawable.pic_jun_top_banner_01,
			R.drawable.pic_jun_top_banner_02 };

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
	private YinJunTongCustomDialog dialog;

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
		case R.id.ll_item_00_01_01: {
			KhtActivity.startAct(getActivity(), IURL.Bank_kht_yjt_url, getResources().getString(R.string.yjt_item_00_01_01),true);
			break;
		}
		case R.id.ll_item_00_01_02: {
			XWebAct.startAct(getActivity(), BocSdkConfig.CARD,
					getResources().getString(R.string.yjt_item_00_01_02),true);
			break;
		}
		case R.id.ll_item_00_01_03: {
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                @Override
                public void suc() {
                	Bundle bundle = new Bundle();
                	bundle.putString("title", getResources().getString(R.string.yjt_item_00_01_03));
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
		case R.id.ll_item_01_01_01: {
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_JUN_REN_ZHUAN_XIANG_DAI);
					bundle.putString("title",getResources().getString(R.string.yjt_item_01_01_01));
					bundle.putBoolean("isShowParamsTitle", true);
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
		case R.id.ll_item_01_01_02: {
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
									intent.putExtra("title", getResources().getString(R.string.yjt_item_01_01_02));
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
		case R.id.ll_item_01_01_03: {
			XWebAct.startAct(getActivity(), BocSdkConfig.WEIHUI + LoginUtil.getUserId(getActivity()),
					getResources().getString(R.string.yjt_item_01_01_03),true);
			break;
		}
		case R.id.ll_item_02_01_01: {
			//军人健康行
			Bundle bundle = new Bundle();
			bundle.putString("userId", LoginUtil.getUserId(getActivity()));
			bundle.putString("token", LoginUtil.getToken(getActivity()));
			bundle.putString("platform", "yjt");
			Intent intent = new Intent(getActivity(), JKETActivity.class);
			intent.putExtra("title", getResources().getString(R.string.yjt_item_02_01_01));
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
		case R.id.ll_item_02_01_02: {
			XWebAct.startAct(getActivity(), IURL.Bank_Jing_Pin_Gou,
					getResources().getString(R.string.yjt_item_02_01_02),true);
			break;
		}
		case R.id.ll_item_02_01_03: {
			//军人易出行
			NUser.LoginInfo u = LoginHelper.get_instance(getActivity()).get_login_info();
			String url = IURL.Bank_Bian_Jie_Lv_You;
			if(LoginUtil.isLog(getActivity())){
				url = url +  "?userid=" + u.userid + "&accessToken=" + u.access_token;
			}
			XWebAct.startAct(getActivity(), url, getResources().getString(R.string.yjt_item_02_01_03),true);
			break;
		}
		case R.id.ll_item_02_01_04: {
			break;
		}
		case R.id.iv_bottom_banner_01: {
			ImageAdsAct.startAct(getActivity(), R.drawable.pic_yjt_jryhxx, "军人优惠信息");
			break;
		}
		case R.id.iv_bottom_banner_02: {
			XWebAct.startAct(getActivity(), IURL.Jun_jrxxpt, getResources().getString(R.string.yjt_item_02_01_03),"");
			break;
		}
		}
	}
}
