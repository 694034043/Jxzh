package com.bocop.zyt.bocop.yfx.bean;

import com.bocop.zyt.bocop.xms.bean.ConstHead;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class BaseResponse {

	@XStreamAlias("CONST_HEAD")
	private ConstHead constHead;

	public ConstHead getConstHead() {
		return constHead;
	}

	public void setConstHead(ConstHead constHead) {
		this.constHead = constHead;
	}

}
