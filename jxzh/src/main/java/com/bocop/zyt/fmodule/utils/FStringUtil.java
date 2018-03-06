package com.bocop.zyt.fmodule.utils;

/**
 * Created by ltao on 2017/2/8.
 */

public class FStringUtil {

    public static boolean is_empty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String turn_string(String s) {

        if (is_empty(s)) {
            return "";
        } else {
            return s;
        }
    }
}
