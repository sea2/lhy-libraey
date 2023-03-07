package com.yunlan.baselibrary.view

import android.widget.TextView

/**
 * Created by lhy on 2022/7/4 0004.
 */
class Test {


    fun findIndex(textView: TextView, mainContent: CharSequence, suffix: CharSequence): Int {
        for (i in mainContent.length downTo 1) {
            textView.text = mainContent.subSequence(0, i).toString() + suffix.toString()
            val lineCount0 = textView.layout.lineCount
            textView.text = mainContent.subSequence(0, i + 1).toString() + suffix.toString()
            val lineCount1 = textView.layout.lineCount
            if (lineCount0 == 2 && lineCount1 == 3) {
                return i
            }
        }
        return -1
    }


}