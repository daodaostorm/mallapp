package com.ran.mall.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ran.mall.entity.bean.UserBean;
import com.ran.mall.entity.bean.UserRequestMoudle;

/**
 * Created by pc on 2017/10/19.
 */

public class PreferenceUtils {

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEdit;

    public static void init(Context context) {
        mSharedPreferences = context.getSharedPreferences("mallapp", Context.MODE_PRIVATE);
        mEdit = mSharedPreferences.edit();

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
