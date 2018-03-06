package com.bocop.zyt.bocop.jxplatform.trafficassistant;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.adapter.MyPeccancyAdapter;
import com.bocop.zyt.bocop.jxplatform.fragment.PayHisFragment;
import com.bocop.zyt.bocop.jxplatform.fragment.PayWaitingFragment;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.mdx.framework.widget.viewpagerindicator.TabPageIndicator;

/** 
 * @author luoyang  
 * @version 创建时间：2015-6-30 下午2:49:49 
 * 类说明 
 */
@ContentView(R.layout.activity_trafficmypeccancy)
public class MyPeccancyActivity extends BaseActivity {

//	@ViewInject(R.id.vp_mypeccancy)
//	ViewPager vpMyPeccancy;
//	@ViewInject(R.id.tpi_mypeccancy)
//	TabPageIndicator tpiMyPenccncy;
	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	
	private ViewPager mViewPager;
	private TabPageIndicator mTabPageIndicator;
	private MyPeccancyAdapter mAdapter;
	private Class fragmentArray[] = { PayWaitingFragment.class,PayHisFragment.class};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_trafficmypeccancy);
		initView();
	}

	private void initView()
	{
		tv_titleName.setText("我的违法");
		mViewPager = (ViewPager) findViewById(R.id.vp_mypeccancy);
		mTabPageIndicator = (TabPageIndicator) findViewById(R.id.tpi_mypeccancy);
		mAdapter = new MyPeccancyAdapter(this, fragmentArray);
		mViewPager.setAdapter(mAdapter);
		
		mTabPageIndicator.setViewPager(mViewPager, 0);
	}
	
}
