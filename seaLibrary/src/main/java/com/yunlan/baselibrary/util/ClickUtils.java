package com.yunlan.baselibrary.util;



public class ClickUtils {

    // 两次点击按钮之间的点击间隔
    private static final int MIN_DELAY_TIME = 300;
    private static long lastClickTime;

    public static boolean isFastClick() {//点击间隔时间判断
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= MIN_DELAY_TIME) {//点击间隔时间
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }


}