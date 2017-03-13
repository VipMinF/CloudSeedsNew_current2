package com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public class YZTZBean {

        /**
         * show_code : 1
         * show_msg : 列表获取成功
         * all_count : 3
         * all_pages : 1
         * now_pagenum : 1
         * now_pagesize : 10
         * list : [{"name":"dsadasd","secondary_id":"12121_21212","plant_code":"品种分类","seed_batches":"种子批号","time":"2017-3-2 10:02:51","status":"2","sow_num":"50","confirm_num":"30"},{"name":"dsadasd","secondary_id":"12121_21212","plant_code":"品种分类","seed_batches":"种子批号","time":"2017-3-2 10:02:51","status":"2","sow_num":"50","confirm_num":"30"},{"name":"dsadasd","secondary_id":"12121_21212","plant_code":"品种分类","seed_batches":"种子批号","time":"2017-3-2 10:02:51","status":"2","sow_num":"50","confirm_num":"30"}]
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
             * secondary_id : 12121_21212
             * plant_code : 品种分类
             * seed_batches : 种子批号
             * time : 2017-3-2 10:02:51
             * status : 2
             * sow_num : 50
             * confirm_num : 30
             */

            private String name;
            private String secondary_id;
            private String plant_code;
            private String seed_batches;
            private String time;
            private String status;
            private String sow_num;
            private String confirm_num;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSecondary_id() {
                return secondary_id;
            }

            public void setSecondary_id(String secondary_id) {
                this.secondary_id = secondary_id;
            }

            public String getPlant_code() {
                return plant_code;
            }

            public void setPlant_code(String plant_code) {
                this.plant_code = plant_code;
            }

            public String getSeed_batches() {
                return seed_batches;
            }

            public void setSeed_batches(String seed_batches) {
                this.seed_batches = seed_batches;
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

            public String getSow_num() {
                return sow_num;
            }

            public void setSow_num(String sow_num) {
                this.sow_num = sow_num;
            }

            public String getConfirm_num() {
                return confirm_num;
            }

            public void setConfirm_num(String confirm_num) {
                this.confirm_num = confirm_num;
            }
        }
}
