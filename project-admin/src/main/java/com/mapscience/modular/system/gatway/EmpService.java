/**
 * 
 */
package com.mapscience.modular.system.gatway;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.mapscience.core.annotation.GatewayConfig;
import com.mapscience.core.annotation.ParameterRule;
import com.mapscience.core.common.ResponseVal;
import com.mapscience.core.enu.DoingMethodEnum;
import com.mapscience.core.exception.TypayRuntimeException;
import com.mapscience.core.getway.EmpRequest;

/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月4日
 *
 */
@Service
public class EmpService {

	@SuppressWarnings("rawtypes")
	@GatewayConfig(DoingMethodEnum.EMP_QUERY)
	public ResponseVal doProcess(HttpServletRequest requst,HttpServletResponse response, @ParameterRule EmpRequest model) throws TypayRuntimeException {
		//订单创建
		return new ResponseVal(200, "请求成功！");
	}
}
