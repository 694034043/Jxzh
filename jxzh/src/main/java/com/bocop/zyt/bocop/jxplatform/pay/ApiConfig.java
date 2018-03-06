package com.bocop.zyt.bocop.jxplatform.pay;

import java.util.HashMap;
import java.util.Map;

/**
 * ${name}
 * ${description}
 *
 * @author yanjun
 * @since 0.0.1
 */

public class ApiConfig {
	
    //public static final String API_URL = "http://kary.zeronezerone.com/gateway.html";
    public static final String API_ALIPAY_URL = "https://api.boc.dcorepay.com/alipay/appcreate";
    
    public static final String API_WX_URL = "https://api.boc.dcorepay.com/pay/unifiedorder";
    
    public static final String WX_APPID = "wx1559c98f3806f393";
    //public static final String WX_APPID = "wx2802c5c4bd3e35fd";

    public static final String API_PARTNER_ID = "test";

    //public static final String API_SECRET_KEY = "06f7aab08aa2431e6dae6a156fc9e0b4";
    public static final String API_ALIPAY_SECRET_KEY = "ec0f67f1fdd82ceb05ea34b732c439af";
    public static final String API_WX_SECRET_KEY = "c178f1157d5ad6e3cd0d094f11e2034c";
    
    public static final String API_VERSION = "1.0";

    public static final String API_SIGN_TYPE = "MD5";

    public static final String API_PROTOCOL = "HTTP_FORM_JSON";

    public static final Map<String, String> API_COMMON_PARAMS;

    static {
        API_COMMON_PARAMS = new HashMap<>();
        API_COMMON_PARAMS.put("partnerId", API_PARTNER_ID);
        API_COMMON_PARAMS.put("version", API_VERSION);
        API_COMMON_PARAMS.put("signType", API_SIGN_TYPE);
        API_COMMON_PARAMS.put("protocol", API_PROTOCOL);
    }
}
