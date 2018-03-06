package com.bocop.zyt.bocop.zyt.widget.core;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bocop.zyt.R;
import com.bocop.zyt.fmodule.utils.ScreenUtil;


/**
 * Created by vincentpaing on 6/7/16.
 */
public class OverlapFragment extends Fragment {

	int resourceId;
	private int[] ss;
	static final String ARG_RES_ID = "ARG_RES_ID";

	public static OverlapFragment newInstance(int resourceId) {
		OverlapFragment overlapFragment = new OverlapFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_RES_ID, resourceId);
		overlapFragment.setArguments(bundle);
		return overlapFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resourceId = getArguments().getInt(ARG_RES_ID);
		ss = ScreenUtil.get_screen_size(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.item_overlap_cover, container, false);
		ImageView coverImageView = (ImageView) rootView.findViewById(R.id.image_cover);
		int w=(int) (ss[0]*1.0/2);
		int h=(int) (w*47.0/30);
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w, h);
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		coverImageView.setLayoutParams(p);
		coverImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), resourceId));
		return rootView;
	}
}
