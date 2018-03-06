package com.bocop.zyt.bocop.jxplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragment;
import com.bocop.zyt.bocop.jxplatform.fragment.PersonalFragment;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyt.ICONST;
import com.bocop.zyt.bocop.zyt.gui.BaseFragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_GanjiangxinquFragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_JingdezhenFragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_bi_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_ci_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_gong_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_jun_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_wen_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_yan_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_yao_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_YintietongFragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_fu_ping_tong_Fragment;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;

import java.util.List;

public class MainActivity extends BaseActivity implements OnClickListener {

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
	public HomeFragment hh;
	private BaseApplication app;
	private MainAct_Yin_ci_tong_Fragment f_ci;
	private MainAct_Yin_yan_tong_Fragment f_yan;
	private MainAct_Yin_wen_tong_Fragment f_wen;
	private MainAct_Yin_gong_tong_Fragment f_gong;
	private MainAct_fu_ping_tong_Fragment f_fu;
	private MainAct_Yin_jun_tong_Fragment f_jun;
	private MainAct_Yin_yao_tong_Fragment f_yao;

	public static void startAct(Context context, int fun) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("fun", fun);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		supportFragmentManager = getSupportFragmentManager();
		app = (BaseApplication) getApplication();
		String mainType = app.getMainType();
		try {
			fun = Integer.parseInt(mainType);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fun = 0;
		}

		iv_main = (ImageView) findViewById(R.id.iv_main);
		iv_person = (ImageView) findViewById(R.id.iv_person);
		tv_main = (TextView) findViewById(R.id.tv_main);
		tv_person = (TextView) findViewById(R.id.tv_person);

		findViewById(R.id.ll_main).setOnClickListener(this);
		findViewById(R.id.ll_person).setOnClickListener(this);
		show_select_fragment();

	}

	@Override
	public void fun_back_press(View v) {
		// TODO Auto-generated method stub
		// startActivity(new Intent(this, GuideActivity.class));
		List<Fragment> fragments = supportFragmentManager.getFragments();
		for (Fragment f : fragments) {
			if (f != null && f.isVisible() && f instanceof BaseFragment) {
				((BaseFragment) f).goBack();
				return;
			}
		}
		backMainView();
	}

	public void backMainView() {
		finish();
	}

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
		case ICONST.Fun_Yincitong: {
			show_yin_ci_tong();
			break;
		}
		case ICONST.Fun_Yinyantong: {
			show_yin_yan_tong();
			break;
		}
		case ICONST.Fun_Yinwentong: {
			show_yin_wen_tong();
			break;
		}
		case ICONST.Fun_Yingongtong: {
			show_yin_gong_tong();
			break;
		}
		case ICONST.Fun_fupingtong: {
			show_fu_ping_tong();
			break;
		}
		case ICONST.Fun_Yinjuntong:
			show_yin_jun_tong();
			break;
		case ICONST.Fun_Yinbitong: {
			show_yin_bi_tong();
			break;
		}
		case ICONST.Fun_Yinyaotong: {
			show_yin_yao_tong();
			break;
		}
		}

	}

	private void show_yin_yao_tong() {
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_yao == null) {
			f_yao = new MainAct_Yin_yao_tong_Fragment();
			mTransaction.add(R.id.fl_container, f_yao);
		} else {
			mTransaction.show(f_yao);
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
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_wen != null){
			mTransaction.hide(f_wen);
		}
		if (f_gong != null){
			mTransaction.hide(f_gong);
		}
		if (f_bi !=null){
			mTransaction.hide(f_bi);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		mTransaction.commit();

		fragment_index = 1;
	}

	private void show_yin_jun_tong() {
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_jun == null) {
			f_jun = new MainAct_Yin_jun_tong_Fragment();
			mTransaction.add(R.id.fl_container, f_jun);
		} else {
			mTransaction.show(f_jun);
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
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_wen != null){
			mTransaction.hide(f_wen);
		}
		if (f_gong != null){
			mTransaction.hide(f_gong);
		}
		if (f_bi !=null){
			mTransaction.hide(f_bi);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
		}
		mTransaction.commit();

		fragment_index = 1;
	}

	private void show_fu_ping_tong() {
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_fu == null) {
			f_fu = new MainAct_fu_ping_tong_Fragment();
			mTransaction.add(R.id.fl_container, f_fu);
		} else {
			mTransaction.show(f_fu);
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
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_wen != null){
			mTransaction.hide(f_wen);
		}
		if (f_gong != null){
			mTransaction.hide(f_gong);
		}
		if (f_bi != null){
			mTransaction.hide(f_bi);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
		}
		mTransaction.commit();

		fragment_index = 1;
	}

	private void show_yin_bi_tong() {
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_bi == null) {
			f_bi = new MainAct_Yin_bi_tong_Fragment();
			mTransaction.add(R.id.fl_container, f_bi);
		} else {
			mTransaction.show(f_bi);
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
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_wen != null){
			mTransaction.hide(f_wen);
		}
		if (f_gong != null){
			mTransaction.hide(f_gong);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
		}
		mTransaction.commit();

		fragment_index = 1;
	}

	private void show_yin_gong_tong() {
		// TODO Auto-generated method stub
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_gong == null) {
			f_gong = new MainAct_Yin_gong_tong_Fragment();
			mTransaction.add(R.id.fl_container, f_gong);
		} else {
			mTransaction.show(f_gong);
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
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_wen != null){
			mTransaction.hide(f_wen);
		}
		if (f_bi != null){
			mTransaction.hide(f_bi);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
		}
		mTransaction.commit();

		fragment_index = 1;
	}

	private void show_yin_wen_tong() {
		// TODO Auto-generated method stub
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_wen == null) {
			f_wen = new MainAct_Yin_wen_tong_Fragment();
			mTransaction.add(R.id.fl_container, f_wen);
		} else {
			mTransaction.show(f_wen);
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
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_gong != null) {
			mTransaction.hide(f_gong);
		}
		if (f_bi != null){
			mTransaction.hide(f_bi);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
		}
		mTransaction.commit();

		fragment_index = 1;
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
		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_yan != null) {
			mTransaction.hide(f_yan);
		}
		if (f_wen != null) {
			mTransaction.hide(f_wen);
		}
		if (f_gong != null) {
			mTransaction.hide(f_gong);
		}
		if (f_bi != null){
			mTransaction.hide(f_bi);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
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
		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_yan != null) {
			mTransaction.hide(f_yan);
		}
		if (f_wen != null) {
			mTransaction.hide(f_wen);
		}
		if (f_gong != null) {
			mTransaction.hide(f_gong);
		}
		if (f_bi != null){
			mTransaction.hide(f_bi);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
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
		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_yan != null) {
			mTransaction.hide(f_yan);
		}
		if (f_wen != null) {
			mTransaction.hide(f_wen);
		}
		if (f_gong != null) {
			mTransaction.hide(f_gong);
		}
		if (f_bi != null){
			mTransaction.hide(f_bi);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
		}
		mTransaction.commit();

		fragment_index = 1;

	}

	public void show_yin_ci_tong() {
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_ci == null) {
			f_ci = new MainAct_Yin_ci_tong_Fragment();
			mTransaction.add(R.id.fl_container, f_ci);
		} else {
			mTransaction.show(f_ci);
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
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		if (f_yan != null) {
			mTransaction.hide(f_yan);
		}
		if (f_wen != null) {
			mTransaction.hide(f_wen);
		}
		if (f_gong != null) {
			mTransaction.hide(f_gong);
		}
		if (f_bi != null){
			mTransaction.hide(f_bi);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
		}
		mTransaction.commit();

		fragment_index = 1;

	}

	public void show_yin_yan_tong() {
		if (fragment_index == 1) {
			return;
		}
		FragmentTransaction mTransaction = supportFragmentManager.beginTransaction();

		if (f_yan == null) {
			f_yan = new MainAct_Yin_yan_tong_Fragment();
			mTransaction.add(R.id.fl_container, f_yan);
		} else {
			mTransaction.show(f_yan);
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
		if (f_person != null) {
			mTransaction.hide(f_person);
		}
		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_wen != null) {
			mTransaction.hide(f_wen);
		}
		if (f_gong != null) {
			mTransaction.hide(f_gong);
		}
		if (f_bi != null){
			mTransaction.hide(f_bi);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
		}
		mTransaction.commit();

		fragment_index = 1;

	}

	public int fragment_index = 0;
	private MainAct_Yin_bi_tong_Fragment f_bi;

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

		if (f_ci != null) {
			mTransaction.hide(f_ci);
		}
		if (f_yan != null) {
			mTransaction.hide(f_yan);
		}
		if (f_wen != null) {
			mTransaction.hide(f_wen);
		}
		if (f_gong != null) {
			mTransaction.hide(f_gong);
		}
		if (f_bi != null){
			mTransaction.hide(f_bi);
		}
		if (f_fu !=null){
			mTransaction.hide(f_fu);
		}
		if (f_jun != null){
			mTransaction.hide(f_jun);
		}
		if (f_yao != null){
			mTransaction.hide(f_yao);
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

	// @Override
	// public void init_widget() {
	// // TODO Auto-generated method stub
	//
	// }

	/*@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
				exitApp();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}*/

	private void exitApp() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			ToastUtils.show(this, "再按一次退出程序", Toast.LENGTH_SHORT);
			exitTime = System.currentTimeMillis();
		} else {
			//finish();
			// System.exit(0);
			//getBaseApp().exit();
			finish();
			CacheBean.getInstance().clearCacheMap();
			// Log.i("tag", "logoutWithoutCallback");
			// LoginUtil.logoutWithoutCallback(MainActivity.this);
			BaseApplication.getInstance().exit();
			// getBaseApp().exit();
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

}
