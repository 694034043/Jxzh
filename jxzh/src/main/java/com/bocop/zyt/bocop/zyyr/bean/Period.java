package com.bocop.zyt.bocop.zyyr.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Period{

	@XStreamAlias("VAL")
	private String period;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
}
