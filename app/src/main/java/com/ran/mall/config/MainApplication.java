package com.ran.mall.config;

import com.ran.library.base.SystemManager;
import com.ran.library.baseApplication.CommonApplication;
import com.ran.mall.system.SystemCrashHandler;
import com.ran.mall.utils.ApplicationUtils;
import com.ran.mall.utils.ToastUtils;


public class MainApplication extends CommonApplication {

    private static MainApplication mInstance;

    public static MainApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationUtils.init(this);
        mInstance = this;
        ToastUtils.init(this);


        SystemManager.getInstance().initSystem(this);
        getSystem(SystemCrashHandler.class);

    }

    private boolean isForeground = false;


    public void setForeground(boolean isForeground) {
        this.isForeground = isForeground;
    }

    public boolean getForeground() {
        return isForeground;
    }
}