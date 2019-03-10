package com.mapscience.modular.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.mapscience.core.util.Result;
import com.mapscience.modular.system.model.ProType;
import com.mapscience.modular.system.service.IProTypeService;

/**
 * <p>
 * 流程类型分类（也可以让表单关联） 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-02-28
 */
@Controller
@RequestMapping("/proType")
public class ProTypeController {

	@Autowired
	private IProTypeService proTypeService;
	 //默认路径
    private final String PREFIX = "/modular/";
	
    /**
     * 进入类型管理界面
     * @return
     */
	@RequestMapping("index")
	public String index() {
		return PREFIX+"proType/index";
	}
	
	/**
	 * g根据条件分页查询流程类型
	 * @param page
	 * @param proType
	 * @return
	 */
	@RequestMapping("findPageProType")
	@ResponseBody
	public Result findPageProType(Page<ProType> page, ProType proType) {
		return this.proTypeService.getPageProType(page, proType);
	}
	
	
}

