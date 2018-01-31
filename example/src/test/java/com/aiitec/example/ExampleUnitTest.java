package com.aiitec.example;

import com.aiitec.openapi.json.Encrypt;
import com.aiitec.openapi.json.interfaces.CustomAlgorithm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        Encrypt.setSaltingStr("123456789");
        Encrypt.setCustomAlgorithm(new CustomAlgorithm() {
            @Override
            public String setAlgorithm(String password, String saltingStr) {
                return Encrypt.md5(Encrypt.md5(password)+saltingStr);
            }
        });
        String md5 =  Encrypt.saltingPassword("12345678");
        System.out.println(md5);
    }
}