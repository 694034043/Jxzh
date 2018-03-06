package com.framewidget.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framewidget.F;
import com.framewidget.R;

/**
 * 顶部 [功能详细描述]
 *
 * @author Administrator
 * @version [2013-11-14 下午4:42:38]
 */
public class Headlayout extends LinearLayout {
    public ImageButton btn_left, btn_right, btn_right_2;

    public TextView tv_title;
    public TextView mTextView_right;
    public TextView mTextView_right_two;
    public ImageView mImageView;
    public RelativeLayout mRelativeLayout;

    public Headlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Headlayout(Context context) {
        super(context);
        init();
    }

    @SuppressLint("NewApi")
    public void init() {
        LayoutInflater f = LayoutInflater.from(getContext());
        View v = f.inflate(R.layout.topbar, this);
        btn_left = (ImageButton) findViewById(R.id.btn_left);
        btn_right = (ImageButton) findViewById(R.id.btn_right);
        btn_right_2 = (ImageButton) findViewById(R.id.btn_right_2);
        tv_title = (TextView) findViewById(R.id.tv_title);
        mTextView_right = (TextView) findViewById(R.id.mTextView_right);
        mTextView_right_two = (TextView) findViewById(R.id.mTextView_right_two);
        mImageView = (ImageView) findViewById(R.id.mImageView);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.mRelativeLayout);
    }

    public TextView getmTextView_right() {
        return mTextView_right;
    }

    public void setmTextView_right(TextView mTextView_right) {
        this.mTextView_right = mTextView_right;
    }

    public RelativeLayout getmRelativeLayout() {
        return mRelativeLayout;
    }

    public void setmRelativeLayout(RelativeLayout mRelativeLayout) {
        this.mRelativeLayout = mRelativeLayout;
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public void setTv_title(TextView tv_title) {
        this.tv_title = tv_title;
    }

    public void setRText(CharSequence s) {
        mTextView_right.setText(s);
        mTextView_right.setVisibility(View.VISIBLE);
    }

    public void setRTextTwo(CharSequence s) {
        mTextView_right_two.setText(s);
        mTextView_right_two.setVisibility(View.VISIBLE);
    }

    /**
     * 返回
     *
     * @param @param act
     * @return void
     * @throws
     * @author Administrator
     * @Title: goBack
     * @Description: TODO
     */
    public void goBack(final Activity act) {
        findViewById(R.id.mLinearLayout_back).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        act.finish();
                    }
                });
        findViewById(R.id.btn_left).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                act.finish();
                F.closeSoftKey(act);
            }
        });
    }

    /**
     * 设置左边按钮监听
     *
     * @param @param c
     * @return void
     * @throws
     * @author Administrator
     * @Title: leftOnclicker
     * @Description: TODO
     */
    public void setLeftOnclicker(OnClickListener c) {
        btn_left.setOnClickListener(c);
        findViewById(R.id.mLinearLayout_back).setOnClickListener(c);
    }

    /**
     * 设置右边按钮监听
     *
     * @param @param c
     * @return void
     * @throws
     * @author Administrator
     * @Title: setRightOnclicker
     * @Description: TODO
     */
    public void setRightOnclicker(OnClickListener c) {
        btn_right.setOnClickListener(c);
        mTextView_right.setOnClickListener(c);
    }

    public void setRightEnable(boolean isEnable) {
        btn_right.setEnabled(isEnable);
        mTextView_right.setEnabled(isEnable);
    }

    public void setRight2Onclicker(OnClickListener c) {
        btn_right_2.setOnClickListener(c);
        mTextView_right_two.setOnClickListener(c);
    }

    /**
     * 设置标题
     *
     * @param @param s
     * @return void
     * @throws
     * @author Administrator
     * @Title: setTitle
     * @Description: TODO
     */
    public void setTitle(String s) {
        tv_title.setText(s);
    }

    /**
     * 设置res
     *
     * @param @param s
     * @return void
     * @throws
     * @author Administrator
     * @Title: setTitle
     * @Description: TODO
     */
    public void setTitleRes(int s) {
        tv_title.setVisibility(View.GONE);
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setBackgroundResource(s);

    }

    /**
     * 设置左边按钮隐藏
     *
     * @param
     * @return void
     * @throws
     * @author Administrator
     * @Title: setLeftGone
     * @Description: TODO
     */
    public void setLeftGone() {
        btn_left.setVisibility(View.GONE);
    }

    /**
     * 设置左边按钮显示
     *
     * @param
     * @return void
     * @throws
     * @author Administrator
     * @Title: setLeftGone
     * @Description: TODO
     */
    public void setLeftShow() {
        btn_left.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右边按钮隐藏
     *
     * @param
     * @return void
     * @throws
     * @author Administrator
     * @Description: TODO
     */
    public void setRightGone() {
        btn_right.setVisibility(View.GONE);
    }

    /**
     * 设置右边按钮显示
     *
     * @param
     * @return void
     * @throws
     * @author Administrator
     * @Description: TODO
     */
    public void setRightShow() {
        btn_right.setVisibility(View.VISIBLE);
    }

    public void setRight2Show() {
        btn_right_2.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左边的按钮的背景
     *
     * @param
     * @return void
     * @throws
     * @author Administrator
     * @Title: setLeftBc
     * @Description: TODO
     */
    public void setLeftBackground(int res) {
        btn_left.setImageResource(res);
        setLeftShow();
    }

    /**
     * 设置右边的按钮的背景
     *
     * @param @param res
     * @return void
     * @throws
     * @author Administrator
     * @Title: setRightBacgroud
     * @Description: TODO
     */
    public void setRightBacgroud(int res) {
        btn_right.setImageResource(res);
        setRightShow();
    }

    public void setRight2Bacgroud(int res) {
        btn_right_2.setImageResource(res);
        setRight2Show();
    }

    /**
     * 返回
     *
     * @param
     * @return void
     * @throws
     * @author Administrator
     * @Title: setGoBack
     * @Description: TODO
     */
    public void setGoBack(final Activity act) {
        findViewById(R.id.mLinearLayout_back).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        act.finish();
                        F.closeSoftKey(act);
                    }
                });
        findViewById(R.id.btn_left).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                act.finish();
                F.closeSoftKey(act);
            }
        });
    }

    public void setBg(int resid) {
        mRelativeLayout.setBackgroundResource(resid);
    }
}
