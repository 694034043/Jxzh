package com.bocop.zyt.bocop.kht.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
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
import android.webkit.DownloadListener;
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
import com.bocop.zyt.bocop.kht.action.KHTAction;
import com.bocop.zyt.bocop.kht.bean.CameraBean;
import com.bocop.zyt.bocop.kht.constants.LifeConstants;
import com.bocop.zyt.bocop.kht.utils.fileupload.ImageUpload;
import com.bocop.zyt.bocop.kht.utils.fileupload.ImgUtils;
import com.bocop.zyt.bocop.kht.utils.fileupload.ResultCode;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@ContentView(R.layout.activity_exchange)
public class KhtActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.webView)
	private WebView webView;

	@ViewInject(R.id.ll_network_closed)
	private LinearLayout llNetworkClosed;
	@ViewInject(R.id.ll_network_load)
	private LinearLayout llNetworkLoad;

	private Boolean loadError;

	ProgressDialog progressDialog;

	@ViewInject(R.id.exchangeProgressBar)
	private ProgressBar bar;

	String strurl;
	String titleName;
	private ImageUpload imageUploadCompress = new ImageUpload();
	private String js;
	private KHTAction action;
	private Pattern pattern;
	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// bar = (ProgressBar)findViewById(R.id.exchangeProgressBar);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			strurl = bundle.getString("url");
			//strurl="http://buildpack:password1@47.89.29.80:88/ux/OpenAccount/app.html";
			//strurl="http://172.23.16.84:8020/OpenAccount/app.html";
			titleName = bundle.getString("name");
			isShowParamsTitle = bundle.getBoolean("isShowParamsTitle");
			Log.i("strurl", strurl);
			Log.i("titleName", titleName);
		}
		initWebView();
		action = new KHTAction(this);
	}
	
	public static void startAct(Context context,String url,String title){
		startAct(context, url, title,false);
	};
	
	public static void startAct(Context context,String url,String title,boolean isShowParamsTitle){
		Intent intent = new Intent(context, KhtActivity.class);
		Bundle b = new Bundle();
		b.putString("url", url);
		b.putString("name", title);
		b.putBoolean("isShowParamsTitle", isShowParamsTitle);
		intent.putExtras(b);
		context.startActivity(intent);
	};

	@OnClick(R.id.iv_back)
	public void back(View v) {

		if (titleName.equals("中银国际证券-自助开户")) {
			finish();
		}
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
		// 按物理返回键处理，这里与app中接入的返回键的逻辑一致
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (titleName.equals("中银国际证券-自助开户")) {
				finish();
			}
			if (webView.canGoBack()) {
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
	   /*@Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);

	        imageUploadCompress.stop(this, requestCode, resultCode, data, true);
	    }*/
	/**
	 * 初始化webView
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
		ws.setUseWideViewPort(false);
		ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		ws.setLoadWithOverviewMode(true);
		ws.setSupportZoom(false);
		ws.setDomStorageEnabled(true);
		webView.clearCache(true);
		webView.setDownloadListener(new MyWebViewDownLoadListener());
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
					HybridUtil.getInstance().handleAppRequest(url, action, new HybridCallBack() {

						@Override
						public void errorMsg(Exception e) {

						}
					});
					return true;
				}
				webView.loadUrl(url);
				return true;
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
					HybridUtil.getInstance().handleAppRequest(url, action, new HybridCallBack() {

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

			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.webkit.WebViewClient#onPageFinished(android.webkit.
			 * WebView, java.lang.String)
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				if (!loadError) {// 当网页加载成功的时候判断是否加载成功
					llNetworkLoad.setVisibility(View.INVISIBLE);// 加载成功的话，则隐藏掉显示正在加载的视图，显示加载了网页内容的WebView
					webView.setVisibility(View.VISIBLE);
					llNetworkClosed.setVisibility(View.INVISIBLE);
				} else { // 加载失败的话，初始化页面加载失败的图，然后替换正在加载的视图页面
					llNetworkLoad.setVisibility(View.INVISIBLE);// 加载成功的话，则隐藏掉显示正在加载的视图，显示加载了网页内容的WebView
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

		});
		webView.setWebChromeClient(new WebChromeClient() {
			// 用于获取title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				Log.i("tag", title);
				if(!isShowParamsTitle){
					titleName = title;
				}
				if (title.contains("123.124")) {
					title = "";
				}
				if (title.length() > 10) {
					title = titleName;
				}
				tv_titleName.setText(titleName);					
				if (!TextUtils.isEmpty(title) && (title.toLowerCase().contains("error")
						|| title.toLowerCase().contains("失败") || title.toLowerCase().contains("找不"))) {
					Log.i("tag88", title);
					loadError = true;
				}
			}

			// 设置网页加载的进度条
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
				// XmsWebActivity.this.getWindow().setFeatureInt(
				// Window.FEATURE_PROGRESS, newProgress);
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
		webView.loadUrl(strurl);

	}
	
	private ValueCallback<Uri[]> mUploadCallbackAboveL;
	private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
	private Uri imageUri;
	private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
	private boolean isShowParamsTitle;
	private void take(){
		ToastUtils.show(this, "请拍照或上传保持清晰的图片", 2000);
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
     this.startActivityForResult(chooserIntent,  FILECHOOSER_RESULTCODE);
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
        imageUploadCompress.stop(this, requestCode, resultCode, data, true);
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

	private class MyWebViewDownLoadListener implements DownloadListener {

		@Override
		public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
				long contentLength) {
			Log.i("tag", "url=" + url);
			Log.i("tag", "userAgent=" + userAgent);
			Log.i("tag", "contentDisposition=" + contentDisposition);
			Log.i("tag", "mimetype=" + mimetype);
			Log.i("tag", "contentLength=" + contentLength);
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
	}

	/** 开始选择图片 */
	
	public void fileUpload_getImageFromPhone(final String js) {
		//{ "sourceType" : "c"  ,  "compressWidth" : "1900"  ,  "compressHeight" : "1220"  , "thumbnailWidth" : "100"  
		//, "thumbnailHeight" : "100"  ,  "imageFile" : "user122"  ,  "imageFormat" : "default"  ,  "args" : "undefined"  ,  "fileSize" : "0.5 "}
		this.js = js;
		startImgUpload();
		
	}

	private void startImgUpload() {
		imageUploadCompress.start(KhtActivity.this, js, LifeConstants.APP_IMG_URL, true,
				new ImageUpload.ImageUploadCallBack() {
					private int targetH;
					private int targetW;

					@Override
					public void onStart(CameraBean bean) {
						try {
							targetH = Integer.parseInt(bean.getThumbnailHeight());
							targetW = Integer.parseInt(bean.getThumbnailWidth());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					/** 返回并显示在WebView */
					@Override
					public void onFinish(File file, String fileName, String imgSize, String extraInfo) {
						if (file == null) {
							action.uploadPic(webView, "", ResultCode.OTHER_ERROR,"");
							return;
						}
						String imgPath = null;
						if (!(file.getName().toLowerCase().endsWith(".tif")
								|| file.getName().toLowerCase().endsWith(".tiff"))) {

							Bitmap bitmap = ThumbnailUtils.extractThumbnail(
									BitmapFactory.decodeFile(file.getAbsolutePath()), targetW, targetH,
									ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
							imgPath = ImgUtils.imgToBase64(file.getAbsolutePath());
						} 
						action.uploadPic( webView, imgPath,  ResultCode.SUCCESS,ImgUtils.getTypeFromName(file));
					}

					@Override
					public void errorMsg(String state, String errorMsg) {
						action.uploadPic(webView, "", state,"");
					}
				});
	}
	public void getLoginViewResultCall(){


		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				String userId = LoginUtil.getUserId(KhtActivity.this);

				if(!TextUtils.isEmpty(userId)){

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", LoginUtil.getUserId(KhtActivity.this));
					map.put("actoken", LoginUtil.getToken(KhtActivity.this));
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

				LoginUtil.authorize(KhtActivity.this, new LoginUtil.ILoginListener() {
					@Override
					public void onLogin() {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("userId", LoginUtil.getUserId(KhtActivity.this));
						map.put("actoken", LoginUtil.getToken(KhtActivity.this));
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

	
}
