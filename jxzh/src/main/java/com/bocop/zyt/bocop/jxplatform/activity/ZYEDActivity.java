package com.bocop.zyt.bocop.jxplatform.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.httpUnits.NetworkUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

@ContentView(R.layout.activity_bmjf)
public class ZYEDActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.webView)
	private WebView webView;
	private String newAccessToken;
	private String refreshToken;
	ProgressDialog progressDialog;

	@ViewInject(R.id.ll_network_closed)
	private LinearLayout llNetworkClosed;
	@ViewInject(R.id.ll_network_load)
	private LinearLayout llNetworkLoad;

	private Boolean loadError;

	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadError = false;
		llNetworkLoad.setVisibility(View.INVISIBLE);
		webView.setVisibility(View.VISIBLE);
		llNetworkClosed.setVisibility(View.INVISIBLE);
		getFinances(this);

	}

	// ???????????????????????????
	@JavascriptInterface
	public void phone(String s) {
		Intent myIntentDial = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" + s));
		startActivity(myIntentDial);
	}

	@OnClick(R.id.iv_back)
	public void back(View v) {
		// ????????????????????????????????????????????????app??????????????????title?????????
		if ("??????E???".equals(tv_titleName.getText().toString())
				|| "".equals(tv_titleName.getText().toString())) {
			finish();
		} else {
			// webView.loadUrl("javascript:commBusiness.nativeAppBack();");
			webView.goBack();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ??????????????????????????????Activity???????????????????????????????????????WebView?????????
		// ????????????????????????????????????app????????????????????????????????????
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (webView.canGoBack()) {
				webView.goBack();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@OnClick(R.id.btnNetworkClose)
	public void button(View v) {
		Log.i("tag", "reload");
		llNetworkLoad.setVisibility(View.INVISIBLE);
		webView.setVisibility(View.INVISIBLE);
		llNetworkClosed.setVisibility(View.INVISIBLE);
		loadError = false;
		getFinances(this);
		// webView.reload();
	}

	/**
	 * ?????????webView
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("JavascriptInterface")
	public void initWebView(String accessToken) {
		WebSettings ws = webView.getSettings();
		ws.setSavePassword(false);
		ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(false);
		ws.setUseWideViewPort(false);
		ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		ws.setLoadWithOverviewMode(true);
		ws.setSupportZoom(false);
		webView.addJavascriptInterface(this, "nativeApp");
		// webView.addJavascriptInterface(new JsInterface(), "nativeApp");
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onLoadResource(WebView view, String url) {
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.webkit.WebViewClient#onPageStarted(android.webkit.WebView
			 * , java.lang.String, android.graphics.Bitmap)
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if (NetworkUtils.isNetworkConnected(ZYEDActivity.this)) {
					llNetworkLoad.setVisibility(View.INVISIBLE);
					webView.setVisibility(View.VISIBLE);
					llNetworkClosed.setVisibility(View.INVISIBLE);
				} else {
					view.stopLoading();
					CustomProgressDialog
							.showBocNetworkSetDialog(ZYEDActivity.this);
					if (webView.canGoBack()) {
						webView.goBack();
					}
				}

			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.webkit.WebViewClient#onPageFinished(android.webkit.WebView
			 * , java.lang.String)
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				if (!loadError) {// ??????????????????????????????????????????????????????
					llNetworkLoad.setVisibility(View.INVISIBLE);// ?????????????????????????????????????????????????????????????????????????????????????????????WebView
					webView.setVisibility(View.VISIBLE);
					llNetworkClosed.setVisibility(View.INVISIBLE);
				} else { // ????????????????????????????????????????????????????????????????????????????????????????????????
					llNetworkLoad.setVisibility(View.INVISIBLE);// ?????????????????????????????????????????????????????????????????????????????????????????????WebView
					webView.setVisibility(View.INVISIBLE);
					llNetworkClosed.setVisibility(View.VISIBLE);
				}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.webkit.WebViewClient#onPageFinished(android.webkit.WebView
			 * , java.lang.String)
			 */
			@SuppressWarnings("deprecation")
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				Log.i("tag", "errorCode:" + errorCode + "description:"
						+ description + "failingUrl:" + failingUrl);
				loadError = true;
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			// ????????????title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				tv_titleName.setText(title);
				if (!TextUtils.isEmpty(title)
						&& (title.toLowerCase().contains("error")
								|| title.toLowerCase().contains("??????") || title
								.toLowerCase().contains("??????"))) {
					Log.i("tag88", title);
					loadError = true;
				}
			}

			// ??????????????????????????????
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				ZYEDActivity.this.getWindow().setFeatureInt(
						Window.FEATURE_PROGRESS, newProgress);
				super.onProgressChanged(view, newProgress);
			}

			// ??????javascript??????alert
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}
		});
		// String token =
		// ContentUtils.getSharePreStr(PersonjiaofeiActivity.this,
		// Constants.SHARED_PREFERENCE_NAME, Constants.ACCESS_TOKEN);
		String userid = LoginUtil.getUserId(ZYEDActivity.this);
		System.out.println("??????======" + userid);
		/**
		 * ????????????
		 */
		webView.loadUrl("http://219.141.191.126:80/FM/index.jsp#/yd/" + userid
				+ "/" + accessToken + "/jxeht/196");
		
		

		// webView.loadUrl("http://219.141.191.126:80/conPayH5/?channel=jxeht"
		// + "&userId=" + userid + "&accessToken=" + accessToken);
		// http://219.141.191.126:80/FM/index.jsp#/yd/userId/accessToken/channel/key

	}

	public void getFinances(final Context context) {
		RestTemplateJxBank restTemplate = new RestTemplateJxBank(context);
		JsonRequestParams params = new JsonRequestParams();
		String action = LoginUtil.getToken(context);
		String userid = LoginUtil.getUserId(context);
		// 11-14 14:20:20.832: I/System.out(10366):
		// //
		// https://openapi.boc.cn/bocop/oauth/token?acton=6eb33fd1-c8ee-44f4-9381-08cd5d4fc98e&client_id=205&password=&grant_type=implicit&user_id=arekas&enctyp=
		params.put("enctyp", "");
		params.put("password", "");
		params.put("grant_type", "implicit");
		params.put("user_id", userid);
		// params.put("client_secret", MainActivity.CONSUMER_SECRET);
		params.put("client_id", "196");
		// params.put("client_id", "357");
		params.put("acton", action);
		// https://openapi.boc.cn/bocop/oauth/token
		restTemplate.postGetType("https://openapi.boc.cn/oauth/token", params,
				new JsonHttpResponseHandler("UTF-8") {
					@Override
					public void onStart() {
						super.onStart();
						progressDialog = new ProgressDialog(context);
						progressDialog.setMessage("?????????????????????...");
						progressDialog.setCanceledOnTouchOutside(false);
						progressDialog.show();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						progressDialog.dismiss();
						System.out.println("????????????===========???"
								+ response.toString());
						try {
							newAccessToken = response.getString("access_token");
							refreshToken = response.getString("refresh_token");
						} catch (JSONException e) {
							newAccessToken = "";
							refreshToken = "";
							e.printStackTrace();
						} finally {
							initWebView(newAccessToken);
							// initWebView();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						llNetworkClosed.setVisibility(View.VISIBLE);
					}
				});
	}

}
