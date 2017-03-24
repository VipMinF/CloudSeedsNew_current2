package com.sunstar.cloudseeds.logic.login;

import android.content.Context;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.extend.ACache;
import com.classichu.classichu.basic.tool.BaseTool;
import com.sunstar.cloudseeds.data.EnCodes;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.login.model.LoginModelImpl;


/**
 * Created by Administrator on 2017/3/15.
 */

public class UserLoginHelper {

    private static final String cachename = "LoginCache";
    private static final String cacheKey = "userloginbean";
    public static final int SALT_SIZE = 8;
    public static final int HASH_INTERATIONS = 1024;
    private static final String SHA1 = "SHA-1";


    public static void saveUserLoginBean_ToAcahe(UserLoginBean userloginbean) {
        Context context = BaseTool.getAppContext();
        ACache macache = ACache.get(context, cachename);
        macache.put(cacheKey, userloginbean);
    }

    public static Object getUserLoginBean_FromAcache() {
        Context context = BaseTool.getAppContext();
        ACache macache = ACache.get(context, cachename);
        return macache.getAsObject(cacheKey);
    }

    public static Boolean autoLogin_Onlocal() {
        UserLoginBean userloginbean = (UserLoginBean) getUserLoginBean_FromAcache();
        if (userloginbean != null && userloginbean.getUserid().length() > 0) {
            return true;
        }
        return false;
    }

    public static void autoLogin_Online(final BasicCallBack<UserLoginBean> loginCallBack) {
        UserLoginBean userloginbean = (UserLoginBean) getUserLoginBean_FromAcache();
        if (userloginbean != null && userloginbean.getUserid().length() > 0) {
            LoginModelImpl loginmodelimpl = new LoginModelImpl();
            loginmodelimpl.loadData(UrlDatas.Login_URL, userloginbean.getUsername(), userloginbean.getPassword(), new BasicCallBack<UserLoginBean>() {
                @Override
                public void onSuccess(UserLoginBean userloginBean) {

                    loginCallBack.onSuccess(userloginBean);
                    UserLoginHelper.saveUserLoginBean_ToAcahe(userloginBean);
                }

                @Override
                public void onError(String s) {
                    loginCallBack.onError(s);
                }
            });

        } else {
            loginCallBack.onError("登录失败");
        }
    }

    public static Boolean loginOut() {
        Context context = BaseTool.getAppContext();
        ACache macache = ACache.get(context, cachename);
        return macache.remove(cacheKey);
    }


    public static UserLoginBean userLoginBean() {
        return (UserLoginBean) getUserLoginBean_FromAcache();
    }

    public static String getUserid() {
        UserLoginBean userLoginBean = (UserLoginBean) getUserLoginBean_FromAcache();
        return userLoginBean.getUserid();
    }


    /**
     * 明文密码加密
     */
    public static String entryptionPassword(String plainPassword) {
        String plain = EnCodes.unescapeHtml(plainPassword);
        byte[] salt = EnCodes.generateSalt();
        byte[] hashPassword = EnCodes.digest(plain.getBytes(), SHA1, salt, HASH_INTERATIONS);
        return EnCodes.encodeHex(salt) + EnCodes.encodeHex(hashPassword);
    }


    /**
     * 验证密码
     */
    public static Boolean validatePassWord(String psw, String entryptedPsw) {

        String pain = EnCodes.unescapeHtml(psw);
        byte[] salt = EnCodes.decodeHex(entryptedPsw.substring(0, 16));
        byte[] hashPassword = EnCodes.digest(pain.getBytes(), SHA1, salt, HASH_INTERATIONS);
        String psssword = EnCodes.encodeHex(salt) + EnCodes.encodeHex(hashPassword);
        return entryptedPsw.equals(psssword);
    }


}
