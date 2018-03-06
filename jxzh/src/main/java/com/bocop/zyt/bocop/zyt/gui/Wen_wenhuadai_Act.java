package com.bocop.zyt.bocop.zyt.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boc.bocop.sdk.util.StringUtil;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragmentHelper;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Wen_wenhuadai_Act extends BaseAct implements View.OnClickListener {

    private TextView tv_actionbar_title;
    private ViewPager vp_top_banner;
    private CirclePageIndicator vp_indicator;
    private RelativeLayout rl_vp_container;
    private LinearLayout llF2;
    private Timer _timer;
    private String title;
    public static final int WEN_HUA_E_GOU = 0;
    public static final int WEN_HUA_E_QUAN = 1;
    public static final int WEN_HUA_E_DAI = 2;
    public static int[] top_banner_pics = {R.drawable.pic_wen_top_banner_01, R.drawable.pic_wen_top_banner_02,
            R.drawable.pic_wen_top_banner_03, R.drawable.pic_wen_top_banner_04};

    public static void startActivity(Context context, String title, int state) {
        Intent intent = new Intent(context, Wen_wenhuadai_Act.class);
        intent.putExtra("state", state);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public void init_widget() {
        title = getIntent().getStringExtra("title");
        title = StringUtil.isNullOrEmpty(title) ? "" : title;
        tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
        tv_actionbar_title.setText(title);
        vp_top_banner = (ViewPager) findViewById(R.id.vp_top_banner);
        rl_vp_container = (RelativeLayout) findViewById(R.id.rl_vp_container);
        vp_indicator = (CirclePageIndicator) findViewById(R.id.vp_indicator);
        llF1 = (LinearLayout) findViewById(R.id.ll_f1);
        llF2 = (LinearLayout) findViewById(R.id.ll_f2);
        llF3 = (LinearLayout) findViewById(R.id.ll_f3);
        if (state == WEN_HUA_E_DAI) {
            llF1.setVisibility(View.VISIBLE);
            llF2.setVisibility(View.GONE);
            llF3.setVisibility(View.GONE);
            top_banner_pics = new int[]{R.drawable.pic_wen_top_banner_02};
        } else if (state == WEN_HUA_E_QUAN) {
            llF1.setVisibility(View.GONE);
            llF2.setVisibility(View.VISIBLE);
            llF3.setVisibility(View.GONE);
            top_banner_pics = new int[]{R.drawable.pic_wen_top_banner_03};
        } else if (state == WEN_HUA_E_GOU) {
            llF1.setVisibility(View.GONE);
            llF2.setVisibility(View.GONE);
            llF3.setVisibility(View.VISIBLE);
            top_banner_pics = new int[]{R.drawable.pic_wen_top_banner_04};
        }
        findViewById(R.id.ll_item_01_f2_01_01_01).setOnClickListener(this);
        findViewById(R.id.ll_item_01_f2_01_01_02).setOnClickListener(this);
        findViewById(R.id.ll_item_01_f2_01_01_03).setOnClickListener(this);

        findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
        findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
        findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);

        findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
        findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
        findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);

        findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
        findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
        findViewById(R.id.ll_item_03_01_03).setOnClickListener(this);
        findViewById(R.id.ll_item_03_01_04).setOnClickListener(this);
        findViewById(R.id.ll_item_03_01_05).setOnClickListener(this);
        show_top_banner();
    }

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

        loop_banner();
    }

    int banner_position = 0;
    private LinearLayout llF1;
    private int state;
    private LinearLayout llF3;

    public void loop_banner() {
        _timer.schedule(new TimerTask() {
            @Override
            public void run() {
                banner_position++;
                if (banner_position % 4 == 0) {
                    banner_position = 0;
                }
                /*vp_top_banner.setCurrentItem(banner_position, true);
				get_handler().post(new Runnable() {
					@Override
					public void run() {
						banner_position++;
						if (banner_position % 4 == 0) {
							banner_position = 0;
						} 
						vp_top_banner.setCurrentItem(banner_position, true);
					}
				});*/

            }
        }, 3000, 3000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wen_qi_dai);
        state = getIntent().getIntExtra("state", -1);
        _timer = new Timer();
        init_widget();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_item_01_f2_01_01_01:
                XWebAct.startAct(this, IURL.Bank_Yin_Wen_tong, "文企贷", true);
                break;
            case R.id.ll_item_01_f2_01_01_02:
                LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_WEN_XIAO_DAI);
                        // Intent intent = new Intent(baseActivity, LoanMainActivity.class);
                        bundle.putString("title", getResources().getString(R.string.ywt_item_01_f2_01_01_02));
                        bundle.putBoolean("isShowParamsTitle", true);
                        Intent intent = new Intent(Wen_wenhuadai_Act.this, TrainsActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case R.id.ll_item_01_f2_01_01_03:
                LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_WEN_CHUANG_DAI);
                        Intent intent = new Intent(Wen_wenhuadai_Act.this, TrainsActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                //ContentDisplayAct.startActivity(this,ContentDisplayAct.Page_chuan_ye_tong_for_wen,"文创贷");
                break;
            case R.id.ll_item_01_01_01: {
//			ImageAdsAct.startAct(this, R.drawable.ic_wen_ads_item_01_01_01, "传统技艺");
                //Ci_Mall_Act.startAct(this);
                //XWebAct.startAct(this, IURL.Bank_Wen_Zhong_Xiao_Qi_Ye_Dai, "中小企业贷");
                break;
            }
            case R.id.ll_item_01_01_02: {
//			ImageAdsAct.startAct(this, R.drawable.ic_wen_ads_item_01_01_02, "传统美术");
                //ToastUtils.show(this,"敬请期待", Toast.LENGTH_LONG);
			/*LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
            		bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_ZHI_GONG_GE_REN_DAI);
            		// Intent intent = new Intent(baseActivity, LoanMainActivity.class);
            		Intent intent = new Intent(this, TrainsActivity.class);
            		intent.putExtras(bundle);
            		startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});*/
                break;
            }
            case R.id.ll_item_01_01_03: {
//			ImageAdsAct.startAct(this, R.drawable.ic_wen_ads_item_01_01_03, "传统戏剧");
			/*NUser.LoginInfo u = LoginHelper.get_instance(this).get_login_info();
			String url = IURL.Bank_Bian_Jie_Lv_You + "?userid=" + u.userid + "&accessToken=" + u.access_token+"&type=2";
			XWebAct.startAct(this, url, "便捷旅游");*/
                //ContentDisplayAct.startActivity(this,ContentDisplayAct.Page_chuan_ye_tong_for_wen,"文化创业贷");
                break;
            }
            case R.id.ll_item_02_01_01: {
//			ImageAdsAct.startAct(this, R.drawable.ic_wen_ads_item_02_01_01, "当代江西");
                // 在线开户
			/*Intent intent = new Intent(this, KhtFirstActivity.class);
			intent.putExtra("state", KhtFirstActivity.WEN_ZAI_XIAN_KAI_HU);
			startActivity(intent);*/
                break;
            }
            case R.id.ll_item_02_01_02: {
//			ImageAdsAct.startAct(this, R.drawable.ic_wen_ads_item_02_01_02, "名人佳话");
                // 理财管家
			/*LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					final Context context = this;
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
			});*/
                break;
            }
            case R.id.ll_item_02_01_03: {
//			ImageAdsAct.startAct(this, R.drawable.ic_wen_ads_item_02_01_03, "建筑考古");
                //XWebAct.startAct(this, IURL.Bank_Cai_Fu_Guan_Jia_Tong, "财富管家");
                break;
            }
            case R.id.ll_item_03_01_01: {
                //Ci_Mall_Act.startAct(this);
			/*// 在线缴费
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(this, BMJFActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});*/
                break;
            }
            case R.id.ll_item_03_01_02: {
                ToastUtils.show(this, "敬请期待", Toast.LENGTH_LONG);
                //XWebAct.startAct(this, IURL.Bank_Yin_Wen_tong, "文企贷");
			/*// 车主优惠
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(this, RiderFristActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});*/

                break;
            }
            case R.id.ll_item_03_01_03: {
			/*NUser.LoginInfo u = LoginHelper.get_instance(this).get_login_info();
			String url = IURL.Bank_Bian_Jie_Lv_You;
			if(LoginUtil.isLog(this)){
				url = url +  "?userid=" + u.userid + "&accessToken=" + u.access_token;
			}
			XWebAct.startAct(this, url, "便捷旅游");*/
//			ImageAdsAct.startAct(this, R.drawable.ic_wen_ads_item_03_01_03, "文化金");
                //XWebAct.startAct(this, IURL.Bank_Jing_Pin_Gou, "精品购物");

                break;
            }
		/*case R.id.ll_item_03_01_04: {
			XWebAct.startAct(this, IURL.Bank_Cai_Fu_Guan_Jia_Tong,
					getResources().getString(R.string.ywt_item_01_f2_01_01_04),true);
			break;
		}*/
            case R.id.ll_item_03_01_05: {
                XWebAct.startAct(this, IURL.getBankCaiFuTong(this),
                        getResources().getString(R.string.ywt_item_01_f2_01_01_04), true);
                break;
            }
        }
    }

}
