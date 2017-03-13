package com.sunstar.cloudseeds.logic.yuzhongxuanzhu.bean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public class XuanZhuBean {


        /**
         * show_code : 1
         * show_msg : 列表获取成功
         * all_count : 3
         * all_pages : 1
         * now_pagenum : 1
         * now_pagesize : 10
         * list : [{"name":"dsadasd","tertiary_id":"21121_1212_1212","selected_code":"1","time":"2017-3-2 10:02:51","status":"2"},{"name":"dsadasd","tertiary_id":"21121_1212_1212","selected_code":"2","time":"2017-3-2 10:02:51","status":"2"},{"name":"dsadasd","tertiary_id":"21121_1212_1212","selected_code":"3","time":"2017-3-2 10:02:51","status":"2"}]
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
             * tertiary_id : 21121_1212_1212
             * selected_code : 1
             * time : 2017-3-2 10:02:51
             * status : 2
             */

            private String name;
            private String tertiary_id;
            private String selected_code;
            private String time;
            private String status;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTertiary_id() {
                return tertiary_id;
            }

            public void setTertiary_id(String tertiary_id) {
                this.tertiary_id = tertiary_id;
            }

            public String getSelected_code() {
                return selected_code;
            }

            public void setSelected_code(String selected_code) {
                this.selected_code = selected_code;
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
        }
}
