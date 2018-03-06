//
//  CardModelBannerImg
//
//  Created by Administrator on 2017-09-18 17:04:24
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.card;

import android.content.Context;
import com.mdx.framework.adapter.Card;
import android.view.View;

import com.bocop.zyt.item.ModelBannerImg;

public class CardModelBannerImg extends Card<String>{
	public String item;
	
	public CardModelBannerImg() {
    	this.type = com.bocop.zyt.commons.CardIDS.CARDID_MODELBANNERIMG;
    }


 	@Override
    public View getView(Context context, View contentView) {
        if (contentView == null) {
            contentView = ModelBannerImg.getView(context, null);;
        }
        ModelBannerImg mModelBannerImg=(ModelBannerImg) contentView.getTag();
//        mModelBannerImg.set(item);
        return contentView;
    }
    
    

}


