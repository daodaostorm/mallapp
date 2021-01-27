package com.ran.mall.ui.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ran.mall.entity.bean.UserRegisterBean;
import com.ran.mall.entity.bean.UserRequestMoudle;
import com.ran.mall.https.HttpRequestClient;
import com.ran.mall.utils.LogUtils;
import com.ran.mall.utils.PreferenceUtils;
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
                LogUtils.INSTANCE.i(TAG,"responseLoginAssess: " + entry +"\n"+"loginName " + user.getUsername()+"\n"+json);

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

                        mLoginContract.hideLoading();
                        mLoginContract.LoginBFail(err, code);
                    }
                });
            }
        });
    }

    /**
     * create user
     */
    public void requestRegister(String strJsonInfo) {
        if (mLoginContract == null) {
            return;
        }

        final UserRegisterBean user = new Gson().fromJson(strJsonInfo, UserRegisterBean.class);

        mLoginContract.showLoading();
        mLoginContract.getSystemRequest().responseRegister(strJsonInfo, new HttpRequestClient.RequestHttpCallBack() {
            @Override
            public void onSuccess(String json) {
                mLoginContract.RegisterSuccess();
            }

            @Override
            public void onFail(final String err, final int code) {
                Log.d(TAG, "responseLoginAssess: " + err + "---" + code);

                mLoginContract.LoginBFail(err, code);
            }
        });
    }

}
