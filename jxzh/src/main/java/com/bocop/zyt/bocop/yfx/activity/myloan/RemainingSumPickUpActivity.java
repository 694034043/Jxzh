package com.bocop.zyt.bocop.yfx.activity.myloan;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.BocOpUtil;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.JsonUtils;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.xms.bean.ConstHead;
import com.bocop.zyt.bocop.xms.utils.KeyboardUtils;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.yfx.activity.LoanMainActivity;
import com.bocop.zyt.bocop.yfx.bean.Period;
import com.bocop.zyt.bocop.yfx.bean.PickUpDataResponse;
import com.bocop.zyt.bocop.yfx.bean.PickUpDetails;
import com.bocop.zyt.bocop.yfx.bean.SA0075;
import com.bocop.zyt.bocop.yfx.bean.SA0075Card;
import com.bocop.zyt.bocop.yfx.utils.CheckoutUtil;
import com.bocop.zyt.bocop.yfx.utils.DataFormatUtil;
import com.bocop.zyt.bocop.yfx.xml.CspXmlYfxCom;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.tools.DialogUtil;
import com.bocop.zyt.jx.view.LoadingView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * ????????????
 * 
 * @author lh
 * 
 */
@ContentView(R.layout.yfx_activity_remaining_sum_pick_up)
public class RemainingSumPickUpActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tvTitle;
	@ViewInject(R.id.tvRemaining)
	private TextView tvRemaining;
	// @ViewInject(R.id.seekDeadline)
	// private DiscreteSeekBar seekDeadline;
	@ViewInject(R.id.tvDeadline)
	private TextView tvDeadline;
	@ViewInject(R.id.etUseSum)
	private EditText etUseSum;
	// @ViewInject(R.id.tvRefundPerStage)
	// private TextView tvRefundPerStage;
	@ViewInject(R.id.tvUse)
	private TextView tvUse;
	@ViewInject(R.id.tvRefundWay)
	private TextView tvRefundWay;
	@ViewInject(R.id.tvRefundCard)
	private TextView tvRefundCard;
	@ViewInject(R.id.tvToCard)
	private TextView tvToCard;
	@ViewInject(R.id.tvRefundIst)
	private TextView tvRefundIst;
	@ViewInject(R.id.btnAffirm)
	private Button btnAffirm;
	@ViewInject(R.id.loadingView)
	private LoadingView loadingView;
	@ViewInject(R.id.scrollView)
	private ScrollView scrollView;

	private List<Period> periodList = new ArrayList<>();
	private List<String> useList = new ArrayList<>();
	private List<String> useIDList = new ArrayList<>();
	private List<String> methodList = new ArrayList<>();
	private List<String> methodIDList = new ArrayList<>();
	private double rate;
	private double rateB;
	private double rateX;
	private PickUpDetails details = new PickUpDetails();

	private CustomProgressDialog dialog;

	private String[] cardArray;// ??????
	private String[] lmtamtArray;// ??????????????????
	private String[] cardArrayStr;

	private double remainSum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tvTitle.setText("????????????");
		dialog = new CustomProgressDialog(this, "...????????????...", R.anim.frame);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		initData();
		requestPickUpData(false);
	}

	@OnClick({ R.id.tvUse, R.id.tvRefundCard, R.id.tvToCard, R.id.tvDeadline, R.id.btnAffirm })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvUse:
			KeyboardUtils.closeInput(this, tvUse);
			final String[] useString = useList.toArray(new String[useList.size()]);
			DialogUtil.showToSelect(this, "", useString, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					tvUse.setText(useString[which]);
					details.setUse(useString[which]);
					details.setUseID(which + "");
				}
			});
			break;
		case R.id.tvRefundCard:
			KeyboardUtils.closeInput(this, tvRefundCard);
			// final String[] rfcString = cardList.toArray(new
			// String[cardList.size()]);
			DialogUtil.showToSelect(this, "", cardArrayStr, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					tvRefundCard.setText(cardArrayStr[which]);
					details.setRepayCard(cardArray[which]);
					details.setRepayCardID(lmtamtArray[which]);
				}
			});
			break;
		// case R.id.tvRefundWay:
		// KeyboardUtils.closeInput(this, tvRefundWay);
		// final String[] rfwString = methodList.toArray(new
		// String[methodList.size()]);
		// DialogUtil.showToSelect(this, "", rfwString, new OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// tvRefundWay.setText(rfwString[which]);
		// // details.setMethodID(methodIDList.get(which));
		// if (which == 0) {
		// rate = rateB;
		// details.setMethod("????????????????????????????????????");
		// details.setMethodID("1");
		// } else if (which == 1) {
		// rate = rateX;
		// details.setMethod("??????????????????");
		// details.setMethodID("2");
		// }
		// tvRefundIst.setText(rate * 100 + "%");
		//
		// }
		// });
		// break;
		case R.id.tvToCard:
			KeyboardUtils.closeInput(this, tvToCard);
			// final String[] tcString = {};
			DialogUtil.showToSelect(this, "", cardArrayStr, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					tvToCard.setText(cardArrayStr[which]);
					details.setDrawingCard(cardArray[which]);
					details.setDrawingCardID(lmtamtArray[which]);
				}
			});
			break;
		case R.id.tvDeadline:
			KeyboardUtils.closeInput(this, tvDeadline);
			final String[] periodStr = new String[periodList.size()];
			for (int i = 0; i < periodList.size(); i++) {
//				periodStr[i] = periodList.get(i).getPeriodString() + "???";
				if (i < 12) {
					int date = (i+1) * 30;
					periodStr[i] = date + "???";
				} else {
					periodStr[i] = Integer.parseInt(periodList.get(i).getPeriodID())/12 + "???";
				}
			}
			DialogUtil.showToSelect(this, "", periodStr, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					tvDeadline.setText(periodStr[which]);
					details.setPeriod(periodStr[which]);
					details.setPeriodID(periodList.get(which).getPeriodID());
				}
			});
			break;

		case R.id.btnAffirm:
			KeyboardUtils.closeInput(this, btnAffirm);
			if (checkData()) {
				// dialog.show();
				// requestBocopForCheckMsg();
				// requestAffirm();//??????
				Intent intent = new Intent(this, PickAffirmActivity.class);
				Bundle bundle = new Bundle();
				details.setPickUpAmount(etUseSum.getText().toString());
				details.setRate(rateB);
				details.setMethod("????????????????????????????????????");
				details.setMethodID("1");
				bundle.putSerializable("PICK_UP_DATA", details);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		}
	}

	private boolean checkData() {
		double drawAmt = 0.0;
		if (!TextUtils.isEmpty(etUseSum.getText().toString())) {
			drawAmt = Double.parseDouble(etUseSum.getText().toString());
		}
		if (CheckoutUtil.isEmpty(etUseSum.getText().toString())) {
			Toast.makeText(this, R.string.inputAmount, Toast.LENGTH_SHORT).show();
			return false;
		} else if ("0".equals(tvDeadline.getText().toString())) {
			Toast.makeText(this, R.string.choosePeriod, Toast.LENGTH_SHORT).show();
			return false;
		} else if ("?????????".equals(tvUse.getText().toString())) {
			Toast.makeText(this, R.string.chooseUse, Toast.LENGTH_SHORT).show();
			return false;
		} else if ("?????????".equals(tvRefundWay.getText().toString())) {
			Toast.makeText(this, R.string.chooseMethod, Toast.LENGTH_SHORT).show();
			return false;
		} else if ("?????????".equals(tvRefundCard.getText().toString())) {
			Toast.makeText(this, R.string.chooseRefundCard, Toast.LENGTH_SHORT).show();
			return false;
		} else if ("?????????".equals(tvToCard.getText().toString())) {
			Toast.makeText(this, R.string.chooseToCard, Toast.LENGTH_SHORT).show();
			return false;
		} else if (drawAmt < 100) {
			Toast.makeText(this, "????????????????????????100???", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	private void initData() {
		useList.clear();
		if (LoanMainActivity.PRO_FLAG == 8 || LoanMainActivity.PRO_FLAG == 9) {
			useList.add("????????????");
		} else {
			useList.add("??????");
			useList.add("??????");
			useList.add("??????");
			useList.add("??????");
			useList.add("??????");
			useList.add("??????");
			useList.add("????????????");
		}
		
		methodList.clear();
		methodList.add("????????????????????????????????????");
		// methodList.add("??????????????????");

		periodList.clear();
		for (int i = 1; i <= 12; i++) {
			Period period = new Period();
			period.setPeriodString(i + "");
			period.setPeriodID(i + "");
			periodList.add(period);
		}
		if (LoanMainActivity.PRO_FLAG == 8 || LoanMainActivity.PRO_FLAG == 9) {
			for (int i = 2; i <= 5; i++) {
				Period period = new Period();
				period.setPeriodString(i*12 + "");
				period.setPeriodID(i*12 + "");
				periodList.add(period);
			}
		}
	}

	private void initListener() {

		// seekDeadline.setNumericTransformer(new NumericTransformer() {
		//
		// @Override
		// public int transform(int value) {
		// if (value > 0) {
		// if (periodList.size() > 0) {
		// tvDeadline.setText(periodList.get(value - 1).getPeriodString());
		// details.setPeriodID(periodList.get(value - 1).getPeriodID());
		// if (!TextUtils.isEmpty(etUseSum.getText().toString())) {
		// String str = DataFormatUtil.moneyStringFormat(
		// (Double.parseDouble(etUseSum.getText().toString()) * rate / 360) +
		// "");
		// // tvRefundPerStage.setText(str);
		// }
		// return Integer.parseInt(periodList.get(value - 1).getPeriodString());
		// } else {
		// tvDeadline.setText(value + "");
		// return value;
		// }
		// } else {
		// tvDeadline.setText(value + "");
		// return value;
		// }
		// }
		// });

		etUseSum.addTextChangedListener(new TextChangeWatcher(etUseSum));
	}

	/**
	 * ??????????????????
	 */
	private void requestPickUpData(final boolean isShowDialog) {
		try {
			CspXmlYfxCom cXmlYfxCom = new CspXmlYfxCom("WL002008", getCacheBean().get(CacheBean.CUST_ID).toString(),
					LoginUtil.getUserId(this));
			String strXml = cXmlYfxCom.getCspXml();
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			byte[] byteMessage = {};
			byteMessage = mcis.getMcis();

			// ????????????
			CspUtil cspUtil = new CspUtil(this);
			cspUtil.setFLAG_YFX_CSP(true);
			cspUtil.setTxCode("002008");
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {

				@Override
				public void onSuccess(String responStr) {
					onGetDataSuccess(responStr);
				}

				@Override
				public void onFinish() {

				}

				@Override
				public void onFailure(String responStr) {
					dialog.cancel();
					CspUtil.onFailure(RemainingSumPickUpActivity.this, responStr);
					loadingView.setVisibility(View.VISIBLE);
					loadingView.setmOnRetryListener(new LoadingView.OnRetryListener() {

						@Override
						public void retry() {
							requestPickUpData(isShowDialog);
						}
					});
				}
			}, isShowDialog);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ????????????????????????????????????
	 */
	private void requestBocopForUserCard() {
		Gson gson = new Gson();
		// List<Map<String,String>> list =new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("USRID", LoginUtil.getUserId(this));
		map.put("ifncal", "0");
		map.put("pageno", "1");
		final String strGson = gson.toJson(map);

		BocOpUtil bocOpUtil = new BocOpUtil(this);
		bocOpUtil.postOpbocNoDialog(strGson, TransactionValue.SA0075, new BocOpUtil.CallBackBoc() {

			@Override
			public void onSuccess(String responStr) {
				SA0075 sa0075;
				Log.i("tag1", responStr);
				try {
					sa0075 = JsonUtils.getObject(responStr, SA0075.class);
					Log.i("tag", String.valueOf(sa0075.getSaplist().size()));
					if (sa0075.getSaplist().size() > 0) {
						cardArray = new String[sa0075.getSaplist().size()];
						lmtamtArray = new String[sa0075.getSaplist().size()];
						for (int i = 0; i < sa0075.getSaplist().size(); i++) {
							SA0075Card sa0075Card = sa0075.getSaplist().get(i);
							cardArray[i] = sa0075Card.accno;
							lmtamtArray[i] = sa0075Card.lmtamt;
							Log.i("tag", "???" + String.valueOf(i) + sa0075Card.accno);
							Log.i("tag1", "???" + String.valueOf(i) + sa0075Card.lmtamt);
						}

						cardArrayStr = new String[cardArray.length];
						for (int i = 0; i < cardArray.length; i++) {
							String num = cardArray[i];
							Log.i("tag", String.valueOf(cardArray.length));
							if (num.length() == 19) {
								cardArrayStr[i] = cardArray[i].substring(0, 4) + "***********"
										+ cardArray[i].substring(15);
							} else {
								cardArrayStr[i] = cardArray[i].substring(0, 4) + "***********"
										+ cardArray[i].substring(12);
							}
							Log.i("tag", "???" + String.valueOf(i) + ":" + "  " + cardArrayStr[i]);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(RemainingSumPickUpActivity.this, "????????????????????????????????????", Toast.LENGTH_LONG).show();
				}
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

				loadingView.setVisibility(View.VISIBLE);
				loadingView.setmOnRetryListener(new LoadingView.OnRetryListener() {

					@Override
					public void retry() {
						requestBocopForUserCard();
					}
				});
				Log.i("tag", responStr);
				// if (responStr.equals("3800015")) {
				// Toast.makeText(TrafficPayActivity.this,
				// "?????????????????????????????????",
				// Toast.LENGTH_LONG).show();
				// }
			}
		});
	}

	/**
	 * 
	 * ???????????????????????????
	 * 
	 * @param responStr
	 */
	private void onGetDataSuccess(String responStr) {
		PickUpDataResponse dataResponse = XStreamUtils.getFromXML(responStr, PickUpDataResponse.class);
		ConstHead constHead = dataResponse.getConstHead();
		if (constHead != null) {
			if ("00".equals(constHead.getErrCode())) {
				if (null != dataResponse.getPickUpData()) {
					loadingView.setVisibility(View.GONE);
					scrollView.setVisibility(View.VISIBLE);
					if (dataResponse.getPickUpData().getRemainningSum() != null) {
						remainSum = Double.parseDouble(dataResponse.getPickUpData().getRemainningSum().replaceAll(",", ""));
					}
					if (!TextUtils.isEmpty(dataResponse.getPickUpData().getRemainningSum())) {
						tvRemaining.setText(DataFormatUtil.moneyStringFormat(remainSum + ""));
					}
					// periodList.clear();
					// periodList.addAll(dataResponse.getPickUpData().getPeriodList());
					// for (int i = 0; i <
					// dataResponse.getPickUpData().getUseList().size(); i++) {
					// useList.add(dataResponse.getPickUpData().getUseList().get(i).getUse());
					// useIDList.add(dataResponse.getPickUpData().getUseList().get(i).getUseID());
					// }
					// for (int i = 0; i <
					// dataResponse.getPickUpData().getCardList().size(); i++) {
					// cardList.add(dataResponse.getPickUpData().getCardList().get(i).getCardString());
					// }
					// for (int i = 0; i <
					// dataResponse.getPickUpData().getMethodList().size(); i++) {
					// methodList.add(dataResponse.getPickUpData().getMethodList().get(i).getMethodString());
					// methodIDList.add(dataResponse.getPickUpData().getMethodList().get(i).getMethodID());
					// }
					String phone = dataResponse.getPickUpData().getPhone();
					String phone1 = "";
					if (!TextUtils.isEmpty(phone)) {
						phone1 = phone.substring(0, 3) + "****" + phone.substring(7);
						details.setPhone(phone);
					}
					if (!TextUtils.isEmpty(dataResponse.getPickUpData().getRateB())) {
						rateB = Double.parseDouble(dataResponse.getPickUpData().getRateB());
					}
					if (!TextUtils.isEmpty(dataResponse.getPickUpData().getRateX())) {
						rateX = Double.parseDouble(dataResponse.getPickUpData().getRateX());
					}
					// seekDeadline.setMax(periodList.size());
					tvRefundIst.setText("?????????" + DataFormatUtil.moneyStringFormat(rateB / 360 * 10000 + ""));
					// ???????????????
					details.setEtoken(dataResponse.getPickUpData().getEtoken());
				}
			} else if ("50".equals(constHead.getErrCode())) {
				DialogUtil.showWithToMain(RemainingSumPickUpActivity.this, constHead.getErrMsg());
			} else {
				CspUtil.onFailure(this, responStr);
				loadingView.setVisibility(View.VISIBLE);
				loadingView.setmOnRetryListener(new LoadingView.OnRetryListener() {
	
					@Override
					public void retry() {
						requestPickUpData(true);
					}
				});
			}
		}
		tvRefundWay.setText("????????????????????????????????????");
		initListener();
		requestBocopForUserCard();
	}

	/**
	 * ??????????????????????????????
	 * 
	 */
	private class TextChangeWatcher implements TextWatcher {

		private EditText editText;

		public TextChangeWatcher(EditText editText) {
			super();
			this.editText = editText;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			String str = s.toString();
			if (!TextUtils.isEmpty(str)) {
				if (str.startsWith(".")) {
					str = "0" + str;
					editText.setText(str);
					editText.setSelection(str.length());
				}
				if (str.contains(".")) {
					if (str.length() - str.lastIndexOf(".") > 3) {
						str = str.substring(0, str.lastIndexOf(".") + 3);
						editText.setText(str);
						editText.setSelection(str.length());
					}
				}

				if (compare(str)) {
					str = remainSum + "";
					editText.setText(str);
					editText.setSelection(str.length());
				}

				// double period =
				// Double.parseDouble(tvDeadline.getText().toString());
				// if (0 != period) {
				// String refundPS = DataFormatUtil
				// .moneyStringFormat((Double.parseDouble(etUseSum.getText().toString())
				// * rate / 365) + "");
				// // tvRefundPerStage.setText(refundPS);
				// }
			}
		}

		private boolean compare(String s) {

			double refund = Double.parseDouble(s.replaceAll(",", ""));
			if (refund > remainSum) {
				return true;
			} else {
				return false;
			}
		}
	}

}
