package com.framewidget.newMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.framewidget.R;
import com.mdx.framework.prompt.MDialog;
import com.mdx.framework.widget.spinnerwheel.AbstractWheel;
import com.mdx.framework.widget.spinnerwheel.OnWheelChangedListener;
import com.mdx.framework.widget.spinnerwheel.adapters.NumericWheelAdapter;

public class DateDfSelectDialog extends MDialog {
	private AbstractWheel yearWheel, monthWheel, dayWheel, thirdWheel;

	private TextView title;

	private Button submit;

	private NumericWheelAdapter monthada, yearada, dayada, thirdda;

	private int syear = 1995, smonth = 1, sday = 1;

	private int minYear = 1900, maxYear = 2099;

	public OnSelected onSelected;

	@SuppressLint("SimpleDateFormat")
	public DateDfSelectDialog(Context context, String now) {
		super(context, R.style.Dialog);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date data;
			if (TextUtils.isEmpty(now)) {
				data = new Date();
			} else {
				data = format.parse(now);
			}
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(data.getTime());
			syear = (cal.get(Calendar.YEAR));
			smonth = cal.get(Calendar.MONTH);
			sday = cal.get(Calendar.DAY_OF_MONTH) - 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		WindowManager.LayoutParams lp = getWindow().getAttributes();
		getWindow().setGravity(Gravity.CENTER | Gravity.BOTTOM);
		getWindow().setAttributes(lp);

	}

	public void clear() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		syear = (cal.get(Calendar.YEAR));
		smonth = cal.get(Calendar.MONTH);
		sday = cal.get(Calendar.DAY_OF_MONTH) - 1;
		if(yearWheel!=null){
			yearWheel.setCurrentItem(syear - minYear);
			monthWheel.setCurrentItem(smonth);
			dayWheel.setCurrentItem(sday);
			thirdWheel.setCurrentItem(0);
		}
	}

	public DateDfSelectDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void create(Bundle savedInstanceState) {
		this.setContentView(R.layout.default_dialog_df_sel);
		title = (TextView) findViewById(R.id.title);
		submit = (Button) findViewById(R.id.submit);
		yearWheel = (AbstractWheel) findViewById(R.id.first);
		monthWheel = (AbstractWheel) findViewById(R.id.second);
		dayWheel = (AbstractWheel) findViewById(R.id.threed);
		thirdWheel = (AbstractWheel) findViewById(R.id.third);

		{
			yearada = new NumericWheelAdapter(this.getContext(), minYear,
					maxYear);
			yearWheel.setViewAdapter(yearada);
			yearWheel.setCurrentItem(syear - minYear);
		}

		{
			monthada = new NumericWheelAdapter(this.getContext(), 1, 12);
			monthWheel.setViewAdapter(monthada);
			monthWheel.setCurrentItem(smonth);
		}

		{
			dayada = new NumericWheelAdapter(this.getContext(), 1, 31);
			dayWheel.setViewAdapter(dayada);
			dayWheel.setCurrentItem(sday);
		}
		{
			thirdda = new NumericWheelAdapter(this.getContext(), 0, 23);
			thirdWheel.setViewAdapter(thirdda);
			thirdWheel.setCurrentItem(0);
		}

		yearWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(AbstractWheel wheel, int oldValue,
					int newValue) {
				title.setText(getText());
				settowheel(newValue);
				yearWheel.getViewAdapter().getItem(yearWheel.getCurrentItem());
			}
		});

		monthWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(AbstractWheel wheel, int oldValue,
					int newValue) {
				title.setText(getText());
				settowheel(newValue);
			}
		});

		dayWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(AbstractWheel wheel, int oldValue,
					int newValue) {
				title.setText(getText());
			}
		});
		thirdWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(AbstractWheel wheel, int oldValue,
					int newValue) {
				title.setText(getText());
			}
		});
		title.setText(getText());

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (onSelected != null) {
					onSelected.onSelected(DateDfSelectDialog.this, getText());
				}
				dismiss();
			}
		});
	}

	private void settowheel(int ind) {
		sday = dayWheel.getCurrentItem();
		int maxvalue = getMonthLastDay((Integer) yearWheel.getViewAdapter()
				.getItem(yearWheel.getCurrentItem()), (Integer) monthWheel
				.getViewAdapter().getItem(monthWheel.getCurrentItem()));
		if (sday >= maxvalue) {
			dayWheel.setCurrentItem(maxvalue - 1);
		}
		dayada.setMaxValue(maxvalue);
	}

	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 鎶婃棩鏈熻缃负褰撴湀绗竴澶�
		a.roll(Calendar.DATE, -1);// 鏃ユ湡鍥炴粴涓�ぉ锛屼篃灏辨槸鏈�悗涓�ぉ
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public int getMinYear() {
		return minYear;
	}

	public void setMinYear(int minYear) {
		this.minYear = minYear;
	}

	public int getMaxYear() {
		return maxYear;
	}

	public void setMaxYear(int maxYear) {
		this.maxYear = maxYear;
	}

	public void setOnSelected(OnSelected onSelected) {
		this.onSelected = onSelected;
	}

	public String getText() {
		return yearWheel.getViewAdapter().getItem(yearWheel.getCurrentItem())
				+ "-"
				+ monthWheel.getViewAdapter().getItem(
						monthWheel.getCurrentItem())
				+ "-"
				+ dayWheel.getViewAdapter().getItem(dayWheel.getCurrentItem())
				+ " "
				+ thirdWheel.getViewAdapter().getItem(
						thirdWheel.getCurrentItem());
	}

	public interface OnSelected {
		public void onSelected(Dialog dia, String selected);
	}
}
