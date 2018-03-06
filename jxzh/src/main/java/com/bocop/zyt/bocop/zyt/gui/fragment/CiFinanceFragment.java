package com.bocop.zyt.bocop.zyt.gui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.HuiDuiTongActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragmentHelper;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.xms.activity.XmsMainActivity;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.bocop.zyt.broadcast.PlayerMusicBroadCastReciver;
import com.bocop.zyt.bocop.zyt.gui.BaseFragment;
import com.bocop.zyt.bocop.zyt.gui.XWebAct;
import com.bocop.zyt.bocop.zyt.utils.MediaPlayerUtils;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class CiFinanceFragment extends BaseFragment implements OnClickListener{

	private View v;
	private ImageView iv_top_ads;
	private TextView tv_actionbar_title;
	private ImageView iv_bg_logo;
	private ImageView iv_music;
	private MediaPlayer mPlayer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.act_ci_jing_rong, null);
		init_widget();
		return v;
	}
	
	public void init_widget() {
		tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setText("金融陶瓷");
		iv_top_ads = (ImageView) v.findViewById(R.id.iv_top_ads);
		FImageloader.load_by_resid_fit_src(getActivity(), R.drawable.bg_pic_act_main_yin_ci_tong_fgt_tao_ci_jing_rong, iv_top_ads);

		iv_bg_logo = (ImageView) v.findViewById(R.id.iv_bg_logo);
		int[] sc = ScreenUtil.get_screen_size(getActivity());
		int w1 = sc[0]- DisplayUtil.dip2px(getActivity(), 70);
		int h1 = w1;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		iv_bg_logo.setLayoutParams(p);
		iv_music = (ImageView) v.findViewById(R.id.iv_music);
		iv_music.setOnClickListener(this);
		mPlayer = MediaPlayerUtils.create();
		if(!mPlayer.isPlaying()){
			iv_music.setImageResource(R.drawable.icon_music_off);
		}
		IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);
        getActivity().registerReceiver(new MyPlayerMusicBroadCastReciver(), myIntentFilter);

		v.findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_01_01_04).setOnClickListener(this);

		v.findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_02_01_04).setOnClickListener(this);

		v.findViewById(R.id.ll_item_03_01_01).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_02).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_03).setOnClickListener(this);
		v.findViewById(R.id.ll_item_03_01_04).setOnClickListener(this);

	}
	
	public class MyPlayerMusicBroadCastReciver extends PlayerMusicBroadCastReciver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			super.onReceive(context, intent);
			int state = intent.getIntExtra("playState", -1);
			if(state == 1){
				iv_music.setImageResource(R.drawable.icon_music_on);
			}else{
				iv_music.setImageResource(R.drawable.icon_music_off);
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.ll_item_01_01_01: {
			WebActivity.startAct(getActivity(),IURL.Ci_kai_hu_bao, "开户宝",R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_02: {
			WebActivity.startAct(getActivity(), IURL.Ci_kuai_dai_bao, "快贷宝",R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_03: {
			WebActivity.startAct(getActivity(), IURL.Ci_chu_kou_bao, "出口宝",R.drawable.shape_base_yct_action_bar);
			break;
		}
		case R.id.ll_item_01_01_04: {
			WebActivity.startAct(getActivity(), IURL.Ci_bao_han_bao, "保函宝",R.drawable.shape_base_yct_action_bar);
			break;
		}

		case R.id.ll_item_02_01_01: {
			
//			个贷通
			LoginHelper.get_instance(getActivity()).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
            		bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_CI_XIAO_DAI_BAO);
            		Intent intent = new Intent(getActivity(), TrainsActivity.class);
            		intent.putExtras(bundle);
            		startActivity(intent);
					//ContentDisplayAct.startActForGeDaiBao(getActivity());
//					Bundle bundle = new Bundle();
//					bundle.putInt("PRO_FLAG", 5);
//					// Intent intent = new Intent(baseActivity,
//					// LoanMainActivity.class);
//					Intent intent = new Intent(_act, TrainsActivity.class);
//					intent.putExtras(bundle);
//					startActivity(intent);
					
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_02_01_02: {
			// 创业通
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
            		bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_CI_CHUANG_DAI_BAO);
            		Intent intent = new Intent(getActivity(), TrainsActivity.class);
            		intent.putExtras(bundle);
            		startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			//ContentDisplayAct.startActForChuangYeBao(getActivity());
//			Bundle bundle = new Bundle();
//			bundle.putInt("PRO_FLAG", 6);
//			 Intent intent = new Intent(baseActivity, LoanMainActivity.class);
//			Intent intent = new Intent(this, TrainsActivity.class);
//			intent.putExtras(bundle);
//			startActivity(intent);
			break;
		}
		case R.id.ll_item_02_01_03: {

			/*// 购汇通
			LoginHelper.get_instance(getActivity()).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putString("flag", "1");
					Intent intent = new Intent(getActivity(), JIEHUIActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});*/
			break;
		}
		case R.id.ll_item_02_01_04: {

			/*// 售汇通
			LoginHelper.get_instance(getActivity()).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putString("flag", "0");
					Intent intent = new Intent(getActivity(), JIEHUIActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});*/
			
			//汇兑通
            LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
                @Override
                public void suc() {
                	Bundle bundle = new Bundle();
                	bundle.putString("title", "汇兑宝");
    				Intent intent = new Intent(getActivity(), HuiDuiTongActivity.class);
    				intent.putExtras(bundle);
    				startActivity(intent);
                }

                @Override
                public void fali() {

                }
            });
			break;
		}

		case R.id.ll_item_03_01_01: {
			XWebAct.startAct(getActivity(), IURL.Bank_Ban_Ka_Tong, "线上办卡","");
			break;
		}
		case R.id.ll_item_03_01_02: {
			LoginHelper.get_instance(getActivity()).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Intent intent = new Intent(getActivity(), XmsMainActivity.class);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		case R.id.ll_item_03_01_03: {
			XWebAct.startAct(getActivity(), IURL.getBankCaiFuTong(getActivity()), "财富管家","");
			break;
		}
		case R.id.ll_item_03_01_04: {
			// 理财通
			LoginHelper.get_instance(getActivity()).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					final Context context = getActivity();
					RestTemplateJxBank restTemplate = new RestTemplateJxBank(context);
					JsonRequestParams params = new JsonRequestParams();
					String action = LoginUtil.getToken(context);
					String userid = LoginUtil.getUserId(context);
					Log.i("action", action);
					Log.i("userid", userid);
					params.put("enctyp", "");
					params.put("password", "");
					params.put("grant_type", "implicit");
					params.put("user_id", userid);
					// params.put("client_secret",
					// MainActivity.CONSUMER_SECRET);
					params.put("client_id", "357"); // 易惠通
					//params.put("client_id", BocSdkConfig.CONSUMER_KEY);
					params.put("acton", action);
					// https://openapi.boc.cn/bocop/oauth/token
					restTemplate.postGetType("https://openapi.boc.cn/oauth/token", params,
							new JsonHttpResponseHandler("UTF-8") {
								private ProgressDialog progressDialog;

								@Override
								public void onStart() {
									super.onStart();
									progressDialog = new ProgressDialog(context);
									progressDialog.setMessage("正在努力加载中...");
									progressDialog.setCanceledOnTouchOutside(false);
									progressDialog.show();
								}

								@Override
								public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
									System.out.println("nihao======" + response.toString());
									progressDialog.dismiss();
									Intent intent = new Intent(context, MoneySelectWebView.class);
									intent.putExtra("url",
											"https:/openapi.boc.cn/ezdb/mobileHtml/html/userCenter/index.html?channel=android");
									try {
										intent.putExtra("access_token", response.getString("access_token"));
										intent.putExtra("refresh_token", response.getString("refresh_token"));
										intent.putExtra("user_id", response.getString("user_id"));
										context.startActivity(intent);
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}

								@Override
								public void onFailure(int statusCode, Header[] headers, Throwable throwable,
										JSONObject errorResponse) {
									if (progressDialog != null) {
										progressDialog.dismiss();
									}
								}
							});
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
		
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
