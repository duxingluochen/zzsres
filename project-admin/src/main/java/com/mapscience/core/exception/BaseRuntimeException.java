package com.mapscience.core.exception;

import org.apache.commons.lang.StringUtils;


/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月3日
 *
 */
public abstract class BaseRuntimeException  extends RuntimeException {
	private static final long serialVersionUID = -4452302797597641927L;
	private Message message;
	public BaseRuntimeException() {
		
	}
	public BaseRuntimeException(String arg0) {
		super(arg0);
	}
	public BaseRuntimeException(Throwable arg0) {
		super(arg0);
	}

	public BaseRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}
	/**
	 * 
	 * @param keapBaseEnum
	 * @param message
	 */
	public BaseRuntimeException(Message response,String message){
		super(message);
		this.message=response;
	}
	/**
	 * 获取{@link BaseEnum<Integer>}错误枚举类型
	 * @return {@link BaseEnum<Integer>}
	 */
	public Message getMsg(){
		return this.message;
	}
	
	@Override
	public String getMessage() {
		return StringUtils.defaultIfEmpty(super.getMessage(), this.message.MESSAGE);
	}

}
