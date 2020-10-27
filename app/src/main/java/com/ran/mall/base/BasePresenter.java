package com.ran.mall.base;

import com.ran.library.base.SystemBase;
import com.ran.library.base.SystemManager;

/**
 * Created by DELL on 2017/10/20.
 */

public class BasePresenter {
    public <T extends SystemBase>T getSystem(Class<T> tClass){
        return SystemManager.getInstance().getSystem(tClass);
    }

}
