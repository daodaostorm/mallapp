package com.ran.mall.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ran.mall.entity.bean.DeviceWifiInfo;
import com.ran.mall.entity.bean.PairedBlueToothInfoBean;
import com.ran.mall.entity.bean.UserBean;
import com.ran.mall.entity.bean.UserRequestMoudle;
import com.ran.mall.entity.bean.VideoLists;

/**
 * Created by pc on 2017/10/19.
 */

public class PreferenceUtils {

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEdit;


    private static SharedPreferences mOcrSharedPreferences;
    private static SharedPreferences.Editor mOcrEdit;

    public static void init(Context context) {
        mSharedPreferences = context.getSharedPreferences("assessRB", Context.MODE_PRIVATE);
        mEdit = mSharedPreferences.edit();

        mOcrSharedPreferences = context.getSharedPreferences("assessOcrRB", Context.MODE_PRIVATE);
        mOcrEdit = mOcrSharedPreferences.edit();
    }

    public static void setOcrSignStarTime(long time){
        mOcrEdit.putLong("time",time);
        mOcrEdit.commit();
    }

    public static long getOcrSignTime(){
        return mOcrSharedPreferences.getLong("time",0);
    }

    public static void setOcrSignToken(String token){
        mOcrEdit.putString("token",token);
        mOcrEdit.commit();
    }

    public static String getOcSignToken(){
        return mOcrSharedPreferences.getString("token",null);
    }


    public static void setUser(String userBean) {
        mEdit.putString("user", userBean == null ? "" : userBean.toString());
        mEdit.commit();
    }

    public static UserBean getUser() {
        String user = mSharedPreferences.getString("user", "");
        if (TextUtils.isEmpty(user)) {
            return null;
        }
        return new Gson().fromJson(user, UserBean.class);
    }

    public static void setVideoList(String videoList) {
        mEdit.putString("videolist", videoList == null ? "" : videoList.toString());
        mEdit.commit();
    }


    public static VideoLists getVideoList() {
        String videolist = mSharedPreferences.getString("videolist", "");
        if (TextUtils.isEmpty(videolist)) {
            return null;
        }
        return new Gson().fromJson(videolist, VideoLists.class);
    }


    public static void setPairedDevice(String strPairedInfo) {
        mEdit.putString("paired_device", strPairedInfo == null ? "" : strPairedInfo.toString());
        mEdit.commit();
    }

    public static PairedBlueToothInfoBean getPairedDevice() {
        String device = mSharedPreferences.getString("paired_device", "");
        if (TextUtils.isEmpty(device)) {
            return null;
        }
        return new Gson().fromJson(device, PairedBlueToothInfoBean.class);
    }

    public static void setWifiDevice(String strPairedInfo) {
        mEdit.putString("wifi_device", strPairedInfo == null ? "" : strPairedInfo.toString());
        mEdit.commit();
    }

    public static DeviceWifiInfo getWifiDevice() {
        String device = mSharedPreferences.getString("wifi_device", "");
        if (TextUtils.isEmpty(device)) {
            return null;
        }
        return new Gson().fromJson(device, DeviceWifiInfo.class);
    }

    public static void setUserRequestData(String userRequest) {
        mEdit.putString("userRequest", userRequest == null ? "" : userRequest);
        mEdit.commit();
    }


    public static UserRequestMoudle getUserRequestData() {
        String user = mSharedPreferences.getString("userRequest", "");
        if (TextUtils.isEmpty(user)) {
            return null;
        }
        return new Gson().fromJson(user, UserRequestMoudle.class);
    }

    public static boolean setLogin(boolean isLogin) {
        mEdit.putBoolean("login", isLogin);
        return mEdit.commit();
    }

    public static boolean isLogin() {

        return mSharedPreferences.getBoolean("login", false);
    }

    public static void clear(){
        mEdit.clear();
        mEdit.commit();
    }

}
