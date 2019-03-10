package com.mapscience.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mapscience.core.event.CommonEvent;
import com.mapscience.core.event.EmptyEvent;

/**
 * 
 *说明：
 *<p> 自定义事件类型、事件执行方式和事件处理器</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Event {

	/** 全局事件唯一KEY */
	String key();
	/**
	 * 事件类型,默认 {@link EventType#after}.<br>
	 * 
	 * @see {@link com.framework.event.annotation.Event.EventType}
	 */
	EventType type() default EventType.after;

	/** 是否同步执行,true=是;false=否,默认false. */
	boolean sync() default false;

	/** 自定义事件处理器. */
	Class<? extends CommonEvent> envet() default EmptyEvent.class;

	enum EventType {
		/** 前置模式,在方法开始之前执行. */
		before,
		/** 后置置模式,在方法开始之前执行. */
		after;
	}
}
