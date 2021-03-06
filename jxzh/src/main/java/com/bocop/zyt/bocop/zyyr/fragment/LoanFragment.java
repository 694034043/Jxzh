package com.bocop.zyt.bocop.zyyr.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.FormsUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.xms.bean.ConstHead;
import com.bocop.zyt.bocop.xms.tools.ViewHolder;
import com.bocop.zyt.bocop.xms.utils.XStreamUtils;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.bocop.zyyr.activity.LoanDetailsActivity;
import com.bocop.zyt.bocop.zyyr.activity.MyLoanActivity;
import com.bocop.zyt.bocop.zyyr.bean.CommonResponse;
import com.bocop.zyt.bocop.zyyr.bean.LoanListDetails;
import com.bocop.zyt.bocop.zyyr.bean.LoanResponse;
import com.bocop.zyt.bocop.zyyr.bean.StatusResponse;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseAdapter;
import com.bocop.zyt.jx.base.BaseFragment;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.tools.DialogUtil;
import com.bocop.zyt.jx.tools.ImageViewUtil;
import com.bocop.zyt.jx.view.LoadingView;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenu;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenuCreator;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenuItem;
import com.bocop.zyt.jx.view.swipemenulistview.SwipeMenuListView;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * ????????????
 *
 * @author lh
 *
 */
public class LoanFragment extends BaseFragment implements OnScrollListener {

	@ViewInject(R.id.lvLoan)
	private SwipeMenuListView lvLoan;
	@ViewInject(R.id.loadingView)
	private LoadingView loadingView;

	List<LoanListDetails> list = new ArrayList<>();
	BaseAdapter<LoanListDetails> adapter;
	private View footerView;
	private LinearLayout llLoading;
	private TextView tvTips;
	private int vItemCount;
	private int pageIndex = 1;
	private boolean canLoadMore = true;
	private int deletePosition;

	/** ???????????????????????????????????? */
	private String status;

	private boolean HIDDEN_FLAG = false;

	private boolean FIRST_REQUEST_FLAG = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = initView(R.layout.zyyr_fragment_loan_all);
		return view;
	}

	@Override
	protected void initData() {
		super.initData();
		lvLoan.setAdapter(adapter = new LoanAdapter(baseActivity, list));
	}

	@Override
	protected void initView() {
		super.initView();

		footerView = LayoutInflater.from(baseActivity).inflate(R.layout.common_layout_listview_footer, null);
		llLoading = (LinearLayout) footerView.findViewById(R.id.llLoading);
		tvTips = (TextView) footerView.findViewById(R.id.tvTips);
		footerView.setVisibility(View.GONE);
		lvLoan.addFooterView(footerView, null, false);

		SwipeMenuCreator menuCreator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				/** ?????????????????? */
				SwipeMenuItem deleteItem = new SwipeMenuItem(baseActivity);
				deleteItem.setBackground(R.color.redLight);
				deleteItem.setWidth(FormsUtil.dip2px(90));
				deleteItem.setIcon(R.drawable.ic_delete);
				menu.addMenuItem(deleteItem);
			}
		};

		lvLoan.setMenuCreator(menuCreator);

		initListener();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			/** ????????????????????????????????????????????????????????????????????????????????????????????? */
			if (MyLoanActivity.DELETE_FLAG) {
				if ("0".equals(status) || "1".equals(status) || "3".equals(status)) {
					pageIndex = 1;
					requestLoanList(true);
				}
			} else {
				if (HIDDEN_FLAG && FIRST_REQUEST_FLAG) {
					FIRST_REQUEST_FLAG = false;
					requestLoanList(true);
				}
			}
		} else {
			HIDDEN_FLAG = true;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		/** ????????????????????????????????????????????????????????????????????????????????????????????? */
		if ("0".equals(status) || "1".equals(status) || "3".equals(status)) {
			if (MyLoanActivity.DELETE_FLAG) {
				pageIndex = 1;
				requestLoanList(true);
			}
		}
	}

	private void initListener() {
		lvLoan.setOnScrollListener(this);

		lvLoan.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {

				deletePosition = position;
				switch (index) {
				case 0:// ??????
					DialogUtil.showWithTwoBtn(baseActivity, "???????????????????????????", "??????", "??????",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
									String appId = list.get(position).getAppID();
									requestDeleteLoan(appId);
								}
							}, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							});
					break;
				}
				return false;
			}
		});

		lvLoan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position != lvLoan.getCount() - 1) {
					Intent intent = new Intent(baseActivity, LoanDetailsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("APP_ID", list.get(position).getAppID());
					bundle.putString("STATUS", list.get(position).getAppStatus());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
	}

	/**
	 * ??????????????????
	 *
	 * @param isShowDialog
	 *            ????????????Dialog
	 */
	private void requestLoanList(final boolean isShowDialog) {
		RequestBody formBody = new FormEncodingBuilder().add("userId", LoginUtil.getUserId(baseActivity))
				.add("appStatus", status).add("page", String.valueOf(pageIndex)).build();
		CspUtil cspUtil = new CspUtil(baseActivity);
		cspUtil.postCspNoLogin(BocSdkConfig.ZYYR_LOAN_LIST, formBody, isShowDialog, new CspUtil.CallBack() {
			@Override
			public void onSuccess(String responStr) {
				onGetListSuccess(responStr);
			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onFailure(String responStr) {
				loadingView.setVisibility(View.VISIBLE);
				lvLoan.setVisibility(View.GONE);
				loadingView.setmOnRetryListener(new LoadingView.OnRetryListener() {

					@Override
					public void retry() {
						requestLoanList(isShowDialog);
					}
				});
			}
		});
	}

	/**
	 * ????????????
	 *
	 * @param appId
	 *            ???????????????
	 */
	private void requestDeleteLoan(String appId) {
		RequestBody formBody = new FormEncodingBuilder().add("appId", appId).build();
		CspUtil cspUtil = new CspUtil(baseActivity);
		cspUtil.postCspNoLogin(BocSdkConfig.ZYYR_DELETE_LOAN, formBody, true, new CspUtil.CallBack() {
			@Override
			public void onSuccess(String responStr) {
				StatusResponse statusResponse = XStreamUtils.getFromXML(responStr, StatusResponse.class);
				ConstHead constHead = statusResponse.getConstHead();
				if (null != constHead && "00".equals(constHead.getErrCode())) {
					MyLoanActivity.DELETE_FLAG = true;
					list.remove(deletePosition);
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(baseActivity, R.string.applyFailure, Toast.LENGTH_SHORT).show();
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * ?????????????????????
	 *
	 * @param responStr
	 */
	private void onGetListSuccess(String responStr) {
		loadingView.setVisibility(View.GONE);
		lvLoan.setVisibility(View.VISIBLE);
		LoanResponse loanResponse = XStreamUtils.getFromXML(responStr, LoanResponse.class);
		ConstHead constHead = loanResponse.getConstHead();
		if (null != constHead && "00".equals(constHead.getErrCode())) {
			CommonResponse commonResponse = loanResponse.getList().getCommonResponse();
			pageIndex = Integer.parseInt(commonResponse.getCurrentPage());
			if (pageIndex == 1) {
				list.clear();
			}
			if (pageIndex == Integer.parseInt(commonResponse.getTotalPages())) {
				canLoadMore = false;
			}
			list.addAll(loanResponse.getList().getList());
			adapter.notifyDataSetChanged();
		} else if ("01".equals(constHead.getErrCode())) {
			ToastUtils.showInfo(baseActivity, "????????????", Toast.LENGTH_SHORT);
		}
		if (!canLoadMore) {
			footerView.setVisibility(View.VISIBLE);
			llLoading.setVisibility(View.GONE);
			tvTips.setVisibility(View.VISIBLE);
		} else {
			footerView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int lastItem = adapter.getCount();
		if (scrollState == 0) {
			// ???????????????item???????????????item???????????????
			if (vItemCount == lastItem) {
				if (canLoadMore) {
					pageIndex++;
					footerView.setVisibility(View.VISIBLE);
					llLoading.setVisibility(View.VISIBLE);
					tvTips.setVisibility(View.GONE);
					requestLoanList(false);
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		vItemCount = firstVisibleItem + visibleItemCount - 1;
	}

	private class LoanAdapter extends BaseAdapter<LoanListDetails> {

		private float preX = 0, preY = 0, nowX = 0, nowY = 0;

		public LoanAdapter(BaseActivity activity, List<LoanListDetails> tList, int layoutResId) {
			super(activity, tList, layoutResId);
			// TODO Auto-generated constructor stub
		}

		public LoanAdapter(BaseActivity activity, List<LoanListDetails> tList) {
			this(activity, tList, R.layout.zyyr_item_my_loan);
		}

		@Override
		public void viewHandler(int position, final LoanListDetails t, View convertView) {
			TextView tvProType = ViewHolder.get(convertView, R.id.tvProType);
			TextView tvProIntro = ViewHolder.get(convertView, R.id.tvProIntro);
			TextView tvStatus = ViewHolder.get(convertView, R.id.tvStatus);
			ImageView ivProIcon = ViewHolder.get(convertView, R.id.ivProIcon);
			final RelativeLayout rlItem = ViewHolder.get(convertView, R.id.rlItem);

			if (null != t) {
				tvProType.setText(t.getProName());
				tvProIntro.setText(t.getProDesc());
				tvStatus.setText(t.getAppStatus());

				if ("1".equals(t.getAppStatus())) {// ?????????
					tvStatus.setText("?????????");
					tvStatus.setTextColor(getResources().getColor(R.color.TextColor_applying));
				} else if ("2".equals(t.getAppStatus())) {// ?????????
					tvStatus.setText("?????????");
					tvStatus.setTextColor(getResources().getColor(R.color.TextColor_finished));
				} else if ("3".equals(t.getAppStatus())) {// ?????????
					tvStatus.setText("?????????");
					tvStatus.setTextColor(getResources().getColor(R.color.TextColor_cancel));
				}

				baseActivity.getBaseApp().getImageLoader().displayImage(t.getProLogo(), ivProIcon,
						ImageViewUtil.getOption());
			}

			rlItem.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					lvLoan.smoothCloseMenu();
					if ("1".equals(t.getAppStatus())) {
						/** ?????????true????????????????????? */
						lvLoan.setFLAG(true);
						return false;
					} else {
						/** ?????????false?????????????????????????????????????????????????????? */
						lvLoan.setFLAG(false);
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							preX = event.getX();
							preY = event.getY();
							rlItem.setBackgroundColor(getResources().getColor(R.color.gray));
							break;
						case MotionEvent.ACTION_UP:
							nowX = event.getX();
							nowY = event.getY();

							if ((Math.abs(nowX - preX) < 10) && (Math.abs(nowY - preY) < 10)) {
								Intent intent = new Intent(baseActivity, LoanDetailsActivity.class);
								intent.putExtra("APP_ID", t.getAppID());
								startActivity(intent);
							}
							rlItem.setBackgroundColor(getResources().getColor(R.color.white));
							break;
						case MotionEvent.ACTION_CANCEL:
							rlItem.setBackgroundColor(getResources().getColor(R.color.white));
							break;
						}
						return true;
					}
				}
			});
		}
	}
}
