package com.mapscience.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mapscience.core.common.ResponseVal;
import com.mapscience.core.util.Result;
import com.mapscience.modular.system.model.CustomForm;
import com.mapscience.modular.system.modelDTO.CustomFormDTO;

/**
 * <p>
 * 自定义表单 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-25
 */
public interface ICustomFormService extends IService<CustomForm> {

	/**
	 * 保存或修改自定义表单
	 * @param customForm
	 * @return
	 */
	public ResponseVal<CustomForm> saveOrUpdateCustomForm(CustomForm customForm);
	
	/**
	 * 平台表单管理
	 * @param page
	 * @param customForm
	 * @return
	 */
	Result getPlatCustomFormPage(Page<CustomFormDTO> page, CustomFormDTO customForm);
	
}
