package com.yunlan.baselibrary.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.github.sea2.R;
import com.google.gson.Gson;
import com.yunlan.baselibrary.bean.CommunityRecommendBean;
import com.yunlan.baselibrary.bean.InsertInfoBean;
import com.yunlan.baselibrary.bean.LinkPublicBean;
import com.yunlan.baselibrary.bean.publish.PublishLinkBean;
import com.yunlan.baselibrary.bean.publish.PublishStringBean;
import com.yunlan.baselibrary.bean.publish.PublishTopicBean;
import com.yunlan.baselibrary.bean.publish.PublishUserBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.content.ContextCompat;

/**
 * Created by lhy on 2022/4/11.
 */

public class PatternUtils {


    /**
     * 例子 //  [{\"insert\":\"你好[李逍遥_可怜]\"},{\"insert\":{\"mention\":{\"id\":\"4913171\",\"name\":\"玩家1\"}}},{\"insert\":{\"topic\":{\"id\":\"1\",\"name\":\"仙剑话题\"}}},{\"insert\":{\"link\":{\"href\":\"https://www.bilibili.com/\",\"name\":\"跳转百度\"}}}]");
     *
     * @param contentText
     * @param linkPublicList
     * @return
     */
    public static String getUpContent(String contentText, List<LinkPublicBean> linkPublicList) {
        if (StringUtils.isEmpty(contentText)) {
            return "";
        }

        Pattern patternUrl = Pattern.compile("[a-zA-z]+://[^\\s]*");
        Matcher matcherUrl = patternUrl.matcher(contentText);
        while (matcherUrl.find()) {
            int start = matcherUrl.start();
            int end = matcherUrl.end();
            String content = contentText.subSequence(start, end).toString();
            if (linkPublicList == null) {
                linkPublicList = new ArrayList<>();
            }
            linkPublicList.add(new LinkPublicBean(3, content, content));
        }


        if (linkPublicList == null || linkPublicList.size() == 0) {
            List<PublishStringBean> list = new ArrayList<>();
            list.add(new PublishStringBean(contentText));
            return new Gson().toJson(list);
        } else {
            List<String> list = new ArrayList<>();
            Pattern pattern = null;
//             pattern = Pattern.compile("#[^@#]+?#|@[-_a-zA-Z0-9\\u4E00-\\u9FA5]+\\s");
            pattern = Pattern.compile("#[^@#]+?#|@[-_a-zA-Z0-9\\u4E00-\\u9FA5]+|[a-zA-z]+://[^\\s]*");
            Matcher matcherUser = pattern.matcher(contentText);
            int endIndex = 0;
            while (matcherUser.find()) {
                int start = matcherUser.start();
                int end = matcherUser.end();
                String content = contentText.subSequence(matcherUser.start(), matcherUser.end()).toString();
                if (matcherUser.start() != 0) {
                    String frontStr = contentText.subSequence(endIndex, matcherUser.start()).toString();
                    endIndex = end;
                    list.add(frontStr);
                }
                list.add(content);
            }

            if (endIndex != 0 && endIndex != contentText.length()) {
                String frontStr = contentText.substring(endIndex).toString();
                list.add(frontStr);
            }

            if (list.size() > 0) {

                List<Object> resultList = new ArrayList<>();
                for (String str : list) {
                    if (StringUtils.isContains(str, "@")) {
                        LinkPublicBean mLinkPublicBean = getUserBean(str, linkPublicList);
                        if (mLinkPublicBean != null) {//用户
                            PublishUserBean publishUserBean = new PublishUserBean();
                            PublishUserBean.InsertDTO insert = new PublishUserBean.InsertDTO();
                            insert.setMention(new PublishUserBean.InsertDTO.MentionDTO(mLinkPublicBean.getUrl(), mLinkPublicBean.getKey()));
                            publishUserBean.setInsert(insert);
                            resultList.add(publishUserBean);
                        } else {
                            PublishStringBean publishStringBean = new PublishStringBean(str);
                            resultList.add(publishStringBean);
                        }
                    } else if (StringUtils.isContains(str, "#")) {
                        LinkPublicBean mLinkPublicBean = getTopicBean(str, linkPublicList);
                        if (mLinkPublicBean != null) {//话题
                            PublishTopicBean publishTopicBean = new PublishTopicBean();
                            PublishTopicBean.InsertDTO insert = new PublishTopicBean.InsertDTO();
                            insert.setTopic(new PublishTopicBean.InsertDTO.TopicDTO(mLinkPublicBean.getUrl(), mLinkPublicBean.getKey()));
                            publishTopicBean.setInsert(insert);
                            resultList.add(publishTopicBean);
                        } else {
                            PublishStringBean publishStringBean = new PublishStringBean(str);
                            resultList.add(publishStringBean);
                        }
                    } else if (StringUtils.isNotEmpty(str) && str.toLowerCase().startsWith("http")) {
                        PublishLinkBean publishLinkBean = new PublishLinkBean();
                        PublishLinkBean.InsertBean insert = new PublishLinkBean.InsertBean();
                        insert.setLink(new PublishLinkBean.InsertBean.LinkBean(str, ""));
                        publishLinkBean.setInsert(insert);
                        resultList.add(publishLinkBean);
                    } else {
                        PublishStringBean publishStringBean = new PublishStringBean(str);
                        resultList.add(publishStringBean);
                    }
                }
                return new Gson().toJson(resultList);
            } else {
                List<PublishStringBean> list2 = new ArrayList<>();
                list2.add(new PublishStringBean(contentText));
                return new Gson().toJson(list2);
            }

        }
    }

    /**
     * 通过 后台的富文本合集转为 本地集合
     *
     * @param contentDTOList
     * @return * [{"insert":"你好[李逍遥_可怜]"},
     * * {"insert":{"mention":{"id":"4913171","name":"玩家1"}}},
     * * {"insert":{"topic":{"id":"1","name":"仙剑话题"}}},
     * * {"insert":{"link":{"href":"https://www.bilibili.com/","name":"跳转百度"}}}]
     */
    public static List<LinkPublicBean> getLinkPublicList(List<CommunityRecommendBean.MomentsDTO.StructContentDTO> contentDTOList) {
        List<LinkPublicBean> linkPublicList = new ArrayList<>();
        if (contentDTOList != null && contentDTOList.size() > 0) {
            for (CommunityRecommendBean.MomentsDTO.StructContentDTO structContent : contentDTOList) {
                String str = structContent.getInsert();
                if (StringUtils.isNotEmpty(str) && StringUtils.isJSON2(str)) {
                    JSONObject jsonObject = null;
                    String json = null;
                    try {
                        jsonObject = new JSONObject(str);
                        if (jsonObject.has("mention")) {//用户
                            JSONObject jsonObjectMention = jsonObject.getJSONObject("mention");
                            LinkPublicBean linkPublicBeanLast = new LinkPublicBean(1, jsonObjectMention.getString("name"), jsonObjectMention.getString("id"));
                            linkPublicList.add(linkPublicBeanLast);
                        } else if (jsonObject.has("topic")) {
                            JSONObject jsonObjectMention = jsonObject.getJSONObject("topic");
                            LinkPublicBean linkPublicBeanLast = new LinkPublicBean(2, jsonObjectMention.getString("name"), jsonObjectMention.getString("id"));
                            linkPublicList.add(linkPublicBeanLast);
                        } else if (jsonObject.has("link")) {
                            JSONObject jsonObjectMention = jsonObject.getJSONObject("link");
                            LinkPublicBean linkPublicBeanLast = new LinkPublicBean(3, jsonObjectMention.getString("name"), jsonObjectMention.getString("href"));
                            linkPublicList.add(linkPublicBeanLast);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return linkPublicList;
    }


    private static LinkPublicBean getUserBean(String endUser, List<LinkPublicBean> linkPublicList) {
        if (linkPublicList != null && linkPublicList.size() > 0) {
            for (LinkPublicBean mLinkPublicBean : linkPublicList) {
                if (StringUtils.isEquals("@".concat(mLinkPublicBean.getKey()).trim(), endUser)) {
                    return mLinkPublicBean;
                }
            }
        }
        return null;
    }

    private static LinkPublicBean getTopicBean(String endTopic, List<LinkPublicBean> linkPublicList) {
        if (linkPublicList != null && linkPublicList.size() > 0) {
            for (LinkPublicBean mLinkPublicBean : linkPublicList) {
                if (StringUtils.isEquals("#".concat(mLinkPublicBean.getKey()).concat("#"), endTopic)) {
                    return mLinkPublicBean;
                }
            }
        }
        return null;
    }


    public interface PatternListener {
        void callBack(String content);
    }


    /**
     * 话题
     *
     * @param spannableString
     * @param mPatternListener
     */
    public static void topicPattern(Context context,SpannableString spannableString,List<CommunityRecommendBean.MomentsDTO.StructContentDTO> contentDTOList, PatternListener mPatternListener) {

        Pattern pattern = null;
        pattern = Pattern.compile("#[^@#]+?#");
        Matcher matcherUser = pattern.matcher(spannableString);
        while (matcherUser.find()) {
            int start = matcherUser.start();
            int end = matcherUser.end();
            String content = spannableString.subSequence(matcherUser.start(), matcherUser.end()).toString();
            if (StringUtils.isNotEmpty(content)) {
                if (StringUtils.isNotEmpty(content)) {
                    InsertInfoBean mInsertInfoBean = getInfoInsert(1, content, contentDTOList);
                    if(mInsertInfoBean!=null) {
                        ClickableSpan clickableSpanUser = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                if (mPatternListener != null) {
                                    mPatternListener.callBack(content);
                                }
                            }

                            public void updateDrawState(TextPaint ds) {
                                ds.setColor(ContextCompat.getColor(context, R.color.color_theme_linking2));    //设置超链接颜色
                                ds.setUnderlineText(false); //超链接去掉下划线
                            }
                        };
                        spannableString.setSpan(clickableSpanUser,
                                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }
    }
    public static void topicPattern(Context context,SpannableString spannableString) {

        Pattern pattern = null;
        pattern = Pattern.compile("#[^@#]+?#");
        Matcher matcherUser = pattern.matcher(spannableString);
        while (matcherUser.find()) {
            int start = matcherUser.start();
            int end = matcherUser.end();
            String content = spannableString.subSequence(matcherUser.start(), matcherUser.end()).toString();
            if (StringUtils.isNotEmpty(content)) {
                if (StringUtils.isNotEmpty(content)) {
                        ClickableSpan clickableSpanUser = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                            }

                            public void updateDrawState(TextPaint ds) {
                                ds.setColor(ContextCompat.getColor(context, R.color.color_theme_linking2));    //设置超链接颜色
                                ds.setUnderlineText(false); //超链接去掉下划线
                            }
                        };
                        spannableString.setSpan(clickableSpanUser,
                                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }

    /**
     * 链接
     *
     * @param spannableString
     * @param mPatternListener
     */
    public static void aPattern(Context context,SpannableString spannableString, PatternListener mPatternListener) {
        Pattern pattern = null;
        pattern = Pattern.compile("<a>[^@#]+?</a>");
        Matcher matcherUser = pattern.matcher(spannableString);
        while (matcherUser.find()) {
            int start = matcherUser.start();
            int end = matcherUser.end();
            String content = spannableString.subSequence(matcherUser.start(), matcherUser.end()).toString();
            if (StringUtils.isNotEmpty(content)) {
                ClickableSpan clickableSpanUser = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        if (mPatternListener != null) {
                            mPatternListener.callBack(content);
                        }
                    }

                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(ContextCompat.getColor(context, R.color.color_theme_linking2));    //设置超链接颜色
                        ds.setUnderlineText(false); //超链接去掉下划线
                    }
                };
                spannableString.setSpan(clickableSpanUser,
                        start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                spannableString.setSpan(new ImageSpan(context, R.drawable.ic_tool_dot_small), start, start + 3,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ImageSpan(context, R.drawable.ic_tool_dot_small), end - 4, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    /**
     * 用户
     *
     * @param spannableString
     * @param mPatternListener
     */
    public static void userPattern(Context context,SpannableString spannableString,List<CommunityRecommendBean.MomentsDTO.StructContentDTO> contentDTOList, PatternListener mPatternListener) {

        Pattern patternUser = null;
        patternUser = Pattern.compile("@[-_a-zA-Z0-9\\u4E00-\\u9FA5]+");  //这里是过滤出[XX]这种形式的字符串，下面是把这种形式的字符串替换成对应的表情
        Matcher matcherUser = patternUser.matcher(spannableString);
        while (matcherUser.find()) {
            int start = matcherUser.start();
            int end = matcherUser.end();
            String content = spannableString.subSequence(matcherUser.start(), matcherUser.end()).toString();
            InsertInfoBean mInsertInfoBean=  getInfoInsert(2,content,contentDTOList);
            if(mInsertInfoBean!=null) {
                ClickableSpan clickableSpanUser = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        if (mPatternListener != null) {
                            mPatternListener.callBack(content);
                        }
                    }

                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(ContextCompat.getColor(context, R.color.color_theme_linking2));    //设置超链接颜色
                        ds.setUnderlineText(false); //超链接去掉下划线
                    }
                };
                spannableString.setSpan(clickableSpanUser,
                        start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
    public static void userPattern(Context context,SpannableString spannableString) {
        Pattern patternUser = null;
        patternUser = Pattern.compile("@[-_a-zA-Z0-9\\u4E00-\\u9FA5]+");  //这里是过滤出[XX]这种形式的字符串，下面是把这种形式的字符串替换成对应的表情
        Matcher matcherUser = patternUser.matcher(spannableString);
        while (matcherUser.find()) {
            int start = matcherUser.start();
            int end = matcherUser.end();
            String content = spannableString.subSequence(matcherUser.start(), matcherUser.end()).toString();
                ClickableSpan clickableSpanUser = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                    }

                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(ContextCompat.getColor(context, R.color.color_theme_linking2));    //设置超链接颜色
                        ds.setUnderlineText(false); //超链接去掉下划线
                    }
                };
                spannableString.setSpan(clickableSpanUser,
                        start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * 搜索
     * @param spannableString
     * @param searchKey
     */
    public static void searchKeyPattern(SpannableString spannableString, String searchKey) {
        if (StringUtils.isEmpty(searchKey)) {
            return;
        }
        Pattern patternUser = null;
        patternUser = Pattern.compile(searchKey);  //这里是过滤出[XX]这种形式的字符串，下面是把这种形式的字符串替换成对应的表情
        Matcher matcherUser = patternUser.matcher(spannableString);
        while (matcherUser.find()) {
            int start = matcherUser.start();
            int end = matcherUser.end();
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DFAA44")),
                    start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }







    /**
     * 表情
     *
     * @param spannableString
     */
    public static void emojiPattern(Context context, SpannableString spannableString) {

        Pattern pattern = null;
        pattern = Pattern.compile("\\[(\\S+?)\\]");  //这里是过滤出[XX]这种形式的字符串，下面是把这种形式的字符串替换成对应的表情
        Matcher matcher = pattern.matcher(spannableString);
        Integer drawableSrc = null;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String content = spannableString.subSequence(matcher.start(), matcher.end()).toString();
            if (StringUtils.isNotEmpty(content)) {
                drawableSrc = EmojiUtil.getInstance().getMap().get(content.trim());
                if (drawableSrc != null && drawableSrc > 0) {
                    //自定义图片大小
//                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableSrc);
//                    bitmap = Bitmap.createScaledBitmap(bitmap, DensityUtil.dip2px(context,12), DensityUtil.dip2px(context,12), true);
//                    ImageSpan imageSpan = new ImageSpan(context, bitmap, DynamicDrawableSpan.ALIGN_BASELINE);

                    spannableString.setSpan(new ImageSpan(context, drawableSrc), start, end,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }

    /** 表情-小类型
     * @param context
     * @param spannableString
     */
    public static void emojiSmallPattern(Context context, SpannableString spannableString) {

        Pattern pattern = null;
        pattern = Pattern.compile("\\[(\\S+?)\\]");  //这里是过滤出[XX]这种形式的字符串，下面是把这种形式的字符串替换成对应的表情
        Matcher matcher = pattern.matcher(spannableString);
        Integer drawableSrc = null;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String content = spannableString.subSequence(matcher.start(), matcher.end()).toString();
            if (StringUtils.isNotEmpty(content)) {
                drawableSrc = EmojiUtil.getInstance().getMap().get(content.trim());
                if (drawableSrc != null && drawableSrc > 0) {
                    //自定义图片大小
//                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableSrc);
//                    bitmap = Bitmap.createScaledBitmap(bitmap, DensityUtil.dip2px(context,5), DensityUtil.dip2px(context,5), true);
//                    ImageSpan imageSpan = new ImageSpan(context, bitmap, DynamicDrawableSpan.ALIGN_BASELINE);
                    Drawable d = ContextCompat.getDrawable(context, drawableSrc);
                    d.setBounds(0, 0, d.getIntrinsicWidth()/2, d.getIntrinsicHeight()/2);
                    ImageSpan imageSpan = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                    spannableString.setSpan(imageSpan, start, end,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }


    /**
     * @param type           1-话题    2-用户    3-链接
     * @param key
     * @param contentDTOList
     * @return * [{"insert":"你好[李逍遥_可怜]"},
     * * {"insert":{"mention":{"id":"4913171","name":"玩家1"}}},
     * * {"insert":{"topic":{"id":"1","name":"仙剑话题"}}},
     * * {"insert":{"link":{"href":"https://www.bilibili.com/","name":"跳转百度"}}}]
     */
    public static InsertInfoBean getInfoInsert(int type, String key, List<CommunityRecommendBean.MomentsDTO.StructContentDTO> contentDTOList) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        if (contentDTOList == null || contentDTOList.size() == 0) {
            return null;
        }

        if (type == 1) {
            for (CommunityRecommendBean.MomentsDTO.StructContentDTO structContent : contentDTOList) {
                String str = structContent.getInsert();
                if (StringUtils.isNotEmpty(str) && StringUtils.isJSON2(str)) {
                    if (StringUtils.isContains(str, "topic")) {
                        JSONObject jsonObject = null;
                        String json = null;
                        try {
                            jsonObject = new JSONObject(str);
                            json = jsonObject.getString("topic");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (StringUtils.isNotEmpty(json)) {
                            InsertInfoBean mInsertInfoBean = new Gson().fromJson(json, InsertInfoBean.class);
                            if (StringUtils.isEquals("#" + mInsertInfoBean.getName() + "#", key.trim())) {
                                return mInsertInfoBean;
                            }
                        }
                    }
                }
            }
        } else if (type == 2) {
            for (CommunityRecommendBean.MomentsDTO.StructContentDTO structContent : contentDTOList) {
                String str = structContent.getInsert();
                if (StringUtils.isNotEmpty(str) && StringUtils.isJSON2(str)) {
                    if (StringUtils.isContains(str, "mention")) {
                        JSONObject jsonObject = null;
                        String json = null;
                        try {
                            jsonObject = new JSONObject(str);
                            json = jsonObject.getString("mention");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (StringUtils.isNotEmpty(json)) {
                            InsertInfoBean mInsertInfoBean = new Gson().fromJson(json, InsertInfoBean.class);
                            if (StringUtils.isEquals("@" + mInsertInfoBean.getName(), key.trim())) {
                                return mInsertInfoBean;
                            }
                        }
                    }
                }
            }
        } else if (type == 3) {
            for (CommunityRecommendBean.MomentsDTO.StructContentDTO structContent : contentDTOList) {
                String str = structContent.getInsert();
                if (StringUtils.isNotEmpty(str) && StringUtils.isJSON2(str)) {
                    if (StringUtils.isContains(str, "link")) {
                        JSONObject jsonObject = null;
                        String json = null;
                        try {
                            jsonObject = new JSONObject(str);
                            json = jsonObject.getString("link");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (StringUtils.isNotEmpty(json)) {
                            InsertInfoBean mInsertInfoBean = new Gson().fromJson(json, InsertInfoBean.class);
                            if (StringUtils.isEquals("<a>" + mInsertInfoBean.getName() + "</a>", key.trim())) {
                                return mInsertInfoBean;
                            }
                        }
                    }
                }
            }
        }
        return null;

    }


    private void aTest(TextView tvContent) {

        String urlText = "<a href='sh://h'>#话题# @用户   @1234  " +
                "\n" +
                "<h2>毕业的诗歌1</h2>\n" +

                "<p>　　我们把时光喂上了蜂蜜</p> [删除]<p>　　给钟表刷上了色彩</p><p>　　柔和了韶光</p><p>　　晕染了芳华</p>[删除]";
        Spanned spanned = HtmlUtils.fromHtml(urlText);
        CharSequence charSequence = HtmlUtils.getClickableHtml(spanned, new HtmlUtils.LinkCallBack() {

            @Override
            public void onClick(String url) {

            }
        });


        //表情符号过滤
        SpannableString spannableString = new SpannableString(urlText);
        Pattern pattern = null;
        pattern = Pattern.compile("\\[(\\S+?)\\]");  //这里是过滤出[XX]这种形式的字符串，下面是把这种形式的字符串替换成对应的表情
        Matcher matcher = pattern.matcher(spannableString);
        Integer drawableSrc = null;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
        }




        tvContent.setText(spannableString, TextView.BufferType.SPANNABLE);
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public static int getTopicPatternAmount(String content) {
        int num = 0;
        Pattern pattern = null;
        pattern = Pattern.compile("#[^@#]+?#");
        Matcher matcherUser = pattern.matcher(content);
        while (matcherUser.find()) {
            num++;
        }
        return num;
    }

    public static int getPatternEndIndex(int index, String content) {
        int num = 0;
        Pattern pattern = null;
        pattern = Pattern.compile("#[^@#]+?#|@[-_a-zA-Z0-9\\u4E00-\\u9FA5]+|[a-zA-z]+://[^\\s]*");
        Matcher matcherUser = pattern.matcher(content);
        while (matcherUser.find()) {
            int start = matcherUser.start();
            int end = matcherUser.end();
            if (index >= start && index <= end) {
                return end;
            }
        }
        return -1;
    }


}
