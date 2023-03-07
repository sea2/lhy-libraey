package com.yunlan.baselibrary.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lhy on 2022/5/7.
 */

public class InsertInfoBean {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("href")
    private String href;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
