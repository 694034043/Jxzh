package com.bocop.zyt.bocop.qzt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;

@ContentView(R.layout.qzt_activity_order_sure)
public class QztOrderSureActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.iv_imageLeft)
	private BackButton backBtn;
	
	@ViewInject(R.id.qztOrder)
	private TextView qztOrder;
	@ViewInject(R.id.qztAmt)
	private TextView qztAmt;
	@ViewInject(R.id.qztCountry)
	private TextView qztCountry;
	@ViewInject(R.id.qztDes)
	private TextView qztDes;
	@ViewInject(R.id.qztType)
	private TextView qztType;
	@ViewInject(R.id.qztName)
	private TextView qztName;
	@ViewInject(R.id.qztId)
	private TextView qztId;
	@ViewInject(R.id.qztTel)
	private TextView qztTel;
	@ViewInject(R.id.qztMail)
	private TextView qztMail;
	@ViewInject(R.id.qztAdress)
	private TextView qztAdress;
	
	@ViewInject(R.id.qztApply)
	private Button btQzt;
	
	private String orderNum;
	private String atm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        String Status = bundle.getString("Status");
        if(Status.equals("3")){
        	btQzt.setVisibility(View.INVISIBLE);
        }else{
        	btQzt.setVisibility(View.VISIBLE);
        }
		tv_titleName.setText("订单");
		ininData();
	}
	
	@OnClick(R.id.qztApply)
	public void onClick(View v){
		Bundle bundle = new Bundle();
		bundle.putString("orderNum", orderNum);
		bundle.putString("amt", atm);
		Intent intent = new Intent(QztOrderSureActivity.this,
				QztPayActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	@OnClick(R.id.qztMain)
	public void toQztMain(View v){
		Intent intentMain = new Intent(this,QztMainActivity.class);
		startActivity(intentMain);
	}
	


	private void ininData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if(bundle != null){
			//Log.i("tag", "bundle != null");
			orderNum = bundle.getString("orderNum");
			atm = bundle.getString("amt");
			qztOrder.setText(orderNum);
			qztAmt.setText(atm);
			qztCountry.setText(bundle.getString("strCountry"));
			qztDes.setText(bundle.getString("strPuopose"));
			qztType.setText(bundle.getString("strCrowdId"));
			qztName.setText(bundle.getString("strQztName"));
			qztId.setText(bundle.getString("strQztId"));
			qztTel.setText(bundle.getString("strQztTel"));
			qztMail.setText(bundle.getString("strQztMail"));
			qztAdress.setText(bundle.getString("strQztAdress"));
		}else{
			//Log.i("tag", "bundle = null");
			btQzt.setEnabled(false);
			tv_titleName.setText("");
		}
	}
	
	

}
