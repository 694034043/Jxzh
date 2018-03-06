package com.bocop.zyt.bocop.gjxq.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

public class ResourceUtil {

	public static int getLayoutId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "layout", paramContext.getPackageName());
	}

	public static int getStringId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "string", paramContext.getPackageName());
	}

	public static int getDrawableId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "drawable", paramContext.getPackageName());
	}

	public static int getStyleId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "style", paramContext.getPackageName());
	}

	public static int getId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "id", paramContext.getPackageName());
	}

	public static int getColorId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "color", paramContext.getPackageName());
	}

	public static int getArrayId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "array", paramContext.getPackageName());
	}

	public static int getAttrId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "attr", paramContext.getPackageName());
	}

	public static Bitmap getBitmap(Context paramContext, String paramString) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(paramContext.getAssets().open(paramString));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
