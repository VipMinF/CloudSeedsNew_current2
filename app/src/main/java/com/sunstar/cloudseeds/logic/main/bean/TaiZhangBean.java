package com.sunstar.cloudseeds.logic.main.bean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/10.
 */

public class TaiZhangBean {

        /**
         * show_code : 1
         * show_msg : 列表获取成功
         * all_count : 3
         * all_pages : 1
         * now_pagenum : 1
         * now_pagesize : 10
         * list : [{"name":"dsadasd","primary_id":"1212312","prot_ype":"品种分类","plan_type":"计划类型","time":"2017-3-2 10:02:51","status":"2"},{"name":"dsadasd","primary_id":"1212312","prot_ype":"品种分类","plan_type":"计划类型","time":"2017-3-2 10:02:51","status":"2"},{"name":"dsadasd","primary_id":"1212312","pro_type":"品种分类","plan_type":"计划类型","time":"2017-3-2 10:02:51","status":"2"}]
         */

        private String show_code;
        private String show_msg;
        private String all_count;
        private String all_pages;
        private String now_pagenum;
        private String now_pagesize;
        private List<ListBean> list;

        public String getShow_code() {
            return show_code;
        }

        public void setShow_code(String show_code) {
            this.show_code = show_code;
        }

        public String getShow_msg() {
            return show_msg;
        }

        public void setShow_msg(String show_msg) {
            this.show_msg = show_msg;
        }

        public String getAll_count() {
            return all_count;
        }

        public void setAll_count(String all_count) {
            this.all_count = all_count;
        }

        public String getAll_pages() {
            return all_pages;
        }

        public void setAll_pages(String all_pages) {
            this.all_pages = all_pages;
        }

        public String getNow_pagenum() {
            return now_pagenum;
        }

        public void setNow_pagenum(String now_pagenum) {
            this.now_pagenum = now_pagenum;
        }

        public String getNow_pagesize() {
            return now_pagesize;
        }

        public void setNow_pagesize(String now_pagesize) {
            this.now_pagesize = now_pagesize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * name : dsadasd
             * primary_id : 1212312
             * prot_ype : 品种分类
             * plan_type : 计划类型
             * time : 2017-3-2 10:02:51
             * status : 2
             * pro_type : 品种分类
             */

            private String name;
            private String primary_id;
            private String prot_ype;
            private String plan_type;
            private String time;
            private String status;
            private String pro_type;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrimary_id() {
                return primary_id;
            }

            public void setPrimary_id(String primary_id) {
                this.primary_id = primary_id;
            }

            public String getProt_ype() {
                return prot_ype;
            }

            public void setProt_ype(String prot_ype) {
                this.prot_ype = prot_ype;
            }

            public String getPlan_type() {
                return plan_type;
            }

            public void setPlan_type(String plan_type) {
                this.plan_type = plan_type;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPro_type() {
                return pro_type;
            }

            public void setPro_type(String pro_type) {
                this.pro_type = pro_type;
            }
        }
}
