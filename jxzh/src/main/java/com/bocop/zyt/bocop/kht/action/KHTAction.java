package com.bocop.zyt.bocop.kht.action;

import android.webkit.WebView;

import com.bocop.zyt.bocop.gm.utils.HybridCallBack;
import com.bocop.zyt.bocop.kht.activity.KhtActivity;
import com.bocop.zyt.bocop.kht.intercept.KhtHybridUtil;
import com.bocop.zyt.jx.tools.LogUtils;

import java.util.HashMap;


public class KHTAction {

    private KhtActivity mainActivity;

    private HybridCallBack callBack = new HybridCallBack() {

        @Override
        public void errorMsg(Exception e) {
            LogUtils.e(e.getMessage());
        }
    };

    public KHTAction(KhtActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void fileUpload_getImageFromPhone(String js) {
        if (mainActivity != null) {
            mainActivity.fileUpload_getImageFromPhone(js);
        }
    }

    public void uploadPic( WebView wvWebView, String imgBase64, String state,String imgExtension) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("method", "fileUpload_getImageFromPhone");
        hashMap.put("state", state);
        hashMap.put("errorMessage", "");
        hashMap.put("imgBase64", imgBase64);
        hashMap.put("imgExtension", imgExtension);
        KhtHybridUtil.getInstance().handleJsRequest(wvWebView, "fsNativeCallBack", hashMap, callBack);
    }
    
	public void getLoginViewResultCall(){
		
		  this.mainActivity.getLoginViewResultCall();
		
	}
}
