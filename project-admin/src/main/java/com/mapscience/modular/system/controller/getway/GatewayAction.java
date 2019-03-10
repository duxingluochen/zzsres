/**
 * 
 */
package com.mapscience.modular.system.controller.getway;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mapscience.core.getway.GetWayController;
import com.mapscience.modular.system.service.MerchantService;

/**
 *说明：
 *<p>对外开放API接口 </p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月3日
 *
 */
@Controller
@RequestMapping("gateway")
public class GatewayAction extends GetWayController{
	
	@Autowired
	private MerchantService merchantService;

 	@RequestMapping(value = "gateway.ht")
    @ResponseBody
    public Object gateway(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> params) {
        return super.gateWay(request, response, params, merchantService);
    }
}
