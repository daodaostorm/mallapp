package com.ran.mall.entity.bean;

import java.io.Serializable;

/**
 */

public class UserEnCodeBean implements Serializable {
    private String origin;
    private String timestamp;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
