package com.ran.library.baseApplication;

import android.app.Application;

import com.ran.library.base.SystemBase;
import com.ran.library.base.SystemManager;

/**
 * Created by DELL on 2017/5/23.
 */

public class CommonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public <T extends SystemBase>T getSystem(Class<T> tClass){
        return SystemManager.getInstance().getSystem(tClass);
    }
}
