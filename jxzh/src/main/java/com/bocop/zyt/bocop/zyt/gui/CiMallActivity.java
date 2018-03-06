package com.bocop.zyt.bocop.zyt.gui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.activity.WebForZytActivity;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyt.IURL;
import com.bocop.zyt.bocop.zyt.biz.LoginHelper;
import com.bocop.zyt.bocop.zyt.broadcast.PlayerMusicBroadCastReciver;
import com.bocop.zyt.bocop.zyt.model.ItemBean;
import com.bocop.zyt.bocop.zyt.utils.MediaPlayerUtils;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.tools.CommonAdapter;
import com.bocop.zyt.jx.tools.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/28.
 */


public class CiMallActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_top_ads;
    private TextView tv_actionbar_title;
    private ImageView iv_bg_logo;
    private ImageView iv_bg_bottom;
    private ImageView iv_music;
    private MediaPlayer mPlayer;
    List<ItemBean> ciMallDatas = new ArrayList<>();
    private MyPlayerMusicBroadCastReciver reciver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ci_mall);
        initdatas();
        init_widget();
    }

    public static void startAct(Context context){
        Intent intent = new Intent(context,CiMallActivity.class);
        context.startActivity(intent);
    }

    private void initdatas() {
        if(ciMallDatas.size()==0){
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_fu_yu, getResources().getString(R.string.yct_mall_item_02_01_01),R.drawable.icon_ci_mall_fu_yu_tao_ci));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cheng_de_xuan, getResources().getString(R.string.yct_mall_item_02_01_02),R.drawable.icon_cheng_de_xuan));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ci_shang_gong_fu, getResources().getString(R.string.yct_mall_item_02_01_03),R.drawable.icon_ci_shang_gong_fu));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_bao_ci_lin, getResources().getString(R.string.yct_mall_item_02_02_01),R.drawable.icon_ci_mall_bao_ci_lin));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_chang_nan_tao_ci, getResources().getString(R.string.yct_mall_item_02_02_02),R.drawable.icon_chang_nan_tao_ci));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_hong_ye, getResources().getString(R.string.yct_mall_item_02_03_01),R.drawable.icon_ci_mall_hong_ye_tao_ci));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_lwyjzmd, getResources().getString(R.string.yct_mall_item_02_32_01),R.drawable.yct_mall_item_02_32_01));
            //ciMallDatas.add(new ItemBean(IURL.Bank_Ci_lwjjqjd, getResources().getString(R.string.yct_mall_item_02_15_03),R.drawable.yct_mall_item_02_15_03));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_sqt, getResources().getString(R.string.yct_mall_item_spt),R.drawable.yct_mall_item_spt));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_lxjjqjd, getResources().getString(R.string.yct_mall_item_02_37_03),R.drawable.yct_mall_item_02_37_03));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ya_yu_tao_ci_deng_shi, getResources().getString(R.string.yct_mall_item_02_04_04),R.drawable.icon_ci_ya_yu_tao_ci_deng_shi));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ni_zhi_ge, getResources().getString(R.string.yct_mall_item_02_07_04),R.drawable.ic_yct_item_02_07_04));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_tang_long, getResources().getString(R.string.yct_mall_item_02_08_01),R.drawable.ic_yct_item_02_08_01));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_tao_xing, getResources().getString(R.string.yct_mall_item_02_08_02),R.drawable.ic_yct_item_02_08_02));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_zhong_jia, getResources().getString(R.string.yct_mall_item_02_09_02),R.drawable.ic_yct_item_02_09_02));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cheng_ci, getResources().getString(R.string.yct_mall_item_02_01_04),R.drawable.icon_ci_cheng_ci_fen_cai_ci));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yi_qin_tang, getResources().getString(R.string.yct_mall_item_02_02_03),R.drawable.icon_yi_xin_tang));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_bo_yi_tao_ci, getResources().getString(R.string.yct_mall_item_02_03_04),R.drawable.icon_bo_yi_tao_ci));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_huan_chang_tao_ci, getResources().getString(R.string.yct_mall_item_02_03_02),R.drawable.icon_huan_chang_tao_ci));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_xiong_yao, getResources().getString(R.string.yct_mall_item_02_08_03),R.drawable.ic_yct_item_02_08_03));
            //ciMallDatas.add(new ItemBean(IURL.Bank_Ci_huan_chang_tao_ci, getResources().getString(R.string.yct_mall_item_02_03_02),R.drawable.icon_huan_chang_tao_ci));
            ciMallDatas.add(new ItemBean(IURL.Bank_Ci_py, getResources().getString(R.string.yct_mall_item_py),R.drawable.yct_mall_item_bei_yun));

			
			/*ciMallDatas.add(new ItemBean(IURL.Bank_Ci_fu_yu, getResources().getString(R.string.yct_mall_item_02_01_01),R.drawable.icon_ci_mall_fu_yu_tao_ci));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cheng_de_xuan, getResources().getString(R.string.yct_mall_item_02_01_02),R.drawable.icon_cheng_de_xuan));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ci_shang_gong_fu, getResources().getString(R.string.yct_mall_item_02_01_03),R.drawable.icon_ci_shang_gong_fu));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cheng_ci, getResources().getString(R.string.yct_mall_item_02_01_04),R.drawable.icon_ci_cheng_ci_fen_cai_ci));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_bao_ci_lin, getResources().getString(R.string.yct_mall_item_02_02_01),R.drawable.icon_ci_mall_bao_ci_lin));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_chang_nan_tao_ci, getResources().getString(R.string.yct_mall_item_02_02_02),R.drawable.icon_chang_nan_tao_ci));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yi_qin_tang, getResources().getString(R.string.yct_mall_item_02_02_03),R.drawable.icon_yi_xin_tang));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_LUYE_qi_jian_dian, getResources().getString(R.string.yct_mall_item_02_02_04),R.drawable.icon_ci_luye_qi_jian_dian));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_hong_ye, getResources().getString(R.string.yct_mall_item_02_03_01),R.drawable.icon_ci_mall_hong_ye_tao_ci));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_huan_chang_tao_ci, getResources().getString(R.string.yct_mall_item_02_03_02),R.drawable.icon_huan_chang_tao_ci));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_bo_tao_tao_ci, getResources().getString(R.string.yct_mall_item_02_03_03),R.drawable.icon_bo_tai_tao_ci));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_bo_yi_tao_ci, getResources().getString(R.string.yct_mall_item_02_03_04),R.drawable.icon_bo_yi_tao_ci));
			
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_qin_yu_tao_ci, getResources().getString(R.string.yct_mall_item_02_04_01),R.drawable.icon_qin_yu));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_hong_xin_tao_ci, getResources().getString(R.string.yct_mall_item_02_04_02),R.drawable.icon_hong_xin_yu_ci));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ju_ya_ge, getResources().getString(R.string.yct_mall_item_02_04_03),R.drawable.icon_ci_ju_ya_ge));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ya_yu_tao_ci_deng_shi, getResources().getString(R.string.yct_mall_item_02_04_04),R.drawable.icon_ci_ya_yu_tao_ci_deng_shi));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ya_yu_tao_ci_yi_shu, getResources().getString(R.string.yct_mall_item_02_05_01),R.drawable.icon_ci_ya_yu_tao_ci_yi_shu));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jin_de_zheng_jia_ju, getResources().getString(R.string.yct_mall_item_02_05_02),R.drawable.icon_ci_jing_de_zheng_jia_ju));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yomerto, getResources().getString(R.string.yct_mall_item_02_05_03),R.drawable.ic_yct_item_02_05_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ai_jue, getResources().getString(R.string.yct_mall_item_02_05_04),R.drawable.ic_yct_item_02_05_04));
			
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_en_yi, getResources().getString(R.string.yct_mall_item_02_06_01),R.drawable.ic_yct_item_02_06_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_fei_lun_te, getResources().getString(R.string.yct_mall_item_02_06_02),R.drawable.ic_yct_item_02_06_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_gong_yu, getResources().getString(R.string.yct_mall_item_02_06_03),R.drawable.ic_yct_item_02_06_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_wu_bang, getResources().getString(R.string.yct_mall_item_02_06_04),R.drawable.ic_yct_item_02_06_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_heng_yan, getResources().getString(R.string.yct_mall_item_02_07_01),R.drawable.ic_yct_item_02_07_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_long_bin, getResources().getString(R.string.yct_mall_item_02_07_02),R.drawable.ic_yct_item_02_07_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_mu_ma, getResources().getString(R.string.yct_mall_item_02_07_03),R.drawable.ic_yct_item_02_07_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ni_zhi_ge, getResources().getString(R.string.yct_mall_item_02_07_04),R.drawable.ic_yct_item_02_07_04));

			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_tang_long, getResources().getString(R.string.yct_mall_item_02_08_01),R.drawable.ic_yct_item_02_08_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_tao_xing, getResources().getString(R.string.yct_mall_item_02_08_02),R.drawable.ic_yct_item_02_08_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_xiong_yao, getResources().getString(R.string.yct_mall_item_02_08_03),R.drawable.ic_yct_item_02_08_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yao_fu, getResources().getString(R.string.yct_mall_item_02_08_04),R.drawable.ic_yct_item_02_08_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_zuo_zhi_yi, getResources().getString(R.string.yct_mall_item_02_09_01),R.drawable.ic_yct_item_02_09_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_zhong_jia, getResources().getString(R.string.yct_mall_item_02_09_02),R.drawable.ic_yct_item_02_09_02));
			----20170906更新商铺信息------
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzjjqjd, getResources().getString(R.string.yct_mall_item_02_09_03),R.drawable.yct_mall_item_02_09_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdztsjbpd, getResources().getString(R.string.yct_mall_item_02_09_04),R.drawable.yct_mall_item_02_09_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jqltcg, getResources().getString(R.string.yct_mall_item_02_10_01),R.drawable.yct_mall_item_02_10_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yjtc, getResources().getString(R.string.yct_mall_item_02_10_02),R.drawable.yct_mall_item_02_10_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_mxcsqjd, getResources().getString(R.string.yct_mall_item_02_10_03),R.drawable.yct_mall_item_02_10_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_pbtcqjd, getResources().getString(R.string.yct_mall_item_02_10_04),R.drawable.yct_mall_item_02_10_04));
			
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_djtc, getResources().getString(R.string.yct_mall_item_02_11_01),R.drawable.yct_mall_item_02_11_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_mdstc, getResources().getString(R.string.yct_mall_item_02_11_02),R.drawable.yct_mall_item_02_11_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_crytc, getResources().getString(R.string.yct_mall_item_02_11_03),R.drawable.yct_mall_item_02_11_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jthqjd, getResources().getString(R.string.yct_mall_item_02_11_04),R.drawable.yct_mall_item_02_11_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_sg, getResources().getString(R.string.yct_mall_item_02_12_01),R.drawable.yct_mall_item_02_12_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cjtc, getResources().getString(R.string.yct_mall_item_02_12_02),R.drawable.yct_mall_item_02_12_02));

			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_msjjqjd, getResources().getString(R.string.yct_mall_item_02_12_03),R.drawable.yct_mall_item_02_12_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_rytcg, getResources().getString(R.string.yct_mall_item_02_12_04),R.drawable.yct_mall_item_02_12_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_sltqjd, getResources().getString(R.string.yct_mall_item_02_13_01),R.drawable.yct_mall_item_02_13_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_miske_qjd, getResources().getString(R.string.yct_mall_item_02_13_02),R.drawable.yct_mall_item_02_13_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdztcb, getResources().getString(R.string.yct_mall_item_02_13_03),R.drawable.yct_mall_item_02_13_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_wxtc, getResources().getString(R.string.yct_mall_item_02_13_04),R.drawable.yct_mall_item_02_13_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ssjrwcq, getResources().getString(R.string.yct_mall_item_02_14_01),R.drawable.yct_mall_item_02_14_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_htjjqjd, getResources().getString(R.string.yct_mall_item_02_14_02),R.drawable.yct_mall_item_02_14_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_xytccj, getResources().getString(R.string.yct_mall_item_02_14_03),R.drawable.yct_mall_item_02_14_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jthqjd, getResources().getString(R.string.yct_mall_item_02_14_04),R.drawable.yct_mall_item_02_14_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ycjjryqjd, getResources().getString(R.string.yct_mall_item_02_15_01),R.drawable.yct_mall_item_02_15_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_hjjj, getResources().getString(R.string.yct_mall_item_02_15_02),R.drawable.yct_mall_item_02_15_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_lwjjqjd, getResources().getString(R.string.yct_mall_item_02_15_03),R.drawable.yct_mall_item_02_15_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_lhjjryzyd, getResources().getString(R.string.yct_mall_item_02_15_04),R.drawable.yct_mall_item_02_15_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_xdqjd, getResources().getString(R.string.yct_mall_item_02_16_01),R.drawable.yct_mall_item_02_16_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ylejjqjd, getResources().getString(R.string.yct_mall_item_02_16_02),R.drawable.yct_mall_item_02_16_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_htstcscd, getResources().getString(R.string.yct_mall_item_02_16_03),R.drawable.yct_mall_item_02_16_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jyjjryqjd, getResources().getString(R.string.yct_mall_item_02_16_04),R.drawable.yct_mall_item_02_16_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_hyjjqjd, getResources().getString(R.string.yct_mall_item_02_17_01),R.drawable.yct_mall_item_02_17_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_wwz_native, getResources().getString(R.string.yct_mall_item_02_17_02),R.drawable.yct_mall_item_02_17_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cxtcg, getResources().getString(R.string.yct_mall_item_02_17_03),R.drawable.yct_mall_item_02_17_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_byqjd, getResources().getString(R.string.yct_mall_item_02_17_04),R.drawable.yct_mall_item_02_17_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_dshqjd, getResources().getString(R.string.yct_mall_item_02_18_01),R.drawable.yct_mall_item_02_18_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_tcg, getResources().getString(R.string.yct_mall_item_02_18_02),R.drawable.yct_mall_item_02_18_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cmkqjd, getResources().getString(R.string.yct_mall_item_02_18_03),R.drawable.yct_mall_item_02_18_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jgtcg, getResources().getString(R.string.yct_mall_item_02_18_04),R.drawable.yct_mall_item_02_18_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzystc, getResources().getString(R.string.yct_mall_item_02_19_01),R.drawable.yct_mall_item_02_19_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_xytccj, getResources().getString(R.string.yct_mall_item_02_19_02),R.drawable.yct_mall_item_02_19_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzsmdtc, getResources().getString(R.string.yct_mall_item_02_19_03),R.drawable.yct_mall_item_02_19_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yjtcg, getResources().getString(R.string.yct_mall_item_02_19_04),R.drawable.yct_mall_item_02_19_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_sysgtcd, getResources().getString(R.string.yct_mall_item_02_20_01),R.drawable.yct_mall_item_02_20_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_pcjj, getResources().getString(R.string.yct_mall_item_02_20_02),R.drawable.yct_mall_item_02_20_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yhtcjj, getResources().getString(R.string.yct_mall_item_02_20_03),R.drawable.yct_mall_item_02_20_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jxqjd, getResources().getString(R.string.yct_mall_item_02_20_04),R.drawable.yct_mall_item_02_20_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_clgcj, getResources().getString(R.string.yct_mall_item_02_21_01),R.drawable.yct_mall_item_02_21_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_gytctyg, getResources().getString(R.string.yct_mall_item_02_21_02),R.drawable.yct_mall_item_02_21_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_byqjd, getResources().getString(R.string.yct_mall_item_02_21_03),R.drawable.yct_mall_item_02_21_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_bjtcczx, getResources().getString(R.string.yct_mall_item_02_21_04),R.drawable.yct_mall_item_02_21_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ycjt, getResources().getString(R.string.yct_mall_item_02_22_01),R.drawable.yct_mall_item_02_22_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_xstcqjd, getResources().getString(R.string.yct_mall_item_02_22_02),R.drawable.yct_mall_item_02_22_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ystcd, getResources().getString(R.string.yct_mall_item_02_22_03),R.drawable.yct_mall_item_02_22_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cyjtcysjj, getResources().getString(R.string.yct_mall_item_02_22_04),R.drawable.yct_mall_item_02_22_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cxtcqjd, getResources().getString(R.string.yct_mall_item_02_23_01),R.drawable.yct_mall_item_02_23_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cfsjj, getResources().getString(R.string.yct_mall_item_02_23_02),R.drawable.yct_mall_item_02_23_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_qytcjj, getResources().getString(R.string.yct_mall_item_02_23_03),R.drawable.yct_mall_item_02_23_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_fstcg, getResources().getString(R.string.yct_mall_item_02_23_04),R.drawable.yct_mall_item_02_23_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yhjjqjd, getResources().getString(R.string.yct_mall_item_02_24_01),R.drawable.yct_mall_item_02_24_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jingchengtcg, getResources().getString(R.string.yct_mall_item_02_24_02),R.drawable.yct_mall_item_02_24_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdznfy, getResources().getString(R.string.yct_mall_item_02_24_03),R.drawable.yct_mall_item_02_24_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzhhtc, getResources().getString(R.string.yct_mall_item_02_24_04),R.drawable.yct_mall_item_02_24_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_wldfqjd, getResources().getString(R.string.yct_mall_item_02_25_01),R.drawable.yct_mall_item_02_25_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ypch, getResources().getString(R.string.yct_mall_item_02_25_02),R.drawable.yct_mall_item_02_25_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yf, getResources().getString(R.string.yct_mall_item_02_25_03),R.drawable.yct_mall_item_02_25_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_xtcsgbdz, getResources().getString(R.string.yct_mall_item_02_25_04),R.drawable.yct_mall_item_02_25_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_lytc, getResources().getString(R.string.yct_mall_item_02_26_01),R.drawable.yct_mall_item_02_26_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yytcg, getResources().getString(R.string.yct_mall_item_02_26_02),R.drawable.yct_mall_item_02_26_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzyjtcjp, getResources().getString(R.string.yct_mall_item_02_26_03),R.drawable.yct_mall_item_02_26_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_dmztcg, getResources().getString(R.string.yct_mall_item_02_26_04),R.drawable.yct_mall_item_02_26_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cyptcg, getResources().getString(R.string.yct_mall_item_02_27_01),R.drawable.yct_mall_item_02_27_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jhtc, getResources().getString(R.string.yct_mall_item_02_27_02),R.drawable.yct_mall_item_02_27_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_hxtc, getResources().getString(R.string.yct_mall_item_02_27_03),R.drawable.yct_mall_item_02_27_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_gy, getResources().getString(R.string.yct_mall_item_02_27_04),R.drawable.yct_mall_item_02_28_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_sstcg, getResources().getString(R.string.yct_mall_item_02_28_01),R.drawable.yct_mall_item_02_28_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzyztc, getResources().getString(R.string.yct_mall_item_02_28_02),R.drawable.yct_mall_item_02_28_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_fstc, getResources().getString(R.string.yct_mall_item_02_28_03),R.drawable.yct_mall_item_02_28_03));
			
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_lmcyqjd, getResources().getString(R.string.yct_mall_item_02_28_04),R.drawable.yct_mall_item_02_28_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_gztcqjd, getResources().getString(R.string.yct_mall_item_02_29_01),R.drawable.yct_mall_item_02_29_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jjltcg, getResources().getString(R.string.yct_mall_item_02_29_02),R.drawable.yct_mall_item_02_29_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_mytcgc, getResources().getString(R.string.yct_mall_item_02_29_03),R.drawable.yct_mall_item_02_29_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_bgqjd, getResources().getString(R.string.yct_mall_item_02_29_04),R.drawable.yct_mall_item_02_29_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_wccp, getResources().getString(R.string.yct_mall_item_02_30_01),R.drawable.yct_mall_item_02_30_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_sdtcqjd, getResources().getString(R.string.yct_mall_item_02_30_02),R.drawable.yct_mall_item_02_30_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cpwtcqygfd, getResources().getString(R.string.yct_mall_item_02_30_03),R.drawable.yct_mall_item_02_30_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ajqjd, getResources().getString(R.string.yct_mall_item_02_30_04),R.drawable.yct_mall_item_02_30_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdz1978tcgzs, getResources().getString(R.string.yct_mall_item_02_31_01),R.drawable.yct_mall_item_02_31_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_mfxtc, getResources().getString(R.string.yct_mall_item_02_31_02),R.drawable.yct_mall_item_02_31_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzqqtccj, getResources().getString(R.string.yct_mall_item_02_31_03),R.drawable.yct_mall_item_02_31_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yqjjqjd, getResources().getString(R.string.yct_mall_item_02_31_04),R.drawable.yct_mall_item_02_31_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_lwyjzmd, getResources().getString(R.string.yct_mall_item_02_32_01),R.drawable.yct_mall_item_02_32_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzycg, getResources().getString(R.string.yct_mall_item_02_32_02),R.drawable.yct_mall_item_02_32_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jlcqjd, getResources().getString(R.string.yct_mall_item_02_32_03),R.drawable.yct_mall_item_02_32_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzyfttccj, getResources().getString(R.string.yct_mall_item_02_32_04),R.drawable.yct_mall_item_02_32_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jycj, getResources().getString(R.string.yct_mall_item_02_33_01),R.drawable.yct_mall_item_02_33_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_lyftcg, getResources().getString(R.string.yct_mall_item_02_33_02),R.drawable.yct_mall_item_02_33_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jltcjjryqjd, getResources().getString(R.string.yct_mall_item_02_33_03),R.drawable.yct_mall_item_02_33_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_xttc, getResources().getString(R.string.yct_mall_item_02_33_04),R.drawable.yct_mall_item_02_33_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_sytc, getResources().getString(R.string.yct_mall_item_02_34_01),R.drawable.yct_mall_item_02_34_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_njbzhzmd, getResources().getString(R.string.yct_mall_item_02_34_02),R.drawable.yct_mall_item_02_34_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cmcj, getResources().getString(R.string.yct_mall_item_02_34_03),R.drawable.yct_mall_item_02_34_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_zhytcsh, getResources().getString(R.string.yct_mall_item_02_34_04),R.drawable.yct_mall_item_02_34_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_ydqjd, getResources().getString(R.string.yct_mall_item_02_35_01),R.drawable.yct_mall_item_02_35_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzgmtc, getResources().getString(R.string.yct_mall_item_02_35_02),R.drawable.yct_mall_item_02_35_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jftc, getResources().getString(R.string.yct_mall_item_02_35_03),R.drawable.yct_mall_item_02_35_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_chcj, getResources().getString(R.string.yct_mall_item_02_35_04),R.drawable.yct_mall_item_02_35_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_bztcg, getResources().getString(R.string.yct_mall_item_02_36_01),R.drawable.yct_mall_item_02_36_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdztltc, getResources().getString(R.string.yct_mall_item_02_36_02),R.drawable.yct_mall_item_02_36_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzhltcjpd, getResources().getString(R.string.yct_mall_item_02_36_03),R.drawable.yct_mall_item_02_36_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_bfqw, getResources().getString(R.string.yct_mall_item_02_36_04),R.drawable.yct_mall_item_02_36_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cdtcg, getResources().getString(R.string.yct_mall_item_02_37_01),R.drawable.yct_mall_item_02_37_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_xmqjd, getResources().getString(R.string.yct_mall_item_02_37_02),R.drawable.yct_mall_item_02_37_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_lxjjqjd, getResources().getString(R.string.yct_mall_item_02_37_03),R.drawable.yct_mall_item_02_37_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_xdtcg, getResources().getString(R.string.yct_mall_item_02_37_04),R.drawable.yct_mall_item_02_37_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_fbxt, getResources().getString(R.string.yct_mall_item_02_38_01),R.drawable.yct_mall_item_02_38_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzystc, getResources().getString(R.string.yct_mall_item_02_38_02),R.drawable.yct_mall_item_02_38_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzyqtcd, getResources().getString(R.string.yct_mall_item_02_38_03),R.drawable.yct_mall_item_02_38_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_mptc, getResources().getString(R.string.yct_mall_item_02_38_04),R.drawable.yct_mall_item_02_38_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzhytc, getResources().getString(R.string.yct_mall_item_02_39_01),R.drawable.yct_mall_item_02_39_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jstcjjg, getResources().getString(R.string.yct_mall_item_02_39_02),R.drawable.yct_mall_item_02_39_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_sdtcjj, getResources().getString(R.string.yct_mall_item_02_39_03),R.drawable.yct_mall_item_02_39_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_dymq, getResources().getString(R.string.yct_mall_item_02_39_04),R.drawable.yct_mall_item_02_39_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_cystcshg, getResources().getString(R.string.yct_mall_item_02_40_01),R.drawable.yct_mall_item_02_40_01));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_hytcqjd, getResources().getString(R.string.yct_mall_item_02_40_02),R.drawable.yct_mall_item_02_40_02));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_yytcqjd, getResources().getString(R.string.yct_mall_item_02_40_03),R.drawable.yct_mall_item_02_40_03));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_jdzbytcjj, getResources().getString(R.string.yct_mall_item_02_40_04),R.drawable.yct_mall_item_02_40_04));
			ciMallDatas.add(new ItemBean(IURL.Bank_Ci_pftc, getResources().getString(R.string.yct_mall_item_02_41_01),R.drawable.yct_mall_item_02_41_01));*/
        }
    }

    public void init_widget() {
        tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
        tv_actionbar_title.setText("陶瓷商城");
        iv_top_ads = (ImageView) findViewById(R.id.iv_top_ads);
        iv_bg_bottom = (ImageView) findViewById(R.id.iv_bg_bottom);
        int[] ret = FImageloader.load_by_resid_fit_src(this,
                R.drawable.bg_pic_act_main_yin_ci_tong_fgt_tao_ci_shang_cheng, iv_top_ads);
        RelativeLayout.LayoutParams iv_bg_bottom_p = new RelativeLayout.LayoutParams(0, 0);
        iv_bg_bottom_p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        FImageloader.load_by_resid_fit_src_rl(this, R.drawable.bg_pic_ci_mall_bottom, iv_bg_bottom, iv_bg_bottom_p);
        iv_music = (ImageView) findViewById(R.id.iv_music);
        iv_music.setOnClickListener(this);
        mPlayer = MediaPlayerUtils.create();
        if(!mPlayer.isPlaying()){
            iv_music.setImageResource(R.drawable.icon_music_off);
        }
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);
        this.registerReceiver(reciver = new MyPlayerMusicBroadCastReciver(), myIntentFilter);
        iv_bg_logo = (ImageView) findViewById(R.id.iv_bg_logo);
        int[] sc = ScreenUtil.get_screen_size(this);
        int w1 = sc[0] - DisplayUtil.dip2px(this, 70);
        int h1 = w1;
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
        p.setMargins(0, ret[1] + DisplayUtil.dip2px(this, 35), 0, 0);
        p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        iv_bg_logo.setLayoutParams(p);

        findViewById(R.id.ll_item_01_01_01).setOnClickListener(this);
        findViewById(R.id.ll_item_01_01_02).setOnClickListener(this);
        findViewById(R.id.ll_item_01_01_03).setOnClickListener(this);
        findViewById(R.id.ll_item_01_01_04).setOnClickListener(this);

        GridView gv = (GridView) findViewById(R.id.gv_yzqy);
        gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gv.setAdapter(new CiMallAdapter(this,ciMallDatas, R.layout.adapter_simple_item_no_border));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = ciMallDatas.get(position).getUrl();
                if(!TextUtils.isEmpty(url)){
                    XWebAct.startAct(CiMallActivity.this, url, ciMallDatas.get(position).getTitle(), R.drawable.shape_base_yct_action_bar);
                }else{
                    ToastUtils.show(CiMallActivity.this, "尽请期待", Toast.LENGTH_SHORT);
                }
            }
        });

        findViewById(R.id.tv_item_02_more).setOnClickListener(this);
        findViewById(R.id.tv_item_01_pay).setOnClickListener(this);
    }

    public class MyPlayerMusicBroadCastReciver extends PlayerMusicBroadCastReciver {

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            int state = intent.getIntExtra("playState", -1);
            if(state == 1){
                iv_music.setImageResource(R.drawable.icon_music_on);
            }else{
                iv_music.setImageResource(R.drawable.icon_music_off);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_item_01_01_01: {

                LoginHelper.get_instance(this).login(this, new LoginHelper.LoginCallback() {

                    @Override
                    public void suc() {
                        XWebActForTaoCIMall.startActForPost(CiMallActivity.this, IURL.Bank_Ci_shang_cheng, "username="
                                        + LoginHelper.get_instance(CiMallActivity.this).get_login_info().userid + "&secret_key=245ab6167079fdcd",
                                "陶瓷商城");

                    }

                    @Override
                    public void fali() {

                    }
                });

                break;
            }
            case R.id.ll_item_01_01_02: {
                XWebAct.startAct(this, IURL.Bank_Ci_jing_zhi_yao, "景之瑶", R.drawable.shape_base_yct_action_bar);
                break;
            }
            case R.id.ll_item_01_01_03: {
                XWebAct.startAct(this, IURL.Bank_Ci_ming_fang_yuan, "名坊园", R.drawable.shape_base_yct_action_bar);
                break;
            }
            case R.id.ll_item_01_01_04: {

                XWebAct.startAct(this, IURL.Bank_Ci_le_xiang_tao_ci, "乐享陶瓷", R.drawable.shape_base_yct_action_bar);

                break;
            }
            case R.id.iv_music:
                if(mPlayer.isPlaying()){
                    mPlayer.pause();
                    sendPlayerMusicBroadCast(2);
                    iv_music.setImageResource(R.drawable.icon_music_off);
                }else{
                    mPlayer.start();
                    sendPlayerMusicBroadCast(1);
                    iv_music.setImageResource(R.drawable.icon_music_on);
                }
                break;
            case R.id.tv_item_02_more:
                WebForZytActivity.startAct(this, IURL.Bank_ci_mall_yzqy_more, "加载更多", false);
                break;
            case R.id.tv_item_01_pay:
                //Intent intent = new Intent(this,CiMallPayDemoActivity.class);
                //this.startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void sendPlayerMusicBroadCast(int state){
        Intent intent = new Intent();  //Itent就是我们要发送的内容
        intent.putExtra("playState", state);
        intent.setAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
        this.sendBroadcast(intent);   //发送广播
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPlayer.isPlaying()){
            iv_music.setImageResource(R.drawable.icon_music_on);
        }else{
            iv_music.setImageResource(R.drawable.icon_music_off);
        }
    }

    public class CiMallAdapter extends CommonAdapter<ItemBean> {

        public CiMallAdapter(Context context, List<ItemBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, ItemBean item) {
            ImageView icon = helper.getView(R.id.iv_icon);
            TextView title = helper.getView(R.id.tv_title);
            icon.setImageResource(item.getIconResouceId());
            title.setText(item.getTitle());
        }
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(reciver);
        super.onDestroy();
    }
}
