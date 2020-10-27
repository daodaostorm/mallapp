package com.ran.mall.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class AppUtils {
    private ActivityManager mActivityManager;


    /**
     * 应用是否在前台
     * @param context
     * @param className
     * @return
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAppOnForeground(Context context){
        if (context == null) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (context.getPackageName().equals(cpn.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    private ActivityManager.RunningAppProcessInfo getForegroundApp(Context context) {
        ActivityManager.RunningAppProcessInfo result = null, info = null;

        if (mActivityManager == null)
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> l = mActivityManager.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> i = l.iterator();
        while (i.hasNext()) {
            info = i.next();
            if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && !isRunningService(context, info.processName)) {
                result = info;
                break;
            }
        }
        return result;
    }

    private ComponentName getActivityForApp(Context context, ActivityManager.RunningAppProcessInfo target) {
        ComponentName result = null;
        ActivityManager.RunningTaskInfo info;

        if (target == null)
            return null;

        if (mActivityManager == null)
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> l = mActivityManager.getRunningTasks(1000);
        Iterator<ActivityManager.RunningTaskInfo> i = l.iterator();

        while (i.hasNext()) {
            info = i.next();
            if (info.baseActivity.getPackageName().equals(target.processName)) {
                result = info.topActivity;
                break;
            }
        }

        return result;
    }

    private boolean isStillActive(Context context, ActivityManager.RunningAppProcessInfo process, ComponentName activity) {
        // activity can be null in cases, where one app starts another. for example, astro
        // starting rock player when a move file was clicked. we dont have an activity then,
        // but the package exits as soon as back is hit. so we can ignore the activity
        // in this case
        if (process == null)
            return false;

        ActivityManager.RunningAppProcessInfo currentFg = getForegroundApp(context);
        ComponentName currentActivity = getActivityForApp(context, currentFg);

        if (currentFg != null && currentFg.processName.equals(process.processName) &&
                (activity == null || currentActivity.compareTo(activity) == 0))
            return true;
        return false;
    }

    private boolean isRunningService(Context context, String processName) {
        if (TextUtils.isEmpty(processName)) {
            return false;
        }
        ActivityManager.RunningServiceInfo service;

        if (mActivityManager == null)
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = mActivityManager.getRunningServices(1000);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            service = i.next();
            if (service.process.equals(processName)) {
                return true;
            }
        }
        return false;
    }

    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    public static String convertToStringRepresentation(final long value){
        final long[] dividers = new long[] { T, G, M, K, 1 };
        final String[] units = new String[] { "TB", "GB", "MB", "KB", "B" };
        if(value < 1)
            throw new IllegalArgumentException("Invalid file size: " + value);
        String result = null;
        for(int i = 0; i < dividers.length; i++){
            final long divider = dividers[i];
            if(value >= divider){
                result = format(value, divider, units[i]);
                break;
            }
        }
        return result;
    }

    public static String getUuid(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }


    private static String format(final long value,
                                 final long divider,
                                 final String unit){
        final double result =
                divider > 1 ? (double) value / (double) divider : (double) value;
        return new DecimalFormat("#,##0.#").format(result) + " " + unit;
    }

    private static long mInterval = 500;
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < mInterval) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String getRunningActivityName(Context context){
        ActivityManager activityManager=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

}
