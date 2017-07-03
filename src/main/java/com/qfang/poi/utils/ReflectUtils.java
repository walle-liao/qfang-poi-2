package com.qfang.poi.utils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * 
 * 反射工具类，封装一些反射方法
 * 
 * @author liaozhicheng
 * @since 1.0
 */
public class ReflectUtils {
	
	public static Field getField(Class<?> type, String fieldName) {
		try {
			return type.getField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Field[] getAllDeclaredFields(Class<?> type) {
		return recursionFields(type).toArray(new Field[0]);
	}
	
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
				Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}
	
	public static void setField(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		}
		catch (IllegalAccessException ex) {
			throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": "
					+ ex.getMessage());
		}
	}
	
	/**
	 * 获取某个类包括其所有父类中私有属性
	 * 
	 * @param type
	 *            类型
	 * @param filters
	 *            需要过滤的属性
	 * @return
	 */
	public static Field[] getAllDeclaredFields(Class<?> type, String[] filters) {
		List<Field> fieldList = recursionFields(type);
		
		// 过滤掉某些不想要的属性
		if (filters != null && filters.length > 0) {
			for (String filter : filters) {
				Iterator<Field> fieldIterator = fieldList.iterator();
				while (fieldIterator.hasNext()) {
					Field field = fieldIterator.next();
					if (field.getName().equals(filter))
						fieldIterator.remove();
				}
			}
		}
		return fieldList.toArray(new Field[0]);
	}
	
	
	public static Field[] getAllDeclaredFields(Class<?> type, Class<? extends Annotation> filter) {
		List<Field> fieldList = recursionFields(type);
		
		if (filter != null) {
			Iterator<Field> fieldIterator = fieldList.iterator();
			while (fieldIterator.hasNext()) {
				Field field = fieldIterator.next();
				Annotation an = field.getAnnotation(filter);
				if (an != null) {
					fieldIterator.remove();
				}
			}
		}
		return fieldList.toArray(new Field[fieldList.size()]);
	}
	
	
	/**
	 * 获取指定类及其父类中某一注解标识的所有属性
	 * 
	 * @param type
	 * @param specify 指定注解
	 * @return
	 */
	public static Field[] getSpecified(Class<?> type, Class<? extends Annotation> specify) {
		List<Field> fieldList = recursionFields(type);
		
		if (specify != null) {
			Iterator<Field> fieldIterator = fieldList.iterator();
			while (fieldIterator.hasNext()) {
				Field field = fieldIterator.next();
				if(!field.isAnnotationPresent(specify)) {
					fieldIterator.remove();
				}
			}
		}
		return fieldList.toArray(new Field[fieldList.size()]);
	}
	
	
	private static List<Field> recursionFields(Class<?> type) {
		Validate.notNull(type);
		
		Class<?> clazz = type;
		List<Field> fieldList = new ArrayList<Field>();
		while (clazz != Object.class) {
			fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		return fieldList;
	}
	
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Ignore {
		
	}

}
