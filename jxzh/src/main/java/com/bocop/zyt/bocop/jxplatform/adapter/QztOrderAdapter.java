package com.bocop.zyt.bocop.jxplatform.adapter;

import android.content.Context;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.bean.QztOrderBean;
import com.bocop.zyt.jx.tools.CommonAdapter;
import com.bocop.zyt.jx.tools.ViewHolder;

import java.util.List;


/**
 * @author luoyang
 * @version 创建时间：2016-4-27 上午9:47:52 类说明
 */

public class QztOrderAdapter extends CommonAdapter<QztOrderBean> {

	public QztOrderAdapter(Context context, List<QztOrderBean> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}
	
	@Override
	public void convert(ViewHolder helper, QztOrderBean item) {
		String strState;
		if(item.getOrder_status().equals("3")){
			strState = "已缴费";
		}else{
			strState = "未缴费";
		}
		helper.setImageResource(R.id.icon, R.drawable.icon_card);
		helper.setText(R.id.qzt_order_num, item.getOrder_num());
		helper.setText(R.id.qzt_order_state, strState + ":" + item.getOrder_amt() + "元");
//		if(item.getOrder_num().length() > 10){
//			String startData = item.getOrder_num().substring(0, 8);
//			Log.i("tag22", startData);
//			SimpleDateFormat format = new SimpleDateFormat("yyyyMMDD");
//			String nowData = format.format(new Date(System.currentTimeMillis()));
//			public Date = System.currentTimeMillis();
//		}
//		helper.getConvertView().set
	}
	
}
