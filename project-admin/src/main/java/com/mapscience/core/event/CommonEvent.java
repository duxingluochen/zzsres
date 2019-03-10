package com.mapscience.core.event;

import java.io.Serializable;
import java.util.EventListener;

/**
 * 
 *说明：
 *<p> 基础事件监听定义.</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public abstract class CommonEvent implements EventListener, Runnable, Serializable {

	/** 序列化标识号. */
	private static final long serialVersionUID = 1L;

	/** 事件全局唯一KEY. */
	protected String key;

	/** 事件处理外外部传参. */
	protected Object parmas;

	/** 是否忽略本次事件. */
	protected boolean ignore;

	/**
	 * 
	 * <pre>
	 * <b>构造函数.</b>
	 * <b>Description:</b> 
	 *     
	 * <b>Author:</b> zhouguangyong@typay.me
	 * <b>Date:</b> 2016-4-22 上午09:51:56
	 * @param key 事件唯一KEY,相同key会被覆盖
	 * </pre>
	 */
	public CommonEvent(String key) {
		super();
		this.key = key;
	}

	public abstract void run();

	/**
	 * 获取 key
	 * 
	 * @return key key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * <pre>
	 * <b>获取事件处理参数.</b>
	 * @return
	 * </per>
	 */
	public Object getParmas() {
		return parmas;
	}

	/**
	 * <pre>
	 * <b>设置事件处理时需要的参数.</b>
	 * @param parmas
	 * </per>
	 */
	public void setParmas(Object parmas) {
		this.parmas = parmas;
	}

	/**
	 * 设置 key
	 * 
	 * @param key
	 *            key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 忽略本次事件.
	 */
	public void ignore() {
		this.ignore = true;
	}

	public void recovery() {
		this.ignore = false;
	}
	
	public boolean isEffective() {
		return !this.ignore;
	}

}
