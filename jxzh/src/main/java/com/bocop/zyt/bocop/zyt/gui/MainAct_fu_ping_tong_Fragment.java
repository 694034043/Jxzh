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
import android.view.Gravity;
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
import com.bocop.zyt.bocop.jxplatform.activity.JKETActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebViewForGjsActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.MyUtil;
import com.bocop.zyt.bocop.kht.activity.KhtActivity;
import com.bocop.zyt.bocop.xms.activity.XmsMainActivity;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
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


public class MainAct_fu_ping_tong_Fragment extends BaseFragment implements View.OnClickListener {
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
        v = inflater.inflate(R.layout.act_main_fu_ping_tong_fragment, null);
        tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
        tv_actionbar_title.setText("扶贫通");
        vp_top_banner = (ViewPager) v.findViewById(R.id.vp_top_banner);
        rl_vp_container = (RelativeLayout) v.findViewById(R.id.rl_vp_container);
        vp_indicator = (CirclePageIndicator) v.findViewById(R.id.vp_indicator);

        v.findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_01_04).setOnClickListener(this);
        v.findViewById(R.id.ll_item_01_02_01).setOnClickListener(this);

        v.findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);
        v.findViewById(R.id.ll_item_02_01_04).setOnClickListener(this);

        v.findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
        v.findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
        v.findViewById(R.id.ll_item_03_01_03).setOnClickListener(this);
        v.findViewById(R.id.ll_item_03_01_04).setOnClickListener(this);

        v.findViewById(R.id.iv_bbs).setOnClickListener(this);

        show_top_banner();

        return v;
    }

    public static final int[] top_banner_pics = {};
    //R.drawable.pic_fu_ping_banner_01,R.drawable.pic_fu_ping_banner_02

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
                LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_grfp);
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
                XWebAct.startAct(getActivity(), Constants.fptUrlForqyfpv2,
                        getResources().getString(R.string.fpt_item_01_01_02),"");
                break;
            }
            case R.id.ll_item_01_01_03: {
                LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_lyfp);
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
                Bundle bundle = new Bundle();
                bundle.putString("url", BocSdkConfig.fpsc);
                bundle.putString("name", "扶贫商城");
                Intent intent = new Intent(getActivity(), WebViewForGjsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            }
            case R.id.ll_item_01_02_01: {
                LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        XWebAct.startAct(getActivity(), IURL.Bank_FU_guan_ai_bbs + LoginUtil.getUserId(getActivity()),
                                getResources().getString(R.string.fpt_item_01_02_01), true);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            }
            case R.id.ll_item_02_01_01: {
                KhtActivity.startAct(getActivity(), IURL.Bank_kht_fpt_url, getResources().getString(R.string.fpt_item_02_01_01));
                break;
            }
            case R.id.ll_item_02_01_02: {
                XWebAct.startAct(getActivity(), BocSdkConfig.CARD,
                        getResources().getString(R.string.fpt_item_02_01_02), true);
                break;
            }
            case R.id.ll_item_02_01_03: {
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
                                        intent.putExtra("title", getResources().getString(R.string.fpt_item_02_01_03));
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
            case R.id.ll_item_02_01_04: {
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
                        //ToastUtils.show(getActivity(), "正在为您跳转中银易商", 2000);
                        if (toast == null) {
                            toast = Toast.makeText(getActivity(), "正在为您跳转中银易商", 2000);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                        }
                        toast.show();
                    } else {
                        Intent viewIntent = new
                                Intent("android.intent.action.VIEW", Uri.parse(Constants.UrlForZyys));
                        startActivity(viewIntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.ll_item_03_01_01: {
                // 秘书通
                LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Intent intent = new Intent(getActivity(), XmsMainActivity.class);
                        intent.putExtra("title", getResources().getString(R.string.fpt_item_03_01_01));
                        startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            }
            case R.id.ll_item_03_01_02: {
                // 在线缴费
                LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Intent intent = new Intent(getActivity(), BMJFActivity.class);
                        intent.putExtra("title", getResources().getString(R.string.fpt_item_03_01_02));
                        intent.putExtra("isShowParamsTitle", true);
                        startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            }
            case R.id.ll_item_03_01_03: {
                XWebAct.startAct(getActivity(), IURL.Bank_gong_xiao_e_jia,
                        getResources().getString(R.string.fpt_item_03_01_03), true);
                break;
            }
            case R.id.ll_item_03_01_04: {
                Bundle bundle = new Bundle();
                bundle.putString("userId", LoginUtil.getUserId(getActivity()));
                bundle.putString("token", LoginUtil.getToken(getActivity()));
                bundle.putString("platform", "fpt");
                bundle.putString("title", getResources().getString(R.string.fpt_item_03_01_04));
                Intent intent = new Intent(getActivity(), JKETActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            }
            case R.id.iv_bbs: {
                LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        XWebAct.startAct(getActivity(), IURL.Bank_FU_guan_ai_bbs + LoginUtil.getUserId(getActivity()),
                                getResources().getString(R.string.fpt_item_01_02_01), true);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            }
        }
    }
}
