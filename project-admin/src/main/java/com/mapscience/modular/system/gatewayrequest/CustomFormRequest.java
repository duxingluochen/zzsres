/**
 * 
 */
package com.mapscience.modular.system.gatewayrequest;

import com.mapscience.core.annotation.ParameterRule;
import com.mapscience.core.getway.BaseWayRequest;

/**
 *说明：
 *<p>商户信息</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月24日
 *
 */
public class CustomFormRequest extends BaseWayRequest{
	private static final long serialVersionUID = 1L;
	/**商户Id*/
	@ParameterRule(required=true, message="商户ID不能为空")
	protected String merchantId;
	/**后台回调地址*/
	@ParameterRule(required=true, message="后台回调地址不能为空")
	protected String backurl;
	/**前台回调地址*/
	@ParameterRule(required=true, message="前台回调地址不能为空")
	protected String url;
	

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getBackurl() {
		return backurl;
	}

	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
