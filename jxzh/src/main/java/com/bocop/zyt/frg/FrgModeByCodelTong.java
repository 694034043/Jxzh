//
//  FrgModelTong
//
//  Created by Administrator on 2017-09-18 16:26:19
//  Copyright (c) Administrator All rights reserved.


/**

 */

package com.bocop.zyt.frg;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.broadcast.PlayerMusicBroadCastReciver;
import com.bocop.zyt.bocop.zyt.gui.fragment.CiCultureFragment;
import com.bocop.zyt.bocop.zyt.utils.MediaPlayerUtils;
import com.bocop.zyt.card.CardModelBanner;
import com.bocop.zyt.card.CardModelCate;
import com.bocop.zyt.card.CardModleBottom;
import com.gjbank.proto.ApisFactory;
import com.item.proto.MModuleIndex;
import com.mdx.framework.adapter.Card;
import com.mdx.framework.adapter.CardAdapter;
import com.mdx.framework.server.api.Son;

import java.util.ArrayList;
import java.util.List;

import static com.bocop.zyt.R.drawable.shape_base_yct_action_bar;
import static com.bocop.zyt.R.id.iv_music;


public class FrgModeByCodelTong extends BaseFrg {

    public ListView mMPageListView;
    public ImageView iv_finish;
    public TextView tv_actionbar_title;
    public com.mdx.framework.widget.MImageView iv_bg;
    public RelativeLayout rel_bg;
    public ImageView iv_music;

    private List<Card<?>> datas = new ArrayList<>();
    private String code;
    private String name;
    private MediaPlayer mPlayer;

    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frg_model_by_code_tong);
        code = getActivity().getIntent().getStringExtra("code");
        name = getActivity().getIntent().getStringExtra("name");
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
        rel_bg = (RelativeLayout) findViewById(R.id.rel_bg);
        iv_music = (ImageView) findViewById(R.id.iv_music);


    }

    public void loaddata() {
        ApisFactory.getApiMModuleIndexByCode().load(getContext(), FrgModeByCodelTong.this, "ModuleIndex", code);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        tv_actionbar_title.setText(name);
        if (code.equals("yct1")||code.equals("yct2")||code.equals("yct3")){
            iv_bg.setScaleType(ImageView.ScaleType.FIT_CENTER);
            rel_bg.setBackgroundResource(R.drawable.shape_base_yct_action_bar);
            iv_music.setVisibility(View.GONE);
        }else {
            iv_bg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            rel_bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
            iv_music.setVisibility(View.GONE);
        }
        mPlayer = MediaPlayerUtils.create();
        if(!mPlayer.isPlaying()){
            iv_music.setImageResource(R.drawable.icon_music_off);
        }
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);
        getActivity().registerReceiver(new MyPlayerMusicBroadCastReciver(), myIntentFilter);
        iv_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayer.isPlaying()){
                    mPlayer.pause();
                    sendPlayerMusicBroadCast(2);
                    iv_music.setImageResource(R.drawable.icon_music_off);
                }else{
                    mPlayer.start();
                    sendPlayerMusicBroadCast(1);
                    iv_music.setImageResource(R.drawable.icon_music_on);
                }
            }
        });
    }
    public void sendPlayerMusicBroadCast(int state){
        Intent intent = new Intent();  //Itent就是我们要发送的内容
        intent.putExtra("playState", state);
        intent.setAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
        getActivity().sendBroadcast(intent);   //发送广播
    }
    public class MyPlayerMusicBroadCastReciver extends PlayerMusicBroadCastReciver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            super.onReceive(context, intent);
            int state = intent.getIntExtra("playState", -1);
            if(state == 1){
                iv_music.setImageResource(R.drawable.icon_music_on);
            }else if(state == 2){
                iv_music.setImageResource(R.drawable.icon_music_off);
            }
        }

    }

    public void ModuleIndex(MModuleIndex data, Son s) {
        if (data != null && s.getError() == 0) {
            iv_bg.setObj(data.background);
            datas = new ArrayList<>();
            CardModelBanner banner = new CardModelBanner(data);
            datas.add(banner);
            for (int i = 0; i < data.categoryList.size(); i++) {
                CardModelCate cate = new CardModelCate(data.categoryList.get(i),data,data.code);
                datas.add(cate);
            }
            CardModleBottom bottom = new CardModleBottom(data);
            datas.add(bottom);
            mMPageListView.setAdapter(new CardAdapter(getContext(), datas));
        }

    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(mPlayer.isPlaying()){
            iv_music.setImageResource(R.drawable.icon_music_on);
        }else{
            iv_music.setImageResource(R.drawable.icon_music_off);
        }
    }
}