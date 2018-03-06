//
//  FrgModelTong
//
//  Created by Administrator on 2017-09-18 16:26:19
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.bocop.zyt.frg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_jun_tong_Fragment;
import com.bocop.zyt.bocop.zyt.view.YinJunTongCustomDialog;
import com.bocop.zyt.card.CardModelBanner;
import com.bocop.zyt.card.CardModelCate;
import com.bocop.zyt.card.CardModleBottom;
import com.gjbank.proto.ApisFactory;
import com.item.proto.MModuleIndex;
import com.item.proto.MTopModule;
import com.mdx.framework.Frame;
import com.mdx.framework.adapter.Card;
import com.mdx.framework.adapter.CardAdapter;
import com.mdx.framework.server.api.Son;

import java.util.ArrayList;
import java.util.List;

import static com.bocop.zyt.R.style.dialog;


public class FrgModelTong extends BaseFrg {

    public ListView mMPageListView;
    public ImageView iv_finish;
    public TextView tv_actionbar_title;
    public com.mdx.framework.widget.MImageView iv_bg;
    private MTopModule module;

    private List<Card<?>> datas = new ArrayList<>();

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_model_tong);
        module = (MTopModule) getArguments().getSerializable("data");
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mMPageListView = (ListView) findViewById(R.id.mMPageListView);
        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
        iv_bg = (com.mdx.framework.widget.MImageView) findViewById(R.id.iv_bg);


    }

    public void loaddata() {
        ApisFactory.getApiMModuleIndex().load(getContext(), FrgModelTong.this, "ModuleIndex", module.id);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frame.HANDLES.close("FrgYhtHome");
                getActivity().finish();
            }
        });
        tv_actionbar_title.setText(module.name);
        if (module.code.equals("yjt") || module.code.equals("jyt")) {
            showDialog();
        }
    }

    public void ModuleIndex(MModuleIndex data, Son s) {
        if (data != null && s.getError() == 0) {
            iv_bg.setObj(data.background);
            datas = new ArrayList<>();
            CardModelBanner banner = new CardModelBanner(data);
            datas.add(banner);
            for (int i = 0; i < data.categoryList.size(); i++) {
                CardModelCate cate = new CardModelCate(data.categoryList.get(i), data,data.code);
                datas.add(cate);
            }
            CardModleBottom bottom = new CardModleBottom(data);
            datas.add(bottom);
            mMPageListView.setAdapter(new CardAdapter(getContext(), datas));
        }

    }

    protected boolean getSoldierInfo() {
        SharedPreferences sp = getActivity().getSharedPreferences(LoginUtil.SP_NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean("hasSoldierInfo", false);
    }

    private void showDialog() {
        if (!getSoldierInfo()) {
            final YinJunTongCustomDialog dialog = new YinJunTongCustomDialog(getActivity(), R.style.dialog_custom);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setOnUpListener(new YinJunTongCustomDialog.OnUpListener() {

                @Override
                public void onUp(YinJunTongCustomDialog dialog) {
                    dialog.cancel();
                    updateSoldierInfo();
                    ToastUtils.show(getActivity(), "您的信息已提交", Toast.LENGTH_LONG);
                }
            });
            dialog.setOnCancelListener(new YinJunTongCustomDialog.OnCancelListener() {

                @Override
                public void onCancel(YinJunTongCustomDialog dailog) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }
    }

    protected void updateSoldierInfo() {
        SharedPreferences.Editor editor;
        SharedPreferences sp = getActivity().getSharedPreferences(LoginUtil.SP_NAME,
                Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putBoolean("hasSoldierInfo", true);
        editor.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Frame.HANDLES.close("FrgYhtHome");
    }
}