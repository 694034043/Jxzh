package com.bocop.zyt.bocop.gjxq.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.gjxq.activity.Details4Activity;
import com.bocop.zyt.bocop.gjxq.activity.ResourceUtil;


public class DetailsAdapter extends BaseAdapter {

	private Details4Activity activity;
	private String[] iconArray;
	private String[] textArray;

	public DetailsAdapter(Details4Activity activity, String[] iconArray, String[] textArray)  {
		this.activity = activity;
		this.iconArray = iconArray;
		this.textArray = textArray;
	}

	@Override
	public int getCount() {
		return iconArray.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(R.layout.details_item_details, null);
			viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		int drawableId = ResourceUtil.getDrawableId(activity, iconArray[position]);
		if(drawableId != 0) {
			viewHolder.ivIcon.setImageResource(drawableId);
		}
		if (textArray[position] != null) {
			viewHolder.tvContent.setText(textArray[position]);
		}
		return convertView;
	}

	static class ViewHolder {
		ImageView ivIcon;
		TextView tvContent;
	}

}
