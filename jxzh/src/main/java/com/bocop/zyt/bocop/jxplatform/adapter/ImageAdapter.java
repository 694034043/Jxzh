package com.bocop.zyt.bocop.jxplatform.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.bean.Advertisement;
import com.bocop.zyt.jx.base.BaseActivity;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

	private List<Advertisement> adImageList;
	private BaseActivity activity;
	
	public ImageAdapter(List<Advertisement> adImageList, BaseActivity activity) {
		this.adImageList = adImageList;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return adImageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return adImageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.layout_advertisement_item, null);
            viewHolder.ivAdImage = (ImageView)convertView.findViewById(R.id.ivAdImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Advertisement adImage = adImageList.get(position);
        if(adImage != null){
        	viewHolder.ivAdImage.setImageResource(adImage.getImageRes());
        }
        return convertView;
	}
	
	 static class ViewHolder {
	        ImageView ivAdImage;
	    }

}
