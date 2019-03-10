package com.mapscience.modular.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mapscience.core.util.common.ObjectUtil;
import com.mapscience.modular.system.mapper.MenuMapper;
import com.mapscience.modular.system.model.Company;
import com.mapscience.modular.system.model.Employee;
import com.mapscience.modular.system.model.Menu;
import com.mapscience.modular.system.modelDTO.MenuDTO;
import com.mapscience.modular.system.service.IMenuService;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-02-28
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;
	@Override
	public List<MenuDTO> findMenusByEmpId(Employee employee, Company company) {
		List<MenuDTO> menus = Lists.newArrayList();
		//程序猿的
		if(5 == employee.getType()) {
			menus = this.menuMapper.selectMenus(employee, company);
			getChild(menus);
		}
		return menus;
	}
	
	private void getChild(List<MenuDTO> menus){
		for (MenuDTO menuDTO : menus) {
			List<MenuDTO> list = this.menuMapper.selectMenusByParentId(menuDTO.getMenuId());
			if(ObjectUtil.isNotEmpty(list)) {
				menuDTO.setChildren(list);
				getChild(list);
			}
		}
	}

}
