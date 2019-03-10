/**
 * 
 */
package com.mapscience.modular.system.gatway;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mapscience.core.annotation.GatewayConfig;
import com.mapscience.core.annotation.ParameterRule;
import com.mapscience.core.enu.DoingMethodEnum;
import com.mapscience.core.util.BASE64Util;
import com.mapscience.core.util.Result;
import com.mapscience.core.util.common.OtherUtil;
import com.mapscience.modular.system.gatewayrequest.CustomFormRequest;
import com.mapscience.modular.system.gatewayrequest.FlowLAunchRequest;
import com.mapscience.modular.system.model.ErrorLog;
import com.mapscience.modular.system.model.Merchant;
import com.mapscience.modular.system.service.IErrorLogService;
import com.mapscience.modular.system.service.MerchantService;

/**
 *说明：
 *<p> 自定义表单外接service</p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月4日
 *
 */
@PropertySource({"classpath:pro.properties"})
@Configuration
public class CustomFormGatway {
	
	Logger logger = Logger.getLogger("自定义表单---CustomFormGatway");
	
	/**外接访问过来的地址*/
	@Value("${localUrl}")
	private String localUrl;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private IErrorLogService errorLogService;
	
	/**
	 * 商户请求创建表单的地址
	 * @param requst
	 * @param response
	 * @param model 
	 * @return
	 */
	@GatewayConfig(DoingMethodEnum.CUSTOM_INDEX)
	public Result createFormIndex(HttpServletRequest requst,HttpServletResponse response, @ParameterRule FlowLAunchRequest model){
		String ip = OtherUtil.getIpAddr(requst);
		try {
			String merchantId = model.getMerchantId();
			//base64加密
			String merchantId64 = BASE64Util.encrypt(model.toString());
			Map<String, Object> params = Maps.newHashMap();
			params.put("merchantId", merchantId);
			params.put("url", localUrl+"static/modular/flow/addflow.html?merchant="+merchantId64+"&url="+model.getUrl()+"&formId="+model.getFormId());
			return this.merchantService.responseVal(new Merchant(merchantId), params);
		} catch (Exception e) {
			logger.error("IP:"+ip+">>>商户请求发起流程的地址出现异常", e);
			this.errorLogService.insertAllColumn(new ErrorLog(e.getMessage(), "商户请求发起流程的地址出现异常", new Date(), JSONObject.toJSONString(model)));
			return new Result(500, "系统出现错误");
		}finally {
			requst = null;
			response = null;
			model = null;
		}
	}
	
	/**
	 * 应用程序请求发起流程获取表单路径
	 * @param requst
	 * @param response
	 * @param model
	 * @return
	 */
	@GatewayConfig(DoingMethodEnum.CUSTOM_LAUNCH)
	public Result launchCustom(HttpServletRequest requst,HttpServletResponse response, @ParameterRule CustomFormRequest model) {
		String ip = OtherUtil.getIpAddr(requst);
		try {
			String merchantId = model.getMerchantId();
			//base64加密
			String merchantId64 = BASE64Util.encrypt(merchantId+","+model.getBackurl());
			Map<String, Object> params = Maps.newHashMap();
			params.put("merchantId", merchantId);
			params.put("url", localUrl+"static/form/createForm.html?merchant="+merchantId64+"&url="+model.getUrl());
			return this.merchantService.responseVal(new Merchant(merchantId), params);
		} catch (Exception e) {
			logger.error("IP:"+ip+">>>商户请求创建表单的地址出现异常", e);
			this.errorLogService.insertAllColumn(new ErrorLog(e.getMessage(), "商户请求创建表单的地址出现异常", new Date(), JSONObject.toJSONString(model)));
			return new Result(500, "系统出现错误");
		}finally {
			requst = null;
			response = null;
			model = null;
		}
	}
}
