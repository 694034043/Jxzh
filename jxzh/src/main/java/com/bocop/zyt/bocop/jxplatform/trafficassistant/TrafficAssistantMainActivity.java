package com.bocop.zyt.bocop.jxplatform.trafficassistant;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.riders.IllegalprocessActivity;
import com.bocop.zyt.bocop.jxplatform.adapter.CarAdapter;
import com.bocop.zyt.bocop.jxplatform.adapter.CarListAdapter;
import com.bocop.zyt.bocop.jxplatform.adapter.TrafficMainAdapter;
import com.bocop.zyt.bocop.jxplatform.bean.CarListBean;
import com.bocop.zyt.bocop.jxplatform.bean.CarListXmlBean;
import com.bocop.zyt.bocop.jxplatform.bean.CspRecHeaderBean;
import com.bocop.zyt.bocop.jxplatform.bean.LicenseInfoBean;
import com.bocop.zyt.bocop.jxplatform.bean.PerFunctionBean;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.ActivityForResultCode;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.CustomInfo;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.bocop.jxplatform.xml.CspRecForCarList;
import com.bocop.zyt.bocop.jxplatform.xml.CspRecForLicenseInfo;
import com.bocop.zyt.bocop.jxplatform.xml.CspRecHeader;
import com.bocop.zyt.bocop.jxplatform.xml.CspXmlAPJJ04;
import com.bocop.zyt.bocop.jxplatform.xml.CspXmlAPJJ06;
import com.bocop.zyt.bocop.jxplatform.xml.CspXmlForCarList;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_trafficassmain)
public class TrafficAssistantMainActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.iv_imageLeft)
	private BackButton backBtn;
	@ViewInject(R.id.bt_traffic_add_car)
	TextView bt_traffic_add_car;

	@ViewInject(R.id.lvtra)
	private ListView traListView;
	// ???????????????
	@ViewInject(R.id.iv_traffic_main_loading_bar)
	private ImageView iv_loading_progress_bar;
	@ViewInject(R.id.ll_traffic_main_loading)
	View ll_traffic_main_loading;

	private AnimationDrawable animationDrawable;

	private static final int INITIAL_DELAY_MILLIS = 300;
	List<CarListBean> carDates = new ArrayList<CarListBean>();
	List<CarListBean> carDatesReq = new ArrayList<CarListBean>();
	CarAdapter carAdapter;

	// ????????????Adapter
	TrafficMainAdapter traAdapter;
	ArrayAdapter<String> adapter;
	@ViewInject(R.id.lv_car_dynamic)
	DynamicListView listView;
	SimpleSwipeUndoAdapter simpleSwipeUndoAdapter;
	AlphaInAnimationAdapter animAdapter;

	List<PerFunctionBean> traDates = new ArrayList<PerFunctionBean>();
	
	String strOwnerName;
	String strIdNo;
	LicenseInfoBean licenseInfoBean;
	public BaseApplication baseApplication = BaseApplication.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initEvent();
		// ??????????????????????????????
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// ?????????????????????????????????
		if (requestCode == ActivityForResultCode.CodeForLicenseAdd
				&& resultCode == RESULT_OK) {
			// ???????????????????????????????????????
			requestCspForCarList();
		}
		// ?????????????????????????????????
		if (requestCode == ActivityForResultCode.CodeForCarGo
				&& resultCode == RESULT_OK) {
			// ???????????????????????????????????????
			Log.i("tag", "?????????????????????");
			requestCspForCarList();
		}
		
		// ?????????????????????????????????????????????
		if (requestCode == ActivityForResultCode.CodeForLicenseAdd
				&& resultCode == RESULT_OK) {
		}
	}

	private void displayProgress() {
		ll_traffic_main_loading.setVisibility(View.VISIBLE);
		listView.setVisibility(View.INVISIBLE);
		animationDrawable.start();
	}

	private void initView() {
		tv_titleName.setText("????????????");
		iv_loading_progress_bar.setBackgroundResource(R.anim.my_progress_bar);
		animationDrawable = (AnimationDrawable) iv_loading_progress_bar
				.getBackground();

		//????????????????????????????????????????????????????????????
		initTraDates();
		traAdapter = new TrafficMainAdapter(TrafficAssistantMainActivity.this,
				traDates, R.layout.item_quickpay);
		traListView.setAdapter(traAdapter);
		requestCspForCarList();
	}


	private void initEvent() {

		traListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ????????????
				if (arg2 == 0) {
					Intent intent = new Intent(
							TrafficAssistantMainActivity.this,
							TrafficQuickPayActivity.class);
					startActivityForResult(intent,
							ActivityForResultCode.CodeForCarAdd);
				}
				// ???????????????
				if (arg2 == 2) {
					repuestCspForLicenseDates();
				}
				//????????????
				if(arg2 == 1) {
					if (baseApplication.isNetStat()){
						Intent intent = new Intent(
								TrafficAssistantMainActivity.this,
								MyPeccancyActivity.class);
						startActivity(intent);
					}else{
						CustomProgressDialog.showBocNetworkSetDialog(TrafficAssistantMainActivity.this);
					}
					
				}
				//????????????
				if(arg2 == 3){
					Intent intent = new Intent(TrafficAssistantMainActivity.this,
							PayHisActivity.class);
					startActivity(intent);
				}
				
				if(arg2 == 4){
					Intent intent = new Intent(TrafficAssistantMainActivity.this,
							IllegalprocessActivity.class);
					startActivity(intent);
				}
				
				// ???????????????
				if (arg2 == 5) {
					if (!CustomInfo.isExistCustomInfo(TrafficAssistantMainActivity.this)) {
						Log.i("LoginUtil", "requestBocopForCustid");
						if (LoginUtil.isLog(TrafficAssistantMainActivity.this)) {
							CustomInfo.requestBocopForCustid(TrafficAssistantMainActivity.this, false);
						}

					} else {
						Log.i("LoginUtil", "postIdForXms");
//						CustomInfo.postLog(baseActivity, homeLifeLogEvent[position]);
					}
					
					callMe(TrafficPhoneActivity.class);
				}

			}
		});

		// ??????????????????
		bt_traffic_add_car.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TrafficAssistantMainActivity.this,
						CarAddActivity.class);
				startActivityForResult(intent,
						ActivityForResultCode.CodeForLicenseAdd);
			}
		});
	}

	private void initTraDates() {
		PerFunctionBean funBean3 = new PerFunctionBean("myLicenseNum",
				R.drawable.icon_mylicense, "???????????????");
		PerFunctionBean funBean2 = new PerFunctionBean("mycarpeccancy",
				R.drawable.icon_mypeccancy, "????????????");
		PerFunctionBean funBean1 = new PerFunctionBean("myQuickPay",
				R.drawable.icon_quickpay, "????????????");
		PerFunctionBean funBean4 = new PerFunctionBean("myPhone",
				R.drawable.icon_traffic_jiaofa, "????????????");
		PerFunctionBean funBean6 = new PerFunctionBean("myPhone",
				R.drawable.icon_quickpay, "???????????????????????????");
		PerFunctionBean funBean5 = new PerFunctionBean("myLiuChen",
				R.drawable.icon_traffic_liuchen, "????????????????????????");
		traDates.add(funBean1);
		traDates.add(funBean2);
		traDates.add(funBean3);
		traDates.add(funBean4);
		traDates.add(funBean5);
		traDates.add(funBean6);
	}

	private void initCarDates(CarListXmlBean xmlBean ) {
		CarListXmlBean carListXmlBean = xmlBean;
		Log.i("tag", "initCarDates1");
		String[] licenseNum = xmlBean.getLicenseNumList(); 
		Log.i("tag", "initCarDates2");
		carDatesReq.clear();
		carDates.clear();
		for (int i = 0; i < licenseNum.length; i++) {
			CarListBean bean = new CarListBean();
			bean.setBtCarPeccancy("????????????");
			bean.setImageCarRes(R.drawable.transport_selector);
			bean.setTbLicenseNumber(licenseNum[i]);
			carDatesReq.add(bean);
		}
	}
	/**
	 * ????????????????????????????????????
	 */
	public void requestCspForCarList() {
		try {
			//??????CSP XML??????
			CspXmlForCarList cspXmlForCarList = new CspXmlForCarList(LoginUtil.getUserId(this));
			String strXml = cspXmlForCarList.getCspXml();
			//??????MCIS??????
			Mcis mcis = new Mcis(strXml,TransactionValue.APJJ02);
			final byte[] byteMessage = mcis.getMcis();
			//????????????
			CspUtil cspUtil = new CspUtil(this);
			Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
			cspUtil.postCspLogin(byteMessage, new CspUtil.CallBack() {
				@Override
				public void onSuccess(String responStr) {
					CarListXmlBean carListXmlBean = CspRecForCarList.readStringXml(responStr);
					if(!carListXmlBean.getErrorcode().equals("00")){
						Toast.makeText(TrafficAssistantMainActivity.this,carListXmlBean.getErrormsg(), Toast.LENGTH_SHORT).show();
						carDates.clear();
						carViewBind();
					}
					else{
						Log.i("tag", "initCarDates");
					initCarDates(carListXmlBean);
					carDates.addAll(carDatesReq);
					carViewBind();
					}
				}
				
				@Override
				public void onFinish() {
				}
				
				@Override
				public void onFailure(String responStr) {
					CspUtil.onFailure(TrafficAssistantMainActivity.this, responStr);
				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void carViewBind() {
		adapter = new CarListAdapter(this, carDates);
		simpleSwipeUndoAdapter = new SimpleSwipeUndoAdapter(adapter, this,
				new MyOnDismissCallback(adapter));
		animAdapter = new AlphaInAnimationAdapter(simpleSwipeUndoAdapter);
		animAdapter.setAbsListView(listView);
		assert animAdapter.getViewAnimator() != null;
		animAdapter.getViewAnimator().setInitialDelayMillis(
				INITIAL_DELAY_MILLIS);
		listView.setAdapter(animAdapter);
		listView.enableDragAndDrop();
		listView.setDraggableManager(new TouchViewDraggableManager(
				R.id.list_row_draganddrop_touchview));
		listView.enableSimpleSwipeUndo();
	}
	
	private void repuestCspForLicenseDates() {
		try {
			//??????CSP XML??????
			CspXmlAPJJ06 cspXmlForCarList = new CspXmlAPJJ06(LoginUtil.getUserId(this));
			String strXml = cspXmlForCarList.getCspXml();
			//??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.APJJ06);
			final byte[] byteMessage = mcis.getMcis();
			//????????????
			CspUtil cspUtil = new CspUtil(this);
			Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
			cspUtil.postCspLogin(byteMessage, new CspUtil.CallBack() {
				@Override
				public void onSuccess(String responStr) {
					try {
						
						licenseInfoBean = CspRecForLicenseInfo.readStringXml(responStr);
						if(licenseInfoBean.getErrorcode().equals("00")  || licenseInfoBean.getErrorcode().equals("99")){
							Bundle bundle = new Bundle();
							bundle.putString("errorCode", licenseInfoBean.getErrorcode());
							bundle.putString("errorMsg", licenseInfoBean.getErrormsg());
							Log.i("tag", licenseInfoBean.getErrormsg());
							bundle.putString("Ownername", licenseInfoBean.getOwnername());
							bundle.putString("Drivenum", licenseInfoBean.getDrivenum());
							bundle.putString("Filenum", licenseInfoBean.getFilenum());
							bundle.putString("Tel", licenseInfoBean.getTel());
							bundle.putString("Quasidriving", licenseInfoBean.getQuasidriving());
							bundle.putString("Penaltyscore", licenseInfoBean.getPenaltyscore());
							bundle.putString("State", licenseInfoBean.getState());
							bundle.putString("Replacementdate", licenseInfoBean.getReplacementdate());
							Intent intent = new Intent(
									TrafficAssistantMainActivity.this,
									LicenseInfoActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						}else {
							Intent intent = new Intent(
									TrafficAssistantMainActivity.this,
									LicenseInfoActivity.class);
							startActivity(intent);
							Toast.makeText(TrafficAssistantMainActivity.this,licenseInfoBean.getErrormsg(), Toast.LENGTH_SHORT).show();
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
						Toast.makeText(TrafficAssistantMainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(TrafficAssistantMainActivity.this, responStr, Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private class MyOnDismissCallback implements OnDismissCallback {

		private final ArrayAdapter<String> mAdapter;

		@Nullable
		private Toast mToast;

		MyOnDismissCallback(final ArrayAdapter<String> adapter) {
			mAdapter = adapter;
		}

		@Override
		public void onDismiss(@NonNull final ViewGroup listView,
				@NonNull final int[] reverseSortedPositions) {
			for (int position : reverseSortedPositions) {
				Log.i("tag","adapter:" + adapter.getItem(position).toString());
				requestCspForCarGo(adapter.getItem(position).toString().trim(), position);
			}
		}
		
		private void requestCspForCarGo(String licenseNUm,int position) {
			try {
				//??????CSP XML??????
				final int positionCarGo = position;
				CspXmlAPJJ04 cspXmlAPJJ04 = new CspXmlAPJJ04(licenseNUm);
				String strXml = cspXmlAPJJ04.getCspXml();
				//??????MCIS??????
				Mcis mcis = new Mcis(strXml,TransactionValue.APJJ04);
				final byte[] byteMessage = mcis.getMcis();
				//????????????
				Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
				CspUtil cspUtil = new CspUtil(TrafficAssistantMainActivity.this);
				cspUtil.postCspLogin(byteMessage, new CspUtil.CallBack() {
					
					@Override
					public void onSuccess(String responStr) {
						Log.i("tag", responStr);
						try {
							CspRecHeaderBean cspRecHeaderBean = CspRecHeader.readStringXml(responStr);
							if(cspRecHeaderBean.getErrorcode().equals("00")){
								Toast.makeText(TrafficAssistantMainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
								Log.i("tag2","requestCspForCarGo" );
								mAdapter.remove(positionCarGo);
							}
							else{
								Toast.makeText(TrafficAssistantMainActivity.this, cspRecHeaderBean.getErrormsg(), Toast.LENGTH_LONG).show();
							}
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFinish() {
					}
					
					@Override
					public void onFailure(String responStr) {
					}
				});
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
}
