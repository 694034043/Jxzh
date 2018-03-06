package com.bocop.zyt.bocop.zyt.gui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.fragment.PersonalFragment;
import com.bocop.zyt.bocop.zyt.ICONST;

public class MainAct extends BaseAct implements View.OnClickListener {

	private int fun;
	private FragmentManager supportFragmentManager;
	private MainAct_GanjiangxinquFragment f_gan;
	private MainAct_JingdezhenFragment f_jing;
	private MainAct_YintietongFragment f_tie;
	private long exitTime;
	private ImageView iv_main;
	private ImageView iv_person;
	private TextView tv_main;
	private TextView tv_person;
	private PersonalFragment f_person;
	private View v;

	public static void startAct(Context context, int fun) {
		Intent intent = new Intent(context, MainAct.class);
		intent.putExtra("fun", fun);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		supportFragmentManager = getSupportFragmentManager();
		// fun = getIntent().getExtras().getInt("fun");

		iv_main = (ImageView) findViewById(R.id.iv_main);
		iv_person = (ImageView) findViewById(R.id.iv_person);
		tv_main = (TextView) findViewById(R.id.tv_main);
		tv_person = (TextView) findViewById(R.id.tv_person);

		v.findViewById(R.id.ll_main).setOnClickListener(this);
		v.findViewById(R.id.ll_person).setOnClickListener(this);
		show_select_fragment();

	}

	// @Override
	// public void init_widget() {
	//
	// }

	private void show_select_fragment() {

		iv_main.setImageResource(R.drawable.act_main_main_02);
		tv_main.setTextColor(getResources().getColor(R.color.theme_color));

		iv_person.setImageResource(R.drawable.act_main_persion_01);
		tv_person.setTextColor(getResources().getColor(R.color.color_949BAB));

		switch (fun) {

		case ICONST.Fun_Gangjiangxinqu: {
			show_gan_jiang_xin_qu();
			break;
		}
		case ICONST.Fun_Jingdezhen: {
			show_jing_de_zhen();
			break;
		}
		case ICONST.Fun_Yintietong: {
			show_yin_tie_tong();
			break;
		}
		}

	}

	public void show_gan_jiang_xin_qu() {
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_gan == null) {
			f_gan = new MainAct_GanjiangxinquFragment();
			mTransaction.add(R.id.fl_container, f_gan);
		} else {
			mTransaction.show(f_gan);
		}
		// -----影藏其他

		if (f_jing != null) {
			mTransaction.hide(f_jing);
		}
		if (f_tie != null) {
			mTransaction.hide(f_tie);
		}
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		mTransaction.commit();

		fragment_index = 1;

	}

	public void show_jing_de_zhen() {
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_jing == null) {
			f_jing = new MainAct_JingdezhenFragment();
			mTransaction.add(R.id.fl_container, f_jing);
		} else {
			mTransaction.show(f_jing);
		}
		// -----影藏其他

		if (f_gan != null) {
			mTransaction.hide(f_gan);
		}
		if (f_tie != null) {
			mTransaction.hide(f_tie);
		}
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		mTransaction.commit();

		fragment_index = 1;

	}

	public void show_yin_tie_tong() {
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_tie == null) {
			f_tie = new MainAct_YintietongFragment();
			mTransaction.add(R.id.fl_container, f_tie);
		} else {
			mTransaction.show(f_tie);
		}
		// -----影藏其他

		if (f_gan != null) {
			mTransaction.hide(f_gan);
		}
		if (f_jing != null) {
			mTransaction.hide(f_jing);
		}
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		mTransaction.commit();

		fragment_index = 1;

	}

	public int fragment_index = 0;

	public void show_person_fragment() {

		if (fragment_index == 3) {
			return;
		}
		iv_main.setImageResource(R.drawable.act_main_main_01);
		tv_main.setTextColor(getResources().getColor(R.color.color_949BAB));

		iv_person.setImageResource(R.drawable.act_main_persion_02);
		tv_person.setTextColor(getResources().getColor(R.color.theme_color));

		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_person == null) {
			f_person = new PersonalFragment();
			mTransaction.add(R.id.fl_container, f_person);
		} else {
			mTransaction.show(f_person);
		}
		// -----影藏其他

		if (f_gan != null) {
			mTransaction.hide(f_gan);
		}
		if (f_jing != null) {
			mTransaction.hide(f_jing);
		}
		if (f_tie != null) {
			mTransaction.hide(f_tie);
		}
		mTransaction.commit();

		fragment_index = 3;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.ll_main: {
			show_select_fragment();
			break;
		}
		case R.id.ll_person: {
			show_person_fragment();
			break;
		}

		}
	}

	@Override
	public void init_widget() {
		// TODO Auto-generated method stub
		
	}

	// @Override
	// public boolean dispatchKeyEvent(KeyEvent event) {
	// if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	// if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount()
	// == 0) {
	// exitApp();
	// }
	// return true;
	// }
	// return super.dispatchKeyEvent(event);
	// }

	// private void exitApp() {
	// if ((System.currentTimeMillis() - exitTime) > 2000) {
	// fun_toast("再按一次退出程序");
	// exitTime = System.currentTimeMillis();
	// } else {
	// finsh_all_acts();
	//// finish();
	//
	// // System.exit(0);
	//// CacheBean.getInstance().clearCacheMap();
	// // Log.i("tag", "logoutWithoutCallback");
	// // LoginUtil.logoutWithoutCallback(MainActivity.this);
	//// getBaseApp().exit();
	//// System.exit(0);
	// }
	//
	// }
}
