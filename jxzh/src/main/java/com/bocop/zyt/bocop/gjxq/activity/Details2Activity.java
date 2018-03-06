package com.bocop.zyt.bocop.gjxq.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;

/**
 * 第二个页面webview
 * @author Administrator
 *
 */
public class Details2Activity extends FragmentActivity {
	
	private TextView tvTitle;
	private WebView wvContent;

	private String[] titleArray = {"保函通", "开户通", "单证通", "企贷通"};
	private String[] urlArray = {"/companyFinance/h5/login/userInfo?type=bht&"
			, "/companyFinance/h5/login/userInfo?type=kht&"
			, "/companyFinance/h5/login/userInfo?type=xzt&"
			, "/companyFinance/h5/login/userInfo?type=qdt&"};
	private int flag = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details2);
		
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		wvContent = (WebView) findViewById(R.id.wvContent);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			flag = bundle.getInt("flag", 0);
			if (flag > 3) {
				flag = 0;
			}
		} 
		
		tvTitle.setText(titleArray[flag]);
		wvContent.setWebViewClient(new WebViewClient());
		wvContent.setWebChromeClient(new WebChromeClient());
		
		String url = BocSdkConfig.qztUrl + urlArray[flag] +"userId="+ LoginUtil.getUserId(this) + "tokens=" +LoginUtil.getToken(this);
		wvContent.loadUrl(url);
	}
	
}
