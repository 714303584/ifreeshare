package com.ifreeshare.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ObjectUtil {

	/**
	 * map 转 Bean
	 * 
	 * @param mpFrom
	 * @param objTo
	 * @return
	 */
	public static Object mapToBean(Map mpFrom, Object objTo) {
		Object[] objKeys = mpFrom.keySet().toArray();
		String strFieldName = "";
		try {
			for (Object objkey : objKeys) {
				strFieldName = objkey.toString();
				Field objField = objTo.getClass().getDeclaredField(strFieldName.toLowerCase());
				objField.setAccessible(true);
				objField.set(objTo, mpFrom.get(strFieldName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objTo;
	}

	/**
	 * map 转 Bean
	 * 
	 * @param map
	 * @param cls
	 * @return
	 */
	public static Object map2Bean(Map map, Class cls) {
		Object obj = null;
		try {
			obj = cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 取出bean里的所有方法
		Method[] methods = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			// 取方法名
			String method = methods[i].getName();
			// 取出方法的类型
			Class[] cc = methods[i].getParameterTypes();
			if (cc.length != 1)
				continue;

			// 如果方法名没有以set开头的则退出本次for
			if (method.indexOf("set") < 0)
				continue;
			// 类型
			String type = cc[0].getSimpleName();

			try {
				// 转成小写
				// Object value = method.substring(3).toLowerCase();
				Object value = method.substring(3, 4).toLowerCase() + method.substring(4);
				System.out.println("value == " + value);
				// 如果map里有该key
				if (map.containsKey(value) && map.get(value) != null) {
					// 调用其底层方法
					setValue(type, map.get(value), i, methods, obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	private static void setValue(String type, Object value, int i, Method[] method, Object bean) {
		if (value != null && !value.equals("")) {
			try {
				if (type.equals("String")) {
					// 第一个参数:从中调用基础方法的对象 第二个参数:用于方法调用的参数
					method[i].invoke(bean, new Object[] { value });
				} else if (type.equals("int") || type.equals("Integer")) {
					method[i].invoke(bean, new Object[] { new Integer("" + value) });
				} else if (type.equals("double") || type.equals("Double")) {
					method[i].invoke(bean, new Object[] { new Double("" + value) });
				} else if (type.equals("float") || type.equals("Float")) {
					method[i].invoke(bean, new Object[] { new Float("" + value) });
				} else if (type.equals("long") || type.equals("Long")) {
					method[i].invoke(bean, new Object[] { new Long("" + value) });
				} else if (type.equals("boolean") || type.equals("Boolean")) {
					method[i].invoke(bean, new Object[] { Boolean.valueOf("" + value) });
				} else if (type.equals("BigDecimal")) {
					method[i].invoke(bean, new Object[] { new BigDecimal("" + value) });
				} else if (type.equals("Date")) {
					Date date = null;
					if (value.getClass().getName().equals("java.util.Date")) {
						date = (Date) value;
					} else {
						String format = ((String) value).indexOf(":") > 0 ? "yyyy-MM-dd hh:mm:ss" : "yyyy-MM-dd";
						SimpleDateFormat sf = new SimpleDateFormat();
						sf.applyPattern(format);
						date = sf.parse((String) (value));
					}
					if (date != null) {
						method[i].invoke(bean, new Object[] { date });
					}
				} else if (type.equals("byte[]")) {
					method[i].invoke(bean, new Object[] { new String(value + "").getBytes() });
				}
			} catch (Exception e) {
				System.out.println("将linkHashMap 或 HashTable 里的值填充到javabean时出错,请检查!");
				e.printStackTrace();
			}
		}
	}
	
	

}
