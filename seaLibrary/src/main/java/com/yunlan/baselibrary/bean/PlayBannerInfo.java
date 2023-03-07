package com.yunlan.baselibrary.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lhy on 2022/10/28.
 */



public  class PlayBannerInfo {

    @SerializedName("banner_list")
    private List<BannerListDTO> bannerList;
    @SerializedName("total_plot_num")
    private String totalPlotNum;

    public List<BannerListDTO> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListDTO> bannerList) {
        this.bannerList = bannerList;
    }

    public String getTotalPlotNum() {
        return totalPlotNum;
    }

    public void setTotalPlotNum(String totalPlotNum) {
        this.totalPlotNum = totalPlotNum;
    }

    public static class BannerListDTO {
        @SerializedName("id")
        private String id;
        @SerializedName("icon")
        private String icon;
        @SerializedName("param")
        private String param;
        @SerializedName("sort")
        private Integer sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }
    }
}