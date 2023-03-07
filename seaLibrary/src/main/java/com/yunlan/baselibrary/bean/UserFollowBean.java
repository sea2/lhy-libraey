package com.yunlan.baselibrary.bean;

import android.text.TextUtils;

import com.yunlan.baselibrary.util.StringUtils;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lhy on 2022/5/10.
 */

public class UserFollowBean {

    @SerializedName("players")
    private List<PlayersDTO> players;

    public List<PlayersDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayersDTO> players) {
        this.players = players;
    }

    public static class PlayersDTO {
        @SerializedName("aid")
        private String aid;
        @SerializedName("clv")
        private Integer clv;
        @SerializedName("name")
        private String name;
        @SerializedName("name_pinyin")
        private String namePinyin;
        @SerializedName("head")
        private String head;
        @SerializedName("head_frame")
        private String headFrame;
        @SerializedName("cert")
        private Boolean cert;
        @SerializedName("cert_msg")
        private String certMsg;
        @SerializedName("remark_name")
        private String remarkName;
        //星标
        @SerializedName("star_target")
        private boolean starTarget;

        public boolean isStarTarget() {
            return starTarget;
        }

        public void setStarTarget(boolean starTarget) {
            this.starTarget = starTarget;
        }

        public String getRemarkName() {
            return remarkName;
        }

        public void setRemarkName(String remarkName) {
            this.remarkName = remarkName;
        }


        /***
         * 获取悬浮栏文本，（#、定位、热门 需要特殊处理）
         * @return
         */
        public String getSection() {
            if (starTarget) {
                return "★";
            } else if (TextUtils.isEmpty(namePinyin)) {
                return "{";
            } else {
                String c = namePinyin.substring(0, 1);
                Pattern p = Pattern.compile("[a-zA-Z]");
                Matcher m = p.matcher(c);
                if (m.matches()) {
                    return c.toUpperCase();
                }
                //在添加定位和热门数据时设置的section就是‘定’、’热‘开头
//                else if (TextUtils.equals(c, "定") || TextUtils.equals(c, "热"))
//                    return namePinyin;
                else
                    return "{";
            }
        }


        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public Integer getClv() {
            return clv;
        }

        public void setClv(Integer clv) {
            this.clv = clv;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNamePinyin() {
            if(starTarget){
               return "*";
            }else{
                if (TextUtils.isEmpty(namePinyin)) {
                    return "{";
                }else{
                    if(namePinyin.length()>0){
                       String str= namePinyin.substring(0,1);
                       if(!StringUtils.checkStr(str)){
                           return "{";
                       }
                    }
                }
                return namePinyin;
            }

        }




        public void setNamePinyin(String namePinyin) {
            this.namePinyin = namePinyin;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getHeadFrame() {
            return headFrame;
        }

        public void setHeadFrame(String headFrame) {
            this.headFrame = headFrame;
        }

        public Boolean getCert() {
            return cert;
        }

        public void setCert(Boolean cert) {
            this.cert = cert;
        }

        public String getCertMsg() {
            return certMsg;
        }

        public void setCertMsg(String certMsg) {
            this.certMsg = certMsg;
        }
    }


}
