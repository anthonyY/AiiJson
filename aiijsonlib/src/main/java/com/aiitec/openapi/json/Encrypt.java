package com.aiitec.openapi.json;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 加密类
 * 
 * @author Anthony
 * 
 */
public class Encrypt {

    /**
	 * MD5加密
	 * @param source 加密内容
	 * @return 加密后的内容（String类型）
	 * @throws UnsupportedEncodingException
	 */
	public static final String md5(String source)  {
		String dest = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray;
			try {
				byteArray = source.getBytes("UTF-8");
				byte[] md5Bytes = md5.digest(byteArray);
				StringBuffer hexValue = new StringBuffer();
				for (int i = 0; i < md5Bytes.length; i++) {
					int val = (md5Bytes[i]) & 0xff;
					if (val < 16)
						hexValue.append("0");
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
     * @param saltingStr
     */
    public static void setSaltingStr(String saltingStr) {
        Encrypt.saltingStr = saltingStr;
    }

    /**
     * 密码加盐
     * 
     * @param str
     * @return 加盐后的密码
     */
    public static String saltingPassword(String str) {
        return md5(str + saltingStr);
    }
}
