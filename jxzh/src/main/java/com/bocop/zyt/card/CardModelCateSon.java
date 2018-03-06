//
//  CardModelCateSon
//
//  Created by Administrator on 2017-09-18 16:48:37
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.card;

import android.content.Context;
import com.mdx.framework.adapter.Card;
import android.view.View;

import com.bocop.zyt.item.ModelCateSon;

public class CardModelCateSon extends Card<String>{
	public String item;
	
	public CardModelCateSon() {
    	this.type = com.bocop.zyt.commons.CardIDS.CARDID_MODELCATESON;
    }


 	@Override
    public View getView(Context context, View contentView) {
        if (contentView == null) {
            contentView = ModelCateSon.getView(context, null);;
        }
        ModelCateSon mModelCateSon=(ModelCateSon) contentView.getTag();
//        mModelCateSon.set(item);
        return contentView;
    }
    
    

}


