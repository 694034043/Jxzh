package com.bocop.zyt.bocop.jxplatform.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocsoft.ofa.http.asynchttpclient.JsonHttpResponseHandler;
import com.bocsoft.ofa.http.asynchttpclient.expand.JsonRequestParams;
import com.google.gson.Gson;
import com.bocop.zyt.R;
import com.bocop.zyt.added.NewQztFristActivity;
import com.bocop.zyt.bocop.gjxq.activity.Details1Activity;
import com.bocop.zyt.bocop.gjxq.activity.Details4Activity;
import com.bocop.zyt.bocop.gm.GoldManagerActivity;
import com.bocop.zyt.bocop.jxplatform.activity.BMJFActivity;
import com.bocop.zyt.bocop.jxplatform.activity.EXYActivity;
import com.bocop.zyt.bocop.jxplatform.activity.GjtFirstActivity;
import com.bocop.zyt.bocop.jxplatform.activity.HomeAdvDetailActivity;
import com.bocop.zyt.bocop.jxplatform.activity.JIEHUIActivity;
import com.bocop.zyt.bocop.jxplatform.activity.JKETActivity;
import com.bocop.zyt.bocop.jxplatform.activity.KhtFirstActivity;
import com.bocop.zyt.bocop.jxplatform.activity.LYTActivity;
import com.bocop.zyt.bocop.jxplatform.activity.PiaowutongAct;
import com.bocop.zyt.bocop.jxplatform.activity.WEIHUIActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebActivity;
import com.bocop.zyt.bocop.jxplatform.activity.WebViewActivity;
import com.bocop.zyt.bocop.jxplatform.activity.YPTActivity;
import com.bocop.zyt.bocop.jxplatform.activity.ZYEDActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.AddGasOilServiceActivity;
import com.bocop.zyt.bocop.jxplatform.activity.riders.MoneySelectWebView;
import com.bocop.zyt.bocop.jxplatform.activity.riders.RiderFristActivity;
import com.bocop.zyt.bocop.jxplatform.adapter.HomeImageAdapter;
import com.bocop.zyt.bocop.jxplatform.adapter.HomeItemAdapter;
import com.bocop.zyt.bocop.jxplatform.adapter.LoopViewPagerAdapter;
import com.bocop.zyt.bocop.jxplatform.bean.Advertisement;
import com.bocop.zyt.bocop.jxplatform.bean.SchoolBean;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplate;
import com.bocop.zyt.bocop.jxplatform.http.RestTemplateJxBank;
import com.bocop.zyt.bocop.jxplatform.trafficassistant.TrafficAssistantMainActivity;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.CustomInfo;
import com.bocop.zyt.bocop.jxplatform.util.CustomProgressDialog;
import com.bocop.zyt.bocop.jxplatform.util.FormsUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtilAnother;
import com.bocop.zyt.bocop.jxplatform.util.QztRequestWithJsonAndHead;
import com.bocop.zyt.bocop.jxplatform.util.Update;
import com.bocop.zyt.bocop.jxplatform.view.MyGridView;
import com.bocop.zyt.bocop.qzt.QztFristActivity;
import com.bocop.zyt.bocop.xms.activity.MessageActivity;
import com.bocop.zyt.bocop.xms.activity.XmsMainActivity;
import com.bocop.zyt.bocop.xms.activity.ZqtActivity;
import com.bocop.zyt.bocop.yfx.activity.TrainsActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.base.BaseFragment;
import com.bocop.zyt.jx.baseUtil.asynchttpclient.AsyncHttpResponseHandler;
import com.bocop.zyt.jx.baseUtil.asynchttpclient.RequestParams;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnTouch;
import com.bocop.zyt.jx.common.util.ContentUtils;
import com.bocop.zyt.jx.constants.Constants;
import com.bocop.zyt.jx.view.indicator.CirclePageIndicator;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BaseFragment implements LoginUtilAnother.ILoginListener {
	private ArrayList<SchoolBean> fristList = new ArrayList<SchoolBean>();
	@ViewInject(R.id.ll_helper)
	private LinearLayout llHelper;
	@ViewInject(R.id.ll_financial)
	private LinearLayout llFinancial;
	@ViewInject(R.id.ll_secretary)
	private LinearLayout llSecretary;
	@ViewInject(R.id.ll_illegals)
	private LinearLayout llIllegals;
	@ViewInject(R.id.ivSingleImage)
	private ImageView ivSingleImage;
	@ViewInject(R.id.rltAd)
	private RelativeLayout rltAd;
	@ViewInject(R.id.vpAd)
	private ViewPager vpAd;
	@ViewInject(R.id.indicator)
	private CirclePageIndicator indicator;
	private HomeItemAdapter mAdapter;
	private LinearLayout ll_yslc;
	private RiderFristActivity mRiderFristActivity;

	private HomeItemAdapter lifeAdapter;
	private HomeItemAdapter financeAdapter;
	private HomeItemAdapter shopAdapter;
	private HomeItemAdapter facilityAdapter;

	@ViewInject(R.id.gvLifeSer)
	private GridView gvLifeSer;
	@ViewInject(R.id.gvFinanceSer)
	private GridView gvFinanceSer;
	@ViewInject(R.id.gvFacilitySer)
	private GridView gvFacilitySer;
	// @ViewInject(R.id.gvShopping)
	// private GridView gvShopping;

	@ViewInject(R.id.gvImage)
	private GridView gvImage;
	private static int[] imageIcons = new int[] { R.drawable.gjxq_cam_dgzzfw, R.drawable.gjxq_zyjrfwzx };
	private static int[] jdzimageIcons = new int[] { R.drawable.jdz1, R.drawable.jdz2 };

	private static int[] yttimageIcons = new int[] { R.drawable.ytt_g1, R.drawable.ytt_g2 };
	private static String[] imageBigText = new String[] { "违章处理", "生活缴费", "理财管家" };
	private static String[] imageSmallText = new String[] { "网上自动生成处理单，无需排队缴费", "到期要缴费了吗？点击这里全都有" };
	private HomeImageAdapter imageAdapter;

	public BaseApplication baseApplication = BaseApplication.getInstance();
	ProgressDialog progressDialog;
	private static Editor editor;
	public int id;
	private float preX = 0;
	private float preY = 0;
	private float nowX = 0;
	private float nowY = 0;
	private boolean isTouch = false;// 是否正在拖动轮播图
	private List<Advertisement> mAdvList = new ArrayList<Advertisement>();
	private List<View> views = new ArrayList<View>();
	private static String[] homePageLogEvent;
	private static String[] homeLifeLogEvent;
	private static String[] homeFinaceLogEvent;
	private static String[] homeFacilityLogEvent;
	private static String[] homeShoppingLogEvent;

	public void showjqqd() {

		// Toast.makeText(this.getActivity(), "敬请期待...", 0).show();
		// AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		// //先得到构造器
		// builder.setTitle("提示"); //设置标题
		// builder.setMessage("敬请期待..."); //设置内容
		// builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		// { //设置确定按钮
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss(); //关闭dialog
		// }
		// });
		// //参数都设置完成了，创建并显示出来
		// builder.create().show();
		xiaodaitong();
	}

	private static String[] homePageImageLogEvent;
	private Handler adHandler = new Handler() { // 启动广告页面自动播放
		@Override
		public void handleMessage(Message msg) {
			if (!isTouch) {
				if (vpAd.getCurrentItem() == views.size() - 1) {
					vpAd.setCurrentItem(0, false);
				} else {
					vpAd.setCurrentItem(vpAd.getCurrentItem() + 1);
				}
				adHandler.sendEmptyMessageDelayed(0, 3000);
			} else {
				adHandler.sendEmptyMessageDelayed(0, 3000);
			}
		};
	};
	BaseApplication app;
	String mainType;
	int ggPosion1 = 4, ggPosion2 = 5;
	RelativeLayout ytt_title1, ytt_title4;
	TextView textView1, textView2;
	MyGridView gvShopping;
	ImageView imageView2, imageView3;
	private Dialog selectDialog;
	private Dialog dz;
	private TextView tv_item_00;
	private TextView textView3;
	private ImageView imgView1;
	private ImageView imgView4;
	private RelativeLayout ytt_title2;
	private RelativeLayout ytt_title3;
	private ImageView iv_line_2;
	private ImageView iv_line_3;
	private ImageView iv_line_4;
	private ImageView iv_line_5;
	private HomeFragmentHelper _hepler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		_hepler = new HomeFragmentHelper(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = initView(R.layout.fragment_home);
		mRiderFristActivity = new RiderFristActivity();
		app = (BaseApplication) getActivity().getApplication();
		mainType = app.getMainType();
		ytt_title1 = (RelativeLayout) view.findViewById(R.id.ytt_title1);
		ytt_title2 = (RelativeLayout) view.findViewById(R.id.ytt_title2);
		ytt_title3 = (RelativeLayout) view.findViewById(R.id.ytt_title3);
		ytt_title4 = (RelativeLayout) view.findViewById(R.id.ytt_title4);
		tv_item_00 = (TextView) view.findViewById(R.id.tv_item_00);
		textView3 = (TextView) view.findViewById(R.id.textView3);
		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		gvShopping = (MyGridView) view.findViewById(R.id.gvShopping);
		imgView1 = (ImageView) view.findViewById(R.id.imgView1);
		imageView2 = (ImageView) view.findViewById(R.id.imgView2);
		imageView3 = (ImageView) view.findViewById(R.id.imgView3);
		imgView4 = (ImageView) view.findViewById(R.id.imgView4);

		iv_line_2 = (ImageView) view.findViewById(R.id.iv_line_2);
		iv_line_3 = (ImageView) view.findViewById(R.id.iv_line_3);
		iv_line_4 = (ImageView) view.findViewById(R.id.iv_line_4);
		iv_line_5 = (ImageView) view.findViewById(R.id.iv_line_5);

		homePageLogEvent = baseActivity.getResources().getStringArray(R.array.homepagelog_array);
		homePageImageLogEvent = baseActivity.getResources().getStringArray(R.array.homepageimagelog_array);

		homeLifeLogEvent = baseActivity.getResources().getStringArray(R.array.homelifelog_array);
		homeFinaceLogEvent = baseActivity.getResources().getStringArray(R.array.homefinacelog_array);
		homeFacilityLogEvent = baseActivity.getResources().getStringArray(R.array.homefacilitylog_array);
		homeShoppingLogEvent = baseActivity.getResources().getStringArray(R.array.homeshoppinglog_array);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListener();
	}

	/**
	 * 设置监听事件
	 */
	private void setListener() {
		lifeAdapter = new HomeItemAdapter(getActivity(), R.array.life, "0");

		shopAdapter = new HomeItemAdapter(getActivity(), R.array.ytt4, "130");

		if (mainType.equals("0")) {
			// 需要2个
			imageView2.setVisibility(View.GONE);
			imageView3.setVisibility(View.GONE);
			gvLifeSer.setVisibility(View.GONE);
			gvShopping.setVisibility(View.GONE);
			ytt_title1.setVisibility(View.GONE);
			ytt_title4.setVisibility(View.GONE);
			facilityAdapter = new HomeItemAdapter(getActivity(), R.array.facility, "3");
			financeAdapter = new HomeItemAdapter(getActivity(), R.array.finance, "1");
			imageAdapter = new HomeImageAdapter(getActivity(), imageIcons, imageBigText, imageSmallText);
			ggPosion1 = 4;
			ggPosion2 = 5;

		} else if (mainType.equals("1")) {
			// 需要2个
			imageView2.setVisibility(View.GONE);
			imageView3.setVisibility(View.GONE);
			gvLifeSer.setVisibility(View.GONE);
			gvShopping.setVisibility(View.GONE);
			ytt_title1.setVisibility(View.GONE);
			ytt_title4.setVisibility(View.GONE);
			facilityAdapter = new HomeItemAdapter(getActivity(), R.array.zyt, "3");
			financeAdapter = new HomeItemAdapter(getActivity(), R.array.finance, "1");
			imageAdapter = new HomeImageAdapter(getActivity(), jdzimageIcons, imageBigText, imageSmallText);
			ggPosion1 = 6;
			ggPosion2 = 7;
		} else if (mainType.equals("2")) {
			// 需要4个
			imageView2.setVisibility(View.VISIBLE);
			imageView3.setVisibility(View.VISIBLE);
			gvLifeSer.setVisibility(View.VISIBLE);
			gvShopping.setVisibility(View.VISIBLE);
			ytt_title1.setVisibility(View.VISIBLE);
			ytt_title4.setVisibility(View.VISIBLE);
			tv_item_00.setText("专属贷款服务");
			textView1.setText("财富增值服务");
			textView2.setText("职工生活服务");
			textView3.setText("消费购物服务");
			imgView1.setImageBitmap(null);
			imageView2.setImageBitmap(null);
			imageView3.setImageBitmap(null);
			imgView4.setImageBitmap(null);
			int c = getActivity().getResources().getColor(R.color.FFF3D9);
			ytt_title1.setBackgroundColor(c);
			ytt_title2.setBackgroundColor(c);
			ytt_title3.setBackgroundColor(c);
			ytt_title4.setBackgroundColor(c);

			iv_line_2.setVisibility(View.GONE);
			iv_line_3.setVisibility(View.GONE);
			iv_line_4.setVisibility(View.GONE);
			iv_line_5.setVisibility(View.GONE);

			facilityAdapter = new HomeItemAdapter(getActivity(), R.array.ytt2, "110");
			financeAdapter = new HomeItemAdapter(getActivity(), R.array.ytt3, "120");
			imageAdapter = null;
			// imageAdapter = new HomeImageAdapter(getActivity(), yttimageIcons,
			// imageBigText, imageSmallText);
			ggPosion1 = 8;
			ggPosion2 = 9;
		}

		// 第一个
		gvLifeSer.setAdapter(lifeAdapter);
		// 第二个
		gvFacilitySer.setAdapter(facilityAdapter);
		// 第三个
		gvFinanceSer.setAdapter(financeAdapter);
		// 第四个
		gvShopping.setAdapter(shopAdapter);
		// 底部广告
		if (imageAdapter != null) {
			gvImage.setAdapter(imageAdapter);
		}

		gvImage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!CustomInfo.isExistCustomInfo(baseActivity)) {
					Log.i("LoginUtil", "requestBocopForCustid");
					if (LoginUtil.isLog(getActivity())) {
						CustomInfo.requestBocopForCustid(baseActivity, false);
					}
				} else {
					Log.i("LoginUtil", "postIdForXms");
					CustomInfo.postLog(baseActivity, homePageImageLogEvent[position]);
				}

				switch (position) {
				// 车友会
				case 0:
					gotoDetail1A(ggPosion1);
					break;

				// 繳費通
				case 1:
					gotoDetail1A(ggPosion2);
					break;

				// 理财通
				case 2:
					liCai();
					break;
				// 优品通
				case 3:
					// youpingtong();
					break;
				// 卡惠通
				case 4:
					// startKht();
					break;
				// 健康通
				case 5:
					// health();
					break;

				default:

					break;
				}
			}
		});

		// 新区信息
		gvLifeSer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// if(!baseApplication.isNetStat()){
				// CustomProgressDialog.showBocNetworkSetDialog(getActivity());
				// return;
				// }
				if (!CustomInfo.isExistCustomInfo(baseActivity)) {
					Log.i("LoginUtil", "requestBocopForCustid");
					if (LoginUtil.isLog(getActivity())) {
						CustomInfo.requestBocopForCustid(baseActivity, false);
					}

				} else {
					Log.i("LoginUtil", "postIdForXms");
					CustomInfo.postLog(baseActivity, homeLifeLogEvent[position]);
				}
				switch (position) {
				// 新区信息
				case 0:
					// 鸿运通
					// 铁路项目贷
					// gotoDetail2A(3);

					final String time_url = "http://qwerb.cn:8080/companyFinance2/h5/index.html#/enterpriseLoan/main?type=qdt&userId="
							+ LoginUtil.getUserId(getActivity()) + "&tokens=" + LoginUtil.getToken(getActivity());
					if (LoginUtil.isLog(getActivity())) {
						Bundle bundleConsult = new Bundle();
						bundleConsult.putString("url", time_url);
						bundleConsult.putString("name", "铁路项目贷");
						Intent intentConsult = new Intent(baseActivity, WebActivity.class);
						intentConsult.putExtras(bundleConsult);
						startActivity(intentConsult);
					} else {
						LoginUtilAnother.authorizeAnother(getActivity(), new LoginUtilAnother.ILoginListener() {

							@Override
							public void onLogin() {
								// TODO Auto-generated method stub
								Bundle bundleConsult = new Bundle();
								bundleConsult.putString("url", time_url);
								bundleConsult.putString("name", "铁路项目贷");
								Intent intentConsult = new Intent(baseActivity, WebActivity.class);
								intentConsult.putExtras(bundleConsult);
								startActivity(intentConsult);
							}

							@Override
							public void onLogin(int position) {
								// TODO Auto-generated method stub
								Bundle bundleConsult = new Bundle();
								bundleConsult.putString("url", time_url);
								bundleConsult.putString("name", "铁路项目贷");
								Intent intentConsult = new Intent(baseActivity, WebActivity.class);
								intentConsult.putExtras(bundleConsult);
								startActivity(intentConsult);
							}

							@Override
							public void onException() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onError() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onCancle() {
								// TODO Auto-generated method stub

							}
						}, 233222);
					}

					// if (baseApplication.isNetStat()) {
					// if (LoginUtil.isLog(baseActivity)) {
					// startActivity(new Intent(getActivity(),
					// GoldManagerActivity.class));
					// } else {
					// LoginUtilAnother.authorizeAnother(getActivity(),
					// HomeFragment.this, 105);
					// }
					// } else {
					// CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					// }

					break;
				case 1:
					// 秘书通
					// 铁路中小贷
					// startYdt();
					// if (baseApplication.isNetStat()) {
					// if (LoginUtil.isLog(baseActivity)) {
					// Intent intent = new Intent(baseActivity,
					// newXmsMainActivity.class);
					// startActivity(intent);
					// } else {
					// LoginUtilAnother.authorizeAnother(getActivity(),
					// HomeFragment.this, 102);
					// }
					//
					// } else {
					// CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					// }

					if (baseApplication.isNetStat()) {
						Bundle bundle = new Bundle();
						// bundle.putString("url", Constants.UrlForMainYdt);
						bundle.putString("url", "http://NOzXDKNY.scene.eqxiu.cn/s/NOzXDKNY");
						bundle.putString("name", "中小通");
						Intent intent = new Intent(baseActivity, WebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					} else {
						CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					}

					break;
				// 车惠通
				case 2: {

					// showjqqd();
					// 职工公积贷
					// Intent intent = new Intent(baseActivity,
					// RiderFristActivity.class);
					// startActivity(intent);

					if (baseApplication.isNetStat()) {
						if (LoginUtil.isLog(baseActivity)) {
							Bundle bundle = new Bundle();
							bundle.putInt("PRO_FLAG", 3);
							// Intent intent = new Intent(baseActivity,
							// LoanMainActivity.class);
							Intent intent = new Intent(baseActivity, TrainsActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						} else {
							LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 222);
						}
					} else {
						CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
					}
					break;
				}
				// 旅游通
				case 3: {
					// 职工消费通
					// if (baseApplication.isNetStat()) {
					// Intent intent = new Intent(baseActivity,
					// LYTActivity.class);
					// startActivity(intent);
					// } else {
					// CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					// }

					// xiaodaitong();

					_hepler.start_zhigongxiaofeidai_act();
					break;
				}
				// 缴费通
				case 4: {
					BaseApplication baseApplication = (BaseApplication) baseActivity.getApplication();
					if (baseApplication.isNetStat()) {
						if (LoginUtil.isLog(baseActivity)) {
							Intent intent = new Intent(baseActivity, BMJFActivity.class);
							startActivity(intent);
						} else {
							LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 103);
						}
					} else {
						CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
					}
					break;
				}

				// 签证通
				case 5: {
					if (baseApplication.isNetStat()) {
						Intent intent = new Intent(baseActivity, QztFristActivity.class);
						startActivity(intent);
					} else {
						CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					}
					break;
				}
				// 健康通
				case 6: {
					if (baseApplication.isNetStat()) {
						if (LoginUtil.isLog(baseActivity)) {
							Bundle bundle = new Bundle();
							bundle.putString("userId", LoginUtil.getUserId(getActivity()));
							bundle.putString("token", LoginUtil.getToken(getActivity()));
							Intent intent = new Intent(baseActivity, JKETActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						} else {
							LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 104);
						}
					} else {
						CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					}
					break;
				}
				// 校园通
				case 7: {
					if (baseApplication.isNetStat()) {
						Bundle bundle = new Bundle();
						bundle.putString("url", Constants.UrlForMainXyt);
						bundle.putString("name", "校园通");
						Intent intent = new Intent(baseActivity, WebActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					} else {
						CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					}
					break;
				}
				// 出行
				case 8: {
					// if (baseApplication.isNetStat()) {
					// Bundle bundle = new Bundle();
					// bundle.putString("url", Constants.UrlForMainXyt);
					// bundle.putString("name", "校园通");
					// Intent intent = new Intent(baseActivity,
					// WebActivity.class);
					// intent.putExtras(bundle);
					// startActivity(intent);
					// } else {
					// CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					// }
					getActivity().startActivity(new Intent(getActivity(), PiaowutongAct.class));

					break;
				}
				// 票务
				case 9: {
					// if (baseApplication.isNetStat()) {
					// Bundle bundle = new Bundle();
					// bundle.putString("url", Constants.UrlForMainXyt);
					// bundle.putString("name", "校园通");
					// Intent intent = new Intent(baseActivity,
					// WebActivity.class);
					// intent.putExtras(bundle);
					// startActivity(intent);
					// } else {
					// CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					// }
					// Intent intent1=new
					// Intent(getActivity(),WebViewActivity.class);
					// intent1.putExtra("url", "http://www.ctrip.com");
					// intent1.putExtra("name", "携程");
					// getActivity().startActivity(intent1);

					if (baseApplication.isNetStat()) {
						if (LoginUtil.isLog(baseActivity)) {
							Bundle bundleTrain = new Bundle();
							bundleTrain.putString("url", Constants.xmsUrlForTrain);
							bundleTrain.putString("name", "购火车票");
							Intent intentTrain = new Intent(baseActivity, WebViewActivity.class);
							intentTrain.putExtras(bundleTrain);
							startActivity(intentTrain);
						} else {
							LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 299);
						}
					} else {
						CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					}

					break;
				}
				case 10: {
					// if (baseApplication.isNetStat()) {
					// Bundle bundle = new Bundle();
					// bundle.putString("url", Constants.UrlForMainXyt);
					// bundle.putString("name", "校园通");
					// Intent intent = new Intent(baseActivity,
					// WebActivity.class);
					// intent.putExtras(bundle);
					// startActivity(intent);
					// } else {
					// CustomProgressDialog.showBocNetworkSetDialog(getActivity());
					// }
					Intent intent1 = new Intent(getActivity(), WebViewActivity.class);
					intent1.putExtra("url", "http://119.29.107.253:8080/fenghang-web/h5/index.html");
					intent1.putExtra("name", "小游戏");
					getActivity().startActivity(intent1);

					break;
				}
				default:

					break;
				}
			}
		});
		gvShopping.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// if(!baseApplication.isNetStat()){
				// CustomProgressDialog.showBocNetworkSetDialog(getActivity());
				// return;
				// }
				if (!CustomInfo.isExistCustomInfo(baseActivity)) {
					Log.i("LoginUtil", "requestBocopForCustid");
					if (LoginUtil.isLog(getActivity())) {
						CustomInfo.requestBocopForCustid(baseActivity, false);
					}

				} else {
					Log.i("LoginUtil", "postIdForXms");
					CustomInfo.postLog(baseActivity, homeLifeLogEvent[position]);
				}
				switch (position) {
				case 0:
					// 优品通
					youpingtong();

					break;
				case 1:
					// 外购通
					htzq();
					break;
				case 2:
					// 卡惠通
					startKht();
					break;
				default:

					break;
				}
			}
		});
		// 个人金融服务
		gvFinanceSer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!CustomInfo.isExistCustomInfo(baseActivity)) {
					Log.i("LoginUtil", "requestBocopForCustid");
					if (LoginUtil.isLog(getActivity())) {
						CustomInfo.requestBocopForCustid(baseActivity, false);
					}

				} else {
					Log.i("LoginUtil", "postIdForXms");
					CustomInfo.postLog(baseActivity, homeFinaceLogEvent[position]);
				}
				if (app.getMainType().equals("2")) {
					switch (position + 1) {

					// 鸿运通
					case 0: {
						gm();

						// if (LoginUtil.isLog(baseActivity)) {
						//// Bundle bundle = new Bundle();
						//// bundle.putString("userId",
						// LoginUtil.getUserId(getActivity()));
						//// bundle.putString("token",
						// LoginUtil.getToken(getActivity()));
						//// Intent intent = new Intent(baseActivity,
						// JKETActivity.class);
						//// intent.putExtras(bundle);
						//// startActivity(intent);
						//
						// Bundle bundleConsult = new Bundle();
						// bundleConsult.putString("name", "铁路项目贷");
						//
						// bundleConsult.putString("url",
						// "http://22.220.13.64:8080/fenghang-web?userId="+LoginUtil.getUserId(getActivity())+"&token="+LoginUtil.getToken(getActivity()));
						// Intent intentConsult = new Intent(baseActivity,
						// WebActivity.class);
						// intentConsult.putExtras(bundleConsult);
						// startActivity(intentConsult);
						// } else {
						// Toast.makeText(getActivity(), "登录后，再测试", 0).show();
						// LoginUtilAnother.authorizeAnother(getActivity(),
						// HomeFragment.this, 9999);
						// }

						break;
					}
					// 公积通
					case 1:
						// if (baseApplication.isNetStat()) {
						// Bundle bundle = new Bundle();
						// bundle.putString("url", Constants.UrlForMainKhtcard);
						// bundle.putString("name", "公积通");
						// Intent intent = new Intent(baseActivity,
						// XmsWebActivity.class);
						// intent.putExtras(bundle);
						// startActivity(intent);
						// }else {
						// CustomProgressDialog.showBocNetworkSetDialog(getActivity());
						// }

						// if (baseApplication.isNetStat()) {
						// if (LoginUtil.isLog(baseActivity)) {
						// Bundle bundle = new Bundle();
						// bundle.putInt("PRO_FLAG", 3);
						// // Intent intent = new Intent(baseActivity,
						// // LoanMainActivity.class);
						// Intent intent = new Intent(baseActivity,
						// TrainsActivity.class);
						// intent.putExtras(bundle);
						// startActivity(intent);
						// } else {
						// LoginUtilAnother.authorizeAnother(getActivity(),
						// HomeFragment.this, 222);
						// }
						// } else {
						// CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
						// }

						// 民生缴费

						pay();

						break;

					// 购车通
					case 2:

					// showjqqd();{
					{

						// if (baseApplication.isNetStat()) {
						// if (LoginUtil.isLog(baseActivity)) {
						// Bundle bundle1 = new Bundle();
						// bundle1.putInt("PRO_FLAG", TrainsActivity.FLAG_gct);
						// // Intent intent = new Intent(baseActivity,
						// // LoanMainActivity.class);
						// Intent intent1 = new Intent(baseActivity,
						// TrainsActivity.class);
						// intent1.putExtras(bundle1);
						// startActivity(intent1);
						// } else {
						// LoginUtilAnother.authorizeAnother(getActivity(),
						// HomeFragment.this, 223);
						// }
						// } else {
						// CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
						// }
						if (baseApplication.isNetStat()) {
							if (LoginUtil.isLog(baseActivity)) {
								Intent intent = new Intent(baseActivity, RiderFristActivity.class);
								startActivity(intent);
							} else {
								LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 223);
							}
						} else {
							CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
						}

						// 车主无忧

						break;
					}
					// 装修通
					case 3: {

						// if (baseApplication.isNetStat()) {
						// if (LoginUtil.isLog(baseActivity)) {
						//
						// Bundle bundle1 = new Bundle();
						// bundle1.putInt("PRO_FLAG", TrainsActivity.FLAG_zxt1);
						// // Intent intent = new Intent(baseActivity,
						// // LoanMainActivity.class);
						// Intent intent1 = new Intent(baseActivity,
						// TrainsActivity.class);
						// intent1.putExtras(bundle1);
						// startActivity(intent1);
						// } else {
						// LoginUtilAnother.authorizeAnother(getActivity(),
						// HomeFragment.this, 224);
						// }
						// } else {
						// CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
						// }

						// 旅游出行

						startLyt();

						break;
					}
					// 消费通
					case 4: {

						// if (baseApplication.isNetStat()) {
						// if (LoginUtil.isLog(baseActivity)) {
						//
						// Bundle bundle1 = new Bundle();
						// bundle1.putInt("PRO_FLAG", TrainsActivity.FLAG_xft);
						// // Intent intent = new Intent(baseActivity,
						// // LoanMainActivity.class);
						// Intent intent1 = new Intent(baseActivity,
						// TrainsActivity.class);
						// intent1.putExtras(bundle1);
						// startActivity(intent1);
						// } else {
						// LoginUtilAnother.authorizeAnother(getActivity(),
						// HomeFragment.this, 225);
						// }
						// } else {
						// CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
						// }

						// 生活秘书
						if (baseApplication.isNetStat()) {
							if (LoginUtil.isLog(baseActivity)) {
								Intent intent = new Intent(baseActivity, XmsMainActivity.class);
								startActivity(intent);
							} else {
								LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 102);
							}

						} else {
							CustomProgressDialog.showBocNetworkSetDialog(getActivity());
						}

						break;
					}

					default:
						break;
					}
				} else {
					switch (position) {
					// 开户通
					// 账户开户
					case 0:
						startKhtcard();
						// liCai();
						break;

					// 余额理财
					case 1:
						startCard();
						// xiaodaitong();
						break;
					// //个贷通
					// 财富
					case 2:
						xiaodaitong();
						// liCai();
						break;
					// 理财通
					case 3:
						Log.i("tag", "start 理财通");
						liCai();
						// startCft();
						break;
					// 购汇通
					case 4:
						Log.i("tag", "start 购汇通");
						startGouhui();
						break;
					// 售汇通
					case 5:
						startJiehui();
						break;
					// 缴费通
					case 6:
						Log.i("tag", "start 缴费通");
						pay();
						break;
					// 财富通
					case 7:
						startCft();
						// startFnt(1);
						break;
					default:
						break;
					}
				}

			}
		});

		// 企业金融服务
		gvFacilitySer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!CustomInfo.isExistCustomInfo(baseActivity)) {
					Log.i("LoginUtil", "requestBocopForCustid");
					if (LoginUtil.isLog(getActivity())) {
						CustomInfo.requestBocopForCustid(baseActivity, false);
					}

				} else {
					Log.i("LoginUtil", "postIdForXms");
					CustomInfo.postLog(baseActivity, homeFacilityLogEvent[position]);
				}
				if (app.getMainType().equals("0")) {
					switch (position) {
					// 开户通
					case 0:
						gotoDetail2A(1);
						break;
					// 企贷通
					case 1:
						gotoDetail2A(3);
						break;

					// 企付通
					case 2:
						gotoDetail1A(3);

						break;
					// 单证通
					case 3:
						gotoDetail2A(2);
						break;
					// 保函通
					case 4:
						gotoDetail2A(0);
						break;
					// 报关通
					case 5:
						gotoDetail1A(0);
						break;
					// E商通
					case 6:
						gotoDetail1A(2);

						break;
					// 车商通
					case 7:
						gotoDetail1A(1);

						break;

					default:

						break;
					}
				} else if (app.getMainType().equals("1")) {
					switch (position) {
					// 开户通
					case 0:
						gotoDetail2A(1);
						break;
					// 企贷通
					case 1:
						gotoDetail2A(3);
						break;

					// 中小通
					case 2:
						startYdt();

						break;
					// 企付通
					case 3:
						gotoDetail1A(3);
						break;
					// 单证通
					case 4:
						gotoDetail2A(2);
						break;
					// 保函通z
					case 5:
						gotoDetail2A(0);
						break;
					// 报关通
					case 6:
						gotoDetail1A(0);
						break;
					// 车商通
					case 7:
						gotoDetail1A(1);

						break;

					default:

						break;
					}
				} else if (app.getMainType().equals("2")) {
					switch (position - 1) {
					// 开户通
					case -1: {

						gm();
						break;
					}
					case 0:
						if (baseApplication.isNetStat()) {
							Intent intent = new Intent(baseActivity, KhtFirstActivity.class);
							startActivity(intent);
						} else {
							CustomProgressDialog.showBocNetworkSetDialog(getActivity());
						}
						break;
					// 办卡通
					case 1:
						// startCard();
						liCai();
						break;

					// 理财通
					case 2:
						// liCai();
						startCft();

						break;
					// 财富通zz
					case 3:
						startCft();
						break;
					// 售汇通
					case 4:
						startJiehui();
						break;
					// 购汇通z
					case 5:
						startGouhui();
						break;

					default:

						break;
					}
				}
			}
		});

		/**
		 * 违章处理
		 */
		llIllegals.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				BaseApplication application = (BaseApplication) baseActivity.getApplication();
				if (application.isNetStat()) {
					if (LoginUtil.isLog(baseActivity)) {
						Intent intent = new Intent(baseActivity, TrafficAssistantMainActivity.class);
						startActivity(intent);
					} else {
						LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 5);
					}
				} else {
					CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
				}
			}
		});
		/**
		 * 小秘书
		 */
		llSecretary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				BaseApplication application = (BaseApplication) baseActivity.getApplication();
				if (application.isNetStat()) {
					if (LoginUtil.isLog(baseActivity)) {
						Intent intent = new Intent(baseActivity, MessageActivity.class);
						startActivity(intent);
					} else {
						LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 1);
					}
				} else {
					CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
				}
			}
		});
		/**
		 * 理财通
		 */
		llFinancial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				BaseApplication application = (BaseApplication) baseActivity.getApplication();
				if (application.isNetStat()) {
					if (LoginUtil.isLog(baseActivity)) {
						getFinances(getActivity());
					} else {
						LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 2);
					}
				} else {
					CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
				}
			}
		});
		/**
		 * 加油服务
		 */
		llHelper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				BaseApplication application = (BaseApplication) baseActivity.getApplication();
				if (application.isNetStat()) {
					if (LoginUtil.isLog(baseActivity)) {
						// ((com.bocop.jxplatform.activity.riders.RiderFristActivity)
						// getActivity()).postUserInfo(id);
						Intent intentAddOil = new Intent(getActivity(), AddGasOilServiceActivity.class);
						startActivity(intentAddOil);
					} else {
						LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 5);
					}
				} else {
					CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

		// show_cai_shen();
	}

	@Override
	protected void initData() {
		super.initData();
		init();
		// 查询是否有新版本
		try {
			freshOauth();
		} catch (Exception e) {
			Log.i("tag", "freshOauth 发送异常");
		}

	}

	private void freshOauth() {
		Log.i("tag", "freshOauth");
		try {
			if (baseApplication.isNetStat()) {
				Update update = new Update(getActivity());
				update.requestVersionFromBocop(new Update.DialogShowingListener() {

					@Override
					public void show(boolean isShowing) {
						if (!isShowing) {
							// showAdDialog(view);
						}
					}
				});
			} else {
				CustomProgressDialog.showBocNetworkSetDialog(getActivity());
			}
		} catch (Exception ex) {
			Log.i("tag", "更新版本发生异常");
		}
	}

	// 轮播图
	private void init() {
		System.out.println("home.....init");
		mAdvList.clear();

		Advertisement adv0 = new Advertisement();
		adv0.setImageRes(R.drawable.sy_lbt01);

		// 养老宝
		Advertisement adv1 = new Advertisement();
		adv1.setImageRes(R.drawable.sy_lbt02);
		// adv1.setContent("http://open.boc.cn/mobile#/search2Detail/12599"); //
		// 网页url

		// 出国金融
		Advertisement adv2 = new Advertisement();
		adv2.setImageRes(R.drawable.sy_lbt03);
		// adv2.setContent("http://open.boc.cn/mobile#/search2Detail/13621");
		// 易社区
		Advertisement adv3 = new Advertisement();
		adv3.setImageRes(R.drawable.sy_lbt04);
		// adv3.setContent("http://open.boc.cn/mobile#/search2Detail/11574");
		// 易商app
		// Advertisement adv4 = new Advertisement();
		// adv4.setImageRes(R.drawable.sy_lbt05);
		Advertisement jdz1 = new Advertisement();
		jdz1.setImageRes(R.drawable.jdz_bl_1);
		Advertisement jdz2 = new Advertisement();
		jdz2.setImageRes(R.drawable.jdz_bl_2);
		Advertisement jdz3 = new Advertisement();
		jdz3.setImageRes(R.drawable.jdz_bl_3);

		Advertisement ytt1 = new Advertisement();
		ytt1.setImageRes(R.drawable.ytt_b1);
		Advertisement ytt2 = new Advertisement();
		ytt2.setImageRes(R.drawable.ytt_b2);
		Advertisement ytt3 = new Advertisement();
		ytt3.setImageRes(R.drawable.ytt_b3);
		Advertisement ytt4 = new Advertisement();
		ytt4.setImageRes(R.drawable.ytt_b4);
		if (mainType.equals("0")) {
			mAdvList.add(adv0);
			mAdvList.add(adv1);
			mAdvList.add(adv2);
			mAdvList.add(adv3);
		} else if (mainType.equals("1")) {
			mAdvList.add(jdz1);
			mAdvList.add(jdz2);
			mAdvList.add(jdz3);
		} else if (mainType.equals("2")) {

			Advertisement ytt11 = new Advertisement();
			ytt11.setImageRes(R.drawable.banner_rail_xx_01);
			Advertisement ytt12 = new Advertisement();
			ytt12.setImageRes(R.drawable.banner_rail_xx_02);
			Advertisement ytt13 = new Advertisement();
			ytt13.setImageRes(R.drawable.banner_ytt_03);

			mAdvList.add(ytt11);
			mAdvList.add(ytt12);
			mAdvList.add(ytt13);
			// mAdvList.add(ytt2);
			// mAdvList.add(ytt3);
			// mAdvList.add(ytt4);
		}

		// mAdvList.add(adv4);
		notifyForAdPic();
	}

	/**
	 * 响应轮播图请求
	 * 
	 * @param retCode
	 * @param response
	 */
	public void notifyForAdPic() {
		if (mAdvList != null && mAdvList.size() > 1) {// 多张图片
			views.clear();
			rltAd.setVisibility(View.VISIBLE);
			ivSingleImage.setVisibility(View.GONE);
			for (Advertisement advertisement : mAdvList) {
				View view = LayoutInflater.from(baseActivity).inflate(R.layout.page_ad, null);
				ImageView iv = (ImageView) view.findViewById(R.id.ivAd);
				iv.setImageResource(advertisement.getImageRes());
				views.add(view);
			}
			LoopViewPagerAdapter adapter = new LoopViewPagerAdapter(views);
			vpAd.setAdapter(adapter);
			indicator.setViewPager(vpAd);
			// 启动轮播图
			if (adHandler.hasMessages(0)) {
				adHandler.removeMessages(0);
			}
			adHandler.sendEmptyMessageDelayed(0, 3000);
		} else if (mAdvList != null && mAdvList.size() == 1) {// 单张图片
			rltAd.setVisibility(View.GONE);
			ivSingleImage.setVisibility(View.VISIBLE);
			ivSingleImage.setImageResource(mAdvList.get(0).getImageRes());
		}
	}

	@OnTouch(R.id.vpAd)
	public boolean onTouch(View v, MotionEvent event) { // 根据触摸情况，判断轮播图触摸事件
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isTouch = true;
			preX = event.getX();
			preY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			isTouch = false;
			nowX = event.getX();
			nowY = event.getY();
			if (nowX == preX && nowY == preY) {
				// 点击跳转事件
				// clickAdPic(vpAd.getCurrentItem());
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			isTouch = false;
			break;
		}
		return false;
	}

	/**
	 * 轮播图点击事件
	 * 
	 * @param index
	 */
	private void clickAdPic(int index) {
		if (mAdvList != null && mAdvList.size() != 0) {
			// if (mAdvList.size() == index + 1) {
			// vpAd.setCurrentItem(0, false);
			// }
			Advertisement adv = mAdvList.get(index);
			String url = adv.getContent();
			Intent intent = new Intent(getActivity(), HomeAdvDetailActivity.class);
			intent.putExtra("url", url);
			startActivity(intent);
		}
	}

	@Override
	public void onLogin() {
	}

	@Override
	public void onCancle() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onException() {
		// TODO Auto-generated method stub
	}

	/**
	 * 获取用户信息，主要是获取手机号为洗车服务做准备
	 */
	private void postUserInfo() {
		RestTemplate restTemplate = new RestTemplate(getActivity());
		RequestParams params = new RequestParams();
		restTemplate.post_nobody("https://openapi.boc.cn/app/useridquery", params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				String string = new String(responseBody);
				System.out.println("查询用户信息------" + string);
				try {
					JSONObject jsonObject = new JSONObject(string);
					String idnoString = jsonObject.optString("idno");
					String mobileNo = jsonObject.optString("mobileno");
					String username = jsonObject.optString("cusname");
					ContentUtils.putSharePre(getActivity(), Constants.SHARED_PREFERENCE_NAME, Constants.ID_NO,
							idnoString);
					ContentUtils.putSharePre(getActivity(), Constants.SHARED_PREFERENCE_NAME, Constants.MOBILENO,
							mobileNo);
					ContentUtils.putSharePre(getActivity(), Constants.SHARED_PREFERENCE_NAME, Constants.USER_NAME,
							username);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

			}
		});
	}

	@Override
	public void onLogin(int position) {
		Log.i("taggg", String.valueOf(position));
		// requestBocopForCustid(getBaseActivity(), false);

		_hepler.login_call(position);

		if (position == 201) {
			getFinances(getActivity());
		}

		if (position == 102) {
			Intent intent = new Intent(baseActivity, MessageActivity.class);
			startActivity(intent);
		}
		if (position == 103) {
			Intent intent = new Intent(baseActivity, BMJFActivity.class);
			startActivity(intent);
		}
		if (position == 222) {

			// Bundle bundle = new Bundle();
			// bundle.putInt("PRO_FLAG", 3);
			// // Intent intent = new Intent(baseActivity,
			// // LoanMainActivity.class);
			// Intent intent = new Intent(baseActivity, TrainsActivity.class);
			// intent.putExtras(bundle);
			// startActivity(intent);

			Bundle bundle = new Bundle();
			bundle.putInt("PRO_FLAG", 3);
			// Intent intent = new Intent(baseActivity,
			// LoanMainActivity.class);
			Intent intent = new Intent(baseActivity, TrainsActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);

			return;
		}
		if (position == 223) {
			/*Bundle bundle1 = new Bundle();
			bundle1.putInt("PRO_FLAG", TrainsActivity.FLAG_gct);
			// Intent intent = new Intent(baseActivity,
			// LoanMainActivity.class);
			Intent intent1 = new Intent(baseActivity, TrainsActivity.class);
			intent1.putExtras(bundle1);
			startActivity(intent1);*/
		}
		if (position == 224) {

			/*Bundle bundle1 = new Bundle();
			bundle1.putInt("PRO_FLAG", TrainsActivity.FLAG_zxt1);
			// Intent intent = new Intent(baseActivity,
			// LoanMainActivity.class);
			Intent intent1 = new Intent(baseActivity, TrainsActivity.class);
			intent1.putExtras(bundle1);
			startActivity(intent1);*/
		}
		if (position == 225) {
			/*Bundle bundle1 = new Bundle();
			bundle1.putInt("PRO_FLAG", TrainsActivity.FLAG_xft);
			// Intent intent = new Intent(baseActivity,
			// LoanMainActivity.class);
			Intent intent1 = new Intent(baseActivity, TrainsActivity.class);
			intent1.putExtras(bundle1);
			startActivity(intent1);*/
		}
		// 健康通
		if (position == 104) {
			Bundle bundle = new Bundle();
			bundle.putString("userId", LoginUtil.getUserId(getActivity()));
			bundle.putString("token", LoginUtil.getToken(getActivity()));
			Intent intent = new Intent(baseActivity, JKETActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		// 鸿运通
		if (position == 105) {
			Intent intent = new Intent(baseActivity, GoldManagerActivity.class);
			startActivity(intent);
		}

		if (position == 301) {
			Intent intent = new Intent(baseActivity, WEIHUIActivity.class);
			startActivity(intent);
		}

		// 售汇通
		if (position == 206) {
			Bundle bundle = new Bundle();
			bundle.putString("flag", "0");
			Intent intent = new Intent(baseActivity, JIEHUIActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		// 购汇通
		if (position == 207) {
			Bundle bundle = new Bundle();
			bundle.putString("flag", "1");
			Intent intent = new Intent(baseActivity, JIEHUIActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		// 公积通
		if (position == 209) {
			Intent intent = new Intent(baseActivity, GjtFirstActivity.class);
			startActivity(intent);
		}
		if (position == 299) {
			Bundle bundleTrain = new Bundle();
			bundleTrain.putString("url", Constants.xmsUrlForTrain);
			bundleTrain.putString("name", "购火车票");
			Intent intentTrain = new Intent(baseActivity, WebViewActivity.class);
			intentTrain.putExtras(bundleTrain);
			startActivity(intentTrain);
		}

		if (position == 205) {
			startFntWithoutLogin(5);
		}
		if (position == 212) {
			startFntWithoutLogin(1);
		}
		if (position == 213) {
			startFntWithoutLogin(2);
		}
		if (position == 214) {
			startFntWithoutLogin(3);
		}

		if (position == 901) {
			Intent intent = new Intent(baseActivity, TrafficAssistantMainActivity.class);
			startActivity(intent);
		}
		// 赣江新区
		if (position == 120) {
			startGJXQDetail4A(0);
		}
		if (position == 121) {
			startGJXQDetail4A(1);
		}
		if (position == 122) {
			startGJXQDetail4A(2);
		}
		if (position == 123) {
			startGJXQDetail4A(3);
		}
		if (position == 124) {
			startGJXQDetail4A(4);
		}
		if (position == 125) {
			startGJXQDetail4A(5);
		}

		if (position == 220) {
			startGJXQDetail1A(0);
		}
		if (position == 221) {
			startGJXQDetail1A(1);
		}
		if (position == 222) {
			startGJXQDetail1A(2);
		}
		if (position == 223) {
			startGJXQDetail1A(3);
		}

		if (position == 230) {
			startGJXQDetail2A(0);
		}
		if (position == 231) {
			startGJXQDetail2A(1);
		}
		if (position == 232) {
			startGJXQDetail2A(2);
		}
		if (position == 233) {
			startGJXQDetail2A(3);
		}
		Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_LONG).show();

	}

	public void postIdForXms() {
		final Context contextXms = getBaseActivity();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		String cardId = ContentUtils.getStringFromSharedPreference(contextXms, Constants.SHARED_PREFERENCE_NAME,
				Constants.CUSTOM_ID_NO);
		String userId = LoginUtil.getUserId(contextXms);
		Log.i("tag22", cardId);
		Log.i("tag22", userId);
		map.put("userId", userId);
		map.put("cardId", cardId);
		final String strGson = gson.toJson(map);
		QztRequestWithJsonAndHead qztRequestWithJsonAndHead = new QztRequestWithJsonAndHead(contextXms);
		qztRequestWithJsonAndHead.postOpbocNoDialog(strGson, BocSdkConfig.qztPostForXmsUrl,
				new QztRequestWithJsonAndHead.CallBackBoc() {
					@Override
					public void onSuccess(String responStr) {
						Log.i("tag22", responStr);
						try {
							ContentUtils.putSharePre(contextXms, Constants.SHARED_PREFERENCE_NAME,
									Constants.CUSTOM_PUT_ALREADY, "1");
							Log.i("tag22", ContentUtils.getStringFromSharedPreference(contextXms,
									Constants.SHARED_PREFERENCE_NAME, Constants.CUSTOM_PUT_ALREADY));
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(String responStr) {
						Log.i("tag33", responStr);
					}

					@Override
					public void onFinish() {
					}
				});
	}

	private void getFinances(final Context context) {
		RestTemplateJxBank restTemplate = new RestTemplateJxBank(context);
		JsonRequestParams params = new JsonRequestParams();
		String action = LoginUtil.getToken(context);
		String userid = LoginUtil.getUserId(context);
		Log.i("action", action);
		Log.i("userid", userid);
		params.put("enctyp", "");
		params.put("password", "");
		params.put("grant_type", "implicit");
		params.put("user_id", userid);
		// params.put("client_secret", MainActivity.CONSUMER_SECRET);
		params.put("client_id", "357"); // 易惠通
		// params.put("client_id", "481");
		params.put("acton", action);
		// https://openapi.boc.cn/bocop/oauth/token
		restTemplate.postGetType("https://openapi.boc.cn/oauth/token", params, new JsonHttpResponseHandler("UTF-8") {
			@Override
			public void onStart() {
				super.onStart();
				progressDialog = new ProgressDialog(context);
				progressDialog.setMessage("正在努力加载中...");
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.show();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				System.out.println("nihao======" + response.toString());
				progressDialog.dismiss();
				Intent intent = new Intent(context, MoneySelectWebView.class);
				intent.putExtra("url",
						"https:/openapi.boc.cn/ezdb/mobileHtml/html/userCenter/index.html?channel=android");
				try {
					intent.putExtra("access_token", response.getString("access_token"));
					intent.putExtra("refresh_token", response.getString("refresh_token"));
					intent.putExtra("user_id", response.getString("user_id"));
					context.startActivity(intent);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
			}
		});
	}

	/**
	 * 小秘书
	 * 
	 * @param view
	 */
	public void smallSecretary() {
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				Intent intent = new Intent(baseActivity, MessageActivity.class);
				startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 102);
			}

		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/**
	 * 金币管理
	 */
	private void gm() {

		// Intent intent = new Intent(baseActivity, GoldManagerActivity.class);
		// startActivity(intent);
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				Intent intent = new Intent(baseActivity, GoldManagerActivity.class);
				startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 105);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}

	}

	/**
	 * 显示广告
	 */
	private void showAdDialog(final View rootView) {
		if (checkTime() && canShowAd()) {
			View view = baseActivity.getLayoutInflater().inflate(R.layout.gm_layout_dialog_ad, null);
			final PopupWindow adWindow = new PopupWindow(view, FormsUtil.SCREEN_WIDTH, FormsUtil.SCREEN_HEIGHT);

			adWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
			ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
			ImageView ivPacket = (ImageView) view.findViewById(R.id.ivPacket);
			ivClose.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					adWindow.dismiss();
				}
			});

			ivPacket.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					adWindow.dismiss();
					requestForPacket(rootView);
				}
			});

			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String str = formatter.format(curDate);
			SharedPreferences sp = getActivity().getSharedPreferences(CacheBean.AD_HASSHOW, Context.MODE_PRIVATE);
			Editor mEditor = sp.edit();
			mEditor.putBoolean(str, true);
			mEditor.commit();
		}
	}

	/**
	 * 显示打开红包页面
	 * 
	 * @param rootView
	 * @param count
	 */
	private void showPacketDialog(final View rootView, final String count) {
		View view = baseActivity.getLayoutInflater().inflate(R.layout.gm_layout_dialog_packet, null);
		final PopupWindow packetWindow = new PopupWindow(view, FormsUtil.SCREEN_WIDTH, FormsUtil.SCREEN_HEIGHT);

		packetWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
		ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
		ImageView ivClickToDraw = (ImageView) view.findViewById(R.id.ivClickToDraw);
		TextView tvAmt = (TextView) view.findViewById(R.id.tvAmt);

		tvAmt.setText(count);
		ivClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				packetWindow.dismiss();
			}
		});

		ivClickToDraw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				packetWindow.dismiss();
				// showADDialog2(rootView, count);
				cacheBean.setCount(count);
				gm();
			}
		});
	}

	/**
	 * 请求红包数量
	 * 
	 * @param rootView
	 */
	private void requestForPacket(final View rootView) {
		RequestBody formBody = new FormEncodingBuilder().build();
		CspUtil cspUtil = new CspUtil(baseActivity);
		cspUtil.postCspNoLogin(BocSdkConfig.RED_PACKET
		// "http://172.23.16.34:8080/dfb_app/common/user/getRedbagInfo.do"
				, formBody, true, new CspUtil.CallBack() {
					@Override
					public void onSuccess(String responStr) {
						JSONObject object;
						String isSuccess = "";
						String count = "";
						try {
							object = new JSONObject(responStr);
							isSuccess = object.getString("result");
							object = new JSONObject(object.getString("message"));
							count = object.getString("redbagcount");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if ("success".equals(isSuccess)) {
							showPacketDialog(rootView, count);
						}
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(String responStr) {
						CspUtil.onFailure(baseActivity, responStr);
					}
				});
	}

	/**
	 * 判断是否过0点
	 * 
	 * @return
	 */
	private boolean checkTime() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		int minuteOfDay = hour * 60 + minute;
		if (minuteOfDay >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断当天是否显示过广告
	 * 
	 * @return
	 */
	private boolean canShowAd() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		SharedPreferences sp = getActivity().getSharedPreferences(CacheBean.AD_HASSHOW, Context.MODE_PRIVATE);
		boolean hasShowed = sp.getBoolean(str, false);
		if (!hasShowed) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Details1Activity "报关通", "车商通", "E商通", "企付通" "CAM 对公自助银行", "中银金融服务中心"
	 */
	private void gotoDetail1A(int flag) {
		if (baseApplication.isNetStat()) {
			// if (LoginUtil.isLog(baseActivity)) {
			startGJXQDetail1A(flag);
			// } else {
			// LoginUtilAnother.authorizeAnother(getActivity(),
			// HomeFragment.this, 220 + flag);
			// }
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	private void startGJXQDetail1A(int FLAG) {
		Bundle bundle = new Bundle();
		bundle.putInt("flag", FLAG);
		Intent intent = new Intent(baseActivity, Details1Activity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * Details1Activity "报关通", "车商通", "E商通", "企付通"
	 */
	private void gotoDetail2A(int flag) {
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				startGJXQDetail2A(flag);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 230 + flag);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	private void gotoDetailytt(int flag) {
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				// startGJXQDetail2A(flag);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, flag);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	private void startGJXQDetail2A(int FLAG) {
		Bundle bundleConsult = new Bundle();

		String url = null;

		if (FLAG == 0) {
			url = BocSdkConfig.qztUrl + "/companyFinance/h5/index.html#/creditCard/main?type=bht&" + "userId="
					+ LoginUtil.getUserId(getActivity()) + "&tokens=" + LoginUtil.getToken(getActivity());
			bundleConsult.putString("name", "保函通");

		} else if (FLAG == 1) {
			url = BocSdkConfig.qztUrl + "/companyFinance/h5/index.html#/openAccount/main?type=kht&" + "userId="
					+ LoginUtil.getUserId(getActivity()) + "&tokens=" + LoginUtil.getToken(getActivity());
			bundleConsult.putString("name", "开户通");

		} else if (FLAG == 2) {
			url = BocSdkConfig.qztUrl + "/companyFinance/h5/index.html#/letterGuarantee/main?type=xzt&" + "userId="
					+ LoginUtil.getUserId(getActivity()) + "&tokens=" + LoginUtil.getToken(getActivity());
			bundleConsult.putString("name", "信证通");

		} else if (FLAG == 3) {
			// url = BocSdkConfig.qztUrl +
			// "/companyFinance/h5/index.html#/enterpriseLoan/main?type=qdt&" +
			// "userId="
			// + LoginUtil.getUserId(getActivity()) + "&tokens=" +
			// LoginUtil.getToken(getActivity());
			url = BocSdkConfig.qztUrl + "/companyFinance/h5/index.html#/enterpriseLoan/main?type=qdt&" + "userId="
					+ LoginUtil.getUserId(getActivity()) + "&tokens=" + LoginUtil.getToken(getActivity())
					+ "&platform=yht";
			// bundleConsult.putString("name", "企贷通");
			bundleConsult.putString("name", "铁路项目贷");

		}

		bundleConsult.putString("url", url);
		Intent intentConsult = new Intent(baseActivity, WebActivity.class);
		intentConsult.putExtras(bundleConsult);
		startActivity(intentConsult);

		// Bundle bundle = new Bundle();
		// bundle.putInt("flag", FLAG);
		// Intent intent = new Intent(baseActivity, Details2Activity.class);
		// intent.putExtras(bundle);
		// startActivity(intent);
	}

	/**
	 * Details3Activity "工商登记", "经营许可", "项目申报", "纳税缴费", "保税园区", "政府信息"
	 */
	private void gotoDetail4A(int flag) {
		if (baseApplication.isNetStat()) {
			// if (LoginUtil.isLog(baseActivity)) {
			startGJXQDetail4A(flag);
			// } else {
			// LoginUtilAnother.authorizeAnother(getActivity(),
			// HomeFragment.this, 120 + flag);
			// }
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	private void startGJXQDetail4A(int FLAG) {
		Bundle bundle = new Bundle();
		bundle.putInt("flag", FLAG);
		Intent intent = new Intent(baseActivity, Details4Activity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * 理财通
	 */
	private void liCai() {
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				getFinances(getActivity());
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 201);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/**
	 * 缴费通
	 */
	private void pay() {
		BaseApplication baseApplication = (BaseApplication) baseActivity.getApplication();
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				Intent intent = new Intent(baseActivity, BMJFActivity.class);
				startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 103);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
		}
	}

	/**
	 * 个贷通
	 */
	private void boced() {
		BaseApplication baseApplication = (BaseApplication) baseActivity.getApplication();
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				Intent intent = new Intent(baseActivity, ZYEDActivity.class);
				startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 6);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
		}
	}

	/**
	 * 健康通
	 */
	private void health() {
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				Bundle bundle = new Bundle();
				bundle.putString("userId", LoginUtil.getUserId(getActivity()));
				bundle.putString("token", LoginUtil.getToken(getActivity()));
				Intent intent = new Intent(baseActivity, JKETActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 104);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/*
	 * 流量红包
	 */
	private void redpaper() {
		Toast.makeText(baseActivity, "敬请期待 ", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 违章处理
	 */
	private void llIllegals() {
		BaseApplication application = (BaseApplication) baseActivity.getApplication();

		if (application.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				Intent intent = new Intent(baseActivity, TrafficAssistantMainActivity.class);
				startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 5);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
		}
	}

	/**
	 * 交通助手
	 */
	private void transHelper() {
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				Intent intent = new Intent(baseActivity, TrafficAssistantMainActivity.class);
				startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 901);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/**
	 * 外购通
	 */
	private void htzq() {

		if (baseApplication.isNetStat()) {
			CustomProgressDialog.showBocFengxianDialog(baseActivity, "免责声明",
					"中国银行信用卡仅作为客户于境外网上商户的支付结算工具，对于商品质量或商户服务无法负责； 本网站引用的网址、商户及商品信息，意在为持卡人海淘行为提供便利，并解决境外海淘所遇常见问题，对境外商户网站合法性、安全性、准确性无法负责；注意网购有风险，如您在海淘过程中如发生商品质量、转运服务、货物丢失、延迟收货等购物纠纷，请您直接与购物网站或转运服务商沟通解决；境外购物需遵守国家相关法律、法规。 ");

		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/**
	 * 外购通
	 */
	private void bocexy() {
		Intent intent = new Intent(baseActivity, EXYActivity.class);
		startActivity(intent);
	}

	/**
	 * 跳转到车友会的方法
	 */
	private void startCyh() {
		Intent intent = new Intent(baseActivity, RiderFristActivity.class);
		startActivity(intent);
	}

	/**
	 * 跳转扶微通、扶农通的方法
	 */
	private void startFnt(int FLAG) {

		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				goToZZYD(FLAG);

			} else {
				// 扶贫
				if (FLAG == 1) {
					LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 212);
				}
				// 扶农
				// if(FLAG == 3){
				// LoginUtilAnother.authorizeAnother(getActivity(),
				// HomeFragment.this, 213);
				// }
				// 扶微
				if (FLAG == 2) {
					LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 213);
				}
			}

		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	private void startFntWithoutLogin(int FLAG) {
		Bundle bundle = new Bundle();
		bundle.putInt("PRO_FLAG", FLAG);
		// Intent intent = new Intent(baseActivity, LoanMainActivity.class);
		Intent intent = new Intent(baseActivity, TrainsActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * 跳转到中银E贷界面
	 */
	private void goToZZYD(final int FLAG) {

		if (!BocSdkConfig.isTest) {
			if (null != CacheBean.getInstance().get(CacheBean.CUST_ID)
					&& !TextUtils.isEmpty(CacheBean.getInstance().get(CacheBean.CUST_ID).toString())) {
				if (checkTime()) {
					startFntWithoutLogin(FLAG);
				} else {
					Toast.makeText(baseActivity, "温馨提示：每日 07:00  --  21:00 提供服务。", Toast.LENGTH_SHORT).show();
				}
			} else {
				LoginUtil.requestBocopForCustid(baseActivity, true, new LoginUtil.OnRequestCustCallBack() {

					@Override
					public void onSuccess() {
						if (checkTime()) {
							startFntWithoutLogin(FLAG);
						} else {
							Toast.makeText(baseActivity, "温馨提示：每日 07:00  --  21:00 提供服务。", Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		} else {
			CacheBean.getInstance().put(CacheBean.CUST_ID, "");
			startFntWithoutLogin(FLAG);
		}

	}

	/**
	 * 跳转到签证通的方法
	 */
	private void startQzt() {
		if (baseApplication.isNetStat()) {
			Intent intent = new Intent(baseActivity, NewQztFristActivity.class);
			startActivity(intent);
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/**
	 * 跳转到签证通的方法
	 */
	private void youpingtong() {
		Intent intent = new Intent(baseActivity, YPTActivity.class);
		startActivity(intent);
	}

	/**
	 * 跳转到卡惠通的方法
	 */
	private void startKht() {

		if (LoginUtil.isLog(baseActivity)) {
			Intent intent = new Intent(baseActivity, WEIHUIActivity.class);
			startActivity(intent);
		} else {
			LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 301);
		}
	}

	/**
	 * 跳转到旅游通的方法
	 */
	private void startLyt() {

		if (baseApplication.isNetStat()) {
			Intent intent = new Intent(baseActivity, LYTActivity.class);
			startActivity(intent);
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/**
	 * 购汇通
	 */
	private void startGouhui() {

		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				Bundle bundle = new Bundle();
				bundle.putString("flag", "1");
				Intent intent = new Intent(baseActivity, JIEHUIActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 207);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/**
	 * 个贷通
	 */
	private void xiaodaitong() {
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				startFntWithoutLogin(5);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 205);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/**
	 * 结汇
	 */
	private void startJiehui() {

		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				Bundle bundle = new Bundle();
				bundle.putString("flag", "0");
				Intent intent = new Intent(baseActivity, JIEHUIActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 206);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/**
	 * 跳转到车友会的方法
	 */
	private void startCft() {
		// CustomProgressDialog.showBocFengxianDialog(baseActivity,"免责声明","中国银行信用卡仅作为客户于境外网上商户的支付结算工具，对于商品质量或商户服务无法负责；
		// 本网站引用的网址、商户及商品信息，意在为持卡人海淘行为提供便利，并解决境外海淘所遇常见问题，对境外商户网站合法性、安全性、准确性无法负责；注意网购有风险，如您在海淘过程中如发生商品质量、转运服务、货物丢失、延迟收货等购物纠纷，请您直接与购物网站或转运服务商沟通解决；境外购物需遵守国家相关法律、法规。
		// ");
		if (baseApplication.isNetStat()) {

			Bundle bundle = new Bundle();
			bundle.putString("url", BocSdkConfig.CFT);
			bundle.putString("name", "财富通");
			Intent intent = new Intent(baseActivity, WebActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			//
			// Intent intent = new Intent(baseActivity, CFTActivity.class);
			// startActivity(intent);
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	private void startCard() {
		// CustomProgressDialog.showBocFengxianDialog(baseActivity,"免责声明","中国银行信用卡仅作为客户于境外网上商户的支付结算工具，对于商品质量或商户服务无法负责；
		// 本网站引用的网址、商户及商品信息，意在为持卡人海淘行为提供便利，并解决境外海淘所遇常见问题，对境外商户网站合法性、安全性、准确性无法负责；注意网购有风险，如您在海淘过程中如发生商品质量、转运服务、货物丢失、延迟收货等购物纠纷，请您直接与购物网站或转运服务商沟通解决；境外购物需遵守国家相关法律、法规。
		// ");

		Bundle bundle = new Bundle();
		bundle.putString("url", BocSdkConfig.CARD);
		bundle.putString("name", "办卡通");
		Intent intent = new Intent(baseActivity, WebActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);

		// Intent intent = new Intent(baseActivity, CardActivity.class);
		// startActivity(intent);
	}

	private void startZdt() {
		if (baseApplication.isNetStat()) {
			startFntWithoutLogin(6);
			// Intent intent = new Intent(baseActivity,
			// FinanceMainActivity.class);
			// startActivity(intent);
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	private void startZqt() {
		if (baseApplication.isNetStat()) {
			Intent intent = new Intent(baseActivity, ZqtActivity.class);
			startActivity(intent);
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/*
	 * 开户通
	 */

	private void startKhtcard() {
		if (baseApplication.isNetStat()) {
			Bundle bundle = new Bundle();
			bundle.putString("url", Constants.UrlForMainKhtcard);
			bundle.putString("name", "开户通");
			Intent intent = new Intent(baseActivity, WebActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/*
	 * 易贷通
	 */

	private void startYdt() {
		if (baseApplication.isNetStat()) {
			Bundle bundle = new Bundle();
			bundle.putString("url", Constants.UrlForMainYdt);
			// bundle.putString("url",
			// "http://NOzXDKNY.scene.eqxiu.cn/s/NOzXDKNY");
			bundle.putString("name", "中小通");
			Intent intent = new Intent(baseActivity, WebActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/*
	 * 公基通
	 */

	private void startGjt() {

		BaseApplication baseApplication = (BaseApplication) baseActivity.getApplication();
		if (baseApplication.isNetStat()) {
			if (LoginUtil.isLog(baseActivity)) {
				startFntWithoutLogin(3);
				// Intent intent = new Intent(baseActivity,
				// GjtFirstActivity.class);
				// startActivity(intent);
			} else {
				LoginUtilAnother.authorizeAnother(getActivity(), HomeFragment.this, 209);
			}
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(baseActivity);
		}

		// Intent intent = new Intent(baseActivity,GjtFirstActivity.class);
		// startActivity(intent);

		// Toast.makeText(baseActivity, "敬请期待 ", Toast.LENGTH_SHORT).show();
		// if (baseApplication.isNetStat()) {
		// Bundle bundle = new Bundle();
		// bundle.putString("url", Constants.UrlForMainKhtcard);
		// bundle.putString("name", "公积通");
		// Intent intent = new Intent(baseActivity,
		// XmsWebActivity.class);
		// intent.putExtras(bundle);
		// startActivity(intent);
		// }else {
		// CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		// }
	}

	/*
	 * 校园通
	 */

	private void startXyt() {
		if (baseApplication.isNetStat()) {
			Bundle bundle = new Bundle();
			bundle.putString("url", Constants.UrlForMainXyt);
			bundle.putString("name", "校园通");
			Intent intent = new Intent(baseActivity, WebActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		} else {
			CustomProgressDialog.showBocNetworkSetDialog(getActivity());
		}
	}

	/**
	 * 启动第三方 app
	 * 
	 * @param mcontext
	 * @param packagename
	 */
	public static void doStartApplicationWithPackageName(Context mcontext, String packagename) {

		// 通过包名获取此 APP 详细信息，包括 Activities、 services 、versioncode 、 name等等
		PackageInfo packageinfo = null;
		try {
			packageinfo = mcontext.getPackageManager().getPackageInfo(packagename, 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		if (packageinfo == null) {
			return;
		}

		// 创建一个类别为 CATEGORY_LAUNCHER 的该包名的 Intent
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(packageinfo.packageName);

		// 通过 getPackageManager()的 queryIntentActivities 方法遍历
		List<ResolveInfo> resolveinfoList = mcontext.getPackageManager().queryIntentActivities(resolveIntent, 0);

		ResolveInfo resolveinfo = resolveinfoList.iterator().next();
		if (resolveinfo != null) {
			// packagename = 参数 packname
			String packageName = resolveinfo.activityInfo.packageName;
			// 这个就是我们要找的该 APP 的LAUNCHER 的 Activity[组织形式：
			// packagename.mainActivityname]
			String className = resolveinfo.activityInfo.name;
			// LAUNCHER Intent
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);

			// 设置 ComponentName参数 1:packagename 参数2:MainActivity 路径
			ComponentName cn = new ComponentName(packageName, className);

			intent.setComponent(cn);
			mcontext.startActivity(intent);
		} else {
			Intent intent = new Intent(mcontext, EXYActivity.class);
			mcontext.startActivity(intent);
		}
	}

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	//
	// sensorManager = (SensorManager)
	// getActivity().getSystemService(Activity.SENSOR_SERVICE);
	// vibrator = (Vibrator)
	// getActivity().getSystemService(Activity.VIBRATOR_SERVICE);
	//
	// if (sensorManager != null) {// 注册监听器
	// sensorManager.registerListener(sensorEventListener,
	// sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	// SensorManager.SENSOR_DELAY_NORMAL);
	// // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
	// }
	// }

	// @Override
	// public void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// if (sensorManager != null) {// 取消监听器
	// sensorManager.unregisterListener(sensorEventListener);
	// }
	// }

	public void show_cai_shen() {

		// 首次登录，检查

		if (selectDialog == null || !selectDialog.isShowing()) {

			selectDialog = new Dialog(getActivity(), R.style.dialog_tr_lt);
			selectDialog.setCancelable(true);
			/* 设置普通对话框的布局 */

			View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_cai_shen_lt, null);
			selectDialog.setContentView(v);

			WindowManager windowManager = getActivity().getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			WindowManager.LayoutParams lp = selectDialog.getWindow().getAttributes();
			lp.width = (int) (display.getWidth()); // 设置宽度
			selectDialog.getWindow().setAttributes(lp);
			//
			ImageView iv_main = (ImageView) v.findViewById(R.id.iv_main);
			AnimationDrawable animaition = (AnimationDrawable) iv_main.getBackground();
			// 最后，就可以启动动画了，代码如下：
			// 是否仅仅启动一次？
			animaition.setOneShot(false);

			animaition.start();

			selectDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub

				}
			});
			selectDialog.show();// 显示对话框
		}

	}

	public void showZonZ() {

		if (selectDialog != null && selectDialog.isShowing()) {
			selectDialog.dismiss();
		}

		if (dz != null && dz.isShowing()) {
			return;
		}
		dz = new Dialog(getActivity(), R.style.dialog_tr_lt);
		dz.setCancelable(false);
		/* 设置普通对话框的布局 */

		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_cai_shen_lt_znz, null);
		dz.setContentView(v);

		WindowManager windowManager = getActivity().getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dz.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		dz.getWindow().setAttributes(lp);
		//
		ImageView iv_main = (ImageView) v.findViewById(R.id.iv_main);
		AnimationDrawable animaition = (AnimationDrawable) iv_main.getBackground();
		iv_main.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dz.dismiss();
			}
		});
		int a = (int) (Math.random() * 10);

		if (a % 2 == 0) {
			iv_main.setImageResource(R.drawable.cai_shen_no_zhong);

		} else {
			iv_main.setImageResource(R.drawable.cai_shen_zhong);

		}
		iv_main.setImageResource(R.drawable.cai_shen_zhong);

		dz.show();// 显示对话框

	}

	private SensorManager sensorManager;
	private Vibrator vibrator;

	private static final int UPTATE_INTERVAL_TIME = 50;
	private static final int SPEED_SHRESHOLD = 20;// 这个值调节灵敏度
	private long lastUpdateTime;
	private float lastX;
	private float lastY;
	private float lastZ;
	/**
	 * 重力感应监听
	 */
	private SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			long currentUpdateTime = System.currentTimeMillis();
			long timeInterval = currentUpdateTime - lastUpdateTime;
			if (timeInterval < UPTATE_INTERVAL_TIME) {
				return;
			}
			lastUpdateTime = currentUpdateTime;
			// 传感器信息改变时执行该方法
			float[] values = event.values;
			float x = values[0]; // x轴方向的重力加速度，向右为正
			float y = values[1]; // y轴方向的重力加速度，向前为正
			float z = values[2]; // z轴方向的重力加速度，向上为正
			float deltaX = x - lastX;
			float deltaY = y - lastY;
			float deltaZ = z - lastZ;

			lastX = x;
			lastY = y;
			lastZ = z;
			double speed = (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / timeInterval) * 100;
			if (speed >= SPEED_SHRESHOLD) {
				vibrator.vibrate(300);

				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						showZonZ();
					}
				});
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	
	
}
