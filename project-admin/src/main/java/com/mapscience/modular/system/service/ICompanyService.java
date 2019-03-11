package com.mapscience.modular.system.service;

import com.mapscience.core.common.ResponseVal;
import com.mapscience.modular.system.model.Company;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 公司基本信息表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-16
 */
public interface ICompanyService extends IService<Company> {

    /**
     * 查询公司组织信息
     * @param company
     * @return
     */
    ResponseVal findComList(Company company);

    ResponseVal findOrgListForOne();
}
