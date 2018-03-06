package com.framewidget;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.webkit.WebIconDatabase.IconListener;
import android.widget.TextView;
import android.widget.Toast;

import com.framewidget.newMenu.ICallback;
import com.mdx.framework.activity.MActivity;
import com.mdx.framework.activity.MFragment;

public class Test3 extends MFragment {
	TextView mTextView;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.test1);
		((TextView) findViewById(R.id.mTextView)).setText("333333333333333");
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
