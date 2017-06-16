package com.it.cloudwater.home.bean;

/**
 * Created by XY on 2016/9/17.
 */
public class BannerDto {

    /**
     * id : 1
     * imageUrl : http://odog3v89f.bkt.clouddn.com/banner/banner1.png
     * bannerTitle : 游离在正邪边缘的人:残袍
     * courseId : 29286
     */

    private int id;
    private String imageUrl;
    private String bannerTitle;
    private int courseId;

    public BannerDto(String bannerTitle, String imageUrl, int id, int courseId) {
        this.bannerTitle = bannerTitle;
        this.imageUrl = imageUrl;
        this.id = id;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
