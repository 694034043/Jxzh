package com.bocop.zyt.jx.constants;


import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;

/**
 *
 * @author xmtang
 *
 */
public class Constants {

    public static final String SHARED_PREFERENCE_NAME = "RIDERS_SHARED_PREFERENCE";

    public static final String SHARED_CUSTOM_INFO = "CUSTOM_INFO";
    public static String UrlForMainQyKhtcard = BocSdkConfig.qztUrl + "/companyFinance/h5/index.html#/openAccount/main?"; //企业开户通
    public static final String CUSTOM_PREFERENCE_NAME = "CUSTOM_PREFERENCE_NAME";
    public static final String CUSTOM_ID_NO = "CUSTOM_ID_NO";
    public static final String CUSTOM_MOBILE_NO = "CUSTOM_MOBILE_NO";
    public static final String CUSTOM_USER_NAME = "CUSTOM_USER_NAME";
    public static final String CUSTOM_CUS_ID = "CUSTOM_CUS_ID";
    public static final String CUSTOM_PUT_ALREADY = "CUSTOM_PUT_ALREADY";
    public static final String CUSTOM_FLAG = "CUSTOM_FLAG";
    public static final String CUSTOM_LOG_FLAG = "CUSTOM_LOG_FLAG";
    public static String xmsUrlForJJS = "https://longshortfx.boc.cn/index.html";//  基金净值
    public static final String XMS_AD = "XMS_AD";
    // public static final String TOKEN = "csrftoken";
    public static final String SESSION_ID = "sessionid";
    public static final boolean isChooseSingleModule = false;   //是否需要读取默认模块版本
    public static final String defaultInviteCode = "kdt"; // 默认模块邀请码
    public static final String[] moduleKeyStore = new String[]{"yqt","yzt","yztyt","kdait","ytt","yct","yyt","ywt","ygt","fpt","jyt","yyaot","all"};  //模块密钥

    public static final String Fun_Gangjiangxinqu = "yqt";
    public static final String Fun_Jingdezhen = "yzt";
    public static final String Fun_yzt_ytzs = "yztyt";
    public static final String Fun_Yintietong = "ytt";
    public static final String Fun_Yincitong = "yct";
    public static final String Fun_Yinyantong = "yyt";
    public static final String Fun_Yinwentong = "ywt";
    public static final String Fun_Yingongtong = "ygt";
    public static final String Fun_fupingtong = "fpt";
    public static final String Fun_Yinjuntong = "jyt";
    public static final String Fun_Yinyaotong = "yyaot";
    public static final String Fun_kedaitong = "kdait";
    public static final String Fun_Yinbitong = "ybt";

    //银瓷通优质企业分类key
    public static final String[] yct_mall_first_key = new String[]{
            "茶器","餐具","摆件","花器","酒器","文房","咖啡具"
    };
    public static final String[][] yct_mall_second_key = new String[][]{
            {"茶具套组","茶杯配件","保温杯"},
            {"餐具单品","餐具套组"},
            {"香器","储物罐","佛像","流水摆件"}
    };

    public static String LOCK_PATTERN_FILE = "password.key";
    /**
     * 登录token
     */
    public static final String ACCESS_TOKEN = "access_token";
    /**
     * SharedPreference 中的字段 保存是否已经登录
     */
    public static final String LOGINED_IN = "logined_in";
    /**
     * SharedPreference 中的字段 保存登录的用户名
     */
    public static final String USER_NAME = "username";
    /**
     * 登录userid
     */
    public static final String USER_ID = "userid";
    /**
     * 定位是否成功标识
     */
    public static final String LOCATE_STATUS = "locate_status";
    /**
     * 当前位置经度
     */
    public static final String LNG_CURRENT_LOCATION = "lng_current_location";
    /**
     * 当前位置纬度
     */
    public static final String LAT_CURRENT_LOCATION = "lat_current_location";
    /**
     * 当前位置纬度
     */
    public static final String AUDIO_INTRODUCTION = "audio_introduction_auto_play";
    /**
     * 是否需要登录
     */
    public static final String NEED_LOGIN = "need_login";
    /**
     * 中行卡号
     */
    public static final String BANK_CARDNO = "bank_cardno";
    /**
     * 身份证号
     */
    public static final String ID_NO = "id_no";
    /**
     * 手机号
     */
    public static final String MOBILENO = "mobile_No";
    /**
     * 当前城市
     */
    public static final String CURRENT_CITY = "current_city";

    public static boolean isActive = true;//程序是否在前台
    public static boolean handFlg = true;//是否已经解锁

    //日志标志
    public static String eventId = "eventId";//  key
    public static String logSys = "yht";//  key
    public static String login = "login";//  登陆时间

    public static String UrlForZyys = "http://open.boc.cn/appstore/#/app/appDetail/299";
    //public static String UrlForZyys = "http://a.app.qq.com/o/simple.jsp?pkgname=cn.swiftpass.enterprise.v3.boc.and";


    //小秘书咨询链接
    public static String xmsUrlForDotbooking = "https://mbs.boc.cn/BOCWapBank/OutletQueryProvinces.do";//  key
    public static String xmsUrlForConsult = "http://wechat.bocichina.com/zygj_weixin/weixin/info/list.jsp?channelid=000100130023&share=1&accountno=gh_ec00b5e1997a";//  key
    public static String xmsUrlForMarket = "http://wechat.bocichina.com/zygj_weixin/weixin/hqboc/list.jsp";//  key
    public static String xmsUrlForDzdp = "http://m.dianping.com/";//  大众点评
    public static String xmsUrlForHx = "http://m.hexun.com/";//  和讯财经
    public static String xmsUrlForLt100 = "http://mobile.lvtu100.com/wap/";//  旅途100
    public static String xmsUrlForExpress= "http://m.kuaidi100.com/index.jsp";// 快递100
    public static String xmsUrlForTranlate= "https://fanyi.baidu.com";// 百度翻译
    //public static String xmsUrlForToutiao= "https://m.toutiao.com";// 头条新闻
    public static String xmsUrlForToutiao= "https://m.toutiao.com/?utm_source=qq_view_detail&hideAll=1&W2atIF=1";// 头条新闻
    public static String xmsUrlForJiudian= "https://touch.qunar.com/hotel";//酒店服务
    public static String xmsUrlForSuning = "http://m.suning.com/";//  苏宁
    public static String xmsUrlForWeather = BocSdkConfig.qztUrl + "/xms/weather.html";//  天气
    public static String xmsUrlForDidi = "http://common.diditaxi.com.cn/general/webEntry";//  滴滴打车
    public static String xmsUrlForFight = "http://touch.qunar.com/h5/flight/";//  购机票
    public static String xmsUrlForTrain = "http://touch.qunar.com/h5/train/?bd_source=boc_e";//  购火车票

    public static String xmsUrlForATM = "http://srh.bankofchina.com/search/wap/atm_l.jsp";//  ATM分布
    public static String xmsUrlForOrg = "http://srh.bankofchina.com/search/wap/opr_l.jsp";//  网点查询
    //public static String xmsUrlForRate = "http://wap.boc.cn/data/rt";//  存贷款利率
    public static String xmsUrlForRate = "http://123.124.191.179/xms/rate.html";//  存贷款利率
    //public static String xmsUrlForExchange = "http://wap.boc.cn/data/fx";//  外币牌价
    //public static String xmsUrlForExchange = "http://q.m.hexun.com/forex/bc_exchange_rate.html";//  外币牌价
    public static String qztUrlForJiudian = "https://www.booking.com/index.zh.html";//  酒店预订
    //  public static String qztUrlForWifi = "https://m.ctrip.com/webapp/activity/wifi?from=singlemessage&isappinstalled=1";//  全球WIFI
    public static String qztUrlForWifi = "https://m.tuniu.com/m2015/wifi/home/Index";//全球Wifi
    public static String xmsUrlForExchange = "http://q.m.hexun.com/forex/bc_exchange_rate.html";//  外币牌价
    public static String xmsUrlForFun = "http://srh.bankofchina.com/search/wap/sr1_i.jsp";//  基金净值

    public static String xmsUrlForBaidu = "https://map.baidu.com/mobile/webapp/index/index/vt=map";//  地图服务
    public static String xmsUrlForTuniu= "https://m.tuniu.com";//  旅游服务
    public static String xmsUrlForJd= "https://m.jd.com";//  网上购物
    public static String xmsUrlForCtrip= "https://m.ctrip.com";//  酒店服务
    public static String xmsUrlFor58Home= "http://m.58.com";//  家政服务
    //public static String xmsUrlForSpider= "http://m.spider.com.cn";// 电影票
    public static String xmsUrlForSpider= "http://m.wepiao.com/";// 电影票
    public static String xmsUrlForZxzd= "http://m.zd.diyifanwen.com";//在线字典
    public static String xmsUrlForFlight = "http://touch.qunar.com/h5/flight/";//航班动态


    public static String chtUrlForTxgc = "http://wx.wevein.com/app/index.php?i=3&c=entry&do=index&m=jy_yht";//  贴息购车
    public static String chtUrlForCxtb = "http://m.bocins.com/#index?mediaSource=bocyihuitong.html";//  // 车险投保

    public static String xmsUrlForTest= "http://720.yunwucm.com/SQS/index.html";// 电影票


    public static String qztUrlForshichang = BocSdkConfig.qztUrl + "/visa/qzt_shichangchaxun.html";//  key
    public static String qztUrlForfeiyong = BocSdkConfig.qztUrl + "/visa/qzt_feiyongchaxun.html ";//  key

    public static String fptUrlForfpsc = BocSdkConfig.qztUrl + "/fpsc/page/index.html";//  扶贫商城
    //public static String fptUrlForqyfp = "http://embipl.epub360.com/v2/manage/book/ltic1b/";//  企业扶贫
    public static String fptUrlForqyfp = "http://3rb1zg.epub360.com.cn/v2/manage/book/zm5qhv/";//  企业扶贫
    public static String fptUrlForqyfpv2 = "http://3rb1zg.epub360.com.cn/v2/manage/book/udoulz/";//  企业扶贫

    public static String qztUrlForChuguo = "http://open.boc.cn/appstore/#/app/appDetail/13620";//  key
    public static String qztUrlForJIncai = BocSdkConfig.qztUrl + "/visa/qzt_jingcaihuodong.html ";//  key

    //证券通
    public static String qztUrlForOpen = "https://wykh.bocichina.com/m/open/index.html#!/business/middlePage.html?banktype=9&paramtype=102&branchno=1052&showOthers=false";// 证券开户
    public static String qztUrlForEtrade = "http://mentry.bocichina.com/special/etrade/phone_download.html";//  证券交易
    public static String xmsUrlForEshop = "http://mentry.bocichina.com/special/eshop/phone_download.html";//  中国红商城


    //首页

    //校园通
    public static String UrlForMainXyt = "http://123.124.191.179/xyt/index.html";//  校园通
    public static String UrlForMainKhtcard = BocSdkConfig.qztUrl + "/kht/index.html";

    //开户通
    public static String UrlForKhtGrQuery = "http://123.124.191.179/kht/report.html";

    //淘宝网
    public static String UrlForMainTbw ="https://m.taobao.com/#index";
    //京东
    public static String UrlForMainJd ="https://m.jd.com/";
    //美团外卖
    public static String UrlForMainMtwm ="http://i.waimai.meituan.com/";
    //百度外卖
    public static String UrlForMainBdwm ="http://waimai.baidu.com/mobile/waimai?qt=confirmcity&amp";

    //中银保险-家财险
    public static String UrlForMainZYBX_JCX = "http://m.bocins.com/property-wap/";
    //中银保险-意外险
    public static String UrlForMainZYBX_YWX = "http://m.bocins.com/eaccidentInsurance/#chooseInsurance";
    //中国人民人寿保险股份有限公司
    public static String UrlForMainYbt ="http://m.picclife.cn/m/index.jhtml?from=singlemessage";
    //中国人民财产保险股份有限公司
    public static String UrlForMainTaiping ="http://baoxian.cntaiping.com/m/";
    //太平人寿保险有限公司
    public static String UrlForMainEpicc ="http://www.epicc.com.cn/m/";

    public static String chtUrlForCht =  BocSdkConfig.qztUrl + "/cht/app/pages/index.html";//  车惠通首页
    public static String chtUrlForSxc =  BocSdkConfig.qztUrl + "/cht/app/pages/index.html#school_list.html";//  新手学车
    public static String chtUrlForGrxx =  BocSdkConfig.qztUrl + "/cht/app/pages/index.html#person_info.html";//  个人信息
    public static String chtUrlForGjj = BocSdkConfig.qztUrl + "/gjt/?userAgent=";// 公积金查询


    public static String ZQTphoto_1 = "https://wechat.bocichina.com/zygj_weixin/weixin/khyl/index_kmh.jsp?actid=81&from=singlemessage&isappinstalled=0";
    public static String ZQThaitong = "http://h5kh.htsec.com/indexnew/p/SxnkqW";
    public static String ZQTguosheng = "https://kh.gsstock.com/osoa/views/downloadPhone.html";
    public static String ZQTdongbei = "https://mkh.nesc.cn/p/99910002";

    public static String ZQThuatai = "https://m.zhangle.com/h5Account/mobile-h5/index.htm?bank=boc&ly=boc01&param1=007180";

    public static String UrlForMainYdt ="http://L4PtMDjv.scene.eqxiu.cn/s/L4PtMDjv";
}
