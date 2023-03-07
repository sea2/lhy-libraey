package com.yunlan.baselibrary.bean.publish;

/**
 * Created by lhy on 2022/6/29 0029.
 */

public class PublishLinkBean {


    private InsertBean insert;

    public InsertBean getInsert() {
        return insert;
    }

    public void setInsert(InsertBean insert) {
        this.insert = insert;
    }

    public static class InsertBean {
        private LinkBean link;

        public LinkBean getLink() {
            return link;
        }

        public void setLink(LinkBean link) {
            this.link = link;
        }

        public static class LinkBean {
            private String href;
            private String name;

            public LinkBean(String href, String name) {
                this.href = href;
                this.name = name;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
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
