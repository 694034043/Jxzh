package com.bocop.zyt.bocop.jxplatform.trafficassistant;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;


/** 
 * @author luoyang  
 * @version 创建时间：2015-6-19 下午1:51:51 
 * 类说明 
 */
@ContentView(R.layout.activity_trafficlicadd)
public class LicenseAddActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView  tv_titleName;
	@ViewInject(R.id.iv_imageLeft)
	private BackButton backBtn;
	
	@ViewInject(R.id.eddrivenum_add)
	EditText eddrivenum_add;
	@ViewInject(R.id.edfilenum_add)
	EditText edfilenum_add;
	@ViewInject(R.id.btlicenseadd)
	Button btlicenseadd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
}
