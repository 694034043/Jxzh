package com.bocop.zyt.bocop.qzt;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boc.bocop.sdk.util.StringUtil;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.bean.Advertisement;
import com.bocop.zyt.bocop.jxplatform.bean.SchoolBean;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.FormsUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.MyUtil;
import com.bocop.zyt.bocop.jxplatform.view.MyGridView;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.jx.ab.view.sliding.AbSlidingPlayView;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.common.util.AbImageUtil;
import com.bocop.zyt.jx.constants.Constants;
import com.bocop.zyt.jx.gridview.ImageUrls;
import com.bocop.zyt.jx.gridview.InfoBean;
import com.bocop.zyt.jx.gridview.UrlGridviewAdapter;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * 签证通首页
 * 
 * @author luoyang
 * 
 */

@ContentView(R.layout.activity_qztmain)
public class QztMainActivity extends BaseActivity implements LoginUtil.ILoginListener {
	// txm
	private ArrayList<SchoolBean> fristList = new ArrayList<SchoolBean>();
	private FristAdaper adaper;
	private GridView gridview_allmodule;
	
	@ViewInject(R.id.qzt_main_jiudian)
	private ImageView  imJiudian;
	@ViewInject(R.id.qzt_main_wifi)
	private ImageView  imWifi;
	@ViewInject(R.id.webView)
	private WebView webView;
	@ViewInject(R.id.llt)
	private LinearLayout llt;
	@ViewInject(R.id.tvHotCountry)
	private TextView tvHotCountry;
	@ViewInject(R.id.tv_more)
	private TextView tvMore;
	
	private ArrayList<SchoolBean> countryList = new ArrayList<SchoolBean>();
	private CounrtyAdaper couAdaper;
	private GridView gdCountry;
	protected Context  context = this;
	
	private List<InfoBean> mList = new ArrayList<>();
	private UrlGridviewAdapter countryAdapter;
	
	private TextView tv_titleName;
	private ImageView iv_title_left;
	protected BaseActivity baseActivity;
	private List<Advertisement> mAdvList = new ArrayList<Advertisement>();
	public BaseApplication baseApplication = BaseApplication.getInstance();
	
	DisplayImageOptions options;
	
	
	//国家ID
	private static String[] counrtyId = new String[] { "1", "11","19","17",
		"4", "18", "13", "2",
		"16", "12","9","14"};	
	//国家
		private static String[] counrtyName = new String[] { "澳大利亚", "美国","英国","新西兰",
			"法国", "意大利", "瑞士", "德国",
			"新加坡", "日本","柬埔寨","泰国"};	

	// BaseApplication application = (BaseApplication) baseActivity
	// .getApplication();
	/**
	 * 集成图片轮播
	 */
	AbSlidingPlayView pv_playview;
	static final int ASPECT_X = 4, ASPECT_Y = 1;
	private int flagState;
	private final static int[] yztyt_banners = new int[]{
			R.drawable.yztyt_qzt_banner_01,
			R.drawable.yztyt_qzt_banner_02,
			R.drawable.yztyt_qzt_banner_03
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_qztmain);
		initView();
		setListener();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		baseActivity = (BaseActivity) QztMainActivity.this;
		tv_titleName = (TextView) findViewById(R.id.tv_titleName);
		String title = getIntent().getStringExtra("title");
		title = StringUtil.isNullOrEmpty(title)?"签证通":title;
		tv_titleName.setText(title);
		flagState = getIntent().getIntExtra("FLAG_STATE", -1);
		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		gridview_allmodule = (MyGridView) findViewById(R.id.gridview_allmodule);
		gdCountry = (MyGridView) findViewById(R.id.gd_counrty);
		pv_playview = (AbSlidingPlayView) findViewById(R.id.spv_photos);
		LayoutParams lp = pv_playview.getLayoutParams();
		lp.width = FormsUtil.SCREEN_WIDTH;
		lp.height = (int) (FormsUtil.SCREEN_WIDTH / 3.15);
		pv_playview.setLayoutParams(lp);
		
		if(MyUtil.isTablet(this)){
			tvMore.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.textview_mid_size));
			tvHotCountry.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.textview_mid_size));
		}
		LayoutParams lltLp = llt.getLayoutParams();
		lltLp.height = (int) (FormsUtil.SCREEN_WIDTH / 4);
		lltLp.width = FormsUtil.SCREEN_WIDTH;
		llt.setLayoutParams(lltLp);
		initHeaderView();

		addSchoolBean();
		adaper = new FristAdaper();
		gridview_allmodule.setAdapter(adaper);
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		addCountryBean();
		countryAdapter = new UrlGridviewAdapter(QztMainActivity.this, mList, gdCountry);
		gdCountry.setAdapter(countryAdapter);
		
		imageLoader.displayImage(BocSdkConfig.qztJiudianUrl, imJiudian,options);
		imageLoader.displayImage(BocSdkConfig.qztWifiUrl, imWifi,options);
//		addCountryBean();
//		couAdaper = new CounrtyAdaper();
//		gdCountry.setAdapter(couAdaper);


	}

	private void initHeaderView() {
		pv_playview.setNavHorizontalGravity(Gravity.RIGHT);
		Drawable iv_playviewindex_off = getResources().getDrawable(
				R.drawable.iv_playviewindex_off);
		Drawable iv_playviewindex_on = getResources().getDrawable(
				R.drawable.iv_playviewindex_on);
		pv_playview.setPageLineImage(
				AbImageUtil.drawableToBitmap(iv_playviewindex_on),
				AbImageUtil.drawableToBitmap(iv_playviewindex_off));
		initPlayViewSize();
		initPlayViewContent();
		pv_playview.setPlayDuration(3000);
	}

	private void initPlayViewSize() {
		final ViewTreeObserver vto = pv_playview.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			boolean hasSetted = false;

			@Override
			public void onGlobalLayout() {
				if (hasSetted)
					return;
				hasSetted = true;
				LayoutParams params = pv_playview.getLayoutParams();
				int w = pv_playview.getMeasuredWidth();
				int h = w / ASPECT_X * ASPECT_Y;
				// 宽度放开的话就会根据宽高比进行适配
				// params.height = h;
				pv_playview.setLayoutParams(params);
				try {
					vto.removeGlobalOnLayoutListener(this);
				} catch (Exception e) {
				}
			}
		});
	}

	private void initPlayViewContent() {
		getAdPhotos();
	}

	void getAdPhotos() {
		pv_playview.removeAllViews();
		Advertisement adv1 = new Advertisement();
		Advertisement adv2 = new Advertisement();
		Advertisement adv3 = new Advertisement();
		if(flagState== TrainsActivity.FLAG_YZTYT_GDT){
			adv1.setImageRes(yztyt_banners[0]);
			mAdvList.add(adv1);
			adv2.setImageRes(yztyt_banners[1]);
			mAdvList.add(adv2);
			adv3.setImageRes(yztyt_banners[2]);
			mAdvList.add(adv3);
			tv_titleName.setText(getResources().getString(R.string.yzt_ytzq_item_02_01_01));
		}else{
			// 第一张
			adv1.setImageRes(R.drawable.visarollimg1);
			// adv1.setContent("http://mp.weixin.qq.com/s?__biz=MjM5NDg0NzIzNA==&mid=401756730&idx=6&sn=ae322c5365773a4abf94f0a90dc5df60&scene=1&srcid=0115ZGwEkPDOdR1gKIke6zUw#rd");
			// // 网页url
			mAdvList.add(adv1);
			// 第二张
			adv2.setImageRes(R.drawable.visarollimg2);
			// adv2.setContent("http://mp.weixin.qq.com/s?__biz=MjM5NDg0NzIzNA==&mid=401756730&idx=5&sn=2169aa0f2751322e4d4874b7f3144929&scene=1&srcid=0115QUo3iYtIvU2q9TpYiX8b#rd");
			mAdvList.add(adv2);
			// 第三张
			adv3.setImageRes(R.drawable.visarollimg3);
			// adv3.setContent("http://mp.weixin.qq.com/s?__biz=MjM5NDg0NzIzNA==&mid=401756730&idx=3&sn=32df249221afd8aa3d2d0de8be8f9792&scene=1&srcid=01158TgGCW2ZeSHQNKtiXMzw#rd");
			mAdvList.add(adv3);
		}
		// Advertisement adv4 = new Advertisement();
		// adv4.setContent("http://mp.weixin.qq.com/s?__biz=MjM5NDg0NzIzNA==&mid=401756730&idx=4&sn=3060be3f4ed8c4fcb4b6803cb2e2d08b&scene=1&srcid=0115bPM13VwmciqlqsePCPbT#rd");
		// adv4.setImageRes(R.drawable.cyh_adv4);
		// mAdvList.add(adv4);
		if (mAdvList != null && mAdvList.size() > 1) {// 多张图片
			// pv_playview.clear();
			for (Advertisement advertisement : mAdvList) {
				View view = getLayoutInflater().inflate(R.layout.item_adpic,
						null);
				ImageView iv = (ImageView) view.findViewById(R.id.iv_photo);
				iv.setImageResource(advertisement.getImageRes());
				pv_playview.addView(iv);
			}
			pv_playview.startPlay();
		}
	}

	@OnClick({R.id.tv_more,R.id.qzt_main_jiudian,R.id.qzt_main_wifi})
	public void onClick(View v){
		switch(v.getId()){
		case R.id.tv_more:
			if (baseApplication.isNetStat()) {
//				Intent intent = new Intent(QztMainActivity.this,
//						QztApplyActivity.class);
				Intent intent = new Intent(QztMainActivity.this,
						QztMoreCountryActivity.class);
				startActivity(intent);
			} else {
				CustomProgressDialog
						.showBocNetworkSetDialog(baseActivity);
			}
			break;
		
		case R.id.qzt_main_jiudian:
			
			 if (baseApplication.isNetStat()) {
				 CustomProgressDialog
				 .showBocFengxianDialog(
				 QztMainActivity.this,
				 "免责声明",
				 "\t\t签证通引用的第三方服务网址、商户及商品信息，意在为客户提供方便快捷的服务链接，所有服务均由第三方服务商提供，相关服务和责任将由第三方承担，如在使用服务的过程中有任何问题和纠纷，请直接与第三方服务商联系解决。 ","qztjiudian");
				
				 } else {
				 CustomProgressDialog.showBocNetworkSetDialog(this);
				 }
			
//			if (baseApplication.isNetStat()) {
//				Bundle bundleExchange = new Bundle();
//				bundleExchange.putString("url", Constants.qztUrlForJiudian);
//				bundleExchange.putString("name", "酒店预订");
//				Intent intentExchange = new Intent(this, WebViewActivity.class);
//				intentExchange.putExtras(bundleExchange);
//				startActivity(intentExchange);
//			} else {
//				CustomProgressDialog
//						.showBocNetworkSetDialog(baseActivity);
//			}
			break;
			
		case R.id.qzt_main_wifi:
			
			if (baseApplication.isNetStat()) {
				 CustomProgressDialog
				 .showBocFengxianDialog(
				QztMainActivity.this,
				 "免责声明",
				 "\t\t签证通引用的第三方服务网址、商户及商品信息，意在为客户提供方便快捷的服务链接，所有服务均由第三方服务商提供，相关服务和责任将由第三方承担，如在使用服务的过程中有任何问题和纠纷，请直接与第三方服务商联系解决。 ","qztwifi");
				
				 } else {
				 CustomProgressDialog.showBocNetworkSetDialog(this);
				 }
			
//			if (baseApplication.isNetStat()) {
//				Bundle bundleExchange = new Bundle();
//				bundleExchange.putString("url", Constants.qztUrlForWifi);
//				bundleExchange.putString("name", "全球Wifi");
//				Intent intentExchange = new Intent(this, WebViewActivity.class);
//				intentExchange.putExtras(bundleExchange);
//				startActivity(intentExchange);
//			} else {
//				CustomProgressDialog
//						.showBocNetworkSetDialog(baseActivity);
//			}
			break;
		}
	}
	
	/**
	 * 设置监听
	 */
	private void setListener() {
		iv_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		// 轮播图的点击事件
		// pv_playview.setOnItemClickListener(new AbOnItemClickListener() {
		//
		// @Override
		// public void onClick(int position) {
		// Intent intent = new Intent(QztFristActivity.this,
		// CyhAdvDetailActivity.class);
		// intent.putExtra("url", mAdvList.get(position).getContent());
		// startActivity(intent);
		// }
		// });

		gdCountry.setOnItemClickListener(new OnItemClickListener() {
			
			/* (non-Javadoc)
			 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (baseApplication.isNetStat()) {
					Bundle bundle = new Bundle();
					bundle.putString("id", counrtyId[position]);
					Intent intent = new Intent(QztMainActivity.this,
							QztCountryView.class);
					intent.putExtras(bundle);
					startActivity(intent);
				} else {
					CustomProgressDialog
							.showBocNetworkSetDialog(baseActivity);
				}
			}
		});
		
		// 列表
		gridview_allmodule.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
//				case 0:
//					if (baseApplication.isNetStat()) {
//						Intent intent = new Intent(QztMainActivity.this,
//								QztApplyActivity.class);
//						startActivity(intent);
//					} else {
//						CustomProgressDialog
//								.showBocNetworkSetDialog(baseActivity);
//					}
//					break;
				case 0:
					if (baseApplication.isNetStat()) {
						if (LoginUtil.isLog(baseActivity)) {
							Intent intent = new Intent(QztMainActivity.this,
									QztOrderActivity.class);
							startActivity(intent);
						} else {
							LoginUtil.authorize(QztMainActivity.this,
									QztMainActivity.this);
						}
					} else {
						CustomProgressDialog
								.showBocNetworkSetDialog(baseActivity);
					}
					break;
//				case 2:
//					Bundle bundleCard = new Bundle();
//					bundleCard.putString("url", BocSdkConfig.CARD);
//					bundleCard.putString("name", "办卡通");
//					Intent intentCard = new Intent(baseActivity,
//							WebActivity.class);
//					intentCard.putExtras(bundleCard);
//					startActivity(intentCard);
//					break;
				case 1:
					Bundle bundle = new Bundle();
					bundle.putString("url", Constants.qztUrlForfeiyong);
					bundle.putString("name", "签证费用");
					Intent intentDotbook = new Intent(QztMainActivity.this,
							WebActivity.class);
					intentDotbook.putExtras(bundle);
					startActivity(intentDotbook);
					break;
				case 2:
					Bundle bundle1 = new Bundle();
					bundle1.putString("url", Constants.qztUrlForshichang);
					bundle1.putString("name", "签证时长");
					Intent intentDotbook1 = new Intent(QztMainActivity.this,
							WebActivity.class);
					intentDotbook1.putExtras(bundle1);
					startActivity(intentDotbook1);
					break;
				// case 5:
				// Bundle bundle2 = new Bundle();
				// bundle2.putString("url", Constants.qztUrlForChuguo);
				// bundle2.putString("name", "出国金融");
				// Intent intentChuGuo = new Intent(QztFristActivity.this,
				// XmsWebActivity.class);
				// intentChuGuo.putExtras(bundle2);
				// startActivity(intentChuGuo);
				// break;

				case 3:
					Bundle bundle3 = new Bundle();
					bundle3.putString("url", Constants.qztUrlForJIncai);
					bundle3.putString("name", "精彩活动");
					Intent intentJincai = new Intent(QztMainActivity.this,
							WebActivity.class);
					intentJincai.putExtras(bundle3);
					startActivity(intentJincai);
					break;
				default:
					break;
				}

			}
		});
	}

	/**
	 * 添加首页选项
	 */
	private void addSchoolBean() {
		fristList.clear();
//		fristList.add(new SchoolBean("签证申请", R.drawable.icon_qztapply,
//				"qztapply", 0));
		fristList.add(new SchoolBean("我的订单", R.drawable.icon_qzttime,
				"qzttime", 0));
//		fristList.add(new SchoolBean("信用卡申请", R.drawable.icon_qztcardapply,
//				"qztcardapply", 0));
		fristList.add(new SchoolBean("签证费用", R.drawable.icon_qztmoney,
				"qztmoney", 0));
		fristList.add(new SchoolBean("签证时长", R.drawable.icon_qztusetime,
				"qztusetime", 0));
		fristList.add(new SchoolBean("精彩活动", R.drawable.icon_qztjincai,
				"qztjincai", 0));
	}
	
	/**
	 * 添加首页选项
	 */
	private void addCountryBean() {
		
//		mList.clear();
		//Log.i("tag", "start addCountryBean");
		for(int i = 0; i< ImageUrls.imageUrls.length; i++){
			InfoBean bean = new InfoBean();
			bean.setCountryName(ImageUrls.countryName[i]);
			bean.setIconUrl(ImageUrls.imageUrls[i]);
			bean.setId(ImageUrls.id[i]);
			mList.add(bean);
		}
		//Log.i("tag", "end addCountryBean");
		
		
//		countryList.clear();
//		countryList.add(new SchoolBean("1", R.drawable.icon_qzt_cou_adly,
//				"qzttime", 0));
//		countryList.add(new SchoolBean("2", R.drawable.icon_qzt_cou_mg,
//				"qztmoney", 0));
//		countryList.add(new SchoolBean("3", R.drawable.icon_qzt_cou_yg,
//				"qztusetime", 0));
//		countryList.add(new SchoolBean("4", R.drawable.icon_qzt_cou_fg,
//				"qztjincai", 0));
//		countryList.add(new SchoolBean("5", R.drawable.icon_qzt_cou_ydl,
//				"qztusetime", 0));
//		countryList.add(new SchoolBean("6", R.drawable.icon_qzt_cou_rs,
//				"qztjincai", 0));
//		countryList.add(new SchoolBean("7", R.drawable.icon_qzt_cou_xjp,
//				"qztjincai", 0));
//		countryList.add(new SchoolBean("8", R.drawable.icon_qzt_cou_rb,
//				"qztusetime", 0));
//		countryList.add(new SchoolBean("9", R.drawable.icon_qzt_cou_jpz,
//				"qztjincai", 0));
	}

	class FristAdaper extends BaseAdapter {
		@Override
		public int getCount() {
			return fristList.size();
		}

		@Override
		public SchoolBean getItem(int position) {
			return fristList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.layout_school, null);
			}
			LinearLayout llt = (LinearLayout) convertView.findViewById(R.id.llt);
			TextView textView = (TextView) convertView
					.findViewById(R.id.tv_layout_school);
			ImageView tvUnreadNum = (ImageView) convertView
					.findViewById(R.id.tv_unreadnum);
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.iv_layut_school);
			if (MyUtil.isTablet(context)) {
            	int spacing = FormsUtil.dip2px(context.getResources().getDimension(R.dimen.margin_or_padding_1));
            	int widthOrHeight = (int) (FormsUtil.SCREEN_WIDTH/4-spacing/3);
            	int tvHeight = FormsUtil.sp2px(context.getResources().getDimension(R.dimen.textview_small_size));
            	int imgMarginHeight = FormsUtil.sp2px(context.getResources().getDimension(R.dimen.margin_or_padding_12));
            	int imgHeight = widthOrHeight-tvHeight-imgMarginHeight*2;
            	llt.setLayoutParams(new LayoutParams(widthOrHeight,widthOrHeight));
            	imageView.setLayoutParams(new LinearLayout.LayoutParams(imgHeight,imgHeight));
            	textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.textview_little_bigger_size));
			}
			SchoolBean schoolBean = fristList.get(position);
			textView.setText(schoolBean.getName());
			imageView.setImageResource(schoolBean.getIntDraweid());
			int unReadNum = schoolBean.getUnReadNum();
			if (unReadNum > 0) {
				tvUnreadNum.setVisibility(View.VISIBLE);
			}
			return convertView;
		}
	}
	
	class CounrtyAdaper extends BaseAdapter {
		@Override
		public int getCount() {
			return countryList.size();
		}

		@Override
		public SchoolBean getItem(int position) {
			return countryList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.layout_country_school, null);
			}
			
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.iv_layut_school);
			SchoolBean schoolBean = countryList.get(position);
			imageView.setImageResource(schoolBean.getIntDraweid());
			int unReadNum = schoolBean.getUnReadNum();
			return convertView;
		}
	}

	@Override
	public void onLogin() {
		// TODO Auto-generated method stub
		Toast.makeText(QztMainActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onCancle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onException() {
		// TODO Auto-generated method stub

	}
}
