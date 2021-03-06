package com.bocop.zyt.bocop.jxplatform.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.gm.utils.HybridCallBack;
import com.bocop.zyt.bocop.gm.utils.HybridUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.RegularCheck;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;

@ContentView(R.layout.activity_exchange)
public class WebForZytActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.webView)
	private WebView webView;

	@ViewInject(R.id.ll_network_closed)
	private LinearLayout llNetworkClosed;
	@ViewInject(R.id.ll_network_load)
	private LinearLayout llNetworkLoad;

	private Boolean loadError;

	private ProgressDialog progressDialog;

	// @ViewInject(R.id.exchangeProgressBar)
	// private WebView exchange_webView;

	@ViewInject(R.id.exchangeProgressBar)
	private ProgressBar bar;

	private Uri imageUri;
	private String strurl;
	private String titleName;
	private String type;
	private String loadUrl = "";
	private boolean isNeedClearHistory = false;

	private ValueCallback<Uri> mUploadMessage;// ?????????????????????
	private ValueCallback<Uri[]> mUploadCallbackAboveL;
	private final static int FILECHOOSER_RESULTCODE = 1;// ?????????????????????</span>
	private Pattern pattern;
	private Map<String, String> map = new HashMap<>();
	private boolean isShowParamsTitle;

	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// bar = (ProgressBar)findViewById(R.id.exchangeProgressBar);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			strurl = bundle.getString("url");
			titleName = bundle.getString("name");
			isShowParamsTitle = bundle.getBoolean("isShowParamsTitle");
			Log.i("strurl", strurl);
			Log.i("titleName", titleName);
		}
		initWebView();

	}

	@OnClick(R.id.iv_back)
	public void back(View v) {
		if (loadUrl.indexOf(IURL.Bank_kht_key_url) != -1 || loadUrl.indexOf(IURL.Bank_qdt_key_url) != -1
				|| loadUrl.indexOf(IURL.Bank_dzt_key_url) != -1 || loadUrl.indexOf(IURL.Bank_bht_key_url) != -1
				|| loadUrl.indexOf(IURL.Bank_pjt_key_url) != -1 || loadUrl.indexOf(IURL.Bank_zxt_key_url) != -1) {
			isNeedClearHistory = true;
			webView.loadUrl(strurl);
		} else if (strurl.equals(loadUrl)) {
			finish();
		} else if (webView.canGoBack()) {
			webView.goBack();
		} else {
			finish();
		}
	}

	public static void startAct(Context context, String type, String url, String title, boolean isShowParamsTitle) {
		Bundle bundleHx = new Bundle();
		bundleHx.putString("url", url);
		bundleHx.putString("type", type);
		bundleHx.putString("name", title);
		bundleHx.putBoolean("isShowParamsTitle", isShowParamsTitle);
		Intent intentHx = new Intent(context, WebForZytActivity.class);
		intentHx.putExtras(bundleHx);
		context.startActivity(intentHx);
	}

	public static void startAct(Context context, String url, String title, boolean isShowParamsTitle) {
		Bundle bundleHx = new Bundle();
		bundleHx.putString("url", url);
		bundleHx.putString("name", title);
		bundleHx.putBoolean("isShowParamsTitle", isShowParamsTitle);
		Intent intentHx = new Intent(context, WebForZytActivity.class);
		intentHx.putExtras(bundleHx);
		context.startActivity(intentHx);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ??????????????????????????????Activity???????????????????????????????????????WebView?????????
		// ????????????????????????????????????app????????????????????????????????????
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (!TextUtils.isEmpty(loadUrl) && (loadUrl.indexOf(IURL.Bank_kht_key_url) != -1
					|| loadUrl.indexOf(IURL.Bank_qdt_key_url) != -1 || loadUrl.indexOf(IURL.Bank_dzt_key_url) != -1
					|| loadUrl.indexOf(IURL.Bank_bht_key_url) != -1 || loadUrl.indexOf(IURL.Bank_pjt_key_url) != -1
					|| loadUrl.indexOf(IURL.Bank_zxt_key_url) != -1)) {
				isNeedClearHistory = true;
				webView.loadUrl(strurl);
			} else if (!TextUtils.isEmpty(loadUrl) && strurl.equals(loadUrl)) {
				finish();
			} else if (webView.canGoBack()) {
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
		llNetworkLoad.setVisibility(View.VISIBLE);
		webView.setVisibility(View.INVISIBLE);
		llNetworkClosed.setVisibility(View.INVISIBLE);
		loadError = false;
		webView.reload();
	}

	/**
	 * ?????????webView
	 */
	@SuppressLint("JavascriptInterface")
	public void initWebView() {

		loadError = false;
		llNetworkLoad.setVisibility(View.VISIBLE);
		webView.setVisibility(View.INVISIBLE);
		llNetworkClosed.setVisibility(View.INVISIBLE);
		WebSettings ws = webView.getSettings();
		ws.setSavePassword(false);
		ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(true);
		ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		ws.setUseWideViewPort(true);
		ws.setLoadWithOverviewMode(true);
		// ws.setSupportZoom(false);
		ws.setDomStorageEnabled(true);
		webView.clearCache(true);
		// webView.addJavascriptInterface(this, "nativeApp");
		// webView.addJavascriptInterface(new JsInterface(), "nativeApp");
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onLoadResource(WebView view, String url) {
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (TextUtils.isEmpty(url)) {
					return false;
				}
				if (pattern == null) {
					pattern = Pattern.compile("apps.callbackapps", Pattern.CASE_INSENSITIVE);
				}
				Matcher matcher = pattern.matcher(url);
				if (matcher != null && matcher.find()) {
					// TODO
					HybridUtil.getInstance().handleAppRequest(url, WebForZytActivity.this, new HybridCallBack() {

						@Override
						public void errorMsg(Exception e) {

						}
					});
					return true;
				} else if (url.startsWith(WebView.SCHEME_TEL)) {
					try {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(url));
						startActivity(intent);
					} catch (ActivityNotFoundException ignored) {
					}
					return true;
				}
				//webView.loadUrl(url);
				return false;
			}

			@SuppressWarnings("deprecation")
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
				if (pattern == null) {
					pattern = Pattern.compile("apps.callbackapps", Pattern.CASE_INSENSITIVE);
				}
				Matcher matcher = pattern.matcher(url);
				if (matcher != null && matcher.find()) {
					// TODO
					// mHandler.sendEmptyMessage(CASE_TIMEOUT);
					HybridUtil.getInstance().handleAppRequest(url, WebForZytActivity.this, new HybridCallBack() {

						@Override
						public void errorMsg(Exception e) {

						}
					});
				}
				return super.shouldInterceptRequest(view, url);
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
				llNetworkLoad.setVisibility(View.VISIBLE);
				webView.setVisibility(View.INVISIBLE);
				llNetworkClosed.setVisibility(View.INVISIBLE);
				String title = map.get("" + url);
				if (isShowParamsTitle || TextUtils.isEmpty(title) || title.contains("123.124")
						|| RegularCheck.isIp(title) || title.length() > 10) {
					tv_titleName.setText(titleName);
				} else {
					tv_titleName.setText(title);
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
				if (!loadError) {// ??????????????????????????????????????????????????????
					llNetworkLoad.setVisibility(View.INVISIBLE);// ?????????????????????????????????????????????????????????????????????????????????????????????WebView
					webView.setVisibility(View.VISIBLE);
					llNetworkClosed.setVisibility(View.INVISIBLE);
					if (!TextUtils.isEmpty(url)) {
						loadUrl = "http://" + url.substring(url.indexOf("http://") + 7).replaceAll("//", "/");
					}
					// ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
					if (!TextUtils.isEmpty(loadUrl) && (loadUrl.indexOf(IURL.Bank_kht_key_url) != -1
							|| loadUrl.indexOf(IURL.Bank_qdt_key_url) != -1
							|| loadUrl.indexOf(IURL.Bank_dzt_key_url) != -1
							|| loadUrl.indexOf(IURL.Bank_bht_key_url) != -1
							|| loadUrl.indexOf(IURL.Bank_pjt_key_url) != -1
							|| loadUrl.indexOf(IURL.Bank_zxt_key_url) != -1)) {
						isNeedClearHistory = true;
					}
				} else { // ????????????????????????????????????????????????????????????????????????????????????????????????
					llNetworkLoad.setVisibility(View.INVISIBLE);// ?????????????????????????????????????????????????????????????????????????????????????????????WebView
					webView.setVisibility(View.INVISIBLE);
					llNetworkClosed.setVisibility(View.VISIBLE);
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
				Log.i("tag", "errorCode:" + errorCode + "description:" + description + "failingUrl:" + failingUrl);
				loadError = true;
			}

			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * android.webkit.WebViewClient#onReceivedSslError(android.webkit.
			 * WebView, android.webkit.SslErrorHandler,
			 * android.net.http.SslError)
			 */
			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				// TODO Auto-generated method stub
				handler.proceed();
			}

			@Override
			public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
				super.doUpdateVisitedHistory(view, url, isReload);
				if (isNeedClearHistory) {
					isNeedClearHistory = false;
					webView.clearHistory();
				}
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			// ????????????title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				Log.i("tag", title);
				map.put(view.getUrl(), title);
				if (isShowParamsTitle || TextUtils.isEmpty(title) || title.contains("123.124")
						|| RegularCheck.isIp(title) || title.length() > 10) {
					tv_titleName.setText(titleName);
				} else {
					tv_titleName.setText(title);
				}
				if (!TextUtils.isEmpty(title) && (title.toLowerCase().contains("error")
						|| title.toLowerCase().contains("??????") || title.toLowerCase().contains("??????"))) {
					Log.i("tag88", title);
					loadError = true;
				}
			}

			// ??????????????????????????????
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					bar.setVisibility(View.GONE);
				} else {
					if (View.GONE == bar.getVisibility()) {
						bar.setVisibility(View.VISIBLE);
					}
					bar.setProgress(newProgress);
				}
				// XmsWebActivity.this.getWindow().setFeatureInt(
				// Window.FEATURE_PROGRESS, newProgress);
				super.onProgressChanged(view, newProgress);
			}

			// ??????javascript??????alert
			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}

			@Override
			public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
											 FileChooserParams fileChooserParams) {
				mUploadCallbackAboveL = filePathCallback;
				take();
				return true;
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage = uploadMsg;
				take();
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
				mUploadMessage = uploadMsg;
				take();
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
				mUploadMessage = uploadMsg;
				take();
			}
		});

		/**
		 * ????????????
		 */
		webView.loadUrl(strurl);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == mUploadMessage && null == mUploadCallbackAboveL)
				return;
			Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
			if (mUploadCallbackAboveL != null) {
				onActivityResultAboveL(requestCode, resultCode, data);
			} else if (mUploadMessage != null) {
				Log.e("result", result + "");
				if (result == null) {
					// mUploadMessage.onReceiveValue(imageUri);
					mUploadMessage.onReceiveValue(imageUri);
					mUploadMessage = null;

					Log.e("imageUri", imageUri + "");
				} else {
					mUploadMessage.onReceiveValue(result);
					mUploadMessage = null;
				}

			}
		}
	}

	@SuppressWarnings("null")
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
		if (requestCode != FILECHOOSER_RESULTCODE || mUploadCallbackAboveL == null) {
			return;
		}

		Uri[] results = null;
		if (resultCode == Activity.RESULT_OK) {
			if (data == null) {
				results = new Uri[] { imageUri };
			} else {
				String dataString = data.getDataString();
				ClipData clipData = data.getClipData();

				if (clipData != null) {
					results = new Uri[clipData.getItemCount()];
					for (int i = 0; i < clipData.getItemCount(); i++) {
						ClipData.Item item = clipData.getItemAt(i);
						results[i] = item.getUri();
					}
				}

				if (dataString != null)
					results = new Uri[] { Uri.parse(dataString) };
			}
		}
		if (results != null) {
			mUploadCallbackAboveL.onReceiveValue(results);
			mUploadCallbackAboveL = null;
		} else {
			results = new Uri[] { imageUri };
			mUploadCallbackAboveL.onReceiveValue(results);
			mUploadCallbackAboveL = null;
		}

		return;
	}

	private void take() {
		File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyApp");
		// Create the storage directory if it does not exist
		if (!imageStorageDir.exists()) {
			imageStorageDir.mkdirs();
		}
		File file = new File(
				imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
		imageUri = Uri.fromFile(file);

		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
		for (ResolveInfo res : listCam) {
			final String packageName = res.activityInfo.packageName;
			final Intent i = new Intent(captureIntent);
			i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
			i.setPackage(packageName);
			i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			cameraIntents.add(i);

		}
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("image/*");
		Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[] {}));
		WebForZytActivity.this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
	}

	public void getLoginViewResultCall() {

		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				String userId = LoginUtil.getUserId(WebForZytActivity.this);

				if (!TextUtils.isEmpty(userId)) {

					Map<String, Object> map = new HashMap<>();
					map.put("userId", LoginUtil.getUserId(WebForZytActivity.this));
					map.put("actoken", LoginUtil.getToken(WebForZytActivity.this));
					HybridUtil.getInstance().handleJsRequest(webView, "getLoginViewResultCall", map,
							new HybridCallBack() {

								@Override
								public void errorMsg(Exception e) {
									// TODO Auto-generated method
									// stub

								}
							});

					return;
				}

				LoginUtil.authorize(WebForZytActivity.this, new LoginUtil.ILoginListener() {
					@Override
					public void onLogin() {
						Map<String, Object> map = new HashMap<>();
						map.put("userId", LoginUtil.getUserId(WebForZytActivity.this));
						map.put("actoken", LoginUtil.getToken(WebForZytActivity.this));
						HybridUtil.getInstance().handleJsRequest(webView, "getLoginViewResultCall", map,
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		wv_main.getSettings().setBuiltInZoomCon trols(true);
//		wv_main.setVisibility(Vie,w.GONE);
		//ViewGroup view = (ViewGroup) getWindow().getDecorView();
		//view.removeAllViews();
		webView.setVisibility(View.GONE);       // ????????????webview??????
		webView.stopLoading();
		webView.removeAllViews();
		webView.destroy();
		webView = null;
	}
}
