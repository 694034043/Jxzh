//
//  CardModelBottomImg
//
//  Created by Administrator on 2017-09-20 18:39:14
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.card;

import android.content.Context;
import com.mdx.framework.adapter.Card;
import android.view.View;

import com.bocop.zyt.item.ModelBottomImg;

public class CardModelBottomImg extends Card<String>{
	public String item;
	
	public CardModelBottomImg() {
    	this.type = com.bocop.zyt.commons.CardIDS.CARDID_MODELBOTTOMIMG;
    }


 	@Override
    public View getView(Context context, View contentView) {
        if (contentView == null) {
            contentView = ModelBottomImg.getView(context, null);;
        }
        ModelBottomImg mModelBottomImg=(ModelBottomImg) contentView.getTag();
//        mModelBottomImg.set(item);
        return contentView;
    }
    
    

}


