//
//  FrgYhtHome
//
//  Created by Administrator on 2017-09-18 13:15:44
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.bocop.zyt.frg;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.fragment.PersonalFragment;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Ke_dai_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_ci_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_wen_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_yan_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_yao_tong_Fragment;
import com.bocop.zyt.bocop.zyt.gui.MainAct_Yin_zhen_tong_ytzs_Fragment;
import com.bocop.zyt.fmodule.utils.IHttpClient;
import com.framewidget.newMenu.OnCheckChange;
import com.framewidget.newMenu.OnPageSelset;
import com.framewidget.newMenu.SlidingFragment;
import com.gjbank.proto.ApisFactory;
import com.item.proto.MModuleIndex;
import com.item.proto.MTopModule;
import com.mdx.framework.config.BaseConfig;
import com.mdx.framework.server.api.Son;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class FrgYhtHome extends BaseFrg implements OnCheckChange, OnPageSelset {

    public LinearLayout mLinearLayout_content;
    public FragmentManager fragmentManager;
    public SlidingFragment mSlidingFragment;
    private MTopModule module;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_yht_home);
        module = (MTopModule) getActivity().getIntent().getSerializableExtra("data");
        initView();
        loaddata();
    }

    private void initView() {
        findVMethod();
    }

    private void findVMethod() {
        mLinearLayout_content = (LinearLayout) findViewById(R.id.mLinearLayout_content);


    }

    public void loaddata() {
        mSlidingFragment = new SlidingFragment(this);
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.mLinearLayout_content, mSlidingFragment);
//        fragmentTransaction
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);// 璁剧疆鍔ㄧ敾鏁堟灉
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if (module.code.equals("yct")) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", module);
            MainAct_Yin_ci_tong_Fragment modelTong = new MainAct_Yin_ci_tong_Fragment();
            modelTong.setArguments(bundle);
            mSlidingFragment.addContentView(modelTong, "首页",
                    R.drawable.btn_checked_sy);
        } else if (module.code.equals("yyt")) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", module);
            MainAct_Yin_yan_tong_Fragment modelTong = new MainAct_Yin_yan_tong_Fragment();
            modelTong.setArguments(bundle);
            mSlidingFragment.addContentView(modelTong, "首页",
                    R.drawable.btn_checked_sy);
        } else if (module.code.equals("yyaot")) {
            mSlidingFragment.addContentView(new MainAct_Yin_yao_tong_Fragment(), "首页",
                    R.drawable.btn_checked_sy);
        } else if (module.code.equals("yztyt")) {
            mSlidingFragment.addContentView(new MainAct_Yin_zhen_tong_ytzs_Fragment(), "首页",
                    R.drawable.btn_checked_sy);
        } else if (module.code.equals("kdt")) {
            mSlidingFragment.addContentView(new MainAct_Ke_dai_tong_Fragment(), "首页",
                    R.drawable.btn_checked_sy);
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", module);
            FrgModelTong modelTong = new FrgModelTong();
            modelTong.setArguments(bundle);
            mSlidingFragment.addContentView(modelTong, "首页",
                    R.drawable.btn_checked_sy);
        }

        mSlidingFragment.addContentView(new PersonalFragment(), "个人",
                R.drawable.btn_checked_gr);
        mSlidingFragment.setMode(0);


    }



    @Override
    public void onCheckedChanged(int id, int position) {

    }

    @Override
    public void OnPageSelseTed(int position) {

    }
}