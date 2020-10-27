package com.ran.mall.utils

/**
 * Created by Jutsin on 2018/5/10/010.
 * email：WjqJutin@163.com
 * effect：checkNull
 */
class CheckNullUtils {

    //判断两个参数不为null
    fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
        if (value1 != null && value2 != null) {
            bothNotNull(value1, value2)
        }
    }

}