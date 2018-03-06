package com.framewidget;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.framewidget.newMenu.ICallback;
import com.mdx.framework.activity.MActivity;
import com.mdx.framework.activity.MFragment;
import com.mdx.framework.widget.MViewPager;

public class Test extends MActivity implements ICallback {
	public MViewPager mContentView;
	public ArrayList<MFragment> fragments = new ArrayList<MFragment>();

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.test);
		setSwipeBackEnable(false);
		mContentView = (MViewPager) findViewById(R.id.frame_content);
		mContentView.setAdapter(new MFragmentAdapter(
				getSupportFragmentManager()));
	}

	public void initFramgent() {

	}

	public class MFragmentAdapter extends FragmentPagerAdapter {

		public MFragmentAdapter(FragmentManager fm) {
			super(fm);
			initFramgent();
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size(); // 代表页数
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			MFragment mf = (MFragment) object;
			mf.clearView();
			super.destroyItem(container, position, object);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void dataLoad_ICallback() {

	}

}
