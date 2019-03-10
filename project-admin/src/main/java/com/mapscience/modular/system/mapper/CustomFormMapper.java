package com.mapscience.modular.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mapscience.modular.system.model.CustomForm;
import com.mapscience.modular.system.modelDTO.CustomFormDTO;

/**
 * <p>
 * 自定义表单 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-25
 */
public interface CustomFormMapper extends BaseMapper<CustomForm> {

	
	List<CustomFormDTO> getPlatCustomFormPage(Page<CustomFormDTO> page, CustomFormDTO customForm);
}
