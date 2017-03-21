package com.sunstar.cloudseeds.logic.login.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/15.
 */

public class UserLoginBean implements Serializable {

    private String show_code;
    private String show_msg;
    private String userid;
    private String password;
    private String username;
    private String moible;
    private String email;
    private String address;
    private String userface;
    private String company;
    private String tickname;



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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userId) {
        this.userid = userId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getMoible() {
        return moible;
    }
    public void setMoible(String mobile) {
        this.moible = mobile;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserface() {
        return userface;
    }
    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public String getTickname() {
        return tickname;
    }
    public void setTickname(String tickname) {
        this.tickname = tickname;
    }


    private static UserLoginBean userLoginBean;
    // 构造函数私有化
    private UserLoginBean() {
    }
    // 提供一个全局的静态方法
    public static UserLoginBean getUserLoginBean() {
        if (userLoginBean == null) {
            synchronized (UserLoginBean.class) {
                if (userLoginBean == null) {
                    userLoginBean = new UserLoginBean();
                }
            }
        }
        return userLoginBean;
    }


}
