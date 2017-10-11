package com.aiitec.openapi.json.enums;

/**
 * GET 组装类型
 * @author Anthony
 *
 */
public enum GetCombinationType {
    /**
     * 请求的样gradlew clean assembleReleaseChannels式 www.xxx.xx/api?n=Setting&a=1&id=33
     */
	NORMAL, 
	/**
	 * 请求的样式www.xxx.xx/api?json={"n":"Setting","a":1,"id":33}
	 */
	JSON;
}
