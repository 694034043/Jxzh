package com.bocop.zyt.bocop.zyt.gui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.bocop.zyt.bocop.zyt.event.BaseEvent;
import com.bocop.zyt.bocop.zyt.event.FinishEvent;
import com.bocop.zyt.bocop.zyt.view.SwipeBackHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ltao on 2017/2/8.
 */

public abstract class BaseAct extends FragmentActivity implements BaseGui/*,SwipeBackHelper.SlideBackManager*/ {

    public final BaseAct _act;

    public BaseAct() {
        _act = this;
    }

    public static Handler _handler = new Handler();
    public static Gson _gson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {

        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    public abstract void init_widget();


    /**
     * eventBus 回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event_call(BaseEvent e) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event_call(FinishEvent e) {

        finish();
    }

    public void finsh_all_acts() {
        EventBus.getDefault().post(new FinishEvent());
    }

    /**
     * 全屏
     */
    public void full_screen() {

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
    }


    public void fun_toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void fun_back_press(View v) {
        finish();
    }
    
    private SwipeBackHelper mSwipeBackHelper;

	/*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mSwipeBackHelper == null) {
            mSwipeBackHelper = new SwipeBackHelper(this);
        }
        return mSwipeBackHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    public Activity getSlideActivity() {
        return this;
    }

    @Override
    public boolean supportSlideBack() {
        return true;
    }

    @Override
    public boolean canBeSlideBack() {
        return true;
    }*/

    @Override
    public void finish() {
        if(mSwipeBackHelper != null) {
            mSwipeBackHelper.finishSwipeImmediately();
            mSwipeBackHelper = null;
        }
        super.finish();
    }
}
