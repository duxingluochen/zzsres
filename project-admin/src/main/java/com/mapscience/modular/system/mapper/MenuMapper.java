package com.mapscience.modular.system.mapper;

import com.mapscience.modular.system.model.Company;
import com.mapscience.modular.system.model.Employee;
import com.mapscience.modular.system.model.Menu;
import com.mapscience.modular.system.modelDTO.MenuDTO;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-02-28
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 
	 * @param employee
	 * @param company
	 * @return
	 */
	@Select("select * from oa_menu where parent_id=#{parentId} ")
	List<MenuDTO> selectMenusByParentId(String parentId);
	
	/**
	 * 
	 * @param employee
	 * @param company
	 * @return
	 */
	@Select("select * from oa_menu where parent_id='0' ")
	List<MenuDTO> selectMenus(Employee employee, Company company);
}
