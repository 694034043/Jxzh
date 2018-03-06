package com.bocop.zyt.bocop.zyyr.bean;

import com.bocop.zyt.bocop.yfx.bean.BaseResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 产品详情
 * 
 * @author lh
 * 
 */
@XStreamAlias("UTILITY_PAYMENT")
public class ProductDetailsResponse extends BaseResponse {

	@XStreamAlias("DATA_AREA")
	private ProductDetailsExtern detailsExtern;

	public ProductDetailsExtern getDetailsExtern() {
		return detailsExtern;
	}

	public void setDetailsExtern(ProductDetailsExtern detailsExtern) {
		this.detailsExtern = detailsExtern;
	}
	
	
	
}
