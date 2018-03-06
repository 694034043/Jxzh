//
//  FrgPtDetail
//
//  Created by Administrator on 2015-01-28 13:49:35
//  Copyright (c) Administrator All rights reserved.

/**

 */

package com.bocop.zyt.frg;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.gm.utils.HybridCallBack;
import com.bocop.zyt.bocop.gm.utils.HybridUtil;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.gui.XWebAct;
import com.bocop.zyt.fmodule.utils.FStringUtil;
import com.item.proto.MFunctionIcon;

import org.apache.http.util.EncodingUtils;

import java.util.HashMap;
import java.util.Map;

import static com.bocop.zyt.R.id.loadingProgressBar;
import static com.bocop.zyt.R.id.wv_main;

public class FrgPtDetail extends BaseFrg {

    public WebView mWebView;
    public ImageView iv_finish;
    public TextView tv_actionbar_title;
    private MFunctionIcon functionIcon;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_pt_detail);
        functionIcon = (MFunctionIcon) getActivity().getIntent().getSerializableExtra("data");
        initView();
        loaddata();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.mWebView);
        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
    }

    public void loaddata() {

        WebSettings ws = mWebView.getSettings();
        ws.setSavePassword(false);
        ws.setJavaScriptEnabled(true);
        ws.setBuiltInZoomControls(true);
        // ws.setUseWideViewPort(false);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ws.setLoadWithOverviewMode(true);
        // ws.setSupportZoom(false);
        ws.setDomStorageEnabled(true);
        mWebView.clearCache(true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        // webView.addJavascriptInterface(this, "nativeApp");
        // webView.addJavascriptInterface(new JsInterface(), "nativeApp");
        mWebView.setWebViewClient(new WebViewClient() {
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

            }

        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            private int oldProgress;

            // 用于获取title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            // 设置网页加载的进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }

            // 处理javascript中的alert
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                return true;
            }
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            }
            public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType) {
            }
            public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType, String capture) {
            }
        });
        /**
         * 生产地址
         */

        // //需要访问的网址
        // String url = "http://www.cqjg.gov.cn/netcar/FindThree.aspx";
        // //post访问需要提交的参数
        // String postDate = "txtName=zzz&QueryTypeLst=1&CertificateTxt=dsds";
        // //由于webView.postUrl(url, postData)中 postData类型为byte[] ，
        // //通过EncodingUtils.getBytes(data, charset)方法进行转换
        // webView.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));

        mWebView.loadUrl(functionIcon.url);
        tv_actionbar_title.setText(functionIcon.name);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    getActivity().finish();
                    // fun_toast("不能继续返回了");
                }

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
        // 按物理返回键处理，这里与app中接入的返回键的逻辑一致
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                getActivity().finish();
                // fun_toast("不能继续返回了");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View arg0) {

    }
    public void getLoginViewResultCall(){


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String userId = LoginUtil.getUserId(getContext());

                if(!TextUtils.isEmpty(userId)){

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("userId", LoginUtil.getUserId(getActivity()));
                    map.put("actoken", LoginUtil.getToken(getActivity()));
                    HybridUtil.getInstance().handleJsRequest(mWebView, "getLoginViewResultCall", map,
                            new HybridCallBack() {

                                @Override
                                public void errorMsg(Exception e) {
                                    // TODO Auto-generated method
                                    // stub

                                }
                            });

                    return;
                }

                LoginUtil.authorize(getContext(), new LoginUtil.ILoginListener() {
                    @Override
                    public void onLogin() {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("userId", LoginUtil.getUserId(getContext()));
                        map.put("actoken", LoginUtil.getToken(getContext()));
                        HybridUtil.getInstance().handleJsRequest(mWebView, "getLoginViewResultCall", map,
                                new HybridCallBack() {

                                    @Override
                                    public void errorMsg(Exception e) {
                                        // TODO Auto-generated method
                                        // stub

                                    }
                                });

                    }

                    @Override
                    public void onCancle() {

                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onException() {

                    }
                });
            }
        });

    }

}