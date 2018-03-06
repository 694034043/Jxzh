package com.bocop.zyt.bocop.zyt.gui;


import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.bocop.zyt.jx.base.BaseActivity;

/**
 * Created by ltao on 2017/2/9.
 */

public class BaseFragment extends Fragment {

	protected int floor = 1;

    public void fun_toast(String text){
        Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
    }

    public Handler get_handler(){

        return BaseAct._handler;
    }

    public int getFloor(){
    	return floor;
    }

    public void setFloor(int floor){
    	this.floor = floor;
    }

    public void updateView(){

    }

    public void goBack(){
    	 ((BaseActivity)getActivity()).backMainView();
    }
}
