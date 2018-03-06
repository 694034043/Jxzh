package com.bocop.zyt.bocop.jxplatform.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.boc.bocop.sdk.util.AccessTokenKeeper;
import com.boc.bocop.sdk.util.Oauth2AccessToken;
import com.boc.bocop.sdk.util.StringUtil;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.BocOpUtilForSA0015;
import com.bocop.zyt.bocop.jxplatform.util.CustomInfo;
import com.bocop.zyt.bocop.jxplatform.util.JsonUtils;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.frg.FrgFunsSelect;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.google.gson.Gson;
import com.mdx.framework.activity.IndexAct;
import com.mdx.framework.activity.MFragment;
import com.mdx.framework.activity.NoTitleAct;
import com.mdx.framework.utility.Helper;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends MFragment {

	private  Editor editor;
	private BaseApplication app;
	

	@Override
	protected void create(Bundle bundle) {
		setContentView(R.layout.activity_splash);
		app = (BaseApplication) getActivity().getApplication();
		isOauthToken();
	}

	private void isOauthToken() {
		String token = getActivity().getIntent().getStringExtra("accesstoken");
		String refreshtoken = getActivity().getIntent().getStringExtra("refreshtoken");
		long expiresTime = getActivity().getIntent().getLongExtra("expiresTime", 0);
		String user = getActivity().getIntent().getStringExtra("user");

		/*
		 * 目前判断了token值是否为空，使用时可根据需求增加其他判断条件
		 */
		if (null != token) {
			Editor editor;
			SharedPreferences sp = getActivity().getSharedPreferences(LoginUtil.SP_NAME, Context.MODE_PRIVATE);
			editor = sp.edit();
			Oauth2AccessToken accessToken = new Oauth2AccessToken();
			accessToken.setToken(token);
			accessToken.setRefreshToken(refreshtoken);
			accessToken.setExpiresIn(String.valueOf(expiresTime));
			accessToken.setUserId(user);

			editor.putString(CacheBean.ACCESS_TOKEN, accessToken.getToken());
			editor.putString(CacheBean.USER_ID, accessToken.getUserId());
			editor.putString(CacheBean.REFRESH_TOKEN, accessToken.getRefreshToken());
			editor.commit();

			/*
			 * 保存传入的token等值，跳转到对应的下一页面
			 */
			AccessTokenKeeper.keepAccessToken(getContext(), accessToken);
			initView();

		} else {
			initView();
		}
	}

	private void initView() {
		
		if(LoginUtil.getToken(getContext()) != null && LoginUtil.getToken(getContext()).length()>10)
		{
			try{
				requestBocopForRefreshToken();
			}
			catch(Exception e){
				Log.i("tag", "requestBocopForRefreshToken 刷新TOKEN发生异常");
			}
		}


		ImageView logoView = (ImageView) this.findViewById(R.id.slash_img);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(3000);
		logoView.startAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				Log.i("tag", "onAnimationStart");
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				Log.i("tag", "onAnimationRepeat");
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Log.i("tag", "onAnimationEnd");
				
//				Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
//				startActivity(intent);
				//FunsSelectAct.startAct(SplashActivity.this);
				login();
				SplashActivity.this.finish();
//				if (LoginUtil.isLog(SplashActivity.this)) {                            	                            	
//            		if (LockPatternUtils.savedPatternExists()) {
//                        //输入手势密码
//                        Intent intent1 = new Intent();
//                        intent1.putExtra("wayid", "wayid");
//                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//                        intent1.setClass(SplashActivity.this, UnlockGesturePasswordActivity.class);
//                        startActivity(intent1);                        
//                    }
//            	}	
				
//				Log.i("tag", "onAnimationEnd");
//				if(LocusPassWordUtil.getHandPassword(getApplicationContext())!=false){
//					Intent intent = new Intent(SplashActivity.this, GuideGesturePasswordActivity.class);
//					intent.putExtra("activityName", "com.bocop.jxplatform.activity.MainActivity");
//					startActivity(intent);
//					SplashActivity.this.finish();
//				}else{
//					Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//					startActivity(intent);
//					SplashActivity.this.finish();
//				}
				
			}
		});
	}
	
	// *******************yuxinhan4-5
		public void login() {
			if (!LoginUtil.isLog(getContext()) && StringUtil.isNullOrEmpty(getTel())) {
//				Intent intent = new Intent(getContext(), InformalLoginActivity.class);
//				startActivity(intent);
				Helper.startActivity(getContext(),InformalLoginActivity.class, NoTitleAct.class);
				app.isShowShortTimeLogin = true;
			}else{
//				FunsSelectAct.startAct(getContext());
				Helper.startActivity(getContext(), FrgFunsSelect.class, IndexAct.class);
			}

		}
		
		public String getTel() {
			SharedPreferences sp = getActivity().getSharedPreferences(LoginUtil.SP_NAME, Context.MODE_PRIVATE);
			Editor editor = sp.edit();

			String userTel = sp.getString(CacheBean.USER_TEL_LOGIN, "");

			// String token = sp.getString(CacheBean.ACCESS_TOKEN, "");
			if (userTel != null && !"".equals(userTel)) {
				return userTel;
			}
			return "";
		}
	
	private void requestBocopForRefreshToken() {
		BocOpUtilForSA0015 bocOpUtil = new BocOpUtilForSA0015(getContext());
		Log.i("tag", "access_token:" + LoginUtil.getToken(getContext()));
		SharedPreferences sp = getActivity().getSharedPreferences(
				LoginUtil.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("client_id", BocSdkConfig.CONSUMER_KEY);
		// map.put("client_secret", BocSdkConfig.CONSUMER_SECRET);
		// map.put("refresh_token", LoginUtil.getRefreshToken(getActivity()));
		// map.put("grant_type", "1");
		Log.i("tag", "client_id:" + BocSdkConfig.CONSUMER_KEY
				+ "client_secret:" + BocSdkConfig.CONSUMER_SECRET
				+ "refresh_token:" + LoginUtil.getRefreshToken(getContext())
				+ "grant_type:" + "1");
		final String strGson = gson.toJson(map);
		Log.i("tag", "postOpboc");
		String url = TransactionValue.SA0015
				+ "?grant_type=refresh_token&client_id="
				+ BocSdkConfig.CONSUMER_KEY + "&client_secret="
				+ BocSdkConfig.CONSUMER_SECRET + "&refresh_token="
				+ LoginUtil.getRefreshToken(getContext());
		Log.i("tag", "url:" + url);
		bocOpUtil.postOpboc(strGson, url, new BocOpUtilForSA0015.CallBackBoc3() {
			@Override
			public void onSuccess(String responStr) {
				Log.i("tag", responStr);
				try {
					Map<String, String> mapRec = null;
					mapRec = JsonUtils.getMapStr(responStr);
					Log.i("tag1", mapRec.toString());
					String access_token = mapRec.get("access_token");
					Log.i("tag0",
							"yuan_Access_token:"
									+ LoginUtil.getToken(getContext()));
					if (access_token != null && access_token.length() > 0) {// 登录成功后将token和useid存储到本地
						editor.putString(CacheBean.ACCESS_TOKEN, access_token);
						editor.commit();
					}
					Log.i("tag0",
							"getAccess_token:"
									+ LoginUtil.getToken(getContext()));
					Log.i("tag0", "Access_token:" + access_token);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFinish() {
				try{
					SharedPreferences sp = getActivity().getSharedPreferences(LoginUtil.SP_NAME, Context.MODE_PRIVATE);
					String userId = sp.getString(CacheBean.USER_ID, "");
					if (!TextUtils.isEmpty(userId)) {
						
						
						//上送日志
						try{
							// 开启推送服务
//							BaseApplication.startGoPush(userId);
							if(!CustomInfo.isExistCustomInfo(getContext())){
								Log.i("LoginUtil", "requestBocopForCustid");
								CustomInfo.requestBocopForCustid(getContext(), false);
							}else{
								Log.i("LoginUtil", "postIdForXms");
								CustomInfo.postIdForXms(getContext());
							}
						}
						catch(Exception ex){
							
						}
					}
				}
				catch(Exception e){
					Log.i("tag", "SplashActivity onFinish 发生异常");
				}
			}

			@Override
			public void onFailure(String responStr) {
			}

			@Override
			public void onStart() {
			}
		});
	}
}
