package com.bocop.zyt.bocop.xms.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.xms.bean.SignContractInfo;
import com.bocop.zyt.bocop.xms.bean.SignResponse;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.xms.xml.CspXmlXms002;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.tools.DialogUtil;

import java.io.UnsupportedEncodingException;


@ContentView(R.layout.xms_activity_finance_sign_contract)
public class FinanceSignContractActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.iv_imageLeft)
	private ImageView iv_imageLeft;
	@ViewInject(R.id.tv_finagreement)
	TextView tvAgreement;
	@ViewInject(R.id.fincheckbox)
	CheckBox ckFin;
	
	@ViewInject(R.id.btn_FinApply)
	private Button btnApply;
	
	@ViewInject(R.id.tv_CusNo)
	private TextView tv_cusNo;
	@ViewInject(R.id.tv_CusName)
	private TextView tv_cusName;

	private String costType;
	private String typeCode;
	private String cusName;
	private String idNO;
	private String cusNO;
	private SignContractInfo contractInfo = new SignContractInfo();
	private static final int SIGN_SAVE_SUCCESS = 2;
	private static final int SIGN_FAILED = 3;
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SIGN_SAVE_SUCCESS:
				callMe(SignContractCompleteActivity.class);
				break;
			case SIGN_FAILED:
				String responStr = (String) msg.obj;
				CspUtil.onFailure(FinanceSignContractActivity.this, responStr);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String str = getIntent().getBundleExtra("BUNDLE").getString("TITLE");
		if (!TextUtils.isEmpty(str)) {
			tv_titleName.setText(str);
		}
		costType = getIntent().getBundleExtra("BUNDLE").getString("COST_TYPE");
		typeCode = getIntent().getBundleExtra("BUNDLE").getString("TYPE_CODE");
		cusName = getIntent().getBundleExtra("BUNDLE").getString("CUS_NAME");
		idNO = getIntent().getBundleExtra("BUNDLE").getString("ID_NO");
		cusNO = getIntent().getBundleExtra("BUNDLE").getString("CUS_ID");
		tv_cusNo.setText(cusNO);
		tv_cusName.setText(cusName);
	}
	
	@OnClick({R.id.btn_FinApply,R.id.tv_finagreement})
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_FinApply:
//			if (!ckFin.isChecked()) {
//				Toast.makeText(this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
//			}else{
				contractInfo.setUserCode(cusNO);
				contractInfo.setCostType(typeCode);
				contractInfo.setUserId(LoginUtil.getUserId(this));
				contractInfo.setSysId("15");//???????????? ????????????
				contractInfo.setName(cusName);
				contractInfo.setArea(idNO);
				contractInfo.setUnit("0791");
				contractInfo.setTrnCode("0791");
				contractInfo.setAreaId("079100");
				contractInfo.setSubscriberno("");
				contractInfo.setPinpaiN("");
				contractInfo.setServicetype("");
				contractInfo.setDevTyp("");
				contractInfo.setOrderDate("");
				contractInfo.setServId("");
				requestSignSave(contractInfo);
//			} 
			break;
			
		case R.id.tv_finagreement:
			Intent intent = new Intent(FinanceSignContractActivity.this, FinanceAgrementActivity.class);
			startActivity(intent);
			break;
		}
		
	}
	
	/**
	 * ??????????????????
	 */
	private void requestSignSave(SignContractInfo contractInfo) {
		try {

			// ??????CSP XML??????
			CspXmlXms002 cspXmlXms002 = new CspXmlXms002(contractInfo.getCostType(), contractInfo.getUserId(),
					contractInfo.getName(), "", contractInfo.getArea(), contractInfo.getUnit(),
					contractInfo.getUserCode(), "09", contractInfo.getSysId(),
					contractInfo.getTrnCode(), contractInfo.getAreaId(), "00010002");
			cspXmlXms002.setServicetype(contractInfo.getServicetype());
			cspXmlXms002.setSubscriberno(contractInfo.getSubscriberno());
			cspXmlXms002.setPinpaiN(contractInfo.getPinpaiN());
			cspXmlXms002.setDevTyp(contractInfo.getDevTyp());
			cspXmlXms002.setServId(contractInfo.getServId());
			Log.i("tag", "setServId");
			String strXml = cspXmlXms002.getCspXml();
			Log.i("tag", "getCspXml");
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			Log.i("tag", "Mcis");
			final byte[] byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
//			cspUtil.setTest(true);
			Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
			cspUtil.postCspLogin(byteMessage, new CspUtil.CallBack() {
				@Override
				public void onSuccess(String responStr) {
					SignResponse response = XStreamUtils.getFromXML(responStr, SignResponse.class);
					if ("50".equals(response.getConstHead().getErrCode())) {
						DialogUtil.showWithOneBtn(FinanceSignContractActivity.this, "???????????????",
								new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								});
					} else {
						mHandler.sendEmptyMessage(SIGN_SAVE_SUCCESS);
					}

				}

				@Override
				public void onFinish() {

				}

				@Override
				public void onFailure(String responStr) {
					Message msg = new Message();
					msg.what = SIGN_FAILED;
					msg.obj = responStr;
					mHandler.sendMessage(msg);
				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
