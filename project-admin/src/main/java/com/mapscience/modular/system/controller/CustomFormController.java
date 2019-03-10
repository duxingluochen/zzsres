package com.mapscience.modular.system.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.mapscience.core.base.controller.BaseController;
import com.mapscience.core.common.ResponseVal;
import com.mapscience.core.util.BASE64Util;
import com.mapscience.core.util.HttpUtil;
import com.mapscience.core.util.JedisUtil;
import com.mapscience.core.util.JsonUtil;
import com.mapscience.core.util.Result;
import com.mapscience.core.util.common.ObjectUtil;
import com.mapscience.modular.system.model.CustomForm;
import com.mapscience.modular.system.model.Merchant;
import com.mapscience.modular.system.modelDTO.CustomFormDTO;
import com.mapscience.modular.system.service.ICustomFormService;
import com.mapscience.modular.system.service.MerchantService;

/**
 * 
 *说明：自定义表单
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2019年1月25日
 *
 */
@Controller
@RequestMapping("/customForm")
public class CustomFormController extends BaseController{

	@Autowired
	private ICustomFormService customFormService;

	private final String PREFIX = "/modular/customform/";
	
	@Autowired
	private MerchantService merchantService;
	/**
	 * 创建自定义表界面
	 * @return
	 */
	@RequestMapping("createIndex")
	public String createIndex(Model model, Boolean flag) {
		if (flag) {
			Object object = JedisUtil.getObject("merchantList");
			if(ObjectUtil.isEmpty(object)) {
				object = this.merchantService.getMerchantList(new Merchant());
				JedisUtil.setObject("merchantList", object);
			}
			model.addAttribute("merchantList", object);
		}
		return PREFIX+"createForm";
	}
	
	/**
	 * 保存和提交
	 * @param customForm
	 * @return
	 */
	@PostMapping("saveOrUpdateCustomForm")
	@ResponseBody
	public ResponseVal<CustomForm> saveOrUpdateCustomForm(CustomForm customForm, String merchant) {
		//解析界面获取属性
		CustomForm newcustomForm = CustomForm.praseTemplate(customForm.getTemplate());
		String url="";
		if(ObjectUtil.isNotEmpty(merchant)) {
			merchant = BASE64Util.decrypt(merchant);
			String[] strs =  merchant.split(",");
			newcustomForm.setMerchantId(strs[0]);
			url = strs[1];
		}
		newcustomForm.setFormId(customForm.getFormId());
		newcustomForm.setFormName(customForm.getFormName());
		if(ObjectUtil.isNotEmpty(customForm.getMerchantId())) {
			newcustomForm.setMerchantId(customForm.getMerchantId());
		}
		ResponseVal<CustomForm> responseVal = this.customFormService.saveOrUpdateCustomForm(newcustomForm);
		Map<String, Object> params = Maps.newHashMap();
		params.put("data", newcustomForm.getDataJson());
		params.put("code", "200");
		params.put("mesg", "请求成功");
		if (200==responseVal.getCode()&& ObjectUtil.isNotEmpty(url)) {
			String httpResult = HttpUtil.sendJson(url, JSON.toJSONString(params));
			// 将返回参数转换成json对象并获取返回参数
			JSONObject response = JsonUtil.toBean(httpResult);
		}
		return responseVal;
	}
	
	/**
	 *预览和查看解析后的表单
	 * @param customForm
	 * @param flag false 没有保存的预览 true 保存通过id预览
	 * @return
	 */
	@RequestMapping("previewCustomForm")
	@ResponseBody
	public ResponseVal<CustomForm> previewCustomForm(CustomForm customForm) {
		if(ObjectUtil.isEmpty(customForm.getFormId())) {
			//解析界面获取属性
			CustomForm newcustomForm = CustomForm.praseTemplate(customForm.getTemplate());
			return new ResponseVal<CustomForm>(200, "成功", newcustomForm);
		}
		return new ResponseVal<CustomForm>(200, "成功", this.customFormService.selectById(customForm.getFormId()));
	}
	
	
	
	/**
	 * 平台分页查询
	 * @param page
	 * @param customForm
	 * @return
	 */
	@RequestMapping("getPlatCustomFormPage")
	@ResponseBody
	public Result getPlatCustomFormPage(Page<CustomFormDTO> page, CustomFormDTO customForm) {
		return this.customFormService.getPlatCustomFormPage(page, customForm);
	}
	
	
	
	@RequestMapping("getCustomFormByFormId")
	@ResponseBody
	public Result getCustomFormByFormId(CustomForm customForm) {
		return	new Result(200, this.customFormService.selectById(customForm.getFormId()));
	}
}

