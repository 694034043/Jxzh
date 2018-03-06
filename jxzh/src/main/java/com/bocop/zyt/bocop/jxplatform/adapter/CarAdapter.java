package com.bocop.zyt.bocop.jxplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.bean.CarListBean;
import com.bocop.zyt.bocop.jxplatform.trafficassistant.CarInfoActivity;
import com.bocop.zyt.bocop.jxplatform.trafficassistant.CarPeccancyActivity;
import com.bocop.zyt.jx.tools.CommonAdapter;
import com.bocop.zyt.jx.tools.ViewHolder;

import java.util.List;

/** 
 * @author luoyang  
 * @version 创建时间：2015-6-24 下午2:38:38 
 * 类说明 
 */

public class CarAdapter extends CommonAdapter<CarListBean> {

	Context context;
//	List<CarListBean> mDates;
//	int mitemLayoutId;
	public CarAdapter(Context context, List<CarListBean> datas,
			int itemLayoutId) {
		super(context, datas, itemLayoutId);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void convert(ViewHolder helper, CarListBean item) {
		// TODO Auto-generated method stub
		helper.setButtonText(R.id.bt_licenseNumber, item.getTbLicenseNumber());
//		helper.setImageResource(R.id.iv_car, item.getImageCarRes());
		helper.setButtonText(R.id.bt_car_peccancy, item.getBtCarPeccancy());
		
		//设置按钮的监听器,查询车辆信息
		Button btLn = helper.getView(R.id.bt_licenseNumber);
		final String licenseNumber = btLn.getText().toString();
		btLn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, CarInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("licenseNumber", licenseNumber);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		
		//设置按钮的监听器,查询车辆违章信息
		helper.getView(R.id.bt_car_peccancy).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, CarPeccancyActivity.class);
				context.startActivity(intent);
			}
		});
	}

}
