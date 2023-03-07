package com.yunlan.baselibrary.bean.publish;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lhy on 2022/5/11.
 */

public class PublishStringBean {
    public PublishStringBean(String insert) {
        this.insert = insert;
    }

    @SerializedName("insert")
    private String insert;

    public String getInsert() {
        return insert;
    }

    public void setInsert(String insert) {
        this.insert = insert;
    }
}
