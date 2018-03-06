package com.bocop.zyt.bocop.jxplatform.pay;

/**
 * Created by Bingo on 2017/11/14.
 */

public class AlipayOrder {

    private String orderStr;
    private String sign;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
