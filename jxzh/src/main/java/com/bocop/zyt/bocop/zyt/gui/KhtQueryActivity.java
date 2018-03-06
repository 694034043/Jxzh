package com.bocop.zyt.bocop.zyt.gui;

import java.util.Map;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.util.JsonUtils;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.utils.Utils;
import com.bocop.zyt.fmodule.utils.IHttpClient;
import com.bocop.zyt.jx.base.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class KhtQueryActivity extends BaseActivity implements OnClickListener{
	
	private TextView tvContent;
	private EditText etData;
	private TextView title;
	private String titleParams;
	
	public static void startAct(Context context,String title,boolean isShowParamTitls){
		Intent intent = new Intent(context, KhtQueryActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("isShowParamTitles", isShowParamTitls);
		context.startActivity(intent);
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_kht_query);
		initView();
	}

	private void initView() {
		etData = (EditText) findViewById(R.id.et_data);
		findViewById(R.id.btn_query).setOnClickListener(this);
		tvContent = (TextView) findViewById(R.id.tv_query_content);
		title = (TextView) findViewById(R.id.tv_actionbar_title);
		titleParams = getIntent().getStringExtra("title");
		titleParams = TextUtils.isEmpty(titleParams)?"开户查询":titleParams;
		title.setText(titleParams);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_query:
			queryData();
			break;
		}
	}

	private void queryData() {
		tvContent.setText("");
		if(!TextUtils.isEmpty(etData.getText().toString())||Utils.isIdcard(etData.getText().toString())){
			if(Utils.isValidatedAllIdcard(etData.getText().toString())){
				IHttpClient.aysnMainThread(IHttpClient.getDefaultHttpClient(), IURL.Bank_kht_grcxjk_url, "{\"sfz_no\":\""+etData.getText().toString()+"\"}=", new IHttpClient.Callback() {
					
					@Override
					public void suc(String ret) {
						try {
							Map<String, String> map = JsonUtils.getMapStr(ret.substring(ret.indexOf("{"),ret.lastIndexOf("}")+1));
							String result = map.get("msg");
							if(map.get("result").equals("5900000")){
								result = "您的编号为："+result;
							}
							tvContent.setText(result);
						} catch (Exception e) {
							Log.e(KhtQueryActivity.class.getName(), e.getMessage());
						}
					}
					
					@Override
					public void fail(String ret) {
						tvContent.setText("查询失败");
					}
				});
			}else{
				ToastUtils.show(this, "请输入正确的身份证号码", Toast.LENGTH_SHORT);
			}
		}else{
			ToastUtils.show(this, "您输入的身份证有误请重新输入", Toast.LENGTH_SHORT);
		}
	}

}
