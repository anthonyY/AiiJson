package com.aiitec.openapi.json;

import com.aiitec.openapi.json.annotation.JSONField;
import com.aiitec.openapi.json.enums.CombinationType;
import com.aiitec.openapi.json.utils.JsonUtils;
import com.aiitec.openapi.json.utils.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class DesCombination {
	
	public static <T> void desCombinationArray2(JSONArray jsonArray, T t,  Class<?> childClass, Field field,
			CombinationType combinationType) throws InstantiationException,
			IllegalAccessException {
		List<Object> arrays = new ArrayList<Object>();

		if (jsonArray != null) {
			String fieldName = field.getName();
			

			for (int i = 0; i < jsonArray.length(); i++) {
				if (JsonUtils.isCommonField(childClass)) {

					// 集合里是常用数据类型
					if (childClass == int.class || childClass == Integer.class) {
						arrays.add(jsonArray.optInt(i, 0));
					} else if (childClass == float.class
							|| childClass == Float.class
							|| childClass == double.class
							|| childClass == Double.class) {
						arrays.add(jsonArray.optDouble(i, 0));
					} else if (childClass == String.class
							|| childClass == char.class) {
						arrays.add(jsonArray.optString(i, ""));
					} else if (childClass == long.class
							|| childClass == Long.class) {
						arrays.add(jsonArray.optLong(i, 0));
					} else if (childClass == boolean.class
							|| childClass == Boolean.class) {
						try {
							// 这里为什么不用optInt? 因为假设服务器返回的是true,
							// 不是1和2，那么就让它报异常，然后再在异常里捕获重新获取
							int value = jsonArray.getInt(i);
							arrays.add(value == 1 ? true : false);
						} catch (Exception e) {
							e.printStackTrace();
							// 上面的错误就走到这里来了，说明可能返回true或者false,那么这里就得用optBoolean了，不能在get了
							arrays.add(jsonArray.optBoolean(i, false));
						}
					}
				} else { // 集合里是对象
					String entityName = null;
					if (!JsonUtils.isCommonField(childClass)) {
						JSONField jsonField = field.getAnnotation(JSONField.class);
						if (jsonField != null) {
							entityName = jsonField.entityName();
						}
					}
					Object entity = childClass.newInstance();
					if (entityName == null || entityName.equals("")) {
						if (fieldName.endsWith("ses")) {
							entityName = fieldName.substring(0,
									fieldName.length() - 2);
						} else if (fieldName.endsWith("s")) {
							entityName = fieldName.substring(0,
									fieldName.length() - 1);
						} else {
							entityName = fieldName;
						}
					}
					String jsonString;
					if (combinationType == CombinationType.AII_STYLE) {
						jsonString = jsonArray.optJSONObject(i).optString(
								entityName, "");
					} else {
						jsonString = jsonArray.optString(i, "");
					}
					if (!TextUtils.isEmpty(jsonString)) {
						try {
							entity = JSON.parseObject(jsonString, childClass, combinationType);
							if (entity != null) {
								arrays.add(entity);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			field.setAccessible(true);
			field.set(t, arrays);
		}
	}

	public static <T> void desCombinationArray(JSONObject json, T t, Class<?> childClass, Field field, String fieldName,
			String entityName, CombinationType combinationType)
			throws InstantiationException, IllegalAccessException {

		List<Object> arrays = new ArrayList<Object>();
		String stringValue = json.optString(fieldName);
		if (!TextUtils.isEmpty(stringValue)) {
			JSONArray jsonArray = null;
			try {
				jsonArray = json.getJSONArray(fieldName);
			} catch (Exception e) {
				// e.printStackTrace();
				//有时候返回不标准，稍微兼容一下
				if (stringValue.startsWith("[") && stringValue.endsWith("]")) {
					try {
						jsonArray = new JSONArray(stringValue);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
			}
			if (jsonArray != null) {
				
				for (int i = 0; i < jsonArray.length(); i++) {
					if (JsonUtils.isCommonField(childClass)) {

						// 集合里是常用数据类型
						if (childClass == int.class
								|| childClass == Integer.class) {
							arrays.add(jsonArray.optInt(i, 0));
						} else if (childClass == float.class
								|| childClass == Float.class
								|| childClass == double.class
								|| childClass == Double.class) {
							arrays.add(jsonArray.optDouble(i, 0));
						} else if (childClass == String.class
								|| childClass == char.class) {
							arrays.add(jsonArray.optString(i, ""));
						} else if (childClass == long.class
								|| childClass == Long.class) {
							arrays.add(jsonArray.optLong(i, 0));
						} else if (childClass == boolean.class
								|| childClass == Boolean.class) {
							try {
								// 这里为什么不用optInt? 因为假设服务器返回的是true,
								// 不是1和2，那么就让它报异常，然后再在异常里捕获重新获取
								int value = jsonArray.getInt(i);
								arrays.add(value == 1 ? true : false);
							} catch (Exception e) {
								e.printStackTrace();
								// 上面的错误就走到这里来了，说明可能返回true或者false,那么这里就得用optBoolean了，不能在get了
								arrays.add(jsonArray.optBoolean(i, false));
							}
						}
					} else { // 集合里是对象
						Object entity = childClass.newInstance();
						if (entityName == null || entityName.equals("")) {
							if (fieldName.endsWith("ses")) {
								entityName = fieldName.substring(0,
										fieldName.length() - 2);
							} else if (fieldName.endsWith("s")) {
								entityName = fieldName.substring(0,
										fieldName.length() - 1);
							} else {
								entityName = fieldName;
							}
						}
						String jsonString;
						if (combinationType == CombinationType.AII_STYLE) {
							jsonString = jsonArray.optJSONObject(i).optString(
									entityName, "");
						} else {
							jsonString = jsonArray.optString(i, "");
						}
						if (!TextUtils.isEmpty(jsonString)) {
							try {
								entity = JSON.parseObject(jsonString, childClass, combinationType);
								if (entity != null) {
									arrays.add(entity);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				field.setAccessible(true);
				field.set(t, arrays);
			}
		}
	}

	/**
	 * 把json的值赋值到类的属性里
	 * 
	 * @param field
	 *            变量
	 * @param json
	 *            json内容
	 * @param t
	 *            对象
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 */
	public static void setValueToAttribute(Field field, JSONObject json, Object t,
			String replaceName) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			IllegalArgumentException {

		if (json.has(replaceName)
				&& !json.optString(replaceName).equalsIgnoreCase("null")
				&& !json.optString(replaceName).equalsIgnoreCase("undefined")) {

			// 这里传过来的变量类型已经确定了，是常用类型，所以不用考虑太多了
			if (field.getType() == int.class
					|| field.getType() == Integer.class) {
				int value = json.optInt(replaceName, 0);
				field.setAccessible(true);
				field.set(t, value);
			} else if (field.getType() == boolean.class) {
				boolean value = false;
				String stringValue = json.optString(replaceName, "2");
				if (stringValue.equals("1")) {
					value = true;
				} else if (stringValue.equals("0") || stringValue.equals("2")) {
					value = false;
				} else {
					value = json.optBoolean(replaceName, false);
				}
				field.setAccessible(true);
				field.set(t, value);
			} else if (field.getType() == float.class) {
				field.setAccessible(true);
				field.set(t, (float) json.optDouble(replaceName, 0));
			} else if (field.getType() == double.class) {
				field.setAccessible(true);
				field.set(t, json.optDouble(replaceName, 0));
			} else if (field.getType() == long.class) {
				// 下面的内容如果返回“”容易报数字转换异常，如果转换出错就让
				field.setAccessible(true);
				field.set(t, json.optLong(replaceName, 0));
			} else {
				field.setAccessible(true);
				field.set(t, json.optString(replaceName, ""));
			}
		}
	}

	public static void setValueToAttribute2(Object t, Field field,
			Class<? extends Object> clazz, Object jValue) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			IllegalArgumentException {

		// 这里传过来的变量类型已经确定了，是常用类型，所以不用考虑太多了
		if (field.getType() == int.class || field.getType() == Integer.class) {
			int value = 0;
			try {
				value = Integer.parseInt(jValue.toString());
			} catch (NumberFormatException e) {
				// e.printStackTrace();
			}
			field.setAccessible(true);
			field.set(t, value);
		} else if (field.getType() == boolean.class) {
			boolean value = false;
			String stringValue = jValue.toString();
			if (stringValue.equals("1") || stringValue.equalsIgnoreCase("true")) {
				value = true;
			} else if (stringValue.equals("0") || stringValue.equals("2")
					|| stringValue.equalsIgnoreCase("false")) {
				value = false;
			}
			field.setAccessible(true);
			field.set(t, value);
		} else if (field.getType() == float.class) {
			field.setAccessible(true);
			float floatValue = 0;
			try {
				floatValue = Float.parseFloat(jValue.toString());
			} catch (NumberFormatException e) {
				//
			}
			field.set(t, floatValue);
		} else if (field.getType() == double.class) {
			field.setAccessible(true);
			double doubleValue = 0;
			try {
				doubleValue = Double.parseDouble(jValue.toString());
			} catch (NumberFormatException e) {
				//
			}
			field.set(t, doubleValue);
		} else if (field.getType() == long.class) {
			// 下面的内容如果返回“”容易报数字转换异常，如果转换出错就让
			long longValue = 0;
			try {
				longValue = Long.parseLong(jValue.toString());
			} catch (NumberFormatException e) {
				//
			}
			field.setAccessible(true);
			field.set(t, longValue);
		} else {
			field.setAccessible(true);
			field.set(t, jValue.toString());
		}

	}

}
