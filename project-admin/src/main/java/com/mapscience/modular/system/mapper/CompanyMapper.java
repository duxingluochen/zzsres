package com.mapscience.modular.system.mapper;

import com.mapscience.modular.system.model.Company;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 公司基本信息表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-16
 */
public interface CompanyMapper extends BaseMapper<Company> {

    List<Company> findComList(Company company);

    /**
     * 查询顶级公司
     * @return
     */
    @Select("select * from t_company where `status`=1 and parent_id='1' ")
    List<Company> findOrgListForOne();
}
