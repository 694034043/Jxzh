package com.bocop.zyt.bocop.zyt.model;


import com.bocop.zyt.bocop.zyt.gui.BaseFragment;

import java.util.ArrayList;


public class Constant {
	public static ArrayList<BaseFragment> fragmentList = new ArrayList<>();

	public static ArrayList<BaseFragment> getFragmentList() {
		return fragmentList;
	}

	public static void setFragmentList(ArrayList<BaseFragment> fragmentList) {
		Constant.fragmentList = fragmentList;
	}
	
	
}
