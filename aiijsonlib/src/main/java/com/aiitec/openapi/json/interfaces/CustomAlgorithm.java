package com.aiitec.openapi.json.interfaces;

/**
 * 自定义加密算法
 * @author Anthony
 * @version 1.0
 * createTime 2017/10/23.
 */

public interface CustomAlgorithm {
    String setAlgorithm(String password, String saltingStr);
}
