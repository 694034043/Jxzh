package com.bocop.zyt.bocop.jxplatform.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;


@ContentView(R.layout.activity_bocopen_web)
public class BocOpenWebActivity extends BaseActivity {
	
	@ViewInject(R.id.iv_imageLeft)
	private BackButton iv_imageLeft;
	
	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	
	@ViewInject(R.id.webView)
	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initWebView();
		
		Bundle bundle = getIntent().getExtras();
		String type = bundle.getString("type");
		String title = bundle.getString("title");
		tv_titleName.setText(title);
		if(type.equals("yhkgl")) {

			String url = BocSdkConfig.HTTP_URL + "/cardmange.php?act=viewAddCard&clientid="
					+ BocSdkConfig.CONSUMER_KEY + "&accesstoken="
					+ LoginUtil.getToken(this) + "&userid="
					+ LoginUtil.getUserId(this) + "&themeid=1&devicetype=1";
			webView.loadUrl(url);
		}else if (type.equals("mmgl")) {
			String url = BocSdkConfig.HTTP_URL + "/cardmange.php?clientid="
					+ BocSdkConfig.CONSUMER_KEY + "&accesstoken="
					+ LoginUtil.getToken(this) + "&userid="
					+ LoginUtil.getUserId(this);
			webView.loadUrl(url);
		}else if (type.equals("tjyhk")) {
			String url = "http://22.188.12.105/wap/cardmange.php?act=viewAddCard&clientid=" + BocSdkConfig.CONSUMER_KEY + "&accesstoken=" + LoginUtil.getToken(this) + "&userid=" + LoginUtil.getUserId(this);
			webView.loadUrl(url);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void initWebView() {
		WebSettings settings = webView.getSettings();
		settings.setSavePassword(false);
    	settings.setJavaScriptEnabled(true);//????????????javaScript
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);//??????????????????
        settings.setBuiltInZoomControls(false);//????????????????????????
        settings.setAppCacheEnabled(true);
        settings.setGeolocationEnabled(true);
        webView.getSettings().setSavePassword(false);
        webView.setWebChromeClient(new WebChromeClient());
	}

}
