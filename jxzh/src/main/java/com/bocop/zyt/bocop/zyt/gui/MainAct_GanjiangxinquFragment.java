package com.bocop.zyt.bocop.zyt.gui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.BMJFActivity;
import com.bocop.zyt.bocop.jxplatform.activity.HuiDuiTongActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebForZytActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.kht.activity.KhtActivity;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.zyt.ICONST;
import com.bocop.zyt.bocop.zyt.ILOG;
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


/**
 * Created by ltao on 2017/2/9.
 */

public class MainAct_GanjiangxinquFragment extends BaseFragment implements View.OnClickListener {

    private View v;
    private TextView tv_actionbar_title;
    private ViewPager vp_top_banner;
    private CirclePageIndicator vp_indicator;
    private RelativeLayout rl_vp_container;
    private Timer _timer;
    private ImageView iv_bottom_ad_01;
    private ImageView iv_bottom_ad_02;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _timer = new Timer();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ILOG.log_4_7("MainAct_GanjiangxinquFragment  onDestroy ");
        _timer.cancel();
        _timer = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.act_main_gan_jiang_xin_qu_fragment, null);
        tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
        tv_actionbar_title.setText(ICONST.YQT_Module_Name);
        vp_top_banner = (ViewPager) v.findViewById(R.id.vp_top_banner);
        rl_vp_container = (RelativeLayout) v.findViewById(R.id.rl_vp_container);
        vp_indicator = (CirclePageIndicator) v.findViewById(R.id.vp_indicator);

        iv_bottom_ad_01 = (ImageView) v.findViewById(R.id.iv_bottom_ad_01);
        iv_bottom_ad_02 = (ImageView) v.findViewById(R.id.iv_bottom_ad_02);

        //调整广告图大小
//        int[] sc = ScreenUtil.get_screen_size(getActivity());
//        int w = sc[0];
//        int h = (int) (152.0 / 720 * sc[0]);
//        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
//        iv_bottom_ad_01.setLayoutParams(p);
//        iv_bottom_ad_02.setLayoutParams(p);

        v.findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_01_04).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_02_01).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_02_02).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_02_03).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_02_04).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_01_04).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_02_01).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_02_02).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_02_03).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_02_04).setOnClickListener(this);


        iv_bottom_ad_01.setOnClickListener(this);
        iv_bottom_ad_02.setOnClickListener(this);
        FImageloader.load_by_resid_fit_src(getActivity(), R.drawable.pic_gan_bottom_ad_01, iv_bottom_ad_01);
        FImageloader.load_by_resid_fit_src(getActivity(), R.drawable.pic_gan_bottom_ad_02, iv_bottom_ad_02);
        show_top_banner();

        return v;
    }

    public static final int[] top_banner_pics = {
            R.drawable.pic_banner_gan_jiang_xin_qu_01,
            R.drawable.pic_banner_gan_jiang_xin_qu_02,
            R.drawable.pic_banner_gan_jiang_xin_qu_03,
            R.drawable.pic_banner_gan_jiang_xin_qu_04,
    };

    private void show_top_banner() {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        int[] sc = ScreenUtil.get_screen_size(getActivity());
        List<View> vs = new ArrayList<>();
        int w = sc[0];
        int h = (int) (3.0 / 10 * sc[0]);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
        rl_vp_container.setLayoutParams(p);
        for (int res : top_banner_pics
                ) {
            ImageView iv = (ImageView) inflater.inflate(R.layout.act_main_gan_jiang_xin_qu_fragment_top_banner_iv, null);


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
            	KhtActivity.startAct(getActivity(), IURL.Bank_kht_yqt_url, "开户通");
                break;
            }
            case R.id.ll_item_01_01_02: {
            	WebForZytActivity.startAct(getActivity(), IURL.Jing_qi_dai_tong,"企贷通",true);
                break;
            }
            case R.id.ll_item_01_01_03: {
//            	ImageAdsAct.startAct(getActivity(), R.drawable.bg_pic_qi_fu_tong, "企付通");
                break;
            }
            case R.id.ll_item_01_01_04: {
            	WebActivity.startAct(getActivity(), IURL.Gan_dan_zheng_tong,"单证通","");
                break;
            }
            case R.id.ll_item_01_02_01: {
            	WebActivity.startAct(getActivity(), IURL.Gan_bao_han_tong,"保函通","");
                break;
            }
            case R.id.ll_item_01_02_02: {
//            	ImageAdsAct.startAct(getActivity(), R.drawable.bg_pic_bao_guan_tong, "报关通");
                break;
            }
            case R.id.ll_item_01_02_03: {
//            	ImageAdsAct.startAct(getActivity(), R.drawable.bg_pic_hang_yun_tong, "航运通");
                break;
            }
            case R.id.ll_item_01_02_04: {
//            	ImageAdsAct.startAct(getActivity(), R.drawable.bg_pic_che_shang_tong, "车商通");
                break;
            }
            case R.id.ll_item_02_01_01: {
            	KhtActivity.startAct(getActivity(), IURL.Bank_kht_yqt_url, "开户通");
                break;
            }
            case R.id.ll_item_02_01_02: {
                XWebAct.startAct(getActivity(), IURL.Bank_Ban_Ka_Tong, "办卡通","");
                break;
            }
            case R.id.ll_item_02_01_03: {
                LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                    	Bundle bundle = new Bundle();
                		bundle.putInt("PRO_FLAG", 5);
                		bundle.putInt("PRO_FlAG_STATE", TrainsActivity.FLAG_YQT_GDT);
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
            case R.id.ll_item_02_01_04: {
            	//理财通
                LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                    	final Context context=getActivity();
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
                		// params.put("client_secret", MainActivity.CONSUMER_SECRET);
                		params.put("client_id", "357"); // 易惠通
                		//params.put("client_id", BocSdkConfig.CONSUMER_KEY);
                		params.put("acton", action);
                		// https://openapi.boc.cn/bocop/oauth/token
                		restTemplate.postGetType("https://openapi.boc.cn/oauth/token", params, new JsonHttpResponseHandler("UTF-8") {
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
                			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
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
            case R.id.ll_item_02_02_01: {
            	/*//购汇通
                LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                    	Bundle bundle = new Bundle();
        				bundle.putString("flag", "1");
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
                    	bundle.putString("title", "汇兑通");
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
            case R.id.ll_item_02_02_02: {
            	/*//售汇通
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
                break;
            }
            case R.id.ll_item_02_02_03: {
                //在线缴费
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
            }
            case R.id.ll_item_02_02_04: {
                XWebAct.startAct(getActivity(), IURL.getBankCaiFuTong(getActivity().getBaseContext()), "财富通","");
                break;
            }

            //底部广告
            case R.id.iv_bottom_ad_01: {
                ImageAdsAct.startAct(getActivity(), R.drawable.bg_pic_cam_dui_gong_zi_zhu_fu_wu, "CAM对公自助服务");
                break;
            }
            case R.id.iv_bottom_ad_02: {
                ImageAdsAct.startAct(getActivity(), R.drawable.bg_pic_zhong_yin_jin_rong_fu_wu_zhong_xin, "中银金融服务中心");
                break;
            }
        }
    }
}
