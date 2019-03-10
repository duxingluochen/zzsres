package com.mapscience.core.exception;

/**
 * 
 *说明：
 *<p> 线程操作异常基类.</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public class ThreadException extends Exception {

    private static final long serialVersionUID = 1L;

    /** 错误代码前缀. */
    public static final String PRIFIX = "THREAD_";

    /** 默认错误代码. */
    public static final String UNKNOWN = "THREAD_UNKNOWN";

    /**
     * 构造方法.
     */
    public ThreadException() {
        super(UNKNOWN);
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     */
    public ThreadException(String code) {
        super(code);
    }

    /**
     * 构造方法.
     * 
     * @param throwable 错误堆栈信息.
     */
    public ThreadException(Throwable throwable) {
        super(UNKNOWN, throwable);
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     * @param throwable 错误堆栈信息.
     */
    public ThreadException(String code, Throwable throwable) {
        super(code, throwable);
    }

}
