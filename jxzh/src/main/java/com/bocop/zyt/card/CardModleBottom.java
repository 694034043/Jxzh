//
//  CardModleBottom
//
//  Created by Administrator on 2017-09-20 18:27:35
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.card;

import android.content.Context;

import com.item.proto.MModuleIndex;
import com.mdx.framework.adapter.Card;
import android.view.View;

import com.bocop.zyt.item.ModleBottom;

public class CardModleBottom extends Card<MModuleIndex>{
	public MModuleIndex item;
	
	public CardModleBottom(MModuleIndex item) {
    	this.type = com.bocop.zyt.commons.CardIDS.CARDID_MODLEBOTTOM;
        this.item=item;
    }


 	@Override
    public View getView(Context context, View contentView) {
        if (contentView == null) {
            contentView = ModleBottom.getView(context, null);;
        }
        ModleBottom mModleBottom=(ModleBottom) contentView.getTag();
        mModleBottom.set(item);
        return contentView;
    }
    
    

}


