package com.bocop.zyt.fmodule.utils;

import android.content.Context;

/**
 * Created by ltao on 2017/2/8.
 */

public class FPackegUtil {

    public static String get_package_name(Context context){
        return context.getPackageName();


//        String versionName = this.getPackageManager().getPackageInfo(
//                pkName, 0).versionName;
//        int versionCode = this.getPackageManager()
//                .getPackageInfo(pkName, 0).versionCode;
//        return pkName + "   " + versionName + "  " + versionCode;
    }
}
