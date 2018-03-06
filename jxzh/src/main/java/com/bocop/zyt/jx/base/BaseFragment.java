package com.bocop.zyt.jx.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.baseUtil.view.ViewUtils;
import com.bocop.zyt.jx.tools.LogUtils;

/**
 * Created by user on 14-9-11.
 */
public class BaseFragment extends Fragment{
	protected View view;// 整体View
	protected boolean isResume = false;// 是否恢复缓存View
	protected BaseApplication appBean;
	protected CacheBean cacheBean;
//	protected UserInfo userInfo;
//	protected LoginUserInfo userInfo;


	protected BaseActivity baseActivity;
	public static Handler _handler = new Handler();

	//是否访问
	protected boolean isVisite = false;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof BaseActivity){
			baseActivity = (BaseActivity) activity;
			appBean = baseActivity.getBaseApp();
			cacheBean = appBean.getCacheBean();
		}
//		userInfo = appBean.getUserInfo();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewUtils.inject(this, view);
		if(!isVisite){
			initData();
			initView();
		}
	}

	protected void initData() {

	}

	protected void initView() {

	}

	public BaseActivity getBaseActivity() {
		return baseActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected View initView(int id){
		if(view ==null){
			view = View.inflate(baseActivity, id, null);
		}else{
//    		((ViewGroup) (view.getRootView())).removeAllViews();
			isVisite =true;
		}
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	public CacheBean getCacheBean() {
		return cacheBean;
	}

	public void onSelected() {
		LogUtils.d("----------- BaseFragment onSelected -----------");
	}
}
