package com.ran.mall.utils;

import android.content.Context;
import android.os.Build;

import com.inuker.bluetooth.library.BluetoothClient;
import com.ran.mall.config.MainApplication;

/**
 * Created by DELL on 2017/8/9.
 */

public class BluetoothClientManger {
    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (BluetoothClientManger.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(MainApplication.getInstance());
                }
            }
        }
        return mClient;
    }
}
