package com.yunlan.baselibrary.bean.publish;


import com.google.gson.annotations.SerializedName;

/**
 * Created by lhy on 2022/5/11.
 */

public class PublishUserBean {


    @SerializedName("insert")
    private InsertDTO insert;

    public InsertDTO getInsert() {
        return insert;
    }

    public void setInsert(InsertDTO insert) {
        this.insert = insert;
    }

    public static class InsertDTO {
        @SerializedName("mention")
        private MentionDTO mention;

        public MentionDTO getMention() {
            return mention;
        }

        public void setMention(MentionDTO mention) {
            this.mention = mention;
        }

        public static class MentionDTO {
            public MentionDTO(String id, String name) {
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
