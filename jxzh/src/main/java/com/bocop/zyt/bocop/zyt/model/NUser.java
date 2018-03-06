package com.bocop.zyt.bocop.zyt.model;

import java.io.Serializable;

/**
 * Created by ltao on 2017/2/14.
 */

public class NUser {

    public static class LoginInfo implements Serializable {
        public LoginInfo(String access_token, String userid, String refresh_token) {
            this.access_token = access_token;
            this.userid = userid;
            this.refresh_token = refresh_token;
        }

        public String access_token;
        public String userid;
        public String refresh_token;

    }
}
