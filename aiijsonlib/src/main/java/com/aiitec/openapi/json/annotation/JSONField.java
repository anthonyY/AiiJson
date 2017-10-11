package com.aiitec.openapi.json.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 注解类
 * @author Anthony
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface JSONField {

	
	/**
	 * 组包解包的变量名，如果相同则可以忽略
	 * @return 定义的变量名，默认空""
	 */
	String name() default ""; 
	/**
	 * 针对集合才有的
	 * 组包数组时的实体名称 比如"regionInfo":[{"region":{......}}]
	 * 里的region
	 * @return 实体名称
	 */
	String entityName() default ""; 

	/**
	 * 是否不组包
	 * @return 是或否， 是的话，就不组包，json就不会出现该字段
	 */
	boolean notCombination() default false ;
	/**
	 * 是否强制组包
	 * @return 是或否， 只用在值为-1时，正常-1的值是不组包的，
	 * 		       但是考虑有的字段真的会有-1，就强制组包，比如楼层
	 */
	boolean isForcedCombination() default false ;
	/**
	 * 是否是密码， 如果是密码的话组包自动MD5加密，如果开启密码加盐，那么这个字段组包会变成加盐后的内容
	 * @return 
	 */
	boolean isPassword() default false ;
}
