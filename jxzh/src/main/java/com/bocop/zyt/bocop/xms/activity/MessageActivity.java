package com.bocop.zyt.bocop.xms.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.BMJFActivity;
import com.bocop.zyt.bocop.jxplatform.activity.CreditCardActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.adapter.XmsItemAdapter;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.BocOpUtil;
import com.bocop.zyt.bocop.jxplatform.util.BocopDialog;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.FormsUtil;
import com.bocop.zyt.bocop.jxplatform.util.JsonUtils;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtilAnother;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.xms.bean.DialogCostType;
import com.bocop.zyt.bocop.xms.bean.MessageCostType;
import com.bocop.zyt.bocop.xms.bean.MessageList;
import com.bocop.zyt.bocop.xms.bean.MessageResponse;
import com.bocop.zyt.bocop.xms.bean.UserResponse;
import com.bocop.zyt.bocop.xms.tools.ViewHolder;
import com.bocop.zyt.bocop.xms.utils.SharedPreferencesUtils;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.xms.view.CostTypeDialog;
import com.bocop.zyt.bocop.xms.xml.CspXmlXms004;
import com.bocop.zyt.bocop.xms.xml.CspXmlXms005;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseAdapter;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.constants.Constants;
import com.bocop.zyt.jx.tools.DialogUtil;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenu;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenuCreator;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenuItem;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenuListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.xms_activity_message)
public class MessageActivity extends BaseActivity implements LoginUtilAnother.ILoginListener
// implements OnScrollListener
{

	@ViewInject(R.id.tv_titleName)
	private TextView tvTitleName;

	@ViewInject(R.id.tvMessageError)
	private TextView tvError;

	@ViewInject(R.id.lv_Message)
	private SwipeMenuListView lvMessage;

	@ViewInject(R.id.vLine)
	private View vLine;
	
	
	@ViewInject(R.id.gvSign)
	private GridView gvSign;
	@ViewInject(R.id.gvBankSer)
	private GridView gvBankSer;
	@ViewInject(R.id.gvOtherSer)
	private GridView gvOtherSer;
	
	private BaseApplication baseApplication = BaseApplication.getInstance();
	
//	@ViewInject(R.id.lvadvice)
//	private ListView traListView;
//	
//	TrafficMainAdapter adviceAdapter;

	private BaseAdapter<MessageCostType> adapter;
	private List<MessageCostType> list = new ArrayList<>();
//	List<PerFunctionBean> traDates = new ArrayList<PerFunctionBean>();
//	TrafficMainAdapter traAdapter;

	/** ?????????????????? */
	private int pageIndex = 0;

	private int flag;
	
	String strCusName;
	String strIdNo;
	String strCusid;
	private static final int MSG_SUCCESS = 0;
	private static final int MSG_FAILED = 1;
	private static final int USER_LIST_SUCCESS = 2;
	private static final int USER_FAILED = 3;
	private DialogCostType costType;
	
	private XmsItemAdapter signAdapter;
	private XmsItemAdapter bankAdapter;
	private XmsItemAdapter otherAdapter;
	
	
	private PopupWindow guidingView;
	private ImageView ivRefreshIntro;
	private Button btnKnown;
	private View vPerch;
	private SharedPreferencesUtils sp = new SharedPreferencesUtils(this, BocSdkConfig.FLAG_PROGRESS);
	
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SUCCESS:
				// TODO ????????????
				// footerView.setVisibility(View.GONE);
				String content = (String) msg.obj;
				MessageResponse messageResponse = XStreamUtils.getFromXML(content, MessageResponse.class);
				if (messageResponse != null) {
					if ("01".equals(messageResponse.getConstHead().getErrCode())) {
						lvMessage.setVisibility(View.GONE);
						tvError.setVisibility(View.VISIBLE);
						tvError.setText(messageResponse.getConstHead().getErrMsg());
					} else {
						if ("0".equals(getFlagProgress())) {
							vPerch.setVisibility(View.VISIBLE);
							ivRefreshIntro.setImageResource(R.drawable.xms_jftx_refresh);
							guidingView.showAsDropDown(vLine);
						} else if ("1".equals(getFlagProgress())) {
							vPerch.setVisibility(View.GONE);
							ivRefreshIntro.setImageResource(R.drawable.xms_jftx_sign);
							guidingView.showAsDropDown(vLine);
						}
						lvMessage.setVisibility(View.VISIBLE);
						tvError.setVisibility(View.GONE);
						MessageList messageList = messageResponse.getMessageList();
						if (messageList != null && messageList.getList() != null) {
							if (pageIndex == 0) {
								list.clear();
							}
							list.addAll(messageList.getList());
							adapter.notifyDataSetChanged();
						} else {
							Toast.makeText(MessageActivity.this, "????????????", Toast.LENGTH_SHORT).show();
						}
					}
				} else {
					Toast.makeText(MessageActivity.this, "????????????", Toast.LENGTH_SHORT).show();
				}
				break;
			case MSG_FAILED:
				// TODO ????????????
				// footerView.setVisibility(View.GONE);
				String responStr = (String) msg.obj;
				CspUtil.onFailure(MessageActivity.this, responStr);
				break;

			case USER_LIST_SUCCESS:
				content = (String) msg.obj;
				UserResponse userResponse = XStreamUtils.getFromXML(content, UserResponse.class);
				//?????????????????????
				if ("01".equals(userResponse.getConstHead().getErrCode())) {
					//07:????????????????????????
					Log.i("tag", "??????????????????");
					if("07".equals(costType.getTypeCode())){
						requestBocopForUseridQuery();
					}
					//????????????????????????
					else{
						Intent intent = new Intent(MessageActivity.this, SignContractActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("TITLE", "??????");
						bundle.putString("COST_TYPE", costType.getCostName());
						bundle.putString("TYPE_CODE", costType.getTypeCode());
						intent.putExtra("BUNDLE", bundle);
						startActivity(intent);
					}
					//?????????????????????????????????
				} else {
					Log.i("tag", "???????????????" + costType.getTypeCode());
					Intent intent = new Intent(MessageActivity.this, UserManagerActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("COST_TYPE", costType.getCostName());
					bundle.putString("TYPE_CODE", costType.getTypeCode());
					bundle.putString("USER_LIST", (String) msg.obj);
					intent.putExtra("BUNDLE", bundle);
					startActivity(intent);
				}
				break;

			case USER_FAILED:
				responStr = (String) msg.obj;
				CspUtil.onFailure(MessageActivity.this, responStr);
				break;
			}
		};
	};
	//?????? ?????? ?????? ??????????????? ??? ????????? ??????
	private void requestBocopForUseridQuery() {
		// TODO Auto-generated method stub
				Gson gson = new Gson();
				Map<String,String> map = new HashMap<String,String>();
				map.put("USRID", LoginUtil.getUserId(this));
				final String strGson = gson.toJson(map);
				
				BocOpUtil bocOpUtil = new BocOpUtil(this);
				bocOpUtil.postOpboc(strGson, TransactionValue.SA0053, new BocOpUtil.CallBackBoc() {
					
					@Override
					public void onSuccess(String responStr) {
						Log.i("tag1", responStr);
						try {
							
							Map<String,String> map;
							map = JsonUtils.getMapStr(responStr);
							strCusName = map.get("cusname").toString();
							strIdNo = map.get("idno");
							strCusid = map.get("cusid");
							Log.i("tag","?????????" + strCusName + "??????????????????" + strIdNo + "?????? ??? ???" + strCusid);
							if (strCusName.length()>0 && strIdNo.length()>10) {
//								strOwnerName = "??????";
//								strIdNo = "362202198702140010";
								Intent intent = new Intent(MessageActivity.this, FinanceSignContractActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("TITLE", "??????");
								bundle.putString("COST_TYPE", costType.getCostName());
								bundle.putString("TYPE_CODE", costType.getTypeCode());
								bundle.putString("CUS_NAME", strCusName);
								bundle.putString("ID_NO", strIdNo);
								bundle.putString("CUS_ID", strCusid);
								intent.putExtra("BUNDLE", bundle);
								startActivity(intent);
//								CustomProgressDialog.showBocRegisterSetDialog(CarAddActivity.this);
							} else {
								CustomProgressDialog.showBocRegisterSetDialog(MessageActivity.this);
//								Toast.makeText(CarAddActivity.this, "???????????????????????????????????????", Toast.LENGTH_LONG).show();
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
						CspUtil.onFailure(MessageActivity.this, responStr);
					}
				});
	}
	// TODO ????????????
	// private int vItemCount = 0;
	// private View footerView;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tvTitleName.setText("?????????");
		flag = 0;

		initView();
		initData();
//		initEvent();
		initListener();
		if(LoginUtil.isLog(this)){
			requestMessage(true);
		}else{
			lvMessage.setVisibility(View.GONE);
			tvError.setVisibility(View.VISIBLE);
			tvError.setText("????????????????????????");
			LoginUtilAnother.authorizeAnother(
					MessageActivity.this, MessageActivity.this,
					1);
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
//		requestMessage(true);
	}

	@OnClick({ R.id.iv_imageLeft, R.id.iv_refresh, R.id.iv_Item,R.id.tvMessageError })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_refresh:
			if(LoginUtil.isLog(this)){
				requestMessage(true);
			}else{
				LoginUtilAnother.authorizeAnother(
						MessageActivity.this, MessageActivity.this,
						1);
			}
			
			break;
		case R.id.iv_imageLeft:
			finish();
			break;
		case R.id.iv_Item:
//			if(LoginUtil.isLog(MessageActivity.this)){
//				showCostDialog();
//			}else{
//				LoginUtilAnother.authorizeAnother(
//						MessageActivity.this, MessageActivity.this,
//						11);
//			}
		case R.id.tvMessageError:
			if(tvError.getText().toString().equals("????????????????????????")){
				LoginUtilAnother.authorizeAnother(
						MessageActivity.this, MessageActivity.this,
						1);
			}
			
//			final CostTypeDialog costDialog = new CostTypeDialog(MessageActivity.this);
//			costDialog.show(new CostTypeOnClickListener() {
//
//				@Override
//				public void OnCostTypeClick(DialogCostType costType) {
//					costDialog.dismiss();
//					Log.i("tag", "????????????" +  costType.getTypeCode());
//					MessageActivity.this.costType = costType;
//					if("07".equals(costType)){
//						requestBocopForUseridQuery();
//					}
//					else{
//						requestCspForUser(costType.getTypeCode());
//					}
//				}
//			});
			break;

		default:
			break;
		}
	}

	private void initView() {


		View guidingView = getLayoutInflater().inflate(R.layout.xms_popwindow_guidingview, null);
		RelativeLayout reGuidingView = (RelativeLayout) guidingView.findViewById(R.id.reGuidingView);

		ivRefreshIntro = (ImageView) guidingView.findViewById(R.id.ivRefreshIntro);
		btnKnown = (Button) guidingView.findViewById(R.id.btnKnown);
		vPerch = guidingView.findViewById(R.id.vPerch);
		initPopListener();
		reGuidingView.getBackground().setAlpha(80);
		FormsUtil.getDisplayMetrics(this);
		this.guidingView = new PopupWindow(guidingView, FormsUtil.SCREEN_WIDTH, FormsUtil.SCREEN_HEIGHT);

		SwipeMenuCreator menuCreator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
				deleteItem.setBackground(R.color.redLight);
				deleteItem.setWidth(dp2px(90));
				deleteItem.setIcon(R.drawable.ic_delete);
				menu.addMenuItem(deleteItem);

			}
		};

		lvMessage.setMenuCreator(menuCreator);

	}
	

	private void initData() {
		if (TextUtils.isEmpty(getFlagProgress())) {
			setFlagProgress("0");
		}

		lvMessage.setAdapter(
				adapter = new BaseAdapter<MessageCostType>(MessageActivity.this, list, R.layout.xms_item_message) {

					@Override
					public void viewHandler(int position, MessageCostType t, View convertView) {
						ImageView ivType = ViewHolder.get(convertView, R.id.ivType);
						TextView tvText = ViewHolder.get(convertView, R.id.tvText);
						LinearLayout llMessage = ViewHolder.get(convertView, R.id.llMessage);

						if (null != t) {
							String costType = "";
							if ("01".equals(t.getType())) {
								ivType.setImageResource(R.drawable.icon_secretary_message_sf);
								costType = "??????";
							} else if ("02".equals(t.getType())) {
								ivType.setImageResource(R.drawable.icon_secretary_message_df);
								costType = "??????";
							} else if ("03".equals(t.getType())) {
								ivType.setImageResource(R.drawable.icon_secretary_message_mqf);
								costType = "?????????";
							} else if ("04".equals(t.getType())) {
								ivType.setImageResource(R.drawable.icon_secretary_message_yxds);
								costType = "???????????????";
							} else if ("05".equals(t.getType())) {
								ivType.setImageResource(R.drawable.icon_secretary_message_ydtx);
								costType = "????????????";
							} else if ("06".equals(t.getType())) {
								ivType.setImageResource(R.drawable.icon_secretary_message_jt);
								costType = "????????????";
							}else if ("07".equals(t.getType())) {
								ivType.setImageResource(R.drawable.icon_secretary_message_fin);
								costType = "?????? ??????";
							}
							
							tvText.setText(t.getPush_text());
//							String cost = t.getCost();
//							if (!"????????????".equals(costType)) {
//								tvText.setText("???????????????" + costType + cost + "???????????????????????????");
//								// tvCostType.setText(costType);
//								// tvCost.setText(t.getCost());
//							} else {
//								if (cost.contains(".")) {
//									cost = cost.substring(0, cost.indexOf("."));
//								}
//								tvText.setText("??????" + cost + "??????????????????????????????????????????????????????");
//								llMessage.setClickable(false);
//							}
						}
					}
				});
	}
	
	private void otherSwitch(int position){
	switch(position){
	//????????????
	case 0:
		Bundle bundleConsult = new Bundle();
		bundleConsult.putString("url", Constants.xmsUrlForMarket);
		bundleConsult.putString("name", "????????????");
		Intent intentConsult = new Intent(MessageActivity.this,
				WebActivity.class);
		intentConsult.putExtras(bundleConsult);
		startActivity(intentConsult);
		break;
	//????????????
	case 1:
		Bundle bundleHx = new Bundle();
		bundleHx.putString("url", Constants.xmsUrlForHx);
		bundleHx.putString("name", "????????????");
		Intent intentHx = new Intent(MessageActivity.this,
				WebActivity.class);
		intentHx.putExtras(bundleHx);
		startActivity(intentHx);
		break;
	//????????????
	case 2:
		Bundle bundleWeather = new Bundle();
		bundleWeather.putString("url", Constants.xmsUrlForWeather);
		bundleWeather.putString("name", "????????????");
		Intent intentWeather = new Intent(MessageActivity.this,
				WebActivity.class);
		intentWeather.putExtras(bundleWeather);
		startActivity(intentWeather);
		break;
	//????????????
	case 3:
		Bundle bundleDzdp= new Bundle();
		bundleDzdp.putString("url", Constants.xmsUrlForDzdp);
		bundleDzdp.putString("name", "????????????");
		Intent intentDzdp = new Intent(MessageActivity.this,
				WebActivity.class);
		intentDzdp.putExtras(bundleDzdp);
		startActivity(intentDzdp);
		break;	
	//????????????
	case 4:
		Bundle bundleDidi= new Bundle();
		bundleDidi.putString("url", Constants.xmsUrlForDidi);
		bundleDidi.putString("name", "????????????");
		Intent intentDidi = new Intent(MessageActivity.this,
				WebActivity.class);
		intentDidi.putExtras(bundleDidi);
		startActivity(intentDidi);
		break;
	//????????? 
	case 5:
		
		Bundle bundleFlight= new Bundle();
		bundleFlight.putString("url", Constants.xmsUrlForFight);
		bundleFlight.putString("name", "?????????");
		Intent intentFlight = new Intent(MessageActivity.this,
				WebActivity.class);
		intentFlight.putExtras(bundleFlight);
		startActivity(intentFlight);
		break;
	//????????????
	case 6:
		Bundle bundleTrain= new Bundle();
		bundleTrain.putString("url", Constants.xmsUrlForTrain);
		bundleTrain.putString("name", "????????????");
		Intent intentTrain = new Intent(MessageActivity.this,
				WebActivity.class);
		intentTrain.putExtras(bundleTrain);
		startActivity(intentTrain);
		break;
		
	//????????????
	case 7:
		Bundle bundle = new Bundle();
		bundle.putString("url", Constants.xmsUrlForLt100);
		bundle.putString("name", "????????????");
		Intent intent = new Intent(MessageActivity.this,
				WebActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
		break;
	
	//????????????
	case 8:
		Bundle bundleBaidu= new Bundle();
		bundleBaidu.putString("url", Constants.xmsUrlForBaidu);
		bundleBaidu.putString("name", "????????????");
		Intent intentBaidu = new Intent(MessageActivity.this,
				WebActivity.class);
		intentBaidu.putExtras(bundleBaidu);
		startActivity(intentBaidu);
		break;
	case 9:
		Bundle bundle58Home= new Bundle();
		bundle58Home.putString("url", Constants.xmsUrlFor58Home);
		bundle58Home.putString("name", "????????????");
		Intent intent58Home = new Intent(MessageActivity.this,
				WebActivity.class);
		intent58Home.putExtras(bundle58Home);
		startActivity(intent58Home);
		break;
		//?????????
	case 10:
		Bundle bundleSpider= new Bundle();
		bundleSpider.putString("url", Constants.xmsUrlForSpider);
		bundleSpider.putString("name", "?????????");
		Intent intentSpider = new Intent(MessageActivity.this,
				WebActivity.class);
		intentSpider.putExtras(bundleSpider);
		startActivity(intentSpider);
		break;
	}
	}

	private void initListener() {

		signAdapter = new XmsItemAdapter(this, R.array.xmssign, "0");
		bankAdapter = new XmsItemAdapter(this, R.array.xmsbankser,
				"1");
		otherAdapter = new XmsItemAdapter(this, R.array.xmsotherser, "2");
		
		gvSign.setAdapter(signAdapter);
		gvBankSer.setAdapter(bankAdapter);
		gvOtherSer.setAdapter(otherAdapter);
		
		gvSign.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(baseApplication.isNetStat()){
					switch(position){
					//?????????????????? 
					case 0:
						if(LoginUtil.isLog(MessageActivity.this)){
							showCostDialog();
						}else{
							LoginUtilAnother.authorizeAnother(
									MessageActivity.this, MessageActivity.this,
									11);
						}
						
						break;
					//????????????
					case 1:
						if(LoginUtil.isLog(MessageActivity.this)){
							Intent intent = new Intent(MessageActivity.this,
									SignOverViewActivity.class);
							startActivity(intent);
						}else{
							LoginUtilAnother.authorizeAnother(
									MessageActivity.this, MessageActivity.this,
									12);
						}
						
						break;
					}
				}else{
					CustomProgressDialog
					.showBocNetworkSetDialog(MessageActivity.this);
				}
			}
			
		});
		
		gvBankSer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(baseApplication.isNetStat()){
					switch(position){
					//ATM?????? 
					case 0:
						Bundle bundleATM = new Bundle();
						bundleATM.putString("url", Constants.xmsUrlForATM);
						bundleATM.putString("name", "ATM??????");
						Intent intentATM = new Intent(MessageActivity.this,
								WebActivity.class);
						intentATM.putExtras(bundleATM);
						startActivity(intentATM);
						break;
					//????????????
					case 1:
						
						Bundle bundleOrg = new Bundle();
						bundleOrg.putString("url", Constants.xmsUrlForOrg);
						bundleOrg.putString("name", "????????????");
						Intent intentOrg = new Intent(MessageActivity.this,
								WebActivity.class);
						intentOrg.putExtras(bundleOrg);
						startActivity(intentOrg);
						break;
					//????????????
					case 2:
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForDotbooking);
						bundle.putString("name", "????????????");
						Intent intentDotbook = new Intent(MessageActivity.this,
								WebActivity.class);
						intentDotbook.putExtras(bundle);
						startActivity(intentDotbook);
						break;
					//???????????????
					case 3:
						Intent intentDebitCard = new Intent(MessageActivity.this,
								CreditCardActivity.class);
						startActivity(intentDebitCard);
						break;	
					//???????????????
					case 4:
						Intent intentRate = new Intent(MessageActivity.this,
								RateActivity.class);
						startActivity(intentRate);
						break;
					//????????????
					case 5:
						Intent intentExchange = new Intent(MessageActivity.this,
								ExchangeActivity.class);
						startActivity(intentExchange);
						break;
					//????????????
					case 6:
						Intent intentFun = new Intent(MessageActivity.this,
								FundActivity.class);
						startActivity(intentFun);
						break;
						
					//????????????
					case 7:
						Bundle bundleMarket = new Bundle();
						bundleMarket.putString("url", Constants.xmsUrlForConsult);
						bundleMarket.putString("name", "????????????");
						Intent intentMarket = new Intent(MessageActivity.this,
								WebActivity.class);
						intentMarket.putExtras(bundleMarket);
						startActivity(intentMarket);
						break;
					}
					
					}else{
					CustomProgressDialog
					.showBocNetworkSetDialog(MessageActivity.this);
				}
			}
			
		});
		
		gvOtherSer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int position2 = position;
				// TODO Auto-generated method stub
				if(baseApplication.isNetStat()){
					
					//????????????
					
					BocopDialog dialog = new BocopDialog(MessageActivity.this, "????????????","\t\t????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
					
					if(flag == 1){
						otherSwitch(position2);
					}else{
						dialog.setPositiveButton(new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								flag = 1;
								otherSwitch(position2);
								dialog.cancel();
							}
						}, "??????");
						dialog.setNegativeButton(new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						}, "?????????");
						if (!MessageActivity.this.isFinishing()) {
							dialog.show();
						}
					}
					}else{
					CustomProgressDialog
					.showBocNetworkSetDialog(MessageActivity.this);
				}
			}
			
		});
		// TODO ????????????
		// lvMessage.setOnScrollListener(this);

		lvMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (getBaseApp().isNetStat()) {
					if (!"06".equals(list.get(position).getType()) && !"07".equals(list.get(position).getType())) {
						Bundle bundle = new Bundle();
						bundle.putString("userId", LoginUtil.getUserId(MessageActivity.this));
						bundle.putString("token", LoginUtil.getToken(MessageActivity.this));
						Intent intent = new Intent(MessageActivity.this, BMJFActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				} else {
					CustomProgressDialog.showBocNetworkSetDialog(MessageActivity.this);
				}
			}
		});

		lvMessage.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {

				switch (index) {
				case 0:// ??????
					DialogUtil.showWithTwoBtn(MessageActivity.this, "?????????????????????", "??????", "??????",
							new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							list.remove(position);
							if (list.size() == 0) {
								lvMessage.setVisibility(View.GONE);
								tvError.setVisibility(View.VISIBLE);
							} else {
								lvMessage.setVisibility(View.VISIBLE);
								tvError.setVisibility(View.GONE);
								adapter.notifyDataSetChanged();
							}
						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					break;
				}
				return false;
			}
		});

	}

	private void initPopListener() {
		btnKnown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("0".equals(getFlagProgress())) {
					setFlagProgress("1");
					vPerch.setVisibility(View.GONE);
					ivRefreshIntro.setImageResource(R.drawable.xms_jftx_sign);
					guidingView.dismiss();
				} else if ("1".equals(getFlagProgress())) {
					setFlagProgress("2");
					vPerch.setVisibility(View.VISIBLE);
					ivRefreshIntro.setImageResource(R.drawable.xms_jftx_refresh);
					guidingView.dismiss();
				}
			}
		});
	}

	private String getFlagProgress() {
		if (null != sp.getValue(BocSdkConfig.FLAG_PROGRESS, String.class)) {
			return sp.getValue(BocSdkConfig.FLAG_PROGRESS, String.class);
		} else {
			return "";
		}
	}

	private void setFlagProgress(String progress) {
		sp.setValue(BocSdkConfig.FLAG_PROGRESS, progress);
	}

	/**
	 * ??????????????????
	 */
	private void requestMessage(boolean isRefresh) {
		try {
			if (isRefresh) {
				pageIndex = 0;
			}
			CspXmlXms005 cspXmlXms005 = new CspXmlXms005(LoginUtil.getUserId(this)
			// "developer"
			, String.valueOf(pageIndex));
			String strXml = cspXmlXms005.getCspXml();
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
					msg.what = MSG_SUCCESS;
					msg.obj = responStr;
					mHandler.sendMessage(msg);
				}

				@Override
				public void onFinish() {

				}

				@Override
				public void onFailure(String responStr) {
					Message msg = new Message();
					msg.what = MSG_FAILED;
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
				this);
		costDialog.show(new CostTypeDialog.CostTypeOnClickListener() {

			@Override
			public void OnCostTypeClick(DialogCostType costType) {
				costDialog.dismiss();
				Log.i("tag", "????????????" +  costType.getTypeCode());
				MessageActivity.this.costType = costType;
				if("07".equals(costType)){
					requestBocopForUseridQuery();
				}
				else{
					requestCspForUser(costType.getTypeCode());
				}
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
			CspXmlXms004 cspXmlXms004 = new CspXmlXms004(LoginUtil.getUserId(this), typeCode);
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

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}



	/* (non-Javadoc)
	 * @see com.bocop.jxplatform.util.LoginUtilAnother.ILoginListener#onLogin(int)
	 */
	@Override
	public void onLogin(int position) {
		// TODO Auto-generated method stub
		if(position == 1){
			requestMessage(true);
		}
		if(position == 11){
			showCostDialog();
		}
		if(position == 12){
			Intent intent = new Intent(MessageActivity.this,
					SignOverViewActivity.class);
			startActivity(intent);
		}
	}



	/* (non-Javadoc)
	 * @see com.bocop.jxplatform.util.LoginUtilAnother.ILoginListener#onLogin()
	 */
	@Override
	public void onLogin() {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see com.bocop.jxplatform.util.LoginUtilAnother.ILoginListener#onCancle()
	 */
	@Override
	public void onCancle() {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see com.bocop.jxplatform.util.LoginUtilAnother.ILoginListener#onError()
	 */
	@Override
	public void onError() {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see com.bocop.jxplatform.util.LoginUtilAnother.ILoginListener#onException()
	 */
	@Override
	public void onException() {
		// TODO Auto-generated method stub
		
	}

}
