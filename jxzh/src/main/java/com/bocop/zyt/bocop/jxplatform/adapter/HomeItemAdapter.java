package com.bocop.zyt.bocop.jxplatform.adapter;

import android.content.Context;
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
 * @author wangzhongcai
 * 
 *         描述：首页gridview适配器
 */
public class HomeItemAdapter extends BaseAdapter {

	// 上下文
	private Context mContext;
	private String flag = "";

	// app图标资源id
	private static int[] sAppIcons = new int[] { R.drawable.icon_cyh,
			R.drawable.icon_secretary, R.drawable.icon_yslc,
			R.drawable.icon_bmjf, R.drawable.icon_jket, R.drawable.icon_htzq,
			R.drawable.icon_boced, R.drawable.icon_qzt, R.drawable.icon_cft,
			R.drawable.icon_card, R.drawable.icon_weihui, R.drawable.icon_edai,
			R.drawable.icon_jiehui, R.drawable.icon_gouhui,
			R.drawable.icon_xdt, R.drawable.icon_ypt };
	// R.drawable.icon_bmjf,
	// R.drawable.icon_jket,R.drawable.icon_htzq,R.drawable.icon_boced,R.drawable.icon_qzt,R.drawable.icon_cft,R.drawable.icon_card,R.drawable.icon_weihui,R.drawable.icon_edai,R.drawable.icon_jiehui,R.drawable.icon_gouhui,R.drawable.icon_exy};

//	private static int[] sLifeIcons = new int[] { 
//			R.drawable.nhy,R.drawable.nms, R.drawable.nch,R.drawable.nny,
//			R.drawable.njf,R.drawable.nqz,R.drawable.njk,R.drawable.nxy,R.drawable.ncx,R.drawable.npw,R.drawable.npw};
	private static int[] sLifeIcons = new int[] { 
			R.drawable.ic_rail_x_01,
			R.drawable.ic_rail_x_02,
			R.drawable.ic_rail_x_03,
			R.drawable.ic_rail_x_04,
			};
	
	private static int[] sFinanceIcons = new int[] {
		R.drawable.gjxp_icon_kht_gr,R.drawable.gjxp_icon_bkt,R.drawable.gjxp_icon_gdt, R.drawable.gjxp_icon_lct,R.drawable.gjxp_icon_ght,
		R.drawable.gjxp_icon_sht,R.drawable.gjxp_icon_jft,
		R.drawable.gjxp_icon_cft};
	
	private static int[] sFacilityIcons = new int[] {
		R.drawable.gjxp_icon_kht_qy,R.drawable.gjxp_icon_qdt,
		R.drawable.gjxp_icon_qft,R.drawable.gjxp_icon_dzt, R.drawable.gjxp_icon_bht,R.drawable.gjxp_icon_bgt
		,R.drawable.gjxp_icon_est,R.drawable.gjxp_icon_cst};
	
	private static int[] sShoppimgIcons = new int[] { R.drawable.icon_main_ypt,R.drawable.icon_main_kht,
			R.drawable.icon_main_wgt,R.drawable.blank };
	// app名称集合
	
  //景德镇滴
	private static int[] jdzimgIcons = new int[] { R.drawable.icon_main_ypt,R.drawable.icon_main_kht,
			R.drawable.icon_main_wgt,R.drawable.blank };
	
//	private static int[] imgIcons110 = new int[] { R.drawable.nkh,R.drawable.nbk,
//			R.drawable.nlc,R.drawable.ncf,R.drawable.nsh ,R.drawable.ngh};
	
	//财富增值服务
	private static int[] imgIcons110 = new int[] { 
			R.drawable.hytt,
			R.drawable.ic_accout_x_1,
			R.drawable.ic_account_x_2,
			R.drawable.ic_account_x_3
			};
	
	
	private static int[] imgIcons120 = new int[] { 
			
//			R.drawable.hytt,
			R.drawable.ic_life_x_1,
			R.drawable.ic_life_x_2,
			R.drawable.ic_life_x_3,
			R.drawable.ic_life_x_4,
			
			};
//	private static int[] imgIcons120 = new int[] { R.drawable.ngj,R.drawable.ngc,  R.drawable.nzx,R.drawable.nxf
//	};
//	private static int[] imgIcons130 = new int[] { R.drawable.nyp,R.drawable.nwg,
//			};
	private static int[] imgIcons130 = new int[] { 
			R.drawable.ic_shop_x_1,
			R.drawable.ic_shop_x_2,
			R.drawable.ic_shop_x_3,
			
	};
	private  String[] sAppNames;

	// 填充器
	private LayoutInflater mInflater;

	private ArrayList<AppInfo> mData = new ArrayList<AppInfo>();

	public HomeItemAdapter(Context context) {
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		sAppNames = mContext.getResources().getStringArray(
				R.array.appname_array);
		buildData();
	}

	public HomeItemAdapter(Context context, int appNamesID, String flag) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.sAppNames = mContext.getResources().getStringArray(appNamesID);
		this.flag = flag;
		buildData();
	}

	private void buildData() {
		if ("0".equals(flag)) {
			for (int i = 0; i < sAppNames.length; i++) {
				AppInfo info = new AppInfo();
				info.iconId = sLifeIcons[i];
				info.name = sAppNames[i];
				mData.add(info);
				Log.i("tag", flag);
			}
		} else if ("1".equals(flag)) {
			for (int i = 0; i < sAppNames.length; i++) {
				AppInfo info = new AppInfo();
				info.iconId = sFinanceIcons[i];
				info.name = sAppNames[i];
				mData.add(info);
				Log.i("tag", flag);
			}

		} else if ("2".equals(flag)) {
			for (int i = 0; i < sAppNames.length; i++) {
				AppInfo info = new AppInfo();
				info.iconId = sShoppimgIcons[i];
				info.name = sAppNames[i];
				mData.add(info);
				Log.i("tag", flag);
			}
		} else if ("3".equals(flag)) {
			for (int i = 0; i < sAppNames.length; i++) {
				AppInfo info = new AppInfo();
				info.iconId = sFacilityIcons[i];
				info.name = sAppNames[i];
				mData.add(info);
				Log.i("tag", flag);
			}
		}
		
		else if ("110".equals(flag)) {
			for (int i = 0; i < sAppNames.length; i++) {
				AppInfo info = new AppInfo();
				info.iconId = imgIcons110[i];
				info.name = sAppNames[i];
				mData.add(info);
				Log.i("tag", flag);
			}
		}
		
		else if ("120".equals(flag)) {
			for (int i = 0; i < sAppNames.length; i++) {
				AppInfo info = new AppInfo();
				info.iconId = imgIcons120[i];
				info.name = sAppNames[i];
				mData.add(info);
				Log.i("tag", flag);
			}
		}
		else if ("130".equals(flag)) {
			for (int i = 0; i < sAppNames.length; i++) {
				AppInfo info = new AppInfo();
				info.iconId = imgIcons130[i];
				info.name = sAppNames[i];
				mData.add(info);
				Log.i("tag", flag);
			}
		}
		else {
			for (int i = 0; i < sAppNames.length; i++) {
				AppInfo info = new AppInfo();
				info.iconId = sAppIcons[i];
				info.name = sAppNames[i];
				mData.add(info);
			}
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
			convertView = mInflater.inflate(R.layout.item_home, null);
			holder.appIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.appName = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AppInfo info = (AppInfo) getItem(position);
		holder.appIcon.setImageResource(info.iconId);
		holder.appName.setText(info.name);

		return convertView;
	}

	class ViewHolder {
		ImageView appIcon;
		TextView appName;
	}
}
