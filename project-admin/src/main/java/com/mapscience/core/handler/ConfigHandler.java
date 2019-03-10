package com.mapscience.core.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapscience.core.base.Item;
import com.mapscience.core.config.Conf;
import com.mapscience.core.config.Confer;
import com.mapscience.core.config.Struct;
import com.mapscience.core.exception.CommonException;
import com.mapscience.core.util.JsonUtil;
import com.mapscience.core.util.common.ObjectUtil;

/**
 * 
 *说明：
 *<p> * <b>系统参数配置管理器, 支持参数值进行解密: BASE64.</b>
 * <b>Description:</b> 主要实现对参数配置文件进行加载、支持对加密的配置参
 *   数值进行简单解密处理;同时主要提供对参数配置持有对象的本地访问.
 *    
 *   特别: 对于参数配置值格式为: $${加密参数值}$$, 如：$${TneGst/MO}$$, 
 *   在业务配置中引用该参数值的自动进行BASE64、SHA等解密, 解密秘钥默认: 0.1234.
 *   </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public final class ConfigHandler implements Handler {

    /** 序列化版本号. */
    private static final long serialVersionUID = 1L;

    /** 逐一缓存持有各模块的参数配置项. */
    protected static final Map<String, Confer> confers = new LinkedHashMap<String, Confer>(0);

    /** 日志记录器. */
    public static final Logger LOGGER = LoggerFactory.getLogger(ConfigHandler.class);

    /**
     * 受保护的构造方法, 防止外部构建对象实例.
     */
    protected ConfigHandler() {
        super();
        LOGGER.debug("instance " + this.toString());
    }

    @Override
    public ConfigHandler init() throws CommonException {
        LOGGER.info(this.toString() + " init.");

        // 依次遍历所有Confer，进行逐一初始化.
        for (Confer confer : confers.values()) {
            confer.init();
        }

        return this;
    }

    @Override
    public ConfigHandler reinit() throws CommonException {
        // 复用初始化方法进行重新加载配置数据.
        // TODO
        return this.init();
    }

    @Override
    public ConfigHandler release() throws CommonException {
        // TODO
        return this;
    }

    /**
     * 依次注册多个参数配置器.
     * 
     * @param confers 多个参数配置器.
     */
    public void setConfers(List<Confer> confers) {
        if (ObjectUtil.isEmpty(confers)) {
            return;
        }

        for (Confer confer : confers) {
            ConfigHandler.confers.put(confer.getCode(), confer);
        }
    }

    /**
     * 获取指定KEY对应的参数配置装载器.
     * 
     * @param key 参数配置装载器唯一标示.
     * @return Configurer
     */
    public static Confer getConfer(String key) {
        return confers.get(key);
    }

    /**
     * 获取所有的参数配置装载器.
     * 
     * @return List<Configurer>
     */
    public static List<Confer> getConfers() {
        List<Confer> loaders = new ArrayList<Confer>(0);
        for (Confer confer : confers.values()) {
            loaders.add(confer);
        }
        return loaders;
    }

    /**
     * 获取所有参数配置.
     * 
     * @return Properties
     */
    public static Map<String, Object> get() {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Confer> list = new ArrayList<Confer>(confers.values());
        for (int i = list.size() - 1; i >= 0; i--) {
            map.putAll(list.get(i).get());
        }
        return map;
    }

    /**
     * 刷新指定配置器数据.
     * 
     * @param clazz
     */
    public static void refresh(Class<?> clazz) {
        // 配置器无效则忽略.
        if (null == clazz) {
            return;
        }

        // 获取对应配置器并进行刷新操作.
        String key = clazz.getName();
        Confer confer = confers.get(key);
        if (null != confer) {
            confer.refresh();
        }
    }

    /**
     * 获取指定KEY对应的参数配置
     * 
     * @param key
     * @return Object
     */
    public static Object get(String key) {
        return get(key, null);
    }
    
    /**
     * 获取指定KEY对应的参数配置指定对象
     * 
     * @param key
     * @return Object
     */
    public static <T> T getBean(String key, Class<T> clazz){
    	Object obj = get(key);
    	if(obj == null){
    		return null;
    	}
    	if(obj instanceof Conf) {
            Conf conf = (Conf) obj;
            // 如果配置有效，并且属于单项配置，则直接返回配置的Value。
            if (Struct.SINGLE == conf._getStruct()) {
                return JsonUtil.toBean(conf.getValue(), clazz);
            }
            if(Struct.LIST == conf._getStruct()){
            	Map<String, Item> items = conf.getItems();
            	JSONObject tempItem = new JSONObject();
            	for (Item item : items.values()) {
            		Object value = item.getValue();
            		// 检查value是否以"{}"或"[]"开头结尾
            		if(value != null && value.toString().startsWith("{") && value.toString().endsWith("}")){
            			value = JsonUtil.toBean(value.toString());
            		} else if(value != null && value.toString().startsWith("[") && value.toString().endsWith("]")){
            			value = JSON.parseArray(value.toString());
            		}
            		tempItem.put(item.getCode(), value);
				}
            	return JsonUtil.toBean(JsonUtil.toJson(tempItem), clazz);
            }
    	} else {
    		return JsonUtil.toBean(JsonUtil.toJson(obj), clazz);
    	}
		return null;
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return Object
     */
    public static Object get(String key, Object _default) {
        for (Confer confer : confers.values()) {
            Object obj = confer.get(key);
            if (null != obj) {
                return obj;
            }
        }
        return _default;
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return Character
     */
    public static Character getChar(String key) {
        return getChar(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return Character
     */
    public static Character getChar(String key, Character _default) {
        String str = getString(key, null);
        // 强制装换为Boolean.
        return ObjectUtil.toChar(str, _default);
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return String
     */
    public static String getString(String key) {
        return getString(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return String
     */
    public static String getString(String key, String _default) {
        Object obj = get(key, _default);
        if (null != obj && obj instanceof Conf) {
            Conf conf = (Conf) obj;
            // 如果配置有效，并且属于单项配置，则直接返回配置的Value。
            if (Struct.SINGLE == conf._getStruct()) {
                return conf.getValue();
            }

            String str = ((Conf) obj).getValue();
            return (null != str ? str : _default);
        }
        if(null == obj){
        	return "";
        }
        return String.valueOf(obj);
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return Boolean
     */
    public static Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return String
     */
    public static Boolean getBoolean(String key, Boolean _default) {
        String str = getString(key, null);
        // 强制装换为Boolean.
        return ObjectUtil.toBoolean(str, _default);
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return Short
     */
    public static Short getShort(String key) {
        return getShort(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return Short
     */
    public static Short getShort(String key, Short _default) {
        String str = getString(key, null);
        // 强制装换为Boolean.
        return ObjectUtil.toShort(str, _default);
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return Integer
     */
    public static Integer getInteger(String key) {
        return getInteger(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return Integer
     */
    public static Integer getInteger(String key, Integer _default) {
        String str = getString(key, null);
        // 强制装换为Boolean.
        return ObjectUtil.toInteger(str, _default);
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return Long
     */
    public static Long getLong(String key) {
        return getLong(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return Long
     */
    public static Long getLong(String key, Long _default) {
        String str = getString(key, null);
        // 强制装换为Boolean.
        return ObjectUtil.toLong(str, _default);
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return Float
     */
    public static Float getFloat(String key) {
        return getFloat(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return Float
     */
    public static Float getFloat(String key, Float _default) {
        String str = getString(key, null);
        // 强制装换为Boolean.
        return ObjectUtil.toFloat(str, _default);
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return Double
     */
    public static Double getDouble(String key) {
        return getDouble(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return Double
     */
    public static Double getDouble(String key, Double _default) {
        String str = getString(key, null);
        // 强制装换为Boolean.
        return ObjectUtil.toDouble(str, _default);
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return BigDecimal
     */
    public static BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return BigDecimal
     */
    public static BigDecimal getBigDecimal(String key, BigDecimal _default) {
        String str = getString(key, null);
        // 强制装换为Boolean.
        return ObjectUtil.toBigDecimal(str, _default);
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return Date
     */
    public static Date getDate(String key) {
        return getDate(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return Date
     */
    public static Date getDate(String key, Date _default) {
        String str = getString(key, null);
        // 强制装换为Boolean.
        return ObjectUtil.toDate(str, _default);
    }

    /**
     * 获取指定KEY对应的参数.
     * 
     * @param key
     * @return Date
     */
    public static Date getDateTime(String key) {
        return getDateTime(key, null);
    }

    /**
     * 获取指定KEY对应的参数, 如果对应KEY不存在, 则返回默认值.
     * 
     * @param key
     * @param _default 默认值.
     * @return Date
     */
    public static Date getDateTime(String key, Date _default) {
        String str = getString(key, null);
        // 强制装换为Boolean.
        return ObjectUtil.toDate(str, _default);
    }

}
