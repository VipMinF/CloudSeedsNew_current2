package com.sunstar.cloudseeds.logic.shangpinqi.bean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/16.
 */

public class SPQDetailBean {


        /**
         * show_code : 1
         * show_msg : 列表获取成功
         * code : rule_zq
         * name : 族群调查编辑页
         * rule_id : 1212
         * key_value : [{"key":"bzsj","title":"显示信息","value":"2017信息信息","code":"","input_type":"text","input_options":[],"input_configs":[],"img_upload_options":{}},{"key":"fysj","title":"发芽时间","code":"","value":"2017-3-16","input_type":"time","input_options":[],"input_configs":[{"config":"after","config_value":"2016-3-6 09:54:55"},{"config":"before","config_value":"2017-3-6 09:54:55"}],"img_upload_options":{}},{"key":"yzsj","title":"移栽输入","code":"","value":"2017-3-16","input_type":"edit","input_options":[],"input_configs":[{"config":"min","config_value":"5"},{"config":"max","config_value":"20"}],"img_upload_options":{"img_upload_key":"zx_upload_img","img_upload_title":"株型图片上传","img_upload_max_count":"1"}},{"key":"khsj","title":"开花多行输入","value":"多行输入大声大声道","input_type":"lines","code":"","input_options":[],"input_configs":[{"config":"min","config_value":""},{"config":"max","config_value":"300"}],"img_upload_options":{}},{"key":"bz","title":"下拉选择","value":"大声的选择打算","input_type":"select","code":"1","input_options":[{"option_code":"0","option_value":"请选择株型"},{"option_code":"1","option_value":"立"},{"option_code":"2","option_value":"半立"}],"input_configs":[],"img_upload_options":{"img_upload_key":"zx_upload_img","img_upload_title":"株型图片上传","img_upload_max_count":"1"}}]
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
            private List<InputOptionsBean> input_options;
            private List<InputConfigsBean> input_configs;

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

            public List<InputOptionsBean> getInput_options() {
                return input_options;
            }

            public void setInput_options(List<InputOptionsBean> input_options) {
                this.input_options = input_options;
            }

            public List<InputConfigsBean> getInput_configs() {
                return input_configs;
            }

            public void setInput_configs(List<InputConfigsBean> input_configs) {
                this.input_configs = input_configs;
            }

            public static class InputOptionsBean {
                public String getOption_code() {
                    return option_code;
                }

                public void setOption_code(String option_code) {
                    this.option_code = option_code;
                }

                public String getOption_value() {
                    return option_value;
                }

                public void setOption_value(String option_value) {
                    this.option_value = option_value;
                }

                private String option_code;
                private String option_value;
            }

            public static class InputConfigsBean {
                public String getConfig() {
                    return config;
                }

                public void setConfig(String config) {
                    this.config = config;
                }

                public String getConfig_value() {
                    return config_value;
                }

                public void setConfig_value(String config_value) {
                    this.config_value = config_value;
                }

                private String config;
                private String config_value;
            }

            public static class ImgUploadOptionsBean {
            }
        }

}
