package com.mapscience.modular.system.service.impl;

import com.mapscience.modular.system.model.Employee;
import com.mapscience.core.util.JedisUtil;
import com.mapscience.core.util.Result;
import com.mapscience.core.util.common.ObjectUtil;
import com.mapscience.modular.system.mapper.EmployeeMapper;
import com.mapscience.modular.system.service.IEmployeeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-16
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

	Logger logger = Logger.getLogger("员工信息实现类");
	
    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Override
    public Employee getEmployeeByAccountAndPasswd(String account, String passWord) {
        return this.employeeMapper.getEmployeeByAccountAndPasswd(account, passWord);
    }
    
	@Override
	public Result getByEmpId(Employee employee) {
		try {
			employee = (Employee)JedisUtil.getObject("employee:"+employee.getEmployeeId());
			if (ObjectUtil.isNotEmpty(employee)) {
				return new Result(200, employee);
			}
			employee = this.employeeMapper.selectById(employee.getEmployeeId());
			if (ObjectUtil.isNotEmpty(employee)) {
				JedisUtil.setObject("employee:"+employee.getEmployeeId(), employee);
				return new Result(200, employee);
			}
			return new Result(500, "未查询到【"+employee.getEmployeeId()+"】用户信息");
		} catch (Exception e) {
			this.logger.error("通过id查询员工信息出现错误，员工id是"+employee.getEmployeeId(), e);
			return new Result(500, "通过id查询员工信息出现错误，员工id是"+employee.getEmployeeId());
		}
	}
	
	
}
