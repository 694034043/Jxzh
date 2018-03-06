package com.framewidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BusinessModel extends LinearLayout implements OnClickListener {
	TextView mTextView;
	Button mButton;
	String accessToken;
	String url, title, zhanghao;

	public BusinessModel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BusinessModel(Context context) {
		super(context);
		init();
	}

	public void init() {
		LayoutInflater f = LayoutInflater.from(getContext());
		f.inflate(R.layout.test5, this);
	}

	@Override
	public void onClick(View v) {

	}

}
