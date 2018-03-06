package com.bocop.zyt.bocop.zyt.gui.fragment.yan;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.WebForZytActivity;
import com.bocop.zyt.bocop.jxplatform.fragment.HomeFragmentHelper;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.bocop.zyt.gui.ViewPagerLoopAdapter;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@ContentView(R.layout.activity_yan_finance)
public class YanFinanceActivity extends BaseActivity implements OnClickListener{

	@ViewInject(R.id.tv_actionbar_title)
    private TextView tv_actionbar_title;
	@ViewInject(R.id.vp_top_banner)
    private ViewPager vp_top_banner;
	@ViewInject(R.id.vp_indicator)
    private CirclePageIndicator vp_indicator;
	@ViewInject(R.id.rl_vp_container)
	private RelativeLayout rl_vp_container;
	private Timer _timer;
	public static Handler handler = new Handler();
	private int banner_position = 0;
	public static final int[] top_banner_pics = { 
			R.drawable.ic_yyt_finance_banner_01};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}
	
	public static void startAct(Context context){
		Intent intent = new Intent(context, YanFinanceActivity.class);
		context.startActivity(intent);
	}
	
	public void fun_back_press(){
		finish();
	}

	private void initViews() {
		_timer = new Timer();
		findViewById(R.id.iv_item_01).setOnClickListener(this);
		findViewById(R.id.iv_item_02).setOnClickListener(this);
		findViewById(R.id.iv_item_03).setOnClickListener(this);
		findViewById(R.id.iv_item_04).setOnClickListener(this);
		tv_actionbar_title.setText(getResources().getString(R.string.yyt_finance_title));
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
	
	public void loop_banner() {

        _timer.schedule(new TimerTask() {
            @Override
            public void run() {

            	handler.post(new Runnable() {
                    @Override
                    public void run() {
                        banner_position++;
                        if (banner_position % top_banner_pics.length == 0) {
                            banner_position = 0;
                        }
                        vp_top_banner.setCurrentItem(banner_position, true);
                    }
                });

            }
        }, 3000, 3000);
    }
	
	private Activity getActivity() {
		return this;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_item_01:
			WebForZytActivity.startAct(this, IURL.Bank_yyt_zxt, getResources().getString(R.string.yyant_finance_item_01_title),true);
			break;
		case R.id.iv_item_02:
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					
					Bundle bundle = new Bundle();
            		bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_YAN_XIAO_FEI_YI_DAI);
            		Intent intent = new Intent(getActivity(), TrainsActivity.class);
            		intent.putExtras(bundle);
            		startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.iv_item_03:
			LoginHelper.get_instance(this).login(getActivity(), new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
					bundle.putInt("PRO_FLAG", TrainsActivity.FLAG_GJT);
					bundle.putInt("PRO_FlAG_STATE",TrainsActivity.FLAG_YYT_GJYD);
					Intent intent = new Intent(getActivity(), TrainsActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		case R.id.iv_item_04:
			LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {
				@Override
				public void suc() {
					Bundle bundle = new Bundle();
            		bundle.putInt("PRO_FLAG", HomeFragmentHelper.TAG_YAN_CHUANG_YE_YI_DAI);
            		Intent intent = new Intent(getActivity(), TrainsActivity.class);
            		intent.putExtras(bundle);
            		startActivity(intent);
				}

				@Override
				public void fali() {

				}
			});
			break;
		}
	}
}
