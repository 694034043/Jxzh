//
//  AdaFuns
//
//  Created by Administrator on 2017-09-18 10:57:18
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.ada;

import java.util.List;

import com.item.proto.MTopModule;
import com.mdx.framework.adapter.MAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;

import com.bocop.zyt.item.Funs;

public class AdaFuns extends MAdapter<MTopModule>{

   public AdaFuns(Context context, List<MTopModule> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        MTopModule item = get(position);
        if (convertView == null) {
            convertView = Funs.getView(getContext(), parent);;
        }
        Funs mFuns=(Funs) convertView.getTag();
        mFuns.set(item);
        return convertView;
    }
}
