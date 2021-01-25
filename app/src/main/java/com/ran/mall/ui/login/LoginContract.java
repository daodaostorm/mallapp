package com.ran.mall.ui.login;

import com.ran.mall.base.BaseView;
import com.ran.mall.system.SystemHttpRequest;

/**
 * Created by pc on 2017/10/20.
 */

public interface LoginContract {
    interface View extends BaseView {
        void LoginSuccess();

        void LoginBFail(String err, int errCode);

        void RegisterSuccess();

        void RegisterFail(String err, int errCode);

        SystemHttpRequest getSystemRequest();

        String getRequestBody();

        void saveNameAndPwd(); //保存账号密码

    }

    interface PresenterI {



    }


}
