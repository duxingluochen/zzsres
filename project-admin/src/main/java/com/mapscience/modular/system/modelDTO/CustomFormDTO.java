/**
 * 
 */
package com.mapscience.modular.system.modelDTO;

import com.mapscience.modular.system.model.CustomForm;

/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2019年3月5日
 *
 */
public class CustomFormDTO extends CustomForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6623060945782174893L;

	
	/**商户名 必填*/
	private String merchantName;


	public String getMerchantName() {
		return merchantName;
	}


	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	
}
