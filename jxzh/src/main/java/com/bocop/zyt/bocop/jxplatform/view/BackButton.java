package com.bocop.zyt.bocop.jxplatform.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bocop.zyt.jx.base.BaseActivity;

/**
 * 自定义返回按钮
 * @author llc
 *
 */
public class BackButton extends ImageView{

	public BackButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
			((BaseActivity)getContext()).getActivityManager().backFinish();
			return super.performClick();
		
	}
	
	

}
