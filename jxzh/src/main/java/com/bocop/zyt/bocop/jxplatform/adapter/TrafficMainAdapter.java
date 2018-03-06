package com.bocop.zyt.bocop.jxplatform.adapter;

import android.content.Context;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.bean.PerFunctionBean;
import com.bocop.zyt.jx.tools.CommonAdapter;
import com.bocop.zyt.jx.tools.ViewHolder;

import java.util.List;

/**
 * @author luoyang
 * @version 创建时间：2015-6-25 上午9:47:52 类说明
 */

public class TrafficMainAdapter extends CommonAdapter<PerFunctionBean> {


	public TrafficMainAdapter(Context context, List<PerFunctionBean> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, PerFunctionBean item) {
		helper.setImageResource(R.id.iv_quickleft, item.getImageRes());
		helper.setText(R.id.tv_quicktitle, item.getTitle());
	}

}
