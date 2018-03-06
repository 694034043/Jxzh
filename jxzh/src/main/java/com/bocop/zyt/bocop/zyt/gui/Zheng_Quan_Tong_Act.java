package com.bocop.zyt.bocop.zyt.gui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bocop.zyt.R;

public class Zheng_Quan_Tong_Act extends BaseAct implements OnClickListener {
	public static void startAct(Context context) {
		Intent intent = new Intent(context, Zheng_Quan_Tong_Act.class);
		context.startActivity(intent);
	}

	private TextView tv_actionbar_title;
	
	
	public static final String Zheng_quan_hang_qing="http://wechat.bocichina.com/zygj_weixin/weixin/hqboc/list.jsp";
	public static final String Cai_jing_zi_xun="http://wechat.bocichina.com/zygj_weixin/weixin/info/list.jsp?channelid=000100130023&share=1&accountno=gh_ec00b5e1997a";
	public static final String Zheng_quan_kai_hu="https://wykh.bocichina.com/m/open/index.html#!/business/middlePage.html?banktype=9&paramtype=102&branchno=1052&showOthers=false";
	public static final String Zheng_quan_jiao_yi="http://mentry.bocichina.com/special/etrade/phone_download.html";
	public static final String Zhong_guo_hong_shang_cheng="http://mentry.bocichina.com/special/eshop/phone_download.html";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_zheng_quan_tong);
		init_widget();
	}

	@Override
	public void init_widget() {
		// TODO Auto-generated method stub
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv_actionbar_title.setText("证券通");
		
		findViewById(R.id.tr_01).setOnClickListener(this);
		findViewById(R.id.tr_02).setOnClickListener(this);
		findViewById(R.id.tr_03).setOnClickListener(this);
		findViewById(R.id.tr_04).setOnClickListener(this);
		findViewById(R.id.tr_05).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		
		switch (v.getId()) {
		case R.id.tr_01:{
			
			XWebAct.startAct(this, Zheng_quan_hang_qing, "证券行情","");
			
			break;
		}
		case R.id.tr_02:{
			XWebAct.startAct(this, Cai_jing_zi_xun, "财经资讯","");
			break;
		}
		case R.id.tr_03:{
			XWebAct.startAct(this, Zheng_quan_kai_hu, "证券开户","");
			break;
		}
		case R.id.tr_04:{
			XWebAct.startAct(this, Zheng_quan_jiao_yi, "证券交易","");
			break;
		}
		case R.id.tr_05:{
			XWebAct.startAct(this, Zhong_guo_hong_shang_cheng, "中国红商城","");
			break;
		}
			

		default:
			break;
		}
	}

}
