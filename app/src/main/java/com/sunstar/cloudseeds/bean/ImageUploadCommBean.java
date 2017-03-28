package com.sunstar.cloudseeds.bean;

/**
 * Created by louisgeek on 2017/3/28.
 */

public class ImageUploadCommBean {

    /**
     * img_upload_key : zx_upload_img
     * img_upload_title : 株型图片上传
     * img_upload_max_count : 3
     */

    private String img_upload_key;
    private String img_upload_title;
    private String img_upload_max_count;

    public String getImg_upload_key() {
        return img_upload_key;
    }

    public void setImg_upload_key(String img_upload_key) {
        this.img_upload_key = img_upload_key;
    }

    public String getImg_upload_title() {
        return img_upload_title;
    }

    public void setImg_upload_title(String img_upload_title) {
        this.img_upload_title = img_upload_title;
    }

    public String getImg_upload_max_count() {
        return img_upload_max_count;
    }

    public void setImg_upload_max_count(String img_upload_max_count) {
        this.img_upload_max_count = img_upload_max_count;
    }
}
