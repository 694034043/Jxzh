package com.bocop.zyt.bocop.gjxq.activity;

import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.added.Attention_Dialog_adapter;
import com.bocop.zyt.bocop.gjxq.activity.adapter.MyPagerAdapter;
import com.bocop.zyt.bocop.jxplatform.activity.MainActivity;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.tools.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

	ViewPager vpContent;

	private List<View> views = new ArrayList<View>();
	BaseApplication app;
	TextView textView4, textView5;
	boolean toturn = false;
	String mainType = "0";
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		vpContent = (ViewPager) findViewById(R.id.vpContent);
		;
		app = (BaseApplication) getApplication();
		// View view1 =
		// LayoutInflater.from(this).inflate(R.layout.layout_guide_view, null);
		// View view2 =
		// LayoutInflater.from(this).inflate(R.layout.layout_guide_view, null);
		View view3 = LayoutInflater.from(this).inflate(R.layout.layout_guide_view, null);
		// 兄弟你这个地方有点。。
		TextView button1 = (TextView) view3.findViewById(R.id.textView1);
		button1.getBackground().setAlpha(150);
		TextView button2 = (TextView) view3.findViewById(R.id.textView2);
		button2.getBackground().setAlpha(150);
		TextView button3 = (TextView) view3.findViewById(R.id.textView3);
		button3.getBackground().setAlpha(150);
		textView4 = (TextView) view3.findViewById(R.id.textView4);
		textView5 = (TextView) view3.findViewById(R.id.textView5);
		SharedPreferences sharedPreferences = this.getSharedPreferences("welcome", MODE_PRIVATE);
		mainType = sharedPreferences.getString("welcome", "noType");
		editor = sharedPreferences.edit();
		if (mainType.equals("noType")) {
			textView4.setText("请选择服务");
		} else {
			textView4.setText(mainType);
			if (mainType.equals("赣江新区")) {
				app.setMainType("0");
			} else if (mainType.equals("景德镇")) {
				app.setMainType("1");
			} else if (mainType.equals("银铁通")) {
				app.setMainType("2");
			} else if (mainType.equals("银瓷通")) {
				app.setMainType("3");
			} else if (mainType.equals("银烟通")) {
				app.setMainType("4");
			}
		}

		views.add(view3);
		gList.add("赣江新区");
		gList.add("景德镇");
		gList.add("银铁通");
		gList.add("银瓷通");
		gList.add("银烟通");
		vpContent.setAdapter(new MyPagerAdapter(views));
		textView4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopupWindow(textView4);
			}
		});
		textView5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = textView4.getText().toString();
				if (name.equals("赣江新区")) {
					app.setMainType("0");
					editor.putString("welcome", "赣江新区");
					editor.commit();
					callMe(MainActivity.class);
					finish();
				} else if (name.equals("景德镇")) {
					app.setMainType("1");
					editor.putString("welcome", "景德镇");
					editor.commit();
					callMe(MainActivity.class);
					finish();
				} else if (name.equals("银铁通")) {
					app.setMainType("2");
					editor.putString("welcome", "银铁通");
					editor.commit();
					callMe(MainActivity.class);
					finish();
				} else if (name.equals("银瓷通")) {
					app.setMainType("3");
					editor.putString("welcome", "银瓷通");
					editor.commit();
					callMe(MainActivity.class);
					finish();
				} else if (name.equals("银烟通")) {
					app.setMainType("4");
					editor.putString("welcome", "银烟通");
					editor.commit();
					callMe(MainActivity.class);
					finish();
				} else if (textView4.getText().toString().equals("请选择服务")) {
					Toast.makeText(getApplicationContext(), "请选择服务", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	PopupWindow popupWindow;
	Attention_Dialog_adapter dAdapter;
	List<String> gList = new ArrayList<String>();

	private void showPopupWindow(View view) {
		View layout = LayoutInflater.from(GuideActivity.this).inflate(R.layout.dialog_attention, null);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		int w_screen = dm.widthPixels;
		int h_screen = dm.heightPixels;

		popupWindow = new PopupWindow(layout, view.getWidth(), DisplayUtil.dip2px(_act, 220), true);
		ListView listView = (ListView) layout.findViewById(R.id.dialog_attention_listView);
		dAdapter = new Attention_Dialog_adapter(gList, GuideActivity.this);
		listView.setAdapter(dAdapter);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		popupWindow.showAsDropDown(view, Gravity.LEFT, 0);
		// 0 全部 1 搜索 2 全部高低分 3 分组公司4 分组的高低分

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				popupWindow.dismiss();
				textView4.setText(gList.get(position));

			}
		});
	}
}
