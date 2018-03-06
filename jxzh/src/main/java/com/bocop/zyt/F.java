package com.bocop.zyt;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.inputmethod.InputMethodManager;

import com.mdx.framework.Frame;
import com.mdx.framework.config.ApiConfig;
import com.mdx.framework.config.BaseConfig;
import com.mdx.framework.server.api.OnApiInitListener;
import com.mdx.framework.utility.Device;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.bocop.zyt.bocop.jxplatform.util.IApplication.userid;

public class F {
    public static String Verify = "", UserId = "", areaCode = "0", city = "全国", code = "", choosecode = "";

    public static void init() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        if (sp.contains("verify")) {
            Verify = sp.getString("verify", "");
            UserId = sp.getString("userid", "");
        }
        if (sp.contains("code")) {
            code = sp.getString("code", "");
        }
        if (sp.contains("choosecode")) {
            choosecode = sp.getString("choosecode", "");
        }
        // Device.getId()
        ApiConfig.setAutoApiInitParams(new OnApiInitListener() {

            @Override
            public String[][] onApiInitListener(Object... objs) {
                return new String[][]{{"appid", BaseConfig.getAppid()},
                        {"deviceid", Device.getId()}, {"userid", F.UserId},
                        {"areaCode", F.areaCode}, {"verify", F.Verify}};
            }
        });
    }

    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param str 指定的字符串
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }


    public static void saveCity(String city) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        sp.edit().putString("city", city).commit();
    }

    public static String getCity() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        return sp.getString("city", "");
    }

    // kfc 1
    // / 关闭软件盘
    public static void closeSoftKey(Activity act) {
        InputMethodManager localInputMethodManager = (InputMethodManager) act
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        IBinder localIBinder = act.getWindow().getDecorView().getWindowToken();
        localInputMethodManager.hideSoftInputFromWindow(localIBinder, 2);

    }

    /**
     * 验证手机号+电话号码
     *
     * @param @param  mobile
     * @param @return
     * @return boolean
     * @throws
     * @author Administrator
     * @Title: isMobile
     * @Description: TODO
     */
    public static boolean isMobile(String mobile) {
        if (mobile.length() == 11) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 验证身份证号是否符合规则
     *
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }


    public static void Login(String userid, String verify) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        sp.edit().putString("verify", verify).putString("userid", userid)
                .commit();
        F.UserId = userid;
        F.Verify = verify;
    }

    public static void setCode(String code) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        sp.edit().putString("code", code)
                .commit();
        F.code = code;
    }

    public static void setChooseCode(String choosecode) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(Frame.CONTEXT);
        sp.edit().putString("choosecode", choosecode)
                .commit();
        F.choosecode = choosecode;
    }


    public static String go2Wei(Double f) {
        return String.format("%.2f", f);
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

    public static String DEVICEID = "",
            HEADURL = "",
            SHAREURL = "",
            DIMAGEURL = "",
            partnerId = "2088521463498321",
            sellerId = "yunbalu@163.com",
            rsaPrivate = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANAFMtcwDj2JPZCxRgNUHgV8rOcfjKJ+yi+c8Pj99bdv8/E5VhqNdylf3jwhRO8iSxWBdt/BBxchn6oaeqn9v6lHkzZlSW/Zg7QR3P0DjI2hr9i2eUe4gssq429YZyn7sYxeJFGvIatF69AVghkKh1i6dn6gtttB/2xUfX32eMxHAgMBAAECgYAtQZRIAKp4dM0yF3815i0J4I/1UIXWLGNsbGZ4pTe6Ct81kdXLvQ85ryO4TTykEf/n09y63VHflG8aINyi8xOpKehrG/uPuzSbOlANJDD0A6wsf3WccKRqCHeEQvvJhMaIq1YgZFagtclALIgZY9msfxY4Jv84yYbkJ5BxNeMH4QJBAPFLXCGBUjXwWnsxBqYN4gbAVegGPZ6fzU8chtfOOh8YDVsD65nj4LFfrywgTU1C69IR1jTshdqr05lPRdjfNokCQQDcsrOVNnin/IWGhnuDjq6upYizxDum43t0ZrFxn0ixLxHik/8xhRbPHeL4rLRvUv+e+yIPqEuNCZeufmQ+IThPAkEAjZOPAiWzy+wz2rZgVUwuA5IUlagbvZe7yiBNyg+5sIbXR0DcmxXGv6wJxxVI5f1PCpnmmnTw8OYCqKCHtgD6KQJBALb4uQZWOMYdB562VCSH4K8Osj1HzaTZKHsiwK5QvnWXbp6I0KP/kR95ybkdhiJdj2wBDQYqIyj08PpL81PHOuUCQBlF+7YS9np7YXIkZ5tdH7Bcdn2SJmRba6qOMkE+c14+NrZ600RwvwDfCS0M0hv7wEzJ2+ykCNi19sf+8+N6Ohs=",
            rsaAlipayPublic = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB",
            notifyUrl = "http://139.196.200.238:888/shfw/mobilePayNotify",
            mobile = "";

    public static void closeFragement(String fragement) {
        if (Frame.HANDLES.get(fragement) != null
                && Frame.HANDLES.get(fragement).size() > 0) {
            Frame.HANDLES.get(fragement).get(0).close();
        }
    }

}
