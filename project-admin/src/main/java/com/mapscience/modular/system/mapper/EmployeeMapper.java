package com.mapscience.modular.system.mapper;

import com.mapscience.modular.system.model.Employee;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 员工信息表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-16
 */
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
	@Select("select * from t_employee where employee_id=#{empId}")
    Employee getByEmpId(String empId);

    /**
     * 根据账户号和密码查询用户信息
     * @param account
     * @param passWord
     * @return
     */
    Employee getEmployeeByAccountAndPasswd(String account, String passWord);
}
