//
//  CardFuns
//
//  Created by Administrator on 2017-09-18 10:57:18
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.card;

import android.content.Context;
import com.mdx.framework.adapter.Card;
import android.view.View;

import com.bocop.zyt.item.Funs;

public class CardFuns extends Card<String>{
	public String item;
	
	public CardFuns() {
    	this.type = com.bocop.zyt.commons.CardIDS.CARDID_FUNS;
    }


 	@Override
    public View getView(Context context, View contentView) {
        if (contentView == null) {
            contentView = Funs.getView(context, null);;
        }
        Funs mFuns=(Funs) contentView.getTag();
//        mFuns.set(item);
        return contentView;
    }
    
    

}


