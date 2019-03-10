package com.mapscience.core.base;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mapscience.core.util.JsonUtil;
/**
 * 
 *说明：
 *<p>实体: 参数配置项. </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public class Item extends Entity {

    /** 序列化版本号. */
    private static final long serialVersionUID = 1L;

    /** 对应数据库的物理表名: SYS_CONF_ITEM. */
    public static final String TABLE_NAME = "SYS_CONF_ITEM";

    // Properties.
    /** 参数配置ID. */
    protected Long configId;

    /** 父节点ID, 默认-1. */
    protected Long parentId;

    /** 唯一代码. */
    protected String code;

    /** 配置名称. */
    protected String name;

    /** 参数配置. */
    protected String value;

    /** 排列顺序. */
    protected Integer queue;

    protected String editor;

    /** 下级节点配置项列表. */
    protected List<Item> children;

    /** 下级节点配置项. */
    protected Map<String, Item> items;

	protected String md5;

    // Constructors.
    public Item() {
        super();
    }

    public Item(Long id) {
        super(id);
    }

    public Item(Long id, Long configId, Long parentId, String code, String name, String value, Integer queue, String remark,
            String editor, Date createTime, Date updateTime) {
    	super(parentId, remark, null, createTime, updateTime, null);
        this.configId = configId;
        this.parentId = parentId;
        this.code = code;
        this.name = name;
        this.value = value;
        this.queue = queue;
    }

    /**
     * 添加子级配置项.
     * 
     * @param item
     */
    public void addItem(Item item) {
        if (null == this.items) {
            this.items = new LinkedHashMap<String, Item>(0);
        }
        this.items.put(item.getCode(), item);
    }

    public void setChildren(List<Item> children) {
        this.children = children;
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

    public Long getConfigId() {
        return configId;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Integer getQueue() {
        return queue;
    }

    public List<Item> getChildren() {
        return this.children;
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