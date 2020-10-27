package com.ran.mall.ui.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ran.mall.entity.bean.UserRequestMoudle;
import com.ran.mall.https.HttpRequestClient;
import com.ran.mall.utils.DataStatisticsUtils;
import com.ran.mall.utils.LogUtils;
import com.ran.mall.utils.PreferenceUtils;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
/**
 * Created by pc on 2017/10/20.
 */

public class LoginPresenter implements LoginContract.PresenterI {

    private String TAG = LoginPresenter.class.getSimpleName();
    private Activity mContext;
    private LoginContract.View mLoginContract;

    public LoginPresenter(@NonNull Activity context, @NonNull LoginContract.View view) {
        mContext = context;
        mLoginContract = view;
    }

    /**
     * login to server
     */
    public void requestServerLogin() {
        if (mLoginContract == null) {
            return;
        }
        final String entry = mLoginContract.getRequestBody();
        if (entry == null || entry.equals("")) {
            return;
        }
        final UserRequestMoudle user = new Gson().fromJson(entry, UserRequestMoudle.class);

        mLoginContract.showLoading();
        mLoginContract.getSystemRequest().responseLogin(entry, new HttpRequestClient.RequestHttpCallBack() {
            @Override
            public void onSuccess(String json) {
                LogUtils.INSTANCE.i(TAG,"responseLoginAssess: " + entry +"\n"+"loginName " + user.getLoginName()+"\n"+json);

                Set<String> tags = new HashSet<String>();
                tags.add(user.getLoginName());
                JPushInterface.setAlias(mContext, 1, user.getLoginName());
                JPushInterface.setTags(mContext, 1, tags);

                if (!TextUtils.isEmpty(json)) {
                    mLoginContract.saveNameAndPwd();
                    PreferenceUtils.setUser(json);
                    PreferenceUtils.setUserRequestData(entry);
                    mLoginContract.LoginSuccess();

                }

            }

            @Override
            public void onFail(final String err, final int code) {
                Log.d(TAG, "responseLoginAssess: " + err + "---" + code);

                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Properties jsonObject = new Properties();

                            jsonObject.put(DataStatisticsUtils.login_state, false);
                            DataStatisticsUtils.setDataEvent(mContext, DataStatisticsUtils.login, jsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mLoginContract.hideLoading();
                        mLoginContract.LoginBFail(err, code);
                    }
                });
            }
        });
    }


}
