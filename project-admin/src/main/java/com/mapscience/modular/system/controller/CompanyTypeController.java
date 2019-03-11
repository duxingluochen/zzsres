package com.mapscience.modular.system.controller;


import com.mapscience.core.common.ResponseVal;
import com.mapscience.modular.system.service.ICompanyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 公司业务类型表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-16
 */
@Controller
@RequestMapping("/companyType")
public class CompanyTypeController {

    /**
     * 公司业务类型
     */
    @Autowired
    private ICompanyTypeService companyTypeService;

    /**
     * 查询全部行业类型
     * @return
     */
    @ResponseBody
    @RequestMapping("findList")
    public ResponseVal findList(){
        return this.companyTypeService.findList();
    }

}

