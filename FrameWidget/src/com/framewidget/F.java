package com.framewidget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public class F {

	// kfc 1
	// / 关闭软件盘
	public static void closeSoftKey(Activity act) {
		InputMethodManager localInputMethodManager = (InputMethodManager) act
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		IBinder localIBinder = act.getWindow().getDecorView().getWindowToken();
		localInputMethodManager.hideSoftInputFromWindow(localIBinder, 2);
	}

	public static List<String> getData() {
		List<String> datas = new ArrayList<String>();
		for (int i = 0; i < 15; i++) {
			datas.add("11111111");
		}
		return datas;
	}

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param context
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
	public static void yShoure(Activity act, String title, String content,
							   DialogInterface.OnClickListener click) {
		new AlertDialog.Builder(act).setTitle(title).setMessage(content)
				.setPositiveButton("是", click).setNegativeButton("否", null)
				.show();

	}
	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		// return new String(c);
		return stringFilter(new String(c));
	}

	/**
	 * 去除特殊字符或将所有中文标号替换为英文标号
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]")
				.replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static byte[] bitmap2Byte(String picpathcrop) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		com.mdx.framework.utility.BitmapRead.decodeSampledBitmapFromFile(
				picpathcrop, 720, 0).compress(Bitmap.CompressFormat.JPEG, 100,
				out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toByteArray();
	}

}
