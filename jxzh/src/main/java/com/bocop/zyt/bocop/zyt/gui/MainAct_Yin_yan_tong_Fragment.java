package com.bocop.zyt.bocop.zyt.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.MyUtil;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.widget.SlidCircleView;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.frg.FrgYYlTong;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.constants.Constants;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;
import com.bumptech.glide.Glide;
import com.item.proto.MTopModule;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.NoTitleAct;
import com.mdx.framework.utility.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by ltao on 2017/2/9.
 */

public class MainAct_Yin_yan_tong_Fragment extends BaseFragment implements View.OnClickListener {

    public BaseApplication baseApplication = BaseApplication.getInstance();
    private View v;
    private TextView tv_actionbar_title;
    private ViewPager vp_top_banner;
    private RelativeLayout rl_vp_container;
    private CirclePageIndicator vp_indicator;
    private Timer _timer;
    private SlidCircleView iv_cicle;
    private ImageView iv_prompt;
    private ImageView iv_finish;
    protected float iv_cicle_X;
    protected float iv_cicle_Y;

    private int ciccle_pading = 18;
    private ImageView iv_cicle_bg;
    private MTopModule module;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _timer = new Timer();
        module = (MTopModule) getArguments().getSerializable("data");

    }
    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    PackageManager packageManager = getActivity().getPackageManager();
                    Intent intent = new Intent();
                    intent = packageManager.getLaunchIntentForPackage("com.boc.bocop.container");
                    startActivity(intent);
                    break;
                case 2:
                    BaseAct._handler.post(gifRunnable);
                    break;
                case 3:
                    get_handler().post(new Runnable() {
                        @Override
                        public void run() {
                            banner_position++;
                            if (banner_position % 4 == 0) {
                                banner_position = 0;
                            }
                            vp_top_banner.setCurrentItem(banner_position, true);
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }

    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        ILOG.log_4_7("MainAct_YintietongFragment  onDestroy ");
        _timer.cancel();
        _timer = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.act_main_yin_yan_tong_fragment, null);
        tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
        tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tv_actionbar_title.setText("银烟通");

        vp_top_banner = (ViewPager) v.findViewById(R.id.vp_top_banner);
        rl_vp_container = (RelativeLayout) v.findViewById(R.id.rl_vp_container);
        vp_indicator = (CirclePageIndicator) v.findViewById(R.id.vp_indicator);

        iv_cicle = (SlidCircleView) v.findViewById(R.id.iv_cicle);
        iv_prompt = (ImageView) v.findViewById(R.id.iv_prompt);
        iv_finish = (ImageView) v.findViewById(R.id.iv_finish);
        SharedPreferences sp = this.getActivity().getSharedPreferences(LoginUtil.SP_NAME, Context.MODE_PRIVATE);
        boolean isfirst = sp.getBoolean(MyUtil.IS_FIRST_OPEN_YIN_YAN_TONG, true);
        if (isfirst) {
            iv_prompt.setVisibility(View.VISIBLE);
            Editor edit = sp.edit();
            edit.putBoolean(MyUtil.IS_FIRST_OPEN_YIN_YAN_TONG, false);
            edit.commit();
        } else {
            iv_prompt.setVisibility(View.GONE);
        }
        show_top_banner();
        iv_cicle_bg = (ImageView) v.findViewById(R.id.iv_cicle_bg);
        int[] sc = ScreenUtil.get_screen_size(getActivity());
        int w1 = sc[0];
        int h1 = w1;
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        iv_cicle.setLayoutParams(p);
        iv_cicle_bg.setLayoutParams(p);
        int padding = DisplayUtil.dip2px(getActivity(), ciccle_pading);
        iv_cicle_bg.setPadding(padding, padding, padding, padding);
        Glide.with(this).load(R.drawable.yin_yan_tong_green_cicle).into(iv_cicle_bg);
        anim();
        iv_cicle.setCallback(new SlidCircleView.Callback() {

            @Override
            public void up() {
                try {
                    if (MyUtil.isAppInstalled(getActivity(), "com.boc.bocop.container")) {
                        _timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);

                            }
                        }, 2000);
                    } else {
                        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(Constants.UrlForZyys));
                        startActivity(viewIntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void down() {

                Helper.startActivity(getContext(), FrgYYlTong.class, NoTitleAct.class, "data", module);
//                Yan_Jing_Rong_Main_Act.startAct(getActivity());

            }
        });

        rl_vp_container.setVisibility(View.INVISIBLE);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frame.HANDLES.close("FrgYhtHome");
                getActivity().finish();
            }
        });
        return v;
    }


    int[] resGif = {
            R.drawable.bg_pic_yin_yan_tong_green_gif_01,
            R.drawable.bg_pic_yin_yan_tong_green_gif_02,
            R.drawable.bg_pic_yin_yan_tong_green_gif_03,
            R.drawable.bg_pic_yin_yan_tong_green_gif_04,
            R.drawable.bg_pic_yin_yan_tong_green_gif_05,
            R.drawable.bg_pic_yin_yan_tong_green_gif_06,
            R.drawable.bg_pic_yin_yan_tong_green_gif_07,
            R.drawable.bg_pic_yin_yan_tong_green_gif_08,
            R.drawable.bg_pic_yin_yan_tong_green_gif_09,
            R.drawable.bg_pic_yin_yan_tong_green_gif_10,
            R.drawable.bg_pic_yin_yan_tong_green_gif_11,
    };

    int position = 0;
    int last = 0;

    class GifRunnable implements Runnable {
        @Override
        public void run() {
            iv_cicle.setImageResource(resGif[position]);

            if (position == 10) {
                last++;
                if (last == 7) {
                    last = 0;
                    position = 0;
                }
            } else {
                position++;
            }
        }
    }

    GifRunnable gifRunnable = new GifRunnable();

    private void anim() {
        _timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);

            }
        }, 0, 100);
    }

    public static final int[] top_banner_pics = {
            R.drawable.banner_pic_yin_yan_tong_ze_ren,
    };

    private void show_top_banner() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        int[] sc = ScreenUtil.get_screen_size(getActivity());
        List<View> vs = new ArrayList<>();
        int w = sc[0];
        int h = (int) (30.0 / 72 * sc[0]);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w, h);
        rl_vp_container.setLayoutParams(p);
        for (int res : top_banner_pics) {
            ImageView iv = (ImageView) inflater.inflate(R.layout.act_main_gan_jiang_xin_qu_fragment_top_banner_iv,
                    null);
            iv.setLayoutParams(p);
            FImageloader.load_by_resid(getActivity(), res, iv);
            vs.add(iv);
        }
        vp_top_banner.setAdapter(new ViewPagerLoopAdapter(vs));
        vp_indicator.setViewPager(vp_top_banner);
        vp_indicator.setVisibility(View.INVISIBLE);
        loop_banner();
    }

    int banner_position = 0;

    public void loop_banner() {
        _timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);

            }
        }, 3000, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cicle: {
                int[] sc = ScreenUtil.get_screen_size(getActivity());
                int r = (int) (1.0 * sc[0] / 2 - DisplayUtil.dip2px(getActivity(), ciccle_pading));
                int c_x = (int) (1.0 * iv_cicle.getWidth() / 2);
                int c_y = c_x;
                int rl_x = (int) (iv_cicle_X - c_x);
                int rl_y = (int) (-iv_cicle_Y + c_y);
                if (Math.abs(rl_x) * Math.abs(rl_x) + Math.abs(rl_y) * Math.abs(rl_y) > r * r) {
                    return;
                }
                int area = 0;
                if (rl_x > 0 && rl_y < 0) {
                    area = 2;
                } else if (rl_x < 0 && rl_y > 0) {
                    area = 1;
                } else if (rl_x > 0 && rl_y > 0) {
                    if (Math.abs(rl_y) / Math.abs(rl_x) > 1.0) {
                        area = 1;
                    } else {
                        area = 2;
                    }
                } else {
                    if (Math.abs(rl_y) / Math.abs(rl_x) > 1.0) {
                        area = 2;
                    } else {
                        area = 1;
                    }
                }

                if (area == 1) {
                    Yan_Tobacco_Main_Act.startAct(getActivity());
                } else if (area == 2) {
                    Yan_Jing_Rong_Main_Act.startAct(getActivity());
                }
                break;
            }
            default:
                break;
        }
    }

}
