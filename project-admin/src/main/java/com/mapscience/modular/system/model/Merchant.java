package com.mapscience.modular.system.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 *说明：
 *<p> 商户 就是我们的下游</p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月4日
 *
 */
public class Merchant  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3982975501229263598L;

	 public Merchant() { }
	 public Merchant(String merchantId) {
		 this.merchantId = merchantId;
	 }
	/**商户ID*/
	private String merchantId;
	
	/**商户秘钥 必填*/
	private String merchantKey;
	
	/**商户名 必填*/
	private String merchantName;
	
	/**商户状态 1是可以访问  0 是禁止访问*/
	private int status;


	/**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    
    
    
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
