package com.yunlan.baselibrary.util;

import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.TextView;

import com.github.sea2.R;
import com.yunlan.baselibrary.bean.FileProvider7;

import java.io.File;

/**
 * Created by lhy on 2022/5/6.
 */

public class Utils {

    static  Application application;

    public static Application getApplication() {
        return application;
    }

    public static void setApplication(Application application) {
        Utils.application = application;
    }

    public static int getDrawableResByName(Context context, String imgName) {
        Resources resources = context.getResources();
        return resources.getIdentifier(imgName, "drawable", context.getPackageName());
    }



    public static void setLevelTag(Context  context, int level, TextView tvLevel, TextView tvTag){
        if (level <= 10) {
            if(level >5){
                tvLevel.setBackgroundResource(R.drawable.shape_bg_home_user_tag_blue);
            }else{
                tvLevel.setBackgroundResource( R.drawable.shape_bg_home_user_tag_green);
            }
            tvTag.setBackgroundResource(Utils.getDrawableResByName(context, "ic_user_tag_lv" + level));
        } else {
            if (level > 20) {
                tvLevel.setBackgroundResource( R.drawable.btn_theme_shape);
                tvTag.setBackgroundResource( Utils.getDrawableResByName(context, "ic_user_tag_lv20"));
            } else {
                tvTag.setBackgroundResource( Utils.getDrawableResByName(context, "ic_user_tag_lv" + level));
                if(level >15){
                    tvLevel.setBackgroundResource( R.drawable.btn_theme_shape);
                }else{
                    tvLevel.setBackgroundResource( R.drawable.shape_bg_home_user_tag_purple);
                }
            }
        }
    }



    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    public static boolean copyStr(Activity activity,String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static void installAPK(Activity activity,String ApkPath) {
        if (activity != null) {
            // String path = --
            // + "/download/";
            File apkfile = new File(ApkPath);
            if (!apkfile.exists()) {
                return;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            FileProvider7.setIntentDataAndType(intent, "application/vnd.android.package-archive", apkfile, true);
            activity.startActivity(intent);

        }
    }





    public static final int DELAY = 400;
    private static long lastClickTime = 0;
    public static boolean isNotFastClick(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > DELAY) {
            lastClickTime = currentTime;
            return true;
        }else{
            return false;
        }
    }



}
