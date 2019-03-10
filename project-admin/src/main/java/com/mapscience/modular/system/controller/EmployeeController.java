package com.mapscience.modular.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mapscience.core.util.Result;
import com.mapscience.modular.system.model.Employee;
import com.mapscience.modular.system.service.IEmployeeService;

/**
 * <p>
 * 员工信息表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-16
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    
    /**
     * 获取员工信息
     * @param employee
     * @return
     */
    @RequestMapping("getByEmpId")
    public Result getByEmpId(Employee employee) {
    	return this.employeeService.getByEmpId(employee);
    }
}

