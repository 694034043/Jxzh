package com.bocop.zyt.bocop.jxplatform.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.httpUnits.NetworkUtils;

import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.activity_htzq)
public class CardActivity extends BaseActivity {
	
	@ViewInject(R.id.htzq_titleName)
	private TextView htzq_titleName;
	@ViewInject(R.id.htzq_webView)
	private WebView htzq_webView;
	
	@ViewInject(R.id.ll_network_closed)
	private LinearLayout llNetworkClosed;
	@ViewInject(R.id.ll_network_load)
	private LinearLayout llNetworkLoad;
	
	int height;
	String userId="";
	String token="";
	private List<String> titles = new ArrayList<String>();
	
	private Boolean loadError ;
	

	@SuppressWarnings("deprecation")
	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loadError  = false;
		llNetworkLoad.setVisibility(View.VISIBLE);
		htzq_webView.setVisibility(View.INVISIBLE);
		llNetworkClosed.setVisibility(View.INVISIBLE);
		
		final ProgressBar bar = (ProgressBar)findViewById(R.id.myProgressBar);
		htzq_webView.getSettings().setSavePassword(false);
		WebSettings settings = htzq_webView.getSettings();
		settings.setSavePassword(false);
    	settings.setJavaScriptEnabled(true);//????????????javaScript
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(true);//??????????????????
        settings.setBuiltInZoomControls(true);//????????????????????????
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setGeolocationEnabled(true);
        htzq_webView.setWebChromeClient(new WebChromeClient(){
			
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				if(title.length() > 10)
				{
					htzq_titleName.setText(title.substring(0, 10));
				}
				else{
					htzq_titleName.setText(title);
				}
				
				 if(!TextUtils.isEmpty(title)&&(title.toLowerCase().contains("error") || title.toLowerCase().contains("??????") || title.toLowerCase().contains("??????"))){
		        	  Log.i("tag88", title);
		              loadError = true;
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
        htzq_webView.setWebViewClient(new WebViewClient(){
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {

        	view.loadUrl(url); //????????????webview??????????????????url

        	return true;
        	}
        	
        	/* (non-Javadoc)
			 * @see android.webkit.WebViewClient#onPageStarted(android.webkit.WebView, java.lang.String, android.graphics.Bitmap)
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if (NetworkUtils.isNetworkConnected(CardActivity.this)) {
					llNetworkLoad.setVisibility(View.INVISIBLE);
					htzq_webView.setVisibility(View.VISIBLE);
					llNetworkClosed.setVisibility(View.INVISIBLE);
				} else {
					view.stopLoading();
					CustomProgressDialog.showBocNetworkSetDialog(CardActivity.this);
					if(htzq_webView.canGoBack()){
						htzq_webView.goBack();
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
					 	htzq_webView.setVisibility(View.VISIBLE);
					 	llNetworkClosed.setVisibility(View.INVISIBLE);
		            } else { //????????????????????????????????????????????????????????????????????????????????????????????????
		            	llNetworkLoad.setVisibility(View.INVISIBLE);//?????????????????????????????????????????????????????????????????????????????????????????????WebView
					 	htzq_webView.setVisibility(View.INVISIBLE);
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
				Log.i("tag", "errorCode:" + errorCode + "description:" + description + "failingUrl:" + failingUrl);
				loadError = true;
			}
        	
        	});
		Bundle bundle = getIntent().getExtras();
		
		htzq_webView.loadUrl(BocSdkConfig.CARD);
		//??????webView???????????????URL?????????????????????app????????????????????????userId???accessToken
				
	}
	@JavascriptInterface
	public void startFunction(final int str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	//	jket_titleName.setText(titles.get(titles.size()- str));
	}
	
	@OnClick(R.id.htzq_back)
	public void back(View v){
		// ????????????????????????????????????????????????app??????????????????title?????????
		if(htzq_webView.canGoBack()){
			
			htzq_webView.goBack();
		}
		else{
			finish();
		}
	}
	
	@OnClick(R.id.btnNetworkClose)
	public void button(View v) {
		Log.i("tag", "reload");
		llNetworkLoad.setVisibility(View.INVISIBLE);
		htzq_webView.setVisibility(View.INVISIBLE);
		llNetworkClosed.setVisibility(View.INVISIBLE);
		loadError  = false;
		htzq_webView.reload();
//		initView();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	// ??????????????????????????????Activity???????????????????????????????????????WebView?????????
		// ????????????????????????????????????app????????????????????????????????????
		if ((keyCode == KeyEvent.KEYCODE_BACK) && htzq_webView.canGoBack()) {
			if(htzq_webView.canGoBack()){
				
				htzq_webView.goBack();
			}
			else{
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
