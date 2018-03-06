//
//  BaseItem
//
//  Created by Administrator on 2017-09-18 10:57:18
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.item;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class BaseItem implements OnClickListener {
	protected Context context;
	protected View contentview;

	@Override
	public void onClick(View v) {

	}

	public View findViewById(int id) {
         return this.contentview.findViewById(id);
    }

}

