//
//  ModelCate
//
//  Created by Administrator on 2017-09-18 16:48:37
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.bocop.zyt.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.ada.AdaModelCateSon;
import com.bocop.zyt.bocop.jxplatform.view.MyGridView;
import com.item.proto.MCategory;
import com.item.proto.MModuleIndex;


public class ModelCate extends BaseItem {
    public TextView tv_cate;
    public MyGridView gv_cate;
    public com.mdx.framework.widget.MImageView iv_bg;


    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_model_cate, null);
        convertView.setTag(new ModelCate(convertView));
        return convertView;
    }

    public ModelCate(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        tv_cate = (TextView) contentview.findViewById(R.id.tv_cate);
        gv_cate = (MyGridView) contentview.findViewById(R.id.gv_cate);
        iv_bg = (com.mdx.framework.widget.MImageView) contentview.findViewById(R.id.iv_bg);


    }

    public void set(MCategory item, MModuleIndex data, String code) {
        tv_cate.setText(item.name);
        if (!TextUtils.isEmpty(data.fontColor)) {
            tv_cate.setTextColor(Color.parseColor(data.fontColor));
        }
        if (!TextUtils.isEmpty(data.fontSize)) {
            tv_cate.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(data.fontSize));
        }
        if (!TextUtils.isEmpty(data.groupBg)) {
            iv_bg.setObj(data.groupBg);
        } else {
            if (!TextUtils.isEmpty(data.groupColor)) {
                tv_cate.setBackgroundColor(Color.parseColor(data.groupColor));
            }
        }


        if (!TextUtils.isEmpty(data.groupNum)) {
            gv_cate.setNumColumns(Integer.parseInt(data.groupNum));
        }

        gv_cate.setAdapter(new AdaModelCateSon(context, item.iconList, code));


    }


}