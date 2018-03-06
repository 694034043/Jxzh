package com.bocop.zyt.bocop.xms.push.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bocop.zyt.bocop.xms.push.service.OnlineService;
import com.bocop.zyt.bocop.xms.push.util.Util;


/**
 * 网络改变的接收者
 * @author hch
 *
 */
public class ConnectivityAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if(Util.hasNetwork(context) == false){
			return;
		}
		Intent startSrv = new Intent(context, OnlineService.class);
		startSrv.putExtra(OnlineService.START_FROM, this.getClass().getName());
		startSrv.putExtra(OnlineService.NAME_CMD, OnlineService.VALUE_RESET);
		context.startService(startSrv);
	}

}
