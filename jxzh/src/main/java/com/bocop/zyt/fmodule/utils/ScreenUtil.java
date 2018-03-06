package com.bocop.zyt.fmodule.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by ltao on 2017/2/13.
 */

public class ScreenUtil {

    public static int[] get_screen_size(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int[] ret = new int[2];
        ret[0] = width;
        ret[1] = height;

        return ret;

    }
}
