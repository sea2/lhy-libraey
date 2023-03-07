package com.yunlan.baselibrary.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;

/**  多段 文本 适用类似协议
 * Created by lhy on 2020/9/8 0008.
 */
public class SpanTextView extends TextView {
    private List<BaseSpanModel> spanModels;
    private SpanClickListener listener;

    public SpanTextView(Context context) {
        super(context);
    }

    public SpanTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpanTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    // 设置文本内容
    public void setText(List<BaseSpanModel> spanModels, SpanClickListener listener) {
        this.spanModels = spanModels;
        this.listener = listener;
        this.setClickable(true);
        this.setMovementMethod(LinkMovementMethod.getInstance());
        //循环取出文本对象
        for (int i = 0; i < spanModels.size(); i++) {
            BaseSpanModel baseSpanModel = spanModels.get(i);
            SpannableString spannableString;
            if (baseSpanModel instanceof ClickSpanModel) {
                spannableString = getClickableSpan(i, (ClickSpanModel) baseSpanModel);
            } else {
                spannableString = new SpannableString(baseSpanModel.getContent());
            }
            //  设置或追加文本内容
            if (i == 0) {
                this.setText(spannableString);
            } else {
                if(spannableString!=null)
                this.append(spannableString);
            }
        }
    }



    private SpannableString getClickableSpan(int position, ClickSpanModel spanModel) {
        SpannableString spannableString = null;
        if(spanModel!=null&& spanModel.getContent()!=null&&spanModel.getContent().length()>0) {
            spannableString = new SpannableString(spanModel.getContent());
            int start = 0;
            int end = spannableString.length();
            //这一行是实现局部点击效果，实现Clickable（自定义的继承ClickableSpan implements OnClickListener）
            spannableString.setSpan(new MyClickableSpan(position), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //这一行是设置文字颜色的
            if (position % 2 == 0)
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#B0B0B0")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            else
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4289DA")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //这一行主要是用来消除点击文字下划线的
            spannableString.setSpan(new NoUnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    class MyClickableSpan extends ClickableSpan implements OnClickListener {
        private int position;

        public MyClickableSpan(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.OnClickListener(position);
            }
            //为了取消点击View效果（某些情况失效）
            setText(spanModels, listener);
        }
    }

    public class NoUnderlineSpan extends UnderlineSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }

    public interface SpanClickListener {
        void OnClickListener(int position);
    }

    public static class BaseSpanModel {

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class ClickSpanModel extends BaseSpanModel {
        private int id;

        public ClickSpanModel() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class TextSpanModel extends BaseSpanModel {
    }
}