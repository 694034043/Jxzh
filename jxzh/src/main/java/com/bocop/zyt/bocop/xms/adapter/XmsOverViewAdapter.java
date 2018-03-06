/**
 * 
 */
package com.bocop.zyt.bocop.xms.adapter;

import android.content.Context;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.xms.bean.OverViewType;
import com.bocop.zyt.jx.tools.CommonAdapter;
import com.bocop.zyt.jx.tools.ViewHolder;

import java.util.List;


/** 
 * @author luoyang  E-mail: luoyang8714@163.com
 * @version 创建时间：2016-5-27 上午11:38:58 
 * 类说明 
 */
/**
 * @author zhongye
 *
 */
public class XmsOverViewAdapter extends CommonAdapter<OverViewType> {

	public XmsOverViewAdapter(Context context, List<OverViewType> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}
	
	@Override
	public void convert(ViewHolder helper, OverViewType item) {
		helper.setImageResource(R.id.icon, R.drawable.icon_card);
		helper.setText(R.id.xms_type, item.getType());
		helper.setText(R.id.xms_username,item.getSign());
		String strCostType = item.getType();
		if ("水费".equals(strCostType)) {
			helper.setImageResource(R.id.icon, R.drawable.icon_secretary_message_sf);
		} else if ("电费".equals(strCostType)) {
			helper.setImageResource(R.id.icon, R.drawable.icon_secretary_message_df);
		} else if ("燃气费".equals(strCostType)) {
			helper.setImageResource(R.id.icon, R.drawable.icon_secretary_message_mqf);
		} else if ("有线电视".equals(strCostType)) {
			helper.setImageResource(R.id.icon, R.drawable.icon_secretary_message_yxds);
		} else if ("移动通讯".equals(strCostType)) {
			helper.setImageResource(R.id.icon, R.drawable.icon_secretary_message_ydtx);
		} else if ("交通缴费".equals(strCostType)) {
			helper.setImageResource(R.id.icon, R.drawable.icon_secretary_message_jt);
		}else if ("存款到期".equals(strCostType) || "理财到期".equals(strCostType)) {
			helper.setImageResource(R.id.icon, R.drawable.icon_secretary_message_fin);
		}
		
		String strSign = item.getSign();
		if("签约".equals(strSign)){
			helper.setText(R.id.xms_date,"推送日期：" + item.getOrderDate() + "号");
			helper.setText(R.id.xms_username,"用户号：" + item.getUserCode() + "  用户名："  + item.getUserName());
			helper.setText(R.id.xms_usernamereal,item.getUserName());
		}else{
			helper.setText(R.id.xms_date,"");
			helper.setText(R.id.xms_username, item.getSign() + "(请点击进行签约)");
		}
	}
}
