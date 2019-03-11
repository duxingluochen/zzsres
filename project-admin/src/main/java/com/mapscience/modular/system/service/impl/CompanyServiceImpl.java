package com.mapscience.modular.system.service.impl;

import com.mapscience.core.common.ResponseVal;
import com.mapscience.modular.system.model.Company;
import com.mapscience.modular.system.mapper.CompanyMapper;
import com.mapscience.modular.system.service.ICompanyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 公司基本信息表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-16
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

    /**
     * 查询公司信息
     * @param company
     * @return
     */
    @Override
    public ResponseVal findComList(Company company) {
        this.baseMapper.findComList(company);
        return null;
    }


    @Override
    public ResponseVal findOrgListForOne() {
        try{
            List<Company> orgListForOne = this.baseMapper.findOrgListForOne();
            return new ResponseVal(HttpStatus.OK.value(),"查询成功",orgListForOne);
        }catch (Exception e ){
            return new ResponseVal(HttpStatus.INTERNAL_SERVER_ERROR.value(),"数据查询错误",e.getMessage());
        }

    }
}
