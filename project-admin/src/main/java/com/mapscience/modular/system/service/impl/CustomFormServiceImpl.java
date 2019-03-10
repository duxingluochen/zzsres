package com.mapscience.modular.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mapscience.core.common.ResponseVal;
import com.mapscience.core.util.Result;
import com.mapscience.modular.system.mapper.CustomFormMapper;
import com.mapscience.modular.system.model.CustomForm;
import com.mapscience.modular.system.modelDTO.CustomFormDTO;
import com.mapscience.modular.system.service.ICustomFormService;

/**
 * <p>
 * 自定义表单 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-25
 */
@Service
public class CustomFormServiceImpl extends ServiceImpl<CustomFormMapper, CustomForm> implements ICustomFormService {

	@Autowired
	private CustomFormMapper customFormMapper;
	
	@Override
	public ResponseVal<CustomForm> saveOrUpdateCustomForm(CustomForm customForm) {
		try {
			int callbackmun= 0 ;
			if(ObjectUtils.isEmpty(customForm.getFormId())) {
				customForm.setCreateTime(new Date());
				customForm.setUpdateTime(new Date());
				customForm.setStatus(1);
				callbackmun=this.customFormMapper.insertAllColumn(customForm);
			}else {
				customForm.setUpdateTime(new Date());
				this.customFormMapper.updateAllColumnById(customForm);
			}
			return callbackmun>0?new ResponseVal<CustomForm>(200, "成功"):new ResponseVal<CustomForm>(500, "失败");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseVal<CustomForm>(500, "失败");
		}
	}

	@Override
	public Result getPlatCustomFormPage(Page<CustomFormDTO> page, CustomFormDTO customForm) {
		try {
			List<CustomFormDTO> customForms = this.customFormMapper.getPlatCustomFormPage(page, customForm);
			return new Result(200, page.setRecords(customForms));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
