package com.mapscience.core.exception;

/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月3日
 *
 */
public class Message {
	public Integer CODE;

	public String MESSAGE;

	/**
	 * @param code
	 * @param message
	 */
	public Message(Integer code, String message) {
		super();
		this.CODE = code;
		this.MESSAGE = message;
	}

}
