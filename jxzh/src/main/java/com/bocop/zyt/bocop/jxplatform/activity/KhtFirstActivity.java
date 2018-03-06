/**
 *
 */
package com.bocop.zyt.bocop.jxplatform.activity;

import com.boc.bocop.sdk.util.StringUtil;

/**
 * @author luoyang  E-mail: luoyang8714@163.com
 * @version 创建时间：2017-1-11 上午8:57:44
 * 类说明
 */
/**
 * @author zhongye
 *
 */
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.bean.Advertisement;
import com.bocop.zyt.bocop.jxplatform.bean.SchoolBean;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtilAnother;
import com.bocop.zyt.bocop.kht.activity.KhtActivity;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.bocop.zyt.gui.ImageAdsAct;
import com.bocop.zyt.bocop.zyt.gui.KhtQueryActivity;
import com.bocop.zyt.jx.ab.view.sliding.AbSlidingPlayView;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.common.util.AbImageUtil;
import com.bocop.zyt.jx.constants.Constants;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * 签证通首页
 *
 * @author luoyang
 *
 */
@ContentView(R.layout.activity_khtfrist)
public class KhtFirstActivity extends BaseActivity implements LoginUtilAnother.ILoginListener {
	// txm
	private ArrayList<SchoolBean> fristList = new ArrayList<SchoolBean>();
	private TextView tv_titleName;
	private ImageView iv_title_left;
	protected BaseActivity baseActivity;
	private List<Advertisement> mAdvList = new ArrayList<Advertisement>();
	public BaseApplication baseApplication = BaseApplication.getInstance();
	public static final int WEN_ZAI_XIAN_KAI_HU = 1;
	public static final int GONG_ZAI_XIAN_KAI_HU = 2;
	public static final int FU_KAI_HU_TONG = 3;
	public static final int BI_KAI_HU_TONG = 4;
	@ViewInject(R.id.ll_main_content)
	LinearLayout llMainContent;
	@ViewInject(R.id.ll_kdt_main)
	LinearLayout kdtMain;
	/**
	 * 集成图片轮播
	 */
	AbSlidingPlayView pv_playview;
	static final int ASPECT_X = 4, ASPECT_Y = 1;
	private int state;
	private String title;
	private boolean isShowTitle;
	private String plateForm;
	private int[] yjtBannerIds = new int[]{R.drawable.pic_yjt_top_banner_01,R.drawable.pic_yjt_top_banner_02};
	private int[] kdtBannerIds = new int[]{R.drawable.ic_kdt_top_banner_04};

	public static void startAct(Context context,String plateForm,String title,boolean isShowTitle){
		Intent intent = new Intent(context,KhtFirstActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("platForm", plateForm);
		intent.putExtra("isShowTitle", isShowTitle);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		state = getIntent().getIntExtra("state", 0);
		title = getIntent().getStringExtra("title");
		plateForm = getIntent().getStringExtra("platForm");
		isShowTitle = getIntent().getBooleanExtra("isShowTitle", false);
		title = StringUtil.isNullOrEmpty(title) ? "军人开户通" : title;
		initView();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		baseActivity = (BaseActivity) KhtFirstActivity.this;
		tv_titleName = (TextView) findViewById(R.id.tv_titleName);
		tv_titleName.setText(title);
		iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
		pv_playview = (AbSlidingPlayView) findViewById(R.id.spv_photos);
		if(!TextUtils.isEmpty(plateForm)&&plateForm.equals(getResources().getString(R.string.kdt_module_key))){
			llMainContent.setVisibility(View.GONE);
			kdtMain.setVisibility(View.VISIBLE);
		}else{
			llMainContent.setVisibility(View.VISIBLE);
			kdtMain.setVisibility(View.GONE);
		}

		initHeaderView();
	}

	@OnClick({R.id.kht_grtykh,R.id.iv_title_left,R.id.ll_qy_account_open,R.id.ll_qy_query,R.id.ll_qy_process,R.id.ll_gr_account_open,R.id.ll_gr_query,R.id.ll_gr_process})
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_gr_account_open:
				KhtActivity.startAct(this, IURL.Bank_kht_grkh_url, getResources().getString(R.string.ll_yjt_gr_account_open),true);
				break;
			case R.id.ll_gr_query:
				KhtQueryActivity.startAct(this,getResources().getString(R.string.ll_yjt_gr_query),true);
				//KhtActivity.startAct(this, IURL.Bank_kht_grcx_url, getResources().getString(R.string.ll_qy_query),true);
				break;
			case R.id.ll_gr_process:
				//KhtActivity.startAct(this, IURL.Banke_grkhlc_url, getResources().getString(R.string.ll_qy_process),true);
				ImageAdsAct.startAct(this, R.drawable.pic_kht_grkhlc, getResources().getString(R.string.ll_yjt_gr_process));
				break;
			case R.id.kht_grtykh:
				KhtActivity.startAct(this, IURL.Bank_kht_grtykh_url, getResources().getString(R.string.ll_yjt_gr_grtykh),true);
				break;
			case R.id.iv_title_left:
				finish();
				break;
			case R.id.ll_qy_account_open:
				if(LoginUtil.isLog(this)){
					KhtActivity.startAct(KhtFirstActivity.this, IURL.ke_qy_kai_hu, getResources().getString(R.string.ll_kdt_qy_account_open),true);
				}else if(!TextUtils.isEmpty(LoginUtil.getTel(this))){
					KhtActivity.startAct(KhtFirstActivity.this, IURL.ke_qy_kai_hu+"&userID="+LoginUtil.getTel(this), getResources().getString(R.string.ll_kdt_qy_account_open),true);
				}else{
					LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
						@Override
						public void suc() {
							KhtActivity.startAct(KhtFirstActivity.this, IURL.ke_qy_kai_hu, getResources().getString(R.string.ll_kdt_qy_account_open),true);
						}

						@Override
						public void fali() {

						}
					});
				}
				break;
			case R.id.ll_qy_query:
				if(LoginUtil.isLog(this)){
					KhtActivity.startAct(KhtFirstActivity.this, IURL.ke_qy_query, "受理查询", false);
				}else if(!TextUtils.isEmpty(LoginUtil.getTel(this))){
					KhtActivity.startAct(KhtFirstActivity.this, IURL.ke_qy_query+"&userID="+LoginUtil.getTel(KhtFirstActivity.this), "受理查询", false);
				}else{
					LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
						@Override
						public void suc() {
							KhtActivity.startAct(KhtFirstActivity.this, IURL.ke_qy_query, "受理查询", false);
						}

						@Override
						public void fali() {

						}
					});
				}
				break;
			case R.id.ll_qy_process:
				//KhtActivity.startAct(KhtFirstActivity.this, IURL.ke_qy_khlc, "开户流程", false);
				ImageAdsAct.startAct(this, R.drawable.pic_kdt_kht_qykhlc, "开户流程","#09C8F2");

				break;

		}
	}

	private void initHeaderView() {
		pv_playview.setNavHorizontalGravity(Gravity.RIGHT);
		Drawable iv_playviewindex_off = getResources().getDrawable(R.drawable.iv_playviewindex_off);
		Drawable iv_playviewindex_on = getResources().getDrawable(R.drawable.iv_playviewindex_on);
		pv_playview.setPageLineImage(AbImageUtil.drawableToBitmap(iv_playviewindex_on),
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
		if(!TextUtils.isEmpty(plateForm)&&plateForm.equals(getResources().getString(R.string.kdt_module_key))){
			for(int i=0;i<kdtBannerIds.length;i++){
				Advertisement adv = new Advertisement();
				adv.setImageRes(kdtBannerIds[i]);
				mAdvList.add(adv);
			}
		}else{
			for(int i=0;i<yjtBannerIds.length;i++){
				Advertisement adv = new Advertisement();
				adv.setImageRes(yjtBannerIds[i]);
				mAdvList.add(adv);
			}
		}
		if (mAdvList != null && mAdvList.size() > 0) {// 多张图片
			for (Advertisement advertisement : mAdvList) {
				View view = getLayoutInflater().inflate(R.layout.item_adpic, null);
				ImageView iv = (ImageView) view.findViewById(R.id.iv_photo);
				iv.setImageResource(advertisement.getImageRes());
				pv_playview.addView(iv);
			}
			pv_playview.startPlay();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocop.jxplatform.util.LoginUtilAnother.ILoginListener#onLogin(int)
	 */
	@Override
	public void onLogin(int position) {
		// TODO Auto-generated method stub
		Bundle bundleHx = new Bundle();
		bundleHx.putString("url", Constants.UrlForMainQyKhtcard);
		bundleHx.putString("type", "kht");
		bundleHx.putString("name", title);
		Intent intentHx = new Intent(baseActivity, WebForZytActivity.class);
		intentHx.putExtras(bundleHx);
		startActivity(intentHx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocop.jxplatform.util.LoginUtilAnother.ILoginListener#onLogin()
	 */
	@Override
	public void onLogin() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocop.jxplatform.util.LoginUtilAnother.ILoginListener#onCancle()
	 */
	@Override
	public void onCancle() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocop.jxplatform.util.LoginUtilAnother.ILoginListener#onError()
	 */
	@Override
	public void onError() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocop.jxplatform.util.LoginUtilAnother.ILoginListener#onException()
	 */
	@Override
	public void onException() {
		// TODO Auto-generated method stub

	}
}
