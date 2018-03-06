package com.framewidget;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.framewidget.newMenu.OnCheckChange;
import com.framewidget.newMenu.OnPageSelset;
import com.framewidget.newMenu.SlidingFragment;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.MActivity;
import com.mdx.framework.activity.MFragment;
import com.mdx.framework.widget.MViewPager;

public class MainActivity extends MActivity implements OnCheckChange,
		OnPageSelset {
	// private FrameLayout mLinearLayout_content;
	FragmentManager fragmentManager;
	SlidingFragment mSlidingFragment;
	private long exitTime = 0;
	public MViewPager mContentView;
	public ArrayList<MFragment> fragments = new ArrayList<MFragment>();

	@SuppressLint("NewApi")
	@Override
	protected void create(Bundle arg0) {
		Log.d("test", "create");
		setContentView(R.layout.activity_main_ce);
		Frame.init(this);
		// mLinearLayout_content = (FrameLayout)
		// findViewById(R.id.mLinearLayout_content);
		// mLinearLayout_content.removeAllViews();
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		if (mSlidingFragment != null) {
			Log.d("test", "mSlidingFragment");
			fragmentTransaction.remove(mSlidingFragment);
		}
		mSlidingFragment = new SlidingFragment(this);
		
		fragmentTransaction.replace(R.id.mLinearLayout_content, mSlidingFragment);
		// fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

		// FragmentManager fragmentManager = getSupportFragmentManager();
		// FragmentTransaction fragmentTransaction = fragmentManager
		// .beginTransaction();
		// if (mFragment != null) {
		// fragmentTransaction.remove(mFragment);
		// }
		// fragmentTransaction.add(R.id.defautlt_container, fragment);
		// mFragment = fragment;
		// fragmentTransaction.commit();

		// mSlidingFragment.addContentView(new Test(), "xiaobi",
		// R.drawable.ic_launcher);
		mSlidingFragment.addContentView(new Test3(), "xiaobi",
				R.drawable.ic_launcher, "10");
		mSlidingFragment.addContentView(new Test3(), "xiaobi",
				R.drawable.ic_launcher, "10");
		mSlidingFragment.addContentView(new Test3(), "xiaobi",
				R.drawable.ic_launcher, "15");
		// mSlidingFragment.addContentView(new Test4(), "xiaobi",
		// R.drawable.ic_launcher);
		// mSlidingFragment.addContentView(new Test4(), "xiaobi",
		// R.drawable.ic_launcher);
		// mSlidingFragment.addContentView(new Test4(), "xiaobi",
		// R.drawable.ic_launcher);
		// mSlidingFragment.setIsShow(true, 0);
		mSlidingFragment.setLeftFragMent(new Test2());
		mSlidingFragment.setRightFragMent(new Test2());
		// mSlidingFragment.toogleBottomBar(false);
		mSlidingFragment.setMode(0);
		mSlidingFragment.setIsShow(false, 0);
		setSwipeBackEnable(false);
		Log.i("测试", "create");
	}

	@Override
	public void disposeMsg(int type, Object obj) {

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", 0).show();
				exitTime = System.currentTimeMillis();
			} else {
				close();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 清空栈
	 * 
	 * @author Administrator
	 * @Title: close
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public static void close() {
		Frame.HANDLES.closeAll();
		Frame.IMAGECACHE.clean();
	}

	@Override
	public void onCheckedChanged(int id, int position) {
		switch (position) {
		case 0:
			// Toast.makeText(getApplicationContext(), "处理了0事件", 0).show();
			break;
		case 2:
			// Toast.makeText(getApplicationContext(), "回到上一页", 0).show();
			// mSlidingFragment.setIsShow(true, 0);
			// mSlidingFragment.goBack();
			break;
		case 1:
			// Toast.makeText(getApplicationContext(), "回到指定页面", 0).show();
			// mSlidingFragment.goWhere(3);
			break;
		}
	}

	@Override
	public void OnPageSelseTed(int position) {

	}
}
