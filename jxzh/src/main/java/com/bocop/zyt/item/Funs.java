//
//  Funs
//
//  Created by Administrator on 2017-09-18 10:57:18
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.bocop.zyt.item;

import com.bocop.zyt.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;

import com.item.proto.MTopModule;
import com.mdx.framework.utility.Helper;
import com.mdx.framework.widget.MImageView;

import static com.boc.bocop.sdk.BOCOPPayApi.getContext;
import static com.bocop.zyt.R.id.iv;


public class Funs extends BaseItem {
    public MImageView iv_fun;


    @SuppressLint("InflateParams")
    public static View getView(Context context, ViewGroup parent) {
        LayoutInflater flater = LayoutInflater.from(context);
        View convertView = flater.inflate(R.layout.item_funs, null);
        convertView.setTag(new Funs(convertView));
        return convertView;
    }

    public Funs(View view) {
        this.contentview = view;
        this.context = contentview.getContext();
        initView();
    }

    private void initView() {
        this.contentview.setTag(this);
        findVMethod();
    }

    private void findVMethod() {
        iv_fun = (MImageView) contentview.findViewById(R.id.iv_fun);


    }

    public void set(final MTopModule item) {
        iv_fun.setObj(item.img);

    }


}