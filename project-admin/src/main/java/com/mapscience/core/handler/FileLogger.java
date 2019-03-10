package com.mapscience.core.handler;

import org.slf4j.LoggerFactory;

import com.mapscience.core.exception.CommonException;
import com.mapscience.core.util.FileUtil;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

/**
 * 
 *说明：
 *<p>以物理文件模式存储的日志记录器 </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public class FileLogger extends com.mapscience.core.log.Logger {

    // 序列化版本标示.
    private static final long serialVersionUID = 1L;

    // slf4j版本的文件记录器.
    protected Logger logger;

    // 日志默认的输出级别.
    public static final Level DEFAULT_LEVEL = Level.INFO;

    // 默认的日志格式：[%d{yyyy-MM-dd HH:mm:ss.SSS} %5p %6c] %m%n
    public static final String DEFAULT_LAYOUT = "[%d{yyyy-MM-dd HH:mm:ss.SSS}][%5p][%11c] %m%n";

    // 默认日志文件生成文件的重命名表达式：yyyy-MM-dd.
    public static final String DEFAULT_DAILYROLLING_EXPRESSION = "yyyy-MM-dd";

    // 将日志文件分别输出到控制台和相应的日志文件.
    public static final boolean DEFAULT_ADDITIVITY = false;

    // 默认控制台输出信息的类别：System.out.
    public static final String DEFAULT_OUTPUT_TYPE = "System.out";

    public FileLogger() {
        this(DEFAULT_LOG_NAME);
    }

    public FileLogger(String name) {
        super();
        this.logger = (Logger) LoggerFactory.getLogger(name);
        this.logger.setLevel(DEFAULT_LEVEL);
       
        PatternLayoutEncoder _layout = new PatternLayoutEncoder();
        _layout.setContext(this.logger.getLoggerContext());
        _layout.setPattern(DEFAULT_LAYOUT);  
        _layout.start();
        try {
        	String defaultPath = ConfigHandler.getString("log.dir.root", FileUtil.BASE_PATH + "/../../logs/");
        	// 文件日志路径
           String fileName = defaultPath + name + ".log";
        	FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
            fileAppender.setFile(fileName);  
            fileAppender.setContext(this.logger.getLoggerContext());  
            fileAppender.setEncoder(_layout);  
            fileAppender.start();  
            this.logger.addAppender(fileAppender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // === ERROR ===============================================
    @Override
    public boolean isErrorEnabled() {
        return true;// logger.isErrorEnabled();
    }

    @Override
    public void error(Object data) {
        logger.error(String.valueOf(data));
    }

    @Override
    public void error(int code, String message) {
        logger.error(code + ":" + message);
    }

    @Override
    public void error(int code, String message, Object data, Throwable cause) {
        logger.error(code + ":" + message + "->" + data, cause);
    }

    @Override
    public void error(CommonException cause) {
        logger.error(cause.getCode() + ":" + cause.getMessage() + "->" + cause.getData(), cause);
    }

    // === WARN ================================================
    @Override
    public boolean isWarnEnabled() {
        return true; // logger.isWarnEnabled();
    }

    @Override
    public void warn(Object data) {
        logger.warn(String.valueOf(data));
    }

    @Override
    public void warn(int code, String message) {
        logger.warn(code + ":" + message);
    }

    @Override
    public void warn(int code, String message, Object data, Throwable cause) {
        logger.warn(code + ":" + message + "->" + data, cause);
    }

    @Override
    public void warn(CommonException cause) {
		logger.warn(cause.getCode() + ":" + cause.getMessage() + "->" + cause.getData(), cause);
    }

    // === INFO ================================================
    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(Object data) {
    	if(logger.isInfoEnabled()){
    		logger.info(String.valueOf(data));
    	}
    }

    @Override
    public void info(int code, String message) {
    	if(logger.isInfoEnabled()){
    		logger.info(code + ":" + message);
    	}
    }

    @Override
    public void info(int code, String message, Object data, Throwable cause) {
    	if(logger.isInfoEnabled()){
    		logger.info(code + ":" + message + "->" + data, cause);
    	}
    }

    @Override
    public void info(CommonException cause) {
    	if(logger.isInfoEnabled()){
    		logger.info(cause.getCode() + ":" + cause.getMessage() + "->" + cause.getData(), cause);
    	}
    }

    // === DEBUG ===============================================
    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(Object data) {
    	if(logger.isDebugEnabled()){
    		logger.debug(String.valueOf(data));
    	}
    }

    @Override
    public void debug(int code, String message) {
    	if(logger.isDebugEnabled()){
    		logger.debug(code + ":" + message);
    	}
    }

    @Override
    public void debug(int code, String message, Object data, Throwable cause) {
    	if(logger.isDebugEnabled()){
    		logger.debug(code + ":" + message + "->" + data, cause);
    	}
    }

    @Override
    public void debug(CommonException cause) {
    	if(logger.isDebugEnabled()){
    		logger.debug(cause.getCode() + ":" + cause.getMessage() + "->" + cause.getData(), cause);
    	}
    }

}
