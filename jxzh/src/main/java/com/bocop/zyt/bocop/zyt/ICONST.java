package com.bocop.zyt.bocop.zyt;

/**
 * Created by ltao on 2017/2/8.
 */

public class ICONST {

    public static final int Fun_Gangjiangxinqu = 0;
    public static final int Fun_Jingdezhen = 1;
    public static final int Fun_Yintietong = 2;
    public static final int Fun_Yincitong = 3;
    public static final int Fun_Yinyantong = 4;
    public static final int Fun_Yinwentong = 5;
    public static final int Fun_Yingongtong = 6;
    public static final int Fun_fupingtong = 7;
    public static final int Fun_Yinjuntong = 8;
    public static final int Fun_Yinyaotong = 9;
    public static final int Fun_Yinbitong = 10;


    public static final String DB_General = "DB_General";


    public static final String Text_App_Name = "易互通";
    public static final String YQT_Module_Name = "银区通";
    public static final String YZT_Module_Name = "银政通";

    public static final String Baidu_Map_Key = "8BB7F0E5C9C77BD6B9B655DB928B74B6E2D838FD";


    public static class BankConfig {

        public static final String APP_VERSION = "1.0.0";
        public static String CONSUMER_KEY = "481";//生产环境
        public static String CONSUMER_SECRET = "ada12ea325838d021a026fece39e65d35bad52b49528d22232";//生产环境
        public static int CONSUMER_PORT = 443;        //生产环境
        public static String CONSUMER_URL = "https://openapi.boc.cn";//生产环境

        public final static String YFX_LOGIN_MCISCSP = CONSUMER_URL + "/mciscsp";// 工薪贷环境
        public final static String LOGIN_MCISCSP = CONSUMER_URL  + "/mciscsp";
        public final static String YFX_LOGIN_MCISCSP_TEST = CONSUMER_URL + ":8080/mciscsp";// 工薪贷测试环境
        public static final String YFX_PATH = "http://127.0.0.1:9080/jxyrt/app/";
        public static final String YFX_IMG_LIST = YFX_PATH + "proList.do?method=query";
        public static final String ZYYR_SERVE = "http://123.124.191.179/jxyrt/app/";
        public static final String ZYYR_PDT_LIST = ZYYR_SERVE + "proList.do?method=proList";
        public static final String ZYYR_PDT_DETAILS = ZYYR_SERVE + "proInfo.do?method=proInfo";
        public static final String ZYYR_LOAN_LIST = ZYYR_SERVE + "myProList.do?method=myProList";
        public static final String ZYYR_LOAN_DETAILS = ZYYR_SERVE + "myProInfo.do?method=myProInfo";
        public static final String ZYYR_DELETE_LOAN = ZYYR_SERVE + "delPro.do?method=delPro";
        public static final String ZYYR_APPLY_LOAN = ZYYR_SERVE + "applyPro.do?method=applyPro";
        public static final String ZYYR_LIST_PARA = ZYYR_SERVE + "listPara.do?method=listPara";
        // 查询认证资料
        public static final String ZYYR_QUERY_USERINFO = ZYYR_SERVE + "userInfo.do?method=userInfo";
        // 修改、添加认证资料
        public static final String ZYYR_MODIFY_USERINFO = ZYYR_SERVE + "modify.do?method=modify";
        // 检查是否认证资料
        public static final String ZYYR_CHCEK_USERINFO = ZYYR_SERVE + "checkUser.do?method=checkUser";

        /*
     * 是否启用注册功能（添按钮）
	 */
        public static Boolean CONSUMER_IS_REGISTER = true;
        /*
	 * 注册地址
	 */
        public static String CONSUMER_REGISTER = "https://open.boc.cn/wap/register.php?act=perregister";
        public static boolean isTest=false;
    }

}
