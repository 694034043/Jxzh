package com.bocop.zyt.bocop.zyt.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.boc.bocop.sdk.util.StringUtil;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.gui.fragment.CiCultureFragment;
import com.bocop.zyt.bocop.zyt.gui.fragment.CiFinanceFragment;
import com.bocop.zyt.bocop.zyt.model.YCTBgMusic;
import com.bocop.zyt.bocop.zyt.utils.MediaPlayerUtils;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.IHttpClient;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.frg.FrgModeByCodelTong;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.item.proto.MTopModule;
import com.mdx.framework.Frame;
import com.mdx.framework.activity.NoTitleAct;
import com.mdx.framework.utility.Helper;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by ltao on 2017/2/9.
 */

public class MainAct_Yin_ci_tong_Fragment extends BaseFragment implements View.OnClickListener {

    private String MP3Dir = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/zhongyht";
    String url = IURL.Ci_main_bg_music_URL;
    private View v;
    private TextView tv_actionbar_title;
    public static ArrayList<BaseFragment> fragmentList = new ArrayList<>();
    private ViewPager vp_top_banner;
    private RelativeLayout rl_vp_container;
    private CirclePageIndicator vp_indicator;
    private Timer _timer;
    private ImageView iv_cicle;
    private ImageView iv_finish;
    protected float iv_cicle_X;
    protected float iv_cicle_Y;
    private ScrollView sv_main;
    public static final String CONFIG_NAME = "yi_ci_tong_config";

    private int ciccle_pading = 36;
    private MediaPlayer mPlayer;
    private ImageView iv_music;
    private ImageView iv_gif;
    private Gson gson = new Gson();
    private boolean isFirstIn;
    private MTopModule module;
    private String mp3File;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _timer = new Timer();
        isFirstIn = true;
        module = (MTopModule) getArguments().getSerializable("data");
//        initMusic();
        mp3File = MP3Dir + "/yct.mp3";
        File f = new File(mp3File);
        if (f.exists()) {
            playMp3(mp3File);
        }

        fragmentList.add(new CiCultureFragment());
        //fragmentList.add(new CiMallFragment());
        fragmentList.add(new CiFinanceFragment());
        Intent intent = new Intent(getActivity(),YCTGuideActivity.class);
        startActivityForResult(intent,0x001);
        getActivity().overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }
    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
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
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer = null;
            } finally {
                // TODO: handle finally clause
            }


        }
    }

    private void playMp3(String url) {
        try {
            if (mPlayer == null) {
                mPlayer = new MediaPlayer();
            }

            mPlayer.setDataSource(url);
            mPlayer.setLooping(true);
            mPlayer.setOnPreparedListener(new OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mPlayer != null) {
                        mp.start();
                    }
                }
            });
            mPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMusicId() {
        SharedPreferences sp = getActivity().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return sp.getString("music_id", "");
    }

    private void saveMusicData(YCTBgMusic resBean) {
        SharedPreferences sp = getActivity().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putString("music_id", resBean.getData().getId()).commit();
    }

    private void initMusic() {
        mPlayer = MediaPlayerUtils.create();
        //String url="http://192.168.0.45/push/music.mp3";
        HashMap<String, String> params = new HashMap<>();
        musicId = getMusicId();
        if (!StringUtil.isNullOrEmpty(musicId)) {
            params.put("id", musicId);
        }
        File d = new File(MP3Dir);
        if (!d.exists()) {
            d.mkdirs();
        }
        int p1 = url.lastIndexOf("/");
        final String mp3File = MP3Dir + url.substring(p1);
        IHttpClient.getAysnMainThread(IHttpClient.getDefaultHttpClient(), url, params, new IHttpClient.Callback() {

            @Override
            public void suc(String ret) {
                YCTBgMusic resBean = gson.fromJson(ret, new TypeToken<YCTBgMusic>() {
                }.getType());
                saveMusicData(resBean);
                if (StringUtil.isNullOrEmpty(musicId)) {
                    downloadMp3(resBean.getData().getUrl(), mp3File, new IHttpClient.Callback() {

                        @Override
                        public void suc(String ret) {
                            playMp3(mp3File);
                        }

                        @Override
                        public void fail(String ret) {
                            Log.e(MainAct_Yin_ci_tong_Fragment.class.getName(), ret);
                        }
                    });
                } else if (resBean.getData().isChanged() != null) {
                    File f = new File(mp3File);
                    if (!resBean.getData().isChanged() && f.exists()) {
                        playMp3(mp3File);
                    } else {
                        if (f.exists()) {
                            f.delete();
                        }
                        downloadMp3(resBean.getData().getUrl(), mp3File, new IHttpClient.Callback() {

                            @Override
                            public void suc(String ret) {
                                playMp3(mp3File);
                            }

                            @Override
                            public void fail(String ret) {
                                Log.e(MainAct_Yin_ci_tong_Fragment.class.getName(), ret);
                            }
                        });
                    }
                }
            }

            @Override
            public void fail(String ret) {
                Log.e(MainAct_Yin_ci_tong_Fragment.class.getName(), ret);
                File d = new File(mp3File);
                if (d.exists()) {
                    playMp3(mp3File);
                }
            }
        });
    }

    private void downloadMp3(String url, final String file1, final IHttpClient.Callback callback) {

        final String mp3File = file1 + "temp";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @SuppressWarnings("resource")
            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;

                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(mp3File);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
//						int progress = (int) (sum * 1.0f / total * 100);
//						Log.d("h_bl", "progress=" + progress);

                    }
                    fos.flush();
                    ILOG.log_4_7("h_bl" + "文件下载成功");
                    file.renameTo(new File(file1));
                    callback.suc(mp3File);
                } catch (Exception e) {
                    ILOG.log_4_7("h_bl" + "文件下载失败" + e.toString());
                    callback.fail(e.toString());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                Log.d("h_bl", "onFailure");
                callback.fail("onFailure");
            }

        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.act_main_yin_ci_tong_fragment, null);
        v.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return getActivity().dispatchTouchEvent(event);
            }
        });
        tv_actionbar_title = (TextView) v.findViewById(R.id.tv_actionbar_title);
        tv_actionbar_title.setText("银瓷通");
        iv_music = (ImageView) v.findViewById(R.id.iv_music);
        iv_gif = (ImageView) v.findViewById(R.id.iv_gif);
        iv_finish = (ImageView) v.findViewById(R.id.iv_finish);
        iv_music.setOnClickListener(this);
        vp_top_banner = (ViewPager) v.findViewById(R.id.vp_top_banner);
        rl_vp_container = (RelativeLayout) v.findViewById(R.id.rl_vp_container);
        vp_indicator = (CirclePageIndicator) v.findViewById(R.id.vp_indicator);
        iv_cicle = (ImageView) v.findViewById(R.id.iv_cicle);
        int[] sc = ScreenUtil.get_screen_size(getActivity());
        int w1 = sc[0];
        int h1 = w1;
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        iv_cicle.setLayoutParams(p);
        int padding = DisplayUtil.dip2px(getActivity(), ciccle_pading);
        iv_cicle.setPadding(padding, padding, padding, padding);
        Glide.with(getActivity()).load(R.drawable.gif_bg_yin_ci_tong_2).asGif().centerCrop().into(iv_gif);
        //FImageloader.load_by_resid(getActivity(), R.drawable.cicle_pic_yin_ci_tong, iv_cicle);
        iv_cicle.setOnClickListener(this);
        iv_cicle.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                iv_cicle_X = event.getX();
                iv_cicle_Y = event.getY();
                return false;
            }
        });

        show_top_banner();
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frame.HANDLES.close("FrgYhtHome");
                getActivity().finish();
            }
        });
        return v;
    }

    public static final int[] top_banner_pics = {R.drawable.bg_pic_act_main_yin_ci_tong_fgt_qian_nian_ci_du,
            R.drawable.bg_pic_act_main_yin_ci_tong_fgt_tao_ci_shang_cheng,
            R.drawable.bg_pic_act_main_yin_ci_tong_fgt_tao_ci_jing_rong,
            R.drawable.bg_pic_act_main_yin_ci_tong_fgt_she_hui_ze_ren,};

    private void show_top_banner() {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        int[] sc = ScreenUtil.get_screen_size(getActivity());
        List<View> vs = new ArrayList<>();
        int w = sc[0];
        int h = (int) (34.0 / 70 * sc[0]);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
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

        loop_banner();
    }

    int banner_position = 0;
    private String musicId;

    public void loop_banner() {

        _timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);


            }
        }, 3000, 3000);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isFirstIn) {
            isFirstIn = false;
            return;
        }
        if (mPlayer.isPlaying()) {
            iv_music.setImageResource(R.drawable.icon_music_on);
        } else {
            iv_music.setImageResource(R.drawable.icon_music_off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_music: {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    iv_music.setImageResource(R.drawable.icon_music_off);
                } else {
                    mPlayer.start();
                    iv_music.setImageResource(R.drawable.icon_music_on);
                }
                break;
            }


            case R.id.iv_cicle: {
                int[] sc = ScreenUtil.get_screen_size(getActivity());
                int r = (int) (1.0 * sc[0] / 2 - DisplayUtil.dip2px(getActivity(), ciccle_pading));
                ILOG.log_4_7("x " + iv_cicle.getX() + "  y " + iv_cicle.getY() + " w " + iv_cicle.getWidth());
                ILOG.log_4_7("中心坐标 " + iv_cicle.getWidth() / 2 + "  " + (iv_cicle.getWidth() / 2 + iv_cicle.getY()));
                int c_x = (int) (1.0 * iv_cicle.getWidth() / 2);
                int c_y = c_x;
                ILOG.log_4_7("点击坐标 " + iv_cicle_X + "  " + iv_cicle_Y);
                int rl_x = (int) (iv_cicle_X - c_x);
                int rl_y = (int) (-iv_cicle_Y + c_y);
                ILOG.log_4_7("相对坐标 " + rl_x + "  " + rl_y);
                if (Math.abs(rl_x) * Math.abs(rl_x) + Math.abs(rl_y) * Math.abs(rl_y) > r * r) {
                    ILOG.log_4_7(" 区域  外");
                    return;
                }
                int area = 0;
                if (rl_y <= 0) {
                    if (rl_x >= 0) {
                        area = 2;
                    } else {
                        area = 3;
                    }
                } else {
                    double angle = 1.0 * Math.abs(rl_y) / Math.abs(rl_x);
                    ILOG.log_4_7("angle " + angle);
                    if (angle >= 0.57) {
                        area = 1;
                    } else {
                        if (rl_x >= 0) {
                            area = 2;
                        } else {
                            area = 3;
                        }
                    }
                }
                ILOG.log_4_7("点击 区域 " + area);
                switch (area) {
                    case 1:
//				CiViewPagerActivity.startAct(getActivity(),0,fragmentList);
                        Helper.startActivity(getContext(), FrgModeByCodelTong.class, NoTitleAct.class, "code", "yct1", "name", "千年瓷都");
                        break;
                    case 2:
//				CiViewPagerActivity.startAct(getActivity(),1,fragmentList);
                        CiMallActivity.startAct(getActivity());
                        //Helper.startActivity(getContext(), FrgModeByCodelTong.class, NoTitleAct.class, "code", "yct3", "name", "陶瓷商城");
                        break;
                    case 3:
//				CiViewPagerActivity.startAct(getActivity(),2,fragmentList);
                        Helper.startActivity(getContext(), FrgModeByCodelTong.class, NoTitleAct.class, "code", "yct2", "name", "陶瓷金融");
                        break;

                    default:
                        break;
                }

                break;
            }
            default:
                break;
        }
    }

}
