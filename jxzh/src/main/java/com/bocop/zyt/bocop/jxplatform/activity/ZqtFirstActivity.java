package com.bocop.zyt.bocop.jxplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.bocop.sdk.util.StringUtil;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.bean.Advertisement;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtilAnother;
import com.bocop.zyt.bocop.xms.activity.ZqtActivity;
import com.bocop.zyt.jx.ab.view.sliding.AbSlidingPlayView;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.common.util.AbImageUtil;
import com.bocop.zyt.jx.constants.Constants;

import java.util.ArrayList;
import java.util.List;


public class ZqtFirstActivity extends BaseActivity implements LoginUtilAnother.ILoginListener,OnClickListener{

    private LinearLayout zhongyinguoji;
    private LinearLayout haitong;
    private LinearLayout guosheng;
    private TextView tv_titleName;
    private ImageView iv_title_left;
    private LinearLayout dongbei;
    private LinearLayout huatai;

    private  boolean adFirstDownFinish = false;
    protected BaseActivity baseActivity;
    private List<Advertisement> mAdvList = new ArrayList<Advertisement>();
    /*
    * 轮播图
    * */
    AbSlidingPlayView pv_playview;
    static final int ASPECT_X = 4, ASPECT_Y = 1;

    //广告个数
    private  boolean adfinishFlg = false;
    private Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
		title = StringUtil.isNullOrEmpty(title)?"证券通":title;
        initView();
        setListener();
    }
    
    public static void startAct(Context context) {
		Intent intent = new Intent(context, ZqtFirstActivity.class);
		context.startActivity(intent);
	}

    private void initView() {
        setContentView(R.layout.activity_zqt_first);
        tv_titleName = (TextView) findViewById(R.id.tv_titleName);
        tv_titleName.setText(title);
        iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
        pv_playview = (AbSlidingPlayView) findViewById(R.id.zqt_photos);
        zhongyinguoji = (LinearLayout) findViewById(R.id.zhongyinguoji);
        haitong = (LinearLayout) findViewById(R.id.haitong);
        guosheng = (LinearLayout) findViewById(R.id.guosheng);
        dongbei = (LinearLayout) findViewById(R.id.dongbei);
        huatai = (LinearLayout) findViewById(R.id.huatai);
        findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
        findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
        findViewById(R.id.ll_item_01_02_01).setOnClickListener(this);
        findViewById(R.id.ll_item_01_02_02).setOnClickListener(this);
        findViewById(R.id.ll_item_02_01_01).setOnClickListener(this);
        findViewById(R.id.ll_item_02_01_02).setOnClickListener(this);
        findViewById(R.id.ll_item_02_02_01).setOnClickListener(this);
        findViewById(R.id.ll_item_02_02_02).setOnClickListener(this);
        try{
            initHeaderView();
        }
        catch(Exception ex){

        }
    }

    //线程获取图片后，hander来更新ui
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //完成主界面更新
                    View view = getLayoutInflater().inflate(R.layout.item_adpic, null);
                    ImageView iv = (ImageView) view.findViewById(R.id.iv_photo);
                    iv.setImageBitmap(bitmap);

                    if(!adFirstDownFinish){
                        pv_playview.removeAllViews();
                        adFirstDownFinish = true;
                    }

                    pv_playview.addView(iv);
                    if(adfinishFlg)pv_playview.startPlay();
                    break;
                default:
                    break;
            }
        }
    };
	private String title;

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
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            boolean hasSetted = false;

            @Override
            public void onGlobalLayout() {
                if (hasSetted)
                    return;
                hasSetted = true;
                ViewGroup.LayoutParams params = pv_playview.getLayoutParams();
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
        // 第一张
        Advertisement adv1 = new Advertisement();
        adv1.setImageRes(R.drawable.zqt_photo4);
        adv1.setContent(Constants.ZQTphoto_1);
        // // 网页url
        mAdvList.add(adv1);
        
        Advertisement adv2 = new Advertisement();
        adv2.setImageRes(R.drawable.zqt_photo5);
        adv2.setContent(Constants.ZQTphoto_1);
        // // 网页url
        mAdvList.add(adv2);
        for (Advertisement advertisement : mAdvList) {
            View view = getLayoutInflater().inflate(R.layout.item_adpic,
                    null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_photo);
            iv.setImageResource(advertisement.getImageRes());
            pv_playview.addView(iv);
        }
        pv_playview.startPlay();

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


////         轮播图的点击事件
//         pv_playview.setOnItemClickListener(new AbOnItemClickListener() {
//         @Override
//         public void onClick(int position) {
//         Intent intent = new Intent(ZQTActivity.this,
//                 CyhAdvDetailActivity.class);
//         intent.putExtra("url", mAdvList.get(position).getContent());
//         startActivity(intent);
//         }
//         });

        //         轮播图的点击事件
        pv_playview.setOnItemClickListener(new AbSlidingPlayView.AbOnItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", Constants.qztUrlForOpen);
                bundle.putString("name", "证券开户");
                Intent intent = new Intent(ZqtFirstActivity.this,
                        WebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // 中银国际证券
        zhongyinguoji.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ZqtFirstActivity.this,
                        ZqtActivity.class);
                startActivity(intent);
            }
        });

        haitong.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Bundle bundleConsult = new Bundle();
                bundleConsult.putString("url", Constants.ZQThaitong);
                bundleConsult.putString("name", "海通证券");
                Intent intentConsult = new Intent(ZqtFirstActivity.this,
                        WebActivity.class);
                intentConsult.putExtras(bundleConsult);
                startActivity(intentConsult);

            }
        });

        guosheng.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Bundle bundleConsult = new Bundle();
                bundleConsult.putString("url", Constants.ZQTguosheng);
                bundleConsult.putString("name", "国盛证券");
                Intent intentConsult = new Intent(ZqtFirstActivity.this,
                        WebActivity.class);
                intentConsult.putExtras(bundleConsult);
                startActivity(intentConsult);

            }
        });
        
        dongbei.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Bundle bundleConsult = new Bundle();
                bundleConsult.putString("url", Constants.ZQTdongbei);
                bundleConsult.putString("name", "东北证券");
                Intent intentConsult = new Intent(ZqtFirstActivity.this,
                        WebActivity.class);
                intentConsult.putExtras(bundleConsult);
                startActivity(intentConsult);

            }
        });

        huatai.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Bundle bundleConsult = new Bundle();
                bundleConsult.putString("url", Constants.ZQThuatai);
                bundleConsult.putString("name", "东北证券");
                Intent intentConsult = new Intent(ZqtFirstActivity.this,
                        WebActivity.class);
                intentConsult.putExtras(bundleConsult);
                startActivity(intentConsult);

            }
        });

    }


    // 实现登陆的接口
    @Override
    public void onLogin(int position) {

    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onCancle() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onException() {

    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_item_01_01_01:
			Intent gshqIntent = new Intent(ZqtFirstActivity.this,
                    WebActivity.class);
			gshqIntent.putExtra("url", Constants.xmsUrlForMarket);
			gshqIntent.putExtra("name", "股市行情");
            startActivity(gshqIntent);
			break;
		case R.id.ll_item_01_01_02:
			Intent cjIntent = new Intent(ZqtFirstActivity.this,
                    WebActivity.class);
			cjIntent.putExtra("url", Constants.xmsUrlForConsult);
			cjIntent.putExtra("name", "财经资讯");
            startActivity(cjIntent);
			break;
		case R.id.ll_item_01_02_01:
			Intent zqkhIntent = new Intent(ZqtFirstActivity.this,
                    WebActivity.class);
			zqkhIntent.putExtra("url", Constants.qztUrlForOpen);
			zqkhIntent.putExtra("name", "证券开户");
            startActivity(zqkhIntent);
			break;
		case R.id.ll_item_01_02_02:
			Intent zqjyIntent = new Intent(ZqtFirstActivity.this,
                    WebActivity.class);
			zqjyIntent.putExtra("url", Constants.qztUrlForEtrade);
			zqjyIntent.putExtra("name", "证券交易");
            startActivity(zqjyIntent);
			break;
		case R.id.ll_item_02_01_01:
            Intent gsIntent = new Intent(ZqtFirstActivity.this,
                    WebActivity.class);
            gsIntent.putExtra("url", Constants.ZQTguosheng);
            gsIntent.putExtra("name", "国盛证券");
            startActivity(gsIntent);
			break;
		case R.id.ll_item_02_01_02:
            Intent htIntent = new Intent(ZqtFirstActivity.this,
                    WebActivity.class);
            htIntent.putExtra("url", Constants.ZQThaitong);
            htIntent.putExtra("name", "海通证券");
            startActivity(htIntent);
			break;
		case R.id.ll_item_02_02_01:
             Intent htaiIntent = new Intent(ZqtFirstActivity.this,
                     WebActivity.class);
             htaiIntent.putExtra("url", Constants.ZQThuatai);
             htaiIntent.putExtra("name", "华泰证券");
             startActivity(htaiIntent);
			break;
		case R.id.ll_item_02_02_02:
            Intent dbIntent = new Intent(ZqtFirstActivity.this,
                    WebActivity.class);
            dbIntent.putExtra("url", Constants.ZQTdongbei);
            dbIntent.putExtra("name", "东北证券");
            startActivity(dbIntent);
			break;

		}
	}

}

