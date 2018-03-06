/**
 * 
 */
package com.bocop.zyt.jx.gridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/** 
 * @author luoyang  E-mail: luoyang8714@163.com
 * @version 创建时间：2017-5-19 上午10:28:34 
 * 类说明 
 */
/**
 * @author zhongye
 *
 */
public class UrlGridviewAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

	 private List<InfoBean> mList;
	    private LayoutInflater mInflater;

//	    private ImageLoader mImageLoader;
	    private int mStart, mEnd;
	    public static String[] URLS;
	    private boolean mFirstIn;//是否是第一次进入
	    
	    
	    DisplayImageOptions options;

	    public UrlGridviewAdapter(Context context, List<InfoBean> list, GridView gridView) {
	        this.mList = list;
	        this.mInflater = LayoutInflater.from(context);
//	        mImageLoader = new ImageLoader(gridView);
	        URLS = new String[list.size()];
	        
	        options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
	        
	        for (int i = 0; i < list.size(); i++) {
	            URLS[i] = list.get(i).iconUrl;//通过循环将所有图片Url转入到静态数组
	        }
	        mFirstIn = true;
	        //注册对应的监听事件
	        gridView.setOnScrollListener(this);
	    }

	    @Override
	    public int getCount() {
	        return mList == null ? 0 : mList.size();
	    }

	    @Override
	    public Object getItem(int position) {
	        return mList.get(position);
	    }

	    @Override
	    public long getItemId(int position) {
	        return position;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        final ViewHolder holder;
	        InfoBean bean = (InfoBean) getItem(position);
	        if (convertView == null) {
	            holder = new ViewHolder();
	            convertView = mInflater.inflate(R.layout.layout_country_school, null);
	            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_layut_school);
	            holder.textView = (TextView) convertView.findViewById(R.id.tv_country_name);
	            holder.textView.setText(bean.getCountryName());
	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	        }
	       
	        String url = bean.iconUrl;
	        
	        convertView.setTag(holder);
	        /**
	         * setTag的时候，tag的内容不能相同，否则只会解析出第一个Tag标签！！！
	         * 当解析第二个的时候就会返回空，所以要保证tag内容的唯一性。切记。
	         */
//	        holder.ivIcon.setTag(url);//用图片Url地址设置Tag
//	        mImageLoader.showImageByAsyncTask(url, holder.ivIcon);
	        
	        BaseActivity.imageLoader.displayImage(URLS[position], holder.imageView, options, new ImageLoadingListener() {
				 @Override
				 public void onLoadingStarted(String imageUri, View view) {
//					 holder.progressBar.setProgress(0);
//					 holder.progressBar.setVisibility(View.VISIBLE);
				 }

				 @Override
				 public void onLoadingFailed(String imageUri, View view,
						 FailReason failReason) {
//					 holder.progressBar.setVisibility(View.GONE);
				 }

				 @Override
				 public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//					 holder.progressBar.setVisibility(View.GONE);
				 }

				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					// TODO Auto-generated method stub
					
				}
			 }
);
	        
	        return convertView;
	    }

	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {
	        //滚动状态切换时候调用
	        if (scrollState == SCROLL_STATE_IDLE) {//如果没有滚动时
	            //加载可见项
//	            mImageLoader.loadImages(mStart, mEnd);
	        } else {
	            //停止任务
//	            mImageLoader.cancelAllTasks();
	        }
	    }

	    @Override
	    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	        //整个滚动过程中都会调用
	        mStart = firstVisibleItem;
	        mEnd = firstVisibleItem + visibleItemCount;
	        //第一次进入显示的时候调用
	        if (mFirstIn && visibleItemCount > 0) {
//	            mImageLoader.loadImages(mStart, mEnd);
	            mFirstIn = false;
	        }
	    }

	    static class ViewHolder {
	        ImageView imageView;
	        TextView textView;
//			ProgressBar progressBar;
	    }

}
