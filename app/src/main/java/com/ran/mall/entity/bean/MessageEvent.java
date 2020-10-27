package com.ran.mall.entity.bean;

/**
 * Created by Justin on 2018/9/12/012 14:55.
 * email：WjqJustin@163.com
 * effect：
 */

public class MessageEvent {
    private boolean isSuccess;

    public MessageEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean getMessage() {
        return isSuccess;
    }
}


