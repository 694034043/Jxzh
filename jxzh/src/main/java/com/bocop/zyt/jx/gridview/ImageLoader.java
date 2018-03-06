/**
 * 
 */
package com.bocop.zyt.jx.gridview;
/** 
 * @author luoyang  E-mail: luoyang8714@163.com
 * @version 创建时间：2017-5-19 上午10:30:18 
 * 类说明 
 */
/**
 * @author zhongye
 *
 */
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.LruCache;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.bocop.zyt.R;

/**
 * Created by 袁磊 on 2017/3/2.
 */
public class ImageLoader {

    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    private LruCache<String, Bitmap> mCaches;
    private GridView mGridView;
    /**
     * set集合中的数据没有顺序，且如果add两个一样的对象或基本类型的数据，set集合里也是只有一个，即set集合中的数据都是独一无二的
     * list中的数据是有顺序的，可以加入多个一样的对象和基本类型的数据
     */
    private Set<NewsAsyncTask> mTask;//用Set集合管理AsyncTask

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@SuppressLint("NewApi")
	public ImageLoader(GridView gridView) {
        mGridView = gridView;
        mTask = new HashSet<>();
        //获取最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;//缓存大小是最大可用内存的1/4
        mCaches = new LruCache<String, Bitmap>(cacheSize) {
            /**
             * Sizeof方法的作用只要是定义缓存中每项的大小，
             * 当我们缓存进去一个数据后，
             * 当前已缓存的Size就会根据这个方法将当前加进来的数据也加上，
             * 便于统计当前使用了多少内存，如果已使用的大小超过maxSize就会进行清除动作
             */
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
    }

    /**
     * 添加到缓存
     *
     * @param url    添加到缓存的Key值
     * @param bitmap 添加到缓存的图片
     */
    @SuppressLint("NewApi")
	private void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {
            mCaches.put(url, bitmap);
        }
    }

    /**
     * 从缓存中获取数据
     *
     * @param url 通过添加到缓存的key值获取
     * @return
     */
    @SuppressLint("NewApi")
	private Bitmap getBitmapFromCache(String url) {
        return mCaches.get(url);
    }


    private Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            //创建Url
            URL url = new URL(urlString);
            //创建HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //获取InputStream对象，用BufferedInputStream包装
            is = new BufferedInputStream(connection.getInputStream());
            //将BufferedInputStream解析为Bitmap
            bitmap = BitmapFactory.decodeStream(is);
            //释放资源
            connection.disconnect();
            //返回Bitmap对象
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //释放资源
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 取消所有运行的任务
     */
    public void cancelAllTasks() {
        if (mTask != null) {
            for (NewsAsyncTask task : mTask) {
                task.cancel(false);
            }
        }
    }

    /**
     * 加载当前可见的所有图片
     *
     * @param start 当前可见的第一条
     * @param end   当前可见的最后一条的下一条
     */
    public void loadImages(int start, int end) {
        for (int i = start; i < end; i++) {
            //循环获取到从start开始到end的所有图片的url
            String url = UrlGridviewAdapter.URLS[i];
            //从缓存中取出对应的图片
            Bitmap bitmap = getBitmapFromCache(url);
            //如果缓存中没有，那么必须去下载
            if (bitmap == null) {
                NewsAsyncTask task = new NewsAsyncTask(url);
                task.execute(url);
                mTask.add(task);
            } else {
                ImageView imageView = (ImageView) mGridView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * 使用AsyncTask的方式加载图片
     *
     * @param url       图片地址
     * @param imageView 显示图片的ImageView
     */
    public void showImageByAsyncTask(String url, ImageView imageView) {
        //从缓存中取出图片
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap == null) {//缓存中没有，设置默认图片
            imageView.setImageResource(R.drawable.icon_qzt_jiazai);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 异步加载的线程
     */
    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {
        //private ImageView mImageView;
        private String mUrl;

        public NewsAsyncTask(String url) {
            //this.mImageView = imageView;
            this.mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //每次下载成功后都将bitmap添加到LruCache缓存
            String url = params[0];//可变参数数组的第一个就是传入的url
            Bitmap bitmap = getBitmapFromUrl(url);//下载
            if (bitmap != null) {//如果下载成功
                addBitmapToCache(url, bitmap);//就加入缓存
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //请求完毕后设置ImageView
            ImageView imageView = (ImageView) mGridView.findViewWithTag(mUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            mTask.remove(this);
        }
    }
}
