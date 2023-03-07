package com.yunlan.baselibrary.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志相关
 */

public class LogUtils {

    public static boolean flag = true;

    /**
     * Debug日志
     *
     * @param msg 消息
     */
    public static void logi(String msg) {// 调试信息
        if (flag && !TextUtils.isEmpty(msg)) {
            Log.i("TAGG", msg);
        }
    }

    public static void loge(String msg) {// 调试信息
        if (flag && !TextUtils.isEmpty(msg)) {
            Log.e("TAGG", msg);
        }
    }

    public static void logf(String msg) {// 调试信息
        if (flag && !TextUtils.isEmpty(msg)) {
            Log.e("TAFF", msg);
        }
    }

    public static void logy(String msg) {// 调试信息
        if (flag && !TextUtils.isEmpty(msg)) {
            Log.e("TAYY", msg);
        }
    }

    public static void logm(String msg) {// 调试信息
        if (flag && !TextUtils.isEmpty(msg)) {
            Log.e("TAMM", msg);
        }
    }

    public static void log_Tag(String TAG, String msg) {// 调试信息
        if (flag && !TextUtils.isEmpty(msg)) {
            Log.e(TAG, msg);
        }
    }

    public static void log_Pay(String msg) {// 调试信息
        if (flag && !TextUtils.isEmpty(msg)) {
            Log.e("GooglePay", msg);
        }
    }

}
