package com.ran.mall.utils;

import com.ran.mall.AssessConfig;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by DELL on 2018/3/11.
 */

public class OcrSign {

    public static final String TAG=OcrSign.class.getSimpleName();

    public final static long appid=1255357306;
    public final static String bucketName="txtechnology";
    public final static String secretId="AKIDdBcuOrSTaZRI5lTkIAxcDjAUnS2VJ01x";
    public final static String secretKey="2znQulUEuSsBQafczELOj1BA0CKXdXRG";
    public final static long effectiveTime=7776000;


    public static String getOcrAppSign(){
        String token;
        if (PreferenceUtils.getOcSignToken()!=null&&PreferenceUtils.getOcrSignTime()!=0&&((System.currentTimeMillis()/1000-PreferenceUtils.getOcrSignTime())<effectiveTime)){
            token=PreferenceUtils.getOcSignToken();
        } else {
            token=appSign(appid,secretId,secretKey,bucketName,effectiveTime);
            PreferenceUtils.setOcrSignToken(token);
        }
        return token;
    }



    /**
     * 生成Authorization签名字段
     *
     * @param appId
     * @param secretId
     * @param secretKey
     * @param bucketName
     * @param expired
     * @return
     * @throws Exception
     */
    public static String appSign(long appId, String secretId, String secretKey, String bucketName,
                                 long expired){
        long now = System.currentTimeMillis() / 1000;
        PreferenceUtils.setOcrSignStarTime(now);
        int rdm = Math.abs(new Random().nextInt());
        String plainText = String.format("a=%d&b=%s&k=%s&t=%d&e=%d&r=%d", appId, bucketName,
                secretId, now, now + expired, rdm);
        byte[] hmacDigest = new byte[0];
        try {
            hmacDigest = HmacSha1(plainText, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] signContent = new byte[hmacDigest.length + plainText.getBytes().length];
        System.arraycopy(hmacDigest, 0, signContent, 0, hmacDigest.length);
        System.arraycopy(plainText.getBytes(), 0, signContent, hmacDigest.length,
                plainText.getBytes().length);
        return Base64Encode(signContent);
    }

    /**
     * 生成base64编码
     *
     * @param binaryData
     * @return
     */
    public static String Base64Encode(byte[] binaryData) {
        String encodedstr = Base64.getEncoder().encodeToString(binaryData);
        return encodedstr;
    }

    /**
     * 生成hmacsha1签名
     *
     * @param binaryData
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] HmacSha1(byte[] binaryData, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        mac.init(secretKey);
        byte[] HmacSha1Digest = mac.doFinal(binaryData);
        return HmacSha1Digest;
    }

    /**
     * 生成hmacsha1签名
     *
     * @param plainText
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] HmacSha1(String plainText, String key) throws Exception {
        return HmacSha1(plainText.getBytes(), key);
    }


    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 is unsupported", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MessageDigest不支持MD5Util", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


}
