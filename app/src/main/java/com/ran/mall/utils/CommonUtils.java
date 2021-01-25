package com.ran.mall.utils;

import com.google.gson.Gson;
import com.ran.mall.entity.bean.UserEnCodeBean;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CommonUtils {

    private static final String key = "ranchu.com666666";
    private static final String initVector = "wozaiyule.999999";

    public static String byteToBase64(byte[] bytes) {

        byte[] encode = android.util.Base64.encode(bytes, android.util.Base64.DEFAULT);
        String result = new String(encode);
        return result;
    }

    public static String encPass (String userName, String strOrgin){

        return encrypt(userName +"-_-"+ strOrgin);

    }

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return byteToBase64(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
