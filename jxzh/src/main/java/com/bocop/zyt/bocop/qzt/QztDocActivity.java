package com.bocop.zyt.bocop.qzt;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.bean.QztDocBean;
import com.bocop.zyt.bocop.jxplatform.bean.QztDocListBean;
import com.bocop.zyt.bocop.jxplatform.util.JsonUtils;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.qzt_activity_doc)
public class QztDocActivity extends BaseActivity {

	@ViewInject(R.id.tv_titleName)
	private TextView tvTitleName;

	@ViewInject(R.id.tv_doc)
	private TextView tvDoc;

	QztDocListBean qztDocListBean;
	List<QztDocBean> listQztDoc;
	
	String countryId;
	String purposeId;
	String crowdId;
	String strSplit = "\\d、";
	String[] arr;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tvTitleName.setText("登记材料");

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			String responStr;
			responStr = bundle.getString("responStr");
			countryId = bundle.getString("countryId");
			purposeId = bundle.getString("purposeId");
			crowdId = bundle.getString("crowdId");
			//Log.i("tag6", responStr);
			initJson(responStr);
		}

		// initList();
	}

	private void initJson(String responStr) {

		try {
			//Log.i("tag6", "initJson");
			qztDocListBean = JsonUtils.getObject(responStr,
					QztDocListBean.class);
			listQztDoc = qztDocListBean.getBody();
			//Log.i("tag6", "initJson comp");
			String info = "<h2 align=\"center\"><b>" +  countryId + ">" + purposeId + ">"  + crowdId + "</b></h2>";
			for (int i = 0; i < listQztDoc.size(); i++) {
				//Log.i("tag6", String.valueOf(i));
				info = info + "<h5 align=\"center\"><b>" + String.valueOf(i + 1) + "、"
						+ listQztDoc.get(i).getDocname().trim() + "("
						+ listQztDoc.get(i).getDoclist() + ")" + "</b></h5>";
				//对详细信息进行分割（如果存在条目的话）
				String strInfo = listQztDoc.get(i).getDocdesc();
				arr = strInfo.split(strSplit);
				info = info +  "<p>" +  arr[0] + "</p>";
				for(int j=1;j<arr.length;j++)
				{
					info = info +  "<p>"  + j + "、" + arr[j] + "</p>";
				}

				//Log.i("tag6", String.valueOf(i) + "comp");
			}
			tvDoc.setText(Html.fromHtml(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// private void initList(){
	// QztListAdapter adapter = new QztListAdapter(QztDocActivity.this, lists);
	// listView.setAdapter(adapter);
	// layout.setVisibility(View.VISIBLE);
	// }

}
