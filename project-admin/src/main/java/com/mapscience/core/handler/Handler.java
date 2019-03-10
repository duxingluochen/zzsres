package com.mapscience.core.handler;

import java.io.Serializable;

import com.mapscience.core.exception.CommonException;
/**
 * 
 *说明：
 *<p>功能、业务模块通用处理器接口 </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public interface Handler extends Serializable {

    /**
     * 初始化方法.
     * 
     * @throws commomException 
     */
	Handler init() throws CommonException;
	
	/**
     * 重新初始化.
     * 
     * @return Handler
     * @throws ComonException
     */
    Handler reinit() throws CommonException;

    /**
     * 就行资源释放.
     * 
     * @return Handler
     * @throws ComonException
     */
    Handler release() throws CommonException;
}
