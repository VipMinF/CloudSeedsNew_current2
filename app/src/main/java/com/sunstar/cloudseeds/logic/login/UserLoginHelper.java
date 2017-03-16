package com.sunstar.cloudseeds.logic.login;

import android.content.Context;

import com.classichu.classichu.basic.extend.ACache;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;



/**
 * Created by Administrator on 2017/3/15.
 */

public class UserLoginHelper {

    private static final  String cachename="LoginCache";
    private static final  String cacheKey="userloginbean";

    public static void saveUserLoginBean_ToAcahe(Context context, UserLoginBean userloginbean){
        ACache macache = ACache.get(context,cachename);
        macache.put(cacheKey,userloginbean);
    }

    public static Object getUserLoginBean_FromAcache(Context context){
        ACache macache = ACache.get(context,cachename);
        return macache.getAsObject(cacheKey);
    }


    public static Boolean autoLoginApp(Context context){

            UserLoginBean userloginbean = (UserLoginBean)getUserLoginBean_FromAcache(context);
            if(userloginbean!= null && userloginbean.getUserid().length() > 0) {
                return  true;
            }
        return  false;
    }

    public static Boolean loginOut(Context context){

        ACache macache = ACache.get(context,cachename);
        return  macache.remove(cacheKey);
    }


    public static  UserLoginBean userLoginBean(Context context){
        return  (UserLoginBean) getUserLoginBean_FromAcache(context);
    }


    public static Boolean autoLogin(File file){

        if (file.exists()) {
            UserLoginBean userloginbean = (UserLoginBean) readObject(file);
            if(userloginbean!= null && userloginbean.getUserid().length() > 0) {
                return  true;
            }
        }
        return  false;
    }

    public static void saveUserLoginBean(File file){

        //保存数据
        UserLoginBean userloginbean = new UserLoginBean();
        userloginbean.setUserid("111111@qq.com");
        userloginbean.setUsername("123");
        userloginbean.setPassword("111111");
        userloginbean.setAddress("00");
        userloginbean.setCompany("浙江瞬时达网络有限公司");
        userloginbean.setEmail("111111@qq.com");
        userloginbean.setMoible("13000000000");
        userloginbean.setTickname("hello");
        userloginbean.setUserface("");
        userloginbean.setShow_code("1");
        userloginbean.setShow_msg("登录成功");

        if (file.exists()){
            writeObject(userloginbean,file);
        }

    }


    /**
     * 删除本地信息
     */
    public static void loginOut(File file) {

        if (file.exists()) {
            file.delete();
        }
    }


    /**
     * 序列化存储文件
     * @param object
     * @param file
     */
    public static void writeObject(Object object,File file){
        ObjectOutputStream oos;
        boolean bb = file.exists();
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 反序列化
     * @param file
     */
    public static Object readObject(File file){
        ObjectInputStream ois = null;
        Object object = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            object = ois.readObject();
            ois.close();
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

}
