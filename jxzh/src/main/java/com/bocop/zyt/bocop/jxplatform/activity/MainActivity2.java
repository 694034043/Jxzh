package com.bocop.zyt.bocop.jxplatform.activity;

import android.app.Dialog;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.gjxq.activity.GuideActivity;
import com.bocop.zyt.bocop.jxplatform.adapter.IconPagerAdapter;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragment;
import com.bocop.zyt.bocop.jxplatform.fragment.PersonalFragment;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.bocop.jxplatform.view.TabButton;
import com.bocop.zyt.bocop.jxplatform.view.TabLayout;
import com.bocop.zyt.bocop.jxplatform.view.riders.NoScrollViewPager;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.tools.LogUtils;


@ContentView(R.layout.activity_main)
public class MainActivity2 extends BaseActivity implements LoginUtil.ILogoutListener {

	@ViewInject(R.id.tv_titleName)
	private TextView tv_titleName;
	@ViewInject(R.id.iv_imageLeft)
	private BackButton backBtn;

	@ViewInject(R.id.btn_home)
	private TabButton btn_home;
	@ViewInject(R.id.btn_activity)
	private TabButton btn_activity;
	@ViewInject(R.id.btn_personal)
	private TabButton btn_personal;
	@ViewInject(R.id.vpFragment)
	private NoScrollViewPager vpFragment;

	private TabLayout tabLayout;

	private long exitTime = 0;

	// public String func[] = { "首页", "活动", "个人"};
	public String func[] = { "易互通", "个人" };
	// private Class fragmentArray[] = { HomeFragment.class,
	// ActivityFragment.class, PersonalFragment.class};
	private Class fragmentArray[] = { HomeFragment.class, PersonalFragment.class };
//	private Class fragmentArray[] = { MainAct.class };
	private IconPagerAdapter pageAdapter;
	public static int as = 0;
	BaseApplication app;
	String mainType;
	private Dialog selectDialog;
	private SensorManager sensorManager;
	private Vibrator vibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (BaseApplication) getApplication();
		mainType = app.getMainType();
		init();
//		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		// initToken();
		
//		if (sensorManager != null) {// 注册监听器
//			sensorManager.registerListener(sensorEventListener,
//					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
//			// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
//		}
//		
//		show_cai_shen();
	}

//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//
//		show_cai_shen();
//
//		
//	}

//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//
//		
//	}
	
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		
//		if (sensorManager != null) {// 取消监听器
//			sensorManager.unregisterListener(sensorEventListener);
//		}
//	}

	

	// public void initToken() {
	// Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(this);
	// if(token != null && token.isSessionValid()) {
	// LoginUserInfo userInfo = new LoginUserInfo();
	// userInfo.setUserId(token.getUserId());
	// userInfo.setAccessToken(token.getToken());
	// getBaseApp().setUserInfo(userInfo);
	// }
	// }

	private void init() {
		// 初始化Tab项
		// tabLayout = new TabLayout(R.color.white,
		// R.color.white).addBtn(btn_home, btn_activity,
		// btn_personal);
		tabLayout = new TabLayout(R.color.white, R.color.white).addBtn(btn_home, btn_personal);
		btn_home.init(R.drawable.sy_default, R.drawable.sy_select, "首页", true, this);
		// btn_activity.init(R.drawable.hd_default, R.drawable.hd_select, "活动",
		// false,
		// this);
		btn_personal.init(R.drawable.user_default, R.drawable.user_select, "个人", false, this);

		pageAdapter = new IconPagerAdapter(this, fragmentArray);
		vpFragment.setAdapter(pageAdapter);

		// 设置初始Tap
		tabLayout.selectBtn(R.id.btn_home);
		if (mainType.equals("0")) {
			tv_titleName.setText("赣江新区-易互通");
		} else if (mainType.equals("1")) {
			tv_titleName.setText("景德镇-政银通");
		} else if (mainType.equals("2")) {
			tv_titleName.setText("银铁通");
		}

		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity2.this, GuideActivity.class));
				finish();

			}
		});
		vpFragment.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				LogUtils.i("setOnPageChangeListener：" + arg0);
				setSelectButton(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		btn_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				vpFragment.setCurrentItem(0, false);
			}
		});
		// btn_activity.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// vpFragment.setCurrentItem(1, false);
		// }
		// });
		btn_personal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				vpFragment.setCurrentItem(1, false);
			}
		});
	}

	// public void setSelectButton(int index) {
	// switch (index) {
	// case 0:
	// tabLayout.selectBtn(R.id.btn_home);
	// tv_titleName.setText("首页");
	// break;
	// case 1:
	// tabLayout.selectBtn(R.id.btn_activity);
	// tv_titleName.setText("活动");
	// break;
	// case 2:
	// tabLayout.selectBtn(R.id.btn_personal);
	// tv_titleName.setText("个人");
	// break;
	//
	// }
	//// fragmentList.get(index).onSelected();
	// }
	public void setSelectButton(int index) {
		switch (index) {
		case 0:
			tabLayout.selectBtn(R.id.btn_home);
			tv_titleName.setText("易互通");
			as = 0;
			break;
		case 1:
			tabLayout.selectBtn(R.id.btn_personal);
			tv_titleName.setText("个人");
			as = 1;
			break;

		}
		// fragmentList.get(index).onSelected();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
				exitApp();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	private void exitApp() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(MainActivity2.this, "再按一次退出程序", Toast.LENGTH_LONG).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			// System.exit(0);
			CacheBean.getInstance().clearCacheMap();
			// Log.i("tag", "logoutWithoutCallback");
			// LoginUtil.logoutWithoutCallback(MainActivity.this);
			getBaseApp().exit();
			System.exit(0);
		}

	}

	// @Override
	// public void onBackPressed() {
	//
	// BocopDialog dialog = new BocopDialog(this, "提示", "确定要退出应用吗？");
	// dialog.setPositiveListener(new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// // TODO Auto-generated method stub
	// CacheBean.getInstance().clearCacheMap();
	// getBaseApp().exit();
	// dialog.dismiss();
	// }
	// });
	// dialog.setNegativeButton(new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// }
	// }, "取消");
	// dialog.show();
	// }
	// DialogUtil.showWithTwoBtn(this, "提示", "确定退出应用吗？", "确定", "取消", new
	// DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// getBaseApp().exit();
	// }
	// }, new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// }
	// });
	// }

	@Override
	public void onLogout() {
		// TODO Auto-generated method stub

	}

}
