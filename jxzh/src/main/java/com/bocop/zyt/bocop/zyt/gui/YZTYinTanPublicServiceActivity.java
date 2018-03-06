package com.bocop.zyt.bocop.zyt.gui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.bocop.jxplatform.view.MyGridView;
import com.bocop.zyt.bocop.qzt.QztMainActivity;
import com.bocop.zyt.bocop.zyt.model.ItemBean;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.tools.CommonAdapter;
import com.bocop.zyt.jx.tools.DisplayUtil;
import com.bocop.zyt.jx.tools.NetworkUtil;
import com.bocop.zyt.jx.tools.ViewHolder;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.BMJFActivity;
import com.bocop.zyt.bocop.jxplatform.activity.HuiDuiTongActivity;
import com.bocop.zyt.bocop.jxplatform.activity.JKETActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebForZytActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.activity.riders.RiderFristActivity;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.kht.activity.KhtActivity;
import com.bocop.zyt.bocop.xms.activity.XmsMainActivity;
import com.bocop.zyt.bocop.xms.activity.YbtActivity;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.bocop.zyt.broadcast.PlayerMusicBroadCastReciver;
import com.bocop.zyt.bocop.zyt.model.NUser;
import com.bocop.zyt.bocop.zyt.utils.MediaPlayerUtils;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;
import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 银政通(鹰潭专属) 公共服务 activity
 * @author chenyc 20170914
 */
@ContentView(R.layout.activity_yzt_yintan_public_service)
public class YZTYinTanPublicServiceActivity extends BaseActivity implements OnClickListener {
	
	@ViewInject(R.id.gv_item_01)
	private MyGridView gvItem01;
	@ViewInject(R.id.gv_item_02)
	private MyGridView gvItem02;
	@ViewInject(R.id.gv_item_03)
	private MyGridView gvItem03;
	@ViewInject(R.id.tv_actionbar_title)
    private TextView tv_actionbar_title;
	@ViewInject(R.id.vp_top_banner)
    private ViewPager vp_top_banner;
	@ViewInject(R.id.vp_indicator)
    private CirclePageIndicator vp_indicator;
	@ViewInject(R.id.rl_vp_container)
	private RelativeLayout rl_vp_container;
	@ViewInject(R.id.iv_music)
	private ImageView iv_music;
	private MyPlayerMusicBroadCastReciver reciver;
	private MediaPlayer mPlayer;
	
    private Timer _timer;
    private int banner_position = 0;
    public static Handler handler = new Handler();
    private List<ItemBean> item01Datas = new ArrayList<>();
	private List<ItemBean> item02Datas = new ArrayList<>();
	private List<ItemBean> item03Datas = new ArrayList<>();
	
	public static final int[] top_banner_pics = { 
			R.drawable.yzt_yingtan_banner_01,
			R.drawable.yzt_yingtan_banner_02,
			R.drawable.yzt_yingtan_banner_03,
			R.drawable.yzt_yingtan_banner_04};
	
	public static void startAct(Context context){
		Intent intent = new Intent(context, YZTYinTanPublicServiceActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initDatas();
		initView();
	}
	
	public void fun_back_press(){
		finish();
	}
	
	protected Context getActivity(){
		return this;
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
	
	private void initView() {
		IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);  
        registerReceiver(reciver = new MyPlayerMusicBroadCastReciver(), myIntentFilter);
        iv_music = (ImageView) findViewById(R.id.iv_music);
        iv_music.setVisibility(View.GONE);
		iv_music.setOnClickListener(this);
		mPlayer = MediaPlayerUtils.create();
		if(!mPlayer.isPlaying()){
			iv_music.setImageResource(R.drawable.icon_music_off);
		}
		_timer = new Timer();
		tv_actionbar_title.setText(getResources().getString(R.string.yzt_ytzq_public_service_title));
        gvItem01.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvItem01.setAdapter(new YZTAdapter(getActivity(), item01Datas, R.layout.adapter_simple_item));
        gvItem01.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				if(item01Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_01_01_01))){
					//KhtActivity.startAct(getActivity(), item01Datas.get(position).getUrl(), item01Datas.get(position).getTitle(),true);
					if(LoginUtil.isLog(getActivity())){
		    			KhtActivity.startAct(getActivity(), item01Datas.get(position).getUrl(), item01Datas.get(position).getTitle(),true);
					}else if(!TextUtils.isEmpty(LoginUtil.getTel(getActivity()))){
		    			KhtActivity.startAct(getActivity(), item01Datas.get(position).getUrl()+"&userID="+LoginUtil.getTel(getActivity()), item01Datas.get(position).getTitle(),true);
					}else{
						LoginHelper.get_instance(YZTYinTanPublicServiceActivity.this).login(YZTYinTanPublicServiceActivity.this, new LoginHelper.LoginCallback() {
			                @Override
			                public void suc() {
			        			KhtActivity.startAct(YZTYinTanPublicServiceActivity.this, item01Datas.get(position).getUrl(), item01Datas.get(position).getTitle(),true);
			                }

			                @Override
			                public void fali() {

			                }
			            });
					}
				}else{
					WebForZytActivity.startAct(getActivity(), item01Datas.get(position).getUrl(), item01Datas.get(position).getTitle(),true);
				}
			}
		});
        gvItem02.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvItem02.setAdapter(new YZTAdapter(getActivity(), item02Datas, R.layout.adapter_simple_item));
        gvItem02.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(item02Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_02_01_01))){
					if (NetworkUtil.isNetworkAvailable(getActivity())) {
						Intent intent = new Intent(getActivity(), QztMainActivity.class);
						intent.putExtra("FLAG_STATE", TrainsActivity.FLAG_YZTYT_GDT);
						startActivity(intent);
					} else {
						CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					}
				}else if(item02Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_02_01_02))){
					Bundle bundle = new Bundle();
					bundle.putString("userId", LoginUtil.getUserId(getActivity()));
					bundle.putString("token", LoginUtil.getToken(getActivity()));
					bundle.putString("platform","fpt");
					bundle.putString("title", getResources().getString(R.string.yzt_ytzq_item_02_01_02));
					Intent intent = new Intent(getActivity(), JKETActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}else if(item02Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_02_01_03))){
					XYTMainActivity.startAct(YZTYinTanPublicServiceActivity.this, "智慧校园");
					//WebForZytActivity.startAct(getActivity(), Constants.UrlForMainXyt,getResources().getString(R.string.yzt_ytzq_item_02_01_03),true);
				}else if(item02Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_02_01_04))){
					NUser.LoginInfo u = LoginHelper.get_instance(YZTYinTanPublicServiceActivity.this).get_login_info();
					String url = IURL.Bank_Bian_Jie_Lv_You;
					if(LoginUtil.isLog(getActivity())){
						url = url +  "?userid=" + u.userid + "&accessToken=" + u.access_token;
					}
					WebForZytActivity.startAct(getActivity(), url,getResources().getString(R.string.yzt_ytzq_item_02_01_04),true);
				}else if(item02Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_02_01_05))){
					LoginHelper.get_instance(YZTYinTanPublicServiceActivity.this).login(YZTYinTanPublicServiceActivity.this, new LoginHelper.LoginCallback() {
	                    @Override
	                    public void suc() {
	                    	Bundle bundle = new Bundle();
	                		bundle.putInt("PRO_FLAG", 5);
	                		bundle.putInt("PRO_FlAG_STATE",TrainsActivity.FLAG_YZTYT_GDT);
	                		Intent intent = new Intent(getActivity(), TrainsActivity.class);
	                		intent.putExtras(bundle);
	                		startActivity(intent);
	                    }

	                    @Override
	                    public void fali() {

	                    }
	                });
				}else if(item02Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_03_01_04))){
					// 理财管家
					LoginHelper.get_instance(YZTYinTanPublicServiceActivity.this).login(YZTYinTanPublicServiceActivity.this, new LoginHelper.LoginCallback() {
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
							// params.put("client_id", "481");
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
											intent.putExtra("title", getResources().getString(R.string.yzt_ytzq_item_03_01_04));
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
				}
			}
		});
        gvItem03.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvItem03.setAdapter(new YZTAdapter(getActivity(), item03Datas, R.layout.adapter_simple_item));
        gvItem03.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(item03Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_03_01_01))){
					// 秘书通
					LoginHelper.get_instance(YZTYinTanPublicServiceActivity.this).login(YZTYinTanPublicServiceActivity.this, new LoginHelper.LoginCallback() {
						@Override
						public void suc() {
							Intent intent = new Intent(getActivity(), XmsMainActivity.class);
							intent.putExtra("title", getResources().getString(R.string.yzt_ytzq_item_03_01_01));
							startActivity(intent);
						}

						@Override
						public void fali() {

						}
					});
				}else if(item03Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_03_01_03))){
					// 易车行
					LoginHelper.get_instance(YZTYinTanPublicServiceActivity.this).login(YZTYinTanPublicServiceActivity.this, new LoginHelper.LoginCallback() {
						@Override
						public void suc() {
							Intent intent = new Intent(getActivity(), RiderFristActivity.class);
							intent.putExtra("title", getResources().getString(R.string.yzt_ytzq_item_03_01_03));
							intent.putExtra("isShowParamsTitle", true);
							startActivity(intent);
						}

						@Override
						public void fali() {

						}
					});
				}else if(item03Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_03_01_05))){
					Intent intent = new Intent(getActivity(), YbtActivity.class);
					intent.putExtra("title", getResources().getString(R.string.yzt_ytzq_item_03_01_05));
					startActivity(intent);
				}else if(item03Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_03_01_06))){
					//汇兑通
		            LoginHelper.get_instance(YZTYinTanPublicServiceActivity.this).login(YZTYinTanPublicServiceActivity.this, new LoginHelper.LoginCallback() {
		                @Override
		                public void suc() {
		                	Bundle bundle = new Bundle();
		                	bundle.putString("title", getResources().getString(R.string.yzt_ytzq_item_03_01_06));
		    				Intent intent = new Intent(getActivity(), HuiDuiTongActivity.class);
		    				intent.putExtras(bundle);
		    				startActivity(intent);
		                }

		                @Override
		                public void fali() {

		                }
		            });
				}else if(item03Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_02_01_07))){
					WebForZytActivity.startAct(getActivity(), BocSdkConfig.YPT,getResources().getString(R.string.yzt_ytzq_item_02_01_07),true);
				}else if(item03Datas.get(position).getTitle().equals(getResources().getString(R.string.yzt_ytzq_item_03_01_02))){
					// 在线缴费
					LoginHelper.get_instance(YZTYinTanPublicServiceActivity.this).login(YZTYinTanPublicServiceActivity.this, new LoginHelper.LoginCallback() {
						@Override
						public void suc() {
							Intent intent = new Intent(getActivity(), BMJFActivity.class);
							intent.putExtra("title", getResources().getString(R.string.yzt_ytzq_item_03_01_02));
							intent.putExtra("isShowParamsTitle", true);
							startActivity(intent);
						}

						@Override
						public void fali() {

						}
					});
				}
			}
		});
        show_top_banner();
	}
	
	private void show_top_banner() {
        LayoutInflater inflater = getLayoutInflater();
        int[] sc = ScreenUtil.get_screen_size(getActivity());
        List<View> vs = new ArrayList<>();
        int w = sc[0];
        //int h = (int) (4 / 10 * sc[0]);
        int h = DisplayUtil.dip2px(this, 180);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
        rl_vp_container.setLayoutParams(p);
        for (int res : top_banner_pics
                ) {
            ImageView iv = (ImageView) inflater.inflate(R.layout.act_main_gan_jiang_xin_qu_fragment_top_banner_iv, null);
            iv.setLayoutParams(p);
            FImageloader.load_by_resid(getActivity(), res, iv);
            vs.add(iv);
        }
        vp_top_banner.setAdapter(new ViewPagerLoopAdapter(vs));
        vp_indicator.setViewPager(vp_top_banner);
        loop_banner();
    }
	
	public class YZTAdapter extends CommonAdapter<ItemBean> {
		
		public YZTAdapter(Context context, List<ItemBean> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, ItemBean item) {
			ImageView icon = helper.getView(R.id.iv_icon);
			TextView title = helper.getView(R.id.tv_title);
			title.setTextColor(Color.parseColor("#4D8BB5"));
			icon.setImageResource(item.getIconResouceId());
			title.setText(item.getTitle());
		}
	}

    public void loop_banner() {

        _timer.schedule(new TimerTask() {
            @Override
            public void run() {

            	handler.post(new Runnable() {
                    @Override
                    public void run() {
                        banner_position++;
                        if (banner_position % 4 == 0) {
                            banner_position = 0;
                        }
                        vp_top_banner.setCurrentItem(banner_position, true);
                    }
                });

            }
        }, 3000, 3000);
    }

	private void initDatas() {
		item01Datas.add(new ItemBean(IURL.kht_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_item_01_01_01), R.drawable.ic_yztyt_dedicated_item_01_01_01));
		item01Datas.add(new ItemBean(IURL.qdt_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_item_01_01_04), R.drawable.ic_yztyt_dedicated_item_01_01_02));
		item01Datas.add(new ItemBean(IURL.pjt_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_item_01_01_06), R.drawable.ic_yztyt_dedicated_item_01_01_03));
		item01Datas.add(new ItemBean(IURL.bht_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_item_01_01_03), R.drawable.ic_yztyt_dedicated_item_01_01_06));
		item01Datas.add(new ItemBean(IURL.dzt_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_item_01_01_02), R.drawable.ic_yztyt_dedicated_item_01_01_04));
		item01Datas.add(new ItemBean(IURL.zxt_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_item_01_01_05), R.drawable.ic_yztyt_dedicated_item_01_01_05));

		item02Datas.add(new ItemBean(IURL.qzt_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_item_02_01_01), R.drawable.ic_yztyt_dedicated_item_02_01_01));
		item02Datas.add(new ItemBean(IURL.yyt_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_item_02_01_02), R.drawable.ic_yztyt_dedicated_item_02_01_02));
		item02Datas.add(new ItemBean(IURL.xyt_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_item_02_01_03), R.drawable.ic_yztyt_dedicated_item_02_01_03));
		item02Datas.add(new ItemBean(IURL.lyt_yzt_ytzs_url, getResources().getString(R.string.yzt_ytzq_item_02_01_04), R.drawable.ic_yztyt_dedicated_item_02_01_04));
		item02Datas.add(new ItemBean("", getResources().getString(R.string.yzt_ytzq_item_03_01_04), R.drawable.ic_yztyt_dedicated_item_02_01_06));
		item02Datas.add(new ItemBean("", getResources().getString(R.string.yzt_ytzq_item_02_01_05), R.drawable.ic_yztyt_dedicated_item_02_01_05));
		
		item03Datas.add(new ItemBean("", getResources().getString(R.string.yzt_ytzq_item_03_01_01), R.drawable.ic_yztyt_dedicated_item_03_01_01));
		item03Datas.add(new ItemBean("", getResources().getString(R.string.yzt_ytzq_item_03_01_02), R.drawable.ic_yztyt_dedicated_item_03_01_02));
		item03Datas.add(new ItemBean("", getResources().getString(R.string.yzt_ytzq_item_03_01_03), R.drawable.ic_yztyt_dedicated_item_03_01_03));
		item03Datas.add(new ItemBean(BocSdkConfig.YPT, getResources().getString(R.string.yzt_ytzq_item_02_01_07), R.drawable.ic_yztyt_dedicated_item_02_01_07));
		item03Datas.add(new ItemBean("", getResources().getString(R.string.yzt_ytzq_item_03_01_05), R.drawable.ic_yztyt_dedicated_item_03_01_05));
		item03Datas.add(new ItemBean("", getResources().getString(R.string.yzt_ytzq_item_03_01_06), R.drawable.ic_yztyt_dedicated_item_03_01_06));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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
		}
	}
	
	public void sendPlayerMusicBroadCast(int state){
		Intent intent = new Intent();  //Itent就是我们要发送的内容
        intent.putExtra("playState", state);  
        intent.setAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
        sendBroadcast(intent);   //发送广播
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(reciver);
	}
}
