package com.mapscience.modular.system.mapper;

import com.mapscience.modular.system.model.ProType;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 流程类型分类（也可以让表单关联） Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-02-28
 */
public interface ProTypeMapper extends BaseMapper<ProType> {

	/**
	 * 根据条件分页查询
	 * @param page
	 * @param proType
	 * @return
	 */
	List<ProType> getPageProType(Page<ProType> page, ProType proType);
	
	/**
	 * 根据条件查询类表
	 * @param proTyp
	 * @return
	 */
	List<ProType> getProTypeList(ProType proTyp);
}
