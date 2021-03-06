package com.bocop.zyt.bocop.jxplatform.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.boc.bocop.sdk.util.StringUtil;
import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

@ContentView(R.layout.activity_webview_com)
public class HuiDuiTongActivity extends BaseActivity {

	@ViewInject(R.id.htzq_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.htzq_webView)
	private WebView webView;
	@ViewInject(R.id.myProgressBar)
	private ProgressBar myProgressBar;
	
	private String newAccessToken;
	private String refreshToken;
	ProgressDialog progressDialog;

	String strNewAccessToken;
	String strRefreshToken;
	private String currentUrl;
	private String flag;
	String url = BocSdkConfig.HUIDUITONG;
	private static final String APP_CACAHE_DIRNAME = "/webcache";

	

	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra("title");
		title = StringUtil.isNullOrEmpty(title)?"?????????":title;
		tv_titleName.setText(title);
		myProgressBar.setVisibility(View.GONE);
		getFinances(this);
	}

	@OnClick(R.id.htzq_back)
	public void back(View v) {
		Log.i("tag", "back");
		// ????????????????????????????????????????????????app??????????????????title?????????
		if (webView.canGoBack()) {
			Log.i("tag", "goBack");
			webView.goBack();
		} else {
			Log.i("tag", "finish");
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			if (webView.canGoBack()) {
				webView.goBack();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ?????????webView
	 */
	@SuppressLint("NewApi")
	public void initWebView(String accessToken) {
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setRenderPriority(RenderPriority.HIGH);// ????????????????????????
		webSettings.setDatabaseEnabled(true);
		webSettings.setDomStorageEnabled(true);
		String dbPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
		webSettings.setDatabasePath(dbPath);
		webSettings.setAllowFileAccess(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setDefaultTextEncodingName("UTF-8");
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		// ??????????????????
		if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
			webSettings.setAllowUniversalAccessFromFileURLs(true);

		if (Build.VERSION.SDK_INT >= 19) {// ?????????????????????
			webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		} else {
			webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}

//		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		

		webView.addJavascriptInterface(new JGHJavascriptInterface(), "app_afs");
		webView.addJavascriptInterface(new ExcePtionJavascriptInterface(), "discoupon");
		

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}
		});
		
		webView.setWebChromeClient(new WebChromeClient() {

			// ??????????????????????????????
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				HuiDuiTongActivity.this.getWindow().setFeatureInt(
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
		String userid = LoginUtil.getUserId(HuiDuiTongActivity.this);
		System.out.println("??????======" + userid);
		/**
		 * ????????????
		 */
		webView.loadUrl(url);

		// webView.loadUrl("http://219.141.191.126:80/conPayH5/?channel=jxeht"
		// + "&userId=" + userid + "&accessToken=" + accessToken);
		// http://219.141.191.126:80/FM/index.jsp#/yd/userId/accessToken/channel/key

	}
	
	public  class JGHJavascriptInterface {
        /**
         * ????????????????????????
         */
        @JavascriptInterface
        public String getUserID() {
        	Log.i("tag",  LoginUtil.getUserId(HuiDuiTongActivity.this));
            return  LoginUtil.getUserId(HuiDuiTongActivity.this);
        }
        /**
         * @return access_token
         */
        @JavascriptInterface
        public  String getAccessToken() {
        	Log.i("tag",  BaseApplication.hdtAccessToken);
            return BaseApplication.hdtAccessToken;
        }

        @JavascriptInterface
        public  String getRefreshToken() {
        	Log.i("tag", BaseApplication.hdtRefreshToken);
            return BaseApplication.hdtRefreshToken;
        }
}
	
	/**
     * H5????????????
     */
    public class ExcePtionJavascriptInterface {

        /**
         * H5??????????????????????????????
         */
        @JavascriptInterface
        public void goMainPage() {
            finish();
        }

        /**
         * H5??????????????????????????????
         */
        @JavascriptInterface
        public void relogin() {
            String msg = "?????????????????????????????????????????????????????????";
            Toast.makeText(HuiDuiTongActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
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
		params.put("client_id", "374");
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
							strNewAccessToken = newAccessToken;
							strRefreshToken = refreshToken;
							BaseApplication.hdtAccessToken = newAccessToken;
							BaseApplication.hdtRefreshToken = refreshToken;
						} catch (JSONException e) {
							newAccessToken = "";
							refreshToken = "";
							e.printStackTrace();
						} finally {
							initWebView(newAccessToken);
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				});
	}

	public class JS {
		/**
		 * @return access_token
		 */
		@JavascriptInterface
		public String getAccessToken() {
			Log.i("tag8", "getAccessToken:" + strNewAccessToken);
			return strNewAccessToken;

		}

		/**
		 * @return refresh_token
		 */
		@JavascriptInterface
		public String getRefreshToken() {
			Log.i("tag8", "getRefreshToken:" + strRefreshToken);
			return strRefreshToken;
		}

		@JavascriptInterface
		public String getUserName() {
			Log.i("tag8",
					"getUserName:"
							+ LoginUtil.getUserId(HuiDuiTongActivity.this));
			return LoginUtil.getUserId(HuiDuiTongActivity.this);
		}

		/**
		 * ????????????????????????
		 */
		@JavascriptInterface
		public String getUserId() {
			Log.i("tag8",
					"getUserId:" + LoginUtil.getUserId(HuiDuiTongActivity.this));
			return LoginUtil.getUserId(HuiDuiTongActivity.this);
		}

		/**
		 * @return 0????????????title???1?????????
		 */
		@JavascriptInterface
		public int getHeader() {
			Log.i("tag8", "getHeader");
			// if (isDBS()) {
			// return 1;
			// }
			return 0;
		}

		@JavascriptInterface
		public String isIndex() {
			Log.i("tag8", "isIndex");
			return "1";
		}

		@JavascriptInterface
		public String getUid() {
			Log.i("tag8",
					"getUid:" + LoginUtil.getUserId(HuiDuiTongActivity.this));
			return LoginUtil.getUserId(HuiDuiTongActivity.this);
		}

		/**
		 * @return
		 */
		@JavascriptInterface
		public int isPointLogin() {
			Log.i("tag8", "isPointLogin");
			return 1;
		}
	}

	// private boolean isDBS() {
	// boolean result = false;
	// int client_id = Integer.parseInt(BocSdkConfig.CONSUMER_KEY);
	// switch (client_id) {
	// case 386:
	// result = true;
	// break;
	// }
	// return result;
	// }

}
