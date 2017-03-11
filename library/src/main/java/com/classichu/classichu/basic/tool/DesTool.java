package com.classichu.classichu.basic.tool;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesTool {

    private static byte[] iv = {0x12, 0x34, 0x56, 0x78, 0x56, 0x34, 0x12, 0x34};//向量
    private final static String DESUTIL_KEY = "d3H9H3f9"; //加密解密密匙

    public static String encryptDES(String encryptString) throws Exception {
        return encryptDES(encryptString, DESUTIL_KEY);
    }

    public static String encryptDES(String encryptString, String Des_Key) throws Exception {

        IvParameterSpec zeroIv = new IvParameterSpec(iv);

        SecretKeySpec key = new SecretKeySpec(Des_Key.getBytes(), "DES");

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);

        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

        return Base64.encodeToString(encryptedData, Base64.NO_WRAP);

    }

    public static String decryptDES(String decryptString) throws Exception {
        return decryptDES(decryptString,decryptString);
    }

    public static String decryptDES(String decryptString, String Des_Key) throws Exception {

        byte[] byteMi = Base64.decode(decryptString, Base64.NO_WRAP);

        IvParameterSpec zeroIv = new IvParameterSpec(iv);

        SecretKeySpec key = new SecretKeySpec(Des_Key.getBytes(), "DES");

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);

        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);

    }

}
