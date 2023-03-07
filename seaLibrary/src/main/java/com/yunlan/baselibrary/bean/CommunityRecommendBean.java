 package com.yunlan.baselibrary.bean;

 import com.google.gson.annotations.SerializedName;
import com.yunlan.baselibrary.util.StringUtils;

import java.io.Serializable;
import java.util.List;

 /**
 * Created by lhy on 2022/5/6.
 */

public class CommunityRecommendBean {



    @SerializedName("type")
    private String type;
    @SerializedName("page")
    private int page;
    @SerializedName("moments")
    private List<MomentsDTO> moments;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MomentsDTO> getMoments() {
        return moments;
    }

    public void setMoments(List<MomentsDTO> moments) {
        this.moments = moments;
    }




    public static class MomentsDTO implements MultiItemEntity {
        @SerializedName("id_str")
        private String idStr;
        @SerializedName("struct_content")
        private List<StructContentDTO> structContent;
        @SerializedName("content")
        private String content;
        @SerializedName("videos")
        private List<BVVideoBean> videos;
        @SerializedName("images")
        private List<ImagesDTO> images;
        @SerializedName("comments_count")
        private int commentsCount;
        @SerializedName("retransmission_num")
        private int retransmissionNum;
        @SerializedName("like_num")
        private long likeNum;
        @SerializedName("first_line_content")
        private String firstLineContent;
        @SerializedName("liked")
        private boolean liked;
        @SerializedName("official")
        private boolean official;
        //是否是原创
        @SerializedName("content_original")
        private boolean contentOriginal;

        //收藏
        @SerializedName("collect_num")
        private int collectNum;
        @SerializedName("collected")
        private boolean collected;
        @SerializedName("user")
        private UserDTO user;
        @SerializedName("from_user")
        private UserDTO fromUser;
        @SerializedName("type")
        private String type;
        @SerializedName("create_at")
        private String createAt;
        @SerializedName("subject")
        private String subject;
        @SerializedName("original_moment")
        private OriginalMoment originalMoment;
        //是原作还是转发
        @SerializedName("original")
        private boolean original;
        @SerializedName("personal_top")
        private boolean personalTop;
        @SerializedName("original_delete")
        private boolean originalDelete;
        @SerializedName("community_id")
        private String communityId;
        @SerializedName("community_name")
        private String communityName;


        //布局类型  1-图文显示一张图    2-图文显示2张图   3-图文显示3张图   4-图文显示4张图

        int itemType=0;

        @Override
        public int getItemType() {
            if(getOriginal()) { //原文
                if (images != null) {
                    if (images.size() == 1) {
                            if (StringUtils.isNotEmpty(images.get(0).getWidth()) && StringUtils.toInt(images.get(0).getWidth()) <= StringUtils.toInt(images.get(0).getHeight())) {
                                return 11;
                            }else{
                                return 1;
                            }
                    } else if (images.size() == 2) {
                        return 2;
                    } else if (images.size() == 3) {
                        return 3;
                    } else if (images.size() >= 4) {
                        return 4;
                    }
                }
            }else{//转发文

                if(originalDelete){ //转发文被删除的布局
                    return 5;
                }
                if(originalMoment!=null&& StringUtils.isEquals(originalMoment.getType(),"P")){
                    //剧情转发
                    return 6;
                }


                if(originalMoment!=null&&originalMoment.getImages()!=null) {
                    int sizeImg=originalMoment.getImages().size();
                    if (sizeImg == 1) {
                        if (StringUtils.isNotEmpty(originalMoment.getImages().get(0).getWidth()) && StringUtils.toInt(originalMoment.getImages().get(0).getWidth()) <= StringUtils.toInt(originalMoment.getImages().get(0).getHeight())) {
                            return 11;
                        }else{
                            return 1;
                        }
                    } else if (sizeImg == 2) {
                        return 2;
                    } else if (sizeImg == 3) {
                        return 3;
                    } else if (sizeImg >= 4) {
                        return 4;
                    }
                }
            }

            return itemType;
        }


        public boolean isContentOriginal() {
            return contentOriginal;
        }

        public void setContentOriginal(boolean contentOriginal) {
            this.contentOriginal = contentOriginal;
        }

        public boolean isOfficial() {
            return official;
        }

        public void setOfficial(boolean official) {
            this.official = official;
        }

        public String getFirstLineContent() {
            return firstLineContent;
        }

        public void setFirstLineContent(String firstLineContent) {
            this.firstLineContent = firstLineContent;
        }

        public boolean getPersonalTop() {
            return personalTop;
        }

        public void setPersonalTop(boolean personalTop) {
            this.personalTop = personalTop;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public OriginalMoment getOriginalMoment() {
            return originalMoment;
        }

        public void setOriginalMoment(OriginalMoment originalMoment) {
            this.originalMoment = originalMoment;
        }

        public boolean getOriginal() {
            return original;
        }

        public void setOriginal(boolean original) {
            this.original = original;
        }

        public boolean getOriginalDelete() {
            return originalDelete;
        }

        public void setOriginalDelete(boolean originalDelete) {
            this.originalDelete = originalDelete;
        }

        public int getCollectNum() {
            return collectNum;
        }

        public void setCollectNum(int collectNum) {
            this.collectNum = collectNum;
        }

        public boolean getCollected() {
            return collected;
        }

        public void setCollected(boolean collected) {
            this.collected = collected;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }


        public int getRetransmissionNum() {
            return retransmissionNum;
        }

        public void setRetransmissionNum(int retransmissionNum) {
            this.retransmissionNum = retransmissionNum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIdStr() {
            return idStr;
        }

        public void setIdStr(String idStr) {
            this.idStr = idStr;
        }

        public List<StructContentDTO> getStructContent() {
            return structContent;
        }

        public void setStructContent(List<StructContentDTO> structContent) {
            this.structContent = structContent;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<BVVideoBean> getVideos() {
            return videos;
        }

        public void setVideos(List<BVVideoBean> videos) {
            this.videos = videos;
        }

        public List<ImagesDTO> getImages() {
            return images;
        }

        public void setImages(List<ImagesDTO> images) {
            this.images = images;
        }

        public int getCommentsCount() {
            return commentsCount;
        }

        public void setCommentsCount(int commentsCount) {
            this.commentsCount = commentsCount;
        }


        public long getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(long likeNum) {
            this.likeNum = likeNum;
        }

        public boolean getLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public UserDTO getUser() {
            return user;
        }

        public UserDTO getFromUser() {
            return fromUser;
        }

        public void setFromUser(UserDTO fromUser) {
            this.fromUser = fromUser;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }

        public static class UserDTO implements Serializable {
            @SerializedName("aid")
            private String aid;
//            图片是 1- 10 蓝色    11-20 黄色
            @SerializedName("clv")
            private int clv;
            @SerializedName("name")
            private String name;
            @SerializedName("head")
            private String head;
            @SerializedName("head_frame")
            private String headFrame;
            @SerializedName("cert")
            private boolean cert;
            @SerializedName("cert_msg")
            private String certMsg;
            @SerializedName("c_title")
            private String cTitle;
            @SerializedName("follow")
            private boolean follow;

            public boolean isFollow() {
                return follow;
            }

            public void setFollow(boolean follow) {
                this.follow = follow;
            }

            public String getCTitle() {
                return cTitle;
            }

            public void setCTitle(String cTitle) {
                this.cTitle = cTitle;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public int getClv() {
                return clv;
            }

            public void setClv(int clv) {
                this.clv = clv;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public boolean getCert() {
                return cert;
            }

            public void setCert(boolean cert) {
                this.cert = cert;
            }

            public String getCertMsg() {
                return certMsg;
            }

            public void setCertMsg(String certMsg) {
                this.certMsg = certMsg;
            }
        }

        /**
         * [{"insert":"你好[李逍遥_可怜]"},
         * {"insert":{"mention":{"id":"4913171","name":"玩家1"}}},
         * {"insert":{"topic":{"id":"1","name":"仙剑话题"}}},
         * {"insert":{"link":{"href":"https://www.bilibili.com/","name":"跳转百度"}}}]
         */
        public static class StructContentDTO {
            @SerializedName("insert")
            private String insert;

            public String getInsert() {
                return insert;
            }

            public void setInsert(String insert) {
                this.insert = insert;
            }

        }

        public static class ImagesDTO {
            @SerializedName("url")
            private String url;
            private String format;
            private String height;
            private String width;

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
        public static class OriginalMoment {

            @SerializedName("id_str")
            private String idStr;
            @SerializedName("type")
            private String type;
            @SerializedName("subject")
            private String subject;
            @SerializedName("struct_content")
            private List<StructContentDTO> structContent;
            @SerializedName("content")
            private String content;
            @SerializedName("first_line_content")
            private String firstLineContent;
            @SerializedName("videos")
            private List<BVVideoBean> videos;
            @SerializedName("images")
            private List<ImagesDTO> images;
            @SerializedName("comments_count")
            private int commentsCount;
            @SerializedName("like_num")
            private int likeNum;
            @SerializedName("retransmission_num")
            private int retransmissionNum;
            @SerializedName("user")
            private UserDTO user;

            public UserDTO getUser() {
                return user;
            }

            public void setUser(UserDTO user) {
                this.user = user;
            }

            public String getIdStr() {
                return idStr;
            }

            public void setIdStr(String idStr) {
                this.idStr = idStr;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public List<StructContentDTO> getStructContent() {
                return structContent;
            }

            public void setStructContent(List<StructContentDTO> structContent) {
                this.structContent = structContent;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getFirstLineContent() {
                return firstLineContent;
            }

            public void setFirstLineContent(String firstLineContent) {
                this.firstLineContent = firstLineContent;
            }

            public List<BVVideoBean> getVideos() {
                return videos;
            }

            public void setVideos(List<BVVideoBean> videos) {
                this.videos = videos;
            }

            public List<ImagesDTO> getImages() {
                return images;
            }

            public void setImages(List<ImagesDTO> images) {
                this.images = images;
            }

            public int getCommentsCount() {
                return commentsCount;
            }

            public void setCommentsCount(int commentsCount) {
                this.commentsCount = commentsCount;
            }

            public int getLikeNum() {
                return likeNum;
            }

            public void setLikeNum(int likeNum) {
                this.likeNum = likeNum;
            }

            public int getRetransmissionNum() {
                return retransmissionNum;
            }

            public void setRetransmissionNum(int retransmissionNum) {
                this.retransmissionNum = retransmissionNum;
            }
        }
    }
}
