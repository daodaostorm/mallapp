package com.ran.mall.entity.bean;

import java.io.Serializable;

/**
 */

public class UserRequestMoudle implements Serializable {


    private String username;
    private String password;
    private String deviceID;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

}
