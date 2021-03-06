package com.bocop.zyt.bocop.jxplatform.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.bocop.sdk.util.StringUtil;
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
public class BMJFActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.ll_network_closed)
	private LinearLayout llNetworkClosed;
	@ViewInject(R.id.btnNetworkClose)
	private Button btnNetworkClose;
	@ViewInject(R.id.lliv_network_closed)
	private LinearLayout llivNetworkClosed;
	@ViewInject(R.id.ll_network_load)
	private LinearLayout llNetworkLoad;
	
	
	@ViewInject(R.id.webView)
	private WebView webView;
	private String newAccessToken;
	private String refreshToken;
	ProgressDialog progressDialog;
	String url;
	String accessToken;
	String userid;
	
	private Boolean loadError ;
	private String title;
	private boolean isShowParamsTitle;

	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadError  = false;
		llNetworkLoad.setVisibility(View.INVISIBLE);
		webView.setVisibility(View.VISIBLE);
		llNetworkClosed.setVisibility(View.INVISIBLE);
		title = getIntent().getStringExtra("title");
		title = StringUtil.isNullOrEmpty(title)?"????????????":title;
		isShowParamsTitle = getIntent().getBooleanExtra("isShowParamsTitle", false);
		userid = LoginUtil.getUserId(BMJFActivity.this);
		getFinances(this);

	}

	
	@OnClick(R.id.iv_back)
	public void back(View v) {
		// ????????????????????????????????????????????????app??????????????????title?????????
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			finish();
		}
	}
	
	@OnClick(R.id.btnNetworkClose)
	public void button(View v) {
		Log.i("tag", "reload");
		llNetworkLoad.setVisibility(View.INVISIBLE);
		webView.setVisibility(View.INVISIBLE);
		llNetworkClosed.setVisibility(View.INVISIBLE);
		llivNetworkClosed.setVisibility(View.INVISIBLE);
		loadError  = false;
		getFinances(this);
//		webView.reload();
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
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	
	

	/**
	 * ?????????webView
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("JavascriptInterface")
	public void initWebView(String accessToken) {
		llNetworkClosed.setVisibility(View.INVISIBLE);
		
		WebSettings ws = webView.getSettings();
		ws.setSavePassword(false);
		ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(false);
		ws.setUseWideViewPort(false);
		ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		ws.setLoadWithOverviewMode(true);
		ws.setSupportZoom(false);
		ws.setAppCacheEnabled(false);
		webView.addJavascriptInterface(this, "nativeApp");
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setSavePassword(false);
		// webView.addJavascriptInterface(new JsInterface(), "nativeApp");
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onLoadResource(WebView view, String url) {
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			/* (non-Javadoc)
			 * @see android.webkit.WebViewClient#onPageStarted(android.webkit.WebView, java.lang.String, android.graphics.Bitmap)
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if (NetworkUtils.isNetworkConnected(BMJFActivity.this)) {
					llNetworkLoad.setVisibility(View.INVISIBLE);
					webView.setVisibility(View.VISIBLE);
					llNetworkClosed.setVisibility(View.INVISIBLE);
					llivNetworkClosed.setVisibility(View.INVISIBLE);
				} else {
					view.stopLoading();
					CustomProgressDialog.showBocNetworkSetDialog(BMJFActivity.this);
					if(webView.canGoBack()){
						webView.goBack();
					}
				}
				
			}
			
			/* (non-Javadoc)
			 * @see android.webkit.WebViewClient#onPageFinished(android.webkit.WebView, java.lang.String)
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				 if (!loadError) {//??????????????????????????????????????????????????????
					 	llNetworkLoad.setVisibility(View.INVISIBLE);//?????????????????????????????????????????????????????????????????????????????????????????????WebView
					 	webView.setVisibility(View.VISIBLE);
					 	llNetworkClosed.setVisibility(View.INVISIBLE);
					 	llivNetworkClosed.setVisibility(View.INVISIBLE);
		            } else { //????????????????????????????????????????????????????????????????????????????????????????????????
		            	llNetworkLoad.setVisibility(View.INVISIBLE);//?????????????????????????????????????????????????????????????????????????????????????????????WebView
		            	webView.setVisibility(View.INVISIBLE);
					 	llNetworkClosed.setVisibility(View.VISIBLE);
					 	llivNetworkClosed.setVisibility(View.VISIBLE);
		            }
			}
			
			/* (non-Javadoc)
			 * @see android.webkit.WebViewClient#onPageFinished(android.webkit.WebView, java.lang.String)
			 */
			@SuppressWarnings("deprecation")
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				Log.i("tag", "errorCode:" + errorCode + "description:" + description + "failingUrl:" + failingUrl);
				loadError = true;
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			// ????????????title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				if(isShowParamsTitle){
					tv_titleName.setText(BMJFActivity.this.title);
				}else{
					tv_titleName.setText(title);
					if(!TextUtils.isEmpty(title)&&(title.toLowerCase().contains("error") || title.toLowerCase().contains("??????") || title.toLowerCase().contains("??????"))){
						Log.i("tag88", title);
						loadError = true;
					}					
				}
			}

			// ??????????????????????????????
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				BMJFActivity.this.getWindow().setFeatureInt(
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
		System.out.println("??????======" + userid);
		/**
		 * ????????????
		 */
		url = "http://219.141.191.126:80/conPayH5/?channel=jxeht" + "&userId="
				+ userid + "&accessToken=" + accessToken;
		Log.i("accessToken", accessToken);
		webView.loadUrl(url);

	}

	public void getFinances(final Context context) {
		RestTemplateJxBank restTemplate = new RestTemplateJxBank(context);
		JsonRequestParams params = new JsonRequestParams();
		String action = LoginUtil.getToken(context);
		String userid = LoginUtil.getUserId(context);
		Log.i("action", action);
		Log.i("userid", userid);
		// 11-14 14:20:20.832: I/System.out(10366):
		// //
		// https://openapi.boc.cn/bocop/oauth/token?acton=6eb33fd1-c8ee-44f4-9381-08cd5d4fc98e&client_id=205&password=&grant_type=implicit&user_id=arekas&enctyp=
		params.put("enctyp", "");
		params.put("password", "");
		params.put("grant_type", "implicit");
		params.put("user_id", userid);
		// params.put("client_secret", MainActivity.CONSUMER_SECRET);
		params.put("client_id", "205");
		Log.i("tag", "205");
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
							accessToken = newAccessToken;
							Log.i("taghuan", refreshToken);
							Log.i("taghuan", newAccessToken);
							Log.i("taghuan", accessToken);
							initWebView(accessToken);
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
