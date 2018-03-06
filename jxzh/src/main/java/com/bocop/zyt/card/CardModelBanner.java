//
//  CardModelBanner
//
//  Created by Administrator on 2017-09-18 16:48:37
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.card;

import android.content.Context;
import android.view.View;

import com.bocop.zyt.item.ModelBanner;
import com.item.proto.MModuleIndex;
import com.mdx.framework.adapter.Card;

public class CardModelBanner extends Card<MModuleIndex>{
	public MModuleIndex item;
	
	public CardModelBanner(MModuleIndex item) {
    	this.type = com.bocop.zyt.commons.CardIDS.CARDID_MODELBANNER;
        this.item = item;
    }


 	@Override
    public View getView(Context context, View contentView) {
        if (contentView == null) {
            contentView = ModelBanner.getView(context, null);;
        }
        ModelBanner mModelBanner=(ModelBanner) contentView.getTag();
        mModelBanner.set(item);
        return contentView;
    }
    
    

}


