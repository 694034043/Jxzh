package com.bocop.zyt.bocop.jxplatform.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.asynchttpclient.AsyncHttpClient;
import com.bocop.zyt.jx.baseUtil.asynchttpclient.AsyncHttpResponseHandler;
import com.bocop.zyt.jx.baseUtil.asynchttpclient.RequestHandle;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * @author luoyang
 * @version 创建时间：2016-4-7 
 */

@SuppressLint("SimpleDateFormat")
public class QztRequestWithJsonAll implements LoginUtil.ILoginListener {
	
	String msgcode;
	 String rtnmsg;

	/**
	 * 用户信息
	 */
	public BaseApplication baseApplication = BaseApplication.getInstance();

	/*
	 * http报文头信息
	 */
	public Context mContext;

	public QztRequestWithJsonAll(Context context) {
		this.mContext = context;
	}

	/**
	 * 
	 * @param context
	 *            :上下文
	 * @param url
	 *            ：中银开放平台CSP通用接口地址(需要验证)
	 * @param mcis
	 *            ：开放平台报文
	 * @param myCallback
	 *            ：回调
	 */

	public void postOpboc(String strGson,String strUrl,CallBackBoc callBack) {

		if (baseApplication.isNetStat()) {
			Log.i("tag", strUrl);
			post(strGson,strUrl,true,callBack);
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(mContext);
		}
	}
	
	public void postOpbocNoDialog(String strGson,String strUrl, CallBackBoc callBack) {

		if (baseApplication.isNetStat()) {
			Log.i("tag", strUrl);
			post(strGson,strUrl,false,callBack);
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(mContext);
		}
	}

	/**
	 * 
	 * @param context
	 *            :上下文
	 * @param url
	 *            ：中银开放平台CSP通用接口地址
	 * @param mcis
	 *            ：开放平台报文
	 * @param myCallback
	 *            ：回调 罗阳：于20150703封装加载框，并监听DIALOG的取消时间
	 */
	private RequestHandle post(String strGson, String url, boolean isShowDialog, final CallBackBoc callBack) {
		final String contentType = "application/json; charset=UTF-8";
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Content-Type", contentType);
		client.addHeader("Cache-Control", "no-cache");
		client.addHeader("Accept-Charset", "UTF-8");
		client.setTimeout(30 * 1000);
		final RequestHandle handle;
		@SuppressWarnings("deprecation")
		StringEntity strEntity = null;
		try {
			strEntity = new StringEntity(strGson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		final CustomProgressDialog dialog = new CustomProgressDialog(mContext,
				"...正在加载...", R.anim.frame);
		
		if (isShowDialog) {
			dialog.show();
		}
		handle = client.post(mContext, url, strEntity, contentType, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				Log.i("tag", "onStart");
				callBack.onStart();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				String strResponseBody = null;
				Log.i("tag", "onSuccess:" + String.valueOf(statusCode));
				try {
					strResponseBody = new String(responseBody, "UTF-8");
					Log.i("tag","APP的公共报头:" +URLDecoder.decode(headers[0].getValue(), "UTF-8") + "|||" + URLDecoder.decode(headers[1].getValue(), "UTF-8"));	
					Log.i("tag", "onSuccess:" + new String(responseBody,"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if(statusCode == 200){
					try {
						callBack.onSuccess(new String(responseBody,"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.i("tag2", "QztRequest call onSuccess msgcde ");
				}
				else{
					callBack.onFailure(String.valueOf(statusCode));
				}
				
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				try {
					Log.i("tag", "onFailure statusCode:" + String.valueOf(statusCode));
					if(responseBody != null){
						callBack.onFailure(String.valueOf(statusCode) + " "
								+ new String(responseBody, "UTF-8"));
					}else{
						callBack.onFailure(String.valueOf(statusCode));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFinish() {
				super.onFinish();
				callBack.onFinish();
				dialog.cancel();
			}

				});
		
		if (isShowDialog) {
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					handle.cancel(true);
				}
			});
			dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						dialog.cancel();
						Toast.makeText(mContext, "您取消了加载", Toast.LENGTH_SHORT).show();
						try{
							if(handle.cancel(true)){
								Log.i("tag1", "onCancel 取消 handle成功");
							}else{
								Log.i("tag1", "onCancel 取消handle失败");
							}
						}
						catch(Exception e){
							Log.i("tag", "异常");
						}
						
					}
					return true;
				}
			});
		}
		return handle;
	}

	public interface CallBackBoc {
		public void onStart();
		
		public void onSuccess(String responStr);

		public void onFailure(String responStr);

		public void onFinish();
	}

	@Override
	public void onLogin() {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "登陆成功", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onCancle() {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "请重新登陆，登录后才能办理相关业务", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "登陆错误", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onException() {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "登陆异常", Toast.LENGTH_LONG).show();
	}

}
