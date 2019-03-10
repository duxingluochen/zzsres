/**
 * 
 */
package com.mapscience.modular.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mapscience.core.util.JedisUtil;
import com.mapscience.core.util.common.ObjectUtil;
import com.mapscience.modular.system.model.CustomForm;
import com.mapscience.modular.system.model.ProType;
import com.mapscience.modular.system.service.ICustomFormService;
import com.mapscience.modular.system.service.IProTypeService;

/**
 *说明：
 *<p> 流程控制器</p>
 * 书写者 @author  WCF
 * 创建时间 2019年3月4日
 *
 */
@RequestMapping("flow")
@Controller
public class FlowController {

	@Autowired
	private ICustomFormService customFormService;
	
	@Autowired
	private IProTypeService proTypeService;
	
	private final String PREFIX = "/modular/";

	/**
	 * 发起流程首页
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model) {
		Object object = JedisUtil.getObject("proType:0");
		if (ObjectUtil.isEmpty(object)) {
			object = this.proTypeService.getProTypeList(new ProType("0"));
			JedisUtil.setObject("proType:0", object);
		}
		Object obj = JedisUtil.getObject("proType:1");
		model.addAttribute("proTypes", object);//一级
		model.addAttribute("proTypec", obj);//二级
		return PREFIX+"flow/index";
	}
	
	/**
	 * 申请流程界面
	 * @return
	 */
	@RequestMapping("startIndex")
	public String startIndex(CustomForm customForm, Model model) {
//		customForm = this.customFormService.selectById(customForm.getFormId());
//		model.addAttribute("template", customForm.getHtml());
		return PREFIX+"flow/addflow";
	}
	
	
}
