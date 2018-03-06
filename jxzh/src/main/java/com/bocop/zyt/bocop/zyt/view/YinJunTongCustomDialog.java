package com.bocop.zyt.bocop.zyt.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.yfx.utils.ToastUtils;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseAdapter;

import java.util.List;


public class YinJunTongCustomDialog extends Dialog implements View.OnClickListener {

	private EditText name;
	private Spinner sex;
	private EditText cardNo;
	private Spinner branch;
	private EditText call;
	private Context context;
	private OnCancelListener onCancelListener;
	private OnUpListener onUpListener;
	private PopupWindow pop;

	public interface OnCancelListener {
		void onCancel(YinJunTongCustomDialog dailog);
	}

	public interface OnUpListener {
		void onUp(YinJunTongCustomDialog dialog);
	}

	public YinJunTongCustomDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		this.setContentView(R.layout.dialog_yin_jun_tong_info);
		int[] screens = ScreenUtil.get_screen_size(context);
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = (int) (screens[0] * 0.8);
		dialogWindow.setAttributes(lp);
		initView();
		initEvent();
	}

	private void initView() {
		name = (EditText) findViewById(R.id.et_name);
		sex = (Spinner) findViewById(R.id.sp_sex);
		initSpinnerData(sex, R.array.sex);
		cardNo = (EditText) findViewById(R.id.et_cardNo);
		branch = (Spinner) findViewById(R.id.sp_branch);
		initSpinnerData(branch, R.array.branch);
		call = (EditText) findViewById(R.id.et_call);
		initPopupWindow();
	}

	private void initSpinnerData(Spinner sp, int resouceId) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item) {
			/*@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				CheckedTextView ctv = (CheckedTextView) v.findViewById(android.R.id.text1);
				if (position == 0) {
					ctv.setTextColor(color.gray);
				}
				return v;
			}*/
		};
		String level[] = context.getResources().getStringArray(resouceId);// 资源文件
		for (int i = 0; i < level.length; i++) {
			adapter.add(level[i]);
		}
		adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);
	}

	private void initPopupWindow() {
		// 创建PopupWindow，参数为显示对象，宽，高
		pop = new PopupWindow(context);
		View v = View.inflate(context, R.layout.pop_sex, null);
		pop.setContentView(v);
		// PopupWindow的设置
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 点击外边消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);
	}

	private void initEvent() {
		findViewById(R.id.close).setOnClickListener(this);
		findViewById(R.id.btn_up).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.close:
			onCancelListener.onCancel(this);
			break;
		case R.id.btn_up:
			if (TextUtils.isEmpty(name.getText().toString())) {
				ToastUtils.show(context, "请输入姓名信息", Toast.LENGTH_SHORT);
				return;
			} /*else if (sex.getSelectedItemPosition()==0) {
				ToastUtils.show(context, "请选择性别信息", Toast.LENGTH_SHORT);
				return;
			} */else if (TextUtils.isEmpty(cardNo.getText().toString())) {
				ToastUtils.show(context, "请输入军人卡卡号信息", Toast.LENGTH_SHORT);
				return;
			} /*else if (branch.getSelectedItemPosition()==0) {
				ToastUtils.show(context, "请选择开户行信息", Toast.LENGTH_SHORT);
				return;
			} */else if (TextUtils.isEmpty(call.getText().toString())) {
				ToastUtils.show(context, "请输入常用联系人信息", Toast.LENGTH_SHORT);
				return;
			}
			onUpListener.onUp(this);
			break;
		default:
			break;
		}
	}

	public void setOnCancelListener(OnCancelListener onCancelListener) {
		this.onCancelListener = onCancelListener;
	}

	public void setOnUpListener(OnUpListener onUpListener) {
		this.onUpListener = onUpListener;
	}

	public class PopListViewAdapter extends BaseAdapter<String> {

		public PopListViewAdapter(BaseActivity activity, List<String> tList, int layoutResId) {
			super(activity, tList, layoutResId);
		}

		@Override
		public void viewHandler(int position, String t, View convertView) {
			TextView context = (TextView) convertView.findViewById(R.id.tv_context);
			context.setText(t);
		}

	}

}
