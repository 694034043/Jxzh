/**
 *
 */
package com.bocop.zyt.bocop.jxplatform.view;


import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bocop.zyt.bocop.jxplatform.activity.MainActivity;
import com.bocop.zyt.jx.base.BaseActivity;

/**
 * @author luoyang  E-mail: luoyang8714@163.com
 * @version 创建时间：2016-10-28 上午10:29:35
 * 类说明
 */

/**
 * @author zhongye
 *
 */
public class HomepageButton extends ImageView {

    /**
     * @param context
     */
    public HomepageButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public HomepageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public HomepageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean performClick() {
        // TODO Auto-generated method stub
//		((BaseActivity)getContext()).getActivityManager().finishAllWithoutMain();
//		Intent intent = new Intent(getContext(),MainActivity.class);
        ((BaseActivity) getContext()).finish();
        return super.performClick();
    }

}
