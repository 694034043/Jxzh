package com.bocop.zyt.bocop.zyt.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.WebForZytActivity;
import com.bocop.zyt.bocop.jxplatform.view.MyGridView;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.model.ItemBean;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.tools.CommonAdapter;
import com.bocop.zyt.jx.tools.ViewHolder;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 银政通(鹰潭专属) 物联产业平台 activity
 * @author chenyc 20170914
 */
@ContentView(R.layout.activity_yzt_yintan_industrial_platform)
public class YZTYinTanIndustrialPlatformActivity extends BaseActivity {
	
	@ViewInject(R.id.gv_item_01)
	private MyGridView gvItem01;
	@ViewInject(R.id.tv_actionbar_title)
    private TextView tv_actionbar_title;
	@ViewInject(R.id.vp_top_banner)
    private ViewPager vp_top_banner;
	@ViewInject(R.id.vp_indicator)
    private CirclePageIndicator vp_indicator;
	@ViewInject(R.id.rl_vp_container)
    private RelativeLayout rl_vp_container;
	
    private Timer _timer;
    private int banner_position = 0;
    public static Handler handler = new Handler();
    private List<ItemBean> item01Datas = new ArrayList<>();
	
	public static final int[] top_banner_pics = { 
			R.drawable.yzt_yingtan_banner_01,
			R.drawable.yzt_yingtan_banner_02,
			R.drawable.yzt_yingtan_banner_03,
			R.drawable.yzt_yingtan_banner_04};
	
	public static void startAct(Context context){
		Intent intent = new Intent(context, YZTYinTanIndustrialPlatformActivity.class);
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
	
	private void initView() {
		_timer = new Timer();
		tv_actionbar_title.setText(getResources().getString(R.string.yzt_ytzq_dedicated_service_item01));
        gvItem01.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvItem01.setAdapter(new YZTAdapter(getActivity(), item01Datas, R.layout.adapter_simple_item));
        gvItem01.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				WebForZytActivity.startAct(getActivity(), item01Datas.get(position).getUrl(),item01Datas.get(position).getTitle(),true);
			}
		});
        gvItem01.setSelection(0);
        show_top_banner();
	}
	
	private void show_top_banner() {
        LayoutInflater inflater = getLayoutInflater();
        int[] sc = ScreenUtil.get_screen_size(getActivity());
        List<View> vs = new ArrayList<>();
        int w = sc[0];
        //int h = (int) (4.0 / 10 * sc[0]);
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
			helper.getConvertView().setBackgroundResource(R.drawable.gv_one_selector);
			ImageView icon = helper.getView(R.id.iv_icon);
			TextView title = helper.getView(R.id.tv_title);
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
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_01_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_01), R.drawable.yzt_ytzq_industrial_platform_item_01_01_01));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_02_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_02), R.drawable.yzt_ytzq_industrial_platform_item_01_01_02));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_03_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_03), R.drawable.yzt_ytzq_industrial_platform_item_01_01_03));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_04_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_04), R.drawable.yzt_ytzq_industrial_platform_item_01_01_04));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_05_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_05), R.drawable.yzt_ytzq_industrial_platform_item_01_01_05));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_06_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_06), R.drawable.yzt_ytzq_industrial_platform_item_01_01_06));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_07_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_07), R.drawable.yzt_ytzq_industrial_platform_item_01_01_07));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_08_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_08), R.drawable.yzt_ytzq_industrial_platform_item_01_01_08));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_09_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_09), R.drawable.yzt_ytzq_industrial_platform_item_01_01_09));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_10_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_10), R.drawable.yzt_ytzq_industrial_platform_item_01_01_10));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_11_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_11), R.drawable.yzt_ytzq_industrial_platform_item_01_01_11));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_12_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_12), R.drawable.yzt_ytzq_industrial_platform_item_01_01_12));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_13_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_13), R.drawable.yzt_ytzq_industrial_platform_item_01_01_13));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_14_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_14), R.drawable.yzt_ytzq_industrial_platform_item_01_01_14));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_15_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_15), R.drawable.yzt_ytzq_industrial_platform_item_01_01_15));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_16_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_16), R.drawable.yzt_ytzq_industrial_platform_item_01_01_16));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_17_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_17), R.drawable.yzt_ytzq_industrial_platform_item_01_01_17));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_18_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_18), R.drawable.yzt_ytzq_industrial_platform_item_01_01_18));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_19_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_19), R.drawable.yzt_ytzq_industrial_platform_item_01_01_19));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_20_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_20), R.drawable.yzt_ytzq_industrial_platform_item_01_01_20));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_21_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_21), R.drawable.yzt_ytzq_industrial_platform_item_01_01_21));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_22_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_22), R.drawable.yzt_ytzq_industrial_platform_item_01_01_22));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_23_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_23), R.drawable.yzt_ytzq_industrial_platform_item_01_01_23));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_24_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_24), R.drawable.yzt_ytzq_industrial_platform_item_01_01_24));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_25_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_25), R.drawable.yzt_ytzq_industrial_platform_item_01_01_25));
		item01Datas.add(new ItemBean(IURL.yzt_yt_industrial_platform_item_26_url, getResources().getString(R.string.yzt_ytzq_industrial_platform_item_01_01_26), R.drawable.yzt_ytzq_industrial_platform_item_01_01_26));
	}
}
