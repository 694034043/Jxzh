package com.bocop.zyt.bocop.jxplatform.pay;

/**
 * Created by Bingo on 2017/11/28.
 */

public class WxpayEvent {

    public static final int WXPAY_SUCCESS = 0;
    public static final int WXPAY_FAIL = -1;
    public static final int WXPAY_CANCEL = -2;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
