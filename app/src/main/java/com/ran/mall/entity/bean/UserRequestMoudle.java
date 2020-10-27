package com.ran.mall.entity.bean;

import java.io.Serializable;

/**
 * Created by pc on 2017/10/20.
 */

public class UserRequestMoudle implements Serializable {


    private String loginName;
    private String password;
    private String deviceID;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRequestMoudle{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
