package com.bocop.zyt.bocop.jxplatform.trafficassistant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.bean.LicenseInfoBean;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.BocOpUtil;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.CustomInfo;
import com.bocop.zyt.bocop.jxplatform.util.JsonUtils;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.jxplatform.xml.CspRecForLicenseInfo;
import com.bocop.zyt.bocop.jxplatform.xml.CspXmlAPJJ06;
import com.bocop.zyt.bocop.yfx.base.EShareBaseActivity;
import com.bocop.zyt.bocop.yfx.bean.ImgUrl;
import com.bocop.zyt.bocop.yfx.bean.InfoStatusResponse;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.constants.Constants;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 易分享
 * 
 * @author lh
 * 
 */
@ContentView(R.layout.activity_traffic_phone)
public class TrafficPhoneActivity extends EShareBaseActivity {

	@ViewInject(R.id.iv_imageLeft)
	private ImageView ivLeft;
	@ViewInject(R.id.tv_titleName)
	private TextView tvTitle;
	// @ViewInject(R.id.ivSingleImage)
	private ImageView ivSingleImage;
	// @ViewInject(R.id.rltAd)
	private RelativeLayout rltAd;
	// @ViewInject(R.id.vpAd)
	private ViewPager vpAd;
	// @ViewInject(R.id.indicator)
	private CirclePageIndicator indicator;
	

	LicenseInfoBean licenseInfoBean;
	String id;	//身份证号
	String name;//姓名
	String oldPhone;//旧手机
	private List<View> views = new ArrayList<View>();

	InfoStatusResponse statusResponse;
	private List<ImgUrl> imgList = new ArrayList<ImgUrl>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
		// CacheBean.getInstance().put(CacheBean.CUST_ID, "001");// 测试
	}

	/**
	 * 工薪贷点击事件
	 * 
	 * @param v
	 */
	@OnClick({ R.id.llTrafficMan, R.id.llTrafficCar})
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.llTrafficMan:
			if (!CustomInfo.isExistCustomInfo(TrafficPhoneActivity.this)) {
				Log.i("tag", "客户信息不存在");
				if (LoginUtil.isLog(TrafficPhoneActivity.this)) {
					requestBocopForCustid(TrafficPhoneActivity.this,false,"lic");
				}

			} else {
				Log.i("tag", "客户信息存在");
				final SharedPreferences sp = this.getSharedPreferences(
						Constants.CUSTOM_PREFERENCE_NAME, Context.MODE_PRIVATE);
				id = sp.getString(Constants.CUSTOM_ID_NO, "anonymous");
				name = sp.getString(Constants.CUSTOM_USER_NAME, "anonymous");
				runLicActivity(id);
				//获取驾驶证手机号
//				repuestCspForLicenseDates();
				
			}
			break;
			
		case R.id.llTrafficCar:
			if (!CustomInfo.isExistCustomInfo(TrafficPhoneActivity.this)) {
				Log.i("tag", "客户信息不存在");
				if (LoginUtil.isLog(TrafficPhoneActivity.this)) {
					requestBocopForCustid(TrafficPhoneActivity.this,false,"car");
				}

			} else {
				Log.i("tag", "客户信息存在");
				final SharedPreferences sp = this.getSharedPreferences(
						Constants.CUSTOM_PREFERENCE_NAME, Context.MODE_PRIVATE);
				id = sp.getString(Constants.CUSTOM_ID_NO, "anonymous");
				name = sp.getString(Constants.CUSTOM_USER_NAME, "anonymous");
				runCarActivity(id,name);
			}
			break;
		}
	}
	
	private void runLicActivity(String id){
		Bundle bundle = new Bundle();
		bundle.putString("id", id);
		Intent intent = new Intent(TrafficPhoneActivity.this,LicensePhoneAddActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	private void runCarActivity(String id,String name){
		Bundle bundle = new Bundle();
		bundle.putString("id", id);
		bundle.putString("name", name);
		Intent intent = new Intent(TrafficPhoneActivity.this,CarPhoneAddActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * 检验时间是否在规定区间内
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private boolean checkTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");

		int nowTime = Integer.parseInt(sdf.format(new Date()));
		if (nowTime >= 70000 && nowTime <= 210000) {
			return true;
		}

		return false;
	}

	private void initView() {
		tvTitle.setText("更改预留手机号");
	}
	
	/**
	 * 查询客户id
	 * 
	 * @param cxt
	 */
	public  void requestBocopForCustid(final Context cxt,
			boolean isShowDialog,final String type) {
		if(!LoginUtil.isLog(cxt)){
			return;
		}
		
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("USRID", LoginUtil.getUserId(cxt));
		final String strGson = gson.toJson(map);
		Log.i("tag", "请求客户信息");

		BocOpUtil bocOpUtil = new BocOpUtil(cxt);
		bocOpUtil.postOpboc(strGson, TransactionValue.SA0053, isShowDialog,
				new BocOpUtil.CallBackBoc() {

					@Override
					public void onSuccess(String responStr) {
						Log.i("tag", responStr);
						try {
							final SharedPreferences sp = cxt
									.getSharedPreferences(
											Constants.CUSTOM_PREFERENCE_NAME,
											Context.MODE_PRIVATE);
							Editor editor = sp.edit();

							Map<String, String> map;
							map = JsonUtils.getMapStr(responStr);
							// 存储信息
							editor.putString(Constants.CUSTOM_ID_NO,
									map.get("idno"));// 身份证
							String id = map.get("cusid");
							editor.putString(Constants.CUSTOM_CUS_ID,
									map.get("cusid"));// 客户号
							editor.putString(Constants.CUSTOM_MOBILE_NO,
									map.get("mobileno"));// 手机号
							editor.putString(Constants.CUSTOM_USER_NAME,
									map.get("cusname"));// 用户名
							editor.putString(Constants.CUSTOM_FLAG, "1");// 客户信息标志
																			// 1：已获取信息。2、已经上传信息(FOR
																			// XMS)
							editor.putString(Constants.CUSTOM_LOG_FLAG, "1");// 客户信息标志传送日志
																				// 1：已获取信息。2、已经上传信息(FOR
																				// LOG)
							editor.commit();
							Log.i("tag", "获取客户信息：idno：" + map.get("idno")
									+ "cusid：" + map.get("cusid"));
							
							if(type.equals("lic")){
								if(id.length() > 10){
									runLicActivity(id);
								}
								else{
									Intent intent1 = new Intent(cxt, LicensePhoneAddActivity.class);
									startActivity(intent1);
								}
								
							}
							if(type.equals("car")){
								if(id.length() > 10){
									runLicActivity(id);
								}
								else{
									Intent intent1 = new Intent(cxt, CarPhoneAddActivity.class);
									startActivity(intent1);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onStart() {
						Log.i("tag", "发送GSON数据：" + strGson);
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(String responStr) {
					}
				});
	}
	
	/*
	 * 获取驾驶证 信息
	 */
	private void repuestCspForLicenseDates() {
		try {
			//生成CSP XML报文
			CspXmlAPJJ06 cspXmlForCarList = new CspXmlAPJJ06(LoginUtil.getUserId(this));
			String strXml = cspXmlForCarList.getCspXml();
			//生成MCIS报文
			Mcis mcis = new Mcis(strXml,TransactionValue.APJJ06);
			final byte[] byteMessage = mcis.getMcis();
			//发送报文
			CspUtil cspUtil = new CspUtil(this);
			Log.i("tag", "发送报文： " + new String(byteMessage, "GBK"));
			cspUtil.postCspLogin(byteMessage, new CspUtil.CallBack() {
				@Override
				public void onSuccess(String responStr) {
					try {
						
						licenseInfoBean = CspRecForLicenseInfo.readStringXml(responStr);
						if(licenseInfoBean.getErrorcode().equals("00")  || licenseInfoBean.getErrorcode().equals("99")){
							Bundle bundle = new Bundle();
							bundle.putString("id", licenseInfoBean.getErrorcode());
							bundle.putString("name", licenseInfoBean.getErrormsg());
							bundle.putString("phone", licenseInfoBean.getTel());
							Intent intent = new Intent(
									TrafficPhoneActivity.this,
									LicensePhoneAddActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						}else {
							Toast.makeText(TrafficPhoneActivity.this,licenseInfoBean.getErrormsg(), Toast.LENGTH_SHORT).show();
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFinish() {

				}

				@Override
				public void onFailure(String responStr) {
					if(responStr.equals("0")){
						Toast.makeText(TrafficPhoneActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(TrafficPhoneActivity.this, responStr, Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
