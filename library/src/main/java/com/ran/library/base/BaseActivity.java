package com.ran.library.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.ran.library.util.NetUtil;

/**
 * Created by DELL on 2017/4/16.
 */

public abstract class  BaseActivity extends AppCompatActivity {
    private NetWorkChangeReceive mNetWorkReceive;
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)

    public final String TAG = this.getClass().getSimpleName();

    private void registerNetWork() {
        if (mNetWorkReceive==null){
            mNetWorkReceive=new NetWorkChangeReceive();
            IntentFilter filter=new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(mNetWorkReceive, filter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerNetWork();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void unregisterNetWork() {
        if (mNetWorkReceive!=null){
            unregisterReceiver(mNetWorkReceive);
            mNetWorkReceive=null;
        }
    }




    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterNetWork();
    }

    public <T extends SystemBase>T getSystem(Class<T> tClass){
        return SystemManager.getInstance().getSystem(tClass);
    }
    public class NetWorkChangeReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                //检查网络状态的类型
                int netWrokState = NetUtil.getNetWorkState(context);
                isNetConnect(netWrokState);
            }
        }
    }

    private void isNetConnect(int netMobile ) {
        switch (netMobile) {
            case 1://wifi
                changeWifi();
                break;
            case 0://移动数据
                changeMobile();
                break;
            case -1://没有网络
                changeNetNull();
                break;
        }
    }

    //网络状态更改为wifi状态
    public abstract void changeWifi();
    //网络状态更改为移动数据
    public abstract void changeMobile();
    //网络状态更改为没有网络
    public abstract void changeNetNull();

}
