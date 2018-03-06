package com.bocop.zyt.bocop.xms.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bocop.zyt.bocop.xms.service.AlarmServiceManager;

/**
 * 系统启动的广播接收者
 * @author hch
 *
 */
public class BootAlarmReceiver extends BroadcastReceiver {

	public BootAlarmReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// 开始闹铃服务
		AlarmServiceManager.getInstance().startAlarmService(context);
	}

}
