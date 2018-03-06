//
//  ModleBottom
//
//  Created by Administrator on 2017-09-20 18:27:35
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.item;

import com.bocop.zyt.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;

import com.bocop.zyt.ada.AdaModelBannerImg;
import com.bocop.zyt.ada.AdaModelBottomImg;
import com.bocop.zyt.bocop.jxplatform.view.MyGridView;
import com.item.proto.MModuleIndex;


public class ModleBottom extends BaseItem{
    public MyGridView gv_banner;


	@SuppressLint("InflateParams")
    public static View getView(Context context,ViewGroup parent){
	     LayoutInflater flater = LayoutInflater.from(context);
	     View convertView = flater.inflate(R.layout.item_modle_bottom,null);
	     convertView.setTag( new ModleBottom(convertView));
	     return convertView;
	}

	public ModleBottom(View view){
		this.contentview=view;
		this.context=contentview.getContext();
		initView();
	}
    
    private void initView() {
    	this.contentview.setTag(this);
    	findVMethod();
    }

    private void findVMethod(){
        gv_banner=(MyGridView)contentview.findViewById(R.id.gv_banner);


    }

    public void set(MModuleIndex item){
		switch (item.line){
			case "1":
				gv_banner.setNumColumns(2);
				break;
			case "2":
				gv_banner.setNumColumns(1);
				break;
		}
		gv_banner.setAdapter(new AdaModelBottomImg(context,item.bottomFocusList));

    }
    
    

}