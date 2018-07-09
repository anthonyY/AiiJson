package com.aiitec.openapi.json.utils;

import com.aiitec.openapi.json.CombinationUtil;

/**
 * @author Anthony
 * @version 1.0
 *          createTime 2018/7/9.
 */

public class DefaultFieldComparator implements CombinationUtil.OnFieldComparatorListener {
    public DefaultFieldComparator(){

    }
    @Override
    public int onFieldComparator(String leftName, String rightName) {
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
}
