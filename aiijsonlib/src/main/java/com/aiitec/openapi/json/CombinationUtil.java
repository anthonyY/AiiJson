package com.aiitec.openapi.json;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aiitec.openapi.json.annotation.JSONField;
import com.aiitec.openapi.json.utils.TextUtils;


public class CombinationUtil {

	
//	private static SparseArray<List<Field>> fieldSparseArray = new SparseArray<>();
	private static HashMap<Integer, List<Field>> map = new HashMap<>();
	private static HashMap<Integer, Class<?>> childClasses = new HashMap<>();
    /**
     * 获取当前类和父类所有字段 ，递归遍历
     * 
     * @param clazz
     *            遍历的类，不一定是哪个类
     * @param allFields
     *            记录所有字段的集合
     * @return 当前类和祖宗类的所有字段（不重复，包括注解名字一样的也变成一个）
     */
    private static List<Field> getFields(Class<?> clazz, List<Field> allFields) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            /*
             * Android Studio 会增加一个 $change 的变量 是增加了Instant
             * Run特性导致的，那就把此特性关闭就可以了(默认是开启的) 关闭方法：Settings> Build, Execution,
             * Deployment> Instant Run> Enable Instant Run to hot swap
             * code/resource changes on deploy(default enabled)（取消此选项）
             * 但是不是每个人都会去设置，所以还是代码处理
             */
            if (field.isSynthetic()) {
                continue;
            }
            if (field.getName().equalsIgnoreCase("serialVersionUID")) {
                continue;
            }
            if (field.getName().equals("CREATOR")) {
                continue;
            }

            // 变量名
            String filedName = field.getName();
            // 注解名
            String filedAnnName = null;
            if (field.getAnnotation(JSONField.class) != null) {
                filedAnnName = field.getAnnotation(JSONField.class).name();
                // 如果不需组包
                if (field.getAnnotation(JSONField.class).notCombination()) {
                    continue;
                }
            }
            boolean isSame = false;
            for (Field field2 : allFields) {
                String allFieldAnnName = null;
                if (field2.getAnnotation(JSONField.class) != null) {
                    allFieldAnnName = field2.getAnnotation(JSONField.class).name();
                }
                if ((field2.getName().equals(filedName))
                        || (!TextUtils.isEmpty(allFieldAnnName) && allFieldAnnName.equals(filedName))
                        || (!TextUtils.isEmpty(allFieldAnnName) && !TextUtils.isEmpty(filedAnnName) && allFieldAnnName
                                .equals(filedAnnName))) {// 已经有了
                    isSame = true;
                    break;
                }
            }
            if (!isSame) {
                allFields.add(field);
            }
        }
        Class<?> parent = clazz.getSuperclass();

        if (parent != null && !parent.equals(Object.class) && !Enum.class.isAssignableFrom(parent)) {
            getFields(parent, allFields);
        }
        return allFields;
    }

    /**
     * 获取类的所以变量， 包括父类，爷爷类，到Object为止
     * 
     * @param clazz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz) {

       
        List<Field> allFields = map.get(clazz.hashCode());
        if(allFields == null){
        	 allFields = new ArrayList<Field>();
        	 allFields = getFields(clazz, allFields);
        	 map.put(clazz.hashCode(), allFields);
        }
        
        return allFields;
    }
    
    public static Class<?> getChildClass(Field field){
    	Class<?> childClass = childClasses.get(field.hashCode());
    	if(childClass == null){
    		ParameterizedType type = (ParameterizedType) field.getGenericType();
            if (type.getActualTypeArguments() != null && type.getActualTypeArguments().length > 0) {
                childClass = (Class<?>) type.getActualTypeArguments()[0];
                if(childClass != null){
                	childClasses.put(field.hashCode(), childClass);
                }
            }
    	}
        return childClass; 
    }
   
}
