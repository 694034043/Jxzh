package com.bocop.zyt.bocop.jxplatform.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.bean.app.AppInfo;

import java.util.ArrayList;


/**
 * @author luoyang
 * 
 *         描述：首页gridview适配器
 */
public class HomeImageAdapter extends BaseAdapter {

	// 上下文
	private Context mContext;
	private String flag = "";

	private int[] imageIcons;
	private String[] imageBigText;
	private String[] imageSmallText;
	// 填充器
	private LayoutInflater mInflater;

	private ArrayList<AppInfo> mData = new ArrayList<AppInfo>();

	public HomeImageAdapter(Context context) {
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		buildData();
	}

	public HomeImageAdapter(Context context, int[] imageIcons,String[] imageBigText,String[] imageSmallText) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.imageIcons = imageIcons;
		this.imageBigText = imageBigText;
		this.imageSmallText = imageSmallText;
		buildData();
	}

	private void buildData() {
			for (int i = 0; i < imageIcons.length; i++) {
				AppInfo info = new AppInfo();
				info.iconId = imageIcons[i];
//				info.name = imageBigText[i];
//				info.otherName = imageSmallText[i];
				mData.add(info);
				Log.i("tag", flag);
			}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData != null ? mData.size() : 0;
	}

	@Override
	public AppInfo getItem(int position) {
		return mData != null ? mData.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_home_image, null);
			holder.appIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.appName = (TextView) convertView.findViewById(R.id.tvbig);
			holder.otherName = (TextView) convertView.findViewById(R.id.tvsmall);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(position < 3){
			holder.appName.setTextColor(Color.rgb(0, 0, 0)); 
			holder.otherName.setTextColor(Color.rgb(0, 0, 0)); 
		}

		AppInfo info = (AppInfo) getItem(position);
		holder.appIcon.setImageResource(info.iconId);
		holder.appName.setText(info.name);
		holder.otherName.setText(info.otherName);

		return convertView;
	}

	class ViewHolder {
		ImageView appIcon;
		TextView appName;
		TextView otherName;
	}
}
