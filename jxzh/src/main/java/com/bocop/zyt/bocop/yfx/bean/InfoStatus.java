package com.bocop.zyt.bocop.yfx.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * θ΅ζηΆζ
 * 
 * @author rd
 * 
 */
public class InfoStatus {

	@XStreamAlias("WLS_VAL_STATUS")
	private String valStatus;

	public InfoStatus(String valStatus) {
		super();
		this.valStatus = valStatus;
	}

	public InfoStatus() {
		super();
	}

	public String getValStatus() {
		return valStatus;
	}

	public void setValStatus(String valStatus) {
		this.valStatus = valStatus;
	}

}
