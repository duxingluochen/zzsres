package com.mapscience.core.log;

import java.io.Serializable;

import com.mapscience.core.exception.CommonException;

/**
 * 
 *说明：
 *<p>日志记录器. </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public abstract class Logger implements Serializable {

    // 序列化版本标示.
    private static final long serialVersionUID = 1L;

    // 日志记录的名称.
    protected String name;

    protected static final String DEFAULT_LOG_NAME = "common";

    public Logger() {
        this(DEFAULT_LOG_NAME);
    }

    public Logger(String name) {
        super();
        this.name = name;
    }

    // === ERROR ===============================================
    public abstract boolean isErrorEnabled();

    public abstract void error(Object data);

    public abstract void error(int code, String message);

    public abstract void error(int code, String message, Object data, Throwable cause);

    public abstract void error(CommonException cause);

    // === WARN ================================================
    public abstract boolean isWarnEnabled();

    public abstract void warn(Object data);

    public abstract void warn(int code, String message);

    public abstract void warn(int code, String message, Object data, Throwable cause);

    public abstract void warn(CommonException cause);

    // === INFO ================================================
    public abstract boolean isInfoEnabled();

    public abstract void info(Object data);

    public abstract void info(int code, String message);

    public abstract void info(int code, String message, Object data, Throwable cause);

    public abstract void info(CommonException cause);

    // === DEBUG ===============================================
    public abstract boolean isDebugEnabled();

    public abstract void debug(Object data);

    public abstract void debug(int code, String message);

    public abstract void debug(int code, String message, Object data, Throwable cause);

    public abstract void debug(CommonException cause);

    @Override
    public String toString() {
        return this.name;
    }

}
