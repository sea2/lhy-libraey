package com.yunlan.baselibrary.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.fragment.app.Fragment;

public class AndroidLifecycleUtils {


    // 判断fragment是否销毁
    public static boolean isValidFragmentForGlide(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        if (fragment != null && fragment.getActivity() != null) {
            return isValidContextForGlide(fragment.getActivity());
        } else {
            return false;
        }
    }

    // 判断Activity是否Destroy
    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            LogUtils.loge("1.判断Activity是否Destroy======");
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (activity.isDestroyed() || activity.isFinishing()) {
                    LogUtils.loge("2.判断Activity是否 isDestroyed isFinishing======");
                    return false;
                }
            } else {
                if (activity.isFinishing()) {
                    LogUtils.loge("3.判断Activity是否 isFinishing======");
                    return false;
                }
            }
        }
        return true;
    }



}
