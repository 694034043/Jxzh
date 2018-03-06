package com.bocop.zyt.bocop.gm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.gm.action.GMAction;
import com.bocop.zyt.bocop.gm.bean.ContactsInfo;
import com.bocop.zyt.bocop.gm.utils.ContactsUtil;
import com.bocop.zyt.bocop.gm.utils.HybridCallBack;
import com.bocop.zyt.bocop.gm.utils.HybridUtil;
import com.bocop.zyt.bocop.gm.view.InputDialog;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.bocop.zyt.model.NUser;
import com.bocop.zyt.fmodule.utils.FStringUtil;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;

import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.ShareSDK;
import sharesdk.onekeyshare.OnekeyShare;

public class GoldManagerActivity extends BaseActivity {

	WebView wbMain;
	private Pattern pattern;
	ProgressBar loadingProgressBar;

	String url = BocSdkConfig.LLYHURL;
	String userId = "";
	String actoken = "";
	String client = "Android";

	private GMAction action;
	private InputDialog dialog;

	/**
	 * 联系人列表
	 */
	private List<String> ciList = new ArrayList<String>();
	private int count = 0;
	private int page = 0;
	
	
	public boolean is_show_game=false;
	private String title;
	private RelativeLayout rl_actionbar;
	private TextView tv_actionbar_title;
	public static void startAct(Context context,String url,String title){
		Intent intent=new Intent(context,GoldManagerActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cpf_activity_main);
		wbMain=(WebView) findViewById(R.id.wbMain);
		urlInput = (EditText) findViewById(R.id.et_id);
		btOk = (Button) findViewById(R.id.bt_ok);
		btOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inputUrl = urlInput.getText().toString();
				if(!TextUtils.isEmpty(inputUrl)){
					url = "http://"+inputUrl;
					if(LoginUtil.isLog(GoldManagerActivity.this)){
						url=url+"?userId="+LoginUtil.getUserId(GoldManagerActivity.this)+"&token="+LoginUtil.getToken(GoldManagerActivity.this)+"&clientid="+BocSdkConfig.CONSUMER_KEY;
						/*url=IURL.Tie_hong_yun_tong_new_url+"?userId="+LoginUtil.getUserId(this)+"&token="+LoginUtil.getToken(this)+
						"&count=0&client=android&mobilePhone="+LoginUtil.getUserId(this);*/
					}
				}
				loadURL();
			}
		});
		loadingProgressBar=(ProgressBar) findViewById(R.id.loadingProgressBar);
		rl_actionbar=(RelativeLayout) findViewById(R.id.rl_actionbar);
		tv_actionbar_title=(TextView) findViewById(R.id.tv_actionbar_title);
		
		
		action = new GMAction(this);
		initData();
		setWebViewSetting(wbMain);
		initWebView();
//		showURLDialog();
		try {
			url=getIntent().getExtras().getString("url");
			title=getIntent().getExtras().getString("title");
			rl_actionbar.setVisibility(View.VISIBLE);
			tv_actionbar_title.setText(title);
		} catch (Exception e) {
			// TODO: handle exception
			url=null;
		}
		
		if(FStringUtil.is_empty(url)){
			is_show_game=true;
			rl_actionbar.setVisibility(View.GONE);
		}
		//url = "http://119.29.107.253:8080/llyh";
		if(LoginUtil.isLog(this)){
			url= IURL.Tie_hong_yun_tong_new_url+"?userId="+LoginUtil.getUserId(this)+"&token="+LoginUtil.getToken(this)+"&clientid="+BocSdkConfig.CONSUMER_KEY;
			/*url=IURL.Tie_hong_yun_tong_new_url+"?userId="+LoginUtil.getUserId(this)+"&token="+LoginUtil.getToken(this)+
			"&count=0&client=android&mobilePhone="+LoginUtil.getUserId(this);*/
		}else{
			url = IURL.Tie_hong_yun_tong_new_url;
		}
		loadURL();
//		showURLDialog();

	};
	
	
	  public void fun_home_press(View view){
	        finish();
	    }

	    @Override
	    public void fun_back_press(View v) {
//	        super.fun_back_press(v);
	            if (wbMain.canGoBack()) {
	            	wbMain.goBack();
	            } else {
	                finish();
	            }
	    }

	private void initData() {

		userId = LoginUtil.getUserId(this);
		actoken = LoginUtil.getToken(this);

	}

	private void loadURL() {
		wbMain.loadUrl(url);
	}

	private void initWebView() {
		wbMain.clearCache(true);
		wbMain.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.i("TAG", "shouldOverrideUrlLoading_url" + url);
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
				wbMain.loadUrl(url);
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

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if (loadingProgressBar != null) {
					loadingProgressBar.setVisibility(View.VISIBLE);
					Log.i("TAG", "进度显示");
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				if (loadingProgressBar != null) {
					loadingProgressBar.setVisibility(View.GONE);
					Log.i("TAG", "进度隐藏");
				}
			}

			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				// super.onReceivedSslError(view, handler, error);
				handler.proceed();
			}
		});

		wbMain.setWebChromeClient(new WebChromeClient() {

			private int oldProgress;

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (loadingProgressBar != null && newProgress > oldProgress) {
					oldProgress = newProgress;
					loadingProgressBar.setProgress(newProgress);
					Log.i("TAG", newProgress + "进度");
					if (newProgress == 100) {
						loadingProgressBar.setVisibility(View.GONE);
						// mHandler.sendEmptyMessage(CASE_USERINFO);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("userId", userId);
						map.put("actoken", actoken);
						map.put("client", client);
						// doShare();
						// HybridUtil.getInstance().handleJsRequest(wbMain,
						// "getUserInfoCall", map, new HybridCallBack() {
						//
						// @Override
						// public void errorMsg(Exception e) {
						// // TODO Auto-generated method stub
						//
						// }
						// });
					}
				}
			}

		});
	}

	@SuppressLint("SetJavaScriptEnabled")
	public static WebSettings setWebViewSetting(WebView webView) {
		if (webView == null) {
			Log.i("TAG", "webView cann't null");
			return null;
		}
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);// 可以运行javaScript

		settings.setUseWideViewPort(false);// 设置自适应屏幕大小
		settings.setLoadWithOverviewMode(false);
		settings.setAppCacheEnabled(false); // 退出清除缓存

		settings.setDomStorageEnabled(true);
		settings.setSupportZoom(false);// 是否支持缩放
		settings.setBuiltInZoomControls(false);// 是否显示缩放按钮

		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		return settings;
	}

	@Override
	protected void onDestroy() {
		if (wbMain != null) {
			wbMain.stopLoading();
			wbMain.clearCache(true);
			wbMain.destroy();
			wbMain = null;
		}
		super.onDestroy();
	}

	/**
	 * 按键事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// if (wbMain.canGoBack()) {
			// wbMain.goBack();
			// return true;
			// } else {
			// if (!canFinish && mHandler != null) {
			// mHandler.postDelayed(new Runnable() {
			//
			// @Override
			// public void run() {
			// canFinish = false;
			// }
			// }, 2000);
			// } else if (canFinish) {
			finish();
			// }
			// if (!canFinish) {
			// Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			// canFinish = true;
			// }
			// }
			break;
		case KeyEvent.KEYCODE_MENU:
			break;
		}
		return false;
	}

	/**
	 * 显示URL输入框
	 */
	private void showURLDialog() {
		dialog = new InputDialog(this, new InputDialog.OnOKClickListener() {

			@Override
			public void onOKClick(String url) {
				if (" ".equals(url)) {
					url = "172.23.16.34:8080/dfb_app";
				} else if ("1".equals(url)) {
					url = "192.168.22.222/game1/index.html";
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				} else if ("2".equals(url)) {
					url = "58.250.71.52:8080/dfb_app";
				}
//				GoldManagerActivity.this.url = url+"?userId="+LoginUtil.getUserId(GoldManagerActivity.this)+"&token="+LoginUtil.getToken(GoldManagerActivity.this)+"&count=0&client=android";
				GoldManagerActivity.this.url = url;
				loadURL();
				dialog.dismiss();
			}
		});

		dialog.etURL.setText(url);

	}

	int i = 0;

	/**
	 * 获取联系人信息列表
	 */
	public void getContactsInfo(int page) {
		this.page = page;
		if (ciList != null && ciList.size() > 0) {
			String hasNext = "";
			if (page >= ciList.size()) {
				hasNext = "0";
			} else {
				hasNext = "1";
			}
			String param = getUploadStr(ciList.get(page - 1), hasNext);
			System.out.println("上传的内容：" + param);

			HybridUtil.getInstance().handleJsRequest(wbMain, "getContactsInfo", param, new HybridCallBack() {

				@Override
				public void errorMsg(Exception e) {
					// TODO Auto-generated method stub
					System.out.println("*********上传参数失败");

				}
			});
		} else {
			new ContactTask(this).execute("");
		}
		System.out.println("通讯录调用次数：" + (++i) + "------查询第" + page + "页");
	}

	/**
	 * 上传用户信息
	 */
	public void getUserInfoCall() {

		// 每次调用去除 tipsFlas
		SharedPreferences sp = getSharedPreferences("tipsFlag", Context.MODE_PRIVATE);
		final String tipsFlag = sp.getString("flagKey", "");
		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("actoken", actoken);
				map.put("client", client);
				map.put("tipsFlag", tipsFlag);
				if (!TextUtils.isEmpty(cacheBean.getCount())) {
					map.put("count", cacheBean.getCount());
				} else {
					map.put("count", "0");
				}
				HybridUtil.getInstance().handleJsRequest(wbMain, "getUserInfoCall", map, new HybridCallBack() {

					@Override
					public void errorMsg(Exception e) {
						// TODO Auto-generated method stub

					}
				});

			}
		});
	}

	/**
	 * 设置横竖屏
	 * 
	 * @param param
	 *            0竖屏 1横屏
	 */
	public void screenDirection(String param) {
		if ("0".equals(param)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else if ("1".equals(param)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}

	public BaseApplication baseApplication = BaseApplication.getInstance();
	private EditText urlInput;
	private Button btOk;
	private String inputUrl;

	@SuppressLint("SdCardPath")
	public void doGetLoginViewResult() {
		_handler.post(new Runnable() {
			
			@Override
			public void run() {
				LoginHelper.get_instance(_act).login(_act, new LoginHelper.LoginCallback() {
					
					@Override
					public void suc() {
						NUser.LoginInfo u = LoginHelper.get_instance(_act).get_login_info();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("userid", u.userid);
						map.put("token", u.access_token);
						map.put("client", client);
						map.put("tipsFlag", "xxxx");
						if (!TextUtils.isEmpty(cacheBean.getCount())) {
							map.put("count", cacheBean.getCount());
						} else {
							map.put("count", "0");
						}
						HybridUtil.getInstance().handleJsRequest(wbMain, "getLoginViewResultCall", map,
								new HybridCallBack() {

									@Override
									public void errorMsg(Exception e) {
										ILOG.log_4_7(""+e.getMessage());
									}
								});
					}
					
					@Override
					public void fali() {
						// TODO Auto-generated method stub
						
					}
				});
				
				
			}
		});

	}

	@SuppressLint("SdCardPath")
	public void doShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("易互通");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		// oks.setTitleUrl("http://www.boc.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("易互通");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/bocjx.png");// 确保SDcard下面存在此张图片
		// oks.setImagePath(getResources().getResourcePackageName(R.drawable.bocjx));
		// url仅在微信（包括好友和朋友圈）中使用
		// 易慧通
		// oks.setImageUrl("http://open.boc.cn/appstore/#/app/appDetail/38201");
		oks.setImageUrl("http://pre.im/zhyqt");
		// 易慧通连接
		// oks.setUrl("http://open.boc.cn/appstore/#/app/appDetail/38201");
		// 易互通
		oks.setUrl("http://pre.im/zhyqt");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		// oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		// oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		// oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI

		oks.show(this);
	}

	class ContactTask extends AsyncTask<String, Integer, List<String>> {

		private Context mContext;

		private long startTime = 0;
		private long endTime = 0;
		private long midTime = 0;

		public ContactTask(Context context) {
			super();
			this.mContext = context;
		}

		@Override
		protected List<String> doInBackground(String... params) {
			startTime = System.currentTimeMillis();
			// List<ContentValues> findLinkmanInfo =
			// ContactsUtil.findLinkmanInfo(mContext, values);

			// List<ContactsInfo> list = new ArrayList<>();
			List<ContactsInfo> list = ContactsUtil.getAllPhoneContacts(GoldManagerActivity.this);
			System.out.println("获取：------" + list.size() + "条联系人信息");
			midTime = System.currentTimeMillis();
			JSONArray array = new JSONArray();
			JSONObject jsonObject;
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if (!"__DUMMY CONTACT from runtime permissions sample".equals(list.get(i).getN())) {
						jsonObject = new JSONObject();
						try {
							jsonObject.put("n", list.get(i).getN());
							jsonObject.put("p", list.get(i).getP());
							jsonObject.put("k", list.get(i).getSortKey());
							System.out.println("Name:" + list.get(i).getN() + "------Phone:" + list.get(i).getP()
									+ "----SortKey:" + list.get(i).getSortKey());
							array.put(jsonObject);
						} catch (Exception e) {
							e.printStackTrace();
						}
						count++;
						if (count == Integer.MAX_VALUE) {
							ciList.add(array.toString());
							System.out.println("已添加数据：------" + array.length() + "条");
							try {
								array = new JSONArray("[]");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							count = 0;
						} else if (i == list.size() - 1) {
							ciList.add(array.toString());
							System.out.println("已添加数据：------" + array.length() + "条");
							try {
								array = new JSONArray("[]");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							count = 0;
						}
					}
				}
				System.out.println("联系人信息已分为：-------" + ciList.size() + "页");
				// return array.toString();
				return ciList;
			} else {
				return ciList;
			}

		}

		@Override
		protected void onPostExecute(List<String> result) {
			super.onPostExecute(result);

			endTime = System.currentTimeMillis();

			System.out.println("获取通讯录耗时：" + (midTime - startTime) + "---转JSON耗时：" + (endTime - midTime));
			System.out.println(result);

			String hasNext = "";
			String param = "";
			if (ciList != null && ciList.size() > 0) {
				if (page >= ciList.size()) {
					hasNext = "0";
				} else {
					hasNext = "1";
				}
				param = getUploadStr(ciList.get(page - 1), hasNext);
				System.out.println("上传的内容：" + param);
			}
			HybridUtil.getInstance().handleJsRequest(wbMain, "getContactsInfo", param, new HybridCallBack() {

				@Override
				public void errorMsg(Exception e) {
					// TODO Auto-generated method stub
					System.out.println("*********上传参数失败");
				}
			});
		}

	}

	/**
	 * 拼接最终要上传的字符串
	 * 
	 * @param content
	 *            拼接好的50条联系人
	 * @param hasNext
	 *            是否有下一页 0否 1是
	 * @return
	 */
	private String getUploadStr(String content, String hasNext) {
		return "{\\'c\\':" + content + ",\\'np\\':\\'" + hasNext + "\\'}";
	}

	public String getLocalHostIp() {
		String ipaddress = "";
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			// 遍历所用的网络接口
			while (en.hasMoreElements()) {
				NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				// 遍历每一个接口绑定的所有ip
				while (inet.hasMoreElements()) {
					InetAddress ip = inet.nextElement();
					if (!ip.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ip.getHostAddress())) {
						return ipaddress = "本机的ip是" + "：" + ip.getHostAddress();
					}
				}

			}
		} catch (SocketException e) {
			Log.e("feige", "获取本地ip地址失败");
			e.printStackTrace();
		}
		return ipaddress;

	}

}
