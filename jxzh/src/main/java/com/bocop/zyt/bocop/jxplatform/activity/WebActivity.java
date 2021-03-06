package com.bocop.zyt.bocop.jxplatform.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;

@ContentView(R.layout.activity_exchange)
public class WebActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.webView)
	private WebView webView;

	@ViewInject(R.id.ll_network_closed)
	private LinearLayout llNetworkClosed;
	@ViewInject(R.id.ll_network_load)
	private LinearLayout llNetworkLoad;

	private Boolean loadError ;

	ProgressDialog progressDialog;

//	@ViewInject(R.id.exchangeProgressBar)
//	private WebView exchange_webView;

	@ViewInject(R.id.exchangeProgressBar)
	private ProgressBar bar;

	String strurl;
	String titleName;

	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		bar = (ProgressBar)findViewById(R.id.exchangeProgressBar);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if(bundle != null){
			strurl= bundle.getString("url");
			titleName = bundle.getString("name");
			//Log.i("strurl", strurl);
			//Log.i("titleName", titleName);
		}
		initWebView();

	}

	@OnClick(R.id.iv_back)
	public void back(View v) {

		if(titleName.equals("??????????????????-????????????")){
			finish();
		}
		if(webView.canGoBack()){
			webView.goBack();
		}
		else{
			finish();
		}
	}

	public static void startAct(Context context, String url, String title) {
		Intent intent = new Intent(context, WebActivity.class);
		Bundle b = new Bundle();
		b.putString("url", url);
		b.putString("name", title);
		intent.putExtras(b);
		context.startActivity(intent);
	}


	public static void startAct(Context context, String url, String title,int title_style) {
		Intent intent = new Intent(context, WebActivity.class);
		Bundle b = new Bundle();
		b.putString("url", url);
		b.putString("name", title);
		b.putInt("title_style", title_style);
		intent.putExtras(b);
		context.startActivity(intent);
	}

	public static void startAct(Context context, String url, String title,String title_style) {
		Intent intent = new Intent(context, WebActivity.class);
		Bundle b = new Bundle();
		b.putString("url", url);
		b.putString("name", title);
		//b.putInt("title_style", title_style);
		intent.putExtras(b);
		context.startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ??????????????????????????????Activity???????????????????????????????????????WebView?????????
		// ????????????????????????????????????app????????????????????????????????????
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if(titleName.equals("??????????????????-????????????")){
				finish();
			}
			if(webView.canGoBack()){
				webView.goBack();
			}
			else{
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@OnClick(R.id.btnNetworkClose)
	public void button(View v) {
		//Log.i("tag", "reload");
		llNetworkLoad.setVisibility(View.VISIBLE);
		webView.setVisibility(View.INVISIBLE);
		llNetworkClosed.setVisibility(View.INVISIBLE);
		loadError  = false;
		webView.reload();
	}

	/**
	 * ?????????webView
	 */
	@SuppressLint("JavascriptInterface")
	public void initWebView() {

		loadError  = false;
		llNetworkLoad.setVisibility(View.VISIBLE);
		webView.setVisibility(View.INVISIBLE);
		llNetworkClosed.setVisibility(View.INVISIBLE);

		WebSettings ws = webView.getSettings();
		ws.setSavePassword(false);
		ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(true);
		ws.setUseWideViewPort(false);
		ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		ws.setLoadWithOverviewMode(true);
		ws.setSupportZoom(false);
		ws.setDomStorageEnabled(true);
		webView.clearCache(true);
		webView.setDownloadListener(new MyWebViewDownLoadListener());
//		webView.addJavascriptInterface(this, "nativeApp");
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
				llNetworkLoad.setVisibility(View.VISIBLE);
				webView.setVisibility(View.INVISIBLE);
				llNetworkClosed.setVisibility(View.INVISIBLE);

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
				} else { //????????????????????????????????????????????????????????????????????????????????????????????????
					llNetworkLoad.setVisibility(View.INVISIBLE);//?????????????????????????????????????????????????????????????????????????????????????????????WebView
					webView.setVisibility(View.INVISIBLE);
					llNetworkClosed.setVisibility(View.VISIBLE);
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
				//Log.i("tag", "errorCode:" + errorCode + "description:" + description + "failingUrl:" + failingUrl);
				loadError = true;
			}

			/* (non-Javadoc)
			 * @see android.webkit.WebViewClient#onReceivedSslError(android.webkit.WebView, android.webkit.SslErrorHandler, android.net.http.SslError)
			 */
			@Override
			public void onReceivedSslError(WebView view,
										   SslErrorHandler handler, SslError error) {
				// TODO Auto-generated method stub
				handler.cancel();
			}


		});
		webView.setWebChromeClient(new WebChromeClient() {
			// ????????????title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				//Log.i("tag", title);
				titleName = title;
				if(title.contains("123.124")){
					title = "";
				}
				if(title.length() > 10){
					title = titleName;
				}

				tv_titleName.setText(title);
				if(!TextUtils.isEmpty(title)&&(title.toLowerCase().contains("error") || title.toLowerCase().contains("??????") || title.toLowerCase().contains("??????"))){
					//Log.i("tag88", title);
					loadError = true;
				}
			}

			// ??????????????????????????????
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					bar.setVisibility(View.INVISIBLE);
				} else {
					if (View.INVISIBLE == bar.getVisibility()) {
						bar.setVisibility(View.VISIBLE);
					}
					bar.setProgress(newProgress);
				}
//				XmsWebActivity.this.getWindow().setFeatureInt(
//						Window.FEATURE_PROGRESS, newProgress);
				super.onProgressChanged(view, newProgress);
			}

			// ??????javascript??????alert
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
									 JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}
		});
		/**
		 * ????????????
		 */
		webView.loadUrl(strurl);

	}


	private class MyWebViewDownLoadListener implements DownloadListener{

		@Override
		public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
									long contentLength) {
			//Log.i("tag", "url="+url);
			//Log.i("tag", "userAgent="+userAgent);
			//Log.i("tag", "contentDisposition="+contentDisposition);
			//Log.i("tag", "mimetype="+mimetype);
			//Log.i("tag", "contentLength="+contentLength);
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
	}


}
