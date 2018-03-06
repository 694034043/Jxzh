package com.framewidget;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.framewidget.newMenu.ICallback;
import com.mdx.framework.activity.MActivity;
import com.mdx.framework.activity.MFragment;
import com.mdx.framework.widget.MPageListView;

public class Test2 extends MFragment implements ICallback {

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.test1);

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void dataLoad_ICallback() {
	}

}
