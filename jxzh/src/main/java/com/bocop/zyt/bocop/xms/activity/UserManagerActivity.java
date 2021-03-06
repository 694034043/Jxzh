package com.bocop.zyt.bocop.xms.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.xms.bean.SignContractInfo;
import com.bocop.zyt.bocop.xms.bean.UserList;
import com.bocop.zyt.bocop.xms.bean.UserResponse;
import com.bocop.zyt.bocop.xms.tools.ViewHolder;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.xms.xml.CspXmlXms003;
import com.bocop.zyt.bocop.xms.xml.CspXmlXms004;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseAdapter;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.tools.DialogUtil;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenu;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenuCreator;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenuItem;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenuListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.xms_activity_user_manager)
public class UserManagerActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tvTitleName;
	@ViewInject(R.id.tv_Item)
	private TextView tvItem;
	@ViewInject(R.id.lv_UserNo)
	private SwipeMenuListView lvUserNo;
	@ViewInject(R.id.tvError)
	private TextView tvError;
	
	@ViewInject(R.id.ll_tishi)
	private LinearLayout llTishi;
	

	private List<SignContractInfo> signList = new ArrayList<SignContractInfo>();
	private BaseAdapter<SignContractInfo> adapter;

	private String costType;//"??????"
	private String typeCode;//01

	private static final int USER_CANCEL_SUCCESS = 0;
	private static final int USER_FAILED = 1;
	private static final int USER_LIST_SUCCESS = 2;
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case USER_CANCEL_SUCCESS:
				signList.remove(msg.arg1);
				if (signList.size() == 0) {
					lvUserNo.setVisibility(View.GONE);
					llTishi.setVisibility(View.GONE);
					tvError.setVisibility(View.VISIBLE);
				} else {
					lvUserNo.setVisibility(View.VISIBLE);
					llTishi.setVisibility(View.VISIBLE);
					tvError.setVisibility(View.GONE);
					adapter.notifyDataSetChanged();
				}
				break;
			case USER_FAILED:
				String responStr = (String) msg.obj;
				CspUtil.onFailure(UserManagerActivity.this, responStr);
				break;

			case USER_LIST_SUCCESS:
				String content = (String) msg.obj;
				if (null != content) {
					UserResponse userResponse = XStreamUtils.getFromXML(content, UserResponse.class);
					if (userResponse != null) {
						if (!"01".equals(userResponse.getConstHead().getErrCode())) {
							lvUserNo.setVisibility(View.VISIBLE);
							tvError.setVisibility(View.GONE);
							UserList userList = userResponse.getUserList();
							if (userList != null && userList.getList() != null) {
								signList.clear();
								signList.addAll(userList.getList());
								adapter.notifyDataSetChanged();
							}
						} else {
							lvUserNo.setVisibility(View.GONE);
							tvError.setVisibility(View.VISIBLE);
						}
					}
				}
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		costType = getIntent().getBundleExtra("BUNDLE").getString("COST_TYPE");
		typeCode = getIntent().getBundleExtra("BUNDLE").getString("TYPE_CODE");
		tvTitleName.setText("???????????????");
		tvItem.setVisibility(View.VISIBLE);
		Log.i("tag", costType);
		if("07".equals(typeCode)){
			tvItem.setVisibility(View.GONE);
			tvItem.setText("");
		}else{
			tvItem.setVisibility(View.VISIBLE);
			tvItem.setText("??????");
		}
		initDate();
		initListener();
	}

	@Override
	protected void onResume() {
		super.onResume();
		requestCspForUser(typeCode);
	}

	@OnClick({ R.id.iv_imageLeft, R.id.tv_Item })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_imageLeft:
			finish();
			break;

		case R.id.tv_Item:
			Intent intent = new Intent(UserManagerActivity.this, SignContractActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("TITLE", "??????");
			bundle.putString("COST_TYPE", costType);
			bundle.putString("TYPE_CODE", typeCode);
			intent.putExtra("BUNDLE", bundle);
			startActivity(intent);
			break;
		}
	}

	private void initDate() {

		SwipeMenuCreator menuCreator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
				SwipeMenuItem editItem=new SwipeMenuItem(getApplicationContext());
				deleteItem.setBackground(R.color.bg_red);
				deleteItem.setWidth(dp2px(60));
				deleteItem.setTitle("??????");
				deleteItem.setTitleSize(16);
				deleteItem.setTitleColor(Color.WHITE);
				
				if(!"07".equals(typeCode)){
					editItem.setBackground(R.color.grayLight);
					editItem.setTitle("??????");
					editItem.setTitleSize(16);
					editItem.setTitleColor(Color.WHITE);
					editItem.setWidth(dp2px(60));
					menu.addMenuItem(editItem);
				}
				menu.addMenuItem(deleteItem);
			}
		};

		lvUserNo.setMenuCreator(menuCreator);

		lvUserNo.setAdapter(adapter = new BaseAdapter<SignContractInfo>(this, signList, R.layout.xms_item_user_manager) {

			@Override
			public void viewHandler(int position, SignContractInfo t, View convertView) {
				TextView tvUserCode = ViewHolder.get(convertView, R.id.tv_UserCode);
				TextView tvUserName = ViewHolder.get(convertView, R.id.tv_UserName);
				TextView tvTextType = ViewHolder.get(convertView, R.id.tvTextType);
				if (null != t) {
					if (TextUtils.isEmpty(t.getSubscriberno())) {
						tvTextType.setText("????????????: ");
						tvUserCode.setText(t.getUserCode());
					} else {
						tvTextType.setText("????????????: ");
						tvUserCode.setText(t.getSubscriberno());
					}
					tvUserName.setText(t.getName());
				}
			}
		});

		String content = getIntent().getBundleExtra("BUNDLE").getString("USER_LIST");
		if (null != content) {
			UserResponse userResponse = XStreamUtils.getFromXML(content, UserResponse.class);
			if (userResponse != null) {
				UserList userList = userResponse.getUserList();
				if (userList != null && userList.getList() != null) {
					signList.clear();
					signList.addAll(userList.getList());
					System.out.println("-------->>"+signList);
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

	private void initListener() {
		lvUserNo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});

		lvUserNo.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {

				switch (index) {
				case 1:// ??????
					deleteSign(position);
					break;
				case 0://??????
					if("07".equals(typeCode)){
						deleteSign(position);
					}else{
						SignContractInfo userInfo=signList.get(position);
						getCostType(userInfo.getCostType());
						Intent intent=new Intent(UserManagerActivity.this,SignContractActivity.class);
						Bundle bundle=new Bundle();
						bundle.putString("TITLE", "??????");
						bundle.putString("COST_TYPE", costType);
						bundle.putString("TYPE_CODE", userInfo.getCostType());
						bundle.putSerializable("USER_INFO", userInfo);
						intent.putExtra("BUNDLE", bundle);
						startActivity(intent);
					}
					break;
				}
				return false;
			}
		});
	}
	
	/**
	 * ????????????
	 * @param position
	 */
	private void deleteSign(final int position){
		DialogUtil.showWithTwoBtn(UserManagerActivity.this, "?????????????????????", "??????", "??????",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						requestCspForCancel(position);
					}
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
	}
	
	private void getCostType(String typecode){
		if("01".equals(typecode)){
			costType="??????";
		}else if("02".equals(typecode)){
			costType="??????";
		}else if("03".equals(typecode)){
			costType="?????????";
		}else if("04".equals(typecode)){
			costType="????????????";
		}else if("05".equals(typecode)){
			costType="????????????";
		}else if("07".equals(typecode)){
			costType="????????????";
		}
	}

	/**
	 * ????????????????????????
	 */
	private void requestCspForCancel(final int postion) {
		try {
			SignContractInfo signContractInfo = signList.get(postion);
			// ??????CSP XML??????
			CspXmlXms003 cspXmlXms003 = new CspXmlXms003(LoginUtil.getUserId(this), signContractInfo.getUserCode(),
					signContractInfo.getSysId(), signContractInfo.getAreaId(),signContractInfo.getCostType());
			String strXml = cspXmlXms003.getCspXml();
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			final byte[] byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
//			cspUtil.setTest(true);
			Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {
				@Override
				public void onSuccess(String responStr) {
					Message msg = new Message();
					msg.what = USER_CANCEL_SUCCESS;
					msg.arg1 = postion;
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

	/**
	 * ??????????????????
	 */
	private void requestCspForUser(String typeCode) {
		try {
			// ??????CSP XML??????
			// ??????01?????????02????????????03???????????????04???????????????05
			CspXmlXms004 cspXmlXms004 = new CspXmlXms004(LoginUtil.getUserId(this), typeCode);
			String strXml = cspXmlXms004.getCspXml();
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			final byte[] byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
//			cspUtil.setTest(true);
			Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {
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

}
