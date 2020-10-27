package com.ran.mall.entity.bean;

/**
 * Created by Justin on 2018/11/19/019 13:43.
 * email：WjqJustin@163.com
 * effect：
 */

public class OnclickEvent {

    public int getCheckIcom() {
        return checkIcom;
    }

    public void setCheckIcom(int checkIcom) {
        this.checkIcom = checkIcom;
    }

    private int checkIcom;

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    private int viewId;

    private int position;
    private boolean isLocal;

    public boolean isCard() {
        return isCard;
    }

    public void setCard(boolean card) {
        isCard = card;
    }

    private boolean isCard;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index;

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
