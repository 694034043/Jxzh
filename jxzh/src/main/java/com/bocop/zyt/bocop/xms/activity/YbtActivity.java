package com.bocop.zyt.bocop.xms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.boc.bocop.sdk.util.StringUtil;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.WebViewActivity;
import com.bocop.zyt.bocop.jxplatform.adapter.TrafficMainAdapter;
import com.bocop.zyt.bocop.jxplatform.bean.PerFunctionBean;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.constants.Constants;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_ybt)
public class YbtActivity extends BaseActivity implements OnClickListener{

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
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		title = getIntent().getStringExtra("title");
		title = StringUtil.isNullOrEmpty(title)?"银保通":title;
		tv_titleName.setText(title);
		findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_02_02_01).setOnClickListener(this);
		findViewById(R.id.ll_item_02_02_02).setOnClickListener(this);
		//initView();
		//initEvent();
	}
	
	/*@OnClick(R.id.iv_imageLeft)
	public void back(View v) {
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
	}*/
	
	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}*/

	private void initView() {

		initTraDates();

		traAdapter = new TrafficMainAdapter(YbtActivity.this, traDates,
				R.layout.item_quickpay);
		traListView.setAdapter(traAdapter);
	}

	/*
	 * 初始化列表
	 */
	//证券行情、财经资讯、证券开户、证券交易和中国红商城 
	private void initTraDates() {
		PerFunctionBean funBean1 = new PerFunctionBean("flight",
				R.drawable.icon_xms_picc, "中国人民人寿保险");
		PerFunctionBean funBean2 = new PerFunctionBean("flight",
				R.drawable.icon_xms_picc, "中国人民财产保险");
		PerFunctionBean funBean3 = new PerFunctionBean("exchange",
				R.drawable.icon_xms_epicc, "太平人寿保险");
//		PerFunctionBean funBean4 = new PerFunctionBean("rate",
//				R.drawable.zqt_icon_etrade, "证券交易");
//		PerFunctionBean funBean5 = new PerFunctionBean("rate",
//				R.drawable.zqt_icon_shop, "中国红商城");
		traDates.add(funBean1);
		traDates.add(funBean2);
		traDates.add(funBean3);
//		traDates.add(funBean4);
//		traDates.add(funBean5);
	}

	private void initEvent() {
		traListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if (!baseApplication.isNetStat()) {
					CustomProgressDialog
					.showBocNetworkSetDialog(YbtActivity.this);
				}
				else{
					if (arg2 == 0) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.UrlForMainYbt);
						bundle.putString("name", "中国人民人寿保险");
						Intent intent = new Intent(YbtActivity.this,
								WebViewActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					
					if (arg2 == 1) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.UrlForMainEpicc);
						bundle.putString("name", "中国人民财产保险");
						Intent intent = new Intent(YbtActivity.this,
								WebViewActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					if (arg2 == 2) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.UrlForMainTaiping);
						bundle.putString("name", "太平人寿保险");
						Intent intent = new Intent(YbtActivity.this,
								WebViewActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					
//					if (arg2 == 3) {
//						Bundle bundle = new Bundle();
//						bundle.putString("url", Constants.qztUrlForEtrade);
//						bundle.putString("name", "证券交易");
//						Intent intent = new Intent(YbtActivity.this,
//								WebActivity.class);
//						intent.putExtras(bundle);
//						startActivity(intent);
//					}
//					
//					if (arg2 ==4) {
//						Bundle bundle = new Bundle();
//						bundle.putString("url", Constants.xmsUrlForEshop);
//						bundle.putString("name", "中国红商城");
//						Intent intent = new Intent(YbtActivity.this,
//								WebActivity.class);
//						intent.putExtras(bundle);
//						startActivity(intent);
//					}
					
				}
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_item_01_01_01:
			Intent zybxywIntent = new Intent(YbtActivity.this,
					WebViewActivity.class);
			zybxywIntent.putExtra("url", Constants.UrlForMainZYBX_YWX);
			zybxywIntent.putExtra("name", "中银保险-意外险");
			startActivity(zybxywIntent);
			break;
		case R.id.ll_item_01_01_02:
			Intent zybxjcIntent = new Intent(YbtActivity.this,
					WebViewActivity.class);
			zybxjcIntent.putExtra("url", Constants.UrlForMainZYBX_JCX);
			zybxjcIntent.putExtra("name", "中银保险-家财险");
			startActivity(zybxjcIntent);
			break;
		case R.id.ll_item_02_01_01:
			Intent zgrsIntent = new Intent(YbtActivity.this,
					WebViewActivity.class);
			zgrsIntent.putExtra("url", Constants.UrlForMainYbt);
			zgrsIntent.putExtra("name", "中国人民人寿保险");
			startActivity(zgrsIntent);
			break;
		case R.id.ll_item_02_01_02:
			Intent zgrmIntent = new Intent(YbtActivity.this,
					WebViewActivity.class);
			zgrmIntent.putExtra("url", Constants.UrlForMainEpicc);
			zgrmIntent.putExtra("name", "中国人民财产保险");
			startActivity(zgrmIntent);
			break;
		case R.id.ll_item_02_02_01:
			Intent tprsIntent = new Intent(YbtActivity.this,
					WebViewActivity.class);
			tprsIntent.putExtra("url", Constants.UrlForMainTaiping);
			tprsIntent.putExtra("name", "太平人寿保险");
			startActivity(tprsIntent);
			break;
		case R.id.ll_item_02_02_02:
			break;

		default:
			break;
		}
	}
}
