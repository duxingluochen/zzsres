package com.mapscience.core.exception;

/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月3日
 *
 */
public class TypayRuntimeException extends BaseRuntimeException {
private static final long serialVersionUID = 1L;
	
	public TypayRuntimeException(Message response, String string) {
		super(response,string);
	}
	/**
	 * 默认成功
	 * @return
	 */
	public static TypayRuntimeException newInstance(){
		return new TypayRuntimeException(new Message(200, "处理成功"),"");
	}
	/**
	 * 自定义是错误类
	 * @param keapBaseEnum
	 * @return
	 */
	public static TypayRuntimeException newInstance(Message response){
		return new TypayRuntimeException(response,"");
	}
	/**
	 * 自定义是错误类
	 * @param keapBaseEnum
	 * @param message
	 * @return
	 */
	public static TypayRuntimeException newInstance(Message response,String message){
		return new TypayRuntimeException(response,message);
	}
}
