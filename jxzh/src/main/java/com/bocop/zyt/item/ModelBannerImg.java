//
//  ModelBannerImg
//
//  Created by Administrator on 2017-09-18 17:04:24
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bocop.zyt.R;
import com.item.proto.MFocus;
import com.mdx.framework.widget.MImageView;


public class ModelBannerImg extends BaseItem{
    public MImageView iv_banner;


	@SuppressLint("InflateParams")
    public static View getView(Context context,ViewGroup parent){
	     LayoutInflater flater = LayoutInflater.from(context);
	     View convertView = flater.inflate(R.layout.item_model_banner_img,null);
	     convertView.setTag( new ModelBannerImg(convertView));
	     return convertView;
	}

	public ModelBannerImg(View view){
		this.contentview=view;
		this.context=contentview.getContext();
		initView();
	}
    
    private void initView() {
    	this.contentview.setTag(this);
    	findVMethod();
    }

    private void findVMethod(){
        iv_banner=(MImageView)contentview.findViewById(R.id.iv_banner);


    }

    public void set(MFocus item){
		iv_banner.setObj(item.img);

    }
    
    

}