//
//  AdaModelBanner
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

import com.bocop.zyt.item.ModelBanner;

public class AdaModelBanner extends MAdapter<String>{

   public AdaModelBanner(Context context, List<String> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        String item = get(position);
        if (convertView == null) {
            convertView = ModelBanner.getView(getContext(), parent);;
        }
        ModelBanner mModelBanner=(ModelBanner) convertView.getTag();
//        mModelBanner.set(item);
        return convertView;
    }
}
