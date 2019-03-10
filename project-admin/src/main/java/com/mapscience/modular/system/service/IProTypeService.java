package com.mapscience.modular.system.service;

import com.mapscience.core.util.Result;
import com.mapscience.modular.system.model.ProType;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 流程类型分类（也可以让表单关联） 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-02-28
 */
public interface IProTypeService extends IService<ProType> {

	/**
	 * 更具条件查询流程分类
	 * @param page
	 * @param proType
	 * @return
	 */
	Result getPageProType(Page<ProType> page, ProType proType);
	
	/**
	 * 根据条件查询类型类表
	 * @param proTyp
	 * @return
	 */
	List<ProType> getProTypeList(ProType proTyp);
}
