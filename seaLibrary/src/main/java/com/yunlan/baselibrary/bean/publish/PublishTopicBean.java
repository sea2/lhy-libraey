package com.yunlan.baselibrary.bean.publish;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lhy on 2022/5/11.
 */

public class PublishTopicBean {


    @SerializedName("insert")
    private InsertDTO insert;

    public InsertDTO getInsert() {
        return insert;
    }

    public void setInsert(InsertDTO insert) {
        this.insert = insert;
    }

    public static class InsertDTO {
        @SerializedName("topic")
        private TopicDTO topic;

        public TopicDTO getTopic() {
            return topic;
        }

        public void setTopic(TopicDTO topic) {
            this.topic = topic;
        }

        public static class TopicDTO {

            public TopicDTO(String id, String name) {
                this.id = id;
                this.name = name;
            }

            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;

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
        }
    }
}
