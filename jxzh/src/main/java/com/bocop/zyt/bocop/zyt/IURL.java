package com.bocop.zyt.bocop.zyt;


import android.content.Context;

import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.jx.baseUtil.util.SignRequestVerify;

/**
 * Created by ltao on 2017/2/8.
 */

public class IURL {

    public static String Bank_Host="http://123.124.191.179";
    public static String SanTai_Test_Host="http://182.106.128.196:80";
    public static String SanTai_Test_Host2="http://119.29.107.253:8080";
    public static String SanTai_Test_Host3="http://118.31.14.52:8080";
    public static String SanTai_Host_zyhtbank = Bank_Host+"/zyhtbanking";
    public static String SanTai_Host_yhtFrontEnd = SanTai_Test_Host3+"/yhtFrontEnd";
    public static String SanTai_Host_openaccount = Bank_Host+"/OpenAccount";
    //public static String SanTai_Host="http://119.29.107.253:8080";

    public static final String Bank_Open_Host = "https://open.boc.cn/wap";
    public static final String Bank_Regist = Bank_Open_Host + "/register.php?clientid=" + ICONST.BankConfig.CONSUMER_KEY + "&themeid=1";
    public static final String Bank_Card_Manage = Bank_Open_Host + "/cardmange.php?clientid=" + ICONST.BankConfig.CONSUMER_KEY + "&themeid=1&devicetype=1";
    public static final String Bank_Pass_Manage = Bank_Open_Host + "/pwdedit.php?clientid=" + ICONST.BankConfig.CONSUMER_KEY + "&themeid=1&devicetype=1";
    public static final String Bank_Pass_Back = "https://open.boc.cn/bocop/#/app/getPwd/";

    public static final String Bank_Kai_Hu_Tong_02 = Bank_Host+"/kht/index.html";
    public static final String Bank_Kai_Hu_Tong = Bank_Host+"/companyFinance/h5/index.html#/openAccount/main?type=kht&";
    public static final String Bank_Qi_Dai_Dai = Bank_Host+"/companyFinance/h5/index.html#/enterpriseLoan/main?type=qdt&";
    public static final String Bank_Dan_Zheng_Tong = Bank_Host+"/companyFinance/h5/index.html#/letterGuarantee/main?type=xzt&";
    public static final String Bank_Bao_Han_Tong = Bank_Host+"/companyFinance/h5/index.html#/creditCard/main?type=bht&";
    public static final String Bank_Ban_Ka_Tong = "https://apply.mcard.boc.cn/apply/mobile/index";//办卡通
    //public static final String Bank_Cai_Fu_Tong = Bank_Host+"/cft/index.html";
    public static final String CFT = Bank_Host + "/gjs/cft/securityCall.html?";
    public static final String Bank_Jiao_Fei_Tong = "http://219.141.191.126:80/conPayH5/?channel=jxeht";//
    public static final String Bank_Sou_Hui_Tong = "https://openapi.boc.cn/af/mobileHtml/html/jiehui/sellExchange.html?channel=android";
    public static final String Bank_Gou_Hui_Tong = "https://openapi.boc.cn/af/mobileHtml/html/gouhui/buyExchange.html?channel=android";
    public static final String Bank_Li_Cai_Tong = "https:/openapi.boc.cn/ezdb/mobileHtml/html/userCenter/index.html?channel=android";

    public static String getBankCaiFuTong(Context context){
        String userId = LoginUtil.getUserId(context);
        String sign= SignRequestVerify.md5SignRequestVerify(userId,"yht","aa37228#$%@@11!!@");
        return CFT + "openId=" + userId + "&authToken=26fa5fbda9cd476d899432dad4c5a982&authApp=yht&nickName=" +  userId + "&sign=" + sign;
    }


    public static String Bank_Ge_Dai_Tong(String userId, String token) {
        return "http://219.141.191.126:80/FM/index.jsp#/yd/" + userId + "/" + token + "/jxeht/196";
    }

    //public static final String Bank_ZHong_Xiao_Tong = "http://L4PtMDjv.scene.eqxiu.cn/s/L4PtMDjv";
    public static final String Bank_ZHong_Xiao_Tong = "http://JDPkWhAX.scene.eqxiu.cn/s/JDPkWhAX";
    public static final String Bank_Bi_DAI_Tong = "http://g.eqxiu.com/s/veL5wWAA";
    public static final String Bank_ZHong_Xiao_e_Dai = "http://u.eqxiu.com/s/CuOq2Qhp";
//    public static final String Bank_Tie_Lu_Zhong_Xiao_Dai = "http://NOzXDKNY.scene.eqxiu.cn/s/NOzXDKNY";


    public static final String Bank_Tie_Lu_Xiang_Mu_Dai = "http://qwerb.cn:8080/companyFinance2/h5/index.html#/enterpriseLoan/main?type=qdt";
    public static final String Bank_Tie_Lu_Zhong_Xiao_Dai = "http://NOzXDKNY.scene.eqxiu.cn/s/NOzXDKNY";
    public static final String Bank_Wen_Zhong_Xiao_Qi_Ye_Dai = "http://h.eqxiu.com/s/S3FQUYTF";
    public static final String Bank_Hong_Yun_Dang_Tou = Bank_Host+"/llyh";
    public static final String Bank_Yu_E_Li_Cai = "https:/openapi.boc.cn/ezdb/mobileHtml/html/userCenter/index.html?channel=android";

    //public static final String Bank_Cai_Fu_Guan_Jia_Tong = Bank_Host+"/cft/index.html";
    public static final String Bank_gong_xiao_e_jia = "http://m.gxyj.com/homepage/index.jhtml?mallId=00001187&category=38";

    public static final String Bank_Zai_Xian_Jiao_Fei_Tong = "http://219.141.191.126:80/conPayH5/?channel=jxeht";//在线缴费
    public static final String Bank_Jing_Pin_Gou = "https://jf365.boc.cn/BOCCMALL_M/index.do";//
    public static final String Bank_Hai_Tao_Gou = Bank_Host+"/htzq/index.html";//
    public static final String Bank_Ju_Hui_Gou = "http://jxboc.uni-infinity.com/wxyx/wx/page/forword/jingxuan.ht?authToken=26fa5fbda9cd476d899432dad4c5a982&authApp=yht";//
    public static final String Bank_Bian_Jie_Lv_You = Bank_Host+"/jxlytApp/appPage/index.html";//

    public static final String Bank_Ci_cheng_de_xuan= "https://chengdexuan.tmall.com/shop/view_shop.htm?spm=a220m.1000862.1000730.1.CEcwMZ&user_number_id=1880489862&rn=3aa50c4f449790fce81f7f8ef5d4e93e";
    public static final String Bank_Yin_Wen_tong = "http://x.eqxiu.com/s/vP5c7ZKI";
    public static final String Bank_jing_dong = "https://www.jd.com/";
    public static final String Bank_tian_mao = "https://www.tmall.com";
    public static final String Bank_tao_bao = "https://www.taobao.com";

    public static final String Bank_Ci_shang_cheng = "http://www.taoworld.xin/walty/index.php/Openplateform/resultUser";//
    public static final String Bank_Ci_hong_ye = "http://mall.cjdzpf.com";
    public static final String Bank_Ci_bao_ci_lin = "https://baocilin.tmall.com/?spm=a1z10.1-b.1997427721.d4918089.NBtVb9";
    public static final String Bank_Ci_fu_yu = "https://fuyujjry.tmall.com/?spm=a220o.1000855.w11224950-15648578423.3.eIjP5S&scene=taobao_shop";
    public static final String Bank_Ci_jing_zhi_yao = "https://weidian.com/?userid=362985763&wfr=c&from=groupmessage&isappinstalled=0";
    public static final String Bank_Ci_ming_fang_yuan = "http://www.jdzcip.net/?from=singlemessage&isappinstalled=0#/main";
    public static final String Bank_Ci_le_xiang_tao_ci = "http://mix.jd.com/v/C_ZSQuGNV6Uhrewg.html";
    public static final String Bank_Ci_ju_ya_ge = "https://shop125391441.taobao.com/?spm=a230r.7195193.1997079397.2.8OGvSZ"; //聚雅阁
    public static final String Bank_Ci_ya_yu_tao_ci_deng_shi = "https://yytcd.jiyoujia.com/shop/view_shop.htm?spm=a1z10.1-c.0.0.3JkJbq"; //雅玉陶瓷灯饰
    public static final String Bank_Ci_ya_yu_tao_ci_yi_shu = "https://yytcd3.taobao.com/?spm=a1z10.3-c.0.0.PKilcG"; //雅玉陶瓷艺术
    public static final String Bank_Ci_LUYE_qi_jian_dian = "https://luye.tmall.com/"; //luke旗舰店
    public static final String Bank_Ci_jin_de_zheng_jia_ju = "https://jingdezhenjj.tmall.com/shop/view_shop.htm?spm=a220m.1000862.1000730.2.iJqhwB&user_number_id=2278967491&rn=57cbe0c1a1cdf1692ed9983db65c8c85"; //景德镇家具
    public static final String Bank_Ci_cheng_ci = "https://jdchengci.kuaizhan.com/96/24/p388548735f7733?from=singlemessage&isappinstalled=0"; //诚瓷
    public static final String Bank_Ci_ci_shang_gong_fu = "https://cishanggongfu.tmall.com/?spm=a220o.1000855.1997427133.d4918065.7XtpW8";
    public static final String Bank_Ci_chang_nan_tao_ci = "https://changnanjj.tmall.com/?spm=a220o.1000855.1997427133.d4918065.rAfB41";
    public static final String Bank_Ci_yi_qin_tang = "https://yiqintangjj.tmall.com/?spm=a220o.1000855.1997427133.d4918065.ylh3zz";
    public static final String Bank_Ci_huan_chang_tao_ci = "https://huanchang.tmall.com/?spm=a1z10.1-b-s.1997427721.d4918089.ehOKiq";
    public static final String Bank_Ci_bo_yi_tao_ci = "http://www.boyi-china.com/";

    public static final String Bank_Ci_ni_zhi_ge = "https://nzgtaoci.taobao.com/index.htm?spm=a1z10.1-c.w5002-10733088113.26.617be950qe1PbG";
    public static final String Bank_Ci_tang_long = "https://tanglongtaoci.taobao.com";
    public static final String Bank_Ci_tao_xing = "http://taoxingtianxia.tmall.com";
    public static final String Bank_Ci_xiong_yao = "https://shop365329797.taobao.com/";
    public static final String Bank_Ci_zhong_jia = "https://shop149758637.taobao.com/shop/view_shop.htm?spm=a313o.7775918.a1zvx.d53.657d8753XdyGz8&mytmenu=mdianpu&user_number_id=2824422045";

    public static final String Bank_Ci_lwyjzmd = "https://luoweiyj.tmall.com/shop/view_shop.htm";
    public static final String Bank_Ci_lxjjqjd = "https://lexiangjj.tmall.com/shop/view_shop.htm?spm=a230r.7195193.1997079397.93.o8VYt1";
    public static final String Bank_Ci_py = "https://beiyu.tmall.com/shop/view_shop.htm";
    public static final String Bank_Ci_sqt = "https://sanqintang.tmall.com/shop/view_shop.htm?spm=a230r.7195193.1997079397.58.o8VYt1";
    public static String Bank_ci_mall_yzqy_more = SanTai_Host_zyhtbank +"/h5/page/chinastore/index.jsp";

    //银区通（原赣江新区，之前没做渠道参数用得是赣江新区）
    public static String Gan_kai_hu_tong=SanTai_Host_zyhtbank+"/h5/page/openAccount/index.html?platform=yqt";
    public static String Gan_qi_dai_tong=SanTai_Host_zyhtbank+"/h5/page/enterpriseLoan/index.html?platform=yqt";
    public static String Gan_bao_han_tong=SanTai_Host_zyhtbank+"/h5/page/creditCard/index.html?platform=yqt";
    //public static String Gan_bao_han_tong=SanTai_Host_zyhtbank+"/h5/page/bill/index.html?platform=yqt";
    public static String Gan_dan_zheng_tong=SanTai_Host_zyhtbank+"/h5/page/letterGuarantee/index.html?platform=yqt";
    //银政通
    public static String Jing_kai_hu_tong=SanTai_Host_zyhtbank+"/h5/page/openAccount/index.html?platform=yzt";
    public static String Jing_qi_dai_tong=SanTai_Host_zyhtbank+"/h5/page/enterpriseLoan/index.html?platform=yzt";
    public static String Jing_bao_han_tong=SanTai_Host_zyhtbank+"/h5/page/creditCard/index.html?platform=yzt";
    public static String Jing_dan_zheng_tong=SanTai_Host_zyhtbank+"/h5/page/letterGuarantee/index.html?platform=yzt";
    //银铁通
    public static String Tie_zai_xian_kai_hu=SanTai_Host_zyhtbank+"/h5/page/openAccount/index.html?platform=ytt";
    public static String Tie_lu_xiang_mu=SanTai_Host_zyhtbank+"/h5/page/enterpriseLoan/index.html?platform=ytt";
    public static String Tie_hong_yun_tong_log=Bank_Host+"/fenghang-web/common/loginNew.do";
    public static String Tie_hong_yun_tong_new_url=Bank_Host+"/fenghang-web/common/loginNew.do";
    public static String Tie_hong_yun_tong_nolog=Bank_Host+"/fenghang-web/common/loginOld.do";
    //银供通
    public static String Gong_e_kai_hu=SanTai_Host_zyhtbank+"/h5/page/openAccount/index.html?platform=ygt";
    public static String Gong_e_bao_han=SanTai_Host_zyhtbank+"/h5/page/creditCard/index.html?platform=ygt";
    public static String Gong_e_dan_zheng=SanTai_Host_zyhtbank+"/h5/page/letterGuarantee/index.html?platform=ygt";
    //银瓷通
    //public static String Ci_kuai_dai_bao=Bank_Host+"/zyhtbanking/h5/page/enterpriseLoan/index.html?platform=yct";
    public static String Ci_kai_hu_bao=SanTai_Host_zyhtbank+"/h5/page/openAccount/index.html?platform=yct";
    public static String Ci_kuai_dai_bao=SanTai_Host_zyhtbank+"/h5/page/tczxt/index.html?platform=yct";
    public static String Ci_bao_han_bao=SanTai_Host_zyhtbank+"/h5/page/creditCard/index.html?platform=yct";
    public static String Ci_chu_kou_bao=SanTai_Host_zyhtbank+"/h5/page/letterGuarantee/index.html?platform=yct";
    public static String Ci_main_bg_music_URL =SanTai_Host_zyhtbank+"/bgMusicConfigure/getbgMusicUrl";
    //  public static String Bank_CI_Qian_Nian_Ci_Du_Lv_You_Jing_Dian="http://www.expoon.com/17320/panorama";
    public static String Bank_CI_Qian_Nian_Ci_Du_Lv_You_Jing_Dian="http://www.expoon.com/18599/panorama";
    public static String Bank_CI_Qian_Nian_Ci_Du_Lv_You_Jing_Dian_2="http://www.expoon.com/23544/panorama";
    public static String Bank_CI_Chang_Nan_Gu_Zheng=SanTai_Host_zyhtbank+"/bank/culture/ancientTown/index.html";
    public static String Bank_CI_Qian_Nian_Ci_Du_Ci_Ye_Wen_Hua=SanTai_Host_zyhtbank+"/bank/culture/mapline/map.html";
    public static String Bank_CI_Qian_Nian_Ci_Du_Li_Shi_Ren_Wu=SanTai_Host_zyhtbank+"/bank/culture/history/index.html";
    public static String Bank_CI_Qian_Nian_Ci_Du_Ci_Yi_Gong_Xu=SanTai_Host_zyhtbank+"/bank/culture/process/index.html";
    public static String Bank_CI_Qian_Nian_Ci_Du_Tao_Ci_Ming_Jia=SanTai_Host_zyhtbank+"/bank/celebrity/person/index.html";
    public static String Bank_CI_Qian_Nian_Ci_Du_Ming_Ci_Jian_Shang=SanTai_Host_zyhtbank+"/bank/celebrity/appreciate/index.html";
    public static String Bank_CI_Qian_Nian_Ci_Du_You_Zhi_Qi_Ye=SanTai_Host_zyhtbank+"/bank/celebrity/person/index.html";
    public static String Bank_CI_Qian_Nian_Ci_Du_Ming_You_Te_Chan=SanTai_Host_zyhtbank+"/bank/travel/food/index.html";
    public static String Bank_CI_Qian_Nian_Ci_Gu_Ci_Feng_Yun=SanTai_Host_zyhtbank+"/bank/celebrity/ancientPorcelain/index.html";
    public static String Bank_CI_Qian_Nian_Ci_Wen_Chuang_tao_ci=SanTai_Host_zyhtbank+"/bank/celebrity/winchance/index.html";
    public static String Bank_CI_Qian_Nian_Ci_Tao_Ci_Shi_Chang=SanTai_Host_zyhtbank+"/bank/map/index.html";
    public static String Bank_CI_Qian_Nian_Ci_Yan_Xue_You_Wan=SanTai_Host_zyhtbank+"/bank/travel/research/index.html";

    public static final String Bank_kht_key_url = "/zyhtbanking/h5/page/openAccount/result";
    public static final String Bank_kht_key_url_old = "/h5/index.html#/openAccount/result?tradeCode";
    public static final String Bank_kht_grkh_url = Bank_Host+"/kht/bk_info.html";
    public static final String Bank_kht_grcx_url = Bank_Host+"/kht/report.html";
    public static final String Bank_kht_grtykh_url = Bank_Host+"/OpenAccount/app.html#/?url=open_account/person_info.html";
    public static final String Bank_kht_grcxjk_url = Bank_Host+"/wsbk/selector.do?method=inq";
    //public static final String Bank_ci_hu_dong_bbs = "http://116.62.154.167:8080/care1/care-main?id=";
    public static final String Bank_ci_hu_dong_bbs = "http://pinrun.tech:8080/care1/care-main?id=";
    //校园通
    public static final String XYT_SELECT_CITY = "https://pay.shang-lian.com/sltfmin/index.html#/city";
    public static final String XYT_PROMPT = "http://123.124.191.179/xyt/introduction.html";

    //扶贫通
    public static String Bank_FU_guan_ai_bbs = "http://qwerb.cn/care/care-main?id=";

    //中小通
    public static final String Bank_zxt_key_url = "/zyhtbanking/h5/page/tczxt/info";
    public static final String Bank_qyfpt_key_url = "/zyhtbanking/h5/page/tczxt/indexFpt.html?platform=";
    public static final String Bank_zxt = SanTai_Host_zyhtbank+ "/h5/page/tczxt/index.jsp?platform=yht";

    //保函通
    public static final String Bank_bht_key_url = "/h5/index.html#/creditCard/result";
    public static final String UrlForMainBht = SanTai_Host_zyhtbank+"/h5/page/creditCard/index.html?platform=yht";
    //单证通
    public static final String Bank_dzt_key_url = "/h5/index.html#/letterGuarantee/result";
    public static final String UrlForMainDzt = SanTai_Host_zyhtbank+"/h5/page/letterGuarantee/index.html?platform=yht";
    //票据通
    public static final String Bank_pjt_key_url = "/h5/page/bill/result";
    public static final String PJT = SanTai_Host_zyhtbank + "/h5/page/bill/index.html?platform=yht";
    //企贷通
    public static final String Bank_qdt_key_url = "/h5/index.html#/enterpriseLoan/result";
    //银烟通
    public static final String Bank_yan_zxt = SanTai_Host_zyhtbank+ "/h5/page/tczxt/indexFpt.jsp?platform=yyt";
    public static final String Bank_yyt_jxychydt = "http://jx.tobacco.gov.cn/nportal/portal/";
    public static final String Bank_yyt_ycyg = "https://www.baiwandian.cn/xinyunlian-weixin-ecom/wx/login.jhtml";
    public static final String Bank_yyt_zxt = SanTai_Host_zyhtbank+ "/h5/page/tczxt/index.html?platform=yyt";
    public static final String Bank_yyt_brand = "http://118.31.14.52:8888/cigaretteBrandsShow/";

    //开户通
    public static String Bank_kht_ytt_url = Bank_Host+"/OpenAccount/app.html?FlexSlider=ytt_lb001|ytt_lb002|ytt_lb003&platform=ytt";
    //public static String Bank_kht_ytt_url = "http://119.29.107.253:8080/zyhtbanking/OpenAccount/app.html?FlexSlider=yht_lb001&platform=yht";
    public static String Bank_kht_fpt_url = Bank_Host+"/OpenAccount/app.html?FlexSlider=fpt_lb001|fpt_lb002&platform=fpt";
    public static String Bank_kht_ygt_url = Bank_Host+"/OpenAccount/app.html?FlexSlider=ygt_lb001|ygt_lb002&platform=ygt";
    public static String Bank_kht_yht_url = Bank_Host+"/OpenAccount/app.html?FlexSlider=yht_lb001|yht_lb002&platform=yht";
    public static String Bank_kht_yqt_url = Bank_Host+"/OpenAccount/app.html?FlexSlider=home_swiper_1|home_swiper_2&platform=yqt";
    public static String Bank_kht_yzt_url = Bank_Host+"/OpenAccount/app.html?FlexSlider=home_swiper_1|home_swiper_2&platform=yzt";
    public static String Bank_kht_yyt_url = Bank_Host+"/OpenAccount/app.html?FlexSlider=home_swiper_1|home_swiper_2&platform=yyt";
    public static String Bank_kht_yjt_url = Bank_Host+"/OpenAccount/app.html?FlexSlider=home_swiper_1|home_swiper_2&platform=yjt";

    public static String Bank_grkh_process_url = Bank_Host+"/OpenAccount/app.html#/?url=open_account/grkhlc.html";
    public static String Bank_grkh_query_url = Bank_Host+"kht/report.html";
    public static String Bank_grkh_url = Bank_Host+"/kht/bk_info.html";
    public static String Bank_qykh_process_url = SanTai_Host_zyhtbank+"/h5/page/openAccount/khlc_index.html?platform=yht";

    //银政通(鹰潭专属)
    public static String kht_yzt_ytzs_url_test="http://116.62.169.111:8888/zyhtbanking/h5/page/openAccount/index.html?platform=yztyt";
    //public static String kht_yzt_ytzs_url=Bank_Host+"/zyhtbanking/h5/page/openAccount/index.html?platform=yztyt";
    public static String kht_yzt_ytzs_url=SanTai_Host_yhtFrontEnd+"/h5/page/specialPage/kht_guide.html?platform=yztyt";
    public static String qdt_yzt_ytzs_url=SanTai_Host_yhtFrontEnd+"/h5/page/specialPage/qdt_guide.html?platform=yztyt";
    public static String bht_yzt_ytzs_url=SanTai_Host_yhtFrontEnd+"/h5/page/specialPage/bht_guide.html?platform=yztyt";
    public static String dzt_yzt_ytzs_url=SanTai_Host_yhtFrontEnd+"/h5/page/specialPage/dzt_guide.html?platform=yztyt";
    public static String zxt_yzt_ytzs_url=SanTai_Host_yhtFrontEnd+"/h5/page/specialPage/zxt_guide.html?platform=yztyt";
    public static String pjt_yzt_ytzs_url=SanTai_Host_yhtFrontEnd+"/h5/page/bill/pjt.html?platform=yztyt";
    public static String zxt_wltdpt_yzt_ytzs_url=SanTai_Host_yhtFrontEnd+"/h5/page/specialPage/zxt_guide.html?platform=yztyt&type=wltd";

    public static String qzt_yzt_ytzs_url=SanTai_Host_zyhtbank+"/h5/page/openAccount/index.html?platform=yzt";
    public static String yyt_yzt_ytzs_url=SanTai_Host_zyhtbank+"/h5/page/enterpriseLoan/index.html?platform=yzt";
    public static String xyt_yzt_ytzs_url=SanTai_Host_zyhtbank+"/h5/page/creditCard/index.html?platform=yzt";
    public static String lyt_yzt_ytzs_url=SanTai_Host_zyhtbank+"/h5/page/letterGuarantee/index.html?platform=yzt";

    //public static String yingtan_smart_city="http://city.mzywx.com/?from=singlemessage&isappinstalled=0&platform=yztyt";
    public static String yingtan_smart_city="http://www.yingtan.gov.cn/tzyt/";

    public static String yzt_yt_industrial_platform_item_01_url="http://www.ytsanchuan.com/";
    public static String yzt_yt_industrial_platform_item_02_url="https://manato.tmall.com/";
    public static String yzt_yt_industrial_platform_item_03_url="http://www.telchina.com.cn/info/show-343.html";
    public static String yzt_yt_industrial_platform_item_04_url="http://wotaijingshui.d17.cc/";
    public static String yzt_yt_industrial_platform_item_05_url="http://www.newnets.cn/Web62/";
    public static String yzt_yt_industrial_platform_item_06_url="http://www.jxtongyinxiang.com/index.asp";
    public static String yzt_yt_industrial_platform_item_07_url="http://www.ahzzx.com/";
    public static String yzt_yt_industrial_platform_item_08_url="http://www.jxgxty.com/";
    public static String yzt_yt_industrial_platform_item_09_url="http://www.jiangnancopper.com/";
    public static String yzt_yt_industrial_platform_item_10_url="http://www.hongqitongye.com/";
    public static String yzt_yt_industrial_platform_item_11_url="http://gxly789.com/aboutus.php";
    public static String yzt_yt_industrial_platform_item_12_url="http://www.jxtxty.com/index.html";
    public static String yzt_yt_industrial_platform_item_13_url="http://www.kaiangufen.com/index.html";
    public static String yzt_yt_industrial_platform_item_14_url="http://www.weiqiangtongye.com/";
    public static String yzt_yt_industrial_platform_item_15_url="http://kangchengcopper.diytrade.com/";
    public static String yzt_yt_industrial_platform_item_16_url="http://www.gxjzty.cn/";
    public static String yzt_yt_industrial_platform_item_17_url="http://www.3mmt.com/";
    public static String yzt_yt_industrial_platform_item_18_url="http://www.3bmy.net/index.html";
    public static String yzt_yt_industrial_platform_item_19_url="http://www.jxsentai.com/";
    public static String yzt_yt_industrial_platform_item_20_url="http://chinayangg.360500.com/";
    public static String yzt_yt_industrial_platform_item_21_url="https://yankonzm.tmall.com/view_shop.htm?spm=a220m.1000858.0.0.4c637810SSYjBi&shop_id=109853524&scm=1048.1.1.12&rn=65400cab37b8d08e50c62f28f211f442";
    public static String yzt_yt_industrial_platform_item_22_url="http://www.gegefood.com/";
    public static String yzt_yt_industrial_platform_item_23_url="https://zhengxinchu.tmall.com/?spm=a220o.1000855.1997427721.d4918089.5c90c348Cm3DRw";
    public static String yzt_yt_industrial_platform_item_24_url="http://www.guoxigroup.com.cn/index.html";
    public static String yzt_yt_industrial_platform_item_25_url="http://www.kaisitong.com/";
    public static String yzt_yt_industrial_platform_item_26_url="http://14563058299520.gw.1688.com/?spm=a262gm.8760941.0.0.31528e60ocqqpG";

//    赣江新区				
//	开户通	http://119.29.107.253:8080/zyhtbanking/h5/page/openAccount/index.html?platform=gjxq		
//	企贷通	http://119.29.107.253:8080/zyhtbanking/h5/page/enterpriseLoan/index.html?platform=gjxq		
//	保函通	http://119.29.107.253:8080/zyhtbanking/h5/page/creditCard/index.html?platform=gjxq		
//	单证通	http://119.29.107.253:8080/zyhtbanking/h5/page/letterGuarantee/index.html?platform=gjxq		
//				
//景德镇				
//	开户通	http://119.29.107.253:8080/zyhtbanking/h5/page/openAccount/index.html?platform=jdz		
//	企贷通	http://119.29.107.253:8080/zyhtbanking/h5/page/enterpriseLoan/index.html?platform=jdz		
//	保函通	http://119.29.107.253:8080/zyhtbanking/h5/page/creditCard/index.html?platform=jdz		
//	单证通	http://119.29.107.253:8080/zyhtbanking/h5/page/letterGuarantee/index.html?platform=jdz		
//				
//银铁通				
//	在线企业开户	http://119.29.107.253:8080/zyhtbanking/h5/page/openAccount/index.html?platform=ytt		
//	铁路项目贷	http://119.29.107.253:8080/zyhtbanking/h5/page/enterpriseLoan/index.html?platform=ytt



    //银笔通
    public static String Bi_jin_yuan_tang = "http://c.b0yp.com/h.TEF5Z3?cv=75xxZGHNamD&sm=f7bf22";
    public static String Bi_zhou_peng_cheng_bi_zhuang = "http://c.b0yp.com/h.TEKJnF?cv=vi7LsoCSNA";
    public static String Bi_an_bang_bi_zhuang = "https://shop100421197.taobao.com";
    public static String Bi_shuang_wei = "https://shuangweinc.tmall.com/";
    public static String Bi_zhou_zhi_guang_ge = "https://zouziguangge.1688.com";
    public static String Bi_si_he_bi_zhuang = "https://shop105255437.taobao.com/?spm=a1z10.3-c.0.0.9xFfK9";

    //科贷通
    public static final String ke_jxskxjst = "http://www.jxstc.gov.cn/";//江西省科学技术厅
    public static final String ke_gjxq = "http://www.gjxq.gov.cn/html/news/";//赣江新区
    public static final String ke_jxskjcxggfwpt = "http://www.jxinfo.gov.cn/";//江西省科技创新公共服务平台
    public static final String ke_ke_dai_tong = SanTai_Host_yhtFrontEnd+"/h5/page/specialPage/zxt_guide.html?platform=kdt";//科贷通
    public static final String ke_qy_kai_hu = SanTai_Host_yhtFrontEnd+"/h5/page/openAccount/applyfor.html?platform=kdt";
    public static final String ke_qy_query = SanTai_Host_yhtFrontEnd+"/h5/page/openAccount/Acceptquery.html?platform=kdt";
    public static final String ke_qy_khlc = SanTai_Host_zyhtbank+"/h5/page/openAccount/khlc_index.html?platform=kdt";
    public static final String ke_kht_finish_page = SanTai_Host_zyhtbank+"/h5/page/openAccount/result.html?platform=kdt";

    //银文通
    public static final String Bank_ywt_jxwhcydt = "http://www.jxwcpt.com/";
    //德泰香樟
    public static final String Bank_DTXZ = "https://shop104413369.taobao.com ";
    //寻梦龙虎山
    public static final String Bank_XMLHS = "https://shop188154058.taobao.com/";
    //陶溪川
    public static final String Bank_TXC = "https://taoxichuan.tmall.com";
    //北斗文旅
    public static final String Bank_BDWL = "https://shop216032124.taobao.com";

    //银军通
    public static String Jun_jrxxpt = "http://www.gfbzb.gov.cn/";

    //邀请码接口
    public static String INVITE_CODE_URL = Bank_Host+"/zyhtbanking/invitationCode/getAll";
    //public static String INVITE_CODE_URL = "http://119.29.107.253:8090/zyhtbanking/invitationCode/getAll";
}
