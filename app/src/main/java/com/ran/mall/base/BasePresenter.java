package com.ran.mall.base;

import com.txt.library.base.SystemBase;
import com.txt.library.base.SystemManager;

/**
 * Created by DELL on 2017/10/20.
 */

public class BasePresenter {
    public <T extends SystemBase>T getSystem(Class<T> tClass){
        return SystemManager.getInstance().getSystem(tClass);
    }

}
