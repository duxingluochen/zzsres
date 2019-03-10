/**
 * 
 */
package com.mapscience.modular.system.gatewayrequest;

import com.mapscience.core.annotation.ParameterRule;

/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2019年3月4日
 *
 */
public class FlowLAunchRequest extends CustomFormRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = -302250665202442762L;

	/**员工ID*/
	@ParameterRule(required=true, message="员工ID不能为空")
	protected String empId;
	
	/**表单ID*/
	@ParameterRule(required=true, message="表单ID不能为空")
	protected String formId;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
}
