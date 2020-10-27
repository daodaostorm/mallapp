package com.ran.mall.entity.bean;

/**
 * Created by Justin on 2018/9/12/012 14:55.
 * email：WjqJustin@163.com
 * effect：
 */

public class FragmentMessageEvent {
    private String json;

    public FragmentMessageEvent(String json) {
        this.json = json;
    }

    public String getMessage() {
        return json;
    }
}


