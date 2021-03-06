package com.bocop.zyt.bocop.yfx.activity.myloan;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.BocOpUtil;
import com.bocop.zyt.bocop.jxplatform.util.BocOpUtilWithoutDia;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.JsonUtils;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.xms.bean.ConstHead;
import com.bocop.zyt.bocop.xms.utils.KeyboardUtils;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.yfx.activity.LoanMainActivity;
import com.bocop.zyt.bocop.yfx.bean.ApplyHistory;
import com.bocop.zyt.bocop.yfx.bean.CommonResponse;
import com.bocop.zyt.bocop.yfx.bean.EtokenCheckResult;
import com.bocop.zyt.bocop.yfx.bean.EtokenNo;
import com.bocop.zyt.bocop.yfx.bean.MsgCode;
import com.bocop.zyt.bocop.yfx.bean.PickUpDetails;
import com.bocop.zyt.bocop.yfx.fragment.UseLoanFragment;
import com.bocop.zyt.bocop.yfx.utils.DataFormatUtil;
import com.bocop.zyt.bocop.yfx.utils.DateCalculatorUtil;
import com.bocop.zyt.bocop.yfx.utils.TimeCountUtil;
import com.bocop.zyt.bocop.yfx.view.ResultDialog;
import com.bocop.zyt.bocop.yfx.xml.CspXmlYfx009;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.tools.DialogUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lh on 16-1-15.
 */
@ContentView(R.layout.yfx_activity_pick_affirm)
public class PickAffirmActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tvTitle;
	@ViewInject(R.id.tvPickUpAmt)
	private TextView tvPickUpAmt;
	@ViewInject(R.id.tvLoanIst)
	private TextView tvLoanIst;
	@ViewInject(R.id.tvPeriod)
	private TextView tvPeriod;
	@ViewInject(R.id.tvLoanUse)
	private TextView tvLoanUse;
	@ViewInject(R.id.tvToCard)
	private TextView tvToCard;
	@ViewInject(R.id.tvRefundWay)
	private TextView tvRefundWay;
	@ViewInject(R.id.tvRefundCard)
	private TextView tvRefundCard;
	@ViewInject(R.id.tvCheckCode)
	private TextView tvCheckCode;
	@ViewInject(R.id.etCheckCode)
	private TextView etCheckCode;
	@ViewInject(R.id.tvGetCheckCode)
	private TextView tvGetCheckCode;
	@ViewInject(R.id.etDynamicCommand)
	private TextView etDynamicCommand;
	@ViewInject(R.id.rlCommand)
	private RelativeLayout rlCommand;


	private PickUpDetails details;
	private CustomProgressDialog dialog;
	private List<ApplyHistory> list = new ArrayList<>();
	/**
	 * ??????????????????
	 */
	String[] dateStr;

	private String etokenno;
	private boolean ETOKEN_FLAG = false;
	private boolean etokenFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		initView();
//		requestEtoken();
	}

	private void initData() {
		if (null != getIntent().getExtras()) {
			details = (PickUpDetails) getIntent().getSerializableExtra("PICK_UP_DATA");
			double pickUpAmount = 0;
			double etoken = 0;
			if (details.getPickUpAmount() != null) {
				pickUpAmount = Double.parseDouble(details.getPickUpAmount());
			}
			if (details.getEtoken() != null) {
				etoken = Double.parseDouble(details.getEtoken());
			}
			if (etoken >= pickUpAmount) {
				etokenFlag = true;
				rlCommand.setVisibility(View.INVISIBLE);
			}
		}
		tvTitle.setText("????????????");
		dialog = new CustomProgressDialog(this, "...????????????...", R.anim.frame);
		dialog.setCanceledOnTouchOutside(false);
	}

	private void initView() {
		tvPickUpAmt.setText(details.getPickUpAmount() + "???");
		tvLoanIst.setText("?????????" + DataFormatUtil.moneyStringFormat(details.getRate() / 360 * 10000 + ""));
		tvPeriod.setText(details.getPeriod());
		tvLoanUse.setText(details.getUse());
		tvToCard.setText(getHideCard(details.getDrawingCard()));
		tvRefundWay.setText(details.getMethod());
		tvRefundCard.setText(getHideCard(details.getRepayCard()));

	}

	private String getHideCard(String cardNo) {
		if (cardNo.length() == 19) {
			cardNo = cardNo.substring(0, 4) + "***********" + cardNo.substring(15);
		} else {
			cardNo = cardNo.substring(0, 4) + "***********" + cardNo.substring(12);
		}

		return cardNo;
	}

	@OnClick({ R.id.btnAffirm, R.id.tvGetCheckCode, R.id.tvCalResult })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAffirm:
			if (checkNull()) {
				String message = "";
				if (LoanMainActivity.PRO_FLAG == 2) {
					message = getString(R.string.supportAgriculture);
				} else if (LoanMainActivity.PRO_FLAG == 1 || LoanMainActivity.PRO_FLAG == 8 
						|| LoanMainActivity.PRO_FLAG == 9) {
					message = getString(R.string.supportPoor);
				} else {
					message = getString(R.string.supportOther);
				}
				DialogUtil.showWithTwoBtnAndTitle(this, "??????", message, "??????", "??????", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						requestAffirm2();
					}
				}, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
			}
			break;
		case R.id.tvGetCheckCode:
			KeyboardUtils.closeInput(this, tvGetCheckCode);
			tvGetCheckCode.setBackgroundResource(R.drawable.yfx_shape_count_check_code);
			TimeCountUtil time = new TimeCountUtil(tvGetCheckCode, 60);
			time.countTime();
			requestBocForTelMsg();
			break;
		case R.id.tvCalResult:
			showResult();
			break;
		}
	}

	private void showResult() {
		list.clear();
		int[] days = calInterval();
		double rate = details.getRate() / 365;
		double amt = Double.parseDouble(details.getPickUpAmount());
		ApplyHistory applyHistory;
		for (int i = 0; i < days.length; i++) {
			applyHistory = new ApplyHistory();
			//TODO LH
			/**????????????30???*/
//			String interest = getFormatedStr((amt * rate * days[i]) + "");
			String interest = getFormatedStr((amt * rate * 30) + "");
			applyHistory.setInterest(interest);
			applyHistory.setRepaymentDate(dateStr[i + 1]);
			if (i != days.length - 1) {
				applyHistory.setInterestTotal(getFormatedStr(interest));
				applyHistory.setPrincipal("0");
			} else {
				//TODO LH
				/**????????????30???*/
//				applyHistory.setInterestTotal(getFormatedStr((amt * (rate * days[i] + 1)) + ""));
				applyHistory.setInterestTotal(getFormatedStr((amt * (rate * 30 + 1)) + ""));
				applyHistory.setPrincipal(getFormatedStr(amt + ""));
			}
			list.add(applyHistory);
		}
		ResultDialog dialog = new ResultDialog(this);
		dialog.show(list);
	}

	private String getFormatedStr(String str) {
		return DataFormatUtil.moneyStringFormat(str);
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @author lh
	 * @return
	 */
	private int[] calInterval() {

		String date = DataFormatUtil.getDaytimeOnly(new Date(), false);
		// String date = "2019-02-01";
		double periodD = Double.parseDouble(details.getPeriodID());
		dateStr = new String[(int) periodD + 1];
		dateStr[0] = date.replaceAll("-", "");

		for (int i = 0; i < periodD; i++) {
			String dateP;
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			String day = date.substring(8);
			int monthD = Integer.parseInt(month);
			monthD += (i + 1);
			if (monthD < 10) {
				month = "0" + monthD;
				dateP = year + month + day;
			} else {
				if (monthD > 12) {
					year = (Integer.parseInt(year) + 1) + "";
					monthD -= 12;
					month = "0" + monthD;
					dateP = year + month + day;
				} else {
					dateP = year + monthD + day;
				}
			}
			dateStr[i + 1] = dateP;
		}

		int[] days = new int[dateStr.length - 1];
		for (int i = 0; i < days.length; i++) {
			try {
				days[i] = DateCalculatorUtil.calInterval(new SimpleDateFormat("yyyyMMdd").parse(dateStr[i]),
						new SimpleDateFormat("yyyyMMdd").parse(dateStr[i + 1]), "D");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return days;
	}

	private boolean checkNull() {
		if (TextUtils.isEmpty(etCheckCode.getText().toString())) {
			Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!etokenFlag) {
			if (TextUtils.isEmpty(etDynamicCommand.getText().toString())) {
				Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		return true;
	}

	/**
	 * ???????????????
	 */
	private void requestBocForTelMsg() {

		BocOpUtilWithoutDia bocOpUtil = new BocOpUtilWithoutDia(this);

		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("usrid", LoginUtil.getUserId(this));
		map.put("usrtel", details.getPhone());
		map.put("randtrantype", TransactionValue.messageType);
		final String strGson = gson.toJson(map);

		bocOpUtil.postOpboc(strGson, TransactionValue.SA7114, new BocOpUtilWithoutDia.CallBackBoc2() {

			MsgCode msgCode;

			@Override
			public void onSuccess(String responStr) {
				Log.i("tag", responStr);
				try {
					msgCode = JsonUtils.getObject(responStr, MsgCode.class);
					Log.i("tag0", msgCode.getCode());
					if (msgCode.getCode().equals("0")) {
						Log.i("tag1", msgCode.getCode());
						String phone1 = "";
						phone1 = details.getPhone().substring(0, 3) + "****" + details.getPhone().substring(7);
						Toast.makeText(PickAffirmActivity.this, "????????????" + phone1 + "????????????", Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onFailure(String responStr) {
				if (responStr.equals("0") || responStr.equals("1")) {
					Toast.makeText(PickAffirmActivity.this, R.string.onFailure, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(PickAffirmActivity.this, responStr, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onStart() {

			}
		});
	}

	/**
	 * ?????????????????????
	 */
	private void requestBocopForCheckMsg() {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		// List<Map<String,String>> list =new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("usrid", LoginUtil.getUserId(this));
		map.put("usrtel", details.getPhone());
		map.put("randtrantype", TransactionValue.messageType);
		map.put("mobcheck", etCheckCode.getText().toString());
		final String strGson = gson.toJson(map);

		BocOpUtil bocOpUtil = new BocOpUtil(this);
		bocOpUtil.postOpbocNoDialog(strGson, TransactionValue.SA7115, new BocOpUtil.CallBackBoc() {

			@Override
			public void onSuccess(String responStr) {
				Log.i("tag", responStr);

				requestAffirm();
			}

			@Override
			public void onStart() {
				Log.i("tag", "??????GSON?????????" + strGson);
			}

			@Override
			public void onFinish() {
				dialog.cancel();
			}

			@Override
			public void onFailure(String responStr) {
				CspUtil.onFailure(PickAffirmActivity.this, responStr);
			}
		});
	}

	/**
	 * ??????Etoken
	 */
	private void requestEtoken() {
		final Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("usrid", LoginUtil.getUserId(this));
		final String strGson = gson.toJson(map);

		BocOpUtil bocOpUtil = new BocOpUtil(this);
		bocOpUtil.postOpbocNoDialog(strGson, TransactionValue.SA0069, new BocOpUtil.CallBackBoc() {

			@Override
			public void onSuccess(String responStr) {
				Log.i("tag", responStr);
				EtokenNo etokenNo = gson.fromJson(responStr, EtokenNo.class);
				etokenno = etokenNo.getEtokenno();
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
				CspUtil.onFailure(PickAffirmActivity.this, responStr);
			}
		});
	}

	/**
	 * ??????Etoken
	 */
	private boolean checkEtoken() {
		final Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("etokenno", etokenno);
		map.put("etokenval", etDynamicCommand.getText().toString());
		map.put("usrid", LoginUtil.getUserId(this));
		final String strGson = gson.toJson(map);

		BocOpUtil bocOpUtil = new BocOpUtil(this);
		bocOpUtil.postOpbocNoDialog(strGson, TransactionValue.SA0083, new BocOpUtil.CallBackBoc() {

			@Override
			public void onSuccess(String responStr) {
				Log.i("tag", responStr);
				EtokenCheckResult result = gson.fromJson(responStr, EtokenCheckResult.class);
				if ("0".equals(result)) {
					ETOKEN_FLAG = true;
				} else {
					ETOKEN_FLAG = false;
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
				ETOKEN_FLAG = false;
				CspUtil.onFailure(PickAffirmActivity.this, responStr);
			}
		});

		return ETOKEN_FLAG;
	}

	/**
	 * ????????????
	 */
	private void requestAffirm() {
		try {
			CspXmlYfx009 cspXmlYfx009 = new CspXmlYfx009(getCacheBean().get(CacheBean.CUST_ID).toString(),
					details.getPickUpAmount(), details.getUseID(), details.getPeriodID(), details.getMethodID(),
					details.getRepayCard(), details.getDrawingCard(), details.getRepayCardID(),
					details.getDrawingCardID());

			String strXml = cspXmlYfx009.getCspXml();
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			byte[] byteMessage = {};
			byteMessage = mcis.getMcis();

			// ????????????
			CspUtil cspUtil = new CspUtil(this);
			cspUtil.setFLAG_YFX_CSP(true);
			cspUtil.setTxCode("001009");
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {

				@Override
				public void onSuccess(String responStr) {
					CommonResponse commonResponse = XStreamUtils.getFromXML(responStr, CommonResponse.class);
					ConstHead constHead = commonResponse.getConstHead();
					if (constHead != null) {
						dialog.cancel();
						if ("00".equals(constHead.getErrCode())) {
							UseLoanFragment.LOAN_CHANGE_FLAG = true;
							Intent intent = new Intent(PickAffirmActivity.this, PickUpAffirmActivity.class);
							intent.putExtra("ERR_CODE", constHead.getErrCode());
							intent.putExtra("ERR_MSG", constHead.getErrMsg());
							startActivity(intent);
						} else if ("50".equals(constHead.getErrCode())) {
							DialogUtil.showWithToMain(PickAffirmActivity.this, constHead.getErrMsg());
						} else {
							CspUtil.onFailure(PickAffirmActivity.this, constHead.getErrMsg());
						}
					}
				}

				@Override
				public void onFinish() {
					dialog.cancel();
				}

				@Override
				public void onFailure(String responStr) {
					dialog.cancel();
					CspUtil.onFailure(PickAffirmActivity.this, responStr);
				}
			}, false);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void requestAffirm2() {
		String isEtoken = etokenFlag ? "N" : "Y";
		RequestBody body = new FormEncodingBuilder().add("appUserId", LoginUtil.getUserId(this))
				.add("actoken", LoginUtil.getToken(this))
				.add("eTokenCode", etDynamicCommand.getText().toString())
				.add("userId", getCacheBean().get(CacheBean.CUST_ID).toString())
				.add("drawingAmount", details.getPickUpAmount()).add("repId", details.getPeriodID())
				.add("methodId", details.getMethodID()).add("repaymentCard", details.getRepayCard())
				.add("repaymentCardId", details.getRepayCardID()).add("drawingCard", details.getDrawingCard())
				.add("drawingCardId", details.getDrawingCardID()).add("telNo", details.getPhone())
				.add("telCode", etCheckCode.getText().toString())
				.add("client", "YA")
				.add("useId", details.getUseID())
				.add("WLS_DRAWING_IS_ETOKEN", isEtoken)
				.build();
		
		CspUtil cspUtil = new CspUtil(this);
		cspUtil.postCspNoLogin(BocSdkConfig.YFX_DRAWING_CONFIRM, body, true, new CspUtil.CallBack() {

			@Override
			public void onSuccess(String responStr) {
				CommonResponse commonResponse = XStreamUtils.getFromXML(responStr, CommonResponse.class);
				ConstHead constHead = commonResponse.getConstHead();
				if (null != constHead) {
							if (constHead.getErrCode().equals("00")) {
								UseLoanFragment.LOAN_CHANGE_FLAG = true;
								Intent intent = new Intent(
										PickAffirmActivity.this,
										PickUpAffirmActivity.class);
								intent.putExtra("ERR_CODE",
										constHead.getErrCode());
								intent.putExtra("ERR_MSG",
										constHead.getErrMsg());
								startActivity(intent);
							} else if (constHead.getErrCode().equals("89")) {
								UseLoanFragment.LOAN_CHANGE_FLAG = true;
								Intent intent = new Intent(
										PickAffirmActivity.this,
										PickUpAffirmActivity.class);
								intent.putExtra("ERR_CODE",
										constHead.getErrCode());
								intent.putExtra("ERR_MSG",
										constHead.getErrMsg());
								startActivity(intent);
							} else if ("50".equals(constHead.getErrCode())) {
								DialogUtil.showWithToMain(PickAffirmActivity.this, constHead.getErrMsg());
							} else {
								CspUtil.onFailure(PickAffirmActivity.this, constHead.getErrMsg());
							}
				}
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(String responStr) {
				CspUtil.onFailure(PickAffirmActivity.this, responStr);
			}
		});
	}
}
