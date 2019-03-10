package com.mapscience.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 流程类型分类（也可以让表单关联）
 * </p>
 *
 * @author ${author}
 * @since 2019-02-28
 */
@TableName("oa_pro_type")
public class ProType extends Model<ProType> {

    private static final long serialVersionUID = 1L;

    /**
     * 流程类型Id
     */
    @TableId("type_id")
    private String typeId;
    /**
     * 流程类型名
     */
    @TableField("type_name")
    private String typeName;
    /**
     * 父id默认是0 
     */
    @TableField("parent_id")
    private String parentId;
    /**
     * 状态 1是启用 2不能增加 3不能查询
     */
    private Integer status;
    
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    
    

    public ProType() {
		super();
	}
    

	public ProType(String parentId) {
		super();
		this.parentId = parentId;
	}


	public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.typeId;
    }
    

    public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
    public String toString() {
        return "ProType{" +
        "typeId=" + typeId +
        ", typeName=" + typeName +
        ", parentId=" + parentId +
        ", sort=" + sort +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
