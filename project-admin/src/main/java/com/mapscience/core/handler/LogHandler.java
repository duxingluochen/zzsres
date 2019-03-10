package com.mapscience.core.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mapscience.core.exception.CommonException;
import com.mapscience.core.log.Logger;

/**
 * 
 *说明：
 *<p>日志记录管理器. </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public class LogHandler implements Handler {

    // 序列化版本标示.
    private static final long serialVersionUID = 1L;

    // 底层日志记录器的默认类型为： LogType.File.
    protected static final LogModel DEFAULT_MODEL = LogModel.FileLog;

    // 预定义私有Logger.
    protected List<Logger> loggers;

    // 当前日志记录器持有的所有记录器实例.
    protected static final Map<String, Logger> _loggers = new HashMap<String, Logger>();

    // 日志记录器.
    public static final Logger logger = LogHandler.getLogger("common");

    /**
     * 受保护的构造方法, 防止外部构建对象实例.
     */
    protected LogHandler() {
        super();
    }

    /**
     * 初始化方法.
     * 
     * @throws commomException 
     */
    @Override
    public LogHandler init() throws CommonException {
        logger.info(LogUtil.LINE);
        logger.info(LogUtil.PREFIX2 + this.getClass().getSimpleName() + " init ...");
        logger.info(LogUtil.PREFIX3 + LogUtil.debugInfo("loggers", this.loggers));
        return this;
    }
    
    /* (non-Javadoc)
     * @see com.framework.core.Handler#reinit()
     */
    @Override
    public Handler reinit() throws CommonException {
        return this.init();
    }
    
    /* (non-Javadoc)
     * @see com.framework.core.Handler#release()
     */
    @Override
    public Handler release() throws CommonException {
    	return this;
    }
    
    /**
     * 获取指定名称对应的Logger记录服务实例.
     * 
     * @param clazz logger具体的类.
     * @return LogService
     */
    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getClass().getName());
    }

    /**
     * 获取指定名称对应的Logger记录服务实例.
     * 
     * @param name logger具体的名称.
     * @return LogService
     */
    public static Logger getLogger(String name) {
        if (!_loggers.containsKey(name)) {
            synchronized (_loggers) {
                if (!_loggers.containsKey(name)) {
                    Logger tl = new FileLogger(name);
                    _loggers.put(name, tl);
                    return tl;
                }
            }
        }

        return _loggers.get(name);
    }

    /**
     * 设置 预初始化Logger.
     * @param loggers 预初始化Logger.
     */
    public void setLoggers(List<Logger> loggers) {
        this.loggers = loggers;
    }

    /**
     * <pre>
     * <b>枚举: 日志记录器类型.</b>
     * <b>Description:</b>
     *   
     * <b>Author:</b> zhouguangyong@typay.me
     * <b>Date:</b> 2014-01-01 上午10:00:01
     * <b>Copyright:</b> Copyright ©2006-2015 typay.me Technology Co., Ltd. All rights reserved.
     * <b>Changelog:</b>
     *   Ver   Date                  Author              Detail
     *   ----------------------------------------------------------------------
     *   1.0   2014-01-01 10:00:01   zhouguangyong@typay.me
     *         new file.
     * </pre>
     */
    protected enum LogModel {
        /**
         * File 实现方式.
         */
        FileLog,
        /**
         * DB 数据库实现方式.
         */
        DBLog;
    }
}
