package com.aiitec.openapi.json.enums;

public enum CombinationType {
    /**
     * {
     *     "goodses":[
     *         {
     *             "name":"商品1",
     *             "price":32.5
     *             
     *         },
     *         {
     *             "name":"商品2",
     *             "price":30.0
     *         }
     *     ]
     * }
     * 正常的json格式，主要是为了区别AII_STYLE模式
     */
	NORMAL, 
	/**
	 * 用于数组的json格式
	 * {
	 *     "goodses":[
	 *         {
	 *             "goods":{
	 *                 "name":"商品1",
	 *                 "price":32.5
	 *             }
	 *         },
	 *         {
     *             "goods":{
     *                 "name":"商品2",
     *                 "price":30.0
     *             }
     *         }
	 *     ]
	 * }
	 * 比正常的json格式多了个key
	 */
	AII_STYLE;
}
