/**
 * 
 */
package com.bocop.zyt.bocop.xms.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bocop.zyt.jx.constants.Constants;

import java.util.Date;

/** 
 * @author luoyang  E-mail: luoyang8714@163.com
 * @version 创建时间：2016-12-9 下午5:45:00 
 * 类说明 
 */
/**
 * @author zhongye
 *
 */
public class Birthday {

	/**
	 * 
	 */
	public Birthday() {
		// TODO Auto-generated constructor stub
	}
	
	public String year;
	public  Date birthday;   
	
	public static boolean isShowDialog(Context cxt){
		
		final SharedPreferences sp = cxt.getSharedPreferences(
				Constants.CUSTOM_PREFERENCE_NAME, Context.MODE_PRIVATE);
		String id = sp.getString(Constants.CUSTOM_ID_NO, null);  //身份证号
		if(id != null){
			IdcardInfoExtractor idcardInfoExtractor  = new IdcardInfoExtractor(id);
//			birthday = idcardInfoExtractor.getBirthday();
		}else{
			
		}
		
		
		return true;
	}

}
