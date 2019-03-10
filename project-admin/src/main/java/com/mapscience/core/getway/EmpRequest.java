package com.mapscience.core.getway;

import com.mapscience.core.annotation.ParameterRule;
import com.mapscience.core.getway.BaseWayRequest;


/**
 *说明：
 *<p> 员工信息</p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月4日
 *
 */
public class EmpRequest extends BaseWayRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6279436066768821119L;

	/**身份证号*/
	@ParameterRule(regEx="(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)", message="身份证格式错误",minLength=16, maxLength=18, required=true)
	private String cardNo;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	
}
