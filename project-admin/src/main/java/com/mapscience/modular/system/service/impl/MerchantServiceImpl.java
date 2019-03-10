/**
 * 
 */
package com.mapscience.modular.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mapscience.core.util.AESUtil;
import com.mapscience.core.util.JedisUtil;
import com.mapscience.core.util.Result;
import com.mapscience.core.util.UUIDUtil;
import com.mapscience.core.util.common.ObjectUtil;
import com.mapscience.modular.system.mapper.MerchantMapper;
import com.mapscience.modular.system.model.Merchant;
import com.mapscience.modular.system.service.MerchantService;

/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月4日
 *
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerchantService {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MerchantMapper merchantMapper;
	
	@Override
	public Result saveOrUpdataMerchant(Merchant merchant) {
		try {
			merchant.setUpdateTime(new Date());
			if(ObjectUtil.isEmpty(merchant.getMerchantId())) {
				merchant.setMerchantId(UUIDUtil.getUUID());
				merchant.setStatus(1);
				merchant.setCreateTime(new Date());
				this.merchantMapper.insert(merchant);
			}else {
				this.merchantMapper.updateById(merchant);
			}
			return new Result(200, null);
		} catch (Exception e) {
			return new Result(500, null);
		}
	}

	@Override
	public Merchant getMerchant(Merchant merchant) {
		try {
			Object object = JedisUtil.getObject("Merchant:"+merchant.getMerchantId());
			if(ObjectUtil.isNotEmpty(object)) {
				return (Merchant)object;
			}
			merchant = this.merchantMapper.getMerchant(merchant);
			JedisUtil.setObject("Merchant:"+merchant.getMerchantId(), merchant);
			return merchant;
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
		
	}

	@Override
	public Result responseVal(Merchant merchant, Map<String, Object> params) {
		try {
			/**查询商户*/
			merchant =  this.merchantMapper.getMerchant(merchant);
			//获取秘钥
			String key = merchant.getMerchantKey();
			//加密
			String enParams = AESUtil.encrypt(params.toString(), key);
			return new Result(200, enParams);
		} catch (Exception e) {
			e.getStackTrace();
			logger.info("返回值出现异常>>>", e);
			return new Result(502, "返回值出现异常");
		}
	}

	@Override
	public Result getMerchantPage(Page<Merchant> page, Merchant merchant) {
		try {
			List<Merchant> merchants =this.merchantMapper.getMerchantPage(page, merchant);
			return new Result(200, page.setRecords(merchants));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("返回值出现异常>>>", e);
		}
		return new Result(500, "返回值出现异常");
	}

	@Override
	public List<Merchant> getMerchantList(Merchant merchant) {
		try {
			List<Merchant> merchants = this.merchantMapper.getMerchantList(merchant);
			return merchants;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
