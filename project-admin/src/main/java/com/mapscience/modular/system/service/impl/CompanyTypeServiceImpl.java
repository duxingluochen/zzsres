package com.mapscience.modular.system.service.impl;

import com.mapscience.core.common.ResponseVal;
import com.mapscience.modular.system.model.CompanyType;
import com.mapscience.modular.system.mapper.CompanyTypeMapper;
import com.mapscience.modular.system.service.ICompanyTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 公司业务类型表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-16
 */
@Service
public class CompanyTypeServiceImpl extends ServiceImpl<CompanyTypeMapper, CompanyType> implements ICompanyTypeService {

    /**
     * 查询全部行业类型
     * @return
     */
    @Override
    public ResponseVal findList() {
        try{
            List<CompanyType> list = this.baseMapper.findList();
            return new ResponseVal(HttpStatus.OK.value(),"查询成功",list);
        }catch (Exception e){
            return new ResponseVal(HttpStatus.INTERNAL_SERVER_ERROR.value(),"数据查询错误",e.getMessage());
        }

    }
}
