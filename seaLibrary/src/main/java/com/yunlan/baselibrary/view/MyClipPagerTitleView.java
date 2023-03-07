package com.yunlan.baselibrary.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * Created by lhy on 2022/10/25 0025.
 */

public class MyClipPagerTitleView extends ColorTransitionPagerTitleView {

    int selectTextSize=18;
    int noSelectTextSize=15;


    public MyClipPagerTitleView(Context context) {
        super(context);
    }


    public MyClipPagerTitleView(Context context,int selectTextSize,int noSelectTextSize) {
        super(context);
        this.selectTextSize=selectTextSize;
        this.noSelectTextSize=noSelectTextSize;
    }



    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected(index, totalCount);
        setTypeface(Typeface.DEFAULT_BOLD); //选中后的字体样式,根据需求自己做修改
        setTextSize(TypedValue.COMPLEX_UNIT_SP, selectTextSize);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index, totalCount);
        setTypeface(Typeface.DEFAULT);//未选中的字体样式,根据需求自己做修改
        setTextSize(TypedValue.COMPLEX_UNIT_SP, noSelectTextSize);
    }
}
