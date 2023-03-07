package com.yunlan.baselibrary.bean;

import java.io.Serializable;

/**
 * Created by lhy on 2022/4/11.
 */

public class LinkPublicBean implements Serializable {
    //name
    String key;
    //id 或链接
    String url;
    // 1： 用户   2： 话题      3 ：链接
    int type;

    public LinkPublicBean(int type, String key, String url) {
        this.key = key;
        this.url = url;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "LinkPublicBean{" +
                "key='" + key + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                '}';
    }
}
