package com.bocop.zyt.bocop.jxplatform.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.adapter.MyPayWaitingAdapter;
import com.bocop.zyt.bocop.jxplatform.bean.APJJ08ListXmlBean;
import com.bocop.zyt.bocop.jxplatform.bean.CarPeccancyBean;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.jxplatform.xml.CspRecForAPJJ10;
import com.bocop.zyt.bocop.jxplatform.xml.CspXmlAPJJ10;
import com.bocop.zyt.jx.base.BaseFragment;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PayWaitingFragment extends BaseFragment
{
	@ViewInject(R.id.lv_my_peccancy)
	ListView lvMyPeccancy;
	//没有违章，不显示
	@ViewInject(R.id.ll_paywaiting_nopeccancy)
	View llPaywaitingNopeccancy;
	//有违章，显示LISTVIEW
	@ViewInject(R.id.ll_paywaiting_mypeccancy)
	View llPaywaitingMypeccancy;
	
	private static final int INITIAL_DELAY_MILLIS = 500;
    private MyPayWaitingAdapter myPayWaitingAdapter;
    AlphaInAnimationAdapter alphaInAnimationAdapter;
    
//    List<MyPeccancyBean> myWaitingPeccancyDatas = new ArrayList<MyPeccancyBean>();
    List<CarPeccancyBean> myWaitingPeccancyDatas = new ArrayList<CarPeccancyBean>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		view = initView(R.layout.fragment_paywaitting);
		return view;
	}
	
	@Override
	protected void initData() {
		super.initData();
		requestCspForPayWaiting();
	}

	@Override
	protected void initView() {
		super.initView();
	}

	private void requestCspForPayWaiting() {
		try {
			//生成CSP XML报文
			CspXmlAPJJ10 cspXmlForCarList = new CspXmlAPJJ10(LoginUtil.getUserId(getActivity()));
			String strXml = cspXmlForCarList.getCspXml();
			//生成MCIS报文
			Mcis mcis = new Mcis(strXml, TransactionValue.APJJ10);
			final byte[] byteMessage = mcis.getMcis();
			//发送报文
			CspUtil cspUtil = new CspUtil(getActivity());
			Log.i("tag", "发送报文： " + new String(byteMessage, "GBK"));
			cspUtil.postCspLogin(byteMessage, new CspUtil.CallBack() {
				@Override
				public void onSuccess(String responStr) {
					APJJ08ListXmlBean listBean = CspRecForAPJJ10.readStringXml(responStr);
					if(listBean.getErrorcode().equals("00") && listBean.getCarPeccancyListBean().size() > 0){
						llPaywaitingNopeccancy.setVisibility(View.INVISIBLE);
						llPaywaitingMypeccancy.setVisibility(View.VISIBLE);
						myWaitingPeccancyDatas = listBean.getCarPeccancyListBean();
						Log.i("tag", String.valueOf(myWaitingPeccancyDatas.size()));
						setListAdapter();
					}
					else{
						llPaywaitingNopeccancy.setVisibility(View.VISIBLE);
						llPaywaitingMypeccancy.setVisibility(View.INVISIBLE);
					}
				}
				
				@Override
				public void onFinish() {
					
				}
				
				@Override
				public void onFailure(String responStr) {
					CspUtil.onFailure(getActivity(), responStr);
				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void setListAdapter(){
		myPayWaitingAdapter = new MyPayWaitingAdapter(baseActivity, myWaitingPeccancyDatas);
        alphaInAnimationAdapter = new AlphaInAnimationAdapter(myPayWaitingAdapter);
        alphaInAnimationAdapter.setAbsListView(lvMyPeccancy);
        Log.d("tag", "setAbsListView");
        assert alphaInAnimationAdapter.getViewAnimator() != null;
        alphaInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);
        Log.d("tag", "setInitialDelayMillis");
        lvMyPeccancy.setAdapter(alphaInAnimationAdapter);
	}
}
