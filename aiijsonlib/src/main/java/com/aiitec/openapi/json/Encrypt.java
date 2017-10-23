package com.aiitec.openapi.json;

import com.aiitec.openapi.json.interfaces.CustomAlgorithm;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 加密类
 *
 * @author Anthony
 *         edit by Anthony at 2017-10-23
 */
public class Encrypt {

    /**
     * MD5加密
     *
     * @param source 加密内容
     * @return 加密后的内容（String类型）
     */
    public static String md5(String source) {
        String dest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray;
            try {
                byteArray = source.getBytes("UTF-8");
                byte[] md5Bytes = md5.digest(byteArray);
                StringBuilder hexValue = new StringBuilder();
                for (byte md5Byte : md5Bytes) {
                    int val = (md5Byte) & 0xff;
                    if (val < 16) {
                        hexValue.append("0");
                    }
                    hexValue.append(Integer.toHexString(val));
                }
                dest = hexValue.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return dest;
    }


    /**
     * 加盐内容
     */
    private static String saltingStr = "81hqbcqfn5m80dreg526s8knq6";

    /**
     * 修改加盐内容
     *
     * @param saltingStr 加盐内容
     */
    public static void setSaltingStr(String saltingStr) {
        Encrypt.saltingStr = saltingStr;
    }

    /**
     * 密码加盐
     *
     * @param pasword 密码
     * @return 加盐后的密码
     */
    public static String saltingPassword(String pasword) {
        if (customAlgorithm != null) {
            return customAlgorithm.setAlgorithm(pasword, saltingStr);
        } else {
            return md5(saltingStr + md5(pasword));
        }

    }

    /**自定义加密算法*/
    private static CustomAlgorithm customAlgorithm;

    public static void setCustomAlgorithm(CustomAlgorithm customAlgorithm) {
        Encrypt.customAlgorithm = customAlgorithm;
    }
}
