package com.sunstar.cloudseeds.logic.main.bean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/21.
 */

public class ClassifyDataBean {

        /**
         * show_code : 1
         * show_msg : 品种分类获取成功
         * all_count : 3
         * list : [{"name":"十字花科","parent_code":"0","code":"1","child":[{"name":"甘蓝类","parent_code":"1","code":"12121"},{"name":"白菜类","parent_code":"1","code":"222222"}]},{"name":"禾本科","parent_code":"0","code":"2","child":[{"name":"玉米","parent_code":"2","code":"4123123"}]},{"name":"葫芦科","parent_code":"0","code":"3","child":[{"name":"黄瓜","parent_code":"3","code":"3232434343"},{"name":"瓜类","parent_code":"3","code":"41231244332323"}]}]
         */

        private String show_code;
        private String show_msg;
        private String all_count;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * name : 十字花科
             * parent_code : 0
             * code : 1
             * child : [{"name":"甘蓝类","parent_code":"1","code":"12121"},{"name":"白菜类","parent_code":"1","code":"222222"}]
             */

            private String name;
            private String parent_code;
            private String code;
            private List<ChildBean> child;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParent_code() {
                return parent_code;
            }

            public void setParent_code(String parent_code) {
                this.parent_code = parent_code;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                /**
                 * name : 甘蓝类
                 * parent_code : 1
                 * code : 12121
                 */

                private String name;
                private String parent_code;
                private String code;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getParent_code() {
                    return parent_code;
                }

                public void setParent_code(String parent_code) {
                    this.parent_code = parent_code;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }
            }
        }

}
