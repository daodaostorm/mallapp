package com.ran.mall.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ran.mall.config.MainApplication;
import com.ran.mall.utils.permission.PermissionUtils;

import java.lang.ref.WeakReference;
import java.util.Stack;

public final class ApplicationUtils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    static WeakReference<Activity> sTopActivityWeakRef;


    private static ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            addActivity(activity);

        }

        @Override
        public void onActivityStarted(Activity activity) {

            MainApplication.getInstance().setForeground(true);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            MainApplication.getInstance().setForeground(false);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            finishActivity(activity);
        }
    };

    private ApplicationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param app 应用
     */
    public static void init(@NonNull final Application app) {
        ApplicationUtils.sApplication = app;
        app.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * 获取 Application
     *
     * @return Application
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

    public static void finishActivity() {

        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();

    }

    private static Stack<Activity> activityStack;

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if (!activityStack.contains(activity))
            activityStack.add(activity);
    }

    private static void setTopActivityWeakRef(final Activity activity) {
        if (activity.getClass() == PermissionUtils.PermissionActivity.class) return;
        if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
            sTopActivityWeakRef = new WeakReference<>(activity);
        }
    }

    public static Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sTopActivityWeakRef != null) {
            currentActivity = sTopActivityWeakRef.get();
        }

        return currentActivity;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
//            activity.finish();
            activity = null;
        }
    }


}
