package com.aiitec.openapi.json;

import com.aiitec.openapi.json.annotation.JSONField;
import com.aiitec.openapi.json.enums.AIIAction;
import com.aiitec.openapi.json.enums.CombinationType;
import com.aiitec.openapi.json.utils.JsonUtils;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/***
 * 组包类
 * 
 * @author Anthony
 * 
 */
public class Combination {

    /**
     * 小数点后面两位，有0就去掉
     * @param text
     * @return
     */
    public static String formatString(Object text) {
        if(text == null){
            return "";
        }
        try {
            DecimalFormat df = new DecimalFormat("0.########");
            String str = df.format(text);
            return str;
        } catch(Exception e){
            e.printStackTrace();
        }

        return text.toString();
    }
    /**
     * 
     * @param field
     * @param result
     * @param sb
     * @param replceName
     * @throws JSONException
     */
    private static void appendValueToJson(Field field, Object result, StringBuilder sb, String replceName)
            throws JSONException {

        if (field.getType() == int.class || field.getType() == float.class || field.getType() == double.class
                || field.getType() == long.class) {
            if (!result.toString().equalsIgnoreCase("-1") && !result.toString().equalsIgnoreCase("-1.0")) {// 如果值不是-1
                if (replceName == null || replceName.equals("")) {
                    sb.append('\"').append(field.getName());
                } else {
                    sb.append('\"').append(replceName);
                }
                sb.append('\"').append(':').append(formatString(result)).append(',');
            }
        } else if (field.getType() == boolean.class) {
            if (result.toString().equalsIgnoreCase("true")) {// 如果值是true
                if (replceName == null || replceName.equals("")) {
                    sb.append('\"').append(field.getName());
                } else {
                    sb.append('\"').append(replceName);
                }
                sb.append('\"').append(':').append("1").append(',');
            } else if (result.toString().equalsIgnoreCase("false")) {// 如果值是true
                if (replceName == null || replceName.equals("")) {
                    sb.append('\"').append(field.getName());
                } else {
                    sb.append('\"').append(replceName);
                }
                sb.append('\"').append(':').append("0").append(',');
            }
        } else if (List.class.isAssignableFrom(field.getType())) {
            @SuppressWarnings("unchecked")
            ArrayList<Object> array = (ArrayList<Object>) result;
            sb.append('\"');
            if (replceName == null || replceName.equals("")) {
                sb.append(field.getName());
            } else {
                sb.append(replceName);
            }
            sb.append('\"').append(':');
            sb.append('[');
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i) != null) {
                    String result2 = stringToJson(array.get(i).toString());
                    sb.append(result2);
                    sb.append(',');

                }
            }
            if (sb.charAt(sb.toString().length() - 1) == ',') {
                sb.deleteCharAt(sb.toString().length() - 1);
            }
            sb.append(']');

            sb.append(',');
        } else {
            sb.append('\"');
            if (replceName == null || replceName.equals("")) {
                sb.append(field.getName());
            } else {
                sb.append(replceName);
            }
            sb.append('\"').append(':');

            String result2 = stringToJson(result.toString());
            sb.append(result2);

            sb.append(',');
        }

    }

    /**
     * 组包json数据
     * 
     * @param t
     * @param field
     * @param sb
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JSONException
     */
    public static void appendJsonData(Object t, Field field, StringBuilder sb,
            CombinationType combinationType) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, JSONException {
        String fieldName = null;
        String entityName = null;
        boolean isEnum = Enum.class.isAssignableFrom(field.getType());
        boolean notCombination = false;
        boolean isPassword = false;
        // 获取变量的注解
        JSONField annotation = field.getAnnotation(JSONField.class);
        if (annotation != null) {
            fieldName = annotation.name();
            entityName = annotation.entityName();
            notCombination = annotation.notCombination();
            isPassword = annotation.isPassword();
        }
        if (notCombination) {
            return;
        }
        // 如果注解名为空，则去原本的字段名
        if (fieldName == null || fieldName.equals("")) {
            fieldName = field.getName();
        }
        if (isEnum) {// 当是枚举的时候
            if (field.getType() == AIIAction.class) {
                field.setAccessible(true);
                AIIAction result = (AIIAction) field.get(t);
                if (result != null && result != AIIAction.NULL) {

                    int value = AIIAction.getValues(result);
                    sb.append('\"').append(fieldName).append('\"').append(':');
                    sb.append("" + value).append(',');
                }
            }
        } else {

            if (JsonUtils.isCommonField(field.getType())) {
                field.setAccessible(true);
                Object result = field.get(t);
                // Object result = clazz.getMethod(methodName).invoke(t);
                if (result != null) {
                    if (isPassword) {
                        if (JSON.saltingPassword) {
                            result = Encrypt.saltingPassword(result.toString());
                        } else {
                            result = Encrypt.md5(result.toString());
                        }
                    }
                    appendValueToJson(field, result, sb, fieldName);
                }
            }
            // 当是集合的时候
            else if (field.getType() == List.class || field.getType() == ArrayList.class) {
            	Class<?> childClass = CombinationUtil.getChildClass(field);
            	if(childClass != null){
            		field.setAccessible(true);
            		ArrayList<?> result = (ArrayList<?>) field.get(t);
                    if (result != null) {
                        if (JsonUtils.isCommonField(childClass)) {
                            appendValueToJson(field, result, sb, fieldName);
                        } else {
                            appendArrayData(fieldName, entityName, result, sb, combinationType);
                        }
                    }
            	}
            }
            // 当是数组的时候
            else if (field.getType().isArray()) {

                field.setAccessible(true);
                Object result = field.get(t);
                if(result != null){
                    sb.append('\"').append(fieldName).append('\"').append(':');
                    sb.append('[');
                    for (int i = 0; i < Array.getLength(result); i++) {
                        Object childObj = Array.get(result, i);
                        if(childObj != null){
                            String childStr = "";
                            if(JsonUtils.isCommonField(childObj.getClass())){
                                childStr = String.valueOf(childObj);
                                if(Number.class.isAssignableFrom(childObj.getClass())){
                                    sb.append(childStr).append(",");
                                } else {
                                    sb.append('"').append(childStr).append('"').append(",");
                                }

                            } else {
                                childStr = JSON.toJsonString(childObj, combinationType);
                                sb.append(childStr).append(",");
                            }
                        }
                    }
                    if(sb.toString().endsWith(",")){
                        sb.deleteCharAt(sb.length()-1);
                    }
                    sb.append("]").append(',');

                }
            }
            else if(Map.class.isAssignableFrom(field.getType())){
                field.setAccessible(true);
                Object result = field.get(t);
                if(result != null){
                    String str = JSON.mapToString((Map)result);
                    sb.append('\"').append(fieldName).append('\"').append(':');
                    sb.append(str).append(',');
                }
            }
            else {
                field.setAccessible(true);
                Object en = field.get(t);
                if (en != null) {
                    String query = JSON.toJsonString(en, combinationType);
                    sb.append('\"').append(fieldName).append('\"').append(':');
                    sb.append(query).append(',');

                }
            }
        }

    }

    /**
     * 对数组的组包
     * 
     * @param arrayName
     *            变量名 （更改后的 如action 的 应该是 a）
     * @param result
     *            变量的值
     * @param sb
     *            组合字符串
     * @throws Exception
     *             抛出异常
     */
    private static void appendArrayData(String arrayName, String entityName, ArrayList<?> result, StringBuilder sb,
            CombinationType combinationType) {
        sb.append('\"').append(arrayName).append('\"').append(':');
        sb.append('[');
        for (int i = 0; i < result.size(); i++) {
            Object entity = result.get(i);
            if (entity != null) {
                if (entityName == null || entityName.equals("")) {// 正常情况，数组是
                    // names,
                    // 那么实体就是name，去掉s或者es
                    if (arrayName.endsWith("ses")) {
                        entityName = arrayName.substring(0, arrayName.length() - 2);
                    } else if (arrayName.endsWith("s")) {
                        entityName = arrayName.substring(0, arrayName.length() - 1);
                    } else {// 如果不是s或者es结尾就数组和实体的名称一样
                        entityName = arrayName;
                    }
                }
                String query = "";
                try {
                    query = com.aiitec.openapi.json.JSON.toJsonString(entity, combinationType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (combinationType == CombinationType.AII_STYLE && query != null) {
                    sb.append('{').append('\"').append(entityName).append('\"').append(':').append(query).append('}')
                            .append(',');

                } else {
                    sb.append(query).append(',');
                }
            }
        }
        if (sb.toString().endsWith(",")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(']');
        sb.append(',');
    }

    /**
     * 将 String 对象编码为 JSON格式，只需处理好特殊字符
     * 
     * @param str
     *            String 对象
     * @return String:JSON格式
     * @version 1.0
     * @date 2015-10-11
     * @Author zhou.wenkai
     */
    private static String stringToJson(final String str) {
        if (str == null || str.length() == 0) {
            return "\"\"";
        }
        final StringBuilder sb = new StringBuilder(str.length() + 2 << 4);
        sb.append('\"');
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);

            sb.append(c == '\"' ? "\\\"" : c == '\\' ? "\\\\" : c == '/' ? "\\/" : c == '\b' ? "\\b"
                    : c == '\f' ? "\\f" : c == '\n' ? "\\n" : c == '\r' ? "\\r" : c == '\t' ? "\\t" : c+"");
        }
        sb.append('\"');
        return sb.toString();
    }
}
