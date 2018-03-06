//
//  ModelBanner
//
//  Created by Administrator on 2017-09-18 16:48:37
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
import com.bocop.zyt.ada.AdaModelBannerImg;
import com.item.proto.MModuleIndex;
import com.mdx.framework.widget.banner.CirleCurr;



public class ModelBanner extends BaseItem{
    public CirleCurr mCirleCurr;


	@SuppressLint("InflateParams")
    public static View getView(Context context,ViewGroup parent){
	     LayoutInflater flater = LayoutInflater.from(context);
	     View convertView = flater.inflate(R.layout.item_model_banner,null);
	     convertView.setTag( new ModelBanner(convertView));
	     return convertView;
	}

	public ModelBanner(View view){
		this.contentview=view;
		this.context=contentview.getContext();
		initView();
	}
    
    private void initView() {
    	this.contentview.setTag(this);
    	findVMethod();
    }

    private void findVMethod(){
        mCirleCurr=(CirleCurr)contentview.findViewById(R.id.mCirleCurr);


    }

    public void set(MModuleIndex item){
		mCirleCurr.setAdapter(new AdaModelBannerImg(context,item.topFocusList));
    }
    
    

}