package com.bocop.zyt.bocop.zyt.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.boc.bocop.sdk.util.StringUtil;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.yfx.activity.LoanActivity;
import com.bocop.zyt.bocop.yfx.activity.LoanMainActivity;
import com.bocop.zyt.bocop.zyyr.activity.FinanceMainActivity;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.FImageloader;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;


public class ContentDisplayAct extends BaseAct implements OnClickListener {

	public static final int Page_chuan_ye_bao = 0;
	public static final int Page_ge_dai_bao = 1;
	public static final int Page_ge_dai_tong_for_yan = 5;//银烟通用的个贷通
	public static final int Page_chuan_ye_tong = 2;//原始创业通
	public static final int Page_chuan_ye_tong_for_yan = 6;//银烟通用的创业通
	public static final int Page_chuan_ye_tong_for_wen = 7;//文化创业贷

	public static void startAct() {
	}

	public static void startActForChuangYeBao(Context context) {
		Intent intent = new Intent(context, ContentDisplayAct.class);
		intent.putExtra("PAGE", Page_chuan_ye_bao);
		context.startActivity(intent);

	}

	public static void startActForGeDaiBao(Context context) {
		Intent intent = new Intent(context, ContentDisplayAct.class);
		intent.putExtra("PAGE", Page_ge_dai_bao);
		context.startActivity(intent);
	}
	public static void startActForGeDaiTongForYan(Context context) {
		Intent intent = new Intent(context, ContentDisplayAct.class);
		intent.putExtra("PAGE", Page_ge_dai_tong_for_yan);
		context.startActivity(intent);
	}
	public static void startActForChuangYeTong(Context context) {
		Intent intent = new Intent(context, ContentDisplayAct.class);
		intent.putExtra("PAGE", Page_chuan_ye_tong);
		context.startActivity(intent);
	}
	public static void startActForChuangYeTongForYan(Context context) {
		Intent intent = new Intent(context, ContentDisplayAct.class);
		intent.putExtra("PAGE", Page_chuan_ye_tong_for_yan);
		context.startActivity(intent);
	}
	public static void startActivity(Context context,int pageState,String title){
		Intent intent = new Intent(context, ContentDisplayAct.class);
		intent.putExtra("PAGE", pageState);
		intent.putExtra("title", title);
		context.startActivity(intent);
	}
	
	private LinearLayout ll_content;
	private TextView tv_big_title;
	private ImageView iv_header;
	private TextView tv_actionbar_title;
	private RelativeLayout rl_pannel;
	private RelativeLayout rl_actionbar;
	private LinearLayout ll_apply;
	private ImageView iv_bg;
	private RelativeLayout rl_newContent;
	private String title;
	private ImageView contentBg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_content_display);
		title = getIntent().getStringExtra("title");
		title = StringUtil.isNullOrEmpty(title)?"":title;
		init_widget();
		load_content();
	}

	@Override
	public void init_widget() {
		// TODO Auto-generated method stub
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		rl_actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		tv_big_title = (TextView) findViewById(R.id.tv_big_title);
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_title);
		iv_header = (ImageView) findViewById(R.id.iv_header);
		rl_pannel = (RelativeLayout) findViewById(R.id.rl_pannel);
		rl_newContent = (RelativeLayout) findViewById(R.id.rl_new_content);
		iv_bg = (ImageView) findViewById(R.id.iv_bg);
		ll_apply = (LinearLayout) findViewById(R.id.ll_apply);
		contentBg = (ImageView) findViewById(R.id.iv_content_bg);
	}

	private void load_content() {
		// TODO Auto-generated method stub
		switch (getIntent().getExtras().getInt("PAGE")) {
		case Page_chuan_ye_bao:
			load_chuang_ye_bao();
			break;
		case Page_ge_dai_bao:
			load_ge_dai_bao();
			break;
		case Page_ge_dai_tong_for_yan:
			rl_actionbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
			load_ge_dai_tong_for_yan();
			break;
		case Page_chuan_ye_tong:
			rl_actionbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
			load_chuang_ye_tong();
			break;
		case Page_chuan_ye_tong_for_yan:
			rl_actionbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
			load_chuang_ye_tong_for_yan();
			break;
		case Page_chuan_ye_tong_for_wen:
			rl_actionbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
			load_chuang_ye_tong_for_wen();
			break;

		default:
			break;
		}
	}

	/**
	 * 文化创业贷 银文通
	 */
	private void load_chuang_ye_tong_for_wen() {
		// TODO Auto-generated method stub
				/*FImageloader.load_by_resid_fit_src(this, R.drawable.logo_bank_header, iv_header);
				tv_actionbar_title.setText(title);
				tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
				tv_big_title.setText(title);
				String[][] txt = { { "贷款对象：", "重点支持围绕文化产业创业的个人" },
						{ "优惠政策：", "创贷宝包含易信贷和易抵贷。新易贷免抵押，免担保，纯信用；易抵贷是抵押类贷款，在部分地区支持房产第二次抵押。" }, { "产品特色：", "在线申请、灵活还款" },
						{ "贷款期限：", "易信贷最长三年；易抵贷最长五年" }, { "贷款额度：", "最高20万人民币" }, { "贷款利率：", "易信贷月息1.55%；易抵贷月息1.3%" },
						{ "特别提醒：", "本产品从易互通申请，由中银消费金融公司承办" }, };

				LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				int margin = DisplayUtil.dip2px(this, 30);
				p.setMargins(0, 0, margin, 0);
				for (String[] ss : txt) {

					TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.act_content_display_item, null);
					tr.setLayoutParams(p);
					TextView tv_title = (TextView) tr.findViewById(R.id.tv_title);
					TextView tv_txt = (TextView) tr.findViewById(R.id.tv_txt);
					tv_title.setText(ss[0]);
					tv_txt.setText(ss[1]);
					ll_content.addView(tr);
				}*/
				tv_actionbar_title.setText(title);
				rl_pannel.setVisibility(View.GONE);
				rl_newContent.setVisibility(View.VISIBLE);
				iv_bg.setBackgroundResource(R.drawable.ywt_content_display_bg_zxqyd);

				TextView tv = new TextView(this);
				tv.setId(0xFF3320);
				tv.setText("申请办理");
				tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				tv.setTextColor(getResources().getColor(R.color.white));

				tv.setPadding(12, 4, 12, 4);
				tv.setGravity(Gravity.CENTER);
				tv.setBackgroundResource(R.drawable.shape_theme_button_tv);
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);

				lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
				lp.setMargins(0, 0, 0, DisplayUtil.dip2px(this, 16));
				tv.setLayoutParams(lp);

				ll_apply.addView(tv);

				tv.setOnClickListener(this);
	}

	/**
	 * 创业宝
	 */
	private void load_chuang_ye_bao() {
		// TODO Auto-generated method stub
		FImageloader.load_by_resid_fit_src(this, R.drawable.logo_bank_header, iv_header);
		tv_actionbar_title.setText("创贷宝");
		tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv_big_title.setText("创贷宝");
		String[][] txt = { { "贷款对象：", "重点支持围绕陶瓷产业创业的个人" },
				{ "优惠政策：", "创贷宝包含新易贷和乐享贷。新易贷免抵押，免担保，纯信用；乐享贷是抵押类贷款，在部分地区支持房产第二次抵押。" }, { "产品特色：", "在线申请、灵活还款" },
				{ "贷款期限：", "新易贷最长三年；乐享贷最长五年" }, { "贷款额度：", "最高20万人民币" }, { "贷款利率：", "新易贷月息1.55%；乐享贷月息1.3%" },
				{ "特别提醒：", "本产品从易互通申请，由中银消费金融公司承办" }, };

		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		int margin = DisplayUtil.dip2px(this, 30);
		p.setMargins(0, 0, margin, 0);
		for (String[] ss : txt) {

			TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.act_content_display_item, null);
			tr.setLayoutParams(p);
			TextView tv_title = (TextView) tr.findViewById(R.id.tv_title);
			TextView tv_txt = (TextView) tr.findViewById(R.id.tv_txt);
			tv_title.setText(ss[0]);
			tv_txt.setText(ss[1]);
			ll_content.addView(tr);
		}

		TextView tv = new TextView(this);
		tv.setId(0xFF3320);
		tv.setText("申请办理");
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tv.setTextColor(getResources().getColor(R.color.white));

		tv.setPadding(12, 4, 12, 4);
		tv.setGravity(Gravity.CENTER);
		tv.setBackgroundResource(R.drawable.shape_theme_button_tv);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lp.setMargins(0, 0, 0, DisplayUtil.dip2px(this, 16));
		tv.setLayoutParams(lp);

		rl_pannel.addView(tv);

		tv.setOnClickListener(this);
	}
	
	/**
	 * 原始创业通
	 */
	private void load_chuang_ye_tong() {
		// TODO Auto-generated method stub
		FImageloader.load_by_resid_fit_src(this, R.drawable.logo_bank_header, iv_header);
		tv_actionbar_title.setText("创业通");
		tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv_big_title.setText("创业通");
		tv_big_title.setTextColor(getResources().getColor(R.color.red));
		String[][] txt = { { "贷款对象：", "易互通注册用户" },
				{ "优惠政策：", "创业通包含新易贷和乐享贷。新易贷免抵押，免担保，纯信用；乐享贷是抵押类贷款，在部分地区支持房产第二次抵押。" }, { "产品特色：", "在线申请、灵活还款" },
				{ "贷款期限：", "新易贷最长三年；乐享贷最长五年" }, { "贷款额度：", "最高20万人民币" }, { "贷款利率：", "新易贷月息1.55%；乐享贷月息1.3%" },
				{ "特别提醒：", "本产品从易互通申请，由中银消费金融公司承办" }, };
		
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		int margin = DisplayUtil.dip2px(this, 30);
		p.setMargins(0, 0, margin, 0);
		for (String[] ss : txt) {
			
			TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.act_content_display_item, null);
			tr.setLayoutParams(p);
			TextView tv_title = (TextView) tr.findViewById(R.id.tv_title);
			TextView tv_txt = (TextView) tr.findViewById(R.id.tv_txt);
			tv_title.setText(ss[0]);
			tv_txt.setText(ss[1]);
			ll_content.addView(tr);
		}
		
		TextView tv = new TextView(this);
		tv.setId(0xFF3320);
		tv.setText("申请办理");
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tv.setTextColor(getResources().getColor(R.color.white));
		
		tv.setPadding(12, 4, 12, 4);
		tv.setGravity(Gravity.CENTER);
		tv.setBackgroundResource(R.drawable.shape_theme_button_tv);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lp.setMargins(0, 0, 0, DisplayUtil.dip2px(this, 16));
		tv.setLayoutParams(lp);
		
		rl_pannel.addView(tv);
		
		tv.setOnClickListener(this);
	}
	private void load_chuang_ye_tong_for_yan() {
		// TODO Auto-generated method stub
		/*FImageloader.load_by_resid_fit_src(this, R.drawable.logo_bank_header, iv_header);
		tv_actionbar_title.setText("创业易贷");
		tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv_big_title.setText("创业易贷");
		tv_big_title.setTextColor(getResources().getColor(R.color.red));
		String[][] txt = { { "贷款对象：", "重点支持具有烟草专卖许可的经销户" },
				{ "优惠政策：", "创业通包含新易贷和乐享贷。新易贷免抵押，免担保，纯信用；乐享贷是抵押类贷款，在部分地区支持房产第二次抵押。" }, { "产品特色：", "在线申请、灵活还款" },
				{ "贷款期限：", "新易贷最长三年；乐享贷最长五年" }, { "贷款额度：", "最高20万人民币" }, { "贷款利率：", "新易贷月息1.55%；乐享贷月息1.3%" },
				{ "特别提醒：", "本产品从易互通申请，由中银消费金融公司承办" }, };
		
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		int margin = DisplayUtil.dip2px(this, 30);
		p.setMargins(0, 0, margin, 0);
		for (String[] ss : txt) {
			
			TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.act_content_display_item, null);
			tr.setLayoutParams(p);
			TextView tv_title = (TextView) tr.findViewById(R.id.tv_title);
			TextView tv_txt = (TextView) tr.findViewById(R.id.tv_txt);
			tv_title.setText(ss[0]);
			tv_txt.setText(ss[1]);
			ll_content.addView(tr);
		}*/
		rl_pannel.setVisibility(View.GONE);
		rl_newContent.setVisibility(View.VISIBLE);
		iv_bg.setBackgroundResource(R.drawable.yyt_content_display_bg_cyyd);
		
		TextView tv = new TextView(this);
		tv.setId(0xFF3320);
		tv.setText("申请办理");
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tv.setTextColor(getResources().getColor(R.color.white));
		
		tv.setPadding(12, 4, 12, 4);
		tv.setGravity(Gravity.CENTER);
		tv.setBackgroundResource(R.drawable.shape_theme_button_tv);
		/*RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lp.setMargins(0, 0, 0, DisplayUtil.dip2px(this, 16));
		tv.setLayoutParams(lp);*/
		
		ll_apply.addView(tv);
		
		tv.setOnClickListener(this);
	}

	/**
	 * 个贷宝
	 */
	private void load_ge_dai_bao() {
		// TODO Auto-generated method stub
		FImageloader.load_by_resid_fit_src(this, R.drawable.logo_bank_header, iv_header);
		tv_actionbar_title.setText("消贷宝");
		tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv_big_title.setText("消贷宝");
		String[][] txt = { { "贷款对象：", "中国银行江西省分行合作项目持卡人" }, { "优惠政策：", "免抵押，免担保，纯信用" },
				{ "产品特色：", "在线申请、秒速获批、在线提款、随借随还、循环使用" }, { "贷款期限：", "最长一年" }, { "贷款额度：", "最高30万元人民币" },
				{ "特别提醒：", "请绑定本人中行借记卡" }, };

		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		int margin = DisplayUtil.dip2px(this, 30);
		p.setMargins(0, 0, margin, 0);
		for (String[] ss : txt) {

			TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.act_content_display_item, null);
			tr.setLayoutParams(p);
			TextView tv_title = (TextView) tr.findViewById(R.id.tv_title);
			TextView tv_txt = (TextView) tr.findViewById(R.id.tv_txt);
			tv_title.setText(ss[0]);
			tv_txt.setText(ss[1]);
			ll_content.addView(tr);
		}
		{
			TextView tv = new TextView(this);
			tv.setId(0xFF3321);
			tv.setText("关爱卡客户\n专属申请");
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tv.setTextColor(getResources().getColor(R.color.white));

			tv.setPadding(12, 4, 12, 4);
			tv.setGravity(Gravity.CENTER);
			tv.setBackgroundResource(R.drawable.shape_theme_button_tv);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);

			lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			lp.setMargins(0, 0, DisplayUtil.dip2px(this, 48), DisplayUtil.dip2px(this, 24));
			tv.setLayoutParams(lp);
			rl_pannel.addView(tv);
			tv.setOnClickListener(this);
		}
		{
			TextView tv = new TextView(this);
			tv.setId(0xFF3322);
			tv.setText("代发薪客户\n专属申请");
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tv.setTextColor(getResources().getColor(R.color.white));

			tv.setPadding(12, 4, 12, 4);
			tv.setGravity(Gravity.CENTER);
			tv.setBackgroundResource(R.drawable.shape_theme_button_tv);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);

			lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			lp.setMargins(DisplayUtil.dip2px(this, 48), 0, 0, DisplayUtil.dip2px(this, 24));
			tv.setLayoutParams(lp);
			rl_pannel.addView(tv);
			tv.setOnClickListener(this);
		}
	}
	/**
	 * 银烟通用的个贷通
	 */
	private void load_ge_dai_tong_for_yan() {
		/*// TODO Auto-generated method stub
		FImageloader.load_by_resid_fit_src(this, R.drawable.logo_bank_header, iv_header);
		tv_actionbar_title.setText("消贷易贷");
		tv_actionbar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv_big_title.setText("消贷易贷");
		String[][] txt = { { "贷款对象：", "中国银行江西省分行合作项目持卡人" }, { "优惠政策：", "免抵押，免担保，纯信用" },
				{ "产品特色：", "在线申请、秒速获批、在线提款、随借随还、循环使用" }, { "贷款期限：", "最长一年" }, { "贷款额度：", "最高30万元人民币" },
				{ "特别提醒：", "请绑定本人中行借记卡" }, };
		
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		int margin = DisplayUtil.dip2px(this, 30);
		p.setMargins(0, 0, margin, 0);
		for (String[] ss : txt) {
			
			TableRow tr = (TableRow) getLayoutInflater().inflate(R.layout.act_content_display_item, null);
			tr.setLayoutParams(p);
			TextView tv_title = (TextView) tr.findViewById(R.id.tv_title);
			TextView tv_txt = (TextView) tr.findViewById(R.id.tv_txt);
			tv_title.setText(ss[0]);
			tv_txt.setText(ss[1]);
			ll_content.addView(tr);
		}*/
		rl_pannel.setVisibility(View.GONE);
		rl_newContent.setVisibility(View.VISIBLE);
		iv_bg.setBackgroundResource(R.drawable.yyt_content_display_bg_xfyd);
		{
			TextView tv = new TextView(this);
			tv.setId(0xFF3321);
			tv.setText("关爱卡客户\n专属申请");
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tv.setTextColor(getResources().getColor(R.color.white));
			
			tv.setPadding(12, 4, 12, 4);
			tv.setGravity(Gravity.CENTER);
			tv.setBackgroundResource(R.drawable.shape_theme_button_tv);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			
			lp.setMargins(0, 0, DisplayUtil.dip2px(this, 48), 0);
			tv.setLayoutParams(lp);
			ll_apply.addView(tv);
			tv.setOnClickListener(this);
		}
		{
			TextView tv = new TextView(this);
			tv.setId(0xFF3322);
			tv.setText("代发薪客户\n专属申请");
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tv.setTextColor(getResources().getColor(R.color.white));
			
			tv.setPadding(12, 4, 12, 4);
			tv.setGravity(Gravity.CENTER);
			tv.setBackgroundResource(R.drawable.shape_theme_button_tv);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			
			lp.setMargins(DisplayUtil.dip2px(this, 48), 0, 0, 0);
			tv.setLayoutParams(lp);
			ll_apply.addView(tv);
			tv.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case 0xFF3320: {
			Intent intent = new Intent(this, FinanceMainActivity.class);
			intent.putExtra("title", title);
			startActivity(intent);
			break;
		}
		case 0xFF3321: {
			//中银E贷
			if (null != CacheBean.getInstance().get(CacheBean.CUST_ID)
					&& !TextUtils.isEmpty(CacheBean.getInstance().get(CacheBean.CUST_ID).toString())) {
				// if (checkTime()) {

				Bundle bundle = new Bundle();
				bundle.putInt("PRO_FLAG", 0);
				Intent intent=new Intent(_act,LoanMainActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				LoginUtil.requestBocopForCustid(this, true, new LoginUtil.OnRequestCustCallBack() {

					@Override
					public void onSuccess() {
						Bundle bundle = new Bundle();
						bundle.putInt("PRO_FLAG", 0);
						Intent intent=new Intent(_act,LoanMainActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
			}
			
			
			
			break;
		}
		case 0xFF3322: {
			//个贷通
			Bundle bundle = new Bundle();
			bundle.putInt("PRO_FLAG", 0);
			Intent intent = new Intent(this, LoanActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}

		default:
			break;
		}
	}
}
