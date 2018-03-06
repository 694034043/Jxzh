package com.bocop.zyt.bocop.zyt.gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.fmodule.utils.FStringUtil;

import org.apache.http.util.EncodingUtils;


public class XWebActForTaoCIMall extends BaseAct {

	public WebView wv_main;

	public String url;
	private ImageView iv_setting;
	private ImageView iv_collection;
	private String _current_url;
	private TextView tv_actionbar_title;
	private String title;
	private RelativeLayout rl_actionbar;
	private boolean hidenBar;

	private ProgressBar loadingProgressBar;

	private boolean post;

	private String post_data;


	public static void startActForPost(Context context, String url,String post_data, String title) {
		Intent intent = new Intent(context, XWebActForTaoCIMall.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		intent.putExtra("hidenBar", false);
		intent.putExtra("post", true);
		intent.putExtra("post_data", post_data);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_xweb_for_ci);

		url = getIntent().getExtras().getString("url");
		title = getIntent().getExtras().getString("title");
		hidenBar = getIntent().getExtras().getBoolean("hidenBar");
		post = getIntent().getExtras().getBoolean("post");
		if(post){
			post_data = getIntent().getExtras().getString("post_data");
			
		}
		init_widget();
		init_webview();

	}

	public void fun_home_press(View view) {
		finish();
	}

	@Override
	public void fun_back_press(View v) {
		// super.fun_back_press(v);
		if (wv_main.canGoBack()) {
			wv_main.goBack();
		} else {
			finish();
		}
	}

	@Override
	public void init_widget() {
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText(FStringUtil.turn_string(title));

		rl_actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		loadingProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
		if (hidenBar) {
			rl_actionbar.setVisibility(View.GONE);
		} else {
			rl_actionbar.setVisibility(View.VISIBLE);

		}
		wv_main = (WebView) findViewById(R.id.wv_main);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
		// 按物理返回键处理，这里与app中接入的返回键的逻辑一致
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (wv_main.canGoBack()) {
				wv_main.goBack();
			} else {
				finish();
				// fun_toast("不能继续返回了");
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 初始化webView
	 */
	@SuppressLint("JavascriptInterface")
	public void init_webview() {

		WebSettings ws = wv_main.getSettings();
		ws.setSavePassword(false);
		ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(true);
		// ws.setUseWideViewPort(false);
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		ws.setLoadWithOverviewMode(true);
		// ws.setSupportZoom(false);
		ws.setDomStorageEnabled(true);
		wv_main.clearCache(true);

		wv_main.getSettings().setJavaScriptEnabled(true);
		wv_main.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		WebSettings settings = wv_main.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		// webView.addJavascriptInterface(this, "nativeApp");
		// webView.addJavascriptInterface(new JsInterface(), "nativeApp");
		wv_main.setWebViewClient(new WebViewClient() {
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
			 * @see android.webkit.WebViewClient#onPageStarted(android.webkit.
			 * WebView, java.lang.String, android.graphics.Bitmap)
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if (loadingProgressBar != null) {
					loadingProgressBar.setVisibility(View.VISIBLE);
					Log.i("TAG", "进度显示");
				}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.webkit.WebViewClient#onPageFinished(android.webkit.
			 * WebView, java.lang.String)
			 */
			@Override
			public void onPageFinished(WebView view, String url) {

				ILOG.log_4_7("当前页面url:" + url);
				if (loadingProgressBar != null) {
					loadingProgressBar.setVisibility(View.GONE);
					Log.i("TAG", "进度隐藏");
				}

			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.webkit.WebViewClient#onPageFinished(android.webkit.
			 * WebView, java.lang.String)
			 */
			@SuppressWarnings("deprecation")
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);

				if(IURL.Bank_Ci_shang_cheng.equals(failingUrl)){
					
					finish();
				}
			}

		});

		wv_main.setWebChromeClient(new WebChromeClient() {
			private int oldProgress;

			@Override
			public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
					FileChooserParams fileChooserParams) {
				return true;
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
			}

			// 用于获取title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				if(FStringUtil.is_empty(title)){
					
					tv_actionbar_title.setText(XWebActForTaoCIMall.this.title);
				}else{
					
					tv_actionbar_title.setText(title);
				}
				// titles.add(title);

			}

			// 设置网页加载的进度条
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				oldProgress = newProgress;
				loadingProgressBar.setProgress(newProgress);
				Log.i("TAG", newProgress + "进度");
				if (newProgress == 100) {
					loadingProgressBar.setVisibility(View.GONE);

				}
				
			}

			// 处理javascript中的alert
			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}
		});
		/**
		 * 生产地址
		 */
		
//		//需要访问的网址  
//		String url = "http://www.cqjg.gov.cn/netcar/FindThree.aspx";  
//		//post访问需要提交的参数  
//		String postDate = "txtName=zzz&QueryTypeLst=1&CertificateTxt=dsds";  
//		//由于webView.postUrl(url, postData)中 postData类型为byte[] ，  
//		//通过EncodingUtils.getBytes(data, charset)方法进行转换  
//		webView.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));  
		
		if(!post){
			wv_main.loadUrl(url);
		}else{
			wv_main.postUrl(url,EncodingUtils.getBytes(post_data, "BASE64"));
			
		}
		

	}

}
