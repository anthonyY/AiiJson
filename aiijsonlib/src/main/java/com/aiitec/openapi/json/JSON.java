package com.aiitec.openapi.json;


import com.aiitec.openapi.json.annotation.JSONField;
import com.aiitec.openapi.json.enums.AIIAction;
import com.aiitec.openapi.json.enums.CombinationType;
import com.aiitec.openapi.json.utils.JsonUtils;
import com.aiitec.openapi.json.utils.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 这个类是处理json和JavaBean的总类， 主要有：<br>
 * JSON.toJsonString(t)<br>
 * JSON.parseObject(jsonString, Entity.class)<br>
 * 以及不同参数的重载方法
 * 
 * @author Anthony <br>
 *         createTime 2016-1-29
 */
public class JSON {

    /**
     * 组包模式，有两种，一种是正常模式NORMAL， 一种是AII_STYLE模式，这种模式的主要是数组的组包多了一层key
     */
    public static CombinationType combinationType = CombinationType.AII_STYLE;
    /** 是否对密码加盐 */
    public static boolean saltingPassword = false;
    public static final String TAG = "TAG_AII_JSON";
    
    private static String getStringFromArray(List<?> list, CombinationType combinationType)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            JSONException {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < list.size(); i++) {
            // PacketUtil.isCommonField(clazz) //我写的是否是常用数据类型包括String 
            // list.get(i).getClass().isPrimitive();
            // java自带的是否是常用数据类型，但是不包括String
            if (JsonUtils.isCommonField(list.get(i).getClass())) {
                sb.append("\"").append(list.get(i)).append("\"");
            } else {
                String value = toJsonStringFromParent(list.get(i).getClass(), list.get(i), combinationType);
                sb.append(value);
            }
            if (i != list.size() - 1) {
                sb.append(',');
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public static String toJsonString(Object t) {
    	String str = toJsonString(t, JSON.combinationType);
        return str;
    }

    /**
     * 把对象组包成json
     * 
     * @param t
     * @return json格式的字符串
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JSONException
     */
    public static String toJsonString(Object t, CombinationType combinationType){
        String requestData = "";
        try {
        	if (t.getClass().equals(List.class)) {

                List<?> list = (List<?>) t;
                requestData = getStringFromArray(list, combinationType);

            } else if (t.getClass().equals(ArrayList.class)) {

                ArrayList<?> list = (ArrayList<?>) t;
                requestData = getStringFromArray(list, combinationType);

            } else {

                requestData = toJsonStringFromParent(t.getClass(), t, combinationType);

            }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return requestData;
    }

    /**
     * 遍历所有当前类和父类、祖宗类的所有变量，进行组包，组成json格式的String
     * 
     * @param clazz
     * @param t
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JSONException
     */
    private static String toJsonStringFromParent(Class<?> clazz, Object t, CombinationType combinationType)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            JSONException {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        // 必须不是枚举，被枚举坑过，很惨
        if (clazz != null && !JsonUtils.isCommonField(clazz) && !Enum.class.isAssignableFrom(clazz)) {

            List<Field> fields = CombinationUtil.getAllFields(clazz);
            for (Field field : fields) {

                Combination.appendJsonData(t, field, sb, combinationType);
            }
            if (sb.toString().endsWith(",")) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * 把json的值传到对象里
     * 
     * @param clazz
     *            对象的类型
     * @param field
     *            属性字段
     * @param t
     *            对象
     * @param json
     *            json数据
     * @throws JSONException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws Exception
     *             抛出异常
     */
    private static <T> void setValuesFromDictination(Class<?> clazz, Field field, T t, JSONObject json,
            CombinationType combinationType) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, JSONException {

        String fieldName = null;
        String entityName = null;
        
        // 获取字段的注解
        JSONField annotation = field.getAnnotation(JSONField.class);
        if (annotation != null) {
            fieldName = annotation.name();
            entityName = annotation.entityName();
        }
        if (fieldName == null || fieldName.equals("")) {
            fieldName = field.getName();
        }

        if (json.has(fieldName)) {
            if (Enum.class.isAssignableFrom(field.getType())) {
                if (field.getType() == AIIAction.class) {
                    int value = json.optInt(fieldName, 0);
                    field.setAccessible(true);
                    field.set(t, AIIAction.valueOf(value));
                }
            } else {
                // 如果是常用数据类型
                if (JsonUtils.isCommonField(field.getType())) {
                    DesCombination.setValueToAttribute(field, clazz, json, t, fieldName);
                }
                // 如果是数组
                else if (List.class.isAssignableFrom(field.getType())) {
                	Class<?> childClass = CombinationUtil.getChildClass(field);
                	if(childClass != null){
                		 DesCombination.desCombinationArray(json, t, clazz, childClass, field, fieldName,
                               entityName, combinationType);
                	}
                }
                // 如果是对象
                else {
                    if (!TextUtils.isEmpty(json.optString(fieldName))) {
                    	Object entity = JSON.parseObject(json.optString(fieldName), field.getType(), combinationType);
                        if (entity != null) {
                            field.setAccessible(true);
                            field.set(t, entity);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> setArrayValuesFromDictination(Class<T> entityClazz, JSONArray array,
            CombinationType combinationType) throws JSONException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, InstantiationException {
        List<T> list = new ArrayList<T>();

        for (int i = 0; i < array.length(); i++) {
            if (entityClazz != null && !JsonUtils.isCommonField(entityClazz)
                    && !Enum.class.isAssignableFrom(entityClazz)) {
                List<Field> fields = CombinationUtil.getAllFields(entityClazz);
                T entity = null;
                try {
                    entity = entityClazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (Field field : fields) {

                    JSONObject obj = array.getJSONObject(i);
                    setValuesFromDictination(entityClazz, field, entity, obj, combinationType);
                }
                list.add(entity);

            } else {
                list.add((T) array.get(i));

            }
        }
        return list;
    }

    private static Field getFieldFromParent(Class<?> clazz, String name){
    	Field field = null;
    	try {
			field = clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
		}
    	if(field == null){
    		Class<?> parent = clazz.getSuperclass();
    		if (parent != null && !parent.equals(Object.class) && !Enum.class.isAssignableFrom(parent)) {
    			field = getFieldFromParent(parent, name);
            }
    	}
    	return field;
    }
    private static <T> T valueFromDictionaryFromParent2(Class<?> clazz, JSONObject json, T t,
            CombinationType combinationType) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, JSONException, InstantiationException {
    	@SuppressWarnings("unchecked")
		Iterator<String> iterator = json.keys();
    	while(iterator.hasNext()){
    		String key = iterator.next();
    		Object value = json.opt(key);
    		if(value == null){
    			continue;
    		}
    		Field field = getFieldFromParent(clazz, key);
			
    		if(field == null){
    			List<Field> fields = CombinationUtil.getAllFields(clazz);
    	        for (Field field2 : fields) {
    	        	JSONField jsonField = field2.getAnnotation(JSONField.class);
    	        	String name = "";
    	        	if(jsonField != null){
    	        		name = jsonField.name();
    	        	}
    	            if(name.equalsIgnoreCase(key)){
    	            	field = field2;
    	            	break;
    	            }
    	        }
    		}
    		setValuesFromDictination2(field, clazz, value, t, combinationType);
    	}
        return t;
    }
    
    /**
     * 这个方法是从json解析到对象时使用 遍历对象的变量的方式来解析， 带2的方法是遍历json来解析
     * @param clazz
     * @param json
     * @param t
     * @param combinationType
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JSONException
     * @throws InstantiationException
     */
    private static <T> T valueFromDictionaryFromParent(Class<?> clazz, JSONObject json, T t,
            CombinationType combinationType) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, JSONException, InstantiationException {
        List<Field> fields = CombinationUtil.getAllFields(clazz);
        for (Field field : fields) {
            setValuesFromDictination(clazz, field, t, json, combinationType);
        }
        return t;
    }

    private static void setValuesFromDictination2(Field field, Class<?> clazz, Object value, Object t, CombinationType combinationType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IllegalArgumentException, InstantiationException{
    	if(field == null) return;
    	if(JsonUtils.isCommonField(field.getType())){//常用数据类型
			DesCombination.setValueToAttribute2(t, field, clazz, value);
		}
		else if(Enum.class.isAssignableFrom(field.getType())){//数组
			if (field.getType() == AIIAction.class) {
                int intValue = Integer.parseInt(value.toString());
                field.setAccessible(true);
                field.set(t, AIIAction.valueOf(intValue));
            }
		}
		else if(List.class.isAssignableFrom(field.getType())){//数组
			Class<?> childClass = CombinationUtil.getChildClass(field);
			if(childClass != null){
                try{
                    JSONArray array = (JSONArray) value;
                    DesCombination.desCombinationArray2(array, t, clazz, childClass, field, combinationType);
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("value is not JSONArray:\t"+clazz+":\t"+field.getName()+"\t"+value);
                }
				
			}
		}
		else {//对象
            try {
                Object entity = JSON.parseObject(value.toString(), field.getType());
                if (entity != null) {
                    try{
                        field.setAccessible(true);
                        field.set(t, entity);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println( "value is not JSONObject:\t"+clazz+":\t"+field.getName()+"\t"+value);
            }

		}
    }
    public static <T> T parseObject(String json, Class<T> clazz, CombinationType combinationType){
         
		try {
			JSONObject js = new JSONObject(json);
			return parseObject(js, clazz, combinationType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return null;
    }

    public static <T> T parseObject(JSONObject json, Class<T> clazz, CombinationType combinationType){
    	try {
	    	T t = clazz.newInstance();
	        t = valueFromDictionaryFromParent2(clazz, json, t, combinationType);
	        return t;
	    } catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }

    public static <T> T parseObject(String json, Class<T> clazz){
       
		try {
			JSONObject js = new JSONObject(json);
			T t = parseObject(js, clazz, JSON.combinationType);
			return t;
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return null;
    }

   
    /**
     * 把JSON组成List集合
     * 
     * @param json
     *            json字符串
     * @param entityClazz
     *            集合的子选项类 比如要转换成List<User> 那么就得传User.class， 而不是List.class
     * @return 转换后的List
     * @throws Exception
     */
    public static <T> List<T> parseArray(String json, Class<T> entityClazz){
        
        List<T> list = new ArrayList<>();
        try {
        	JSONArray array = new JSONArray(json);
        	list = setArrayValuesFromDictination(entityClazz, array, JSON.combinationType);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return list;
    }

   
}
