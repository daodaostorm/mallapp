package com.ran.mall.entity.bean;

/**
 * Created by Justin on 2018/9/12/012 14:55.
 * email：WjqJustin@163.com
 * effect：
 */

public class UploadStatusEvent {
    private boolean isFinish;

    public UploadStatusEvent(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public boolean getMessage() {
        return isFinish;
    }
}


