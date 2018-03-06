package com.framewidget.newMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.mdx.framework.widget.MViewPager;

/**
 * 
 */
public class DfMViewPager extends MViewPager {
	public ICallback callback;

	public DfMViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DfMViewPager(Context context) {
		super(context);
	}

	public void setCallback(ICallback callback) {
		this.callback = callback;
	}

}
