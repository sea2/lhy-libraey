package com.yunlan.baselibrary.util;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;

/**
 * Created by lhy on 2018/2/8.
 */

public class HtmlUtils {


    public static Spanned fromHtml(String htmlStr) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlStr, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(htmlStr);
        }
    }


    public static void setLinkClickable(final SpannableStringBuilder clickableHtmlBuilder, final URLSpan urlSpan,LinkCallBack linkCallBack) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.parseColor("#4289DA"));	//设置超链接颜色
                ds.setUnderlineText(false); //超链接去掉下划线
            }

            @Override
            public void onClick(View widget) {
                String originUrl = urlSpan.getURL();//获取url地址
                if(linkCallBack!=null){
                    linkCallBack.onClick(originUrl);
                }
            }
        };
        clickableHtmlBuilder.removeSpan(urlSpan);
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }

    public static CharSequence getClickableHtml(Spanned spannedHtml,LinkCallBack linkCallBack) {
        SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spannedHtml);
        URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        for (final URLSpan span : urls){
            setLinkClickable(clickableHtmlBuilder, span,linkCallBack);
        }
        return clickableHtmlBuilder;
    }

    public interface LinkCallBack{
        void onClick(String url);
    }
}
