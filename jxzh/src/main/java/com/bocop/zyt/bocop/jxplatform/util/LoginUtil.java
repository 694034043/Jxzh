package com.bocop.zyt.bocop.jxplatform.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.gesture.util.LocusPassWordUtil;
import com.bocop.zyt.bocop.xms.push.service.OnlineService;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.common.util.ContentUtils;
import com.bocop.zyt.jx.constants.Constants;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LoginUtil {

	static CacheBean cacheBean;
	// private static SharedPreferences sp;

	public static final String SP_NAME = "config";
	public static final String PATTERN_NAME = "pattern";
	public static final String TIME_NAME = "time";

	private static Editor editor;
	private static ILoginListener myCallback;

	private static HashMap<String, OnMsgServiceOkListener> mListeners = new HashMap<String, OnMsgServiceOkListener>();
	private static final String TAG = "LoginUtil";
	private static Context context;
	public static OnlineService onlineService = null;

	public interface ILoginListener {
		void onLogin();

		void onCancle();

		void onError();


		void onException();

	}

	public interface ILogoutListener {
		void onLogout();

	}

	private static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (myCallback != null) {
				if (msg.what == 0) {
					Context ctx = (Context) msg.obj;
//					SharedPreferences sp = context.getSharedPreferences(
//							LoginUtil.SP_NAME, Context.MODE_PRIVATE);
//					Log.i("tag", "sp");
//					SharedPreferences spcus = context.getSharedPreferences(
//							Constants.CUSTOM_PREFERENCE_NAME, Context.MODE_PRIVATE);
//					Log.i("tag", "spcus");
//					if (sp.getString(CacheBean.CUST_ID, null) == null || spcus.getString(Constants.CUSTOM_CUS_ID, null) == null) {
//						try{
//						requestBocopForCustid(context, false);}
//						catch(Exception ex){
//							Log.i("exception", ex.getMessage());
//						}
//					}else{Log.i("tag33", "no requestBocopForCustid");}
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

					myCallback.onLogin();
//					Intent intent = null;
//					if (LocusPassWordUtil.getHandPassword(context)) {
//						intent = new Intent(context,
//								GuideGesturePasswordActivity.class);
//					} else
//						intent = new Intent(context,
//								CreateGesturePasswordActivity.class);
//					int what = (Integer) msg.obj;
//					intent.putExtra("isSetting", true);
//					intent.putExtra("what", what);
//					intent.putExtra("page", 1);
//					context.startActivity(intent);
				} else if (msg.what == 7) {
					authorize(context, myCallback);
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
		final SharedPreferences sp = cxt.getSharedPreferences(
				LoginUtil.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		LoginUtil.myCallback = myCallback;

		BOCOPPayApi bocopSDKApi = BOCOPPayApi.getInstance(cxt,
				BocSdkConfig.CONSUMER_KEY, BocSdkConfig.CONSUMER_SECRET);
		bocopSDKApi.initURLIPPort(cxt, BocSdkConfig.CONSUMER_URL,
				BocSdkConfig.CONSUMER_PORT, BocSdkConfig.CONSUMER_IS_REGISTER,
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
				Message msg = new Message();
				msg.obj = cxt;
				msg.what = 0;
				if (className.contains("BOCOPOAuthInfo")) {
					BOCOPOAuthInfo info = (BOCOPOAuthInfo) response;
					String access_token = info.getAccess_token();
					String refresh_token = info.getRefresh_token();
					String userid = info.getUserId();
					IApplication.userid = userid;
					if (access_token != null && access_token.length() > 0) {// ??????????????????token???useid???????????????
						editor.putString(CacheBean.ACCESS_TOKEN, access_token);
						editor.putString(CacheBean.USER_ID, userid);
						editor.putString(CacheBean.REFRESH_TOKEN, refresh_token);
						editor.commit();
						success(cxt, 0);

						// Intent startSrv = new Intent(cxt,
						// OnlineService.class);
						// startSrv.putExtra(OnlineService.START_FROM, this
						// .getClass().getName());
						// startSrv.putExtra(OnlineService.NAME_CMD,
						// OnlineService.VALUE_RESET);
						// cxt.startService(startSrv);
						// ??????????????????
						BaseApplication.startGoPush(userid);
						Log.i("tag23", "startGoPush");


						//??????????????????
					}
				} else if (className.contains("RegisterResponse")) {
					RegisterResponse regInfo = (RegisterResponse) response;
					String access_token = regInfo.getAccess_token();
					String userid = regInfo.getUserid();
					IApplication.userid = userid;
					editor.putString(CacheBean.ACCESS_TOKEN, access_token);
					editor.putString(CacheBean.USER_ID, userid);
					editor.commit();
					success(cxt, 0);

					// ??????????????????
					BaseApplication.startGoPush(userid);
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
	private static void success(Context cxt, int what) {
		context = cxt;
		if (LocusPassWordUtil.isShowGesture(cxt)) {
			Log.i("log", "isShowGesture");
			mHandler.sendEmptyMessage(what);
		} else {
			Log.i("log", "no isShowGesture");
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

	/**
	 * ????????????id
	 *
	 * @param cxt
	 */
	public static void requestBocopForCustid(final Context cxt, boolean isShowDialog) {
		requestBocopForCustid(cxt, isShowDialog, null);
	}

	public static void requestBocopForCustid(final Context cxt, boolean isShowDialog,
											 final OnRequestCustCallBack requestCustCallBack) {
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("USRID", getUserId(cxt));
		final String strGson = gson.toJson(map);
		Log.i("taggg", "requestBocopForCustid");

		BocOpUtil bocOpUtil = new BocOpUtil(cxt);
		bocOpUtil.postOpboc(strGson, TransactionValue.SA0053, isShowDialog, new BocOpUtil.CallBackBoc() {

			@Override
			public void onSuccess(String responStr) {
				Log.i("tag", responStr);
				try {

					Map<String, String> map;
					map = JsonUtils.getMapStr(responStr);
					String cusid = map.get("cusid").toString();
					CacheBean.getInstance().put(CacheBean.CUST_ID, cusid);
					editor.putString(CacheBean.CUST_ID, cusid);
					editor.commit();
					// ??????????????????
					Log.i("tag", "start");
					ContentUtils.putSharePre(cxt, Constants.CUSTOM_PREFERENCE_NAME, Constants.CUSTOM_ID_NO,
							map.get("idno"));
					ContentUtils.putSharePre(cxt, Constants.CUSTOM_PREFERENCE_NAME, Constants.CUSTOM_MOBILE_NO,
							map.get("mobileno"));
					ContentUtils.putSharePre(cxt, Constants.CUSTOM_PREFERENCE_NAME, Constants.CUSTOM_USER_NAME,
							map.get("cusname").toString());
					ContentUtils.putSharePre(cxt, Constants.CUSTOM_PREFERENCE_NAME, Constants.CUSTOM_CUS_ID,
							map.get("cusid"));
					ContentUtils.putSharePre(cxt, Constants.CUSTOM_PREFERENCE_NAME, Constants.CUSTOM_FLAG, "1");
					CustomInfo.postIdForXms(cxt);
//					if (requestCustCallBack != null) {
//						Log.i("tag", "onSuccess");
//						requestCustCallBack.onSuccess();
//					}
					Log.i("tag", "??????id???" + cusid);
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					if (requestCustCallBack != null) {
						requestCustCallBack.onSuccess();
					}

				}
			}

			@Override
			public void onStart() {
				Log.i("tag", "??????GSON?????????" + strGson);
			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onFailure(String responStr) {
				CspUtil.onFailure(cxt, responStr);
			}
		});
	}

	public interface OnRequestCustCallBack {
		void onSuccess();
	}

	public static void postIdForXms() {
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		String cardId = ContentUtils.getStringFromSharedPreference(context,
				Constants.SHARED_PREFERENCE_NAME, Constants.CUSTOM_ID_NO);
		String userId = LoginUtil.getUserId(context);
		Log.i("tag22", cardId);
		Log.i("tag22", userId);
		map.put("userId", userId);
		map.put("cardId", cardId);
		final String strGson = gson.toJson(map);
		QztRequestWithJsonAndHead qztRequestWithJsonAndHead = new QztRequestWithJsonAndHead(
				context);
		qztRequestWithJsonAndHead
				.postOpbocNoDialog(
						strGson,
						BocSdkConfig.qztPostForXmsUrl,
						new QztRequestWithJsonAndHead.CallBackBoc() {
							@Override
							public void onSuccess(String responStr) {
								Log.i("tag22", responStr);
								try {
									ContentUtils.putSharePre(context,
											Constants.SHARED_PREFERENCE_NAME,
											Constants.CUSTOM_PUT_ALREADY, "1");
									Log.i("tag22",
											ContentUtils
													.getStringFromSharedPreference(
															context,
															Constants.SHARED_PREFERENCE_NAME,
															Constants.CUSTOM_PUT_ALREADY));
								} catch (Exception e) {
									e.printStackTrace();
								}

							}

							@Override
							public void onStart() {
							}

							@Override
							public void onFailure(String responStr) {
								Log.i("tag33", responStr);
							}

							@Override
							public void onFinish() {
							}
						});
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
	public static void showLogoutAppDialog(final Activity cxt,
										   final ILogoutListener myOutCallback) {
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

		// CspUtil cspUtil = new CspUtil(cxt);
		// RequestBody formBody = new FormEncodingBuilder()
		// .add("method", "delete").add("userId", getUserId(cxt)).build();
		// cspUtil.postCspNoLogin(BocSdkConfig.SECRETARY_DEVICE_PATH, formBody,
		// false, new CallBack() {
		//
		// @Override
		// public void onSuccess(String responStr) {
		// LogUtils.d("delete userId success " + responStr);
		// }
		//
		// @Override
		// public void onFinish() {
		//
		// }
		//
		// @Override
		// public void onFailure(String responStr) {
		// LogUtils.d("delete userId failed " + responStr);
		// }
		// });

		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();
		IApplication.userid = "";
		BOCOPPayApi bocopSDKApi = BOCOPPayApi.getInstance(cxt,
				BocSdkConfig.CONSUMER_KEY, BocSdkConfig.CONSUMER_SECRET);
		bocopSDKApi.delOAuthorize(cxt);
		editor.putString(CacheBean.ACCESS_TOKEN, null);
		editor.putString(CacheBean.USER_ID, null);
		editor.putString(CacheBean.REFRESH_TOKEN, null);
		editor.putString(CacheBean.CUST_ID, null);
		editor.commit();

		try{
			CustomInfo.deleteCustomInfo(cxt);
		}catch (Exception ex){

		}


		// SharedPreferences spf = cxt.getSharedPreferences(
		// OnlineService.SECRETARY_MSG, Context.MODE_PRIVATE);
		// Editor mEditor = spf.edit();
		// mEditor.remove(BocSdkConfig.REGIST_DEVICE_KEY);
		// mEditor.commit();

		// ??????????????????
//		BaseApplication.stopGoPush();
		CacheBean.getInstance().clearCacheMap();
		if (myOutCallback != null) {
			myOutCallback.onLogout();
		}
	}

	public static void logoutWithoutCallback(Context cxt) {



		try{
			SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
					Context.MODE_PRIVATE);
			editor = sp.edit();
//			IApplication.userid = "";
//			Log.i("tag8", sp.getString(CacheBean.CUST_ID,null));
			BOCOPPayApi bocopSDKApi = BOCOPPayApi.getInstance(cxt,
					BocSdkConfig.CONSUMER_KEY, BocSdkConfig.CONSUMER_SECRET);
			bocopSDKApi.delOAuthorize(cxt);
			editor.putString(CacheBean.ACCESS_TOKEN, null);
			editor.putString(CacheBean.USER_ID, null);
			editor.putString(CacheBean.REFRESH_TOKEN, null);
			editor.putString(CacheBean.CUST_ID, null);
//			Log.i("tag8", sp.getString(CacheBean.CUST_ID,null));
			editor.commit();
			CacheBean.getInstance().clearCacheMap();
			//??????????????????
			CustomInfo.deleteCustomInfo(cxt);
		}catch (Exception ex){

		}
	}

	// private static Oauth2AccessToken accessToken = null;
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
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();

		String userId = sp.getString(CacheBean.USER_ID, "");
		String token = sp.getString(CacheBean.ACCESS_TOKEN, "");
		if (userId != null && !"".equals(userId) && token != null
				&& !"".equals(token)) {
			return true;
		}
		return false;
	}

	public static String getUserId(Context cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();

		String userId = sp.getString(CacheBean.USER_ID, "");
		if (userId != null && !"".equals(userId)) {
			return userId;
		}
		return "";
	}

	public static String getToken(Context cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();

		String token = sp.getString(CacheBean.ACCESS_TOKEN, "");
		if (token != null && !"".equals(token)) {
			return token;
		}
		return "";
	}

	public static String getRefreshToken(Context cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();

		String token = sp.getString(CacheBean.REFRESH_TOKEN, "");
		if (token != null && !"".equals(token)) {
			return token;
		}
		return "";
	}

	public static String getPattern(Activity cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		String userId = sp.getString(CacheBean.USER_ID, "");
		String patternString = "";
		if (userId != null && !"".equals(userId)) {
			patternString = sp.getString(userId + PATTERN_NAME, "");
		}
		return patternString;
	}

	public static void setPattern(Activity cxt, String value) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		String userId = sp.getString(CacheBean.USER_ID, "");
		if (userId != null && !"".equals(userId)) {
			sp.edit().putString(userId + PATTERN_NAME, value).commit();
		}
	}

	public static long getUserLong(Activity cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
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
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		String userId = sp.getString(CacheBean.USER_ID, "");
		if (userId != null && !"".equals(userId)) {
			sp.edit().putLong(userId + TIME_NAME, System.currentTimeMillis())
					.commit();
		}
	}

	public static boolean getOnoff(Activity cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
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
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME,
				Context.MODE_PRIVATE);
		String userId = sp.getString(CacheBean.USER_ID, "");
		if (userId != null && !"".equals(userId)) {
			sp.edit().remove(userId + PATTERN_NAME).commit();
		}
	}

	/**
	 * <p>
	 * ??????????????????????????????????????????????????????????????????????????????????????????
	 * </p>
	 * <b><i>???????????????????????????????????????????????????????????????????????????</i></b>
	 *
	 * @param onlineService
	 *            ????????????
	 */
	public static void setOnlineService(OnlineService onlineService) {
		Log.i(TAG, "set OnlineService" + System.currentTimeMillis());
		LoginUtil.onlineService = onlineService;
		for (OnMsgServiceOkListener listener : mListeners.values()) {
			listener.msgServiceOk();
		}
	}

	/**
	 * ?????????????????????????????????????????????
	 *
	 * @param msgServiceOkListener
	 */
	public static void registerMsgService(
			OnMsgServiceOkListener msgServiceOkListener) {
		Log.i(TAG, "??????service??????" + msgServiceOkListener.getMsgListenerName());
		LoginUtil.mListeners.put(msgServiceOkListener.getMsgListenerName(),
				msgServiceOkListener);
	}

	/**
	 * ????????????????????????????????????
	 *
	 * @author hch
	 *
	 */
	public interface OnMsgServiceOkListener {
		/**
		 * ???????????????????????????????????????????????????
		 */
		void msgServiceOk();

		String getMsgListenerName();

	}

	/**
	 * ????????????????????????????????????
	 * @param context
	 * @return
	 */
	public static String getTel(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LoginUtil.SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();

		String userTel = sp.getString(CacheBean.USER_TEL_LOGIN, "");

		// String token = sp.getString(CacheBean.ACCESS_TOKEN, "");
		if (userTel != null && !"".equals(userTel)) {
			return userTel;
		}
		return "";
	}
}
