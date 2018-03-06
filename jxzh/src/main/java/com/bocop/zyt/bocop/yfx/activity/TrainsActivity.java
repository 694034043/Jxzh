package com.bocop.zyt.bocop.yfx.activity;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragmentHelper;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.yfx.view.Utils;
import com.bocop.zyt.bocop.yfx.xml.CspXmlYfx016;
import com.bocop.zyt.bocop.yfx.xml.identitycheck.IdentityCheckBean;
import com.bocop.zyt.bocop.yfx.xml.identitycheck.IdentityCheckResp;
import com.bocop.zyt.bocop.zyt.gui.ImageAdsAct;
import com.bocop.zyt.bocop.zyyr.activity.FinanceMainActivity;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.common.util.ContentUtils;
import com.bocop.zyt.jx.constants.Constants;
import com.bocop.zyt.jx.tools.DialogUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author LH
 *
 */
@ContentView(R.layout.yfx_activity_trains)
public class TrainsActivity extends BaseActivity implements LoginUtil.ILoginListener {

    public static final int FLAG_FPT = 1; // 扶贫通
    public static final int FLAG_FNT = 2; // 扶农通
    public static final int FLAG_GJT = 3; // 公积通 职工公积贷
    public static final int FLAG_FWT = 4; // 扶微通
    public static final int FLAG_GDT = 5; // 个贷通 职工消费贷 消费贷
    public static final int FLAG_ZDT = 6; // 专贷通
    public static final int FLAG_ZXT = 7; // 中小通
    public static final int FLAG_grfp = 8; // 个人扶贫
    public static final int FLAG_lyfp = 9; // 旅游扶贫
    public static final int FLAG_gct = 10; // 购车
    public static final int FLAG_zxt1 = 11; // 装修
    public static final int FLAG_xft = 12; // 消费

    public static final String PRO_FlAG_STATE = "PRO_FlAG_STATE";
    public static final int FLAG_YQT_GDT = 50001;//银区通个贷
    public static final int FLAG_YZT_GDT = 50002;//银政通个贷
    public static final int FLAG_YZTYT_GDT = 50013;//银政通银滩个贷

    public static final int FLAG_YYT_GJYD = 60011;//银烟通公积易贷


    public static int PRO_FLAG = 0;
    public static int PRO_FLAG1 = 0;
    private String custType = ""; //入口类型

    public BaseApplication baseApplication = BaseApplication.getInstance();
    protected BaseActivity baseActivity;

    @ViewInject(R.id.tv_titleName)
    private TextView tv_titleName;
    @ViewInject(R.id.iv_imageLeft)
    private BackButton backBtn;

    @ViewInject(R.id.ivImage)
    private ImageView ivImage;
    @ViewInject(R.id.ivHeader)
    private ImageView ivHeader;
    @ViewInject(R.id.ivFooter)
    private ImageView ivFooter;
    @ViewInject(R.id.ivText)
    private ImageView ivText;
    @ViewInject(R.id.btnApply)
    Button btnApply;
    @ViewInject(R.id.btnApply2)
    Button btnApply2;

    @ViewInject(R.id.llApply2)
    LinearLayout llApply2;
    @ViewInject(R.id.ivBorder02)
    ImageView ivBorder02;

    @ViewInject(R.id.ll_button_part)
    LinearLayout llButtonPart;

    /**
     * 专为银瓷通设计
     */
    public static void startActForYinCI(Context context, int flag, int bg_res_id, String title) {
        Intent intent = new Intent(context, TrainsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("PRO_FLAG", flag);
        bundle.putInt("bg_res_id", bg_res_id);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnApply = (Button) findViewById(R.id.btnApply);

        try {
            initView();
            initListener();
        } catch (Exception e) {
            finish();
        }

        if (getIntent().hasExtra("bg_res_id")) {

            // 银瓷通

            int bg_res_id = getIntent().getExtras().getInt("bg_res_id");
            String title = getIntent().getExtras().getString("title");

            ivImage.setImageResource(bg_res_id);
            tv_titleName.setText(title);

        }

        String title = getIntent().getExtras().getString("title");
        boolean isShowParamsTitle = getIntent().getBooleanExtra("isShowParamsTitle",false);
        if(isShowParamsTitle){
            tv_titleName.setText(title);
        }
    }

    @SuppressLint("NewApi")
    private void initView() {
        PRO_FLAG = getIntent().getExtras().getInt("PRO_FLAG");
        proFlagState = getIntent().getExtras().getInt("PRO_FlAG_STATE");
        switch (PRO_FLAG) {
            case FLAG_FPT:
                ivImage.setImageResource(R.drawable.yfx_trains_bg_fpt);
                tv_titleName.setText("扶贫通");
                custType = "FP";
                break;
            case FLAG_FNT:
                ivImage.setImageResource(R.drawable.yfx_trains_bg_fnt);
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak);
                llApply2.setVisibility(View.VISIBLE);
                tv_titleName.setText("扶农通");
                custType = "FN";
                break;
            case HomeFragmentHelper.TAG_FU_NONG_DAI:
                //((RelativeLayout.LayoutParams) llButtonPart.getLayoutParams()).setMargins(0, 0, 0, (int) Utils.dp2px(getResources(),20));
                ivImage.setImageResource(R.drawable.ygt_trains_display_bg_fnd);
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak);
                llApply2.setVisibility(View.VISIBLE);
                tv_titleName.setText(getResources().getString(R.string.ygt_item_01_01_02));
                custType = "FN";
                break;
            case FLAG_GJT:
                //ivImage.setImageResource(R.drawable.yfx_trains_bg_gjt);
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_gjt1);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                if(proFlagState == FLAG_YYT_GJYD){
                    btnApply.setBackgroundResource(R.drawable.yfx_trains_yyt_btn_query);
                    btnApply2.setBackgroundResource(R.drawable.yfx_trains_yyt_btn_apply);
                    llApply2.setVisibility(View.VISIBLE);
                    ivImage.setVisibility(View.GONE);
                    ivHeader.setVisibility(View.VISIBLE);
                    ivBorder02.setVisibility(View.VISIBLE);
                    ivHeader.setImageResource(R.drawable.yfx_trains_bg_yyt_gjt_header);
                    ivBorder02.setImageResource(R.drawable.yfx_trains_bg_yyt_gjt_border);
                    tv_titleName.setText("公积易贷");
                }else{
                    llApply2.setVisibility(View.VISIBLE);
                    ivImage.setVisibility(View.GONE);
                    ivHeader.setVisibility(View.VISIBLE);
                    ivFooter.setVisibility(View.VISIBLE);
                    ivText.setVisibility(View.VISIBLE);
                    ivHeader.setImageResource(R.drawable.yfx_trains_bg_gjt_header);
                    ivText.setImageResource(R.drawable.yfx_trains_bg_gjt_text);
                    tv_titleName.setText("公积通");
                }
                custType = "GJ";
                break;
            case HomeFragmentHelper.TAG_ZHI_GONG_GONG_JI_DAI:
                ivImage.setImageResource(R.drawable.ytt_trains_bg_zggjd);
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_gjt1);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                llApply2.setVisibility(View.VISIBLE);
                tv_titleName.setText("职工公积贷");
                custType = "GJ";
                break;
            case HomeFragmentHelper.TAG_GONG_JI_DAI:
                //((RelativeLayout.LayoutParams) llButtonPart.getLayoutParams()).setMargins(0, 0, 0, (int) Utils.dp2px(getResources(),20));
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_gjt1);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                llApply2.setVisibility(View.VISIBLE);
                ivImage.setImageResource(R.drawable.ygt_trains_display_bg_gjd);
                tv_titleName.setText(getResources().getString(R.string.ygt_item_01_01_04));
                custType = "GJ";
                break;
            case FLAG_FWT:
                ivImage.setImageResource(R.drawable.yfx_trains_bg_fwt);
                tv_titleName.setText("扶微通");
                break;
            case HomeFragmentHelper.TAG_WEN_XIAO_DAI: {
                // 文消贷
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak);
                llApply2.setVisibility(View.VISIBLE);
                ivImage.setImageResource(R.drawable.ywt_content_display_bg_wxd);
                tv_titleName.setText(getResources().getString(R.string.ywt_item_01_f2_01_01_02));
                custType = "GD";
                break;
            }
            case HomeFragmentHelper.Tag_zhigongxiaofeidai: {
                // 职工消费贷
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak);
                llApply2.setVisibility(View.VISIBLE);
                ivImage.setImageResource(R.drawable.ytt_trains_bg_zgxfd);
                tv_titleName.setText("消费贷");
                custType = "GD";
                break;
            }
            case HomeFragmentHelper.TAG_XIAO_FEI_DAI: {
                // 职工消费贷
                //((RelativeLayout.LayoutParams) llButtonPart.getLayoutParams()).setMargins(0, 0, 0, (int) Utils.dp2px(getResources(),20));
                ivImage.setImageResource(R.drawable.ygt_trains_display_bg_xfd);
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak);
                llApply2.setVisibility(View.VISIBLE);
                tv_titleName.setText(getResources().getString(R.string.ygt_item_01_01_03));
                custType = "GD";
                break;
            }
            case HomeFragmentHelper.TAG_JUN_REN_ZHUAN_XIANG_DAI: {
                // 军人尊享贷
			/*RelativeLayout.LayoutParams jrzxdParams = (RelativeLayout.LayoutParams) llButtonPart.getLayoutParams();
			jrzxdParams.height = (int)Utils.dp2px(getResources(), 45);
			jrzxdParams.setMargins(0, 0, 0, (int) Utils.dp2px(getResources(), 40));*/
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak);

                llApply2.setVisibility(View.VISIBLE);
                ivImage.setImageResource(R.drawable.yjt_trains_bg_jrzxd);
                tv_titleName.setText(getResources().getString(R.string.yjt_item_01_01_01));
                custType = "GD";
                break;
            }
            case HomeFragmentHelper.TAG_BI_BI_CHUANG_TONG:
                ivImage.setImageResource(R.drawable.ybt_trains_bg_bct);
                tv_titleName.setText(getResources().getString(R.string.ybt_finance_item_01_01_02));
                break;
            case HomeFragmentHelper.TAG_YAN_XIAO_FEI_YI_DAI:
                // 消费易贷
                btnApply.setBackgroundResource(R.drawable.yfx_trains_yyt_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_yyt_btn_gak);
                btnApply.setVisibility(View.VISIBLE);
                btnApply2.setVisibility(View.VISIBLE);
                llApply2.setVisibility(View.VISIBLE);
                ivImage.setVisibility(View.GONE);
                ivHeader.setVisibility(View.VISIBLE);
                ivBorder02.setVisibility(View.VISIBLE);
                ivHeader.setImageResource(R.drawable.yfx_trains_bg_yyt_xfyd_header);
                ivBorder02.setImageResource(R.drawable.yfx_trains_bg_yyt_xfyd_border);
                tv_titleName.setText("消费易贷");
                custType = "GD";
                break;
            case FLAG_GDT:
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak);
                llApply2.setVisibility(View.VISIBLE);
                ivImage.setVisibility(View.VISIBLE);
                tv_titleName.setText("个贷通");
                if(proFlagState == FLAG_YQT_GDT){
                    ivImage.setImageResource(R.drawable.yqt_trains_bg_gdt);
                }else if(proFlagState == FLAG_YZT_GDT){
                    ivImage.setImageResource(R.drawable.yzt_trains_bg_gdt);
                }else if(proFlagState == FLAG_YZTYT_GDT){
                    ivImage.setImageResource(R.drawable.yztyt_trains_bg_gdt);
                    tv_titleName.setText(getResources().getString(R.string.yzt_ytzq_item_02_01_05));
                }else{
                    //ivImage.setImageResource(R.drawable.yfx_trains_bg_gdt);
                    ivImage.setVisibility(View.GONE);
                    ivHeader.setVisibility(View.VISIBLE);
                    ivFooter.setVisibility(View.VISIBLE);
                    ivText.setVisibility(View.VISIBLE);
                    ivText.setImageResource(R.drawable.yfx_trains_bg_gdt_text);
                    ivHeader.setImageResource(R.drawable.yfx_trains_bg_gdt_header);
                }
                custType = "GD";
                break;
            case FLAG_ZDT:
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak2);
                llApply2.setVisibility(View.VISIBLE);
                ivImage.setImageResource(R.drawable.yfx_trains_bg_zdt);
                tv_titleName.setText("创业通");
                break;
            case HomeFragmentHelper.TAG_WEN_CHUANG_DAI:
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak2);
                llApply2.setVisibility(View.VISIBLE);
                ivImage.setImageResource(R.drawable.ywt_content_display_bg_zxqyd);
                tv_titleName.setText(getResources().getString(R.string.ywt_item_01_f2_01_01_03));
                break;
            case HomeFragmentHelper.TAG_CI_XIAO_DAI_BAO:
                ((RelativeLayout.LayoutParams) llButtonPart.getLayoutParams()).setMargins(0, 0, 0, (int) Utils.dp2px(getResources(),35));
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak);
                llApply2.setVisibility(View.VISIBLE);
                ivImage.setImageResource(R.drawable.yct_trains_bg_xdb);
                tv_titleName.setText("消贷宝");
                custType = "GD";
                break;
            case HomeFragmentHelper.TAG_CI_CHUANG_DAI_BAO:
                ((RelativeLayout.LayoutParams) llButtonPart.getLayoutParams()).setMargins(0, 0, 0, (int) Utils.dp2px(getResources(),35));
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak2);
                llApply2.setVisibility(View.VISIBLE);
                ivImage.setImageResource(R.drawable.yct_trains_bg_cdb);
                tv_titleName.setText("创贷宝");
                break;
            case HomeFragmentHelper.TAG_YAN_CHUANG_YE_YI_DAI:
                llApply2.setVisibility(View.VISIBLE);
                ivImage.setVisibility(View.GONE);
                ivHeader.setVisibility(View.VISIBLE);
                ivBorder02.setVisibility(View.VISIBLE);
                ivHeader.setImageResource(R.drawable.yfx_trains_bg_yyt_cyyd_header);
                ivBorder02.setImageResource(R.drawable.yfx_trains_bg_yyt_cyyd_border);
                btnApply.setBackgroundResource(R.drawable.yfx_trains_yyt_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_yyt_btn_gak);
                tv_titleName.setText("创业易贷");
                break;
            case FLAG_ZXT:
                ivImage.setImageResource(R.drawable.yfx_trains_bg_zxt);
                tv_titleName.setText("中小通");
                break;
            case FLAG_grfp:
                ivImage.setImageResource(R.drawable.yfx_trains_bg_grfp);
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak);
                llApply2.setVisibility(View.VISIBLE);
                tv_titleName.setText("个人扶贫贷");
                custType = "FP";
                break;
            case FLAG_lyfp:
                ivImage.setImageResource(R.drawable.yfx_trains_bg_lyfp);
                btnApply.setBackgroundResource(R.drawable.yfx_trains_btn_apply);
                btnApply2.setBackgroundResource(R.drawable.yfx_trains_btn_gak);
                llApply2.setVisibility(View.VISIBLE);
                tv_titleName.setText("旅游扶贫贷");
                custType = "FP";
                break;
            case FLAG_gct:
                ivImage.setImageResource(R.drawable.yfx_trains_bg_gct);
                tv_titleName.setText("购车通");
                break;
            case FLAG_zxt1:
                ivImage.setImageResource(R.drawable.yfx_trains_bg_zxt1);
                tv_titleName.setText("装修通");
                break;
            case FLAG_xft:
                ivImage.setImageResource(R.drawable.yfx_trains_bg_xft);
                tv_titleName.setText("消费通");

                break;
        }

    }

    public static boolean isHiden2 = false;
    private int proFlagState;

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        PRO_FLAG1 = 0;
    }

    private void initListener() {
        btnApply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (PRO_FLAG == FLAG_gct
                        || PRO_FLAG == FLAG_zxt1
                        || PRO_FLAG == FLAG_xft) {
                    isHiden2 = true;
                } else {
                    isHiden2 = false;
                }
                if (PRO_FLAG == HomeFragmentHelper.Tag_zhigongxiaofeidai) {
                    PRO_FLAG = FLAG_GDT;
                    PRO_FLAG1 = HomeFragmentHelper.Tag_zhigongxiaofeidai;
                    goToZZYD();
                    return;
                }
                if (PRO_FLAG == FLAG_GDT
                        || PRO_FLAG == HomeFragmentHelper.TAG_WEN_XIAO_DAI
                        || PRO_FLAG == HomeFragmentHelper.TAG_YAN_XIAO_FEI_YI_DAI
                        || PRO_FLAG == HomeFragmentHelper.TAG_JUN_REN_ZHUAN_XIANG_DAI
                        || PRO_FLAG == HomeFragmentHelper.TAG_CI_XIAO_DAI_BAO) {
                    goToZZYD();
                } else if (PRO_FLAG == FLAG_ZDT
                        || PRO_FLAG == HomeFragmentHelper.TAG_WEN_XIAO_DAI
                        || PRO_FLAG == HomeFragmentHelper.TAG_WEN_CHUANG_DAI
                        || PRO_FLAG == HomeFragmentHelper.TAG_CI_CHUANG_DAI_BAO
                        || PRO_FLAG == HomeFragmentHelper.TAG_YAN_CHUANG_YE_YI_DAI) {
                    // 专贷通
                    Intent intent = new Intent(TrainsActivity.this, FinanceMainActivity.class);
                    startActivity(intent);
                }else if (PRO_FLAG == FLAG_GJT
                        || PRO_FLAG == HomeFragmentHelper.TAG_GONG_JI_DAI
                        || PRO_FLAG == HomeFragmentHelper.TAG_ZHI_GONG_GONG_JI_DAI) {
                    if (LoginUtil.isLog(TrainsActivity.this)) {
                        //公积金查询
                        Intent intent = new Intent(TrainsActivity.this, FundQueryActivity.class);
                        startActivity(intent);
                    } else {
                        LoginUtil.authorize(TrainsActivity.this, TrainsActivity.this);
                    }
                } else {
                    goToZZYD();
                }
            }
        });

        btnApply2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (PRO_FLAG == FLAG_GDT
                        || PRO_FLAG==HomeFragmentHelper.Tag_zhigongxiaofeidai
                        || PRO_FLAG == HomeFragmentHelper.TAG_YAN_XIAO_FEI_YI_DAI
                        || PRO_FLAG == HomeFragmentHelper.TAG_CI_XIAO_DAI_BAO
                        || PRO_FLAG == HomeFragmentHelper.TAG_JUN_REN_ZHUAN_XIANG_DAI
                        || PRO_FLAG==HomeFragmentHelper.TAG_XIAO_FEI_DAI
                        || PRO_FLAG==HomeFragmentHelper.TAG_WEN_XIAO_DAI) {
                    ConsultationActivity.startAct(TrainsActivity.this, PRO_FLAG);
                }else if(PRO_FLAG == FLAG_GJT
                        || PRO_FLAG == HomeFragmentHelper.TAG_GONG_JI_DAI
                        || PRO_FLAG == HomeFragmentHelper.TAG_ZHI_GONG_GONG_JI_DAI){
                    goToZZYD();
                }else if(PRO_FLAG==FLAG_ZDT
                        || PRO_FLAG == HomeFragmentHelper.TAG_YAN_CHUANG_YE_YI_DAI
                        || PRO_FLAG == HomeFragmentHelper.TAG_WEN_CHUANG_DAI
                        || PRO_FLAG==HomeFragmentHelper.TAG_CI_CHUANG_DAI_BAO){
                    ImageAdsAct.startAct(TrainsActivity.this, R.drawable.ic_bg_zdt_inquiry, "业务咨询");
                }else if(PRO_FLAG == FLAG_grfp
                        || PRO_FLAG == FLAG_lyfp){
                    ConsultationActivity.startAct(TrainsActivity.this, ConsultationActivity.STATE_FUPING);
                }else if(PRO_FLAG == FLAG_FNT
                        || PRO_FLAG == HomeFragmentHelper.TAG_FU_NONG_DAI){
                    ConsultationActivity.startAct(TrainsActivity.this, PRO_FLAG);
                }
            }
        });
    }

    private void goToZhzyyd(){
        if (!LoginUtil.isLog(this)) {
            LoginUtil.authorize(this, TrainsActivity.this);
            return;
        }

        Bundle bundle = new Bundle();
        //bundle.putInt("PRO_FLAG", 0);
        bundle.putInt("PRO_FLAG", PRO_FLAG);
        Intent intent = new Intent(TrainsActivity.this,LoanActivity.class);
        intent.putExtras(bundle	);
        startActivity(intent);
    }

    /**
     * 跳转到中银E贷界面
     */
    private void goToZZYD() {

        if (!LoginUtil.isLog(this)) {
            LoginUtil.authorize(this, TrainsActivity.this);
            return;
        }

        final String cardId = ContentUtils.getSharePreStr(this, Constants.CUSTOM_PREFERENCE_NAME,
                Constants.CUSTOM_ID_NO);
        if (!BocSdkConfig.isTest) {
            if (null != CacheBean.getInstance().get(CacheBean.CUST_ID)
                    && !TextUtils.isEmpty(CacheBean.getInstance().get(CacheBean.CUST_ID).toString())
                    && !TextUtils.isEmpty(cardId)) {
                // if (checkTime()) {
                requestIdentityCheck(cardId);
                // Bundle bundle = new Bundle();
                // bundle.putInt("PRO_FLAG", PRO_FLAG == FLAG_GDT ? 0 :
                // PRO_FLAG);
                // callMe(LoanMainActivity.class, bundle);
                // } else {
                // Toast.makeText(this, "温馨提示：每日 07:00 -- 21:00 提供服务。",
                // Toast.LENGTH_SHORT).show();
                // }
            } else {
                LoginUtil.requestBocopForCustid(this, true, new LoginUtil.OnRequestCustCallBack() {

                    @Override
                    public void onSuccess() {
                        requestIdentityCheck(cardId);
                        // Bundle bundle = new Bundle();
                        // bundle.putInt("PRO_FLAG", PRO_FLAG == FLAG_GDT ? 0 :
                        // PRO_FLAG);
                        // callMe(LoanMainActivity.class, bundle);
                    }
                });
            }
        } else {
            CacheBean.getInstance().put(CacheBean.CUST_ID, "");
            Bundle bundle = new Bundle();
            bundle.putInt("PRO_FLAG", 0);
            callMe(LoanMainActivity.class, bundle);
        }
    }

    /**
     * 跳转到中银E贷界面
     */
	/*private void goToZZYD() {
		String strId;
		strId = CustomInfo.getCustomId(TrainsActivity.this);
		if (strId != null) {
			Log.i("tag", "strId");
		}
		final String cardId = ContentUtils.getSharePreStr(this, Constants.CUSTOM_PREFERENCE_NAME,
				Constants.CUSTOM_ID_NO);
		if (!BocSdkConfig.isTest) {
			if (null != CacheBean.getInstance().get(CacheBean.CUST_ID)
					&& !TextUtils.isEmpty(CacheBean.getInstance().get(CacheBean.CUST_ID).toString())) {
				// if (checkTime()) {
				requestIdentityCheck(cardId);
				Bundle bundle = new Bundle();
				bundle.putInt("PRO_FLAG", PRO_FLAG == FLAG_GDT ? 0 : FLAG_GDT);
				callMe(LoanMainActivity.class, bundle);
				// } else {
				// Toast.makeText(this, "温馨提示：每日 07:00 -- 21:00 提供服务。",
				// Toast.LENGTH_SHORT).show();
				// }
			} else {
				LoginUtil.requestBocopForCustid(this, true, new OnRequestCustCallBack() {

					@Override
					public void onSuccess() {
						requestIdentityCheck(cardId);
						Bundle bundle = new Bundle();
						bundle.putInt("PRO_FLAG", PRO_FLAG == FLAG_GDT ? 0 : FLAG_GDT);
						callMe(LoanMainActivity.class, bundle);
					}
				});
			}
		} else {
			CacheBean.getInstance().put(CacheBean.CUST_ID, "");
			Bundle bundle = new Bundle();
			//bundle.putInt("PRO_FLAG", 0);
			bundle.putInt("PRO_FLAG", PRO_FLAG == 0 ? 0 : PRO_FLAG);
			callMe(LoanMainActivity.class, bundle);
		}
	}*/

    /**
     * 请求身份验证
     */
    private void requestIdentityCheck(String cardId) {
        try {
            CspXmlYfx016 cspXmlYfx016 = new CspXmlYfx016(cardId, custType);
            String strXml = cspXmlYfx016.getCspXml();
            // 生成MCIS报文
            Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
            final byte[] byteMessage = mcis.getMcis();
            // 发送报文
            CspUtil cspUtil = new CspUtil(this);
            cspUtil.setFLAG_YFX_CSP(true);
            Log.i("tag", "发送报文： " + new String(byteMessage, "GBK"));
            cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {

                @Override
                public void onSuccess(String responStr) {
                    IdentityCheckBean identityCheckBean = IdentityCheckResp.readStringXml(responStr);
                    if (identityCheckBean != null) {
                        if ("00".equals(identityCheckBean.getErrorcode())) {
                            String type = identityCheckBean.getCustType();
                            if(HomeFragmentHelper.TAG_FU_NONG_DAI == PRO_FLAG){
                                goToZhzyyd();
                            }else if ("ZH".equals(type)) {
                                if ("GD".equals(custType)) {
                                    goToZhzyyd();
                                } else {
                                    DialogUtil.showWithToMain(TrainsActivity.this, "您不是中银受邀客户!");
                                }
                            }else {
                                Bundle bundle = new Bundle();
                                bundle.putInt("PRO_FLAG", PRO_FLAG == FLAG_GDT ? 0 : PRO_FLAG);
                                callMe(LoanMainActivity.class, bundle);
                            }
                        } else if ("50".equals(identityCheckBean.getErrorcode())) {
                            DialogUtil.showWithToMain(TrainsActivity.this, identityCheckBean.getErrormsg());
                        } else {
                            CspUtil.onFailure(TrainsActivity.this, identityCheckBean.getErrormsg());
                        }
                    }
                }

                @Override
                public void onFailure(String responStr) {
                    ToastUtils.show(TrainsActivity.this, responStr, Toast.LENGTH_SHORT);
                }

                @Override
                public void onFinish() {

                }

            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求身份验证
     */
	/*private void requestIdentityCheck(String cardId) {
		try {
			CspXmlYfx016 cspXmlYfx016 = new CspXmlYfx016(cardId, custType);
			String strXml = cspXmlYfx016.getCspXml();
			// 生成MCIS报文
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			final byte[] byteMessage = mcis.getMcis();
			// 发送报文
			CspUtil cspUtil = new CspUtil(this);
			cspUtil.setFLAG_YFX_CSP(true);
			Log.i("tag", "发送报文： " + new String(byteMessage, "GBK"));
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CallBack() {

				@Override
				public void onSuccess(String responStr) {
					IdentityCheckBean identityCheckBean = IdentityCheckResp.readStringXml(responStr);
					if (identityCheckBean != null) {
						if ("00".equals(identityCheckBean.getErrorcode())) {
							String type = identityCheckBean.getCustType();
							if("GD".equals(custType)){
								if ("ZH".equals(type)) {
									goToZhzyyd();
								} else  {
									Bundle bundle = new Bundle();
									bundle.putInt("PRO_FLAG", PRO_FLAG == FLAG_GDT ? 0 : PRO_FLAG);
									callMe(LoanMainActivity.class, bundle);
								}
							}else{
								DialogUtil.showWithToMain(TrainsActivity.this, "您不是中银受邀客户!");
							}
						} else if ("50".equals(identityCheckBean.getErrorcode())) {
							DialogUtil.showWithToMain(TrainsActivity.this, identityCheckBean.getErrormsg());
						} else {
							CspUtil.onFailure(TrainsActivity.this, identityCheckBean.getErrormsg());
						}
					}
				}

				@Override
				public void onFailure(String responStr) {
					ToastUtils.show(TrainsActivity.this, responStr, Toast.LENGTH_SHORT);
				}

				@Override
				public void onFinish() {

				}

			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}*/

    /**
     * 检验时间是否在规定区间内
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private boolean checkTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");

        int nowTime = Integer.parseInt(sdf.format(new Date()));
        if (nowTime >= 70000 && nowTime <= 210000) {
            return true;
        }

        return false;
    }

    /* (non-Javadoc)
     * @see com.bocop.jxplatform.util.LoginUtil.ILoginListener#onLogin()
     */
    @Override
    public void onLogin() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    /* (non-Javadoc)
     * @see com.bocop.jxplatform.util.LoginUtil.ILoginListener#onCancle()
     */
    @Override
    public void onCancle() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.bocop.jxplatform.util.LoginUtil.ILoginListener#onError()
     */
    @Override
    public void onError() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.bocop.jxplatform.util.LoginUtil.ILoginListener#onException()
     */
    @Override
    public void onException() {
        // TODO Auto-generated method stub

    }
}
