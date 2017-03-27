package com.sunstar.cloudseeds.logic.imageupload.bean;

/**
 * Created by louisgeek on 2017/3/27.
 */

public class UploadBase64ImgsBean {

    private int userID;
    private String companyid;
    private String plantnumber;
    private String oldFileName;
    private String shoottime;
    private String extension;
    private String base64ImgStr;

    public UploadBase64ImgsBean(int userID,String companyid,String plantnumber, String oldFileName, String shoottime, String extension, String base64ImgStr) {
        this.userID = userID;
        this.plantnumber = plantnumber;
        this.companyid = companyid;
        this.oldFileName = oldFileName;
        this.shoottime = shoottime;
        this.extension = extension;
        this.base64ImgStr = base64ImgStr;
    }

    public UploadBase64ImgsBean() {
        super();

    }

    public String getShoottime() {
        return shoottime;
    }

    public void setShoottime(String shoottime) {
        this.shoottime = shoottime;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getPlantnumber() {
        return plantnumber;
    }

    public void setPlantnumber(String plantnumber) {
        this.plantnumber = plantnumber;
    }

    public String getOldFileName() {
        return oldFileName;
    }
    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getBase64ImgStr() {
        return base64ImgStr;
    }
    public void setBase64ImgStr(String base64ImgStr) {
        this.base64ImgStr = base64ImgStr;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

}
