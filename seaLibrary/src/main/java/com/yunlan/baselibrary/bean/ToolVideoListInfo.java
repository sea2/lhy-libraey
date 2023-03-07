package com.yunlan.baselibrary.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lhy on 2022/10/27.
 */

public  class ToolVideoListInfo {


    @SerializedName("menus")
    private List<MenusDTO> menus;
    @SerializedName("infos")
    private List<InfosDTO> infos;

    public void setMenus(List<MenusDTO> menus) {
        this.menus = menus;
    }

    public List<MenusDTO> getMenus() {
        return menus;
    }

    public List<InfosDTO> getInfos() {
        return infos;
    }

    public void setInfos(List<InfosDTO> infos) {
        this.infos = infos;
    }

    public static class MenusDTO {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("remark")
        private String remark;
        @SerializedName("icon")
        private String icon;

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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class InfosDTO {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("remark")
        private String remark;
        @SerializedName("video_url")
        private String videoUrl;
        @SerializedName("video_icon")
        private String videoIcon;
        @SerializedName("video_top_icon")
        private String videoTopIcon;

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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getVideoIcon() {
            return videoIcon;
        }

        public void setVideoIcon(String videoIcon) {
            this.videoIcon = videoIcon;
        }

        public String getVideoTopIcon() {
            return videoTopIcon;
        }

        public void setVideoTopIcon(String videoTopIcon) {
            this.videoTopIcon = videoTopIcon;
        }
    }
}