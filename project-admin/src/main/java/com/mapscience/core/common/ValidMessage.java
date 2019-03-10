package com.mapscience.core.common;

/**
 *说明：
 *<p> 报文验证结果封装实体</p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月3日
 *
 */
public class ValidMessage {
	/** 验证结果. */
	protected boolean valid;

	/** 验证消息. */
	protected String message;

	/** 验证数据对象. */
	protected Object data;

	public ValidMessage() {
	}

	public ValidMessage(boolean valid, String message) {
		super();
		this.valid = valid;
		this.message = message;
	}

	/**
	 * 获取 valid
	 * 
	 * @return valid valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * 设置 valid
	 * 
	 * @param valid
	 *            valid
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * 获取 message
	 * 
	 * @return message message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置 message
	 * 
	 * @param message
	 *            message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取 验证数据对象.
	 * 
	 * @return data 验证数据对象.
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置 验证数据对象.
	 * 
	 * @param data
	 *            验证数据对象.
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
