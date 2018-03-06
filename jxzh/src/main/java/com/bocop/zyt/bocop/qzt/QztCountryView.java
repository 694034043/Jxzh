/**
 * 
 */
package com.bocop.zyt.bocop.qzt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.util.FormsUtil;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


/** 
 * @author luoyang  E-mail: luoyang8714@163.com
 * @version 创建时间：2017-5-15 下午5:41:57 
 * 类说明 
 */
/**
 * @author zhongye
 *
 */

@ContentView(R.layout.qzt_activity_countryview)
public class QztCountryView extends BaseActivity {
	
	String countryId = "1";
	String[] strTv;
	
	@ViewInject(R.id.tv_titleName)
	private TextView  tv_titleName;
	
	@ViewInject(R.id.countryName)
	private TextView  tv_name;
	@ViewInject(R.id.qzt_tv1)
	private TextView  tv1;
	@ViewInject(R.id.qzt_tv2)
	private TextView  tv2;
	@ViewInject(R.id.tvImage)
	private TextView  tvImage;
	
	@ViewInject(R.id.ivCounrty)
	private ImageView  imCounrty;
	@ViewInject(R.id.rlt)
	private RelativeLayout  rlt;
	DisplayImageOptions options;
	
	
	/* (non-Javadoc)
	 * @see com.boc.jx.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tv_titleName.setText("国家详情");
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.icon_android)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.resetViewBeforeLoading(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
		init();
		
		LayoutParams lp = rlt.getLayoutParams();
		lp.height = (int) (FormsUtil.SCREEN_WIDTH/2.6);
		lp.width = FormsUtil.SCREEN_WIDTH;
		rlt.setLayoutParams(lp);
		
		strTv = QztCounrtyViewConstant.getQztCountryTv(Integer.parseInt(countryId));
		//Log.i("tag", strTv[0]);
		//Log.i("tag", strTv[1]);
		//Log.i("tag", strTv[2]);
		//Log.i("tag", strTv[3]);
		
		imageLoader.displayImage(strTv[0], imCounrty, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
//				spinner.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
					case IO_ERROR:
						message = "Input/Output error";
						break;
					case DECODING_ERROR:
						message = "Image can't be decoded";
						break;
					case NETWORK_DENIED:
						message = "Downloads are denied";
						break;
					case OUT_OF_MEMORY:
						message = "Out Of Memory error";
						break;
					case UNKNOWN:
						message = "Unknown error";
						break;
				}
//				Toast.makeText(ImagePagerActivity.this, message, Toast.LENGTH_SHORT).show();

//				spinner.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				spinner.setVisibility(View.GONE);
			}
		});
		
		
		 //用普通方法加载图片
//        new NormalLoadPictrue().getPicture(strTv[0],imCounrty);
        
        //Log.i("tag", "NormalLoadPictrue");
		
		tv_name.setText(strTv[1]);
		tv1.setText(strTv[2]);
		tv2.setText(strTv[3]);
	}
	
	@OnClick(R.id.bt_qztapply)
	public void onClick(View v){
			Bundle bundle = new Bundle();
			bundle.putString("id", countryId);
			Intent intent = new Intent(QztCountryView.this,
					QztApplyActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
	}
	private void init(){
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if(bundle != null){
			countryId = bundle.getString("id");
			//Log.i("tag", bundle.getString("id"));
		}
	}
	
	
	public class NormalLoadPictrue {
	     
	    private String uri;
	    private ImageView imageView;
	    private byte[] picByte;
	     
	     
	    public void getPicture(String uri,ImageView imageView){
	        this.uri = uri;
	        this.imageView = imageView;
	        new Thread(runnable).start();
	    }
	     
	    @SuppressLint("HandlerLeak")
	    Handler handle = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            if (msg.what == 1) {
	                if (picByte != null) {
	                	tvImage.setVisibility(View.GONE);
	                	imCounrty.setVisibility(View.VISIBLE);
	                    Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
	                    imageView.setImageBitmap(bitmap);
	                }
	            }
	        }
	    };
	 
	    Runnable runnable = new Runnable() {
	        @Override
	        public void run() {
	            try {
	                URL url = new URL(uri);
	                //Log.i("tag", "url:" + url);
	                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	                conn.setRequestMethod("GET");
	                conn.setReadTimeout(20000);
	                //Log.i("tag", "setRequestMethod:");
	                if (conn.getResponseCode() == 200) {
	                    InputStream fis =  conn.getInputStream();
	                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                    byte[] bytes = new byte[1024];
	                    int length = -1;
	                    while ((length = fis.read(bytes)) != -1) {
	                        bos.write(bytes, 0, length);
	                    }
	                    picByte = bos.toByteArray();
	                    bos.close();
	                    fis.close();
	                     
	                    Message message = new Message();
	                    message.what = 1;
	                    handle.sendMessage(message);
	                }
	                 
	                 
	            }catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    };
	     
	}
}
