package com.mapscience.modular.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.mapscience.core.util.Result;
import com.mapscience.modular.system.model.Merchant;

/**
 *说明：
 *<p> 商户服务类</p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月4日
 *
 */
public interface MerchantService {

	/***
	 * 增加或者修改商户
	 * @param merchant
	 * @return
	 */
	Result saveOrUpdataMerchant(Merchant merchant);
	
	/***
	 * 通过商户Id获取商户
	 * @param merchant
	 * @return
	 */
	Merchant getMerchant(Merchant merchant);
	
	/**
	 * 所有返回值得封装方法
	 * @param merchant
	 * @param params
	 * @return
	 */
	Result responseVal(Merchant merchant, Map<String, Object> params);
	
	/**
	 * 分页查询
	 * @param page
	 * @param merchant
	 * @return
	 */
	Result getMerchantPage(Page<Merchant> page,Merchant merchant);
	
	/**
	 * 查询列表
	 * @param merchant
	 * @return
	 */
	List<Merchant> getMerchantList(Merchant merchant);
}
