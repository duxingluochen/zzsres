package com.mapscience.core.config;

import java.util.Map;

/**
 * 
 *说明：
 *<p>参数配置服务接口. </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public interface Confer {

    /**
     * 初始化.
     * 
     * @return Confer
     */
    Confer init();

    /**
     * 刷新配置.
     * 
     * @return boolean
     */
    boolean refresh();

    /**
     * 获取所有参数的清单.
     * 
     * @return Map<String, Object>
     */
    Map<String, Object> get();

    /**
     * 获取指定参数配置.
     * 
     * @param key 指定参数配置的key.
     * @return Object
     */
    Object get(String key);

    /**
     * 获取唯一代码.
     * 
     * @return String
     */
    String getCode();

}
