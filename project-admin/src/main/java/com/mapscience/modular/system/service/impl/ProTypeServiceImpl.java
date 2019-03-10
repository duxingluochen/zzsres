package com.mapscience.modular.system.service.impl;

import com.mapscience.modular.system.model.ProType;
import com.mapscience.core.util.JedisUtil;
import com.mapscience.core.util.Result;
import com.mapscience.modular.system.mapper.ProTypeMapper;
import com.mapscience.modular.system.service.IProTypeService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程类型分类（也可以让表单关联） 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-02-28
 */
@Service
public class ProTypeServiceImpl extends ServiceImpl<ProTypeMapper, ProType> implements IProTypeService {
	
	Logger logger = Logger.getLogger("流程类型分类（也可以让表单关联） 服务实现类");
	
	@Autowired
	private ProTypeMapper proTypeMapper;

	@Override
	public Result getPageProType(Page<ProType> page, ProType proType) {
		try {
			List<ProType> proTypes = this.proTypeMapper.getPageProType(page, proType);
			return new Result(200, page.setRecords(proTypes));
		} catch (Exception e) {
			logger.error("根据条件查询分页查询流程类型出现异常>>>请求实体是"+JSON.toJSONString(proType), e);
			return new Result(500, "根据条件查询分页查询流程类型出现异常！");
		}
	}

	@Override
	public List<ProType> getProTypeList(ProType proTyp) {
		try {
			List<ProType> proTypes = this.proTypeMapper.getProTypeList(proTyp);
			List<List<ProType>> lists = Lists.newArrayList();
			for (ProType proType : proTypes) {
				List<ProType> types = this.proTypeMapper.getProTypeList(new ProType(proType.getTypeId()));
				lists.add(types);
			}
			JedisUtil.setObject("proType:1", lists);
			return proTypes;
		} catch (Exception e) {
			logger.error("根据条件查询类型类表出现异常>>>请求实体是"+JSON.toJSONString(proTyp), e);
		}
		return null;
	}

}
