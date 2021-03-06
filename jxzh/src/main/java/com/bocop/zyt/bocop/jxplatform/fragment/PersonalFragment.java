package com.bocop.zyt.bocop.jxplatform.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.FindPasswordActivity;
import com.bocop.zyt.bocop.jxplatform.activity.way.pattern.GuideGesturePasswordActivity;
import com.bocop.zyt.bocop.jxplatform.activity.way.pattern.UnlockGesturePasswordActivity;
import com.bocop.zyt.bocop.jxplatform.adapter.PerFunctionAdapter;
import com.bocop.zyt.bocop.jxplatform.bean.PerFunctionBean;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.trafficassistant.AbountActivity;
import com.bocop.zyt.bocop.jxplatform.trafficassistant.BocOpWebActivity;
import com.bocop.zyt.bocop.jxplatform.trafficassistant.DownloadService;
import com.bocop.zyt.bocop.jxplatform.trafficassistant.Help;
import com.bocop.zyt.bocop.jxplatform.util.BocOpUtilVersion;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.JsonUtils;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.view.MyListView;
import com.bocop.zyt.frg.BaseFrg;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.google.gson.Gson;
import com.mdx.framework.Frame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import sharesdk.onekeyshare.OnekeyShare;

public class PersonalFragment extends BaseFrg implements LoginUtil.ILoginListener,
        LoginUtil.ILogoutListener {

    private MyListView listView;
    private MyListView listView2;
    private MyListView listView3;
    private ImageView iv_login;
    private ImageView iv_finish;
    private TextView tv_login;
    private RelativeLayout rl_logout;
    private TextView tv_logout;

    private List<PerFunctionBean> funcList;
    private List<PerFunctionBean> funcList2;
    private List<PerFunctionBean> funcList3;
    private PerFunctionAdapter adapter;
    private PerFunctionAdapter adapter2;
    private PerFunctionAdapter adapter3;

    private String appUrl; // ??????
    private String isNeedUpdate; // ????????????
    private String version; // ?????????
    private String updateContent; // ????????????
    private CacheBean cacheBean;

    /**
     * service
     */
    private boolean isBinded;
    private boolean isDestroy = true;
    //	private DownloadBinder binder;
    protected Context Context;
    private TextView tv_actionbar_title;

    protected BaseApplication appBean;

    @Override
    protected void create(Bundle bundle) {
        setContentView(R.layout.fragment_personal);
        cacheBean = ((BaseApplication) getActivity().getApplication()).getCacheBean();
        init();
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (LoginUtil.isLog(getContext())) {
            tv_login.setText(LoginUtil.getUserId(getContext()));
            iv_login.setImageResource(R.drawable.pc_ic_head_login);
            rl_logout.setVisibility(View.VISIBLE);
        } else {
            iv_login.setImageResource(R.drawable.pc_ic_head_logout);
            tv_login.setText("??????");
            cacheBean.setUserInfo(null);
            rl_logout.setVisibility(View.GONE);
        }
        // Log.i("tag", "onResume");
    }


    private void init() {


        listView = (MyListView) findViewById(R.id.listView);
        listView2 = (MyListView) findViewById(R.id.listView2);
        listView3 = (MyListView) findViewById(R.id.listView3);
        iv_login = (ImageView) findViewById(R.id.iv_login);
        tv_login = (TextView) findViewById(R.id.tv_login);
        rl_logout = (RelativeLayout) findViewById(R.id.rl_logout);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        iv_finish = (ImageView) findViewById(R.id.iv_finish);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginUtil.isLog(getContext())) {
                    return;
                } else {
                    LoginUtil.authorize(getActivity(), PersonalFragment.this);
                }
            }
        });

        iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginUtil.isLog(getContext())) {
                    return;
                } else {
                    LoginUtil.authorize(getActivity(), PersonalFragment.this);
                }
            }
        });

        System.out.println("init...........personal");
        funcList = new ArrayList<PerFunctionBean>();
        funcList2 = new ArrayList<PerFunctionBean>();
        funcList3 = new ArrayList<PerFunctionBean>();

        // ???????????????
        PerFunctionBean user = new PerFunctionBean();
        user.setTitle("????????????");
        user.setImageRes(R.drawable.register);
        user.setFunctionId("myVersion");
        funcList.add(user);

        // ???????????????
        PerFunctionBean yhkgl = new PerFunctionBean();
        yhkgl.setTitle(getString(R.string.lv_cardmanager));
        yhkgl.setImageRes(R.drawable.ic1);
        yhkgl.setFunctionId("yhkgl");
        funcList.add(yhkgl);

        // ????????????
        PerFunctionBean mmgl = new PerFunctionBean();
//				getString(R.string.lv_userpassword), R.drawable.ic2, "mmgl");
        mmgl.setTitle(getString(R.string.lv_userpassword));
        mmgl.setImageRes(R.drawable.pc_icon_mmgl);
        mmgl.setFunctionId("mmgl");
        funcList.add(mmgl);

        // ????????????
        PerFunctionBean ssgl = new PerFunctionBean();
        ssgl.setTitle("????????????");
        ssgl.setImageRes(R.drawable.icon_ssgl);
        ssgl.setFunctionId("ssgl");
        funcList2.add(ssgl);

        // ????????????
        PerFunctionBean zhmm = new PerFunctionBean();
        zhmm.setTitle("????????????");
        zhmm.setImageRes(R.drawable.icon_zhmm);
        zhmm.setFunctionId("zhmm");
        funcList2.add(zhmm);

        // ????????????
        PerFunctionBean yjfk = new PerFunctionBean();
        yjfk.setTitle("????????????");
        yjfk.setImageRes(R.drawable.icon_yjfk);
        yjfk.setFunctionId("yjfk");
        funcList2.add(yjfk);

        // ????????????
        PerFunctionBean help = new PerFunctionBean();
        help.setTitle("????????????");
        help.setImageRes(R.drawable.ic3);
        help.setFunctionId("myVersion");
        funcList2.add(help);

        // ????????????
        PerFunctionBean share = new PerFunctionBean();
        share.setTitle("????????????");
        share.setImageRes(R.drawable.ic4);
        share.setFunctionId("myVersion");
        funcList.add(share);

        // ??????
        PerFunctionBean about = new PerFunctionBean();
        about.setTitle("??????");
        about.setImageRes(R.drawable.ic8);
        about.setFunctionId("myVersion");
        funcList2.add(about);

        adapter = new PerFunctionAdapter(funcList, getContext());
        listView.setAdapter(adapter);

        adapter2 = new PerFunctionAdapter(funcList2, getContext());
        listView2.setAdapter(adapter2);

        adapter3 = new PerFunctionAdapter(funcList3, getContext());
        listView3.setAdapter(adapter3);

        //
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(),
                        BocOpWebActivity.class);
                Bundle bundle = new Bundle();
                if (position == 0) {
                    bundle.putString("title", "????????????");
                    bundle.putString("type", "userregister");
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (position == 3) {
//					bundle.putString("title", "????????????");
//					bundle.putString("type", "mmgl");
//					intent.putExtras(bundle);
//					startActivity(intent);
                    showShare();
                } else if (LoginUtil.isLog(getContext())) {
                    if (position == 1) {
                        bundle.putString("title", "???????????????");
                        bundle.putString("type", "yhkgl");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else if (position == 2) {
                        bundle.putString("title", "????????????");
                        bundle.putString("type", "mmgl");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        //showShare();
                    }
                } else {
                    LoginUtil.authorize(getActivity(), PersonalFragment.this);
                }

            }
        });

        listView2.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == 0) {
                    BaseApplication application = (BaseApplication) getActivity()
                            .getApplication();

                    if (application.isNetStat()) {
                        if (LoginUtil.isLog(getContext())) {
                            //????????????????????????????????????????????????????????????????????????????????????????????????????????????
                            if (BaseApplication.getInstance().getLockPatternUtils().savedPatternExists(LoginUtil.getUserId(getContext()))) {
                                //??????????????????
                                Intent intent = new Intent();
                                intent.putExtra("update", "update");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setClass(getActivity(), UnlockGesturePasswordActivity.class);
                                startActivity(intent);
                            } else {//???????????????????????????????????????????????????????????????
                                Intent intent = new Intent(getContext(), GuideGesturePasswordActivity.class);
                                startActivity(intent);
                            }

                        } else {
                            LoginUtil.authorize(getActivity(), PersonalFragment.this);
                        }
                    } else {
                        CustomProgressDialog.showBocNetworkSetDialog(getContext());
                    }

                }
                if (arg2 == 1) {
//					Toast.makeText(getActivity(), "?????????????????????", Toast.LENGTH_SHORT).show();????????????
                    Intent intent = new Intent(getActivity(), FindPasswordActivity.class); //
                    startActivity(intent);
                }
                if (arg2 == 2) {
                    Uri uri = Uri.parse("mailto:service_boc@163.com");
                    String[] email = {""};
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra(Intent.EXTRA_CC, email);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "?????????????????????:");
                    startActivity(Intent.createChooser(intent, "???????????????????????????"));
                }
                if (arg2 == 3) {
//					Toast.makeText(getActivity(), "?????????????????????", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Help.class); // ??????
                    startActivity(intent);
                }
                if (arg2 == 4) {
                    Intent intent = new Intent(getActivity(), AbountActivity.class); // ??????
                    startActivity(intent);
                }
            }

//			private void sendEmail(Context context) {
//				Intent intent = new Intent();
//				intent.setData(Uri.parse("mailto:"));
//				intent.putExtra(Intent.EXTRA_SUBJECT, "????????????????????????????????????");
//				intent.putExtra(Intent.EXTRA_TEXT, "??????????????????");
//				context.startActivity(intent);
//			}
        });
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frame.HANDLES.close("FrgYhtHome");
                getActivity().finish();
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUtil.showLogoutAppDialog(getActivity(), PersonalFragment.this);
            }
        });
    }

    protected void requestVersionFromBocop() {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("clientid", BocSdkConfig.CONSUMER_KEY);// ??????KEY
        String strGson = gson.toJson(map);
        Log.i("tag", "????????????JSON????????????" + strGson);
        if (strGson != null && strGson.length() > 0) {
            BocOpUtilVersion util = new BocOpUtilVersion(getActivity());
            util.postOpboc(strGson, TransactionValue.SA0000, new BocOpUtilVersion.CallBackBoc() {

                @Override
                public void onSuccess(String responStr) {
                    try {
                        Log.i("tag", responStr);
                        Map<String, String> map;
                        map = JsonUtils.getMapStr(responStr);
                        isNeedUpdate = map.get("need_update") + "";// ??????????????????
                        appUrl = map.get("appurl") + ""; // ????????????
                        version = map.get("version") + "";// ???????????????
                        updateContent = map.get("new_function") + "";// ????????????
                        Log.i("tag", isNeedUpdate + "," + appUrl + "," + version
                                + updateContent);
                        if (isUpdate(BocSdkConfig.APP_VERSION, version)) {
                            showUpdate(); // ?????????????????????
                        } else {
//							Toast.makeText(baseActivity, "?????????????????????",
//							Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onFailure(String responStr) {
                    CspUtil.onFailure(getActivity(), responStr);
                }
            });
        }
    }

    protected void showUpdate() {
        Dialog dialog;
        if ("1".equals(isNeedUpdate)) {
            dialog = new AlertDialog.Builder(getActivity())
                    .setTitle("???????????????")
                    .setMessage(updateContent)
                    // ????????????
                    .setPositiveButton("??????",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // network.getBigFile(activity, appUrl,
                                    // Environment.getExternalStorageDirectory()
                                    // + BaseValue.APK_DIR,
                                    // BaseValue.APK_NAME, downLoadHandler);
                                    dialog.cancel();
                                }
                            }).create();
            // ???????????????
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } else {// ????????????
            dialog = new AlertDialog.Builder(getActivity())
                    .setTitle("???????????????")
                    .setMessage(updateContent)
                    .setPositiveButton("????????????",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                }
                            })
                    .setNeutralButton("????????????",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent updateIntent = new Intent(getActivity(), DownloadService.class);
                                    updateIntent.putExtra("url", appUrl);
                                    getActivity().startService(updateIntent);
                                    Log.i("tag", "startService");
//									getActivity().bindService(updateIntent, conn, Context.BIND_AUTO_CREATE);
                                    // network.getBigFile(activity,
                                    // SettingMainFragment.this, appUrl,
                                    // Environment.getExternalStorageDirectory()
                                    // + BaseValue.APK_DIR,
                                    // BaseValue.APK_NAME, downLoadHandler);
//									HttpDownloader httpDownLoader = new HttpDownloader();
//									Log.i("tag", "httpDownLoader");
//									appUrl = "http://b.hiphotos.baidu.com/image/pic/item/9e3df8dcd100baa1dcabdd6e4310b912c9fc2e5b.jpg";
//									int result = httpDownLoader.downfile(
//											appUrl, "/jxboca/", "jxbocop.apk");
//									if (result == 0) {
//										Toast.makeText(getActivity(), "???????????????",
//												Toast.LENGTH_SHORT).show();
//									} else if (result == 1) {
//										Toast.makeText(getActivity(), "???????????????",
//												Toast.LENGTH_SHORT).show();
//									} else if (result == -1) {
//										Toast.makeText(getActivity(), "???????????????",
//												Toast.LENGTH_SHORT).show();
//									}
//									dialog.cancel();
                                }
                            }).create();// ??????
            // ???????????????
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param oldVersion
     * @param newVersion
     * @return
     */
    public Boolean isUpdate(String oldVersion, String newVersion) {
        String oldVer = "";
        String newVer = "";
        String oldVerArrary[] = oldVersion.split("\\.");
        String newVerArrary[] = newVersion.split("\\.");
        for (int i = 0; i < oldVerArrary.length; i++) {
            oldVer += oldVerArrary[i];
        }
        for (int j = 0; j < newVerArrary.length; j++) {
            newVer += newVerArrary[j];
        }
        if (Integer.parseInt(newVer) > Integer.parseInt(oldVer)) {
            return true;
        } else {
            return false;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //??????sso??????
        oks.disableSSOWhenAuthorize();

        // ?????????Notification??????????????????  2.5.9?????????????????????????????????
//		oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title???????????????????????????????????????????????????????????????QQ????????????
        oks.setTitle("?????????");
        // titleUrl?????????????????????????????????????????????QQ????????????
//		oks.setTitleUrl("http://www.boc.cn");
        // text???????????????????????????????????????????????????
        oks.setText("?????????");
        // imagePath???????????????????????????Linked-In?????????????????????????????????
        oks.setImagePath("/sdcard/bocjx.png");//??????SDcard????????????????????????
//		oks.setImagePath(getResources().getResourcePackageName(R.drawable.bocjx));
        // url???????????????????????????????????????????????????
        oks.setImageUrl("http://open.boc.cn/appstore/#/app/appDetail/38201");
        oks.setUrl("http://open.boc.cn/appstore/#/app/appDetail/38201");
        // comment???????????????????????????????????????????????????QQ????????????
//		oks.setComment("????????????????????????");
        // site??????????????????????????????????????????QQ????????????
//		oks.setSite(getString(R.string.app_name));
        // siteUrl??????????????????????????????????????????QQ????????????
//		oks.setSiteUrl("http://sharesdk.cn");

        // ????????????GUI
        oks.show(getActivity());
        Log.i("tag", "show");
    }

    @OnClick(value = {R.id.iv_login, R.id.tv_login})
    public void loginClick(View view) {

    }

    @OnClick(R.id.tv_logout)
    public void logoutClick(View view) {
        LoginUtil.showLogoutAppDialog(getActivity(), PersonalFragment.this);
        // LoginUtil.logout(baseActivity, this,baseActivity.getBaseApp());
    }

    @Override
    public void onLogin() {
        iv_login.setImageResource(R.drawable.pc_ic_head_login);
        tv_login.setText(LoginUtil.getUserId(getContext()));
        rl_logout.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "????????????", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancle() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onException() {

    }

    @Override
    public void onLogout() {
        // TODO Auto-generated method stub
        iv_login.setImageResource(R.drawable.pc_ic_head_logout);
        tv_login.setText("??????");
        cacheBean.setUserInfo(null);
        rl_logout.setVisibility(View.GONE);
        Toast.makeText(getContext(), "????????????", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Frame.HANDLES.close("FrgYhtHome");
    }

}
