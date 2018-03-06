package com.framewidget;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.framewidget.newMenu.SlidingFragment.MFragmentAdapter;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.MActivity;
import com.mdx.framework.activity.MFragment;
import com.mdx.framework.widget.MViewPager;

public class Main2Activity extends MActivity {
	private MViewPager frame_content;
	private RadioGroup mRadioGroup;
	protected ArrayList<MFragment> fragments = new ArrayList<MFragment>();

	@SuppressLint("NewApi")
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.activity_main_ce);
		Frame.init(this);
		frame_content = (MViewPager) findViewById(R.id.frame_content);
		mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);

		fragments.add(new Test2());
		fragments.add(new Test2());
		frame_content.setAdapter(new MFragmentAdapter(
				getSupportFragmentManager()));
	}

	public class MFragmentAdapter extends FragmentPagerAdapter {
		public MFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			MFragment mf = (MFragment) object;
			mf.clearView();
			super.destroyItem(container, position, object);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
