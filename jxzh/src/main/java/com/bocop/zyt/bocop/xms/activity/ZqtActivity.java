package com.bocop.zyt.bocop.xms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebViewActivity;
import com.bocop.zyt.bocop.jxplatform.adapter.TrafficMainAdapter;
import com.bocop.zyt.bocop.jxplatform.bean.PerFunctionBean;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.constants.Constants;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_zqt)
public class ZqtActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
//	@ViewInject(R.id.iv_imageLeft)
//	private BackButton backBtn;
	@ViewInject(R.id.lvadvice)
	private ListView traListView;


	private BaseApplication baseApplication = BaseApplication.getInstance();
	List<PerFunctionBean> traDates = new ArrayList<PerFunctionBean>();
	List<PerFunctionBean> messageDates = new ArrayList<PerFunctionBean>();
	TrafficMainAdapter traAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tv_titleName.setText("证券通");
		initView();
		initEvent();
	}
	
	@OnClick(R.id.iv_imageLeft)
	public void back(View v) {
			finish();
	}

	private void initView() {

		initTraDates();

		traAdapter = new TrafficMainAdapter(ZqtActivity.this, traDates,
				R.layout.item_quickpay);
		traListView.setAdapter(traAdapter);
	}

	/*
	 * 初始化列表
	 */
	//证券行情、财经资讯、证券开户、证券交易和中国红商城 
	private void initTraDates() {
		PerFunctionBean funBean1 = new PerFunctionBean("flight",
				R.drawable.icon_xms_market, "证券行情");
		PerFunctionBean funBean2 = new PerFunctionBean("flight",
				R.drawable.icon_xms_consult, "财经资讯");
		PerFunctionBean funBean3 = new PerFunctionBean("exchange",
				R.drawable.zqt_icon_open, "证券开户");
		PerFunctionBean funBean4 = new PerFunctionBean("rate",
				R.drawable.zqt_icon_etrade, "证券交易");
		PerFunctionBean funBean5 = new PerFunctionBean("rate",
				R.drawable.zqt_icon_shop, "中国红商城");
		traDates.add(funBean1);
		traDates.add(funBean2);
		traDates.add(funBean3);
		traDates.add(funBean4);
		traDates.add(funBean5);
	}

	private void initEvent() {
		traListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if (!baseApplication.isNetStat()) {
					CustomProgressDialog
					.showBocNetworkSetDialog(ZqtActivity.this);
				}
				else{
					if (arg2 == 0) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForMarket);
						bundle.putString("name", "证券行情");
						Intent intent = new Intent(ZqtActivity.this,
								WebViewActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					
					if (arg2 == 1) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForConsult);
						bundle.putString("name", "财经咨询");
						Intent intent = new Intent(ZqtActivity.this,
								WebViewActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					if (arg2 == 2) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.qztUrlForOpen);
						bundle.putString("name", "证券开户");
						Intent intent = new Intent(ZqtActivity.this,
								WebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					
					if (arg2 == 3) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.qztUrlForEtrade);
						bundle.putString("name", "证券交易");
						Intent intent = new Intent(ZqtActivity.this,
								WebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					
					if (arg2 ==4) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.xmsUrlForEshop);
						bundle.putString("name", "中国红商城");
						Intent intent = new Intent(ZqtActivity.this,
								WebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					
				}
				
			}
		});
	}
}
