package com.bocop.zyt.bocop.zyt.gui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;


public class Ci_qian_nian_ci_du_Act extends BaseAct implements OnClickListener{

	
	public static void startAct(Context context){
		Intent intent=new Intent(context,Ci_qian_nian_ci_du_Act.class);
		context.startActivity(intent);
	}

	private ImageView iv_top_ads;
	private TextView tv_actionbar_title;
	private ImageView iv_bg_logo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_ci_qian_nian_ci_du);
		init_widget();
	}

	@Override
	public void init_widget() {
		// TODO Auto-generated method stub
		tv_actionbar_title=(TextView)findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText("银瓷通");
		iv_top_ads=(ImageView)findViewById(R.id.iv_top_ads);
		FImageloader.load_by_resid_fit_src(this, R.drawable.bg_pic_act_main_yin_ci_tong_fgt_qian_nian_ci_du, iv_top_ads);
		
		iv_bg_logo = (ImageView) findViewById(R.id.iv_bg_logo);
		int[] sc = ScreenUtil.get_screen_size(this);
		int w1 = sc[0]- DisplayUtil.dip2px(this, 70);
		int h1 = w1;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		iv_bg_logo.setLayoutParams(p);
		
		
		
		findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
		findViewById(R.id.ll_item_01_01_04).setOnClickListener(this);
		
		
		findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);
		findViewById(R.id.ll_item_02_01_04).setOnClickListener(this);
		
//		findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
//		findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
		
		
		findViewById(R.id.ll_item_04_01_01).setOnClickListener(this);
		findViewById(R.id.ll_item_04_01_02).setOnClickListener(this);
		findViewById(R.id.ll_item_04_01_03).setOnClickListener(this);
		findViewById(R.id.ll_item_04_01_04).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.ll_item_01_01_01:{
			XWebAct.startAct(_act, IURL.Bank_CI_Qian_Nian_Ci_Du_Ci_Ye_Wen_Hua, "瓷业文脉", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_02:{
			XWebAct.startAct(_act, "http://119.29.107.253:8080/zyhtbanking/bank/culture/ancientTown/index.html", "昌南古镇", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_03:{
			XWebAct.startAct(_act, IURL.Bank_CI_Qian_Nian_Ci_Du_Li_Shi_Ren_Wu, "历史人物", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_04:{
			XWebAct.startAct(_act, IURL.Bank_CI_Qian_Nian_Ci_Du_Ci_Yi_Gong_Xu, "瓷艺工序", R.drawable.shape_base_yct_action_bar);
			break;
		}
			
		case R.id.ll_item_02_01_01:{
			XWebAct.startAct(_act, IURL.Bank_CI_Qian_Nian_Ci_Du_Tao_Ci_Ming_Jia, "陶瓷名家", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_02_01_02:{
			XWebAct.startAct(_act, IURL.Bank_CI_Qian_Nian_Ci_Du_Ming_Ci_Jian_Shang, "名瓷鉴赏", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_02_01_03:{
			XWebAct.startAct(_act, "http://119.29.107.253:8080/zyhtbanking/bank/celebrity/ancientPorcelain/index.html", "古瓷风韵", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_02_01_04:{
			XWebAct.startAct(_act, "http://119.29.107.253:8080/zyhtbanking/bank/celebrity/winchance/index.html", "文创陶瓷", R.drawable.shape_base_yct_action_bar);
			break;
		}
		

		
		case R.id.ll_item_04_01_01:{
			
			XWebAct.startAct(_act, IURL.Bank_CI_Qian_Nian_Ci_Du_Lv_You_Jing_Dian, "VR看瓷都", R.drawable.shape_base_yct_action_bar);
			
			break;
		}
		case R.id.ll_item_04_01_02:{
			XWebAct.startAct(_act, "http://119.29.107.253:8080/zyhtbanking/bank/map/index.html", "陶瓷市场", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_04_01_03:{
			XWebAct.startAct(_act, "http://119.29.107.253:8080/zyhtbanking/bank/travel/research/index.html", "研学游玩", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_04_01_04:{
			XWebAct.startAct(_act, IURL.Bank_CI_Qian_Nian_Ci_Du_Ming_You_Te_Chan, "名优特产", R.drawable.shape_base_yct_action_bar);
			break;
		}

		default:
			break;
		}
	}

}
