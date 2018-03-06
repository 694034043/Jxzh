//
//  AdaModelBannerImg
//
//  Created by Administrator on 2017-09-18 17:04:24
//  Copyright (c) Administrator All rights reserved.


/**
   
*/

package com.bocop.zyt.ada;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bocop.zyt.item.ModelBannerImg;
import com.item.proto.MFocus;
import com.mdx.framework.adapter.MAdapter;

import java.util.List;

public class AdaModelBannerImg extends MAdapter<MFocus>{

   public AdaModelBannerImg(Context context, List<MFocus> list) {
        super(context, list);
    }


 	@Override
    public View getview(int position, View convertView, ViewGroup parent) {
        MFocus item = get(position);
        if (convertView == null) {
            convertView = ModelBannerImg.getView(getContext(), parent);;
        }
        ModelBannerImg mModelBannerImg=(ModelBannerImg) convertView.getTag();
        mModelBannerImg.set(item);
        return convertView;
    }
}
