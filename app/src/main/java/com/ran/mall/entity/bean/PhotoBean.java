package com.ran.mall.entity.bean;

import android.net.Uri;

/**
 * Created by DELL on 2018/1/12.
 */

public class PhotoBean {
    private String photoPath;
    private boolean isDelect;
    private boolean isAdd;
    private Uri mUri;


    public Uri getmUri() {
        return mUri;
    }

    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean isDelect() {
        return isDelect;
    }

    public void setDelect(boolean delect) {
        isDelect = delect;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }
}
