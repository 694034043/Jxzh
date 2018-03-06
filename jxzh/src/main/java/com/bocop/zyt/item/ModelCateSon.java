//
//  ModelCateSon
//
//  Created by Administrator on 2017-09-18 16:48:37
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.bocop.zyt.item;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.gm.GoldManagerActivity;
import com.bocop.zyt.bocop.jxplatform.activity.BMJFActivity;
import com.bocop.zyt.bocop.jxplatform.activity.HuiDuiTongActivity;
import com.bocop.zyt.bocop.jxplatform.activity.JKETActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.activity.ZqtFirstActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.activity.riders.RiderFristActivity;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragmentHelper;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.MyUtil;
import com.bocop.zyt.bocop.xms.activity.XmsMainActivity;
import com.bocop.zyt.bocop.xms.activity.YbtActivity;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.bocop.zyt.gui.XWebAct;
import com.bocop.zyt.bocop.zyt.gui.XWebActForTaoCIMall;
import com.bocop.zyt.bocop.zyt.model.NUser;
import com.bocop.zyt.frg.FrgModeByCodelTong;
import com.bocop.zyt.jx.constants.Constants;
import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.item.proto.MFunctionIcon;
import com.mdx.framework.activity.NoTitleAct;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.MImageView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class ModelCateSon extends BaseItem {
    public LinearLayout lin_detail;
    public MImageView lv_logo;
    public TextView tv_name;
    public ImageView iv_right;
    public ImageView iv_bottom;


    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_model_cate_son, null);
        convertView.setTag(new ModelCateSon(convertView));
        return convertView;
    }

    public ModelCateSon(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        lin_detail = (LinearLayout) contentview.findViewById(R.id.lin_detail);
        lv_logo = (MImageView) contentview.findViewById(R.id.lv_logo);
        tv_name = (TextView) contentview.findViewById(R.id.tv_name);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);


    }

    public void set(final MFunctionIcon item, final String code) {
        if (code.equals("ywt")||code.equals("yyt")||code.equals("yjt")||code.equals("jyt")){
            iv_bottom.setVisibility(View.VISIBLE);
            iv_right.setVisibility(View.VISIBLE);
        }else {
            iv_bottom.setVisibility(View.GONE);
            iv_right.setVisibility(View.GONE);
        }
        if (code.equals("ywt")){
            iv_bottom.setBackgroundColor(Color.parseColor("#ffffff"));
            iv_right.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            iv_bottom.setBackgroundColor(Color.parseColor("#dcdcdc"));
            iv_right.setBackgroundColor(Color.parseColor("#dcdcdc"));
        }
        lv_logo.setObj(item.icon);
        tv_name.setText(item.name);
        lin_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(item.url)) {
                    PanDuan(item.sn, item.name, code);
                } else {
//                    Helper.startActivity(context, FrgPtDetail.class, NoTitleAct.class, "data", item);
                    if (code.equals("yct3")) {
                        XWebAct.startAct(context, item.url, item.name,code);
                    } else {
                        WebActivity.startAct(context, item.url, item.name,code);
                    }

                }

            }
        });

    }


    /**
     * 根据后台返回的code来跳转原生页面
     *
     * @param code
     */
    private void PanDuan(final String code, final String title, final String from) {
        switch (code) {
            case "grfpd":
                //个人扶贫贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_grfp);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case "lyfpd":
                //旅游扶贫贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_lyfp);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case "lct":
                //理财
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
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
                                        intent.putExtra("title", title);
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
            case "shzs":
                //生活助手
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Intent intent = new Intent(context, XmsMainActivity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("from", from);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case "zxjf":
                //在线缴费
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Intent intent = new Intent(context, BMJFActivity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("isShowParamsTitle", true);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case "jkgj":
                //健康管家
                Bundle bundle = new Bundle();
                bundle.putString("userId", LoginUtil.getUserId(context));
                bundle.putString("token", LoginUtil.getToken(context));
                bundle.putString("platform", "fpt");
                bundle.putString("title", title);
                Intent intent = new Intent(context, JKETActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
            case "zggjd":
                //职工公积贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_ZHI_GONG_GONG_JI_DAI);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case "zgxfd":
                //职工消费贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.Tag_zhigongxiaofeidai);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case "hydt":
                //鸿运当头
                intent = new Intent(context, GoldManagerActivity.class);
                context.startActivity(intent);
                break;
            case "cfgj":
                //财富管家
                XWebAct.startAct(context, IURL.getBankCaiFuTong(context), title,from);
                break;
            case "czyh":
                //车主优惠
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Intent intent = new Intent(context, RiderFristActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "bjlv":
                //便捷旅游
                NUser.LoginInfo u = LoginHelper.get_instance((Activity) context).get_login_info();
                String url = IURL.Bank_Bian_Jie_Lv_You;
                if (LoginUtil.isLog(context)) {
                    url = url + "?userid=" + u.userid + "&accessToken=" + u.access_token;
                }
                XWebAct.startAct(context, url, title,from);

                break;
            case "hjg":
                //聚惠购
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        NUser.LoginInfo u = LoginHelper.get_instance((Activity) context).get_login_info();
                        String url = IURL.Bank_Ju_Hui_Gou + "&userId=" + u.userid + "&tokens=" + u.access_token;
                        ILOG.log_4_7("url " + url);
                        XWebAct.startAct(context, url, title,from);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "xfyd":
                //消费易贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {

                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_YAN_XIAO_FEI_YI_DAI);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "cyyd":
                //消费易贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {

                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_YAN_CHUANG_YE_YI_DAI);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "zqyd":
                //证券
                ZqtFirstActivity.startAct(context);

                break;
            case "wcg":
                //文瓷购
//                Ci_Mall_Act.startAct(context);
                Helper.startActivity(context, FrgModeByCodelTong.class, NoTitleAct.class, "code", "yct3", "name", "陶瓷商城");
                break;
            case "wxd":
                //文消贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_WEN_XIAO_DAI);
                        // Intent intent = new Intent(baseActivity, LoanMainActivity.class);
                        bundle.putString("title", title);
                        bundle.putBoolean("isShowParamsTitle", true);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "wcd":
                //文创贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_WEN_CHUANG_DAI);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "syed":
                //社员e贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_FU_NONG_DAI);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "xfed":
                //消费e贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_XIAO_FEI_DAI);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "gjed":
                //公积e贷
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_GONG_JI_DAI);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "jhex":
                //聚惠e享
                XWebAct.startAct(context, BocSdkConfig.WEIHUI + LoginUtil.getUserId(context),
                        title, true);

                break;
            case "wget":
                //外购e通
                CustomProgressDialog.showBocFengxianDialog(context, "免责声明",
                        "\t\t中国银行信用卡仅作为客户用于境外网上商户的支付结算工具，对于商品质量或商户服务无法负责；本网站引用的网址、商户及商品信息，意在为持卡人境外购物行为提供便利，并解决境外购物所遇常见问题，对境外商户网站合法性、安全性、准确性无法负责；注意网购有风险，如您在境外购物过程中发生商品质量、转运服务、货物丢失、延迟收货等购物纠纷，请您直接与购物网站或转运服务商沟通解决；境外购物需遵守国家相关法律、法规。 ",
                        "com.bocop.jxplatform.activity.HTZQActivity", title, true);

                break;
            case "ezf":
                //e支付
                Timer _timer = new Timer();
                try {
                    if (MyUtil.isAppInstalled(context, "com.boc.bocop.container")) {
                        _timer.schedule(new TimerTask() {

                            @Override
                            public void run() {

                                PackageManager packageManager = context.getPackageManager();
                                Intent intent = new Intent();
                                intent = packageManager.getLaunchIntentForPackage("com.boc.bocop.container");
                                context.startActivity(intent);
                            }
                        }, 2000);
                    } else {
                        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(Constants.UrlForZyys));
                        context.startActivity(viewIntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case "hd":
                //汇兑通
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", title);
                        Intent intent = new Intent(context, HuiDuiTongActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "bx":
                intent = new Intent(context, YbtActivity.class);
                intent.putExtra("title", title);
                context.startActivity(intent);

                break;
            case "zft":
                _timer = new Timer();
                try {
                    if (MyUtil.isAppInstalled(context, "com.boc.bocop.container")) {
                        _timer.schedule(new TimerTask() {

                            @Override
                            public void run() {

                                PackageManager packageManager = context.getPackageManager();
                                Intent intent = new Intent();
                                intent = packageManager.getLaunchIntentForPackage("com.boc.bocop.container");
                                context.startActivity(intent);
                            }
                        }, 2000);
                        //ToastUtils.show(getActivity(), "正在为您跳转中银易商", 2000);

                    } else {
                        Intent viewIntent = new
                                Intent("android.intent.action.VIEW", Uri.parse(Constants.UrlForZyys));
                        context.startActivity(viewIntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case "yztgdt":
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", 5);
                        bundle.putInt("PRO_FlAG_STATE", TrainsActivity.FLAG_YZT_GDT);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "yqtgdt":
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", 5);
                        bundle.putInt("PRO_FlAG_STATE", TrainsActivity.FLAG_YQT_GDT);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "gdt":
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_GDT);
                        bundle.putString("title", title);
                        bundle.putString("from", from);
                        bundle.putBoolean("isShowParamsTitle", true);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            case "cyt":
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_ZDT);
                        bundle.putString("title", title);
                        bundle.putString("from", from);
                        bundle.putBoolean("isShowParamsTitle", true);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case "tsj":
                //陶世界
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {

                    @Override
                    public void suc() {
                        // TODO Auto-generated method stub
                        XWebActForTaoCIMall.startActForPost(context, IURL.Bank_Ci_shang_cheng, "username="
                                        + LoginHelper.get_instance((Activity) context).get_login_info().userid + "&secret_key=245ab6167079fdcd",
                                "陶瓷商城");

                    }

                    @Override
                    public void fali() {
                        // TODO Auto-generated method stub

                    }
                });
                break;
            case "yctxdb":
                //消贷宝
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_CI_XIAO_DAI_BAO);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case "yctcdb":
                //创贷宝
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_CI_CHUANG_DAI_BAO);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);

                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case "jrxdt":
                //军人薪贷通
                LoginHelper.get_instance((Activity) context).login((Activity) context, new LoginHelper.LoginCallback() {
                    @Override
                    public void suc() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_JUN_REN_ZHUAN_XIANG_DAI);
                        bundle.putString("title", title);
                        bundle.putBoolean("isShowParamsTitle", true);
                        bundle.putString("from", from);
                        Intent intent = new Intent(context, TrainsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void fali() {

                    }
                });
                break;
            case "jrkh":
                //军人卡汇通
                XWebAct.startAct(context, BocSdkConfig.WEIHUI + LoginUtil.getUserId((Activity) context),
                        title, true);
                break;
            default:
                Helper.toast("敬请期待", context);
                break;
        }
    }


}