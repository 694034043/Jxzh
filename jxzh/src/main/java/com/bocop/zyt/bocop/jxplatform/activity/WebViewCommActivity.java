package com.bocop.zyt.bocop.jxplatform.activity;

import android.net.Uri;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;

@SuppressLint("SetJavaScriptEnabled")
@ContentView(R.layout.activity_webview_com)
public class WebViewCommActivity extends BaseActivity {

	@ViewInject(R.id.htzq_titleName)
	private TextView htzq_titleName;
	@ViewInject(R.id.htzq_webView)
	private WebView htzq_webView;
	
	
	String strurl;
	String titleName;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if(bundle != null){
			strurl= bundle.getString("url");
			titleName = bundle.getString("name");
		}
		if(titleName != null){
			htzq_titleName.setText(titleName);
		}
		
		final ProgressBar bar = (ProgressBar) findViewById(R.id.myProgressBar);
		htzq_webView.getSettings().setSavePassword(false);
		WebSettings settings = htzq_webView.getSettings();
		settings.setSavePassword(false);
		settings.setJavaScriptEnabled(true);// 可以运行javaScript
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setDomStorageEnabled(true);
		settings.setSupportZoom(true);// 是否支持缩放
		settings.setBuiltInZoomControls(true);// 是否显示缩放按钮
		settings.setAppCacheEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setGeolocationEnabled(true);
		htzq_webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				if (title.length() > 10) {
					htzq_titleName.setText(title.substring(0, 10));
				} else {
					htzq_titleName.setText(title);
				}
			}

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
				super.onProgressChanged(view, newProgress);
			}
			
			

		});
		htzq_webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				Log.i("url:", url);
//				view.loadUrl(url); // 在当前的webview中跳转到新的url
//
//				return true;
				
				  if (url.startsWith("scheme:") || url.startsWith("scheme:")) {
                      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                      startActivity(intent);
                 }
                 return false;
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
			}
			
			/* (non-Javadoc)
			 * @see android.webkit.WebViewClient#onReceivedSslError(android.webkit.WebView, android.webkit.SslErrorHandler, android.net.http.SslError)
			 */
			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				// TODO Auto-generated method stub
				handler.proceed();
			}
			
		});
		
		
		

		htzq_webView.loadUrl(strurl);
		// 设置webView需要请求的URL，其中，如果在app中已登入，请传入userId与accessToken

	}


	@OnClick(R.id.htzq_back)
	public void back(View v) {
		// 这部分是判断返回时是否返回接入的app，此处是根据title来判断

		try {
			if (htzq_webView.canGoBack()) {
				htzq_webView.goBack();
			} else {
				finish();
			}
		} catch (Exception ex) {
			Log.i("tag", "Exception" + ex.getMessage().toString());
			finish();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
		// 按物理返回键处理，这里与app中接入的返回键的逻辑一致
		try {
			if ((keyCode == KeyEvent.KEYCODE_BACK)) {
				if (htzq_webView.canGoBack()) {
					htzq_webView.goBack();
				} else {
					finish();
				}
				return true;
			}
			return super.onKeyDown(keyCode, event);
		} catch (Exception ex) {
			Log.i("tag", "Exception" + ex.getMessage().toString());
			finish();
			return true;
		}
	}
	

}
