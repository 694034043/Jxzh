package com.bocop.zyt.bocop.yfx.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragmentHelper;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;

@ContentView(R.layout.yfx_activity_cousulation)
public class ConsultationActivity extends BaseActivity {
	@ViewInject(R.id.tvContent)
	private TextView tvContent;
	@ViewInject(R.id.tvTitleName)
	private TextView tvTitleName;
	@ViewInject(R.id.tv_titleName)
	private TextView title;
	@ViewInject(R.id.rl_text)
	private RelativeLayout rlText;
	@ViewInject(R.id.iv_img)
	private ImageView ivImag;
	private static final String STATE = "state";
	public static final int STATE_DEFAULT = 0;
	public static final int STATE_FUPING = 1;
	public static final int STATE_FUNONG = 2;
	private int state;
	private String contentTitle;
	
	public static void startAct(Context context,int state){
		Intent intent = new Intent(context,ConsultationActivity.class);
		intent.putExtra("state", state);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		title.setText("业务咨询");
		state = getIntent().getIntExtra(STATE, STATE_DEFAULT);
		if(state== HomeFragmentHelper.Tag_zhigongxiaofeidai){
			contentTitle = getResources().getString(R.string.ytt_item_01_01_04);
		}else if(state==HomeFragmentHelper.TAG_YAN_XIAO_FEI_YI_DAI){
			contentTitle = getResources().getString(R.string.yyant_finance_item_01_01_01);
		}else if(state==HomeFragmentHelper.TAG_CI_XIAO_DAI_BAO){
			contentTitle = getResources().getString(R.string.yct_finance_item_02_01_01);
		}else if(state==HomeFragmentHelper.TAG_WEN_XIAO_DAI){
			contentTitle = getResources().getString(R.string.ywt_item_01_f2_01_01_02);
		}else if(state==HomeFragmentHelper.TAG_JUN_REN_ZHUAN_XIANG_DAI){
			contentTitle = getResources().getString(R.string.yjt_item_01_01_01);
		}else if(state==HomeFragmentHelper.TAG_XIAO_FEI_DAI){
			contentTitle = getResources().getString(R.string.ygt_item_01_01_03);
		}else{
			contentTitle = "个贷通";
		}
		//满足以下五类标准的代发薪客户和非代发薪客户：\n 1、公务员类客户 \n 2、中国银行关爱专属客户 \n 3、优质行业的国有企业、事业单位客户 \n 4、行业内处于领先地位的民营企业客户 \n 5、全球500强企业及其分支机构
		String content="(1) 怎样确定“"+contentTitle+"”受邀客户？  \n          贷款对象为中国银行江西省分行主动邀约的客户，邀约对象包括代发薪客户、中国银行关爱系列卡专属客户、行政事业单位等符合条件的客户。 \n(2) 贷款用途该如何选择? "
					+ " \n          您需要根据您贷款的实际用途进行选择。贷款用途仅用于个人合法合理的消费支出，借款人不得将贷款用于购房、投资经营和无指定用途的个人支出，不得用于任何法律法规、监管规定、国家政策禁止银行贷款投入的项目、用途，包括基金、理财、股票证劵投资等。";
		title.setText("常见问题");
		if(state==STATE_FUPING){
			content = "(1)什么是“政府扶贫主管部门建档立卡的贫困户”？ \n          根据国务院扶贫办《扶贫开发建档立卡工作方案》等相关规定，已在相关政府扶贫主管部门完成审批流程并建立贫困档案，获得贫困卡的贫困户。"
					+ "\n(2) 中行扶贫贷贷款对象？\n          江西省内建档立卡贫困客户，符合金融精准扶贫贷款条件，并按当地政府部门与中行合作协议约定程序审核通过的贫困户。";			
		title.setText("常见问题");
		}else if(state==STATE_FUNONG){
			content = "(1)中行扶农通贷款对象？\n  江西省内农村地区从事种植、养殖、生产等客户。";
		}
	    tvContent.setText(content);
	}

}
