/**
 * 
 */
package com.mapscience.modular.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.mapscience.core.util.Result;
import com.mapscience.modular.system.model.Merchant;
import com.mapscience.modular.system.service.MerchantService;

/**
 *说明：
 *<p>商户控制器 </p>
 * 书写者 @author  WCF
 * 创建时间 2019年3月5日
 *
 */
@Controller
@RequestMapping("merchant")
public class MerchantController {
	
	private final String PREFIX = "/modular/merchant/";
	
	@Autowired
	private MerchantService merchantService;
	
	/**
	 * 平台信息界面
	 * @return
	 */
	@RequestMapping("platforminformation")
	public String index() {
		return PREFIX+"platforminformation";
	}
	
	/**
	 * 增加界面
	 * @return
	 */
	@RequestMapping("add")
	public String add() {
		return PREFIX+"add";
	}
	
	/**
	 * 平台表单界面
	 * @return
	 */
	@RequestMapping("platform")
	public String platform() {
		return PREFIX+"platform";
	}
	
	
	/**
	 * 分页查询
	 * @param page
	 * @param merchant
	 * @return
	 */
	@RequestMapping("getMerchantPage")
	@ResponseBody
	public Result getMerchantPage(Page<Merchant> page, Merchant merchant) {
		return this.merchantService.getMerchantPage(page, merchant);
	}
	
	
	/**
	 * zengjia
	 * @param merchant
	 * @return
	 */
	@RequestMapping("addMerchant")
	@ResponseBody
	public Result addMerchant(Merchant merchant) {
		return this.merchantService.saveOrUpdataMerchant(merchant);
	}
	
}
