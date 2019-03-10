package com.mapscience.core.base;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.mapscience.core.util.common.ObjectUtil;


/**
 * 
 *说明：
 *<p> 主要实现了Serializable、Comparable、Cloneable,
 *    并且扩展复写对象的自定义clone、toString的实现.</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public class Entity implements Serializable, Comparable<Entity>, Cloneable {

	/** 序列化版本号. */
	private static final long serialVersionUID = 1L;

	// Properties.
	/** 主键. UidUtil.id() */
	protected Long id;

	/** 摘要; 备注. */
	protected String remark;

	/** 状态, 0:禁用(默认); 1:启用. */
	protected Integer status;

	/** 创建时间, 仅新增时设置, 以后永久不变, 默认:sysdate. */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createTime;

	/** 修改时间, 每次修改记录时变更, 默认:sysdate. */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date updateTime;
	
	/** 有效标示, 0:否; 1:是(默认). */
	protected Integer valid;

	// Constructors.
	public Entity() {
		super();
	}

	public Entity(Long id) {
		super();
		this.id = id;
	}

	public Entity(Long id, String remark, Integer status, Date createTime, Date updateTime, Integer valid) {
		super();
		this.id = id;
		this.remark = remark;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.valid = valid;
	}

	// Property accessors.
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public void setCreateTime(long createTime) {
		this.createTime = (new Date(createTime));
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUpdateTime(long modifyTime) {
		this.updateTime = (new Date(modifyTime));
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	/**
	 * <pre>
	 * <b>对实体的基础属性赋值</b>
	 * 
	 * 调用实体辅助工具为实体模型设置通用的属性值.
	 * </pre>
	 * 
	 */
	public void voluationBaseAttrs() {
		EntityUtil.setAttr(this);
	}

	/**
	 * 判断对象是否为同一个对象
	 * 
	 * @return int
	 */
	@Override
	public int compareTo(final Entity obj) {
		return equals(obj) ? 1 : 0;
	}

	/**
	 * 克隆对象(浅度克隆).
	 * 
	 * @param deep
	 *            是否深度克隆标示, true:深度克隆;false：浅度克隆.
	 * @return Object 对象的副本.
	 */
	protected Object clone(boolean deep) throws CloneNotSupportedException {
		if (deep) {
			return ObjectUtil.clone(this);
		} else {
			return super.clone();
		}
	}

	/**
	 * GC回收时的回调处理.
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 重写父类对象的toString实现.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return super.toString();
	}

}
