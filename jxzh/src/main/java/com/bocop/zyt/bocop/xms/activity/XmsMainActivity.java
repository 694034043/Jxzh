package com.bocop.zyt.bocop.xms.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boc.bocop.sdk.util.StringUtil;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.CreditCardActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebViewActivity;
import com.bocop.zyt.bocop.jxplatform.adapter.XmsItemAdapter;
import com.bocop.zyt.bocop.jxplatform.bean.app.AppInfo;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.BocopDialog;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.FormsUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtilAnother;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.bocop.xms.service.SoundService;
import com.bocop.zyt.bocop.xms.utils.IdcardInfoExtractor;
import com.bocop.zyt.bocop.xms.utils.ServiceWork;
import com.bocop.zyt.bocop.xms.xml.CspXmlXmsCom;
import com.bocop.zyt.bocop.xms.xml.message.MessageBean;
import com.bocop.zyt.bocop.xms.xml.message.MessageListResp;
import com.bocop.zyt.bocop.xms.xml.message.MessageListXmlBean;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnItemClick;
import com.bocop.zyt.jx.constants.Constants;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ??????????????????
 * 
 * @author ftl
 * 
 */
@ContentView(R.layout.xms_activity_xms_main)
public class XmsMainActivity extends BaseActivity implements LoginUtilAnother.ILoginListener {

	@ViewInject(R.id.llXmsMain)
	private LinearLayout llXmsMain;
	@ViewInject(R.id.iv_imageLeft)
	private BackButton ivImageLeft;
	@ViewInject(R.id.ivBack)
	private ImageView ivBack;
	@ViewInject(R.id.tv_titleName)
	private TextView tvTitle;
	@ViewInject(R.id.ivRefresh)
	private ImageView ivRefresh;
	@ViewInject(R.id.llRemind)
	private LinearLayout llRemind;
	@ViewInject(R.id.tvRemind)
	private TextView tvRemind;//????????????
	
	@ViewInject(R.id.gvRemind)
	private GridView gvRemind;// ????????????
	@ViewInject(R.id.gvJRService)
	private GridView gvJRService;// ????????????
	@ViewInject(R.id.gvDailyLife)
	private GridView gvDailyLife;// ????????????
	@ViewInject(R.id.gvNecessary)
	private GridView gvNecessary;// ????????????
	@ViewInject(R.id.rel_bg)
	private RelativeLayout rel_bg;// ????????????

	private XmsItemAdapter gvRemindAdapter;
	private XmsItemAdapter gvJRAdapter;
	private XmsItemAdapter dailyLifeAdapter;
	private XmsItemAdapter necessaryAdapter;
	private int flag = 0;
	private BocopDialog dialog;// ????????????
	private List<MessageBean> msgData;
	
	private static final int MY_MESSAGE_CODE = 101;
	private static final int FINANCE_MESSAGE_CODE = 102;
	private static final int PAY_REMIND_CODE = 103;
	private static final int CUSTOM_REMIND_CODE = 104;
	private static final int CREDIT_POINT_CODE = 105;
	
	
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			if (LoginUtil.isLog(XmsMainActivity.this) && isShowDialog() && isBirthday()) {
				Log.i("tagg", "??????????????????");
				showAdDialog(XmsMainActivity.this.llXmsMain);
			} else {
				Log.i("tagg", "?????????????????????");
			}
		}
	};
	private String title;
	private String from;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initTitle();
		initData();
		initListener();
		mHandler.sendEmptyMessageDelayed(0, 200);
	}
	
	private void initTitle(){
		title = getIntent().getStringExtra("title");
		from = getIntent().getStringExtra("from");
		title = StringUtil.isNullOrEmpty(title)?"?????????":title;
		tvTitle.setText(title);
		ivImageLeft.setVisibility(View.GONE);
		ivBack.setVisibility(View.VISIBLE);
		if (from.equals("yct1")||from.equals("yct2")||from.equals("yct3")){
			rel_bg.setBackgroundResource(R.drawable.shape_base_yct_action_bar);
		}else {
			rel_bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
		}
	}
	
	private void initData() {
		gvRemindAdapter = new XmsItemAdapter(this, R.array.xmsremind, "1");
		gvRemind.setAdapter(gvRemindAdapter);
		
		gvJRAdapter = new XmsItemAdapter(this, R.array.xmsjrservice, "2");
		gvJRService.setAdapter(gvJRAdapter);
		
		dailyLifeAdapter = new XmsItemAdapter(this, R.array.xmsotherser, "3");
		gvDailyLife.setAdapter(dailyLifeAdapter);

		necessaryAdapter = new XmsItemAdapter(this, R.array.xmsnecessary, "4");
		gvNecessary.setAdapter(necessaryAdapter);

		dialog = new BocopDialog(this, "????????????",
				"\t\t????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
		msgData = getBaseApp().getMsgData();
	}
	
	private void initListener() {
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@OnItemClick({R.id.gvRemind, R.id.gvJRService, R.id.gvDailyLife, R.id.gvNecessary})
	public void setOnItemClickListener(AdapterView<?> parent, View view,
			final int position, long id) {
		switch (parent.getId()) {
		case R.id.gvRemind:
			handleEvent(R.id.gvRemind, 1, gvRemindAdapter.getItem(position).getName(), position);
			break;
		case R.id.gvJRService:
			handleEvent(R.id.gvJRService, 1, gvJRAdapter.getItem(position).getName(), position);
			break;
		case R.id.gvDailyLife:
			handleEvent(R.id.gvDailyLife, flag, dailyLifeAdapter.getItem(position).getName(), position);
			break;
		case R.id.gvNecessary:
			handleEvent(R.id.gvNecessary, flag, necessaryAdapter.getItem(position).getName(), position);
			break;
		}
	}
	
	private void handleEvent(final int id, int flagParam, String name, final int position) {
		if (this.getBaseApp().isNetStat()) {
			if (!"".equals(name)) { 
				if (flagParam == 1) {
					handleJump(id, position);
				} else {
					dialog.setPositiveButton(
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									flag = 1;
									handleJump(id, position);
									dialog.cancel();
								}
							}, "??????");
					dialog.setNegativeButton(
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}, "?????????");
					if (!this.isFinishing()) {
						dialog.show();
					}
				}
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(this);
		}
	}
	
	private void handleJump(int id, int position) {
		switch (id) {
		case R.id.gvRemind:
			switchRemind(position);
			break;
		case R.id.gvJRService:
			switchJRService(position);
			break;
		case R.id.gvDailyLife:
			switchDailyLife(position);
			break;
		case R.id.gvNecessary:
			switchNecessary(position);
			break;
		}
	}
	
	/**
	 * ????????????
	 * @param position
	 */
	private void switchRemind(int position) {
		switch (position) {
		// ????????????
		case 0:
		// ????????????
		case 1:
			int code = position == 0 ? MY_MESSAGE_CODE : FINANCE_MESSAGE_CODE;
			if (LoginUtil.isLog(this)) {
				Intent intentMsg = new Intent(this, MyMessageActivity.class);
				intentMsg.putExtra("type", position);
				startActivity(intentMsg);
			} else {
				LoginUtilAnother.authorizeAnother(this, this, code);
			}
			break;
		// ????????????
		case 2:
			if (LoginUtil.isLog(this)) {
				Intent intentpPay = new Intent(this, PayRemindActivity.class);
				startActivity(intentpPay);
			} else {
				LoginUtilAnother.authorizeAnother(this, this, PAY_REMIND_CODE);
			}
			break;
		// ???????????????
		case 3:
			if (LoginUtil.isLog(this)) {
				Intent intentpRemind = new Intent(this, CustomRemindActivity.class);
				startActivity(intentpRemind);
			}  else {
				LoginUtilAnother.authorizeAnother(this, this, CUSTOM_REMIND_CODE);
			}
			break;
		}
	}
	
	/**
	 * ????????????
	 * @param position
	 */
	private void switchJRService(int position) {
		switch (position) {
		// ???????????????
		case 0:
			Bundle bundleRate = new Bundle();
			bundleRate.putString("url", Constants.xmsUrlForRate);
			bundleRate.putString("name", "???????????????");
			Intent intentRate = new Intent(this, WebActivity.class);
			intentRate.putExtras(bundleRate);
			startActivity(intentRate);
			break;
		// ????????????
		case 1:
			Bundle bundleExchange = new Bundle();
			bundleExchange.putString("url", Constants.xmsUrlForExchange);
			bundleExchange.putString("name", "????????????");
			Intent intentExchange = new Intent(this, WebActivity.class);
			intentExchange.putExtras(bundleExchange);
			startActivity(intentExchange);
			break;
		// ???????????????
		case 2:
			if (LoginUtil.isLog(this)) {
				Intent intentDebitCard = new Intent(this, CreditCardActivity.class);
				startActivity(intentDebitCard);
			} else {
				LoginUtilAnother.authorizeAnother(this, this, CREDIT_POINT_CODE);
			}
			break;	
		// ????????????
		case 3:
			Bundle bundleHx = new Bundle();
			bundleHx.putString("url", Constants.xmsUrlForHx);
			bundleHx.putString("name", "????????????");
			Intent intentHx = new Intent(this, WebViewActivity.class);
			intentHx.putExtras(bundleHx);
			startActivity(intentHx);
			break;
		}
	}
	
	/**
	 * ????????????
	 * @param position
	 */
	private void switchDailyLife(int position) {
		switch (position) {
		// ????????????
		case 0:
			Bundle bundleWeather = new Bundle();
			bundleWeather.putString("url", Constants.xmsUrlForWeather);
			bundleWeather.putString("name", "????????????");
			Intent intentWeather = new Intent(this, WebViewActivity.class);
			intentWeather.putExtras(bundleWeather);
			startActivity(intentWeather);
			break;
		// ????????????
		case 1:
			Bundle bundleDzdp = new Bundle();
			bundleDzdp.putString("url", Constants.xmsUrlForDzdp);
			bundleDzdp.putString("name", "????????????");
			Intent intentDzdp = new Intent(this, WebViewActivity.class);
			intentDzdp.putExtras(bundleDzdp);
			startActivity(intentDzdp);
			break;
		// ????????????
		case 2:
			Bundle bundle58Home = new Bundle();
			bundle58Home.putString("url", Constants.xmsUrlFor58Home);
			bundle58Home.putString("name", "????????????");
			Intent intent58Home = new Intent(this, WebViewActivity.class);
			intent58Home.putExtras(bundle58Home);
			startActivity(intent58Home);
			break;
		// ?????????
		case 3:
			Bundle bundleSpider = new Bundle();
			bundleSpider.putString("url", Constants.xmsUrlForSpider);
			bundleSpider.putString("name", "?????????");
			Intent intentSpider = new Intent(this, WebViewActivity.class);
			intentSpider.putExtras(bundleSpider);
			startActivity(intentSpider);
			break;
		// ??????100
		case 4:
			Bundle bundleExpress = new Bundle();
			bundleExpress.putString("url", Constants.xmsUrlForExpress);
			bundleExpress.putString("name", "????????????");
			Intent intentExpress = new Intent(this, WebViewActivity.class);
			intentExpress.putExtras(bundleExpress);
			startActivity(intentExpress);
			break;
		// ????????????
		case 5:
			Bundle bundleTranlate = new Bundle();
			bundleTranlate.putString("url", Constants.xmsUrlForTranlate);
			bundleTranlate.putString("name", "????????????");
			Intent intentTranlate = new Intent(this,WebViewActivity.class);
			intentTranlate.putExtras(bundleTranlate);
			startActivity(intentTranlate);
			break;
		// ????????????
		case 6:
			Bundle bundleJiudian = new Bundle();
			bundleJiudian.putString("url", Constants.xmsUrlForToutiao);
			bundleJiudian.putString("name", "????????????");
			Intent intentJiudian = new Intent(this,WebViewActivity.class);
			intentJiudian.putExtras(bundleJiudian);
			startActivity(intentJiudian);
			break;
		// ????????????
		case 7:
			Bundle bundleBoce = new Bundle();
			bundleBoce.putString("url", Constants.xmsUrlForZxzd);
			bundleBoce.putString("name", "????????????");
			Intent intentBoce = new Intent(this, WebViewActivity.class);
			intentBoce.putExtras(bundleBoce);
			startActivity(intentBoce);
			break;
		}
	}
	
	/**
	 * ????????????
	 * @param position
	 */
	private void switchNecessary(int position) {
		switch (position) {
		// ????????????
		case 0:
			Bundle bundleDidi = new Bundle();
			bundleDidi.putString("url", Constants.xmsUrlForDidi);
			bundleDidi.putString("name", "????????????");
			Intent intentDidi = new Intent(this, WebViewActivity.class);
			intentDidi.putExtras(bundleDidi);
			startActivity(intentDidi);
			break;
		// ????????????
		case 1:
			Bundle bundleFlight = new Bundle();
			bundleFlight.putString("url", Constants.xmsUrlForFight);
			bundleFlight.putString("name", "????????????");
			Intent intentFlight = new Intent(this, WebViewActivity.class);
			intentFlight.putExtras(bundleFlight);
			startActivity(intentFlight);
			break;
		// ????????????
		case 2:
			Bundle bundleTrain = new Bundle();
			bundleTrain.putString("url", Constants.xmsUrlForTrain);
			bundleTrain.putString("name", "????????????");
			Intent intentTrain = new Intent(this, WebViewActivity.class);
			intentTrain.putExtras(bundleTrain);
			startActivity(intentTrain);
			break;
		// ????????????
		case 3:
			Bundle bundle = new Bundle();
			bundle.putString("url", Constants.xmsUrlForLt100);
			bundle.putString("name", "????????????");
			Intent intent = new Intent(this, WebViewActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		// ????????????
		case 4:
			Bundle bundleBaidu = new Bundle();
			bundleBaidu.putString("url", Constants.xmsUrlForBaidu);
			bundleBaidu.putString("name", "????????????");
			Intent intentBaidu = new Intent(this, WebViewActivity.class);
			intentBaidu.putExtras(bundleBaidu);
			startActivity(intentBaidu);
			break;
			// ????????????
		case 5:
			Bundle bundleToutiao = new Bundle();
			bundleToutiao.putString("url", Constants.xmsUrlForJiudian);
			bundleToutiao.putString("name", "????????????");
			Intent intentToutiao = new Intent(this,WebViewActivity.class);
			intentToutiao.putExtras(bundleToutiao);
			startActivity(intentToutiao);
			break;	
		// ????????????
		case 6:
			Bundle bundleFlightm = new Bundle();
			bundleFlightm.putString("url", Constants.xmsUrlForFlight);
			bundleFlightm.putString("name", "????????????");
			Intent intentFlightm = new Intent(this, WebViewActivity.class);
			intentFlightm.putExtras(bundleFlightm);
			startActivity(intentFlightm);
			break;
		}
		
	}
	
	/**
	 * ????????????
	 */
	private void showAdDialog(final View rootView) {
		Log.i("tag", "getLayoutInflater");
		View view = getLayoutInflater().inflate(
				R.layout.xms_layout_dialog_ad, null);
		Log.i("tag", "PopupWindow");
		final PopupWindow adWindow = new PopupWindow(view,
				FormsUtil.SCREEN_WIDTH, FormsUtil.SCREEN_HEIGHT);
		Log.i("tag", "showAtLocation");
		adWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
		Log.i("tag", "findViewById");
		ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
		ivClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adWindow.dismiss();
				
				stopPlayBirthday();
			}
		});

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date curDate = new Date(System.currentTimeMillis());// ??????????????????
		String str = formatter.format(curDate);
		String year = str.substring(0, 4);
		Log.i("showAdDialog???????????????", year);
		final SharedPreferences sp = getSharedPreferences(
				Constants.CUSTOM_PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor mEditor = sp.edit();
		mEditor.putBoolean(year, true);
		mEditor.commit();
		
		playBirthday();
	}
	
	private Boolean isShowDialog() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date curDate = new Date(System.currentTimeMillis());// ??????????????????
		String str = formatter.format(curDate);
		String year = str.substring(0, 4);
		Log.i("???????????????", year);
		final SharedPreferences sp = this.getSharedPreferences(
				Constants.CUSTOM_PREFERENCE_NAME, Context.MODE_PRIVATE);
		boolean hasShowed = sp.getBoolean(year, false);
		if (!hasShowed) {
			Log.i("tag", "????????????????????????????????????true,????????????????????????");
			return true;
		} else {
			Log.i("tag", "??????????????????????????????????????????false,???????????????????????????");
			return false;
		}
	}
	
	private Boolean isBirthday(){
		try{
			final SharedPreferences sp = this.getSharedPreferences(
					Constants.CUSTOM_PREFERENCE_NAME, Context.MODE_PRIVATE);
			String cardId = sp.getString(Constants.CUSTOM_ID_NO, null);
			if(cardId != null){
				Log.i("tag", "???????????????" + cardId);
				IdcardInfoExtractor idcardInfoExtractor = new IdcardInfoExtractor(cardId);
				if (idcardInfoExtractor.getIsBirthday()) {
					return true;
				} else {
					return false;
				}
			}else{
				Log.i("tag", "???????????????");
				return false;
			}
		}catch(Exception e){
			return false;
		}
		
	}
	
	private void playBirthday(){
		Intent intent = new Intent(XmsMainActivity.this,SoundService.class);
        intent.putExtra("playing", true);
        startService(intent);
	}
	
	private void stopPlayBirthday(){
		Intent intent = new Intent(XmsMainActivity.this,SoundService.class);
        intent.putExtra("playing", false);
        startService(intent);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		requestMessagePre();
	}
	
	/**
	 * ??????????????????
	 */
	public void requestMessagePre() {
		if (LoginUtil.isLog(this)) {
			if (msgData == null) {
				requestMessage();
			} else {
				changeMsgCount();
			}
		}
	}
	
	/**
	 * ????????????
	 */
	private void requestMessage() {
		try {
			// ??????CSP XML??????
			String txCode = "MS002005";
			CspXmlXmsCom cspXmlXmsCom = new CspXmlXmsCom(LoginUtil.getUserId(this), txCode);
			cspXmlXmsCom.setPageNo("");
			String strXml = cspXmlXmsCom.getCspXml();
			Log.i("tag", "getMSgXml");
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			Log.i("tag", "Mcis");
			final byte[] byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
			Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
//			cspUtil.setTest(true);
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {
				
				@Override
				public void onSuccess(String responStr) {
					if(responStr!=null){
						MessageListXmlBean msgListXmlBean = MessageListResp.readStringXml(responStr);
						if(msgListXmlBean.getErrorcode().equals("00")){
							msgData = msgListXmlBean.getMessageList();
							getBaseApp().setMsgData(msgData);
							changeMsgCount();
						}
					}
				}

				@Override
				public void onFinish() {

				}
				@Override
				public void onFailure(String responStr) {
					
				}
			}, false);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ????????????????????????
	 */
    @SuppressWarnings("unchecked")
	private void changeMsgCount() {
    	if (msgData != null && msgData.size() > 0) {
			SharedPreferences sp = getSharedPreferences(MyMessageActivity.SELECT_ROLE, Context.MODE_PRIVATE);
			Map<String,Object> map = (Map<String, Object>) sp.getAll();
			Set<String> keyset = map.keySet();
			List<String> list = new ArrayList<String>(keyset);
			list.remove(MyMessageActivity.FIRST_SELECT);
			list.remove(MyMessageActivity.CURRENT_ROLE);
			//??????????????????
			int myMsgCount = 0;
			//????????????????????????
			int myReadCount = 0;
			//????????????????????????
			int finReadCount = 0;
			for(int i=0; i<msgData.size(); i++){
				if (!"20".equals(msgData.get(i).getType())) {
					myMsgCount++;
				}
			}
			for (int i = 0; i < list.size(); i++) {
				String key = list.get(i);
				int flag = 0;//?????????
				for(int j=0; j<msgData.size(); j++){
					if(key.equals(msgData.get(j).getMessageId())){
						flag=1;
						if (!"20".equals(msgData.get(j).getType())) {
							myReadCount++;
						} else {
							finReadCount++;
						}
					}
				}
				if(flag==0){
					sp.edit().remove(key).commit();
				}
			}
			AppInfo myMsgInfo = gvRemindAdapter.getItem(0);
			AppInfo finMsgInfo = gvRemindAdapter.getItem(1);
			if (myMsgInfo != null) {
				myMsgInfo.setMsgCount(myMsgCount - myReadCount);
			}
			if (finMsgInfo != null) {
				finMsgInfo.setMsgCount(msgData.size() - myMsgCount - finReadCount);
			}
			gvRemindAdapter.notifyDataSetChanged();
		}
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		if(ServiceWork.isServiceWork(XmsMainActivity.this, "com.bocop.xms.service.SoundService"));{
			stopPlayBirthday();
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if(ServiceWork.isServiceWork(XmsMainActivity.this, "com.bocop.xms.service.SoundService"));{
			stopPlayBirthday();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(ServiceWork.isServiceWork(XmsMainActivity.this, "com.bocop.xms.service.SoundService"));{
			stopPlayBirthday();
		}
	}

	@Override
	public void onLogin(int position) {
		requestMessage();
		switch(position){
		case MY_MESSAGE_CODE:
		case FINANCE_MESSAGE_CODE:
			int type = position == MY_MESSAGE_CODE ? 0 : 1;
			Intent intentMsg = new Intent(this, MyMessageActivity.class);
			intentMsg.putExtra("type", type);
			startActivity(intentMsg);
			break;
		case PAY_REMIND_CODE:
			Intent intentpPay = new Intent(this, PayRemindActivity.class);
			startActivity(intentpPay);
			break;
		case CUSTOM_REMIND_CODE:
			Intent intentpRemind = new Intent(this, CustomRemindActivity.class);
			startActivity(intentpRemind);
			break;
		case CREDIT_POINT_CODE:
			Intent intentDebitCard = new Intent(this, CreditCardActivity.class);
			startActivity(intentDebitCard);
			break;
		}
	}

	@Override
	public void onLogin() {
		
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
}
