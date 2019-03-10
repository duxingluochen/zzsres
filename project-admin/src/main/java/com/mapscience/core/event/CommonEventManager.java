package com.mapscience.core.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mapscience.core.annotation.Event;
import com.mapscience.core.handler.ConfigHandler;
import com.mapscience.core.handler.LogHandler;
import com.mapscience.core.handler.LogUtil;
import com.mapscience.core.log.Logger;
import com.mapscience.core.util.ClassUtil;
import com.mapscience.core.util.IocUtil;
import com.mapscience.core.util.JsonUtil;
import com.mapscience.core.util.common.ObjectUtil;
import com.mapscience.core.util.common.StringUtil;
import com.mapscience.core.util.thread.MultiThreadPool;

/**
 * 
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public abstract class CommonEventManager {

	private static final Logger LOGGER = LogHandler.getLogger("common.event");

	protected static Map<String, CommonEvent> events = new HashMap<String, CommonEvent>();

	protected static ThreadLocal<CommonEvent> local = new ThreadLocal<CommonEvent>();

	// 初始化加载
	public static void initEvent() {

		String packageName = ConfigHandler.getString("event.package.name");
		if (ObjectUtil.isEmpty(packageName)) {
			return;
		}
		// 获取扫描包配置
		String[] packageNames = StringUtil.delimitedToArray(packageName);
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (String _packageName : packageNames) {
			classes.addAll(ClassUtil.getClass(_packageName));
		}

		Map<String, Event> events = new HashMap<String, Event>();
		for (Class<?> clazz : classes) {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				Annotation[] annotations = method.getAnnotations();
				for (Annotation annotation : annotations) {
					// 判断注解是否是自定事件注解类型
					if (annotation.annotationType() == Event.class) {
						Event event = (Event) annotation;
						String key = event.key();
						// 如果已经存在KEY,直接抛出异常
						if (events.containsKey(key)) {
							throw new Error(JsonUtil.toJson(event) + " " + key + " already exists.");
						}
						// 获取监听
						Class<? extends CommonEvent> eventClazz = event.envet();
						CommonEvent customEvent = null;
						// 日志消息
						StringBuffer info = new StringBuffer(clazz.getName() + "." + method.getName());
						info.append(" add event->key:").append(key).append(", ");
						try {
							customEvent = ClassUtil.instance(eventClazz, key);
							// 如果不是EmptyEventListenner类型监听,加入监听管理器里面
							if (!(customEvent instanceof EmptyEvent)) {
								customEvent.setKey(key);
								CommonEventManager.add(customEvent);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						events.put(clazz.getName() + "." + method.getName(), event);

						info.append(" event:").append(eventClazz.getSimpleName());
						LOGGER.info(200, LogUtil.PREFIX2 + info.toString());
					}
				}
			}
		}
	}

	public static void add(CommonEvent event) {
		CommonEventManager.events.put(event.getKey(), event);
	}

	/**
	 * 移除事件
	 * 
	 * @param event
	 *            DoorListener
	 */
	@SuppressWarnings("unlikely-arg-type")
	public static void remove(CommonEvent event) {
		if (event == null) {
			return;
		}
		CommonEventManager.events.remove(event);
	}

	/**
	 * <pre>
	 * <b>手动触发执行事件.</b>
	 * @param key
	 * </per>
	 */
	public static void trigger(String key) {
		CommonEvent listenner = CommonEventManager.events.get(key);
		if (null == listenner) {
			return;
		}
		IocUtil.getBean(MultiThreadPool.class).exec(listenner);
	}

	/**
	 * <pre>
	 * <b>手动触发执行事件.</b>
	 * @param key 事件KEY
	 * @param param 事件参数
	 * </per>
	 */
	public static void trigger(String key, Object param) {
		CommonEvent listenner = CommonEventManager.events.get(key);
		if (null == listenner) {
			return;
		}
		listenner.setParmas(param);
		IocUtil.getBean(MultiThreadPool.class).exec(listenner);
	}

	/**
	 * <pre>
	 * <b>获取系统所有事件.</b>
	 * @return
	 * </per>
	 */
	public static Map<String, CommonEvent> getAll() {
		return events;
	}

	/**
	 * <pre>
	 * <b>根据KEY获取指定事件.</b>
	 * @param key
	 * @return
	 * </per>
	 */
	public static CommonEvent get(String key) {
		// CommonEvent event = local.get();
		// if(null != event){
		// return event;
		// }
		CommonEvent event = events.get(key);
		// 此处深度克隆一个事件,防止调用时对源定义的更改。
		CommonEvent cloneEvent = ObjectUtil.clone(event);
		local.set(cloneEvent);
		return cloneEvent;
	}

	/**
	 * <pre>
	 * <b>获取当前线程的事件.</b>
	 * @param key
	 * @return
	 * </per>
	 */
	public static CommonEvent getCurrentEvent(String key) {
		CommonEvent event = local.get();
		if (null != event) {
			return event;
		}
		event = events.get(key);
		// 此处深度克隆一个事件,防止调用时对源定义的更改。
		CommonEvent cloneEvent = ObjectUtil.clone(event);
		local.set(cloneEvent);
		return cloneEvent;
	}
	
	/**
	 * <pre>
	 * <b>忽略事件触发.</b>
	 * @param eventKey 事件KEY
	 * </per>
	 */
	public static void ignore(String eventKey) {
		CommonEvent event = getCurrentEvent(eventKey);
		if(event != null){
			event.ignore();
		}
	}

}
