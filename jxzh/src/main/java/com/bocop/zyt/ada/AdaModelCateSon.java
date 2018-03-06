//
//  AdaModelCateSon
//
//  Created by Administrator on 2017-09-18 16:48:37
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.bocop.zyt.ada;

import java.util.List;

import com.item.proto.MFunctionIcon;
import com.mdx.framework.adapter.MAdapter;

import android.content.Context;
import android.view.ViewGroup;
import android.view.View;

import com.bocop.zyt.item.ModelCateSon;

public class AdaModelCateSon extends MAdapter<MFunctionIcon> {
    String code;

    public AdaModelCateSon(Context context, List<MFunctionIcon> list, String code) {
        super(context, list);
        this.code = code;
    }


    @Override
    public View getview(int position, View convertView, ViewGroup parent) {
        MFunctionIcon item = get(position);
        if (convertView == null) {
            convertView = ModelCateSon.getView(getContext(), parent);
            ;
        }
        ModelCateSon mModelCateSon = (ModelCateSon) convertView.getTag();
        mModelCateSon.set(item, code);
        return convertView;
    }
}
