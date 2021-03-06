package com.bocop.zyt.jx.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.bocop.gopushlibrary.service.GoPushManage;
import com.bocop.zyt.F;
import com.bocop.zyt.bocop.jxplatform.activity.way.pattern.ScreenObserver;
import com.bocop.zyt.bocop.jxplatform.activity.way.pattern.UnlockGesturePasswordActivity;
import com.bocop.zyt.bocop.jxplatform.activity.way.view.LockPatternUtils;
import com.bocop.zyt.bocop.jxplatform.config.BocSdkConfig;
import com.bocop.zyt.bocop.jxplatform.util.FormsUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.xms.xml.message.MessageBean;
import com.bocop.zyt.bocop.zyt.view.ActivityLifecycleHelper;
import com.bocop.zyt.jx.baseUtil.cache.CacheBean;
import com.bocop.zyt.jx.constants.Constants;
import com.bocop.zyt.jx.httpUnits.AndroidCommFactory;
import com.bocop.zyt.jx.httpUnits.ConnCommMachine;
import com.bocop.zyt.jx.httpUnits.NetworkUtils;
import com.bocop.zyt.jx.tools.FileUtils;
import com.mdx.framework.Frame;
import com.mdx.framework.MApplication;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by hwt on 14-9-11.
 * <p/>
 * ??????Bean ,????????????????????????????????????????????????activity???????????????
 */

@SuppressLint("NewApi")
public class BaseApplication extends MultiDexApplication {
	private CacheBean cacheBean;// ??????bean
	private ActivityManager activityManager;// activity????????????
	private Map<Integer, BaseFragment> fragmentList;
	private UserInfo userInfo;
	private static BaseApplication baseApplication;
	private ImageLoader imageLoader;

	public HashMap<String, ConnCommMachine> hs;// ??????url?????????machine???hashmap
	private AndroidCommFactory factory;// ??????????????????
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();
	private boolean netStat = false;// ??????????????????
	private Handler handler;
	private List<MessageBean> msgData;

	private boolean isDownload;
	public static String deviceId;
	public static GoPushManage goPushManage = null;
	public BMapManager mBMapManager = null;
	public static final String strKey = "8BB7F0E5C9C77BD6B9B655DB928B74B6E2D838FD";
	public boolean m_bKeyRight = true;
	public boolean isShowShortTimeLogin = false;
	
	public static String hdtUserId;
	public static String hdtAccessToken;
	public static String hdtRefreshToken;
	
	public LockPatternUtils mLockPatternUtils;
    String mainType="-1";
	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	//added by gengjunying 2016-04-01
	public int count = 0;
	public boolean isfront = true;

	private ScreenObserver mScreenObserver;
	
	// ????????????????????????
	private BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			setNetStat(NetworkUtils.isNetworkConnected(context));// ????????????????????????
		}
	};
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate() {

		Frame.init(getApplicationContext());
		F.init();
		super.onCreate();
		//added by gengjunying 2016-04-01
		mLockPatternUtils = new LockPatternUtils(this);
		
		
		/* ??????????????? */
		baseApplication = this;
		cacheBean = CacheBean.getInstance();
		activityManager = new ActivityManager();
		fragmentList = new HashMap<Integer, BaseFragment>();
		FormsUtil.getDisplayMetrics(this);
		isDownload = false;
		// ????????????
		factory = new AndroidCommFactory();
		setNetStat(NetworkUtils.isNetworkConnected(this));// ????????????????????????

		// ????????????????????????????????????
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(connectionReceiver, intentFilter);

		// SharedPreferences spf = getSharedPreferences(
		// OnlineService.SECRETARY_MSG, Context.MODE_PRIVATE);
		// deviceId = spf.getString(BocSdkConfig.DEVICE_ID_KEY, "");
		// if (TextUtils.isEmpty(deviceId)) {
		// Editor editor = spf.edit();
		// editor.remove(BocSdkConfig.UUID_KEY);
		// editor.remove(BocSdkConfig.REGIST_DEVICE_KEY);
		// deviceId = new DeviceInfo(this).getDeviceId();
		// editor.putString(BocSdkConfig.DEVICE_ID_KEY, deviceId);
		// editor.commit();
		// }
		// LogUtils.d("deviceId :" + deviceId);

		imageLoader = initImageLoader(getApplicationContext());

//		// ???????????????
		goPushManage = GoPushManage.getInstance(this.getApplicationContext());
		goPushManage.setDebugMode(true);
		try{
			initEngineManager(this);
		}
		catch(Exception ex){
			Log.i("tag", ex.getMessage());
		}
		
		registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
		
		
		initScreenObserver();
		
		//-------------------------gengjunying
		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

			@Override
			public void onActivityCreated(Activity arg0, Bundle arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivityDestroyed(Activity activity) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivityPaused(Activity activity) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivityResumed(Activity activity) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivityStarted(Activity activity) {
				// TODO Auto-generated method stub
				if (!isApplicationBroughtToBackground(baseApplication.getBaseContext())) {
					if (!Constants.handFlg) {
						if (LoginUtil.isLog(baseApplication.getBaseContext())) {
							if (mLockPatternUtils.savedPatternExists(LoginUtil.getUserId(baseApplication.getBaseContext()))) {
								// ??????????????????
								Intent intent = new Intent();
								intent.putExtra("wayid", "wayid");
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.setClass(baseApplication.getBaseContext(),UnlockGesturePasswordActivity.class);
							
								startActivity(intent);							
							}
						}
					}
				}
			}

			@Override
			public void onActivityStopped(Activity activity) {
				// TODO Auto-generated method stub
				isApplicationBroughtToBackground(baseApplication.getBaseContext());
			}
		});
	}
	
	// ==============================??????app???????????????????????????========================================================================
	/**
	 * ????????????????????????????????????????????????
	 */
	public static boolean isApplicationBroughtToBackground(final Context context) {
		android.app.ActivityManager am = (android.app.ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				Constants.handFlg = false;
				return true;
			}
		}
		return false;

	}

	// ===========================??????????????????????????????????????????????????????========start=================================================================
	private void initScreenObserver() {
		mScreenObserver = new ScreenObserver(baseApplication.getBaseContext());
		mScreenObserver
				.observerScreenStateUpdate(new ScreenObserver.observerScreenStateUpdateListener() {
					@Override
					public void onScreenOn() {						
						System.out.println(" ???????????????,?????????????????? ");
						if(!isApplicationBroughtToBackground(baseApplication.getBaseContext())){
							if (LoginUtil.isLog(baseApplication.getBaseContext())) {
								if (mLockPatternUtils.savedPatternExists(LoginUtil.getUserId(baseApplication.getBaseContext()))) {			
									// ??????????????????
									Intent intent = new Intent();
									intent.putExtra("wayid", "wayid");
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									intent.setClass(
											baseApplication.getBaseContext(),
											UnlockGesturePasswordActivity.class);								
									startActivity(intent);		
									//Toast.makeText(baseApplication.getBaseContext(), "???????????????", 0).show();
								}
							}
						}
					}

					@Override
					public void onScreenOff() {
						System.out.println(" ???????????????,?????????????????? ");
					}
				});
	}

	// ==========================================================end=============================================================

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}
		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
			Log.i("tag", "BMapManager  ???????????????!");
			Toast.makeText(
					BaseApplication.getInstance().getApplicationContext(),
					"BMapManager  ???????????????!", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param userId
	 */
	public static void startGoPush(String userId) {
		if (goPushManage != null) {
			goPushManage.startPushService(userId);
		}
	}

	// ??????????????????????????????????????????????????????????????????????????????
	public static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(
						BaseApplication.getInstance().getApplicationContext(),
						"????????????????????????", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(
						BaseApplication.getInstance().getApplicationContext(),
						"??????????????????????????????", Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// ??????Key?????????
				Toast.makeText(
						BaseApplication.getInstance().getApplicationContext(),
						"????????????????????????Key???" + iError, Toast.LENGTH_LONG).show();
				BaseApplication.getInstance().m_bKeyRight = false;
			}
		}
	}

	/**
	 * ??????????????????
	 */
	public static void stopGoPush() {
		if (goPushManage != null) {
			goPushManage.stopPushService();
		}
	}

	// ???????????????????????????
	public ConnCommMachine getCommMachine() {
		if (factory != null) {
			return factory.CreateNewCommMachine(BocSdkConfig.COMM_TYPE);
		} else {
			factory = new AndroidCommFactory();
			return factory.CreateNewCommMachine(BocSdkConfig.COMM_TYPE);
		}
	}

	public void putUrl(String url, ConnCommMachine machine) {
		w.lock();
		try {
			hs.put(url, machine);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			w.unlock();
		}
	}

	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	// ??????url????????????machine
	public ConnCommMachine getMachine(String url) {
		r.lock();
		ConnCommMachine machine = null;
		try {
			machine = hs.get(url);
			return machine;
		} catch (Exception e) {
			e.printStackTrace();
			return machine;
		} finally {
			r.unlock();
		}

	}

	public void clearAll() {
		w.lock();
		try {
			hs.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			w.unlock();
		}
	}

	public void clearPoint(String url) {
		w.lock();
		try {
			hs.remove(url);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			w.unlock();
		}
	}

	public boolean isNetStat() {
		return netStat;
	}

	public void setNetStat(boolean netStat) {
		this.netStat = netStat;
	}

	private List<Map<String, String>> listmap = new CopyOnWriteArrayList<Map<String, String>>();

	public List<Map<String, String>> getListmap() {
		return listmap;
	}

	// ?????????????????? ???20150703LUOYANG??????

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public static BaseApplication getInstance() {
		return baseApplication;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * ??????????????????
	 * 
	 * @return CacheBean
	 */
	public CacheBean getCacheBean() {
		return cacheBean;
	}

	/**
	 * ??????activity????????????
	 * 
	 * @return activity????????????
	 */
	public ActivityManager getActivityManager() {
		return activityManager;
	}

	/**
	 * ???????????? ????????????
	 */
	public void exit() {
		exit(null);
	}

	/**
	 * ????????????
	 * 
	 * @param exitAppListener
	 *            ????????????????????????????????????????????????????????????????????????
	 */
	public void exit(OnExitAppListener exitAppListener) {
		// ??????????????????
		if (exitAppListener != null)
			exitAppListener.onExit();
		// ?????????????????????
		// FileUtils.deleteFilesByDirectory(getCacheDir());
		// FileUtils.deleteFilesByDirectory(getFilesDir());
		// FileUtils.deleteFilesByDirectory(getExternalCacheDir());
		// ??????????????????
		// imageLoader.clearDiskCache();
		// ??????????????????????????????????????????activity
		cacheBean.clearCache();
		fragmentList.clear();
		activityManager.finishAll();
		if (msgData != null) {
			msgData.clear();
		}
		// ????????????????????????
		android.os.Process.killProcess(android.os.Process.myPid());
		android.app.ActivityManager activityMgr = (android.app.ActivityManager) getSystemService(ACTIVITY_SERVICE);
		activityMgr.restartPackage(getPackageName());
		System.exit(0);
		System.gc();
	}

	/**
	 * ?????????????????????
	 */
	public interface OnExitAppListener {
		public void onExit();
	}

	public Map<Integer, BaseFragment> getFragmentList() {
		return fragmentList;
	}

	public void setFragmentList(Map<Integer, BaseFragment> fragmentList) {
		this.fragmentList = fragmentList;
	}

	/**
	 * ?????????ImageLoader??????
	 * 
	 * @param context
	 *            ???????????????
	 */
	public ImageLoader initImageLoader(Context context) {
		ImageLoaderConfiguration config = null;
		File cacheFile = FileUtils
				.getStorageDerectory(BaseConfig.IMAGE_CACHE_PATH);
		if (cacheFile == null) {
			config = new ImageLoaderConfiguration.Builder(context)
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.denyCacheImageMultipleSizesInMemory()
					.diskCacheFileNameGenerator(new Md5FileNameGenerator())
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					// .writeDebugLogs() // Remove for release app
					.build();
		} else {
			config = new ImageLoaderConfiguration.Builder(context)
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.denyCacheImageMultipleSizesInMemory()
					.diskCacheFileNameGenerator(new Md5FileNameGenerator())
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					// .writeDebugLogs()// Remove for release app
					.diskCache(new LimitedAgeDiscCache(cacheFile, 30 * 60))
					.diskCacheFileCount(100).build();
		}
		ImageLoader loader = ImageLoader.getInstance();
		loader.init(config);
		return loader;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}
	
	public LockPatternUtils getLockPatternUtils() {
        return mLockPatternUtils;
    }
	
	public List<MessageBean> getMsgData() {
		return msgData;
	}
	
	public void setMsgData(List<MessageBean> msgData) {
		this.msgData = msgData;
	}
}
