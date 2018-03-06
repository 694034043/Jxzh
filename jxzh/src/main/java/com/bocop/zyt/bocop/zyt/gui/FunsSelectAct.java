package com.bocop.zyt.bocop.zyt.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.boc.bocop.sdk.util.Logger;
import com.boc.bocop.sdk.util.StringUtil;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.InformalLoginActivity;
import com.bocop.zyt.bocop.jxplatform.activity.MainActivity;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.widget.CoverFlow;
import com.bocop.zyt.bocop.zyt.widget.FancyCoverFlow;
import com.bocop.zyt.bocop.zyt.widget.core.OverlapFragment;
import com.bocop.zyt.bocop.zyt.widget.core.PagerContainer;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.constants.Constants;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;


public class FunsSelectAct extends BaseAct implements OnClickListener {

	private long exitTime;
	private FancyCoverFlow ff_main;
	private PagerContainer pc_main;
	private CirclePageIndicator vp_indicator;
	private TextView tv_enter;
	private int[] ss;
	private TextView tv_actionbar_title;
	//private MyPagerAdapter myPagerAdapter;
	int cit = 0;
	private ViewPager viewPager;
	private MyFragmentPagerAdapter pagerAdapter;
	private BaseApplication app;
	private TableRow tr_dots;
	private Timer _timer;
	Toast toast;
	
	static int[] pics_old = { };
//	R.drawable.pic_card_yin_qu_tong, R.drawable.pic_card_yin_zheng_tong,
//	R.drawable.pic_card_yin_tie_tong, R.drawable.pic_card_yin_ci_tong, R.drawable.pic_card_yin_yan_tong,
//	R.drawable.pic_card_yin_wen_tong, R.drawable.pic_card_yin_gong_tong, R.drawable.pic_card_fu_ping_tong, R.drawable.pic_card_yin_jun_tong, R.drawable.pic_card_yin_yao_tong
	private String[] moduleName;
	static int[] pics = {};
	int showId=-1;
	private int c;
	
	public static void startAct(Context context) {
		Intent intent = new Intent(context, FunsSelectAct.class);
		context.startActivity(intent);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_funs_select);
		ss = ScreenUtil.get_screen_size(this);
		app = (BaseApplication) getApplication();
		_timer = new Timer();
		moduleName = getResources().getStringArray(R.array.moduleName);
		init_widget();
		//login();
	}

	public String getInviteModuleName(Context cxt) {
		SharedPreferences sp = cxt.getSharedPreferences(LoginUtil.SP_NAME, Context.MODE_PRIVATE);
		String userTel = sp.getString(CacheBean.INVITE_MODULE_NAME, "");
		if (userTel != null && !"".equals(userTel)) {
			return userTel;
		}
		return "";
	}

	// *******************yuxinhan4-5
	public void login() {
		if (!LoginUtil.isLog(this) && StringUtil.isNullOrEmpty(getTel()) && !app.isShowShortTimeLogin) {
			Intent intent = new Intent(this, InformalLoginActivity.class);
			startActivity(intent);
			app.isShowShortTimeLogin = true;
		}

	}

	public String getTel() {
		SharedPreferences sp = getSharedPreferences(LoginUtil.SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();

		String userTel = sp.getString(CacheBean.USER_TEL_LOGIN, "");

		// String token = sp.getString(CacheBean.ACCESS_TOKEN, "");
		if (userTel != null && !"".equals(userTel)) {
			return userTel;
		}
		return "";
	}

	@Override
	public void init_widget() {
		String inviteModuleName = getInviteModuleName(this);
		for(int i = 0; i< Constants.moduleKeyStore.length; i++){
			if(inviteModuleName.equals(Constants.moduleKeyStore[i])){
				if(i>=pics_old.length){
					pics = pics_old;
				}else{
					pics = new int[1];
					pics[0] = pics_old[i];
					showId = i;
				}
				break;
			}
		}
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
		tv_actionbar_title.setTextSize(20);
		tv_actionbar_title.setText("首页");
		pc_main = (PagerContainer) findViewById(R.id.pc_main);
		tv_enter = (TextView) findViewById(R.id.tv_enter);
		tr_dots = (TableRow) findViewById(R.id.tr_dots);
		
		/*if(pics.length>1){
			tr_dots.removeAllViews();
			for (int i = 0; i < pics.length; i++) {
				ImageView iv = new ImageView(this);
				iv.setScaleType(ScaleType.FIT_XY);
				LayoutParams lp = iv.getLayoutParams();
				if(lp==null){
					lp = new LayoutParams(Utils.dpToPx(8, getResources()), Utils.dpToPx(8, getResources()));
				}else{
					lp.width = Utils.dpToPx(8, getResources());
					lp.height = Utils.dpToPx(8, getResources());					
				}
				//ImageView iv = (ImageView) tr_dots.getChildAt(i);
				iv.setImageResource(R.drawable.icon_gray_dot);
				tr_dots.addView(iv);
			}
		}*/
		
		RelativeLayout.LayoutParams ptv = new RelativeLayout.LayoutParams((int) (ss[1] * 1.0 / 4),
				DisplayUtil.dip2px(_act, 40));

		ptv.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		ptv.addRule(RelativeLayout.CENTER_HORIZONTAL);
		ptv.bottomMargin = DisplayUtil.dip2px(_act, 40);

		tv_enter.setLayoutParams(ptv);

		pc_main.setOverlapEnabled(true);
		pc_main.setClipChildren(false);
		if(pics.length==0){
			return;
		}

		viewPager = pc_main.getViewPager();
		// vp_indicator.setViewPager(viewPager);
		pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
		//myPagerAdapter = new MyPagerAdapter();
		viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
		viewPager.setClipChildren(false);
		viewPager.setAdapter(pagerAdapter);

		new CoverFlow
		.Builder()
		.with(viewPager)
		.scale(0.3f)
		.pagerMargin(-(float) (ss[0] * 3.7 / 5))
		.spaceSize(0f)
		.rotationY(26f).build();

		// Manually setting the first View to be elevated
		viewPager.post(new Runnable() {
			@Override
			public void run() {
				Fragment fragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
				ViewCompat.setElevation(fragment.getView(), 8.0f);
				if (StringUtil.isNullOrEmpty(app.getMainType()) || app.getMainType().equals("-1")) {
					cit = pagerAdapter.getCount() / 2;
				} else {
					try {
						cit = Integer.valueOf(app.getMainType());
					} catch (Exception e) {
						cit = pagerAdapter.getCount() / 2;
					}
				}
				viewPager.setCurrentItem(cit);
				dot_select(cit);
			}
		});

		pc_main.setCallback(new PagerContainer.Callback() {

			@Override
			public void select(int position) {
				// TODO Auto-generated method stub
				ILOG.log_4_7("page " + position);
				dot_select(position);
			}
		});

		tv_enter.setOnClickListener(this);
	}

	private void dot_select(int postion) {
		if(pics.length>1){
			for (int i = 0; i < pics.length; i++) {
				ImageView iv = (ImageView) tr_dots.getChildAt(i);
				iv.setVisibility(View.VISIBLE);
				if (i == postion) {
					iv.setImageResource(R.drawable.icon_red_dot);
				} else {
					iv.setImageResource(R.drawable.icon_gray_dot);
				}
			}
		}
	}
	
	private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
        	ImageView coverImageView = new ImageView(FunsSelectAct.this);
    		int w=(int) (ss[0]*1.0/2);
    		int h=(int) (w*47.0/30);
    		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w, h);
    		p.addRule(RelativeLayout.CENTER_IN_PARENT);
    		coverImageView.setLayoutParams(p);
    		coverImageView.setImageDrawable(ContextCompat.getDrawable(FunsSelectAct.this, pics[position]));
    		container.addView(coverImageView);
            return coverImageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return pics.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }

	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return OverlapFragment.newInstance(pics[position]);
		}

		@Override
		public int getCount() {
			return pics.length;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_enter: {
			c = viewPager.getCurrentItem();
			if(showId<pics_old.length&&showId!=-1){
				c = showId;
			}
			app.setMainType("" + c);
			_timer.schedule(new TimerTask() {
		    	
				@Override
				public void run() {
					MainActivity.startAct(FunsSelectAct.this, c);
				}
			}, 2000);
		    	try {
		    		if(toast==null){
		    			toast = Toast.makeText(this, "正在进入"+moduleName[c], 2000);
		    			toast.setGravity(Gravity.CENTER, 0, 0);
		    		}else{
		    			toast.setText("正在进入"+moduleName[c]);
		    		}
					toast.show();
				} catch (Exception e) {
					Logger.e(e.toString());
				}
		    }
			//com.bocop.jxplatform.activity.MainActivity.startAct(this, c);
			break;

		default:
			break;
		}

	}
	
	@Override
	public void onBackPressed() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			ToastUtils.show(this, "再按一次退出程序", Toast.LENGTH_SHORT);
			exitTime = System.currentTimeMillis();
		} else {
			CacheBean.getInstance().clearCacheMap();
			//BaseApplication.getInstance().exit();
			//System.exit(0);
			super.onBackPressed();
		}
	}
}
