package com.aiitec.openapi.json;

import com.aiitec.openapi.json.annotation.JSONField;
import com.aiitec.openapi.json.utils.TextUtils;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class CombinationUtil {

	

	private static HashMap<Class<?>, List<Field>> map = new HashMap<>();
    private static SoftReference<HashMap<Class<?>, List<Field>>> softMap = new SoftReference<>(map);
	private static HashMap<Field, Class<?>> childClasses = new HashMap<>();
    private static SoftReference<HashMap<Field, Class<?>>> softChildClasses = new SoftReference<>(childClasses);



    private static OnFieldComparatorListener onFieldComparatorListener;

    public static void setOnFieldComparatorListener(OnFieldComparatorListener onFieldComparatorListener) {
        CombinationUtil.onFieldComparatorListener = onFieldComparatorListener;
    }

    /**
     * 排序监听接口，本来想提供Comparator 给用户调用的，但是 Annotation 的 名字怕用户会忽略，直接取field.getName() 然后找不到问题，所以写成这样了
     */
    public interface OnFieldComparatorListener{
        int onFieldComparator(String fieldName1, String fieldName2) ;
    }
    /**需要过滤的字段*/
	private static List<String> filterFields = new ArrayList<>();
	static {
        filterFields.add("serialVersionUID");
        filterFields.add("CREATOR");
        filterFields.add("companion");
    }
    public static void addFilterField(String fieldName){
        filterFields.add("fieldName");
    }
    public static void removeFilterField(String fieldName){
        if(filterFields.contains(fieldName)){
            filterFields.remove("fieldName");
        }
    }
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
            boolean isfilterField = false;
            for(String filterField : filterFields){
                if (field.getName().equalsIgnoreCase(filterField)) {
                    isfilterField = true;
                    break;
                }
            }
            if(isfilterField){
                continue;
            }

            if (field.getAnnotation(JSONField.class) != null && field.getAnnotation(JSONField.class).notCombination()) {
                // 如果不需组包
                continue;
            }

            // 变量名
            String filedName = getAnnFieldName(field);

            boolean isSame = false;
            for (Field field2 : allFields) {
                String filedName2 = getAnnFieldName(field2);
                if (filedName2.equals(filedName)) {
                    // 已经有了
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
     * 获取类的所有变量， 包括父类，爷爷类，到Object为止
     * 
     * @param clazz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> allFields = null;
        HashMap<Class<?>, List<Field>> map = softMap.get();
        if(map != null){
            allFields = map.get(clazz);
        } else {
            map = new HashMap<>();
            softMap = new SoftReference<>(map);
        }
        if(allFields == null){
        	 allFields = new ArrayList<>();
        	 allFields = getFields(clazz, allFields);
            map.put(clazz, allFields);
        }
        if(JSON.needSort){
            sortKeys(allFields);
        }
        return allFields;
    }
    
    public static Class<?> getChildClass(Field field){
        HashMap<Field, Class<?>> childClasses = softChildClasses.get();
        Class<?> childClass = null;
        if(childClasses != null){
            childClass = childClasses.get(field);
        } else {
            childClasses = new HashMap<>();
            softChildClasses = new SoftReference<>(childClasses);
        }

    	if(childClass == null){
    		ParameterizedType type = (ParameterizedType) field.getGenericType();
            if (type.getActualTypeArguments() != null && type.getActualTypeArguments().length > 0) {
                childClass = (Class<?>) type.getActualTypeArguments()[0];
                if(childClass != null){
                	childClasses.put(field, childClass);
                }
            }
    	}
        return childClass; 
    }

    /**
     * 对变量进行排序
     * @param params
     */
    private static void sortKeys(List<Field> params){
        Collections.sort(params, new Comparator<Field>(){
            @Override
            public int compare(Field leftField, Field rightField) {
                String leftName = getAnnFieldName(leftField);
                String rightName = getAnnFieldName(rightField);
                if(onFieldComparatorListener != null){
                    return onFieldComparatorListener.onFieldComparator(leftName, rightName);
                }
                for (int i = 0; i < leftName.length(); i++) {
                    char ch1 = leftName.charAt(i);
                    if(rightName.length() > i){
                        char ch2 = rightName.charAt(i);
                        if(ch1 == ch2){
                            //如果第一个字母相同，再比较下一个
                            continue;
                        } else {
                            return ch1 - ch2;
                        }

                    } else {
                        return 1;
                    }
                }
                //比较结果一致，再比较长度
                return leftName.length() - rightName.length();
            }
        });
    }

    /**
     * 获取 变量的 名字或者注解名， 如果有注解名，就用注解名
     * @param field
     * @return
     */
    public static String getAnnFieldName(Field field) {
        // 变量名
        String filedName = field.getName();
        // 注解名
        String filedAnnName = null;
        if (field.getAnnotation(JSONField.class) != null) {
            // 如果不需组包
            if (field.getAnnotation(JSONField.class).notCombination()) {
                return filedName;
            }
            filedAnnName = field.getAnnotation(JSONField.class).name();
            if (!TextUtils.isEmpty(filedAnnName)) {
                return filedAnnName;
            }
        }
        return filedName;
    }
}
