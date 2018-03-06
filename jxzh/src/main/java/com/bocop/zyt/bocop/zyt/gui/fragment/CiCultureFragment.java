package com.bocop.zyt.bocop.zyt.gui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.broadcast.PlayerMusicBroadCastReciver;
import com.bocop.zyt.bocop.zyt.gui.BaseFragment;
import com.bocop.zyt.bocop.zyt.gui.XWebAct;
import com.bocop.zyt.bocop.zyt.utils.MediaPlayerUtils;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;


public class CiCultureFragment extends BaseFragment implements OnClickListener{
	
	private View v;
	private ImageView iv_top_ads;
	private TextView tv_actionbar_title;
	private ImageView iv_bg_logo;
	private ImageView iv_music;
	private MediaPlayer mPlayer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.act_ci_qian_nian_ci_du, null);
		init_widget();
		return v;
	}
	
	public void init_widget() {
		// TODO Auto-generated method stub
		tv_actionbar_title=(TextView)v.findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText("千年瓷都");
		iv_top_ads=(ImageView)v.findViewById(R.id.iv_top_ads);
		FImageloader.load_by_resid_fit_src(getActivity(), R.drawable.bg_pic_act_main_yin_ci_tong_fgt_qian_nian_ci_du, iv_top_ads);
		
		iv_bg_logo = (ImageView) v.findViewById(R.id.iv_bg_logo);
		iv_music = (ImageView) v.findViewById(R.id.iv_music);
		iv_music.setOnClickListener(this);
		mPlayer = MediaPlayerUtils.create();
		if(!mPlayer.isPlaying()){
			iv_music.setImageResource(R.drawable.icon_music_off);
		}
		IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);
        getActivity().registerReceiver(new MyPlayerMusicBroadCastReciver(), myIntentFilter);
		int[] sc = ScreenUtil.get_screen_size(getActivity());
		int w1 = sc[0]- DisplayUtil.dip2px(getActivity(), 70);
		int h1 = w1;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		iv_bg_logo.setLayoutParams(p);
		
		
		v.findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_04).setOnClickListener(this);
		
		
		v.findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_04).setOnClickListener(this);
		
//		findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
//		findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
		
		
		v.findViewById(R.id.ll_item_04_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_04_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_04_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_04_01_04).setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.ll_item_01_01_01:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Du_Ci_Ye_Wen_Hua, "瓷业文脉", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_02:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Chang_Nan_Gu_Zheng, "昌南古镇", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_03:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Du_Li_Shi_Ren_Wu, "历史人物", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_04:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Du_Ci_Yi_Gong_Xu, "瓷艺工序", R.drawable.shape_base_yct_action_bar);
			break;
		}
			
		case R.id.ll_item_02_01_01:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Du_Tao_Ci_Ming_Jia, "陶瓷名家", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_02_01_02:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Du_Ming_Ci_Jian_Shang, "名瓷鉴赏", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_02_01_03:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Gu_Ci_Feng_Yun, "古瓷风韵", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_02_01_04:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Wen_Chuang_tao_ci, "文创陶瓷", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_04_01_01:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Du_Lv_You_Jing_Dian, "VR看瓷都", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_04_01_02:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Du_Lv_You_Jing_Dian_2, "VR看中行",R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_04_01_03:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Tao_Ci_Shi_Chang, "陶瓷市场", R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_04_01_04:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Yan_Xue_You_Wan, "研学游玩", R.drawable.shape_base_yct_action_bar);
			break;
		}
		/*case R.id.ll_item_04_01_04:{
			XWebAct.startAct(getActivity(), IURL.Bank_CI_Qian_Nian_Ci_Du_Ming_You_Te_Chan, "名优特产", R.drawable.shape_base_yct_action_bar);
			break;
		}*/
		case R.id.iv_music:
			if(mPlayer.isPlaying()){
				mPlayer.pause();
				sendPlayerMusicBroadCast(2);
				iv_music.setImageResource(R.drawable.icon_music_off);
			}else{
				mPlayer.start();
				sendPlayerMusicBroadCast(1);
				iv_music.setImageResource(R.drawable.icon_music_on);
			}
			break;

		default:
			break;
		}
	}
	
	public void sendPlayerMusicBroadCast(int state){
		Intent intent = new Intent();  //Itent就是我们要发送的内容
        intent.putExtra("playState", state);  
        intent.setAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
        getActivity().sendBroadcast(intent);   //发送广播
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
