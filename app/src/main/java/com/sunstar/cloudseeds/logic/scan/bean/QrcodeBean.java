package com.sunstar.cloudseeds.logic.scan.bean;

/**
 * Created by Administrator on 2017/3/17.
 */

public class QrcodeBean {

    private String show_code;
    private String show_msg;
    private String secondary_id;   //族群id
    private String tertiary_id;    //选株id

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

    public String getSecondary_id() {
        return secondary_id;
    }
    public void setSecondary_id(String secondary_id) {
        this.secondary_id = secondary_id;
    }

    public String getTertiary_id() {
        return tertiary_id;
    }
    public void setTertiary_id(String tertiary_id) {
        this.tertiary_id = tertiary_id;
    }

}
