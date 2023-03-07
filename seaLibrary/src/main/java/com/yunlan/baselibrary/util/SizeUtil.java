package com.yunlan.baselibrary.util;


/**
 * 根据屏幕的密度比等比设置高度
 */

public class SizeUtil {


    // 主页直播高度
    public static int getLiveHeight(int width) {
        return (int) (width / 2);
    }


}
