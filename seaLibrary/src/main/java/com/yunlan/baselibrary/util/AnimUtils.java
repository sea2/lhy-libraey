package com.yunlan.baselibrary.util;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import com.github.sea2.R;


public class AnimUtils {

    /**
     * 晃动动画
     * @param counts 1秒钟晃动多少下
     */
    public static Animation shakeAnimation(int counts) {
        Animation rotateAnimation = new RotateAnimation(0, 5, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new CycleInterpolator(counts));
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setDuration(600);
        return rotateAnimation;
    }

    /**
     * 左右晃动动画
     */
    public static Animation TranslateAnimation(int duration) {
        TranslateAnimation animation = new TranslateAnimation(0, -6, 0, 0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(duration);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }


    /**
     * 放转动画
     */
    public static Animation RotateAnimation(Activity mActivity) {
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        return animation;
    }



}
