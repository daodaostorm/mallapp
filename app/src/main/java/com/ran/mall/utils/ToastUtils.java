package com.ran.mall.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.ran.mall.BuildConfig;

/**
 * Created by fanglin on 2016/8/3.
 */
public class ToastUtils {

    private static Toast mToast;

    public static void init(Context context) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }

    }

    public static void shortShow(int res) {
        mToast.setText(res);
        mToast.show();
    }

    public static void shortShow(String res) {
        mToast.setText(res);
        mToast.show();
    }

    public static void showToast(final String toast, final Context context) {
        if (BuildConfig.DEBUG)
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }).start();
    }


    public static void longShow(int res) {
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(res);
        mToast.show();
    }

    public static void longShow(String res) {
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(res);
        mToast.show();
    }


}
