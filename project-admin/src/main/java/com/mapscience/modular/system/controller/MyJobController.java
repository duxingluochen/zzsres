/**
 * 
 */
package com.mapscience.modular.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *说明：
 *<p> 我的工作</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月28日
 *
 */
@Controller
@RequestMapping("/myjob")
public class MyJobController {

	 //默认路径
    private final String PREFIX = "/modular/";
	
	@RequestMapping("index")
	public String index() {
		return PREFIX+"myjob/index";
	}
	
}
