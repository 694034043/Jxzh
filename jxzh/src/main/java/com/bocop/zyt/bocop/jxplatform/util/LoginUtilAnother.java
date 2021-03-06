package com.bocop.zyt.bocop.jxplatform.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.boc.bocop.sdk.BOCOPPayApi;
import com.boc.bocop.sdk.api.bean.ResponseBean;
import com.boc.bocop.sdk.api.bean.oauth.BOCOPOAuthInfo;
import com.boc.bocop.sdk.api.bean.oauth.RegisterResponse;
import com.boc.bocop.sdk.api.event.ResponseListener;
import com.boc.bocop.sdk.util.Oauth2AccessToken;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.gesture.util.LocusPassWordUtil;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;

public class LoginUtilAnother {

	static CacheBean cacheBean;
	// private static SharedPreferences sp;

	public static final String SP_NAME = "config";
	public static final String PATTERN_NAME = "pattern";
	public static final String TIME_NAME = "time";
	private static Context context;
	private static Editor editor;
	private static ILoginListener myCallback;
	private static int currentPosition;

	public interface ILoginListener {
		void onLogin(int position);

		void onLogin();

		void onCancle();

		void onError();

		void onException();

	}

	public interface ILogoutListener {
		void onLogout();

	}

	private static Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			if (myCallback != null) {
				if (msg.what == 0) {
					Context ctx = (Context) msg.obj;
					
					Log.i("LoginUtil", "isExistCustomInfo");
					if(!CustomInfo.isExistCustomInfo(ctx)){
						Log.i("LoginUtil", "requestBocopForCustid");
						CustomInfo.requestBocopForCustid(ctx, false);
					}else{
						Log.i("LoginUtil", "postIdForXms");
						CustomInfo.postIdForXms(ctx);
					}
					myCallback.onLogin();
				} else if (msg.what == 1) {
					myCallback.onCancle();
				} else if (msg.what == 2) {
					myCallback.onError();
				} else if (msg.what == 3) {
					myCallback.onException();
				} else if (msg.what == 5) {
					myCallback.onLogin(currentPosition);
				} else if (msg.what == 6) {
					Context ctx = (Context) msg.obj;
					
					Log.i("LoginUtil", "isExistCustomInfo");
					if(!CustomInfo.isExistCustomInfo(ctx)){
						Log.i("LoginUtil", "requestBocopForCustid");
						CustomInfo.requestBocopForCustid(ctx, false);
					}else{
						Log.i("LoginUtil", "postIdForXms");
						CustomInfo.postIdForXms(ctx);
					}
					myCallback.onLogin(currentPosition);
				}
			}
		};
	};

	// private static void init(Context cxt) {
	// if (cacheBean == null) {
	// cacheBean = CacheBean.getInstance();
	// }
	// if (editor == null) {
	// sp = cxt.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
	// editor = sp.edit();
	// }
	// }

	/**
	 * ?????????????????????sdk
	 */

	public static void authorize(final Context cxt, ILoginListener myCallback) {
		LoginUtilAnother.myCallback = myCallback;
//		if (LocusPassWordUtil.getHandPassword(cxt)) {
//			success(cxt, 0, true);
//			return;
//		}
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		BOCOPPayApi bocopSDKApi = BOCOPPayApi.getInstance(cxt, BocSdkConfig.CONSUMER_KEY, BocSdkConfig.CONSUMER_SECRET);
		bocopSDKApi.initURLIPPort(cxt, BocSdkConfig.CONSUMER_URL, BocSdkConfig.CONSUMER_PORT, BocSdkConfig.CONSUMER_IS_REGISTER,
				BocSdkConfig.CONSUMER_REGISTER);
		// bocopSDKApi.authorize(context, listener);
		bocopSDKApi.authorize(cxt, new ResponseListener() {
			// bocopSDKApi.authorizeAsr(cxt,true, new ResponseListener() {
			@Override
			public void onException(Exception arg0) {
				mHandler.sendEmptyMessage(3);
			}

			@Override
			public void onError(Error arg0) {
				mHandler.sendEmptyMessage(2);
			}

			@Override
			public void onComplete(ResponseBean response) {
				// com.boc.bocop.sdk.api.bean.oauth.RegisterResponse
				String className = response.getClass().getName();
				if (className.contains("BOCOPOAuthInfo")) {
					BOCOPOAuthInfo info = (BOCOPOAuthInfo) response;
					String access_token = info.getAccess_token();
					String refresh_token = info.getRefresh_token();
					String userid = info.getUserId();
					if (access_token != null && access_token.length() > 0) {// ??????????????????token???useid???????????????
						editor.putString(CacheBean.ACCESS_TOKEN, access_token);
						editor.putString(CacheBean.USER_ID, userid);
						editor.putString(CacheBean.REFRESH_TOKEN, refresh_token);
						editor.commit();
						success(cxt, 0, true);
//						Intent intent = new Intent(cxt, MineSetGesturePwdActivity.class);
//						startActivity(intent);
					}
				} else if (className.contains("RegisterResponse")) {
					RegisterResponse regInfo = (RegisterResponse) response;
					String access_token = regInfo.getAccess_token();
					String userid = regInfo.getUserid();

					editor.putString(CacheBean.ACCESS_TOKEN, access_token);
					editor.putString(CacheBean.USER_ID, userid);
					editor.commit();
					success(cxt, 0, true);
				}
			}

			@Override
			public void onCancel() {
				mHandler.sendEmptyMessage(1);
			}

		});
	}

	protected static void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ?????????????????????sdk
	 */

	public static void authorizeAnother(final Context cxt, ILoginListener myCallback, int position) {

		currentPosition = position;
		LoginUtilAnother.myCallback = myCallback;
//		if (!LocusPassWordUtil.getHandPassword(cxt)) {
//			success(cxt, 5, true);
//			return;
//		}
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		BOCOPPayApi bocopSDKApi = BOCOPPayApi.getInstance(cxt, BocSdkConfig.CONSUMER_KEY, BocSdkConfig.CONSUMER_SECRET);
		bocopSDKApi.initURLIPPort(cxt, BocSdkConfig.CONSUMER_URL, BocSdkConfig.CONSUMER_PORT, BocSdkConfig.CONSUMER_IS_REGISTER,
				BocSdkConfig.CONSUMER_REGISTER);
		// bocopSDKApi.authorize(context, listener);
		bocopSDKApi.authorize(cxt, new ResponseListener() {
			// bocopSDKApi.authorizeAsr(cxt,true, new ResponseListener() {
			@Override
			public void onException(Exception arg0) {
				mHandler.sendEmptyMessage(3);
			}

			@Override
			public void onError(Error arg0) {
				mHandler.sendEmptyMessage(2);
			}

			@Override
			public void onComplete(ResponseBean response) {
				// com.boc.bocop.sdk.api.bean.oauth.RegisterResponse
				String className = response.getClass().getName();
				if (className.contains("BOCOPOAuthInfo")) {
					BOCOPOAuthInfo info = (BOCOPOAuthInfo) response;
					String access_token = info.getAccess_token();
					String refresh_token = info.getRefresh_token();
					String userid = info.getUserId();
					if (access_token != null && access_token.length() > 0) {// ??????????????????token???useid???????????????
						editor.putString(CacheBean.ACCESS_TOKEN, access_token);
						editor.putString(CacheBean.USER_ID, userid);
						editor.putString(CacheBean.REFRESH_TOKEN, refresh_token);
						editor.commit();
						success(cxt, 5, true);
					}
				} else if (className.contains("RegisterResponse")) {
					RegisterResponse regInfo = (RegisterResponse) response;
					String access_token = regInfo.getAccess_token();
					String userid = regInfo.getUserid();

					editor.putString(CacheBean.ACCESS_TOKEN, access_token);
					editor.putString(CacheBean.USER_ID, userid);
					editor.commit();
					success(cxt, 5, true);
				}
			}

			@Override
			public void onCancel() {
				mHandler.sendEmptyMessage(1);
			}

		});
	}

	/**
	 * ????????????
	 */
	private static void success(Context cxt, int what, boolean successed) {
		if (LocusPassWordUtil.getHandPassword(cxt) && successed) {
			mHandler.sendEmptyMessage(what);
		} else {
			context = cxt;
			Message msg = mHandler.obtainMessage();
			msg.obj = cxt;
			msg.what = 6;
			mHandler.sendMessage(msg);
		}
	}

	public static void startHandler(int what) {
		if (what == 0 && what == 5)
			return;
		mHandler.sendEmptyMessage(what);
	}

	// public static void authorize(Context context, ILoginListener myCallback)
	// {
	// // init(context);
	// LoginUtil.myCallback = myCallback;
	// BOCOPPayApi bocopSDKApi = BOCOPPayApi.getInstance(context,
	// BocSdkConfig.CONSUMER_KEY, BocSdkConfig.CONSUMER_SECRET);
	// bocopSDKApi.initURLIPPort(context, BocSdkConfig.CONSUMER_URL,
	// BocSdkConfig.CONSUMER_PORT, BocSdkConfig.CONSUMER_IS_REGISTER,
	// BocSdkConfig.CONSUMER_REGISTER);
	// bocopSDKApi.authorizeAsr(context, true, new ResponseListener() {
	//
	// @Override
	// public void onException(Exception arg0) {
	//
	// }
	//
	// @Override
	// public void onError(Error arg0) {
	//
	// }
	//
	// @Override
	// public void onComplete(ResponseBean response) {
	// Logger.d("testOAuthAsr ????????????" + response.toString());
	// if (response instanceof BOCOPOAuthInfo) {
	// BOCOPOAuthInfo info = (BOCOPOAuthInfo) response;
	// String access_token = info.getAccess_token();
	// String userid = info.getUserId();
	// if (access_token != null && access_token.length() > 0) {
	// LoginUserInfo userInfo = new LoginUserInfo();
	// userInfo.setAccessToken(access_token);
	// userInfo.setUserId(userid);
	// BaseApplication.getInstance().setUserInfo(userInfo);
	// mHandler.sendEmptyMessage(0);
	// }
	//
	// }
	// else if (response instanceof RegisterResponse) { //???????????????????????????
	// RegisterResponse info = (RegisterResponse) response;
	// String access_token = info.getAccess_token();
	// String userid = info.getUserid();
	// if (access_token != null && access_token.length() > 0) {
	// LoginUserInfo userInfo = new LoginUserInfo();
	// userInfo.setAccessToken(access_token);
	// userInfo.setUserId(userid);
	// BaseApplication.getInstance().setUserInfo(userInfo);
	// mHandler.sendEmptyMessage(0);
	// }
	// }
	// }
	//
	// @Override
	// public void onCancel() {
	//
	// }
	// });
	//
	// }

	/**
	 * ?????????????????????
	 * 
	 * 1???????????????,???????????????token???userid?????? 2??????????????????????????????????????? 3?????????????????????????????????
	 */
	public static void showLogoutAppDialog(final Activity cxt, final ILogoutListener myOutCallback) {
		BocopDialog dialog = new BocopDialog(cxt, "??????", "???????????????????????????");
		dialog.setPositiveListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				logout(cxt, myOutCallback);
				dialog.dismiss();
			}
		});
		dialog.setNegativeButton(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}, "??????");
		dialog.show();

		// AlertDialog.Builder builder = new Builder(cxt);
		// builder.setTitle("??????");
		// builder.setMessage("???????????????????????????");
		// builder.setPositiveButton("??????", new DialogInterface.OnClickListener()
		// {
		// public void onClick(DialogInterface dialog, int which) {
		// logout(cxt, myOutCallback);
		// dialog.dismiss();
		// }
		// });
		// builder.setNegativeButton("??????", new DialogInterface.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		// }
		// });
		// if (!(cxt).isFinishing()) {
		// builder.show();
		// }
	}

	/**
	 * ?????????????????????
	 * 
	 * 1???????????????,???????????????token???userid?????? 2???????????????????????????????????????
	 * 
	 */
	public static void logout(Activity cxt, ILogoutListener myOutCallback) {

		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		BOCOPPayApi bocopSDKApi = BOCOPPayApi.getInstance(cxt, BocSdkConfig.CONSUMER_KEY, BocSdkConfig.CONSUMER_SECRET);
		bocopSDKApi.delOAuthorize(cxt);
		editor.putString(CacheBean.ACCESS_TOKEN, null);
		editor.putString(CacheBean.USER_ID, null);
		editor.putString(CacheBean.REFRESH_TOKEN, null);
		editor.commit();
		CacheBean.getInstance().clearCacheMap();
		if (myOutCallback != null) {
			myOutCallback.onLogout();
		}
	}

	public static void logoutWithoutCallback(Activity cxt) {

		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		BOCOPPayApi bocopSDKApi = BOCOPPayApi.getInstance(cxt, BocSdkConfig.CONSUMER_KEY, BocSdkConfig.CONSUMER_SECRET);
		bocopSDKApi.delOAuthorize(cxt);
		editor.putString(CacheBean.ACCESS_TOKEN, null);
		editor.putString(CacheBean.USER_ID, null);
		editor.putString(CacheBean.REFRESH_TOKEN, null);
		editor.commit();
		CacheBean.getInstance().clearCacheMap();
	}

	private static Oauth2AccessToken accessToken = null;

	// public static void logout(Context cxt, ILoginoutListener
	// myOutCallback,final BaseApplication application) {
	//
	// SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
	// Context.MODE_PRIVATE);
	// editor = sp.edit();
	//
	// BOCOPPayApi bocopSDKApi = BOCOPPayApi.getInstance(cxt,
	// BocSdkConfig.CONSUMER_KEY,
	// BocSdkConfig.CONSUMER_SECRET);
	// bocopSDKApi.delOAuthorize(cxt);
	// editor.putString(CacheBean.ACCESS_TOKEN, null);
	// editor.putString(CacheBean.USER_ID, null);
	// editor.commit();
	// CacheBean.getInstance().clearCacheMap();
	// // RequestUtil.resetSession();
	// if (myOutCallback != null) {
	// myOutCallback.onLogout();
	// }

	// LoginUtil.myOutCallback = myOutCallback;
	// // BOCOPPayApi bocopSDKApi = BOCOPPayApi.getInstance(context,
	// BocSdkConfig.CONSUMER_KEY, BocSdkConfig.CONSUMER_SECRET);
	// // bocopSDKApi.delOAuthorize(context);
	// application.setUserInfo(null);
	// mHandler.sendEmptyMessage(1);
	// }

	public static boolean isLog(Context cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		String userId = sp.getString(CacheBean.USER_ID, "");
		String token = sp.getString(CacheBean.ACCESS_TOKEN, "");
		if (userId != null && !"".equals(userId) && token != null && !"".equals(token)) {
			return true;
		}
		return false;
	}

	public static String getUserId(Context cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		String userId = sp.getString(CacheBean.USER_ID, "");
		if (userId != null && !"".equals(userId)) {
			return userId;
		}
		return "";
	}

	public static String getToken(Context cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		String token = sp.getString(CacheBean.ACCESS_TOKEN, "");
		if (token != null && !"".equals(token)) {
			return token;
		}
		return "";
	}

	public static String getRefreshToken(Context cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		String token = sp.getString(CacheBean.REFRESH_TOKEN, "");
		if (token != null && !"".equals(token)) {
			return token;
		}
		return "";
	}

	public static String getPattern(Activity cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Activity.MODE_PRIVATE);
		String userId = sp.getString(CacheBean.USER_ID, "");
		String patternString = "";
		if (userId != null && !"".equals(userId)) {
			patternString = sp.getString(userId + PATTERN_NAME, "");
		}
		return patternString;
	}

	public static void setPattern(Activity cxt, String value) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Activity.MODE_PRIVATE);
		String userId = sp.getString(CacheBean.USER_ID, "");
		if (userId != null && !"".equals(userId)) {
			sp.edit().putString(userId + PATTERN_NAME, value).commit();
		}
	}

	public static long getUserLong(Activity cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Activity.MODE_PRIVATE);
		String userId = sp.getString(CacheBean.USER_ID, "");
		long now = 0;
		if (userId != null && !"".equals(userId)) {
			now = sp.getLong(userId + TIME_NAME, System.currentTimeMillis());
			// Toast.makeText(cxt, userId + "time" + ":" + now,
			// Toast.LENGTH_SHORT).show();
		}
		return now;
	}

	public static void setUserLong(Activity cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Activity.MODE_PRIVATE);
		String userId = sp.getString(CacheBean.USER_ID, "");
		if (userId != null && !"".equals(userId)) {
			sp.edit().putLong(userId + TIME_NAME, System.currentTimeMillis()).commit();
		}
	}

	public static boolean getOnoff(Activity cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Activity.MODE_PRIVATE);
		String userId = sp.getString(CacheBean.USER_ID, "");
		boolean onoff = false;

		if (userId != null && !"".equals(userId)) {
			String patternString = sp.getString(userId + PATTERN_NAME, "");
			if (patternString != null && !"".equals(patternString)) {
				onoff = true;
			}
		}
		return onoff;
	}

	public static void turnOff(Activity cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtilAnother.SP_NAME, Activity.MODE_PRIVATE);
		String userId = sp.getString(CacheBean.USER_ID, "");
		if (userId != null && !"".equals(userId)) {
			sp.edit().remove(userId + PATTERN_NAME).commit();
		}
	}
}
