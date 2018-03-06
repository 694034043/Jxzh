//
//  CardModelCate
//
//  Created by Administrator on 2017-09-18 16:48:37
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.card;

import android.content.Context;
import android.view.View;

import com.bocop.zyt.item.ModelCate;
import com.item.proto.MCategory;
import com.item.proto.MModuleIndex;
import com.mdx.framework.adapter.Card;

import static com.bocop.zyt.F.code;

public class CardModelCate extends Card<MCategory>{
	public MCategory item;
	public MModuleIndex data;
    String code;

	public CardModelCate(MCategory item, MModuleIndex data,String code) {
    	this.type = com.bocop.zyt.commons.CardIDS.CARDID_MODELCATE;
        this.item=item;
        this.data=data;
        this.code=code;
    }


 	@Override
    public View getView(Context context, View contentView) {
        if (contentView == null) {
            contentView = ModelCate.getView(context, null);;
        }
        ModelCate mModelCate=(ModelCate) contentView.getTag();
        mModelCate.set(item,data,code);
        return contentView;
    }
    
    

}


