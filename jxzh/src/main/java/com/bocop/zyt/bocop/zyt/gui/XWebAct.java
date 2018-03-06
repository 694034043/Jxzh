package com.bocop.zyt.bocop.zyt.gui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.bocop.zyt.bocop.gm.utils.HybridCallBack;
import com.bocop.zyt.bocop.gm.utils.HybridUtil;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.fmodule.utils.FStringUtil;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bocop.zyt.F.code;
import static com.bocop.zyt.R.id.iv_bg;
import static com.bocop.zyt.R.id.iv_music;
import static com.bocop.zyt.R.id.rel_bg;


public class XWebAct extends BaseAct {

	public WebView wv_main;

	public String url;
	public String from;
	private ImageView iv_setting;
	private ImageView iv_collection;
	private String _current_url;
	private TextView tv_actionbar_title;
	private String title;
	private RelativeLayout rl_actionbar;
	private boolean hidenBar;
	private ValueCallback<Uri[]> mUploadCallbackAboveL;

	private ProgressBar loadingProgressBar;

	private boolean post;

	private String post_data;
	
	private static final int Title_Lenght=9;

	private boolean isShowParamsTitle;

	public static void startAct(Context context, String url, String title,String from) {
		Intent intent = new Intent(context, XWebAct.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		intent.putExtra("hidenBar", false);
		intent.putExtra("post", false);
		intent.putExtra("from", from);
		context.startActivity(intent);
	}
	
	public static void startAct(Context context, String url, String title,boolean isShowParamsTitle) {
		Intent intent = new Intent(context, XWebAct.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		intent.putExtra("isShowParamsTitle", isShowParamsTitle);
		intent.putExtra("hidenBar", false);
		intent.putExtra("post", false);
		context.startActivity(intent);
	}

	public static void startAct(Context context, String url, String title, int bar_style) {
		Intent intent = new Intent(context, XWebAct.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		intent.putExtra("hidenBar", false);
		intent.putExtra("post", false);
		intent.putExtra("bar_style", bar_style);
		context.startActivity(intent);
	}

	public static void startActNoActionbar(Context context, String url, String title) {
		Intent intent = new Intent(context, XWebAct.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		intent.putExtra("hidenBar", true);
		intent.putExtra("post", false);
		context.startActivity(intent);
	}

	public static void startActForPost(Context context, String url, String post_data, String title) {
		Intent intent = new Intent(context, XWebAct.class);
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
		setContentView(R.layout.act_xweb);

		url = getIntent().getExtras().getString("url");
		title = getIntent().getExtras().getString("title");
		from = getIntent().getExtras().getString("from");
		hidenBar = getIntent().getExtras().getBoolean("hidenBar");
		post = getIntent().getExtras().getBoolean("post");
		isShowParamsTitle = getIntent().getBooleanExtra("isShowParamsTitle", false);
		if (post) {
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
		if (from.equals("yct1")||from.equals("yct2")||from.equals("yct3")){
			rl_actionbar.setBackgroundResource(R.drawable.shape_base_yct_action_bar);
		}else {
			rl_actionbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
		}

		if (getIntent().hasExtra("bar_style")) {

			int bar_style = getIntent().getExtras().getInt("bar_style");

			rl_actionbar.setBackgroundResource(bar_style);
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

			}

		});

		wv_main.setWebChromeClient(new WebChromeClient() {
			private int oldProgress;

			// 用于获取title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				if (FStringUtil.is_empty(title)||isShowParamsTitle) {
					tv_actionbar_title.setText(XWebAct.this.title);
				} else {

					if(title.length()>=Title_Lenght){
						tv_actionbar_title.setText(title.substring(0, Title_Lenght)+"...");
					}else{
						
						tv_actionbar_title.setText(title);
					}
				}

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
			
			@Override
			public boolean onShowFileChooser(WebView webView,
					ValueCallback<Uri[]> filePathCallback,
					FileChooserParams fileChooserParams) {
				mUploadCallbackAboveL=filePathCallback;
				take();
				return true;
			}
			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage=uploadMsg;
				take();
			}
			public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType) {
				mUploadMessage=uploadMsg;
				take();
			}
			public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType, String capture) {
				mUploadMessage=uploadMsg;
				take();
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

		if (!post) {
			wv_main.loadUrl(url);
		} else {
			wv_main.postUrl(url, EncodingUtils.getBytes(post_data, "BASE64"));

		}

	}
	
	private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
	private Uri imageUri;
	private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
	private void take(){
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
     // Create the storage directory if it does not exist
     if (! imageStorageDir.exists()){
         imageStorageDir.mkdirs();                  
     }
     File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");  
     imageUri = Uri.fromFile(file); 

     final List<Intent> cameraIntents = new ArrayList<Intent>();
     final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
     final PackageManager packageManager = getPackageManager();
     final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
     for(ResolveInfo res : listCam) {
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
     Intent chooserIntent = Intent.createChooser(i,"Image Chooser");
     chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
     XWebAct.this.startActivityForResult(chooserIntent,  FILECHOOSER_RESULTCODE);
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            }
            else  if (mUploadMessage != null) {
            	Log.e("result",result+"");
            	if(result==null){
//            		mUploadMessage.onReceiveValue(imageUri);
            		mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;	
                   
                    Log.e("imageUri",imageUri+"");
            	}else {
            		mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;	
				}
            	              
            }
        }
    }
    


    @SuppressWarnings("null")
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }
        
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
            	results = new Uri[]{imageUri};
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
                        results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if(results!=null){
        	   mUploadCallbackAboveL.onReceiveValue(results);
   	        mUploadCallbackAboveL = null;
        }else{
        	results = new Uri[]{imageUri};
        	mUploadCallbackAboveL.onReceiveValue(results);
   	        mUploadCallbackAboveL = null;
        }
     
        return;
    }

	@Override
	public void finish() {
	    ViewGroup view = (ViewGroup) getWindow().getDecorView();
	    view.removeAllViews();
	    super.finish();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		wv_main.getSettings().setBuiltInZoomCon trols(true);
//		wv_main.setVisibility(Vie,w.GONE);
		//ViewGroup view = (ViewGroup) getWindow().getDecorView();
	    //view.removeAllViews();
		wv_main.destroy();
	}
	public void getLoginViewResultCall(){


		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				String userId = LoginUtil.getUserId(XWebAct.this);

				if(!TextUtils.isEmpty(userId)){

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", LoginUtil.getUserId(XWebAct.this));
					map.put("actoken", LoginUtil.getToken(XWebAct.this));
					HybridUtil.getInstance().handleJsRequest(wv_main, "getLoginViewResultCall", map,
							new HybridCallBack() {

								@Override
								public void errorMsg(Exception e) {
									// TODO Auto-generated method
									// stub

								}
							});

					return;
				}

				LoginUtil.authorize(XWebAct.this, new LoginUtil.ILoginListener() {
					@Override
					public void onLogin() {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("userId", LoginUtil.getUserId(XWebAct.this));
						map.put("actoken", LoginUtil.getToken(XWebAct.this));
						HybridUtil.getInstance().handleJsRequest(wv_main, "getLoginViewResultCall", map,
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
