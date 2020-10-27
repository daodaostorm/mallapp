package com.ran.mall.utils

import android.util.Log
import com.ran.mall.AssessConfig

import com.ran.mall.BuildConfig


object LogUtils {

    var isDebug = true

    @JvmStatic
    fun i(msg: String) {
        if (isDebug) {
            Log.i(AssessConfig.LOGNAME, msg)
        }
    }
    @JvmStatic
    fun i(tag: String, msg: String) {
        if (isDebug) {

            Log.i(AssessConfig.LOGNAME, tag + "  " + msg)
        }

    }
    @JvmStatic
    fun d(tag: String, msg: String) {
        if (isDebug) {

            Log.d(AssessConfig.LOGNAME, tag + "  " + msg)
        }

    }
    @JvmStatic
    fun e(tag: String, msg: String) {
        if (isDebug) {

            Log.e(AssessConfig.LOGNAME, tag + "  " + msg)
        }

    }
    @JvmStatic
    fun w(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {

            Log.w(AssessConfig.LOGNAME, tag + "  " + msg)
        }

    }

}
