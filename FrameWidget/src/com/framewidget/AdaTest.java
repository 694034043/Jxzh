package com.framewidget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdx.framework.adapter.MAdapter;

public class AdaTest extends MAdapter<String> {

	public AdaTest(Context context, List<String> datas) {
		super(context, getData());
	}

	private static List<String> getData() {
		List<String> da = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			da.add("");
		}
		return da;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.test5, null);
		}
		return convertView;
	}
}
