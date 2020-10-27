package com.ran.mall.config;

import android.app.Notification;
import android.bluetooth.BluetoothGatt;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mta.track.StatisticsDataAPI;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.ran.library.base.SystemManager;
import com.ran.library.baseApplication.CommonApplication;
import com.ran.mall.BuildConfig;
import com.ran.mall.R;
import com.ran.mall.entity.bean.ReportStateListBean;
import com.ran.mall.system.SystemBaiduLocation;
import com.ran.mall.system.SystemCrashHandler;
import com.ran.mall.utils.ApplicationUtils;
import com.ran.mall.utils.ToastUtils;

import java.util.ArrayList;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


public class MainApplication extends CommonApplication {

    private static MainApplication mInstance;

    public static MainApplication getInstance() {
        return mInstance;
    }

    public static ArrayList<ReportStateListBean> reportStateList = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();

//        File file = new File(Environment.getExternalStorageDirectory(),"app1.trace");
//
//        Debug.startMethodTracing(file.getAbsolutePath());
        DoraemonKit.install(this);
//        initLeakCanary();
        ApplicationUtils.init(this);
        mInstance = this;
        StatConfig.setDebugEnable(BuildConfig.DEBUG); // [可选]设置是否打开debug输出，上线时请关闭，Logcat标签为"MtaSDK"
        StatService.registerActivityLifecycleCallbacks(this);   // 基础统计API
        StatisticsDataAPI.instance(this);
        ToastUtils.init(this);

        initJpushConfig();


        SystemManager.getInstance().initSystem(this);
        //getSystem(SystemKandy.class);//初始化Kandy
        //getSystem(SystemChat.class);//初始化消息控制类
        getSystem(SystemBaiduLocation.class);
        getSystem(SystemCrashHandler.class);
//        Debug.stopMethodTracing();

    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }

    }

    private void initJpushConfig() {
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.drawable.jpush_notification_icon;

        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）

        JPushInterface.setPushNotificationBuilder(100, builder);

    }

    public void setReportStateList(ArrayList<ReportStateListBean> rsListBean) {
        reportStateList.clear();
        reportStateList.addAll(rsListBean);
    }

    public ArrayList<ReportStateListBean> getReportStateList() {
        return reportStateList;
    }


    private boolean isForeground = false;


    public void setForeground(boolean isForeground) {
        this.isForeground = isForeground;
    }

    public boolean getForeground() {
        return isForeground;
    }
}