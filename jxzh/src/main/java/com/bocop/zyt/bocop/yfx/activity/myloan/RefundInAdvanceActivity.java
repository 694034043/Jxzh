package com.bocop.zyt.bocop.yfx.activity.myloan;

import android.annotation.SuppressLint;
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
import com.bocop.zyt.bocop.jxplatform.util.JsonUtils;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.xms.bean.ConstHead;
import com.bocop.zyt.bocop.xms.utils.KeyboardUtils;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.yfx.bean.CommonResponse;
import com.bocop.zyt.bocop.yfx.bean.RefundData;
import com.bocop.zyt.bocop.yfx.bean.RefundDataResponse;
import com.bocop.zyt.bocop.yfx.view.RefundPlanDialog;
import com.bocop.zyt.bocop.yfx.view.SlideSwitch;
import com.bocop.zyt.bocop.yfx.xml.CspXmlYfx011;
import com.bocop.zyt.bocop.yfx.xml.CspXmlYfx014;
import com.bocop.zyt.bocop.yfx.xml.CspXmlYfx017;
import com.bocop.zyt.bocop.yfx.xml.repayment.RepaymentBean;
import com.bocop.zyt.bocop.yfx.xml.repayment.RepaymentListResp;
import com.bocop.zyt.bocop.yfx.xml.repayment.RepaymentListXmlBean;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.tools.DialogUtil;
import com.bocop.zyt.jx.view.LoadingView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ????????????
 *
 * @author lh
 */
@ContentView(R.layout.yfx_activity_refund_in_advance)
public class RefundInAdvanceActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tvTitle;
	@ViewInject(R.id.loadingView)
	private LoadingView loadingView;
	@ViewInject(R.id.scrollView)
	private ScrollView scrollView;
	/**
	 * ??????????????????
	 */
	@ViewInject(R.id.tvRemainToRefund)
	private TextView tvRemainToRefund;
	/**
	 * ????????????
	 */
	@ViewInject(R.id.tvLoanAmount)
	private TextView tvLoanAmount;
	/**
	 * ????????????
	 */
	@ViewInject(R.id.tvPrincipal)
	private TextView tvPrincipal;
	/**
	 * ????????????
	 */
	@ViewInject(R.id.tvOverdueAmt)
	private TextView tvOverdueAmt;
	/**
	 * ??????????????????
	 */
	@ViewInject(R.id.tvOverdueTab)
	private TextView tvOverdueTab;
	/**
	 * ????????????
	 */
	@ViewInject(R.id.tvInterest)
	private TextView tvInterest;
	/**
	 * ????????????
	 */
	@ViewInject(R.id.tvRefund)
	private TextView tvRefund;
	/**
	 * ????????????
	 */
	@ViewInject(R.id.etRefundSum)
	private EditText etRefundSum;
	/**
	 * ????????????
	 */
	@ViewInject(R.id.tvRefundCard)
	private TextView tvRefundCard;
	/**
	 * ????????????
	 */
	@ViewInject(R.id.tvAccBalance)
	private TextView tvAccBalance;
	
	@ViewInject(R.id.btnApply)
	private Button btnApply;
	// @ViewInject(R.id.tvRefundAll)
	// private TextView tvRefundAll;
	@ViewInject(R.id.swRefundAll)
	private SlideSwitch swRefundAll;
	@ViewInject(R.id.tvExplain)
	private TextView tvExplain;

	/**
	 * ??????????????????
	 */
	private String reAmount;
	private String accNo;
	/**
	 * ????????????
	 */
	private double interest;
	/**
	 * ????????????
	 */
	private double overdueAmt;
	/**
	 * ????????????
	 */
	private List<RepaymentBean> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		requestRefundData();
	}

	private void initData() {
		tvTitle.setText("????????????");
		if (null != getIntent().getBundleExtra("BUNDLE")) {
			accNo = getIntent().getBundleExtra("BUNDLE").getString("ACCT_NO");
		}
	}

	private void initListener() {
		etRefundSum.addTextChangedListener(new TextChangeWatcher(etRefundSum));
		swRefundAll.setSlideListener(new SlideSwitch.SlideListener() {

			@Override
			public void open() {
				KeyboardUtils.closeInput(RefundInAdvanceActivity.this, swRefundAll);
				if (!TextUtils.isEmpty(tvRemainToRefund.getText().toString())) {
					etRefundSum.setEnabled(false);
					etRefundSum.setText(
							getDoubleParse(Double.parseDouble(tvRefund.getText().toString().replaceAll(" ", "") + "")));
				}
			}

			@Override
			public void close() {
				KeyboardUtils.closeInput(RefundInAdvanceActivity.this, swRefundAll);
				etRefundSum.setEnabled(true);
				etRefundSum.setText("");
			}
		});
	}

	private boolean checkData() {
		double refund = 0.0;
		if (!TextUtils.isEmpty(etRefundSum.getText().toString())) {
			refund = Double.parseDouble(etRefundSum.getText().toString());
		}
		if (TextUtils.isEmpty(etRefundSum.getText().toString())) {
			Toast.makeText(this, R.string.inputRefundAmount, Toast.LENGTH_SHORT).show();
			return false;
		} else if (interest > refund) {
			Toast.makeText(this, "???????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	@OnClick({ R.id.btnApply/** , R.id.tvRefundAll */, R.id.tvRefundPlan})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnApply:
			KeyboardUtils.closeInput(this, btnApply);
			if (checkData()) {
				refundAffirm();
			}
			break;
		// case R.id.tvRefundAll:
		// KeyboardUtils.closeInput(this, tvRefundAll);
		// if (!TextUtils.isEmpty(tvRemainToRefund.getText().toString())) {
		// etRefundSum.setText(Double.parseDouble(tvRemainToRefund.getText().toString().replaceAll("
		// ", "")) + "");
		// }
		// break;
		case R.id.tvRefundPlan:
			if (list == null) {
				requestRefundPlan();
			} else {
				showRefundPlan();
			}
			break;
		}
	}
	
	private void showRefundPlan() {
		RefundPlanDialog dialog = new RefundPlanDialog(this);
		dialog.show(list);
	}
	
	/**
	 * ???????????????????????? 
	 */
	private void requestBocopForCardBal(String cardNo) {
//		cardNo = "6013821800007656785";
		Gson gson = new Gson();
		Map<String,String> map = new HashMap<String,String>();
		//??????id
		map.put("custNo", LoginUtil.getUserId(this));
		//??????
		map.put("cardNo", cardNo);
		final String strGson = gson.toJson(map);
		
		BocOpUtil bocOpUtil = new BocOpUtil(this);
		bocOpUtil.postOpboc(strGson, TransactionValue.DEBIT_BALANCE, new BocOpUtil.CallBackBoc() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(String responStr) {
				try {
					Map<String, Object> map = JsonUtils.getMapObj(responStr);
					Map<String, String> strMap = (Map<String, String>) map.get("cardServiceDTO");
					if (strMap != null) {
						String cardBal = strMap.get("balance").toString();
						if (cardBal != null) {
							Float fCardBal = Float.parseFloat(cardBal)/100;
							tvAccBalance.setText(fCardBal.toString());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onStart() {
				Log.i("tag", "????????????GSON?????????" + strGson);
			}
			
			@Override
			public void onFinish() {
				
			}
			
			@Override
			public void onFailure(String responStr) {
				
			}
		});
	}

	
	/**
	 * ????????????????????????
	 */
	private void requestRefundPlan() {
		try {
			CspXmlYfx017 cspXmlYfx017 = new CspXmlYfx017(accNo);
			String strXml = cspXmlYfx017.getCspXml();
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			byte[] byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
			cspUtil.setFLAG_YFX_CSP(true);
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {

				@Override
				public void onSuccess(String responStr) {
					RepaymentListXmlBean xmlBean = RepaymentListResp.readStringXml(responStr);
					if ("00".equals(xmlBean.getErrorcode())) {
						if (xmlBean.getRepaymentList() != null) {
							list = xmlBean.getRepaymentList();
							showRefundPlan();
						} 
					} else if ("50".equals(xmlBean.getErrorcode())) {
						DialogUtil.showWithToMain(RefundInAdvanceActivity.this, xmlBean.getErrormsg());
					} else {
						CspUtil.onFailure(RefundInAdvanceActivity.this, xmlBean.getErrormsg());
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

	/**
	 * ??????????????????
	 */
	private void requestRefundData() {
		try {
			CspXmlYfx014 cspXmlYfx014 = new CspXmlYfx014(getCacheBean().get(CacheBean.CUST_ID).toString(), accNo);
			String strXml = cspXmlYfx014.getCspXml();
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			byte[] byteMessage = {};
			byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
			cspUtil.setFLAG_YFX_CSP(true);
			cspUtil.setTxCode("001014");
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {

				@Override
				public void onSuccess(String responStr) {
					onGetDataSuccess(responStr);
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFailure(String responStr) {
					DialogUtil.showWithOneBtn(RefundInAdvanceActivity.this, responStr, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
						}
					});
					loadingView.setVisibility(View.VISIBLE);
					loadingView.setmOnRetryListener(new LoadingView.OnRetryListener() {

						@Override
						public void retry() {
							requestRefundData();
						}
					});
				}
			});

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ?????????????????????
	 */
	private void onGetDataSuccess(String responStr) {
		try {
			RefundDataResponse dataResponse = XStreamUtils.getFromXML(responStr, RefundDataResponse.class);
			ConstHead constHead = dataResponse.getConstHead();
			if (constHead != null) {
				if ("00".equals(constHead.getErrCode())) {
					loadingView.setVisibility(View.GONE);
					scrollView.setVisibility(View.VISIBLE);
					RefundData data = dataResponse.getRefundData();
					tvLoanAmount.setText(getDoubleParse(Double.parseDouble(data.getLoanAmount().replaceAll(" ", ""))));
					tvPrincipal.setText(getDoubleParse(Double.parseDouble(data.getRemainAmt().replaceAll(" ", ""))));
					tvInterest.setText(getDoubleParse(Double.parseDouble(data.getInterest().replaceAll(" ", ""))));
	
					double principal = Double.parseDouble(data.getRemainAmt().replaceAll(" ", ""));
					interest = Double.parseDouble(data.getInterest().replaceAll(" ", ""));
	
					reAmount = data.getRemainAmt().replaceAll(" ", "");
	
					if (!TextUtils.isEmpty(data.getOverdueAmt())) {
						overdueAmt = Double.parseDouble(data.getOverdueAmt().replaceAll(" ", ""));
					}
					tvOverdueAmt.setText(TextUtils.isEmpty(data.getOverdueAmt()) ? "0.00"
							: getDoubleParse(Double.parseDouble(data.getOverdueAmt().replaceAll(" ", ""))));
					tvRefund.setText(getDoubleParse(Double.parseDouble((principal + interest) + "")));
	
					tvRemainToRefund.setText(getDoubleParse(Double.parseDouble(principal + "")));
					tvRefundCard.setText(getHideCard(data.getRepayCard()));
					if (overdueAmt > 0) {
						tvTitle.setText("????????????");
						tvOverdueAmt.setTextColor(getResources().getColor(R.color.depth_red));
						tvOverdueTab.setTextColor(getResources().getColor(R.color.depth_red));
						tvExplain.setText("?????????????????????????????????????????????????????????");
						swRefundAll.setVisibility(View.INVISIBLE);
						etRefundSum.setText(getDoubleParse(overdueAmt));
						etRefundSum.setEnabled(false);
					} else {
						swRefundAll.setVisibility(View.VISIBLE);
						tvExplain.setText("?????????????????????????????????????????????");
						if (swRefundAll.getChecked()) {// ??????????????????
							etRefundSum.setText(getDoubleParse(Double.parseDouble((principal + interest) + "")));
						} else {
							etRefundSum.setText("");
						}
					}
					initListener();
					requestBocopForCardBal(data.getRepayCard());
				} else if ("50".equals(constHead.getErrCode())) {
					DialogUtil.showWithToMain(RefundInAdvanceActivity.this, constHead.getErrMsg());
				} else {
					DialogUtil.showWithOneBtn(this, constHead.getErrMsg(), new OnClickListener() {
	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
						}
					});
					loadingView.setVisibility(View.VISIBLE);
					scrollView.setVisibility(View.GONE);
					loadingView.setmOnRetryListener(new LoadingView.OnRetryListener() {
	
						@Override
						public void retry() {
							requestRefundData();
						}
					});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ????????????
	 */
	private void refundAffirm() {
		try {
			String refundSum = etRefundSum.getText().toString().replaceAll(",", "");
			CspXmlYfx011 cXmlYfx011 = new CspXmlYfx011(getCacheBean().get(CacheBean.CUST_ID).toString(), accNo,
					refundSum, interest + "");
			String strXml = cXmlYfx011.getCspXml();
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			byte[] byteMessage = {};
			byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
			cspUtil.setFLAG_YFX_CSP(true);
			cspUtil.setTxCode("001011");
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {

				@Override
				public void onSuccess(String responStr) {
					CommonResponse commonResponse = XStreamUtils.getFromXML(responStr, CommonResponse.class);
					ConstHead constHead = commonResponse.getConstHead();
					if (constHead != null) {
						if ("00".equals(constHead.getErrCode())) {
							// getActivityManager().finishAllWithoutActivity(LoanMainActivity.class);
							Intent intent = new Intent(RefundInAdvanceActivity.this, RefundSuccessActivity.class);
							intent.putExtra("ERR_CODE", constHead.getErrCode());
							intent.putExtra("ERR_MSG", constHead.getErrMsg());
							startActivity(intent);
						} else if ("50".equals(constHead.getErrCode())) {
							DialogUtil.showWithToMain(RefundInAdvanceActivity.this, constHead.getErrMsg());
						} else {
							CspUtil.onFailure(RefundInAdvanceActivity.this, constHead.getErrMsg());
						}
					}
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFailure(String responStr) {
					// TODO Auto-generated method stub
					CspUtil.onFailure(RefundInAdvanceActivity.this, responStr);

				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private String getHideCard(String cardNo) {
		if (cardNo.length() == 19) {
			cardNo = cardNo.substring(0, 4) + "***********" + cardNo.substring(15);
		} else {
			cardNo = cardNo.substring(0, 4) + "***********" + cardNo.substring(12);
		}

		return cardNo;
	}

	/**
	 * ??????????????????????????????
	 */
	private class TextChangeWatcher implements TextWatcher {

		private EditText editText;
		double remain = Double.parseDouble(reAmount.replaceAll(",", ""));

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
				// if (compare(str)) {
				// str = remain + "";
				// editText.setText(str);
				// editText.setSelection(str.length());
				// }
			}
		}

		private boolean compare(String s) {
			double refund = Double.parseDouble(s.replaceAll(",", ""));
			if (refund > remain) {
				return true;
			} else {
				return false;
			}
		}

	}

	@SuppressLint("DefaultLocale")
	private String getDoubleParse(double d) {
		return String.format("%.2f", d);
	}
}
