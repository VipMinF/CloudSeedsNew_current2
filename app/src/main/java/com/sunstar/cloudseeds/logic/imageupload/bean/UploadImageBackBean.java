package com.sunstar.cloudseeds.logic.imageupload.bean;

/**
 * Created by louisgeek on 2017/3/27.
 */

public class UploadImageBackBean {



    /**
     * attachmentid : 404
     * attcode : 1
     * attname : IMG_20160621_163335.png
     * path : /uploadfile/image/20160707
     * filename : 201677101129702.png
     * smallimagefilename : 2016771011297114.png
     * microimagefilename : 20167710112912583011.png
     * micro250_250imagefilename : 20167710112957185104.png
     * cateid : 0
     * size : 50409
     * sizeunit : 0
     * shoottime : 2015-12-11 12:32:01
     * userid : 82
     * checkstate : 0
     * addtime : 2016-07-07 10:11:29
     * updatetime : 1900-1-1
     */

    private int attachmentid;
    private int attcode;
    private String attname;
    private String path;
    private String filename;
    private String smallimagefilename;
    private String microimagefilename;
    private String micro250_250imagefilename;
    private int cateid;
    private int size;
    private int sizeunit;
    private String shoottime;
    private int userid;
    private int checkstate;
    private String addtime;
    private String updatetime;

    public int getAttachmentid() {
        return attachmentid;
    }

    public void setAttachmentid(int attachmentid) {
        this.attachmentid = attachmentid;
    }

    public int getAttcode() {
        return attcode;
    }

    public void setAttcode(int attcode) {
        this.attcode = attcode;
    }

    public String getAttname() {
        return attname;
    }

    public void setAttname(String attname) {
        this.attname = attname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSmallimagefilename() {
        return smallimagefilename;
    }

    public void setSmallimagefilename(String smallimagefilename) {
        this.smallimagefilename = smallimagefilename;
    }

    public String getMicroimagefilename() {
        return microimagefilename;
    }

    public void setMicroimagefilename(String microimagefilename) {
        this.microimagefilename = microimagefilename;
    }

    public String getMicro250_250imagefilename() {
        return micro250_250imagefilename;
    }

    public void setMicro250_250imagefilename(String micro250_250imagefilename) {
        this.micro250_250imagefilename = micro250_250imagefilename;
    }

    public int getCateid() {
        return cateid;
    }

    public void setCateid(int cateid) {
        this.cateid = cateid;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSizeunit() {
        return sizeunit;
    }

    public void setSizeunit(int sizeunit) {
        this.sizeunit = sizeunit;
    }

    public String getShoottime() {
        return shoottime;
    }

    public void setShoottime(String shoottime) {
        this.shoottime = shoottime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCheckstate() {
        return checkstate;
    }

    public void setCheckstate(int checkstate) {
        this.checkstate = checkstate;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
