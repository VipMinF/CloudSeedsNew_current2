package com.sunstar.cloudseeds.logic.normalrecord.Bean;

import android.util.Log;

import java.util.List;

/**
 * Created by xiaoxian on 2017/4/25.
 */
//{"code":1,"message":"请求成功","info":[{"show_code":"1","show_msg":"农事类型获取成功","all_count":3,"list":[{"name":"灌溉1","code":"77301e492b904f50a097400d9dce10c8"},{"name":"打药","code":"c62eb4ae86964864a67de92cc637865d"},{"name":"施肥","code":"2c458940e8014fcc856e0028d2f85f74"}]}]}
public class FarmTypeListBean {
    private String code;
    private String message;
    private List<Info> info;

    public String getCode() {
        return  code;
    }

    public  String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public  void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return code+"  "+message+"   "+info;
    }

        public List<ListBean> getInfo() {
        Info info = (Info)this.info.get(0);
            Log.v("infolist",""+info.list);
          return info.list;
    }

    public static class Info {
        private  String show_code;
        private String show_msg;
        private String all_count;
        private List<ListBean> list;

        public  String getShow_code() {
            return show_code;
        }

        public String getShow_msg() {
            return  show_msg;
        }

        public  String getAll_count() {
            return  all_count;
        }

        public List<ListBean>getList() {
            return  list;
        }

        public  void setShow_code(String code) {
            show_code = code;
        }

        public void setShow_msg(String msg) {
            show_msg = msg;
        }

        public void setAll_count(String all_count) {
            this.all_count = all_count;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "   "+list;
        }
    }

    public static class ListBean {
       private  String name;
        private String code;

        public String getName() {
            Log.v("listbeanname",name);
            return name;
        }

        public String getCode() {
            return code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
          return "  "+name+" "+code;
        }
    }

}
