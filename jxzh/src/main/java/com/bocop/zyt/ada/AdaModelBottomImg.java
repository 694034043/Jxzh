//
//  AdaModelBottomImg
//
//  Created by Administrator on 2017-09-20 18:39:14
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.ada;

import java.util.List;

import com.item.proto.MFocus;
import com.mdx.framework.adapter.MAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;

import com.bocop.zyt.item.ModelBottomImg;

public class AdaModelBottomImg extends MAdapter<MFocus>{

   public AdaModelBottomImg(Context context, List<MFocus> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        MFocus item = get(position);
        if (convertView == null) {
            convertView = ModelBottomImg.getView(getContext(), parent);;
        }
        ModelBottomImg mModelBottomImg=(ModelBottomImg) convertView.getTag();
        mModelBottomImg.set(item);
        return convertView;
    }
}
