package com.bocop.zyt.bocop.zyt.biz;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.bocop.zyt.bocop.jxplatform.util.LoginUtil;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.model.NUser;
import com.bocop.zyt.fmodule.utils.FSPDb;
import com.bocop.zyt.fmodule.utils.FStringUtil;
import com.bocop.zyt.jx.base.BaseApplication;

/**
 * Created by ltao on 2017/2/14.
 * <p>
 * 登录专用
 */

public class LoginHelper {

    private static LoginHelper instance = null;
    private final Context context;
    private final FSPDb fspDb;

    public static synchronized LoginHelper get_instance(Activity activity) {
        return get_instance_x(activity);
    }

    public static synchronized LoginHelper get_instance(Fragment fragment) {
        return get_instance_x(fragment.getActivity());
    }

    private static LoginHelper get_instance_x(Activity activity) {
        if (instance == null) {
            instance = new LoginHelper(activity);
        }

        return instance;
    }

    public static LoginHelper get_instance_xx() {
        if (instance == null) {
            instance = new LoginHelper(BaseApplication.getInstance());
        }

        return instance;
    }


    public LoginHelper(Context context) {
        this.context = context;
        fspDb = new FSPDb(context, "LoginHelper");

    }

    public interface LoginCallback {

        public void suc();

        public void fali();

    }

    public void login(Activity act, final LoginCallback cb) {

        /**
         * 登录后不再登录
         *
         */
        if (!FStringUtil.is_empty(get_login_info().userid)) {
            cb.suc();
            return;
        }
        
        LoginUtil.authorize(act, new LoginUtil.ILoginListener() {
			
			@Override
			public void onLogin() {
				// TODO Auto-generated method stub
				cb.suc();
			}
			
			@Override
			public void onException() {
				// TODO Auto-generated method stub
				cb.fali();
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				cb.fali();
			}
			
			@Override
			public void onCancle() {
				// TODO Auto-generated method stub
				cb.fali();
			}
		});
        

    }

    public void login_out(Activity activity) {

     
    	LoginUtil.logoutWithoutCallback(activity);
    }


//    private void login_suc(final LoginCallback cb) {
//
//        BaseAct._handler.post(new Runnable() {
//            @Override
//            public void run() {
//                cb.suc();
//            }
//        });
//
//    }
//
//    private void login_fail(final LoginCallback cb) {
//        BaseAct._handler.post(new Runnable() {
//            @Override
//            public void run() {
//                cb.fali();
//            }
//        });
//
//    }

//    public void save_login_info(NUser.LoginInfo loginInfo) {
//        String ret = BaseAct._gson.toJson(loginInfo);
//        ILOG.log_4_7("存储信息 " + ret);
//        fspDb.save("login_info", ret);
//    }

    public NUser.LoginInfo get_login_info() {
        try {
            String ret = fspDb.get("login_info");
            ILOG.log_4_7("得到信息 " + ret);
            NUser.LoginInfo u = new NUser.LoginInfo( LoginUtil.getToken(context),  LoginUtil.getUserId(context),  LoginUtil.getRefreshToken(context));
            
            return u;
        } catch (Exception e) {

            return new NUser.LoginInfo("", "", "");
        }

    }

    public String fill_url_token(String url) {

        NUser.LoginInfo u = get_login_info();

        return url + "&accesstoken=" + u.access_token + "&userid=" + u.userid;
    }


}
