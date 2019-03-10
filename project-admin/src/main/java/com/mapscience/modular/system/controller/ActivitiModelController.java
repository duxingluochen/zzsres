package com.mapscience.modular.system.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 流程模型图-关联表单 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-03-05
 */
@Controller
@RequestMapping("/activitiModel")
public class ActivitiModelController {

	private final String PREFIX = "/modular/activiti/";
	
	/**
	 * 创建流程界面
	 * @return
	 */
	@RequestMapping("modelerIndex")
	public String modelerIndex() {
		return PREFIX+"modeler";
	}
}

