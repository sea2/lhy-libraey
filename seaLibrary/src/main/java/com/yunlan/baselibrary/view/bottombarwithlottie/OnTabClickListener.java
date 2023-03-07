package com.yunlan.baselibrary.view.bottombarwithlottie;

import android.view.View;

/**
 * Created by lhy on 2022/10/27.
 */


public abstract class OnTabClickListener implements View.OnClickListener {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static int MIN_CLICK_DELAY_TIME = 100;
    private static long lastClickTime;

    public abstract void onMultiClick(View v);

    public OnTabClickListener() {
    }

    public OnTabClickListener(int time) {
        MIN_CLICK_DELAY_TIME = time;
    }

    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;
            onMultiClick(v);
        }
    }



}