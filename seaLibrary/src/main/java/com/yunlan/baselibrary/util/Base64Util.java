package com.yunlan.baselibrary.util;


import android.util.Base64;

import java.nio.charset.StandardCharsets;


/**
 * Created by lhy on 2017/7/18.
 */

public class Base64Util {


    public static int getAESKeyPart1(int x, int y) {
        for (int i = 1; i <= x * y; i++) {
            if (i % x == 0 && i % y == 0)
                return i;
        }
        return x * y;
    }



    // 加密
    public static String encodeBase64(byte[] strByte) {
        String result = "";
        if (strByte != null) {
            result = new String(Base64.encode(strByte, Base64.DEFAULT), StandardCharsets.UTF_8);
        }
        return result;
    }

    public static String encodeBase64(String str) {
        String result = "";
        if (str != null) {
            result = new String(Base64.encode(str.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT), StandardCharsets.UTF_8);
        }
        return result;
    }

    // 解密
    public static String decodeBase64(String str) {
        String result = "";
        if (str != null) {
            result = new String(Base64.decode(str, Base64.DEFAULT), StandardCharsets.UTF_8);
        }
        return result;
    }

    public static byte[] decodeBase64ToBytes(String str) {
        if (str != null) {
            try {
                return Base64.decode(str, Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }


}
