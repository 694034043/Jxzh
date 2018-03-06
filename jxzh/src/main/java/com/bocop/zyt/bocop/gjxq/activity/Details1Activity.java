package com.bocop.zyt.bocop.gjxq.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.jx.base.BaseActivity;

/**
 * 第一个页面
 * @author Administrator
 *
 */
public class Details1Activity extends BaseActivity {

	private ImageView tvBackImageview;
	private TextView tvTitle;
	private ImageView ivPicture;
//	private TextView tvHead;
//	private TextView tvContent;
	private ImageView ivPicture2;

	private String[] titleArray = {"报关通", "车商通", "航运通", "企付通", "CAM对公自助服务", "中银金融服务中心","居民健康卡介绍","中小通","出国金融","个贷通"};
	private String[] pictureArray = {"gjxq_detail1_bgt", "gjxq_detail1_cst", "gjxq_detail1_est", "gjxq_detail1_qft", 
			"gjxq_detail1_cam_dgzzyh", "gjxq_detail1_zyjrfwzx","jdzgg1","jdzgg2","ytt_gd1","ytt_gd2"};
//	private String[] pictureArray2 = {"gjxq_detail1_bgt", "gjxq_detail1_cst", "gjxq_detail1_est", "gjxq_detail1_qft", "gjxq_detail1_cam_dgzzyh_b", "gjxq_detail1_zyjrfwzx_b"};
	private String[] headArray = {"报关通", "车商通", "航运通", "企付通", "CAM对公自助服务", "中银金融服务中心"};
	private String[] contentArray;

	private int flag = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details1);
		
		tvBackImageview = (ImageView) findViewById(R.id.tvBackImageView);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		ivPicture = (ImageView) findViewById(R.id.ivPicture);
//		tvHead = (TextView) findViewById(R.id.tvHead);
//		tvContent = (TextView) findViewById(R.id.tvContent);
//		ivPicture2 = (ImageView) findViewById(R.id.ivPicture2);

		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			flag = bundle.getInt("flag", 0);
			if (flag > 9) {
				flag = 0;
			}
		} 
		
		if(flag == 4 || flag ==5){
		}else{
//			tvHead.setVisibility(View.GONE);
//			tvContent.setVisibility(View.GONE);
		}
		
		contentArray = getResources().getStringArray(R.array.details_content);
		tvTitle.setText(titleArray[flag]);
//		tvHead.setText(headArray[flag]);
//		tvContent.setText(contentArray[flag]);
		int drawableId = ResourceUtil.getDrawableId(Details1Activity.this, pictureArray[flag]);
//		int drawableId2 = ResourceUtil.getDrawableId(Details1Activity.this, pictureArray2[flag]);

		if(drawableId != 0) {
			ivPicture.setImageResource(drawableId);
		}
//		if(drawableId2 != 0) {
//			ivPicture2.setImageResource(drawableId2);
//		}
		tvBackImageview.setOnClickListener(new OnClickListener() {
            
			@Override
			public void onClick(View arg0) {
				getActivityManager().backFinish();
				
			}
        });
	}
	
}



