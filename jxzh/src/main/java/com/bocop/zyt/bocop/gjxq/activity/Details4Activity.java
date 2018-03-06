package com.bocop.zyt.bocop.gjxq.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.gjxq.activity.adapter.DetailsAdapter;
import com.bocop.zyt.jx.base.BaseActivity;

/**
 * 第四个页面
 * @author Administrator
 *
 */
public class Details4Activity extends BaseActivity {
	
	private ImageView tvBackImageview;
	private TextView tvTitle;
	private ImageView ivPicture1;
	private ImageView ivPicture2;
	private GridView gvContent;

	private String[] titleArray = {"新区信息", "工商登记", "经营许可", "项目申报", "纳税缴费", "保税园区"};
	private String[] pictureArray1 = {"gjxq_zf_xqxx_t", "gjxq_zf_gsdj_t", "gjxq_zf_jyxk_t", "gjxq_zf_xmsb_t", "gjxq_zf_nsjf_t", "gjxq_zf_bsyq_t"};
	private String[] pictureArray2 = {"gjxq_zf_xqxx_b", "gjxq_zf_gsdj_b", "gjxq_zf_jyxk_b", "gjxq_zf_xmsb_b", "gjxq_zf_nsjf_b", "gjxq_zf_bsyq_b"};
	private String[] iconArray = null;
	private String[] textArray = null;
	private int flag = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details4);
		
		
		tvBackImageview = (ImageView) findViewById(R.id.tvBackImageView);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		ivPicture1 = (ImageView) findViewById(R.id.ivPicture1);
		ivPicture2 = (ImageView) findViewById(R.id.ivPicture2);
		gvContent = (GridView) findViewById(R.id.gvContent);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			flag = bundle.getInt("flag", 0);
			if (flag > 5) {
				flag = 0;
			}
		} 
		initData();

		tvTitle.setText(titleArray[flag]);
		int drawableId1 = ResourceUtil.getDrawableId(Details4Activity.this, pictureArray1[flag]);
		int drawableId2 = ResourceUtil.getDrawableId(Details4Activity.this, pictureArray2[flag]);
		if(drawableId1 != 0) {
			ivPicture1.setImageResource(drawableId1);
		}
		if(drawableId2 != 0) {
			ivPicture2.setImageResource(drawableId2);
		}
		gvContent.setAdapter(new DetailsAdapter(this, iconArray, textArray));
		gvContent.setNumColumns(iconArray.length);
		
		tvBackImageview.setOnClickListener(new OnClickListener() {
            
			@Override
			public void onClick(View arg0) {
				getActivityManager().backFinish();
				
			}
        });
	}
	
	private void initData(){
		
		switch (flag) {
		case 0:
			String[] iconArray_s0 = {"gjxq_zf_icon_zcfg", "gjxq_zf_icon_xqdt", "gjxq_zf_icon_bmsz"};
			iconArray = iconArray_s0;
			String[] textArray_s0 = {"政策法规", "新区动态", "部门设置"};
			textArray = textArray_s0;
			break;
		case 1:
			String[] iconArray_s1 = {"gjxq_zf_icon_zcdj", "gjxq_zf_icon_bgdj", "gjxq_zf_icon_cszc"};
			iconArray = iconArray_s1;
			String[] textArray_s1 = {"注册登记", "变更登记", "撤销注销"};
			textArray = textArray_s1;
			break;
		case 2:
			String[] iconArray_s2 = {"gjxq_zf_icon_nlmy", "gjxq_zf_icon_jtys", "gjxq_zf_icon_zljd"};
			iconArray = iconArray_s2;
			String[] textArray_s2 = {"农林牧渔", "交通运输", "质量监督"};
			textArray = textArray_s2;
			break;
		case 3:
			String[] iconArray_s3 = {"gjxq_zf_icon_tzxmsb", "gjxq_zf_icon_gdzcxmsz", "gjxq_zf_icon_qtxmsz"};
			iconArray = iconArray_s3;
			String[] textArray_s3 = {"投资项目申报", "固定资产项目申报", "其他项目申报"};
			textArray = textArray_s3;
			break;
		case 4:
			String[] iconArray_s4 = {"gjxq_zf_icon_ns", "gjxq_zf_icon_jf"};
			iconArray = iconArray_s4;
			String[] textArray_s4 = {"纳税", "缴费"};
			textArray = textArray_s4;
			break;
		case 5:
			String[] iconArray_s5 = {"gjxq_zf_icon_zczd", "gjxq_zf_icon_xzsp"};
			iconArray = iconArray_s5;
			String[] textArray_s5 = {"政策指导", "行政审批"};
			textArray = textArray_s5;
			break;
		}
		
	}
	
}
