package com.bocop.zyt.fmodule.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bocop.zyt.fmodule.FLOG;


/**
 * Created by ltao on 2017/2/8.
 */

public class FImageloader {
//
//    Glide基本可以load任何可以拿到的媒体资源，如：
//    load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
//    load assets资源：load("file:///android_asset/f003.gif")
//    load raw资源：load("Android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
//    load drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
//    load ContentProvider资源：load("content://media/external/images/media/139469")
//    load http资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
//    load https资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")


    private static String package_name = null;

    public static String getPackage_name(Context context) {
        if (FStringUtil.is_empty(package_name)) {
            package_name = FPackegUtil.get_package_name(context);
        }

        return package_name;

    }

    public static void load_by_resid(Context context, int resid, ImageView imageView) {

        Glide.with(context).load(resid).into(imageView);


    }

    public static int[] load_by_resid_fit_src(Context context, int resid, ImageView imageView) {

        Bitmap b = BitmapFactory.decodeResource(context.getResources(), resid);
        double w = b.getWidth();
        double h = b.getHeight();

        b = null;//回收
        int[] sc = ScreenUtil.get_screen_size(context);
        int w1 = sc[0];
        int h1 = 0;
        if (w >= h) {
            h1 = (int) (h / w * sc[0]);
        } else {
            h1 = (int) (h / w * sc[0]);
        }

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w1, h1);
        imageView.setLayoutParams(p);
        Glide.with(context).load(resid).into(imageView);

        int[] ret=new int[2];
        ret[0]=w1;
        ret[1]=h1;
        return ret;

    }

    public static void load_by_resid_fit_src_fl(Context context, int resid, ImageView imageView) {

        Bitmap b = BitmapFactory.decodeResource(context.getResources(), resid);
        double w = b.getWidth();
        double h = b.getHeight();

        b = null;//回收
        int[] sc = ScreenUtil.get_screen_size(context);
        int w1 = sc[0];
        int h1 = 0;
        if (w >= h) {
            h1 = (int) (h / w * sc[0]);
        } else {
            h1 = (int) (h / w * sc[0]);
        }

        FLOG.log("hh-hh", "h1 " + h1);

        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(w1, h1);
        imageView.setLayoutParams(p);
        Glide.with(context).load(resid).into(imageView);


    }

    public static void load_by_resid_fit_src_rl(Context context, int resid, ImageView imageView) {

        Bitmap b = BitmapFactory.decodeResource(context.getResources(), resid);
        double w = b.getWidth();
        double h = b.getHeight();

        b = null;//回收
        int[] sc = ScreenUtil.get_screen_size(context);
        int w1 = sc[0];
        int h1 = 0;
        if (w >= h) {
            h1 = (int) (h / w * sc[0]);
        } else {
            h1 = (int) (h / w * sc[0]);
        }

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
        imageView.setLayoutParams(p);
        Glide.with(context).load(resid).into(imageView);


    }
    public static void load_by_resid_fit_src_rl(Context context, int resid, ImageView imageView,RelativeLayout.LayoutParams p) {
    	
    	Bitmap b = BitmapFactory.decodeResource(context.getResources(), resid);
    	double w = b.getWidth();
    	double h = b.getHeight();
    	
    	b = null;//回收
    	int[] sc = ScreenUtil.get_screen_size(context);
    	int w1 = sc[0];
    	int h1 = 0;
    	if (w >= h) {
    		h1 = (int) (h / w * sc[0]);
    	} else {
    		h1 = (int) (h / w * sc[0]);
    	}
    	
//    	RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
    	p.width=w1;
    	p.height=h1;
    	imageView.setLayoutParams(p);
    	Glide.with(context).load(resid).into(imageView);
    	
    	
    }
}
