package com.bocop.zyt.bocop.xms.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.jxplatform.config.TransactionValue;
import com.bocop.zyt.bocop.jxplatform.util.CspUtil;
import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.jxplatform.util.Mcis;
import com.bocop.zyt.bocop.jxplatform.view.BackButton;
import com.bocop.zyt.bocop.xms.adapter.DateAdapter;
import com.bocop.zyt.bocop.xms.service.AlarmService;
import com.bocop.zyt.bocop.xms.service.AlarmServiceManager;
import com.bocop.zyt.bocop.xms.utils.SharedPreferencesUtils;
import com.bocop.zyt.bocop.xms.xml.CspXmlXmsCom;
import com.bocop.zyt.bocop.xms.xml.remind.EventBean;
import com.bocop.zyt.bocop.xms.xml.remind.EventComResp;
import com.bocop.zyt.bocop.xms.xml.remind.EventComXmlBean;
import com.bocop.zyt.bocop.xms.xml.remind.EventListResp;
import com.bocop.zyt.bocop.xms.xml.remind.EventListXmlBean;
import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.baseUtil.view.annotation.ContentView;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;
import com.bocop.zyt.jx.baseUtil.view.annotation.event.OnClick;
import com.bocop.zyt.jx.tools.CalendarUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  ???????????????
 * 
 * @author ftl
 * 
 */
@ContentView(R.layout.xms_activity_custom_remind)
public class CustomRemindActivity extends BaseActivity {
	
	@ViewInject(R.id.iv_imageLeft)
	private BackButton ivImageLeft;
	@ViewInject(R.id.ivBack)
	private ImageView ivBack;
	@ViewInject(R.id.tv_titleName)
	private TextView tvTitle;
	@ViewInject(R.id.llRemind)
	private LinearLayout llRemind;
	@ViewInject(R.id.tvRemind)
	private TextView tvRemind;//????????????
	@ViewInject(R.id.lvContent)
	private ListView lvContent;
	
	private DateAdapter dateAdapter;
	private int selectPostion = 0;
	private String dayNumbers[] = new String[7];
	private List<EventBean> itemList;// ??????
	private SlideAdapter adapter;
	private int currentPosition;
	private String showDate;//???????????????
	private Map<Integer, Boolean> isSelected=new HashMap<Integer, Boolean>();
 	private List beSelectedData = new ArrayList();
 	private SharedPreferencesUtils spf;
 	private List<EventBean> list;//??????????????????
 	private String showDateFormat;
 	
 	private MyReceiver myReceiver;
 	private List<EventBean> eventList;
	private MediaPlayer player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		initTitle();
		initData();
		initReceiver();
		initRemindVoice();
		requestTodayRemind();
		requestRemindList();
	}
	
	private void initTitle() {
		tvTitle.setText("???????????????");
		ivImageLeft.setVisibility(View.GONE);
		ivBack.setVisibility(View.VISIBLE);
	}
	
	private void initData() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy???MM???dd???");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		showDate = sdf.format(date);
		showDateFormat=sdf1.format(date);
		//???????????????????????????
		itemList = new ArrayList<EventBean>();
		adapter = new SlideAdapter();
		lvContent.setAdapter(adapter);
		
		spf = new SharedPreferencesUtils(this, AlarmService.ALARM_SER);
		list = (List<EventBean>) spf.getObject(LoginUtil.getUserId(this), EventBean.class);
	}
	
	/**
	 * ?????????????????????
	 */
	public void initReceiver() {
		myReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();  
        filter.addAction(Intent.ACTION_TIME_TICK);  
        registerReceiver(myReceiver, filter);  
	}
	
	/**
	 * ?????????????????????
	 */
	public void initRemindVoice() {
		player = MediaPlayer.create(this, R.raw.remind_voice);
		player.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				if(mp != null){
					mp.reset();
					mp.release();
					player = MediaPlayer.create(CustomRemindActivity.this, R.raw.remind_voice);
				}
			}
		});
	}
	
	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Intent.ACTION_TIME_TICK.equals(action)) {
				remindVoice();
	        }
		}
		
	}
	
	/**
	 * ????????????
	 */
	public void remindVoice() {
		if (eventList != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			//??????????????????
			String currentTime = sdf.format(new Date());
			Log.i("XmsMainActivity", "currentTime-->" + currentTime);
			Log.i("XmsMainActivity", "eventList.size-->" + eventList.size());
			for (int i = 0; i < eventList.size(); i++) {
				EventBean eventBean = eventList.get(i);
				String time = eventBean.getRemindtime();
				if (time != null && time.length() > 4) {
					time = time.substring(0, 5);
				}
				if (currentTime.equals(time)){
					//??????????????????
					llRemind.setVisibility(View.VISIBLE);
					tvRemind.setText(eventBean.getRemindtime() + " " + eventBean.getContent());
					tvRemind.setFocusable(true);
					tvRemind.setFocusableInTouchMode(true);
					tvRemind.requestFocus();
					//??????????????????
					player.setLooping(false);
					player.start();
					break;
				}
			}
		}
	}
	
	@OnClick({R.id.ivBack, R.id.rlAdd, R.id.ivDelete})
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;
		case R.id.rlAdd:
			 Intent intent = new Intent();
			 intent.setClass(CustomRemindActivity.this, RemindManageActivity.class);
			 Bundle bundle = new Bundle();
			 bundle.putString(RemindManageActivity.DATE,showDate);
			 bundle.putString(RemindManageActivity.OPRATE, "1");
			 bundle.putString(RemindManageActivity.DAY_OF_WEEK, CalendarUtils.getWeekDay(showDateFormat));
			 intent.putExtras(bundle);
			 startActivityForResult(intent, 1);
			break;
		case R.id.ivDelete:
			llRemind.setVisibility(View.GONE);
			break;
		}
	}
	
	class SlideAdapter extends android.widget.BaseAdapter {

	       
    	public SlideAdapter(){
    		//initList();
    	}
		@Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xms_item_remind, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final EventBean item = itemList.get(position);
            if (item != null) {
            	holder.tvRemindTime.setText(item.getRemindtime());
            	//holder.tvEndTime.setText("???????????????"+item.getEndtime());
            	//holder.tvRecycleType.setText(item.getType());
            	holder.tvContent.setText(item.getContent());
            }
            holder.ivDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					for (Integer p : isSelected.keySet()) {
						isSelected.put(p, false);
					}
					holder.llOperate.setVisibility(View.GONE);
					requestDeleteEvent(itemList.get(position).getEventId());
				}
			});
            holder.ivEdit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//dayOfWeek(selectPostion);
					for (Integer p : isSelected.keySet()) {
						isSelected.put(p, false);
					}
					holder.llOperate.setVisibility(View.GONE);
					Intent intent = new Intent();
					intent.setClass(CustomRemindActivity.this, RemindManageActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString(RemindManageActivity.DATE,showDate);
			    	bundle.putString(RemindManageActivity.OPRATE, "2");
			    	bundle.putString(RemindManageActivity.DAY_OF_WEEK,  CalendarUtils.getWeekDay(showDateFormat));
			    	bundle.putSerializable(RemindManageActivity.EVENT, itemList.get(position));
			    	intent.putExtras(bundle);
			    	startActivityForResult(intent, 2);
				}
			});
            holder.rlRemind.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					currentPosition = position;
					if(isSelected.size()!=0){
						boolean flag = !isSelected.get(position);
						// ?????????????????????FALSE
						for (Integer p : isSelected.keySet()) {
							isSelected.put(p, false);
						}
						// ??????????????????????????????
						isSelected.put(position, flag);
						SlideAdapter.this.notifyDataSetChanged();
						beSelectedData.clear();
						if (flag)
							beSelectedData.add(itemList.get(position));
					}
				
				}
			});
            if(isSelected.size()!=0){
            	 if(isSelected.get(position)){
                 	holder.llOperate.setVisibility(View.VISIBLE);
                 }else{
                 	holder.llOperate.setVisibility(View.GONE);
                 }
            }
           
            return convertView;
        }
        
	
    }

    private static class ViewHolder {
    	public TextView tvRemindTime;
        public TextView tvContent;
        public LinearLayout llOperate;
        public RelativeLayout rlRemind;
        public ImageView ivDelete;
        public ImageView ivEdit;
        ViewHolder(View view) {
        	tvRemindTime = (TextView) view.findViewById(R.id.tvRemindTime);
        	tvContent = (TextView) view.findViewById(R.id.tvContent);
        	llOperate=(LinearLayout)view.findViewById(R.id.llOperate);
        	rlRemind=(RelativeLayout)view.findViewById(R.id.rlRemind);
        	ivDelete=(ImageView)view.findViewById(R.id.ivDel);
        	ivEdit=(ImageView)view.findViewById(R.id.ivEdit);
        }
    }
    
	 /**
     * ??????????????????
     * 
     */
    public void requestTodayRemind() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String date = sdf.format(new Date());
    	try {
			// ??????CSP XML??????
			CspXmlXmsCom cspXmlXmsCom = new CspXmlXmsCom(LoginUtil.getUserId(this), "MS002001");
			cspXmlXmsCom.setDate(date);
			String strXml = cspXmlXmsCom.getCspXml();
			Log.i("tag", "getCspXml");
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			Log.i("tag", "Mcis");
			final byte[] byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
			Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {
				@Override
				public void onSuccess(String responStr) {
					EventListXmlBean eventListXmlBean = EventListResp.readStringXml(responStr);
					if ("00".equals(eventListXmlBean.getErrorcode())) {
						eventList = eventListXmlBean.getEventList();
						remindVoice();
					} else if("01".equals(eventListXmlBean.getErrorcode())){
						llRemind.setVisibility(View.GONE);
					}
					
				}

				@Override
				public void onFinish() {
					
				}

				@Override
				public void onFailure(String responStr) {
					
				}
			}, false);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * ??????????????????
     * 
     */
    private void requestRemindList() {
    	Log.i("RemindServiceFragment", "requestRemindList...");
    	try {
			// ??????CSP XML??????
			CspXmlXmsCom cspXmlXmsCom = new CspXmlXmsCom(LoginUtil.getUserId(this), "MS002001");
			cspXmlXmsCom.setDate("");
			String strXml = cspXmlXmsCom.getCspXml();
			Log.i("tag", "getCspXml");
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			Log.i("tag", "Mcis");
			final byte[] byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
			Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
//			cspUtil.setTest(true);
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {
				@Override
				public void onSuccess(String responStr) {
					EventListXmlBean eventListXmlBean = EventListResp.readStringXml(responStr);
					if (!"00".equals(eventListXmlBean.getErrorcode())) {
						if ("01".equals(eventListXmlBean.getErrorcode())) {
							itemList.clear();
							adapter.notifyDataSetChanged();
						} else {
							Toast.makeText(CustomRemindActivity.this, eventListXmlBean.getErrormsg(), Toast.LENGTH_SHORT).show();
						}
					} else {
						final List<EventBean> eventList = eventListXmlBean.getEventList();
						if (eventList != null && eventList.size() > 0) {
							itemList.clear();
							itemList.addAll(eventList);
							for (int i = 0; i < itemList.size(); i++) {
				    			isSelected.put(i, false);
				    		}
				    		// ????????????????????????
				    		if (beSelectedData.size() > 0) {
				    			beSelectedData.clear();
				    		}
							adapter.notifyDataSetChanged();
							new AsyncTask<String, Integer, String>(){

								@Override
								protected String doInBackground(String... params) {
									
									// ??????????????????
									boolean isDataChange = false;
									for (int i = 0; i < eventList.size(); i++) {
										EventBean eventBean = eventList.get(i);
										if (list == null) {
											list = new ArrayList<EventBean>();
											list.addAll(eventList);
											isDataChange = true;
										} else {
											int count = 0;
											for (int j = 0; j < list.size(); j++) {
												EventBean mEventBean = list.get(j);
												//????????????
												if (!eventBean.getEventId().equals(mEventBean.getEventId())) {
													count++;
												} 
											}
											//????????????????????????
											if (count == list.size()) {
												Log.i("RemindServiceFragment", "????????????...");
												list.add(eventBean);
												isDataChange = true;
											} 
										}
									}
									if (isDataChange) {
										spf.setObject(LoginUtil.getUserId(CustomRemindActivity.this), list);
									}
									return null;
								}

								@Override
								protected void onPostExecute(String result) {
									super.onPostExecute(result);
									// ??????????????????
									AlarmServiceManager.getInstance().startAlarmService(CustomRemindActivity.this);
							 }}.execute("");
						}
					}
				}

				@Override
				public void onFinish() {

				}
				@Override
				public void onFailure(String responStr) {
					Toast.makeText(CustomRemindActivity.this, responStr, Toast.LENGTH_SHORT).show();
				}
			}, true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * ??????????????????
     * @param remindtime
     * @return
     */
    public String handleRemindTime(String time) {
    	String remindtime = dateAdapter.getCurrentYear(selectPostion) + "-" + 
				dateAdapter.getCurrentMonth(selectPostion) + "-" + dayNumbers[selectPostion];
    	if (time != null && time.length() > 4) {
    		remindtime = remindtime + " " + time.substring(0, 5);
    	}
    	Log.i("RemindServiceFragment", "????????????????????? " + remindtime);
    	return remindtime;
    }
    
    /**
     * ??????????????????
     * @param eventBean
     * @return
     */
    public EventBean handleEventBean(EventBean eventBean) {
    	EventBean mEventBean = new EventBean();
		mEventBean.setEventId(eventBean.getEventId());
		mEventBean.setContent(eventBean.getContent());
		mEventBean.setType(eventBean.getType());
		mEventBean.setRemindtime(handleRemindTime(eventBean.getRemindtime()));
		return mEventBean;
    } 
    
	/**
	 * ??????????????????
	 * @param eventId
	 */
	private void requestDeleteEvent(final String eventId) {
		try {
			// ??????CSP XML??????
			CspXmlXmsCom cspXmlXmsCom = new CspXmlXmsCom(LoginUtil.getUserId(this), "MS002002");
			cspXmlXmsCom.setEventId(eventId != null ? eventId : "");
			String strXml = cspXmlXmsCom.getCspXml();
			Log.i("tag", "getCspXml");
			// ??????MCIS??????
			Mcis mcis = new Mcis(strXml, TransactionValue.CSPSZF);
			//Log.i("tag", "Mcis");
			final byte[] byteMessage = mcis.getMcis();
			// ????????????
			CspUtil cspUtil = new CspUtil(this);
			Log.i("tag", "??????????????? " + new String(byteMessage, "GBK"));
//			cspUtil.setTest(true);
			cspUtil.postCspLogin(new String(byteMessage, "GBK"), new CspUtil.CallBack() {

				@Override
				public void onSuccess(String responStr) {
					EventComXmlBean eventComXmlBean = EventComResp.readStringXml(responStr);
					if (!eventComXmlBean.getErrorcode().equals("00")) {
						Toast.makeText(CustomRemindActivity.this, eventComXmlBean.getErrormsg(), Toast.LENGTH_SHORT).show();
					} else{
						itemList.remove(currentPosition);
						adapter.notifyDataSetChanged();
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								EventBean eventBean = list.get(i);
								if (eventId.equals(eventBean.getEventId())) {
									list.remove(eventBean);
									spf.setObject(LoginUtil.getUserId(CustomRemindActivity.this), list);
									break;
								}
							}
						}
					}
				}

				@Override
				public void onFinish() {

				}

				@Override
				public void onFailure(String responStr) {
					Toast.makeText(CustomRemindActivity.this, responStr, Toast.LENGTH_SHORT).show();
				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			requestTodayRemind();
			requestRemindList();
		}
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(myReceiver);
		super.onDestroy();
	}

}
