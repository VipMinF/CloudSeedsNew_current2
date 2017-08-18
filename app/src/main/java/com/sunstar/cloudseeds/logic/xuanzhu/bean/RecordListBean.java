package com.sunstar.cloudseeds.logic.xuanzhu.bean;

import java.util.List;

/**
 * Created by xiaoxian on 2017/5/2.
 */
/*
 {"code":1,"message":"请求成功","info":[{"show_code":"1","show_msg":"日常记录数据列表获取成功","all_count":1,"list":[{"time":"2017-05-02","detail":"GG","image":[]}]}]}
* */

public class RecordListBean {
    private String code;
    private String message;
    private List<Info> info;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setInfo(List info) {
        this.info = info;
    }

    public List<Info> getInfo() {
        return info;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public static  class Info {
        private String  show_code;
        private String show_msg;
        private String all_count;
        private List<ListInfo> list;

        public void setAll_count(String all_count) {
            this.all_count = all_count;
        }

        public void setList(List list) {
            this.list = list;
        }

        public void setShow_code(String show_code) {
            this.show_code = show_code;
        }

        public void setShow_msg(String show_msg) {
            this.show_msg = show_msg;
        }

        public List<ListInfo> getList() {
            return list;
        }

        public String getAll_count() {
            return all_count;
        }

        public String getShow_code() {
            return show_code;
        }

        public String getShow_msg() {
            return show_msg;
        }

    }

    public static class ListInfo {
        private String time;
        private String detail;
        private List<imageInfo> image;
        private String id;
        private String farm_type_name;
        private String result_data_id;

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setImage(List image) {
            this.image = image;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setFarm_type_name(String farm_type_name) {
            this.farm_type_name = farm_type_name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setResult_data_id(String result_data_id) {
            this.result_data_id = result_data_id;
        }

        public String getFarm_type_name() {
            return farm_type_name;
        }

        public String getId() {
            return id;
        }

        public String getResult_data_id() {
            return result_data_id;
        }

        public List<imageInfo> getImage() {
            return image;
        }

        public String getDetail() {
            return detail;
        }

        public String getTime() {
            return time;
        }
    }

    public static class imageInfo {
      private  String code;
      private  String Oldfilename;
      private  String WsmallImageName;
      private  String SmallImageName;
      private  String BigImageName;

        public void setBigImageName(String bigImageName) {
            BigImageName = bigImageName;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setOldfilename(String oldfilename) {
            Oldfilename = oldfilename;
        }

        public void setSmallImageName(String smallImageName) {
            SmallImageName = smallImageName;
        }

        public void setWsmallImageName(String wsmallImageName) {
            WsmallImageName = wsmallImageName;
        }

        public String getBigImageName() {
            return BigImageName;
        }

        public String getCode() {
            return code;
        }

        public String getOldfilename() {
            return Oldfilename;
        }

        public String getSmallImageName() {
            return SmallImageName;
        }

        public String getWsmallImageName() {
            return WsmallImageName;
        }

    }

}
