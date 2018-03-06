package com.bocop.zyt.bocop.zyyr.bean;

import com.bocop.zyt.bocop.yfx.bean.BaseResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 申请贷款状态
 * 
 * @author lh
 * 
 */
@XStreamAlias("UTILITY_PAYMENT")
public class StatusResponse extends BaseResponse {
	@XStreamAlias("DATA_AREA")
	private StatusExtern statusExtern;

	public StatusExtern getStatusExtern() {
		return statusExtern;
	}

	public void setStatusExtern(StatusExtern statusExtern) {
		this.statusExtern = statusExtern;
	}

}
