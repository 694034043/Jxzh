package com.bocop.zyt.bocop.jxplatform.util;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import com.bocop.zyt.bocop.jxplatform.activity.EXYActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.jx.constants.Constants;

import java.util.ArrayList;
import java.util.List;

public class MyUtil {

	public static final String IS_FIRST_OPEN_YIN_YAN_TONG = "is_first_open_yin_yan_tong";

	public static final String city = "{11:\"北京\",12:\"天津\",13:\"河北\",14:\"山西\",15:\"内蒙古\",21:\"辽宁\",22:\"吉林\",23:\"黑龙江 \",31:\"上海\",32:\"江苏\",33:\"浙江\",34:\"安徽\",35:\"福建\",36:\"江西\",37:\"山东\",41:\"河南\",42:\"湖北 \",43:\"湖南\",44:\"广东\",45:\"广西\",46:\"海南\",50:\"重庆\",51:\"四川\",52:\"贵州\",53:\"云南\",54:\"西藏 \",61:\"陕西\",62:\"甘肃\",63:\"青海\",64:\"宁夏\",65:\"新疆\",71:\"台湾\",81:\"香港\",82:\"澳门\",91:\"国外 \"}";

	/**
	 * 获取应用版本号
	 *
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		String versionName = "1.0.0";
		try {
			PackageInfo mPackageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			versionName = mPackageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	public static boolean isAppInstalled(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		ArrayList<String> pName = new ArrayList<String>();
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		return pName.contains(packageName);
	}

	/**
	 * 判断是否为平板
	 * @param context
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static boolean isTablet(Context context) {

		boolean shouldBeTablet = false;

		if (context != null) {

			Configuration conf = context.getResources().getConfiguration();

			shouldBeTablet = (conf.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)

					>= Configuration.SCREENLAYOUT_SIZE_LARGE

					&& conf.smallestScreenWidthDp >= 600;

		}

		return shouldBeTablet;

	}

	/**
	 * 启动第三方 app
	 *
	 * @param mcontext
	 * @param packagename
	 */
	public static void startAppOld(Context mcontext,
								   String packagename) {
		// 通过包名获取此 APP 详细信息，包括 Activities、 services 、versioncode 、 name等等
		PackageInfo packageinfo = null;
		try {
			packageinfo = mcontext.getPackageManager().getPackageInfo(
					packagename, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		if (packageinfo == null) {
			Bundle bundle = new Bundle();
			bundle.putString("url", Constants.UrlForZyys);
			bundle.putString("name", "中银易商");
			Intent intent = new Intent(mcontext, WebActivity.class);
			intent.putExtras(bundle);
			mcontext.startActivity(intent);
		}

		// 创建一个类别为 CATEGORY_LAUNCHER 的该包名的 Intent
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(packageinfo.packageName);

		// 通过 getPackageManager()的 queryIntentActivities 方法遍历
		List<ResolveInfo> resolveinfoList = mcontext.getPackageManager()
				.queryIntentActivities(resolveIntent, 0);

		ResolveInfo resolveinfo = resolveinfoList.iterator().next();
		if (resolveinfo != null) {
			// packagename = 参数 packname
			String packageName = resolveinfo.activityInfo.packageName;
			// 这个就是我们要找的该 APP 的LAUNCHER 的 Activity[组织形式：
			// packagename.mainActivityname]
			String className = resolveinfo.activityInfo.name;
			// LAUNCHER Intent
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);

			// 设置 ComponentName参数 1:packagename 参数2:MainActivity 路径
			ComponentName cn = new ComponentName(packageName, className);

			intent.setComponent(cn);
			mcontext.startActivity(intent);
		} else {
			Intent intent = new Intent(mcontext, EXYActivity.class);
			mcontext.startActivity(intent);
		}
	}
}
