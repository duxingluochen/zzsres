package com.mapscience.core.config;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mapscience.core.base.Entity;
import com.mapscience.core.base.Item;
import com.mapscience.core.util.JsonUtil;


/**
 * 
 *说明：
 *<p> 实体: 参数配置.</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public class Conf extends Entity {

    /** 序列化版本号. */
    private static final long serialVersionUID = 1L;

    /** 对应数据库的物理表名: SYS_CONF. */
    public static final String TABLE_NAME = "SYS_CONF";

    // Properties.
    /** 代码. */
    protected String code;

    /** 名称. */
    protected String name;

    /** 结构, 0:单项结构(默认); 1:列表结构; 2:树形结构. */
    protected Struct struct;

    /** 配置值. */
    protected String value;
    
    protected String editor;
    
    protected String md5;

    /** 多个配置项. */
    protected Map<String, Item> items;

    // Constructors.
    public Conf() {
        super();
    }

    public Conf(Long id) {
        super(id);
    }

    public Conf(Long id, String code, String name, Integer struct, String value, String remark, String editor, Date createTime,
            Date updateTime) {
    	super(id, remark, null, createTime, updateTime, null);	
        this.code = code;
        this.name = name;
        this.struct = Struct.format(struct);
        this.value = value;
    }

    /**
     * 添加配置项.
     * 
     * @param item
     */
    public void addItem(Item item) {
        if (null == this.items) {
            this.items = new LinkedHashMap<String, Item>(0);
        }
        this.items.put(item.getCode(), item);
    }

    // Methods
    /**
     * 获取结构枚举.
     * 
     * @return Struct
     */
    public Struct _getStruct() {
        return this.struct;
    }

    // Property accessors.
    public Long getId() {
        return id;
    }

    public String getRemark() {
        return remark;
    }

    public Integer getStatus() {
        return status;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getStruct() {
        return (null != this.struct ? this.struct.getKey() : null);
    }

    public String getValue() {
        return value;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public String getEditor() {
		return editor;
	}
    
	public String getMd5() {
		return md5;
	}

	@Override
    public String toString() {
        return super.toString() + " " + JsonUtil.toJson(this);
    }

}
