package com.bocop.zyt.bocop.zyt.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.model.Constant;

import java.util.ArrayList;

public class CiViewPagerActivity extends BaseAct {
	
	private static final String SELECT_CURRENT_ITEM = "select_current_item";
	private ViewPager viewPager;
	private ArrayList<BaseFragment> fragmentList = new ArrayList<>();
	MyFragmentPagerAdapter pagerAdapter;
	private int selCurrentItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_ci_view_pager);
		if(Constant.fragmentList.size()>0){
			fragmentList.clear();
			fragmentList.addAll(Constant.fragmentList);
		}/*else{
			fragmentList.add(new CiCultureFragment());
			fragmentList.add(new CiMallFragment());
			fragmentList.add(new CiFinanceFragment());
		}*/
		selCurrentItem = getIntent().getIntExtra(SELECT_CURRENT_ITEM, 0);
		if(selCurrentItem>=fragmentList.size()||selCurrentItem<0){
			selCurrentItem = 0;
		}
		init_widget();
	}
	
	public static void startAct(Context context,int selectCurrentItem) {
		Intent intent = new Intent(context, CiViewPagerActivity.class);
		intent.putExtra(SELECT_CURRENT_ITEM, selectCurrentItem);
		context.startActivity(intent);
	}
	
	public static void startAct(Context context,int selectCurrentItem,ArrayList<BaseFragment> fragmentList) {
		Intent intent = new Intent(context, CiViewPagerActivity.class);
		intent.putExtra(SELECT_CURRENT_ITEM, selectCurrentItem);
		context.startActivity(intent);
		Constant.fragmentList.clear();
		Constant.fragmentList.addAll(fragmentList);
	}

	@Override
	public void init_widget() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		//给ViewPager设置适配器  
		viewPager.setAdapter(pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));  
		viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
		viewPager.setCurrentItem(selCurrentItem);//设置当前显示标签页为第一页  
	}
	
	public class MyFragmentPagerAdapter extends FragmentPagerAdapter{  
	    ArrayList<BaseFragment> list;
	    public MyFragmentPagerAdapter(FragmentManager fm,ArrayList<BaseFragment> list) {  
	        super(fm);  
	        this.list = list;  
	    }  
	      
	    @Override  
	    public int getCount() {  
	        return list.size();  
	    }  
	      
	    @Override  
	    public Fragment getItem(int arg0) {  
	        return list.get(arg0);  
	    }
	}
	
	@Override
	public void onBackPressed() {
		BaseFragment selectFragment = (BaseFragment) pagerAdapter.getItem(viewPager.getCurrentItem());
		int floor = selectFragment.getFloor();
		if(floor>=2){
			selectFragment.updateView();
		}else{
			super.onBackPressed();
		}
	}

}
