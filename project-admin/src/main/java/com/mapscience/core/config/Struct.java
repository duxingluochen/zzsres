package com.mapscience.core.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.mapscience.core.util.JsonUtil;
/**
 * 
 *说明：
 *<p> 枚举字典: 参数配置数据结构.</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public enum Struct {

    /** 1, "单项" */
    SINGLE(1, "单项"),

    /** 2, "列表" */
    LIST(2, "列表"),

    /** 3, "树形" */
    TREE(3, "树形");

    /** Key. */
    private int key;

    /** 名称. */
    private String label;

    /** 预序列化为JSON格式的MAP形式的字典. */
    private static final String JSON_MAP = JsonUtil.toJson(toMap());

    /** 预序列化为JSON格式的二维数组形式的字典. */
    private static final String JSON_ARRAY = JsonUtil.toJson(toArray());

    /**
     * 构造方法.
     * 
     * @param key
     * @param label
     */
    private Struct(int key, String label) {
        this.key = key;
        this.label = label;
    }

    /**
     * 转换对应枚举.
     * 
     * @param key
     * @return Struct
     */
    public static Struct format(Integer key) {
        if (null != key) {

            for (Struct item : Struct.values()) {
                if (key == item.key) {
                    return item;
                }
            }
        }

        return null;
    }

    /**
     * 获取清单.
     * 
     * @return Map<Object, String>
     */
    public static Map<Object, String> toMap() {
        Map<Object, String> map = new LinkedHashMap<Object, String>();

        for (Struct item : Struct.values()) {
            map.put(item.key, item.label);
        }

        return map;
    }

    /**
     * 获取二维数组.
     * 
     * @return Object[]
     */
    public static Object[] toArray() {
        Struct[] values = Struct.values();
        Object[] array = new Object[values.length];

        for (int i = 0; i < values.length; i++) {
            array[i] = new Object[] { values[i].key, values[i].label };
        }

        return array;
    }

    /**
     * 获取Json格式的Map.
     * 
     * @return String
     */
    public static String jsonMap() {
        return JSON_MAP;
    }

    /**
     * 获取Json格式的二维数组.
     * 
     * @return String
     */
    public static String jsonArray() {
        return JSON_ARRAY;
    }

    public int getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

}
