package com.aiitec.openapi.json.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtils {
	/**
     * 格式化json
     * 
     * @param content
     * @return
     */
    public static String formatJson(String content) {

        StringBuffer sb = new StringBuffer();
        int index = 0;
        int count = 0;
        while (index < content.length()) {
            char ch = content.charAt(index);
            if (ch == '{' || ch == '[') {
                sb.append(ch);
                sb.append('\n');
                count++;
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
            } else if (ch == '}' || ch == ']') {
                sb.append('\n');
                count--;
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
                sb.append(ch);
            } else if (ch == ',') {
                sb.append(ch);
                sb.append('\n');
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
            } else {
                sb.append(ch);
            }
            index++;
        }
        return sb.toString();
    }

    /**
     * 把格式化的json紧凑
     * 
     * @param content
     * @return
     */
    public static String compactJson(String content) {
        String regEx = "[\t\n]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        return m.replaceAll("").trim();
    }

    /**
     * 是否是常用数据类型，包括常用类的包装类Integer等和String
     * 
     * @param classType
     *            需要比较的类
     * @return 是否是常用数据类型
     */
    public static boolean isCommonField(Class<?> classType) {
        boolean isCommonField = (classType.equals(int.class) || classType.equals(Integer.class)
                || classType.equals(float.class) || classType.equals(Float.class) || classType.equals(double.class)
                || classType.equals(Double.class) || classType.equals(long.class) || classType.equals(Long.class)
                || classType.equals(char.class) || classType.equals(String.class) || classType.equals(boolean.class) || classType
                .equals(Boolean.class));
        return isCommonField;
    }

}
