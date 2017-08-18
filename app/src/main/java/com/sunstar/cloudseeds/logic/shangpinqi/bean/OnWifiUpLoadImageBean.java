package com.sunstar.cloudseeds.logic.shangpinqi.bean;

import com.classichu.photoselector.imagespicker.ImagePickBean;

import java.io.Serializable;
import java.util.List;

/**
 * 在WiFi下上传商品期图片所需信息
 * Created by xiaoxian on 2017/6/13.
 */

public class OnWifiUpLoadImageBean implements Serializable {
    private  String itemid;
    private  String resultid;
    private  String plant_number;
    private  String companyid;
    private  ImagePickBean imagePickBean;
    private  String uploadUrl;
    private  int userID;
    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public void setImagePickBean(ImagePickBean imagePickBean) {
        this.imagePickBean = imagePickBean;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public void setPlant_number(String plant_number) {
        this.plant_number = plant_number;
    }

    public void setResultid(String resultid) {
        this.resultid = resultid;
    }

    public ImagePickBean getImagePickBean() {
        return imagePickBean;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCompanyid() {
        return companyid;
    }

    public String getItemid() {
        return itemid;
    }

    public String getPlant_number() {
        return plant_number;
    }

    public String getResultid() {
        return resultid;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public int getUserID() {
        return userID;
    }
}
