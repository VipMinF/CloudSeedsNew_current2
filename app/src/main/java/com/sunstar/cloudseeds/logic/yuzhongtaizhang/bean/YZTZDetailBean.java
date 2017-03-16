package com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public class YZTZDetailBean {

        /**
         * show_code : 1
         * show_msg : 列表获取成功
         * code : rule_spq
         * name : 族群调查详细页
         * rule_id : 1212
         * key_value : [{"key":"bzsj","title":"显示信息","value":"2017信息信息","code":"","input_type":"text","input_options":[],"input_configs":[],"img_upload_options":{}},{"key":"fysj","title":"发芽时间","code":"","value":"2017-3-16","input_type":"time","input_options":[],"input_configs":[],"img_upload_options":{}},{"key":"yzsj","title":"移栽输入","code":"","value":"2017-3-16","input_type":"edit","input_options":[],"input_configs":[],"img_upload_options":{"img_upload_key":"zx_upload_img","img_upload_title":"株型图片上传","img_upload_max_count":"1"}},{"key":"khsj","title":"开花多行输入","value":"多行输入大声大声道","input_type":"lines","code":"","input_options":[],"input_configs":[],"img_upload_options":{}},{"key":"bz","title":"下拉选择","value":"大声的选择打算","input_type":"select","code":"1","input_options":[],"input_configs":[],"img_upload_options":{"img_upload_key":"zx_upload_img","img_upload_title":"株型图片上传","img_upload_max_count":"1"}}]
         */

        private String show_code;
        private String show_msg;
        private String code;
        private String name;
        private String rule_id;
        private List<KeyValueBean> key_value;

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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRule_id() {
            return rule_id;
        }

        public void setRule_id(String rule_id) {
            this.rule_id = rule_id;
        }

        public List<KeyValueBean> getKey_value() {
            return key_value;
        }

        public void setKey_value(List<KeyValueBean> key_value) {
            this.key_value = key_value;
        }

        public static class KeyValueBean {
            /**
             * key : bzsj
             * title : 显示信息
             * value : 2017信息信息
             * code :
             * input_type : text
             * input_options : []
             * input_configs : []
             * img_upload_options : {}
             */

            private String key;
            private String title;
            private String value;
            private String code;
            private String input_type;
            private ImgUploadOptionsBean img_upload_options;
            private List<?> input_options;
            private List<?> input_configs;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getInput_type() {
                return input_type;
            }

            public void setInput_type(String input_type) {
                this.input_type = input_type;
            }

            public ImgUploadOptionsBean getImg_upload_options() {
                return img_upload_options;
            }

            public void setImg_upload_options(ImgUploadOptionsBean img_upload_options) {
                this.img_upload_options = img_upload_options;
            }

            public List<?> getInput_options() {
                return input_options;
            }

            public void setInput_options(List<?> input_options) {
                this.input_options = input_options;
            }

            public List<?> getInput_configs() {
                return input_configs;
            }

            public void setInput_configs(List<?> input_configs) {
                this.input_configs = input_configs;
            }

            public static class ImgUploadOptionsBean {
            }
        }
}
