//
//  AdaModelCate
//
//  Created by Administrator on 2017-09-18 16:48:37
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.ada;

import java.util.List;
import com.mdx.framework.adapter.MAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;

import com.bocop.zyt.item.ModelCate;

public class AdaModelCate extends MAdapter<String>{

   public AdaModelCate(Context context, List<String> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = ModelCate.getView(getContext(), parent);;
        }
        ModelCate mModelCate=(ModelCate) convertView.getTag();
//        mModelCate.set(item);
        return convertView;
    }
}
