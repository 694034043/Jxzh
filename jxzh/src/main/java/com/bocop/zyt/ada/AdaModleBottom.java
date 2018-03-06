//
//  AdaModleBottom
//
//  Created by Administrator on 2017-09-20 18:27:35
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.ada;

import java.util.List;
import com.mdx.framework.adapter.MAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;

import com.bocop.zyt.item.ModleBottom;

public class AdaModleBottom extends MAdapter<String>{

   public AdaModleBottom(Context context, List<String> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = ModleBottom.getView(getContext(), parent);;
        }
        ModleBottom mModleBottom=(ModleBottom) convertView.getTag();
//        mModleBottom.set(item);
        return convertView;
    }
}
