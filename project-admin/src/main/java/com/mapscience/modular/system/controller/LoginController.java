package com.mapscience.modular.system.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mapscience.config.jwt.JwtToken;
import com.mapscience.core.common.constant.Constant;
import com.mapscience.core.common.status.ProjectStatusEnum;
import com.mapscience.core.util.AesCipherUtil;
import com.mapscience.core.util.JedisUtil;
import com.mapscience.core.util.JwtUtil;
import com.mapscience.core.util.Result;
import com.mapscience.modular.system.model.Company;
import com.mapscience.modular.system.model.Employee;
import com.mapscience.modular.system.modelDTO.MenuDTO;
import com.mapscience.modular.system.service.IEmployeeService;
import com.mapscience.modular.system.service.IMenuService;

/**
 * <p>
 * 登录记录 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-16
 */
@Controller
@PropertySource("classpath:jwt.properties")
public class LoginController {

	/**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    private IEmployeeService employeeService;
    
    @Autowired
    private IMenuService menuService;
    //默认路径
    private final String PREFIX = "/modular/";

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request, Model modelAndView) {
        /*if (!JedisUtil.exists("session:"+request.getSession().getId())) {
            return "/login";
        }*/
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Employee employee = (Employee)session.getAttribute("emp");
        Company company = (Company)session.getAttribute("company");
       /* List<MenuDTO> menus = this.menuService.findMenusByEmpId(employee, company);
        modelAndView.addAttribute("menus", menus);*/
        return PREFIX + "/home/home_index";
    }
    
    
    /**
     * 用户登录
     * @param employee
     * @return
     */
	@PostMapping("/login")
	@ResponseBody
    public Result login(Employee employee){
        //验证是否为空
        if(ObjectUtils.isEmpty(employee.getAccount())){
            return  new Result(ProjectStatusEnum.NO_THIS_USER.getCode(),ProjectStatusEnum.NO_THIS_USER.getMsg());
        }
        if (ObjectUtils.isEmpty(employee.getPassWord())){
            return  new Result(ProjectStatusEnum.USE_PASSWORD_NO.getCode(),ProjectStatusEnum.USE_PASSWORD_NO.getMsg());
        }
        //清除空格
        employee.setAccount(employee.getAccount().trim());
        employee.setPassWord(employee.getPassWord().trim());
        String key = AesCipherUtil.enCrypto(employee.getPassWord());
        Employee emp = this.employeeService.getEmployeeByAccountAndPasswd(employee.getAccount(), key);
        if (ObjectUtils.isEmpty(emp)){
            return new Result(ProjectStatusEnum.USER_NOT_EXISTED.getCode(), ProjectStatusEnum.USER_NOT_EXISTED.getMsg());
        }
        // 清除可能存在的Shiro权限信息缓存
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + emp.getEmployeeId())) {
            JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + emp.getEmployeeId());
        }
        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + emp.getEmployeeId(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(emp.getEmployeeId(), currentTimeMillis);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("emp", emp);
        JwtToken jwtToken = new JwtToken(token);
        subject.login(jwtToken);
        JedisUtil.setObject(Constant.SESSION + session.getId(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        return new Result(HttpStatus.OK.value(), token);
    }
}

