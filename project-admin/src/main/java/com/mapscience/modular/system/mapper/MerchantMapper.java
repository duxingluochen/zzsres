/**
 * 
 */
package com.mapscience.modular.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mapscience.core.util.Result;
import com.mapscience.modular.system.model.Merchant;

/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月24日
 *
 */
@Repository
public interface MerchantMapper extends BaseMapper<Merchant>{

	/***
	 * 增加或者修改商户
	 * @param merchant
	 * @return
	 */
	int saveOrUpdataMerchant(Merchant merchant);
	
	/***
	 * 通过商户Id获取商户
	 * @param merchant
	 * @return
	 */
	@Select("select * from oa_merchant where status = 1 and merchant_id=#{merchantId}")
	Merchant getMerchant(Merchant merchant);
	
	/**
	 * 分页查询
	 * @param page
	 * @param merchant
	 * @return
	 */
	List<Merchant> getMerchantPage(Page<Merchant> page,Merchant merchant);
	
	
	List<Merchant> getMerchantList(Merchant merchant);
}
