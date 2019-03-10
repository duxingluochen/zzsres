package com.mapscience.modular.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.mapscience.modular.system.model.Company;
import com.mapscience.modular.system.model.Employee;
import com.mapscience.modular.system.model.Menu;
import com.mapscience.modular.system.modelDTO.MenuDTO;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-02-28
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * 通过员工公司Id查询员工的菜单
	 * @param empId
	 * @param comId
	 * @return
	 */
	List<MenuDTO> findMenusByEmpId(Employee employee, Company company);
	
}
