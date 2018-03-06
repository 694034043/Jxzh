package com.bocop.zyt.bocop.gjxq.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.jx.base.BaseActivity;

/**
 * 第三个页面
 * @author Administrator
 *
 */
public class Details3Activity extends BaseActivity {
	
	private ImageView tvBackImageview;
	private TextView tvTitle;
	private TextView tvContent;
	
	private String[] titleArray = {"工商登记", "经营许可", "项目申报", "纳税缴费", "保税园区", "政府信息"};
	private String[] contentArray = {"提供企业的工商注册设立、变更及注销登记服务，同时提供企业刻制印章，名称预先核准、备案登记、不动产证办理等业务。"
			, "提供农林牧渔、医疗医药、食品卫生、环保绿化、交通运输、安全生产、市政市容、质量监督、水利水务等多方面全方位的服务。"
			, "提供政府及企业投资项目审批与备案，本区域内固定资产投资项目及经各级投资主管部门备案及审批、政府其他项目申报等服务。"
			, "提供税务登记、变更、注销、退税、减免税、税收代征、出口退税认定等服务。"
			, "提供保税园区加工贸易、保税退税等优惠政策指导，园区企业行政审批等服务。"
			, "提供赣江新区政策法规，新区动态，部门设置等信息。/n办公地址：南昌市经济技术开发区儒乐湖大道"};
	private int flag = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details3);
		
		tvBackImageview = (ImageView) findViewById(R.id.tvBackImageView);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvContent = (TextView) findViewById(R.id.tvContent);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			flag = bundle.getInt("flag", 0);
			if (flag > 5) {
				flag = 0;
			}
		} 
		
		tvTitle.setText(titleArray[flag]);
		tvContent.setText(contentArray[flag]);
		
		tvBackImageview.setOnClickListener(new OnClickListener() {
            
			@Override
			public void onClick(View arg0) {
				getActivityManager().backFinish();
				
			}
        });
	}
	
}
