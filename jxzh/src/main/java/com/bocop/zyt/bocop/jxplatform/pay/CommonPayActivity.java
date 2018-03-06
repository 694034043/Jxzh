package com.bocop.zyt.bocop.jxplatform.pay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.MyUtil;
import com.bocop.zyt.bocop.jxplatform.util.RSAUtil;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.bocop.qzt.QztMainActivity;
import com.bocop.zyt.bocop.qzt.QztPayActivity;
import com.bocop.zyt.bocop.zyt.gui.XWebAct;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.constants.Constants;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class CommonPayActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "CommonPayActivity";

    private String mMoney;
    private String mOrderNum;
    private String mStrPuopose;

    private RadioButton btnUnion;
    private RadioButton btnWechat;
    private RadioButton btnAlipay;

    private int mPayMode;
    public static final int PAY_MODE_UNION = 0x001;
    public static final int PAY_MODE_WECHAT = 0x002;
    public static final int PAY_MODE_ALIPAY = 0x003;
    public static final int PAY_MODE_ZYPAY = 0x004;

    private String mPublicKey;

    private RSAUtil mRSAUtil;

    //    private static final String PAY_SERVER = "http://182.106.129.135:9999";
   // private static final String PAY_SERVER = "http://123.124.191.179:80";
//    private static final String PAY_SERVER = "http://172.20.2.153:9080";
//    private static final String PAY_SERVER = "http://172.20.2.153:8080";
    private static final String PAY_SERVER = "http://116.62.169.111:8080";

    private OkHttpClient okHttpClient;

    private ProgressDialog mProgressDialog;

	private RadioButton btnZypay;
	
	private Timer _timer;

	private int payMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _timer = new Timer();
        setContentView(R.layout.activity_common_pay);
        if(!EventBus.getDefault().isRegistered(this)){
        	EventBus.getDefault().register(this);        	
        }
        initView();
        mRSAUtil = RSAUtil.getInstance();
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        getPublicKeyTask();
    }

    private void initView() {

        mProgressDialog = new ProgressDialog(CommonPayActivity.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("加载中...");

        Bundle bundle = getIntent().getExtras();
        String title = "";
        if (bundle != null) {
            title = bundle.getString("title");
            mMoney = bundle.getString("amt", "0");
            payMode = bundle.getInt("pay_mode");
            mOrderNum = bundle.getString("orderNum");
            mStrPuopose = bundle.getString("strPuopose");
        }

        BackButton backButton = (BackButton) findViewById(R.id.iv_imageLeft);
        backButton.setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.tv_titleName);
        tvTitle.setText(title);

        TextView tvMoney = (TextView) findViewById(R.id.common_pay_money);
        EditText etMoney = (EditText) findViewById(R.id.et_pay_money);
        mMoney = etMoney.getText().toString();
        String money = "需支付：" + (TextUtils.isEmpty(etMoney.getText().toString())?"1":etMoney.getText().toString()) + "元";
        SpannableString ss = new SpannableString(money);
        ss.setSpan(new ForegroundColorSpan(Color.RED), 4, money.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvMoney.setText(ss);

        RelativeLayout layoutUnion = (RelativeLayout) findViewById(R.id.common_pay_layout_union);
        RelativeLayout layoutWechat = (RelativeLayout) findViewById(R.id.common_pay_layout_wechat);
        RelativeLayout layoutAlipay = (RelativeLayout) findViewById(R.id.common_pay_layout_alipay);
        RelativeLayout layoutZypay = (RelativeLayout) findViewById(R.id.common_pay_layout_zypay);
        layoutUnion.setOnClickListener(this);
        layoutWechat.setOnClickListener(this);
        layoutAlipay.setOnClickListener(this);
        layoutZypay.setOnClickListener(this);
        
        btnUnion = (RadioButton) findViewById(R.id.common_pay_layout_union_btn);
        btnWechat = (RadioButton) findViewById(R.id.common_pay_layout_wechat_btn);
        btnAlipay = (RadioButton) findViewById(R.id.common_pay_layout_alipay_btn);
        btnZypay = (RadioButton) findViewById(R.id.common_pay_layout_zypay_btn);

        //btnUnion.setChecked(true);
        switch (payMode) {
		case PAY_MODE_ALIPAY:
			btnAlipay.setChecked(true);
	        mPayMode = PAY_MODE_ALIPAY;
			break;
		case PAY_MODE_UNION:
			btnUnion.setChecked(true);
	        mPayMode = PAY_MODE_UNION;
			break;
		case PAY_MODE_WECHAT:
			btnWechat.setChecked(true);
	        mPayMode = PAY_MODE_WECHAT;
			break;
		case PAY_MODE_ZYPAY:
			btnZypay.setChecked(true);
	        mPayMode = PAY_MODE_ZYPAY;
			break;
		default:
			btnUnion.setChecked(true);
	        mPayMode = PAY_MODE_UNION;
			break;
		}
        Button btnPay = (Button) findViewById(R.id.common_pay_layout_btn_pay);
        btnPay.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
        	EventBus.getDefault().unregister(this);
        }
    }

    private void getPublicKeyTask() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    String url = PAY_SERVER + "/payService/getPublicKey.action";

                    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");
                    JSONObject json = new JSONObject();
                    json.put("device", "ANDROID");
                    String postStringBody = json.toString();
                    RequestBody stringRequestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postStringBody);
                    Request stringRequest = new Request
                            .Builder()
                            .url(url)
                            .post(stringRequestBody)
                            .build();

                    Call call = okHttpClient.newCall(stringRequest);
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return null;
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                mProgressDialog.dismiss();

                try {
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject json = new JSONObject(result);
                        int resultCode = json.optInt("resultCode");
                        switch (resultCode) {
                            case 0:
                                mPublicKey = json.optString("result");
                                Log.e(TAG, "公钥>" + mPublicKey);
                                break;
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CommonPayActivity.this);
                        builder.setCancelable(false);
                        builder.setMessage("连接服务失败");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_imageLeft:
                finish();
                break;
            case R.id.common_pay_layout_union:
                mPayMode = PAY_MODE_UNION;

                btnUnion.setChecked(true);
                btnWechat.setChecked(false);
                btnAlipay.setChecked(false);
                btnZypay.setChecked(false);
                break;
            case R.id.common_pay_layout_wechat:
                mPayMode = PAY_MODE_WECHAT;

                btnUnion.setChecked(false);
                btnWechat.setChecked(true);
                btnAlipay.setChecked(false);
                btnZypay.setChecked(false);
                break;
            case R.id.common_pay_layout_alipay:
                mPayMode = PAY_MODE_ALIPAY;

                btnUnion.setChecked(false);
                btnWechat.setChecked(false);
                btnAlipay.setChecked(true);
                btnZypay.setChecked(false);
                break;
            case R.id.common_pay_layout_zypay:
                /*mPayMode = PAY_MODE_ZYPAY;

                btnUnion.setChecked(false);
                btnWechat.setChecked(false);
                btnAlipay.setChecked(false);
                btnZypay.setChecked(true);*/
                
              //中银支付
                try {
					if(MyUtil.isAppInstalled(this, "com.boc.bocop.container")){
					    _timer.schedule(new TimerTask() {
							@Override
							public void run() {
								PackageManager packageManager = getPackageManager();   
							    Intent intent=new Intent();
							    intent = packageManager.getLaunchIntentForPackage("com.boc.bocop.container");
							    startActivity(intent);
							}
						}, 2000);
					}else{
					    /*Intent viewIntent = new Intent("android.intent.action.VIEW",Uri.parse(Constants.UrlForZyys));  
					    startActivity(viewIntent);*/
					    /*Bundle bundle = new Bundle();
						bundle.putString("url", Constants.UrlForZyys);
						bundle.putString("name", "中银易商");
						Intent intent = new Intent(getActivity(), WebActivity.class);
						intent.putExtras(bundle);
						getActivity().startActivity(intent);*/
						//XWebAct.startAct(this, Constants.UrlForZyys, "中银易商", true);
						WebActivity.startAct(this, Constants.UrlForZyys, "中银易商");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
                break;
            case R.id.common_pay_layout_btn_pay:

                switch (mPayMode) {
                    case PAY_MODE_UNION:
                        Bundle bundle = new Bundle();
                        bundle.putString("orderNum", mOrderNum);
                        bundle.putString("amt", mMoney);
                        Intent intent = new Intent(CommonPayActivity.this,
                                QztPayActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case PAY_MODE_WECHAT:
                        //微信支付
                        getWxpayOrder();
                        break;
                    case PAY_MODE_ALIPAY:
                        //支付宝
                        getAlipayOrder();
                        break;
                    case PAY_MODE_ZYPAY:
                        //中银支付
                        try {
        					if(MyUtil.isAppInstalled(this, "com.boc.bocop.container")){
        					    _timer.schedule(new TimerTask() {
        							@Override
        							public void run() {
        								PackageManager packageManager = getPackageManager();   
        							    Intent intent=new Intent();
        							    intent = packageManager.getLaunchIntentForPackage("com.boc.bocop.container");
        							    startActivity(intent);
        							}
        						}, 2000);
        					}else{
        					    /*Intent viewIntent = new Intent("android.intent.action.VIEW",Uri.parse(Constants.UrlForZyys));  
        					    startActivity(viewIntent);*/
        					    /*Bundle bundle = new Bundle();
        						bundle.putString("url", Constants.UrlForZyys);
        						bundle.putString("name", "中银易商");
        						Intent intent = new Intent(getActivity(), WebActivity.class);
        						intent.putExtras(bundle);
        						getActivity().startActivity(intent);*/
        						XWebAct.startAct(this, Constants.UrlForZyys, "中银易商", true);
        					}
        				} catch (Exception e) {
        					e.printStackTrace();
        				}
                        break;
                }

                break;
        }
    }

    private void getWxpayOrder() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    String url = PAY_SERVER + "/payService/wx/createWxOrder.action";

                    JSONObject order = new JSONObject();
                    order.put("device", "Android");
                    order.put("subject", mStrPuopose);
//                            order.put("amount", mMoney);
                    order.put("amount", mMoney);
                    order.put("orderId", mOrderNum);
                    order.put("userId", LoginUtil.getUserId(CommonPayActivity.this));

                    byte[] bytes = mRSAUtil.encryptByPublicKeyForSpilt(order.toString().getBytes("UTF-8"), mPublicKey);
                    String orderStr = Base64.encodeToString(bytes, Base64.NO_WRAP);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("wxpayOrder", orderStr);
                    jsonObject.put("type", 1);

                    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

                    String postStringBody = jsonObject.toString();
                    RequestBody stringRequestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postStringBody);
                    Request stringRequest = new Request
                            .Builder()
                            .url(url)
                            .post(stringRequestBody)
                            .build();
                    Call call = okHttpClient.newCall(stringRequest);
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                	Log.w("cyc", e.getMessage());
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                mProgressDialog.dismiss();

                if (!TextUtils.isEmpty(s)) {
                    Log.e(TAG, "微信订单>" + s);

                    try {
                        JSONObject response = new JSONObject(s);
                        int resultCode = response.optInt("resultCode");
                        String msg = response.optString("msg", "");
                        switch (resultCode) {
                            case 0:
                                JSONObject result = response.optJSONObject("result");
                                if (result != null) {
                                    String reqAppid = result.optString("reqAppid");
                                    String reqNoncestr = result.optString("reqNoncestr");
                                    String reqPackage = result.optString("reqPackage");
                                    String reqPartnerid = result.optString("reqPartnerid");
                                    String reqPrepayid = result.optString("reqPrepayid");
                                    String reqSign = result.optString("reqSign");
                                    String reqTimestamp = result.optString("reqTimestamp");

                                    /*IWXAPI wxapi = WXAPIFactory.createWXAPI(CommonPayActivity.this, ApiConfig.WX_APPID, false);
                                    // 将该app注册到微信
                                    wxapi.registerApp(ApiConfig.WX_APPID);

                                    PayReq req = new PayReq();
                                    req.appId = reqAppid;
                                    req.partnerId = reqPartnerid;
                                    req.prepayId = reqPrepayid;
                                    req.nonceStr = reqNoncestr;
                                    req.timeStamp = reqTimestamp;
                                    req.packageValue = reqPackage;
                                    req.sign = reqSign;

                                    wxapi.sendReq(req);*/
                                }
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.execute();
    }

    private void getAlipayOrder() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {


                try {
                    JSONObject order = new JSONObject();
                    order.put("device", "Android");
                    order.put("subject", mStrPuopose);
//                            order.put("amount", mMoney);
                    order.put("amount", "0.01");
                    order.put("orderId", mOrderNum);
                    order.put("userId", LoginUtil.getUserId(CommonPayActivity.this));

                    byte[] bytes = mRSAUtil.encryptByPublicKeyForSpilt(order.toString().getBytes(), mPublicKey);
                    String orderStr = Base64.encodeToString(bytes, Base64.NO_WRAP);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("alipayOrder", orderStr);
                    jsonObject.put("type", 1);

                    String url = PAY_SERVER + "/payService/alipay/createAlipayOrder";

                    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

                    String postStringBody = jsonObject.toString();
                    RequestBody stringRequestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postStringBody);
                    Request stringRequest = new Request
                            .Builder()
                            .url(url)
                            .post(stringRequestBody)
                            .build();
                    Call call = okHttpClient.newCall(stringRequest);

                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                mProgressDialog.dismiss();

                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject response = new JSONObject(s);
                        int resultCode = response.optInt("resultCode");
                        switch (resultCode) {
                            case 0:
                                JSONObject result = response.optJSONObject("result");
                                String msg = response.optString("msg", "");
                                if (result != null) {
                                    AlipayOrder alipayOrder = new AlipayOrder();
                                    alipayOrder.setOrderStr(result.optString("orderStr"));
                                    alipayOrder.setSign(result.optString("sign"));

                                    alipay(alipayOrder);
                                } else if (!TextUtils.isEmpty(msg)) {
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case -1:
                                Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
                                break;
                            case -999:
                                Toast.makeText(getApplicationContext(), "系统错误", Toast.LENGTH_SHORT).show();
                                break;
                            case -3:
                                Toast.makeText(getApplicationContext(), "缺少参数", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.execute();
    }

    private void alipay(final AlipayOrder order) {

        new AsyncTask<Void, Void, Map<String, String>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgressDialog.show();
            }

            @Override
            protected Map<String, String> doInBackground(Void... params) {

                /*PayTask alipay = new PayTask(CommonPayActivity.this);
                String orderParam = order.getOrderStr();
                String sign = order.getSign();
                String orderInfo = orderParam + "&" + sign;

                return alipay.payV2(orderInfo, true);*/
                return null;
            }

            @Override
            protected void onPostExecute(Map<String, String> payResultMap) {
                super.onPostExecute(payResultMap);

                mProgressDialog.dismiss();

                try {

                    String resultStatus = payResultMap.get("resultStatus");
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //支付成功
                        String result = payResultMap.get("result");
                        Log.e(TAG, "支付宝支付结果>" + result);

                        JSONObject json = new JSONObject(result);

                        JSONObject alipay_trade_app_pay_response = json.optJSONObject("alipay_trade_app_pay_response");

                        String trade_no = alipay_trade_app_pay_response.optString("trade_no");
                        String out_trade_no = alipay_trade_app_pay_response.optString("out_trade_no");

                        queryAndUpdateOrderStatus(trade_no, out_trade_no);
                    } else if (TextUtils.equals(resultStatus, "4000")) {
                        //订单支付失败
                        Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        //用户中途取消
                        Toast.makeText(getApplicationContext(), "取消支付", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.equals(resultStatus, "6002")) {
                        //网络连接出错
                        Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    /**
     * 支付宝支付成功回调
     */
    private void queryAndUpdateOrderStatus(final String trade_no, final String out_trade_no) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    JSONObject content = new JSONObject();
                    content.put("orderId", out_trade_no);
                    content.put("tradeNo", trade_no);

                    byte[] bytes = mRSAUtil.encryptByPublicKeyForSpilt(content.toString().getBytes(), mPublicKey);
                    String contentStr = Base64.encodeToString(bytes, Base64.NO_WRAP);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("alipayOrder", contentStr);
                    jsonObject.put("type", 1);

                    String url = PAY_SERVER + "/payService/alipay/queryAndUpdateOrderStatus";

                    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

                    String postStringBody = jsonObject.toString();
                    RequestBody stringRequestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postStringBody);
                    final Request stringRequest = new Request
                            .Builder()
                            .url(url)
                            .post(stringRequestBody)
                            .build();

                    Call call = okHttpClient.newCall(stringRequest);
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                mProgressDialog.dismiss();

                if (!TextUtils.isEmpty(s)) {
                    Log.e(TAG, "支付宝成功回调>" + s);
                }

                Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();

                paySuccessCallback();
            }
        }.execute();
    }

    /**
     * 微信支付成功回调
     */
    private void wxpayCallback(final String orderId) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    JSONObject content = new JSONObject();
                    content.put("orderId", orderId);

                    byte[] bytes = mRSAUtil.encryptByPublicKeyForSpilt(content.toString().getBytes(), mPublicKey);
                    String contentStr = Base64.encodeToString(bytes, Base64.NO_WRAP);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("wxpayOrder", contentStr);
                    jsonObject.put("type", 1);

                    String url = PAY_SERVER + "/payService/wx/payCallBack";

                    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

                    String postStringBody = jsonObject.toString();
                    RequestBody stringRequestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postStringBody);
                    final Request stringRequest = new Request
                            .Builder()
                            .url(url)
                            .post(stringRequestBody)
                            .build();

                    Call call = okHttpClient.newCall(stringRequest);
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                mProgressDialog.dismiss();

                if (!TextUtils.isEmpty(s)) {
                    Log.e(TAG, "微信成功回调>" + s);
                }

                Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();

                paySuccessCallback();
            }
        }.execute();
    }

    /**
     * 支付成功后，页面跳转处理
     */
    private void paySuccessCallback() {

        Intent intent = new Intent(CommonPayActivity.this, QztMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxpayEvent(WxpayEvent event) {

        switch (event.getStatus()) {
            case 0:
                wxpayCallback(mOrderNum);
                break;
            case -1:
                Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
                break;
            case -2:
                Toast.makeText(getApplicationContext(), "取消支付", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
