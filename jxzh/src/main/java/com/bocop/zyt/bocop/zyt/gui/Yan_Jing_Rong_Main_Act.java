package com.bocop.zyt.bocop.zyt.gui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.BMJFActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.activity.ZqtFirstActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.activity.riders.RiderFristActivity;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragmentHelper;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.xms.activity.XmsMainActivity;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
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


public class Yan_Jing_Rong_Main_Act extends BaseAct implements OnClickListener {

	public static void startAct(Context context) {
		Intent intent = new Intent(context, Yan_Jing_Rong_Main_Act.class);
		context.startActivity(intent);
	}



	private ViewPager vp_top_banner;
	private RelativeLayout rl_vp_container;
	private CirclePageIndicator vp_indicator;
	private Timer _timer;
	private TextView tv_actionbar_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_yan_jing_rong_main);
		_timer = new Timer();
		init_widget();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		_timer.cancel();
		_timer = null;
	}

	@Override
	public void init_widget() {
		// TODO Auto-generated method stub
		findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);

		findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);

		findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_03_01_03).setOnClickListener(this);
		vp_top_banner = (ViewPager) findViewById(R.id.vp_top_banner);
		rl_vp_container = (RelativeLayout) findViewById(R.id.rl_vp_container);
		vp_indicator = (CirclePageIndicator) findViewById(R.id.vp_indicator);
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv_actionbar_title.setText("银烟通");
		show_top_banner();
	}
	
	

	public static final int[] top_banner_pics = {
//			R.drawable.banner_pic_yin_yan_tong_ze_ren,
//			R.drawable.banner_pic_yin_yan_tong_xing_huo,
			R.drawable.banner_pic_yin_yan_tong_tobacco,
			};

	private void show_top_banner() {
		LayoutInflater inflater = getLayoutInflater();

		int[] sc = ScreenUtil.get_screen_size(this);
		List<View> vs = new ArrayList<>();
		int w = sc[0];
		int h = (int) (30.0 / 72 * sc[0]);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
		rl_vp_container.setLayoutParams(p);
		for (int res : top_banner_pics) {
			ImageView iv = (ImageView) inflater.inflate(R.layout.act_main_gan_jiang_xin_qu_fragment_top_banner_iv,
					null);

			iv.setLayoutParams(p);
			FImageloader.load_by_resid(this, res, iv);
			vs.add(iv);
		}
		vp_top_banner.setAdapter(new ViewPagerLoopAdapter(vs));
		vp_indicator.setViewPager(vp_top_banner);
		vp_indicator.setVisibility(View.INVISIBLE);
		loop_banner();

	}

	int banner_position = 0;

	public void loop_banner() {

		_timer.schedule(new TimerTask() {
			@Override
			public void run() {

				_handler.post(new Runnable() {
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
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.ll_item_01_01_01: {
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {

					Bundle bundle = new Bundle();
            		bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_YAN_XIAO_FEI_YI_DAI);
            		Intent intent = new Intent(Yan_Jing_Rong_Main_Act.this, TrainsActivity.class);
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
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
            		bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_YAN_CHUANG_YE_YI_DAI);
            		Intent intent = new Intent(Yan_Jing_Rong_Main_Act.this, TrainsActivity.class);
            		intent.putExtras(bundle);
            		startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			//ContentDisplayAct.startActForChuangYeTongForYan(_act);
			break;
		}
		case R.id.ll_item_01_01_03: {
			XWebAct.startAct(this, "http://e.eqxiu.com/s/qVVUlJRj", "经营易贷","");
			break;
		}

		case R.id.ll_item_02_01_01: {
			WebActivity.startAct(this, IURL.Bank_kht_yyt_url, "开户通","");
			break;
		}
		case R.id.ll_item_02_01_02: {
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
		case R.id.ll_item_02_01_03: {
			//Zheng_Quan_Tong_Act.startAct(_act);
			ZqtFirstActivity.startAct(_act);
			break;
		}

		case R.id.ll_item_03_01_01: {

			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(_act, BMJFActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_03_01_02: {

			// 车主优惠
			LoginHelper.get_instance(this).login(_act, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(_act, RiderFristActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_03_01_03: {
			
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

		default:
			break;
		}
	}

}
