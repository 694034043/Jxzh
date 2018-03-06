package com.bocop.zyt.bocop.yfx.activity.loanprodetail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.xms.bean.ConstHead;
import com.bocop.zyt.bocop.xms.utils.KeyboardUtils;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.yfx.bean.CommonResponse;
import com.bocop.zyt.bocop.yfx.bean.LinkManInfo;
import com.bocop.zyt.bocop.yfx.fragment.LoanProDetailFragment;
import com.bocop.zyt.bocop.yfx.utils.CheckoutUtil;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.yfx.xml.CspXmlYfx004;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人信息
 *
 * @author rd
 */
@ContentView(R.layout.yfx_activity_personal_info)
public class PersonalInfoActivity extends BaseActivity {
	@ViewInject(R.id.tv_titleName)
	private TextView tvTitle;
	@ViewInject(R.id.llAddDetail)
	private LinearLayout llAddDetail;
	@ViewInject(R.id.btnSave)
	private Button btnSave;
	@ViewInject(R.id.etFamilyName)
	private EditText etFamilyName;// 家人姓名
	@ViewInject(R.id.etFamilyPhoneNum)
	private EditText etFamilyPhoneNum;// 家人电话
	@ViewInject(R.id.etColleagueName)
	private EditText etColleagueName;// 同事姓名
	@ViewInject(R.id.etColleaguePhoneNum)
	private EditText etColleaguePhoneNum;// 同事电话
	@ViewInject(R.id.etFreindName)
	private EditText etFreindName;// 朋友姓名
	@ViewInject(R.id.etFreindPhoneNum)
	private EditText etFreindPhoneNum;// 朋友电话
	@ViewInject(R.id.llLinkDetail)
	private LinearLayout llLinkDetail;// 联系人信息
	@ViewInject(R.id.etCity)
	private EditText etCity;
	@ViewInject(R.id.etArea)
	private EditText etArea;
	@ViewInject(R.id.etStreet)
	private EditText etStreet;
	// @ViewInject(R.id.etAddress)
	// private EditText etAddress;// 详细地址
	private List<LinkManInfo> linkManInfos;// 联系人列表
	private LinkManInfo familyInfo;// 家人信息
	private LinkManInfo friendiInfo;// 朋友信息
	private LinkManInfo colleagueiInfo;// 同事信息
	private String FAMILY = "1";// 1 我的家人
	private String FRIEND = "2";// 2 我的朋友
	private String COLLEAGUE = "3";// 3我的同事

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tvTitle.setText(getString(R.string.personalInfo));
		linkManInfos = new ArrayList<>();

	}

	@OnClick({ R.id.llMyAdd, R.id.llMyLinkman, R.id.btnSave, R.id.iv_imageLeft })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.llMyAdd:
			if (!llAddDetail.isShown()) {
				llAddDetail.setVisibility(View.VISIBLE);
			} else {
				llAddDetail.setVisibility(View.GONE);
			}
			break;

		case R.id.llMyLinkman:
			if (!llLinkDetail.isShown()) {
				llLinkDetail.setVisibility(View.VISIBLE);
			} else {
				llLinkDetail.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.btnSave:
			KeyboardUtils.closeInput(this, btnSave);
			String address = etCity.getText().toString() + etArea.getText().toString() + etStreet.getText().toString();
			String familyName = etFamilyName.getText().toString();
			String familyPhoneNum = etFamilyPhoneNum.getText().toString();
			familyInfo = new LinkManInfo(FAMILY, familyName, familyPhoneNum);

			String colleagueName = etColleagueName.getText().toString();
			String colleaguePhoneNum = etColleaguePhoneNum.getText().toString();
			colleagueiInfo = new LinkManInfo(COLLEAGUE, colleagueName, colleaguePhoneNum);

			String freindName = etFreindName.getText().toString();
			String freindPhoneNum = etFreindPhoneNum.getText().toString();
			friendiInfo = new LinkManInfo(FRIEND, freindName, freindPhoneNum);

			linkManInfos.clear();
			linkManInfos.add(familyInfo);
			linkManInfos.add(friendiInfo);
			linkManInfos.add(colleagueiInfo);
			if (CheckoutUtil.isEmpty(address, familyName, familyPhoneNum, colleagueName, colleaguePhoneNum, freindName,
					freindPhoneNum)) {
				ToastUtils.show(this, getString(R.string.emptyInput), Toast.LENGTH_SHORT);
			} else if (!CheckoutUtil.isMobileNo(familyPhoneNum)) {
				ToastUtils.show(this, getString(R.string.errorFamilyNum), Toast.LENGTH_SHORT);
			} else if (!CheckoutUtil.isMobileNo(freindPhoneNum)) {
				ToastUtils.show(this, getString(R.string.errorFriendNum), Toast.LENGTH_SHORT);
			} else if (!CheckoutUtil.isMobileNo(colleaguePhoneNum)) {
				ToastUtils.show(this, getString(R.string.errorColleagueNum), Toast.LENGTH_SHORT);
			} else {

				requestAddPersonalInfo(address, linkManInfos);// 请求补录个人信息

			}

			break;
		case R.id.iv_imageLeft:
			finish();
			break;
		}
	}

	/**
	 * 请求补录个人信息
	 *
	 * @param linkManInfos2
	 * @param address
	 */
	private void requestAddPersonalInfo(String address, List<LinkManInfo> linkManInfos2) {
		try {
			CspXmlYfx004 cspXmlYfx004 = new CspXmlYfx004(LoginUtil.getUserId(this), address, linkManInfos2);
			String strXml = cspXmlYfx004.getCspXml();
			// 生成MCIS报文
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			final byte[] byteMessage = mcis.getMcis();
			// 发送报文
			CspUtil cspUtil = new CspUtil(this);
			cspUtil.setFLAG_YFX_CSP(true);
			Log.i("tag", "发送报文： " + new String(byteMessage, "GBK"));
			cspUtil.setTxCode("001004");
			cspUtil.postCspLogin(byteMessage, new CspUtil.CallBack() {

				@Override
				public void onSuccess(String responStr) {
					CommonResponse commonResponse = XStreamUtils.getFromXML(responStr, CommonResponse.class);
					ConstHead constHead = commonResponse.getConstHead();
					if (null != constHead && "00".equals(constHead.getErrCode())) {
						Toast.makeText(PersonalInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
						LoanProDetailFragment.STATUS_CHANGE_FLAG = true;
						callMe(PreviewAuthentInfoActivity.class);
					} else {
						CspUtil.onFailure(PersonalInfoActivity.this, responStr);
					}

				}

				@Override
				public void onFailure(String responStr) {
					CspUtil.onFailure(PersonalInfoActivity.this, responStr);

				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub

				}

			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
