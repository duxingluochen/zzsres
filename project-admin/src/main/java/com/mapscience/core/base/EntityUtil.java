package com.mapscience.core.base;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

import com.mapscience.core.util.ClassUtil;
import com.mapscience.core.util.MD5Util;
import com.mapscience.core.util.UUIDUtil;

/**
 * 
 *说明：
 *<p> 为实体设置相应的ID、创建时间、修改时间、修改用户、有效标示、MD5属性.</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public abstract class EntityUtil {

	/**
	 * 为多个实体模型设置通用的属性值.
	 * 
	 * @param entitys
	 *            多个目标实例
	 */
	public static void setAttr(Entity... entitys) {
		int len = 0;
		if (null == entitys || 0 == (len = entitys.length)) {
			return;
		}

		// 记录编辑者
		String editor = /*TokenHolder.getAccount()*/ null;
		// TODO 该处理方式存在缺陷.
		editor = (null == editor ? "sys" : editor);
		// 获取当前系统时间
		long currTime = System.currentTimeMillis();
		// 为记录批量生成新ID.
		long[] ids = UUIDUtil.ids(len);
		for (int i = 0; i < len; i++) {
			Entity model = entitys[i];
			setAttr(model, ids[i], currTime, editor);
		}
	}

	/**
	 * 为多个实体模型设置通用的属性值.
	 * 
	 * @param entitys
	 *            多个目标实例
	 */
	public static <T extends Entity> void setAttr(Collection<T> entitys) {
		int size = 0;
		if (null == entitys || 0 == (size = entitys.size())) {
			return;
		}

		// 记录编辑者
		String editor = /*TokenHolder.getAccount()*/ null;
		// TODO 该处理方式存在缺陷.
		editor = (null == editor ? "sys" : editor);

		// 获取当前系统时间
		long currTime = System.currentTimeMillis();
		// 为记录批量生成新ID.
		long[] ids = UUIDUtil.ids(size);

		int i = 0;
		for (Entity model : entitys) {
			setAttr(model, ids[i], currTime, editor);
			i++;
		}
	}

	/**
	 * 设置ID、系统时间、编辑者
	 * 
	 * @param entity
	 * @param id
	 * @param currTime
	 * @param editor
	 */
	public static void setAttr(Entity entity, long id, long currTime, String editor) {
		// 如果实体模型无效，则不执行
		if (null == entity) {
			return;
		}

		// 如果实体无唯一ID，则进行设置ID、创建时间、有效标示、MD5属性
		if (null == entity.getId()) {
			entity.setId(id);
			entity.setCreateTime(currTime);
		}
		// 如果实体无有效标识,统一设置为1
		if(null == entity.getValid()){
			entity.setValid(1);
		}
		
		// 总是设置记录编辑者和修改时间.
		entity.setUpdateTime(currTime);
	}
	
	/**
	 * 构建实体字段的MD5值
	 * @param entity
	 * @return
	 */
	public static String buildMd5(Entity entity){
		StringBuffer values = new StringBuffer();
		// 获取实体字段
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 获取非静态字段取MD5
			if(!Modifier.isStatic(field.getModifiers())){
				Object value = ClassUtil.getValue(entity, field.getName());
				if(null != value){
					values.append(value);
				}
			}
		}
		return MD5Util.encrypt(values.toString());
	}
	
}
