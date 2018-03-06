package com.bocop.zyt.bocop.jxplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.F;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.gesture.util.StringUtil;
import com.bocop.zyt.bocop.jxplatform.trafficassistant.BocOpWebActivity;
import com.bocop.zyt.bocop.jxplatform.util.BocOpUtil;
import com.bocop.zyt.bocop.jxplatform.util.BocOpUtilWithoutDia;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.QztRequestWithJsonAndHead;
import com.bocop.zyt.bocop.jxplatform.util.RegularCheck;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.bocop.jxplatform.view.MyProgressBar;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.model.InviteCodeBean;
import com.bocop.zyt.fmodule.utils.IHttpClient;
import com.bocop.zyt.frg.FrgFunsSelect;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdx.framework.activity.IndexAct;
import com.mdx.framework.activity.MFragment;
import com.mdx.framework.utility.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.bocop.zyt.R.id.tv_invite_code;


public class InformalLoginActivity extends MFragment implements OnClickListener, LoginUtil.ILoginListener {

    private TextView tv_titleName;
    private BackButton backBtn;

    EditText edlicensenum;
    TextView spsplicensetype;
    EditText edvehiclenum;
    EditText edTel;
    private EditText input = null;
    private Gson gson = new Gson();
    EditText edTelVerifyCode;

    TextView tvSendMsg;
    private LinearLayout lltSendMsg; // 点击获取验证码
    RelativeLayout rltLoading;
    private Thread thread;

    /**
     * 短信状态
     */
    private static final int LAST_TIME = 1;
    private static final int LESS_TIME = 2;
    private static final int FINISH_TIME = 3;
    private static final int GO_MAIN_VIEW = 4;
    private long startTime;// 开始计时时间
    private long endTime;// 当前时间
    private int currentTime = 59;
    private MyProgressBar myProgressBar;

    Button usrTelAdd;
    String strGson;
    private static Editor editor;

//	@ViewInject(R.id.fomal_login)
//	Button fomalLogin;

    Button signup;

    private long exitTime = 0;
    private EditText etInviteCode;
    private String inviteCode;


    String strTel = "";
    String strTelVerifyCode;
    String strFlag = "0";            //判断是否已经获取验证码
    private boolean stopThread = false;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LAST_TIME:
                    break;
                case LESS_TIME:
                    currentTime--;
                    tvSendMsg.setText(currentTime + "秒后重新获取");
                    tvSendMsg.setTextSize(12);
                    break;
                case FINISH_TIME:
                    tvSendMsg.setText("获取验证码");
                    tvSendMsg.setTextSize(12);
                    lltSendMsg.setClickable(true);
                    lltSendMsg.setBackgroundResource(R.drawable.send_btn_selector);
                    tvSendMsg.setTextColor(getResources().getColor(R.color.white));

                    currentTime = 59;
                    break;
                case GO_MAIN_VIEW:
                    F.setCode(etInviteCode.getText().toString());
                    Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_LONG).show();
//        FunsSelectAct.startAct(getContext());
                    Helper.startActivity(getContext(), FrgFunsSelect.class, IndexAct.class);
                    finish();
                    break;
            }
        }

        ;
    };

    @Override
    protected void create(Bundle bundle) {
        initView();
        tv_titleName.setText("游客体验");
        usrTelAdd.setOnClickListener(this);
//		fomalLogin.setOnClickListener(this);
        signup.setOnClickListener(this);
        lltSendMsg.setOnClickListener(this);
        input = (EditText) findViewById(R.id.tv_addtel);
    }

    private void initView() {
        setContentView(R.layout.activity_informal_login);
        tv_titleName = (TextView) findViewById(R.id.tv_titleName);
        backBtn = (BackButton) findViewById(R.id.iv_imageLeft);
        edlicensenum = (EditText) findViewById(R.id.edlicensenum_add);
        spsplicensetype = (TextView) findViewById(R.id.splicensetype_add);
        edvehiclenum = (EditText) findViewById(R.id.edvehiclenum_add);
        edTel = (EditText) findViewById(R.id.tv_addtel);
        edTelVerifyCode = (EditText) findViewById(R.id.et_telverify_code);
        tvSendMsg = (TextView) findViewById(R.id.tv_telsend_msg);
        lltSendMsg = (LinearLayout) findViewById(R.id.llt_telsend_msg); // 点击获取验证码
        rltLoading = (RelativeLayout) findViewById(R.id.rlt_telloading);
        usrTelAdd = (Button) findViewById(R.id.TelAdd);
        signup = (Button) findViewById(R.id.sign_up);
        etInviteCode = (EditText) findViewById(tv_invite_code);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(),
                BocOpWebActivity.class);

        switch (v.getId()) {
            case R.id.TelAdd:
                bindCar();
                break;
            case R.id.sign_up:
//				signup();
                break;
            case R.id.llt_telsend_msg:
                strTel = edTel.getText().toString().trim();
                if (!RegularCheck.isMobile(strTel)) {
                    Helper.toast("请输入正确的手机号码", getContext());
                    return;
                }
                strFlag = "1";
                lltSendMsg.setClickable(false);
                requestBocForTelMsg();
                break;
            default:
                break;
        }
        if (LoginUtil.isLog(getContext())) {
            finish();
        }
    }


//	private void signup(){
//		FunsSelectAct.startAct(InformalLoginActivity.this);
//		finish();
//	}


    private void bindCar() {
        strTel = edTel.getText().toString().trim();
        inviteCode = etInviteCode.getText().toString();
        Log.i("tag", strTel);
        strTelVerifyCode = edTelVerifyCode.getText().toString().trim();

        if ("".equals(edTel.getText().toString().trim())) {
            Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (strFlag.equals("0")) {
            Toast.makeText(getContext(), "请获取验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (strTelVerifyCode.length() != 6) {
            Toast.makeText(getContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtil.isNullOrEmpty(inviteCode)) {
            Toast.makeText(getContext(), "请输入邀请码", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean isTrueInviteCode = false;
        checkInviteCode();
    }

    private void checkInviteCode() {
        if (!TextUtils.isEmpty(inviteCode)) {
            final CustomProgressDialog dialog = new CustomProgressDialog(getContext(),
                    "...正在加载...", R.anim.frame);
            dialog.show();
            IHttpClient.getAysnMainThread(IHttpClient.getDefaultHttpClient(), IURL.INVITE_CODE_URL, new HashMap<Object, Object>(), new IHttpClient.Callback() {

                @Override
                public void suc(String ret) {
                    // TODO Auto-generated method stub
                    try {
                        ArrayList<InviteCodeBean> resBeans = gson.fromJson(ret, new TypeToken<ArrayList<InviteCodeBean>>() {
                        }.getType());
                        if (resBeans != null && resBeans.size() > 0) {
                            for (InviteCodeBean bean : resBeans) {
                                if (inviteCode.trim().equals(bean.getInvitationCode())) {
                                    putInviteCode(bean);
                                    //putTel(InformalLoginActivity.this);
                                    //handler.sendEmptyMessage(GO_MAIN_VIEW);
                                    requestBocopForCheckMsg();
                                    return;
                                }
                            }
                        }
                        ToastUtils.show(getContext(), "请输入正确的邀请码", Toast.LENGTH_SHORT);
                    } catch (Exception e) {
                        ToastUtils.show(getContext(), "验证邀请码失败", Toast.LENGTH_SHORT);
                    } finally {
                        dialog.cancel();
                    }
                }

                @Override
                public void fail(String ret) {
                    ToastUtils.show(getContext(), "获取邀请码失败,请检查网络是否通畅", Toast.LENGTH_SHORT);
                    dialog.cancel();
                }
            });
        }
    }

    protected void putInviteCode(InviteCodeBean bean) {
        // TODO Auto-generated method stub
        Editor editor;
        SharedPreferences sp = getActivity().getSharedPreferences(LoginUtil.SP_NAME,
                Context.MODE_PRIVATE);
        editor = sp.edit();
        Log.i("tag", "inviteCode----put--" + inviteCode);
        editor.putString(CacheBean.INVITE_CODE, bean.getInvitationCode());
        editor.putString(CacheBean.INVITE_MODULE_NAME, bean.getAppLogo());
        Log.i("tag", "inviteCode----put--" + inviteCode);
        editor.commit();
    }

    /**
     * 发送验证码
     *
     * @param v
     */
    @OnClick(R.id.llt_telsend_msg)
    public void SendMsg(View v) {
        strTel = edTel.getText().toString().trim();
        if (!RegularCheck.isMobile(strTel)) {
            Toast.makeText(getContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        strFlag = "1";
        lltSendMsg.setClickable(false);
        requestBocForTelMsg();

    }


    //
    private void requestBocForTelMsg() {
        //点击短信发送按钮后的加载框
        rltLoading.setVisibility(View.VISIBLE);
        myProgressBar = new MyProgressBar(getContext(), rltLoading);
        myProgressBar.addView();
        tvSendMsg.setText("正在努力...");
        tvSendMsg.setTextSize(12);
        tvSendMsg.setTextColor(getResources().getColor(R.color.pay_send_msg1));
        BocOpUtilWithoutDia bocOpUtil = new BocOpUtilWithoutDia(getContext());

        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("usrid", "logintest");
        map.put("usrtel", strTel);
        map.put("randtrantype", TransactionValue.messageType);
        final String strGson = gson.toJson(map);

        bocOpUtil.postOpbocNolog(strGson, TransactionValue.SA7114, new BocOpUtilWithoutDia.CallBackBoc2() {
            //					SA0052 sa0052;
            @Override
            public void onSuccess(String responStr) {
                Log.i("tag", responStr);
                Toast.makeText(getContext(), "短信已发送，请查收", Toast.LENGTH_LONG).show();
                tvSendMsg.setText("59秒后重新获取");
                tvSendMsg.setTextSize(12);
                lltSendMsg.setClickable(false);
                handler.sendEmptyMessage(LAST_TIME);
                myProgressBar.removeView();
                rltLoading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "短信验证码已发送！", Toast.LENGTH_SHORT);
                thread  =new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startTime = System.currentTimeMillis();// 获取当前时间
                        while (!stopThread) {
                            endTime = System.currentTimeMillis();// 获取当前时间
                            if ((endTime - startTime) > 1000) {
                                startTime = System.currentTimeMillis();
                                if (currentTime > 0) {
                                    handler.sendEmptyMessage(LESS_TIME);
                                } else {
                                    handler.sendEmptyMessage(FINISH_TIME);
                                    break;
                                }
                            }
                        }
                    }
                });
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(String responStr) {
                Log.i("tag", "cartel onFailure");
                tvSendMsg.setText("获取验证码");
                tvSendMsg.setTextSize(12);
                lltSendMsg.setClickable(true);
                lltSendMsg.setBackgroundResource(R.drawable.send_btn_selector);
                tvSendMsg.setTextColor(getResources().getColor(R.color.white));
                currentTime = 59;
                rltLoading.setVisibility(View.GONE);
                if (responStr.equals("0") || responStr.equals("1")) {
                    Toast.makeText(getContext(), R.string.onFailure, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), responStr, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStart() {
                Log.i("tag", "发送GSON数据：" + strGson);
            }
        });
    }

    /**
     * 验证短信验证码
     */
    private void requestBocopForCheckMsg() {
        // TODO Auto-generated method stub
        Gson gson = new Gson();
//				List<Map<String,String>> list =new ArrayList<Map<String,String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("usrid", "logintest");
        map.put("usrtel", strTel);
        map.put("randtrantype", TransactionValue.messageType);
        map.put("chkcode", strTelVerifyCode);
        final String strGson = gson.toJson(map);

        BocOpUtil bocOpUtil = new BocOpUtil(getContext());
        bocOpUtil.postOpbocNolog(strGson, TransactionValue.SA7115, new BocOpUtil.CallBackBoc() {

            @Override
            public void onSuccess(String responStr) {
                Log.i("tag", responStr);
                putTel(getContext());
                handler.sendEmptyMessage(GO_MAIN_VIEW);
                requestBocopForLogin();
                //requestBocopForUseridQuery();	//用户附加信息查询
//						try {
//							sa0052 = JsonUtils.getObject(responStr, SA0052.class);
//							Log.i("tag0", sa0052.getResult());
//							if(sa0052.getResult().equals("0")){
//								Log.i("tag1", sa0052.getResult() + " 短信验证通过，想CSP发送报文，缴纳罚款");
//								requestBocopForUseridQuery();	//用户附加信息查询
//							}
//							else{
//								Toast.makeText(CarAddActivity.this, "短信验证码输入有误", Toast.LENGTH_LONG).show();
//							}
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
            }

            @Override
            public void onStart() {
                Log.i("tag", "发送GSON数据：" + strGson);
            }

            @Override
            public void onFinish() {
//						requestBocopForUseridQuery();	//用户附加信息查询
                //finish();
            }

            @Override
            public void onFailure(String responStr) {
                CspUtil.onFailure(getContext(), responStr);
            }
        });

    }

    private void requestBocopForLogin() {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        String userTel = edTel.getText().toString().trim();

        map.put("userTel", userTel);
        map.put("appId", BocSdkConfig.CONSUMER_KEY);
//		map.put("cardId", strCardId);
        strGson = gson.toJson(map);
        Log.i("tag", strGson);
        try {
            strGson = URLDecoder.decode(gson.toJson(map), "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        QztRequestWithJsonAndHead qztRequestWithJson = new QztRequestWithJsonAndHead(
                getContext());
        qztRequestWithJson
                .postOpboc(
                        strGson,
                        BocSdkConfig.infomalLogin,
                        new QztRequestWithJsonAndHead.CallBackBoc() {

                            @Override
                            public void onSuccess(String responStr) {
                                Log.i("tag22", responStr);
                                try {
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getContext(),
                                        responStr, Toast.LENGTH_LONG).show();

								/*InformalLoginActivity.this.finish();
                                //FunsSelectAct.startAct(InformalLoginActivity.this);
								Intent intent = new Intent(InformalLoginActivity.this, FunsSelectAct.class);
								startActivity(intent);*/
                                //handler.sendEmptyMessage(GO_MAIN_VIEW);
                                //finish();
                            }

                            @Override
                            public void onStart() {
                                // TODO Auto-generated method stub
                                Log.i("tag", "发送JSON报文" + strGson);
                            }

                            @Override
                            public void onFinish() {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onFailure(String responStr) {
                                Toast.makeText(getContext(),
                                        responStr, Toast.LENGTH_LONG).show();
                                //handler.sendEmptyMessage(GO_MAIN_VIEW);
                            }
                        });
    }


//	    public boolean putTel(Context cxt){
//			SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
//					Context.MODE_PRIVATE);
//			editor = sp.edit();
//			editor.putString(CacheBean.USER_TEL_LOGIN, edTel.getText().toString().trim());
//
//		}

    public void putTel(Context cxt) {
        Editor editor;
        SharedPreferences sp = getActivity().getSharedPreferences(LoginUtil.SP_NAME,
                Context.MODE_PRIVATE);
        editor = sp.edit();
        String userTel = edTel.getText().toString().trim();
        Log.i("tag", "userTel----put--" + userTel);
        editor.putString(CacheBean.USER_TEL_LOGIN, userTel);
        //editor.putString(CacheBean.INVITE_CODE, inviteCode);

        Log.i("tag", "userTel----put--" + userTel);
        editor.commit();
    }


    public String getTel(Context cxt) {
        SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
                Context.MODE_PRIVATE);
        editor = sp.edit();

        String userTel = sp.getString(CacheBean.USER_TEL_LOGIN, "");

//		String token = sp.getString(CacheBean.ACCESS_TOKEN, "");
        if (userTel != null && !"".equals(userTel)
                ) {
            return userTel;
        }
        return "";
    }

//	@Override
//	public boolean dispatchKeyEvent(KeyEvent event) {
//		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//			if (event.getAction() == KeyEvent.ACTION_DOWN
//					&& event.getRepeatCount() == 0) {
//				exitApp();
//			}
//			return true;
//		}
//		return super.dispatchKeyEvent(event);
//	}
//
//	private void exitApp() {
//		if ((System.currentTimeMillis() - exitTime) > 2000) {
//			Toast.makeText(InformalLoginActivity.this, "再按一次退出程序", Toast.LENGTH_LONG)
//					.show();
//			exitTime = System.currentTimeMillis();
//		} else {
//			finish();
//			CacheBean.getInstance().clearCacheMap();
//			// Log.i("tag", "logoutWithoutCallback");
//			// LoginUtil.logoutWithoutCallback(MainActivity.this);
//			// getBaseApp().exit();
////			 System.exit(0);
//		}
//
//	}


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopThread = true;//这样在线程执行run方法就会退出那个循环了
    }

    @Override
    public void onLogin() {
        // TODO Auto-generated method stub
        requestBocopForLogin();
        F.setCode(etInviteCode.getText().toString());
        Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_LONG).show();
//        FunsSelectAct.startAct(getContext());
        Helper.startActivity(getContext(), FrgFunsSelect.class, IndexAct.class);
        finish();
    }

    @Override
    public void onCancle() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onError() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onException() {
        // TODO Auto-generated method stub

    }


}
