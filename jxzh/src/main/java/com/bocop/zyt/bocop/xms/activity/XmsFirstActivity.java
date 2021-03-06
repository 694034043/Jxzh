package com.bocop.zyt.bocop.xms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.adapter.TrafficMainAdapter;
import com.bocop.zyt.bocop.jxplatform.bean.PerFunctionBean;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.BocOpUtil;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.JsonUtils;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtilAnother;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.bocop.xms.bean.DialogCostType;
import com.bocop.zyt.bocop.xms.bean.UserResponse;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.xms.view.CostTypeDialog;
import com.bocop.zyt.bocop.xms.xml.CspXmlXms004;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.constants.Constants;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.xms_activity_first)
public class XmsFirstActivity extends BaseActivity implements LoginUtilAnother.ILoginListener {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.iv_imageLeft)
	private BackButton backBtn;
	@ViewInject(R.id.lvadvice)
	private ListView traListView;

	@ViewInject(R.id.lvSignMamager)
	private ListView lvSingManager;

	@ViewInject(R.id.lvMessage)
	private ListView lvMessage;

	private DialogCostType costType;

	private BaseApplication baseApplication = BaseApplication.getInstance();
	List<PerFunctionBean> traDates = new ArrayList<PerFunctionBean>();
	List<PerFunctionBean> singManagerDates = new ArrayList<PerFunctionBean>();
	List<PerFunctionBean> messageDates = new ArrayList<PerFunctionBean>();
	TrafficMainAdapter traAdapter;
	TrafficMainAdapter signManagerAdapter;
	TrafficMainAdapter messageAdapter;

	private static final int USER_LIST_SUCCESS = 0;
	private static final int USER_FAILED = 1;
	
	private int flag;

	String strCusName;
	String strIdNo;
	String strCusid;

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case USER_LIST_SUCCESS:
				String content = (String) msg.obj;
				UserResponse userResponse = XStreamUtils.getFromXML(content,
						UserResponse.class);
				// ?????????????????????
				if ("01".equals(userResponse.getConstHead().getErrCode())) {
					// 07:????????????????????????
					Log.i("tag", "??????????????????");
					if ("07".equals(costType.getTypeCode())) {
						requestBocopForUseridQuery();
					}
					// ????????????????????????
					else {
						Intent intent = new Intent(XmsFirstActivity.this,
								SignContractActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("TITLE", "??????");
						bundle.putString("COST_TYPE", costType.getCostName());
						bundle.putString("TYPE_CODE", costType.getTypeCode());
						intent.putExtra("BUNDLE", bundle);
						startActivity(intent);
					}
					// ?????????????????????????????????
				} else {
					Log.i("tag", "???????????????" + costType.getTypeCode());
					Intent intent = new Intent(XmsFirstActivity.this,
							UserManagerActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("COST_TYPE", costType.getCostName());
					bundle.putString("TYPE_CODE", costType.getTypeCode());
					bundle.putString("USER_LIST", (String) msg.obj);
					intent.putExtra("BUNDLE", bundle);
					startActivity(intent);
				}
				break;

			case USER_FAILED:
				String responStr = (String) msg.obj;
				CspUtil.onFailure(XmsFirstActivity.this, responStr);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tv_titleName.setText("?????????");
		//????????? ?????????????????????
		flag = 0;
		initView();
		initEvent();
	}

	private void initView() {

		initTraDates();

		traAdapter = new TrafficMainAdapter(XmsFirstActivity.this, traDates,
				R.layout.item_quickpay);
		traListView.setAdapter(traAdapter);
		signManagerAdapter = new TrafficMainAdapter(XmsFirstActivity.this,
				singManagerDates, R.layout.item_quickpay);
		lvSingManager.setAdapter(signManagerAdapter);
		messageAdapter = new TrafficMainAdapter(XmsFirstActivity.this,
				messageDates, R.layout.item_quickpay);
		lvMessage.setAdapter(messageAdapter);
	}

	/*
	 * ???????????????
	 */
	private void initTraDates() {
		PerFunctionBean funBean1 = new PerFunctionBean("exchange",
				R.drawable.icon_xms_rate, "????????????");
		PerFunctionBean funBean2 = new PerFunctionBean("rate",
				R.drawable.icon_xms_deposit, "???/????????????");
		// PerFunctionBean funBean3 = new PerFunctionBean("rate",
		// R.drawable.icon_xms_metal, "???????????????");
		PerFunctionBean funBean4 = new PerFunctionBean("rate",
				R.drawable.icon_xms_org, "????????????");
		PerFunctionBean funBean5 = new PerFunctionBean("rate",
				R.drawable.icon_xms_invest, "????????????");
		PerFunctionBean funBean6 = new PerFunctionBean("rate",
				R.drawable.icon_xms_fund, "????????????");
		PerFunctionBean funBean7 = new PerFunctionBean("rate",
				R.drawable.icon_xms_atm, "ATM??????");
		PerFunctionBean funBean8 = new PerFunctionBean("train",
				R.drawable.icon_xms_train, "???????????????");
		PerFunctionBean funBean9 = new PerFunctionBean("flight",
				R.drawable.icon_xms_fight, "???????????????");
		PerFunctionBean funBean10 = new PerFunctionBean("flight",
				R.drawable.icon_xms_dotbooking, "????????????");
		PerFunctionBean funBean11 = new PerFunctionBean("flight",
				R.drawable.icon_xms_consult, "????????????");
		PerFunctionBean funBean12 = new PerFunctionBean("flight",
				R.drawable.icon_xms_market, "????????????");
		PerFunctionBean funBean13 = new PerFunctionBean("flight",
				R.drawable.icon_xms_dzdp, "????????????");
		PerFunctionBean funBean14 = new PerFunctionBean("flight",
				R.drawable.icon_xms_hx, "????????????");
		PerFunctionBean funBean15 = new PerFunctionBean("flight",
				R.drawable.icon_xms_lv100, "??????100");
		PerFunctionBean funBean16 = new PerFunctionBean("flight",
				R.drawable.icon_xms_shuning, "??????");
		PerFunctionBean funBean17 = new PerFunctionBean("flight",
				R.drawable.icon_xms_weather, "????????????");
		traDates.add(funBean1);
		traDates.add(funBean2);
		// traDates.add(funBean3);
		traDates.add(funBean4);
		traDates.add(funBean5);
		traDates.add(funBean6);
		traDates.add(funBean7);
		traDates.add(funBean8);
		traDates.add(funBean9);
		traDates.add(funBean10);
		traDates.add(funBean11);
		traDates.add(funBean12);
		traDates.add(funBean13);
		traDates.add(funBean14);
		traDates.add(funBean15);
		traDates.add(funBean16);
		traDates.add(funBean17);
		

		PerFunctionBean signManager = new PerFunctionBean("signManager",
				R.drawable.xms_main_manage, "??????");
		singManagerDates.add(signManager);
		PerFunctionBean signOverView = new PerFunctionBean("signOverView",
				R.drawable.xms_main_msglist, "????????????");
		singManagerDates.add(signOverView);

		PerFunctionBean message = new PerFunctionBean("message",
				R.drawable.xms_main_signedlist, "????????????");
		messageDates.add(message);
	}

	private void initEvent() {

		lvSingManager.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (baseApplication.isNetStat()) {
					if (LoginUtil.isLog(XmsFirstActivity.this)) {
						if (position == 0) {
							showCostDialog();
						}
						if (position == 1) {
							Intent intent = new Intent(XmsFirstActivity.this,
									SignOverViewActivity.class);
							startActivity(intent);
						}
					} else {
						LoginUtilAnother.authorizeAnother(
								XmsFirstActivity.this, XmsFirstActivity.this,
								position);
					}
				} else {
					CustomProgressDialog
							.showBocNetworkSetDialog(XmsFirstActivity.this);
				}
			}
		});

		lvMessage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
//					if (baseApplication.isNetStat()) {
//						if (LoginUtil.isLog(XmsFirstActivity.this)) {
//							Intent intent = new Intent(XmsFirstActivity.this,
//									MessageActivity.class);
//							startActivity(intent);
//						} else {
//							LoginUtilAnother.authorizeAnother(
//									XmsFirstActivity.this,
//									XmsFirstActivity.this, 9);
//						}
//					} else {
//						CustomProgressDialog
//								.showBocNetworkSetDialog(XmsFirstActivity.this);
//					}
					
					Intent intent = new Intent(XmsFirstActivity.this,
							MessageActivity.class);
					startActivity(intent);
				}

			}
		});

		traListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if (!baseApplication.isNetStat()) {
					CustomProgressDialog
					.showBocNetworkSetDialog(XmsFirstActivity.this);
				}
				else{
					// ????????????
					if (arg2 == 0) {
						Intent intent = new Intent(XmsFirstActivity.this,
								ExchangeActivity.class);
						startActivity(intent);
					}
					// ???/????????????
					if (arg2 == 1) {
						Intent intent = new Intent(XmsFirstActivity.this,
								RateActivity.class);
						startActivity(intent);
					}
					// ???????????????
					if (arg2 == 2) {
						Intent intent = new Intent(XmsFirstActivity.this,
								OrgActivity.class);
						startActivity(intent);
					}
					if (arg2 == 3) {
						Intent intent = new Intent(XmsFirstActivity.this,
								InvestActivity.class);
						startActivity(intent);
					}
					if (arg2 == 4) {
						Intent intent = new Intent(XmsFirstActivity.this,
								FundActivity.class);
						startActivity(intent);
					}
					if (arg2 == 5) {
						Intent intent = new Intent(XmsFirstActivity.this,
								AtmActivity.class);
						startActivity(intent);
					}
					if (arg2 == 6) {
						Intent intent = new Intent(XmsFirstActivity.this,
								TrainActivity.class);
						startActivity(intent);
					}
					if (arg2 == 7) {
						Intent intent = new Intent(XmsFirstActivity.this,
								FlightActivity.class);
						startActivity(intent);
					}
					if (arg2 == 8) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForDotbooking);
						bundle.putString("name", "????????????");
						Intent intent = new Intent(XmsFirstActivity.this,
								XmsWebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					if (arg2 == 9) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForConsult);
						bundle.putString("name", "????????????");
						Intent intent = new Intent(XmsFirstActivity.this,
								XmsWebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					if (arg2 == 10) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForMarket);
						bundle.putString("name", "????????????");
						Intent intent = new Intent(XmsFirstActivity.this,
								XmsWebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					if (arg2 == 11) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForDzdp);
						bundle.putString("name", "????????????");
						Intent intent = new Intent(XmsFirstActivity.this,
								XmsWebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					if (arg2 == 12) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForHx);
						bundle.putString("name", "????????????");
						Intent intent = new Intent(XmsFirstActivity.this,
								XmsWebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					if (arg2 == 13) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForLt100);
						bundle.putString("name", "??????100");
						Intent intent = new Intent(XmsFirstActivity.this,
								XmsWebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					if (arg2 == 14) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForSuning);
						bundle.putString("name", "??????");
						Intent intent = new Intent(XmsFirstActivity.this,
								XmsWebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					if (arg2 == 15) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForWeather);
						bundle.putString("name", "????????????");
						Intent intent = new Intent(XmsFirstActivity.this,
								XmsWebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				}
				
			}
		});
	}

	@Override
	public void onLogin(int position) {
		// ??????????????????
		if (position == 9) {
			Intent intent = new Intent(getBaseContext(), MessageActivity.class);
			startActivity(intent);
		}
		// ????????????
		if (position == 1) {
			Intent intent = new Intent(getBaseContext(),
					SignOverViewActivity.class);
			startActivity(intent);
		}
		/*
		 * ???????????? ???????????? ??????
		 */
		if (position == 0) {
			showCostDialog();
		}
	}

	@Override
	public void onLogin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onException() {
		// TODO Auto-generated method stub

	}

	// ?????? ?????? ?????? ??????????????? ??? ????????? ??????
	private void requestBocopForUseridQuery() {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("USRID", LoginUtil.getUserId(this));
		final String strGson = gson.toJson(map);

		BocOpUtil bocOpUtil = new BocOpUtil(this);
		bocOpUtil.postOpboc(strGson, TransactionValue.SA0053,
				new BocOpUtil.CallBackBoc() {

					@Override
					public void onSuccess(String responStr) {
						Log.i("tag1", responStr);
						try {

							Map<String, String> map;
							map = JsonUtils.getMapStr(responStr);
							strCusName = map.get("cusname").toString();
							strIdNo = map.get("idno");
							strCusid = map.get("cusid");
							Log.i("tag", "?????????" + strCusName + "??????????????????"
									+ strIdNo + "?????? ??? ???" + strCusid);
							if (strCusName.length() > 0
									&& strIdNo.length() > 10) {
								// strOwnerName = "??????";
								// strIdNo = "362202198702140010";
								Intent intent = new Intent(
										XmsFirstActivity.this,
										FinanceSignContractActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("TITLE", "??????");
								bundle.putString("COST_TYPE",
										costType.getCostName());
								bundle.putString("TYPE_CODE",
										costType.getTypeCode());
								bundle.putString("CUS_NAME", strCusName);
								bundle.putString("ID_NO", strIdNo);
								bundle.putString("CUS_ID", strCusid);
								intent.putExtra("BUNDLE", bundle);
								startActivity(intent);
								// CustomProgressDialog.showBocRegisterSetDialog(CarAddActivity.this);
							} else {
								CustomProgressDialog
										.showBocRegisterSetDialog(XmsFirstActivity.this);
								// Toast.makeText(CarAddActivity.this,
								// "???????????????????????????????????????", Toast.LENGTH_LONG).show();
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onStart() {
						Log.i("tag", "??????GSON?????????" + strGson);
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(String responStr) {
						CspUtil.onFailure(XmsFirstActivity.this, responStr);
					}
				});
	}

	/**
	 * ??????????????????
	 */
	private void requestCspForUser(String typeCode) {
		try {
			// ??????CSP XML??????
			// ??????01?????????02????????????03???????????????04???????????????05,????????????07
			CspXmlXms004 cspXmlXms004 = new CspXmlXms004(
					LoginUtil.getUserId(this), typeCode);
			String strXml = cspXmlXms004.getCspXml();
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			final byte[] byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
			Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
			cspUtil.postCspLogin(byteMessage, new CspUtil.CallBack() {
				@Override
				public void onSuccess(String responStr) {
					Message msg = new Message();
					msg.what = USER_LIST_SUCCESS;
					msg.obj = responStr;
					mHandler.sendMessage(msg);
				}

				@Override
				public void onFinish() {

				}

				@Override
				public void onFailure(String responStr) {
					Message msg = new Message();
					msg.what = USER_FAILED;
					msg.obj = responStr;
					mHandler.sendMessage(msg);
				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void showCostDialog() {
		final CostTypeDialog costDialog = new CostTypeDialog(
				XmsFirstActivity.this);
		costDialog.show(new CostTypeDialog.CostTypeOnClickListener() {

			@Override
			public void OnCostTypeClick(DialogCostType costType) {
				costDialog.dismiss();
				Log.i("tag", "????????????" + costType.getTypeCode());
				XmsFirstActivity.this.costType = costType;
				if ("07".equals(costType)) {
					requestBocopForUseridQuery();
				} else {
					requestCspForUser(costType.getTypeCode());
				}
			}
		});
	}

}
